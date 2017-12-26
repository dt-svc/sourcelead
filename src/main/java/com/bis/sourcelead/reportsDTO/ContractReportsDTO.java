package com.bis.sourcelead.reportsDTO;

public class ContractReportsDTO {

	private String clientName;
	private int contractsCount;
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
}
