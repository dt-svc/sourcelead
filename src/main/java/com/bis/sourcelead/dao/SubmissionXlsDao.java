package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.SubmissionXls;

public interface SubmissionXlsDao {

	public SubmissionXls storeSubmissionXls(SubmissionXls submissionXls) throws Exception;
	
	SubmissionXls getSubmissionXlsByUUID(String UUID);
}
