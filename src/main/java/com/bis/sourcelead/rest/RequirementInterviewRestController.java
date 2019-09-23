package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementInterviewDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProposeTime;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionStatus;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProposeTimeService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * Prasanna Vadla
 * @author bis113
 *
 */

@RestController
public class RequirementInterviewRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	private RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementInterviewHistoryService requirementInterviewHistoryService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Autowired
	 private RequirementInterviewerService requirementInterviewerService;
	 
	 @Autowired
	 ProposeTimeService  proposeTimeService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService; 
	 
	 @Autowired
	 RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	 
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private CandidateService candidateService;
	
	@RequestMapping(value = "/app/getAllInterviewers")
	public @ResponseBody ResponseList<User> getAllApprovers(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ResponseList<User> response = new ResponseList<>();
		try {
			List<User> interviewersNewList = new ArrayList<>();
			List<User> interviewersList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
			for(User userList:interviewersList){
				interviewersNewList.add(userList);
			}
			response.setData(interviewersNewList);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		}catch(Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	@RequestMapping(value = "/app/getAllShortListCandidates")
	public @ResponseBody ResponseList<RequirementSubmissionsDTO> getAllShortListCandidates(@RequestParam(value="requirementId", required=false) Long requirementId,
			                                              @RequestParam(value="existingApplicantsIds", required=false) List<Long> existingApplicantsIds,
			                                              @RequestParam(value="isClientInterview", required=false) String isClientInterview,
			                                              HttpServletRequest request) throws Exception {
		ResponseList<RequirementSubmissionsDTO> response = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		if (requirement != null && requirement.getPrimaryRecruiterUserId() != null && user.getId() == requirement.getPrimaryRecruiterUserId().getId()) {
			user = requirement.getManagerUserId();
		}
		if(user != null) {
			try {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				List<RequirementSubmissionsDTO> shortListCandidatesList = requirementSubmissionService.getAllShortListCandidatesByOrgLocationsIdAndRequirementId(orgLocations, requirementId,existingApplicantsIds,isClientInterview,user,loginUserRole);
				for (RequirementSubmissionsDTO sub : shortListCandidatesList) {
					 StringBuilder nameBuilder = new StringBuilder();
					RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(sub.getId());
					if(StringUtils.isNotBlank(requirementSubmissionTemplateFields.getLastName())){
						sub.setCandidateName(nameBuilder.append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).toString());
					}else{
						sub.setCandidateName(requirementSubmissionTemplateFields.getFirstName());
					}
//					sub.setIsClientSubmission(sub.getRequirement().getIsClientJob());
				}
				response.setData(shortListCandidatesList);
				System.out.println("Result Size : " + shortListCandidatesList.size());
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
	
	/**To add interview
	 * @author Prasad Salina
	 * @param json
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADD_INTERVIEW')")
	@RequestMapping(value = "/app/saveInterview" , method = RequestMethod.POST)
	   public String saveInterview(@RequestParam(value="jsonData", required=false) String json, HttpServletRequest request) throws Exception {
			
			JSONObject jsonObj = new JSONObject(json);
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			
			requirementInterviewService.saveRequirementInterview(jsonObj,user,orgLocations,loginOrg);
 	    
 	    
			return "success";

  }
	
    @RequestMapping(value = "/app/updateRescheduleInterview" , method = RequestMethod.POST)
    public String updateRescheduleInterview(@RequestParam(value="jsonData", required=false) String json, HttpServletRequest request) throws Exception {
			
 	    String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			requirementInterviewService.updateRescheduleInterview(jsonObj,user,orgLocations,loginOrg);
			return "success";
   }
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_INTERVIEW','ROLE_VIEW_CANCEL_INTERVIEW')")
	@RequestMapping(value = "/app/getAllInternalInterviews" , method = RequestMethod.POST)
	 public @ResponseBody  String getAllInternalInterviews(@RequestParam(value="submissionId", required=false) Long submissionId, HttpServletRequest request) throws Exception {
		
	     OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String resultJson = null;
			
		// List<RequirementInterview> interviewsList = requirementInterviewService.getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(submissionId, orgLocations.getId());
		 List<RequirementInterviewHistory> interviewsList = requirementInterviewHistoryService.getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(submissionId, orgLocations);
		 resultJson = commonUtil.toJSON(interviewsList);
		 return resultJson;
	   }
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_INTERVIEW','ROLE_VIEW_CANCEL_INTERVIEW')")
	@RequestMapping(value = "/app/getScheduledInterviewsListForSelectedApplicants" , method = RequestMethod.POST)
	 public @ResponseBody  String getScheduledInterviewsListForSelectedApplicants(@RequestParam(value="selectedReqId", required=false) String selectedReqId, @RequestParam(value="selectedSubmissions", required=false) String selectedSubmissions, HttpServletRequest request) throws Exception {
		
	     OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	     Timezone timezone = null;
			if(orgLocations.getTimezone() != null){
				timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
			}
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 String resultJson = null;
		 List<RequirementInterviewHistory> interviewsList = new ArrayList<RequirementInterviewHistory>();
		 if(StringUtils.isNumeric(selectedSubmissions)){
			 String[] submissionIds = selectedSubmissions.split(",");
			 if(null != submissionIds && submissionIds.length > 0){
				 for(String submissionId : submissionIds){
					 List<RequirementInterviewHistory> interviews = requirementInterviewHistoryService.getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long.parseLong(submissionId), orgLocations);
					 if(interviews.size() > 0){
						 for(RequirementInterviewHistory interviewHistory : interviews){
							 if(interviewHistory.getInterviewId().getSubmissionId().getCandidate().getLastName() != null && interviewHistory.getInterviewId().getSubmissionId().getCandidate().getLastName() != "" && interviewHistory.getInterviewId().getSubmissionId().getCandidate().getLastName() != "undefined"){
								 interviewHistory.setCandidateName(interviewHistory.getInterviewId().getSubmissionId().getCandidate().getFirstName()+" "+ interviewHistory.getInterviewId().getSubmissionId().getCandidate().getLastName());
							 }else{
								 interviewHistory.setCandidateName(interviewHistory.getInterviewId().getSubmissionId().getCandidate().getFirstName());
							 }
							 
								if(null != interviewHistory.getInterviewId().getInterviewType() && "" != interviewHistory.getInterviewId().getInterviewType()){
									interviewHistory.setInterviewType(interviewHistory.getInterviewId().getInterviewType());
								}
							 	DateFormat eventstartTimrFroDashBoard = new SimpleDateFormat( "dd-MM-YYYY hh:mm aa");
								if(null != timezone.getTimeZoneId() && null != interviewHistory.getId() && null != interviewHistory.getInterviewId().getStartTime()){
									String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getStartTime());  
									String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
									Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
									currentDateInTimezone = eventstartTimrFroDashBoard.format(converteDate);
									interviewHistory.setInterviewTimeZoneStartTimeStr(currentDateInTimezone);
								}
								if(null != timezone.getTimeZoneId() && null != interviewHistory.getId() && null != interviewHistory.getInterviewId().getEndTime()){
									String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getEndTime());  
									String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
									Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
									currentDateInTimezone = eventstartTimrFroDashBoard.format(converteDate);
									interviewHistory.setInterviewTimeZoneEndTimeStr(currentDateInTimezone);
								}
								if(null != interviewHistory.getInterviewId().getTimezone()){
									interviewHistory.setTimezone(interviewHistory.getInterviewId().getTimezone().getTimeZoneId());
								}
								if(null != interviewHistory.getInterviewId().getLocation()){
									interviewHistory.setLocation(interviewHistory.getInterviewId().getLocation());
								}
								List<RequirementInterviewer> interviewerList = requirementInterviewerService.getInterviwersListByInterviewId(interviewHistory.getInterviewId().getId());
								if(interviewerList.size()>0){
									StringBuilder isb = new StringBuilder();
									for(RequirementInterviewer interviewer : interviewerList){
										isb.append(interviewer.getInterviewerUser().getFirstName()).append(", ");
									}
									interviewHistory.setInterviewersList(isb.toString().substring(0, isb.toString().length()-2));
								}
						 }
						 interviewsList.addAll(interviews);
					 }
				 }
			 }
		 }
		 resultJson = commonUtil.toJSON(interviewsList);
		 return resultJson;
	   }
	
	  
	@PreAuthorize("hasAnyRole('ROLE_VIEW_INTERVIEW','ROLE_VIEW_CANCEL_INTERVIEW')")
	@RequestMapping(value = "/app/getAllCustomerInterviews" , method = RequestMethod.POST)
	 public @ResponseBody  String getAllCustomerInterviews(@RequestParam(value="submissionId", required=false) Long submissionId,
			 @RequestParam(value = "requirementId", required = false) Long requirementId,
			 HttpServletRequest request) throws Exception {
		
	     OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String resultJson = null;
			
		 List<RequirementInterview> interviewsList = requirementInterviewService.getCustomerInterviewsBySubmissionIdAndOrgLocationsId(submissionId, orgLocations.getId(),requirementId);
		 resultJson = commonUtil.toJSON(interviewsList);
		 return resultJson;
	   }
	 
	/**
	 * @author Laxman
	 * @param json
	 * @param request
	 * @return success
	 * @throws Exception
	 */
	
	@RequestMapping(value = {"/saveProposeTime", "/app/saveProposeTime"} , method = RequestMethod.POST)
	   public String saveProposeTime(@RequestParam(value="jsonData", required=false) String json,@RequestParam(value="submissionId", required=false) Long submissionId, HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
 	        String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);
			/*String pattern = "dd-MM-yyyy";
	        SimpleDateFormat format = new SimpleDateFormat(pattern);*/
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 RequirementInterview interview = requirementInterviewService.getRequirementInterviewById(jsonObj.getString("requirementInterviewId"));
			int maxTaxRowCount = Integer.parseInt(jsonObj.getString("proposeNewTimeRowsNum"+interview.getId()+""));
			 for(int i=0;i<maxTaxRowCount;i++){
				 ProposeTime proposeTime = new ProposeTime();
					 if(null != jsonObj.getString("interviewDate"+i) && !"".equalsIgnoreCase(jsonObj.getString("interviewDate"+i))){
						 Date proposeDate = commonUtil.orgLocationDateFormat(interview.getSubmissionId().getOrgLocations().getDateFormat(),jsonObj.getString("interviewDate"+i));
						 proposeTime.setProposeDate(proposeDate);
					 }
					 if(null != jsonObj.getString("time"+i) && !"".equalsIgnoreCase(jsonObj.getString("time"+i))){
						 proposeTime.setProposeTime(jsonObj.getString("time"+i));
					 }
					 
					 proposeTime.setInterviewId(interview);
					 proposeTime.setStatus(Constants.RECORD_ACTIVE);
					 proposeTime.setCreatedDate(commonUtil.currentDate());
					 if(user != null){
					 proposeTime.setCreatedBy(user.getUserCode());
			 }
					 proposeTimeService.saveProposeTime(proposeTime);
			 }
			 
			 RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(jsonObj.getString("requirementInterviewId"));
			 requirementInterview.setInterviewStatus("Maybe");	 
			 requirementInterviewService.saveRequirementInterview(requirementInterview);
				 
			 RequirementSubmission  requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(interview.getSubmissionId().getId());
		     RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			 requirementSubmissionHistory.setFromStage(ApplicantSubmissionStatus.INTERVIEW.toString());
			 
			 requirementSubmission.setSubmissionStatus(ApplicantSubmissionStatus.INTERVIEW_TENTATIVE);
			 //Applicant to created by interviewer send Email
			 if(orgLocations != null){
			requirementInterviewService.applicantInterviewProposeTimeEmailToInterviewInitiator(jsonObj.getString("requirementInterviewId"),orgLocations,loginOrg,user);	 
			 }else{
			   requirementInterviewService.applicantInterviewProposeTimeEmailToInterviewInitiator(jsonObj.getString("requirementInterviewId"),requirementSubmission.getOrgLocations(),loginOrg,user);	 
			 }
			 if(user != null){
			 requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user, requirementSubmissionHistory, requirementSubmission.getCreatedBy());
			 }
			return "success";

  }
	
	/**
	 * @author Laxman 
	 * @param interviewId
	 * @param request
	 * @return resultJson
	 * @throws Exception
	 */
	@RequestMapping(value = "/app/getProposeTime" , method = RequestMethod.POST)
	 public @ResponseBody  String getProposeTime(@RequestParam(value="interviewId", required=false) Long interviewId, @RequestParam(value="locationId", required=false) Long locationId,HttpServletRequest request) throws Exception {
		 String resultJson = null;
		 List<ProposeTime> proposeTimeList = proposeTimeService.getProposeTimeByInterViewId(interviewId);
		 OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);
		 //SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		 for(ProposeTime pst:proposeTimeList){
			if(null != pst.getProposeDate()){
				pst.setProposeDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),pst.getProposeDate()));	
				pst.setProposeTime(pst.getProposeTime());
			}
		 }
		 resultJson = commonUtil.toJSON(proposeTimeList);
		 return resultJson;
	   }
	
	/**
	 * @author Laxman 
	 * @param json
	 * @param proposeTimeId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/app/acceptProposeTime" , method = RequestMethod.POST)
	    public String acceptProposeTime(@RequestParam(value="jsonData", required=false) String json,@RequestParam(value="proposeTimeId", required=false) Long proposeTimeId,
	    		@RequestParam(value="proposeDate", required=false) String proposeDate,@RequestParam(value="proposeTime", required=false) String proposeTime,HttpServletRequest request) throws Exception {
				
	 	    String json1 = "{" + json + "}";
				JSONObject jsonObj = new JSONObject(json1);
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Org loginOrg = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				requirementInterviewService.acceptProposeTimeBytProposeTimeId(proposeDate,proposeTime,user,orgLocations,loginOrg,proposeTimeId);
				return "success";
	   }
	 
	 
	 /*@RequestMapping(value = "/ws/acceptOrRejectInterview")
	 @ResponseBody  String acceptOrRejectInterview(@RequestParam(value="interviewStatus", required=false) String interviewStatus,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
			 HttpServletRequest request) throws Exception {
		 
		 RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		 
		 String message = " ";
		 if(requirementInterview.getInterviewStatus() == null && !("").equals(requirementInterview.getInterviewStatus())){
			 requirementInterview.setInterviewStatus(interviewStatus);
			 requirementInterviewService.saveRequirementInterview(requirementInterview);
			 if(interviewStatus.equalsIgnoreCase("Attending")){
				 message = "You have Accepted the Interview.";
			 }
			 if(interviewStatus.equalsIgnoreCase("Not Attending")){
				 message = "You have Rejected the Interview.";
			 }
			 if(interviewStatus.equalsIgnoreCase("Maybe")){
				 message = "Not sure for the Interview.";
			 }
		return message;
	 }	*/
	 
	 @RequestMapping(value = "/app/sendMailToapplicant")
	 @ResponseBody String sendMailToapplicant(@RequestParam(value="submissionId", required=false) String submissionId,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
				@RequestParam(value = "interviewHistoryId", required = false) String interviewHistoryId,
			 HttpServletRequest request) throws Exception {
		 
		 
		 RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
		 
		 RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		 
		 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		 OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 requirementInterviewService.sendEmailToApplicants(requirementSubmission.getCandidate(),orgLoc, requirementSubmission, loginUser,org, requirementInterview);
		 
		 RequirementInterviewHistory interviewHistory =  requirementInterviewHistoryService.getRequirementInterviewHistoryById(Long.parseLong(interviewHistoryId));
		 interviewHistory.setNotificationToApplicant(Constants.YES);
		 requirementInterviewHistoryService.storeRequirementInterviewHistory(interviewHistory);
		 
		return "success";
	 }		
	 @RequestMapping(value = "/app/sendMailToVendor")
	 @ResponseBody String sendMailToVendor(@RequestParam(value="submissionId", required=false) String submissionId,
				@RequestParam(value = "requirementInterviewId", required = false) String requirementInterviewId,
				@RequestParam(value = "interviewHistoryId", required = false) String interviewHistoryId,
			 HttpServletRequest request) throws Exception {
		 
		 
		 RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(Long.parseLong(submissionId));
		 
		 if(requirementSubmission.getSourceSubmission() != null){
      	   RequirementSubmission parentSubmission = requirementSubmissionService.getRequirementSubmissionById(requirementSubmission.getSourceSubmission().getId());
	        	   parentSubmission.setSubmissionStatus(ApplicantSubmissionStatus.CLIENT_INTERVIEW);
	        	   requirementSubmissionService.storeRequirementSubmission(parentSubmission);
         }
		 
		 RequirementInterview requirementInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewId);
		 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");

		 requirementInterviewService.sendEmailToVendors(requirementSubmission.getCandidate(), requirementSubmission, loginUser,org, requirementInterview,orgLocations);
	 
		 RequirementInterviewHistory interviewHistory =  requirementInterviewHistoryService.getRequirementInterviewHistoryById(Long.parseLong(interviewHistoryId));
		 interviewHistory.setNotificationToSpoc(Constants.YES);
		 requirementInterviewHistoryService.storeRequirementInterviewHistory(interviewHistory);
		 
		 RequirementInterviewHistory vendorInterviewHistory = new RequirementInterviewHistory();
  	     vendorInterviewHistory.setInterviewId(requirementInterview);
  	     vendorInterviewHistory.setSubmissionId(requirementSubmission.getSourceSubmission());
  	     vendorInterviewHistory.setOrgLocationId(requirementSubmission.getVendorLocationId());
         vendorInterviewHistory.setInterviewSourceType(Constants.CLIENT_INTERVIEW);
         vendorInterviewHistory.setNotificationToApplicant(Constants.NO);
         vendorInterviewHistory.setNotificationToSpoc(Constants.NO);
         vendorInterviewHistory.setStatus(Constants.RECORD_ACTIVE);
         vendorInterviewHistory.setCreatedDate(new Date());
         vendorInterviewHistory.setCreatedBy(loginUser.getUserCode());
         vendorInterviewHistory.setUpdatedDate(new Date());
         vendorInterviewHistory.setUpdatedBy(loginUser.getUserCode());
         requirementInterviewHistoryService.storeRequirementInterviewHistory(vendorInterviewHistory);
		 
		 
		return "success";
	 }		
	 
	 
	 
	 @RequestMapping(value = "/app/getInterviewSlotDates")
		public ResponseList<String> getInterviewSlotDates(@RequestParam(value = "applicantSubmissionIds", required = false) String applicantSubmissionIds,
				@RequestParam(value = "interviewerIds", required = false) String interviewerIds, HttpServletRequest request) throws Exception {
		 	ResponseList<String> response = new ResponseList<>();
		 	try {
					OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					List<String> interviewDatesList = new ArrayList<>();
					List<Long> interviewUserIdList = new ArrayList<>();
					List<Long> submissionsList = new ArrayList<>();
					List<Date> interviewerDatesList = new ArrayList<>();
					List<Date> candidatesDatesList = new ArrayList<>();
					Set<Date> datesList = new LinkedHashSet<>();
					
					if(interviewerIds != null && !interviewerIds.isEmpty()) {
						String[] interviewerUserIds = interviewerIds.split(",");
						for(int i=0;i<interviewerUserIds.length;i++) {
							interviewUserIdList.add(Long.valueOf(interviewerUserIds[i]));
						}
						interviewerDatesList = requirementInterviewService.getInterviewDatesForInterviewrsInterviewSlots(interviewUserIdList, loginOrgLocations.getId());
					}
					
					if(applicantSubmissionIds != null && !applicantSubmissionIds.isEmpty()) {
						String[] submissionIds = applicantSubmissionIds.split(",");
						for(int i=0;i<submissionIds.length;i++) {
							submissionsList.add(Long.valueOf(submissionIds[i]));
						}
						candidatesDatesList = requirementInterviewService.getInterviewDatesForCandidateInterviewSlots(submissionsList);
					}
					
					if(interviewerDatesList != null && interviewerDatesList.size() > 0) {
						datesList.addAll(interviewerDatesList);
					}
					
					if(candidatesDatesList != null && candidatesDatesList.size() > 0) {
						datesList.addAll(candidatesDatesList);
					}
					
					Timezone timezone = null;
					if(loginOrgLocations.getTimezone() != null){
						timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
					}
					if(datesList != null && datesList.size() > 0) {
						for(Date date : datesList) {
							    String converted_dateStr =  CalendarUtil.getDateStrForGivenTimeZone(timezone.getTimeZoneId(), date, "yyyy/MM/dd");
							    if(StringUtils.isNotEmpty(converted_dateStr)) {
							    	 interviewDatesList.add(converted_dateStr);
							    }
						}
					}
					response.setData(interviewDatesList);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
			}catch(Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
			return response;
		}
		
	 
	 
	 @RequestMapping(value = "/app/getInterviewerAndApplicantsDetails")
		public @ResponseBody Response<RequirementInterviewer> getInterviewerAndApplicantsDetails(
				@RequestParam(value = "applicantSubId", required = false) String applicantSubId,
				@RequestParam(value = "interviewerIds", required = false) String interviewerIds,
				@RequestParam(value = "date", required = false) String date,
				HttpServletRequest request) throws Exception {
		 	Response<RequirementInterviewer> response = new Response<>();
			OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				try {
					Timezone timezone = null;
					if(loginOrgLocations.getTimezone() != null){
						timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
					}
					//SimpleDateFormat simpleDateFormat = loginOrgLocations.getLocFullFormDateFormatter();
					Date startUTCDate = null;
					Date endUTCDate = null;
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
					Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),date);
					
					String startTimeTS = Constants.TZ_DF.format(startTime); 
					String endTimeTS = END_TZ_DF.format(startTime);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startTimeTS);
					String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
						 startUTCDate = TZ_DF_TS.parse(startDateString);
						 endUTCDate = TZ_DF_TS.parse(endDateString);
					
					RequirementInterviewer reqInterviwer = new RequirementInterviewer();
					//interviewer interviews list	 
					List<Long> interviewersList = new ArrayList<>();
					
					List<RequirementInterviewer> selectedInterviewerList = new ArrayList<>();
					if(!interviewerIds.equalsIgnoreCase("null") && !interviewerIds.equalsIgnoreCase("undefined") ){
						
						for (String interviewId : interviewerIds.split(","))
							interviewersList.add(Long.valueOf(interviewId));
					
						selectedInterviewerList =  requirementInterviewerService.getinterviewListByOrgLocationAndUserId(loginOrgLocations,interviewersList,startUTCDate,endUTCDate);
						for (RequirementInterviewer requirementInterviewer : selectedInterviewerList) {
							User interviewrUser = userService.getUserById(requirementInterviewer.getInterviewerUser().getId());
							if(interviewrUser.getLastName() !=null){
								requirementInterviewer.setInterviewerName(interviewrUser.getFirstName()+" "+interviewrUser.getLastName());
							}else{
								requirementInterviewer.setInterviewerName(interviewrUser.getFirstName());
							}
							RequirementInterview reqInterview = requirementInterviewService.getRequirementInterviewById(requirementInterviewer.getRequirementInterview().getId().toString());
							//requirementInterviewer.setInterviewStartTime(reqInterview.getStartTime().toString());
							//requirementInterviewer.setInterviewEndTime(reqInterview.getEndTime().toString());
							DateFormat eventstartTimrFroDashBoard = new SimpleDateFormat( "hh:mm aa");
							reqInterview.setCandidateName(reqInterview.getSubmissionId().getCandidate().getFirstName());
							if(null != timezone.getTimeZoneId() && null != reqInterview.getId() && null != reqInterview.getStartTime()){
								String currentTimeStr = Constants.TZ_DF.format(reqInterview.getStartTime());  
								String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
								Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
								currentDateInTimezone = eventstartTimrFroDashBoard.format(converteDate);
								requirementInterviewer.setInterviewStartTime(currentDateInTimezone);
							}
							
							if(null  != timezone.getTimeZoneId() && null != reqInterview.getId() && null != reqInterview.getEndTime()){
								String currentTimeStr = Constants.TZ_DF.format(reqInterview.getEndTime());  
								String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
								Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
								String eventstartTimrFroDashBoardStr = eventstartTimrFroDashBoard.format(converteDate);
								requirementInterviewer.setInterviewEndTime(eventstartTimrFroDashBoardStr);
							}
							requirementInterviewer.setRequirementTitle(reqInterview.getSubmissionId().getRequirement().getJobTitle());
							
							if(reqInterview.getInterviewType()!=null){
								requirementInterviewer.setInterviewType(reqInterview.getInterviewType());
							}
							else{
								requirementInterviewer.setInterviewType("--");
							}
						}
					}
					
					reqInterviwer.setInterviewerList(selectedInterviewerList);
					//applicantslist
					List<RequirementInterview> requirementInterview = new ArrayList<>();
					if(!applicantSubId.equalsIgnoreCase("null") && !applicantSubId.equalsIgnoreCase("undefined")){
						List<String> submissionsList = new ArrayList<String>(Arrays.asList(applicantSubId.split(",")));
						List<Long> submissionsIds = new ArrayList<>();
						for (String subId : submissionsList){
							submissionsIds.add(Long.valueOf(subId));
						}
						
						List<Long> candidateIds  = requirementSubmissionService.getcandidateListBySubmissionId(submissionsIds,loginOrgLocations.getId());
						
						List<Long> submissionsIdList = requirementSubmissionService.getSubmissionIdByCandidateId(candidateIds,loginOrgLocations.getId());
						
						
						requirementInterview = requirementInterviewService.getRequirementInterviewsBySubmissionAndOrgLocId(submissionsIdList,loginOrgLocations.getId(),startUTCDate,endUTCDate);
						for(RequirementInterview reqInterview : requirementInterview){
							DateFormat eventstartTimrFroDashBoard = new SimpleDateFormat( "hh:mm aa");
							reqInterview.setCandidateName(reqInterview.getSubmissionId().getCandidate().getFirstName());
							if(null != timezone.getTimeZoneId() && null != reqInterview.getId() && null != reqInterview.getStartTime()){
								String currentTimeStr = Constants.TZ_DF.format(reqInterview.getStartTime());  
								String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
								Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
								currentDateInTimezone = eventstartTimrFroDashBoard.format(converteDate);
								reqInterview.setInterviewStartDateTimeStr(currentDateInTimezone);
							}
							
							
							if(null  != timezone.getTimeZoneId() && null != reqInterview.getId() && null != reqInterview.getEndTime()){
								String currentTimeStr = Constants.TZ_DF.format(reqInterview.getEndTime());  
								String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
								Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
								String eventstartTimrFroDashBoardStr = eventstartTimrFroDashBoard.format(converteDate);
								reqInterview.setInterviewEndtDateTimeStr(eventstartTimrFroDashBoardStr);
							}
							else{
								reqInterview.setInterviewEndtDateTimeStr("");
							}
							/*reqInterview.setInterviewStartDateTimeStr(reqInterview.getStartTime().toString());
							reqInterview.setInterviewEndtDateTimeStr(reqInterview.getEndTime().toString());*/
							if(reqInterview.getInterviewType()!=null){
								reqInterview.setInterviewType(reqInterview.getInterviewType());
							}
							else{
								reqInterview.setInterviewType("--");
							}
							reqInterview.setRequirementTitle(reqInterview.getSubmissionId().getRequirement().getJobTitle());
						}
					}
					reqInterviwer.setApplicantsList(requirementInterview);
					response.setObject(reqInterviwer);
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
	 
	 @RequestMapping(value = {"/app/getInterviewsListBySubmission","/rest/getInterviewsListBySubmission"}, method = RequestMethod.POST)
		public ModelAndView getInterviewsListBySubmission(@RequestParam(value = "submissionId", required = false) Long submissionId,
							@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception{
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			  
			ModelAndView model = new ModelAndView();
			try {
				model.addObject("candidate", candidateService.getCandidateByCandidateId(candidateId));
				model.addObject("interviewsList", requirementInterviewService.getRequirementInterviewsBySubmissionIdAndOrgLocationsId(submissionId, orgLocations));
				model.setViewName("requirement/requirementSubmissionInterviewsList");
				model.addObject("sourceleadUrl", sourceleadUrl);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return model;
		}
	 
	 
	 @RequestMapping(value = "/app/saveInterviewStatusByInterviewId" , method = RequestMethod.POST)
		   public Response<String> saveInterviewStatusByInterviewId(RequirementInterviewDTO requirementInterviewDTO, HttpServletRequest request) throws Exception {
		 		Response<String> response = new Response<>();
		 		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 		if(user != null) {
					try {
						RequirementInterview ri = requirementInterviewService.getRequirementInterviewById(requirementInterviewDTO.getId());
						if(ri != null) {
							if(StringUtils.isNotEmpty(requirementInterviewDTO.getInterviewScore())) {
								ri.setInterviewScore(Integer.parseInt(requirementInterviewDTO.getInterviewScore()));
							}
							if(StringUtils.isNotEmpty(requirementInterviewDTO.getInterviewPhase())) {
								ri.setInterviewPhase(requirementInterviewDTO.getInterviewPhase());
								
								if(requirementInterviewDTO.getInterviewPhase().equalsIgnoreCase("Rejected")) {
									saveSubmissionStatusForRejectInterview(ri.getSubmissionId(), user, orgLocations);
								}else if(requirementInterviewDTO.getInterviewPhase().equalsIgnoreCase("Selected")) {
									saveSubmissionStatusForSelectedInterview(ri.getId(), ri.getSubmissionId(), user, orgLocations);
								}
							}
							if(StringUtils.isNotEmpty(requirementInterviewDTO.getInterviewComment())) {
								ri.setInterviewComment(requirementInterviewDTO.getInterviewComment());
							}
							ri.setUpdatedBy(user.getUserCode());
							ri.setUpdatedDate(new Date());
							requirementInterviewService.saveRequirementInterview(ri);
							
							response.setObject("success");
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						}else {
							response.setResponse(Result.AWKWARD.name());
							response.setResponseMessage(Result.AWKWARD.getValue());
						}
					}catch (Exception e) {
						e.printStackTrace();
						response.setResponse(Result.AWKWARD.name());
						response.setResponseMessage(Result.AWKWARD.getValue());
					}
		 		}else {
		 			response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		 		}
				return response;
	}
	 
	 
	public void saveSubmissionStatusForRejectInterview(RequirementSubmission submission, User user, OrgLocations orgLocations) throws Exception{
		//RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
		// to update submission history
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus().toString());
		requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.REJECTED.toString());
		// to update Submission status
		submission.setPreviousSubmissionStatus(submission.getSubmissionStatus());
		submission.setRejectedReason("Interview Rejected");
		submission.setRejectedCategoryType("Interview Rejection");
		submission.setSubmissionStatus(ApplicantSubmissionStatus.REJECTED);
		submission.setUpdatedBy(user.getUserCode());
		StringBuilder sb = new StringBuilder();
		submission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		submission.setUpdatedDate(new Date());
		requirementSubmissionService.storeRequirementSubmission(submission);
		if(!orgLocations.getId().equals(submission.getCandidate().getOrgLocationId().getId()) && submission.getSourceSubmission() != null &&  !submission.getCandidate().getCandidateType().equalsIgnoreCase("Professional")) {
			requirementSubmissionService.sendRejectionMailToVendorContact(user, submission.getRejectedReason(), submission.getId(), orgLocations);
		}
		requirementSubmissionHistoryService.updateRequirementHistory(submission, user, requirementSubmissionHistory, submission.getCreatedBy());
	}
	
	public void saveSubmissionStatusForSelectedInterview(Long interviewId, RequirementSubmission submission, User user, OrgLocations orgLocations) throws Exception {
		RequirementInterview ri = requirementInterviewService.getLatestInterviewBySubmissionId(submission.getId());
		if(ri.getId().equals(interviewId)) {
			// to update submission history
			RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
			requirementSubmissionHistory.setFromStage(submission.getSubmissionStatus().toString());
			requirementSubmissionHistory.setToStage(ApplicantSubmissionStatus.SELECTED_ON_INTERVIEW.toString());
			// to update Submission status
			submission.setPreviousSubmissionStatus(submission.getSubmissionStatus());
			submission.setSubmissionStatus(ApplicantSubmissionStatus.SELECTED_ON_INTERVIEW);
			submission.setUpdatedBy(user.getUserCode());
			StringBuilder sb = new StringBuilder();
			submission.setUpdatedUser(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			submission.setUpdatedDate(new Date());
			requirementSubmissionService.storeRequirementSubmission(submission);
			requirementSubmissionHistoryService.updateRequirementHistory(submission, user, requirementSubmissionHistory, submission.getCreatedBy());
		}
	}
	 
}
