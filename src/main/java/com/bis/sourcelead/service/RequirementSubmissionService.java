package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.RequirementSubmission;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */


public interface RequirementSubmissionService {
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;
}
