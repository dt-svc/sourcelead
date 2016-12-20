package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
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
		String query = "from RequirementInterviewHistory rih where rih.submissionId.id = :submissionId and rih.orgLocationId.id = :orgLocationsId ";
		List<RequirementInterviewHistory> interviewsList =  session.createQuery(query).setLong("submissionId", submissionId).setLong("orgLocationsId", orgLocationsId).setCacheable(true).list();
		
		return interviewsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getAllInterviewListByCurrentDate(String currentTime, String triggerTime) {
		Session session = this.sessionFactory.getCurrentSession();
		 String query ="from RequirementInterviewHistory rih left join fetch rih.interviewId ri left join fetch rih.submissionId submission left join fetch  submission.orgLocations where rih.interviewId.id IN(select interview.id from RequirementInterview interview where interview.status = 1 and interview.startTime > :date and interview.startTime < :triggerTime) and rih.status = 1";
		List<RequirementInterviewHistory> requirementInterviewList = session.createQuery(query).setString("date",currentTime).setString("triggerTime",triggerTime).setCacheable(true).list();
		return requirementInterviewList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementInterviewHistory> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		
		String subQuery = "";
		String namedParameters = "";
		
		Map<String, Object> params = new HashMap<String,Object>();
		
		if(!StringUtils.equalsIgnoreCase(userType, "ADMIN")){
			subQuery = "and (rih.submissionId.requirement.managerUserId.id =:userId OR rih.createdBy =:userCode)";
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
            query ="from RequirementInterviewHistory rih where rih.orgLocationId = :orgLocationsId and rih.submissionId.status = 1 and DATE(rih.interviewId.dateOfInterview) = CURDATE() and (rih.interviewId.endTime IS NULL OR rih.interviewId.endTime >= NOW()) "+subQuery;
        }else if(dateRange != null && "previous".equals(dateRange)){
            query ="from RequirementInterviewHistory rih where rih.orgLocationId = :orgLocationsId and rih.submissionId.status = 1 and DATE(rih.interviewId.dateOfInterview) <= CURDATE() and (rih.interviewId.endTime IS NULL OR rih.interviewId.endTime < NOW())"+subQuery;
        }else if(dateRange != null && "upcoming".equals(dateRange)){
        	query ="from RequirementInterviewHistory rih where rih.orgLocationId = :orgLocationsId and rih.submissionId.status = 1 and DATE(rih.interviewId.dateOfInterview) > CURDATE()"+subQuery;
        }else{
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	java.util.Date date = dateFormat.parse(dateRange);
        	java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
        	query ="from RequirementInterviewHistory rih where rih.orgLocationId = :orgLocationsId and rih.submissionId.status = 1 and DATE(rih.interviewId.dateOfInterview) = :sqlStartDate"+subQuery;
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
		
		return queryExe.setCacheable(true).list();
	}

	
}
