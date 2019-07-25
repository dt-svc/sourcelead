package com.bis.sourcelead.dto;

import java.io.Serializable;

import org.json.JSONObject;

public class MobileServiceApplyJobDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String submissionDetails;
	
	JSONObject submissionJsonObject;

	public String getSubmissionDetails() {
		return submissionDetails;
	}

	public void setSubmissionDetails(String submissionDetails) {
		this.submissionDetails = submissionDetails;
	}

	public JSONObject getSubmissionJsonObject() {
		return submissionJsonObject;
	}

	public void setSubmissionJsonObject(JSONObject submissionJsonObject) {
		this.submissionJsonObject = submissionJsonObject;
	}
	
	
	
}
