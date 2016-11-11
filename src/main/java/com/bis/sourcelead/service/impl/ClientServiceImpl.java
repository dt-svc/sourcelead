package com.bis.sourcelead.service.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
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
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	SubmissionTemplateService  submissionTemplateService;
	
	@Autowired
	RolesService rolesService;
	
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
			Org org = null;
			User spocUser = null;
			SubmissionTemplate submissionTemplate = null;
			SubmissionTemplate sourceLeadDefaultTemplate = null;
			if(jsonObj.has("orgLocationId")){
				clientOrgLocations = orgLocationService.getOrgLocations(Long.parseLong(jsonObj.getString("orgLocationId")));
				if(jsonObj.has("country") && !isNullOrEmpty(jsonObj.getString("country"))){
					clientOrgLocations.setCountry(jsonObj.getString("country"));
				}else if(jsonObj.has("orgCountry") && !isNullOrEmpty(jsonObj.getString("orgCountry"))){
					clientOrgLocations.setCountry(jsonObj.getString("orgCountry"));
				}
				if(jsonObj.has("state") && !isNullOrEmpty(jsonObj.getString("state"))){
					clientOrgLocations.setState(jsonObj.getString("state"));
				}else if(jsonObj.has("orgState") && !isNullOrEmpty(jsonObj.getString("orgState"))){
					clientOrgLocations.setCountry(jsonObj.getString("orgState"));
				}
				
				if(jsonObj.has("city") && !isNullOrEmpty(jsonObj.getString("city"))){
					clientOrgLocations.setCity(jsonObj.getString("city"));
				}else if(jsonObj.has("orgCity") && !isNullOrEmpty(jsonObj.getString("orgCity"))){
					clientOrgLocations.setCountry(jsonObj.getString("orgCity"));
				}
				
			}
			
			if(clientOrgLocations == null){
				org = populateOrg(jsonObj, user.getUserCode(),responderType);
				clientOrgLocations =   orgLocationService.orgLocationsListByJson(jsonObj, org, orgLocations, user.getUserCode(),responderType);
				
				orgRelationsList =  orgRelationsService.orgRelationsListByJson(jsonObj, clientOrgLocations, orgLocations, user.getUserCode(),responderType);
				clientOrgLocations.setOrgRelationsList(orgRelationsList);
				orgLocationsList.add(clientOrgLocations);
				org.setOrgLocationsList(orgLocationsList);
				orgService.addOrganization(org);
				saveOrgBasedRoles(clientOrgLocations,user);
			}else{
				orgRelationsList =  orgRelationsService.orgRelationsListByJson(jsonObj, clientOrgLocations, orgLocations, user.getUserCode(),responderType);
				clientOrgLocations.setOrgRelationsList(orgRelationsList);
				orgLocationsList.add(clientOrgLocations);
				org = clientOrgLocations.getOrg();
				if(jsonObj.has("companyWebsite")){
					org.setWebSite(convertWebsite(jsonObj.getString("companyWebsite")));
				}
				if(jsonObj.has("webSite")){
					org.setWebSite(convertWebsite(jsonObj.getString("webSite")));
				}
				org.setOrgLocationsList(orgLocationsList);
				orgService.addOrganization(org);
			}
			
			if(jsonObj.has("email")){
				spocUser = userService.storeUser(jsonObj,user,clientOrgLocations,"CREATEUSER",responderType,loginOrg);
				if(!isNullOrEmpty(jsonObj.getString("roleType")) && null == clientOrgLocations.getUser() && null == org.getUser()){
					clientOrgLocations.setUser(spocUser);
					org.setUser(spocUser);
					orgLocationService.updateOrgLocations(clientOrgLocations);
					orgService.addOrganization(org);
				}
			}
			if(jsonObj.has("owner")){
				String ownersId =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("owner");
					ownersId = jsonArray.toString();
				}
				catch(JSONException e){
					ownersId =jsonObj.getString("owner");
				}
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
				orgSpoc.setResponderLocationId(clientOrgLocations);
				orgSpoc.setInitiatorLocationId(orgLocations);
				User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				orgSpoc.setOwnerUser(ownerUser);
				if(jsonObj.has("spocStatus")){
					orgSpoc.setSpocStatus(jsonObj.getString("spocStatus"));
				}else{
					orgSpoc.setSpocStatus(Constants.ACTIVE);
				}
				if(jsonObj.has("isMailSent")){
					orgSpoc.setInvitationStatus(Constants.MAIL_SENT);
				}else{
					orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
				}
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				orgSpoc.setCreatedDate(commonUtil.currentDate());
				orgSpoc.setCreatedBy(user.getUserCode());
				orgSpoc.setUpdatedDate(commonUtil.currentDate());
				orgSpoc.setUpdatedBy(user.getUserCode());
				orgSpocService.addOrgSpoc(orgSpoc);
				}
			}
			
			if(org.getOrgType().equalsIgnoreCase("Guest")){
				submissionTemplate = new SubmissionTemplate();
				sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateById(1L);
				submissionTemplate.setTemplateName(org.getOrgName()+" Default Template");
				submissionTemplate.setDescription(org.getOrgName()+" Default Template");
				submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
				submissionTemplate.setOrgLocationId(clientOrgLocations);
				submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
				submissionTemplate.setCreatedBy(user.getUserCode());
				submissionTemplate.setCreatedDate(new Date());
				submissionTemplateService.addSubmissionTemplate(submissionTemplate);
			}
			
			submissionTemplate = submissionTemplateService.getDefaultSubmissionTemplateByClientLocationIdAndOrgLocationId(clientOrgLocations.getId(), orgLocations.getId());
			if(submissionTemplate == null){
				submissionTemplate = new SubmissionTemplate();
				sourceLeadDefaultTemplate = submissionTemplateService.getSubmissionTemplateById(1L);
				submissionTemplate.setTemplateName("Default Template");
				submissionTemplate.setDescription("Default Template Description");
				submissionTemplate.setJsonString(sourceLeadDefaultTemplate.getJsonString());
				submissionTemplate.setIsDefault("Y");
				if("Client".equals(responderType)){
					submissionTemplate.setClientLocationId(clientOrgLocations);
					submissionTemplate.setOrgLocationId(orgLocations);
				}else if("Vendor".equals(responderType)){
					submissionTemplate.setClientLocationId(orgLocations);
					submissionTemplate.setOrgLocationId(clientOrgLocations);
				}
				submissionTemplate.setCreatedBy(user.getUserCode());
				submissionTemplate.setCreatedDate(new Date());
				submissionTemplateService.addSubmissionTemplate(submissionTemplate);
			}
		}
	
	
	/**
	 * populating Org data
	 * @param jsonObj
	 * @param loginUserCode
	 * @return Org
	 * @throws JSONException
	 */
		private Org populateOrg(JSONObject jsonObj, String loginUserCode,String responderType) throws JSONException {
			Org org = new Org();
		
			if(jsonObj.has("orgName")){
				org.setOrgName(jsonObj.getString("orgName"));
			}
			
			if(jsonObj.has("companyWebsite")){
				org.setWebSite(convertWebsite(jsonObj.getString("companyWebsite")));
			}
			if(jsonObj.has("webSite")){
				org.setWebSite(convertWebsite(jsonObj.getString("webSite")));
			}
			org.setOrgType("Guest");
			
			if(responderType.equalsIgnoreCase("Client")){
				org.setModulesId(Integer.toString(Constants.MODULE_ATS));
			}
			else if(responderType.equalsIgnoreCase("Vendor")){
				org.setModulesId(Integer.toString(Constants.MODULE_VMS));
			}
			
			org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
			org.setCreatedDate(commonUtil.currentDate());
			org.setUpdatedBy(loginUserCode);
			org.setCreatedBy(loginUserCode);
			org.setUpdatedDate(commonUtil.currentDate());
			return org;

		}
		
		
		
		
		public void saveOrgBasedRoles(OrgLocations orgLocations,User user) throws Exception{
			List<Roles> rolesList = rolesService.getSourceleadRoles(1L);
			if(null != rolesList){
				for(Roles roles : rolesList){
					Roles orgRoles = new Roles();
					orgRoles.setRoleName(roles.getRoleName());
					orgRoles.setDisplayName(roles.getDisplayName());
					orgRoles.setOrgLocations(orgLocations);
					orgRoles.setOrg(orgLocations.getOrg());
					orgRoles.setActionId(roles.getActionId());
					orgRoles.setCreatedBy(user.getUserCode());
					orgRoles.setCreatedDate(new Date());
					rolesService.storeRoles(orgRoles);
				}
			}
			
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
			
			OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(clientLocationId,orgRelationId);
			String level = "level";
			if(null != orgRelation){
				
				 OrgLocations orgLocations = null;
					//if login user org id is equals to client location id then we get vendor list of ResponderLocationId Org details 
					 if(orgRelation.getLocationId().getId() == loginLocationId){ 
						 orgLocations = orgRelation.getResponderLocationId();
						 if(jsonObj.getString(level) != null && !jsonObj.getString(level).isEmpty()){
								orgRelation.setInitiatorLevel(jsonObj.getString(level));
							}
					 }
					 if(orgRelation.getResponderLocationId().getId() == loginLocationId){ 
						 orgLocations = orgRelation.getLocationId();
						 if(jsonObj.getString(level) != null && !jsonObj.getString(level).isEmpty()){
								orgRelation.setResponderLevel(jsonObj.getString(level));
							}
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
					 if(jsonObj.getString(level) != null && !jsonObj.getString(level).isEmpty()){
							orgRelation.setInitiatorLevel(jsonObj.getString(level));
						}
				 }
				 if(orgRelation.getResponderLocationId().getId()== loginOrgId){ 
					 orgLocations = orgRelation.getLocationId();
					 if(jsonObj.getString(level) != null && !jsonObj.getString(level).isEmpty()){
							orgRelation.setResponderLevel(jsonObj.getString(level));
						}
				 }
				//orgRelation.setLevel(jsonObj.getString(level));
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
		
		@Override
		public void updateVendorContactDetails(JSONObject jsonObj, Long spocId,Long loginOrgId, User loginUser ,Long ownerId, Long spocUserId,OrgLocations orgLocation) throws Exception {
			
			//OrgRelations orgRelation = orgRelationsService.getClientAndVendorOrgDeatilsById(vendorLocationId,orgRelationId);
			
			OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(spocId);
			
			String vendorContactFirstName = "vendorContactFirstName";
			String vendorContactLastName = "vendorContactLastName";
			String mobilePhone = "mobilePhone";
			String spocStatus = "spocStatus";
			String emailId = "email";
			String owner = "owner";
			
			if(null != orgSpoc){
				/*User user = orgSpoc.getSpocUser();*/
				User editUser =null;
				if(jsonObj.getString("existingSpocUserId") != null && !jsonObj.getString("existingSpocUserId").isEmpty()){
					User existingUser = userService.getUserById(Long.parseLong(jsonObj.getString("existingSpocUserId")));
					if(jsonObj.getString(vendorContactFirstName) != null && !jsonObj.getString(vendorContactFirstName).isEmpty()){
						existingUser.setFirstName(jsonObj.getString(vendorContactFirstName));
					}else{
						existingUser.setFirstName("");
					}
					if(jsonObj.getString(vendorContactLastName) != null && !jsonObj.getString(vendorContactLastName).isEmpty()){
						existingUser.setLastName(jsonObj.getString(vendorContactLastName));
					}
					else{
						existingUser.setLastName("");
					}
					if(jsonObj.getString(mobilePhone) != null && !jsonObj.getString(mobilePhone).isEmpty()){
						existingUser.setMobilePhone(jsonObj.getString(mobilePhone));
					}else{
						existingUser.setMobilePhone("");
					}
					if(jsonObj.getString(emailId) != null && !jsonObj.getString(emailId).isEmpty()){
						existingUser.setEmail(jsonObj.getString(emailId));
					}else{
						existingUser.setEmail("");
					}
					editUser = userService.addUser(existingUser);
				}
				
				if(jsonObj.getString(spocStatus) != null && !jsonObj.getString(spocStatus).isEmpty()){
					orgSpoc.setSpocStatus(jsonObj.getString(spocStatus));
				}
				
				/*if(orgSpoc.getSpocUser().getId() == loginUser.getId()){
					if(jsonObj.getString(owner) != null && !jsonObj.getString(owner).isEmpty()){
						User spocUser = new User();
						spocUser.setId(Long.parseLong(jsonObj.getString(owner)));
						orgSpoc.setSpocUser(spocUser);
					}
				}
				if(orgSpoc.getOwnerUser().getId() == loginUser.getId()){
					if(jsonObj.getString(owner) != null && !jsonObj.getString(owner).isEmpty()){
						User spocUser = new User();
						spocUser.setId(Long.parseLong(jsonObj.getString(owner)));
						orgSpoc.setOwnerUser(spocUser);
					}
				}*/
				User user = orgSpoc.getSpocUser();
				if(user.getId() == spocUserId){
					if(jsonObj.getString(owner) != null && !jsonObj.getString(owner).isEmpty()){
						User spocUser = new User();
						spocUser.setId(Long.parseLong(jsonObj.getString(owner)));
						orgSpoc.setSpocUser(spocUser);
					}
				}
				
				else if(user.getId() == ownerId){
					if(jsonObj.getString(owner) != null && !jsonObj.getString(owner).isEmpty()){
						User spocUser = new User();
						spocUser.setId(Long.parseLong(jsonObj.getString(owner)));
						orgSpoc.setOwnerUser(spocUser);
					}
				}
				else{
					if(jsonObj.getString(owner) != null && !jsonObj.getString(owner).isEmpty()){
						User spocUser = new User();
						spocUser.setId(Long.parseLong(jsonObj.getString(owner)));
						orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
						orgSpoc.setOwnerUser(spocUser);
					}
				}
				orgSpocService.addOrgSpoc(orgSpoc);
				
				if(orgSpoc.getTempOwnerId() != orgSpoc.getOwnerUser().getId()){
					user= userService.getUserById(Long.parseLong(jsonObj.getString(owner)));
					sendEmailToClientContact(user,editUser,orgLocation,loginUser);
				}
				
			}else{
				StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"ORG RELATIONS"));
				throw new SourceLeadObjectNotFoundException(message.toString());
			}

		}

		private void sendEmailToClientContact(User user, User editUser, OrgLocations orgLocation,User loginUser) throws Exception {
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(editUser.getFirstName());
			 emailMessage.setLastName(editUser.getLastName());
			 emailMessage.setToEmail(editUser.getEmail());
			 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
			 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
			 
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
				 globalVarsMap.put("clientFirstName", emailMessage.getFirstName());
				 globalVarsMap.put("clientLastName",emailMessage.getLastName());
				 globalVarsMap.put("loginUser", emailMessage.getFromName());
				 globalVarsMap.put("loginOrgName",orgLocation.getOrg().getOrgName());
				 globalVarsMap.put("ownerName", user.getFirstName());
				 globalVarsMap.put("ownerEmail",user.getEmail());
				 
				 //setting the Recipient information
				 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
				 EmailRecipient emailRecipient = new EmailRecipient();
				 emailRecipient.setEmail(emailMessage.getToEmail());
				 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
				 emailRecipientList.add(emailRecipient);
			
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = null;
				 emailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.EDIT_CLIENTCONTACT,Constants.ENTITY_TYPE_EMAIL);
			
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = "";
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
			 
			 EmailScenario noificationEmailScenario =  emailScenarioService.getEmailScenarioByUniqueName(Constants.EDIT_CLIENTCONTACT,Constants.ENTITY_TYPE_NOTIFICATION); 
			 String actionLink = "";
			 
			 String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(),globalVarsMap);
			 noificationEmailScenario.setBody(notificationbody);
			 
			 emailScenerioUtil.sendNotification(actionLink, loginUser, editUser, orgLocation, noificationEmailScenario);
			
		}
		
		
}
