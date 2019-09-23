package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.SubmissionTemplate;

public interface SubmissionTemplateService {

	public SubmissionTemplate addSubmissionTemplate(SubmissionTemplate submissionTemplate) throws Exception;
	
	List<SubmissionTemplate> getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(Long clientLocationId,Long orgLocationId,String tempalteType) throws Exception;
	
	SubmissionTemplate getSubmissionTemplateById(Long Id) throws Exception;
	
	SubmissionTemplate getDefaultSubmissionTemplateByOrgLocationId(Long orgLocationId) throws Exception;
	
	SubmissionTemplate getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(Long clientLocationId,Long orgLocationId) throws Exception;
	
	SubmissionTemplate getSubmissionTemplateByTemplateName(Long clientLocationId,Long orgLocationId,String templateName) throws Exception;
	
	SubmissionTemplate getSubmissionTemplateByLabelName(Long templateId,String labelName) throws Exception;
	
	List<SubmissionTemplate> getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(Long orgLocationId,String templateType) throws Exception;
	
	SubmissionTemplate getSubmissionTemplateByTemplateNameByOrg(Long orgLocationId,String templateName) throws Exception;
	
	List<SubmissionTemplate> getInterviewFeedbackTemplatesByOrgLocationId(Long orgLocationId) throws Exception;
	
	SubmissionTemplate getSubmissionTemplateByUUID(String uuid) throws Exception;
	
}
