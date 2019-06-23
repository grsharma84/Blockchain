package com.blockchain.mercury.repository;

import com.blockchain.mercury.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findByTransactionId(int transactionId);

    List<Transaction> findAllByUserId(int userId);
}