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


@Entity
@Table(name = "SUB_TABS")
public class SubTabs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "TAB_ID", nullable = false)
	private Tabs tabs;
	
	@Column(name = "SUBTAB_NAME")
	private String subTabName;
	
	@Column(name ="SUBTAB_DISPLAY_NAME")
	private String subtabDisplayName;
	
	@Column(name = "STATUS", nullable = false)
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

	public Tabs getTabs()
	{
		return tabs;
	}


	public String getSubtabDisplayName()
	{
		return subtabDisplayName;
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

	public void setTabs(Tabs tabs)
	{
		this.tabs = tabs;
	}


	public void setSubtabDisplayName(String subtabDisplayName)
	{
		this.subtabDisplayName = subtabDisplayName;
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

	public String getSubTabName()
	{
		return subTabName;
	}

	public void setSubTabName(String subTabName)
	{
		this.subTabName = subTabName;
	}
	
	
	
	
	
	
	
}
