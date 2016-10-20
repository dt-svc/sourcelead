package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.RequirementSubmission;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */
public interface RequirementSubmissionDao {
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;
	
}
