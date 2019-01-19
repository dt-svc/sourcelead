package com.bis.sourcelead.reportsDTO;

public class ContractReportsDTO {

	private String clientName;
	private int contractsCount;
	private String frequency;
	private String fromDate;
	private String toDate;
	private int msaCount;
	private int sowCount;
	private int nsaCount;
	private int ndaCount;
	private long contactUser;
	private String templateSubmissionStatus;
	
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
	public int getMsaCount()
	{
		return msaCount;
	}
	public void setMsaCount(int msaCount)
	{
		this.msaCount = msaCount;
	}
	public int getSowCount()
	{
		return sowCount;
	}
	public void setSowCount(int sowCount)
	{
		this.sowCount = sowCount;
	}
	public int getNsaCount()
	{
		return nsaCount;
	}
	public void setNsaCount(int nsaCount)
	{
		this.nsaCount = nsaCount;
	}
	public int getNdaCount()
	{
		return ndaCount;
	}
	public void setNdaCount(int ndaCount)
	{
		this.ndaCount = ndaCount;
	}
	public long getContactUser()
	{
		return contactUser;
	}
	public void setContactUser(long contactUser)
	{
		this.contactUser = contactUser;
	}
	public String getTemplateSubmissionStatus() {
		return templateSubmissionStatus;
	}
	public void setTemplateSubmissionStatus(String templateSubmissionStatus) {
		this.templateSubmissionStatus = templateSubmissionStatus;
	}
	
}
