package com.bis.sourcelead.payroll.entity;

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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "BIS_USER_MAPPING")
public class BisUserMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Column(name = "BRANCH")
	private String branch;
	
	@Column(name = "EMP_ID")
	private String empId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User userId;

	@Column(name = "CANDIDATE_NAME")
	private String candidateName;
	
	@Column(name = "RATE")
	private double rate;
	
	@Column(name = "FIXEDRATE")
	private double fixedRate;

	@Column(name = "PAYRATE")
	private double payRate;
	
	@Column(name = "FIXEDPAY")
	private double fixedPay;
	
	@Column(name = "INSURANCE")
	private double insurance;
	
	@Column(name = "DED")
	private double ded;
	
	@Column(name = "EFFECTIVE_RATE")
	private double effectiveRate;
	
	@Column(name = "PAYROLL_DIFFERENCE")
	private double payrollDifference;
	
	@Column(name = "IMMIGRATION")
	private String immigration;
	
	@Column(name = "COMMENTS"  ,columnDefinition = "TEXT default NULL")
	private String comments;
	
	@Column(name = "ADVANCE_HCP")
	private double advanceHcp;
	
	@Column(name = "BALANCE")
	private double balance;
	
	@Column(name = "VERSION")
	private int version;
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getFixedRate() {
		return fixedRate;
	}

	public void setFixedRate(double fixedRate) {
		this.fixedRate = fixedRate;
	}

	public double getPayRate() {
		return payRate;
	}

	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}

	public double getFixedPay() {
		return fixedPay;
	}

	public void setFixedPay(double fixedPay) {
		this.fixedPay = fixedPay;
	}

	public double getInsurance() {
		return insurance;
	}

	public void setInsurance(double insurance) {
		this.insurance = insurance;
	}

	public double getDed() {
		return ded;
	}

	public void setDed(double ded) {
		this.ded = ded;
	}

	public double getEffectiveRate() {
		return effectiveRate;
	}

	public void setEffectiveRate(double effectiveRate) {
		this.effectiveRate = effectiveRate;
	}

	public double getPayrollDifference() {
		return payrollDifference;
	}

	public void setPayrollDifference(double payrollDifference) {
		this.payrollDifference = payrollDifference;
	}

	public String getImmigration() {
		return immigration;
	}

	public void setImmigration(String immigration) {
		this.immigration = immigration;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public double getAdvanceHcp() {
		return advanceHcp;
	}

	public void setAdvanceHcp(double advanceHcp) {
		this.advanceHcp = advanceHcp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

}
