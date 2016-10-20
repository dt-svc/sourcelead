package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.service.RequirementSubmissionService;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */


@Service
public class RequirementSubmissionServiceImpl implements RequirementSubmissionService{
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionServiceImpl.class);
	
	@Autowired
	private RequirementSubmissionDao requirementSubmissionDao;
	

	@Override
	public RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission) throws Exception {
		logger.debug("In ProjectsServiceImpl class saveProject method...");
		return requirementSubmissionDao.storeRequirementSubmission(requirementSubmission);
	}

}
