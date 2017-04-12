package com.bis.sourcelead.entity;

import java.io.Serializable;

public class CandidatesHotListDummy implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String candidateName;
	
	private String candidateEmail;
	
	private String candidatePhnNo;
	
	private String projectName;
	
	private String projectEndDate;
	
	private String [] projectNameList;
	
	private String [] projectEndDateList;

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getCandidatePhnNo() {
		return candidatePhnNo;
	}

	public void setCandidatePhnNo(String candidatePhnNo) {
		this.candidatePhnNo = candidatePhnNo;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String[] getProjectNameList() {
		return projectNameList;
	}

	public void setProjectNameList(String[] projectNameList) {
		this.projectNameList = projectNameList;
	}

	public String[] getProjectEndDateList() {
		return projectEndDateList;
	}

	public void setProjectEndDateList(String[] projectEndDateList) {
		this.projectEndDateList = projectEndDateList;
	}
	
}
