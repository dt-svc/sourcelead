package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;

public class IndPayrollPaySlipEarningAndDeducationTotal {
	
	private String earnings;

	private BigDecimal  amount;

	private BigDecimal grossSalary;
	
    private BigDecimal  deductionAmount;
	
	private String deduction;

	public String getEarnings() {
		return earnings;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public BigDecimal getGrossSalary() {
		return grossSalary;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
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

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getDeduction() {
		return deduction;
	}

	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}

}
