package com.customerapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customerapp.entity.Transaction;



@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	
 List<Transaction> findByCustomerId(Integer customerId);

}
