package com.bis.sourcelead.dao.impl;

import java.io.Writer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.InterviewFeedbackTemplateDao;
import com.bis.sourcelead.entity.InterviewFeedbackTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

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
		interviewFeedbackTemplate = session.createQuery("from InterviewFeedbackTemplate where requirementInterviewerId = :requirementInterviewid" ).setLong( "requirementInterviewid", requirementInterviewid ).list();
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
	
	@Override
	@Transactional
	public List<InterviewFeedbackTemplate> getInterviewerAndRatingListByInterviewId(Long requirementInterviewId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<InterviewFeedbackTemplate> interviewFeedbackTemplate = new ArrayList<InterviewFeedbackTemplate>();
		List<Object[]> objList = null;
		 objList  = session.createSQLQuery("select ift.INTERVIEW_TEMPLATE_JSON_STRING, u.FIRST_NAME, u.LAST_NAME from INTERVIEW_FEEDBACK_TEMPLATE ift, REQUIREMENT_INTERVIEWER ri, USER u  where ri.id=ift.REQUIREMENT_INTERVIEWER_ID and u.id=ri.INTERVIEWER_USER_ID and ri.INTERVIEW_ID =:requirementInterviewId ").setLong("requirementInterviewId", requirementInterviewId).list();
		if(objList != null && objList.size() > 0) {
			 for(Object[] obj : objList){
					InterviewFeedbackTemplate ifTemplate = new InterviewFeedbackTemplate();
					if(obj[0] != null) {
						ifTemplate.setInterviewerRating(obj[0].toString());
					}
					if(obj[2] != null && obj[2] != "") {
						ifTemplate.setInterviewerName(obj[1].toString()+" "+obj[2].toString());
					}else {
						ifTemplate.setInterviewerName(obj[1].toString());
					}
					interviewFeedbackTemplate.add(ifTemplate);
				}
		}
	
			
			List<Object[]> externalObjList  = session.createSQLQuery("select ift.INTERVIEW_TEMPLATE_JSON_STRING, ri.EXTERNAL_INTERVIEWER_NAME from INTERVIEW_FEEDBACK_TEMPLATE ift, REQUIREMENT_INTERVIEWER ri, REQUIREMENT_INTERVIEW iterview, REQUIREMENT_SUBMISSION submission  where ift.REQUIREMENT_INTERVIEWER_ID  = ri.id  and ri.INTERVIEW_ID = iterview.ID and iterview.SUBMISSION_ID = submission.ID  and ri.INTERVIEW_ID = :requirementInterviewId and ri.INTERVIEWER_USER_ID IS NULL ").setLong("requirementInterviewId", requirementInterviewId).list();
			if(externalObjList != null && externalObjList.size() > 0) {
				for(Object[] obj : externalObjList){
					InterviewFeedbackTemplate ifTemplate = new InterviewFeedbackTemplate();
					if(obj[0] != null) {
						ifTemplate.setInterviewerRating(obj[0].toString());
					}
					if(obj[1] != null) {
						ifTemplate.setInterviewerName(obj[1].toString());
					}
					interviewFeedbackTemplate.add(ifTemplate);
				}
			}
		
		return interviewFeedbackTemplate;
	}
	
}
