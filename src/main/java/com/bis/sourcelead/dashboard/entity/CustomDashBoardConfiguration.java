package com.bis.sourcelead.dashboard.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.entity.OrgLocations;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "DASHBOARD_CONFIGURATION")
public class CustomDashBoardConfiguration {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )
	private OrgLocations orgLocations;
	
	@Column(name = "DASHBOARD_NAME")
	private String dashboardName;
	
	@Column(name = "DESCRIPTION",columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "APPLICABLE_FOR")
	private String applicableFor;
	
	@Column(name = "DASHBOARD_CONF_UUID")
	private String dashboardConfUUID;
	
	
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dashBoardConfId")
	@Fetch(FetchMode.SELECT)
    @JsonBackReference
	private Set<CustomDashBoardRoleConfiguration> dashBoardRoleConf;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dashBoardConfId")
	@Fetch(FetchMode.SELECT)
    @JsonBackReference
	private Set<CustomDashBoardAssociatesConf> dashBoardAssociatesConf;


	public Long getId()
	{
		return id;
	}


	public OrgLocations getOrgLocations()
	{
		return orgLocations;
	}


	public String getDashboardName()
	{
		return dashboardName;
	}


	public String getDescription()
	{
		return description;
	}


	public String getApplicableFor()
	{
		return applicableFor;
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


	public Set<CustomDashBoardRoleConfiguration> getDashBoardRoleConf()
	{
		return dashBoardRoleConf;
	}


	public Set<CustomDashBoardAssociatesConf> getDashBoardAssociatesConf()
	{
		return dashBoardAssociatesConf;
	}


	public void setId(Long id)
	{
		this.id = id;
	}


	public void setOrgLocations(OrgLocations orgLocations)
	{
		this.orgLocations = orgLocations;
	}


	public void setDashboardName(String dashboardName)
	{
		this.dashboardName = dashboardName;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public void setApplicableFor(String applicableFor)
	{
		this.applicableFor = applicableFor;
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


	public void setDashBoardRoleConf(Set<CustomDashBoardRoleConfiguration> dashBoardRoleConf)
	{
		this.dashBoardRoleConf = dashBoardRoleConf;
	}


	public void setDashBoardAssociatesConf(Set<CustomDashBoardAssociatesConf> dashBoardAssociatesConf)
	{
		this.dashBoardAssociatesConf = dashBoardAssociatesConf;
	}


	public Integer getStatus()
	{
		return status;
	}


	public void setStatus(Integer status)
	{
		this.status = status;
	}


	public String getDashboardConfUUID()
	{
		return dashboardConfUUID;
	}


	public void setDashboardConfUUID(String dashboardConfUUID)
	{
		this.dashboardConfUUID = dashboardConfUUID;
	}
	
	
	
	
	
	
	
}
