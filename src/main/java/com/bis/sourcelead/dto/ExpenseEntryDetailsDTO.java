package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ExpenseEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String currencyCode;
	
	List<ProjectMembersDTO> projectMembersList;
	
	List<ProjectsDTO> projectsList;
	
	List<String> paymentModes;
	
	List<String> currencyCodeList;
	
	List<String> paidByType;

	private String loginCurrencyCode;

	public List<ProjectMembersDTO> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembersDTO> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}

	public List<ProjectsDTO> getProjectsList()
	{
		return projectsList;
	}

	public void setProjectsList(List<ProjectsDTO> projectsList)
	{
		this.projectsList = projectsList;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public List<String> getPaymentModes() {
		return paymentModes;
	}

	public void setPaymentModes(List<String> paymentModes) {
		this.paymentModes = paymentModes;
	}

	public List<String> getCurrencyCodeList() {
		return currencyCodeList;
	}

	public void setCurrencyCodeList(List<String> currencyCodeList) {
		this.currencyCodeList = currencyCodeList;
	}

	public List<String> getPaidByType() {
		return paidByType;
	}

	public void setPaidByType(List<String> paidByType) {
		this.paidByType = paidByType;
	}

	public String getLoginCurrencyCode() {
		return loginCurrencyCode;
	}

	public void setLoginCurrencyCode(String loginCurrencyCode) {
		this.loginCurrencyCode = loginCurrencyCode;
	}

	
}
