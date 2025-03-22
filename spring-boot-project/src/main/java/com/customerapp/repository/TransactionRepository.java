package com.customerapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.customerapp.entity.Transaction;



@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	
 @Query(nativeQuery = true, value = "SELECT customer_id, DATE_FORMAT(transaction_date, '%y-%m') AS month, SUM(CASE WHEN amount > 100 THEN (amount - 100) * 2 + 50 WHEN amount > 50 THEN (amount - 50) ELSE 0 END) AS reward_points FROM transactions GROUP BY customer_id, month ORDER BY customer_id, month") 
	List<Object[]>	getRewardPointsPerMonth();
	
 @Query(nativeQuery = true, value = "SELECT customer_id, SUM(CASE WHEN amount > 100 THEN (amount - 100) * 2 + 50 WHEN amount > 50 THEN (amount - 50) ELSE 0 END) AS reward_points FROM transactions GROUP BY customer_id") 
 List<Object[]>	getTotalRewardPoints();

}
