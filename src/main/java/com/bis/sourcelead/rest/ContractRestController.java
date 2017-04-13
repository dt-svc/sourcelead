package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Handles contract related functionalities
 * @author root
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ContractRestController {
	
	@Autowired
	ContractsTemplateService contractsTemplateService;
	
	
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	public @ResponseBody String saveClient(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		contractsTemplateService.saveContractTemplate(jsonObj, user, orgLocation);
		
			return "clientDashboard";
	} 

	
}
