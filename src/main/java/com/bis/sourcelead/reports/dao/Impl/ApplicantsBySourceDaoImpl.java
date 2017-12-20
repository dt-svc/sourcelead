package com.bis.sourcelead.reports.dao.Impl;

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

import com.bis.sourcelead.dao.impl.RequirementSubmissionDaoImpl;
import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;

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
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicantsBySourceDaoImpl.class);

	@Override
	@Transactional
	public Integer getCrowdsourcingApplicantsCount(List<Long> jobIds, Date startDate, Date endDate,Long orgLocationId) {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query ="select count(*) from RequirementSubmission rs where rs.requirement.orgLocationId =:orgLocationId ";
		List<Long> submissionCount =   session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
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
		
		List<Long> jobCount = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
			Integer count = 0;
			if(jobCount != null && !jobCount.isEmpty()){
					count = jobCount.get(0).intValue();
			}
			return count;
	}

}
