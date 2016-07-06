package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.EmailScenarioDao;
import com.bis.sourcelead.entity.EmailScenario;

/**
 * 
 * @author Prasad salina
 *
 */
@Repository
public class EmailScenarioDaoImpl implements EmailScenarioDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailScenarioDaoImpl.class);

	/**
	 * This method for get the EmailScenario
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode(String uniqueScenarioName,
			String orgCode, String locationCode) throws Exception {
		logger.debug("In EmailScenarioDaoImpl class getEmailScenarioByUniqueNameAndOrgCodeAndLocationsCode method");
		
		Session session = this.sessionFactory.getCurrentSession();
		
		String query = "from EmailScenario es where es.uniqueScenarioName = :uniqueScenarioName and es.locationCode = :locationCode and es.orgCode =:orgCode";
		 
		List<EmailScenario> emailScenariosList =  session.createQuery(query.toString()) .setString("uniqueScenarioName", uniqueScenarioName)
																												.setString("locationCode", locationCode)
																												.setString("orgCode", orgCode).list();
		EmailScenario emailScenario = null;
		if(emailScenariosList != null && !emailScenariosList.isEmpty())
		{
			emailScenario = (EmailScenario) emailScenariosList.iterator().next();
		}
		return emailScenario;
	}

	/**
	 * This method for save the EmailScenario
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public EmailScenario save(EmailScenario scenario) throws Exception {
		logger.debug("In EmailScenarioDaoImpl class save method");
		Session session = this.sessionFactory.getCurrentSession();
		session.save(scenario);
		return scenario;	
	}

}
