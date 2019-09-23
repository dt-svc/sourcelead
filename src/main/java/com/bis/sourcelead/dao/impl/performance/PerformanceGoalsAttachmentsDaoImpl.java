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

import com.bis.sourcelead.dao.performance.PerformanceGoalsAttachmentsDao;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAssignTo;
import com.bis.sourcelead.entity.performance.PerformanceGoalsAttachments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.util.CommonUtil;

@Repository
@Transactional
public class PerformanceGoalsAttachmentsDaoImpl implements PerformanceGoalsAttachmentsDao{
	
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceGoalsAttachmentsDaoImpl.class);

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
	public PerformanceGoalsAttachments getPerformanceGoalsAttachmentsByUUID(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceGoalsAttachments> performanceGoalsAttachments = new ArrayList<PerformanceGoalsAttachments>();
		logger.debug("getPerformanceGoalsAttachmentsByUUID  start");
		performanceGoalsAttachments = session.createQuery("from PerformanceGoalsAttachments attachments where "
				+ " attachments.uuid = :uuid " ).setString("uuid", uuid).list();
		logger.debug("getPerformanceGoalsAttachmentsByUUID  end");
		if (performanceGoalsAttachments.size() > 0) {
			return performanceGoalsAttachments.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PerformanceGoalsAttachments savePerformanceGoalsAttachments(
			PerformanceGoalsAttachments performanceGoalsAttachments) throws Exception {
		

		logger.debug("Adding savePerformanceGoalsAttachments");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceGoalsAttachments != null) {
				session.saveOrUpdate(performanceGoalsAttachments);
			}
			logger.debug("savePerformanceGoalsAttachments Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_GOALS_ATTACHMENTS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceGoalsAttachments;
		
		
	}

}
