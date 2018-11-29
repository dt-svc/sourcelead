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

import com.bis.sourcelead.dao.performance.PerformanceFeedbackTemplateDao;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.entity.performance.PerformanceFeedbackTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceFeedbackTemplateDaoImpl implements PerformanceFeedbackTemplateDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceFeedbackTemplateDaoImpl.class);

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

	@Value("${jdbc.schema}")
	private String schema;

	@Override
	public PerformanceFeedbackTemplate savePerformanceFeedbackTemplate(
			PerformanceFeedbackTemplate performanceFeedbackTemplate) throws Exception {
		logger.debug("Adding PerformanceFeedbackTemplate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceFeedbackTemplate != null) {
				session.saveOrUpdate(performanceFeedbackTemplate);
			}
			logger.debug("PerformanceFeedbackTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_FEEDBACK_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceFeedbackTemplate;
	}

	@Override
	public PerformanceFeedbackTemplate getPerformanceFeedbackTemplateById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceFeedbackTemplate> performanceFeedbackTemplate = new ArrayList<PerformanceFeedbackTemplate>();
		logger.debug("getPerformanceFeedbackTemplateById  start");
		performanceFeedbackTemplate = session.createQuery("from PerformanceFeedbackTemplate template where template.id = :id " ).setLong( "id", id).list();
		logger.debug("getPerformanceFeedbackTemplateById  end");
		if (performanceFeedbackTemplate.size() > 0) {
			return performanceFeedbackTemplate.get(0);
		} else {
			return null;
		}
	}
}
