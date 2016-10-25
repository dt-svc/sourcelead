package com.bis.sourcelead.rest;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * Handles requests for the Requirement service.
 * 
 * @author Prasad K
 * @date 13th Sept 2016
 *
 */

@RestController
public class RequirementRestController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private RequirementApproverHierarchyService requirementApproverHierarchyService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 private  RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 private CandidateSkillsService candidateSkillsService;
	 
	 @Autowired
	 private CandidateService candidateService;
	 
	 @Autowired
	 private CandidateResumesService candidateResumeSerivce;
	 
	
	/**
	 * @author prasad k
	 * @since  sept 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	*/
	
	@RequestMapping(value = "/storeRequirement", method = RequestMethod.POST)
	public @ResponseBody String storeRequirement(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request,@RequestParam(value = "saveType", required = false) String saveType) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		json = json + ", saveType : "+saveType;
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Requirement requirement = requirementService.storeRequirement(jsonObj,orgLocation,user,multiFile);
		
		return "success";
		
	}
	
	/**
	 * @author sarath kumaar
	 * @since  oct 18 2016
	 * @version 0.0.1
	 * 
	 * This method is used to accept/Reject Requirement in Requirement table.
	 *  
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return success msg  
	 * 
	 * @throws NullPointer
	*/
	
	@RequestMapping(value = "/requirementAcceptOrReject", method = RequestMethod.POST)
	public @ResponseBody String requirementAcceptOrReject(@RequestParam(value = "requirementId", required = false) Long requirementId,@RequestParam(value = "status", required = false)  String status, @RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception {
		
	 
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());
	    
		// to get  RequirementApproverHierarchy with maximum Level
		Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

		if(status.equalsIgnoreCase("APPROVED")){ 
		    if(maxLevel!=null){
		    	 if(maxLevel == requirementApproverHierarchy.getLevel()){
		    		 requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.APPROVED);
				   }else{
				    	List<RequirementApproverHierarchy> list1  =  requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
				    	for(RequirementApproverHierarchy rah :list1){
				    		if(maxLevel.equals(rah.getLevel())){
								User approverUser = userService.getUserById(rah.getApproverUserId().getId());
								requirementService.approverTwoEmailScenario(loginUser, approverUser,requirement,null,null);
				    			
							   } 
				    	} 
		    }
		    	
		    }
		    
			requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
			
		}
		if(status.equalsIgnoreCase("REJECTED")){
			 if(maxLevel!=null){
		    	 if(maxLevel == requirementApproverHierarchy.getLevel()){
		    		 requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
				   }
		    }
			 
			requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
			requirementApproverHierarchy.setRejectedReason(reason);
		}
		requirementService.addRequirement(requirement);
		requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
		
		return "success";
		
	}
	
	
	/**
	 * @author punnana sarath kumar
	 * @since  sept 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	*/
	
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public @ResponseBody String getUserList(@RequestParam(value="teamType",required = false) String teamType,HttpServletRequest request) throws Exception {
		
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		List<User> usersList = userService.getOwnersByOrgLocationId(orgLocation.getId());
		List<User> usersList = userService.getUsersByTeamType(orgLocation.getId(),teamType);
		for(User user : usersList){
			List<RequirementAssignTo> assign = requirementService.getUserAssignJobCount(user.getId());
			user.setAssignJobCount(assign.size());
		}
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;
	}
	
	/**
	 * Handles Vendor Contacts 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClientContact", method = RequestMethod.POST)
	@ResponseBody
	public String saveClientContact(@RequestParam(value = "json", required = false) String json, @RequestParam(value="orgRelationId", required=false) String orgRelationId,
			                        @RequestParam(value="clientLocationId", required=false) String clientLocationId , HttpServletRequest request) throws Exception {
		
		String responderType = "Client";
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId, clientLocationId,responderType);
		
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(Long.parseLong(orgRelationId), user.getId(),orgLocations.getId());
		for (OrgSpoc orgSpoc : orgSpocsList) {
			orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
			orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
			orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
		}
		return commonUtil.toJSON(orgSpocsList);
	}
	
	/**
	 * @author Venkat Kesav
	 * @since  sept 21 2016
	 * @version 0.0.1
	 * 
	 * This method is used to getTeamsList.
	 *  
	 * @param request
	 * @return resultJson 
	 * 
	 * @throws NullPointer
	*/
	@RequestMapping(value = "/getTeamsList", method = RequestMethod.POST)
	public @ResponseBody String getTeamsList(HttpServletRequest request) throws Exception {
		
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocation.getId());
		
		resultJson = commonUtil.toJSON(teamsList);
		return resultJson;
	}
	/**
	 * @author Rajashekar
	 * Recommendations 
	 * @param requirementId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementRecommendationsByRequirementId", method = RequestMethod.POST)
	public @ResponseBody String getRequirementRecommendationsByRequirementId(@RequestParam(value="requirementId",required = false) Long requirementId,HttpServletRequest request) throws Exception {
		String resultJson;
		
		float expPercentage = 0;
		float locationPercentage = 0;
		float ratePercentage = 0;
		float skillPercentage = 0;
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<Candidate> candidateList = candidateService.getAllCandidatesByLocation(orgLocation.getId());
//		List<RequirementSubmission> requirementRecommendationList = requirementSubmissionService.getAllShortListCandidatesByOrgLocationsIdAndRequirementId(orgLocation.getId(), requirementId);
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		String reqSkills = requirement.getSkills();
		String [] reqMaxSkills = reqSkills.split(",");
		float percentPerSkill = 30/(float)reqMaxSkills.length;
		
		String reqMinExp = requirement.getMinExp();
		String reqMaxExp = requirement.getMaxExp();
		int reqExp = Integer.parseInt(reqMaxExp) *12 ;
		float percentPerMonthExp = 20/(float)reqExp;
		
		String reqCity = requirement.getCity();
		
		int reqRate = Integer.parseInt(requirement.getRate());
		String reqRateType = requirement.getRateType();
		float reqPerMonthSal = 0;
		if("Hourly".equalsIgnoreCase(reqRateType)){
			int perDaySal = reqRate *24;
			reqPerMonthSal = perDaySal * 30;
		}else if("Weekly".equalsIgnoreCase(reqRateType)){
			int perDaySal = reqRate /7;
			reqPerMonthSal = perDaySal * 30;
		}else if("Bi-weekly".equalsIgnoreCase(reqRateType)){
			int perDaySal = reqRate /14;
			reqPerMonthSal = perDaySal * 30;
		}else if("Semi-Monthly".equalsIgnoreCase(reqRateType)){
			int perDaySal = reqRate /15;
			reqPerMonthSal = perDaySal * 30;
		}else if("Monthly".equalsIgnoreCase(reqRateType)){
			reqPerMonthSal = reqRate;
		}else if("Bi-Monthly".equalsIgnoreCase(reqRateType)){
			int perDaySal = reqRate /60;
			reqPerMonthSal = perDaySal * 30;
		}else{
			reqPerMonthSal = reqRate /12;
		}
		
		
		for(Candidate candidate : candidateList){
			int matchedSkillCount = 0;
			List<CandidateSkills> candidateSkillList  = candidateSkillsService.getSkillByCandidateId(candidate.getId());
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
			CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
			for(CandidateSkills candidateSkills : candidateSkillList){
				if(reqSkills.contains(candidateSkills.getName())){
					matchedSkillCount++;
				}
			}
			 skillPercentage = matchedSkillCount*percentPerSkill;
			
			int  candidateExpMonths = Integer.parseInt(candidate.getExperienceYears())*12 + Integer.parseInt(candidate.getExperienceMonths());
		
			if(candidateExpMonths < Integer.parseInt(reqMinExp) *12){
				expPercentage = 0;
			}else if(candidateExpMonths >= Integer.parseInt(reqMinExp) *12 && candidateExpMonths <= Integer.parseInt(reqMaxExp) *12){
				expPercentage = percentPerMonthExp*(float)candidateExpMonths;
			}else{
				expPercentage = 20;
			}
			if(candidate.getCity().equalsIgnoreCase(reqCity)){
				locationPercentage = 10;
			}
			
			String candidateRateType = "Hourly";
			float perMonthCandidateSal = 0;
			if("Hourly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate *24;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Weekly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /7;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Bi-weekly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /14;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Semi-Monthly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /15;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Monthly".equalsIgnoreCase(candidateRateType)){
				perMonthCandidateSal = reqRate;
			}else if("Bi-Monthly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /60;
				perMonthCandidateSal = perDaySal * 30;
			}else{
				perMonthCandidateSal = reqRate /12;
			}
			
			if(perMonthCandidateSal<=reqPerMonthSal){
				ratePercentage = 20;
			}
			float totalPercentage = skillPercentage+expPercentage+locationPercentage+ratePercentage;
			double roundOff = Math.round(totalPercentage * 100.0) / 100.0;
			String candidateScore = String.valueOf(roundOff);
			candidate.setCandidateScore(candidateScore);
			candidate.setNoOfReq(reqList.size());
			candidate.setCandidateResume(candidateResume.getFileName());
		}
		resultJson = commonUtil.toJSON(candidateList);
		return resultJson;
		
	}
}
