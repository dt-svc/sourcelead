package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.dto.SearchDTO;

public class GenerateINDPayrollDTO extends SearchDTO {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	private String groupId;

	private String payPeriod;

	private String payrollPayDate;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}

	public String getPayrollPayDate() {
		return payrollPayDate;
	}

	public void setPayrollPayDate(String payrollPayDate) {
		this.payrollPayDate = payrollPayDate;
	}

}
