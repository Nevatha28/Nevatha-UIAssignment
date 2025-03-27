package com.customerapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerapp.dto.AuthenticationRequest;
import com.customerapp.dto.AuthenticationResponse;
import com.customerapp.dto.RegisterRequest;
import com.customerapp.entity.Customer;
import com.customerapp.service.CustomerService;
import com.customerapp.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request){
		Customer customer = new Customer();
		customer.setUsername(customer.getUsername());
		customer.setPassword(request.getPassword());
		customer.setEmail(request.getEmail());
		Customer savedCustomer = customerService.registerCustomer(customer);
		return ResponseEntity.ok(savedCustomer);	
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		String token = jwtUtil.generateToken(request.getUsername());
		return ResponseEntity.ok(new AuthenticationResponse(token));
		
		
	}
	

}
