package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

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
	public List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long loginUserId,List<Long> ids,Long orgLocationId , String relationType) {
		logger.debug("In OrgRelations getAllVendorsContactListByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgSpoc> spocsList = null;
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUserId,orgLocationId);
		if (isadmin) {
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", relationType).setCacheable(true).list();
        }else if(ids != null && !ids.isEmpty()){
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ((spoc.ownerUser.id = :loginUserId OR spoc.ownerUser IN (:ids)) OR (spoc.spocUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).setCacheable(true).list();
        }else{
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where (spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setCacheable(true).list();
        }
		return spocsList;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getRespondingPartyDetailsById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
   	 String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org org where orgrelation.id = :id ");
      List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("id", id).setCacheable(true).list();
      OrgRelations orgRelation = null;
		if(orgRelations != null && !orgRelations.isEmpty())
		{
			orgRelation = orgRelations.iterator().next();
		}
		return orgRelation;
}

	/**
	 * 
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgRelations> getAllClientsFilterByOrgLocationId(Long orgLocationId, String status) throws Exception {
		logger.debug("In OrgRelations getAllClientsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		String[] level = status.split(",");
		
		String subQuery2 = "";
		String subQuery3  ="";

			if (Arrays.asList(level).contains("Tier-1")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-1' or ";
			}
			if (Arrays.asList(level).contains("Tier-1")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-1' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-2")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-2' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-2")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-2' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-3")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-3' or ";
			}
			if (Arrays.asList(level).contains("Tier-3")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-3' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-4")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-4' or ";
			}
			if (Arrays.asList(level).contains("Tier-4")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-4' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-5")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-5' or ";
			}
			if (Arrays.asList(level).contains("Tier-5")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-5' or ";
			}
			if(!subQuery2.equalsIgnoreCase("")){
			subQuery2 = " (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			
			}
			if(!subQuery3.equalsIgnoreCase("")){
				subQuery3 = " (" + subQuery3.substring(0, subQuery3.length() - 4) + ")";
			}
		

		List<OrgRelations> clientsList = null;
		if(Arrays.asList(level).contains("")){
			Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor' ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor' )  ");
			clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
			
		}else{
			Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor' ) or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor' )  ");
			clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		
		
		
		return clientsList;
	}
	
	/**
	 * Laxman 
	 * To get a Vendors list of the login user by orgLocationId and status
	 * @return OrgSpoc FilterList
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgRelations> getAllVendorsFilterByOrgLocationId(Long orgLocationId, String status) throws Exception {
		logger.debug("In OrgRelations getAllVendorsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String[] level = null;
		if(status != null){
			level = status.split(",");	
		}
		
		
		
		String subQuery2 = "";
		String subQuery3  ="";
		
		if(level != null){
			if (Arrays.asList(level).contains("Tier-1")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-1' or ";
			}
			if (Arrays.asList(level).contains("Tier-1")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-1' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-2")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-2' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-2")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-2' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-3")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-3' or ";
			}
			if (Arrays.asList(level).contains("Tier-3")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-3' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-4")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-4' or ";
			}
			if (Arrays.asList(level).contains("Tier-4")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-4' or ";
			}
			
			if (Arrays.asList(level).contains("Tier-5")) {
				subQuery2 = subQuery2 + "orgrelation.initiatorLevel = 'Tier-5' or ";
			}
			if (Arrays.asList(level).contains("Tier-5")) {
				subQuery3 = subQuery3 + "orgrelation.responderLevel = 'Tier-5' or ";
			}
			if(!subQuery2.equalsIgnoreCase("")){
			subQuery2 = " (" + subQuery2.substring(0, subQuery2.length() - 4) + ")";
			
			}
			if(!subQuery3.equalsIgnoreCase("")){
				subQuery3 = " (" + subQuery3.substring(0, subQuery3.length() - 4) + ")";
			}
		}
	
			
		
		
		List<OrgRelations> vandorsList = null;
		if(level == null || Arrays.asList(level).contains("")){
			Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )");
			vandorsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}else{
			Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )");
			vandorsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		}
		
		return vandorsList;
	}
	
	/**
	 * Laxman 
	 * 19/9/2016
	 * To get a Vendors Spoc list of the login user by orgLocationId and status relationType vendorIds invitationStatus
	 * @return OrgSpoc List
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationId(Long loginUserId, List<Long> ids, Long orgLocationId, String relationType, String status, String vendorIds, String invitationStatus) {
		logger.debug("In OrgRelations getAllVendorsContactListByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String[] tokens = status.split(",");
		String[] invitations = invitationStatus.split(",");
		String[] client = null;
		if(vendorIds != null){
			client = vendorIds.split(",");
		}
		
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUserId,orgLocationId);
		
		ArrayList<OrgSpoc> finalList = new ArrayList<OrgSpoc>();
		List<OrgSpoc> spocsList = null;
		String spocStatus = "";
		String spocInvitationStatus = "";
		
	    //spoc status	
		if (Arrays.asList(tokens).contains("Active")) {
			spocStatus = spocStatus + "spoc.spocStatus = 'Active' or ";
		}
		
		if (Arrays.asList(tokens).contains("In-Active")) {
			spocStatus = spocStatus + "spoc.spocStatus = 'In-Active' or ";
		}
		
		if (Arrays.asList(tokens).contains("New")) {
			spocStatus = spocStatus + "spoc.spocStatus = 'New' or ";
		}
		if (Arrays.asList(tokens).contains("Negotiation")) {
			spocStatus = spocStatus + "spoc.spocStatus = 'Negotiation' or ";
		}
		if (Arrays.asList(tokens).contains("Contracted")) {
			spocStatus = spocStatus + "spoc.spocStatus = 'Contracted' or ";
		}
		if(!spocStatus.equalsIgnoreCase("")){
		   spocStatus = " (" + spocStatus.substring(0, spocStatus.length() - 4) + ")";
		}
		
		//spoc invitation status
		if (Arrays.asList(invitations).contains("Sent")) {
			spocInvitationStatus = spocInvitationStatus + "spoc.invitationStatus = 'Sent' or ";
		}
		if (Arrays.asList(invitations).contains("New")) {
			spocInvitationStatus = spocInvitationStatus + "spoc.invitationStatus = 'New' or ";
		}
		if (Arrays.asList(invitations).contains("Accepted")) {
			spocInvitationStatus = spocInvitationStatus + "spoc.invitationStatus = 'Accepted' or ";
		}
		if (Arrays.asList(invitations).contains("Rejected")) {
			spocInvitationStatus = spocInvitationStatus + "spoc.invitationStatus = 'Rejected' or ";
		}
		
		if(!spocInvitationStatus.equalsIgnoreCase("")){
		   spocInvitationStatus = " (" + spocInvitationStatus.substring(0, spocInvitationStatus.length() - 4) + ")";
		}
		
		
		//vendor length based on vendors List
		if (client != null && client[0] != null && client[0].length() > 0) {
			if(!spocStatus.equalsIgnoreCase("") && !spocInvitationStatus.equalsIgnoreCase("")){
				spocStatus = spocStatus +" and ";
			}
				for (String clnt : client) {
					String subquery1 = "";
					if(spocStatus.equalsIgnoreCase("") && spocInvitationStatus.equalsIgnoreCase("")){
						subquery1 = "orgrelation.id =" + clnt;
					} else if((spocInvitationStatus.equalsIgnoreCase("") && !spocStatus.equalsIgnoreCase("")) || (spocStatus.equalsIgnoreCase("") && !spocInvitationStatus.equalsIgnoreCase(""))){
						subquery1 = "orgrelation.id =" + clnt+" and ";
					}else{
					    subquery1 = "orgrelation.id =" + clnt+" and ";
					}
					
					if (isadmin) {
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", relationType).setCacheable(true).list();
			            finalList.addAll(spocsList);
					}else if(ids != null && !ids.isEmpty()){
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and ((spoc.ownerUser.id = :loginUserId OR spoc.ownerUser IN (:ids)) OR (spoc.spocUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).setCacheable(true).list();
			            finalList.addAll(spocsList);
					}else{
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and (spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setCacheable(true).list();
			            finalList.addAll(spocsList);
					}
		      }
		}else{
			if(!spocStatus.equalsIgnoreCase("")){
				spocStatus = spocStatus +" and "; 
			}
			if(!spocInvitationStatus.equalsIgnoreCase("")){
				spocInvitationStatus = spocInvitationStatus +" and "; 
			}
			
			if (isadmin) {
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+spocStatus+" "+spocInvitationStatus+" ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", relationType).setCacheable(true).list();
	            finalList.addAll(spocsList);
			}else if(ids != null && !ids.isEmpty()){
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+spocStatus+" "+spocInvitationStatus+" ((spoc.ownerUser.id = :loginUserId OR spoc.ownerUser IN (:ids)) OR (spoc.spocUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).setCacheable(true).list();
	            finalList.addAll(spocsList);
			}else{
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+spocStatus+" "+spocInvitationStatus+" (spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setCacheable(true).list();
	            finalList.addAll(spocsList);
			}
		} 
		return finalList;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgRelations> getAllVendorsListByOrgName(String orgName,Long orgLocationId) {
		logger.debug("In OrgRelations getAllVendorsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRelations> vandorsList = null;
		Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgLocation.org.orgName = :orgName and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )");
		
		vandorsList = vendorsListQuery.setString("orgName", orgName).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return vandorsList;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgRelations> getAllClientsByOrgNameOrgLocationId(String orgName, Long orgLocationId) {
		logger.debug("In OrgRelations getAllClientsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRelations> clientsList = null;
		Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgLocation.org.orgName = :orgName and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor' )");
		clientsList = clientsListQuery.setString("orgName", orgName).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		return clientsList;
	}
	
	
}