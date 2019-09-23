
package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "INVOICE_LINE_ITEMS")
public class InvoiceLineItems {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;
	
	@Column(name = "INLINE_DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String inlineDescription;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contracts contract; 
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVOICE_ID", nullable = false)
	private Invoice invoice;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_SUBMISSION_ID", nullable = false)
	private RequirementSubmission requirementSubmissionId;
	
	@Column(name = "RATE")
	private String rate;
	
	@Column(name = "TOTAL_HOURS")
	private String totalHours;
	
	@Column(name = "BILL_RATE_TYPE")
	private String billRateType;
	
	@Column(name = "EXPENSES")
	private String expenses;
	
	@Column(name = "SEPERATE_INVOCE")
	private String seperateInvoice;
	
	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "QUANTITY")
	private String quantity;
	
	@Column(name = "EXPENSE_CATEGORY")
	private String expenseCategory;
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	@JsonProperty
	private String candidateName;
	
	@Transient
	@JsonProperty
	private String sowName;

	@Transient
	private Long candidateId;
	
	@Transient
	private Long submissionId;
		
	@Transient
	@JsonProperty
	private String startDateStr;
	
	@Transient
	@JsonProperty
	private String requirementName;
	
	
	@Transient
	@JsonProperty
	private String employeeId;

	@Transient
	@JsonProperty 
	private String endDateStr;
	
	@Transient
	private Long contractId;
	
	@Transient
	private String sowContractName;
	
	@Transient
	private List<Contracts> contractList;
	
	@Transient
	private Long userId;

	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartDate()
	{
		return startDate;
	}
	
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	
	public Candidate getCandidate()
	{
		return candidate;
	}
	
	public void setCandidate(Candidate candidate)
	{
		this.candidate = candidate;
	}
	
	public Contracts getContract()
	{
		return contract;
	}
	
	public void setContract(Contracts contract)
	{
		this.contract = contract;
	}
	
	public Invoice getInvoice()
	{
		return invoice;
	}
	
	public void setInvoice(Invoice invoice)
	{
		this.invoice = invoice;
	}
	
	public OrgLocations getOrgLocation()
	{
		return orgLocation;
	}
	
	public void setOrgLocation(OrgLocations orgLocation)
	{
		this.orgLocation = orgLocation;
	}
	
	public String getRate()
	{
		return rate;
	}
	
	public void setRate(String rate)
	{
		this.rate = rate;
	}
	
	public String getTotalHours()
	{
		return totalHours;
	}
	
	public void setTotalHours(String totalHours)
	{
		this.totalHours = totalHours;
	}
	
	public String getExpenses()
	{
		return expenses;
	}
	
	public void setExpenses(String expenses)
	{
		this.expenses = expenses;
	}
	
	public String getSeperateInvoice()
	{
		return seperateInvoice;
	}
	
	public void setSeperateInvoice(String seperateInvoice)
	{
		this.seperateInvoice = seperateInvoice;
	}
	
	public String getAmount()
	{
		return amount;
	}
	
	public void setAmount(String amount)
	{
		this.amount = amount;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public Date getUpdatedDate()
	{
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}


	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getSowName() {
		return sowName;
	}

	public void setSowName(String sowName) {
		this.sowName = sowName;
	}
	
	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}


	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
}
	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getSowContractName()
	{
		return sowContractName;
	}

	public void setSowContractName(String sowContractName)
	{
		this.sowContractName = sowContractName;
	}

	public List<Contracts> getContractList()
	{
		return contractList;
	}

	public void setContractList(List<Contracts> contractList)
	{
		this.contractList = contractList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getInlineDescription() {
		return inlineDescription;
	}

	public void setInlineDescription(String inlineDescription) {
		this.inlineDescription = inlineDescription;
	}

	public String getExpenseCategory() {
		return expenseCategory;
	}

	public void setExpenseCategory(String expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	public String getBillRateType() {
		return billRateType;
	}

	public void setBillRateType(String billRateType) {
		this.billRateType = billRateType;
	}

	public RequirementSubmission getRequirementSubmissionId() {
		return requirementSubmissionId;
	}

	public void setRequirementSubmissionId(RequirementSubmission requirementSubmissionId) {
		this.requirementSubmissionId = requirementSubmissionId;
	}

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	
	
}
