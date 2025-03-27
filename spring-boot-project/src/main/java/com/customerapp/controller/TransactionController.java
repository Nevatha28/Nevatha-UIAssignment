package com.customerapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerapp.entity.Transaction;
import com.customerapp.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	private TransactionService transactionService ;
	
	@PostMapping("/{customerId}")
	public ResponseEntity<Transaction> addTransaction(@PathVariable Integer customerId ,@RequestBody Transaction transaction){
		
		return ResponseEntity.ok(transactionService.addTransaction(customerId, transaction));
		
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<List<Transaction>> getTransaction(@PathVariable Integer customerId){
		
		return ResponseEntity.ok(transactionService.getTransactionByCustomer(customerId));
		
	}
	
	@PostMapping("/{transactionId}")
	public ResponseEntity<String> deleteTransaction(@PathVariable Integer transactionId){
		transactionService.deleteTransaction(transactionId);
		return ResponseEntity.ok("Transaction deleted successfully.");
		
	}
	

}
