package com.customerapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customerapp.entity.Customer;
import com.customerapp.entity.RewardPoints;
import com.customerapp.entity.Transaction;
import com.customerapp.repository.CustomerRepository;
import com.customerapp.repository.RewardPointsRepository;
import com.customerapp.repository.TransactionRepository;

import jakarta.transaction.UserTransaction;

@Service
public class RewardServices {

	@Autowired
	private TransactionRepository transactionRepository ;
	
	@Autowired
	private RewardPointsRepository rewardPointsRepository ;
	
	@Autowired
	private CustomerRepository customerRepository ;
	
	
	
	
	public int CalculateRewardPoints(double amount) {
		int points = 0;
		if(amount > 100) {
			points += (int)((amount - 100)*2);
			amount = 100;
		}
		if(amount > 50) {
			points +=(int)((amount - 50)*1);
		}
		return points;
	}
	public void calculateAnsSaveRewards(Integer customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));
		List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
		Map<String,Integer> monthlyRewards = new HashMap<>();
		for(Transaction transaction: transactions) {
			String month = transaction.getTransactionDate().getYear() + "-" + transaction.getTransactionDate().getMonth();
			int points = CalculateRewardPoints(transaction.getAmount());
			monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points) ;
		}
		for(Map.Entry<String, Integer> entry : monthlyRewards.entrySet()) {
			RewardPoints rewardPoints = new RewardPoints();
			rewardPoints.setCustomer(customer);
			rewardPoints.setMonth(entry.getKey());
			rewardPoints.setPoints(entry.getValue());
			rewardPointsRepository.save(rewardPoints);
		}
	}
	public List<RewardPoints> getRewardsPointsByCustomer(Integer customerId){
		return rewardPointsRepository.findByCustomerId(customerId);
	}
		
}
