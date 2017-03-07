package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */
public interface RequirementSubmissionTemplateFieldsDao {
	
	RequirementSubmissionTemplateFields storeRequirementSubmissionTemplateFields(RequirementSubmissionTemplateFields requirementSubmissionTemplateFields)throws Exception;

	RequirementSubmissionTemplateFields getSubmissionsTemplateFieldsByRequirementSubmissionsId(
			Long requirementSubmissionsId);

	List<RequirementSubmissionTemplateFields> getSubmissionsTemplateFieldsByRequirementSubmissionsIdAndOrgLocationId(
			long submissionId, Long orgLocationId);
	
}
