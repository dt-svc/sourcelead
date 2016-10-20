package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
/**
 * 
 * @author Rajashekar
 * Handers all Vendor actions
 *
 */

@RestController
@PropertySource("${propertyLocation:app.properties}")
public class RequirementReleaseRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	/**
	 * Handles Vendor Contacts 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/releaseJobsToClientsOrVendors", method = RequestMethod.POST)
	@ResponseBody
	public String releaseJobsToClientsOrVendors(@RequestParam(value = "requirementId", required = false) Long requirementId,
												@RequestParam(value = "contactsIds", required = false) String contactsIds,
			                                    @RequestParam(value="vendorIds", required=false) String vendorIds,
			                                    HttpServletRequest request) throws Exception {
			
		
			Requirement parentRequirement = requirementService.getRequirementByRequirementId(requirementId);
			String[] contacts = contactsIds.split(",");
			if(contacts != null &&  contacts.length>0 && contacts[0] != null && contacts[0] != "" ){
				for(String contact   : contacts){
						
						Requirement requirement  = new Requirement();
						requirement.setParent_requirement_id(parentRequirement.getId());
						
						OrgSpoc orgSpoc = new OrgSpoc();
						orgSpoc.setId(Long.parseLong(contact));
						requirement.setSpocId(orgSpoc);
						
						requirement.setId(null);
						
						requirementService.addRequirement(requirement);
				}
			}	
			
			String[] vendors = vendorIds.split(",");
			if(vendors != null &&  vendors.length>0 && vendors[0] != null && vendors[0] != ""){
				for(String vendor   : vendors){
					
					List<OrgSpoc> orgSpocList = orgSpocService.getAllContactsbyOrgId(Long.parseLong(vendor));
					for(OrgSpoc orgSpoc : orgSpocList){
						Requirement requirement  = new Requirement();
						requirement.setParent_requirement_id(parentRequirement.getId());
						
						requirement.setSpocId(orgSpoc);
						requirement.setId(null);
						
						requirementService.addRequirement(requirement);
					}
				}
			}
			
			
			return "vendorsList";
	}

	   
}