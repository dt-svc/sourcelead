package com.bis.sourcelead.dao.impl.performance;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.bis.sourcelead.dao.performance.PerformanceAppraisalDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.AppraisalAssignedAssociatesDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceAppraisalDaoImpl implements PerformanceAppraisalDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	CommonUtil commonUtil;
	
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

	@Value("${jdbc.schema}")
	private String schema;

	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId, Long orgLocationId,String dateFormat)
			throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query queryObj = null;
		List<PerformanceAppraisalTemplateDTO> assignToList = null;
		
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<PerformanceAppraisalTemplateDTO> response = new ResponseList<>();
		String querySeachStr = "";
		
		StringBuilder query1 = new StringBuilder("");
		if(performanceAppraisalTemplateDTO.getSearchValue() != null && !"".equalsIgnoreCase(performanceAppraisalTemplateDTO.getSearchValue())) {
			 query1.append(" Q1.name like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalStartDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalEndDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalProcessStartDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalProcessEndDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.selfAppraisalStartDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.selfAppraisalEndDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ");
			 
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getSortColumn()))
		{
			sortingQry = " order by Q1."+ performanceAppraisalTemplateDTO.getSortColumn() +" "+performanceAppraisalTemplateDTO.getSortOrder();
		}
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			querySeachStr = "where "+"("+query1.toString()+") AND Q1.daysDifference <= 0";
		}else {
			querySeachStr = "where Q1.daysDifference <= 0";
		}
		
		try {
			
			String appraisalDateFormat =  "";
			
			if("yyyy-MM-dd".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%Y-%m-%d\"";
			}else if("dd-MM-yyyy".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%d-%m-%Y\"";
			}else if("MM-dd-yyyy".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%m-%d-%Y\"";
			}else if("yyyy-dd-MM".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%Y-%d-%m\"";
			}
			
			query = "SELECT Q1.* " + 
			 		 "FROM (" + 
					 "SELECT " + 
					"TEMPLATE.NAME AS name, " + 
					"TEMPLATE.UUID AS templateUUId, " + 
					"DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+appraisalDateFormat+") AS appraisalStartDate, "+
					"DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+appraisalDateFormat+") AS appraisalEndDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_START_DATE,"+appraisalDateFormat+") AS appraisalProcessStartDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_END_DATE,"+appraisalDateFormat+") AS appraisalProcessEndDate, "+
					"DATE_FORMAT(TEMPLATE.SELF_APPRAISAL_START_DATE,"+appraisalDateFormat+") AS selfAppraisalStartDate, "+
					"DATE_FORMAT(TEMPLATE.SELF_APPRAISAL_END_DATE,"+appraisalDateFormat+") AS selfAppraisalEndDate, "+
					"DATEDIFF(TEMPLATE.SELF_APPRAISAL_START_DATE,CURRENT_DATE()) AS daysDifference " + 
							             
					"FROM " + 
					"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, " + 
					"PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE " + 
					"WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID AND ASSIGNED.USER_ID = :userId " + 
					"AND ASSIGNED.ORG_LOCATION_ID = :orgLocationId )Q1 "+querySeachStr+sortingQry;
			
			String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
					" FROM" + 
					"(" + query +
					")Q2;" ;
			
			
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("userId", userId);
			queryObj.setParameter("orgLocationId", orgLocationId);
			
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDTO.class));
			queryObj.setFirstResult(performanceAppraisalTemplateDTO.getStart());
			queryObj.setMaxResults(performanceAppraisalTemplateDTO.getLength());
			assignToList = queryObj.list();
			
			countlist = session.createSQLQuery(countQuery).setLong("userId", userId).setLong("orgLocationId", orgLocationId).list();
			filterCount = countlist.iterator().next().intValue();
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		int totalCount = 0;

		
		if (performanceAppraisalTemplateDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(assignToList);
		return response;
	}

	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(
			PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO, Long userId, Long orgLocationId,
			String dateFormat) throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		Query queryObj = null;
		List<PerformanceAppraisalTemplateDTO> assignToList = null;
		
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<PerformanceAppraisalTemplateDTO> response = new ResponseList<>();
		String querySeachStr = "";
		
		StringBuilder query1 = new StringBuilder("");
		if(performanceAppraisalTemplateDTO.getSearchValue() != null && !"".equalsIgnoreCase(performanceAppraisalTemplateDTO.getSearchValue())) {
			 query1.append(" Q1.name like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalStartDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalEndDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalProcessStartDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.appraisalProcessEndDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.peerReviewStartDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
			 .append("OR Q1.peerReviewEndDate like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ");
			 
		}
		
		String sortingQry = "";
		if(StringUtils.isNotBlank(performanceAppraisalTemplateDTO.getSortColumn()))
		{
			sortingQry = " order by Q1."+ performanceAppraisalTemplateDTO.getSortColumn() +" "+performanceAppraisalTemplateDTO.getSortOrder();
		}
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			querySeachStr = "where "+"("+query1.toString()+") AND Q1.dayDiff <= 0";
		}else {
			querySeachStr = "where Q1.dayDiff <= 0";
		}
		
		try {
			
			String appraisalDateFormat =  "";
			
			if("yyyy-MM-dd".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%Y-%m-%d\"";
			}else if("dd-MM-yyyy".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%d-%m-%Y\"";
			}else if("MM-dd-yyyy".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%m-%d-%Y\"";
			}else if("yyyy-dd-MM".equalsIgnoreCase(dateFormat)) {
				appraisalDateFormat = "\"%Y-%d-%m\"";
			}
			
			
			query = "SELECT Q1.* " + 
			 		 "FROM (" + 
					 "SELECT " + 
					"TEMPLATE.NAME AS name, " + 
					"TEMPLATE.UUID AS templateUUId, " + 
					"DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+appraisalDateFormat+") AS appraisalStartDate, "+
					"DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+appraisalDateFormat+") AS appraisalEndDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_START_DATE,"+appraisalDateFormat+") AS appraisalProcessStartDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_END_DATE,"+appraisalDateFormat+") AS appraisalProcessEndDate, "+
					"DATE_FORMAT(TEMPLATE.PEER_REVIEW_START_DATE,"+appraisalDateFormat+") AS peerReviewStartDate, "+
					"DATE_FORMAT(TEMPLATE.PEER_REVIEW_END_DATE,"+appraisalDateFormat+") AS peerReviewEndDate, "+
					"DATEDIFF(TEMPLATE.PEER_REVIEW_START_DATE,CURRENT_DATE()) AS dayDiff " + 
							             
					"FROM " + 
					"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, " + 
					"PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE, " + 
					"PERFORMANCE_APPRAISAL_PEERS PEERS " + 
					"WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID AND PEERS.ID = ASSIGNED.PEER_ID  " + 
					"AND ASSIGNED.ORG_LOCATION_ID = :orgLocationId AND FIND_IN_SET('"+userId+"',PEERS.PEER_USER_ID) > 0)Q1 "+querySeachStr+sortingQry;
			
			String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
					" FROM" + 
					"(" + query +
					")Q2;" ;
			
			
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("orgLocationId", orgLocationId);
			
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDTO.class));
			queryObj.setFirstResult(performanceAppraisalTemplateDTO.getStart());
			queryObj.setMaxResults(performanceAppraisalTemplateDTO.getLength());
			assignToList = queryObj.list();
			
			countlist = session.createSQLQuery(countQuery).setLong("orgLocationId", orgLocationId).list();
			filterCount = countlist.iterator().next().intValue();
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		int totalCount = 0;

		
		if (performanceAppraisalTemplateDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount = countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty()){
			response.setRecordsFiltered(filterCount);
		}else {
			response.setRecordsFiltered(totalCount);
		}
		
		response.setData(assignToList);
		return response;
	}
}
