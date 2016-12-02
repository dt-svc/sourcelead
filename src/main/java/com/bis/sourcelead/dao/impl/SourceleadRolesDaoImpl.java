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

import com.bis.sourcelead.dao.SourceleadRolesDao;
import com.bis.sourcelead.entity.SourceleadRoles;

@Repository
public class SourceleadRolesDaoImpl implements SourceleadRolesDao {
	
	
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

	private static final Logger logger = LoggerFactory.getLogger(SourceleadRolesDaoImpl.class);

	@Override
	@Transactional
	public List<SourceleadRoles> getSourceleadRoles() {
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from SourceleadRoles sourceleadRoles where sourceleadRoles.status = '1' ").setCacheable(true).list();
	}

}
