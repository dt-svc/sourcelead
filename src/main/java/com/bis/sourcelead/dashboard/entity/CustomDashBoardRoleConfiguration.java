package com.bis.sourcelead.dashboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DASHBOARD_ROLE_CONF")
public class CustomDashBoardRoleConfiguration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "DASHBOARD_CONF_ID", nullable = false)
	private CustomDashBoardConfiguration dashBoardConfId;
	
	@Column(name = "ROLE_ID")
	private Long roleId;
	
	@Column(name = "STATUS")
	private Integer status;
	
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

	public CustomDashBoardConfiguration getDashBoardConfId()
	{
		return dashBoardConfId;
	}

	public Long getRoleId()
	{
		return roleId;
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

	public void setDashBoardConfId(CustomDashBoardConfiguration dashBoardConfId)
	{
		this.dashBoardConfId = dashBoardConfId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
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

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	
	
}
