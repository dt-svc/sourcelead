package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.bis.sourcelead.entity.OrgLocations;

public class RequirementSubmissionsDTO extends SearchDTO implements Serializable {

	private long requirementId;
	private long orgLocationId;
	private long userId;
	private String submissionType;
	private String loginUserRole;
	private String userCode;
	private String name;
	private String email;
	private String phNo;
	private String resume;
	private String appliedOn;
	private String rate;
	private String source;
	private String exp;
	private String status;
	private String clientSubmissions;
		
	public long getRequirementId()
	{
		return requirementId;
	}
	public void setRequirementId(long requirementId)
	{
		this.requirementId = requirementId;
	}
	public long getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	public String getSubmissionType()
	{
		return submissionType;
	}
	public void setSubmissionType(String submissionType)
	{
		this.submissionType = submissionType;
	}
	public String getLoginUserRole()
	{
		return loginUserRole;
	}
	public void setLoginUserRole(String loginUserRole)
	{
		this.loginUserRole = loginUserRole;
	}
	public String getUserCode()
	{
		return userCode;
	}
	public void setUserCode(String userCode)
	{
		this.userCode = userCode;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPhNo()
	{
		return phNo;
	}
	public void setPhNo(String phNo)
	{
		this.phNo = phNo;
	}
	public String getResume()
	{
		return resume;
	}
	public void setResume(String resume)
	{
		this.resume = resume;
	}
	public String getAppliedOn()
	{
		return appliedOn;
	}
	public void setAppliedOn(String appliedOn)
	{
		this.appliedOn = appliedOn;
	}
	public String getRate()
	{
		return rate;
	}
	public void setRate(String rate)
	{
		this.rate = rate;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getExp()
	{
		return exp;
	}
	public void setExp(String exp)
	{
		this.exp = exp;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getClientSubmissions()
	{
		return clientSubmissions;
	}
	public void setClientSubmissions(String clientSubmissions)
	{
		this.clientSubmissions = clientSubmissions;
	}
}
