package com.bis.sourcelead.reportsDTO;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;

public class ProjectReportDTO extends SearchDTO implements Serializable{ 

	private String projectName;
	private String projectStatus;
	private String[] associateStatus;
	private String projectStartDate;
	private String projectEndDate;
	private String projectMembers;
	private String memberStartDate;
	private String memberEndDate;
	private String memberStatus;
	private String type;
	private String projectClientNames;
	private List<Long> projectIds;
	private List<Long> projectManagers;
	private List<Long> projectClients;
	private String projectManagerNames;
	private String projectSeqNo;
	private boolean refreshFlag;
	
	
	public boolean isRefreshFlag() {
		return refreshFlag;
	}
	public void setRefreshFlag(boolean refreshFlag) {
		this.refreshFlag = refreshFlag;
	}
	public String[] getAssociateStatus() {
		return associateStatus;
	}
	public void setAssociateStatus(String[] associateStatus) {
		this.associateStatus = associateStatus;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	public String getProjectEndDate() {
		return projectEndDate;
	}
	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	public String getProjectMembers() {
		return projectMembers;
	}
	public void setProjectMembers(String projectMembers) {
		this.projectMembers = projectMembers;
	}
	public String getMemberStartDate() {
		return memberStartDate;
	}
	public void setMemberStartDate(String memberStartDate) {
		this.memberStartDate = memberStartDate;
	}
	public String getMemberEndDate() {
		return memberEndDate;
	}
	public void setMemberEndDate(String memberEndDate) {
		this.memberEndDate = memberEndDate;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProjectClientNames() {
		return projectClientNames;
	}
	public void setProjectClientNames(String projectClientNames) {
		this.projectClientNames = projectClientNames;
	}
	public List<Long> getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}
	public List<Long> getProjectManagers() {
		return projectManagers;
	}
	public void setProjectManagers(List<Long> projectManagers) {
		this.projectManagers = projectManagers;
	}
	public List<Long> getProjectClients() {
		return projectClients;
	}
	public void setProjectClients(List<Long> projectClients) {
		this.projectClients = projectClients;
	}
	public String getProjectManagerNames() {
		return projectManagerNames;
	}
	public void setProjectManagerNames(String projectManagerNames) {
		this.projectManagerNames = projectManagerNames;
	}
	public String getProjectSeqNo() {
		return projectSeqNo;
	}
	public void setProjectSeqNo(String projectSeqNo) {
		this.projectSeqNo = projectSeqNo;
	}
	 
	
	
}
