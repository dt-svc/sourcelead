package com.bis.sourcelead.dao.impl.performance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalDao;
import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.ProjectassignTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalPeersDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceFeedbackDTO;
import com.bis.sourcelead.dto.performance.PerformanceGeneralTasksDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceJobsDetailsDTO;
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
			querySeachStr = "where "+"("+query1.toString()+") AND Q1.dayDiff = 1 ";
		}else {
			querySeachStr = "where Q1.dayDiff = 1 ";
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
					"ASSIGNED.UUID AS templateUUId, " + 
					"DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+appraisalDateFormat+") AS appraisalStartDate, "+
					"DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+appraisalDateFormat+") AS appraisalEndDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_START_DATE,"+appraisalDateFormat+") AS appraisalProcessStartDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_END_DATE,"+appraisalDateFormat+") AS appraisalProcessEndDate, "+
					"DATE_FORMAT(TEMPLATE.SELF_APPRAISAL_START_DATE,"+appraisalDateFormat+") AS selfAppraisalStartDate, "+
					"DATE_FORMAT(TEMPLATE.SELF_APPRAISAL_END_DATE,"+appraisalDateFormat+") AS selfAppraisalEndDate, "+
					"CONVERT(CURRENT_DATE() >= TEMPLATE.SELF_APPRAISAL_START_DATE,CHAR)AS dayDiff " + 
							             
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
			 .append("OR Q1.appraisalAssignedUser like '%" + performanceAppraisalTemplateDTO.getSearchValue() + "%' ")
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
			querySeachStr = "where "+"("+query1.toString()+") AND Q1.dayDiff = 1";
		}else {
			querySeachStr = "where Q1.dayDiff = 1";
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
					"ASSIGNED.UUID AS templateUUId, " + 
					"CONCAT (U.FIRST_NAME,' ',IFNULL(U.MIDDLE_NAME,''),IFNULL(U.LAST_NAME,'')) AS appraisalAssignedUser, " +
					"U.FILE_URL AS appraisalAssignedUserImgUrl, " + 
					"DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+appraisalDateFormat+") AS appraisalStartDate, "+
					"DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+appraisalDateFormat+") AS appraisalEndDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_START_DATE,"+appraisalDateFormat+") AS appraisalProcessStartDate, "+
					"DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_END_DATE,"+appraisalDateFormat+") AS appraisalProcessEndDate, "+
					"DATE_FORMAT(TEMPLATE.PEER_REVIEW_START_DATE,"+appraisalDateFormat+") AS peerReviewStartDate, "+
					"DATE_FORMAT(TEMPLATE.PEER_REVIEW_END_DATE,"+appraisalDateFormat+") AS peerReviewEndDate, "+
					"CONVERT(CURRENT_DATE() >= TEMPLATE.PEER_REVIEW_START_DATE,CHAR)AS dayDiff " + 
							             
					"FROM " + 
					"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, " + 
					"PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE, " + 
					"USER U, " + 
					"PERFORMANCE_APPRAISAL_PEERS PEERS " + 
					"WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID AND PEERS.ID = ASSIGNED.PEER_ID AND ASSIGNED.USER_ID = U.ID  " + 
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

	@Override
	public PerformanceAppraisalDetailsDTO getSelfAppraisalTemplateDetails(String assignedUUID, String dateFormat)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalTemplateDetailsDTO> templateDetailsList = new ArrayList<PerformanceAppraisalTemplateDetailsDTO>();
		List<PerformanceAppraisalPeersDTO> peersList = new ArrayList<PerformanceAppraisalPeersDTO>();
		PerformanceAppraisalTemplateDetailsDTO templateDTO = null;
		PerformanceAppraisalDetailsDTO performanceAppraisalDetailsDTO = new PerformanceAppraisalDetailsDTO();
		
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
		
		String query =  "SELECT "
						+" TEMPLATE.NAME AS name, "           
						+" DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+appraisalDateFormat+") AS appraisalStartDate, "
						+" DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+appraisalDateFormat+") AS appraisalEndDate, "
						+" DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_START_DATE,"+appraisalDateFormat+") AS appraisalProcessStartDate, "
						+" DATE_FORMAT(TEMPLATE.PROCESS_PERIOD_END_DATE,"+appraisalDateFormat+") AS appraisalProcessEndDate, "
						+" DATE_FORMAT(TEMPLATE.SELF_APPRAISAL_START_DATE,"+appraisalDateFormat+") AS selfAppraisalStartDate, "
						+" DATE_FORMAT(TEMPLATE.SELF_APPRAISAL_END_DATE,"+appraisalDateFormat+") AS selfAppraisalEndDate, "
						+" DATE_FORMAT(TEMPLATE.APPROVING_FROM_START_DATE,"+appraisalDateFormat+") AS reportingManagerReviewStartDate, "
						+" DATE_FORMAT(TEMPLATE.APPROVING_FROM_END_DATE,"+appraisalDateFormat+") AS reportingManagerReviewEndDate, "
						+" DATE_FORMAT(TEMPLATE.RESOURCE_MANAGER_REVIEW_START_DATE,"+appraisalDateFormat+") AS resourceManagerReviewStartDate, "
						+" DATE_FORMAT(TEMPLATE.RESOURCE_MANAGER_REVIEW_END_DATE,"+appraisalDateFormat+") AS resourceManagerReviewEndDate, "
						+" TEMPLATE.DESCRIPTION AS description, "
						+" TEMPLATE.DESCRIPTION_DISPLAY AS descriptionDisplay, "
						+" PEERS.PEER_USER_ID AS peerIds, "
						+" CONCAT (U.FIRST_NAME,' ',IFNULL(U.MIDDLE_NAME,''),IFNULL(U.LAST_NAME,'')) AS reportingManager, " 
						+ "U.FILE_URL AS reportingManagerImgUrl, "
						+ "(SELECT (CONCAT(USR.FIRST_NAME,' ',IFNULL(USR.MIDDLE_NAME, ''),IFNULL(USR.LAST_NAME, ''))) FROM USER USR WHERE USR.ID = UR.RESOURCE_MANAGER_ID) AS resourceManager, "
						+ "(SELECT USR.FILE_URL FROM USER USR WHERE USR.ID = UR.RESOURCE_MANAGER_ID) AS resourceManagerImgUrl "
						
						+ "FROM PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE, "
						+ "PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED "
						+ "LEFT JOIN PERFORMANCE_APPRAISAL_PEERS PEERS  ON ASSIGNED.PEER_ID = PEERS.ID, "
						+ "USER U "
					    + "LEFT JOIN  USER_ROLE UR ON U.ID = UR.REPORTING_MANAGER_ID "
						  
						+"WHERE  "
						+ "UR.REPORTING_MANAGER_ID = U.ID  AND UR.ORG_LOCATIONS_ID = ASSIGNED.ORG_LOCATION_ID "
						+" AND ASSIGNED.UUID = :assignedUUID ";
			
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("assignedUUID", assignedUUID);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDetailsDTO.class));
		templateDetailsList = queryObj.list();
		templateDTO = templateDetailsList.get(0);
		
		
		if(templateDTO.getPeerIds() != null) {
			
			String peerUserIds [] = templateDTO.getPeerIds().split(",");
			String peerUserIdStr = "'" + StringUtils.join(peerUserIds,"','") + "'";
			
			String peersQuery =  "SELECT " + 
								 "CONCAT (U.FIRST_NAME,' ',IFNULL(U.LAST_NAME,'')) AS associateName, " +
								 "U.FILE_URL AS userImgUrl " + 
										             
								 "FROM " + 
								 "USER U " + 
								 "WHERE U.ID IN ("+peerUserIdStr+") ";
			
			
			queryObj = session.createSQLQuery(peersQuery);
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalPeersDTO.class));
			peersList = queryObj.list();
						
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		performanceAppraisalDetailsDTO.setAppraisalTemplate(templateDTO);
		performanceAppraisalDetailsDTO.setPeersList(peersList);
		
		return performanceAppraisalDetailsDTO;
		
	}

	@Override
	public List<PerformanceFeedbackTemplateDTO> getPerformanceAssignedFeedbackTemplateDTOList(String assignedUUID)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		List<PerformanceFeedbackTemplateDTO> feedbackTemplateDTOList = null;
		
		try {
			String query =  "SELECT "
					+" TEMPLATE.CATEGORIES "           
					+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
					+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE "
					+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID   "
					+" AND ASSIGNED.UUID = :assignedUUID ";
			
			Query queryObj = session.createSQLQuery(query);
			queryObj.setString("assignedUUID", assignedUUID);
			String categories = (String) queryObj.uniqueResult();
			
			if(categories != null) {
				
				String categoriesIds [] = categories.split(",");
				String categoriesIdsStr = "'" + StringUtils.join(categoriesIds,"','") + "'";
				
				String feedbackTemplateqQuery =  "SELECT "
						+" TEMPLATE.NAME AS templateName, "  
						+" TEMPLATE.UUID AS feedbackTemplateUUID "  
						+" FROM PERFORMANCE_FEEDBACK_TEMPLATE TEMPLATE "
						+" WHERE TEMPLATE.ID IN ("+categoriesIdsStr+") ";
				
				Query feedbackQueryObj = session.createSQLQuery(feedbackTemplateqQuery);
				feedbackQueryObj.setResultTransformer(Transformers.aliasToBean(PerformanceFeedbackTemplateDTO.class));
				feedbackTemplateDTOList = feedbackQueryObj.list();
		}
	 }catch (Exception e) {
			e.printStackTrace();
		}
		
		return feedbackTemplateDTOList;
	}

	@Override
	public PerformanceFeedbackTemplateDTO getPerformanceFeedbackTemplateDTOByUUID(String uuid) throws Exception {
		
		List<PerformanceFeedbackTemplateDTO> feedbackTemplateDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query =  "SELECT "
				+" TEMPLATE.JSON_STRING AS jsonString "           
				+" FROM PERFORMANCE_FEEDBACK_TEMPLATE TEMPLATE "
				+" WHERE TEMPLATE.UUID = :uuid ";
		
		Query feedbackQueryObj = session.createSQLQuery(query);
		feedbackQueryObj.setString("uuid", uuid);
		feedbackQueryObj.setResultTransformer(Transformers.aliasToBean(PerformanceFeedbackTemplateDTO.class));
		feedbackTemplateDTOList = feedbackQueryObj.list();
		
		return feedbackTemplateDTOList.get(0);
	}

	@Override
	public PerformanceFeedbackTemplateDTO getPerformanceSubmittedFeedbackTemplateDTOByUUID(String templateUUID,
			String assignedUUID, Long userId) throws Exception {
		
		List<PerformanceFeedbackTemplateDTO> feedbackTemplateDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceFeedbackTemplateDTO performanceFeedbackTemplateDTO = new PerformanceFeedbackTemplateDTO();
		
		
		String assignedCategoriesQuery =  "SELECT "
				+" TEMPLATE.CATEGORIES_JSON_STRING "           
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
				+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE"
				+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID AND ASSIGNED.UUID = :assignedUUID ";
		
		Query assignedCategoriesQueryObj = session.createSQLQuery(assignedCategoriesQuery);
		assignedCategoriesQueryObj.setString("assignedUUID", assignedUUID);
		String categoriesJsonString = (String) assignedCategoriesQueryObj.uniqueResult();
		
		
		String assignedQuery =  "SELECT "
				+" ASSIGNED.ID "           
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED "
				+" WHERE ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(assignedQuery);
		queryObj.setString("assignedUUID", assignedUUID);
		BigInteger assignedId = (BigInteger) queryObj.uniqueResult();
		
		String feddbackTemplateQuery =  "SELECT "
				+" TEMPLATE.ID "           
				+" FROM PERFORMANCE_FEEDBACK_TEMPLATE TEMPLATE "
				+" WHERE TEMPLATE.UUID = :templateUUID ";
		
		Query feddbackTemplatequeryObj = session.createSQLQuery(feddbackTemplateQuery);
		feddbackTemplatequeryObj.setString("templateUUID", templateUUID);
		BigInteger feedbackTemplateId = (BigInteger) feddbackTemplatequeryObj.uniqueResult();
		
		
		String query =  "SELECT "
				+" TEMPLATE.JSON_STRING AS jsonString, "
				+" TEMPLATE.ID AS feedbackTemplateId, "
				+ "(SELECT FEEDBACK.FEEDBACK_JSON_STRING FROM PERFORMANCE_APPRAISAL_USER_FEEDBACK FEEDBACK WHERE FEEDBACK.USER_ID = :userId AND FEEDBACK.APPRAISAL_ASSIGNED_ID = :assignedId AND FEEDBACK.FEEDBACK_TEMPLATE_ID = :feedbackTemplateId  ) AS submittedJsonString, "
				+ "(SELECT FEEDBACK.FEEDBACK_JSON_STRING_VALUES FROM PERFORMANCE_APPRAISAL_USER_FEEDBACK FEEDBACK WHERE FEEDBACK.USER_ID = :userId AND FEEDBACK.APPRAISAL_ASSIGNED_ID = :assignedId AND FEEDBACK.FEEDBACK_TEMPLATE_ID = :feedbackTemplateId ) AS submittedJsonStringValues, "
				+ "(SELECT FEEDBACK.SCORE FROM PERFORMANCE_APPRAISAL_USER_FEEDBACK FEEDBACK WHERE FEEDBACK.USER_ID = :userId AND FEEDBACK.APPRAISAL_ASSIGNED_ID = :assignedId AND FEEDBACK.FEEDBACK_TEMPLATE_ID = :feedbackTemplateId ) AS categoryScore, "
				+ "(SELECT FEEDBACK.FEEDBACK_CATEGORIES_SCORE FROM PERFORMANCE_FEEDBACK FEEDBACK WHERE FEEDBACK.USER_ID = :userId AND FEEDBACK.APPRAISAL_ASSIGNED_ID = :assignedId ) AS categoriesTotalScore "
				+" FROM PERFORMANCE_FEEDBACK_TEMPLATE TEMPLATE "
				+" WHERE TEMPLATE.UUID = :templateUUID ";
		
		Query feedbackQueryObj = session.createSQLQuery(query)
				.addScalar("jsonString", StandardBasicTypes.STRING)
				.addScalar("feedbackTemplateId", StandardBasicTypes.STRING)
				.addScalar("submittedJsonString", StandardBasicTypes.STRING)
				.addScalar("submittedJsonStringValues", StandardBasicTypes.STRING)
				.addScalar("categoryScore", StandardBasicTypes.STRING)
				.addScalar("categoriesTotalScore", StandardBasicTypes.STRING);
		feedbackQueryObj.setString("templateUUID", templateUUID);
		feedbackQueryObj.setLong("userId", userId);
		feedbackQueryObj.setBigInteger("assignedId", assignedId);
		feedbackQueryObj.setBigInteger("feedbackTemplateId", feedbackTemplateId);
		feedbackQueryObj.setResultTransformer(Transformers.aliasToBean(PerformanceFeedbackTemplateDTO.class));
		feedbackTemplateDTOList = feedbackQueryObj.list();
		performanceFeedbackTemplateDTO = feedbackTemplateDTOList.get(0);
		performanceFeedbackTemplateDTO.setCategoriesJsonString(categoriesJsonString);
		
		return performanceFeedbackTemplateDTO;
	}

	@Override
	public PerformanceGoalsDTO getperformanceGoalsDetails(String assignedUUID, Long userId) throws Exception {
		
		List<PerformanceAppraisalTemplateDetailsDTO> templateDetailsList = new ArrayList<PerformanceAppraisalTemplateDetailsDTO>();
		List<PerformanceGoalsDTO> goalDetailsList = new ArrayList<PerformanceGoalsDTO>();
		String mysqlDateFormat = "\"%Y-%m-%d\"";
		Session session = this.sessionFactory.getCurrentSession();
		boolean flag = true;
		PerformanceGoalsDTO goalsDTO = null;
		
		try {
		String query =  "SELECT "
				+" ASSIGNED.ORG_LOCATION_ID AS orgLocationId, "  
				+" ASSIGNED.USER_ID AS appraisalAssignedUserId, " 
				+" ASSIGNED.ID AS appraisalAssignedId, " 
				+" TEMPLATE.CATEGORIES_JSON_STRING AS categoriesJsonString, " 
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+mysqlDateFormat+") AS appraisalStartDate, "
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+mysqlDateFormat+") AS appraisalEndDate "
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
				+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE "
				+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID   "
				+" AND ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("assignedUUID", assignedUUID);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDetailsDTO.class));
		templateDetailsList = queryObj.list();
		PerformanceAppraisalTemplateDetailsDTO templateDTO = templateDetailsList.get(0);
		
		
		String goalsScoreQuery =  "SELECT "
				+" FEEDBACK.GOALS_SCORE "           
				+" FROM PERFORMANCE_FEEDBACK FEEDBACK "
				+" WHERE FEEDBACK.APPRAISAL_ASSIGNED_ID = :appraisalAssignedId AND FEEDBACK.USER_ID = :userId ";
		
		Query goalsScoreQueryObj = session.createSQLQuery(goalsScoreQuery);
		goalsScoreQueryObj.setBigInteger("appraisalAssignedId", templateDTO.getAppraisalAssignedId());
		goalsScoreQueryObj.setBigInteger("userId", templateDTO.getAppraisalAssignedUserId());
		Integer goalsScore = (Integer) goalsScoreQueryObj.uniqueResult();
		
		
		String goalsQuery = "SELECT " + 
				"  COUNT(Q1.GOAL_ID) AS toalAssignedGoalsCount, SUM(CASE WHEN (Q1.PERCENTAGE_DONE > 0 AND Q1.PERCENTAGE_DONE < 100)" + 
				"  THEN 1 ELSE 0 END) AS toalAssignedInprogressGoalsCount," + 
				"  SUM(CASE WHEN (Q1.PERCENTAGE_DONE = 0 OR Q1.PERCENTAGE_DONE IS NULL) THEN 1 ELSE 0 END) AS toalAssignedNotStartedGoalsCount," + 
				"  SUM(CASE WHEN Q1.PERCENTAGE_DONE = 100 THEN 1 ELSE 0 END) AS toalAssignedCompletedGoalsCount," + 
				"  SUM(CASE WHEN" + 
				"  ((Q1.PERCENTAGE_DONE > '0' AND Q1.PERCENTAGE_DONE < '100') || (Q1.PERCENTAGE_DONE = '0') || (Q1.PERCENTAGE_DONE IS NULL)) " + 
				"	AND DATEDIFF(Q1.END_DATE,CURDATE()) < 0 THEN 1 ELSE 0 END) AS toalAssignedOverDueGoalsCount" + 
				"  FROM" + 
				"    (SELECT " + 
				"            ASSIGN.GOAL_ID," + 
				"            GOALS.END_DATE," + 
				"            GOALS.PERCENTAGE_DONE" + 
				"    FROM" + 
				"       PERFORMANCE_GOALS GOALS, PERFORMANCE_GOALS_ASSIGN_TO ASSIGN " + 
				"    WHERE" + 
				"        ASSIGN.GOAL_ID = GOALS.ID" + 
				"            AND DATEDIFF('"+templateDTO.getAppraisalStartDate()+"', GOALS.START_DATE) <= 0" + 
				"            AND DATEDIFF(GOALS.END_DATE, '"+templateDTO.getAppraisalEndDate()+"') <= 0" + 
				"            AND ASSIGN.ASSIGNED_USER_ID = :userId" + 
				"            AND ASSIGN.ORG_LOCATION_ID = :orgLocationId AND ASSIGN.STATUS = '1' " + 
				"    GROUP BY ASSIGN.GOAL_ID) Q1";
		
		
		Query goalsQueryObj = session.createSQLQuery(goalsQuery)
				.addScalar("toalAssignedGoalsCount", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("toalAssignedInprogressGoalsCount", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("toalAssignedNotStartedGoalsCount", StandardBasicTypes.BIG_DECIMAL)
				.addScalar("toalAssignedCompletedGoalsCount", StandardBasicTypes.BIG_DECIMAL);
		goalsQueryObj.setBigInteger("userId", templateDTO.getAppraisalAssignedUserId());
		goalsQueryObj.setBigInteger("orgLocationId", templateDTO.getOrgLocationId());
		goalsQueryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGoalsDTO.class));
		goalDetailsList = goalsQueryObj.list();
		goalsDTO = goalDetailsList.get(0);
		goalsDTO.setCategoriesJsonString(templateDTO.getCategoriesJsonString());
		goalsDTO.setGoalsScore(goalsScore);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return goalsDTO;
	}

	@Override
	public List<ProjectassignTasksDTO> getPerformanceAssignedProjectAssignedTasksDTOList(String assignedUUID,String dateFormat)
			throws Exception {

		List<PerformanceAppraisalTemplateDetailsDTO> templateDetailsList = new ArrayList<PerformanceAppraisalTemplateDetailsDTO>();
		String mysqlDateFormat = "\"%Y-%m-%d\"";
		Session session = this.sessionFactory.getCurrentSession();
		List<ProjectassignTasksDTO> projectassignTaskList = null;
		try {
		String query =  "SELECT "
				+" ASSIGNED.ORG_LOCATION_ID AS orgLocationId, " 
				+" ASSIGNED.USER_ID AS appraisalAssignedUserId, " 
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+mysqlDateFormat+") AS appraisalStartDate, "
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+mysqlDateFormat+") AS appraisalEndDate "
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
				+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE "
				+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID   "
				+" AND ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("assignedUUID", assignedUUID);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDetailsDTO.class));
		templateDetailsList = queryObj.list();
		PerformanceAppraisalTemplateDetailsDTO templateDTO = templateDetailsList.get(0);
		
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
		
		
		String projectTaskQuery = " SELECT proj.PROJECT_NAME as projectName, " + 
				   " proj.ID as projectId, " + 
				   " DATE_FORMAT(proj.START_DATE,"+appraisalDateFormat+") AS fromDate, " +
				   " DATE_FORMAT(proj.END_DATE,"+appraisalDateFormat+") AS toDate, " +
				   " can.ID as candidateId, " + 
			       " lData.NAME as taskStatus " + 
			       " FROM sourcelead.PROJECT_ASSIGN_TASK assignTask,PROJECT_TASKS ptasks,PROJECTS proj,LOOKUP_DATA lData,PROJECT_MEMBERS projMem,CANDIDATE can, PROJECT_CLIENTS pc, User user " + 
			       " where assignTask.PROJECT_TASK_ID = ptasks.ID AND ptasks.PROJECT_ID = proj.ID  " + 
			       " AND lData.ID = ptasks.TASK_STATUS " + 
			       " AND projMem.ID = assignTask.PROJECT_MEMBER_ID " + 
			       " AND can.ID = projMem.CANDIDATE_ID " + 
			       " AND can.CONSULTANT_USER_ID = user.ID " + 
			       " AND pc.PROJECT_ID = proj.ID "+
			       " AND can.ORG_LOCATION_ID = pc.ORG_LOCATIONS_ID "+
			       " AND pc.ORG_LOCATIONS_ID = :orgLocationId AND user.ID = :userId "+
		           " AND DATEDIFF('"+templateDTO.getAppraisalStartDate()+"',assignTask.CREATED_DATE) <= 0" + 
				   " AND DATEDIFF(assignTask.CREATED_DATE,'"+templateDTO.getAppraisalEndDate()+"') <= 0" ;
			       
			        Query projectTaskQueryObj = session.createSQLQuery(projectTaskQuery);
			        projectTaskQueryObj.setBigInteger("userId", templateDTO.getAppraisalAssignedUserId());
			        projectTaskQueryObj.setBigInteger("orgLocationId", templateDTO.getOrgLocationId());
			        projectTaskQueryObj.setResultTransformer(Transformers.aliasToBean(ProjectassignTasksDTO.class));
					projectassignTaskList = projectTaskQueryObj.list();
					
		}catch (Exception e) {
			e.printStackTrace();
		}
		return projectassignTaskList;
	}

	@Override
	public List<PerformanceJobsDetailsDTO> getPerformanceJobsDetails(String assignedUUID, String dateFormat)
			throws Exception {
		
		List<PerformanceJobsDetailsDTO> jobDetailsList = null;
		List<PerformanceAppraisalTemplateDetailsDTO> templateDetailsList = new ArrayList<PerformanceAppraisalTemplateDetailsDTO>();
		String mysqlDateFormat = "\"%Y-%m-%d\"";
		Session session = this.sessionFactory.getCurrentSession();
		try {
		String query =  "SELECT "
				+" ASSIGNED.ORG_LOCATION_ID AS orgLocationId, " 
				+" ASSIGNED.USER_ID AS appraisalAssignedUserId, "
				+" U.USER_CODE AS assignedUserCode," 
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+mysqlDateFormat+") AS appraisalStartDate, "
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+mysqlDateFormat+") AS appraisalEndDate "
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
				+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE, USER U "
				+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID  AND ASSIGNED.USER_ID = U.ID "
				+" AND ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("assignedUUID", assignedUUID);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDetailsDTO.class));
		templateDetailsList = queryObj.list();
		PerformanceAppraisalTemplateDetailsDTO templateDTO = templateDetailsList.get(0);
		
		String assignedQuery =  "SELECT "
				+" ASSIGNTO.REQUIREMENT_ID " 
				+" FROM REQUIREMENT_ASSIGN_TO ASSIGNTO , "
				+" REQUIREMENT REQ "
				+" WHERE ASSIGNTO.REQUIREMENT_ID =  REQ.ID AND ASSIGNTO.ASSIGN_USER_ID = :userId "
				+" AND REQ.ORG_LOCATION_ID = :orgLocationId "
				+" AND DATEDIFF('"+templateDTO.getAppraisalStartDate()+"',ASSIGNTO.CREATED_DATE) <= 0"  
				+" AND DATEDIFF(ASSIGNTO.CREATED_DATE,'"+templateDTO.getAppraisalEndDate()+"') <= 0" ;
		
		Query assignedQueryObj = session.createSQLQuery(assignedQuery);
		assignedQueryObj.setBigInteger("userId", templateDTO.getAppraisalAssignedUserId());
		assignedQueryObj.setBigInteger("orgLocationId", templateDTO.getOrgLocationId());
		List<BigInteger> requirementList =  assignedQueryObj.list();
		
		String requirementIdStr = "'" + StringUtils.join(requirementList,"','") + "'";

		String submissionsQuery = "SELECT " + 
				"  SUBMISSION_HISTORY.TO_STAGE AS submissionStage," + 
				"  SUBMISSION_HISTORY.FROM_STAGE AS fromSubmissionStage," + 
				"  SUBMISSION_HISTORY.REQUIREMENT_SUBMISSION_ID AS submissionId, " + 
				"  REQ.ID AS requirementId, " + 
				"  REQ.JOB_TITLE AS requirementName, " + 
				
				"  (SELECT " +
				"  ORG_NAME" +
				"  FROM" +
				"  ORG_LOCATIONS OL," +
				"  ORG_RELATIONS ORL" +
				"  WHERE" +
				"  (OL.ID = ORL.LOCATION_ID OR OL.ID = ORL.RESPONDER_LOCATION_ID) AND OL.ID != REQ.ORG_LOCATION_ID AND ORL.ID = REQ.ORG_RELATION_ID AND REQ.ORG_RELATION_ID IS NOT NULL ) AS clientName " +
				
				"  FROM" + 
				"  REQUIREMENT_SUBMISSION_HISTORY SUBMISSION_HISTORY, REQUIREMENT_SUBMISSION SUBMISSION, REQUIREMENT REQ " + 
				"  WHERE" + 
				"  SUBMISSION_HISTORY.REQUIREMENT_SUBMISSION_ID = SUBMISSION.ID AND SUBMISSION.REQUIREMENT_ID = REQ.ID " + 
				"  AND SUBMISSION.CREATED_BY = :userCode " + 
				"  AND REQ.ID IN ("+requirementIdStr+") ";
		
		Query jobQueryObj = session.createSQLQuery(submissionsQuery);
		jobQueryObj.setString("userCode", templateDTO.getAssignedUserCode());
		jobQueryObj.setResultTransformer(Transformers.aliasToBean(PerformanceJobsDetailsDTO.class));
		jobDetailsList =  jobQueryObj.list();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return jobDetailsList;
	}

	@Override
	public PerformanceGeneralTasksDetailsDTO getGeneralTasksDetails(String assignedUUID, String dateFormat)
			throws Exception {
		
		List<PerformanceAppraisalTemplateDetailsDTO> templateDetailsList = new ArrayList<PerformanceAppraisalTemplateDetailsDTO>();
		String mysqlDateFormat = "\"%Y-%m-%d\"";
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGeneralTasksDetailsDTO> generalTaskList = null;
		PerformanceGeneralTasksDetailsDTO generalTaskDTO = null;
		try {
		String query =  "SELECT "
				+" ASSIGNED.ORG_LOCATION_ID AS orgLocationId, " 
				+" ASSIGNED.USER_ID AS appraisalAssignedUserId, " 
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_START_DATE,"+mysqlDateFormat+") AS appraisalStartDate, "
				+" DATE_FORMAT(TEMPLATE.APPRAISAL_END_DATE,"+mysqlDateFormat+") AS appraisalEndDate "
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
				+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE "
				+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID   "
				+" AND ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(query);
		queryObj.setString("assignedUUID", assignedUUID);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDetailsDTO.class));
		templateDetailsList = queryObj.list();
		PerformanceAppraisalTemplateDetailsDTO templateDTO = templateDetailsList.get(0);
		
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
		
		
		String generalTasksQuery = "SELECT " + 
				"  COUNT(Q1.ID) AS tasksCount, " +
				"  SUM(CASE WHEN Q1.TASK_STATUS = 'Closed' THEN 1 ELSE 0 END) AS completedCount, " + 
				"  SUM(CASE WHEN (Q1.TASK_STATUS = 'Open' AND DATEDIFF(DATE_FORMAT(Q1.TASK_DATE,"+mysqlDateFormat+"),CURDATE()) >= 0) THEN 1 ELSE 0 END) AS openTasksCount, " + 
				"  SUM(CASE WHEN (Q1.TASK_STATUS = 'In-Progress' AND DATEDIFF(DATE_FORMAT(Q1.TASK_DATE,"+mysqlDateFormat+"),CURDATE()) >= 0) THEN 1 ELSE 0 END) AS inprogressCount, " +
				"  SUM(CASE WHEN ((Q1.TASK_STATUS = 'Open' OR Q1.TASK_STATUS = 'In-Progress') AND DATEDIFF(DATE_FORMAT(Q1.TASK_DATE,"+mysqlDateFormat+"),CURDATE()) < 0) THEN 1 ELSE 0 END) AS overdueCount " +
				"  FROM" + 
				"    (SELECT " + 
				"            T.ID, " + 
				"            T.TASK_DATE, " + 
				"            T.TASK_STATUS" + 
				"    FROM" + 
				"       TASKS T" + 
				"    WHERE" + 
				"        T.ASSIGNED_USER_ID = :userId" + 
				"        AND T.ASSIGNED_ORGLOCATION_ID = :orgLocationId " + 
				"        AND DATEDIFF('"+templateDTO.getAppraisalStartDate()+"',DATE_FORMAT(T.TASK_DATE,"+mysqlDateFormat+")) <= 0" + 
				"        AND DATEDIFF(DATE_FORMAT(T.TASK_DATE,"+mysqlDateFormat+"),'"+templateDTO.getAppraisalEndDate()+"') <= 0" + 
				"    ) Q1";
		
		
		Query generalTaskQueryObj = session.createSQLQuery(generalTasksQuery)
				.addScalar("tasksCount", StandardBasicTypes.INTEGER)
				.addScalar("openTasksCount", StandardBasicTypes.INTEGER)
				.addScalar("completedCount", StandardBasicTypes.INTEGER)
				.addScalar("inprogressCount", StandardBasicTypes.INTEGER)
				.addScalar("overdueCount", StandardBasicTypes.INTEGER);
		generalTaskQueryObj.setBigInteger("userId", templateDTO.getAppraisalAssignedUserId());
		generalTaskQueryObj.setBigInteger("orgLocationId", templateDTO.getOrgLocationId());
		generalTaskQueryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGeneralTasksDetailsDTO.class));
		generalTaskList = generalTaskQueryObj.list();
		generalTaskDTO = generalTaskList.get(0);
					
		}catch (Exception e) {
			e.printStackTrace();
		}
		return generalTaskDTO;
	}

	

	@Override
	public PerformanceFeedbackDTO getPerformanceFeedbackDetails(String assignedUUID, Long userId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String assignedCategoriesQuery =  "SELECT "
				+" ASSIGNED.ID AS appraisalAssignedId, "  
				+" TEMPLATE.CATEGORIES_JSON_STRING AS categoriesJsonString "           
				+" FROM PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES ASSIGNED, "
				+" PERFORMANCE_APPRAISAL_TEMPLATE TEMPLATE"
				+" WHERE ASSIGNED.APPRAISAL_TEMPLATE_ID = TEMPLATE.ID AND ASSIGNED.UUID = :assignedUUID ";
		
		Query queryObj = session.createSQLQuery(assignedCategoriesQuery);
		queryObj.setString("assignedUUID", assignedUUID);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceAppraisalTemplateDetailsDTO.class));
		List<PerformanceAppraisalTemplateDetailsDTO> templateDetailsList = queryObj.list();
		PerformanceAppraisalTemplateDetailsDTO templateDTO = templateDetailsList.get(0);
		
		
		String performanceFeedbackQuery =  "SELECT "
				+" FEEDBAK.FINAL_SCORE AS finalScore, " 
				+" FEEDBAK.TASKS_SCORE AS tasksScore, "
				+" FEEDBAK.COMMENTS AS comments " 
				+" FROM PERFORMANCE_FEEDBACK FEEDBAK "
				+" WHERE FEEDBAK.APPRAISAL_ASSIGNED_ID = :appraisalAssignedId AND FEEDBAK.USER_ID = :userId ";
		
		Query performanceFeedbackObj = session.createSQLQuery(performanceFeedbackQuery);
		performanceFeedbackObj.setBigInteger("appraisalAssignedId", templateDTO.getAppraisalAssignedId());
		performanceFeedbackObj.setLong("userId", userId);
		performanceFeedbackObj.setResultTransformer(Transformers.aliasToBean(PerformanceFeedbackDTO.class));
		List<PerformanceFeedbackDTO> performanceFeedbackList = performanceFeedbackObj.list();
		PerformanceFeedbackDTO feedBackDTO = performanceFeedbackList.get(0);
		feedBackDTO.setJsonCategoriesString(templateDTO.getCategoriesJsonString());
		
		return feedBackDTO;
	}
}
