package com.bis.sourcelead.dto;

import java.math.BigInteger;

public class CandidateResumesDTO {

	 private BigInteger resumeId;
	 
	 private BigInteger orgLocationId;
	 
	 private BigInteger candidateId;
	 
	 private String title ;
	 
	 private String createdDate;
	 
	 private String updatedDate;
	 
	 

	public BigInteger getResumeId() {
		return resumeId;
	}

	public void setResumeId(BigInteger resumeId) {
		this.resumeId = resumeId;
	}

	public BigInteger getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(BigInteger orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public BigInteger getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(BigInteger candidateId) {
		this.candidateId = candidateId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
}
