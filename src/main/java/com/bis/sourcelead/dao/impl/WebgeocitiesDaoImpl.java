package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.WebgeocitiesDao;
import com.bis.sourcelead.entity.Webgeocities;

/**
 * 
 * @author Prasad Salina
 *
 */
@Repository
public class WebgeocitiesDaoImpl implements WebgeocitiesDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);



	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Webgeocities> getAllCitiesByStateCodeCountryCode(List<String> stateCodes, String countryCode) throws Exception {
		logger.debug("In StatesDaoImpl getAllStatesByCountryCode method");
		Session session = this.sessionFactory.getCurrentSession();
		Query query= session.createQuery("from Webgeocities cities where cities.stateCode IN :stateCodes and cities.countryCode = :countryCode");
		List<Webgeocities> citiesList = query.setParameterList("stateCodes", stateCodes).setString("countryCode", countryCode).setCacheable(true).list();
		return citiesList;
	}

}
