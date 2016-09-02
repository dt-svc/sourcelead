package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
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
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
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
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations addOrgRelations(OrgRelations orgRelations) throws Exception
	{
		logger.debug("Adding OrgRelations");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgRelations != null) {
				session.saveOrUpdate(orgRelations);
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
	      String query = ("from OrgRelations orgrelation   left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org   where  orgrelation.locationId =:orgLocationId and  orgrelation.responderLocationId =:clientOrgLocationId");
		  
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
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception {
		logger.debug("In OrgRelations getAllClientsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRelations> clientsList = null;
		// Prasanna vadla : query changed to get new client list for ATS_VMS  
		//Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where orgrelation.locationId = :orgLocationId");
		
		Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor' )");
		clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return clientsList;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId cl join fetch cl.org where orgrelation.responderLocationId = :orgLocationId");
		  
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
	/**
	 * Prasanna vadla
	 * To get a Vendors list of the login user by orgLocationId
	 * @return OrgSpoc List
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgRelations> getAllVendorsByOrgLocationId(Long orgLocationId) {
		logger.debug("In OrgRelations getAllVendorsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRelations> vandorsList = null;
		Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )");
		
		vandorsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return vandorsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId) {
			Session session = this.sessionFactory.getCurrentSession();
	    	 String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org org left join fetch org.user where orgrelation.id = :orgRelationId and (orgrelation.locationId = :orgLocationId or orgrelation.responderLocationId = :orgLocationId)");
	       List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).setCacheable(true).list();
	       OrgRelations orgRelation = null;
			if(orgRelations != null && !orgRelations.isEmpty())
			{
				orgRelation = orgRelations.iterator().next();
			}
			return orgRelation;
	}
	
	
/**
 * @author Rajashekar
 * find OrgRelation by Id
 * @return OrgRelations
 * @throws Exception
 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getOrRelationsById(Long id) throws Exception
	{
		Session session = this.sessionFactory.getCurrentSession();
			return session.get(OrgRelations.class, id);
	}
	/**
	 * Prasanna vadla
	 * 31/8/2016
	 * To get a spoc list from all clients
	 * @return OrgSpoc List
	 */
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long loginUserId,List<Long> ids,Long orgLocationId ) {
		logger.debug("In OrgRelations getAllVendorsContactListByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> spocsList;
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUserId,orgLocationId);
		if (isadmin) {
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) and spoc.status <> 'INACTIVE' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
        }else if(ids != null && !ids.isEmpty()){
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ((spoc.ownerUser.id = :loginUserId OR spoc.ownerUser IN (:ids)) OR (spoc.spocUser.id =:loginUserId)) and (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) and spoc.status <> 'INACTIVE' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setParameterList("ids", ids).setCacheable(true).list();
        }else{
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where (spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId) and (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) and spoc.status <> 'INACTIVE' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setCacheable(true).list();
        }
		return spocsList;
	}
	
}