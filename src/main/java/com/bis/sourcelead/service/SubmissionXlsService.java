package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.SubmissionXls;

public interface SubmissionXlsService {

	public SubmissionXls storeSubmissionXls(SubmissionXls submissionXls) throws Exception;
	
	SubmissionXls getSubmissionXlsByUUID(String UUID);
}
