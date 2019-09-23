package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.bis.sourcelead.enums.Submission_Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "EXPENSES")
public class Expenses implements Serializable {
private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="LOCATION_ID", nullable=false ) 
	@JsonBackReference
    private OrgLocations orgLocationId;
	
	@Column(name = "EXPENSES_LOCATION_ID") 
    private Long expensesLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_MEMBERS_ID", nullable=false)
	@JsonBackReference
	private ProjectMembers projectMembers;
	
	@Column(name = "EXPENSE_ID")
	private String expenseId;
	
	@Column(name = "PURPOSE", columnDefinition = "TEXT default NULL")
	private String purpose;
	
	@Column(name = "TOTAL_AMOUNT")
	private BigDecimal totalAmount;
	
	@Column(name = "Total_Submitted_Amount")
	private BigDecimal totalsubmittedAmount;
	
	@Column(name = "CLIENT_NAME")
	private String clientName;
	
	@Column(name = "START_DATE")
    @JsonBackReference
	private Date startDate;
	
	@Column(name = "END_DATE")
    @JsonBackReference
	private Date endDate;
	
	@Column(name = "NO_OF_EXPENSES")
	private String noOfExpenses;
	
	@Column(name = "STATUS")
	@JsonBackReference
	private String status;
	
	@Column(name = "CREATED_DATE")
	@JsonBackReference
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	@JsonBackReference
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	@JsonBackReference
	private Date updatedDate;
	
	@Column(name = "SUBMITTED_DATE")
	@JsonBackReference
	private Date submittedDate;
	
	@Column(name = "UPDATED_BY")
	 @JsonBackReference
	private String updatedBy;
	
	@Column(name = "EXPENSE_STATUS")
	@Enumerated(EnumType.STRING)
	private Submission_Status expenseStatus;
//	  
	@Column(name = "BILLING_RECIEVABLE_STATUS")
	private String billingRecievableStatus;
	
	@Column(name = "BILLING_PAYABLE_STATUS")
	private String billingPayableStatus;
	
	@Column(name = "BILLING_STATUS")
	private String billingStatus;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable=false)
	@JsonBackReference
	private Projects projects;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MASTER_CANDIDATE_ID", nullable=false) 
	@JsonBackReference
    private MasterCandidate masterCandidateId;
	
	
	@OneToMany(mappedBy="expensesId")
	@JsonBackReference
//  @Fetch(FetchMode.SELECT)
    private Set<ExpensesEntries> expensesEntries;
	
	
	@OneToMany(mappedBy="expenseId")
	@JsonBackReference
   // @Fetch(FetchMode.SELECT)
    private List<ExpensesApproverHierarchy> approverHierarchyList;

	@Transient
	@JsonProperty
	private String startDateStr;
	
	@Transient
	@JsonProperty
	private String endDateStr;
	
//	@Transient
//	private List<ExpensesEntries> expenseEntriesList;
		
	@Transient
	private List<User> projectManagerUserList;
	
	@Transient
	private List<User> projectEmp;
  
	@Transient
	private String employementType;
	
	@Transient
	private String expenseAutoId;
	
	@Transient
	private String userName;
	
	@Transient
	private String userEmail;
	
	@Transient
	@JsonProperty
	private String projectName;
	
	@Transient
	private Map<String,String> monthsStringFormat;
	
	@Transient
	@JsonProperty
	private String dateFrequency;
	
	@Transient
	@JsonProperty
	private String locationsCode;
    

	@Transient
	@JsonProperty
	private BigDecimal totalAmountRoundoffValue;
	

	@Transient
	@JsonProperty
	private String expenseLocationCode;
	
	@Transient
	@JsonProperty
	private String orgRelationUUID;
	
	@Transient
	@JsonProperty
	private String orgRelationId;
	
	@Transient
	@JsonProperty
	private String expensesRejectedReason;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public ProjectMembers getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(ProjectMembers projectMembers) {
		this.projectMembers = projectMembers;
	}

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNoOfExpenses() {
		return noOfExpenses;
	}

	public void setNoOfExpenses(String noOfExpenses) {
		this.noOfExpenses = noOfExpenses;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<ExpensesEntries> getExpensesEntries()
	{
		return expensesEntries;
	}

	public void setExpensesEntries(Set<ExpensesEntries> expensesEntries)
	{
		this.expensesEntries = expensesEntries;
	}
	public List<ExpensesApproverHierarchy> getApproverHierarchyList() {
		return approverHierarchyList;
	}

	public void setApproverHierarchyList(List<ExpensesApproverHierarchy> approverHierarchyList) {
		this.approverHierarchyList = approverHierarchyList;
	}

	public Submission_Status getExpenseStatus() {
		return expenseStatus;
	}

	public void setExpenseStatus(Submission_Status expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	

	public String getLocationsCode() {
		if(orgLocationId != null){
			locationsCode = orgLocationId.getLocationCode();
		}
		return locationsCode;
	}

	public void setLocationsCode(String locationsCode) {
		this.locationsCode = locationsCode;
	}

	public String getStartDateStr() {
		if(startDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			startDateStr = dateFormat.format(startDate);
		}
		
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(endDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			endDateStr = dateFormat.format(endDate);
		}
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public List<User> getProjectManagerUserList() {
		return projectManagerUserList;
	}

	public void setProjectManagerUserList(List<User> projectManagerUserList) {
		this.projectManagerUserList = projectManagerUserList;

	}

	public String getEmployementType() {
		return employementType;
	}

	public void setEmployementType(String employementType) {
		this.employementType = employementType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getProjectName() {
		if(projectMembers != null && projectMembers.getProjects() != null && StringUtils.isNotEmpty(projectMembers.getProjects().getProjectName())){
			projectName = projectMembers.getProjects().getProjectName();
		}
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Map<String, String> getMonthsStringFormat() {
		return monthsStringFormat;
	}

	public void setMonthsStringFormat(Map<String, String> monthsStringFormat) {
		this.monthsStringFormat = monthsStringFormat;
	}

	public String getDateFrequency() {
		return dateFrequency;
	}

	public void setDateFrequency(String dateFrequency) {
		this.dateFrequency = dateFrequency;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBillingRecievableStatus() {
		return billingRecievableStatus;
	}

	public void setBillingRecievableStatus(String billingRecievableStatus) {
		this.billingRecievableStatus = billingRecievableStatus;
	}

	public String getBillingPayableStatus() {
		return billingPayableStatus;
	}

	public void setBillingPayableStatus(String billingPayableStatus) {
		this.billingPayableStatus = billingPayableStatus;
	}

	public BigDecimal getTotalAmountRoundoffValue() {
		return totalAmountRoundoffValue;
	}

	public void setTotalAmountRoundoffValue(BigDecimal totalAmountRoundoffValue) {
		this.totalAmountRoundoffValue = totalAmountRoundoffValue;
	}


	public Projects getProjects() {
		return projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	public MasterCandidate getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(MasterCandidate masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public String getExpenseLocationCode() {
		return expenseLocationCode;
	}

	public void setExpenseLocationCode(String expenseLocationCode) {
		this.expenseLocationCode = expenseLocationCode;
	}

	public String getOrgRelationUUID()
	{
		return orgRelationUUID;
	}

	public void setOrgRelationUUID(String orgRelationUUID)
	{
		this.orgRelationUUID = orgRelationUUID;
	}

	public String getOrgRelationId()
	{
		return orgRelationId;
	}

	public void setOrgRelationId(String orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}

	public String getExpensesRejectedReason() {
		return expensesRejectedReason;
	}

	public void setExpensesRejectedReason(String expensesRejectedReason) {
		this.expensesRejectedReason = expensesRejectedReason;
	}

	public Long getExpensesLocationId() {
		return expensesLocationId;
	}

	public void setExpensesLocationId(Long expensesLocationId) {
		this.expensesLocationId = expensesLocationId;
	}

	public String getExpenseAutoId() {
		return expenseAutoId;
	}

	public void setExpenseAutoId(String expenseAutoId) {
		this.expenseAutoId = expenseAutoId;
	}

	public BigDecimal getTotalsubmittedAmount() {
		return totalsubmittedAmount;
	}

	public void setTotalsubmittedAmount(BigDecimal totalsubmittedAmount) {
		this.totalsubmittedAmount = totalsubmittedAmount;
	}

	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public List<User> getProjectEmp() {
		return projectEmp;
	}

	public void setProjectEmp(List<User> projectEmp) {
		this.projectEmp = projectEmp;
	}
	
	
	

//	public List<ExpensesEntries> getExpenseEntriesList() {
//		return expenseEntriesList;
//	}
//
//	public void setExpenseEntriesList(List<ExpensesEntries> expenseEntriesList) {
//		this.expenseEntriesList = expenseEntriesList;
//	}


	
	
}
