package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.measure.unit.SystemOfUnits;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.performance.PerformanceGoalsAssignToDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;
import com.itextpdf.kernel.log.SystemOutCounter;

@Repository
@Transactional
public class PerformanceGoalsAssignToDaoImpl implements PerformanceGoalsAssignToDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceGoalsAssignToDaoImpl.class);

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
	public PerformanceGoalsAssignTo getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId(Long assignedUserId,
			Long goalId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoalsAssignTo> performanceGoalsAssignTo = new ArrayList<PerformanceGoalsAssignTo>();
		logger.debug("getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId  start");
		performanceGoalsAssignTo = session.createQuery("from PerformanceGoalsAssignTo assignTo where "
				+ " assignTo.assignedUserId.id = :assignedUserId and assignTo.goalId.id = :goalId and assignTo.status = '1' " ).setLong("assignedUserId", assignedUserId).setLong( "goalId", goalId).list();
		logger.debug("getPerformanceGoalsAssignToByAssignedUserIdAndOrgLocationId  end");
		if (performanceGoalsAssignTo.size() > 0) {
			return performanceGoalsAssignTo.get(0);
		} else {
			return null;
		}
	}
	
	
	@Override
	public PerformanceGoalsAssignTo savePerformanceGoalsAssignTo(PerformanceGoalsAssignTo performanceGoalsAssignTo)
			throws Exception {
		

		logger.debug("Adding PerformanceGoalsAssignTo");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceGoalsAssignTo != null) {
				session.saveOrUpdate(performanceGoalsAssignTo);
			}
			logger.debug("PerformanceGoalsAssignTo Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_GOALS_ASSIGN_TO"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceGoalsAssignTo;
		
		
	}


	@Override
	public List<PerformanceGoalsAssignTo> getPerformanceGoalsAssignToListByGoalId(Long goalId) throws Exception {
	
		logger.debug("In getPerformanceGoalsAssignToListByGoalId method");
		Session session = this.sessionFactory.getCurrentSession();

		String query = "from PerformanceGoalsAssignTo assignTo where assignTo.goalId.id = :goalId and assignTo.status = '1' ";
		List<PerformanceGoalsAssignTo> performanceGoalsAssignToList = session.createQuery(query).setLong("goalId", goalId).list();
		return performanceGoalsAssignToList;
	}

}
