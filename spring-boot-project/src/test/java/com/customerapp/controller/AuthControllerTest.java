package com.customerapp.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.customerapp.dto.AuthenticationRequest;
import com.customerapp.dto.AuthenticationResponse;
import com.customerapp.dto.RegisterRequest;
import com.customerapp.entity.Customer;
import com.customerapp.service.CustomerService;
import com.customerapp.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private Customer customer;
    private RegisterRequest registerRequest;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUsername("testuser");
        customer.setPassword("password");
        customer.setEmail("testuser@example.com");

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("testuser");
        registerRequest.setPassword("password");
        registerRequest.setEmail("testuser@example.com");

        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("testuser");
        authenticationRequest.setPassword("password");
    }

    @Test
    public void testRegister() {
        when(customerService.registerCustomer(any(Customer.class))).thenReturn(customer);

        ResponseEntity<?> response = authController.register(registerRequest);

        verify(customerService).registerCustomer(any(Customer.class));
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testLogin() {
        when(jwtUtil.generateToken(eq("testuser"))).thenReturn("dummy-token");

        ResponseEntity<?> response = authController.login(authenticationRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil).generateToken("testuser");
        assertEquals("dummy-token", ((AuthenticationResponse) response.getBody()).getToken());
    }
}