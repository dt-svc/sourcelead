package com.bis.sourcelead.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.LetterHeadDao;
import com.bis.sourcelead.entity.LetterHead;

@Repository
public class LetterHeadDaoImpl implements LetterHeadDao{
	
	private static final Logger logger = LoggerFactory.getLogger(LetterHeadDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public LetterHead storeLetterHeadData(LetterHead letterHead)
	{
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (letterHead != null) {
				session.saveOrUpdate(letterHead);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return letterHead;
	}

	
}
