package com.bis.sourcelead.payroll.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bis.sourcelead.dto.SearchDTO;

public class AssociatePayrollPayheadHistoryDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
    private Long indEmployeePayrollId;
    private Long payrollPayhead;
    private String payrollPayheadName;
    private String payrollPayheadShortCode;
    private String payrollPayheadType;
	private BigDecimal monthly;
	private BigDecimal annualy;
	private Integer status;
	private String uuid;
  	private Long createdBy;
  	private String createdDate;
  	private Long updatedBy;
  	private String updatedDate;
  	private String indEmpPayrollUUID;
  	private String paySheetFrequencyUUID;
    private Integer indEmployeePayrollStatus;
    
    private BigDecimal monthlyOld;
	private BigDecimal annualyOld;
	private String paySlip;
	
	private String taxStatus;
  	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIndEmployeePayrollId() {
		return indEmployeePayrollId;
	}
	public void setIndEmployeePayrollId(Long indEmployeePayrollId) {
		this.indEmployeePayrollId = indEmployeePayrollId;
	}
	public Long getPayrollPayhead() {
		return payrollPayhead;
	}
	public void setPayrollPayhead(Long payrollPayhead) {
		this.payrollPayhead = payrollPayhead;
	}
	public String getPayrollPayheadName() {
		return payrollPayheadName;
	}
	public void setPayrollPayheadName(String payrollPayheadName) {
		this.payrollPayheadName = payrollPayheadName;
	}
	public String getPayrollPayheadShortCode() {
		return payrollPayheadShortCode;
	}
	public void setPayrollPayheadShortCode(String payrollPayheadShortCode) {
		this.payrollPayheadShortCode = payrollPayheadShortCode;
	}
	public String getPayrollPayheadType() {
		return payrollPayheadType;
	}
	public void setPayrollPayheadType(String payrollPayheadType) {
		this.payrollPayheadType = payrollPayheadType;
	}
	public BigDecimal getMonthly() {
		return monthly;
	}
	public void setMonthly(BigDecimal monthly) {
		this.monthly = monthly;
	}
	public BigDecimal getAnnualy() {
		return annualy;
	}
	public void setAnnualy(BigDecimal annualy) {
		this.annualy = annualy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getIndEmpPayrollUUID() {
		return indEmpPayrollUUID;
	}
	public void setIndEmpPayrollUUID(String indEmpPayrollUUID) {
		this.indEmpPayrollUUID = indEmpPayrollUUID;
	}
	public String getPaySheetFrequencyUUID() {
		return paySheetFrequencyUUID;
	}
	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID) {
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}
	public Integer getIndEmployeePayrollStatus() {
		return indEmployeePayrollStatus;
	}
	public void setIndEmployeePayrollStatus(Integer indEmployeePayrollStatus) {
		this.indEmployeePayrollStatus = indEmployeePayrollStatus;
	}
	public BigDecimal getMonthlyOld() {
		return monthlyOld;
	}
	public void setMonthlyOld(BigDecimal monthlyOld) {
		this.monthlyOld = monthlyOld;
	}
	public BigDecimal getAnnualyOld() {
		return annualyOld;
	}
	public void setAnnualyOld(BigDecimal annualyOld) {
		this.annualyOld = annualyOld;
	}
	public String getPaySlip() {
		return paySlip;
	}
	public void setPaySlip(String paySlip) {
		this.paySlip = paySlip;
	}
	public String getTaxStatus() {
		return taxStatus;
	}
	public void setTaxStatus(String taxStatus) {
		this.taxStatus = taxStatus;
	}
}
