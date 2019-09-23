package com.bis.sourcelead.dto;

import java.math.BigDecimal;

public class AssociateSalaryPayheadIndDTO extends CommonDTO {

    private Long id;
	
    private Long associateSalaryIndId;
	
    private Long payrollPayheadId;
    
    private String payrollPayheadShortCode;
	
	private BigDecimal monthly;
	
	private BigDecimal annualy;
	
	private String uuid;
	
	private Integer status;
    
  	private Long createdBy;
    
  	private String createdDate;
    
  	private Long updatedBy;
    
  	private String updatedDate;
  	
  	private Boolean checked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssociateSalaryIndId() {
		return associateSalaryIndId;
	}

	public void setAssociateSalaryIndId(Long associateSalaryIndId) {
		this.associateSalaryIndId = associateSalaryIndId;
	}

	public Long getPayrollPayheadId() {
		return payrollPayheadId;
	}

	public void setPayrollPayheadId(Long payrollPayheadId) {
		this.payrollPayheadId = payrollPayheadId;
	}

	public String getPayrollPayheadShortCode() {
		return payrollPayheadShortCode;
	}

	public void setPayrollPayheadShortCode(String payrollPayheadShortCode) {
		this.payrollPayheadShortCode = payrollPayheadShortCode;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
