package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementInterviewerDao;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementInterviewerDaoImpl implements RequirementInterviewerDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewerDaoImpl.class);
	
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

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterviewer storeRequirementInterviewer(RequirementInterviewer interviewer) throws Exception {
		logger.debug("Adding RequirementInterviewer");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (interviewer != null) {
				session.saveOrUpdate(interviewer);
			}
			logger.debug("RequirementInterviewer Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_SUBMISSION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return interviewer;
	}

	@Override
	public List<RequirementInterviewer> getInterviwersListByInterviewId(Long requirementInterviewId) {
		logger.debug("In RequirementInterviewerDaoImpl class getInterviwersListByInterviewId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewer interviewer where interviewer.requirementInterview.id = :requirementInterviewId and interviewer.status = 1";
		List<RequirementInterviewer> requirementInterviewersList =  session.createQuery(query).setLong("requirementInterviewId", requirementInterviewId).setCacheable(true).list();
		return requirementInterviewersList;
	}
	
}
