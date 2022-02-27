package com.api.villagedevin.model.transport;

import java.io.Serializable;

public class VillageReportDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double budget;
	private Double totalRevenue;
	private Double differenceRevenueAndExpense;
	private Double mostExpenseCitizen;

	public VillageReportDTO() {
	}

	public VillageReportDTO(Double budget, Double totalRevenue, Double differenceRevenueAndExpense,
			Double mostExpenseCitizen) {
		this.budget = budget;
		this.totalRevenue = totalRevenue;
		this.differenceRevenueAndExpense = differenceRevenueAndExpense;
		this.mostExpenseCitizen = mostExpenseCitizen;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public Double getDifferenceRevenueAndExpense() {
		return differenceRevenueAndExpense;
	}

	public void setDifferenceRevenueAndExpense(Double differenceRevenueAndExpense) {
		this.differenceRevenueAndExpense = differenceRevenueAndExpense;
	}

	public Double getMostExpenseCitizen() {
		return mostExpenseCitizen;
	}

	public void setMostExpenseCitizen(Double mostExpenseCitizen) {
		this.mostExpenseCitizen = mostExpenseCitizen;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	@Override
	public String toString() {
		return "VillageReportDTO [totalRevenue=" + totalRevenue + ", totalExpense=" + differenceRevenueAndExpense
				+ ", mostExpenseCitizen=" + mostExpenseCitizen + ", budget=" + budget + "]";
	}

}
