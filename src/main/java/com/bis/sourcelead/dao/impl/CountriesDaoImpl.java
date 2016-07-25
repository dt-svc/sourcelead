package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CountriesDao;
import com.bis.sourcelead.entity.Countries;

@Repository
public class CountriesDaoImpl implements CountriesDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Countries getCountriesByCountryName(String name) {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<Countries> countires = new ArrayList<Countries>();
		logger.debug("findByUserName start");
		countires = session.createQuery("from Countries where name = :name" ).setString( "name", name ).list();
		logger.debug("findByUserName end");
		if (countires.size() > 0) {
			return countires.get(0);
		} else {
			return null;
		}
   	}

}
