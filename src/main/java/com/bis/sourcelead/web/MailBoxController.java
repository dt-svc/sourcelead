package com.bis.sourcelead.web;

import java.util.ArrayList;
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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
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
	
	
	@RequestMapping(value = "/getClientContacts")
	public @ResponseBody String getClientContacts(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    
	    List<OrgSpoc> clientContactListByOrgLocationId = orgSpocService.getAllClientContactsListByOrgLocationId(user.getId(), orgLocations.getId());
	    resultJson = commonUtil.toJSON(clientContactListByOrgLocationId);
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
	public @ResponseBody String getCandidatesForCampaign(HttpServletRequest request) throws Exception {

	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    String resultJson;
	    List<Candidate> candList = new ArrayList<Candidate>();
	    List<Candidate> candidatesList = candidateService.getAllActiveCandidatesForCampaignByLocation(orgLocations.getId(),"Consultant");
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
	
	
}
