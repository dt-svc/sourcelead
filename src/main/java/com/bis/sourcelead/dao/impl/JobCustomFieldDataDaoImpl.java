package com.bis.sourcelead.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.JobCustomFieldDataDao;
import com.bis.sourcelead.entity.JobCustomFieldData;

@Repository
public class JobCustomFieldDataDaoImpl implements  JobCustomFieldDataDao{

	private static final Logger logger = LoggerFactory.getLogger(JobCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public JobCustomFieldData savejobCustomFieldData(JobCustomFieldData jobCustomFields) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (jobCustomFields != null) {
				session.saveOrUpdate(jobCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return jobCustomFields;
	}

}
