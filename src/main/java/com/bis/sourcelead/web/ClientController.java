package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
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
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

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
	
	
	
	 @RequestMapping(value = "/client")
		public ModelAndView client() throws Exception {

			ModelAndView model = new ModelAndView();
			 Collection<User> userList = userService.getUsersByUserRoles();
			 model.addObject("user_List", userList);
			model.setViewName("client/client");
			return model;
	    }
	 
	 
	 @RequestMapping(value = "/clientDashboard")
		public ModelAndView clientDashboard(HttpServletRequest request) throws Exception {
		   
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    ModelAndView model = new ModelAndView();
			model.setViewName("client/clientDashboard");
			Collection<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId());
			model.addObject("clientsList", clientsList);
			
			return model;
	    }
	 
	 
	 
	 
	 
	 @RequestMapping(value = "/saveClient" , method = RequestMethod.POST)
	 public @ResponseBody  String saveClient(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		 	
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String loginUserCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCode");
			String locationCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");

			
			
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			JSONObject jsonObj = new JSONObject(json);
			Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();   
		 	
		 	/*Saving for Org*/
		    Org org = new Org();
			org.setOrgName(jsonObj.getString("orgName"));
		    org.setWebSite(jsonObj.getString("webSite"));
			org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
			org.setCreatedDate(new Date());
			org.setUpdatedBy(loginUserCode);
			Org addOrg = orgService.addOrganization(org);
			
			/*Saving for OrgLocations*/
			OrgLocations  orgLocations = new OrgLocations();
			orgLocations.setOrg(addOrg);
			
			orgLocations.setCountry(loginOrgLocations.getCountry());
			orgLocations.setState(loginOrgLocations.getState());
			orgLocations.setCity(loginOrgLocations.getCity());
			String clientCountry = locationCode.substring(Math.max(locationCode.length() - 2, 0));
			orgLocations.setLocationCode(org.getOrgCode()+clientCountry);
			orgLocations.setCreatedDate(new Date());
			orgLocations.setCreatedBy(loginUserCode);
			OrgLocations addOrgLocations=orgLocationService.addOrgLocations(orgLocations);
			
			/*Saving for OrgRelations*/
			
			OrgRelations orgRelations = new OrgRelations();
	//		orgRelations.setRelationType("Customer");
			orgRelations.setClientLocationId(orgLocations);
			orgRelations.setLocationId(loginOrgLocations);
			orgRelations.setStatus("ACTIVE");
			orgRelations.setCreatedBy(loginUserCode);
			orgRelations.setCreatedDate(new Date());
			OrgRelations addRelations = orgRelationsService.addOrgRelations(orgRelations);
			
			Collection<Org> orgsList = orgService.getOrgs();
			
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			logger.info("The projects added by the admin are :"+maxCount);
			for (int c = 1; c < maxCount; c++) {
				Projects project = new Projects();
				ProjectApproverHierarchy projectApproverHierarchy = new ProjectApproverHierarchy();
				Set<ProjectApproverHierarchy> projectApproverSet = new HashSet<ProjectApproverHierarchy>();
				project.setProjectSequeneNumber(jsonObj.getString(c+"projectId"));
				project.setProjectName(jsonObj.getString(c+"projectName"));
				String startDateString = jsonObj.getString(c+"projectStartDate");
				String i = jsonObj.getString(c+"projectManager");
			    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
			    Date startDate = null;
			    try {
			        startDate = df.parse(startDateString);
			        String newDateString = df.format(startDate);
			        System.out.println(newDateString);
			    } catch (ParseException e) {
			        e.printStackTrace();
			    }
				project.setStartDate(startDate);
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
//					ProjectApproverHierarchy ProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
				}
				projectApproverSet.add(projectApproverHierarchy);
				
				project.setProjectApprovers(projectApproverSet);
				
				projectsService.saveProject(project);
				
			}

			
			 return "clientDashboard";
	   }
	
	
		
		private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
		

	
}
