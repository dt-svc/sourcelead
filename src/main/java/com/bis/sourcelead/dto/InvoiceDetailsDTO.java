package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;

public class InvoiceDetailsDTO extends SearchDTO implements Serializable {
	
	private Long id;
	private OrgLocations orgLocationId;
	private String sku;
	private String productService;
	private String empName;
	private String number;
	private String memoDescription;
	private Date date;
	private Date lastmodified;
	private Date dueDate;
	private String rate;
	private String qty;
	private String amount;
	private String arPaid;
	private String oldNew;
	private Date fromDate;
	private Date toDate;
	private String batchNumber;
	private String status;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	private String dateStr;
	private Long userId;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setOrgLocationId(OrgLocations orgLocationId)
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
	public Date getDate()
	{
		return date;
	}
	public void setDate(Date date)
	{
		this.date = date;
	}
	public Date getLastmodified()
	{
		return lastmodified;
	}
	public void setLastmodified(Date lastmodified)
	{
		this.lastmodified = lastmodified;
	}
	public Date getDueDate()
	{
		return dueDate;
	}
	public void setDueDate(Date dueDate)
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
	public Date getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}
	public Date getToDate()
	{
		return toDate;
	}
	public void setToDate(Date toDate)
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
	public Date getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(Date createdDate)
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
	public Date getUpdatedDate()
	{
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate)
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
	public Long getUserId()
	{
		return userId;
	}
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

}
