package com.bis.sourcelead.reports.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.reports.dao.ApplicantsBackoutReportDao;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;

@Repository
public class ApplicantsBackoutReportDaoImpl implements ApplicantsBackoutReportDao {
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
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ApplicantsBackoutDTO> getApplicantBackoutReport(List<Long> jobIdList, Date[] searchDates, int offset) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query submissionCount = null;
		
		List<ApplicantsBackoutDTO> countList = new ArrayList<ApplicantsBackoutDTO>();
	
		if(searchDates != null) {
			 query = "select (select count(rs.BACKOUT_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='BACKOUT' and rs.BACKOUT_TYPE LIKE 'Current Company' and rs.REQUIREMENT_ID IN (:jobIds) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as currentCompany,"
					+ "(select count(rs.BACKOUT_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='BACKOUT' and rs.BACKOUT_TYPE LIKE 'other' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as other,"
					+ "(select count(rs.BACKOUT_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='BACKOUT' and rs.BACKOUT_TYPE LIKE 'Compensation' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as compensation,"
					+ "(select count(rs.BACKOUT_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='BACKOUT' and rs.BACKOUT_TYPE LIKE 'Better Offer' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as betterOffer,"
					+ "(select count(rs.BACKOUT_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='BACKOUT' and rs.BACKOUT_TYPE LIKE 'Cold Feet' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as coldFeet,"
					+ "(select count(rs.BACKOUT_TYPE)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='BACKOUT' and rs.BACKOUT_TYPE LIKE 'Personal' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as personal,"
					+ "(select count(*)  from REQUIREMENT_SUBMISSION rs where rs.SUBMISSION_STATUS ='BACKOUT' and rs.REQUIREMENT_ID IN (:jobIds ) and DATE(rs.CREATED_DATE) BETWEEN :startDate and :endDate) as totalCount";
			 submissionCount =  session.createSQLQuery(query).setParameterList("jobIds", jobIdList).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]);
		}
			submissionCount.setFirstResult(offset).setMaxResults(18).setResultTransformer(Transformers.aliasToBean(ApplicantsBackoutDTO.class));
			countList = submissionCount.list();
			if (countList.isEmpty()) {
				ApplicantsBackoutDTO applicantsBackoutDTO = new ApplicantsBackoutDTO();
				//applicantsBackoutDTO.setCountFlag("true");
				countList.add(applicantsBackoutDTO);
			}
			return countList;
		
	}


}
