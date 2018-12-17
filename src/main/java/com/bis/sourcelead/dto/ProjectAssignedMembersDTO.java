package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ProjectAssignedMembersDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer maxTsRowNum;
	
	private List<ProjectMembersDTO> projectMembers;

	private String projectClientUUID;
	
	private String type;
	
	public List<ProjectMembersDTO> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(List<ProjectMembersDTO> projectMembers) {
		this.projectMembers = projectMembers;
	}

	public Integer getMaxTsRowNum() {
		return maxTsRowNum;
	}

	public void setMaxTsRowNum(Integer maxTsRowNum) {
		this.maxTsRowNum = maxTsRowNum;
	}

	public String getProjectClientUUID() {
		return projectClientUUID;
	}

	public void setProjectClientUUID(String projectClientUUID) {
		this.projectClientUUID = projectClientUUID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
