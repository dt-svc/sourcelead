package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.TeamService;
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
	UserMailBoxService userMailBoxService;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/mailBox")
 	public ModelAndView mailBox(HttpServletRequest request) throws Exception {
		    ModelAndView model = new ModelAndView();
		   
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
		    Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Inbox");
			Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(), "Requested");
			
 			model.setViewName("mailBox");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("inboxUnreadMailsCount", inboxUnreadMailsCount);
 			model.addObject("requestedUnreadMailsCount", requestedUnreadMailsCount);
 			return model;
 	}

	
	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/composeMail")
 	public ModelAndView composeMail(HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			model.setViewName("composeMail");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/hotlistMail")
 	public ModelAndView hotlistMail(HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			model.setViewName("hotlistMail");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	

	@RequestMapping(value = "/requestForCandidateResume")
 	public ModelAndView requestForCandidateResume(@RequestParam(value="UUID", required=false) String UUID,
			 HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			model.setViewName("requestForCandidateResume");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			model.addObject("UUID", UUID);
 			return model;
 	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/getClientContacts")
	public @ResponseBody String getClientContacts(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<OrgSpoc> clientContactListByOrgLocationId = orgSpocService.getAllClientContactsListByOrgLocationId(user.getId(), orgLocations.getId());
	    resultJson = commonUtil.toJSON(clientContactListByOrgLocationId);
		return resultJson;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX')")
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
	}
	
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/getClientsListForHotlist")
	public @ResponseBody String getClientsListForHotlist(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId(),user.getId());
	    resultJson = commonUtil.toJSON(clientsList);
		return resultJson;
	}
	
	@PreAuthorize("hasRole('ROLE_HOTLIST_CAMPAIGN')")
	@RequestMapping(value = "/getCandidatesForCampaign")
	public @ResponseBody String getCandidatesForCampaign(@RequestParam(value = "candidatesListType", required = false) String candidatesListType,
			HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    List<Candidate> candList = new ArrayList<Candidate>();
	    List<Candidate> candidatesList = null;
	    List<Team> teamList = null;
	    
	    if(!isNullOrEmpty(candidatesListType)){
	    	if(candidatesListType.equalsIgnoreCase("consultants")){
	    		 candidatesList = candidateService.getAllActiveCandidatesForCampaignByLocation(orgLocations.getId(),"Consultant");
	    	}else if(candidatesListType.equalsIgnoreCase("candidates")){
	    		 candidatesList = candidateService.getAllActiveCandidatesForCampaignByLocation(orgLocations.getId(),"Candidate");
	    	}
	    	else if(candidatesListType.equalsIgnoreCase("openHotlist")){
	    		candidatesList = candidateService.getAllHotlistCandidatesAndNotAssignedToTeamByLocation(orgLocations.getId());
	    	}
	    	
	    	else if(candidatesListType.equalsIgnoreCase("hotlistConsultants") || candidatesListType.equalsIgnoreCase("hotlistCandidates")){
	    		UserRole userRole = userRoleService.getUserByUserId(user.getId());
	    		if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_ADMIN") || userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
	    			teamList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
	    		}else{
	    			teamList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), orgLocations.getId());
	    		}
	    		if(null != teamList && teamList.size() > 0){
	    			if(candidatesListType.equalsIgnoreCase("hotlistConsultants")){
		    			candidatesList = candidateService.getAllCandidatesForCampaignByTeam(teamList,"Consultant");
		    		}else if(candidatesListType.equalsIgnoreCase("hotlistCandidates")){
		    			candidatesList = candidateService.getAllCandidatesForCampaignByTeam(teamList,"Candidate");
		    		}
	    		}
	    		
	    		
	    	}
	    	
	    }
	    
	    resultJson = commonUtil.toJSON(candidatesList);
		return resultJson;
	}
	
	
	
	public void calculateDays(List<UserMailBox> mailBoxList) throws ParseException {
		for (UserMailBox userMailBox : mailBoxList) {
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
				String commonDateFormat = standardFormat.format(userMailBox.getCreatedDate());
				userMailBox.setCalDays(commonDateFormat.toString());
			} 

		}

	}
	
	
}
