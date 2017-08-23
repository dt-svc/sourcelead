package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectQuestionnaireDao;
import com.bis.sourcelead.entity.ProjectQuestionnaire;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class ProjectQuestionnaireDaoImpl implements ProjectQuestionnaireDao{

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
    
	private static final Logger logger = LoggerFactory.getLogger(ProjectQuestionnaireDaoImpl.class);

	@Override
	@Transactional
	public ProjectQuestionnaire saveProjectQuestionnaire(ProjectQuestionnaire projectQuestionnaire) throws Exception {
	
		logger.debug("In ProjectQuestionnaire Save method");
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.saveOrUpdate(projectQuestionnaire);
		}catch(Exception exception){
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PROJECT_QUESTIONNAIRE"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
		return projectQuestionnaire;
	}

}
