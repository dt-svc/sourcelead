package com.bis.sourcelead.web;

import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
/**
 * Handles requests for the Client service.
 * 
 * @author PrasadK
 * @date 13th June 2016
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	/**
	 * Handles to display client jsp
	 * @return ModelAndView
	 * @throws Exception
	 */
	 @RequestMapping(value = "/client")
		public ModelAndView client(HttpServletRequest request) throws Exception {
			ModelAndView model = new ModelAndView();
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Clients);
			
			request.getSession().setAttribute("sessionData",sessionData);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 Collection<User> userList = userService.getUsersByUserRolesAndOrgLocationId(orgLocations.getId());
				model.addObject("user_List", userList);
			model.setViewName("client/client");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
	 
	 /**
	  * Handles to display clientdash board
	  * @param request
	  * @return ModelAndView
	  * @throws Exception
	  */
	 @RequestMapping(value = "/clientDashboard")
		public ModelAndView clientDashboard(HttpServletRequest request) throws Exception {
		    User user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    ModelAndView model = new ModelAndView();
			model.setViewName("client/clientDashboard");
			
	        
			
			Collection<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
			for(OrgRelations orgRelation : clientsList){
				Long count =orgRelationsService.getProjectsCountByOrgRelationIdAndOrgLocationId(orgRelation.getId(), orgLocations.getId());
				orgRelation.setProjectsCont(count);
			}
			model.addObject("clientsList", clientsList);
		
			SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.Clients);
			request.getSession().setAttribute("sessionData",sessionData);
			
			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "clientCreateMsg");
			if(!isNullOrEmpty(msg)){
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "clientCreateMsg");
			}
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	    }
	 
	 
	 
	 
	 /**
	  * Handles client as well as project save
	  * @param json
	  * @param request
	  * @return ResponseBody
	  * @throws Exception
	  */
	 @RequestMapping(value = "/saveClient" , method = RequestMethod.POST)
	 public @ResponseBody  String saveClient(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		 	
		    json = "{"+json+"}";
			JSONObject jsonObj = new JSONObject(json);
			Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();   

			String loginUserCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCode");
			String locationCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			
		 	/*Saving for Org*/
		    Org org = new Org();
			org.setOrgName(jsonObj.getString("orgName"));
			org.setWebSite(convertWebsite(jsonObj.getString("webSite")));
		   // org.setWebSite(jsonObj.getString("webSite"));convertWebsite
			org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
			org.setCreatedDate(new Date());
			org.setUpdatedBy(loginUserCode);
			Org addOrg = orgService.addOrganization(org);
			
			/*Saving for OrgLocations*/
			OrgLocations  orgLocations = new OrgLocations();
			orgLocations.setOrg(addOrg);
			orgLocations.setPhone(jsonObj.getString("phone"));
			orgLocations.setEmail(jsonObj.getString("email"));
			orgLocations.setCountry(jsonObj.getString("country"));
			orgLocations.setState(jsonObj.getString("state"));
			orgLocations.setCity(jsonObj.getString("city"));
			String clientCountry = locationCode.substring(Math.max(locationCode.length() - 2, 0));
			orgLocations.setLocationCode(org.getOrgCode()+clientCountry);
			orgLocations.setCreatedDate(new Date());
			orgLocations.setCreatedBy(loginUserCode);
			orgLocationService.addOrgLocations(orgLocations);
			/*Saving for OrgRelations*/
			OrgRelations orgRelations = new OrgRelations();
			orgRelations.setClientLocationId(orgLocations);
			orgRelations.setLocationId(loginOrgLocations);
			orgRelations.setStatus("ACTIVE");
			orgRelations.setCreatedBy(loginUserCode);
			orgRelations.setCreatedDate(new Date());
		   orgRelationsService.addOrgRelations(orgRelations);
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			logger.info("The projects added by the admin are :"+maxCount);
			for (int c = 1; c < maxCount; c++) {
				Projects project = new Projects();
				ProjectApproverHierarchy projectApproverHierarchy = new ProjectApproverHierarchy();
				Set<ProjectApproverHierarchy> projectApproverSet = new HashSet<ProjectApproverHierarchy>();
				project.setProjectSequeneNumber(jsonObj.getString(c+"projectId"));
				project.setProjectName(jsonObj.getString(c+"projectName"));
			
                OrgLocations  orgLocationsID = new OrgLocations();
				orgLocationsID.setId(orgLocation.getId());//orgRelationId
				project.setOrgLocationsId(orgLocationsID);
				project.setOrgRelationId(orgRelations); // newly Added
				
				String startDateString = jsonObj.getString(c+"projectStartDate");
				String endDateString = jsonObj.getString(c+"projectEndDate");//projectEndDate
			    Date startDate = getDateFormat(startDateString); 
			    Date endDate = getDateFormat(endDateString);
				project.setStartDate(startDate);
				project.setEndDate(endDate);
				project.setProjectStatus(Project_Status.STARTED);
				project.setCreatedDate(new Date());
				projectApproverHierarchy.setProjectId(project);
				User approverUserId1 = new User();
		    	approverUserId1.setId(Long.parseLong(jsonObj.getString(c+"projectManager")));
				projectApproverHierarchy.setApproverUserId(approverUserId1);
				projectApproverHierarchy.setLevel(1);
				ProjectApproverHierarchy projectApproverHierarchy1 = new ProjectApproverHierarchy();
				if(!jsonObj.getString("level2Manager").isEmpty()){
					projectApproverHierarchy1.setProjectId(project);
					User approverUserId2 = new User();
					approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
					projectApproverHierarchy1.setApproverUserId(approverUserId2);
					projectApproverHierarchy1.setLevel(2);
					projectApproverSet.add(projectApproverHierarchy1);
				}
				projectApproverSet.add(projectApproverHierarchy);
				
				project.setProjectApprovers(projectApproverSet);
				/*Saving for Projects*/
				projectsService.saveProject(project);
				
				 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			     commonUtil.storeSourceleadTasks(user, project);
			}
			
			 SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg", "Successfully you have created a client");
			 
			 return "clientDashboard";
	   }
	

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
		 * validate duplicate projects by project sequence number
		 * @param ProjectSequeneNumber
		 * @param request
		 * @return boolean
		 * @throws Exception
		 */
		 @RequestMapping(value = "/validateProjectSequeneNumberId" , method = RequestMethod.POST)
		 public @ResponseBody  Boolean validateProjectSequeneNumberById(@RequestParam(value="ProjectSequeneNumber", required=false) String ProjectSequeneNumber, HttpServletRequest request) throws Exception {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	Boolean flag = projectsService.validateProjectSequeneNumber(ProjectSequeneNumber,orgLocations.getId());
			 	
				return flag;
		   }
		
		 
		 
		 /**
		  * Handles client Details by OrgRelationId
		  * @param orgLocationId
		  * @param request
		  * @return JSON
		  * @throws Exception
		  */
		 
		 @RequestMapping(value = "/getClientDeatilsById", method = RequestMethod.POST)
		 public @ResponseBody String getProjectById(@RequestParam(value="clientId", required=false) String orgLocationId,HttpServletRequest request) throws Exception{ 
		String resultJson = "";
		OrgRelations orgRelation = orgRelationsService.getClientByOrgLocationId(Long.parseLong(orgLocationId));
		orgRelation.setOrgName(orgRelation.getClientLocationId().getOrg().getOrgName());
		orgRelation.setWebSite(orgRelation.getClientLocationId().getOrg().getWebSite());
		orgRelation.setCountry(orgRelation.getClientLocationId().getCountry());
		orgRelation.setCity(orgRelation.getClientLocationId().getCity());
		orgRelation.setState(orgRelation.getClientLocationId().getState());
		orgRelation.setPhone(orgRelation.getClientLocationId().getPhone());
		orgRelation.setEmail(orgRelation.getClientLocationId().getEmail());
		logger.info("Client Name : " + orgRelation.getClientLocationId().getOrg().getId());
		Long projectCount = orgRelationsService.getNumberOfProjectsPerClient(orgRelation.getId());
		logger.info("Total number of projects for client " + orgRelation.getClientLocationId().getOrg().getOrgName()
				+ "is : " + projectCount);
		orgRelation.setProjectsCont(projectCount);
		resultJson = toJSON(orgRelation);
		return resultJson;
		  }
		 
		 /**
		  * Read total number of projects 
		  * @param orgRelationId
		  * @param locationId
		  * @param request
		  * @return json
		  * @throws Exception
		  */
		 @RequestMapping(value = "/getAllClientProjectsByOrgRelationId", method = RequestMethod.POST)
		 public @ResponseBody String getAllClientProjectsByOrgRelationId(@RequestParam(value="orgRelationId", required=false) Long orgRelationId, @RequestParam(value="locationId", required=false) Long locationId,HttpServletRequest request) throws Exception{ 
			 
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 Collection<Projects> clientProjectsList = projectsService.getAllClientProjectsByOrgRelationId(orgRelationId,orgLocations.getId());
			 ArrayList<Projects> clientProjectsArrList = new ArrayList<Projects>();
			 
			 for(Projects project : clientProjectsList){
				 project.setProjectName(project.getProjectName());
				 
				 if(project.getStartDate() !=null && !isNullOrEmpty(project.getStartDate().toString())){
					 project.setStartDateStr(Constants.DATEFORMAT.format(project.getStartDate()));
				 }
				 if(project.getEndDate() !=null && !isNullOrEmpty(project.getEndDate().toString())){
					 project.setEndDateStr(Constants.DATEFORMAT.format(project.getEndDate()));
				 }
				 
				 if(project.getOrgRelationId().getId() != null){
					 Integer assignedEmpSize=projectMembersService.getEmployeeAssignedProjectsCount(project.getOrgRelationId().getId() , orgLocations.getId());
				     project.setAssignedEmpCount(assignedEmpSize);
				 }
				 if(project.getProjectStatus() !=null){
					 project.setProjectStatus(project.getProjectStatus());
				 }
				 
				 
				 
				 /*
				  * Add managers to project list(shows in select box) in client tab
				  */
				 Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(project.getId());
				    String[] approvers = new String[projectApproverHierarchies.size()];
				    int i = 0;
				    for(ProjectApproverHierarchy php : projectApproverHierarchies){
				    	approvers[i] = php.getApproverUserId().getFirstName()+"&"+php.getApproverUserId().getId();
				    	i++;
				    	if(php.getLevel()==1){
				    		project.setRelationId(php.getApproverUserId().getId());
				    	}
				    }
				    project.setProjectManagerName(approvers);

			 }
			 ObjectMapper mapper = new ObjectMapper();
			 String jsonInString = mapper.writeValueAsString(clientProjectsList);
			 
			 return jsonInString;
		    }
		
		
		private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
		
		protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
		
		
		/**
		 * Handles project inline editing
		 * @param orgRelationId
		 * @param fieldName
		 * @param fieldValue
		 * @param request
		 * @return json
		 * @throws Exception
		 */
		@RequestMapping(value = "/updateClientProjectById", method = RequestMethod.POST)
		 public String updateClientProjectById(@RequestParam(value="orgRelationId", required=false) Long orgRelationId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue,HttpServletRequest request) throws Exception{ 
		OrgRelations orgRelation = orgRelationsService.getClientByOrgLocationId(orgRelationId);
		Org org = orgRelation.getClientLocationId().getOrg();
		if ("clientName".equalsIgnoreCase(fieldName)) {
			logger.info("Client name before update is :" + orgRelation.getClientLocationId().getOrg().getOrgName());
			org.setOrgName(fieldValue);
			Org updatedOrg = orgService.updateOrg(org);
			logger.info("Client name After update is :" + updatedOrg.getOrgName());
		} else if ("clientWebsite".equalsIgnoreCase(fieldName)) {
			logger.info("Client website before update is :" + orgRelation.getClientLocationId().getOrg().getWebSite());
			org.setWebSite(fieldValue);
			Org updatedWebsite = orgService.updateOrg(org);
			logger.info("Client name After update is :" + updatedWebsite.getWebSite());
		}else if("clientCity".equalsIgnoreCase(fieldName)){
			logger.info("Client city before update is :" +orgRelation.getClientLocationId().getCity());
		     OrgLocations orgLocations = orgRelation.getClientLocationId();
		     orgLocations.setCity(fieldValue);
		     OrgLocations updatedCity =  orgLocationService.updateOrgLocations(orgLocations);
		     logger.info("Client city After update is :" +updatedCity.getCity());
		}else if("clientSate".equalsIgnoreCase(fieldName)){
			logger.info("Client Stae before update is :" +orgRelation.getClientLocationId().getState());
		     OrgLocations orgLocations = orgRelation.getClientLocationId();
		     orgLocations.setState(fieldValue);
		     OrgLocations updatedState=  orgLocationService.updateOrgLocations(orgLocations);
		     logger.info("Client location After update is :" +updatedState.getState());
		}else if("clientCountry".equalsIgnoreCase(fieldName)){
			 logger.info("Client country before update is :" +orgRelation.getClientLocationId().getCountry());
		     OrgLocations orgLocations = orgRelation.getClientLocationId();
		     orgLocations.setCountry(fieldValue);
		     OrgLocations updatedCity =  orgLocationService.updateOrgLocations(orgLocations);
		     logger.info("Client country After update is :" +updatedCity.getCountry());
		}else if("clientEmail".equalsIgnoreCase(fieldName)){ 
			 logger.info("Client Email before update is :" +orgRelation.getClientLocationId().getEmail());
		     OrgLocations orgLocations = orgRelation.getClientLocationId();
		     orgLocations.setEmail(fieldValue);
		     OrgLocations updatedEmail =  orgLocationService.updateOrgLocations(orgLocations);
		     logger.info("Client Email After update is :" +updatedEmail.getEmail());
		}else{
			 logger.info("Client Ponenumber before update is :" +orgRelation.getClientLocationId().getPhone());
		     OrgLocations orgLocations = orgRelation.getClientLocationId();
		     orgLocations.setPhone(fieldValue);
		     OrgLocations updatedPhone =  orgLocationService.updateOrgLocations(orgLocations);
		     logger.info("Client Ponenumber After update is :" +updatedPhone.getEmail());
		}

		return "success";
		  }
		 
/**
 * Handles Inline edit for client projects
 * @param projectId
 * @param fieldName
 * @param fieldValue
 * @param request
 * @return Json
 * @throws Exception
 */
		@RequestMapping(value = "/inlineEditClientProjects", method = RequestMethod.POST)
		 public String inlineEditClientProjects(@RequestParam(value="projectId", required=false) Long projectId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue,HttpServletRequest request) throws Exception{
			
			Projects projects = projectsService.getProjectById(projectId);
			Set<ProjectApproverHierarchy> pahSet = new HashSet<ProjectApproverHierarchy>();
			logger.info("Project name before Edit "+projects.getProjectName());
			if("projectName".equalsIgnoreCase(fieldName)){
				projects.setProjectName(fieldValue);
			}else if("endDate".equalsIgnoreCase(fieldName)){
				if(fieldValue != null && !"".equalsIgnoreCase(fieldValue)){
				Date endDate = getDateFormat(fieldValue);
				projects.setEndDate(endDate);
				}
			}else if("clientId".equalsIgnoreCase(fieldName)){
				Long l1 = Long.parseLong(fieldValue);
				Collection<ProjectApproverHierarchy> pah = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
				for(ProjectApproverHierarchy p1 :pah){
					if(l1 == p1.getApproverUserId().getId()){
						if(p1.getLevel() == 1){
							  p1.setLevel(2);
						  }else{
							  p1.setLevel(1);
						  }
					}else{
						if(p1.getLevel() == 1){
							p1.setLevel(2);
						}else{
							 p1.setLevel(1);
						}
					}
					pahSet.add(p1);
				}
		    	projects.setProjectApprovers(pahSet);
			}else{
				projects.setProjectStatus(Project_Status.getNameByValue(fieldValue));
			}
			projectsService.updateProject(projects);
			return fieldValue; 
		
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
		 * Handles ClientName duplicate or not
		 * @param clientName
		 * @return
		 * @throws Exception 
		 */
		 @RequestMapping(value = "/ws/isClientNameValid", method = RequestMethod.GET)
		   public @ResponseBody boolean isClientNameValidated(@RequestParam(value="clientName", required=false) String clientName) throws Exception {

			 return  orgService.isClientNameValid(clientName);
		   }
		 
		
}