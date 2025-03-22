package com.customerapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerapp.service.RewardServices;

@RestController
@RequestMapping("/rewards")
public class RewardController {

	@Autowired
	private RewardServices rewardService;
	
	@GetMapping("/monthly")
	public ResponseEntity<List<Map<String,Object>>> getMonthlyRewards(){
		return ResponseEntity.ok(rewardService.getMonthlyRewards());
	}
		
	@GetMapping("/total")
	public ResponseEntity<List<Map<String,Object>>> getTotalRewards(){
		return ResponseEntity.ok(rewardService.getTotalRewards());
		
	}
	
}
