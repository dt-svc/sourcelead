package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.performance.PerformanceRatingScaleDao;
import com.bis.sourcelead.entity.performance.PerformanceRatingScale;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceRatingScaleDaoImpl implements PerformanceRatingScaleDao {
	
	

	private static final Logger logger = LoggerFactory.getLogger(PerformanceRatingScaleDaoImpl.class);

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
	public PerformanceRatingScale savePerformanceRatingScale(PerformanceRatingScale performanceRatingScale)
			throws Exception {
		logger.debug("Adding PerformanceRatingScale");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceRatingScale != null) {
				session.saveOrUpdate(performanceRatingScale);
			}
			logger.debug("PerformanceRatingScale Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_RATING_SCALE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceRatingScale;
	}
	


}
