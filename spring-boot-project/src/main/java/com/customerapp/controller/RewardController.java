package com.customerapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerapp.entity.RewardPoints;
import com.customerapp.service.RewardServices;

@RestController
@RequestMapping("/rewards")
public class RewardController {

	@Autowired
	private RewardServices rewardService;
	
	@PostMapping("/calculate/{customerId}")
	public ResponseEntity<String> calculateRewards(@PathVariable Integer customerId){
		rewardService.calculateAnsSaveRewards(customerId);
		return ResponseEntity.ok("Reward points calculated and saved");
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<List<RewardPoints>> getRewards(@PathVariable Integer customerId){
		return ResponseEntity.ok(rewardService.getRewardsPointsByCustomer(customerId));
	}
	
}
