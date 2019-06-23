package com.blockchain.mercury.controllers;

import com.blockchain.mercury.dao.TransactionDao;
import com.blockchain.mercury.dao.UserDetailDao;
import com.blockchain.mercury.entity.Transaction;
import com.blockchain.mercury.enums.Currency;
import com.blockchain.mercury.enums.Status;
import com.blockchain.mercury.utils.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    TransactionDao transactionDao;
    @Autowired
    UserDetailDao userDetailDao;
    @Autowired
    Cache cache;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping("/user/add/userid/{userid}/token/{token}/quantity/{quantity}")
    @ResponseBody
    public String addUser(@PathVariable("userid") int userid,
                          @PathVariable("token") Currency token,
                          @PathVariable("quantity") double quantity) {

        logger.info("Adding new user " + userid + " token " +
                token.toString() + " quantity " + quantity);
        userDetailDao.saveOrUpdate(userid, token, quantity);
        return "Successfully saved the user";
    }

    @GetMapping("/user/withdraw/userid/{userid}/token/{token}/quantity/{quantity}")
    @ResponseBody
    public String withdraw(@PathVariable("userid") int userid,
                           @PathVariable("token") Currency token,
                           @PathVariable("quantity") double quantity) {

        logger.info("Received request to withdraw " + token.toString() + " from user id " + userid);
        if (cache.get(userid) != null) {
            return updateMap(cache.get(userid), userid, token, quantity);
        } else {
            HashMap<Currency, Double> userMap = userDetailDao.getUserDetails(userid);
            if (userMap == null || userMap.size() == 0) {
                logger.info("Make sure user exists, add a new user using the user API");
                return "No such User Exist";
            }
            return updateMap(userMap, userid, token, quantity);
        }
    }


    public String updateMap(HashMap<Currency, Double> map, int userId, Currency token, Double quantity) {

        Transaction tran;
        String res = "";
        if (map.containsKey(token) && map.get(token) >= quantity) {
            tran = new Transaction();
            tran.setSoldQuantity(quantity);
            tran.setSoldToken(token.toString());
            tran.setUserId(userId);
            tran.setStatus(Status.NOT_SETTLED.toString());
            //Save the transaction in the database
            transactionDao.saveOrUpdate(tran);
            //Update the user detail table with the updated token quantity
            userDetailDao.saveOrUpdate(userId, token, map.get(token) - quantity);
            //Update the map with the latest quantity
            map.put(token, map.get(token) - quantity);
            res = "SUFFICIENT_BALANCE";
        } else {
            res = "INSUFFICIENT_BALANCE";
        }

        //Add/Update this entry in the cache
        cache.put(userId, map);
        return res;
    }

}
