package com.bis.sourcelead.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class AssociateSalaryIndDTO extends CommonDTO {

	private Long id;
	
	@Required(desc = "Pay Type")
	private String payType;
	
	@Required(desc = "Pay Frequency")
	private String payFrequency;
	
	@Required(desc = "Pay Group")
	private String payGroup;
	
	private String standardHours;
	
	private String annualSal;
	
	private String perPaySal;
	
	private String hourlyRate;
	
	private Long userId;
	
	private String previousPayType;
	
	private String previousPayFrequency;
	
	private String previousStandardHours;
	
	private String previousAnnualSal;
	
	private String previousPerPaySal;
	
	private String previousHourlyRate;
	
	private String salaryType;
	
	private String effectiveDate;
	
	private String increasePer;
	
	private String changeReason;
	
	private Long candidateId;
	
	private String stopSalary;
	
	private Integer status;
	
	private String uuid;
    
	private Long createdBy;
    
  	private String createdDate;
    
  	private Long updatedBy;
    
  	private String updatedDate;
    
    private List<AssociateSalaryPayheadIndDTO> associateSalaryPayheadIndList;
    
    private Integer recordUpdatedCount;
    
    private String associateEmail;
    
    private String errorMessage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayFrequency() {
		return payFrequency;
	}

	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}

	public String getPayGroup() {
		return payGroup;
	}

	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}

	public String getStandardHours() {
		return standardHours;
	}

	public void setStandardHours(String standardHours) {
		this.standardHours = standardHours;
	}

	public String getAnnualSal() {
		return annualSal;
	}

	public void setAnnualSal(String annualSal) {
		this.annualSal = annualSal;
	}

	public String getPerPaySal() {
		return perPaySal;
	}

	public void setPerPaySal(String perPaySal) {
		this.perPaySal = perPaySal;
	}

	public String getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(String hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPreviousPayType() {
		return previousPayType;
	}

	public void setPreviousPayType(String previousPayType) {
		this.previousPayType = previousPayType;
	}

	public String getPreviousPayFrequency() {
		return previousPayFrequency;
	}

	public void setPreviousPayFrequency(String previousPayFrequency) {
		this.previousPayFrequency = previousPayFrequency;
	}

	public String getPreviousStandardHours() {
		return previousStandardHours;
	}

	public void setPreviousStandardHours(String previousStandardHours) {
		this.previousStandardHours = previousStandardHours;
	}

	public String getPreviousAnnualSal() {
		return previousAnnualSal;
	}

	public void setPreviousAnnualSal(String previousAnnualSal) {
		this.previousAnnualSal = previousAnnualSal;
	}

	public String getPreviousPerPaySal() {
		return previousPerPaySal;
	}

	public void setPreviousPerPaySal(String previousPerPaySal) {
		this.previousPerPaySal = previousPerPaySal;
	}

	public String getPreviousHourlyRate() {
		return previousHourlyRate;
	}

	public void setPreviousHourlyRate(String previousHourlyRate) {
		this.previousHourlyRate = previousHourlyRate;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getIncreasePer() {
		return increasePer;
	}

	public void setIncreasePer(String increasePer) {
		this.increasePer = increasePer;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	
	public String getStopSalary() {
		return stopSalary;
	}

	public void setStopSalary(String stopSalary) {
		this.stopSalary = stopSalary;
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

	public List<AssociateSalaryPayheadIndDTO> getAssociateSalaryPayheadIndList() {
		return associateSalaryPayheadIndList;
	}

	public void setAssociateSalaryPayheadIndList(List<AssociateSalaryPayheadIndDTO> associateSalaryPayheadIndList) {
		this.associateSalaryPayheadIndList = associateSalaryPayheadIndList;
	}

	public Integer getRecordUpdatedCount() {
		return recordUpdatedCount;
	}

	public void setRecordUpdatedCount(Integer recordUpdatedCount) {
		this.recordUpdatedCount = recordUpdatedCount;
	}

	public String getAssociateEmail() {
		return associateEmail;
	}

	public void setAssociateEmail(String associateEmail) {
		this.associateEmail = associateEmail;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
