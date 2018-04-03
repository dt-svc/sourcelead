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

import com.bis.sourcelead.entity.OrgLocations;

@Entity
@Table(name = "PAYROLL_DEDUCTION")
public class PayrollDeduction {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "PAYROLL_DEDUCTION_TYPE")
	private String payrollDeductionType;
	
	@Column(name = "PAYROLL_DEDUCTION_METHOD")
	private String payrollDeductionMethod;
	
	@Column(name = "PAYROLL_SCHEDULED")
	private String payrollScheduled;
	
	@Column(name = "TAX_TYPE")
	private String taxType;
	
	@Column(name = "DEDUCTION_UUID")
	private String deductionUUID;
	
	
	@Column(name = "DEDUCTION_AMOUNT")
	private BigDecimal deductionAmount;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "STATUS")
	private Integer status;
	
	
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

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getPayrollDeductionType()
	{
		return payrollDeductionType;
	}

	public void setPayrollDeductionType(String payrollDeductionType)
	{
		this.payrollDeductionType = payrollDeductionType;
	}

	public String getPayrollDeductionMethod()
	{
		return payrollDeductionMethod;
	}

	public void setPayrollDeductionMethod(String payrollDeductionMethod)
	{
		this.payrollDeductionMethod = payrollDeductionMethod;
	}

	public BigDecimal getDeductionAmount()
	{
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount)
	{
		this.deductionAmount = deductionAmount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
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

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getPayrollScheduled()
	{
		return payrollScheduled;
	}

	public void setPayrollScheduled(String payrollScheduled)
	{
		this.payrollScheduled = payrollScheduled;
	}

	public String getTaxType()
	{
		return taxType;
	}

	public void setTaxType(String taxType)
	{
		this.taxType = taxType;
	}

	public String getDeductionUUID()
	{
		return deductionUUID;
	}

	public void setDeductionUUID(String deductionUUID)
	{
		this.deductionUUID = deductionUUID;
	}
	
	
	
}
