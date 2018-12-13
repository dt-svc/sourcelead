package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CUSTOM_INVOICE_TEMPLATE")
public class CustomInvoiceTemplate implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "ITEMS")
	private String items;

	@Column(name = "STATUS")
	private int status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY", referencedColumnName = "ID")
	private User createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY", referencedColumnName = "ID")
	private User updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_RELATION_ID", referencedColumnName = "ID")
	private OrgRelations orgRelations;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", referencedColumnName = "ID")
	private OrgLocations orgLocations;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public OrgRelations getOrgRelations() {
		return orgRelations;
	}

	public void setOrgRelations(OrgRelations orgRelations) {
		this.orgRelations = orgRelations;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

}
