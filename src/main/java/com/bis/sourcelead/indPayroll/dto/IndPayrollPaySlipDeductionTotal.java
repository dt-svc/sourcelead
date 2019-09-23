package com.bis.sourcelead.indPayroll.dto;

import java.math.BigDecimal;

public class IndPayrollPaySlipDeductionTotal {
	
	private BigDecimal  deductionAmount;
	
	private String deductionTotal;

	public BigDecimal getDeductionAmount() {
		return deductionAmount;
	}

	public String getDeductionTotal() {
		return deductionTotal;
	}

	public void setDeductionAmount(BigDecimal deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public void setDeductionTotal(String deductionTotal) {
		this.deductionTotal = deductionTotal;
	}

}
