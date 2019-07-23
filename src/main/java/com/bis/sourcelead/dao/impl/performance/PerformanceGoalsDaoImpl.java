package com.bis.sourcelead.dao.impl.performance;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.bis.sourcelead.dao.performance.PerformanceGoalsDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TeamDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsAssignToDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDTO;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.dto.performance.PerformanceTeamMembersDTO;
import com.bis.sourcelead.entity.SubmissionRequirementListView;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceGoalsDaoImpl implements PerformanceGoalsDao{
	
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceGoalsDaoImpl.class);

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
	public PerformanceGoals savePerformanceGoals(PerformanceGoals performanceGoals) throws Exception {
		logger.debug("Adding PerformanceCompetencies");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceGoals != null) {
				session.saveOrUpdate(performanceGoals);
			}
			logger.debug("PerformanceGoals Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_GOALS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceGoals;
	}

	@Override
	public PerformanceGoals getPerformanceGoalsById(Long goalId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoals> performanceGoals = new ArrayList<PerformanceGoals>();
		logger.debug("getPerformanceGoalsById  start");
		performanceGoals = session.createQuery("from PerformanceGoals goals left join fetch goals.goalId where goals.id = :id " ).setLong( "id", goalId).list();
		logger.debug("getPerformanceGoalsById  end");
		if (performanceGoals.size() > 0) {
			return performanceGoals.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getTotalGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		
		String goalTypeStr = "";
		if(performanceGoalType.equalsIgnoreCase("self")) {
			goalTypeStr = "GOALS.TEAM_ID IS NULL AND ";
		}else {
			goalTypeStr = "GOALS.TEAM_ID IS NOT NULL AND ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
					+ " GOALS.CREATED_BY = :userId AND GOALS.STATUS = '1' AND "
				    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.ASSIGNED_USER_ID";
			 
		        
				Query selfQueryString = session.createSQLQuery(selfQuery);
				selfQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
				BigInteger selfCount  = (BigInteger) selfQueryString.uniqueResult();
				if(selfCount == null) {
					selfCount = new BigInteger("0");
				}
				
				
				String assignedQuery ="SELECT"
						+ " COUNT(ASSIGN.GOAL_ID) AS REQ_COUNT"
						+ " FROM"
						    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
						    + " PERFORMANCE_GOALS GOALS,"
						    + " ORG_LOCATIONS OL"
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
							+ " GOALS.CREATED_BY <> :userId AND "
						    + " ASSIGN.ASSIGNED_USER_ID = :userId AND ASSIGN.STATUS = '1' AND "
						    + " OL.ID = :orgLocationId"
						    + " GROUP BY ASSIGN.GOAL_ID";
					 
				        
						Query assignedQueryString = session.createSQLQuery(assignedQuery);
						assignedQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
						BigInteger assignedCount  = (BigInteger) assignedQueryString.uniqueResult();
						
						if(assignedCount == null) {
							assignedCount = new BigInteger("0");
						}
				
						BigInteger totalCount = selfCount.add(assignedCount);
						totalCountDTO.setGoalCount(totalCount);
						totalCountDTO.setSelfCount(selfCount);
						totalCountDTO.setAssignedCount(assignedCount);
						
				return totalCountDTO;
		        
		
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getInprogressGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		
		String goalTypeStr = "";
		if(performanceGoalType.equalsIgnoreCase("self")) {
			goalTypeStr = "GOALS.TEAM_ID IS NULL AND ";
		}else {
			goalTypeStr = "GOALS.TEAM_ID IS NOT NULL AND ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
					+ " GOALS.CREATED_BY = :userId AND GOALS.STATUS = '1' AND "
					+ " (GOALS.PERCENTAGE_DONE > '0' AND GOALS.PERCENTAGE_DONE < '100') AND "
				    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
				    + " DATEDIFF(GOALS.END_DATE,CURDATE()) >= 0 AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.ASSIGNED_USER_ID";
			 
		        
				Query selfQueryString = session.createSQLQuery(selfQuery);
				selfQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
				BigInteger selfCount  = (BigInteger) selfQueryString.uniqueResult();
				if(selfCount == null) {
					selfCount = new BigInteger("0");
				}
				
				
				String assignedQuery ="SELECT"
						+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
						+ " FROM"
						    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
						    + " PERFORMANCE_GOALS GOALS,"
						    + " ORG_LOCATIONS OL"
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
							+ " GOALS.CREATED_BY <> :userId AND ASSIGN.STATUS = '1' AND "
							+ " (GOALS.PERCENTAGE_DONE > '0' AND GOALS.PERCENTAGE_DONE < '100') AND "
						    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
						    + " DATEDIFF(GOALS.END_DATE,CURDATE()) >= 0 AND "
						    + " OL.ID = :orgLocationId"
						    + " GROUP BY ASSIGN.GOAL_ID";
					 
				        
						Query assignedQueryString = session.createSQLQuery(assignedQuery);
						assignedQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
						BigInteger assignedCount  = (BigInteger) assignedQueryString.uniqueResult();
						
						if(assignedCount == null) {
							assignedCount = new BigInteger("0");
						}
				
						BigInteger totalCount = selfCount.add(assignedCount);
						totalCountDTO.setGoalCount(totalCount);
						totalCountDTO.setSelfCount(selfCount);
						totalCountDTO.setAssignedCount(assignedCount);
						
				return totalCountDTO;
		        
		
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getCompletedGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		
		
		String goalTypeStr = "";
		if(performanceGoalType.equalsIgnoreCase("self")) {
			goalTypeStr = "GOALS.TEAM_ID IS NULL AND ";
		}else {
			goalTypeStr = "GOALS.TEAM_ID IS NOT NULL AND ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
					+ " GOALS.CREATED_BY = :userId AND GOALS.STATUS = '1' AND "
					+ " GOALS.PERCENTAGE_DONE = '100' AND "
				    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.ASSIGNED_USER_ID";
			 
		        
				Query selfQueryString = session.createSQLQuery(selfQuery);
				selfQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
				BigInteger selfCount  = (BigInteger) selfQueryString.uniqueResult();
				if(selfCount == null) {
					selfCount = new BigInteger("0");
				}
				
				
				String assignedQuery ="SELECT"
						+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
						+ " FROM"
						    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
						    + " PERFORMANCE_GOALS GOALS,"
						    + " ORG_LOCATIONS OL"
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
							+ " GOALS.CREATED_BY <> :userId AND ASSIGN.STATUS  ='1' AND "
							+ " GOALS.PERCENTAGE_DONE = '100' AND "
						    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
						    + " OL.ID = :orgLocationId"
						    + " GROUP BY ASSIGN.GOAL_ID";
					 
				        
						Query assignedQueryString = session.createSQLQuery(assignedQuery);
						assignedQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
						BigInteger assignedCount  = (BigInteger) assignedQueryString.uniqueResult();
						
						if(assignedCount == null) {
							assignedCount = new BigInteger("0");
						}
				
						BigInteger totalCount = selfCount.add(assignedCount);
						totalCountDTO.setGoalCount(totalCount);
						totalCountDTO.setSelfCount(selfCount);
						totalCountDTO.setAssignedCount(assignedCount);
						
				return totalCountDTO;
		        
		
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getOverDueGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		
		
		String goalTypeStr = "";
		if(performanceGoalType.equalsIgnoreCase("self")) {
			goalTypeStr = "GOALS.TEAM_ID IS NULL AND ";
		}else {
			goalTypeStr = "GOALS.TEAM_ID IS NOT NULL AND ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
					+ " GOALS.CREATED_BY = :userId AND GOALS.STATUS ='1' AND "
					+ " ((GOALS.PERCENTAGE_DONE > '0' AND GOALS.PERCENTAGE_DONE < '100') || (GOALS.PERCENTAGE_DONE = '0') || (GOALS.PERCENTAGE_DONE IS NULL)) AND "
				    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
				    + " DATEDIFF(GOALS.END_DATE,CURDATE()) < 0 AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.ASSIGNED_USER_ID";
			 
		        
				Query selfQueryString = session.createSQLQuery(selfQuery);
				selfQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
				BigInteger selfCount  = (BigInteger) selfQueryString.uniqueResult();
				if(selfCount == null) {
					selfCount = new BigInteger("0");
				}
				
				
				String assignedQuery ="SELECT"
						+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
						+ " FROM"
						    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
						    + " PERFORMANCE_GOALS GOALS,"
						    + " ORG_LOCATIONS OL"
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
							+ " GOALS.CREATED_BY <> :userId AND "
							+ " ((GOALS.PERCENTAGE_DONE > '0' AND GOALS.PERCENTAGE_DONE < '100') || (GOALS.PERCENTAGE_DONE = '0') || (GOALS.PERCENTAGE_DONE IS NULL)) AND "
						    + " ASSIGN.ASSIGNED_USER_ID = :userId AND ASSIGN.STATUS = '1' AND "
						    + " DATEDIFF(GOALS.END_DATE,CURDATE()) < 0 AND "
						    + " OL.ID = :orgLocationId"
						    + " GROUP BY ASSIGN.GOAL_ID";
					 
				        
						Query assignedQueryString = session.createSQLQuery(assignedQuery);
						assignedQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
						BigInteger assignedCount  = (BigInteger) assignedQueryString.uniqueResult();
						
						if(assignedCount == null) {
							assignedCount = new BigInteger("0");
						}
				
						BigInteger totalCount = selfCount.add(assignedCount);
						totalCountDTO.setGoalCount(totalCount);
						totalCountDTO.setSelfCount(selfCount);
						totalCountDTO.setAssignedCount(assignedCount);
						
				return totalCountDTO;
		        
		
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getNotStartedGoalsDoNutChartCounts(Long userId, Long orgLocationId,String performanceGoalType)
			throws Exception {
		
		String goalTypeStr = "";
		if(performanceGoalType.equalsIgnoreCase("self")) {
			goalTypeStr = "GOALS.TEAM_ID IS NULL AND ";
		}else {
			goalTypeStr = "GOALS.TEAM_ID IS NOT NULL AND ";
		}
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
					+ " GOALS.CREATED_BY = :userId AND GOALS.STATUS = '1' AND "
					+ " ((GOALS.PERCENTAGE_DONE = '0') || (GOALS.PERCENTAGE_DONE IS NULL)) AND "
				    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
				    + " DATEDIFF(GOALS.END_DATE,CURDATE()) >= 0 AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.ASSIGNED_USER_ID";
			 
		        
				Query selfQueryString = session.createSQLQuery(selfQuery);
				selfQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
				BigInteger selfCount  = (BigInteger) selfQueryString.uniqueResult();
				if(selfCount == null) {
					selfCount = new BigInteger("0");
				}
				
				
				String assignedQuery ="SELECT"
						+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
						+ " FROM"
						    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
						    + " PERFORMANCE_GOALS GOALS,"
						    + " ORG_LOCATIONS OL"
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND GOALS.ID = ASSIGN.GOAL_ID AND "+goalTypeStr+" "
							+ " GOALS.CREATED_BY <> :userId AND ASSIGN.STATUS = '1' AND "
							+ " ((GOALS.PERCENTAGE_DONE = '0') || (GOALS.PERCENTAGE_DONE IS NULL)) AND "
						    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
						    + " DATEDIFF(GOALS.END_DATE,CURDATE()) >= 0 AND "
						    + " OL.ID = :orgLocationId"
						    + " GROUP BY ASSIGN.GOAL_ID";
					 
				        
						Query assignedQueryString = session.createSQLQuery(assignedQuery);
						assignedQueryString.setParameter("userId", userId).setParameter("orgLocationId", orgLocationId);
						BigInteger assignedCount  = (BigInteger) assignedQueryString.uniqueResult();
						
						if(assignedCount == null) {
							assignedCount = new BigInteger("0");
						}
				
						BigInteger totalCount = selfCount.add(assignedCount);
						totalCountDTO.setGoalCount(totalCount);
						totalCountDTO.setSelfCount(selfCount);
						totalCountDTO.setAssignedCount(assignedCount);
						
				return totalCountDTO;
		        
		
	}

	@Override
	public ResponseList<PerformanceGoalsDTO> getPerformanceGoalsList(PerformanceGoalsDTO performanceGoalsDTO,Long userId, Long orgLocationId,String dateFormat,String performanceGoalType) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		List<BigInteger> countlist = null;
		Integer filterCount = 0;
		ResponseList<PerformanceGoalsDTO> response = new ResponseList<>();
		Query queryObj = null;
		List<PerformanceGoalsDTO> goalsList = null;
		String querySeachStr =  "";
		
		String goalDateFormat =  "";
		
		String goalTypeStr = "";
		if(performanceGoalType.equalsIgnoreCase("self")) {
			goalTypeStr = "GOALS.TEAM_ID IS NULL AND ";
		}else {
			goalTypeStr = "GOALS.TEAM_ID IS NOT NULL AND ";
		}
		
		if("yyyy-MM-dd".equalsIgnoreCase(dateFormat)) {
			goalDateFormat = "\"%Y-%m-%d\"";
		}else if("dd-MM-yyyy".equalsIgnoreCase(dateFormat)) {
			goalDateFormat = "\"%d-%m-%Y\"";
		}else if("MM-dd-yyyy".equalsIgnoreCase(dateFormat)) {
			goalDateFormat = "\"%m-%d-%Y\"";
		}else if("yyyy-dd-MM".equalsIgnoreCase(dateFormat)) {
			goalDateFormat = "\"%Y-%d-%m\"";
		}
		
		  StringBuilder query1 = new StringBuilder("");
			if(performanceGoalsDTO.getSearchValue() != null && !"".equalsIgnoreCase(performanceGoalsDTO.getSearchValue())) {
				 query1.append(" Q1.goalName like '%" + performanceGoalsDTO.getSearchValue() + "%' ")
					.append(" OR Q1.goalStartDate like '%" + performanceGoalsDTO.getSearchValue() + "%' ")
					.append(" OR Q1.goalEndDate like '%" + performanceGoalsDTO.getSearchValue() + "%' ");
				 
			}
			
			
			if(query1.toString() != null && !query1.toString().isEmpty()){
				querySeachStr = " where "+"("+query1.toString()+")";
			}
			
			try {
			 query = "SELECT Q1.* " + 
			 		 "FROM (" + 
					 "SELECT " + 
					"GOALS.ID AS id, " +
					"GOALS.GOAL_NAME AS goalName," + 
					"DATE_FORMAT(GOALS.START_DATE ,"+goalDateFormat+") AS goalStartDate, " + 
					"DATE_FORMAT(GOALS.END_DATE ,"+goalDateFormat+") AS goalEndDate, " + 
					"GOALS.PERCENTAGE_DONE AS percentageDone, " +
					"GOALS.GOAL_TYPE AS goalType, " +
					"GOALS.UUID AS uuid " +
					"FROM " + 
					"PERFORMANCE_GOALS GOALS, " + 
					"PERFORMANCE_GOALS_ASSIGN_TO ASSIGN, " + 
					"ORG_LOCATIONS OL " + 
					"WHERE OL.ID = ASSIGN.ORG_LOCATION_ID AND " + 
					"GOALS.ID = ASSIGN.GOAL_ID " + 
					"AND (GOALS.CREATED_BY = :userId OR (ASSIGN.ASSIGNED_USER_ID = :userId AND ASSIGN.STATUS = '1')) " + 
					"AND GOALS.ORG_LOCATION_ID = :orgLocationId AND "+goalTypeStr+" GOALS.STATUS = '1' " + 
					"GROUP BY  GOALS.ID, "+
					"DATE_FORMAT(GOALS.CREATED_DATE ,\"%Y-%m-%d\"))Q1 "+querySeachStr;
		
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("orgLocationId", orgLocationId);
			queryObj.setParameter("userId", userId);
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGoalsDTO.class));
			queryObj.setFirstResult(performanceGoalsDTO.getStart());
			queryObj.setMaxResults(performanceGoalsDTO.getLength());
			goalsList = queryObj.list();
			
			String  countQuery = "SELECT COUNT(*) AS TOTAL" + 
					" FROM" + 
					"(" + query +
					")Q2;" ;
			
			countlist = session.createSQLQuery(countQuery).setLong("orgLocationId", orgLocationId).setLong("userId", userId).list();
			filterCount = countlist.iterator().next().intValue();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			int totalCount = 0;

			
			if (performanceGoalsDTO != null) {
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
			
			response.setData(goalsList);
			return response;
	}

	@Override
	public PerformanceGoals getPerformanceGoalsByUUID(String goalUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoals> performanceGoals = new ArrayList<PerformanceGoals>();
		logger.debug("getPerformanceGoalsByUUID  start");
		performanceGoals = session.createQuery("from PerformanceGoals goals left join fetch goals.goalId left join fetch goals.teamId where goals.uuid = :goalUUID " ).setString( "goalUUID", goalUUID).list();
		logger.debug("getPerformanceGoalsByUUID  end");
		if (performanceGoals.size() > 0) {
			return performanceGoals.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<PerformanceGoalsDTO> getPerformanceSubGoalsList(Long goalId, String dateFormat)
			throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoalsDTO> goalsList = null;
		
		try {
			
			String goalDateFormat =  "";
			
			if("yyyy-MM-dd".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%Y-%m-%d\"";
			}else if("dd-MM-yyyy".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%d-%m-%Y\"";
			}else if("MM-dd-yyyy".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%m-%d-%Y\"";
			}else if("yyyy-dd-MM".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%Y-%d-%m\"";
			}
			
			Query queryObj = null;
			
			String query = 	 "SELECT " + 
					"GOALS.ID AS id, " +
					"GOALS.GOAL_NAME AS goalName," + 
					"DATE_FORMAT(GOALS.START_DATE ,"+goalDateFormat+") AS goalStartDate, " + 
					"DATE_FORMAT(GOALS.END_DATE ,"+goalDateFormat+") AS goalEndDate, " + 
					"DATE_FORMAT(GOALS.CREATED_DATE ,"+goalDateFormat+") AS createdDate, " + 
					"GOALS.PERCENTAGE_DONE AS percentageDone, " +
					"GOALS.UUID AS uuid " +
					"FROM " + 
					"PERFORMANCE_GOALS GOALS WHERE " + 
					"GOALS.GOAL_ID = :goalId AND GOALS.STATUS = '1' " ;
		
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("goalId", goalId);
			queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGoalsDTO.class));
			goalsList = queryObj.list();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return goalsList;
	}

	@Override
	public List<PerformanceGoalsAssignToDTO> getPerformanceGoalsAssignedUsersList(String goalUUID, String dateFormat)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoalsAssignToDTO> goalsAssignToList = null;
		
		try {
			
			String goalDateFormat =  "";
			
			if("yyyy-MM-dd".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%Y-%m-%d\"";
			}else if("dd-MM-yyyy".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%d-%m-%Y\"";
			}else if("MM-dd-yyyy".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%m-%d-%Y\"";
			}else if("yyyy-dd-MM".equalsIgnoreCase(dateFormat)) {
				goalDateFormat = "\"%Y-%d-%m\"";
			}
			
			Query queryObj = null;
			
		String query =  "SELECT GROUP_CONCAT(Q1.GOAL_NAME) as goalName, " +
						"CONCAT(Q1.FIRST_NAME,' ',IFNULL(Q1.LAST_NAME,'')) as assignedUser, " +
						"Q1.FILE_URL as userImgUrl, " +
						"DATE_FORMAT(Q1.CREATED_DATE,"+goalDateFormat+") as assignedDate "+
						"FROM(" +
						"SELECT PG.GOAL_NAME, "+
				        "GA.ASSIGNED_USER_ID, "+
				        "U.FIRST_NAME, "+
						"U.LAST_NAME, "+
				        "U.FILE_URL,ga.CREATED_DATE "+
				        "FROM "+
				  		"PERFORMANCE_GOALS PG, "+ 
				  		"USER U,"+
				  		"PERFORMANCE_GOALS_ASSIGN_TO GA WHERE "+
				  		"PG.ID = GA.GOAL_ID and U.ID = GA.ASSIGNED_USER_ID "+
						"AND PG.GOAL_ID IN (SELECT ID  FROM PERFORMANCE_GOALS  WHERE UUID = :goalUUID ) AND GA.STATUS = '1' "+
				  
				  		"UNION "+
				  
						"SELECT PG.GOAL_NAME, "+
				        "GA.ASSIGNED_USER_ID, "+
				        "U.FIRST_NAME,U.LAST_NAME,U.FILE_URL,GA.CREATED_DATE FROM "+
				        "PERFORMANCE_GOALS PG, "+
				        "USER U, "+
				       	"PERFORMANCE_GOALS_ASSIGN_TO GA WHERE "+
				       	"PG.id = GA.GOAL_ID  and PG.UUID = :goalUUID "+
				       	"AND U.id = GA.ASSIGNED_USER_ID AND GA.STATUS = '1')Q1 "+
				  		"GROUP BY Q1.ASSIGNED_USER_ID ";
		
		queryObj = session.createSQLQuery(query);
		queryObj.setParameter("goalUUID", goalUUID);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGoalsAssignToDTO.class));
		goalsAssignToList = queryObj.list();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
  
		return goalsAssignToList;
	}

	@Override
	public double getPerformanceSubGoalsPercentageDoneCount(Long goalId) throws Exception {
	
		Session session = this.sessionFactory.getCurrentSession();
		
		
		String query = 	 "SELECT " + 
				"SUM(GOALS.PERCENTAGE_DONE) AS percentageDone " +
				"FROM " + 
				"PERFORMANCE_GOALS GOALS WHERE " + 
				"GOALS.GOAL_ID = :goalId AND GOALS.STATUS = '1' " ;
		
		List<BigInteger> countlist = session.createSQLQuery(query).setLong("goalId", goalId).list();
		double percentageDone = countlist.iterator().next().doubleValue();
		
		return percentageDone;
	}

	@Override
	public double getPerformanceParentGoalsPercentageDoneCountBySubGoalsPercentageDoneCount(Long goalId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoalsDTO> goalsAssignToList = null;
		
		String query = 	 "SELECT " + 
				"SUM(GOALS.PERCENTAGE_DONE) AS percentageDone, " +
				"COUNT(GOALS.ID) AS subGoalsCount " +
				"FROM " + 
				"PERFORMANCE_GOALS GOALS WHERE " + 
				"GOALS.GOAL_ID = :goalId AND GOALS.STATUS = '1' GROUP BY GOALS.GOAL_ID " ;
		
		Query queryObj = null;
		queryObj = session.createSQLQuery(query);
		queryObj.setParameter("goalId", goalId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGoalsDTO.class));
		goalsAssignToList = queryObj.list();
		double percentageDone = 0d;
		if(goalsAssignToList != null && !goalsAssignToList.isEmpty()) {
			PerformanceGoalsDTO goalsDTO = goalsAssignToList.get(0);
			percentageDone = goalsDTO.getPercentageDone() / goalsDTO.getSubGoalsCount().intValue();
		}
		
		return percentageDone;
	}

	@Override
	public List<TeamDTO> getPerformanceGoalsTeamList(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<TeamDTO> teamsList = null;
		
		try {
			
			Query queryObj = null;
			
			String query = 	 "SELECT " + 
					"T.ID AS id, " +
					"T.NAME AS name " + 
					"FROM " + 
					"TEAM T WHERE " + 
					"T.ORG_LOCATION_ID = :orgLocationId AND T.STATUS = '1' " ;
		
			queryObj = session.createSQLQuery(query);
			queryObj.setParameter("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(TeamDTO.class));
			teamsList = queryObj.list();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return teamsList;
	
	}

	@Override
	public String getTeamMemberUserIdsByTeamId(Long teamId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		StringBuilder performanceTeamMebersIds = new StringBuilder();
		
try {
			
			
			String teamMembersquery = "SELECT " + 
					"TM.MEMBER_USER_ID " +
					"FROM " + 
					"TEAM_MEMBERS TM WHERE " + 
					"TM.TEAM_ID = :teamId AND TM.STATUS = '1' " ;
			
			List<BigInteger> teamMebersList = null;
			teamMebersList = session.createSQLQuery(teamMembersquery).setLong("teamId", teamId).list();
			
			String teamMembers = StringUtils.join(teamMebersList, ',');
			
			String teamOwnerquery = "SELECT " + 
					"T.OWNER_USER_ID " +
					"FROM " + 
					"TEAM T WHERE " + 
					"T.ID = :teamId AND T.STATUS = '1' " ;
			
			List<BigInteger> teamOwnerList = null;
			teamOwnerList = session.createSQLQuery(teamOwnerquery).setLong("teamId", teamId).list();
			performanceTeamMebersIds = performanceTeamMebersIds.append(teamOwnerList.get(0).toString()).append(",").append(teamMembers);
		}catch (Exception e) {
			e.printStackTrace();
		}

		
		return performanceTeamMebersIds.toString();
	}

	@Override
	public List<PerformanceTeamMembersDTO> getPerformanceTeamMembersByTeamId(Long teamId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceTeamMembersDTO> teamMembersList = null;
		
		
		try {
		Query queryObj = null;
		
		String query =  "SELECT Q1.teamMemberId , " +
						"Q1.teamMemberName  " +
						"FROM(" +
						"SELECT TM.MEMBER_USER_ID AS teamMemberId , "+
				        "CONCAT(U.FIRST_NAME,' ',IFNULL(U.MIDDLE_NAME,''),' ',IFNULL(U.LAST_NAME,'')) AS teamMemberName "+
				        "FROM "+
				  		"TEAM_MEMBERS TM, "+ 
				  		"USER U,"+
				  		"TEAM T WHERE "+
				  		"TM.MEMBER_USER_ID = U.ID AND TM.TEAM_ID = :teamId "+
						"AND TM.STATUS = '1' "+
				  
				  		"UNION "+
				  
						"SELECT T.OWNER_USER_ID AS teamMemberId, "+
				        "CONCAT(U.FIRST_NAME,' ',IFNULL(U.MIDDLE_NAME,''),' ',IFNULL(U.LAST_NAME,'')) AS teamMemberName "+
				        "FROM "+
				        "TEAM T, "+
				        "USER U "+
				       	"WHERE "+
				       	"T.OWNER_USER_ID = U.ID AND T.ID = :teamId "+
				       	"AND T.STATUS = '1')Q1";
		
		queryObj = session.createSQLQuery(query);
		queryObj.setParameter("teamId", teamId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceTeamMembersDTO.class));
		teamMembersList = queryObj.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return teamMembersList;
		
	}

	@Override
	public List<PerformanceGoalsAssignToDTO> getReportingManagersUsersForAssigningGoals(Long reportingManagerId,
			Long orgLocationId) {
		
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoalsAssignToDTO> reportingManagersUsersList = null;
		Query queryObj = null;
		
		try {
		String query = "SELECT UR.USER_ID AS assignedUserId , "+
        "CONCAT(U.FIRST_NAME,' ',IFNULL(U.MIDDLE_NAME,''),' ',IFNULL(U.LAST_NAME,'')) AS assignedUser "+
        "FROM "+
  		"USER_ROLE UR, "+ 
  		"USER U,"+
  		"ORG_LOCATIONS OL WHERE "+
  		"UR.USER_ID = U.ID AND UR.ORG_LOCATIONS_ID = OL.ID AND "+
  		"UR.REPORTING_MANAGER_ID = :reportingManagerId AND UR.ORG_LOCATIONS_ID = :locationId "+
		"AND UR.USER_STATE <> 'NOT_LOGGEDIN' GROUP BY UR.USER_ID ";
		
		queryObj = session.createSQLQuery(query);
		queryObj.setParameter("reportingManagerId", reportingManagerId);
		queryObj.setParameter("locationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceGoalsAssignToDTO.class));
		reportingManagersUsersList = queryObj.list();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return reportingManagersUsersList;
	}

}
