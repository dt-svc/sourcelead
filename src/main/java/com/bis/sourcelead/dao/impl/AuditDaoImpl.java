package com.bis.sourcelead.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.AuditDao;
import com.bis.sourcelead.entity.AuditRecord;

@Repository
@Transactional
public class AuditDaoImpl implements AuditDao {
	private static final Logger logger = LoggerFactory.getLogger(AuditDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveAuditReport(AuditRecord audit) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.saveOrUpdate(audit);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
}
