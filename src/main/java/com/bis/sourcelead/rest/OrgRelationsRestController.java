package com.bis.sourcelead.rest;

import java.util.Collection;

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

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.AutoCompleteController;
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
	private OrgRelationsService orgRelationsService;
	

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ClientService clientService;
	
	/*------------Create a OrgRelations--------------------------------*/
	/**
	 * Handles request for storing OrgRelations into Database
	 * @return orgRelations
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/saveClientFromProject", method = RequestMethod.GET)
	public @ResponseBody  String saveClientFromProject(
			@RequestParam(value="json", required=false) String json ,HttpServletRequest request) throws Exception
			 
	{
		String resultJson;
		
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		clientService.storeClient(jsonObj, user, orgLocation,"");
		
		Collection<OrgRelations> orgClientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId()); 
		for(OrgRelations ocl :orgClientList){
			ocl.setOrgName(ocl.getResponderLocationId().getOrg().getOrgName());
			ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
		 }
		
		 resultJson = commonUtil.toJSON(orgClientList);
		 return resultJson;
	}
	
	
	
	
	
	
}
