package com.bis.sourcelead.reports.dao.Impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.EmailScenarioConfiguration;
import com.bis.sourcelead.reports.dao.CustomEmailConfigurationDao;

@Repository
public class CustomEmailConfigurationDaoImpl implements CustomEmailConfigurationDao{

	private static final Logger logger = LoggerFactory.getLogger(CustomEmailConfigurationDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmailScenarioConfiguration> getCustomEmailConfigurationByLocation(Long locationId) {
		logger.debug("In CustomEmailConfigurationDaoImpl class getCustomEmailConfigurationByLocation method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from EmailScenarioConfiguration cec where cec.id = :locationId";
		return  session.createQuery(query).setParameter("locationId", locationId).list();
		
	}

}
