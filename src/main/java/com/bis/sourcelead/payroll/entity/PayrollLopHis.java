package com.bis.sourcelead.payroll.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;

@Entity
@Table(name = "PAYROLL_LOP_HIS")
public class PayrollLopHis {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID")
	private EmployeePayroll payroll;
	
	@ManyToOne
	@JoinColumn(name = "IND_PAYROLL_ID")
	private IndEmployeePayroll indPayroll;
	
	@ManyToOne
	@JoinColumn(name = "LEAVE_TYPE_ID")
	private LeaveType leaveTypeId;
	
	
	@Column( name = "LEAVE_TYPE_NAME")
	private String leaveTypeName;
	
	@Column( name = "TOTAL_BALANCE_LEAVES")
	private Float totalBalanceLeaves;
	
	@Column( name = "TOTAL_APPLIED_LEAVES")
	private Float totalAppliedLeaves;
	
	@Column( name = "LOP_LEAVES")
	private Float lopLeaves;
	
	@Column( name = "LOP_AMOUNT")
	private BigDecimal lopAmount;
	
	@Column( name = "PAYROLL_START_DATE")
	private Date payrollStartDate;
	
	@Column( name = "PAYROLL_END_DATE")
	private Date payrollEndDate;
	
	@Column( name = "PAYROLL_LOP_HIS_UUID")
	private String payrollLopHisUUID;
	
	@Column( name = "LOP_EXEMPT")
	private String lopExempt;
	
	@Column( name = "STATUS")
	private Integer status;
	
	@Column( name = "CREATED_DATE")
	private Date createdDate;
	
	@Column( name = "CREATED_BY")
	private Long createdBy;
	
	@Column( name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column( name = "UPDATED_BY")
	private Long updatedBy;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}

	public Float getTotalBalanceLeaves()
	{
		return totalBalanceLeaves;
	}

	public Float getTotalAppliedLeaves()
	{
		return totalAppliedLeaves;
	}

	public Float getLopLeaves()
	{
		return lopLeaves;
	}

	public BigDecimal getLopAmount()
	{
		return lopAmount;
	}

	public Date getPayrollStartDate()
	{
		return payrollStartDate;
	}

	public Date getPayrollEndDate()
	{
		return payrollEndDate;
	}

	public String getPayrollLopHisUUID()
	{
		return payrollLopHisUUID;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	public EmployeePayroll getPayroll()
	{
		return payroll;
	}

	public void setPayroll(EmployeePayroll payroll)
	{
		this.payroll = payroll;
	}

	public IndEmployeePayroll getIndPayroll() {
		return indPayroll;
	}

	public void setIndPayroll(IndEmployeePayroll indPayroll) {
		this.indPayroll = indPayroll;
	}

	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}

	public void setTotalBalanceLeaves(Float totalBalanceLeaves)
	{
		this.totalBalanceLeaves = totalBalanceLeaves;
	}

	public void setTotalAppliedLeaves(Float totalAppliedLeaves)
	{
		this.totalAppliedLeaves = totalAppliedLeaves;
	}

	public void setLopLeaves(Float lopLeaves)
	{
		this.lopLeaves = lopLeaves;
	}

	public void setLopAmount(BigDecimal lopAmount)
	{
		this.lopAmount = lopAmount;
	}

	public void setPayrollStartDate(Date payrollStartDate)
	{
		this.payrollStartDate = payrollStartDate;
	}

	public void setPayrollEndDate(Date payrollEndDate)
	{
		this.payrollEndDate = payrollEndDate;
	}

	public void setPayrollLopHisUUID(String payrollLopHisUUID)
	{
		this.payrollLopHisUUID = payrollLopHisUUID;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public LeaveType getLeaveTypeId()
	{
		return leaveTypeId;
	}

	public void setLeaveTypeId(LeaveType leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}

	public String getLopExempt()
	{
		return lopExempt;
	}

	public void setLopExempt(String lopExempt)
	{
		this.lopExempt = lopExempt;
	}
	
	
}
