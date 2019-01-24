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

@Entity
@Table(name = "CONFIGURE_PAYROLL_APPROVER")
public class ConfigurePayrollApprover {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocation;
	
	@Column(name = "AUTOMATIC_PAYROLL", nullable = false)
	private String automaticPayroll;
	
	@Column(name = "AUTO_PAYROLL_FREQUENCY", nullable = false)
	private String autoPayrollFrequency;
	
	@Column(name = "PAYROLL_TYPE", nullable = false)
	private String payrollType;
	
	@Column(name = "STATUS", nullable = false)
	private Integer status;
	
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;
	
	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE", nullable = false)
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY", nullable = false)
	private Long updatedBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "configurePayrollApprover")
	private Set<PayrollApprover> payrollApprover;
	
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

	public String getAutomaticPayroll()
	{
		return automaticPayroll;
	}

	public String getPayrollType()
	{
		return payrollType;
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

	public void setOrgLocation(OrgLocations orgLocation)
	{
		this.orgLocation = orgLocation;
	}

	public void setAutomaticPayroll(String automaticPayroll)
	{
		this.automaticPayroll = automaticPayroll;
	}

	public void setPayrollType(String payrollType)
	{
		this.payrollType = payrollType;
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

	public Set<PayrollApprover> getPayrollApprover()
	{
		return payrollApprover;
	}

	public void setPayrollApprover(Set<PayrollApprover> payrollApprover)
	{
		this.payrollApprover = payrollApprover;
	}

	public String getAutoPayrollFrequency()
	{
		return autoPayrollFrequency;
	}

	public void setAutoPayrollFrequency(String autoPayrollFrequency)
	{
		this.autoPayrollFrequency = autoPayrollFrequency;
	}

	
	 
}
