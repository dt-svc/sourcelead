package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.enums.Submission_Status;
import com.ibm.icu.math.BigDecimal;

public class ExpensesDTO implements Serializable {
private static final long serialVersionUID = 1L;

	private Long id;
	
	private String expenseId;
	
	private String ExpenseAutoId;
	
	private String expensePurpose;
	
	private float totalAmount;
	
	private String clientNameExpense;
	
	private String projectName;
	
	private String noOfExpenses;
	
	private Submission_Status expenseStatus;
	
	private Submission_Status combinedSubmissionStatus;
	
    private List<ExpensesEntriesDTO> expensesEntries;
	
	private Long projectMemberId;
	
	private String startDateExpense;
	
	private String endDateExpense;
	
	private boolean sahApproved;
	
	private String expCreatedBy;
	
	private BigDecimal expenseAmountDivId;
	
	private String staticPath;

	private List<Map<String, String>> fileInfo;
	
	private List<FileInfo> uploadedFileInfo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpensePurpose() {
		return expensePurpose;
	}

	public void setExpensePurpose(String purpose) {
		this.expensePurpose = purpose;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	/*public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}*/


	public String getNoOfExpenses() {
		return noOfExpenses;
	}

	public void setNoOfExpenses(String noOfExpenses) {
		this.noOfExpenses = noOfExpenses;
	}

	public Submission_Status getExpenseStatus() {
		return expenseStatus;
	}

	public void setExpenseStatus(Submission_Status expenseStatus) {
		this.expenseStatus = expenseStatus;
	}

	public List<ExpensesEntriesDTO> getExpensesEntries() {
		return expensesEntries;
	}

	public void setExpensesEntries(List<ExpensesEntriesDTO> expensesEntries) {
		this.expensesEntries = expensesEntries;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	/*public String getStartDate() {
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
	}*/

	public Submission_Status getCombinedSubmissionStatus() {
		return combinedSubmissionStatus;
	}

	public void setCombinedSubmissionStatus(Submission_Status combinedSubmissionStatus) {
		this.combinedSubmissionStatus = combinedSubmissionStatus;
	}

	public boolean getSahApproved() {
		return sahApproved;
	}

	public void setSahApproved(boolean sahApproved) {
		this.sahApproved = sahApproved;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getExpCreatedBy() {
		return expCreatedBy;
	}

	public void setExpCreatedBy(String expCreatedBy) {
		this.expCreatedBy = expCreatedBy;
	}

	/*public BigDecimal getTotalAmontRoundOffValue()
	{
		return totalAmontRoundOffValue;
	}

	public void setTotalAmontRoundOffValue(BigDecimal totalAmontRoundOffValue)
	{
		this.totalAmontRoundOffValue = totalAmontRoundOffValue;
	}*/

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<Map<String, String>> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<Map<String, String>> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getExpenseAutoId() {
		return ExpenseAutoId;
	}

	public void setExpenseAutoId(String expenseAutoId) {
		ExpenseAutoId = expenseAutoId;
	}

	public String getClientNameExpense() {
		return clientNameExpense;
	}

	public void setClientNameExpense(String clientNameExpense) {
		this.clientNameExpense = clientNameExpense;
	}

	public String getStartDateExpense() {
		return startDateExpense;
	}

	public void setStartDateExpense(String startDateExpense) {
		this.startDateExpense = startDateExpense;
	}

	public String getEndDateExpense() {
		return endDateExpense;
	}

	public void setEndDateExpense(String endDateExpense) {
		this.endDateExpense = endDateExpense;
	}

	public BigDecimal getExpenseAmountDivId() {
		return expenseAmountDivId;
	}

	public void setExpenseAmountDivId(BigDecimal expenseAmountDivId) {
		this.expenseAmountDivId = expenseAmountDivId;
	}
	
	
	
	
	
}
