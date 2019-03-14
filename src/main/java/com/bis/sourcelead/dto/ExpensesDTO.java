package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ExpensesDTO extends TimesheetExpenseEntryDetailsUtilDTO implements Serializable {
private static final long serialVersionUID = 1L;

	private Long id;
	
	private String expenseAutoId;
	
	private String expensePurpose;
	
	private long projectId;
	
	private String projectMemberName;
	
	private String projectName;
	
	private Long projectMemberId;
	
	private Long masterCandidateId;
	
	private String clientName;
	
	private BigDecimal totalAmount;
	
	private String noOfExpenses;
	
	private String expenseStatus;
	
	private String combinedSubmissionStatus;
	
    private List<ExpensesEntriesDTO> expenseEntry;
	
	private String startDate;
	
	private String endDate;
	
	private String createdBy;
	
	private boolean sahApproved;
	
	
//	private String expenseLocationCode;
	
	private BigDecimal expenseAmountDivId;
	
	private List<Long> removedDocumentIds;
	
	private String projectClientUUID;
	 
	private String projectUUID;
	
	private String orgRelationUUID;
	
	private String expensesRejectedReason;
	
	private String projectStatus;
	
	private String billingStatus;
	
	private String projectMemberStartDate;
	
	private String projectMemberEndDate;
	
	private String submittedDate;
	
	
	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getProjectMemberStartDate() {
		return projectMemberStartDate;
	}

	public void setProjectMemberStartDate(String projectMemberStartDate) {
		this.projectMemberStartDate = projectMemberStartDate;
	}

	public String getProjectMemberEndDate() {
		return projectMemberEndDate;
	}

	public void setProjectMemberEndDate(String projectMemberEndDate) {
		this.projectMemberEndDate = projectMemberEndDate;
	}

	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	private BigDecimal totalsubmittedAmount;

	public BigDecimal getTotalsubmittedAmount() {
		return totalsubmittedAmount;
	}

	public void setTotalsubmittedAmount(BigDecimal totalsubmittedAmount) {
		this.totalsubmittedAmount = totalsubmittedAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenseAutoId() {
		return expenseAutoId;
	}

	public void setExpenseAutoId(String expenseAutoId) {
		this.expenseAutoId = expenseAutoId;
	}

	public String getExpensePurpose() {
		return expensePurpose;
	}

	public void setExpensePurpose(String expensePurpose) {
		this.expensePurpose = expensePurpose;
	}

	
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Long getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}


	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	

	public String getNoOfExpenses() {
		return noOfExpenses;
	}

	public void setNoOfExpenses(String noOfExpenses) {
		this.noOfExpenses = noOfExpenses;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getExpenseStatus() {
		return expenseStatus;
	}

	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}

	public String getCombinedSubmissionStatus() {
		return combinedSubmissionStatus;
	}

	public void setCombinedSubmissionStatus(String combinedSubmissionStatus) {
		this.combinedSubmissionStatus = combinedSubmissionStatus;
	}

	public List<ExpensesEntriesDTO> getExpenseEntry() {
		return expenseEntry;
	}

	public void setExpenseEntry(List<ExpensesEntriesDTO> expenseEntry) {
		this.expenseEntry = expenseEntry;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isSahApproved() {
		return sahApproved;
	}

	public void setSahApproved(boolean sahApproved) {
		this.sahApproved = sahApproved;
	}



	public BigDecimal getExpenseAmountDivId() {
		return expenseAmountDivId;
	}

	public void setExpenseAmountDivId(BigDecimal expenseAmountDivId) {
		this.expenseAmountDivId = expenseAmountDivId;
	}

	public String getProjectMemberName() {
		return projectMemberName;
	}

	public void setProjectMemberName(String projectMemberName) {
		this.projectMemberName = projectMemberName;
	}

	/*public String getExpenseLocationCode() {
		return expenseLocationCode;
	}

	public void setExpenseLocationCode(String expenseLocationCode) {
		this.expenseLocationCode = expenseLocationCode;
	}
*/
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Long> getRemovedDocumentIds() {
		return removedDocumentIds;
	}

	public void setRemovedDocumentIds(List<Long> removedDocumentIds) {
		this.removedDocumentIds = removedDocumentIds;
	}

	public String getProjectClientUUID()
	{
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID)
	{
		this.projectClientUUID = projectClientUUID;
	}

	public String getProjectUUID()
	{
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID)
	{
		this.projectUUID = projectUUID;
	}

	public String getOrgRelationUUID()
	{
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID)
	{
		this.orgRelationUUID = orgRelationUUID;
	}

	public String getExpensesRejectedReason() {
		return expensesRejectedReason;
	}
	public void setExpensesRejectedReason(String expensesRejectedReason) {
		this.expensesRejectedReason = expensesRejectedReason;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}


}
