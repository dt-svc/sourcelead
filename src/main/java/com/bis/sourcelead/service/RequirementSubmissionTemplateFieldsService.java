package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */


public interface RequirementSubmissionTemplateFieldsService {
	
	RequirementSubmissionTemplateFields storeRequirementSubmissionTemplateFields(RequirementSubmissionTemplateFields requirementSubmissionTemplateFields)throws Exception;

	RequirementSubmissionTemplateFields getSubmissionsTemplateFieldsByRequirementSubmissionsId(Long id);

	List<RequirementSubmissionTemplateFields> getSubmissionsTemplateFieldsByRequirementSubmissionsIdAndOrgLocationId(
			long parseLong, Long vendorLocationId);
}
