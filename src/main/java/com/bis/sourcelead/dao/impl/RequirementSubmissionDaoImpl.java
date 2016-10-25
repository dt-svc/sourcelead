package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class RequirementSubmissionDaoImpl implements RequirementSubmissionDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RequirementDao requirementDao;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission getRequirementSubmissionById(Long submissionId) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();	
		RequirementSubmission submission = session.load(RequirementSubmission.class, new Long(submissionId));
		logger.info("RequirementSubmission loaded successfully, RequirementSubmission details="+submission);
		return submission;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(Long orgLocationsId, Long requirementId, List<Long> existingApplicantsIds) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 List<RequirementSubmission> shortListCandidatesList;
		 if(existingApplicantsIds != null && !existingApplicantsIds.isEmpty()){
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.submissionStatus = 'SHORTLIST' and rs.id NOT IN (:existingApplicantsIds)";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setParameterList("existingApplicantsIds", existingApplicantsIds).setCacheable(true).list();
		 }else{
			 String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and rs.submissionStatus = 'SHORTLIST' ";
			 shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setCacheable(true).list();
		 }
	     return shortListCandidatesList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception {
		logger.debug("Adding RequirementSubmission");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementSubmission != null) {
				session.saveOrUpdate(requirementSubmission);
			}
			logger.debug("RequirementSubmission Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_SUBMISSION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementSubmission;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId,User user, OrgLocations loginOrgLocation) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		/*Requirement requirement  = requirementDao.getRequirementByRequirementId(requirementId);
		
		if(requirement.getManagerUserId().getId()==user.getId()){
			
			
		}*/
		
		String query = "from RequirementSubmission reqSub where reqSub.orgLocations.id =:loginOrgLocation and reqSub.requirement.id =:requirementId";
		return session.createQuery(query).setLong("loginOrgLocation", loginOrgLocation.getId()).setLong("requirementId", requirementId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(Long orgLocationsId,
			Long requirementId,String submissionStatus) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 
		 String submissionStages = "";
		 
		 if(submissionStatus.equalsIgnoreCase("SUBMISSION")){
			 submissionStages = "and rs.submissionStatus = 'SUBMISSION' ";
		 }
		 
		 else if(submissionStatus.equalsIgnoreCase("SHORTLIST")){
			 submissionStages = "and rs.submissionStatus <> 'SUBMISSION' and rs.submissionStatus <> 'SUBMIT_TO_CLIENT' ";
		 }
		 
	     String query = "from RequirementSubmission rs where rs.orgLocations.id = :orgLocationsId and rs.requirement.id = :requirementId and (rs.sourceSubmission.id IS NULL or rs.sourceSubmission.id = '') "+submissionStages+" ";
	     List<RequirementSubmission> shortListCandidatesList = session.createQuery(query).setLong( "orgLocationsId", orgLocationsId).setLong( "requirementId", requirementId).setCacheable(true).list();
	     return shortListCandidatesList;
	}
	
	
    
  }

