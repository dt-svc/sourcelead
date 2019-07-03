package com.bis.sourcelead.dao.impl.performance;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalUserFeedbackDao;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalUserFeedback;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceAppraisalUserFeedbackDaoImpl implements PerformanceAppraisalUserFeedbackDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalUserFeedbackDaoImpl.class);

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
	public void savePerformanceAppraisalUserFeedback(PerformanceAppraisalUserFeedback performanceAppraisalUserFeedback)
			throws Exception {
		
		logger.debug("Adding PerformanceAppraisalUserFeedback");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalUserFeedback != null) {
				session.saveOrUpdate(performanceAppraisalUserFeedback);
			}
			logger.debug("PerformanceAppraisalUserFeedback Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_USER_FEEDBACK"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		
	}

	@Override
	public PerformanceAppraisalUserFeedback getPerformanceAppraisalUserFeedbackAssignedUserId(Long userId,
			 Long appraisalAssignedId,Long feedbackTemplateId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalUserFeedback> performanceAppraisalUserFeedback = new ArrayList<PerformanceAppraisalUserFeedback>();
		logger.debug("getPerformanceAppraisalUserFeedbackAssignedUserId  start");
		performanceAppraisalUserFeedback = session.createQuery("from PerformanceAppraisalUserFeedback feedback where "
				+ " feedback.userId.id = :userId and feedback.appraisalAssignedId = :appraisalAssignedId and feedback.feedbackTemplateId = :feedbackTemplateId " ).setLong("appraisalAssignedId", appraisalAssignedId).setLong( "feedbackTemplateId", feedbackTemplateId).setLong( "userId", userId).list();
		logger.debug("getPerformanceAppraisalUserFeedbackAssignedUserId  end");
		if (performanceAppraisalUserFeedback.size() > 0) {
			return performanceAppraisalUserFeedback.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Integer getFeedbackCategoriesScore(Long userId, Long appraisalAssignedId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String categoryCountQuery =  "SELECT "
				+" SUM(FEEDBACK.SCORE) " 
				+" FROM PERFORMANCE_APPRAISAL_USER_FEEDBACK FEEDBACK "
				+" WHERE FEEDBACK.APPRAISAL_ASSIGNED_ID = :appraisalAssignedId "
				+" AND FEEDBACK.USER_ID = :userId ";
		
		Query categoryCountQueryObj = session.createSQLQuery(categoryCountQuery);
		categoryCountQueryObj.setLong("userId", userId);
		categoryCountQueryObj.setLong("appraisalAssignedId", appraisalAssignedId);
		BigDecimal categoriesTotalCount = (BigDecimal) categoryCountQueryObj.uniqueResult();
		
		return categoriesTotalCount.intValue();
	}
}
