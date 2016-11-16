package com.bis.sourcelead.rest;

import java.io.Writer;
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
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
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
	 private CandidateService candidateService;
	 
	 @Autowired
	 private UserRoleService userRoleService;
	 
	 @Autowired
	 private RecommendationsService recommendationsService;
	 
	 
	 
	
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
		    	 if(maxLevel.equals(requirementApproverHierarchy.getLevel())){
		    		     requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.APPROVED);
		    		     requirement.setUpdatedBy(loginUser.getUserCode());
				    	 requirement.setUpdatedDate(commonUtil.currentDate());
				    	 requirementService.addRequirement(requirement);
				      List<RequirementApproverHierarchy> list1  =  requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
		    		    for(RequirementApproverHierarchy rah :list1){
			    		      rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
			    		      rah.setUpdatedBy(loginUser.getUserCode());
							  rah.setUpdatedDate(commonUtil.currentDate());
			    		      requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
		            	} 
		    		    User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());
		    			requirementService.acceptEmailScenario(loginUser, intiatorUser, requirement,orgLocation); 
				 }else{
					    requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
					    requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
					    requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
					    requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
						User approverUser = userService.getUserById(requirementApproverHierarchy.getApproverUserId().getId());
						requirementService.approverTwoEmailScenario(loginUser, approverUser,requirement,null,orgLocation);
		          }
            }
		}
		if(status.equalsIgnoreCase("REJECTED")){
			 if(maxLevel!=null){
		    	 if(maxLevel.equals(requirementApproverHierarchy.getLevel())){
		    		     requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
		    		     requirement.setUpdatedBy(loginUser.getUserCode());
				    	 requirement.setUpdatedDate(commonUtil.currentDate());
				    	 requirementService.addRequirement(requirement);
				      List<RequirementApproverHierarchy> list1  =  requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
		    		    for(RequirementApproverHierarchy rah :list1){
			    		      rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
			    		      rah.setUpdatedBy(loginUser.getUserCode());
			    		      if(rah.getApproverUserId().getId() == loginUser.getId()){
			    		    	  requirementApproverHierarchy.setRejectedReason(reason);
			    		      }
							  rah.setUpdatedDate(commonUtil.currentDate());
			    		      requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
		            	} 
		    		    
		    		    
					    // Email for Reject requirement  from approver level Two to Initiator.

		    		    User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());
		    			requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, null, reason); 
				   }else{
					    requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
					    requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
					    requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
					    requirementApproverHierarchy.setRejectedReason(reason);
					    requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
					    
					    // Email for Reject requirement  from approver level One to Initiator.
					    User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());
		    			requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, null, reason); 
						//User approverUser = userService.getUserById(requirementApproverHierarchy.getApproverUserId().getId());
						//requirementService.approverTwoEmailScenario(loginUser, approverUser,requirement,null,orgLocation);
				   }
		    }
		}
		
		
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
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocation.getId());
			if(userRole !=null){
				user.setUserType(userRole.getUserType());
			}
		}
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;
	}
	
	
	
	@RequestMapping(value = "/getApproverTwoByUserId", method = RequestMethod.POST)
	public @ResponseBody String getApproverTwoByUserId(@RequestParam(value = "currentApproverId", required = false)  Long currentApproverId,
		HttpServletRequest request) throws Exception {
		String resultJson = null;
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		List<User> userList = userService.getUsersByTeamTypeAndNoCurrentUser(currentApproverId,orgLocation.getId());
		resultJson = toJSON(userList);
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
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId, clientLocationId,responderType,org);
		
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(Long.parseLong(orgRelationId), user.getId(),orgLocations.getId());
		for (OrgSpoc orgSpoc : orgSpocsList) {
			String ownerLastName = "";
			if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getLastName() != null){
				ownerLastName = orgSpoc.getOwnerUser().getLastName();
			}
			orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
			orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
			orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+ownerLastName);
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
	 * @return Json
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRequirementRecommendationsByRequirementId", method = RequestMethod.POST)
	public @ResponseBody String getRequirementRecommendationsByRequirementId(@RequestParam(value="requirementId",required = false) Long requirementId,HttpServletRequest request) throws Exception {
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
        List<Candidate> candidateList = recommendationsService.getAllCandidatesByLocation(orgLocation.getId());
		
		Requirement requirement = recommendationsService.getRequirementByRequirementId(requirementId);
		
		List<Candidate> candidateListCopy = recommendationsService.getRecommendationsWithScore(candidateList,requirement);
		
		resultJson = commonUtil.toJSON(candidateListCopy);
		return resultJson;
		
	}
	
	

    /**
	 * @David Raju Sammeta
	 * 
	 * This Method   getRequirementWhichIsReleasedOrNot  is Used to check whether the requirement is released Or Not 
	 * 
	 * @param requirementId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementWhichIsReleasedOrNot", method = RequestMethod.POST)
	public @ResponseBody String getRequirementWhichIsReleasedOrNot(@RequestParam(value = "requirementId", required = false)  Long requirementId,
		HttpServletRequest request) throws Exception {
		String resultJson = null;
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		Boolean  flag = requirementService.checkRequirementIsReleasedOrNot(requirementId, orgLocation.getId());
		resultJson = toJSON(flag);
		return resultJson;
	}



	
	
	/**
	 * prasad K
	 * This method is check job id duplicate or not
	 * @param jobId
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/validateJobId",  method = RequestMethod.POST)
	    public @ResponseBody String validateJobId(@RequestParam(value="jobId", required=false) Long jobId,HttpServletRequest request) throws Exception {
		    	String result;
				String resultJson;
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Integer jobIdCount = requirementService.validateJobId(jobId,orgLocation.getId());
				if(jobIdCount>0){
					result = "duplicate";
				}
				else{
					result = "unique";
				}
				resultJson = commonUtil.toJSON(result);
				return resultJson;
		    }
	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	 @RequestMapping(value = "/validatejobName",  method = RequestMethod.POST)
	    public @ResponseBody String validatejobName(@RequestParam(value="jobName", required=false) String jobName,HttpServletRequest request) throws Exception {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Boolean validatejobName = requirementService.validateRequirementTitle(jobName,orgLocation.getId());
				return validatejobName.toString();
		    }
}
