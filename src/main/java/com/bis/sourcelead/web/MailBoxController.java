package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
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
import com.bis.sourcelead.dto.EmailAccountSettingsDTO;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.EmailAccountSettings;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContactsGroupService;
import com.bis.sourcelead.service.EmailAccountSettingsService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class MailBoxController {
	

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CandidateHotListService candidateHotListService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	EmailAccountSettingsService emailAccountSettingsService;
	
	@Autowired
	UserMailBoxService userMailBoxService;
	
	@Autowired
	SourceleadELKBaseIndexer<CampaignIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	ContactsGroupService contactsGroupService;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/app/mailBox/{mailType}", method = RequestMethod.GET)
	 public @ResponseBody void mailBox(@PathVariable String mailType,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/userMailBox?mailType=").append(mailType).toString());
			
	}
	
	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/app/userMailBox")
 	public ModelAndView userMailBox(HttpServletRequest request,@RequestParam(value="mailType", required=false) String mailType) throws Exception {

		    ModelAndView model = new ModelAndView();
		   
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
		    
		    Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Inbox");
			Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Requested");
			SourceLeadSessionManager.setSessionAttribute(request, "unReadMailsCount", inboxUnreadMailsCount+requestedUnreadMailsCount);
			
			List<EmailAccountSettings> emailAccountSettingsServiceList = emailAccountSettingsService.emailAccountSettingsList(user.getId());
			if(emailAccountSettingsServiceList != null){
				model.addObject("emailAccountSettingsServiceList", emailAccountSettingsServiceList);
			}
 			model.setViewName("mailBox");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("inboxUnreadMailsCount", inboxUnreadMailsCount);
 			model.addObject("requestedUnreadMailsCount", requestedUnreadMailsCount);
 			model.addObject("mailType", mailType);
 			return model;
 	}
	
	
	
	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/app/composeMail")
 	public ModelAndView composeMail(HttpServletRequest request,@RequestParam(value="UUID", required=false) String UUID, @RequestParam(defaultValue="") String mailType) throws Exception {
		    
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    ModelAndView model = new ModelAndView();
 			model.setViewName("composeMail");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			
 			List<ContactsGroup> contactsGroupList = contactsGroupService.getContactsGroupListByUserId(user.getId());
 			 model.addObject("contactsGroupList", contactsGroupList);
 			 
 			if(!isNullOrEmpty(UUID)){
 				model.addObject("UUID", UUID);
 				UserMailBox mailBox = userMailBoxService.getUserMailBoxByUUID(UUID);
 				if(null != mailBox){
 					model.addObject("subject", mailBox.getSubject());
 					model.addObject("fromEmail", mailBox.getFromMail());
 					model.addObject("toEmail", mailType.equalsIgnoreCase("reply") ? mailBox.getToMail() : mailBox.getFromMail());
 				}else{
 					model.setViewName("registration/persmissionDenied");
 				}
 			}
 			
 			return model;
 	}

	@RequestMapping(value = "/app/emailSettings")
	@ResponseBody
	public ModelAndView emailSettings(HttpServletRequest request, @RequestParam(defaultValue = "others") String source) {
		ModelAndView model = new ModelAndView();
		String incomingServer = "secure.emailsrvr.com";
		String incomingPort = "993";
		String outgoingServer = "secure.emailsrvr.com";
		String outgoingPort = "465";
		switch (source) {
		case "gmail":
			incomingServer = "imap.gmail.com";
			incomingPort = "993";
			outgoingServer = "smtp.gmail.com";
			outgoingPort = "465";
			break;
		case "yahoo":
			incomingServer = "imap.mail.yahoo.com";
			incomingPort = "993";
			outgoingServer = "smtp.mail.yahoo.com";
			outgoingPort = "465";
			break;
		default:
			break;
		}
		EmailAccountSettingsDTO emailAccountSettingsDTO = new EmailAccountSettingsDTO();
		emailAccountSettingsDTO.setIncomingServer(incomingServer);
		emailAccountSettingsDTO.setIncomingPort(incomingPort);
		emailAccountSettingsDTO.setIncomingSSL(true);
		emailAccountSettingsDTO.setOutgoingServer(outgoingServer);
		emailAccountSettingsDTO.setOutgoingPort(outgoingPort);
		emailAccountSettingsDTO.setOutgoingSSL(true);
		model.addObject("emailAccount", emailAccountSettingsDTO);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("emailConfiguration");
		return model;
	}
	
	
	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/app/forwardEmail")
 	public ModelAndView forwardEmail(HttpServletRequest request,@RequestParam(value="UUID", required=false) String UUID,
 			@RequestParam(value="mailType", required=false) String mailType) {
		    ModelAndView model = new ModelAndView();
 			if(mailType.equalsIgnoreCase("composeMail")){
 				 model.setViewName("composeMail");
 			}else{
 				 model.setViewName("hotlistMail");
 			}
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			if(!isNullOrEmpty(UUID)){
 				model.addObject("UUID", UUID);
 				model.addObject("mailType", "forwardEmail");
 				UserMailBox mailBox = userMailBoxService.getUserMailBoxByUUID(UUID);
 				if(null == mailBox){
 					model.setViewName("registration/persmissionDenied");
 				}
 			}
 			
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/app/hotlistMail")
 	public ModelAndView hotlistMail(HttpServletRequest request) throws Exception {
		 System.out.println(" inisde hotlist mail ");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    ModelAndView model = new ModelAndView();
 			model.setViewName("hotlistMail");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			 commonUtil.highLightMenu(Constants.Campaigns, request);
 			 List<ContactsGroup> contactsGroupList = contactsGroupService.getContactsGroupListByUserId(user.getId());
			 model.addObject("contactsGroupList", contactsGroupList);
			 System.out.println(" after hotlist mail completed");
 			return model;
 	}
	
	

	@RequestMapping(value = "/app/requestForCandidateResume")
 	public ModelAndView requestForCandidateResume(@RequestParam(value="UUID", required=false) String UUID,
			 HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
		    UserMailBox usermail = userMailBoxService.getUserMailBoxByUUID(UUID);
 			model.setViewName("requestForCandidateResume");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("orgLocationId", usermail.getOrgLocationId().getId());
 			model.addObject("orgId", usermail.getOrgLocationId().getOrg().getId());
 			model.addObject("UUID", UUID);
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/app/getClientContacts")
	public @ResponseBody String getClientContacts(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<OrgSpoc> clientContactListByOrgLocationId = orgSpocService.getAllClientContactsListByOrgLocationId(user.getId(), orgLocations.getId());
	    resultJson = commonUtil.toJSON(clientContactListByOrgLocationId);
		return resultJson;
	}
	
	
	
	/*@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/getUserMails")
	public @ResponseBody String getUserMails(@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    List<UserMailBox> mailBoxList = null;
	    
	    if(!type.equalsIgnoreCase("Starred")){
	    	mailBoxList = userMailBoxService.getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(user.getId(), orgLocations.getId(), type);
	    }else if(type.equalsIgnoreCase("Starred")){
	    	mailBoxList = userMailBoxService.getUserFavouriteMailsByUserIdAndOrgLocationId(user.getId(), orgLocations.getId());
	    }
	     
	    if(mailBoxList != null){
			calculateDays(mailBoxList);
		}
	    resultJson = commonUtil.toJSON(mailBoxList);
		return resultJson;
	}*/
	
	
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
	@RequestMapping(value = "/app/getUserMailsFromELK")
	public @ResponseBody String getUserMailsFromELK(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "mailType", required = false) String mailType,
			HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    Long timeZoneId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "timeZoneId");
	    Timezone timezone = timeZoneService.getTimezone(timeZoneId);
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    List<CampaignIndex> mailBoxList = new ArrayList<CampaignIndex>();
	    Long maxResults = null;
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    df.setTimeZone(TimeZone.getTimeZone("UTC"));
	    
	     BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocations.getId()));
		 boolQueryBuilder.must(QueryBuilders.matchQuery("userId", user.getId()));
		 boolQueryBuilder.must(QueryBuilders.matchQuery("status", Constants.RECORD_ACTIVE));
		 
		 if(!type.equalsIgnoreCase("Starred")){
			 boolQueryBuilder.must(QueryBuilders.matchQuery("mailStatus", type));
		 }else{
			 boolQueryBuilder.must(QueryBuilders.matchQuery("isFavourite", Constants.RECORD_ACTIVE));
		 }
		 
		 if(StringUtils.isNotBlank(mailType) &&  mailType.equalsIgnoreCase("mails")){
			 boolQueryBuilder.must(QueryBuilders.matchQuery("mailType", 1));
		 }else{
			 boolQueryBuilder.must(QueryBuilders.matchQuery("mailType", 2));
		 }
		 
		
	     maxResults = userMailBoxService.getMailBoxCountByUserIdAndOrgLocationid(user.getId(), orgLocations.getId(), type);
	   
	    try {
	    	 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResultsForCampaigns(Constants.ELK_CAMPAIGN_INDEX, Constants.ELK_CAMPAIGN_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
	 	     if(null != searchHits && searchHits.length >0){
	 	    	ObjectMapper mapper = new ObjectMapper();
	 	    	for (SearchHit hit : searchHits) {
	 	    		StringBuilder json = new StringBuilder(hit.getSourceAsString());
	 	    		mapper.setDateFormat(df);
	 	    	    CampaignIndex campaignIndex = mapper.readValue(json.toString(), CampaignIndex.class);
	 	    		mailBoxList.add(campaignIndex);
	 	    	}
	 	    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	     
	   
	    
	    if(mailBoxList != null){
			calculateDays(mailBoxList,timezone);
		}
	    resultJson = commonUtil.toJSON(mailBoxList);
		return resultJson;
	}
	
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/app/getClientsListForHotlist")
	public @ResponseBody String getClientsListForHotlist(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId(),user.getId());
	    resultJson = commonUtil.toJSON(clientsList);
		return resultJson;
	}
	
	
	@SuppressWarnings("static-access")
	public void calculateDays(List<CampaignIndex> mailBoxList, Timezone timezone) throws ParseException {
		for (CampaignIndex userMailBox : mailBoxList) {
			long diff = commonUtil.currentDate().getTime() - userMailBox.getCreatedDate().getTime();
			
		    long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder  time = new StringBuilder();
			
			if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
				userMailBox.setCalDays(time.append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0 && diffHours == 0){
				userMailBox.setCalDays(time.append(diffMinutes).append(" min ").append(diffSeconds).append(" sec ago ").toString());
			}else if(diffDays == 0){
				userMailBox.setCalDays(time.append(diffHours).append(" hours ").append(diffMinutes).append(" min ago ").toString());
			}else if(diffDays < 7){
				userMailBox.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			}else{
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
				String currentTimeStr = Constants.TZ_DF.format(userMailBox.getCreatedDate());
				String currentDateInTimezone= CommonUtil.convertTimeZones("UTC" ,timezone.getTimeZoneId(), currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				String commonDateFormat = standardFormat.format(converteDate);
				userMailBox.setCalDays(commonDateFormat);
			} 

		}

	}
	
	
	
}
