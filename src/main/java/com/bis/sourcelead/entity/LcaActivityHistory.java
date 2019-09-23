/*package com.bis.sourcelead.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "LCA_ACTIVITY_HISTORY")
public class LcaActivityHistory implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LCA_ID", nullable = false)
	private Lca lcaId;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	@Column(name = "ACTION_BY")
   	private String actionBy;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "ACTION_DESC")
	@Type(type="text")
   	private String actionDesc;
	
   	@Column(name = "CREATED_DATE")
   	private Date createdDate;
   	
   	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;
   	
   	@Column(name = "UPDATED_DATE")
   	private Date updatedDate;
   	
   	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
   	
   	@Transient
	@JsonProperty
	private String commentedUser;
   	
   	@Transient
	@JsonProperty
	private String createdDateString;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lca getLcaId() {
		return lcaId;
	}

	public void setLcaId(Lca lcaId) {
		this.lcaId = lcaId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getActionBy() {
		return actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCommentedUser() {
		return commentedUser;
	}

	public void setCommentedUser(String commentedUser) {
		this.commentedUser = commentedUser;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}
}
*/