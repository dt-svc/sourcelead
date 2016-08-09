package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.CitiesDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.exception.NoRecordsFoundException;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class CitiesDaoImpl implements CitiesDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
	private static final Logger logger = LoggerFactory.getLogger(CitiesDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Cities addCity(Cities cities) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if(cities != null){
				session.save(cities);
			}
		}
		catch(Exception exception)	{
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"Cities"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return cities;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Cities updateCity(Cities cities) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		if(cities != null){
			 session.saveOrUpdate(cities);
		}
		if(cities == null){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"Cities"));
			throw new SourceLeadObjectNotFoundException(message.toString());
		}
	    return cities;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Cities getCity(String id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (Cities) session.get(Cities.class, id);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteCity(String id) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		try{
			Cities cities = (Cities) session.get(Cities.class, id);
			cities.setCityName("Vizag");
			session.saveOrUpdate(cities);
			isDeleted = true;
			logger.info("Successfully deleted..!");
		}catch(Exception exception){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"Cities"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		return isDeleted;
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Cities> getAllCities() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<Cities> cityList = null;
		Query citiesListQuery= session.createQuery("from Cities");
		cityList=citiesListQuery.list();;
		return cityList;
	}

}
