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

import com.bis.sourcelead.dao.RequirementInterviewDao;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementInterviewDaoImpl implements RequirementInterviewDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewDaoImpl.class);
	
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
	public RequirementInterview storeRequirementInterview(RequirementInterview requirementInterview) throws Exception {
		logger.debug("Adding RequirementInterview");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementInterview != null) {
				session.saveOrUpdate(requirementInterview);
			}
			logger.debug("RequirementInterview Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_SUBMISSION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementInterview;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterview getRequirementInterviewById(String requirementInterviewId) {
			logger.debug("In RequirementInterview getRequirementInterviewById method");
			Long requirementInterviewIdValue = Long.parseLong(requirementInterviewId);
			Session session = this.sessionFactory.getCurrentSession();
			return (RequirementInterview) session.get(RequirementInterview.class, requirementInterviewIdValue);
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewDaoImpl class getRequirementInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterview interview where interview.submissionId.id = :submissionId and interview.submissionId.orgLocations.id = :orgLocationsId ";
		List<RequirementInterview> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		
		return interviewsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementInterview> getCustomerInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId,Long orgLocationsId) {
		logger.debug("In RequirementInterviewDaoImpl class getRequirementInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterview interview where interview.submissionId.id = :submissionId and interview.submissionId.vendorLocationId.id = :orgLocationsId ";
		List<RequirementInterview> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		
		return interviewsList;
	}
	
}
