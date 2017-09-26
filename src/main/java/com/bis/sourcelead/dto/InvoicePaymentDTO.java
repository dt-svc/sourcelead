package com.bis.sourcelead.dto;

import java.util.List;

public class InvoicePaymentDTO {
	private String invoiceId;
	private String paymentDate;
	private String paymentMethod;
	private String referenceNumber;
	private String totalAmount;
	private String paymentAmount;
	private String customeAmount;
	private String paymentMemo;
	private List<FileInfo> fileInfo;
	
	public String getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	public String getPaymentDate()
	{
		return paymentDate;
	}
	
	public void setPaymentDate(String paymentDate)
	{
		this.paymentDate = paymentDate;
	}
	
	public String getPaymentMethod()
	{
		return paymentMethod;
	}
	
	public void setPaymentMethod(String paymentMethod)
	{
		this.paymentMethod = paymentMethod;
	}
	
	public String getReferenceNumber()
	{
		return referenceNumber;
	}
	
	public void setReferenceNumber(String referenceNumber)
	{
		this.referenceNumber = referenceNumber;
	}
	
	public String getPaymentAmount()
	{
		return paymentAmount;
	}
	
	public void setPaymentAmount(String paymentAmount)
	{
		this.paymentAmount = paymentAmount;
	}
	
	public String getCustomeAmount()
	{
		return customeAmount;
	}
	
	public void setCustomeAmount(String customeAmount)
	{
		this.customeAmount = customeAmount;
	}
	
	public String getPaymentMemo()
	{
		return paymentMemo;
	}
	
	public void setPaymentMemo(String paymentMemo)
	{
		this.paymentMemo = paymentMemo;
	}

	public List<FileInfo> getFileInfo()
	{
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo)
	{
		this.fileInfo = fileInfo;
	}

	public String getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount)
	{
		this.totalAmount = totalAmount;
	}
	
}
