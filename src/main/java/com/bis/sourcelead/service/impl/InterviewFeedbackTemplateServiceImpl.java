package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.InterviewFeedbackTemplateDao;
import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.service.InterviewFeedbackTemplateService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class InterviewFeedbackTemplateServiceImpl implements InterviewFeedbackTemplateService {
	
	@Autowired
	private InterviewFeedbackTemplateDao interviewFeedbackTemplateDao;

	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public InterviewFeedbackTemplate addInterviewFeedbackTemplate(InterviewFeedbackTemplate interviewFeedbackTemplate)
			throws Exception {
		interviewFeedbackTemplate.setUpdatedDate(commonUtil.currentDate());
		return interviewFeedbackTemplateDao.addInterviewFeedbackTemplate(interviewFeedbackTemplate);
	}


	@Override
	public InterviewFeedbackTemplate getInterviewFeedbackTemplateById(Long requirementInterviewid) throws Exception {
		return interviewFeedbackTemplateDao.getInterviewFeedbackTemplateById(requirementInterviewid);
	}


	@Override
	public InterviewFeedbackTemplate getInterviewFeedbackTemplateByTemplateIdAndRequirementInterviewId(Long templateId,Long requirementInterviewId) throws Exception {
		return interviewFeedbackTemplateDao.getInterviewFeedbackTemplateByTemplateIdAndRequirementInterviewId(templateId,requirementInterviewId);
	}

	
	@Override
	public List<InterviewFeedbackTemplate> getInterviewerAndRatingListByInterviewId(Long requirementInterviewId) throws Exception {
		return interviewFeedbackTemplateDao.getInterviewerAndRatingListByInterviewId(requirementInterviewId);
	}
}
