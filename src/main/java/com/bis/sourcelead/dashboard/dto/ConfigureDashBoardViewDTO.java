package com.bis.sourcelead.dashboard.dto;

import java.math.BigInteger;


public class ConfigureDashBoardViewDTO {
	

	private BigInteger customModuleCofigId;

	private BigInteger subTabId;
	
	private BigInteger dashBoardConfiId;
	
	private String subTabName;
	
	private String displayTabName;
	
	private String jsonStringData;

	public BigInteger getCustomModuleCofigId()
	{
		return customModuleCofigId;
	}

	public BigInteger getSubTabId()
	{
		return subTabId;
	}

	public BigInteger getDashBoardConfiId()
	{
		return dashBoardConfiId;
	}

	public String getSubTabName()
	{
		return subTabName;
	}

	public String getDisplayTabName()
	{
		return displayTabName;
	}

	public void setCustomModuleCofigId(BigInteger customModuleCofigId)
	{
		this.customModuleCofigId = customModuleCofigId;
	}

	public void setSubTabId(BigInteger subTabId)
	{
		this.subTabId = subTabId;
	}

	public void setDashBoardConfiId(BigInteger dashBoardConfiId)
	{
		this.dashBoardConfiId = dashBoardConfiId;
	}

	public void setSubTabName(String subTabName)
	{
		this.subTabName = subTabName;
	}

	public void setDisplayTabName(String displayTabName)
	{
		this.displayTabName = displayTabName;
	}

	public String getJsonStringData() {
		return jsonStringData;
	}

	public void setJsonStringData(String jsonStringData) {
		this.jsonStringData = jsonStringData;
	}

	
	
}
