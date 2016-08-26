package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
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
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.exception.NoRecordsFoundException;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

@Repository
public class OrgLocationsDaoImpl implements OrgLocationsDao {

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

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationsDaoImpl.class);

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations addOrgLocations(OrgLocations orgLocations) throws Exception {
		logger.debug("Adding orgLocations");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgLocations != null) {
				session.save(orgLocations);
			}
			logger.debug("orgLocations Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ORGLOCATIONS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
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
		OrgLocations orgLocations = session.get(OrgLocations.class, id);
		return orgLocations;
		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Boolean deleteOrgLocations(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
		boolean isDeleted = false;
		try{
			if (id != null) {
				OrgLocations orgLocations = (OrgLocations) session.get(OrgLocations.class, id);
				orgLocations.setStatus(Status.INACTIVE);
				session.saveOrUpdate(orgLocations);
				isDeleted = true;
				logger.info("Successfully deleted..!");
			}
			else {
				isDeleted = false;
			}
			
		}catch(Exception e){
			StringBuilder message= new StringBuilder(MessageFormat.format(statusDelete,"ORGLOCATIONS"));
			throw new SourceLeadObjectNotFoundException(message.toString());	
		}
		
		return isDeleted;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgLocations> getAllOrgLocations() throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLocations> orgLocationsList = null;
		Query citiesListQuery= session.createQuery("from OrgLocations");
		orgLocationsList=citiesListQuery.list();
		return orgLocationsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocationsByLocationCode(String locationCode) throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgLocations orgLocations where  orgLocations.locationCode =:locationCode");
		  
	       List<OrgLocations> orgLocations = session.createQuery(query.toString()).setString("locationCode", locationCode).setCacheable(true).list();
	     
	        OrgLocations orgLocation = null;
			if(orgLocations != null && !orgLocations.isEmpty())
			{
				orgLocation = (OrgLocations) orgLocations.iterator().next();
			}
			
			return orgLocation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgLocations getOrgLocationsByOrgCode(String orgCode) throws Exception {
		

		  Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgLocations orgLocations where  orgLocations.org =:orgCode");
		  
	       List<OrgLocations> orgLocations = session.createQuery(query.toString()).setString("orgCode", orgCode).list();
	       
	       OrgLocations orgLocation = null;
			if(orgLocations != null && !orgLocations.isEmpty())
			{
				orgLocation = (OrgLocations) orgLocations.iterator().next();
			}
			
			return orgLocation;
		
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public OrgLocations findByEmail(String email, Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgLocations> orgLocations = new ArrayList<OrgLocations>();
		logger.debug("findByEmail start");
		try{
			orgLocations = session.createQuery("from OrgLocations ol where ol.email=:email and ol.id(select orgRelations.responderLocationId.org.id from OrgRelations orgRelations"+" where orgRelations.locationId.id = :orgLocationId)" ).setString( "email", email ).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
			
			logger.debug("findByEmail end");
			if (orgLocations.size() > 0) {
				return orgLocations.get(0);
			} else {
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
		
   	}

}
