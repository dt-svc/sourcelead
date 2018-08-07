package com.bis.sourcelead.payroll.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class PayitemHistory {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column (name="PAY_ITEM_ID")
	private Long payItemId;
	
	@ManyToOne
	@JoinColumn(name = "PAYROLL_ID")
	private EmployeePayroll payroll;
	
	@Column (name="FREQUENCY_START_DATE")
	private Date frequencyStartDate;
	
	@Column (name="FREQUENCY_END_DATE")
	private Date frequencyEndDate;
	
	@Column (name="PAY_CHECK_DATE")
	private Date payCheckDate;
	
	
	@Column (name="PAYITEM_DATE")
	private Date payItemDate;
	
	@Column (name="PAYITEM_HIS_UUID")
	private String payItemHisUUID;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public Long getPayItemId()
	{
		return payItemId;
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

	public Date getPayItemDate()
	{
		return payItemDate;
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

	public void setPayItemId(Long payItemId)
	{
		this.payItemId = payItemId;
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

	public void setPayItemDate(Date payItemDate)
	{
		this.payItemDate = payItemDate;
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

	public EmployeePayroll getPayroll()
	{
		return payroll;
	}

	public void setPayroll(EmployeePayroll payroll)
	{
		this.payroll = payroll;
	}

	public String getPayItemHisUUID()
	{
		return payItemHisUUID;
	}

	public void setPayItemHisUUID(String payItemHisUUID)
	{
		this.payItemHisUUID = payItemHisUUID;
	}
	
	
}
