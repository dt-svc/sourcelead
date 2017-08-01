package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.enums.Submission_Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "EXPENSES", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "Expenses")
public class Expenses implements Serializable {
private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="LOCATION_ID", nullable=false ) 
	@JsonBackReference
    private OrgLocations orgLocationId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "PROJECT_MEMBERS_ID", nullable=false)
	@JsonBackReference
	private ProjectMembers projectMembers;
	
	@Column(name = "EXPENSE_ID")
	private String expenseId;
	
	@Column(name = "PURPOSE", columnDefinition = "TEXT default NULL")
	private String purpose;
	
	@Column(name = "TOTAL_AMOUNT")
	private float totalAmount;
	
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
	
	@Column(name = "UPDATED_BY")
	 @JsonBackReference
	private String updatedBy;
	
	@Column(name = "EXPENSE_STATUS")
	@Enumerated(EnumType.STRING)
	private Submission_Status expenseStatus;
	
	@OneToMany(mappedBy="expensesId",cascade = CascadeType.ALL)
	 @JsonBackReference
//    @Fetch(FetchMode.SELECT)
    private Set<ExpensesEntries> expensesEntries;
	
	@OneToMany(mappedBy="expenseId",cascade = CascadeType.ALL)
	 @JsonBackReference
   // @Fetch(FetchMode.SELECT)
    private Set<ExpensesApproverHierarchy> approverHierarchyList;

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
	private String employementType;
	
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

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
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
	public Set<ExpensesApproverHierarchy> getApproverHierarchyList() {
		return approverHierarchyList;
	}

	public void setApproverHierarchyList(Set<ExpensesApproverHierarchy> approverHierarchyList) {
		this.approverHierarchyList = approverHierarchyList;
	}

	public Submission_Status getExpenseStatus() {
		return expenseStatus;
	}

	public void setExpenseStatus(Submission_Status expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	

	public String getStartDateStr() {
		if(startDate != null){
			startDateStr =	Constants.DF_DMY.format(startDate);
		}
		
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(endDate != null){
			endDateStr = Constants.DF_DMY.format(endDate);
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


//	public List<ExpensesEntries> getExpenseEntriesList() {
//		return expenseEntriesList;
//	}
//
//	public void setExpenseEntriesList(List<ExpensesEntries> expenseEntriesList) {
//		this.expenseEntriesList = expenseEntriesList;
//	}


	
	
}
