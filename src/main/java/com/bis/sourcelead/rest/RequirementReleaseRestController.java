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

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
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
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Requirement parentRequirement = requirementService.getRequirementByRequirementId(requirementId);
			String[] contacts = contactsIds.split(",");
			if(contacts != null &&  contacts.length>0 && contacts[0] != null && contacts[0] != "" ){
				for(String contact   : contacts){
						OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(Long.parseLong(contact));
						Requirement requirement  = new Requirement();
						releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc);
				}
			}	
			
			String[] vendors = vendorIds.split(",");
			if(vendors != null &&  vendors.length>0 && vendors[0] != null && vendors[0] != ""){
				for(String vendor   : vendors){
					
					List<OrgSpoc> orgSpocList = orgSpocService.getAllContactsbyOrgId(Long.parseLong(vendor));
					for(OrgSpoc orgSpoc : orgSpocList){
						Requirement requirement  = new Requirement();
						releaseJobToVendorOrContacts(requirement, parentRequirement, orgSpoc);
					}
				}
			}
			
			return "vendorsList";
	}

	private void releaseJobToVendorOrContacts(Requirement requirement, Requirement parentRequirement, OrgSpoc orgSpoc) throws Exception {
		
		//requirement = parentRequirement;
		
		requirement.setParent_requirement_id(parentRequirement.getId());
		requirement.setOrgLocationId(orgSpoc.getResponderLocationId());
		requirement.setJobTitle(parentRequirement.getJobTitle());
		requirement.setJobType(parentRequirement.getJobType());
		requirement.setJobId(parentRequirement.getJobId());
		requirement.setPositions(parentRequirement.getPositions());
		requirement.setEducation(parentRequirement.getEducation());
		requirement.setMinExp(parentRequirement.getMinExp());
		requirement.setMaxExp(parentRequirement.getMaxExp());
		requirement.setCurrencyCode(parentRequirement.getCurrencyCode());
		requirement.setRate(parentRequirement.getRate());
		requirement.setRateType(parentRequirement.getRateType());
		requirement.setCountry(parentRequirement.getCountry());
		requirement.setState(parentRequirement.getState());
		requirement.setCity(parentRequirement.getCity());
		requirement.setSkills(parentRequirement.getSkills());
		requirement.setDescription(parentRequirement.getDescription());
		requirement.setDocFile(parentRequirement.getDocFile());
		requirement.setFileContentType(parentRequirement.getFileContentType());
		requirement.setFileName(parentRequirement.getFileName());
		requirement.setIsClientJob("Y");
		
		requirement.setSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		requirement.setClientSubmissionTemplateJsonString(parentRequirement.getSubmissionTemplateJsonString());
		
		requirement.setStatus(Constants.TEAM_ACTIVE);
		requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
		
		if(orgSpoc != null){
			requirement.setSpocId(orgSpoc);
			requirement.setClientId(orgSpoc.getOrgRelations());
			requirement.setUpdatedBy(orgSpoc.getSpocUser().getUserCode());
			requirement.setCreatedBy(orgSpoc.getSpocUser().getUserCode());
		}
		
		requirement.setCreatedDate(commonUtil.currentDate());
		requirement.setUpdatedDate(commonUtil.currentDate());
		
		requirement.setId(null);
		requirementService.addRequirement(requirement);
	}
}