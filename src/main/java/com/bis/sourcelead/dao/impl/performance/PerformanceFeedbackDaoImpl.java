package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.performance.PerformanceFeedbackDao;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalUserFeedback;
import com.bis.sourcelead.entity.performance.PerformanceFeedback;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceFeedbackDaoImpl implements PerformanceFeedbackDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceFeedbackDaoImpl.class);

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
	public void savePerformanceFeedback(PerformanceFeedback performanceFeedback) throws Exception {
		
		logger.debug("Adding PerformanceFeedback");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceFeedback != null) {
				session.saveOrUpdate(performanceFeedback);
			}
			logger.debug("PerformanceFeedback Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_FEEDBACK"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@Override
	public PerformanceFeedback getPerformanceFeedbackByAssignedUserId(Long userId, Long appraisalAssignedId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceFeedback> performanceFeedback = new ArrayList<PerformanceFeedback>();
		logger.debug("getPerformanceFeedbackByAssignedUserId  start");
		performanceFeedback = session.createQuery("from PerformanceFeedback feedback where "
				+ " feedback.userId.id = :userId and feedback.appraisalAssignedId = :appraisalAssignedId " ).setLong("appraisalAssignedId", appraisalAssignedId).setLong( "userId", userId).list();
		logger.debug("getPerformanceFeedbackByAssignedUserId  end");
		if (performanceFeedback.size() > 0) {
			return performanceFeedback.get(0);
		} else {
			return null;
		}
	
	}

}
