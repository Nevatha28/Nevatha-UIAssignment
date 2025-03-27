package com.customerapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.customerapp.entity.Customer;
import com.customerapp.entity.Transaction;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private TestEntityManager testEntityManager;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setUsername("testuser");
        customer.setPassword("password");
        customer.setEmail("testuser@example.com");
        transaction.setCustomer(customer);
        transaction.setAmount(100.0);
        transactionRepository.save(transaction);
    }

    @Test
    public void testFindByCustomerId() {
        List<Transaction> foundTransactions = transactionRepository.findByCustomerId(1);
        assertTrue(foundTransactions.size() > 0);
        assertEquals(1, foundTransactions.get(0).getCustomer().getId());
    }

    @Test
    public void testSaveTransaction() {
        Transaction newTransaction = new Transaction();
        Customer newCustomer = new Customer();
        newCustomer.setId(2);
        newCustomer.setUsername("newuser");
        newCustomer.setPassword("newpassword");
        newCustomer.setEmail("newuser@example.com");
        newTransaction.setCustomer(newCustomer);
        newTransaction.setAmount(200.0);
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        assertEquals(2, savedTransaction.getCustomer().getId());
    }
}