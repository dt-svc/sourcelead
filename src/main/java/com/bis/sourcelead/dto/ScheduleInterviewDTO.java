package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ScheduleInterviewDTO implements Serializable {

	/**
	 * @author Mahaveer Manoj
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String candidateName;
	private String createdUser;
	private String isClientSubmission;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getIsClientSubmission() {
		return isClientSubmission;
	}

	public void setIsClientSubmission(String isClientSubmission) {
		this.isClientSubmission = isClientSubmission;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
