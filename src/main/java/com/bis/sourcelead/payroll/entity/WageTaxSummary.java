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
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "WAGE_TAX_SUMMARY")
public class WageTaxSummary implements Serializable {
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

	@Column(name = "CHECK_DATE")
	private Date checkDate;

	@Column(name = "CHECK_NO")
	private String checkNo;

	@Column(name = "GROSS")
	private float gross;

	@Column(name = "FWT")
	private float fwt;

	@Column(name = "SSEC")
	private float ssec;

	@Column(name = "MED")
	private float med;

	@Column(name = "SWT")
	private float swt;

	@Column(name = "SDI_SUI")
	private float sdiSui;

	@Column(name = "LOCAL")
	private float local;

	@Column(name = "DEDS")
	private float deds;

	@Column(name = "NET")
	private float net;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date 	toDate;

	@Column(name = "BATCH_NUMBER")
	private String batchNumber;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "SSN_NUMBER")
	private String ssnNumber;
	
	@Column(name = "VERSION")
	private Integer version;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User userId;

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
	
	@Transient
	@JsonProperty
	private String checkDateStr;

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

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckNo() {
		return checkNo;
	}

	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}

	public float getGross() {
		return gross;
	}

	public void setGross(float gross) {
		this.gross = gross;
	}

	public float getFwt() {
		return fwt;
	}

	public void setFwt(float fwt) {
		this.fwt = fwt;
	}

	public float getSsec() {
		return ssec;
	}

	public void setSsec(float ssec) {
		this.ssec = ssec;
	}

	public float getMed() {
		return med;
	}

	public void setMed(float med) {
		this.med = med;
	}

	public float getSwt() {
		return swt;
	}

	public void setSwt(float swt) {
		this.swt = swt;
	}

	public float getSdiSui() {
		return sdiSui;
	}

	public void setSdiSui(float sdiSui) {
		this.sdiSui = sdiSui;
	}

	public float getLocal() {
		return local;
	}

	public void setLocal(float local) {
		this.local = local;
	}

	public float getDeds() {
		return deds;
	}

	public void setDeds(float deds) {
		this.deds = deds;
	}

	public float getNet() {
		return net;
	}

	public void setNet(float net) {
		this.net = net;
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

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public String getFromDateStr()
	{
		return fromDateStr;
	}

	public void setFromDateStr(String fromDateStr)
	{
		this.fromDateStr = fromDateStr;
	}

	public String getToDateStr()
	{
		return toDateStr;
	}

	public void setToDateStr(String toDateStr)
	{
		this.toDateStr = toDateStr;
	}

	public String getCheckDateStr() {
		return checkDateStr;
	}

	public void setCheckDateStr(String checkDateStr) {
		this.checkDateStr = checkDateStr;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
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
