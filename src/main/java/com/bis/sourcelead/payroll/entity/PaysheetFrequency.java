package com.bis.sourcelead.payroll.entity;

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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserGroup;

@Entity
@Table(name = "PAY_SHEET_FREQUENCY")
public class PaysheetFrequency {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@ManyToOne
	@JoinColumn(name = "GROUP_ID", nullable = false)
	private UserGroup userGroup;
	
	@Column(name = "FREQUENCY_START_DATE")
	private Date frequencyStartDate;
	
	@Column(name = "FREQUENCY_END_DATE")
	private Date frequencyEndDate;
	
	@Column (name = "PAY_CHECK_DATE")
	private Date payCheckDate;
	
	@Column (name = "PAY_SHEET_FREQUENCY_UUID")
	private String paySheetFrequencyUUID;
	
	@Column (name = "TYPE")
	private String type;
	
	@Column (name="APPROVER_STATUS")
	private String approverStatus;
	
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paysheetFrequency")
	private Set<Paysheet> paysheet;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "paysheetFrequency")
	private Set<EmployeePayroll> employeepayroll;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public OrgLocations getOrgLocation()
	{
		return orgLocation;
	}

	public UserGroup getUserGroup()
	{
		return userGroup;
	}

	

	public Date getFrequencyStartDate()
	{
		return frequencyStartDate;
	}

	public Date getFrequencyEndDate()
	{
		return frequencyEndDate;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}


	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public Set<Paysheet> getPaysheet()
	{
		return paysheet;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setOrgLocation(OrgLocations orgLocation)
	{
		this.orgLocation = orgLocation;
	}

	public void setUserGroup(UserGroup userGroup)
	{
		this.userGroup = userGroup;
	}


	public void setFrequencyStartDate(Date frequencyStartDate)
	{
		this.frequencyStartDate = frequencyStartDate;
	}

	public void setFrequencyEndDate(Date frequencyEndDate)
	{
		this.frequencyEndDate = frequencyEndDate;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}


	public Long getCreatedBy()
	{
		return createdBy;
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

	public void setPaysheet(Set<Paysheet> paysheet)
	{
		this.paysheet = paysheet;
	}

	public Date getPayCheckDate()
	{
		return payCheckDate;
	}

	public String getPaySheetFrequencyUUID()
	{
		return paySheetFrequencyUUID;
	}

	public Set<EmployeePayroll> getEmployeepayroll()
	{
		return employeepayroll;
	}

	public void setEmployeepayroll(Set<EmployeePayroll> employeepayroll)
	{
		this.employeepayroll = employeepayroll;
	}

	public void setPayCheckDate(Date payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}

	public void setPaySheetFrequencyUUID(String paySheetFrequencyUUID)
	{
		this.paySheetFrequencyUUID = paySheetFrequencyUUID;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getApproverStatus()
	{
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus)
	{
		this.approverStatus = approverStatus;
	}

}
