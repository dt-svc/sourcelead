package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bis.sourcelead.enums.HIERARCHY_TYPE;

@Entity
@Table(name = "CONTRACT_OWNER_HIERARCHY")
public class ContractOwnerHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	@Column(name = "LEVEL")
	private Integer level;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contracts contract;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_USER_ID", nullable = false)
	private User ownerUserId;
	@Column(name = "HIERARCHY_TYPE")
	@Enumerated(EnumType.STRING)
	private HIERARCHY_TYPE hierarchyType;
	@Column(name = "CONTRACT_STATUS")
	private String templateSubmissionStatus;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "REJECTED_REASON")
	private String rejectedReason;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	@Transient
	private ArrayList<String> approverNames;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Integer getLevel()
	{
		return level;
	}
	
	public void setLevel(Integer level)
	{
		this.level = level;
	}
	
	public Contracts getContract()
	{
		return contract;
	}
	
	public void setContract(Contracts contract)
	{
		this.contract = contract;
	}
	
	public User getOwnerUserId()
	{
		return ownerUserId;
	}

	public void setOwnerUserId(User ownerUserId)
	{
		this.ownerUserId = ownerUserId;
	}

	public HIERARCHY_TYPE getHierarchyType()
	{
		return hierarchyType;
	}
	
	public void setHierarchyType(HIERARCHY_TYPE hierarchyType)
	{
		this.hierarchyType = hierarchyType;
	}
	
	public String getTemplateSubmissionStatus()
	{
		return templateSubmissionStatus;
	}
	
	public void setTemplateSubmissionStatus(String templateSubmissionStatus)
	{
		this.templateSubmissionStatus = templateSubmissionStatus;
	}
	
	public String getRejectedReason()
	{
		return rejectedReason;
	}
	
	public void setRejectedReason(String rejectedReason)
	{
		this.rejectedReason = rejectedReason;
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
	
	public ArrayList<String> getApproverNames()
	{
		return approverNames;
	}
	
	public void setApproverNames(ArrayList<String> approverNames)
	{
		this.approverNames = approverNames;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
	
}
