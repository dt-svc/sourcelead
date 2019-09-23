package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.dto.DepartmentDTO;

public class PerformanceAppraisalTemplateDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long appraisalTemplateId;
	
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
	
	private String appraisalProcessStartDate;
	
	private String appraisalProcessEndDate;
	
	private String description ;
	
	private String descriptionDisplay;
	
	private String feedbackCategory;
	
	private String appraisalCategories;
	
	private String comptencyCategories;
	
	private String jsonCategoriesString ;
	
	private String appraisalGoals;
	
	@Required(desc="goalsWeightage", dependsOn = "appraisalGoals", parentValue = "Y")
	private String goalsWeightage;
	
	private String appraisalProjectTasks;
	
	@Required(desc="projectTasksWeightage", dependsOn = "appraisalProjectTasks", parentValue = "Y")
	private String projectTasksWeightage;
	
	private List<DepartmentDTO> departmentList;
	
	
	public Long getAppraisalTemplateId() {
		return appraisalTemplateId;
	}

	public void setAppraisalTemplateId(Long appraisalTemplateId) {
		this.appraisalTemplateId = appraisalTemplateId;
	}

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

	public List<DepartmentDTO> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DepartmentDTO> departmentList) {
		this.departmentList = departmentList;
	}

	public String getAppraisalProcessStartDate() {
		return appraisalProcessStartDate;
	}

	public void setAppraisalProcessStartDate(String appraisalProcessStartDate) {
		this.appraisalProcessStartDate = appraisalProcessStartDate;
	}

	public String getAppraisalProcessEndDate() {
		return appraisalProcessEndDate;
	}

	public void setAppraisalProcessEndDate(String appraisalProcessEndDate) {
		this.appraisalProcessEndDate = appraisalProcessEndDate;
	}

	public String getFeedbackCategory() {
		return feedbackCategory;
	}

	public void setFeedbackCategory(String feedbackCategory) {
		this.feedbackCategory = feedbackCategory;
	}

	public String getAppraisalGoals() {
		return appraisalGoals;
	}

	public void setAppraisalGoals(String appraisalGoals) {
		this.appraisalGoals = appraisalGoals;
	}

	public String getGoalsWeightage() {
		return goalsWeightage;
	}

	public void setGoalsWeightage(String goalsWeightage) {
		this.goalsWeightage = goalsWeightage;
	}

	public String getAppraisalProjectTasks() {
		return appraisalProjectTasks;
	}

	public void setAppraisalProjectTasks(String appraisalProjectTasks) {
		this.appraisalProjectTasks = appraisalProjectTasks;
	}

	public String getProjectTasksWeightage() {
		return projectTasksWeightage;
	}

	public void setProjectTasksWeightage(String projectTasksWeightage) {
		this.projectTasksWeightage = projectTasksWeightage;
	}

	public String getComptencyCategories() {
		return comptencyCategories;
	}

	public void setComptencyCategories(String comptencyCategories) {
		this.comptencyCategories = comptencyCategories;
	}

	

	
}
