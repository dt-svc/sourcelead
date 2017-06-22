package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "SOURCELEAD_NOTES", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "sourceleadNotes")
public class SourceleadNotes {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )     
	private OrgRelations orgRelationID;
	
	@ManyToOne
    @JoinColumn(name="USER_ID", nullable=false )     
	private User userId;
	
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_ID", nullable=false )     
	private Requirement requirementId;
	
	@ManyToOne
    @JoinColumn(name="CONTRACT_ID", nullable=false )     
	private Contracts contractId;
	
	@ManyToOne
    @JoinColumn(name="INVOICE_ID", nullable=false )     
	private Invoice invoicdeId;
	
	@Column(name = "COMMENT",columnDefinition = "TEXT default NULL")
	private String comment;
	
	@Column(name = "NOTE_TYPE")
	private String noteType;
	
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

	public OrgRelations getOrgRelationID()
	{
		return orgRelationID;
	}

	public void setOrgRelationID(OrgRelations orgRelationID)
	{
		this.orgRelationID = orgRelationID;
	}

	public User getUserId()
	{
		return userId;
	}

	public void setUserId(User userId)
	{
		this.userId = userId;
	}

	public Requirement getRequirementId()
	{
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId)
	{
		this.requirementId = requirementId;
	}

	public Contracts getContractId()
	{
		return contractId;
	}

	public void setContractId(Contracts contractId)
	{
		this.contractId = contractId;
	}

	public Invoice getInvoicdeId()
	{
		return invoicdeId;
	}

	public void setInvoicdeId(Invoice invoicdeId)
	{
		this.invoicdeId = invoicdeId;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public String getNoteType()
	{
		return noteType;
	}

	public void setNoteType(String noteType)
	{
		this.noteType = noteType;
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
	
	
}
