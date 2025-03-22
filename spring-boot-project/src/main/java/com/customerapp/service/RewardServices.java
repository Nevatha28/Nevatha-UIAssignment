package com.customerapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customerapp.repository.TransactionRepository;

@Service
public class RewardServices {

	@Autowired
	private TransactionRepository transactionRepository ;
	
	public List<Map<String, Object>> getMonthlyRewards(){
		
		List<Object[]> results = transactionRepository.getRewardPointsPerMonth();
		List<Map<String, Object>> response = new ArrayList<>();
		for(Object[] row : results) {
			Map<String, Object> data = new HashMap<>();
			data.put("customerId", row[0]);
			data.put("month", row[1]);
			data.put("rewardPoints", row[2]);
			response.add(data);
		}
		return response;
	}
	
	public List<Map<String, Object>> getTotalRewards(){
		
		List<Object[]> results = transactionRepository.getTotalRewardPoints();
		List<Map<String, Object>> response = new ArrayList<>();
		for(Object[] row : results) {
			Map<String, Object> data = new HashMap<>();
			data.put("customerId", row[0]);
			data.put("totalRewardPoints", row[2]);
			response.add(data);
		}
		return response;
	
	}
	
	
	
}
