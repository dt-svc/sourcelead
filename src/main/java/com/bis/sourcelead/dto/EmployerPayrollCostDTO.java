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

public class EmployerPayrollCostDTO extends SearchDTO implements Serializable {

	private String employeeId;
	private String employeeName;
	private String ssn;
	private String totalHrs;
	private float grossWages;
	private float fica;
	private float futa;
	private float sui;
	private float erLocals;
	private float wcCost;
	private float adminFee;
	private float totalErCost;
	private Date fromDate;
	private Date toDate;
	private String batchNumber;
	private String status;
	private String fromDateStr;
	private String toDateStr;
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
	
	
	
}
