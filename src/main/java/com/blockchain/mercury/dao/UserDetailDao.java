package com.blockchain.mercury.dao;

import com.blockchain.mercury.entity.UserDetail;
import com.blockchain.mercury.enums.Currency;
import com.blockchain.mercury.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Gaurav Sharma on 6/22/2019.
 */
@Component
public class UserDetailDao {

    @Autowired
    UserDetailRepository repo;


    public UserDetail findByUserIdAndToken(int userId, String token) {
        return repo.findByUserIdAndToken(userId, token);
    }

    public List<UserDetail> findAllByUserId(int userId) {
        return repo.findAllByUserId(userId);
    }

    public HashMap<Currency, Double> getUserDetails(int userId) {

        List<UserDetail> userDetails = findAllByUserId(userId);

        //If the user does not exist
        if (userDetails == null || userDetails.size() == 0) {
            return new HashMap<>();
        }

        HashMap<Currency, Double> map = new HashMap<>();

        for (UserDetail details : userDetails) {
            map.put(Currency.valueOf(details.getToken()), details.getQuantity());
        }

        return map;
    }


    public void saveOrUpdate(int userId, Currency token, double quantity) {

        UserDetail userDetail = findByUserIdAndToken(userId, token.toString());
        if (userDetail != null)
            userDetail.setQuantity(quantity);
        else {
            userDetail = new UserDetail(userId, token.toString(), quantity);
        }
        repo.save(userDetail);
    }

    public void save(UserDetail userDetail) {
        repo.save(userDetail);
    }
}
