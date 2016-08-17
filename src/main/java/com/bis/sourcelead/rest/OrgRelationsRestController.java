package com.bis.sourcelead.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
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
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Organization service.
 * 
 * @author prasadK
 * @date 08th jul 2016
 *
 */

@RestController
public class OrgRelationsRestController {

	private static final Logger logger = LoggerFactory.getLogger(OrgRelationsRestController.class);

	
	@Autowired
	private OrgService orgService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	

	@Autowired
	private CommonUtil commonUtil;
	
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
		String resultJson = null;
		json = "{"+json+"}";
		JSONObject jsonObj = new JSONObject(json);
		String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		String loginUserCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "userCode");
		OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		
		/*Client saving in create project from project tab*/
		
		Org org = papulateOrg(jsonObj,loginUserCode);
		Org addOrg = orgService.addOrganization(org);
		OrgLocations  orgLocations = papulateOrgLocations(addOrg,loginOrgLocations,loginUserCode,loginOrgLocation);
		orgLocationService.addOrgLocations(orgLocations);
		OrgRelations orgRelations = papulateOrgRelations(orgLocations,loginOrgLocations,loginUserCode);
		orgRelationsService.addOrgRelations(orgRelations);
		
		Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
		for(OrgRelations ocl :orgClientList){
			ocl.setOrgName(ocl.getClientLocationId().getOrg().getOrgName());
			ocl.setOrgid(ocl.getClientLocationId().getOrg().getId());
		 }
		
		 resultJson = commonUtil.toJSON(orgClientList);
		 return resultJson;
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
	
	private Org papulateOrg(JSONObject jsonObj, String loginUserCode) throws JSONException {
		Org org = new Org();
		org.setOrgName(jsonObj.getString("orgName"));
		org.setWebSite(convertWebsite(jsonObj.getString("companyWebsite")));
		org.setOrgType("Guest");
		org.setOrgCode(SourceLeadRandomCodeHelper.generateRandomOrgCode());
		org.setStatus(Status.ACTIVE);
		org.setCreatedDate(new Date());
		org.setCreatedBy(loginUserCode);
		return org;
		
	}
	
	private OrgLocations papulateOrgLocations(Org addOrg, OrgLocations loginOrgLocations, String loginUserCode, String loginOrgLocation) {
		OrgLocations  orgLocations = new OrgLocations();
		orgLocations.setOrg(addOrg);
		orgLocations.setCountry(loginOrgLocations.getCountry());
		String clientCountry = loginOrgLocation.substring(Math.max(loginOrgLocation.length() - 2, 0));
		orgLocations.setLocationCode(addOrg.getOrgCode()+clientCountry);
		orgLocations.setStatus(Status.ACTIVE);
		orgLocations.setCreatedDate(new Date());
		orgLocations.setCreatedBy(loginUserCode);
		return orgLocations;
		
	}
	

	private OrgRelations papulateOrgRelations(OrgLocations orgLocations, OrgLocations loginOrgLocations, String loginUserCode) {
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setRelationType("Customer");
		orgRelations.setClientLocationId(orgLocations);
		orgRelations.setLocationId(loginOrgLocations);
		orgRelations.setStatus("ACTIVE");
		orgRelations.setCreatedBy(loginUserCode);
		orgRelations.setCreatedDate(new Date());
		return orgRelations;
	}
}
