package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ASSOCIATE_LOAN_AND_ADVANCES_HISTORY")
public class AssociateLoanAndAdvanceHistory {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ASSOCIATE_LOAN_ADVANCE_ID")
	private AssociateLoanAndAdvances associateLoanAndAdvances;
	
	@ManyToOne
	@JoinColumn(name = "IND_EMPLOYEE_PAYROLL_ID")
	private IndEmployeePayroll indEmployeePayrollId;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATIONS_ID")
	private OrgLocations orgLocationsId;
	
	@Column(name = "PAYROLL_TYPE")
	private String payrollType;
	
	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID")
	private EmployeePayroll payroll;
	
	@Column(name = "DED_AMOUNT")
	private Double deductAmount;
	
	@Column(name = "PAID_AMOUNT")
	private Double paidAmount;
	
	@Column(name = "DUE_AMOUNT")
	private Double dueAmount;
	
	@Column(name = "SCHEDULE")
	private Integer schedule;
	
	@Column(name = "SCHEDULE_START_DATE")
	private Date scheduleStartDate;
	
	@Column(name = "SCHEDULE_END_DATE")
	private Date scheduleEndDate;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Transient
    @JsonProperty
    private String name;
	
	@Transient
    @JsonProperty
    private String type;
	
	@Transient
    @JsonProperty
    private Double totalAmount;
	
	@Transient
    @JsonProperty
    private String requestedTo;
	
	@Transient
    @JsonProperty
    private String requestedOn;
	
	@Transient
    @JsonProperty
    private Integer noOfShedules;
	
	@Transient
    @JsonProperty
    private Double lastPaindOn;
	
	@Transient
    @JsonProperty
    private String loanStatus;
	 
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public AssociateLoanAndAdvances getAssociateLoanAndAdvances()
	{
		return associateLoanAndAdvances;
	}

	public void setAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances)
	{
		this.associateLoanAndAdvances = associateLoanAndAdvances;
	}

	public Double getDeductAmount()
	{
		return deductAmount;
	}

	public void setDeductAmount(Double deductAmount)
	{
		this.deductAmount = deductAmount;
	}

	public Integer getSchedule()
	{
		return schedule;
	}

	public void setSchedule(Integer schedule)
	{
		this.schedule = schedule;
	}

	public Date getScheduleStartDate()
	{
		return scheduleStartDate;
	}

	public void setScheduleStartDate(Date scheduleStartDate)
	{
		this.scheduleStartDate = scheduleStartDate;
	}

	public Date getScheduleEndDate()
	{
		return scheduleEndDate;
	}

	public void setScheduleEndDate(Date scheduleEndDate)
	{
		this.scheduleEndDate = scheduleEndDate;
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

	public EmployeePayroll getPayroll() {
		return payroll;
	}

	public void setPayroll(EmployeePayroll payroll) {
		this.payroll = payroll;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public IndEmployeePayroll getIndEmployeePayrollId() {
		return indEmployeePayrollId;
	}

	public OrgLocations getOrgLocationsId() {
		return orgLocationsId;
	}

	public String getPayrollType() {
		return payrollType;
	}

	public void setIndEmployeePayrollId(IndEmployeePayroll indEmployeePayrollId) {
		this.indEmployeePayrollId = indEmployeePayrollId;
	}

	public void setOrgLocationsId(OrgLocations orgLocationsId) {
		this.orgLocationsId = orgLocationsId;
	}

	public void setPayrollType(String payrollType) {
		this.payrollType = payrollType;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public Double getDueAmount() {
		return dueAmount;
	}

	public String getRequestedTo() {
		return requestedTo;
	}

	public String getRequestedOn() {
		return requestedOn;
	}

	public Integer getNoOfShedules() {
		return noOfShedules;
	}

	public Double getLastPaindOn() {
		return lastPaindOn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public void setRequestedTo(String requestedTo) {
		this.requestedTo = requestedTo;
	}

	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}

	public void setNoOfShedules(Integer noOfShedules) {
		this.noOfShedules = noOfShedules;
	}

	public void setLastPaindOn(Double lastPaindOn) {
		this.lastPaindOn = lastPaindOn;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
}
