package com.bis.sourcelead.reportsDTO;

public class ClientReportsDTO {

	private String clientName;
	private int contractsCount;
	private String frequency;
	private String fromDate;
	private String toDate;
	private int releasedJobs;
	private int noOfContacts;
	public String getClientName()
	{
		return clientName;
	}
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	public int getContractsCount()
	{
		return contractsCount;
	}
	public void setContractsCount(int contractsCount)
	{
		this.contractsCount = contractsCount;
	}
	public String getFrequency()
	{
		return frequency;
	}
	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
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
	public int getReleasedJobs()
	{
		return releasedJobs;
	}
	public void setReleasedJobs(int releasedJobs)
	{
		this.releasedJobs = releasedJobs;
	}
	public int getNoOfContacts()
	{
		return noOfContacts;
	}
	public void setNoOfContacts(int noOfContacts)
	{
		this.noOfContacts = noOfContacts;
	}
}
