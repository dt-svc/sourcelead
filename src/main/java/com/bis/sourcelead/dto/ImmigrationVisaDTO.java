package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class ImmigrationVisaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	 

    /**
     *@author Mamatha Adla
     */
    private Long id;
    private Long candidateId;
    
    private Long candidateVisaId;
    
    private String visaNumber;
	
	private String visaType;

	@Required(desc = "country")
	private String country;
	
	@Required(desc = "validFrom")
	private String validFrom;
	
	@Required(desc = "validTo")
	private String validTo;
	
	private String isCurrent;
	
	private Integer Status;
	
	private String currentStatus;

	private String createdBy;
	
	private String createdDate;

	private String updatedDate;

	private String updatedBy;

	private List<Long> expiredVisaLst;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getCandidateVisaId() {
		return candidateVisaId;
	}

	public void setCandidateVisaId(Long candidateVisaId) {
		this.candidateVisaId = candidateVisaId;
	}

	public String getVisaNumber() {
		return visaNumber;
	}

	public void setVisaNumber(String visaNumber) {
		this.visaNumber = visaNumber;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}


	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public List<Long> getExpiredVisaLst() {
		return expiredVisaLst;
	}

	public void setExpiredVisaLst(List<Long> expiredVisaLst) {
		this.expiredVisaLst = expiredVisaLst;
	}

}
