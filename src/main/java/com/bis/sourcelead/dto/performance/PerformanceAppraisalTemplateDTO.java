package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.annotations.Required;

public class PerformanceAppraisalTemplateDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Required(desc="name")
	private String name;
	
	@Required(desc="appraisalStartDate")
	private String appraisalStartDate;
	
	@Required(desc="appraisalEndDate")
	private String appraisalEndDate;
	
	private String appraisalDepartment ;
	
	private String appraisalRoles ;
	
	private String appraisalEmpType ;
	
	private String selfAppraisalStartDate;
	
	private String selfAppraisalEndDate;
	
	private String appraisalApprovingStartDate;
	
	private String appraisalApprovingEndDate;
	
	private String applicableFor;
	
	private String experienceYears;
	
	private String experienceMonths;
	
	private String description ;
	
	private String discriptionDisplay;
	
	@Required(desc="categories")
	private String categories;
	
	@Required(desc="jsonTemplateString")
	private String jsonTemplateString ;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppraisalStartDate() {
		return appraisalStartDate;
	}

	public void setAppraisalStartDate(String appraisalStartDate) {
		this.appraisalStartDate = appraisalStartDate;
	}

	public String getAppraisalEndDate() {
		return appraisalEndDate;
	}

	public void setAppraisalEndDate(String appraisalEndDate) {
		this.appraisalEndDate = appraisalEndDate;
	}

	public String getAppraisalDepartment() {
		return appraisalDepartment;
	}

	public void setAppraisalDepartment(String appraisalDepartment) {
		this.appraisalDepartment = appraisalDepartment;
	}

	public String getAppraisalRoles() {
		return appraisalRoles;
	}

	public void setAppraisalRoles(String appraisalRoles) {
		this.appraisalRoles = appraisalRoles;
	}

	public String getAppraisalEmpType() {
		return appraisalEmpType;
	}

	public void setAppraisalEmpType(String appraisalEmpType) {
		this.appraisalEmpType = appraisalEmpType;
	}

	public String getSelfAppraisalStartDate() {
		return selfAppraisalStartDate;
	}

	public void setSelfAppraisalStartDate(String selfAppraisalStartDate) {
		this.selfAppraisalStartDate = selfAppraisalStartDate;
	}

	public String getSelfAppraisalEndDate() {
		return selfAppraisalEndDate;
	}

	public void setSelfAppraisalEndDate(String selfAppraisalEndDate) {
		this.selfAppraisalEndDate = selfAppraisalEndDate;
	}

	public String getAppraisalApprovingStartDate() {
		return appraisalApprovingStartDate;
	}

	public void setAppraisalApprovingStartDate(String appraisalApprovingStartDate) {
		this.appraisalApprovingStartDate = appraisalApprovingStartDate;
	}

	public String getAppraisalApprovingEndDate() {
		return appraisalApprovingEndDate;
	}

	public void setAppraisalApprovingEndDate(String appraisalApprovingEndDate) {
		this.appraisalApprovingEndDate = appraisalApprovingEndDate;
	}

	public String getApplicableFor() {
		return applicableFor;
	}

	public void setApplicableFor(String applicableFor) {
		this.applicableFor = applicableFor;
	}

	public String getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}

	public String getExperienceMonths() {
		return experienceMonths;
	}

	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscriptionDisplay() {
		return discriptionDisplay;
	}

	public void setDiscriptionDisplay(String discriptionDisplay) {
		this.discriptionDisplay = discriptionDisplay;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getJsonTemplateString() {
		return jsonTemplateString;
	}

	public void setJsonTemplateString(String jsonTemplateString) {
		this.jsonTemplateString = jsonTemplateString;
	}
	
	
	
	
}
