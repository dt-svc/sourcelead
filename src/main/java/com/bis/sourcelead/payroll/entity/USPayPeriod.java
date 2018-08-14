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

import com.bis.sourcelead.entity.OrgLocations;

@Entity
@Table(name = "US_PAY_PERIOD")
public class USPayPeriod {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "PAY_SHEET_FREQUENCY_ID", nullable = false)
	private PaysheetFrequency paysheetFrequency;
	
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	
	@Column (name="PAY_PERIOD_NUM")
	private Integer payPeriodNum;
	
	
	@Column (name="APPROVER_STATUS")
	private String approverStatus;
	
	  
	public String getApproverStatus()
	{
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus)
	{
		this.approverStatus = approverStatus;
	}

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

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public PaysheetFrequency getPaysheetFrequency()
	{
		return paysheetFrequency;
	}

	public Integer getPayPeriodNum()
	{
		return payPeriodNum;
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

	public void setPaysheetFrequency(PaysheetFrequency paysheetFrequency)
	{
		this.paysheetFrequency = paysheetFrequency;
	}

	public void setPayPeriodNum(Integer payPeriodNum)
	{
		this.payPeriodNum = payPeriodNum;
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

	public OrgLocations getOrgLocation()
	{
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation)
	{
		this.orgLocation = orgLocation;
	}
	
	
}
