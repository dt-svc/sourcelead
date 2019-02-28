package com.bis.sourcelead.payroll.entity;

import java.math.BigDecimal;
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

@Entity
@Table(name = "PAY_ADJUSTMENT_HISTORY")
public class PayAdjustmentHistory {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID", nullable = false)
	private EmployeePayroll payrollId;
	
	
	@Column( name = "PRE_REGULAR_HOURS")
	private String preRegularHours;
	
	@Column( name = "PRE_REGULAR_HOURS_AMOUNT")
	private BigDecimal preRegularHoursAmt;
	
	@Column( name = "PRE_OVERTIME_HOURS")
	private String preOvertimeHours;
	
	@Column( name = "PRE_OVERTIME_HOURS_AMOUNT")
	private BigDecimal preOvertimeHoursAmt;
	
	@Column( name = "PRE_PAY_TYPE")
	private String prePayType;
	
	@Column( name = "PRE_PAY_PER_SALARY")
	private BigDecimal prePayPerSalary;
	
	@Column( name = "PAY_TYPE")
	private String payType;
	
	@Column( name = "PAY_PER_SALARY")
	private BigDecimal payPerSalary;
	
	
	@Column( name = "REGULAR_HOURS")
	private String regularHours;
	
	@Column( name = "REGULAR_HOURS_AMOUNT")
	private BigDecimal regularHoursAmt;
	
	@Column( name = "OVERTIME_HOURS")
	private String overtimeHours;
	
	@Column( name = "PRE_LOP_AMOUNT")
	private BigDecimal preLopAmount;
	
	@Column( name = "OVERTIME_HOURS_AMOUNT")
	private BigDecimal overtimeHoursAmt;
	
	@Column( name = "LOP_AMOUNT")
	private BigDecimal lopAmount;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payAdjustmentHistory")
	private Set<PayAdjustmentDedHistory> payAdjustmentDedHistory;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "payAdjustmentHistory")
	private Set<PayAdjustmentPayItemHistory> payAdjustmentPayItemHistory;
	

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public EmployeePayroll getPayrollId()
	{
		return payrollId;
	}

	public String getPreRegularHours()
	{
		return preRegularHours;
	}

	public BigDecimal getPreRegularHoursAmt()
	{
		return preRegularHoursAmt;
	}

	public String getPreOvertimeHours()
	{
		return preOvertimeHours;
	}

	public BigDecimal getPreOvertimeHoursAmt()
	{
		return preOvertimeHoursAmt;
	}

	public String getRegularHours()
	{
		return regularHours;
	}

	public BigDecimal getRegularHoursAmt()
	{
		return regularHoursAmt;
	}

	public String getOvertimeHours()
	{
		return overtimeHours;
	}

	public BigDecimal getOvertimeHoursAmt()
	{
		return overtimeHoursAmt;
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

	public void setPayrollId(EmployeePayroll payrollId)
	{
		this.payrollId = payrollId;
	}

	public void setPreRegularHours(String preRegularHours)
	{
		this.preRegularHours = preRegularHours;
	}

	public void setPreRegularHoursAmt(BigDecimal preRegularHoursAmt)
	{
		this.preRegularHoursAmt = preRegularHoursAmt;
	}

	public void setPreOvertimeHours(String preOvertimeHours)
	{
		this.preOvertimeHours = preOvertimeHours;
	}

	public void setPreOvertimeHoursAmt(BigDecimal preOvertimeHoursAmt)
	{
		this.preOvertimeHoursAmt = preOvertimeHoursAmt;
	}

	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}

	public void setRegularHoursAmt(BigDecimal regularHoursAmt)
	{
		this.regularHoursAmt = regularHoursAmt;
	}

	public void setOvertimeHours(String overtimeHours)
	{
		this.overtimeHours = overtimeHours;
	}

	public void setOvertimeHoursAmt(BigDecimal overtimeHoursAmt)
	{
		this.overtimeHoursAmt = overtimeHoursAmt;
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

	public Set<PayAdjustmentDedHistory> getPayAdjustmentDedHistory()
	{
		return payAdjustmentDedHistory;
	}

	public Set<PayAdjustmentPayItemHistory> getPayAdjustmentPayItemHistory()
	{
		return payAdjustmentPayItemHistory;
	}

	public void setPayAdjustmentDedHistory(Set<PayAdjustmentDedHistory> payAdjustmentDedHistory)
	{
		this.payAdjustmentDedHistory = payAdjustmentDedHistory;
	}

	public void setPayAdjustmentPayItemHistory(Set<PayAdjustmentPayItemHistory> payAdjustmentPayItemHistory)
	{
		this.payAdjustmentPayItemHistory = payAdjustmentPayItemHistory;
	}

	public String getPrePayType()
	{
		return prePayType;
	}

	public BigDecimal getPrePayPerSalary()
	{
		return prePayPerSalary;
	}

	public String getPayType()
	{
		return payType;
	}

	public BigDecimal getPayPerSalary()
	{
		return payPerSalary;
	}

	public void setPrePayType(String prePayType)
	{
		this.prePayType = prePayType;
	}

	public void setPrePayPerSalary(BigDecimal prePayPerSalary)
	{
		this.prePayPerSalary = prePayPerSalary;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	public void setPayPerSalary(BigDecimal payPerSalary)
	{
		this.payPerSalary = payPerSalary;
	}

	public BigDecimal getPreLopAmount()
	{
		return preLopAmount;
	}

	public BigDecimal getLopAmount()
	{
		return lopAmount;
	}

	public void setPreLopAmount(BigDecimal preLopAmount)
	{
		this.preLopAmount = preLopAmount;
	}

	public void setLopAmount(BigDecimal lopAmount)
	{
		this.lopAmount = lopAmount;
	}
	

}
