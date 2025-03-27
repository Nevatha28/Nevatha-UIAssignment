package com.customerapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.customerapp.entity.Customer;
import com.customerapp.entity.Transaction;
import com.customerapp.repository.CustomerRepository;
import com.customerapp.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Customer customer;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1);
        customer.setUsername("testuser");
        customer.setPassword("password");
        customer.setEmail("testuser@example.com");

        transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setAmount(150.0);
        transaction.setTransactionDate(LocalDate.now());
    }

    @Test
    public void testAddTransaction() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction addedTransaction = transactionService.addTransaction(1, transaction);

        verify(customerRepository).findById(1);
        verify(transactionRepository).save(transaction);
        assertEquals(customer, addedTransaction.getCustomer());
        assertEquals(LocalDate.now(), addedTransaction.getTransactionDate());
    }

    @Test
    public void testGetTransactionByCustomer() {
        when(transactionRepository.findByCustomerId(1)).thenReturn(Arrays.asList(transaction));

        List<Transaction> transactions = transactionService.getTransactionByCustomer(1);

        verify(transactionRepository).findByCustomerId(1);
        assertEquals(1, transactions.size());
        assertEquals(customer, transactions.get(0).getCustomer());
    }

    @Test
    public void testDeleteTransaction() {
        transactionService.deleteTransaction(1);

        verify(transactionRepository).deleteById(1);
    }

    @Test
    public void testAddTransaction_CustomerNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            transactionService.addTransaction(1, transaction);
        });

        verify(customerRepository).findById(1);
    }
}