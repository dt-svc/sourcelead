package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InterviewFeedbackTemplateDao;
import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class InterviewFeedbackTemplateDaoImpl implements InterviewFeedbackTemplateDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	private static final Logger logger = LoggerFactory.getLogger(InterviewFeedbackTemplateDaoImpl.class);

	@Override
	@Transactional
	public InterviewFeedbackTemplate addInterviewFeedbackTemplate(InterviewFeedbackTemplate interviewFeedbackTemplate) throws Exception {
		logger.debug("Interview FeedbackTemlate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (interviewFeedbackTemplate != null) {
				session.saveOrUpdate(interviewFeedbackTemplate);
			}
			logger.debug("SubmissionTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"INTERVIEWFEEDBACK_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return interviewFeedbackTemplate;
	}

	@Override
	@Transactional
	public InterviewFeedbackTemplate getInterviewFeedbackTemplateById(Long requirementInterviewid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<InterviewFeedbackTemplate> interviewFeedbackTemplate = new ArrayList<InterviewFeedbackTemplate>();
		logger.debug("getInterviewFeedbackTemplateById start");
		interviewFeedbackTemplate = session.createQuery("from InterviewFeedbackTemplate where requirementInterviewerId =:requirementInterviewid" ).setLong( "requirementInterviewid", requirementInterviewid ).list();
		logger.debug("getInterviewFeedbackTemplateById end");
		if (interviewFeedbackTemplate.size() > 0) {
			return interviewFeedbackTemplate.get(0);
		} else {
			return null;
		}
	}
	@Override
	@Transactional
	public InterviewFeedbackTemplate getInterviewFeedbackTemplateByTemplateIdAndRequirementInterviewId(Long templateId,Long requirementInterviewId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<InterviewFeedbackTemplate> interviewFeedbackTemplate = new ArrayList<InterviewFeedbackTemplate>();
		logger.debug("getInterviewFeedbackTemplateById start");
		interviewFeedbackTemplate = session.createQuery("from InterviewFeedbackTemplate ift where ift.submissionTemplateId.id =:templateId and ift.requirementInterviewerId.id =:requirementInterviewId" ).setLong( "templateId", templateId ).setLong( "requirementInterviewId", requirementInterviewId ).list();
		logger.debug("getInterviewFeedbackTemplateById end");
		if (interviewFeedbackTemplate.size() > 0) {
			return interviewFeedbackTemplate.get(0);
		} else {
			return null;
		}
	}

}
