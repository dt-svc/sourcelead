package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;

public class IndPayrollPaySlipEarningTotal {
	
	private String total;

	private BigDecimal  amount;

	private BigDecimal grossSalary;

	public String getTotal() {
		return total;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}
	
}
