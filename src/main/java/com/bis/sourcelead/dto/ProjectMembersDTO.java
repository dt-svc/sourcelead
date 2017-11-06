package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ProjectMembersDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	private String name;
	
	private String expenseId;
	
	private String projectName;
	
	private String projectStartDate;
	
	private String projectEndDate;
	
	private String clientName;
	
	private Long candidateId;
	
	private Long masterCandidateId;
	
	private Long projectMemberId;
	
	private String payRateCurrency;
	
	private Double payRate;
	
	private String payRateRateType;
	
	private String billRateCurrency;
	
	private Double billRate;
	
	private String billRateRateType;
	
	private String memberStartDate;
	
	private String memberEndDate;
	
	private Double estHours;
	
	private Double estBudget;
	
	private Long projectId;
	
	List<ExpensesCategoryListDTO> expenseCategoryList;
	
	

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getPayRateCurrency() {
		return payRateCurrency;
	}

	public void setPayRateCurrency(String payRateCurrency) {
		this.payRateCurrency = payRateCurrency;
	}

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}

	public String getPayRateRateType() {
		return payRateRateType;
	}

	public void setPayRateRateType(String payRateRateType) {
		this.payRateRateType = payRateRateType;
	}

	public String getBillRateCurrency() {
		return billRateCurrency;
	}

	public void setBillRateCurrency(String billRateCurrency) {
		this.billRateCurrency = billRateCurrency;
	}

	public Double getBillRate() {
		return billRate;
	}

	public void setBillRate(Double billRate) {
		this.billRate = billRate;
	}

	public String getBillRateRateType() {
		return billRateRateType;
	}

	public void setBillRateRateType(String billRateRateType) {
		this.billRateRateType = billRateRateType;
	}

	public String getMemberStartDate() {
		return memberStartDate;
	}

	public void setMemberStartDate(String memberStartDate) {
		this.memberStartDate = memberStartDate;
	}

	public String getMemberEndDate() {
		return memberEndDate;
	}

	public void setMemberEndDate(String memberEndDate) {
		this.memberEndDate = memberEndDate;
	}

	public Double getEstHours() {
		return estHours;
	}

	public void setEstHours(Double estHours) {
		this.estHours = estHours;
	}

	public Double getEstBudget() {
		return estBudget;
	}

	public void setEstBudget(Double estBudget) {
		this.estBudget = estBudget;
	}

	public List<ExpensesCategoryListDTO> getExpenseCategoryList()
	{
		return expenseCategoryList;
	}

	public void setExpenseCategoryList(List<ExpensesCategoryListDTO> expenseCategoryList)
	{
		this.expenseCategoryList = expenseCategoryList;
	}

	public Long getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
}
