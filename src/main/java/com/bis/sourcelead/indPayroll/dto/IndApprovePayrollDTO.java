package com.bis.sourcelead.indPayroll.dto;

public class IndApprovePayrollDTO {
	
	private Long[] empPayrollId;
	
	private String[] empPayrollUUID;
	
	private String[] excludeEmpPayrollUUID;
	
	private Long paySheetFrequencyId;
	
	private String paySheetFrequencyUUID;
	
	private String approverStatus;
	


	public Long[] getEmpPayrollId() {
		return empPayrollId;
	}

	public void setEmpPayrollId(Long[] empPayrollId) {
		this.empPayrollId = empPayrollId;
	}

	public String[] getEmpPayrollUUID() {
		return empPayrollUUID;
	}

	public void setEmpPayrollUUID(String[] empPayrollUUID) {
		this.empPayrollUUID = empPayrollUUID;
	}

	public String[] getExcludeEmpPayrollUUID() {
		return excludeEmpPayrollUUID;
	}

	public void setExcludeEmpPayrollUUID(String[] excludeEmpPayrollUUID) {
		this.excludeEmpPayrollUUID = excludeEmpPayrollUUID;
	}

	public Long getPaySheetFrequencyId() {
		return paySheetFrequencyId;
	}

	public void setPaySheetFrequencyId(Long paySheetFrequencyId) {
		this.paySheetFrequencyId = paySheetFrequencyId;
	}

	public String getPaySheetFrequencyUUID() {
		return paySheetFrequencyUUID;
	}

	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID) {
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}
	
}
