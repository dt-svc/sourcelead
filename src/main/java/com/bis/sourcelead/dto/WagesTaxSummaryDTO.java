package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public class WagesTaxSummaryDTO extends SearchDTO implements Serializable{

	private Long id;
	private OrgLocations orgLocationId;
	private String employeeId;
	private String employeeName;
	private Date checkDate;
	private String checkNo;
	private float gross;
	private float fwt;
	private float ssec;
	private float med;
	private float swt;
	private float sdiSui;
	private float local;
	private float deds;
	private float net;
	private Date fromDate;
	private Date 	toDate;
	private String batchNumber;
	private String status;
	private String ssnNumber;
	private User userId;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String fromDateStr;
	private String toDateStr;
	private String checkDateStr;
	private String sortColumn;
	private String sortOrder;
	
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
	public String getSsnNumber() {
		return ssnNumber;
	}
	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
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
	public String getCheckDateStr() {
		return checkDateStr;
	}
	public void setCheckDateStr(String checkDateStr) {
		this.checkDateStr = checkDateStr;
	}
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	
	
}
