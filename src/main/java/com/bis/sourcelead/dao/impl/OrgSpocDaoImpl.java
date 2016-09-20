package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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

import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class OrgSpocDaoImpl  implements OrgSpocDao{
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
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSpocDaoImpl.class);
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc addOrgSpoc(OrgSpoc orgSpoc) throws SourceLeadConstraintViolationException {

		logger.debug("Adding OrgSpoc");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (orgSpoc != null) {
				session.saveOrUpdate(orgSpoc);
			}
			logger.debug("OrgSpoc Added Successfully");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"ORG_SPOC"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return orgSpoc;
	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId, List<Long> ids,Long orgLocationId) throws Exception {
		logger.debug("In OrgSpocDaoImpl getAllSpocsByOrgRelationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> spocsList;
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
		if (isadmin) {
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser owner where spoc.orgRelations.id = :orgRelationId and spoc.status <> 'INACTIVE' ");
			spocsList = query.setLong("orgRelationId", orgRelationId).setCacheable(true).list();
		}else if(ids != null && !ids.isEmpty()){
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser owner where spoc.orgRelations.id = :orgRelationId and spoc.status <> 'INACTIVE' and (spoc.ownerUser.id = :userId OR spoc.ownerUser IN (:ids))");
			spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setParameterList("ids", ids).setCacheable(true).list();
		}else{
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser owner where spoc.orgRelations.id = :orgRelationId and (spoc.ownerUser.id = :userId OR spoc.spocUser.id = :userId) and spoc.status <> 'INACTIVE' ");
			spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setCacheable(true).list();
		}
		
		return spocsList;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc getOrgSpocById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs = new ArrayList<OrgSpoc>();
		logger.debug("getOrgSpocById start");
		orgSpocs = session.createQuery("from OrgSpoc where id=:id" ).setCacheable(true).setLong( "id", Id ).setCacheable(true).list();
		logger.debug("getOrgSpocById end");
		if (orgSpocs.size() > 0) {
			return orgSpocs.get(0);
		} else {
			return null;
		}
   	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc validateSpacEmailIdByOwnerUserIdAndOrgRelationId(String email, Long orgRelationId, Long userId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs = new ArrayList<OrgSpoc>();
		logger.debug("validateSpacEmailIdByOwnerUserIdAndOrgRelationId start");
		orgSpocs = session.createQuery("from OrgSpoc spoc where spoc.spocUser.email =:email and spoc.ownerUser.id = :userId and spoc.orgRelations.id = :orgRelationId").setCacheable(true).setLong( "userId", userId)
				 .setString( "email", email).setLong( "orgRelationId", orgRelationId).setCacheable(true).list();
		logger.debug("validateSpacEmailIdByOwnerUserIdAndOrgRelationId end");
		if (orgSpocs.size() > 0) {
			return orgSpocs.get(0);
		} else {
			return null;
		}
   	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc getSpocAndOwnerDetailsById(Long spocId, Long ownerId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs ;
		logger.debug("getSpocAndOwnerDetailsById start");
		orgSpocs = session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser owner where spoc.id=:spocId and spoc.ownerUser.id =:ownerId" ).setCacheable(true).setLong( "spocId", spocId).setLong( "ownerId", ownerId ).setCacheable(true).list();
		logger.debug("getSpocAndOwnerDetailsById end");
		if (orgSpocs.size() > 0) {
			return orgSpocs.get(0);
		} else {
			return null;
		}
		}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getOwnerIdbySpocId(Long spocUserId,Long ownerId) {
		logger.debug("getOwnerIdbySpocId start");
		Session session = this.sessionFactory.getCurrentSession();
	    String query = "select spoc.ownerUser.id from OrgSpoc spoc where spoc.spocUser.id=:spocUserId and spoc.ownerUser.id != :ownerId";
	    List<Long> ownersList = session.createQuery(query).setLong( "ownerId", ownerId ).setLong( "spocUserId", spocUserId ).setCacheable(true).list();
        return ownersList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc getSpocUserByUserId(Long spocUserId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs = new ArrayList<OrgSpoc>();
		logger.debug("getSpocUserByUserId start");
		orgSpocs = session.createQuery("from OrgSpoc spoc where spoc.spocUser.id = :spocUserId " ).setCacheable(true).setLong( "spocUserId", spocUserId ).setCacheable(true).list();
		logger.debug("getSpocUserByUserId end");
		if (orgSpocs.size() > 0) {
			return orgSpocs.get(0);
		} else {
			return null;
		}
   	}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getAllContactsbyOrgLocationIdAndUserIdForRequirement(Long orgLocationId, Long loginUserId,String relationType) throws Exception {
			logger.debug("In OrgSpocDaoImpl getAllContactsbyOrgLocationIdAndUserIdForRequirement method");
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList;
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUserId,orgLocationId);
			if (isadmin){
				Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser left join fetch spoc.responderLocationId responderLocationId  left join fetch responderLocationId.org  left join fetch spoc.orgRelations orgrelation where spoc.status <> 'INACTIVE' and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType ))");
				spocsList = query.setString("relationType", relationType).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
			}else{
	            Query query= session.createQuery("from OrgSpoc spoc   left join fetch spoc.responderLocationId responderLocationId  left join fetch responderLocationId.org left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where (spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> 'INACTIVE' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setCacheable(true).list();
	        }
			return spocsList;
		}

}
