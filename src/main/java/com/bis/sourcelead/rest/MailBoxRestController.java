package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.service.HotListEmailCampaignService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class MailBoxRestController {
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	UserSignatureService userSignatureService;
	
	@Autowired
	HotListEmailCampaignService hotListEmailCampaignService;
	
	@Autowired
	UserMailBoxService userMailBoxService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CandidateSkillsService candidateSkillsService;
	
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	
	@RequestMapping(value = "/getClientContactsEmailsByOrgRelationId", method = RequestMethod.POST)
	@ResponseBody String getClientContactsEmailsByOrgRelationId(@RequestParam(value = "orgRelationIds", required = false) String orgRelationIds) throws Exception {
		
		String[] relationsId = orgRelationIds.split(",");
		StringBuilder emails  = new StringBuilder();
		for(int i=0 ; i < relationsId.length; i++){
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocByOrgRelationId(Long.valueOf(relationsId[i]));
			for(OrgSpoc orgSpoc :orgSpocList){
				emails.append(orgSpoc.getSpocUser().getEmail()).append(",");
			}
		}
		emails.toString();
		
		 String spocEmails =  emails.substring(0,emails.length()-1);
		 System.out.println(spocEmails);
		
		return commonUtil.toJSON(spocEmails);
	}
	
	
	@RequestMapping(value = "/sendHotListCampaign", method = RequestMethod.POST)
	public String sendHotListCampaign(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		hotListEmailCampaignService.sendHotListEmailCampaign(jsonObj, user, orgLocation, multiFile);
		
		
		
		return "success";
	}
	
	
	@RequestMapping(value = "/saveUserSignature", method = RequestMethod.POST)
	public String saveUserSignature(@RequestParam(value = "json", required = false) String json,HttpServletRequest request) throws Exception {
		
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		userSignatureService.setUserSignatureDetails(jsonObj, user, orgLocation);
		
		
		return "success";
	}
	
	

	 @RequestMapping(value = "/getSignatures", method = RequestMethod.POST)
		public @ResponseBody String getSignatures(HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<UserSignature> singaturesList = userSignatureService.getUserSignatureListByUserIdAndLocationId(user.getId(), orgLocations.getId());
			resultJson = toJSON(singaturesList);
			return resultJson;
		}

	 
	 
	 @RequestMapping(value = "/getRequestedCandidates", method = RequestMethod.POST)
		public @ResponseBody String getRequestedCandidates(@RequestParam(value = "UUID", required = false) String UUID,
				HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
			List<Long> candidateId = new ArrayList<Long>();
			 for (String s : userMailbox.getCandidateId().split(",")){
				 candidateId.add(Long.parseLong(s));
			 }
			
			List<Candidate> reqCandidatesList = new ArrayList<Candidate>();
			List<Candidate> candidatesList = candidateService.getAllRequestedCandidatesForCampaign(candidateId);
			if(null != candidatesList){
			
				for(Candidate candidate : candidatesList){
					StringBuilder skills = new StringBuilder();
					List<CandidateSkills> skillsList = candidateSkillsService.getCandidateSkillsByCandidateId(candidate.getId().toString());
					 if(skillsList != null){
						 for(CandidateSkills skill : skillsList){
							 skills.append(skill.getName()).append(", ");
						 }
					 }
					 
					 skills.toString();
					 String candidateSkills =  skills.substring(0,skills.length()-2);
					 
					 String candidateName = null;
					 String experienceInYears = null;
					 String experienceInMonths = null;
					 String totalExp = null;
					 String rate = null;
					
					 
					 if(userMailbox.getNameFields().equalsIgnoreCase("firstname")){
						 candidateName = candidate.getFirstName();
					 }
				 	 else if(userMailbox.getNameFields().equalsIgnoreCase("firstnameandlastName")){
						 if(!isNullOrEmpty(candidate.getLastName())){
							 candidateName = candidate.getFirstName()+" "+candidate.getLastName();
					   	 }else{
					   		candidateName = candidate.getFirstName();
					   	 }
					 }
				 	 else if(userMailbox.getNameFields().equalsIgnoreCase("firstname1stletterlastname")){
				 		 if(!isNullOrEmpty(candidate.getLastName())){
				 			candidateName = candidate.getFirstName()+" "+String.valueOf(candidate.getLastName().charAt(0));
				 		 }else{
				 			candidateName = candidate.getFirstName();
				 		 }
				 		
				 	 }
				 	 else if(userMailbox.getNameFields().equalsIgnoreCase("1letterfirstnamelastname")){
				 		 if(!isNullOrEmpty(candidate.getLastName())){
				 			candidateName = String.valueOf(candidate.getFirstName().charAt(0))+" "+candidate.getLastName();
				 		 }else{
				 			candidateName = String.valueOf(candidate.getFirstName().charAt(0));
				 		 }
				 		
				 	 }
				 	else if(userMailbox.getNameFields().equalsIgnoreCase("1letterfirstname1letterlastname")){
				 		 if(!isNullOrEmpty(candidate.getLastName())){
				 			candidateName = String.valueOf(candidate.getFirstName().charAt(0))+" "+String.valueOf(candidate.getLastName().charAt(0));
				 		 }else{
				 			candidateName = String.valueOf(candidate.getFirstName().charAt(0));
				 		 }
				 		
				 	 }
					 
					 if(!isNullOrEmpty(candidate.getExperienceYears())){
						 experienceInYears = candidate.getExperienceYears();
					 }
					 
					 if(!isNullOrEmpty(candidate.getExperienceMonths())){
						 experienceInMonths = candidate.getExperienceMonths();
					 }else{
						 experienceInMonths = "0";
					 }
					 
					 if(isNullOrEmpty(candidate.getExperienceMonths()) && isNullOrEmpty(candidate.getExperienceMonths())){
						 totalExp = "-----";
					 }else{
						 totalExp = experienceInYears+" Years "+experienceInMonths+" Months";
					 }
					 
					 if(!isNullOrEmpty(candidate.getExpectedRate())){
						 rate = candidate.getExpectedRate()+" /"+candidate.getRateType();
					 }else{
						 rate = "-----";
					 }
					 
					 candidate.setCandidateName(candidateName);
					 candidate.setTotalExp(totalExp);
					 candidate.setRate(rate);
					 candidate.setSkills(candidateSkills);
					 
					 reqCandidatesList.add(candidate);
				}
			}
			
			
			
			resultJson = toJSON(reqCandidatesList);
			return resultJson;
		}

	 
	 public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
}
