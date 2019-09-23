package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class ImmigrationPassportDTO extends SearchDTO{
	
	private static final long serialVersionUID = 1L;
	 

    /**
     *@author Mamatha Adla
     */
	private Long candidatePassportId;
	
    private String candidateId;
    
    private String passportNumber;

	@Required(desc = "country")
	private String country;
	
	@Required(desc = "validFrom")
	private String validFrom;
	
	@Required(desc = "validTo")
	private String validTo;
	
	private Integer Status;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;
	
	

	public Long getCandidatePassportId() {
		return candidatePassportId;
	}

	public void setCandidatePassportId(Long candidatePassportId) {
		this.candidatePassportId = candidatePassportId;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
