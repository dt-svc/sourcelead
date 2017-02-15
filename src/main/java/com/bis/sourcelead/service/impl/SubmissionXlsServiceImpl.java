package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SubmissionXlsDao;
import com.bis.sourcelead.entity.SubmissionXls;
import com.bis.sourcelead.service.SubmissionXlsService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class SubmissionXlsServiceImpl  implements SubmissionXlsService{

	@Autowired
	SubmissionXlsDao submissionXlsDao;
	
	@Autowired
	 private CommonUtil commonUtil;

	@Override
	public SubmissionXls storeSubmissionXls(SubmissionXls submissionXls) throws Exception {
		submissionXls.setUpdatedDate(commonUtil.currentDate());
		return submissionXlsDao.storeSubmissionXls(submissionXls);
	}

	@Override
	public SubmissionXls getSubmissionXlsByUUID(String UUID) {
		return submissionXlsDao.getSubmissionXlsByUUID(UUID);
	}
}
