package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.RequirementSubmissionHistoryDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
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
	public Boolean isInCoolingOffPeriodByEmailAndLocationsId(String email, Long orgLocationId, String reqCoolingOffPeriod, Long requirementId) throws ParseException{
		 Session session = this.sessionFactory.getCurrentSession();
		
		 // This Code is for  getting the sql enddate based on range
	     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Calendar c = Calendar.getInstance();
	     c.setTime(new Date()); // Now use today date.
	     Integer dateRange = Integer.parseInt(reqCoolingOffPeriod);
         c.add(Calendar.DATE, -dateRange); // Subtracting dateRange days
         String updatedDate     = dateFormat.format(c.getTime());
         Date parsed = dateFormat.parse(updatedDate);
         java.sql.Timestamp coolingOffEndDate = new java.sql.Timestamp(parsed.getTime());
		
		
		String query = "FROM RequirementSubmissionHistory rsh left join fetch rsh.submissionId subId left join fetch subId.candidate reqCan  WHERE reqCan.id IN(SELECT id FROM Candidate can WHERE can.email =:email AND can.orgLocationId.id =:orgLocationId) AND (rsh.createdDate BETWEEN :coolingOffEndDate AND NOW()) ORDER BY rsh.createdDate DESC";
				
		
		List<RequirementSubmissionHistory> requirementHistoryList = session.createQuery(query).setString("email", email).setLong("orgLocationId", orgLocationId).setDate("coolingOffEndDate", coolingOffEndDate).setCacheable(true).list();
	  
		boolean isInCoolingOffPeriod = false;
		if (requirementHistoryList != null && !requirementHistoryList.isEmpty() ) {
			RequirementSubmissionHistory requirementSubmissionHistory = requirementHistoryList.get(0);
			if(coolingOffEndDate.before(requirementSubmissionHistory.getCreatedDate())){
				isInCoolingOffPeriod = true;
			}
			
         }
         return isInCoolingOffPeriod;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rsh where rsh.submissionId.id IN (:submissionIds)";
		return session.createQuery(query).setParameterList("submissionIds", submissionIds).setCacheable(true).list();
	}

}
