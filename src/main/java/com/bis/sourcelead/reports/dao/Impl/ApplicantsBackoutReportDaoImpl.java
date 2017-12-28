package com.bis.sourcelead.reports.dao.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.reports.dao.ApplicantsBackoutReportDao;

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
	public Map<String, Integer> getApplicantBackoutReport(List<Long> jobIdList, Date[] searchDates) {

		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<Long> submissionCount = null;
		
		List<String> typeName = new ArrayList<>();
		Map<String,Integer> typeCount = new LinkedHashMap<>();
		Integer count= 0;
		typeName.add("Current Company");
		typeName.add("Compensation");
		typeName.add("Better Offer");
		typeName.add("Cold Feet");
		typeName.add("Personal");
		typeName.add("other");
		for(String type:typeName) {
		if(searchDates != null) {
			 query ="select count(*) from RequirementSubmission rs where rs.submissionStatus ='BACKOUT' and rs.backOutCategoryType = '"+type+"' and rs.requirement.id IN (:jobIds) "
					+ " and (rs.createdDate BETWEEN :startDate and :endDate)";
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setParameter("startDate", searchDates[0]).setParameter("endDate", searchDates[1]).setCacheable(true).list();
		}else {
			 query ="select count(*) from RequirementSubmission rs where rs.submissionStatus ='BACKOUT' and rs.backOutCategoryType = '"+type+"' and rs.requirement.id IN (:jobIds) ";
			 submissionCount =  session.createQuery(query.toString()).setParameterList("jobIds", jobIdList).setCacheable(true).list();
		}
		
			
			if(submissionCount != null && !submissionCount.isEmpty()){
					count = submissionCount.get(0).intValue();
			}
			typeCount.put(type, count);
		}
			return typeCount;
		
	}


}
