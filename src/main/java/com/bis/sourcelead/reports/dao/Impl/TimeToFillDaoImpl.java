package com.bis.sourcelead.reports.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.reports.dao.TimeToFillDao;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class TimeToFillDaoImpl implements TimeToFillDao{

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
	CommonUtil commonUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(TimeToFillDaoImpl.class);

	@Override
	@Transactional
	public List<RequirementSubmissionHistory> getRequirementHistoryByJobIdsAndStage(List<Long> jobIdList,
			Date[] searchDates, String stage) throws Exception {
		
		logger.debug("In TimeToFillDaoImpl class getRequirementHistoryByJobIdsAndStage method");
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rh left join fetch rh.submissionId left join fetch rh.submissionId.requirement where rh.submissionId.requirement.id IN (:jobIds) and rh.toStage = :toStage "
				+ " and (rh.createdDate BETWEEN :startDate and :endDate)";
		return  session.createQuery(query).setParameterList("jobIds", jobIdList).setString("toStage", stage).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).list();
		
	}
	
	@Override
	@Transactional
	public List<RequirementSubmissionHistory>  getRequirementHistoryByJobIdsAndStageForTimeToFill(List<Long> jobIdList, Date[] searchDates) throws Exception {
		
		logger.debug("In TimeToFillDaoImpl class getRequirementHistoryByJobIdsAndStage method");
		List<RequirementSubmissionHistory> reqHistoryList = new ArrayList<RequirementSubmissionHistory>();
		 Session session = this.sessionFactory.getCurrentSession();
		 String  fromDateStr =commonUtil.elkDateFormatter(searchDates[0]);
	 	 String  toDateStr  = commonUtil.elkDateFormatter(searchDates[1]);
		 StringBuilder resultQuery = new StringBuilder("");	
	     
		 if(jobIdList.size()>0) {
	     resultQuery.append("SELECT ");
	     resultQuery.append(" RH.* ");
	     resultQuery.append(" FROM ");
	     resultQuery.append(" REQUIREMENT_SUBMISSION_HISTORY RH, REQUIREMENT_SUBMISSION RS  ");
	     resultQuery.append(" WHERE ");
	     resultQuery.append(" RH.REQUIREMENT_SUBMISSION_ID = RS.ID ");
	     resultQuery.append(" AND RH.TO_STAGE IN ('OFFERACCEPTED')");
	     resultQuery.append(" AND RS.REQUIREMENT_ID IN (:jobIds) ");
	     resultQuery.append(" AND DATE_FORMAT(RS.CREATED_DATE, '%Y-%m-%d') BETWEEN '"+fromDateStr+"' AND '"+toDateStr+"' ");
	     return  session.createSQLQuery(resultQuery.toString()).addEntity(RequirementSubmissionHistory.class).setParameterList("jobIds", jobIdList).list();
		 }else { // if  jobIdList is empty returning an empty list to the service skipping query call to database.
			 return reqHistoryList;
		 }
	}

	
	@Override
	@Transactional
	public RequirementSubmissionHistory getRequirementHistoryBySubmissionIdsAndStage(Long submissionId, String stage)
			throws Exception {
		
		logger.debug("In TimeToFillDaoImpl class getRequirementHistoryBySubmissionIdsAndStage method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementSubmissionHistory rh where rh.submissionId.id = :submissionId and rh.toStage = :toStage ";
		List submissionHistoryList = session.createQuery(query).setLong("submissionId", submissionId).setString("toStage", stage).list();
		
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		if(submissionHistoryList != null && !submissionHistoryList.isEmpty())
		{
			requirementSubmissionHistory = (RequirementSubmissionHistory) submissionHistoryList.get(0);
		}
		return requirementSubmissionHistory;
	
	}
}
