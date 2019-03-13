package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;

public class InvoiceDetailsDTO extends SearchDTO implements Serializable {
	
	private String id;
	private String orgLocationId;
	private String sku;
	private String productService;
	private String empName;
	private String number;
	private String memoDescription;
	private String date;
	private String lastmodified;
	private String dueDate;
	private String rate;
	private String payRate;
	private String qty;
	private String amount;
	private String arPaid;
	private String oldNew;
	private String fromDate;
	private String toDate;
	private String batchNumber;
	private String status;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private String dateStr;
	private String userId;
	private String empId;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getProductService()
	{
		return productService;
	}
	public void setProductService(String productService)
	{
		this.productService = productService;
	}
	public String getEmpName()
	{
		return empName;
	}
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
	public String getMemoDescription()
	{
		return memoDescription;
	}
	public void setMemoDescription(String memoDescription)
	{
		this.memoDescription = memoDescription;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getLastmodified()
	{
		return lastmodified;
	}
	public void setLastmodified(String lastmodified)
	{
		this.lastmodified = lastmodified;
	}
	public String getDueDate()
	{
		return dueDate;
	}
	public void setDueDate(String dueDate)
	{
		this.dueDate = dueDate;
	}
	public String getRate()
	{
		return rate;
	}
	public void setRate(String rate)
	{
		this.rate = rate;
	}
	public String getPayRate()
	{
		return payRate;
	}
	public void setPayRate(String payRate)
	{
		this.payRate = payRate;
	}
	public String getQty()
	{
		return qty;
	}
	public void setQty(String qty)
	{
		this.qty = qty;
	}
	public String getAmount()
	{
		return amount;
	}
	public void setAmount(String amount)
	{
		this.amount = amount;
	}
	public String getArPaid()
	{
		return arPaid;
	}
	public void setArPaid(String arPaid)
	{
		this.arPaid = arPaid;
	}
	public String getOldNew()
	{
		return oldNew;
	}
	public void setOldNew(String oldNew)
	{
		this.oldNew = oldNew;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getBatchNumber()
	{
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber)
	{
		this.batchNumber = batchNumber;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	public String getUpdatedDate()
	{
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	public String getDateStr()
	{
		return dateStr;
	}
	public void setDateStr(String dateStr)
	{
		this.dateStr = dateStr;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	

}
