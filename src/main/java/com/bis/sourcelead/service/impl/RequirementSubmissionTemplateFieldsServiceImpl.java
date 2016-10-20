package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.RequirementSubmissionTemplateFieldsDao;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */


@Service
public class RequirementSubmissionTemplateFieldsServiceImpl implements RequirementSubmissionTemplateFieldsService{
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionTemplateFieldsServiceImpl.class);
	
	@Autowired
	private RequirementSubmissionTemplateFieldsDao requirementSubmissionTemplateFieldsDao;

	@Override
	public RequirementSubmissionTemplateFields storeRequirementSubmissionTemplateFields(
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields) throws Exception {
		logger.debug("In RequirementSubmissionTemplateFieldsServiceImpl class saveProject method...");
		return requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(requirementSubmissionTemplateFields);
	}
}
