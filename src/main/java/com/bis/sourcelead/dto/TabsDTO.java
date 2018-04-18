package com.bis.sourcelead.dto;

import java.io.Serializable;

public class TabsDTO implements Serializable{

private static final long serialVersionUID = 1L;
	
	private long id;
	
	private String tabName;
	
	private String tabIcon;
	
	private String isTabSubscribed;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabIcon() {
		return tabIcon;
	}

	public void setTabIcon(String tabIcon) {
		this.tabIcon = tabIcon;
	}

	public String getIsTabSubscribed() {
		return isTabSubscribed;
	}

	public void setIsTabSubscribed(String isTabSubscribed) {
		this.isTabSubscribed = isTabSubscribed;
	}
}
