package com.bis.sourcelead.dto;

import java.io.Serializable;

public class MobileServiceApplyJobDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String submissionDetails;

	private String submissionValues;

	public String getSubmissionDetails() {
		return submissionDetails;
	}

	public void setSubmissionDetails(String submissionDetails) {
		this.submissionDetails = submissionDetails;
	}

	public String getSubmissionValues() {
		return submissionValues;
	}

	public void setSubmissionValues(String submissionValues) {
		this.submissionValues = submissionValues;
	}

}
