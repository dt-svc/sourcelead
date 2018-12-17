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
import javax.persistence.Table;

import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "PAYROLL_APPROVER")
public class PayrollApprover {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "CONFIGURE_PAYROLL_APPROVER_ID", nullable = false)
	private ConfigurePayrollApprover configurePayrollApprover;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
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

	
	public Long getId()
	{
		return id;
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


	public User getUser()
	{
		return user;
	}


	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}


	public ConfigurePayrollApprover getConfigurePayrollApprover()
	{
		return configurePayrollApprover;
	}


	public void setConfigurePayrollApprover(ConfigurePayrollApprover configurePayrollApprover)
	{
		this.configurePayrollApprover = configurePayrollApprover;
	}


	public void setUser(User user)
	{
		this.user = user;
	}

	
	
}
