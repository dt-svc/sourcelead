package com.bis.sourcelead.payroll.dto;

import java.util.List;

import com.bis.sourcelead.dto.IndAssociatePaymentAccountDTO;

public class AssociateBankErrorData {
	
	private List<AssociateBankDataDTO> errorData;
	
	private int insertedCount;
	
	private int count;
	
	private List<AssociatePfEsiDetailsDTO> pfErrorData;
	
	private List<IndAssociatePaymentAccountDTO> bdErrorData;

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

	public List<AssociatePfEsiDetailsDTO> getPfErrorData() {
		return pfErrorData;
	}

	public void setPfErrorData(List<AssociatePfEsiDetailsDTO> pfErrorData) {
		this.pfErrorData = pfErrorData;
	}

	public List<IndAssociatePaymentAccountDTO> getBdErrorData() {
		return bdErrorData;
	}

	public void setBdErrorData(List<IndAssociatePaymentAccountDTO> bdErrorData) {
		this.bdErrorData = bdErrorData;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
