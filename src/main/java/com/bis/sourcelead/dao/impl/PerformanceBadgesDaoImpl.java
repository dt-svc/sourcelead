package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.PerformanceBadgesDao;
import com.bis.sourcelead.entity.PerformanceBagdes;

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
	public List<PerformanceBagdes> getPerformanceBadgesList(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PerformanceBagdes badges where badges.orgLocationId.id =:orgLocationId and badges.status ='1' ";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

}
