package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.ui.Model;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.CampaignList;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.CreateSender;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CampaignService;
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
@RequestMapping("/app")
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
	
	@Autowired
	CampaignService campaignService;
	
	
	
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/campaigns/{mailType}", method = RequestMethod.GET)
	 public @ResponseBody void CampaignMailBox(@PathVariable String mailType,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/hotListCampaigns?mailType=").append(mailType).toString());
			
	}
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')")
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
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/joblistMail")
 	public ModelAndView joblistMail(HttpServletRequest request) throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    ModelAndView model = new ModelAndView();
 			model.setViewName("joblistMail");
 			 commonUtil.highLightMenu(Constants.Campaigns, request);
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
	
	
	/**
	 * Returns campaignSender jsp popup
	 * @return jsp
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/campaignSender",method=RequestMethod.POST)
	public String campaignSenderJsp(@RequestParam(value="senderId", required=false) String senderId,HttpServletRequest request,HttpServletResponse response,Model model) throws NumberFormatException, Exception {
		if(StringUtils.isNotBlank(senderId)) {
			CreateSender senderIdentity = campaignService.findSenderIdentityById(Long.parseLong(senderId));
			model.addAttribute("sender", senderIdentity);
		}
		return "campaign/campaignSender";
//		return model;
	}
	
	/**
	 * Returns campaignSendersList jsp
	 * 
	 * @param request
	 * @param response
	 * @return jsp
	 */
	@RequestMapping(value = "/campaignSendersList")
	public ModelAndView campaignSendersListJsp(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView();
		
		 commonUtil.highLightMenu(Constants.CampaignSender, request);
		 
		model.setViewName("campaign/campaignSenderList");
		commonUtil.highLightMenu(Constants.Campaigns, request);
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	/**
	 * Returns recipientsList jsp
	 * 
	 * @param request
	 * @param response
	 * @return jsp
	 * @throws Exception 
	 */
	@RequestMapping(value = "/recipientsList")
	public ModelAndView recipientsListJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		commonUtil.highLightMenu(Constants.Campaigns, request);
		model.setViewName("campaign/campaignRecipientList");
		commonUtil.highLightMenu(Constants.CampaignRecipient, request);
		return model;
	}
	
	
	/**
	 * Resturns campaignSender jsp popup
	 * @return jsp
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@RequestMapping(value = "/campaignAddRecipient",method=RequestMethod.POST)
	public String campaignAddRecipientJsp(@RequestParam(value="listId", required=false) String listId,HttpServletRequest request,HttpServletResponse response,Model model) throws NumberFormatException, Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(StringUtils.isNotBlank(listId) && !"undefined".equalsIgnoreCase(listId)) {
			CampaignList campaignList = campaignService.getCampaignListById(Long.parseLong(listId));
			model.addAttribute("campaignList", campaignList);
			if(campaignList.getCampaignRecipient() !=null && campaignList.getCampaignRecipient().size() >0) {
				model.addAttribute("recipientList", campaignList.getCampaignRecipient());
			}
		}
		List<ContactsGroup> contactsGroupList = contactsGroupService.getContactsGroupListByUserId(user.getId());
		model.addAttribute("contactsGroupList", contactsGroupList);
		model.addAttribute("sourceleadUrl", sourceleadUrl);
		return "campaign/campaignRecipient";
	}
	
	/**
	 * returning campaign stats graph
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/campaignStatsGraph")
	public ModelAndView campaignStatsGraph(@RequestParam(value="campaignName",required=false) String campaignName,HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.addObject("campaignName", campaignName);
		   model.setViewName("campaign/campaignStatsGraph");
	       return model;
	}
	
	@RequestMapping(value = "/campaignStats")
	public ModelAndView campaignStats(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("campaign/campaignStats");
	       return model;
	}
	
	
	
	
	
}