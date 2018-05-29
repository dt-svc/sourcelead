package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.beans.CandidateCompareDTO;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class RecommendedCustomizeScoreRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(RecommendedCustomizeScoreRestController.class);

	private static final String CandidateCompareDTO = null;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Autowired
	CommonUtil commonUtil;
	 
	@Autowired
	OrgLocationService orgLocationService;
	 
	 
	@RequestMapping(value = "/getCustomizeScore")
	public @ResponseBody Response<RecommendedCustomizeScore> getCustomizeScore(@RequestParam(value="requirementId", required=false) Long requirementId,HttpServletRequest request) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<RecommendedCustomizeScore> res = new Response<>();
		if(user != null) {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			RecommendedCustomizeScore rcs = null;
			try {
			rcs = recommendedCustomizeScoreService.getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(requirementId, orgLocations.getId());
			if(rcs == null){
				 rcs = new RecommendedCustomizeScore();
			}
			//Skills
			List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
			if(null != rcs && null != rcs.getCustomizeSkills()){
		  		String[] skills = rcs.getCustomizeSkills().split(","); 
			    for(String skill : skills){ 
			    	if(skillsList.contains(skill)){
			    		    skillsList.remove(skill);
			    	}
			   }  
		  	}
			rcs.setDbSkills(skillsList.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]",""));
			
			//Visa Type
			List<String> visaList = lookUpDataService.getAllVisaByCategory();
			if(null != rcs && null != rcs.getCustomizeVisatype()){
		  		String[] arr = rcs.getCustomizeVisatype().split(","); 
			    for(String visaName : arr){ 
			    	if(visaList.contains(visaName)){
			    		visaList.remove(visaName);
			    	}
			   }  
		  	}
			rcs.setDbVisaType(visaList.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]",""));
			
			//Education 
			List<String> educationsList = lookUpDataService.getLookUpDataByCategoryEducation();
			if(null != rcs && null != rcs.getCustomizeEducation()){
		  		String[] arr = rcs.getCustomizeEducation().split(","); 
			    for(String editEducationName : arr){ 
			    	if(educationsList.contains(editEducationName)){
			    		educationsList.remove(editEducationName);
			    	}
			   }  
		  	}
			rcs.setDbEducation(educationsList.toString().replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]",""));
			res.setObject(rcs);
			res.setResponse(Result.SUCCESS.name());
			res.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				res.setResponse(Result.AWKWARD.name());
				res.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			res.setResponse(Result.SESSION_TIMEDOUT.name());
			res.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
	return res;
	}

	

	@RequestMapping(value = "/updateCustomizeScore")
	public @ResponseBody Response<String> updateCustomizeScore(@RequestParam(value="requirementId", required=false) Long requirementId,
			@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String json1 = "{"+json+"}";
	    JSONObject jsonObj = new JSONObject(json1);
	    Response<String> response = new Response<>();
	    if(user != null) {
	    try {
			RecommendedCustomizeScore rcs = recommendedCustomizeScoreService.getRecommendedCustomizeScoreByRequirementIdAndOrgLocationId(requirementId, orgLocations.getId());
			
			rcs.setReqirementId(rcs.getReqirementId());	
			rcs.setOrgLocationId(rcs.getOrgLocationId());
	
			//splits the Skills 
			if(jsonObj.has("customizeSkills")){
				String skillId =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("customizeSkills");
					skillId = jsonArray.toString();
				}
				catch(JSONException e){
					skillId =jsonObj.getString("customizeSkills");
				}
				skillId = skillId.replaceAll("\\[", "").replaceAll("\\]","");
				
				
				skillId = skillId.replaceAll("\"", "");
				rcs.setCustomizeSkills(skillId);
			
			}else{
				rcs.setCustomizeSkills(null);
			}
			
			
			//Split the Noticeperiod
			if(jsonObj.has("noticeperiod")){
					String jsonArray = jsonObj.getString("noticeperiod");
					String noticeperiodId = jsonArray.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "");
					rcs.setCustomizeNoticeperiod(noticeperiodId);
				
				}else{
					rcs.setCustomizeNoticeperiod(null);
				}
			//Split the Visa type
			if(jsonObj.has("visaType")){
				String jsonArray = jsonObj.getString("visaType");
				String visaType = jsonArray.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "");
				rcs.setCustomizeVisatype(visaType);
			
			}else{
				rcs.setCustomizeVisatype(null);
			}
			
			//Split the Education
			if(jsonObj.has("education")){
				String jsonArray = jsonObj.getString("education");
				String education = jsonArray.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "");
				rcs.setCustomizeEducation(education);
			
			}else{
				rcs.setCustomizeEducation(null);
			}
			
			
			//Split the Keywords
					if(jsonObj.has("anyKeyWord")){
								
							String anyKeyWordId =null;
							try{
								JSONArray jsonArray = jsonObj.getJSONArray("anyKeyWord");
								anyKeyWordId = jsonArray.toString();
							}
							catch(JSONException e){
								anyKeyWordId =jsonObj.getString("anyKeyWord");
							}
							anyKeyWordId = anyKeyWordId.replaceAll("\\[", "").replaceAll("\\]","");
							
							
							anyKeyWordId = anyKeyWordId.replaceAll("\"", "");
							rcs.setCustomizeKeywords(anyKeyWordId);
						
						}else{
							rcs.setCustomizeKeywords(null);
						}
			/*if(jsonObj.has("customizeCountry")){
				rcs.setCustomizeState(jsonObj.getString("customizeCountry").split(",")[0]);
			 }
			if(jsonObj.has("customizeState")  && null != jsonObj.getString("customizeState") && "" != jsonObj.getString("customizeState")){
				rcs.setCustomizeState(jsonObj.getString("customizeState"));
			 }*/
			 
			if(jsonObj.has("customizedCity")){
				String cityId =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("customizedCity");
					cityId = jsonArray.toString();
				}
				catch(JSONException e){
					cityId =jsonObj.getString("customizedCity");
				}
				cityId = cityId.replaceAll("\\[", "").replaceAll("\\]","");
				
				
				cityId = cityId.replaceAll("\"", "");
				rcs.setCustomizeCity(cityId);
			
			}else{
				rcs.setCustomizeCity(null);
			}
			
			if(jsonObj.has("minExperience") && null != jsonObj.getString("minExperience") && "" != jsonObj.getString("minExperience")){
				rcs.setCustomizeMinExperience(jsonObj.getString("minExperience"));
			 }
			if(jsonObj.has("maxExperience") && null != jsonObj.getString("maxExperience") && "" != jsonObj.getString("maxExperience")){
				rcs.setCustomizeMaxExperience(jsonObj.getString("maxExperience"));
			 }
			if(jsonObj.has("currentRateType") && null != jsonObj.getString("currentRateType") && "" != jsonObj.getString("currentRateType")){
				rcs.setCustomizeRatetype(jsonObj.getString("currentRateType"));
			 }
			if(jsonObj.has("customizeRate") && null != jsonObj.getString("customizeRate") && "" != jsonObj.getString("customizeRate")){
				rcs.setCustomizeRate(jsonObj.getString("customizeRate"));
			 }
			
			if(jsonObj.has("skillScore")){
				rcs.setCustomizeSkillScore(jsonObj.getString("skillScore"));
			 }
			if(jsonObj.has("locationScore")){
				rcs.setCustomizeLocationScore(jsonObj.getString("locationScore"));
			 }
			if(jsonObj.has("experienceScore")){
				rcs.setCustomizeExperienceScore(jsonObj.getString("experienceScore"));
			 }
			if(jsonObj.has("rateScore")){
				rcs.setCustomizeRateScore(jsonObj.getString("rateScore"));
			 }
			if(jsonObj.has("noticeperiodScore")){
				rcs.setCustomizeNoticeperiodScore(jsonObj.getString("noticeperiodScore"));
			 }
			if(jsonObj.has("keywordScore")){
				rcs.setCustomizeKeywordsScore(jsonObj.getString("keywordScore"));
			 }
			
			if(jsonObj.has("visaTypeScore")){
				rcs.setCustomizeVisatypecore(jsonObj.getString("visaTypeScore"));
			 }
			
			if(jsonObj.has("educationScore")){
				rcs.setCustomizeEducationScore(jsonObj.getString("educationScore"));
			 }
			
			 if(user.getUserCode() != null){
				 rcs.setUpdatedBy(user.getUserCode());	
		    }
			rcs.setUpdatedDate(commonUtil.currentDate());
			
			recommendedCustomizeScoreService.updateCustomizeScore(rcs);
			response.setObject("success");
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}
	
	/**
	 * @author Pavan Jampana used to get job and recommended candidates to
	 *         compare
	 * 
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/getCandidatesToCompare","/rest/getCandidatesToCompare" }, method = RequestMethod.GET)
	@ResponseBody Response<CandidateCompareDTO> getCandidatesToCompare(@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "candidatesSelected", required = false) String candidatesSelected,CandidateDTO candidateDTO) throws Exception
	{
		Response<CandidateCompareDTO> response = new Response<>();
		try {
			CandidateCompareDTO candidateCompare = recommendedCustomizeScoreService.getCandidateListToCompare(requirementId,orgLocationId,candidatesSelected,candidateDTO);
			response.setObject(candidateCompare);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
   protected static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
}

