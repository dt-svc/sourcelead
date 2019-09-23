package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RequirementLocationsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
public class RequirementLocationsDaoImpl implements RequirementLocationsDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	

	private static final Logger logger = LoggerFactory.getLogger(RequirementLocationsDaoImpl.class);

	@Override
	public RequirementLocations saveRequirementLocations(RequirementLocations requirementLocations) throws Exception {
		
		logger.debug("Adding UserRole");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementLocations != null) {
				session.saveOrUpdate(requirementLocations);
			}
			logger.debug("RequirementLocations Added Successfully");
		} catch (Exception exception) {
			StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"RequirementLocations"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return requirementLocations;
	}

	@Override
	public List<RequirementLocations> getRequirementLocationsListByRequirementId(Long requirementId) throws Exception {
		 
		logger.debug("In getRequirementLocationsListByRequirementId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from RequirementLocations rl  where rl.requirementId.id = :requirementId ";  
		List<RequirementLocations> requirementLocationsList = session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		return requirementLocationsList;
		 
	}

	

	@Override
	public List<Object[]> getRequirementLocationsListByRequirementIdMap(List<Long> reqIdsList) throws Exception {

		logger.debug("In getRequirementLocationsListByRequirementId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "SELECT RL.REQUIREMENT_ID, RL.LOCATION  FROM REQUIREMENT_LOCATIONS RL, REQUIREMENT R  WHERE RL.REQUIREMENT_ID  = R.ID AND  RL.REQUIREMENT_ID IN(:idsList)";
		List<Object[]> requirementLocationsList =  (List<Object[]>)session.createSQLQuery(query).setParameterList("idsList", reqIdsList).setCacheable(true).list();
		return requirementLocationsList;

	}
	@Override
	public List<String> getRequirementLocationsUUIDByRequirementId(Long requirementId) throws Exception{
		
		logger.debug("In getRequirementLocationsUUIDByRequirementId method");
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select  rl.locationUUID from RequirementLocations rl  where rl.requirementId.id = :requirementId ";  
		List<String> requirementLocationsUUIDList = session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		return requirementLocationsUUIDList;
		
	}

	@Override
	public RequirementLocations getRequirementLocationsByLocationUUID(String locationUUID) throws Exception {
		List<RequirementLocations> location = new ArrayList<RequirementLocations>();
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("getRequirementLocationsByLocationUUID start");
		location = session.createQuery("from RequirementLocations rl left join fetch rl.requirementId where rl.locationUUID = :locationUUID" ).setCacheable(true).setString("locationUUID", locationUUID ).setCacheable(true).list();
		logger.debug("getRequirementLocationsByLocationUUID end");
		if (location.size() > 0) {
			return location.get(0);
		} else {
			return null;
		}
	}

	

	@Override
	public void deleteRequirementLocations(RequirementLocations requirementLocations) throws Exception {
		logger.debug("deleting RequirementLocations");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (requirementLocations != null) {
				session.delete(requirementLocations);
			}
			logger.debug("RequirementLocations deleted Successfully");
		} catch (Exception exception) {
			
		}
		
	}

	@Override
	public List<RequirementLocations> getAllRequirementByOrgLocationId(Long orgLocationId) throws Exception
	{
		List<RequirementLocations> location = new ArrayList<RequirementLocations>();
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug("getRequirementLocationsByLocationUUID start");
		List<RequirementLocations> locationlist= session.createQuery("from RequirementLocations rl left join fetch rl.requirementId r where r.id in(select id from Requirement req where req.orgLocationId = :orgLocationId)" ).setCacheable(true).setLong("orgLocationId", orgLocationId ).setCacheable(true).list();
	
			return locationlist;
	}

	@Override
	public List<String> getRequirementLocationsStringListByRequirementId(Long requirementId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "select  rl.location from RequirementLocations rl  where rl.requirementId.id = :requirementId ";  
		List<String> requirementLocationsUUIDList = session.createQuery(query).setLong("requirementId", requirementId).setCacheable(true).list();
		return requirementLocationsUUIDList;
	}

}
