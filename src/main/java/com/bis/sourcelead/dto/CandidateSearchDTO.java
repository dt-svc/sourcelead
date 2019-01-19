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
	private String generatedId;
	private List<String> createdBy;
	private String employmnetTypes;
	private String candidateFilterSkills;
    private String availability;
    private String candidateTypeFilter;
    private String createdByCandidates;
    private String createdByFilter;
    private List<String> candidateTags;
    private String isSharedInMarketplace;
    
    
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

	public String getGeneratedId()
	{
		return generatedId;
	}

	public void setGeneratedId(String generatedId)
	{
		this.generatedId = generatedId;
	}

	public List<String> getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(List<String> createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmploymnetTypes() {
		return employmnetTypes;
	}

	public void setEmploymnetTypes(String employmnetTypes) {
		this.employmnetTypes = employmnetTypes;
	}

	public String getCandidateFilterSkills() {
		return candidateFilterSkills;
	}

	public void setCandidateFilterSkills(String candidateFilterSkills) {
		this.candidateFilterSkills = candidateFilterSkills;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getCandidateTypeFilter() {
		return candidateTypeFilter;
	}

	public void setCandidateTypeFilter(String candidateTypeFilter) {
		this.candidateTypeFilter = candidateTypeFilter;
	}

	public String getCreatedByCandidates() {
		return createdByCandidates;
	}

	public void setCreatedByCandidates(String createdByCandidates) {
		this.createdByCandidates = createdByCandidates;
	}

	public String getCreatedByFilter() {
		return createdByFilter;
	}

	public void setCreatedByFilter(String createdByFilter) {
		this.createdByFilter = createdByFilter;
	}

	public List<String> getCandidateTags() {
		return candidateTags;
	}

	public void setCandidateTags(List<String> candidateTags) {
		this.candidateTags = candidateTags;
	}

	public String getIsSharedInMarketplace() {
		return isSharedInMarketplace;
	}

	public void setIsSharedInMarketplace(String isSharedInMarketplace) {
		this.isSharedInMarketplace = isSharedInMarketplace;
	}

	@Override
	public String toString() {
		return "CandidateSearchDTO [candidateSkills=" + candidateSkills + ", candidateVisa=" + candidateVisa
				+ ", primarySkill=" + primarySkill + ", minRate=" + minRate + ", maxRate=" + maxRate + ", currency="
				+ currency + ", rateType=" + rateType + ", designation=" + designation + ", minExpYears=" + minExpYears
				+ ", maxExpYears=" + maxExpYears + ", noticePeriod=" + noticePeriod + ", designationType="
				+ designationType + ", education=" + education + ", excludeEducation=" + excludeEducation
				+ ", certification=" + certification + ", excludeEmployers=" + excludeEmployers + ", employers="
				+ employers + ", applicantType=" + applicantType + ", candidateStatus=" + candidateStatus
				+ ", isAssociate=" + isAssociate + ", candidateType=" + candidateType + ", generatedId=" + generatedId
				+ ", createdBy=" + createdBy + ", employmnetTypes=" + employmnetTypes + ", candidateFilterSkills="
				+ candidateFilterSkills + ", availability=" + availability + ", candidateTypeFilter="
				+ candidateTypeFilter + ", createdByCandidates=" + createdByCandidates + ", createdByFilter="
				+ createdByFilter + ", candidateTags=" + candidateTags + ", isSharedInMarketplace="
				+ isSharedInMarketplace + "]";
	}
	
}
