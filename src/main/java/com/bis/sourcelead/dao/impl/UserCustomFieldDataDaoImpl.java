package com.bis.sourcelead.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.UserCustomFieldDataDao;
import com.bis.sourcelead.entity.UserCustomFieldData;

@Repository
public class UserCustomFieldDataDaoImpl implements UserCustomFieldDataDao{
	
	private static final Logger logger = LoggerFactory.getLogger(UserCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public UserCustomFieldData saveUserCustomFieldData(UserCustomFieldData userCustomFields) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (userCustomFields != null) {
				session.saveOrUpdate(userCustomFields);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return userCustomFields;
	}

}
