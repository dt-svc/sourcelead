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

import com.bis.sourcelead.dao.performance.PerformanceTagCompetenciesDao;
import com.bis.sourcelead.dto.performance.PerformanceTagCompetenciesDTO;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.entity.performance.PerformanceTagCompetencies;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceTagCompetenciesDaoImpl implements PerformanceTagCompetenciesDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceTagCompetenciesDaoImpl.class);

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
	public PerformanceTagCompetencies savePerformanceTagCompetencies(
			PerformanceTagCompetencies performanceTagCompetencies) throws Exception {
		logger.debug("Adding PerformanceTagCompetencies");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceTagCompetencies != null) {
				session.saveOrUpdate(performanceTagCompetencies);
			}
			logger.debug("PerformanceTagCompetencies Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_TAG_COMPETENCIES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceTagCompetencies;
	}

	@Override
	public PerformanceTagCompetencies getPerformanceTagCompetenciesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceTagCompetencies> performanceCompetenciesList = new ArrayList<PerformanceTagCompetencies>();
		logger.debug("getPerformanceTagCompetenciesById  start");
		performanceCompetenciesList = session.createQuery("from PerformanceTagCompetencies tagCompetencies where tagCompetencies.id = :id " ).setLong( "id", id).list();
		logger.debug("getPerformanceTagCompetenciesById  end");
		if (performanceCompetenciesList.size() > 0) {
			return performanceCompetenciesList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<PerformanceTagCompetencies> getPerformanceTagCompetenciesList(Long orgLocationId) throws Exception {Session session = this.sessionFactory.getCurrentSession();
	String query = "from PerformanceTagCompetencies tagCompetencies where tagCompetencies.orgLocationId.id =:orgLocationId and tagCompetencies.status ='1' ";
	return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();}
}
