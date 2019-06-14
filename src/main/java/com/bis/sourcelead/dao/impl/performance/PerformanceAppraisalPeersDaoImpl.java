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

import com.bis.sourcelead.dao.performance.PerformanceAppraisalPeersDao;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class PerformanceAppraisalPeersDaoImpl implements PerformanceAppraisalPeersDao{

	private static final Logger logger = LoggerFactory.getLogger(PerformanceAppraisalPeersDaoImpl.class);

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
	public PerformanceAppraisalPeers savePerformanceAppraisalPeers(PerformanceAppraisalPeers performanceAppraisalPeers)
			throws Exception {
		logger.debug("Adding savePerformanceAppraisalPeers");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (performanceAppraisalPeers != null) {
				session.saveOrUpdate(performanceAppraisalPeers);
			}
			logger.debug("savePerformanceAppraisalPeers Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"PERFORMANCE_APPRAISAL_PEERS"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return performanceAppraisalPeers;
	}

	@Override
	public PerformanceAppraisalPeers getPerformanceAppraisalPeersByUserIdAndOrgLocationId(Long userId,
			Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<PerformanceAppraisalPeers> performanceAppraisalPeersList = new ArrayList<PerformanceAppraisalPeers>();
		logger.debug("getPerformanceAppraisalPeersByUserIdAndOrgLocationId  start");
		performanceAppraisalPeersList = session.createQuery("from PerformanceAppraisalPeers peers where peers.userId.id = :userId and peers.orgLocationId.id = :orgLocationId " ).setLong( "userId", userId).setLong( "orgLocationId", orgLocationId).list();
		logger.debug("getPerformanceAppraisalPeersByUserIdAndOrgLocationId  end");
		if (performanceAppraisalPeersList.size() > 0) {
			return performanceAppraisalPeersList.get(0);
		} else {
			return null;
		}
	 }
}
