package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CitiesDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.OrgLocations;

@Repository
public class CitiesDaoImpl implements CitiesDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(CitiesDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Cities addCity(Cities cities) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(cities);
		}
		catch(HibernateException exception)	{
		}
		return cities;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Cities updateCity(Cities cities) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(cities);
	    return cities;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Cities getCity(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return (Cities) session.get(Cities.class, id);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteCity(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		if (id != null) {
			Cities cities = (Cities) session.get(Cities.class, id);
			cities.setCityName("Vizag");
			session.saveOrUpdate(cities);
			isDeleted = true;
			logger.info("Successfully deleted..!");
		}
		else {
			isDeleted = false;
		}
		return isDeleted;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Cities> getAllCities() throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<Cities> cityList = null;
		Query citiesListQuery= session.createQuery("from Cities");
		cityList=citiesListQuery.list();;
		return cityList;
	}

}
