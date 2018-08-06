package com.bis.sourcelead.payroll.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "US_EMP_DEDUCTION_HIS")
public class DeductionHistory {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "USER_ID", nullable = false)
	private Long userId;
	
	@Column(name = "DEDUCTION_ID", nullable = false)
	private Long deductionId;
	
	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID",nullable = false)
	private EmployeePayroll payroll;
	
	@Column(name = "FREQUENCY_START_DATE", nullable = false)
	private Date frequencyStartDate;
	
	@Column(name = "FREQUENCY_END_DATE", nullable = false)
	private Date frequencyEndDate;
	
	@Column(name = "PAY_CHECK_DATE", nullable = false)
	private Date payCheckDate;
	
	@Column(name = "US_EMP_DEDUCTION_HIS_UUID")
	private String deductionHisUUID;
	
	@Column(name = "DEDUCTION_DATE", nullable = false)
	private Date deductionDate;
	
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;
	
	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE", nullable = false)
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY", nullable = false)
	private Long updatedBy;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public Long getUserId()
	{
		return userId;
	}

	public Long getDeductionId()
	{
		return deductionId;
	}

	public Date getFrequencyStartDate()
	{
		return frequencyStartDate;
	}

	public Date getFrequencyEndDate()
	{
		return frequencyEndDate;
	}

	public Date getPayCheckDate()
	{
		return payCheckDate;
	}

	public Date getDeductionDate()
	{
		return deductionDate;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}


	public Date getUpdatedDate()
	{
		return updatedDate;
	}


	public void setId(Long id)
	{
		this.id = id;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public void setDeductionId(Long deductionId)
	{
		this.deductionId = deductionId;
	}

	public void setFrequencyStartDate(Date frequencyStartDate)
	{
		this.frequencyStartDate = frequencyStartDate;
	}

	public void setFrequencyEndDate(Date frequencyEndDate)
	{
		this.frequencyEndDate = frequencyEndDate;
	}

	public void setPayCheckDate(Date payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public void setDeductionDate(Date deductionDate)
	{
		this.deductionDate = deductionDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getDeductionHisUUID()
	{
		return deductionHisUUID;
	}

	public void setDeductionHisUUID(String deductionHisUUID)
	{
		this.deductionHisUUID = deductionHisUUID;
	}

	public EmployeePayroll getPayroll()
	{
		return payroll;
	}

	public void setPayroll(EmployeePayroll payroll)
	{
		this.payroll = payroll;
	}

	
	
}
