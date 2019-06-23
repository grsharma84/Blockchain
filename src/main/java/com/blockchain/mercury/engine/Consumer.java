package com.blockchain.mercury.engine;

import com.blockchain.mercury.dao.TransactionDao;
import com.blockchain.mercury.dao.UserDetailDao;
import com.blockchain.mercury.entity.Transaction;
import com.blockchain.mercury.entity.UserDetail;
import com.blockchain.mercury.model.KafkaMessage;
import com.blockchain.mercury.enums.Status;
import com.blockchain.mercury.utils.Cache;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    Gson gson;

    @Autowired
    TransactionDao transactionDao;
    @Autowired
    UserDetailDao userDao;
    @Autowired
    Cache cache;

    Consumer() {
        gson = new Gson();
    }

    @KafkaListener(topics = "mercury", groupId = "group_id")
    public void consume(String message) throws Exception {
        logger.info(String.format("Consuming message -> %s", message));

        try {
            //Convert the kafka message to Kafka message object
            KafkaMessage kafkaMessage = gson.fromJson(message, KafkaMessage.class);

            Transaction transaction = transactionDao.findByTransactionId(kafkaMessage.getTransactionId());

            //If its not a settled transaction
            if (transaction != null && !transaction.getStatus().equals(Status.SETTLED.toString())) {

                //If the sold token quantity does not match, update the user data for sold quantity
                if (transaction.getSoldToken().equals(kafkaMessage.getSoldToken().toString())
                        && transaction.getSoldQuantity() != kafkaMessage.getSoldQuantity()) {
                    UserDetail userDetail = userDao.findByUserIdAndToken(transaction.getUserId(), transaction.getSoldToken().toString());
                    //Add back quantity
                    userDetail.setQuantity(userDetail.getQuantity() + (transaction.getSoldQuantity() - kafkaMessage.getSoldQuantity()));
                    //Evict from the cache as the quantity has been modified
                    cache.evict(transaction.getUserId());
                    userDao.save(userDetail);
                }

                //Update the user data for the brought quantity
                UserDetail userDetail = userDao.findByUserIdAndToken(kafkaMessage.getUserId(), kafkaMessage.getBroughtToken().toString());
                if (userDetail != null && kafkaMessage.getBroughtQuantity() > 0.0) {
                    userDetail.setQuantity(userDetail.getQuantity() + kafkaMessage.getBroughtQuantity());
                    cache.evict(transaction.getUserId());
                    userDao.save(userDetail);
                }else{
                    //If there is no record for that user and token combination
                    UserDetail userDetail1 = new UserDetail(kafkaMessage.getUserId(),kafkaMessage.getBroughtToken().toString(),
                            kafkaMessage.getBroughtQuantity());
                    userDao.save(userDetail1);
                }

                transaction.setBoughtToken(kafkaMessage.getBroughtToken().toString());
                transaction.setBoughtQuantity(kafkaMessage.getBroughtQuantity());
                transaction.setStatus(Status.SETTLED.toString());
                transactionDao.saveOrUpdate(transaction);

            } else {
                logger.info(String.format("No such Transaction exist, skipping this record"));
            }
        } catch (Exception e) {
            logger.info("Exception " + e.getMessage());
            throw e;
        }
    }
}
