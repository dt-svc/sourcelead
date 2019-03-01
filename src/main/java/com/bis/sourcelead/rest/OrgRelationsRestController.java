package com.bis.sourcelead.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.FinanceConfigureEmailService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Organization service.
 * 
 * @author prasadK
 * @date 08th jul 2016
 *
 */

@RestController
@RequestMapping("/app")
public class OrgRelationsRestController {

	private static final Logger logger = LoggerFactory.getLogger(OrgRelationsRestController.class);
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private FinanceConfigureEmailService FinanceConfigureEmailService;
	
	/*------------Create a OrgRelations--------------------------------*/

	// TODO Don't delete until regression testing done @Rajashekar
	/**
	 * Handles request for storing OrgRelations into Database
	 * @return orgRelations
	 * @throws NullPointerException
	 */
	/*@PreAuthorize("hasAnyRole('ROLE_CREATE_CLIENT')")
	@RequestMapping(value = "/saveClientFromProject", method = RequestMethod.GET)
	public @ResponseBody  String saveClientFromProject(
			@RequestParam(value="json", required=false) String json ,
			@RequestParam(value = "responderType", required = false) String responderType,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			HttpServletRequest request) throws Exception
			 
	{
		String resultJson;
		
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		
		if(null != orgLocationId){
			jsonObj.put("orgLocationId", orgLocationId);
		}

		clientService.storeClient(jsonObj, user, orgLocation,responderType,org);
		
		Collection<OrgRelations> orgClientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId()); 
		for(OrgRelations ocl :orgClientList){
			ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
			ocl.setOrgid(ocl.getResponderLocationId().getOrg().getId());
		 }
		
		 resultJson = commonUtil.toJSON(orgClientList);
		 return resultJson;
	}*/
	
	@RequestMapping(value="/updatePaymentTermsInOrgrelations")
	public String updatePaymentTermsInOrgrelations() {
		int count = orgRelationsService.updatePaymentTermsInOrgrelations();
		return count + " Records updated";
	}
	
	
	
	 @RequestMapping(value = "/saveFinanceConfigureEmail", method = RequestMethod.POST)
	 @ResponseBody
	 public String saveFinanceConfigureEmail(EmailReminderDTO emailreminderConfigurationDTO,
			@RequestParam(value = "responderType", required = false) String responderType,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, HttpServletRequest request)
			throws Exception {
		  System.out.println("inside the rest controller class");

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		if (null != orgLocationId) {
			emailreminderConfigurationDTO.setOrgLocationId(orgLocationId);
		}

		FinanceConfigureEmailService.storeFinanceConfigureEmailService(emailreminderConfigurationDTO,org,orgLocation);
		SourceLeadSessionManager.setSessionAttribute(request, "clientCreateMsg", "Success!! New client created");
		return "emailreminderConfigurationDashboard";
	}
	
	
}
