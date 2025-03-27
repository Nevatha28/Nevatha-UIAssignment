package com.customerapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.customerapp.entity.RewardPoints;

@Repository
public interface RewardPointsRepository extends JpaRepository<RewardPoints, Integer>{

	List<RewardPoints> findByCustomerId(Integer customerId);
}
