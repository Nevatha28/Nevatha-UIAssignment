package com.customerapp.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.customerapp.entity.Transaction;
import com.customerapp.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;
    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;
    private List<Transaction> transactionList;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setId(1);
        transaction.setAmount(100.0);

        transactionList = Arrays.asList(transaction);
    }

    @Test
    public void testAddTransaction() {
        when(transactionService.addTransaction(eq(1), any(Transaction.class))).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.addTransaction(1, transaction);

        verify(transactionService).addTransaction(1, transaction);
        assertEquals(transaction, response.getBody());
    }

    @Test
    public void testGetTransaction() {
        when(transactionService.getTransactionByCustomer(1)).thenReturn(transactionList);

        ResponseEntity<List<Transaction>> response = transactionController.getTransaction(1);

        verify(transactionService).getTransactionByCustomer(1);
        assertEquals(transactionList, response.getBody());
    }

    @Test
    public void testDeleteTransaction() {
        ResponseEntity<String> response = transactionController.deleteTransaction(1);

        verify(transactionService).deleteTransaction(1);
        assertEquals("Transaction deleted successfully.", response.getBody());
    }
}