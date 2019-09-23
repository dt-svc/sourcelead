package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class ClientProjectDTO implements Serializable{

	private static final long serialVersionUID = 1L; 
	
	@Required(desc="level1Manager")
    private String level1Manager;
	
	private String level2Manager;
	
	@Required(desc="clientOrgLocId")
	private String clientOrgLocId;
	
	@Required(desc="clientProject")
	private String clientProject;
	
    private String level1ExpenseApprover;
	
	private String level2ExpenseApprover;
	

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

	public String getLevel1ExpenseApprover() {
		return level1ExpenseApprover;
	}

	public void setLevel1ExpenseApprover(String level1ExpenseApprover) {
		this.level1ExpenseApprover = level1ExpenseApprover;
	}

	public String getLevel2ExpenseApprover() {
		return level2ExpenseApprover;
	}

	public void setLevel2ExpenseApprover(String level2ExpenseApprover) {
		this.level2ExpenseApprover = level2ExpenseApprover;
	}

}
