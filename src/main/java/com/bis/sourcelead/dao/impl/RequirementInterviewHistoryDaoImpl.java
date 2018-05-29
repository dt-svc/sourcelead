package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.dao.RequirementInterviewHistoryDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
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
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_INTERVIEW_HISTORY"));
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
		String query = "from RequirementInterviewHistory rih left join fetch rih.interviewId  where rih.submissionId.id = :submissionId and rih.orgLocationId.id = :orgLocationsId ORDER BY rih.createdDate DESC";
		List<RequirementInterviewHistory> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		
		return interviewsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getAllInterviewListByCurrentDate(String currentTime, String triggerTime) {
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from RequirementInterviewHistory rih left join fetch rih.interviewId ri left join fetch rih.submissionId submission left join fetch  submission.orgLocations where rih.interviewId.id IN(select interview.id from RequirementInterview interview where interview.status = 1 and interview.startTime > :date and interview.startTime < :triggerTime) and rih.status = 1";
		List<RequirementInterviewHistory> requirementInterviewList = session.createQuery(query).setString("date",currentTime).setString("triggerTime",triggerTime).list();
		return requirementInterviewList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode,Date startUTCDate,Date endUTCDate)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery = "";
		String namedParameters = "";
		
		Map<String, Object> params = new HashMap<String,Object>();
		
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			subQuery = "and ((rih.submissionId.requirement.managerUserId.id =:userId OR rih.createdBy =:userCode) OR (rih.interviewId.id IN (select interviewer.requirementInterview.id from RequirementInterviewer interviewer where interviewer.interviewerUser.id = :userId  ))) ";
			params.put("userId",userId);
			params.put("userCode",userCode);
			params.put("orgLocationsId",orgLocationsId);
		}else{
			
			params.put("orgLocationsId",orgLocationsId);
		}
		
		String subQueryCommon = "from RequirementInterviewHistory rih left join fetch rih.interviewId where rih.orgLocationId = :orgLocationsId and rih.submissionId.status = 1 and";
		
		String query = "";
        if(dateRange != null && "today".equals(dateRange)){
           // query = subQueryCommon+ "(DATE(rih.interviewId.dateOfInterview) = CURDATE()) and (rih.interviewId.endTime IS NULL OR rih.interviewId.endTime >= NOW()) "+ subQuery;
        	query = subQueryCommon+ "( rih.interviewId.startTime >= :startDate and rih.interviewId.startTime <= :endDate) "+ subQuery;
        	params.put("startDate",startUTCDate);
        	params.put("endDate",endUTCDate);
        }else if(dateRange != null && "previous".equals(dateRange)){
           // query = subQueryCommon+ " (DATE(rih.interviewId.dateOfInterview) <= CURDATE()) and (rih.interviewId.endTime IS NULL OR rih.interviewId.endTime < NOW())"+ subQuery;
        	query = subQueryCommon+ "( rih.interviewId.startTime < :startDate ) "+ subQuery;
        	params.put("startDate",startUTCDate);
        }else if(dateRange != null && "upcoming".equals(dateRange)){
        	query = subQueryCommon+ " (DATE(rih.interviewId.startTime) > :startDate)"+ subQuery;
        	params.put("startDate",startUTCDate);
        }else if(endUTCDate == null){
        	query = subQueryCommon+ " (DATE(rih.interviewId.startTime) >= :startDate)"+ subQuery;
        	params.put("startDate",startUTCDate);
        }else{
        	//query = subQueryCommon+ " (rih.interviewId.dateOfInterview between :startDate and :endDate)"; 
        	query = subQueryCommon+ "( rih.interviewId.startTime >= :startDate and rih.interviewId.startTime <= :endDate) "+subQuery;
        	params.put("startDate",startUTCDate);
        	params.put("endDate",endUTCDate);
        }
        
        Query queryExe = session.createQuery(query);
        for (String str : queryExe.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationsId") || str.equalsIgnoreCase("userId")){
				queryExe.setLong(str, (long) params.get( str ));
			}
			else{
				queryExe.setParameter( str, params.get( str ) );
			}
        }
		
		return queryExe.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getClientInterviewsHistoryBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewHistoryDaoImpl class getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewHistory rih where rih.submissionId.id = :submissionId and rih.orgLocationId.id = :orgLocationsId ORDER BY rih.createdDate DESC";
		List<RequirementInterviewHistory> interviewsHistoryList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		
		return interviewsHistoryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getRequirementInternalAndIsClientInterviewsCountBySubmissionIdAndOrgLocationsId(Long submissionId,
			Long orgLocationsId) throws Exception {
		     Session session = this.sessionFactory.getCurrentSession();
			
			String query ="select count(*) from RequirementInterviewHistory rih where rih.submissionId.id = :submissionId and rih.orgLocationId.id = :orgLocationsId";
			List<Long> interviewsCount = session.createQuery(query.toString()).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
				Integer count = 0;
				if(interviewsCount != null && !interviewsCount.isEmpty()){
						count = interviewsCount.get(0).intValue();
				}
				return count;}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getAllProfessinalInterviews(String dateRange,Long candidateId,String currentTime, Date startUTCDate, Date endUTCDate,Timezone timezone)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "";
		
		Map<String, Object> params = new HashMap<String,Object>();
		
		subQuery = "and (rih.submissionId.candidate.id =:candidateId)";
		params.put("candidateId",candidateId);
		
		String query = "from RequirementInterviewHistory rih left join fetch rih.interviewId where rih.submissionId.status = 1  and ( rih.interviewId.startTime >= :startDate and rih.interviewId.startTime <= :endDate) "+subQuery;
    	params.put("startDate",startUTCDate);
    	params.put("endDate",endUTCDate);
     
        Query queryExe = session.createQuery(query);
        for (String str : queryExe.getNamedParameters())
        {
			if(str.equalsIgnoreCase("candidateId")){
				queryExe.setLong(str, (long) params.get( str ));
			}
			else{
				queryExe.setParameter( str, params.get( str ) );
			}
        }
		
		return queryExe.list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getAllRecruiterSubmittedInterviews(String dateRange,Long userId,String currentTime, Date startUTCDate, Date endUTCDate,Timezone timezone) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String subQuery = "and (rih.submissionId.recruiterUserId.id =:recruiterUserId)";
		String query = "from RequirementInterviewHistory rih where rih.submissionId.status = 1 and ( rih.interviewId.dateOfInterview >= :startDate and rih.interviewId.dateOfInterview <= :endDate) "+subQuery;
    	List<RequirementInterviewHistory> interviewsHistoryList =  session.createQuery(query).setLong("recruiterUserId", userId).setParameter("startDate",startUTCDate).setParameter("endDate",endUTCDate).list();
		
		return interviewsHistoryList;
	}
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getCrowdSorurcingInterviews(Long submissionId) throws Exception {
		
		logger.debug("In RequirementInterviewHistoryDaoImpl class getCrowdSorurcingInterviews method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewHistory rih left join fetch rih.interviewId  where rih.submissionId.id = :submissionId  ORDER BY rih.createdDate DESC";
		List<RequirementInterviewHistory> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).list();
		
		return interviewsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterviewHistory getRequirementInterviewHistoryByInterviewId(Long interviewId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterviewHistory rih  where rih.interviewId.id = :interviewId ";
		List<RequirementInterviewHistory> requirementInterviewHistory = session.createQuery(query).setLong("interviewId", interviewId).list();
		RequirementInterviewHistory interviewHistory = null;
		RequirementInterview interview = null;
		if(requirementInterviewHistory != null && !requirementInterviewHistory.isEmpty()){
			interviewHistory = requirementInterviewHistory.iterator().next();
		}
		return interviewHistory;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getCountOfAllInterviewAssignedToLoginUser(Long orgLocationsId, String userType, Long userId, String userCode) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery = "";
		Map<String, Object> params = new HashMap<String,Object>();
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			subQuery = "and ((rih.submissionId.requirement.managerUserId.id =:userId OR rih.createdBy =:userCode) OR (rih.interviewId.id IN (select interviewer.requirementInterview.id from RequirementInterviewer interviewer where interviewer.interviewerUser.id = :userId  ))) ";
			params.put("userId",userId);
			params.put("userCode",userCode);
			params.put("orgLocationsId",orgLocationsId);
		}else{
			params.put("orgLocationsId",orgLocationsId);
		}
		String subQueryCommon = "select count(*) from RequirementInterviewHistory rih  where rih.orgLocationId = :orgLocationsId and rih.submissionId.status = 1 ";
		
		String query = subQueryCommon + subQuery;
		
        Query queryExe = session.createQuery(query);
        for (String str : queryExe.getNamedParameters())
        {
			if(str.equalsIgnoreCase("orgLocationsId") || str.equalsIgnoreCase("userId")){
				queryExe.setLong(str, (long) params.get( str ));
			}
			else{
				queryExe.setParameter( str, params.get( str ) );
			}
        }
		return (Long)queryExe.uniqueResult();
	}
}
