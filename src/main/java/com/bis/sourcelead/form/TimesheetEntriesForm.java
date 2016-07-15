package com.bis.sourcelead.form;

import org.springframework.web.multipart.MultipartFile;

public class TimesheetEntriesForm {
	
	
	private Integer id;
	
	private String[] submissionId;
	
	private MultipartFile file;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String[] getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(String[] submissionId) {
		this.submissionId = submissionId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}