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

import com.bis.sourcelead.dao.performance.PerformanceAppraisalAssignedAssociatesDao;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalAssignedAssociates;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceAppraisalAssignedAssociatesDaoImpl implements PerformanceAppraisalAssignedAssociatesDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalAssignedAssociatesDaoImpl.class);

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
	public PerformanceAppraisalAssignedAssociates savePerformanceAppraisalAssignedAssociates(
			PerformanceAppraisalAssignedAssociates performanceAppraisalAssignedAssociates) throws Exception {
		
		logger.debug("Adding savePerformanceAppraisalAssignedAssociates");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalAssignedAssociates != null) {
				session.saveOrUpdate(performanceAppraisalAssignedAssociates);
			}
			logger.debug("savePerformanceAppraisalAssignedAssociates Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_ASSIGNED_ASSOCIATES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceAppraisalAssignedAssociates;
	}
}
