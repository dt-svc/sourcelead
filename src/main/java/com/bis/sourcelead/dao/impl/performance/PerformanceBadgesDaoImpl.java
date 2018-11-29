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

import com.bis.sourcelead.dao.performance.PerformanceBadgesDao;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceBadgesDaoImpl implements PerformanceBadgesDao{
	
	private static final Logger logger = LoggerFactory.getLogger(PerformanceBadgesDaoImpl.class);

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
	public List<PerformanceBadges> getPerformanceBadgesList(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PerformanceBadges badges where badges.orgLocationId.id =:orgLocationId and badges.status ='1' ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	public PerformanceBadges storePerformanceBadges(PerformanceBadges performanceBadges) throws Exception {
		logger.debug("Adding PerformanceBadges");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceBadges != null) {
				session.saveOrUpdate(performanceBadges);
			}
			logger.debug("PerformanceBadges Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_BADGES"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceBadges;
	}

	@Override
	public PerformanceBadges getPerformanceBadgesById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceBadges> performanceBadges = new ArrayList<PerformanceBadges>();
		logger.debug("getPerformanceBadgesById  start");
		performanceBadges = session.createQuery("from PerformanceBadges badges where badges.id = :id " ).setLong( "id", id).list();
		logger.debug("getPerformanceBadgesById  end");
		if (performanceBadges.size() > 0) {
			return performanceBadges.get(0);
		} else {
			return null;
		}
	}

}
