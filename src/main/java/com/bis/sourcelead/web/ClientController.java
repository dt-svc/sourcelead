package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.Collection;
import java.util.Date;

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

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
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
	
	
	
	 @RequestMapping(value = "/client")
		public ModelAndView client() throws Exception {

			ModelAndView model = new ModelAndView();
			model.setViewName("client/client");
			return model;
	    }
	 
	 
	 @RequestMapping(value = "/clientDashboard")
		public ModelAndView clientDashboard() throws Exception {

			ModelAndView model = new ModelAndView();
			model.setViewName("client/clientDashboard");
			return model;
	    }
	 
	 
	 @RequestMapping(value = "/saveClient" , method = RequestMethod.POST)
	 public @ResponseBody  String saveClient(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		 	
		 	String resultJson = null;
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			String loginUserCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCode");
			String locationCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");

			
			
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			JSONObject jsonObj = new JSONObject(json);
			Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();   
		 	
		 	/*Saving for Org*/
			 Org org = new Org();
			 
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
			
			resultJson = toJSON(orgsList);
			 return resultJson;
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
