package com.customerapp.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.customerapp.entity.RewardPoints;
import com.customerapp.service.RewardServices;

@ExtendWith(MockitoExtension.class)
public class RewardControllerTest {

    @Mock
    private RewardServices rewardService;

    @InjectMocks
    private RewardController rewardController;

    private RewardPoints rewardPoints;
    private List<RewardPoints> rewardPointsList;

    @BeforeEach
    public void setUp() {
        rewardPoints = new RewardPoints();
        rewardPoints.setId(1);
        rewardPoints.setPoints(100);

        rewardPointsList = Arrays.asList(rewardPoints);
    }

    @Test
    public void testCalculateRewards() {
        ResponseEntity<String> response = rewardController.calculateRewards(1);

        verify(rewardService).calculateAnsSaveRewards(1);
        assertEquals("Reward points calculated and saved", response.getBody());
    }

    @Test
    public void testGetRewards() {
        when(rewardService.getRewardsPointsByCustomer(1)).thenReturn(rewardPointsList);

        ResponseEntity<List<RewardPoints>> response = rewardController.getRewards(1);

        verify(rewardService).getRewardsPointsByCustomer(1);
        assertEquals(rewardPointsList, response.getBody());
    }
}