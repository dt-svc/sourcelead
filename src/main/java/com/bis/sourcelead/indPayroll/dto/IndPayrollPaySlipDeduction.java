package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;

public class IndPayrollPaySlipDeduction {
	
	private String deduction;

	private BigDecimal  deductionAmount;
	
	private String deductionTotal;

	public String getDeduction() {
		return deduction;
	}

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeduction(String deduction) {
		this.deduction = deduction;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public String getDeductionTotal() {
		return deductionTotal;
	}

	public void setDeductionTotal(String deductionTotal) {
		this.deductionTotal = deductionTotal;
	}

}
