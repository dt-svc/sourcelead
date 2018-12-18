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
	
	@Required (desc = "experienceYears", dependsOn = "applicableFor", parentValue = "Experienced")
	private String experienceYears;
	
	@Required (desc = "experienceMonths", dependsOn = "applicableFor",  parentValue = "Experienced")
	private String experienceMonths;
	
	private String description ;
	
	private String descriptionDisplay;
	
	@Required(desc="appraisalCategories")
	private String appraisalCategories;
	
	@Required(desc="jsonTemplateString")
	private String jsonCategoriesString ;
	
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


	public String getDescriptionDisplay() {
		return descriptionDisplay;
	}

	public void setDescriptionDisplay(String descriptionDisplay) {
		this.descriptionDisplay = descriptionDisplay;
	}

	public String getAppraisalCategories() {
		return appraisalCategories;
	}

	public void setAppraisalCategories(String appraisalCategories) {
		this.appraisalCategories = appraisalCategories;
	}

	public String getJsonCategoriesString() {
		return jsonCategoriesString;
	}

	public void setJsonCategoriesString(String jsonCategoriesString) {
		this.jsonCategoriesString = jsonCategoriesString;
	}

	
}
