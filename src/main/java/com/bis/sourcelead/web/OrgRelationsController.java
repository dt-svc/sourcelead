package com.bis.sourcelead.web;

import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
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

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CountriesService;
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
 * Handles requests for the Organization service.
 * 
 * @author prasadK
 * @date 08th jul 2016
 *
 */

@RestController
public class OrgRelationsController {

	private static final Logger logger = LoggerFactory.getLogger(OrgRelationsController.class);

	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private CountriesService countriesService;

	
	
	/*------------Create a OrgRelations--------------------------------*/
	/**
	 * Handles request for storing OrgRelations into Database
	 * @return orgRelations
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/saveOrgRelations", method = RequestMethod.GET)
	public @ResponseBody  String addModelDialogTasks(
			@RequestParam(value="json", required=false) String json ,HttpServletRequest request) throws Exception
			 
	{
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		String loginUserCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCode");
       
		OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		
		String resultJson = null;
		
		/*Saving for Org*/
		Org org = new Org();
		org.setOrgName(jsonObj.getString("orgName"));
		org.setWebSite(convertWebsite(jsonObj.getString("companyWebsite")));
		org.setOrgType("Guest");
		org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		org.setStatus(Status.ACTIVE);
		org.setCreatedDate(new Date());
		org.setCreatedBy(loginUserCode);
		Org addOrg = orgService.addOrganization(org);
		
		/*Saving for OrgLocations*/
		OrgLocations  orgLocations = new OrgLocations();
		orgLocations.setOrg(addOrg);
		orgLocations.setCountry(loginOrgLocations.getCountry());
		String clientCountry = loginOrgLocation.substring(Math.max(loginOrgLocation.length() - 2, 0));
		orgLocations.setLocationCode(org.getOrgCode()+clientCountry);
		orgLocations.setStatus(Status.ACTIVE);
		orgLocations.setCreatedDate(new Date());
		orgLocations.setCreatedBy(loginUserCode);
		OrgLocations addOrgLocations=orgLocationService.addOrgLocations(orgLocations);
		
		/*Saving for OrgRelations*/
		
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setRelationType("Customer");
		orgRelations.setClientLocationId(orgLocations);
		orgRelations.setLocationId(loginOrgLocations);
		orgRelations.setStatus("ACTIVE");
		orgRelations.setCreatedBy(loginUserCode);
		orgRelations.setCreatedDate(new Date());
		OrgRelations addRelations = orgRelationsService.addOrgRelations(orgRelations);
		
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
		for(OrgRelations ocl :orgClientList){
			ocl.setOrgName(ocl.getClientLocationId().getOrg().getOrgName());
			ocl.setOrgid(ocl.getClientLocationId().getOrg().getId());
		 }
		
		resultJson = toJSON(orgClientList);
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
	/*
	@RequestMapping(value = "/saveTimesheet", method = RequestMethod.GET )
	public @ResponseBody String saveTimesheet(@RequestParam(value="json", required=false) String json
											 ) throws Exception {
		String result = "";
		String resultJson = null;
		json=json.replaceAll("\\[", "\\'");
		json=json.replaceAll("\\]", "\\'");
		JSONObject jsonObj = new JSONObject(json);
    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	
    	String proId = jsonObj.getString("projectId");
    	String[] words = proId.split(",");
    	Integer[] ids ;
    	for(String w:words){  
    		id
    	}  
    	
    	String[] projectIds =   jsonObj.getNames("projectId");
    	
    	//Collection<TimesheetEntries> timesheetEntriesList = timesheetEntries(jsonObj,projectId);
    	String[] projectIds = new String[2];
    	projectIds[0] = "1";
    	projectIds[1] = "2";

    	for(String projectId : projectIds){
    		
		   
	   	TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
	   	timesheetSubmissions.setStatus("agreed");
	   	timesheetSubmissions.setSubmissionStatus("not agreed");
	   	ProjectMembers pm = new ProjectMembers();
	   	pm.setId((long) 1);
	   	timesheetSubmissions.setProjectMembers(pm);
	   	timesheetSubmissions.setStartDate(new Date());
	   	timesheetSubmissions.setEndDate(new Date());
	   	timesheetSubmissions.setUpdatedDate(new Date());
	   	timesheetSubmissions.setCreatedDate(new Date());
	   	timesheetSubmissionsDao.addTimesheetSubmissions(timesheetSubmissions);
			   
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxRowNumber"+projectId);
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			int rowNum = 0;
			
			for (int c = 1; c < maxCount; c++) {
				if (isNullOrEmpty(jsonObj.getString(c+"project"+projectId+"timeSheetDay1Id")))
					continue;
				
				TimesheetEntries timesheetEntries = new TimesheetEntries();
				timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
				timesheetEntries.setStartDate(new Date());
				timesheetEntries.setEndDate(new Date());
				timesheetEntries.setCreatedDate(new Date());
				timesheetEntries.setUpdatedDate(new Date());
				
			   timesheetEntries.setMonHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay1Id"));
				timesheetEntries.setMoncomments(jsonObj.getString(c+"project"+projectId+"comment1Id"));
				Tasks monTask = new Tasks();
				monTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setMonTask(monTask);
				
				
				timesheetEntries.setTueHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay2Id"));
				timesheetEntries.setTuecomments(jsonObj.getString(c+"project"+projectId+"comment2Id"));
				Tasks tueTask = new Tasks();
				tueTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setTueTask(tueTask);
				
				

				timesheetEntries.setWedHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay3Id"));
				timesheetEntries.setWedcomments(jsonObj.getString(c+"project"+projectId+"comment3Id"));
				Tasks wedTask = new Tasks();
				wedTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setWedTask(wedTask);
				 
				timesheetEntries.setThuHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay4Id"));
				timesheetEntries.setThucomments(jsonObj.getString(c+"project"+projectId+"comment4Id"));
				Tasks thuTask = new Tasks();
				thuTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setThuTask(thuTask);
				 
				 
				timesheetEntries.setFriHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay4Id"));
				timesheetEntries.setFricomments(jsonObj.getString(c+"project"+projectId+"comment4Id"));
				Tasks friTask = new Tasks();
				friTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setFriTask(friTask);
				 
				 
				 
				timesheetEntries.setSatHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay4Id"));
				timesheetEntries.setSatcomments(jsonObj.getString(c+"project"+projectId+"comment4Id"));
				Tasks satTask = new Tasks();
				satTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setSatTask(satTask);
				 
				 
				timesheetEntries.setSunHours(jsonObj.getString(c+"project"+projectId+"timeSheetDay4Id"));
				timesheetEntries.setSuncomments(jsonObj.getString(c+"project"+projectId+"comment4Id"));
				Tasks sunTask = new Tasks();
				sunTask.setId(Long.parseLong(jsonObj.getString(c+"project"+projectId+"task1Id")));
				timesheetEntries.setSunTask(sunTask);
				 
				
				timesheetEntries.setFricomments(jsonObj.getString(c+"project"+projectId+"timeSheetDay1Id"));
				rowNum++;
				
				timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
				
			}
    	}
    	
		result =  "success";
		resultJson = toJSON(result);
		return resultJson;
	}
	*/
	
}
