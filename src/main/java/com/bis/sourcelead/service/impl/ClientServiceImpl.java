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

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.exception.SourceLeadObjectNotFoundException;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
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
				org.setWebSite(jsonObj.getString("companyWebsite"));
			}
			if(jsonObj.has("webSite")){
				org.setWebSite(convertWebsite(jsonObj.getString("webSite")));
			}
			
			org.setOrgType("Guest");
			org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
			org.setCreatedDate(new Date());
			org.setUpdatedBy(loginUserCode);
			org.setCreatedBy(loginUserCode);
			org.setUpdatedDate(new Date());
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
					orgRelations.setClientLocationId(clientOrgLocation);
					orgRelations.setLocationId(orgLocations);
					orgRelations.setStatus("ACTIVE");
					orgRelations.setRelationType("Customer");
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
		public void updateClientDetails(JSONObject jsonObj, Long orgRelationId) throws Exception {
			
			OrgRelations orgRelation = orgRelationsService.getClientByOrgLocationId(orgRelationId);
			
			if(null != orgRelation){
				OrgLocations orgLocations = orgRelation.getClientLocationId();
						
				if(jsonObj.getString("clientCity") != null && !jsonObj.getString("clientCity").isEmpty()){
					orgLocations.setCity(jsonObj.getString("clientCity"));
				}
				if(jsonObj.getString("clientState") != null && !jsonObj.getString("clientState").isEmpty()){
					orgLocations.setState(jsonObj.getString("clientState"));
				}
				if(jsonObj.getString("clientCountry") != null && !jsonObj.getString("clientCountry").isEmpty()){
					orgLocations.setCountry(jsonObj.getString("clientCountry"));
			     }
				if(jsonObj.getString("EmailId") !=null && !jsonObj.getString("EmailId").isEmpty()){
					orgLocations.setEmail(jsonObj.getString("EmailId"));
				}
				if(jsonObj.getString("clientPhoneNumber") != null && !jsonObj.getString("clientPhoneNumber").isEmpty()){
					orgLocations.setPhone(jsonObj.getString("clientPhoneNumber"));
				}
				orgLocationService.updateOrgLocations(orgLocations);
			}else{
				StringBuilder message= new StringBuilder(MessageFormat.format(statusUpdate,"ORG RELATIONS"));
				throw new SourceLeadObjectNotFoundException(message.toString());
			}
			
		}
}
