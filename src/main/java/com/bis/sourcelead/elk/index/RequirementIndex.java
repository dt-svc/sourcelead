package com.bis.sourcelead.elk.index;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.itextpdf.text.ListItem;

/**
 * 
 * @author Rajashekar and Prasanna
 * @date 11th jan 2017
 */
public class RequirementIndex {

	private Long requirementId;
	
	private Long orgLocationId;
	
	private String jobTitle;
	
	private String currentRateCurrency;
	
	private String numberOfPositions;
	
	private String numberOfApplicants;
	
	private Float rate;
	
	private String rateType;
	
	private Integer minExperience;
	
	private Integer maxExperience;
	
	private String requirementStatus;
	
	private String country;
	
	private String state;
	
	private String city;
	
	private String[] education;
	
	private String client;
	
	private String[] skills;
	
	private String jobType;
	
	private String jobId;
	
	private String clientContactName;
	
	private String clientContactEmail;
	
	private String manager;
	
	private String priority; 

	

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}


	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}
	
	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public Integer getMinExperience() {
		return minExperience;
	}

	public void setMinExperience(Integer minExperience) {
		this.minExperience = minExperience;
	}

	public Integer getMaxExperience() {
		return maxExperience;
	}

	public void setMaxExperience(Integer maxExperience) {
		this.maxExperience = maxExperience;
	}

	public String getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}




	public String[] getEducation() {
		return education;
	}

	public void setEducation(String[] education) {
		this.education = education;
	}

	public String[] getSkills() {
		return skills;
	}

	public void setSkills(String[] skills) {
		this.skills = skills;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getClientContactName() {
		return clientContactName;
	}

	public void setClientContactName(String clientContactName) {
		this.clientContactName = clientContactName;
	}

	public String getClientContactEmail() {
		return clientContactEmail;
	}

	public void setClientContactEmail(String clientContactEmail) {
		this.clientContactEmail = clientContactEmail;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCurrentRateCurrency() {
		return currentRateCurrency;
	}

	public void setCurrentRateCurrency(String currentRateCurrency) {
		this.currentRateCurrency = currentRateCurrency;
	}

	public String getNumberOfPositions() {
		return numberOfPositions;
	}

	public void setNumberOfPositions(String numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}

	public String getNumberOfApplicants() {
		return numberOfApplicants;
	}

	public void setNumberOfApplicants(String numberOfApplicants) {
		this.numberOfApplicants = numberOfApplicants;
	}

}
