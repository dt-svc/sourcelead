package com.bis.sourcelead.payroll.dto;

import java.util.List;

public class AssociateBankErrorData {
	
	private List<AssociateBankDataDTO> errorData;
	
	private int insertedCount;

	public List<AssociateBankDataDTO> getErrorData()
	{
		return errorData;
	}

	public int getInsertedCount()
	{
		return insertedCount;
	}

	public void setErrorData(List<AssociateBankDataDTO> errorData)
	{
		this.errorData = errorData;
	}

	public void setInsertedCount(int insertedCount)
	{
		this.insertedCount = insertedCount;
	}
	
	
}
