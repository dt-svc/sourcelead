package com.bis.sourcelead.dto;

public class OrgLocationStorageDetailDTO {
	private long id;
	private long size;
	private long resumes;
	private long invoices;
	private long sows;
	private long timesheets;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public long getSize()
	{
		return size;
	}
	
	public void setSize(long size)
	{
		this.size = size;
	}
	
	public long getResumes()
	{
		return resumes;
	}
	
	public void setResumes(long resumes)
	{
		this.resumes = resumes;
	}
	
	public long getInvoices()
	{
		return invoices;
	}
	
	public void setInvoices(long invoices)
	{
		this.invoices = invoices;
	}
	
	public long getSows()
	{
		return sows;
	}
	
	public void setSows(long sows)
	{
		this.sows = sows;
	}
	
	public long getTimesheets()
	{
		return timesheets;
	}
	
	public void setTimesheets(long timesheets)
	{
		this.timesheets = timesheets;
	}
}
