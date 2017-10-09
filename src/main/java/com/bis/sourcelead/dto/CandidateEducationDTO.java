package com.bis.sourcelead.dto;

import java.util.Date;

import com.bis.sourcelead.annotations.Required;

public class CandidateEducationDTO extends CommonDTO {

	
	@Required(desc = "Education name")
	private String name;
	
	private String specialization;
	
	private String candidateEduId;
	 
	private String startDate;
	
	private String endDate;

	private String institute;
	
	private String address;
	
	private String createdDate;
	
	private String createdBy;
	
	private String updatedDate;
	
	private String updatedBy;
	
	private String scoreType;
	
	private String score;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

	public String getCandidateEduId() {
		return candidateEduId;
	}

	public void setCandidateEduId(String candidateEduId) {
		this.candidateEduId = candidateEduId;
	}

	

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}



}
