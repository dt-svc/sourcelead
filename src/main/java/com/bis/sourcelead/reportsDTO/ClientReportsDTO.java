package com.bis.sourcelead.reportsDTO;

public class ClientReportsDTO {

	private String clientName;
	private String clientContactName;
	private int contractsCount;
	private String frequency;
	private String fromDate;
	private String toDate;
	private int releasedJobs;
	private int noOfContacts;
	private int submissionCount;
	private int hiredCount;
	private int avgTTF;
	private int totalContracts;
	private int totalBillingAmount;
	private long orgRelationId;
	private long orgSpocId;
	public String getClientName()
	{
		return clientName;
	}
	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}
	public String getClientContactName()
	{
		return clientContactName;
	}
	public void setClientContactName(String clientContactName)
	{
		this.clientContactName = clientContactName;
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
	public int getSubmissionCount()
	{
		return submissionCount;
	}
	public void setSubmissionCount(int submissionCount)
	{
		this.submissionCount = submissionCount;
	}
	public int getHiredCount()
	{
		return hiredCount;
	}
	public void setHiredCount(int hiredCount)
	{
		this.hiredCount = hiredCount;
	}
	public int getAvgTTF()
	{
		return avgTTF;
	}
	public void setAvgTTF(int avgTTF)
	{
		this.avgTTF = avgTTF;
	}
	public int getTotalContracts()
	{
		return totalContracts;
	}
	public void setTotalContracts(int totalContracts)
	{
		this.totalContracts = totalContracts;
	}
	public int getTotalBillingAmount()
	{
		return totalBillingAmount;
	}
	public void setTotalBillingAmount(int totalBillingAmount)
	{
		this.totalBillingAmount = totalBillingAmount;
	}
	public long getOrgRelationId()
	{
		return orgRelationId;
	}
	public void setOrgRelationId(long orgRelationId)
	{
		this.orgRelationId = orgRelationId;
	}
	public long getOrgSpocId()
	{
		return orgSpocId;
	}
	public void setOrgSpocId(long orgSpocId)
	{
		this.orgSpocId = orgSpocId;
	}
}
