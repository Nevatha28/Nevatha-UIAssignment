package com.customerapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.customerapp.entity.Customer;
import com.customerapp.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUsername("testuser");
        customer.setPassword("password");
        customer.setEmail("testuser@example.com");
    }

    @Test
    public void testRegisterCustomer() {
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer registeredCustomer = customerService.registerCustomer(customer);

        verify(passwordEncoder).encode("password");
        verify(customerRepository).save(customer);
        assertEquals("encodedPassword", registeredCustomer.getPassword());
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        when(customerRepository.findByUsername("testuser")).thenReturn(Optional.of(customer));

        UserDetails userDetails = customerService.loadUserByUsername("testuser");

        verify(customerRepository).findByUsername("testuser");
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(customerRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customerService.loadUserByUsername("unknownuser");
        });

        verify(customerRepository).findByUsername("unknownuser");
    }
}