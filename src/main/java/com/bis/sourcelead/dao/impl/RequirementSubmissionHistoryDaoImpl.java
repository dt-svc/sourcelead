package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;

@Repository
public class RequirementSubmissionHistoryDaoImpl implements RequirementSubmissionHistoryDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory updateRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		try{
			session.saveOrUpdate(requirementSubmissionHistory);
		}catch(Exception ex){
			logger.error(ExceptionUtils.getFullStackTrace(ex)); 
		}
		logger.info("updated requirement history..!!");
		return requirementSubmissionHistory;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType,Long submissionId) throws Exception {
		logger.debug("In RequirementSubmissionHistoryDaoImpl class getRequirementHistoryByLocationIdAndStage method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory requirementSubmissionHistory where requirementSubmissionHistory.submissionId.id = :submissionId and requirementSubmissionHistory.toStage = :toStage and requirementSubmissionHistory.submissionId.orgLocations.id = :orgLocationID ";
		return  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationID", orgLocationID).setString("toStage", submissionType).setCacheable(true).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId) {
		logger.debug("In RequirementSubmissionHistoryDaoImpl class getRequirementHistoryByLocationIdAndStage method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory requirementSubmissionHistory where requirementSubmissionHistory.submissionId.id = :submissionId";
		List submissionHistoryList = session.createQuery(query).setLong("submissionId", submissionId).setCacheable(true).list();
		
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		if(submissionHistoryList != null && !submissionHistoryList.isEmpty())
		{
			requirementSubmissionHistory = (RequirementSubmissionHistory) submissionHistoryList.get(0);
		}
		return requirementSubmissionHistory;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(
			Long candidateId, Long orgLocationId, Long requirementId, Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh where rsh.submissionId.id = :submissionId and rsh.submissionId.candidate.id =:candidateId and rsh.submissionId.requirement.id =:requirementId and rsh.submissionId.orgLocations =:orgLocationId ORDER BY rsh.createdDate DESC";
	     List<RequirementSubmissionHistory> requirementHistoryList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setLong("submissionId", submissionId).setCacheable(true).list();
	  return requirementHistoryList;
	}

}
