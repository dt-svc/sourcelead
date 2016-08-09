package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.Collection;
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

import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.exception.NoRecordsFoundException;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;

/**
 * 
 * @author PrasadK
 * @date 08th jul 2016
 *
 */
@Repository
public class OrgRelationsDaoImpl implements OrgRelationsDao {
	private static final Logger logger = LoggerFactory.getLogger(OrgRelationsDaoImpl.class);
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
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations addOrgRelations(OrgRelations orgRelations) throws Exception
	{
		logger.debug("Adding OrgRelations");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgRelations != null) {
				session.save(orgRelations);
			}
			logger.debug("OrgRelations Added Successfully");
		} catch (Exception exception) {
			    StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_RELATION"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgRelations;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId, Long clientOrgLocationId)
			throws Exception {
		
		  Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation   left join fetch orgrelation.clientLocationId orgLocation left join fetch orgLocation.org   where  orgrelation.locationId =:orgLocationId and  orgrelation.clientLocationId =:clientOrgLocationId");
		  
	       List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setLong("clientOrgLocationId", clientOrgLocationId).setCacheable(true).list();
	       
	       OrgRelations orgLocation = null;
			if(orgRelations != null && !orgRelations.isEmpty())
			{
				orgLocation = (OrgRelations) orgRelations.iterator().next();
			}
			
			return orgLocation;

		
		
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Collection<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception {
		logger.debug("In OrgRelations getAllClientsByOrgLocationId method");
        Session session = this.sessionFactory.getCurrentSession();
        List<OrgRelations> clientsList = null;
		Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.clientLocationId orgLocation left join fetch orgLocation.org where orgrelation.locationId = :orgLocationId");
		clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		
		return clientsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation left join fetch orgrelation.clientLocationId cl join fetch cl.org where orgrelation.clientLocationId = :orgLocationId");
		  
	       List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
	       
	       OrgRelations orgLocation = null;
			if(orgRelations != null && !orgRelations.isEmpty())
			{
				orgLocation = (OrgRelations) orgRelations.iterator().next();
			}
			
			return orgLocation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getNumberOfProjectsPerClient(Long orgRelationId) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String query = "select count(*)  from Projects projects where projects.orgRelationId = :orgRelationId";
		Long count = (Long) session.createQuery(query).setLong("orgRelationId", orgRelationId).setCacheable(true).uniqueResult();
		
		return count;
	}

	/**
	 * This method for get projects count for particular client
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId) throws Exception{
		
		Session session = this.sessionFactory.getCurrentSession();
		String query="select count(*) from Projects pro where pro.orgRelationId = :orgRelationId and pro.orgLocationsId = :orgLocationId";
		Long count = (Long) session.createQuery(query).setLong("orgRelationId", orgRelationId).setLong("orgLocationId", orgLocationId).setCacheable(true).uniqueResult();
		return count;
	}
	
}