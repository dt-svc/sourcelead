package com.bis.sourcelead.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Notifications;
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
	
	
	@RequestMapping(value = "/mailBox")
 	public ModelAndView mailBox(HttpServletRequest request) {
		    ModelAndView model = new ModelAndView();
 			model.setViewName("mailBox");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}

	
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
	
	
	@RequestMapping(value = "/getClientContacts")
	public @ResponseBody String getClientContacts(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<OrgSpoc> clientContactListByOrgLocationId = orgSpocService.getAllClientContactsListByOrgLocationId(user.getId(), orgLocations.getId());
	    resultJson = commonUtil.toJSON(clientContactListByOrgLocationId);
		return resultJson;
	}
	
	
	@RequestMapping(value = "/getUserMails")
	public @ResponseBody String getUserMails(@RequestParam(value = "type", required = false) String type,
			HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<UserMailBox> mailBoxList = userMailBoxService.getUserMailBoxByUserIdAndOrgLocationIdAndMailStatus(user.getId(), orgLocations.getId(), type);
	    if(mailBoxList!=null){
			calculateDays(mailBoxList);
		}
	    resultJson = commonUtil.toJSON(mailBoxList);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getClientsListForHotlist")
	public @ResponseBody String getClientsListForHotlist(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(orgLocations.getId(),user.getId());
	    resultJson = commonUtil.toJSON(clientsList);
		return resultJson;
	}
	
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
	    	else if(candidatesListType.equalsIgnoreCase("hotlistConsultants") || candidatesListType.equalsIgnoreCase("hotlistCandidates")){
	    		UserRole userRole = userRoleService.getUserByUserId(user.getId());
	    		if(userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_ADMIN") || userRole.getRoles().getRoleName().equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
	    			teamList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
	    		}else{
	    			teamList = teamService.getUserAssignedTeamListByOrgLocationId(user.getId(), orgLocations.getId());
	    		}
	    		if(candidatesListType.equalsIgnoreCase("hotlistConsultants")){
	    			candidatesList = candidateService.getAllCandidatesForCampaignByTeam(teamList,"Consultant");
	    		}else if(candidatesListType.equalsIgnoreCase("hotlistCandidates")){
	    			candidatesList = candidateService.getAllCandidatesForCampaignByTeam(teamList,"Candidate");
	    		}
	    		
	    	}
	    	
	    }
	    
	   
	    
	    
	    if(null != candidatesList){
	    	for(Candidate candidate : candidatesList){
	    		boolean flag = candidateHotListService.isCandidateIsFavourite(candidate.getId(), orgLocations.getId());
	    		if(flag == true){
	    			candidate.setIsFavourite(Constants.YES);
	    		}else{
	    			candidate.setIsFavourite(Constants.NO);
	    		}
	    		candList.add(candidate);
	    	}
	    }
	    resultJson = commonUtil.toJSON(candList);
		return resultJson;
	}
	
	
	
	public void calculateDays(List<UserMailBox> mailBoxList) throws ParseException {
		for (UserMailBox userMailBox : mailBoxList) {
			// differentiating date and time
			Date date = userMailBox.getCreatedDate();
			String[] parts = date.toString().split(" ");
			userMailBox.setDateValue(parts[0]);
			final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
			final Date dateObj = sdf.parse(parts[1].toString());
			userMailBox.setTime(new SimpleDateFormat("K:mm a").format(dateObj));
			// calculating days b/w dates
			Date currentDate = new Date();
			Date d1 = userMailBox.getCreatedDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String strDate = dateFormat.format(currentDate);
			Date d2 = dateFormat.parse(strDate);
			long diff = d2.getTime() - d1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			userMailBox.setCalDays(diffDays);

		}

	}
	
	
}
