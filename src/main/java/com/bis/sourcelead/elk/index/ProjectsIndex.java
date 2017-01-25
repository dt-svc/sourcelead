package com.bis.sourcelead.elk.index;

import java.io.Serializable;
import java.util.Date;

public class ProjectsIndex implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long orgLocationId;
 	
	private String projectSequeneNumber;
	
    private String projectName;
    
    private String level1ManagerName;
    
    private String level2ManagerName;
 
    private String startDate;
    
    private String endDate;
    
	private String invoiceFrequency;
    
	private String projectStatus;
	
	private Long orgRelationId;
	
    private String status;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getProjectSequeneNumber() {
		return projectSequeneNumber;
	}

	public void setProjectSequeneNumber(String projectSequeneNumber) {
		this.projectSequeneNumber = projectSequeneNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLevel1ManagerName() {
		return level1ManagerName;
	}

	public void setLevel1ManagerName(String level1ManagerName) {
		this.level1ManagerName = level1ManagerName;
	}

	public String getLevel2ManagerName() {
		return level2ManagerName;
	}

	public void setLevel2ManagerName(String level2ManagerName) {
		this.level2ManagerName = level2ManagerName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInvoiceFrequency() {
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(String invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Long getOrgRelationId() {
		return orgRelationId;
	}

	public void setOrgRelationId(Long orgRelationId) {
		this.orgRelationId = orgRelationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
