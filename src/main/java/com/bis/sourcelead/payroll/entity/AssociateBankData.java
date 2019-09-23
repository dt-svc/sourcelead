package com.bis.sourcelead.payroll.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "ASSOCIATE_BANK_DATA")
public class AssociateBankData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidate;
	 
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	@Column(name = "ROUTING_NUMBER")
	private String routingNumber;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name="DEFAULT_ACCOUNT")
	private Boolean  defaultAccount;
	
	@Column(name = "ASSOCIATE_BANK_UUID")
	private String associateBankUuid;

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


	public Candidate getCandidate()
	{
		return candidate;
	}

	public void setCandidate(Candidate candidate)
	{
		this.candidate = candidate;
	}

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}


	public String getAccountType()
	{
		return accountType;
	}

	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getRoutingNumber()
	{
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber)
	{
		this.routingNumber = routingNumber;
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

	public String getAssociateBankUuid()
	{
		return associateBankUuid;
	}

	public void setAssociateBankUuid(String associateBankUuid)
	{
		this.associateBankUuid = associateBankUuid;
	}

	public Boolean getDefaultAccount()
	{
		return defaultAccount;
	}

	public void setDefaultAccount(Boolean defaultAccount)
	{
		this.defaultAccount = defaultAccount;
	}
	
	
	
	
}
