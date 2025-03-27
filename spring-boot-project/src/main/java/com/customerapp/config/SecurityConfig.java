package com.customerapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.customerapp.service.CustomerService;
import com.customerapp.util.JwtRequestFilter;
import com.customerapp.util.JwtUtil;

import jakarta.servlet.Filter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	/*public SecurityConfig(JwtUtil jwtUtil, CustomerService customerService) {
		this.jwtUtil = jwtUtil;
		this.customerService = customerService;	
	}*/
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/auth/register","/auth/login").permitAll()
		.anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(new JwtRequestFilter(jwtUtil,customerService), (Class<? extends Filter>) UsernamePasswordAuthenticationToken.class);
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authentication(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
