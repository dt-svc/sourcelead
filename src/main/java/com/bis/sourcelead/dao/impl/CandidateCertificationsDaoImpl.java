package com.bis.sourcelead.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.entity.CandidateCertifications;

/**
 * @author Shivayogi
 * @date 25th August 2016
 */

@Repository
public class CandidateCertificationsDaoImpl implements CandidateCertificationsDao {

	private static final Logger logger = LoggerFactory.getLogger(CandidateCertificationsDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public CandidateCertifications storeCandidateCertifications(JSONObject jsonObject) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(jsonObject);
		logger.debug("In CandidateCertificationsDaoImpl class store method");
		return storeCandidateCertifications(jsonObject);
	}
}