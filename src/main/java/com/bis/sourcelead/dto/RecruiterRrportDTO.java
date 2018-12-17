package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

public class RecruiterRrportDTO extends SearchDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private BigInteger requirementId;
    private Date createdDate;
    private BigInteger orgId;
    private BigInteger orgLocationId;
    private BigInteger assignedUserId;
    private BigInteger managerUserId;
    private String assignedUserStatus;
    private BigInteger primaryUserId;
    private BigInteger approverUserId;
    private String releaseJobToAllUsers;
    
	public BigInteger getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(BigInteger requirementId) {
		this.requirementId = requirementId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public BigInteger getOrgId() {
		return orgId;
	}
	public void setOrgId(BigInteger orgId) {
		this.orgId = orgId;
	}
	public BigInteger getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(BigInteger orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public BigInteger getAssignedUserId() {
		return assignedUserId;
	}
	public void setAssignedUserId(BigInteger assignedUserId) {
		this.assignedUserId = assignedUserId;
	}
	public BigInteger getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(BigInteger managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getAssignedUserStatus() {
		return assignedUserStatus;
	}
	public void setAssignedUserStatus(String assignedUserStatus) {
		this.assignedUserStatus = assignedUserStatus;
	}
	public BigInteger getPrimaryUserId() {
		return primaryUserId;
	}
	public void setPrimaryUserId(BigInteger primaryUserId) {
		this.primaryUserId = primaryUserId;
	}
	public BigInteger getApproverUserId() {
		return approverUserId;
	}
	public void setApproverUserId(BigInteger approverUserId) {
		this.approverUserId = approverUserId;
	}
	public String getReleaseJobToAllUsers()
	{
		return releaseJobToAllUsers;
	}
	public void setReleaseJobToAllUsers(String releaseJobToAllUsers)
	{
		this.releaseJobToAllUsers = releaseJobToAllUsers;
	}
	
}
