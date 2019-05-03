package com.bis.sourcelead.entity;

import java.io.Serializable;
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

@Entity
@Table(name="EMPLOYEE")
public class Employees implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable=false )        
    private User user;
	
	@Column(name ="EMPLOYEE_ID")
	private String employeeId;
	
	@Column(name ="HIRE_DATE")
	private Date hireDate;	
	
	@Column(name ="DOB")
	private Date dob;
	
	@Column(name="COUNTRY", columnDefinition = "LONGTEXT default NULL")
	private String country;
	
	@Column(name="STATE", columnDefinition = "LONGTEXT default NULL")
	private String state;
	
	@Column(name="CITY", columnDefinition = "LONGTEXT default NULL")
	private String city;
	
	@Column(name="EMPLOYEE_STATE")
	private String employeeState;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATIONS_ID", nullable=false )        
    private OrgLocations orgLocations;
	
	/*@Column(name="Invoice_Frequency")
	private String InvoiceFrequency;*/
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )        
    private OrgRelations orgRelationId;
	    
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DEPARTMENT_ID", nullable=false )        
    private Department departmentId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPOC_ID", nullable=false )        
    private OrgSpoc spocId;
	
	@Column(name = "EMPLOYMENT_TYPE")
	private String userEmploymentType;
	
	@Column(name="RATE")
	private String rate;
	
	@Column(name="RATE_PER")
	private String ratePer;
	
	@Column(name="DAILY_HOURS")
	private String dailyHours;
	
	@Column(name = "DESIGNATION_OTHER_TYPE")
	private String designationOtherType;
	
	@Column(name = "CURRENT_CURRENCY")
	private String currentCurrency;
	
	@Column(name ="CONFIRMATION_DATE")
	private Date confirmationDate;	
	
	@Column(name ="PROBATION_PERIOD")
	private String probationPeriod;	
	
	@Column(name ="PROBATION_STATUS")
	private String probationStatus;	
	
	@Transient
    private String hireDateStr;
	
	@Transient
    private String projectStartDate;
	
	@Transient
    private String projectEndDate;
	
	@Transient
	List<User> allReportingManagers;
	
	@Transient
    private String userType;
	
	@Transient
	private String panNumber;
	
	@Transient
	private String dateOfBirth;
	
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getEmployeeState() {
		return employeeState;
	}

	public void setEmployeeState(String employeeState) {
		this.employeeState = employeeState;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	/*public String getInvoiceFrequency() {
		return InvoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		InvoiceFrequency = invoiceFrequency;
	}*/

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

	public List<User> getAllReportingManagers() {
		return allReportingManagers;
	}

	public void setAllReportingManagers(List<User> allReportingManagers) {
		this.allReportingManagers = allReportingManagers;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRatePer() {
		return ratePer;
	}

	public void setRatePer(String ratePer) {
		this.ratePer = ratePer;
	}

	public String getDailyHours() {
		return dailyHours;
	}

	public void setDailyHours(String dailyHours) {
		this.dailyHours = dailyHours;
	}

	public String getHireDateStr() {
		return hireDateStr;
	}

	public void setHireDateStr(String hireDateStr) {
		this.hireDateStr = hireDateStr;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public OrgRelations getOrgRelationId()
	{
		return orgRelationId;
	}

	public void setOrgRelationId(OrgRelations orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}

	public OrgSpoc getSpocId()
	{
		return spocId;
	}

	public void setSpocId(OrgSpoc spocId)
	{
		this.spocId = spocId;
	}

	public String getUserEmploymentType()
	{
		return userEmploymentType;
	}

	public void setUserEmploymentType(String userEmploymentType)
	{
		this.userEmploymentType = userEmploymentType;
	}

	public String getDesignationOtherType()
	{
		return designationOtherType;
	}

	public void setDesignationOtherType(String designationOtherType)
	{
		this.designationOtherType = designationOtherType;
	}

	public String getCurrentCurrency()
	{
		return currentCurrency;
	}

	public void setCurrentCurrency(String currentCurrency)
	{
		this.currentCurrency = currentCurrency;
	}

	public Department getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Department departmentId)
	{
		this.departmentId = departmentId;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public String getProbationPeriod() {
		return probationPeriod;
	}

	public void setProbationPeriod(String probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	public String getProbationStatus() {
		return probationStatus;
	}

	public void setProbationStatus(String probationStatus) {
		this.probationStatus = probationStatus;
	}

}
