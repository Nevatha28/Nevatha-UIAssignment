package com.customerapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customerapp.entity.Customer;
import com.customerapp.entity.Transaction;
import com.customerapp.repository.CustomerRepository;
import com.customerapp.repository.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository ;
	
	@Autowired
	private CustomerRepository customerRepository ;
	
	public Transaction addTransaction(Integer customerId , Transaction transaction){
		
		Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new RuntimeException("Customer not found"));
		transaction.setCustomer(customer);
		transaction.setTransactionDate(LocalDate.now());
		return transactionRepository.save(transaction);
	}
	
	public List<Transaction> getTransactionByCustomer(Integer customerId){
		
		return transactionRepository.findByCustomerId(customerId);
		
	}
	
	public void deleteTransaction(Integer transactionId) {
		transactionRepository.deleteById(transactionId);
	}

}
