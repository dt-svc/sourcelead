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

import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceAppraisalTemplateDaoImpl implements PerformanceAppraisalTemplateDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalTemplateDaoImpl.class);

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
	public PerformanceAppraisalTemplate savePerformanceAppraisalTemplate(
			PerformanceAppraisalTemplate performanceAppraisalTemplate) throws Exception {
		logger.debug("Adding PerformanceAppraisalTemplate");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalTemplate != null) {
				session.saveOrUpdate(performanceAppraisalTemplate);
			}
			logger.debug("PerformanceAppraisalTemplate Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceAppraisalTemplate;
	}

	@Override
	public List<PerformanceAppraisalTemplate> getPerformanceAppraisalTemplateList(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PerformanceAppraisalTemplate template where template.orgLocationId.id =:orgLocationId and template.status ='1' ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	public PerformanceAppraisalTemplate getPerformanceAppraisalTemplateById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalTemplate> performanceAppraisalTemplate = new ArrayList<PerformanceAppraisalTemplate>();
		logger.debug("getPerformanceAppraisalTemplateById  start");
		performanceAppraisalTemplate = session.createQuery("from PerformanceAppraisalTemplate template where template.id = :id " ).setLong( "id", id).list();
		logger.debug("getPerformanceAppraisalTemplateById  end");
		if (performanceAppraisalTemplate.size() > 0) {
			return performanceAppraisalTemplate.get(0);
		} else {
			return null;
		}
	 }
}
