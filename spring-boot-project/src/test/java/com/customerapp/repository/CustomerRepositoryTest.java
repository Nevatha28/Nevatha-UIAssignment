package com.customerapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.customerapp.entity.Customer;

import jakarta.transaction.Transactional;


@DataJpaTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryTest {

	@Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
    	customerRepository.deleteAll();
        Customer customer = new Customer();
        customer.setUsername("testuser");
        customer.setPassword("password");
        customer.setEmail("testuser@example.com");
        customerRepository.save(customer);
    }

    @Test
    public void testFindByUsername() {
        Optional<Customer> foundCustomer = customerRepository.findByUsername("testuser");
        assertTrue(foundCustomer.isPresent());
        assertEquals("testuser", foundCustomer.get().getUsername());
    }

    @Test
    public void testSaveCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setUsername("newuser");
        newCustomer.setPassword("newpassword");
        newCustomer.setEmail("newuser@example.com");
        Customer savedCustomer = customerRepository.save(newCustomer);
        assertEquals("newuser", savedCustomer.getUsername());
    }
}