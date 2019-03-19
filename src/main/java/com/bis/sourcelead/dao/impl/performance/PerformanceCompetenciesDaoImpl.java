package com.bis.sourcelead.dao.impl.performance;

import java.text.MessageFormat;
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

import com.bis.sourcelead.dao.performance.PerformanceCompetenciesDao;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceCompetenciesDaoImpl implements PerformanceCompetenciesDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceCompetenciesDaoImpl.class);

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
	public PerformanceCompetencies savePerformanceCompetencies(PerformanceCompetencies performanceCompetencies)
			throws Exception {
		logger.debug("Adding PerformanceCompetencies");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceCompetencies != null) {
				session.saveOrUpdate(performanceCompetencies);
			}
			logger.debug("PerformanceCompetencies Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_TEMPLATE"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceCompetencies;
	}

	@Override
	public List<PerformanceCompetenciesDTO> getPerformanceCompetenciesListByOrgLocationId(Long orgLocationId)
			throws Exception {
		List<PerformanceCompetenciesDTO> competenciesDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
		query = "SELECT competencies.UUID AS uuid, "
				+ " competencies.COMPETENCIES_NAME AS name "
				+ " competencies.DESCRIPTION AS description "
				+ " FROM PERFORMANCE_COMPETENCIES competencies WHERE competencies.ORG_LOCATION_ID = :orgLocationId AND competencies.STATUS = '1' ";
		Query queryObj = session.createSQLQuery(query);
		queryObj.setLong("orgLocationId", orgLocationId);
		queryObj.setResultTransformer(Transformers.aliasToBean(PerformanceCompetenciesDTO.class));
		competenciesDTOList = queryObj.list();
		return competenciesDTOList;
	}

}
