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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "ASSOCIATE_BALANCE_LEAVES")
public class AssociateBalanceLeave implements Serializable{
	
	
private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	@Column (name = "LEAVE_TYPE_ID")
	private Long leaveTypeId;
	
	@Column (name = "TOTAL_LEAVES")
	private String totalLeaves;
	
	@Column (name = "ASSO_BAL_LEAVE_UUID")
	private String assoBalLeaveUUID;
	
	@Column (name = "STATUS")
	private Integer status;

	@Column (name = "LEAVETYPE_ROLES")
	private String leavetypeRoles;
	
	@Column (name = "LEAVETYPE_DEPARTMENT")
	private String leavetypeDepartment;
	
	@Column (name = "LEAVETYPE_EMP_TYPE")
	private String leavetypeEmpType;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	public Long getId()
	{
		return id;
	}

	

	public Long getLeaveTypeId()
	{
		return leaveTypeId;
	}

	public String getTotalLeaves()
	{
		return totalLeaves;
	}

	public String getAssoBalLeaveUUID()
	{
		return assoBalLeaveUUID;
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

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public void setLeaveTypeId(Long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}

	public void setTotalLeaves(String totalLeaves)
	{
		this.totalLeaves = totalLeaves;
	}

	public void setAssoBalLeaveUUID(String assoBalLeaveUUID)
	{
		this.assoBalLeaveUUID = assoBalLeaveUUID;
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

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getLeavetypeRoles() {
		return leavetypeRoles;
	}

	public String getLeavetypeDepartment() {
		return leavetypeDepartment;
	}

	public String getLeavetypeEmpType() {
		return leavetypeEmpType;
	}

	public void setLeavetypeRoles(String leavetypeRoles) {
		this.leavetypeRoles = leavetypeRoles;
	}

	public void setLeavetypeDepartment(String leavetypeDepartment) {
		this.leavetypeDepartment = leavetypeDepartment;
	}

	public void setLeavetypeEmpType(String leavetypeEmpType) {
		this.leavetypeEmpType = leavetypeEmpType;
	}
}
