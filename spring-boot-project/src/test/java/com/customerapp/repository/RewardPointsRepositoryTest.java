package com.customerapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.customerapp.entity.Customer;
import com.customerapp.entity.RewardPoints;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class RewardPointsRepositoryTest {

	@Autowired
	private RewardPointsRepository rewardPointsRepository;

	private RewardPoints rewardPoints;

	@BeforeEach
	public void setUp() {
		rewardPoints = new RewardPoints();
		Customer customer = new Customer();
		customer.setId(1);
		customer.setUsername("testuser");
		customer.setPassword("password");
		customer.setEmail("testuser@example.com");
		rewardPoints = RewardPoints.builder().customer(customer).points(150).build();
		rewardPointsRepository.save(rewardPoints);
	}

	@Test
	public void testFindByCustomerId() {
		List<RewardPoints> foundRewardPoints = rewardPointsRepository.findByCustomerId(1);
		assertTrue(foundRewardPoints.size() > 0);
		assertEquals(1, foundRewardPoints.get(0).getCustomer().getId());
	}

	@Test
	public void testSaveRewardPoints() {
		RewardPoints newRewardPoints = new RewardPoints();
		Customer newCustomer = new Customer();
		newCustomer.setId(2);
		newCustomer.setUsername("newuser");
		newCustomer.setPassword("newpassword");
		newCustomer.setEmail("newuser@example.com");
		newRewardPoints = RewardPoints.builder().customer(newCustomer).points(200).build();

		RewardPoints savedRewardPoints = rewardPointsRepository.save(newRewardPoints);

		assertEquals(2, savedRewardPoints.getCustomer().getId());
	}
}