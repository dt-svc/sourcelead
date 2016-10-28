package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SubmissionXlsDao;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.service.SubmissionXlsService;

@Service
public class SubmissionXlsServiceImpl  implements SubmissionXlsService{

	@Autowired
	SubmissionXlsDao submissionXlsDao;

	@Override
	public SubmissionXls storeSubmissionXls(SubmissionXls submissionXls) throws Exception {
		return submissionXlsDao.storeSubmissionXls(submissionXls);
	}

	@Override
	public SubmissionXls getSubmissionXlsByUUID(String UUID) {
		return submissionXlsDao.getSubmissionXlsByUUID(UUID);
	}
}
