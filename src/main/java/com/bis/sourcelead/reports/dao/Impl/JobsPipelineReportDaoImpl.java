package com.bis.sourcelead.reports.dao.Impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.reports.dao.JobsPipelineReportDao;

@Repository
@Transactional(rollbackFor = { Exception.class })
public class JobsPipelineReportDaoImpl implements JobsPipelineReportDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Requirement> getAllJobsForReportBasedOnStartAndEndDates(Long orgLocationId, Long loginUserId, Date startDateUTC, Date endDateUTC) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		String query = "";
		if(orgLocationId != null){
			query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.createdBy.id =:loginUserId and ( requirement.createdDate >= :startDateUTC and requirement.createdDate <= :endDateUTC )";
			releasedJobsList = session.createQuery(query).setLong( "loginUserId", loginUserId).setParameter("startDateUTC", startDateUTC).setParameter( "endDateUTC", endDateUTC ).list();
		}
        return releasedJobsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(List<Long> jobIds,Date[] searchDates) {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission>  subList  = null;
		String query = null;
		if(jobIds.size() > 0) {
			if(searchDates != null) {
				 query =" from RequirementSubmission rs left join fetch rs.requirement    where rs.requirement.id IN (:jobIds) and date(rs.createdDate) BETWEEN :startDate and :endDate";
				 subList = session.createQuery(query).setParameterList("jobIds", jobIds).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).list();
			}else {
				 query =" from RequirementSubmission rs left join fetch rs.requirement    where rs.requirement.id IN (:jobIds)";
				 subList = session.createQuery(query).setParameterList("jobIds", jobIds).list();
			}	
		}
		
	    return subList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="from RequirementSubmissionHistory rsh where rsh.submissionId.id =:submissionId and rsh.createdDate = (select Max(rh.createdDate) from RequirementSubmissionHistory rh where rh.submissionId.id =:submissionId and rh.toStage = rsh.toStage) Group By rsh.toStage";
		List<RequirementSubmissionHistory>  subHistiryList = session.createQuery(query).setLong("submissionId", submissionId).list();
	    return subHistiryList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonIdAndMasterUserCode(Long submissionId, String masterUserCode) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			String query ="from RequirementSubmissionHistory rsh where rsh.submissionId.id =:submissionId and rsh.masterUserCode IN (" + masterUserCode + ") and rsh.createdDate = (select Max(rh.createdDate) from RequirementSubmissionHistory rh where rh.submissionId.id =:submissionId and rh.toStage = rsh.toStage) group by rsh.toStage";
			List<RequirementSubmissionHistory> rshList = session.createQuery(query).setLong("submissionId", submissionId).list();
			return rshList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer requirementListStatusCountByJobIdsAndDates(List<Long> jobIds, Date[] searchDates, String JobStage) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "";
		List<Long> requiremnetCount = null;
		if(jobIds.size() > 0) {
			if(searchDates != null){
				query = "select  count(*) from Requirement requirement  where requirement.id IN (:jobIds) and requirement.requirementSubmissionStatus = :JobStage and date(requirement.createdDate) BETWEEN :startDate and :endDate";
				requiremnetCount = session.createQuery(query).setParameterList("jobIds", jobIds).setString("JobStage", JobStage).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).list();
			}else {
				query = "select  count(*) from Requirement requirement  where requirement.id IN (:jobIds) and requirement.requirementSubmissionStatus = :JobStage";
				requiremnetCount = session.createQuery(query).setParameterList("jobIds", jobIds).setString("JobStage", JobStage).list();
			
			}
		}
		
		Integer count = 0;
		if(requiremnetCount != null && !requiremnetCount.isEmpty()){
			count = requiremnetCount.get(0).intValue();
	}
	return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Requirement> requirementListStatusCountByJobIdsAndDates(List<Long> jobIds, Date[] searchDates) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement>  reqList  = null;
		String query = null;
		if(jobIds.size() > 0) {
			if(searchDates != null) {
				 query =" from Requirement requirement  where requirement.id IN (:jobIds) and requirement.requirementSubmissionStatus IN('OPEN','ONHOLD','CLOSED','UNFILLEDCLOSED','FILLEDCLOSED','CANCEL') and date(requirement.createdDate) BETWEEN :startDate and :endDate";
				 reqList = session.createQuery(query).setParameterList("jobIds", jobIds).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).list();
			}else {
				 query =" from  Requirement requirement  where requirement.id IN (:jobIds) and requirement.requirementSubmissionStatus IN('OPEN','ONHOLD','CLOSED','UNFILLEDCLOSED','FILLEDCLOSED','CANCEL')";
				 reqList = session.createQuery(query).setParameterList("jobIds", jobIds).list();
			}	
		}
	    return reqList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Requirement> vendorRequirementListStatusCountByJobIdsAndDates(List<Long> jobIds, Date[] searchDates) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> reqList = null;
		String query = null;
		if (jobIds.size() > 0) {
			if (searchDates != null) {
				query = " from Requirement requirement  where requirement.id IN (:jobIds) and date(requirement.createdDate) BETWEEN :startDate and :endDate";
				reqList = session.createQuery(query).setParameterList("jobIds", jobIds).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).list();
			} else {
				query = " from  Requirement requirement  where requirement.id IN (:jobIds)";
				reqList = session.createQuery(query).setParameterList("jobIds", jobIds).list();
			}
		}
		return reqList;
	}
}
