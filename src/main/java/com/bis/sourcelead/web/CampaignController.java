package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.ContactsGroupService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class CampaignController {
	

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
    
    @Autowired
    private RequirementService requirementService;
    
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	UserMailBoxService userMailBoxService;
	
	@Autowired
	SourceleadELKBaseIndexer<CampaignIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	ContactsGroupService contactsGroupService;
	
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/campaigns/{mailType}", method = RequestMethod.GET)
	 public @ResponseBody void CampaignMailBox(@PathVariable String mailType,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/sourcelead/hotListCampaigns?mailType=").append(mailType).toString());
			
	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/hotListCampaigns")
 	public ModelAndView hotListCampaigns(HttpServletRequest request,@RequestParam(value="mailType", required=false) String mailType) throws Exception {
		    ModelAndView model = new ModelAndView();
		   
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
		    commonUtil.highLightMenu(Constants.Campaigns, request);
		    
		    Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Inbox");
			Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Requested");
			SourceLeadSessionManager.setSessionAttribute(request, "unReadMailsCount", inboxUnreadMailsCount+requestedUnreadMailsCount);
			
 			model.setViewName("mailBox");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("inboxUnreadMailsCount", inboxUnreadMailsCount);
 			model.addObject("requestedUnreadMailsCount", requestedUnreadMailsCount);
 			model.addObject("mailType", mailType);
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/joblistMail")
 	public ModelAndView joblistMail(HttpServletRequest request) throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    ModelAndView model = new ModelAndView();
 			model.setViewName("joblistMail");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			 List<ContactsGroup> contactsGroupList = contactsGroupService.getContactsGroupListByUserId(user.getId());
			 model.addObject("contactsGroupList", contactsGroupList);
 			return model;
 	}
	
	/**
	 * get Jobs list
	 * @param jobCampaignType
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJobsForCampaign", method = RequestMethod.GET)
	public @ResponseBody ResponseList<RequirementDTO> getJobsForCampaign(RequirementDTO requirementDTO,@RequestParam(value="jobCampaignType", required=true) String jobCampaignType,
			HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    
	    String[] tableColumns = {"ischeck", "jobId", "jobTitle", "jobType", "rate", "location", "posted on", "priority"};
	    int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default referContactsDTO order
		if(requirementDTO.getOrder() != null && !requirementDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(requirementDTO.getOrder().get(0).get("column"));
			sortColumnOrder = requirementDTO.getOrder().get(0).get("dir");
		}
		requirementDTO.setSortColumn(tableColumns[sortColumnIndex]);
		requirementDTO.setSortOrder(sortColumnOrder);
		requirementDTO.setJobCampaignType(jobCampaignType);
	    
		ResponseList<RequirementDTO> requirementList = requirementService.getAllRequirementsForJobCampaing(requirementDTO, orgLocations.getId(), user.getId(),orgLocations.getDateFormat());
	   
		return requirementList;
	}
	
	
	@RequestMapping(value = "/viewAllJobCampaigns")
 	public ModelAndView viewAllJobCampaigns(@RequestParam(value="jobCampaignUUID", required=false) String jobCampaignUUID,HttpServletRequest request) throws Exception {
		    ModelAndView model = new ModelAndView();
		    
 			model.setViewName("viewAllJobCampaigns");
 			model.addObject("jobCampaignUUID",jobCampaignUUID);
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	@RequestMapping(value = "/jobCampaignSummary")
	public ModelAndView jobCampaignSummary(@RequestParam(value = "requirementUUID", required = false) String requirementUUID) {
		
	 ModelAndView model = new ModelAndView();
	 model.setViewName("jobCampaignSummary");
	 model.addObject("sourceleadUrl", sourceleadUrl);
	 model.addObject("requirementUUID", requirementUUID);
	 return model;
		
	}
	
	
	
}
