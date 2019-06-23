package com.blockchain.mercury.repository;

import com.blockchain.mercury.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {


    UserDetail findByUserIdAndToken(int userId, String token);

    List<UserDetail> findAllByUserId(int userId);

    UserDetail findById(int id);
}