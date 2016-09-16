package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.SubmissionTemplate;

public interface SubmissionTemplateService {

	public SubmissionTemplate addSubmissionTemplate(SubmissionTemplate submissionTemplate) throws Exception;
	
	List<SubmissionTemplate> getSubmissionTemplateByClientLocationIdAndOrgLocationId(Long clientLocationId,Long orgLocationId) throws Exception;
}
