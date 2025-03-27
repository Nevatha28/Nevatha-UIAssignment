package com.customerapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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
import com.customerapp.entity.RewardPoints;
import com.customerapp.entity.Transaction;
import com.customerapp.repository.CustomerRepository;
import com.customerapp.repository.RewardPointsRepository;
import com.customerapp.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class RewardServicesTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RewardPointsRepository rewardPointsRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private RewardServices rewardServices;

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
        transaction.setTransactionDate(LocalDate.of(2023, 3, 25));
    }

    @Test
    public void testCalculateRewardPoints() {
        int points = rewardServices.CalculateRewardPoints(150.0);
        assertEquals(150, points);
    }

    @Test
    public void testCalculateAnsSaveRewards() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(transactionRepository.findByCustomerId(1)).thenReturn(Arrays.asList(transaction));

        rewardServices.calculateAnsSaveRewards(1);

        verify(customerRepository).findById(1);
        verify(transactionRepository).findByCustomerId(1);
        verify(rewardPointsRepository).save(any(RewardPoints.class));
    }

    @Test
    public void testGetRewardsPointsByCustomer() {
        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setCustomer(customer);
        rewardPoints.setMonth("2023-3");
        rewardPoints.setPoints(150);

        when(rewardPointsRepository.findByCustomerId(1)).thenReturn(Arrays.asList(rewardPoints));

        List<RewardPoints> rewardPointsList = rewardServices.getRewardsPointsByCustomer(1);

        verify(rewardPointsRepository).findByCustomerId(1);
        assertEquals(1, rewardPointsList.size());
        assertEquals("2023-3", rewardPointsList.get(0).getMonth());
        assertEquals(150, rewardPointsList.get(0).getPoints());
    }

    @Test
    public void testCalculateAnsSaveRewards_CustomerNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            rewardServices.calculateAnsSaveRewards(1);
        });

        verify(customerRepository).findById(1);
    }
}