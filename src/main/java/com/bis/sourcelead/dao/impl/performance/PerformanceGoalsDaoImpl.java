package com.bis.sourcelead.dao.impl.performance;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
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

import com.bis.sourcelead.dao.performance.PerformanceGoalsDao;
import com.bis.sourcelead.dto.performance.PerformanceGoalsDoNutChartCountsDTO;
import com.bis.sourcelead.entity.RecruiterReportAssociateCountView;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.entity.performance.PerformanceGoals;
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
		performanceGoals = session.createQuery("from PerformanceGoals goals where goals.id = :id " ).setLong( "id", goalId).list();
		logger.debug("getPerformanceGoalsById  end");
		if (performanceGoals.size() > 0) {
			return performanceGoals.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PerformanceGoalsDoNutChartCountsDTO getTotalGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
					+ " GOALS.CREATED_BY = :userId AND "
				    + " ASSIGN.ASSIGNED_USER_ID <> :userId AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.GOAL_ID";
			 
		        
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
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
							+ " GOALS.CREATED_BY <> :userId AND "
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
	public PerformanceGoalsDoNutChartCountsDTO getInprogressGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
					+ " GOALS.CREATED_BY = :userId AND "
					+ " (GOALS.PERCENTAGE_DONE > '0' AND GOALS.PERCENTAGE_DONE < '100') AND "
				    + " ASSIGN.ASSIGNED_USER_ID <> :userId AND "
				    + " DATEDIFF(GOALS.END_DATE,CURDATE()) >= 0 AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.GOAL_ID";
			 
		        
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
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
							+ " GOALS.CREATED_BY <> :userId AND "
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
	public PerformanceGoalsDoNutChartCountsDTO getCompletedGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
					+ " GOALS.CREATED_BY = :userId AND "
					+ " GOALS.PERCENTAGE_DONE = '100' AND "
				    + " ASSIGN.ASSIGNED_USER_ID <> :userId AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.GOAL_ID";
			 
		        
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
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
							+ " GOALS.CREATED_BY <> :userId AND "
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
	public PerformanceGoalsDoNutChartCountsDTO getOverDueGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
					+ " GOALS.CREATED_BY = :userId AND "
					+ " ((GOALS.PERCENTAGE_DONE > '0' AND GOALS.PERCENTAGE_DONE < '100') || (GOALS.PERCENTAGE_DONE = '0') || (GOALS.PERCENTAGE_DONE IS NULL)) AND "
				    + " ASSIGN.ASSIGNED_USER_ID <> :userId AND "
				    + " DATEDIFF(GOALS.END_DATE,CURDATE()) < 0 AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.GOAL_ID";
			 
		        
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
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
							+ " GOALS.CREATED_BY <> :userId AND "
							+ " ((GOALS.PERCENTAGE_DONE > '0' AND GOALS.PERCENTAGE_DONE < '100') || (GOALS.PERCENTAGE_DONE = '0') || (GOALS.PERCENTAGE_DONE IS NULL)) AND "
						    + " ASSIGN.ASSIGNED_USER_ID = :userId AND "
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
	public PerformanceGoalsDoNutChartCountsDTO getNotStartedGoalsDoNutChartCounts(Long userId, Long orgLocationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		PerformanceGoalsDoNutChartCountsDTO totalCountDTO = new PerformanceGoalsDoNutChartCountsDTO();
		String selfQuery ="SELECT"
				+ " COUNT(DISTINCT ASSIGN.GOAL_ID) AS REQ_COUNT"
				+ " FROM"
				    + " PERFORMANCE_GOALS_ASSIGN_TO ASSIGN,"
				    + " PERFORMANCE_GOALS GOALS,"
				    + " ORG_LOCATIONS OL"
				    
					+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
					+ " GOALS.CREATED_BY = :userId AND "
					+ " ((GOALS.PERCENTAGE_DONE = '0') || (GOALS.PERCENTAGE_DONE IS NULL)) AND "
				    + " ASSIGN.ASSIGNED_USER_ID <> :userId AND "
				    + " DATEDIFF(GOALS.END_DATE,CURDATE()) >= 0 AND "
				    + " OL.ID = :orgLocationId"
				    + " GROUP BY ASSIGN.GOAL_ID";
			 
		        
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
						    
							+ " WHERE  OL.ID = ASSIGN.ORG_LOCATION_ID AND "
							+ " GOALS.CREATED_BY <> :userId AND "
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

}
