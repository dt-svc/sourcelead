package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.SubmissionTemplateDao;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.service.SubmissionTemplateService;

@Service
public class SubmissionTemplateServiceImpl implements SubmissionTemplateService {
	
	@Autowired
	private SubmissionTemplateDao submissionTemplateDao;

	@Override
	public SubmissionTemplate addSubmissionTemplate(SubmissionTemplate submissionTemplate) throws Exception {
		return submissionTemplateDao.addSubmissionTemplate(submissionTemplate);
	}

}
