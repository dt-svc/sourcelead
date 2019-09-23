package com.bis.sourcelead.payroll.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.OrgLocations;

@Entity
@Table(name = "US_PAYROLL_SCHEDULE")
public class UsPayrollSchedule {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@Column(name = "PAY_FREQUENCY")
	private String payFrequency;
	
	@Column(name = "PAY_PERIOD_START_DATE")
	private Date payPeriodStartDate;
	
	@Column(name = "PAY_CHECK_DATE")
	private Date payCheckDate;
	
	@Column(name = "RUN_PAYROLL_DATE")
	private Date runPayrollDate;
	
	@Column(name = "NEXT_PAY_MONTH")
	private String nextPayMonth;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "NOTIFY_BEFORE")
	private String notifyMember;
	
	@Column(name = "PAYROLL_SCHEDULE_UUID")
	private String payrollScheduleUUID;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleId")
	private Set<USPayScheduleDate> scheduleDates;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocation() {
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation) {
		this.orgLocation = orgLocation;
	}


	public String getPayFrequency()
	{
		return payFrequency;
	}

	public Date getPayPeriodStartDate()
	{
		return payPeriodStartDate;
	}

	public Date getPayCheckDate()
	{
		return payCheckDate;
	}

	public Date getRunPayrollDate()
	{
		return runPayrollDate;
	}

	public String getNextPayMonth()
	{
		return nextPayMonth;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}

	public void setPayPeriodStartDate(Date payPeriodStartDate)
	{
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public void setPayCheckDate(Date payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public void setRunPayrollDate(Date runPayrollDate)
	{
		this.runPayrollDate = runPayrollDate;
	}

	public void setNextPayMonth(String nextPayMonth)
	{
		this.nextPayMonth = nextPayMonth;
	}

	public Integer getStatus() {
		return status;
	}

	public String getNotifyMember() {
		return notifyMember;
	}

	public void setNotifyMember(String notifyMember) {
		this.notifyMember = notifyMember;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getPayrollScheduleUUID()
	{
		return payrollScheduleUUID;
	}

	public void setPayrollScheduleUUID(String payrollScheduleUUID)
	{
		this.payrollScheduleUUID = payrollScheduleUUID;
	}

	public Set<USPayScheduleDate> getScheduleDates()
	{
		return scheduleDates;
	}

	public void setScheduleDates(Set<USPayScheduleDate> scheduleDates)
	{
		this.scheduleDates = scheduleDates;
	}

	
	
}
