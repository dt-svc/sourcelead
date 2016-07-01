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
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.OrgLocations;

@Repository
public class OrgLocationsDaoImpl implements OrgLocationsDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations addOrgLocations(OrgLocations orgLocations) throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.save(orgLocations);
		}
		catch(HibernateException exception)	{
		}
		return orgLocations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations updateOrgLocations(OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
	    session.saveOrUpdate(orgLocations);
	    logger.info("Successfully updated..!");
		return orgLocations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocations(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		return (OrgLocations) session.get(OrgLocations.class, id);
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteOrgLocations(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		if (id != null) {
			OrgLocations orgLocations = (OrgLocations) session.get(OrgLocations.class, id);
			orgLocations.setStatus("0");
			session.saveOrUpdate(orgLocations);
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
	public List<OrgLocations> getAllOrgLocations() throws Exception {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLocations> orgLocationsList = null;
		Query citiesListQuery= session.createQuery("from OrgLocations");
		orgLocationsList=citiesListQuery.list();;
		return orgLocationsList;
	}
	
	

}
