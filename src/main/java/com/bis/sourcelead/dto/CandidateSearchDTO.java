package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class CandidateSearchDTO extends SearchDTO implements Serializable {

	private List<String> candidateSkills;
	private List<String> candidateVisa;
	private String primarySkill;
	private String minRate;
	private String maxRate;
	private String currency;
	private String rateType;
	private List<String> designation;
	private String minExpYears;
	private String maxExpYears;
	private List<String> noticePeriod;
	private String designationType;
	private List<String> education;
	private List<String> excludeEducation;
	private List<String> certification;
	private List<String> excludeEmployers;
	private List<String> employers;
	private String applicantType;
	private int candidateStatus;
	private String isAssociate;
	private String candidateType;

	public List<String> getCandidateSkills() {
		return candidateSkills;
	}

	public void setCandidateSkills(List<String> candidateSkills) {
		this.candidateSkills = candidateSkills;
	}

	
	public List<String> getCandidateVisa()
	{
		return candidateVisa;
	}

	public void setCandidateVisa(List<String> candidateVisa)
	{
		this.candidateVisa = candidateVisa;
	}

	public String getMinRate() {
		return minRate;
	}

	public void setMinRate(String minRate) {
		this.minRate = minRate;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public List<String> getDesignation() {
		return designation;
	}

	public void setDesignation(List<String> designation) {
		this.designation = designation;
	}

	public String getMinExpYears() {
		return minExpYears;
	}

	public void setMinExpYears(String minExpYears) {
		this.minExpYears = minExpYears;
	}

	public String getMaxExpYears() {
		return maxExpYears;
	}

	public void setMaxExpYears(String maxExpYears) {
		this.maxExpYears = maxExpYears;
	}

	public List<String> getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(List<String> noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public String getDesignationType() {
		return designationType;
	}

	public void setDesignationType(String designationType) {
		this.designationType = designationType;
	}

	public List<String> getEducation() {
		return education;
	}

	public void setEducation(List<String> education) {
		this.education = education;
	}

	public List<String> getExcludeEducation() {
		return excludeEducation;
	}

	public void setExcludeEducation(List<String> excludeEducation) {
		this.excludeEducation = excludeEducation;
	}

	public List<String> getCertification() {
		return certification;
	}

	public void setCertification(List<String> certification) {
		this.certification = certification;
	}

	public List<String> getExcludeEmployers() {
		return excludeEmployers;
	}

	public void setExcludeEmployers(List<String> excludeEmployers) {
		this.excludeEmployers = excludeEmployers;
	}

	public List<String> getEmployers() {
		return employers;
	}

	public void setEmployers(List<String> employers) {
		this.employers = employers;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public int getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(int candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public String getIsAssociate() {
		return isAssociate;
	}

	public void setIsAssociate(String isAssociate) {
		this.isAssociate = isAssociate;
	}

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}

}
