package com.customerapp.service;

import java.time.LocalDate;
import java.time.YearMonth;
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



@Service
public class RewardServices {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RewardPointsRepository rewardPointsRepository;

	@Autowired
	private CustomerRepository customerRepository;

	public int CalculateRewardPoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (int) ((amount - 100) * 2);
			amount = 100;
		}
		if (amount > 50) {
			points += (int) ((amount - 50) * 1);
		}
		return points;
	}

	public void calculateAnsSaveRewards(Integer customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));
		List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
		Map<String, Integer> monthlyRewards = new HashMap<>();
		for (Transaction transaction : transactions) {
			String month = transaction.getTransactionDate().getYear() + "-"
					+ transaction.getTransactionDate().getMonth();
			int points = CalculateRewardPoints(transaction.getAmount());
			monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
		}
		for (Map.Entry<String, Integer> entry : monthlyRewards.entrySet()) {
			RewardPoints rewardPoints = new RewardPoints.Builder()
				    .customer(customer)
				    .month(entry.getKey())
				    .points(entry.getValue())
				    .build();
			rewardPointsRepository.save(rewardPoints);
		}
	}

	public List<RewardPoints> getRewardsPointsByCustomer(Integer customerId) {
		return rewardPointsRepository.findByCustomerId(customerId);
	}

	public Map<String, Integer> getLastThreeMonthsRewards(Integer customerId) {
		List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
		LocalDate currentDate = LocalDate.now();
		Map<String, Integer> monthlyRewards = new HashMap<>();

		// Iterate over last 3 months
		for (int i = 0; i < 3; i++) {
			YearMonth yearMonth = YearMonth.from(currentDate.minusMonths(i));
			String monthKey = yearMonth.getYear() + "-" + yearMonth.getMonthValue();

			// Filter transactions for this specific month
			int monthlyPoints = transactions.stream().filter(t -> {
				YearMonth txnMonth = YearMonth.from(t.getTransactionDate());
				return txnMonth.equals(yearMonth);
			}).mapToInt(t -> CalculateRewardPoints(t.getAmount())).sum();
			monthlyRewards.put(monthKey, monthlyPoints);
		}
		int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();
		monthlyRewards.put("Total", totalRewards);

		return monthlyRewards;
	}

}
