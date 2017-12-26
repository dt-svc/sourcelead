package com.bis.sourcelead.dao.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.NewContractCustomFieldDataDao;
import com.bis.sourcelead.entity.NewContractCustomFieldData;

@Repository
public class NewContractCustomFieldDataDaoImpl implements NewContractCustomFieldDataDao {

	private static final Logger logger = LoggerFactory.getLogger(NewContractCustomFieldDataDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public NewContractCustomFieldData saveNewContractCustomFieldData(NewContractCustomFieldData contractCustomFields) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(contractCustomFields);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return contractCustomFields;
	}

}
