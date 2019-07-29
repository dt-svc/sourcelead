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
@Table(name = "CUSTOM_MODULE_CONF")
public class CustomModuleConfiguration {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "DASHBOARD_CONFI_ID", nullable = false)
	private CustomDashBoardConfiguration dashBoardConfId;
	
	@Column(name = "TAB_ID")
	private Long tabId;
	
	@Column(name="SUBTAB_ID")
	private Long subTabId;
	
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

	public Long getTabId()
	{
		return tabId;
	}

	public Long getSubTabId()
	{
		return subTabId;
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

	public void setDashBoardConfId(CustomDashBoardConfiguration dashBoardConfId)
	{
		this.dashBoardConfId = dashBoardConfId;
	}

	public void setTabId(Long tabId)
	{
		this.tabId = tabId;
	}

	public void setSubTabId(Long subTabId)
	{
		this.subTabId = subTabId;
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
	
	
	
	
}
