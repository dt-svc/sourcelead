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
import javax.persistence.Transient;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.ConversionUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "USER_PYAROLL")
public class UserPayroll implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "EMP_ID")
	private String empId;
	
	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User userId;
	
	@Column(name = "PAYROLL_START_DATE")
	private Date payrollStartDate;
	
	@Column(name = "PAYROLL_END_DATE")
	private Date payrollEndDate;
	
	
	@Transient
	@JsonProperty
	private String payrollStartDateStr;
	
	@Transient
	@JsonProperty
	private String payrollEndDateStr;
	
	@Column(name = "TOTAL_HOURS")
	private String totalHours;
	
	@Column(name = "HALF_HOURS")
	private String halfHours;
	
	@Column(name = "PAY_RATE")
	private String payRate;
	
	@Column(name = "AMOUNT")
	private String amount;
	
	@Column(name = "PRE_DEDUCTINS")
	private String preDeductins;
	
	@Column(name = "FINAL_AMOUNT")
	private String finalAmount;
	
	@Column(name = "POST_DEDUCTIONS")
	private String postDeductions;
	
	@Column(name = "REIMBURSEMENT")
	private String reimbursement;
	
	@Column(name = "COMMENTS"  ,columnDefinition = "TEXT default NULL")
	private String comments;
	
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

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public Date getPayrollStartDate() {
		return payrollStartDate;
	}

	public void setPayrollStartDate(Date payrollStartDate) {
		this.payrollStartDate = payrollStartDate;
	}

	public Date getPayrollEndDate() {
		return payrollEndDate;
	}

	public void setPayrollEndDate(Date payrollEndDate) {
		this.payrollEndDate = payrollEndDate;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getHalfHours() {
		return halfHours;
	}

	public void setHalfHours(String halfHours) {
		this.halfHours = halfHours;
	}

	public String getPayRate() {
		return payRate;
	}

	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPreDeductins() {
		return preDeductins;
	}

	public void setPreDeductins(String preDeductins) {
		this.preDeductins = preDeductins;
	}

	public String getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(String finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getPostDeductions() {
		return postDeductions;
	}

	public void setPostDeductions(String postDeductions) {
		this.postDeductions = postDeductions;
	}

	public String getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(String reimbursement) {
		this.reimbursement = reimbursement;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getPayrollStartDateStr() {
		if(payrollStartDate != null){
			payrollStartDateStr = ConversionUtil.getDateAsString(payrollStartDate);
		}
		return payrollStartDateStr;
	}

	public String getPayrollEndDateStr() {
		if(payrollEndDate != null){
			payrollEndDateStr = ConversionUtil.getDateAsString(payrollEndDate);
		}
		return payrollEndDateStr;
	}

	
}
