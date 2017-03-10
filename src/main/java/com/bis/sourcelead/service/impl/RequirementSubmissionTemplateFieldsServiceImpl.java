package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementSubmissionTemplateFieldsDao;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.util.CommonUtil;

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

	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public RequirementSubmissionTemplateFields storeRequirementSubmissionTemplateFields(
			RequirementSubmissionTemplateFields requirementSubmissionTemplateFields) throws Exception {
		logger.debug("In RequirementSubmissionTemplateFieldsServiceImpl class saveProject method...");
		requirementSubmissionTemplateFields.setUpdatedDate(commonUtil.currentDate());
		return requirementSubmissionTemplateFieldsDao.storeRequirementSubmissionTemplateFields(requirementSubmissionTemplateFields);
	}

	@Override
	public RequirementSubmissionTemplateFields getSubmissionsTemplateFieldsByRequirementSubmissionsId(Long requirementSubmissionsId) {
		return requirementSubmissionTemplateFieldsDao.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmissionsId);
	}

	@Override
	public List<RequirementSubmissionTemplateFields> getSubmissionsTemplateFieldsByRequirementSubmissionsIdAndOrgLocationId(
			long submissionId, Long vendorLocationId) {
		return requirementSubmissionTemplateFieldsDao.getSubmissionsTemplateFieldsByRequirementSubmissionsIdAndOrgLocationId(submissionId,vendorLocationId);
	}
}
