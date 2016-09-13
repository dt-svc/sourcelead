package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.SubmissionTemplate;

public interface SubmissionTemplateDao {

	public SubmissionTemplate addSubmissionTemplate(SubmissionTemplate submissionTemplate) throws Exception;
	
	List<SubmissionTemplate> getSubmissionTemplateByClientLocationIdAndOrgLocationId(Long clientLocationId,Long orgLocationId) throws Exception;
}
