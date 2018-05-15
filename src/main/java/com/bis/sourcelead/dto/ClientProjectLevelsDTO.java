package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ClientProjectLevelsDTO implements Serializable {
  
	private static final long serialVersionUID = 1L; 
	
	private String endClientchecked;
	
	private String levelValue;
	
	private String clientOrgLocId;
	
	private String levelClient;

	
	
	
	public String getEndClientchecked() {
		return endClientchecked;
	}

	public void setEndClientchecked(String endClientchecked) {
		this.endClientchecked = endClientchecked;
	}

	public String getLevelValue() {
		return levelValue;
	}

	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}

	public String getClientOrgLocId() {
		return clientOrgLocId;
	}

	public void setClientOrgLocId(String clientOrgLocId) {
		this.clientOrgLocId = clientOrgLocId;
	}

	public String getLevelClient() {
		return levelClient;
	}

	public void setLevelClient(String levelClient) {
		this.levelClient = levelClient;
	}
}
