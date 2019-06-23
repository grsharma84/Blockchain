package com.blockchain.mercury.dao;

import com.blockchain.mercury.entity.Transaction;
import com.blockchain.mercury.enums.Status;
import com.blockchain.mercury.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Gaurav Sharma on 6/22/2019.
 */
@Component
public class TransactionDao {

    @Autowired
    TransactionRepository repo;

    public Transaction findByTransactionId(int transactionId) {
        return repo.findByTransactionId(transactionId);
    }

    public List<Transaction> findAllByUserId(int userId) {
        return repo.findAllByUserId(userId);
    }

    public Transaction saveOrUpdate(Transaction tran) {
        if(tran.getStatus().equals(Status.NOT_SETTLED.toString()))
            tran.setExecutionDate(new Date(Calendar.getInstance().getTime().getTime()));

        if(tran.getStatus().equals(Status.SETTLED.toString()))
            tran.setSettlementDate(new Date(Calendar.getInstance().getTime().getTime()));

        return repo.save(tran);
    }
}
