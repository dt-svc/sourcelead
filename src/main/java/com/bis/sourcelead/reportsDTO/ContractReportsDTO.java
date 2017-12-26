package com.bis.sourcelead.reportsDTO;

public class ContractReportsDTO {

	private String clientName;
	private int contractsCount;
	private String frequency;
	private String fromDate;
	private String toDate;
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
}
