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
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "EMPLOYER_PAYROLL_COST")
public class EmployerPayrollCost implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;

	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@Column(name = "EMPLOYEE_NAME")
	private String employeeName;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "TOTAL_HRS")
	private String totalHrs;

	@Column(name = "GROSS_WAGES")
	private float grossWages;

	@Column(name = "FICA")
	private float fica;

	@Column(name = "FUTA")
	private float futa;

	@Column(name = "SUI")
	private float sui;

	@Column(name = "ER_LOCALS")
	private float erLocals;

	@Column(name = "WC_COST")
	private float wcCost;

	@Column(name = "ADMIN_FEE")
	private float adminFee;

	@Column(name = "TOTAL_ER_COST")
	private float totalErCost;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date 	toDate;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "VERSION")
	private int version;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Column(name = "UUID")
	private String uuid;
	
	@Transient
	@JsonProperty
	private String fromDateStr;
	
	@Transient
	@JsonProperty
	private String toDateStr;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getTotalHrs() {
		return totalHrs;
	}

	public void setTotalHrs(String totalHrs) {
		this.totalHrs = totalHrs;
	}

	public float getGrossWages() {
		return grossWages;
	}

	public void setGrossWages(float grossWages) {
		this.grossWages = grossWages;
	}

	public float getFica() {
		return fica;
	}

	public void setFica(float fica) {
		this.fica = fica;
	}

	public float getFuta() {
		return futa;
	}

	public void setFuta(float futa) {
		this.futa = futa;
	}

	public float getSui() {
		return sui;
	}

	public void setSui(float sui) {
		this.sui = sui;
	}

	public float getErLocals() {
		return erLocals;
	}

	public void setErLocals(float erLocals) {
		this.erLocals = erLocals;
	}

	public float getWcCost() {
		return wcCost;
	}

	public void setWcCost(float wcCost) {
		this.wcCost = wcCost;
	}

	public float getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(float adminFee) {
		this.adminFee = adminFee;
	}

	public float getTotalErCost() {
		return totalErCost;
	}

	public void setTotalErCost(float totalErCost) {
		this.totalErCost = totalErCost;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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

	public String getFromDateStr() {
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr) {
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr() {
		return toDateStr;
	}

	public void setToDateStr(String toDateStr) {
		this.toDateStr = toDateStr;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
}
