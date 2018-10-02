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

import com.bis.sourcelead.payroll.entity.EmployeePayroll;

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
	@JoinColumn(name = "PAYROLL_ID")
	private EmployeePayroll payroll;
	
	@Column(name = "DED_AMOUNT")
	private Double deductAmount;
	
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
	
	
}
