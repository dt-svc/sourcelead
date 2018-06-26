package com.bis.sourcelead.reports.dao.Impl;

import java.util.ArrayList;
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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.dao.ApplicantDeclineDao;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;


@Repository
public class ApplicantDeclineDaoImpl implements ApplicantDeclineDao{
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicantDeclineDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	@Transactional

	public List<ApplicantDeclineDTO> getDeclineApplicantSkillCount(List<Long> jobIds, Date[] searchDates,OrgLocations orgLocation, int offset) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query submissionCount = null;
		
		List<ApplicantDeclineDTO> countList = null;
	if(jobIds.size() > 0){
		if(searchDates != null) {
			 query = "select (select count(rs.REASON_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='REJECTED' and rs.REASON_TYPE LIKE 'Bad Fit' and rs.REQUIREMENT_ID IN (:jobIds) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as badfitCount,"
					+ "(select count(rs.REASON_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='REJECTED' and rs.REASON_TYPE LIKE 'other' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as otherCount,"
					+ "(select count(rs.REASON_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='REJECTED' and rs.REASON_TYPE LIKE 'Interview Rejection' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as interviewCount,"
					+ "(select count(rs.REASON_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='REJECTED' and rs.REASON_TYPE LIKE 'Salary' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as salaryCount,"
					+ "(select count(rs.REASON_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='REJECTED' and rs.REASON_TYPE LIKE 'Unsuitable Personality' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as unsuitableCount,"
					+ "(select count(rs.REASON_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='REJECTED' and rs.REASON_TYPE LIKE 'Wrong Skill Sets' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as skillsCount,"
					+ "(select count(*)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='REJECTED' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as totalCount";
			 submissionCount =  session.createSQLQuery(query).setParameterList("jobIds", jobIds).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]);
		}
			submissionCount.setFirstResult(offset).setMaxResults(18).setResultTransformer(Transformers.aliasToBean(ApplicantDeclineDTO.class));
			countList = submissionCount.list();
			if (countList.isEmpty()) {
				ApplicantDeclineDTO applicantsDeclineDTO = new ApplicantDeclineDTO();
				//applicantsBackoutDTO.setCountFlag("true");
				countList.add(applicantsDeclineDTO);
			}
	   }
			return countList;
	}





	

}
	

