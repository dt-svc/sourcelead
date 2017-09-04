package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "SOURCELEAD_NOTES", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "sourceleadNotes")
public class SourceleadNotes {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )     
	private OrgRelations orgRelationID;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )     
	private OrgLocations orgLocationID;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="USER_ID", nullable=false )     
	private User userId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="SPOC_ID", nullable=false )     
	private OrgSpoc spocId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="REQUIREMENT_ID", nullable=false )     
	private Requirement requirementId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="CONTRACT_ID", nullable=false )     
	private Contracts contractId;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@ManyToOne
    @JoinColumn(name="INVOICE_ID", nullable=false )     
	private Invoice invoicdeId;
	
	@Transient
	private Long commentAuthorUserId;
	
	@Column(name = "COMMENT",columnDefinition = "TEXT default NULL")
	private String comment;
	
	@Column(name = "NOTE_TYPE")
	private String noteType;
	
	@Column(name = "CHECK_BOX")
	private String checkBox;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "DROPDOWN_TYPE")
	private String categoryType;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private String userName;
	
	@Transient
	private String createdDateString;
	
	@Transient
	private String requirementTitle;
	
	@Transient
	private String contactName;
	
	@Transient
	private String contract;
	
	@Transient
	private String invoiceId;
	
	@Transient
	private Long userIdStr;
	
	@Transient
	private String updatedDateString;
	

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

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getCreatedDateString()
	{
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString)
	{
		this.createdDateString = createdDateString;
	}

	public String getUpdatedDateString()
	{
		return updatedDateString;
	}

	public void setUpdatedDateString(String updatedDateString)
	{
		this.updatedDateString = updatedDateString;
	}

	public String getCheckBox()
	{
		return checkBox;
	}

	public void setCheckBox(String checkBox)
	{
		this.checkBox = checkBox;
	}

	public String getCategoryType()
	{
		return categoryType;
	}

	public void setCategoryType(String categoryType)
	{
		this.categoryType = categoryType;
	}

	public String getRequirementTitle()
	{
		return requirementTitle;
	}

	public void setRequirementTitle(String requirementTitle)
	{
		this.requirementTitle = requirementTitle;
	}

	public String getContactName()
	{
		return contactName;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	public String getContract()
	{
		return contract;
	}

	public void setContract(String contract)
	{
		this.contract = contract;
	}

	public String getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	public OrgSpoc getSpocId()
	{
		return spocId;
	}

	public void setSpocId(OrgSpoc spocId)
	{
		this.spocId = spocId;
	}

	public Long getUserIdStr()
	{
		return userIdStr;
	}

	public void setUserIdStr(Long userIdStr)
	{
		this.userIdStr = userIdStr;
	}

	public OrgLocations getOrgLocationID()
	{
		return orgLocationID;
	}

	public void setOrgLocationID(OrgLocations orgLocationID)
	{
		this.orgLocationID = orgLocationID;
	}

	public Long getCommentAuthorUserId()
	{
		return commentAuthorUserId;
	}

	public void setCommentAuthorUserId(Long commentAuthorUserId)
	{
		this.commentAuthorUserId = commentAuthorUserId;
	}
	
	
}
