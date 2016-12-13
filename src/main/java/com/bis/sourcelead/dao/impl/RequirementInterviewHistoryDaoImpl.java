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

import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementInterviewHistoryDao;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementInterviewHistoryDaoImpl implements RequirementInterviewHistoryDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	RequirementDao requirementDao;
	
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
	public RequirementInterviewHistory storeRequirementInterviewHistory(
			RequirementInterviewHistory requirementInterviewHistory) throws Exception {
		logger.debug("Adding RequirementInterviewHistory");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementInterviewHistory != null) {
				session.saveOrUpdate(requirementInterviewHistory);
			}
			logger.debug("RequirementInterviewHistory Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_SUBMISSION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementInterviewHistory;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterviewHistory getRequirementInterviewHistoryById(Long Id) {
			logger.debug("In RequirementInterviewHistory getRequirementInterviewHistoryById method");
			Session session = this.sessionFactory.getCurrentSession();
			return (RequirementInterviewHistory) session.get(RequirementInterviewHistory.class, Id);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewHistoryDaoImpl class getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewHistory rih where rih.submissionId.id = :submissionId and rih.orgLocationId.id = :orgLocationsId ";
		List<RequirementInterviewHistory> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		
		return interviewsList;
	}
	

	
}
