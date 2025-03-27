package com.customerapp.entity;

import io.micrometer.common.lang.NonNullApi;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rewards_points")
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class RewardPoints {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	private String month;

	private Integer points;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Integer id;
		private Customer customer;
		private String month;
		private Integer points;

		public Builder id(Integer id) {
			this.id = id;
			return this;
		}

		public Builder customer(Customer customer) {
			this.customer = customer;
			return this;
		}

		public Builder month(String month) {
			this.month = month;
			return this;
		}

		public Builder points(Integer points) {
			this.points = points;
			return this;
		}

		public RewardPoints build() {
			RewardPoints rewardPoints = new RewardPoints();
			rewardPoints.id = this.id;
			rewardPoints.customer = this.customer;
			rewardPoints.month = this.month;
			rewardPoints.points = this.points;
			return rewardPoints;
		}
	}
}
