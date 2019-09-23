package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.OrgSpocDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Roles;
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
	      String query = ("from OrgRelations orgrelation   left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org   where  (orgrelation.locationId =:orgLocationId and  orgrelation.responderLocationId =:clientOrgLocationId) OR (orgrelation.responderLocationId =:orgLocationId and  orgrelation.locationId =:clientOrgLocationId)");
		  
	       List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setLong("clientOrgLocationId", clientOrgLocationId).list();
	       
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
		clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).list();
		return clientsList;
	}

	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId cl join fetch cl.org where orgrelation.responderLocationId = :orgLocationId");
		  
	       List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).list();
	       
	       OrgRelations orgLocation = null;
			if(orgRelations != null && !orgRelations.isEmpty())
			{
				orgLocation = (OrgRelations) orgRelations.iterator().next();
			}
			
			return orgLocation;
	}
	
	
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getOrgRelationById(Long id) throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation left join fetch orgrelation.locationId loc left join fetch loc.org  left join fetch orgrelation.responderLocationId cl left join fetch cl.org where orgrelation.id = :id");
		  
	       List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("id", id).list();
	       
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
		Long count = (Long) session.createQuery(query).setLong("orgRelationId", orgRelationId).uniqueResult();
		
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
		Long count = (Long) session.createQuery(query).setLong("orgRelationId", orgRelationId).setLong("orgLocationId", orgLocationId).uniqueResult();
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
		
		vandorsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).list();
		return vandorsList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId) {
			Session session = this.sessionFactory.getCurrentSession();
	    	 String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org org "
	    	 		+ "left join fetch org.user left join fetch orgrelation.bankDetailsId where orgrelation.id = :orgRelationId and (orgrelation.locationId = :orgLocationId or orgrelation.responderLocationId = :orgLocationId)");
	       List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("orgLocationId", orgLocationId).setLong("orgRelationId", orgRelationId).list();
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
	   	 String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId responder left join fetch responder.org  "
	   	 		+ " left join fetch orgrelation.locationId orgLoc left join fetch orgLoc.org where orgrelation.id = :id ");
	      List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("id", id).list();
	      OrgRelations orgRelation = null;
			if(orgRelations != null && !orgRelations.isEmpty())
			{
				orgRelation = orgRelations.iterator().next();
			}
			return orgRelation;
		
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
            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", relationType).list();
        }else if(ids != null && !ids.isEmpty()){
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where ((spoc.ownerUser.id = :loginUserId OR spoc.ownerUser IN (:ids)) OR (spoc.spocUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).list();
        }else{
            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where (spoc.ownerUser.id = :loginUserId OR spoc.spocUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).list();
        }
		return spocsList;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getRespondingPartyDetailsById(Long id) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
   	 String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org org where orgrelation.id = :id ");
      List<OrgRelations> orgRelations = session.createQuery(query.toString()).setLong("id", id).list();
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
			clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).list();
			
		}else{
			Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor' ) or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor' )  ");
			clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).list();
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
	public List<OrgRelations> getAllVendorsFilterByOrgLocationId(Long orgLocationId, String status, String vendorsRelationId) throws Exception {
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
		
		List<Long> vendorsRelationIdList = new ArrayList<Long>();
		String[] vendorsRelationIdArr = null;
	    if(vendorsRelationId != ""){
	    	vendorsRelationIdArr = vendorsRelationId.split(",");
	    	for(String VRID : vendorsRelationIdArr){
	    		vendorsRelationIdList.add(Long.parseLong(VRID));
	    	}
	    }
		
		String subQuery4 = "";
		
			
		//orgrelation.id IN (:vendorsRelationIdList)
		if(vendorsRelationIdList.size() > 0){
			subQuery4 = " and orgrelation.id IN (:vendorsRelationIdList) ";
		}
		
		
		List<OrgRelations> vandorsList = null;
		if(level == null || Arrays.asList(level).contains("")){
			Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ( (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )) "+subQuery4+" ");
			if(vendorsRelationIdList.size() > 0){
				vandorsList = vendorsListQuery
						.setLong("orgLocationId", orgLocationId)
						.setParameterList("vendorsRelationIdList", vendorsRelationIdList)
						.list();
			}else{
			vandorsList = vendorsListQuery
						.setLong("orgLocationId", orgLocationId)
						.list();
			}
		}else{
			Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ( ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) ) "+subQuery4+" ");
			if(vendorsRelationIdList.size() > 0){
				vandorsList = vendorsListQuery
						.setLong("orgLocationId", orgLocationId)
						.setParameterList("vendorsRelationIdList", vendorsRelationIdList)
						.list();
			}else{
			vandorsList = vendorsListQuery
						.setLong("orgLocationId", orgLocationId)
						.list();
			}
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
		if (Arrays.asList(tokens).contains("Contract")) {
			spocStatus = spocStatus + "spoc.spocStatus = 'Contract' or ";
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
			            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", relationType).list();
			            finalList.addAll(spocsList);
					}else if(ids != null && !ids.isEmpty()){
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and ((spoc.vendorUser.id = :loginUserId OR spoc.vendorUser IN (:ids)) OR (spoc.clientUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).list();
			            finalList.addAll(spocsList);
					}else{
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).list();
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
	            spocsList = query.setLong("orgLocationId", orgLocationId).setString("relationType", relationType).list();
	            finalList.addAll(spocsList);
			}else if(ids != null && !ids.isEmpty()){
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+spocStatus+" "+spocInvitationStatus+" ((spoc.vendorUser.id = :loginUserId OR spoc.vendorUser IN (:ids)) OR (spoc.clientUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).list();
	            finalList.addAll(spocsList);
			}else{
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where "+spocStatus+" "+spocInvitationStatus+" (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setLong("loginUserId", loginUserId).setString("relationType", relationType).list();
	            finalList.addAll(spocsList);
			}
		} 
		return finalList;	
	}
	
	
	/**
	 * Venkat Kesav 
	 * 02/03/2017
	 * To get a Vendors Spoc list of the login user by orgLocationId , By Tab Access and status relationType vendorIds invitationStatus
	 * @return OrgSpoc List
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(Long loginUserId, List<Long> ids, Long orgLocationId, String relationType, String status, String vendorIds, String invitationStatus, Long tabId, List<Long> vendorOrgLocationsIds) {
		logger.debug("In OrgRelations getAllVendorsContactFilterListByOrgLocationIdAndTabAccess method");
		Session session = this.sessionFactory.getCurrentSession();
		
		String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id IN (:vendorOrgLocationsIds)";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 
	    		 /*Remove/filter null & empty string objects from array-lambda stream*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }else {
	        		 ownerRolesList.add(0L);
	        	 }
	    	 }
	    	 
	     }
		
		
		
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
		   spocStatus = " and (" + spocStatus.substring(0, spocStatus.length() - 4) + ")";
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
		   spocInvitationStatus = " and (" + spocInvitationStatus.substring(0, spocInvitationStatus.length() - 4) + ")";
		}
		
		
		//vendor length based on vendors List
		if (client != null && client[0] != null && client[0].length() > 0) {
			if(!spocStatus.equalsIgnoreCase("") && !spocInvitationStatus.equalsIgnoreCase("")){
				spocStatus = spocStatus +" and ";
			}
				for (String clnt : client) {
					String subquery1 = "";
					if(spocStatus.equalsIgnoreCase("") && spocInvitationStatus.equalsIgnoreCase("")){
						subquery1 = "and orgrelation.id =" + clnt;
					} else if((spocInvitationStatus.equalsIgnoreCase("") && !spocStatus.equalsIgnoreCase("")) || (spocStatus.equalsIgnoreCase("") && !spocInvitationStatus.equalsIgnoreCase(""))){
						subquery1 = "and orgrelation.id =" + clnt+"";
					}else{
					    subquery1 = "and orgrelation.id =" + clnt+"";
					}
					
					if (isadmin) {
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and ((:varZero  IN (:ownerRolesList)) OR (:varZero NOT IN (:ownerRolesList) and userRole.roles.id IN (:ownerRolesList)))) "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setString("relationType", relationType).setParameterList("ownerRolesList", ownerRolesList).setString("varZero", "0").list();
			            finalList.addAll(spocsList);
			            
			            
					}else if(ids != null && !ids.isEmpty()){
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and ((:varZero  IN (:ownerRolesList)) OR (:varZero NOT IN (:ownerRolesList) and userRole.roles.id IN (:ownerRolesList)))) "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and ((spoc.vendorUser.id = :loginUserId OR spoc.vendorUser IN (:ids)) OR (spoc.clientUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).setString("varZero", "0").setParameterList("ownerRolesList", ownerRolesList).list();
			            finalList.addAll(spocsList);
					}else{
			            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and ((:varZero  IN (:ownerRolesList)) OR (:varZero NOT IN (:ownerRolesList) and userRole.roles.id IN (:ownerRolesList)))) "+subquery1+" "+spocStatus+" "+spocInvitationStatus+" and (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
			            spocsList = query.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setString("varZero", "0").setParameterList("ownerRolesList", ownerRolesList).list();
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
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and ((:varZero  IN (:ownerRolesList)) OR (:varZero NOT IN (:ownerRolesList) and userRole.roles.id IN (:ownerRolesList)))) "+spocStatus+" "+spocInvitationStatus+" ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setString("relationType", relationType).setParameterList("ownerRolesList", ownerRolesList).setString("varZero", "0").list();
	            finalList.addAll(spocsList);
			}else if(ids != null && !ids.isEmpty()){
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and ((:varZero  IN (:ownerRolesList)) OR (:varZero NOT IN (:ownerRolesList) and userRole.roles.id IN (:ownerRolesList)))) "+spocStatus+" "+spocInvitationStatus+" ((spoc.vendorUser.id = :loginUserId OR spoc.vendorUser IN (:ids)) OR (spoc.clientUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setString("varZero", "0").setParameterList("ids", ids).setParameterList("ownerRolesList", ownerRolesList).list();
	            finalList.addAll(spocsList);
			}else{
	            Query query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and ((:varZero  IN (:ownerRolesList)) OR (:varZero NOT IN (:ownerRolesList) and userRole.roles.id IN (:ownerRolesList)))) "+spocStatus+" "+spocInvitationStatus+" (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
	            spocsList = query.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setString("varZero", "0").setParameterList("ownerRolesList", ownerRolesList).list();
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
		Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgLocation.orgLocationName = :orgName and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )");
		
		vandorsList = vendorsListQuery.setString("orgName", orgName).setLong("orgLocationId", orgLocationId).list();
		return vandorsList;
	}
	

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgRelations> getAllClientsByOrgNameOrgLocationId(String orgName, Long orgLocationId) {
		logger.debug("In OrgRelations getAllClientsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRelations> clientsList = null;
		Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgLocation.orgLocationName = :orgName and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor' )");
		clientsList = clientsListQuery.setString("orgName", orgName).setLong("orgLocationId", orgLocationId).list();
		return clientsList;
	}

	@Override
	@Transactional
	public List<OrgRelations> getClientOrVendorListByOrgLocationId(Long orgLocationId, String type) {
		
		if(type.equalsIgnoreCase("Vendor")){
			type = "Client";
		}else{
			type = "Vendor";
		}
		    Session session = this.sessionFactory.getCurrentSession();
			Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :type) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :type )");
			List<OrgRelations> relationsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setString("type", type).list();
			return relationsList;
	}
	
	@Override
	@Transactional
	public List<OrgRelations> getClientOrVendorListByOrgLocationIdForImmigration(Long orgLocationId, String type) {
		
		if(type.equalsIgnoreCase("Vendor")){
			type = "Client";
		}else{
			type = "Vendor";
		}
		Query vendorsListQuery = null;
		if(type.equalsIgnoreCase("Client")){
		    Session session = this.sessionFactory.getCurrentSession();
		 vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :type and orgrelation.isLawFirm ='Y') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :type )");
		}else{
			 Session session = this.sessionFactory.getCurrentSession();
			 vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :type) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :type )");
				
		}
			List<OrgRelations> relationsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setString("type", type).list();
			return relationsList;
	}

	@Override
	@Transactional
	public Integer getAllClientsCountByOrgLocationId(Long orgLocationId) throws Exception {

		logger.debug("In OrgRelations getAllClientsCountByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();

		Query vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :type ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :type )");
		List<OrgRelations> relationsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setString("type", "Client").list();
		
		Integer count = 0;
		if(relationsList != null && !relationsList.isEmpty()){
				count = relationsList.size();
		}
		return count;
	}

	@Override
	@Transactional
	public Integer getAllVendorsCountByOrgLocationId(Long orgLocationId,String type) throws Exception {

		logger.debug("In OrgRelations getAllVendorsCountByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();

			Query vendorsListQuery= session.createQuery("select count(*) from OrgRelations orgrelation where ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :type ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :type ))");
			List<Long> relationsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setString("type",type).list();
		
		Long count = 0l;
		if(relationsList != null && !relationsList.isEmpty()){
				count = relationsList.get(0).longValue();
		}
		return count.intValue();

	}

	@Override
	@Transactional
	public List<OrgRelations> getAllClientsByOrgLocationIdAndDomain(Long orgLocationId, String domain, String responderType)
	{
		logger.debug("In OrgRelations getAllClientsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRelations> clientsList = null;
		// Prasanna vadla : query changed to get new client list for ATS_VMS  
		//Query clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where orgrelation.locationId = :orgLocationId");
		
		Query clientsListQuery;
		if("".equalsIgnoreCase(responderType) || "Client".equalsIgnoreCase(responderType)){
			clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Vendor' and orgrelation.locationId.org.webSite =:domain) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Vendor' and orgrelation.responderLocationId.org.webSite =:domain)");
			clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).setString("domain", domain).list();
		}else{
			clientsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client' and orgrelation.locationId.org.webSite =:domain) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client'  and orgrelation.responderLocationId.org.webSite =:domain)");
			clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).setString("domain", domain).list();
		}
		
		return clientsList;
	}

	@Override
	@Transactional
	public OrgRelations getVendorOrgRelationByResponderLocationIdAndLocationId(Long locationId, Long responderLocationId)
			throws Exception {
		
		
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation left join fetch orgrelation.locationId  left join fetch orgrelation.responderLocationId "
	      		+ " where (orgrelation.locationId = :locationId and orgrelation.responderLocationId = :responderLocationId and orgrelation.initiatorType = 'Vendor') or (orgrelation.responderLocationId = :locationId and orgrelation.locationId = :responderLocationId and orgrelation.responderType = 'Client' )");
		  
	       List<OrgRelations> orgRelationsList = session.createQuery(query.toString()).setLong("locationId", locationId).setLong("responderLocationId", responderLocationId).list();
	       
	       OrgRelations orgRelations = null;
			if(orgRelationsList != null && !orgRelationsList.isEmpty())
			{
				orgRelations = (OrgRelations) orgRelationsList.iterator().next();
			}
			
			return orgRelations;
	}
	
	
	@Override
	@Transactional
	public OrgRelations getOrgRelationByResponderLocationIdAndLocationId(Long responderLocationId, Long locationId)
			throws Exception {
		
		Session session = this.sessionFactory.getCurrentSession();
	      String query = ("from OrgRelations orgrelation left join fetch orgrelation.locationId  left join fetch orgrelation.responderLocationId "
	      		+ " where (orgrelation.locationId = :locationId and orgrelation.responderLocationId = :responderLocationId and orgrelation.responderType = 'Vendor') or (orgrelation.responderLocationId = :locationId and orgrelation.locationId = :responderLocationId and orgrelation.initiatorType = 'Vendor' )");
		  
	       List<OrgRelations> orgRelationsList = session.createQuery(query.toString()).setLong("locationId", locationId).setLong("responderLocationId", responderLocationId).list();
	       
	       OrgRelations orgRelations = null;
			if(orgRelationsList != null && !orgRelationsList.isEmpty())
			{
				orgRelations = (OrgRelations) orgRelationsList.iterator().next();
			}
			
			return orgRelations;
	}

	@Override
	@Transactional
	public OrgRelations getOrgRelationsByUUID(String UUID) throws Exception {
	Session session = this.sessionFactory.getCurrentSession();
   	 String query = ("from OrgRelations orgrelation left join fetch orgrelation.responderLocationId left join fetch orgrelation.locationId where orgrelation.UUID = :UUID ");
      List<OrgRelations> orgRelations = session.createQuery(query.toString()).setString("UUID", UUID).list();
      OrgRelations orgRelation = null;
		if(orgRelations != null && !orgRelations.isEmpty())
		{
			orgRelation = orgRelations.iterator().next();
		}
		return orgRelation;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<OrgRelations> getAllVendorsFilterByOrgLocationIdAndOrgRelationDTO(OrgRelationDTO orgRelationDTO,Long orgLocationId, String status, String vendorsRelationId) throws Exception {
    Session session = this.sessionFactory.getCurrentSession();
    ResponseList<OrgRelations> response = new ResponseList<>();
		String[] level = null;
		if(status != null){
			level = status.split(",");	
		}
		
		String subQuery2 = "";
		String subQuery3  ="";
		Query vendorsListQuery = null;
		Query vendorsCountQuery = null;
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
		
		List<Long> vendorsRelationIdList = new ArrayList<Long>();
		String[] vendorsRelationIdArr = null;
	    if(vendorsRelationId != ""){
	    	vendorsRelationIdArr = vendorsRelationId.split(",");
	    	for(String VRID : vendorsRelationIdArr){
	    		vendorsRelationIdList.add(Long.parseLong(VRID));
	    	}
	    }
		
		String subQuery4 = "";
		
			
		//orgrelation.id IN (:vendorsRelationIdList)
		if(vendorsRelationIdList.size() > 0){
			subQuery4 = " and orgrelation.id IN (:vendorsRelationIdList) ";
		}
		StringBuilder query1 = new StringBuilder("");
		if(orgRelationDTO.getSearchValue() !=null && !"".equalsIgnoreCase(orgRelationDTO.getSearchValue())) {
		 query1.append("and (orgrelation.responderLocationId.orgLocationName like '%" + orgRelationDTO.getSearchValue() + "%' ")
		       .append("OR orgrelation.responderLocationId.country like '%" + orgRelationDTO.getSearchValue() + "%' ")
			   .append("OR orgrelation.responderLocationId.state like '%" + orgRelationDTO.getSearchValue() + "%' ")
			   .append("OR orgrelation.initiatorLevel like '%" + orgRelationDTO.getSearchValue() + "%' )");
					
		}
		List<OrgRelations> vandorsList = null;
		List<Long> countlist = null;
		if(level == null || Arrays.asList(level).contains("")){
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ( (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )) "+subQuery4+" "+query1+"");
				  vendorsCountQuery= session.createQuery("Select Count(*) from OrgRelations orgrelation  where ( (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )) "+subQuery4+" "+query1+"");
			}else {
				  vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ( (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )) "+subQuery4+" ");
				  vendorsCountQuery= session.createQuery("Select Count(*) from OrgRelations orgrelation  where ( (orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' )) "+subQuery4+"");
			}
			if(vendorsRelationIdList.size() > 0){
				vandorsList = vendorsListQuery.setLong("orgLocationId", orgLocationId).setParameterList("vendorsRelationIdList", vendorsRelationIdList).setFirstResult(orgRelationDTO.getStart()).setMaxResults(orgRelationDTO.getLength()).list();
				countlist = vendorsCountQuery.setLong("orgLocationId", orgLocationId).setParameterList("vendorsRelationIdList", vendorsRelationIdList).list();
			
			}else{
		   	   vandorsList = vendorsListQuery .setLong("orgLocationId", orgLocationId).setFirstResult(orgRelationDTO.getStart()).setMaxResults(orgRelationDTO.getLength()) .list();
		       countlist = vendorsCountQuery.setLong("orgLocationId", orgLocationId).list();
			}
			
		}else{
			if(query1.toString() != null && !query1.toString().isEmpty()){
				 vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ( ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) ) "+subQuery4+" "+query1+"");
				 vendorsCountQuery=session.createQuery("Select Count(*) from OrgRelations orgrelation  where ( ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) ) "+subQuery4+" "+query1+"");
			}else {
				 vendorsListQuery= session.createQuery("from OrgRelations orgrelation left join fetch orgrelation.locationId left join fetch orgrelation.responderLocationId orgLocation left join fetch orgLocation.org where ( ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) ) "+subQuery4+" ");
				 vendorsCountQuery=session.createQuery("Select Count(*) from OrgRelations orgrelation  where ( ("+subQuery2+" and orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = 'Client') or ("+subQuery3+" and orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = 'Client' ) ) "+subQuery4+"");
			}
			if(vendorsRelationIdList.size() > 0){
				vandorsList = vendorsListQuery .setLong("orgLocationId", orgLocationId).setParameterList("vendorsRelationIdList", vendorsRelationIdList).setFirstResult(orgRelationDTO.getStart()).setMaxResults(orgRelationDTO.getLength()).list();
				countlist = vendorsCountQuery.setLong("orgLocationId", orgLocationId).setParameterList("vendorsRelationIdList", vendorsRelationIdList).list();
			}else{
			   vandorsList = vendorsListQuery .setLong("orgLocationId", orgLocationId) .setFirstResult(orgRelationDTO.getStart()).setMaxResults(orgRelationDTO.getLength()).list();
			   countlist = vendorsCountQuery.setLong("orgLocationId", orgLocationId) .list();
			}
		}
		
		int totalCount = 0;
		if (orgRelationDTO != null) {
			if(countlist != null && !countlist.isEmpty()) {
				totalCount =countlist.iterator().next().intValue();
			}
		} 
		response.setRecordsTotal(totalCount);
		
		if(query1.toString() != null && !query1.toString().isEmpty() && vandorsList != null && !vandorsList.isEmpty()){
			response.setRecordsFiltered(vandorsList.size());
		}else {
			response.setRecordsFiltered(totalCount);
		}
		response.setData(vandorsList);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseList<OrgSpoc> getAllVendorsContactFilterListByOrgLocationIdAndTabAccessDTO(OrgSpocDTO orgSpocDTO,Long loginUserId, List<Long> ids, Long orgLocationId, String relationType,String status, Long tabId, List<Long> vendorOrgLocationsIds) {
     Session session = this.sessionFactory.getCurrentSession();
		String actionsQuery = "select sa.id from SourceleadActions sa where sa.tabs.id = :tabId  ";
	     List<Long> tabActionsIds = session.createQuery(actionsQuery.toString()).setLong( "tabId", tabId).list();
	     
	     String rolesQuery = "from Roles roles where roles.orgLocations.id IN (:vendorOrgLocationsIds)";
	     List<Roles> rolesList = session.createQuery(rolesQuery.toString()).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).list();
	     List<Long> ownerRolesList = new ArrayList<Long>();
	     Query query  = null;
	     Query queryCount = null;
	     for(Roles roles : rolesList){
	    	 if(null != roles.getActionId()){
	    		 List<Long> roleActionsList = new ArrayList<Long>();
	    		 
	    		 /*Remove/filter null & empty string objects from array-lambda stream*/
	    		 for (String s : Arrays.stream(roles.getActionId().split(",")).filter(value -> value != null && value.length() > 0).toArray(size -> new String[size])){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }
	    		 /*for (String s : roles.getActionId().split(",")){
	        		 roleActionsList.add(Long.parseLong(s));
	        	 }*/
	        	 
	        	 roleActionsList.retainAll(tabActionsIds);
	        	 if(roleActionsList.size() > 0){
	        		 ownerRolesList.add(roles.getId());
	        	 }
	    	 }
	    	 
	     }
		
		String[] tokens = status.split(",");
		
		Boolean isadmin = userRoleDao.isUserAdminByOrgLocationId(loginUserId,orgLocationId);
		
			ResponseList<OrgSpoc> response = new ResponseList<>();
			List<OrgSpoc> spocsList = null;
			List<Long> spocsListCount = null;
			String spocStatus = "";
			
		    //spoc status	
			if (Arrays.asList(tokens).contains("Active")) {
				spocStatus = spocStatus + "spoc.spocStatus = 'Active' or ";
			}
			
			if (Arrays.asList(tokens).contains("New")) {
				spocStatus = spocStatus + "spoc.spocStatus = 'New' or ";
			}
		 
			if (Arrays.asList(tokens).contains("Contracted")) {
				spocStatus = spocStatus + "spoc.spocStatus = 'Contracted' or ";
			}
			if(!spocStatus.equalsIgnoreCase("")){
			   spocStatus = " and (" + spocStatus.substring(0, spocStatus.length() - 4) + ")";
			}
		 
			if(!spocStatus.equalsIgnoreCase("")){
				spocStatus = spocStatus +" and "; 
			}
			
			StringBuilder query1 = new StringBuilder("");
			if(orgSpocDTO.getSearchValue() !=null && !"".equalsIgnoreCase(orgSpocDTO.getSearchValue())) {
			 query1.append("and (spoc.vendorUser.email like '%" + orgSpocDTO.getSearchValue() + "%' ")
			       .append("OR (spoc.vendorUser.firstName like '" + orgSpocDTO.getSearchValue() + "%' OR spoc.vendorUser.lastName like '%" + orgSpocDTO.getSearchValue() + "')")
				   .append("OR spoc.vendorUser.mobilePhone like '%" + orgSpocDTO.getSearchValue() + "%' ")
			       .append("OR (spoc.clientUser.firstName like '" + orgSpocDTO.getSearchValue() + "%' OR spoc.clientUser.lastName like '%" + orgSpocDTO.getSearchValue() + "')")
				   .append("OR spoc.vendorLocationId.orgLocationName like '%" + orgSpocDTO.getSearchValue() + "%' )");
			}
			
			 
			if (isadmin) {
				  //list query
	              query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) "+spocStatus+"   ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' "+query1+"");
	              spocsList = query.setLong("orgLocationId", orgLocationId).setFirstResult(orgSpocDTO.getStart()).setMaxResults(orgSpocDTO.getLength()).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setString("relationType", relationType).setParameterList("ownerRolesList", ownerRolesList).list();

	              //count query
	              queryCount = session.createQuery("Select Count(*) from OrgSpoc spoc   where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) "+spocStatus+"   ((spoc.orgRelations.locationId = :orgLocationId and spoc.orgRelations.initiatorType = :relationType ) or (spoc.orgRelations.responderLocationId = :orgLocationId and spoc.orgRelations.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' "+query1+"");
	              spocsListCount = queryCount.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setString("relationType", relationType).setParameterList("ownerRolesList", ownerRolesList).list();
			}else if(ids != null && !ids.isEmpty()){
				//List Query
	              query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) "+spocStatus+"  ((spoc.vendorUser.id = :loginUserId OR spoc.vendorUser IN (:ids)) OR (spoc.clientUser.id =:loginUserId)) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
	              spocsList = query.setLong("orgLocationId", orgLocationId).setFirstResult(orgSpocDTO.getStart()).setMaxResults(orgSpocDTO.getLength()).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).setParameterList("ownerRolesList", ownerRolesList).list();
			
	              //Count Query
	              queryCount= session.createQuery("Select Count(*) from OrgSpoc spoc where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) "+spocStatus+"  ((spoc.vendorUser.id = :loginUserId OR spoc.vendorUser IN (:ids)) OR (spoc.clientUser.id =:loginUserId)) and ((spoc.orgRelations.locationId = :orgLocationId and spoc.orgRelations.initiatorType = :relationType ) or (spoc.orgRelations.responderLocationId = :orgLocationId and spoc.orgRelations.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
	              spocsListCount = queryCount.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ids", ids).setParameterList("ownerRolesList", ownerRolesList).list();
			
	              
			} else{
				  //List Qury
	              query= session.createQuery("from OrgSpoc spoc left join fetch spoc.orgRelations orgrelation left join fetch orgrelation.locationId orgLocation left join fetch orgLocation.org where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) "+spocStatus+"   (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((orgrelation.locationId = :orgLocationId and orgrelation.initiatorType = :relationType ) or (orgrelation.responderLocationId = :orgLocationId and orgrelation.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
	              spocsList = query.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ownerRolesList", ownerRolesList).list();
			   
	              // Count Qury
	              queryCount= session.createQuery("Select Count(*) from OrgSpoc spoc   where spoc.clientUser IS NOT NULL and spoc.vendorUser IS NOT NULL and spoc.vendorUser.id IN (select userRole.user.id from UserRole userRole where userRole.userState != 'INACTIVE' and userRole.roles.id IN (:ownerRolesList)) "+spocStatus+"   (spoc.vendorUser.id = :loginUserId OR spoc.clientUser.id =:loginUserId) and ((spoc.orgRelations.locationId = :orgLocationId and spoc.orgRelations.initiatorType = :relationType ) or (spoc.orgRelations.responderLocationId = :orgLocationId and spoc.orgRelations.responderType = :relationType )) and (spoc.vendorLocationId.id IN (:vendorOrgLocationsIds)) and spoc.status <> '0' ");
	              spocsListCount = queryCount.setLong("orgLocationId", orgLocationId).setParameterList( "vendorOrgLocationsIds", vendorOrgLocationsIds).setLong("loginUserId", loginUserId).setString("relationType", relationType).setParameterList("ownerRolesList", ownerRolesList).list();
			   
			}
			int totalCount = 0;
			if (orgSpocDTO != null) {
				if(spocsListCount != null && !spocsListCount.isEmpty()) {
					totalCount =spocsListCount.iterator().next().intValue();
				}
			} 
			response.setRecordsTotal(totalCount);
			
			if(query1.toString() != null && !query1.toString().isEmpty() && spocsList != null && !spocsList.isEmpty()){
				response.setRecordsFiltered(spocsList.size());
			}else {
				response.setRecordsFiltered(totalCount);
			}
			response.setData(spocsList);
			
		return response;	
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public OrgRelations getClientsByOrgLocationIdAndName(Long orgLocationId, String name) throws Exception {
		logger.debug("In OrgRelations getAllClientsByOrgLocationId method");
		Session session = this.sessionFactory.getCurrentSession();
		List<OrgRelations> clientsList = null;
		Query clientsListQuery= session.createQuery("from OrgRelations orgrelation where (orgrelation.locationId.id = :orgLocationId and orgrelation.initiatorType = 'Vendor' and orgrelation.responderLocationId.orgLocationName= :name) or (orgrelation.responderLocationId.id = :orgLocationId and orgrelation.responderType = 'Vendor' and orgrelation.locationId.orgLocationName= :name )");
		clientsList = clientsListQuery.setLong("orgLocationId", orgLocationId).setString("name", name).list();
		OrgRelations orgLocation = null;
		if(clientsList != null && !clientsList.isEmpty())
		{
			orgLocation = (OrgRelations) clientsList.iterator().next();
		}
		return orgLocation;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<OrgRelationDTO> getAllClientsListOrgLocationId(Long orgLocationId) throws Exception {
		
		
		List<OrgRelationDTO> orgRelationDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
				
		
 	  query ="SELECT "
 			+" ORL.ID AS orgRelId, "
			+" CASE WHEN "
			+" ORL.LOCATION_ID = :orgLocationId  AND ORL.INITIATOR_TYPE = 'Vendor' THEN ORL.RESPONDER_LOCATION_ID "
			+" ELSE  ORL.LOCATION_ID "
			+" END AS  clientLocationId, "
			+" (SELECT "
			+" ORG_NAME"
			+" FROM"
			+" ORG_LOCATIONS OL"
			+" WHERE"
			+" (OL.ID = ORL.LOCATION_ID OR OL.ID = ORL.RESPONDER_LOCATION_ID) AND OL.ID != :orgLocationId ) AS clientName"
			+" FROM"
			+" ORG_RELATIONS ORL"
			+" WHERE"
			+" (ORL.LOCATION_ID = :orgLocationId AND ORL.INITIATOR_TYPE = 'Vendor') OR (ORL.RESPONDER_LOCATION_ID = :orgLocationId AND ORL.RESPONDER_TYPE = 'Vendor')"
			+" GROUP BY ORL.ID";
		    
            Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(OrgRelationDTO.class));
			orgRelationDTOList = queryObj.list();
			
			
			return orgRelationDTOList;
	}

	@Override
	@Transactional
	public List<OrgRelationDTO> getAllVendorsListOrgLocationId(Long orgLocationId) throws Exception {
		
		List<OrgRelationDTO> orgRelationDTOList = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = null;
				
		
 	  query ="SELECT "
 			+" ORL.ID AS orgRelId, "
 			+" CASE WHEN "
			+" ORL.LOCATION_ID = :orgLocationId  AND ORL.INITIATOR_TYPE = 'Client' THEN ORL.RESPONDER_LOCATION_ID "
			+" ELSE  ORL.LOCATION_ID "
			+" END AS  vendorLocationId, "
			+" (SELECT "
			+" ORG_NAME"
			+" FROM"
			+" ORG_LOCATIONS OL"
			+" WHERE"
			+" (OL.ID = ORL.LOCATION_ID OR OL.ID = ORL.RESPONDER_LOCATION_ID) AND OL.ID != :orgLocationId ) AS vendorName"
			+" FROM"
			+" ORG_RELATIONS ORL"
			+" WHERE"
			+" (ORL.LOCATION_ID = :orgLocationId AND ORL.INITIATOR_TYPE = 'Client') OR (ORL.RESPONDER_LOCATION_ID = :orgLocationId AND ORL.RESPONDER_TYPE = 'Client')"
			+" GROUP BY ORL.ID";
 	  
            Query queryObj = session.createSQLQuery(query);
			queryObj.setLong("orgLocationId", orgLocationId);
			queryObj.setResultTransformer(Transformers.aliasToBean(OrgRelationDTO.class));
			orgRelationDTOList = queryObj.list();
			
			
			return orgRelationDTOList;
	
	}

	@Override
	@Transactional
	public int updatePaymentTermsInOrgrelations() {
		int count = 0;
		try {
			System.out.println("-------------hi Start of updatePaymentTermsInOrgrelations()-------------");
			Session session = this.sessionFactory.getCurrentSession();
			String query = "select ors.id,ors.PaymentTerm from OrgRelations ors";
			List<Object[]> queryList = session.createQuery(query).list();
			if(queryList != null && !queryList.isEmpty()) {
				for(Object[] obj : queryList) {
					System.out.println("ID : " + obj[0] + " PAYMENT_TERM : " + obj[1]);
					OrgRelations orgRelations = session.get(OrgRelations.class, Long.valueOf(obj[0].toString()));
					
					String query2 = "from OrgRelations ors where ors.id = " + obj[0];
					Query queryObj2 = session.createQuery(query2);
					List<OrgRelations> rshList = queryObj2.list();
					for(OrgRelations orgRe : rshList) {
						if(StringUtils.isNotBlank(orgRelations.getPaymentTerm())){
						if(orgRelations.getPaymentTerm().equals("SelectPaymentTerms")){
							orgRe.setPaymentTerm(null);
							session.saveOrUpdate(orgRe);
							count++;
						}
						
					}
					}
				}
			}
			System.out.println("-------------End of updatePaymentTermsInOrgrelations()-------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
}