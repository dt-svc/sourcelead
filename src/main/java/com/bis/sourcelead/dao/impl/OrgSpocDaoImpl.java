package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
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
	public List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId, List<Long> ids,Long orgLocationId,String type) throws Exception {
		logger.debug("In OrgSpocDaoImpl getAllSpocsByOrgRelationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> spocsList;
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
		
		if(null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)){
			if (isadmin) {
				Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.clientUser clientUser left join fetch spoc.vendorUser vendorUser left join fetch spoc.ownerUser where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' ");
				spocsList = query.setLong("orgRelationId", orgRelationId).list();
			}else if(ids != null && !ids.isEmpty()){
				Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.clientUser clientUser left join fetch spoc.vendorUser vendorUser left join fetch spoc.ownerUser where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' and (spoc.vendorUser.id = :userId OR spoc.vendorUser IN (:ids))");
				spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setParameterList("ids", ids).list();
			}else{
				Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.clientUser clientUser left join fetch spoc.vendorUser vendorUser left join fetch spoc.ownerUser where spoc.orgRelations.id = :orgRelationId and spoc.vendorUser.id = :userId and spoc.status <> '0' ");
				spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).list();
			}
		}else{
			if (isadmin) {
				Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.clientUser clientUser left join fetch spoc.vendorUser vendorUser left join fetch spoc.ownerUser where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' ");
				spocsList = query.setLong("orgRelationId", orgRelationId).list();
			}else if(ids != null && !ids.isEmpty()){
				Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.clientUser clientUser left join fetch spoc.vendorUser vendorUser left join fetch spoc.ownerUser where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' and (spoc.clientUser.id = :userId OR spoc.clientUser IN (:ids))");
				spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setParameterList("ids", ids).list();
			}else{
				Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.clientUser clientUser left join fetch spoc.vendorUser vendorUser left join fetch spoc.ownerUser where spoc.orgRelations.id = :orgRelationId and spoc.clientUser.id = :userId and spoc.status <> '0' ");
				spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).list();
			}
		}
		return spocsList;
	}
	
	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgSpoc> getAllActiveSpocsByOrgRelationId(Long orgRelationId, Long userId, List<Long> ids,Long orgLocationId) throws Exception {
		logger.debug("In OrgSpocDaoImpl getAllSpocsByOrgRelationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> spocsList;
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
		if (isadmin) {
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser owner where spoc.spocStatus = 'Active' and spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' ");
			spocsList = query.setLong("orgRelationId", orgRelationId).list();
		}else if(ids != null && !ids.isEmpty()){
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser owner where spoc.spocStatus = 'Active' and spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' and (spoc.ownerUser.id = :userId OR spoc.ownerUser IN (:ids))");
			spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setParameterList("ids", ids).list();
		}else{
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.ownerUser owner where spoc.spocStatus = 'Active' and spoc.orgRelations.id = :orgRelationId and (spoc.ownerUser.id = :userId OR spoc.spocUser.id = :userId) and spoc.status <> '0' ");
			spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).list();
		}
		
		return spocsList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc getOrgSpocById(Long Id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs = new ArrayList<OrgSpoc>();
		logger.debug("getOrgSpocById start");
		orgSpocs = session.createQuery("from OrgSpoc spoc left join fetch spoc.vendorLocationId left join fetch spoc.clientLocationId  where spoc.id = :id" ).setLong( "id", Id ).list();
		logger.debug("getOrgSpocById end");
		if (orgSpocs.size() > 0) {
			return orgSpocs.get(0);
		} else {
			return null;
		}
   	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc validateSpacEmailIdByOwnerUserIdAndOrgRelationId(String email, Long orgRelationId, Long userId, Integer status) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs = new ArrayList<OrgSpoc>();
		logger.debug("validateSpacEmailIdByOwnerUserIdAndOrgRelationId start");
		orgSpocs = session.createQuery("from OrgSpoc spoc where spoc.clientUser.email =:email and spoc.vendorUser.id = :userId and spoc.orgRelations.id = :orgRelationId and spoc.status = :status").setLong( "userId", userId)
				 .setString( "email", email).setLong( "orgRelationId", orgRelationId).setInteger( "status", status).list();
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
		orgSpocs = session.createQuery("from OrgSpoc spoc left join fetch spoc.vendorUser vendorUser where spoc.id=:spocId and (spoc.vendorUser.id =:ownerId OR spoc.clientUser.id =:ownerId)" ).setLong( "spocId", spocId).setLong( "ownerId", ownerId ).list();
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
	    String query = "select spoc.vendorUser.id from OrgSpoc spoc where spoc.clientUser.id=:spocUserId and spoc.vendorUser.id != :ownerId";
	    List<Long> ownersList = session.createQuery(query).setLong( "ownerId", ownerId ).setLong( "spocUserId", spocUserId ).list();
        return ownersList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Long> getOwnerUserIdsbySpocEmailAndOrgRelationId(String email, Long orgRelationId,String type) {
		logger.debug("getOwnerIdbySpocId start");
		Session session = this.sessionFactory.getCurrentSession();
		List<Long> ownersList = null;
		if(orgRelationId != null && email != null){
			String query = "";
			if(null != type && !"".equalsIgnoreCase(type) && "client".equalsIgnoreCase(type)){
				query = "select spoc.ownerUser.id from OrgSpoc spoc where spoc.clientUser.email = :email and spoc.orgRelations.id = :orgRelationId and spoc.status = '1' ";
			}else if(null != type && !"".equalsIgnoreCase(type) && "vendor".equalsIgnoreCase(type)){
				query = "select spoc.ownerUser.id from OrgSpoc spoc where spoc.vendorUser.email = :email and spoc.orgRelations.id = :orgRelationId and spoc.status = '1' ";
			}
			
		    ownersList = session.createQuery(query).setString( "email", email ).setLong( "orgRelationId", orgRelationId ).list();
		}
	    
        return ownersList;
        
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc getSpocByClientOrVendorUserId(Long userId) {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs = new ArrayList<OrgSpoc>();
		logger.debug("getSpocByClientOrVendorUserId start");
		orgSpocs = session.createQuery("from OrgSpoc spoc where (spoc.vendorUser.id = :userId OR spoc.clientUser.id = :userId) " ).setLong( "userId", userId ).list();
		logger.debug("getSpocByClientOrVendorUserId end");
		if (orgSpocs.size() > 0) {
			return orgSpocs.get(0);
		} else {
			return null;
		}
   	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgSpoc getSpocBySpocUUID(String spocUUID) {
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> orgSpocs = new ArrayList<OrgSpoc>();
		logger.debug("getSpocBySpocUUID start");
		orgSpocs = session.createQuery("from OrgSpoc spoc left join fetch spoc.vendorUser left join fetch spoc.clientUser left join fetch spoc.ownerUser where spoc.UUID = :spocUUID " ).setString( "spocUUID", spocUUID ).list();
		logger.debug("getSpocBySpocUUID end");
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
			
			if (isadmin) {
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", relationType).list();
			}else{
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).list();
			}
			
			return spocsList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getAllContactsbyOrgId(Long orgId) throws Exception {
			
			logger.debug("In OrgSpocDaoImpl getAllContactsbyOrgId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList;
			 
			Query query= session.createQuery("from OrgSpoc spoc  left join fetch spoc.orgRelations orgrelation where spoc.orgRelations.id=:orgId and spoc.status <> '0' "); 
			spocsList = query.setLong("orgId", orgId).list();
			return spocsList;
			
		}
		
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(Long orgId, String invitationStatus, String spocStatus) throws Exception {
			logger.debug("In OrgSpocDaoImpl getAllContactsbyOrgId method");
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList;
			Query query= session.createQuery("from OrgSpoc spoc  left join fetch spoc.orgRelations orgrelation where spoc.orgRelations.id=:orgId and spoc.invitationStatus =:invitationStatus and spoc.spocStatus =:spocStatus and spoc.status <> '0' "); 
			spocsList = query.setLong("orgId", orgId).setString("invitationStatus", invitationStatus).setString("spocStatus", spocStatus).list();
			return spocsList;
		}
		
		
		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getAllContactsbyOrgRelationIdAndLocationId(Long relationId, Long locationId, String initiator) throws Exception {
			
			logger.debug("In OrgSpocDaoImpl getAllContactsbyOrgRelationIdAndLocationId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList = null;
			 
			if(StringUtils.equals(initiator, "Vendor")){
				Query query= session.createQuery("from OrgSpoc spoc where spoc.orgRelations.id=:relationId and spoc.initiatorLocationId.id=:locationId and spoc.status <> '0' ");
				spocsList = query.setLong("relationId", relationId).setLong("locationId", locationId).list();
			}else if(StringUtils.equals(initiator, "Client")){
				Query query= session.createQuery("from OrgSpoc spoc where spoc.orgRelations.id=:relationId and spoc.responderLocationId.id=:locationId and spoc.status <> '0' ");
				spocsList = query.setLong("relationId", relationId).setLong("locationId", locationId).list();
			}
			 
			return spocsList;
			
		}

		@Override
		@Transactional
		public List<Long> getAssignedAsClientOrVendorSpocCountByUserId(Long userId, Long orgLocationId,String type) throws Exception {
			logger.debug("getAssignedAsClientOrVendorSpocCountByUserId start");
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> ownersList = null;;
			 String query = " ";
				/*String query = "select spoc.id from OrgSpoc spoc where ((spoc.ownerUser.id = :ownerUserId and spoc.orgRelations.locationId = :orgLocationId and spoc.orgRelations.initiatorType = :initiatorType) "
						+ "OR (spoc.spocUser.id = :spocUserId and spoc.orgRelations.responderLocationId = :responderLocationId and spoc.orgRelations.responderType = :responderType))";
						
						ownersList = session.createQuery(query).setLong("ownerUserId", userId)
													   .setLong("orgLocationId", orgLocationId)
													   .setString("initiatorType", type)
			    									   .setLong("spocUserId", userId)
			    									   .setLong("responderLocationId", orgLocationId)
			    									   .setString("responderType", type)
			    									   .list();*/
			 
			   if(null != type && !"".equalsIgnoreCase(type) && "Vendor".equalsIgnoreCase(type)){
				   query = "select spoc.id from OrgSpoc spoc where spoc.clientUser.id = :userId and spoc.clientLocationId.id = :orgLocationId ";
			   }else{
				   query = "select spoc.id from OrgSpoc spoc where spoc.vendorUser.id = :userId and spoc.vendorLocationId.id = :orgLocationId ";
			   }
			   
				ownersList = session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId)
			    									   .list();
		    
	        return ownersList;
	        
		}

		@Override
		@Transactional
		public List<OrgSpoc> getAssignedAsClientOrVendorSpocByUserId(Long userId, Long orgLocationId, String type)
				throws Exception {
			
			logger.debug("In getAssignedAsClientOrVendorSpocByUserId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList;
			String query = " ";
			/*Query query = session.createQuery("from OrgSpoc spoc where ((spoc.ownerUser.id = :ownerUserId and spoc.orgRelations.locationId = :orgLocationId and spoc.orgRelations.initiatorType = :initiatorType) "
						+ "OR (spoc.spocUser.id = :spocUserId and spoc.orgRelations.responderLocationId = :responderLocationId and spoc.orgRelations.responderType = :responderType))"); 
			spocsList = query.setLong("ownerUserId", userId)
					   .setLong("orgLocationId", orgLocationId)
					   .setString("initiatorType", type)
					   .setLong("spocUserId", userId)
					   .setLong("responderLocationId", orgLocationId)
					   .setString("responderType", type)
					   .list();*/
			
			
			if(null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)){
				   query = "from OrgSpoc spoc left join fetch spoc.orgRelations left join fetch spoc.orgRelations.locationId left join fetch spoc.ownerUser left join fetch spoc.vendorUser left join fetch spoc.clientUser where spoc.clientUser.id = :userId and spoc.clientLocationId.id = :orgLocationId";
			   }else{
				   query = "from OrgSpoc spoc left join fetch spoc.orgRelations left join fetch spoc.orgRelations.locationId left join fetch spoc.ownerUser left join fetch spoc.vendorUser left join fetch spoc.clientUser where spoc.vendorUser.id = :userId and spoc.vendorLocationId.id = :orgLocationId ";
			   }
			
			    spocsList = session.createQuery(query).setLong("userId", userId).setLong("orgLocationId", orgLocationId)
					   .list();
			return spocsList;
			
		}

		@Override
		@Transactional
		public List<OrgSpoc> getAllClientContactsListByOrgLocationId(Long userId, Long orgLocationId) throws Exception {
			
			logger.debug("In OrgRelations getAllVendorsContactListByOrgLocationId method");
			Session session = this.sessionFactory.getCurrentSession();
			
			
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
			
			ArrayList<OrgSpoc> finalList = new ArrayList<OrgSpoc>();
			List<OrgSpoc> spocsList = null;
			
			
			if (isadmin) {
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status = '1' and (spoc.spocStatus = 'Active' OR spoc.spocStatus = 'Contracted') ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", "Vendor").list();
	            finalList.addAll(spocsList);
			}else{
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where  (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType ))  and spoc.status = '1' and (spoc.spocStatus = 'Active' OR spoc.spocStatus = 'Contracted')");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", userId).setString("relationType", "Vendor").list();
	            finalList.addAll(spocsList);
			}
			
			return finalList;
		}
		
		
		@Override
		@Transactional
		public Integer getAllClientContactsCountForCampaignByOrgLocationId(Long orgRelationId,Long userId,Long orgLocationId) throws Exception {
		   
			Session session = this.sessionFactory.getCurrentSession();
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
			List<Long> spocsList = null;
			if (isadmin) {
				 Query query= session.createQuery(" select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and ((spoc.orgRelations.locationId = :orgLocationId and spoc.orgRelations.initiatorType = :relationType ) or (spoc.orgRelations.responderLocationId = :orgLocationId and spoc.orgRelations.responderType = :relationType )) and spoc.status = '1' and (spoc.spocStatus = 'Active' OR spoc.spocStatus = 'Contracted') ");
	              spocsList = query.setLong("orgRelationId", orgRelationId).setLong("orgLocationId", orgLocationId).setString("relationType", "Vendor").list();
			}else{
				 Query query= session.createQuery("select count(*) from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((spoc.orgRelations.locationId = :orgLocationId and spoc.orgRelations.initiatorType = :relationType ) or (spoc.orgRelations.responderLocationId = :orgLocationId and spoc.orgRelations.responderType = :relationType ))  and spoc.status = '1' and (spoc.spocStatus = 'Active' OR spoc.spocStatus = 'Contracted') ");
	              spocsList = query.setLong("orgRelationId", orgRelationId).setLong("orgLocationId", orgLocationId).setString("relationType", "Vendor").setLong("loginUserId", userId).list();
			}
                 
	        	Integer count = 0;
	    		if(null != spocsList  && !spocsList.isEmpty()){
	    				count = spocsList.get(0).intValue();
	    		}
	    		return count;
		}

		@Override
		@Transactional
		public List<OrgSpoc> getOrgSpocByOrgRelationId(Long orgRelationId) throws Exception {
			logger.debug("In OrgSpocDaoImpl getOrgSpocByOrgRelationId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			 
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.vendorUser left join fetch spoc.clientUser where  spoc.orgRelations.id = :orgRelationId and spoc.spocStatus ='Active'");
			List<OrgSpoc> spocsList = query.setLong("orgRelationId", orgRelationId).list();
			 
			return spocsList;
			
	   	}

		@Override
		@Transactional
		public Integer getAllVendorContactsCountbyOrgLocationId(Long orgLocationId,String type,Long userId) throws Exception {
		   
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> spocsList = null;
			
			
	           // Query query= session.createQuery("select count(*) from OrgSpoc spoc where  ((spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId) and  ((spoc.orgRelations.locationId.id = :locationId and spoc.orgRelations.initiatorType = :responderType ) or (spoc.orgRelations.responderLocationId.id = :locationId and spoc.orgRelations.responderType = :responderType )) ) and spoc.status <> '0' ");
				
                  Query query= session.createQuery(" from OrgSpoc spoc where  ((spoc.orgRelations.locationId.id = :locationId and spoc.orgRelations.initiatorType = :responderType ) or (spoc.orgRelations.responderLocationId.id = :locationId and spoc.orgRelations.responderType = :responderType )) and (spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId)  and spoc.status <> '0' ");
	              spocsList = query.setLong("locationId", orgLocationId).setLong("loginUserId", userId).setString("responderType", type).list();
					
					

	        	Integer count = 0;
	    		if(spocsList != null && !spocsList.isEmpty()){
	    				count = spocsList.get(0).intValue();
	    		}
	    		return count;
		}

		@Override
		@Transactional
		public OrgSpoc getSpocListByResponderLocationId(Long initiatingPartyOrgLocationId,Long responderLocationId,String respondingOrgType) throws Exception {
			
			logger.debug("In OrgSpocDaoImpl getSpocListByResponderLocationId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			 
			String subQuey = "";
			if(respondingOrgType.equalsIgnoreCase("VMS")){
				subQuey = "spoc.orgRelations.responderType = 'Client' ";
			}else if(respondingOrgType.equalsIgnoreCase("ATS")){
				subQuey = "spoc.orgRelations.responderType = 'Vendor' ";
			}
			
			List<OrgSpoc> orgSpocs  = session.createQuery("from OrgSpoc spoc where spoc.vendorLocationId.id = :initiatingPartyOrgLocationId and  spoc.clientLocationId.id = :responderLocationId and "+subQuey+" ").setLong( "initiatingPartyOrgLocationId", initiatingPartyOrgLocationId).setLong( "responderLocationId", responderLocationId ).list();
			if (orgSpocs.size() > 0) {
				return orgSpocs.get(0);
			} else {
				return null;
			}
			
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getOrgSpocByUserIdAndOrgLocationCode(Long loginUserId, Long orgLocationId) throws Exception {
			
			logger.debug("In OrgSpocDaoImpl getAllContactsbyOrgLocationIdAndUserIdForRequirement method");
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList;
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUserId,orgLocationId);
			
			if (isadmin) {
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ( spoc.clientUser.id = :loginUserId or spoc.vendorUser.id = :loginUserId )  and (orgrelation.locationId = :orgLocationId or orgrelation.responderLocationId = :orgLocationId) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).list();
			}else{
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ( spoc.clientUser.id = :loginUserId or spoc.vendorUser.id = :loginUserId )   and (orgrelation.locationId = :orgLocationId or orgrelation.responderLocationId = :orgLocationId) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).list();
			}
			
			return spocsList;
			
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<Long> getOwnerListByUserIdAndOrglocationId(User user, OrgLocations orgLocations,String type) {
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> spocsList;
			Query query;
			if(type.equalsIgnoreCase("Client")){
				 query= session.createQuery("select spoc.vendorUser.id from OrgSpoc spoc where (spoc.orgRelations.locationId = :orgLocationId or spoc.orgRelations.responderLocationId = :orgLocationId) and spoc.status <> '0' and spoc.clientUser.id = :userId ");
			}
			else{
				 query= session.createQuery("select spoc.clientUser.id from OrgSpoc spoc where (spoc.orgRelations.locationId = :orgLocationId or spoc.orgRelations.responderLocationId = :orgLocationId) and spoc.status <> '0' and spoc.vendorUser.id = :userId ");
			}
	            spocsList = query.setLong("orgLocationId", orgLocations.getId()).setLong("userId", user.getId()).list();
			return spocsList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public OrgSpoc getValidateOwnersBySpocUserIdAndSpocOwnerUserId(Long spocUserId, Long spocOwnderUserId,Long responderLocationId, String responderType) throws Exception
		{
			Query query;
			Session session = this.sessionFactory.getCurrentSession();
			if("Client".equalsIgnoreCase(responderType)){
				query= session.createQuery("from OrgSpoc spoc  where  spoc.clientUser.id =:spocUserId and spoc.ownerUser.id =:spocOwnderUserId and spoc.clientLocationId.id = :responderLocationId");
			}else{
				query= session.createQuery("from OrgSpoc spoc  where  spoc.vendorUser.id =:spocUserId and spoc.ownerUser.id =:spocOwnderUserId and spoc.vendorLocationId.id = :responderLocationId");
			}
			List<OrgSpoc> spocsList = query.setLong("spocUserId", spocUserId).setLong("spocOwnderUserId", spocOwnderUserId).setLong("responderLocationId", responderLocationId).list();
			OrgSpoc orgSpoc = null;
			if(spocsList != null && spocsList.size() > 0){
				orgSpoc = spocsList.get(0);
			}
			return orgSpoc;
			// TODO Auto-generated method stub
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getOrgSpocContactEmailsByOrgRelationIdAndUserId(Long userId, Long orgRelationId,Long orgLocationId)
				throws Exception {
			logger.debug("In OrgSpocDaoImpl getOrgSpocByOrgRelationId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList = null;
			
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
			
			 if(isadmin){
				 Query query= session.createQuery("from OrgSpoc spoc where  spoc.orgRelations.id = :orgRelationId and spoc.status = '1'");
				 spocsList = query.setLong("orgRelationId", orgRelationId).list();
			 }else{
				 Query query= session.createQuery("from OrgSpoc spoc where  spoc.orgRelations.id = :orgRelationId and spoc.status = '1' "
				 		+ " and (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId)");
				 spocsList = query.setLong("orgRelationId", orgRelationId).setLong("loginUserId", userId).list();
			 }
			 
			 
			return spocsList;
			
	   	}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public OrgSpoc getValidateOwnersByVendorUserIdAndSpocOwnerUserId(Long vendorUserId, Long spocOwnderUserId, Long responderLocationId) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();
			Query query= session.createQuery("from OrgSpoc spoc  where  spoc.vendorUser.id =:vendorUserId and spoc.ownerUser.id =:spocOwnderUserId and spoc.vendorLocationId.id = :responderLocationId");
			List<OrgSpoc> spocsList = query.setLong("vendorUserId", vendorUserId).setLong("spocOwnderUserId", spocOwnderUserId).setLong("responderLocationId", responderLocationId).list();
			OrgSpoc orgSpoc = null;
			if(spocsList != null && spocsList.size() > 0){
				orgSpoc = spocsList.get(0);
			}
			return orgSpoc;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();
			 //from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor'
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org  where  (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor') ");
			List<OrgSpoc> spocsList = query.setLong("orgLocationId", orgLocationId).list();
			 
			return spocsList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getSpoclistByDomain(Long orgRelationId, String domain) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();
			 //from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor'
			Query query;
			if("".equalsIgnoreCase(domain)){
				query= session.createQuery("from OrgSpoc spoc left join fetch spoc.clientUser user where spoc.orgRelations.id = :orgRelations");
			}else{
				query= session.createQuery("from OrgSpoc spoc left join fetch spoc.vendorUser user where spoc.orgRelations.id = :orgRelations and user.email like '%"+domain+"%'");
			}
			List<OrgSpoc> spocsList = query.setLong("orgRelations", orgRelationId).list();
			 
			return spocsList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public boolean getOwnerUserExistedByOrgRelationIdAndOwnerUserId(Long orgRelationId, Long ownerUserId)
				throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> orgSpoc = new ArrayList<OrgSpoc>();
			logger.debug("getOwnerUserExistedByOrgRelationIdAndOwnerUserId start");
			orgSpoc = session.createQuery("from OrgSpoc spoc where spoc.orgRelations.id = :orgRelationId and spoc.ownerUser.id = :ownerUserId and spoc.status = '1' ").setLong("orgRelationId", orgRelationId).setLong("ownerUserId", ownerUserId).list();
			logger.debug("getOwnerUserExistedByOrgRelationIdAndOwnerUserId End");
			Boolean flag = false;
			if (orgSpoc.size() > 0) {
				flag = true;
			} 
			return flag;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public OrgSpoc getClientUserByOrgRelationIdAndOwnerUserId(Long orgRelationId, Long clientUserId)
				throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			Query query= session.createQuery("from OrgSpoc spoc  where  spoc.clientUser.id = :clientUserId and spoc.orgRelations.id = :orgRelationId and spoc.status = '1' ");
			List<OrgSpoc> spocsList = query.setLong("clientUserId", clientUserId).setLong("orgRelationId", orgRelationId).list();
			OrgSpoc orgSpoc = null;
			if(spocsList != null && spocsList.size() > 0){
				orgSpoc = spocsList.get(0);
			}
			return orgSpoc;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public OrgSpoc getSpocByOrgRelationIdAndAssignedUserId(Long orgRelationId, Long assignUserId) throws Exception {
			
			Session session = this.sessionFactory.getCurrentSession();
			Query query= session.createQuery("from OrgSpoc spoc  where spoc.vendorUser.id = :assignUserId and spoc.orgRelations.id = :orgRelationId and spoc.status = '1' ");
			List<OrgSpoc> spocsList = query.setLong("assignUserId", assignUserId).setLong("orgRelationId", orgRelationId).list();
			OrgSpoc orgSpoc = null;
			if(spocsList != null && spocsList.size() > 0){
				orgSpoc = spocsList.get(0);
			}
			return orgSpoc;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public List<OrgSpoc> getSpocListByVendorUserIdAndOrgRelationId(Long orgRelationId, Long vendorUserId)
				throws Exception {
			
	        logger.debug("In OrgSpocDaoImpl getSpocListByVendorUserIdAndOrgRelationId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList;
			 
			Query query= session.createQuery("from OrgSpoc spoc  left join fetch spoc.orgRelations orgrelation where orgrelation.id = :orgRelationId and spoc.vendorUser.id = :vendorUserId and spoc.status = '1' "); 
			spocsList = query.setLong("orgRelationId", orgRelationId).setLong("vendorUserId", vendorUserId).list();
			return spocsList;
		}

		@Override
		@Transactional(rollbackFor = { Exception.class })
		public OrgSpoc getOrgSpocByUUID(String UUID) throws Exception
		{
			Session session = this.sessionFactory.getCurrentSession();
		   	 String query = ("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch spoc.clientUser left join fetch spoc.vendorUser where spoc.UUID = :UUID ");
		      List<OrgSpoc> orgSpocs = session.createQuery(query.toString()).setString("UUID", UUID).list();
		      OrgSpoc orgSpoc=null;
				if(orgSpocs != null && !orgSpocs.isEmpty())
				{
					orgSpoc = orgSpocs.iterator().next();
				}
				return orgSpoc;
		}


		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public Integer getNumberOfContactCount(Long orgRelationId, Long userId, List<Long> ids, Long orgLocationId,String type) throws Exception {
			Session session = this.sessionFactory.getCurrentSession();
			List<Long> spocsList;
			Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
			
			if(null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)){
				if (isadmin) {
					Query query= session.createQuery(" select count(*) from OrgSpoc spoc  where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' ");
					spocsList = query.setLong("orgRelationId", orgRelationId).list();
				}else if(ids != null && !ids.isEmpty()){
					Query query= session.createQuery(" select count(*) from OrgSpoc spoc  where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' and (spoc.vendorUser.id = :userId OR spoc.vendorUser IN (:ids))");
					spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setParameterList("ids", ids).list();
				}else{
					Query query= session.createQuery(" select count(*) from OrgSpoc spoc  where spoc.orgRelations.id = :orgRelationId and spoc.vendorUser.id = :userId and spoc.status <> '0' ");
					spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).list();
				}
			}else{
				if (isadmin) {
					Query query= session.createQuery(" select count(*) from OrgSpoc spoc  where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' ");
					spocsList = query.setLong("orgRelationId", orgRelationId).list();
				}else if(ids != null && !ids.isEmpty()){
					Query query= session.createQuery(" select count(*) from OrgSpoc spoc  where spoc.orgRelations.id = :orgRelationId and spoc.status <> '0' and (spoc.clientUser.id = :userId OR spoc.clientUser IN (:ids))");
					spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).setParameterList("ids", ids).list();
				}else{
					Query query= session.createQuery(" select count(*) from OrgSpoc spoc  where spoc.orgRelations.id = :orgRelationId and spoc.clientUser.id = :userId and spoc.status <> '0' ");
					spocsList = query.setLong("orgRelationId", orgRelationId).setLong("userId", userId).list();
				}
			}
			Integer count = 0;
    		if(null != spocsList  && !spocsList.isEmpty()){
    				count = spocsList.get(0).intValue();
    		}
    		return count;
		}

		@Override
		@Transactional
		public OrgSpoc getGusetClientOrgSpocByOrgRelationId(Long orgRelationId) {
			
			Session session = this.sessionFactory.getCurrentSession();
			Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations left join fetch spoc.clientUser left join fetch spoc.vendorUser "
					+ " where spoc.orgRelations.id = :orgRelationId and spoc.status = '1' ");
			List<OrgSpoc> spocsList = query.setLong("orgRelationId", orgRelationId).list();
			OrgSpoc orgSpoc = null;
			if(spocsList != null && spocsList.size() > 0){
				orgSpoc = spocsList.get(0);
			}
			return orgSpoc;
		}

		@Override
		@Transactional
		public OrgSpoc getSpocByOrgRelationIdForNewVendor(Long orgRelationId) throws Exception {
			

			Session session = this.sessionFactory.getCurrentSession();
		   	 String query = ("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch spoc.clientUser left join fetch spoc.vendorUser left join fetch spoc.orgRelations orgRelations where orgRelations.id = :orgRelationId ");
		      List<OrgSpoc> orgSpocs = session.createQuery(query.toString()).setLong("orgRelationId", orgRelationId).list();
		      OrgSpoc orgSpoc = null;
				if(orgSpocs != null && !orgSpocs.isEmpty())
				{
					orgSpoc = orgSpocs.iterator().next();
				}
				return orgSpoc;
		
		}

		@Override
		@Transactional
		public OrgSpoc getSpocByVendorUserIdAndOrgRelationId(Long orgRelationId, Long vendorUserId) throws Exception
		{

	        logger.debug("In OrgSpocDaoImpl getSpocListByVendorUserIdAndOrgRelationId method");
			
			Session session = this.sessionFactory.getCurrentSession();
			List<OrgSpoc> spocsList;
			 
			Query query= session.createQuery("from OrgSpoc spoc  left join fetch spoc.orgRelations orgrelation where orgrelation.id = :orgRelationId and spoc.vendorUser.id = :vendorUserId and spoc.status = '1' "); 
			spocsList = query.setLong("orgRelationId", orgRelationId).setLong("vendorUserId", vendorUserId).list();
		     OrgSpoc orgSpoc = null;
						if(spocsList != null && !spocsList.isEmpty())
						{
							orgSpoc = spocsList.iterator().next();
						}
						return orgSpoc;
		}
	 
}
