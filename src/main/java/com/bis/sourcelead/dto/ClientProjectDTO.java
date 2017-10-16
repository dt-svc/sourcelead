package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ClientProjectDTO implements Serializable{

	private static final long serialVersionUID = 1L; 
	
    private String level1Manager;
	
	private String level2Manager;
	
	private String clientOrgLocId;
	
	private String clientProject;

	public String getLevel1Manager() {
		return level1Manager;
	}

	public void setLevel1Manager(String level1Manager) {
		this.level1Manager = level1Manager;
	}

	public String getLevel2Manager() {
		return level2Manager;
	}

	public void setLevel2Manager(String level2Manager) {
		this.level2Manager = level2Manager;
	}

	public String getClientOrgLocId() {
		return clientOrgLocId;
	}

	public void setClientOrgLocId(String clientOrgLocId) {
		this.clientOrgLocId = clientOrgLocId;
	}

	public String getClientProject() {
		return clientProject;
	}

	public void setClientProject(String clientProject) {
		this.clientProject = clientProject;
	}

}
