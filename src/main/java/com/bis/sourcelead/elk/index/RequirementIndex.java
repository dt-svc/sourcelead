package com.bis.sourcelead.elk.index;

import java.util.List;

/**
 * 
 * @author root
 * @date 11th jan 2017
 */
public class RequirementIndex {

	private Long RequirementId;
	private Long OrgLocationId;
	private String RequirementTitle;
	private String rate;
	private int minExperience;
	private int maxExperience;
	private List<String> requirementStatus;
	private String country;
	private String state;
	private String city;
	private List<String> education;
	private List<String> client;
	private List<String> skills;
	private String employeeType;
	private String jobId;
	private String clientContactName;
	private String manager;
	private String priority; 

	public Long getRequirementId() {
		return RequirementId;
	}

	public void setRequirementId(Long requirementId) {
		RequirementId = requirementId;
	}

	public Long getOrgLocationId() {
		return OrgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		OrgLocationId = orgLocationId;
	}

	public String getRequirementTitle() {
		return RequirementTitle;
	}

	public void setRequirementTitle(String requirementTitle) {
		RequirementTitle = requirementTitle;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public int getMinExperience() {
		return minExperience;
	}

	public void setMinExperience(int minExperience) {
		this.minExperience = minExperience;
	}

	public int getMaxExperience() {
		return maxExperience;
	}

	public void setMaxExperience(int maxExperience) {
		this.maxExperience = maxExperience;
	}

	public List<String> getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(List<String> requirementStatus) {
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

	public List<String> getEducation() {
		return education;
	}

	public void setEducation(List<String> education) {
		this.education = education;
	}

	public List<String> getClient() {
		return client;
	}

	public void setClient(List<String> client) {
		this.client = client;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
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

}
