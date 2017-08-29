package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectAssignedMembersDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer maxTsRowNum;
	
	private List<ProjectMembersDTO> projectMembers;

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

	
}
