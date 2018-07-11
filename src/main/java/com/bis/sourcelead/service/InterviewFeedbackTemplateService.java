package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.InterviewFeedbackTemplate;

public interface InterviewFeedbackTemplateService {

	
	public InterviewFeedbackTemplate addInterviewFeedbackTemplate(InterviewFeedbackTemplate interviewFeedbackTemplate) throws Exception;
	
	InterviewFeedbackTemplate getInterviewFeedbackTemplateById(Long requirementInterviewid) throws Exception;
	
	InterviewFeedbackTemplate getInterviewFeedbackTemplateByTemplateIdAndRequirementInterviewId(Long templateId,Long requirementInterviewId) throws Exception;
	
	List<InterviewFeedbackTemplate> getInterviewerAndRatingListByInterviewId(Long requirementInterviewId) throws Exception;
}
