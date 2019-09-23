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
import com.bis.sourcelead.dao.RequirementInterviewDao;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewer;
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

	@Autowired
	RequirementSubmissionDao requirementSubmissionDao;
	
	
	@Autowired
	RequirementDao requirementDao;
	
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
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"REQUIREMENT_INTERVIEW"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementInterview;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterview getRequirementInterviewById(String requirementInterviewId) {
			logger.debug("In RequirementInterview getRequirementInterviewById method");
			Session session = this.sessionFactory.getCurrentSession();
			String query = " from RequirementInterview ri left join fetch ri.submissionId sub left join fetch sub.requirement where ri.id = :requirementInterviewId" ;
			List<RequirementInterview> interviewsList =session.createQuery(query).setLong("requirementInterviewId", Long.parseLong(requirementInterviewId)).list(); 
			RequirementInterview interview = null;
			if(interviewsList != null && !interviewsList.isEmpty()){
				interview = interviewsList.get(0);
			}
			return interview;
			
			
			
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterview getRequirementInterviewByIdAndSubmissionIdAndOrgLocationsId(Long requirementInterviewId,Long submissionId, Long orgLocationsId) {
		logger.debug("In RequirementInterview getRequirementInterviewById method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementInterview ri where ri.id = :requirementInterviewId and ri.submissionId.id = :submissionId and ri.submissionId.orgLocations.id = :orgLocationsId" ;
		List<RequirementInterview> interviewsList =session.createQuery(query).setLong("requirementInterviewId", requirementInterviewId).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list(); 
		RequirementInterview interview = null;
		if(interviewsList != null && !interviewsList.isEmpty()){
			interview = interviewsList.iterator().next();
		}
		return interview;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewDaoImpl class getRequirementInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterview interview where (interview.submissionId.id = :submissionId and interview.submissionId.orgLocations.id = :orgLocationsId)  OR (interview.submissionId.sourceSubmission.id = :submissionId and interview.submissionId.vendorLocationId.id = :orgLocationsId) ";
		List<RequirementInterview> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		
		return interviewsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementInterview> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewDaoImpl class getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterview interview where interview.submissionId.id = :submissionId and interview.submissionId.orgLocations.id = :orgLocationsId ";
		List<RequirementInterview> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		
		return interviewsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public List<RequirementInterview> getCustomerInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId,Long orgLocationsId,Long requirementId) throws Exception {
		List<RequirementInterview> interviewsList = null;
		Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
		if(requirement.getParentRequirement()!=null){
		logger.debug("In RequirementInterviewDaoImpl class getCustomerInterviewsBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		//String query = "from RequirementInterview interview where interview.submissionId.id IN(Select rs.id from RequirementSubmission rs where rs.sourceSubmission.id = :submissionId and rs.requirement.id = :requirementId and rs.vendorLocationId.id = :orgLocationsId ) and interview.notificationToSpoc='Y' ";
		//interviewsList =  session.createQuery(query).setLong("requirementId", requirement.getParentRequirement().getId()).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		}
		return interviewsList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterview> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery = "";
		String namedParameters = "";
		
		Map<String, Object> params = new HashMap<String,Object>();
		
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			subQuery = "and (ri.submissionId.requirement.managerUserId.id =:userId OR ri.createdBy =:userCode)";
			params.put("userId",userId);
			params.put("userCode",userCode);
			//subQuery = "and ri.submissionId.requirement.managerUserId.id =:userId";
			//namedParameters = ".setLong('userId', userId)";
			//params.put("userId",userId);
			params.put("orgLocationsId",orgLocationsId);
		}else{
			
			params.put("orgLocationsId",orgLocationsId);
		}
		
		String query = "";
        if(dateRange != null && "today".equals(dateRange)){
            query ="from RequirementInterview ri where ri.submissionId.orgLocations = :orgLocationsId and ri.submissionId.submissionStatus = 'INTERVIEW' and ri.submissionId.status = 1 and DATE(ri.dateOfInterview) = CURDATE() and ri.endTime >= NOW() "+subQuery;
        }else if(dateRange != null && "previous".equals(dateRange)){
            query ="from RequirementInterview ri where ri.submissionId.orgLocations = :orgLocationsId and ri.submissionId.submissionStatus = 'INTERVIEW' and ri.submissionId.status = 1 and DATE(ri.dateOfInterview) <= CURDATE() and ri.endTime < NOW()"+subQuery;
        }else if(dateRange != null && "upcoming".equals(dateRange)){
        	query ="from RequirementInterview ri where ri.submissionId.orgLocations = :orgLocationsId and ri.submissionId.submissionStatus = 'INTERVIEW' and ri.submissionId.status = 1 and DATE(ri.dateOfInterview) > CURDATE()"+subQuery;
        }else{
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	java.util.Date date = dateFormat.parse(dateRange);
        	java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
        	query ="from RequirementInterview ri where ri.submissionId.orgLocations = :orgLocationsId and ri.submissionId.submissionStatus = 'INTERVIEW' and ri.submissionId.status = 1 and DATE(ri.dateOfInterview) = :sqlStartDate"+subQuery;
        	params.put("sqlStartDate",sqlStartDate);
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
		
        //CAST('10:00:00' AS TIME) BETWEEN start_time AND end_time
        
		//String query = "from RequirementInterview ri where DATE(ri.dateOfInterview) = CURDATE()";
		//List<RequirementInterview> requirementInterviewList = session.createQuery(query).setLong("orgLocationsId", orgLocationsId).list();
		//return requirementInterviewList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterview> getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId(Long submissionId,
			Long orgLocationsId) throws Exception {
		logger.debug("In RequirementInterviewDaoImpl class getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementInterview interview where (interview.submissionId.id = :submissionId and interview.submissionId.orgLocations.id = :orgLocationsId)  OR (interview.submissionId.sourceSubmission.id = :submissionId and interview.submissionId.vendorLocationId.id = :orgLocationsId) ";
		List<RequirementInterview> interviewsHistoryList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).list();
		
		return interviewsHistoryList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Integer getInterviewCountByCandidateId(Long candidateId, String submissionStatus, Date startDate,
			Date endDate) throws Exception {
		
		 Session session = this.sessionFactory.getCurrentSession();
	     
		 String query = "select count(*) from RequirementInterview interview where interview.submissionId.candidate.id = :candidateId and "
		 		+ "interview.submissionId.submissionStatus = :submissionStatus and (interview.createdDate BETWEEN :startDate and :endDate))";
	     
	     List<Long> interviewCount = session.createQuery(query).setLong("candidateId", candidateId)
	    		   .setString("submissionStatus", submissionStatus)
	    		   .setDate("startDate", startDate)
				   .setDate("endDate", endDate).list();
		 Integer count= 0;
		 
		 if(interviewCount != null && !interviewCount.isEmpty()){
				count = interviewCount.get(0).intValue();
		 }
		return count;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterview> getRequirementInterviewsBySubmissionAndOrgLocId(List<Long> submissionsList,
			Long locationId, Date startUTCDate, Date endUTCDate) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementInterview interview where interview.submissionId.id IN(:submissionsList) and interview.startTime >= :startDateUTC and interview.startTime <= :endDateUTC)" ;
		List<RequirementInterview> interviewList =session.createQuery(query).setParameter("startDateUTC", startUTCDate).setParameter("endDateUTC", endUTCDate).setParameterList("submissionsList", submissionsList).list(); 
		return interviewList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterview getinterviewBySubmissionId(Long submissionId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementInterview interview where interview.submissionId.id = :submissionId and interview.id=(SELECT MAX(ri.id) FROM RequirementInterview ri where ri.submissionId.id = :submissionId)" ;
		List<RequirementInterview> interviewsList =session.createQuery(query).setLong("submissionId", submissionId).list(); 
		RequirementInterview interview = null;
		if(interviewsList != null && !interviewsList.isEmpty()){
			interview = interviewsList.iterator().next();
		}
		return interview;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public RequirementInterview getLatestInterviewBySubmissionId(Long submissionId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " from RequirementInterview interview where interview.submissionId.id = :submissionId and interview.startTime=(SELECT MAX(ri.startTime) FROM RequirementInterview ri where ri.submissionId.id = :submissionId)" ;
		List<RequirementInterview> interviewsList =session.createQuery(query).setLong("submissionId", submissionId).list(); 
		RequirementInterview interview = null;
		if(interviewsList != null && !interviewsList.isEmpty()){
			interview = interviewsList.iterator().next();
		}
		return interview;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Date> getInterviewDatesForCalendarEvent(Long userId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " select interview.startTime from RequirementInterview interview where "
				+ " interview.id IN (select interviewer.requirementInterview.id from RequirementInterviewer interviewer where interviewer.interviewerUser.id = :userId) "
				+ " and interview.submissionId.orgLocations.id = :orgLocationId " ;
		List<Date> interviewDates =session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list(); 
		return interviewDates;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Date> getInterviewDatesForInterviewrsInterviewSlots(List<Long> interivewerIds, Long orgLocationId)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = " select interview.startTime from RequirementInterview interview where "
				+ " interview.id IN (select interviewer.requirementInterview.id from RequirementInterviewer interviewer where interviewer.interviewerUser.id IN (:interivewerIds) ) "
				+ " and interview.submissionId.orgLocations.id = :orgLocationId " ;
		List<Date> interviewDates =session.createQuery(query).setParameterList("interivewerIds", interivewerIds).setLong("orgLocationId", orgLocationId).list(); 
		return interviewDates;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Date> getInterviewDatesForCandidateInterviewSlots(List<Long> applicantSubmissionIds) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		String query = " select interview.startTime from RequirementInterview interview where interview.submissionId.id IN (:applicantSubmissionIds) " ;
		List<Date> interviewDates =session.createQuery(query).setParameterList("applicantSubmissionIds", applicantSubmissionIds).list(); 
		return interviewDates;
	}

	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterview> getAllInterviewListByCurrentDate(String currentTime, String triggerTime) {
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from RequirementInterview ri left join fetch ri.submissionId submission left join fetch  submission.orgLocations where ri.status = 1 and ri.startTime > :date and ri.startTime < :triggerTime";
		List<RequirementInterview> requirementInterviewList = session.createQuery(query).setString("date",currentTime).setString("triggerTime",triggerTime).list();
		return requirementInterviewList;
	}*/
	
}
