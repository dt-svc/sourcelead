package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;

public class IndPayrollPaySlipEarning {
	
	private String earnings;

	private BigDecimal  amount;

	private BigDecimal grossSalary;
	
	private String earningsTotal;

	public String getEarnings() {
		return earnings;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}

	public String getEarningsTotal() {
		return earningsTotal;
	}

	public void setEarningsTotal(String earningsTotal) {
		this.earningsTotal = earningsTotal;
	}

}
