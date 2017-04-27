package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "INVOICE", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "invoice")
public class Invoice {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	
	@ManyToOne
    @JoinColumn(name="CLIENT_ORG_RELATION_ID", nullable=false )     
	private OrgRelations clientOrgRelationID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CLIENT_CONTACT_USER_ID")
	private User contactContactUser;
	
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )     
	private OrgLocations orgLocation;
	
	@Column(name = "INVOICE_ID")
	private String invoiceId;
	
	@Column(name = "INVOICE_DATE")
	private Date invoiceDate;
	
	@Column(name = "DUE_DATE")
	private Date dueDate;
	
	@Column(name = "BILLING_ADDRESS",columnDefinition = "TEXT default NULL")
	private String billingAddress;
	
	@Column(name = "DISCOUNT_AMOUNT")
	private String discountAmount;
	
	@Column(name = "SUB_TOTAL")
	private String subTotal;
	
	@Column(name = "TOTAL")
	private String total;
	
	@Column(name = "DUE_AMOUNT")
	private String dueAmount;
	
	
	@Column(name = "INVOICE_COMMENT",columnDefinition = "TEXT default NULL")
	private String invoiceComment;
	
	@Column(name = "STATUS")
	private String status;
	
	
	@Column(name = "INVOICE_STATUS")
	private String invoiceStatus;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
	private Set<InvoiceLineItems> invoiceLineItems;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public OrgRelations getClientOrgRelationID()
	{
		return clientOrgRelationID;
	}

	public void setClientOrgRelationID(OrgRelations clientOrgRelationID)
	{
		this.clientOrgRelationID = clientOrgRelationID;
	}

	public User getContactContactUser()
	{
		return contactContactUser;
	}

	public void setContactContactUser(User contactContactUser)
	{
		this.contactContactUser = contactContactUser;
	}


	public String getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	public Date getInvoiceDate()
	{
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	public Date getDueDate()
	{
		return dueDate;
	}

	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}

	public String getBillingAddress()
	{
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress)
	{
		this.billingAddress = billingAddress;
	}

	public String getDiscountAmount()
	{
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount)
	{
		this.discountAmount = discountAmount;
	}

	public String getSubTotal()
	{
		return subTotal;
	}

	public void setSubTotal(String subTotal)
	{
		this.subTotal = subTotal;
	}

	public String getTotal()
	{
		return total;
	}

	public void setTotal(String total)
	{
		this.total = total;
	}

	public String getDueAmount()
	{
		return dueAmount;
	}

	public void setDueAmount(String dueAmount)
	{
		this.dueAmount = dueAmount;
	}

	public String getInvoiceComment()
	{
		return invoiceComment;
	}

	public void setInvoiceComment(String invoiceComment)
	{
		this.invoiceComment = invoiceComment;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
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

	public Set<InvoiceLineItems> getInvoiceLineItems()
	{
		return invoiceLineItems;
	}

	public void setInvoiceLineItems(Set<InvoiceLineItems> invoiceLineItems)
	{
		this.invoiceLineItems = invoiceLineItems;
	}

	public OrgLocations getOrgLocation()
	{
		return orgLocation;
	}

	public void setOrgLocation(OrgLocations orgLocation)
	{
		this.orgLocation = orgLocation;
	}

	public String getInvoiceStatus()
	{
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus)
	{
		this.invoiceStatus = invoiceStatus;
	}

	
	
}
