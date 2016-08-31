package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * 
 * @author : Venkat Idapalapati
 *
 */

@Service
public class ClientServiceImpl implements ClientService{
	
	
	@Autowired
	private OrgService orgService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	private OrgRelationsService orgRelationsService;

	@Autowired
	private ProjectsService projectsService;

	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;

	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
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
	public void saveClient(JSONObject jsonObj,User user,OrgLocations orgLocations) throws Exception {
		
		Org org = populateOrg(jsonObj, user.getUserCode());
		orgService.addOrganization(org);
		OrgLocations clientOrgLocation = populateOrgLocations(jsonObj, org, orgLocations.getLocationCode(), user.getUserCode());
		orgLocationService.addOrgLocations(clientOrgLocation);
		OrgRelations orgRelations = populateOrgRelations(clientOrgLocation, orgLocations, user.getUserCode());
		orgRelationsService.addOrgRelations(orgRelations);
		
		if(jsonObj.has("maxTsRowNum")){
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		
		for (int c = 1; c < maxCount; c++) {
			Projects project = new Projects();
			ProjectApproverHierarchy projectApproverHierarchy = new ProjectApproverHierarchy();
			Set<ProjectApproverHierarchy> projectApproverSet = new HashSet<ProjectApproverHierarchy>();
			project.setProjectSequeneNumber(jsonObj.getString(c + "projectId"));
			project.setProjectName(jsonObj.getString(c + "projectName"));
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			project.setOrgLocationsId(orgLocations);
			project.setOrgRelationId(orgRelations); // newly Added

			String startDateString = jsonObj.getString(c + "projectStartDate");
			String endDateString = jsonObj.getString(c + "projectEndDate");// projectEndDate
			Date startDate = getDateFormat(startDateString);
			if(!isNullOrEmpty(endDateString)){
				Date endDate = getDateFormat(endDateString);
				project.setEndDate(endDate);
			}
			
			project.setStartDate(startDate);
			project.setProjectStatus(Project_Status.STARTED);
			project.setCreatedDate(new Date());
			projectApproverHierarchy.setProjectId(project);
			User approverUserId1 = new User();
			approverUserId1.setId(Long.parseLong(jsonObj.getString(c + "projectManager")));
			projectApproverHierarchy.setApproverUserId(approverUserId1);
			projectApproverHierarchy.setLevel(1);
			ProjectApproverHierarchy projectApproverHierarchy1 = new ProjectApproverHierarchy();
			if (!isNullOrEmpty(jsonObj.getString("level2Manager"))) {
				projectApproverHierarchy1.setProjectId(project);
				User approverUserId2 = new User();
				approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
				projectApproverHierarchy1.setApproverUserId(approverUserId2);
				projectApproverHierarchy1.setLevel(2);
				projectApproverSet.add(projectApproverHierarchy1);
			}
			projectApproverSet.add(projectApproverHierarchy);

			project.setProjectApprovers(projectApproverSet);
			project.setCreatedBy(user.getUserCode());
			/* Saving for Projects */
			projectsService.saveProject(project);
			commonUtil.storeSourceleadTasks(user, project);
		}

	}
		
	}
	
	/**
	 * @author VenkatKesav
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * 
	 * This method is for client saving.
	 * 
	 * @param jsonObj,user, orgLocations
	 *  
	 * @return void 
	 * 
	 * @throws NullPointer
	 */
	@Override
public void storeClient(JSONObject jsonObj,User user,OrgLocations orgLocations,String responderType,Org loginOrg) throws Exception {
		
		HashSet<OrgLocations> orgLocationsList = new HashSet<OrgLocations>();
		OrgLocations clientOrgLocations = null;
		Set<OrgRelations> orgRelationsList = null;
		User spocUser = null;
		if(jsonObj.has("orgLocationId")){
			clientOrgLocations = orgLocationService.getOrgLocations(Long.parseLong(jsonObj.getString("orgLocationId")));
		}
		
		if(clientOrgLocations == null){
			Org org = populateOrg(jsonObj, user.getUserCode());
			clientOrgLocations =   orgLocationService.orgLocationsListByJson(jsonObj, org, orgLocations, user.getUserCode(),responderType);
			
			orgRelationsList =  orgRelationsService.orgRelationsListByJson(jsonObj, clientOrgLocations, orgLocations, user.getUserCode(),responderType);
			clientOrgLocations.setOrgRelationsList(orgRelationsList);
			orgLocationsList.add(clientOrgLocations);
			org.setOrgLocationsList(orgLocationsList);
			orgService.addOrganization(org);
		}else{
			orgRelationsList =  orgRelationsService.orgRelationsListByJson(jsonObj, clientOrgLocations, orgLocations, user.getUserCode(),responderType);
			clientOrgLocations.setOrgRelationsList(orgRelationsList);
			orgLocationsList.add(clientOrgLocations);
			Org org = clientOrgLocations.getOrg();
			org.setOrgLocationsList(orgLocationsList);
			orgService.addOrganization(org);
		}

		spocUser = userService.findByEmail(jsonObj.getString("email"));
		if(spocUser == null){
			spocUser = userService.storeUser(jsonObj,user,clientOrgLocations,"CREATEUSER",responderType,loginOrg);
		}
		
		
		if(jsonObj.has("owner")){
			String ownersId = jsonObj.getString("owner");
			ownersId = ownersId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] owners = ownersId.split(",");
			
			for(int i=0 ;i<owners.length;i++ ){
				OrgSpoc orgSpoc = new OrgSpoc();
				for(OrgLocations orl :orgLocationsList){
					Set<OrgRelations> orgr = orl.getOrgRelationsList();
					for (OrgRelations orgRelations : orgr) {
						orgSpoc.setOrgRelations(orgRelations);
					}
				}
				orgSpoc.setSpocUser(spocUser);
				User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				orgSpoc.setOwnerUser(ownerUser);
				orgSpoc.setSpocStatus(jsonObj.getString("spocStatus"));
				orgSpoc.setInvitationStatus("NEW");
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				orgSpoc.setCreatedDate(commonUtil.currentDate());
				orgSpoc.setCreatedBy(user.getUserCode());
				orgSpoc.setUpdatedDate(commonUtil.currentDate());
				orgSpoc.setUpdatedBy(user.getUserCode());
				orgSpocService.addOrgSpoc(orgSpoc);
				}
		}
		
	}
	/**
	 * populating Org data
	 * @param jsonObj
	 * @param loginUserCode
	 * @return Org
	 * @throws JSONException
	 */
		private Org populateOrg(JSONObject jsonObj, String loginUserCode) throws JSONException {
			Org org = new Org();
		
			org.setOrgName(jsonObj.getString("orgName"));
			org.setOrgType("Company");
			
			if(jsonObj.has("companyWebsite")){
				org.setWebSite(convertWebsite(jsonObj.getString("companyWebsite")));
			}
			if(jsonObj.has("webSite")){
				org.setWebSite(convertWebsite(jsonObj.getString("webSite")));
			}
			
			org.setOrgType("Guest");
			org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
			org.setCreatedDate(commonUtil.currentDate());
			org.setUpdatedBy(loginUserCode);
			org.setCreatedBy(loginUserCode);
			org.setUpdatedDate(commonUtil.currentDate());
			return org;

		}
		
		
		/**
		 * populating OrgLocations Data
		 * @param jsonObj
		 * @param addOrg
		 * @param locationCode
		 * @param loginUserCode
		 * @return OrgLocations
		 * @throws JSONException
		 */
			private OrgLocations populateOrgLocations(JSONObject jsonObj, Org addOrg, String locationCode, String loginUserCode)
					throws JSONException {
				OrgLocations orgLocations = new OrgLocations();
				orgLocations.setOrg(addOrg);
				if(jsonObj.has("phone")){
					orgLocations.setPhone(jsonObj.getString("phone"));
				}
				if(jsonObj.has("emailId")){
					orgLocations.setEmail(jsonObj.getString("emailId"));
				}
				
				if(jsonObj.has("country")){
					orgLocations.setCountry(jsonObj.getString("country"));
				}
				if(jsonObj.has("state")){
					orgLocations.setState(jsonObj.getString("state"));
				}
				
				if(jsonObj.has("city")){
					orgLocations.setCity(jsonObj.getString("city"));
				}
				
				String clientCountry = locationCode.substring(Math.max(locationCode.length() - 2, 0));
				orgLocations.setLocationCode(addOrg.getOrgCode() + clientCountry);
				orgLocations.setCreatedDate(new Date());
				orgLocations.setCreatedBy(loginUserCode);
				orgLocations.setUpdatedBy(loginUserCode);
				orgLocations.setUpdatedDate(new Date());
				return orgLocations;
			}
			
			
			/**
			 * populate OrgRelations Data
			 * @param orgLocations
			 * @param loginOrgLocations
			 * @param loginUserCode
			 * @return
			 */
				private OrgRelations populateOrgRelations(OrgLocations clientOrgLocation, OrgLocations orgLocations,String loginUserCode) {

					OrgRelations orgRelations = new OrgRelations();
					orgRelations.setResponderLocationId(clientOrgLocation);
					orgRelations.setLocationId(orgLocations);
					orgRelations.setStatus("ACTIVE");
					//orgRelations.setRelationType("Customer");
					orgRelations.setInitiatorType("Client");
					orgRelations.setResponderType("");
					orgRelations.setCreatedBy(loginUserCode);
					orgRelations.setCreatedDate(new Date());
					return orgRelations;
				}
		
		

		private String convertWebsite(String website){
			if(!website.startsWith("http") && !website.startsWith("https")){
				 website = "http://" + website;
		    }        
		    URL netUrl = null;
			try {
				netUrl = new URL(website);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
		    String host = netUrl.getHost();
		    if(host.startsWith("www")){
		        host = host.substring("www".length()+1);
		    }
		    return host;
		}
		
		/**
		 * Returns Date
		 * 
		 * @param startDateString
		 * @return
		 */
		private Date getDateFormat(String startDateString) {

			Date formatedDate = null;
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				formatedDate = df.parse(startDateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return formatedDate;
		}

		/**
		 * check given string empty or null
		 * @param str
		 * @return boolean
		 */
		protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}


		@Override
		public void updateClientDetails(JSONObject jsonObj, Long orgRelationId, Long clientLocationId,Long loginLocationId) throws Exception {
			
			//OrgRelations orgRelation = orgRelationsService.getClientByOrgLocationId(orgRelationId);
			OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(clientLocationId,orgRelationId);
			
			if(null != orgRelation){
				//OrgLocations orgLocations = orgRelation.getResponderLocationId();
				
				 OrgLocations orgLocations = null;
					//if login user org id is equals to client location id then we get vendor list of ResponderLocationId Org details 
					 if(orgRelation.getLocationId().getId() == loginLocationId){ 
						 orgLocations = orgRelation.getResponderLocationId();
					 }
					 if(orgRelation.getResponderLocationId().getId() == loginLocationId){ 
						 orgLocations = orgRelation.getLocationId();
					 }
					if(jsonObj.getString("level") != null && !jsonObj.getString("level").isEmpty()){
						orgRelation.setLevel(jsonObj.getString("level"));
				     }
				 orgRelationsService.addOrgRelations(orgRelation);
				
				
				Org org = orgLocations.getOrg();
				if(null != org){
				if(jsonObj.getString("clientName") != null && !jsonObj.getString("clientName").isEmpty()){
					org.setOrgName(jsonObj.getString("clientName"));
				}
				
				if(jsonObj.getString("companyWebsite") != null && !jsonObj.getString("companyWebsite").isEmpty()){
					org.setWebSite(jsonObj.getString("companyWebsite"));
				}
				orgService.updateOrg(org);
				}	
				if(jsonObj.getString("clientCity") != null && !jsonObj.getString("clientCity").isEmpty()){
					orgLocations.setCity(jsonObj.getString("clientCity"));
				}else{
					orgLocations.setCity("");
				}
				if(jsonObj.getString("clientState") != null && !jsonObj.getString("clientState").isEmpty()){
					orgLocations.setState(jsonObj.getString("clientState"));
				}else{
					
					orgLocations.setState("");
				}
				if(jsonObj.getString("clientCountry") != null && !jsonObj.getString("clientCountry").isEmpty()){
					orgLocations.setCountry(jsonObj.getString("clientCountry"));
			     }else{
			    	 
			    	 orgLocations.setCountry("");
			     }
				/*if(jsonObj.has("EmailId") && jsonObj.getString("EmailId") !=null && !jsonObj.getString("EmailId").isEmpty()){
					orgLocations.setEmail(jsonObj.getString("EmailId"));
				}else{
					
					orgLocations.setEmail("");
				}*/
				/*if(jsonObj.has("clientPhoneNumber") && jsonObj.getString("clientPhoneNumber") != null && !jsonObj.getString("clientPhoneNumber").isEmpty()){
					orgLocations.setPhone(jsonObj.getString("clientPhoneNumber"));
				}else{
					orgLocations.setPhone("");
				}*/
				orgLocationService.updateOrgLocations(orgLocations);
			}else{
				StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"ORG RELATIONS"));
				throw new SourceLeadObjectNotFoundException(message.toString());
			}
			
		}

		
		@Override
		public void updateVendorDetails(JSONObject jsonObj, Long orgRelationId, Long vendorLocationId,Long loginOrgId) throws Exception {
			
			OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(vendorLocationId,orgRelationId);
			String vendorName = "vendorName";
			String companyWebsite = "companyWebsite";
			String vendorCity = "vendorCity";
			String vendorState = "vendorState";
			String vendorCountry = "vendorCountry" ;
			//String emailId = "EmailId";
			String level = "level";
			
			if(null != orgRelation){
				 OrgLocations orgLocations = null;
				//if login user org id is equals to vendor location id then we get vendor list of ResponderLocationId Org details 
				 if(orgRelation.getLocationId().getId()== loginOrgId){ 
					 orgLocations = orgRelation.getResponderLocationId();
				 }
				 if(orgRelation.getResponderLocationId().getId()== loginOrgId){ 
					 orgLocations = orgRelation.getLocationId();
				 }
				orgRelation.setLevel(jsonObj.getString(level));
			 orgRelationsService.addOrgRelations(orgRelation);		
				 
				Org org = orgLocations.getOrg();
				if(null != org){
					if(jsonObj.getString(vendorName) != null && !jsonObj.getString(vendorName).isEmpty()){
						org.setOrgName(jsonObj.getString(vendorName));
					}
					
					if(jsonObj.getString(companyWebsite) != null && !jsonObj.getString(companyWebsite).isEmpty()){
						org.setWebSite(jsonObj.getString(companyWebsite));
					}
				orgService.updateOrg(org);
				}	
				if(jsonObj.getString(vendorCity) != null && !jsonObj.getString(vendorCity).isEmpty()){
					orgLocations.setCity(jsonObj.getString(vendorCity));
				}else{
					orgLocations.setCity("");
				}
				if(jsonObj.getString(vendorState) != null && !jsonObj.getString(vendorState).isEmpty()){
					orgLocations.setState(jsonObj.getString(vendorState));
				}else{
					
					orgLocations.setState("");
				}
				if(jsonObj.getString(vendorCountry) != null && !jsonObj.getString(vendorCountry).isEmpty()){
					orgLocations.setCountry(jsonObj.getString(vendorCountry));
			     }else{
			    	 
			    	 orgLocations.setCountry("");
			     }
				/*if(jsonObj.getString(emailId) !=null && !jsonObj.getString(emailId).isEmpty()){
					orgLocations.setEmail(jsonObj.getString(emailId));
				}else{
					orgLocations.setEmail("");
				}*/
				orgLocationService.updateOrgLocations(orgLocations);
			}else{
				StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"ORG RELATIONS"));
				throw new SourceLeadObjectNotFoundException(message.toString());
			}

		}
}
