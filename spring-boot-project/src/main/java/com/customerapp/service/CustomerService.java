package com.customerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.customerapp.entity.Customer;
import com.customerapp.repository.CustomerRepository;

@Service
public class CustomerService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer registerCustomer(Customer customer) {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerRepository.save(customer);
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Customer customer = customerRepository.findByUsername(username).
				orElseThrow(()->  new UsernameNotFoundException("User not found"));
		return User.withUsername(customer.getUsername()).password(customer.getPassword()).build();		
	}
		
	
	
	
	
	
	

}
