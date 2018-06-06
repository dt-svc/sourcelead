package com.bis.sourcelead.dao.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
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
		return  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationID", orgLocationID).setString("toStage", submissionType).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId) {
		logger.debug("In RequirementSubmissionHistoryDaoImpl class getRequirementHistoryByLocationIdAndStage method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory requirementSubmissionHistory where requirementSubmissionHistory.submissionId.id = :submissionId";
		List submissionHistoryList = session.createQuery(query).setLong("submissionId", submissionId).list();
		
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
	     List<RequirementSubmissionHistory> requirementHistoryList = session.createQuery(query).setLong("candidateId", candidateId).setLong("orgLocationId", orgLocationId).setLong("requirementId", requirementId).setLong("submissionId", submissionId).list();
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
		return session.createQuery(query).setParameterList("submissionIds", submissionIds).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Integer getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, Date startDateUTC, Date endDateUTC,String isGlobalUser) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> requirementsCount = null;
		String globalAccessQuery = "";
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			globalAccessQuery = " and rsh.submissionId.orgLocations.org.id IN (select ol.org.id from OrgLocations ol where ol.id = :orgLocationId)";
		}else {
			globalAccessQuery = " and rsh.submissionId.orgLocations.id = :orgLocationId";
		}
		
		
		query ="select count(*) from RequirementSubmissionHistory rsh where rsh.createdBy =:userCode and rsh.toStage = :toStage "+globalAccessQuery+" "
				+ " and date(rsh.createdDate) BETWEEN :startDate and :endDate";
		
		requirementsCount =  session.createQuery(query.toString()).setString("userCode", user.getUserCode()).setString("toStage", submissionStage).setLong("orgLocationId", orgLocationId).setParameter("startDate", startDateUTC).setParameter("endDate", endDateUTC).list();
		Integer count= 0;
		if(requirementsCount != null && !requirementsCount.isEmpty()){
				count = requirementsCount.get(0).intValue();
		}
		return count;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndSubId(Long orgLocationID, Long submissionId)
	{
		logger.debug("In RequirementSubmissionHistoryDaoImpl class getRequirementHistoryByLocationIdAndSubId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory requirementSubmissionHistory where requirementSubmissionHistory.submissionId.id = :submissionId and requirementSubmissionHistory.submissionId.orgLocations.id = :orgLocationID";
		return  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationID", orgLocationID).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementSubmissionHistory addRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)
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
	@Transactional
	public UserDTO getSubmissionCountsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user,
			String submissionStage, String startDateUTC, String endDateUTC, String isGlobalUser,Long orgId) throws Exception {
		
		

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> requirementsCount = null;
		String globalAccessQuery = "";
		
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			globalAccessQuery = " AND OL.ORG_ID = O.ID AND OL.ORG_ID = :orgId";
		}else {
			globalAccessQuery = "  AND RS.ORG_LOCATION_ID = OL.ID AND RS.ORG_LOCATION_ID = :orgLocationId";
		}
		


		query ="SELECT "
   
				+ " SUM(CASE"
        + " WHEN UPPER(RSH.TO_STAGE) = 'SUBMISSION' THEN 1"
        + " ELSE 0"
   + "  END) AS userApplicantsCount,"
    + " SUM(CASE"
        + " WHEN UPPER(RSH.TO_STAGE) = 'SHORTLIST' THEN 1"
        + " ELSE 0"
    + " END) AS userShortlistedApplicantsCount,"
    + " SUM(CASE"
        + " WHEN UPPER(RSH.TO_STAGE) = 'SUBMIT_TO_CLIENT' THEN 1"
        + " ELSE 0"
   + "  END) AS userSubmittedApplicantsCount,"
    + " SUM(CASE"
        + " WHEN UPPER(RSH.TO_STAGE) = 'INTERVIEW' THEN 1"
        + " ELSE 0"
    + " END) AS userInterviewedApplicantsCount,"
    + " SUM(CASE"
        + " WHEN UPPER(RSH.TO_STAGE) = 'OFFERMADE' THEN 1"
        + " ELSE 0"
    + " END) AS userOfferedApplicantsCount,"
    + " SUM(CASE"
        + " WHEN UPPER(RSH.TO_STAGE) = 'REJECTED' THEN 1"
       + "  ELSE 0"
    + " END) AS userRejectedApplicantsCount,"
    + " SUM(CASE"
        + " WHEN UPPER(RSH.TO_STAGE) = 'BACKOUT' THEN 1"
        + " ELSE 0"
    + " END) AS userApplicantsBackOutCount"
+ " FROM"
    + " REQUIREMENT_SUBMISSION_HISTORY RSH,"
    + " REQUIREMENT_SUBMISSION RS,"
    + " ORG_LOCATIONS OL,"
    + " ORG O"
+ " WHERE"
   + "  RSH.CREATED_BY = :userCode"
        + " AND RSH.REQUIREMENT_SUBMISSION_ID = RS.ID AND  "
   +"(RSH.CREATED_DATE BETWEEN '"+startDateUTC+"' AND '"+endDateUTC+"')"
   +globalAccessQuery;
		
		Query queryString = session.createSQLQuery(query);
		if(StringUtils.isNotBlank(isGlobalUser) && isGlobalUser.equalsIgnoreCase(Constants.YES)) {
			queryString.setParameter("userCode", user.getUserCode()).setParameter("orgId", orgId);
		}else {
			queryString.setParameter("userCode", user.getUserCode()).setParameter("orgLocationId", orgLocationId);
		}
		
		queryString.setResultTransformer(Transformers.aliasToBean(UserDTO.class));
		UserDTO userDTO = (UserDTO) queryString.uniqueResult();
		return userDTO;
    

		

		
	
		
/*		query ="select count(*) from RequirementSubmissionHistory rsh where rsh.createdBy =:userCode and rsh.toStage = :toStage "+globalAccessQuery+" "
				+ " and date(rsh.createdDate) BETWEEN :startDate and :endDate";
		
		requirementsCount =  session.createQuery(query.toString()).setString("userCode", user.getUserCode()).setString("toStage", submissionStage).setLong("orgLocationId", orgLocationId).setParameter("startDate", startDateUTC).setParameter("endDate", endDateUTC).list();
		Integer count= 0;
		if(requirementsCount != null && !requirementsCount.isEmpty()){
				count = requirementsCount.get(0).intValue();
		}
		return count;*/
		
	
		
	}

}
