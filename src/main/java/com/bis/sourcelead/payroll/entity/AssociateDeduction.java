package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
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
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "ASSOCIATE_DEDUCTION")
public class AssociateDeduction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "DEDUCTION_TYPE_ID", nullable = false)
	private PayrollDeduction deductionTypeId;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User userId;
	 
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "DEDUCTION_METHOD")
	private String deductionMethod;
	
	@Column(name = "DEDUCTION_AMOUNT")
	private BigDecimal deductionAmount;
	
	@Column(name = "STATUS")
	private Integer status;
	
	
	@Column(name = "ASSOCIATE_DEDUCTION_UUID")
	private String associateDeductionUuid;
	
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

	
	public PayrollDeduction getDeductionTypeId()
	{
		return deductionTypeId;
	}

	public void setDeductionTypeId(PayrollDeduction deductionTypeId)
	{
		this.deductionTypeId = deductionTypeId;
	}

	public User getUserId()
	{
		return userId;
	}

	public void setUserId(User userId)
	{
		this.userId = userId;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getDeductionMethod()
	{
		return deductionMethod;
	}

	public void setDeductionMethod(String deductionMethod)
	{
		this.deductionMethod = deductionMethod;
	}

	public BigDecimal getDeductionAmount()
	{
		return deductionAmount;
	}

	public void setDeductionAmount(BigDecimal deductionAmount)
	{
		this.deductionAmount = deductionAmount;
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

	public String getAssociateDeductionUuid()
	{
		return associateDeductionUuid;
	}

	public void setAssociateDeductionUuid(String associateDeductionUuid)
	{
		this.associateDeductionUuid = associateDeductionUuid;
	}
	
	
	
	
}
