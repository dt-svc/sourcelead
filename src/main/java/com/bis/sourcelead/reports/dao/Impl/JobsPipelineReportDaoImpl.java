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
public class JobsPipelineReportDaoImpl implements JobsPipelineReportDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Requirement> getAllJobsForReportBasedOnStartAndEndDates(Long orgLocationId, Long loginUserId, Date startDateUTC, Date endDateUTC) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Requirement> releasedJobsList = null;
		String query = "";
		if(orgLocationId != null){
			query = "from Requirement requirement  left join fetch requirement.parentRequirement left join fetch requirement.clientId where requirement.createdBy.id =:loginUserId and ( requirement.createdDate >= :startDateUTC and requirement.createdDate <= :endDateUTC )";
			releasedJobsList = session.createQuery(query).setLong( "loginUserId", loginUserId).setParameter("startDateUTC", startDateUTC).setParameter( "endDateUTC", endDateUTC ).setCacheable(true).list();
		}
        return releasedJobsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(List<Long> jobIds,Date[] searchDates) {
		Session session = this.sessionFactory.getCurrentSession();
		List<RequirementSubmission>  subList  = null;
		String query = null;
		if(searchDates != null) {
			 query =" from RequirementSubmission rs left join fetch rs.requirement    where rs.requirement.id IN (:jobIds) and (rs.createdDate BETWEEN :startDate and :endDate)";
			 subList = session.createQuery(query).setParameterList("jobIds", jobIds).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).setCacheable(true).list();
		}else {
			 query =" from RequirementSubmission rs left join fetch rs.requirement    where rs.requirement.id IN (:jobIds)";
			 subList = session.createQuery(query).setParameterList("jobIds", jobIds).setCacheable(true).list();
		}
	    return subList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonId(Long submissionId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query ="from RequirementSubmissionHistory rsh where rsh.submissionId.id =:submissionId and rsh.createdDate = (select Max(rh.createdDate) from RequirementSubmissionHistory rh where rh.submissionId.id =:submissionId and rh.toStage = rsh.toStage) Group By rsh.toStage";
		List<RequirementSubmissionHistory>  subHistiryList = session.createQuery(query).setLong("submissionId", submissionId).setCacheable(true).list();
	    return subHistiryList;
	}
	
}
