package com.bis.sourcelead.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class RequirementSubmissionHistoryDaoImpl implements RequirementSubmissionHistoryDao{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionHistoryDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory isInCoolingOffPeriodByEmailAndLocationsId(String email, Long orgLocationId, String reqCoolingOffPeriod, Long requirementId) throws ParseException{
		 Session session = this.sessionFactory.getCurrentSession();
		
		
         String query = " FROM RequirementSubmissionHistory rsh where rsh.submissionId.candidate.email =:email AND rsh.submissionId.orgLocations.id =:orgLocationId  ORDER BY rsh.createdDate asc";
		
		List<RequirementSubmissionHistory> requirementHistoryList = session.createQuery(query).setString("email", email).setLong("orgLocationId", orgLocationId).list();
	  
		
		RequirementSubmissionHistory requirementSubmissionHistory1 = null;
		if (requirementHistoryList != null && !requirementHistoryList.isEmpty() ) {
			RequirementSubmissionHistory requirementSubmissionHistory = requirementHistoryList.get(0);
			
	         String query1 = " FROM RequirementSubmissionHistory rsh where rsh.submissionId.id =:submissionId ORDER BY rsh.createdDate DESC";
	 		 List<RequirementSubmissionHistory> requirementHistoryList1 = session.createQuery(query1).setLong("submissionId", requirementSubmissionHistory.getSubmissionId().getId()).list();
	 		
	 		 if (requirementHistoryList1 != null && !requirementHistoryList1.isEmpty() ) {
				requirementSubmissionHistory1 = requirementHistoryList1.get(0);
				
	 		}
         }
		
         return requirementSubmissionHistory1;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh where rsh.submissionId.id IN (:submissionIds)";
		return session.createQuery(query).setParameterList("submissionIds", submissionIds).setCacheable(true).list();
	}

	@Override
	public Long getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, String timePeriod) throws Exception {
		
		 String query ="select count(*)from RequirementSubmissionHistory rsh where rsh.orgLocations.id =:orgLocationId and rsh.createdBy =:userCode ";
		Session session = this.sessionFactory.getCurrentSession();
	    long count = ((Long)session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setString("userCode",user.getUserCode()).uniqueResult()).intValue();
    
       return count;
	}

}
