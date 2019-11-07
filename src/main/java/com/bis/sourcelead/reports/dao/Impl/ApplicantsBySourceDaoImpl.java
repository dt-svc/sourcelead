package com.bis.sourcelead.reports.dao.Impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dto.ApplicantBySourceCountDTO;
import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;
import com.bis.sourcelead.util.CommonUtil;

@Repository
public class ApplicantsBySourceDaoImpl implements ApplicantsBySourceDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicantsBySourceDaoImpl.class);

	@Override
	@Transactional
	public Integer getCrowdsourcingApplicantsCount(List<Long> jobIds, Date[] searchDates) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> submissionCount = null;
		if(searchDates != null) {
			 query ="select count(*) from RequirementSubmission rs where rs.requirement.id IN (:jobIds) "
					+ " and date(rs.requirement.createdDate) BETWEEN :startDate and :endDate and (rs.candidate.candidateType = 'Professional' OR (rs.vendorCandidateId.candidateType = 'Professional' AND rs.vendorCandidateId IS NOT NULL)) ";
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIds).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).list();
		}else {
			 query ="select count(*) from RequirementSubmission rs where (rs.candidate.candidateType = 'Professional' OR (rs.vendorCandidateId.candidateType = 'Professional' AND rs.vendorCandidateId IS NOT NULL)) and rs.requirement.id IN (:jobIds) ";
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIds).list();
		}
		
			Integer count= 0;
			if(submissionCount != null && !submissionCount.isEmpty()){
					count = submissionCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public Integer getRequirementsCount(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*)from Requirement requirement where requirement.orgLocationId.id = :orgLocationId";
		
		List<Long> jobCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
			Integer count = 0;
			if(jobCount != null && !jobCount.isEmpty()){
					count = jobCount.get(0).intValue();
			}
			return count;
	}

	@Override
	@Transactional
	public ApplicantBySourceCountDTO getCrowdsourcingApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ApplicantBySourceCountDTO internalApplicantBySourceCountDTO= null;
		String  fromDateStr =commonUtil.elkDateFormatter(searchDates[0]);
 	    String  toDateStr  = commonUtil.elkDateFormatter(searchDates[1]);
        StringBuilder resultQuery = new StringBuilder("");	
        resultQuery.append("SELECT ");
        resultQuery.append(" SUM(Q1.submissionCount) AS submissionCount, ");
        resultQuery.append(" SUM(Q1.shortlistCount) AS shortlistCount, ");
        resultQuery.append(" SUM(Q1.rejectedCount) AS rejectedCount, ");
        resultQuery.append(" SUM(Q1.submitToClientCount) AS submitToClientCount, ");
        resultQuery.append(" SUM(Q1.interviewCount) AS interviewCount, ");
        resultQuery.append(" SUM(Q1.clientInterviewCount) AS clientInterviewCount, ");
        resultQuery.append(" SUM(Q1.offermadeCount) AS offermadeCount, ");
        resultQuery.append(" SUM(Q1.clientOffermadeCount) AS clientOffermadeCount, ");
        resultQuery.append(" SUM(Q1.offerAcceptedCount) AS offerAcceptedCount, ");
        resultQuery.append(" SUM(Q1.clientOfferAcceptedCount) AS clientOfferAcceptedCount, ");
        resultQuery.append(" SUM(Q1.offerRejectedCount) AS offerRejectedCount, ");
        resultQuery.append(" SUM(Q1.backoutCount) AS backoutCount ");
       
         resultQuery.append("FROM  (SELECT RH.TO_STAGE, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMISSION' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  submissionCount, ");
		 resultQuery.append(" CASE  WHEN UPPER(RH.TO_STAGE) = 'SHORTLIST' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  shortlistCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'REJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS rejectedCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMIT_TO_CLIENT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS submitToClientCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS interviewCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientInterviewCount, "); 
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offermadeCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOffermadeCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID)  ELSE 0 END AS offerAcceptedCount,  ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOfferAcceptedCount, "); 
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERREJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offerRejectedCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'BACKOUT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0  END AS backoutCount  ");
		 resultQuery.append(" FROM  ");
		 resultQuery.append(" REQUIREMENT_SUBMISSION_HISTORY RH, ");
		 resultQuery.append(" REQUIREMENT_SUBMISSION RS, ");
		 resultQuery.append(" CANDIDATE C ");
		 resultQuery.append(" WHERE  ");
		 
		 resultQuery.append(" RH.REQUIREMENT_SUBMISSION_ID = RS.ID ");
		 resultQuery.append(" AND RS.REQUIREMENT_ID IN (:jobIds)");
		 resultQuery.append(" AND  ( (RS.CANDIDATE_ID= C.ID AND C.CANDIDATE_TYPE='Professional' ) OR (RS.VENDOR_CANDIDATE_ID=C.ID  AND C.CANDIDATE_TYPE='Professional' AND RS.VENDOR_CANDIDATE_ID IS NOT NULL))");
		 
		  if(searchDates != null) {
			  resultQuery.append(" AND  DATE_FORMAT(RS.CREATED_DATE, '%Y-%m-%d')  BETWEEN '"+fromDateStr+"' AND '"+toDateStr+"'");
			  resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
			}else {
				resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
			}
			 
		  Query queryString = session.createSQLQuery(resultQuery.toString()).setParameterList("jobIds", jobIdList);
		  internalApplicantBySourceCountDTO = (ApplicantBySourceCountDTO)queryString.setResultTransformer(Transformers.aliasToBean(ApplicantBySourceCountDTO.class)).uniqueResult();
		  return internalApplicantBySourceCountDTO;
		}
	
	/*@Override
	@Transactional
	public ApplicantBySourceCountDTO getCrowdsourcingApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> submissionCount = null;
		
		if(searchDates != null) {
			query ="select  count(*) from RequirementSubmissionHistory rh where ((rh.submissionId.candidate.id IN(select cand.id from Candidate cand where cand.candidateType = 'Professional')) OR (rh.submissionId.vendorCandidateId IS NOT NULL AND rh.submissionId.vendorCandidateId.id IN(select cand.id from Candidate cand where cand.candidateType = 'Professional'))) "
					+ " and  rh.submissionId.requirement.id IN (:jobIds) and rh.toStage = :stage and date(rh.submissionId.createdDate) BETWEEN :startDate and :endDate";
			
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).setString("stage", stage).list();
		}else {
			query ="select  count(*) from RequirementSubmissionHistory rh where ((rh.submissionId.candidate.id IN(select cand.id from Candidate cand where cand.candidateType = 'Professional')) OR (rh.submissionId.vendorCandidateId IS NOT NULL AND rh.submissionId.vendorCandidateId.id IN(select cand.id from Candidate cand where cand.candidateType = 'Professional'))) "
			 		+ " and rh.toStage = :stage and rh.submissionId.requirement.id IN (:jobIds) ";
			
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setString("stage", stage).list();
		}
		
			Integer count= 0;
			if(submissionCount != null && !submissionCount.isEmpty()){
					count = submissionCount.get(0).intValue();
			}
			return count;
	}*/

	@Override
	@Transactional
	public ApplicantBySourceCountDTO getVendorSourceApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
	
		Session session = this.sessionFactory.getCurrentSession();
		ApplicantBySourceCountDTO internalApplicantBySourceCountDTO= null;
		String  fromDateStr =commonUtil.elkDateFormatter(searchDates[0]);
 	    String  toDateStr  = commonUtil.elkDateFormatter(searchDates[1]);
        StringBuilder resultQuery = new StringBuilder("");	
        resultQuery.append("SELECT ");
        resultQuery.append(" SUM(Q1.submissionCount) AS submissionCount, ");
        resultQuery.append(" SUM(Q1.shortlistCount) AS shortlistCount, ");
        resultQuery.append(" SUM(Q1.rejectedCount) AS rejectedCount, ");
        resultQuery.append(" SUM(Q1.submitToClientCount) AS submitToClientCount, ");
        resultQuery.append(" SUM(Q1.interviewCount) AS interviewCount, ");
        resultQuery.append(" SUM(Q1.clientInterviewCount) AS clientInterviewCount, ");
        resultQuery.append(" SUM(Q1.offermadeCount) AS offermadeCount, ");
        resultQuery.append(" SUM(Q1.clientOffermadeCount) AS clientOffermadeCount, ");
        resultQuery.append(" SUM(Q1.offerAcceptedCount) AS offerAcceptedCount, ");
        resultQuery.append(" SUM(Q1.clientOfferAcceptedCount) AS clientOfferAcceptedCount, ");
        resultQuery.append(" SUM(Q1.offerRejectedCount) AS offerRejectedCount, ");
        resultQuery.append(" SUM(Q1.backoutCount) AS backoutCount ");
       
         resultQuery.append("FROM  (SELECT RH.TO_STAGE, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMISSION' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  submissionCount, ");
		 resultQuery.append(" CASE  WHEN UPPER(RH.TO_STAGE) = 'SHORTLIST' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  shortlistCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'REJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS rejectedCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMIT_TO_CLIENT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS submitToClientCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS interviewCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientInterviewCount, "); 
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offermadeCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOffermadeCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID)  ELSE 0 END AS offerAcceptedCount,  ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOfferAcceptedCount, "); 
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERREJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offerRejectedCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'BACKOUT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0  END AS backoutCount  ");
		 resultQuery.append(" FROM  ");
		 resultQuery.append(" REQUIREMENT_SUBMISSION_HISTORY RH, ");
		 resultQuery.append(" REQUIREMENT_SUBMISSION RS");
		 resultQuery.append(" WHERE  ");
		 
		 resultQuery.append(" RH.REQUIREMENT_SUBMISSION_ID = RS.ID ");
		 
		 resultQuery.append(" AND RS.REQUIREMENT_ID IN (:jobIds) ");
		 resultQuery.append(" AND RS.VENDOR_LOCATION_ID IS NOT NULL");
		 
		  if(searchDates != null) {
			  resultQuery.append(" AND  DATE_FORMAT(RS.CREATED_DATE, '%Y-%m-%d')  BETWEEN '"+fromDateStr+"' AND '"+toDateStr+"'");
			  resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
			}else {
				resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
			}
			 
		  Query queryString = session.createSQLQuery(resultQuery.toString()).setParameterList("jobIds", jobIdList);
		  internalApplicantBySourceCountDTO = (ApplicantBySourceCountDTO)queryString.setResultTransformer(Transformers.aliasToBean(ApplicantBySourceCountDTO.class)).uniqueResult();
		  return internalApplicantBySourceCountDTO;
		}

	
	/*@Override
	@Transactional
	public Integer getVendorSourceApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates, String stage) {
	
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> submissionCount = null;
		
		if(searchDates != null) {
			query ="select  count(*) from RequirementSubmissionHistory rh where rh.submissionId.vendorLocationId IS NOT NULL "
					+ " and  rh.submissionId.requirement.id IN (:jobIds) and rh.toStage = :stage and date(rh.submissionId.createdDate) BETWEEN :startDate and :endDate";
			
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).setString("stage", stage).list();
		}else {
			
			 query ="select  count(*) from RequirementSubmissionHistory rh where rh.submissionId.vendorLocationId IS NOT NULL "
				 		+ " and rh.toStage = :stage and rh.submissionId.requirement.id IN (:jobIds) ";
			
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setString("stage", stage).list();
		}
		
			Integer count= 0;
			if(submissionCount != null && !submissionCount.isEmpty()){
					count = submissionCount.get(0).intValue();
			}
			return count;
	}
*/
	
	@Override
	@Transactional
	public ApplicantBySourceCountDTO getFreelanceRecruiterSourceApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
		
		Session session = this.sessionFactory.getCurrentSession();
		ApplicantBySourceCountDTO internalApplicantBySourceCountDTO= null;
		String  fromDateStr =commonUtil.elkDateFormatter(searchDates[0]);
 	    String  toDateStr  = commonUtil.elkDateFormatter(searchDates[1]);
        StringBuilder resultQuery = new StringBuilder("");	
        resultQuery.append("SELECT ");
        resultQuery.append(" SUM(Q1.submissionCount) AS submissionCount, ");
        resultQuery.append(" SUM(Q1.shortlistCount) AS shortlistCount, ");
        resultQuery.append(" SUM(Q1.rejectedCount) AS rejectedCount, ");
        resultQuery.append(" SUM(Q1.submitToClientCount) AS submitToClientCount, ");
        resultQuery.append(" SUM(Q1.interviewCount) AS interviewCount, ");
        resultQuery.append(" SUM(Q1.clientInterviewCount) AS clientInterviewCount, ");
        resultQuery.append(" SUM(Q1.offermadeCount) AS offermadeCount, ");
        resultQuery.append(" SUM(Q1.clientOffermadeCount) AS clientOffermadeCount, ");
        resultQuery.append(" SUM(Q1.offerAcceptedCount) AS offerAcceptedCount, ");
        resultQuery.append(" SUM(Q1.clientOfferAcceptedCount) AS clientOfferAcceptedCount, ");
        resultQuery.append(" SUM(Q1.offerRejectedCount) AS offerRejectedCount, ");
        resultQuery.append(" SUM(Q1.backoutCount) AS backoutCount ");
       
         resultQuery.append("FROM  (SELECT RH.TO_STAGE, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMISSION' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  submissionCount, ");
		 resultQuery.append(" CASE  WHEN UPPER(RH.TO_STAGE) = 'SHORTLIST' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  shortlistCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'REJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS rejectedCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMIT_TO_CLIENT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS submitToClientCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS interviewCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientInterviewCount, "); 
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offermadeCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOffermadeCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID)  ELSE 0 END AS offerAcceptedCount,  ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOfferAcceptedCount, "); 
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERREJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offerRejectedCount, ");
		 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'BACKOUT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0  END AS backoutCount  ");
		 resultQuery.append(" FROM  ");
		 resultQuery.append(" REQUIREMENT_SUBMISSION_HISTORY RH, ");
		 resultQuery.append(" REQUIREMENT_SUBMISSION RS");
		 resultQuery.append(" WHERE  ");
		 
		 resultQuery.append(" RH.REQUIREMENT_SUBMISSION_ID = RS.ID ");
		 
		 resultQuery.append(" AND RS.REQUIREMENT_ID IN (:jobIds) ");
		 resultQuery.append(" AND RS.RECRUITER_USER_ID IS NOT NULL");
		 
		  if(searchDates != null) {
			  resultQuery.append(" AND  DATE_FORMAT(RS.CREATED_DATE, '%Y-%m-%d')  BETWEEN '"+fromDateStr+"' AND '"+toDateStr+"'");
			  resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
			}else {
				resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
			}
			 
		  Query queryString = session.createSQLQuery(resultQuery.toString()).setParameterList("jobIds", jobIdList);
		  internalApplicantBySourceCountDTO = (ApplicantBySourceCountDTO)queryString.setResultTransformer(Transformers.aliasToBean(ApplicantBySourceCountDTO.class)).uniqueResult();
		  return internalApplicantBySourceCountDTO;
	}
	
	/*@Override
	@Transactional
	public Integer getFreelanceRecruiterSourceApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates,
			String stage) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> submissionCount = null;
		
		if(searchDates != null) {
			query ="select  count(*) from RequirementSubmissionHistory rh where rh.submissionId.recruiterUserId IS NOT NULL "
					+ " and  rh.submissionId.requirement.id IN (:jobIds) and rh.toStage = :stage and date(rh.submissionId.createdDate) BETWEEN :startDate and :endDate";
			 
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).setString("stage", stage).list();
		}else {
			query ="select  count(*) from RequirementSubmissionHistory rh where rh.submissionId.recruiterUserId IS NOT NULL "
			 		+ " and rh.toStage = :stage and rh.submissionId.requirement.id IN (:jobIds) ";
			 
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setString("stage", stage).list();
		}
		
			Integer count= 0;
			if(submissionCount != null && !submissionCount.isEmpty()){
					count = submissionCount.get(0).intValue();
			}
			return count;
	}*/

	@Override
	@Transactional
	public ApplicantBySourceCountDTO getInternalSourceApplicantsCountByStage(List<Long> jobIdList, Date[] searchDates) {
		
				 Session session = this.sessionFactory.getCurrentSession();
				 String  fromDateStr =commonUtil.elkDateFormatter(searchDates[0]);
		  	     String  toDateStr  = commonUtil.elkDateFormatter(searchDates[1]);
				 ApplicantBySourceCountDTO internalApplicantBySourceCountDTO= null;
		         StringBuilder resultQuery = new StringBuilder("");
		         resultQuery.append("SELECT ");
		         resultQuery.append(" SUM(Q1.submissionCount) AS submissionCount, ");
                 resultQuery.append(" SUM(Q1.shortlistCount) AS shortlistCount, ");
		         resultQuery.append(" SUM(Q1.rejectedCount) AS rejectedCount, ");
		         resultQuery.append(" SUM(Q1.submitToClientCount) AS submitToClientCount, ");
		         resultQuery.append(" SUM(Q1.interviewCount) AS interviewCount, ");
		         resultQuery.append(" SUM(Q1.clientInterviewCount) AS clientInterviewCount, ");
		         resultQuery.append(" SUM(Q1.offermadeCount) AS offermadeCount, ");
		         resultQuery.append(" SUM(Q1.clientOffermadeCount) AS clientOffermadeCount, ");
		         resultQuery.append(" SUM(Q1.offerAcceptedCount) AS offerAcceptedCount, ");
		         resultQuery.append(" SUM(Q1.clientOfferAcceptedCount) AS clientOfferAcceptedCount, ");
		         resultQuery.append(" SUM(Q1.offerRejectedCount) AS offerRejectedCount, ");
		         resultQuery.append(" SUM(Q1.backoutCount) AS backoutCount ");

				 resultQuery.append(" FROM  (SELECT RH.TO_STAGE, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMISSION' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  submissionCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SHORTLIST' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS  shortlistCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'REJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS rejectedCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'SUBMIT_TO_CLIENT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS submitToClientCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS interviewCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_INTERVIEW' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientInterviewCount, "); 
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offermadeCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERMADE' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOffermadeCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID)  ELSE 0 END AS offerAcceptedCount,  ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'CLIENT_OFFERACCEPTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS clientOfferAcceptedCount, "); 
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'OFFERREJECTED' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0 END AS offerRejectedCount, ");
				 resultQuery.append(" CASE WHEN UPPER(RH.TO_STAGE) = 'BACKOUT' THEN COUNT(RS.REQUIREMENT_ID) ELSE 0  END AS backoutCount  ");
				 resultQuery.append(" FROM  ");
				 resultQuery.append(" REQUIREMENT_SUBMISSION_HISTORY RH, ");
				 resultQuery.append(" REQUIREMENT_SUBMISSION RS ");
				 resultQuery.append(" WHERE  ");
				 resultQuery.append(" RH.REQUIREMENT_SUBMISSION_ID = RS.ID ");
				 resultQuery.append(" AND RS.VENDOR_CANDIDATE_ID IS NULL AND RS.SOURCE_SUBMISSION_ID IS NULL");
				 resultQuery.append(" AND RS.REQUIREMENT_ID IN(:jobIds)"); 
				
				if(searchDates != null) {
					resultQuery.append(" AND  DATE_FORMAT(RS.CREATED_DATE, '%Y-%m-%d')  BETWEEN '"+fromDateStr+"' AND '"+toDateStr+"'");
					resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
				}else {
					resultQuery.append(" GROUP BY RH.TO_STAGE)Q1");
				}
				 
			  Query queryString = session.createSQLQuery(resultQuery.toString()).setParameterList("jobIds", jobIdList);
			  internalApplicantBySourceCountDTO = (ApplicantBySourceCountDTO)queryString.setResultTransformer(Transformers.aliasToBean(ApplicantBySourceCountDTO.class)).uniqueResult();
			  return internalApplicantBySourceCountDTO;
			}

}
