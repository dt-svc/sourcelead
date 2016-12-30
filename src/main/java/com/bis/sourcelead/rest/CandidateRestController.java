package com.bis.sourcelead.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.SourceleadResumeParser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
/**
 * Handles requests for the Candidate service.
 * 
 * @author Prasad K
 * @date 30th June 2016
 *
 */

@RestController
public class CandidateRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private CandidateResumesService  candidateResumesService;
	
	@Autowired
	private CandidateSkillsService candidateSkillsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	RequirementOffersService requirementOffersService;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService; 
	
	@Autowired
	RequirementInterviewHistoryService requirementInterviewHistoryService;
	
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService; 
	
	@Autowired
	private SourceleadResumeParser sourceleadResumeParser;
	

	/**
	 * @author prasad k
	 * @since  sep 1 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save candidate in candidate table.
	 * 1.internalley store/save in candidateEducation in candidateEducation table .
	 * 2.internalley store/save in candidateSkills in candidateSkills table .
	 * 3.internalley store/save in candidateResumes in candidateResumes table .
	 * 4.internalley store/save in candidateEmployers in candidateEmployers table .
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeCandidate method
	 * @param orgLocations This is the second paramter to storeCandidate method
	 * @param user This is the third paramter to storeCandidate method
	 * @param multiFile This is the fourth paramter to storeCandidate method
	 * 
	 * @return Candidate 
	 * 
	 * @throws NullPointer
	*/
	
	@PreAuthorize("hasAnyRole('ROLE_CREATE_CANDIDATE','ROLE_VIEW_CANDIDATE','ROLE_VIEW_EDIT_CANDIDATE_EDUCATION')") 
	@RequestMapping(value = "/storeCandidate", method = RequestMethod.POST)
	public @ResponseBody String storeCandidate(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateService.storeCandidate(jsonObj,orgLocation,user,multiFile);
		
		if(candidate.getCandidateType().equalsIgnoreCase("Candidate")){
			StringBuilder candidateCreateMsg = new StringBuilder();
		    SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg", candidateCreateMsg.append("Success !! ").append(candidate.getFirstName()).append(" Candidate Added").toString());
		}
		else{
			StringBuilder consultantCreateMsg = new StringBuilder();
			SourceLeadSessionManager.setSessionAttribute(request, "consultantCreateMsg", consultantCreateMsg.append("Success !! ").append(candidate.getFirstName()).append(" Consultant Added").toString());
		}
		return "success";
	}
	
	@RequestMapping(value = "/parseResume", method = RequestMethod.POST)
	public @ResponseBody String parseResume(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request,
			 @RequestParam(value="locationMapFile", required=false) CommonsMultipartFile locationMapFileData) throws Exception {
		
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
	        multiFile = request.getFile(itrator.next());
		}
		String resumeDetails = null;
		if(multiFile != null){
			resumeDetails = sourceleadResumeParser.getCandidateDetailsFromResume(multiFile.getBytes(),request);
		}
		return resumeDetails;
	}
	 /**
	  * To  validate user email
	  * @param EmailId
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/validateCandidateEmail" , method = RequestMethod.POST)
	 public @ResponseBody  String validateCandidateEmail(@RequestParam(value="email", required=false) String email,HttpServletRequest request) throws Exception {
		 	
		 	String result = null;
		 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	Candidate candidate = candidateService.findByCandidateEmail(email,orgLocation.getId());
		 	
		 	if (candidate != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }
	 
	 @RequestMapping(value = "/validateSkillName" , method = RequestMethod.POST)
	 public @ResponseBody  Boolean validateSkillName(@RequestParam(value="skillName", required=false) String skillName, @RequestParam(value="candidateId", required=false) String candidateId, HttpServletRequest request) throws Exception {
		 	Boolean flag = false;
		 	CandidateSkills candidateSkills = candidateSkillsService.getSkillsByCandidateIdSkillName(candidateId,skillName);
		    if(candidateSkills != null){
		    	flag = candidateSkillsService.validateSkillName(skillName);
		    }
			return flag;
	   }
	 
	 
	 @RequestMapping(value = "/getCandidatesList", method = RequestMethod.POST)
		public @ResponseBody String getCandidatesList(
				@RequestParam(value = "requirementId", required = false) Long requirementId,
				@RequestParam(value = "candidateType", required = false) String candidateType, HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			List<Candidate> candidatesList = candidateService.getCandidatesByOrgLocationID(orgLocations.getId(),requirementId,candidateType);
			
			resultJson = toJSON(candidatesList);
			return resultJson;
		}
	 
	 /**
		 * @author sarath kumar
		 * @since  Dec 1 2016
		 * @version 0.0.1
		 * 
		 * 
		 * @throws NullPointer
		*/
	 @RequestMapping(value = "/checkCandidateState", method = RequestMethod.POST)
	 public @ResponseBody String checkCandidateState(
				@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request) throws Exception {
			
		    String resultJson = null;
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			Boolean isCandidateActive = false;
			if(candidate != null){
				 if(candidate.getStatus() == 1){
					 isCandidateActive = true;
				 }
			}
			resultJson = toJSON(isCandidateActive);
			return resultJson;
		}
	 
	 
	 /**
	  * @author Rajashekar
	  * @param candidateId
	  * @param request
	  * @return string
	  * @throws Exception
	  */
	 @RequestMapping(value = "/downloadCandidateResume" , method = RequestMethod.GET)
	 public @ResponseBody  void downloadCandidateResume(@RequestParam(value="candidateId", required=false) Long candidateId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		 	CandidateResumes candidateResume = candidateResumesService.getCandidateLatesResumes(candidateId, 1);
		 	
		 	  response.setContentType("application/octet-stream");
		 	 
		 	  StringBuilder fileName = new StringBuilder();
		      response.setHeader("Content-Disposition", fileName.append("attachment; filename=\"").append(candidateResume.getFileName()).append("\"").toString());
			    
				InputStream inputStream = new ByteArrayInputStream(candidateResume.getDocFile());
				try {
					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();

				} catch (FileNotFoundException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
				} 
			/*byte[] pdfData = candidateResume.getDocFile();
			FileOutputStream fileOuputStream =null;
			try { 
			     fileOuputStream = new FileOutputStream(candidateResume.getFileName()); 
			    fileOuputStream.write(pdfData);
			 } finally {
			    fileOuputStream.close();
			 }
			String mimeType = "application/msword";
	        if (mimeType == null) {        
	            mimeType = "application/octet-stream";
	        }              
	        response.setContentType(mimeType);
	        String headerKey = "Content-Disposition";
	         String headerValue = String.format("attachment; filename=\"%s\"", candidateResume.getFileName());
	         response.setHeader(headerKey, headerValue);
	        InputStream inputStream = new ByteArrayInputStream(candidateResume.getDocFile());
			try {
				OutputStream outStream = response.getOutputStream();
	            
	            byte[] buffer = new byte[500];
	            int bytesRead = -1;
	             
	            while ((bytesRead = inputStream.read(buffer)) != -1) {
	                outStream.write(buffer, 0, bytesRead);
	            }
	             
	            inputStream.close();
	            outStream.close();   
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
	   }
	 
	 
	 
	 
	 @RequestMapping(value = "/getUserByRoleId", method = RequestMethod.POST)
		public @ResponseBody String getUserByRoleId(
				@RequestParam(value = "roleId", required = false) Long roleId, HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			List<User> userList = userService.getUsersForConsultantsByUserRoleIdAndOrgLocationId(roleId, orgLocations.getId());
			
			resultJson = toJSON(userList);
			return resultJson;
		}
	 
	 
	 @RequestMapping(value = "/getAllRoleUsers", method = RequestMethod.POST)
		public @ResponseBody String getAllRoleUsers(HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
			
			List<User> userList = userService.getUsersForAddAsConsultant(org,orgLocations.getId());
			for (User user : userList) {
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(userRole != null){
					user.setUserType(userRole.getRoles().getDisplayName());
				}
			}
			resultJson = toJSON(userList);
			return resultJson;
		}
	 
	 
	 @RequestMapping(value = "/getConsultantUserDetailsById",  method = RequestMethod.POST)
	    public @ResponseBody String getConsultantUserDetailsById(@RequestParam(value="userId", required=false) Long userId) {
			
		   ArrayList<String> userList = new ArrayList<String>();
		   UserRole userRole = null;
		   User user = null;
			try {
				user = userService.getUserById(userId);
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			
			if(null != user)
			{
				userList.add(user.getFirstName());
				userList.add(user.getLastName());
				userList.add(user.getEmail());
				userList.add(user.getMobilePhone());
				
			}
			
			return commonUtil.toJSON(userList);
			
		}
	 
	 @PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	 @RequestMapping(value = "/getTeams", method = RequestMethod.POST)
		public @ResponseBody String getTeams(HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Team> teamList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
			resultJson = toJSON(teamList);
			return resultJson;
		}
	 
	 
	/**
	 * @author Laxman
	 * @ this method is uses for get the requirement History by candidate
	 * @param candidateId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementHistoryListByCandidateId")
	public String getRequirementHistoryListByCandidateId(@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 List<RequirementSubmission> requirementSubmission =requirementSubmissionService.getRequirementHistoryListByCandidateIdAndOrgLocationID(candidateId, loginOrgLocation.getId());
		 for(RequirementSubmission req :requirementSubmission ){
			 req.setPositionType(req.getRequirement().getJobTitle());
			 req.setCountry(req.getRequirement().getCountry());
			 req.setState(req.getRequirement().getState());
			 req.setCity(req.getRequirement().getCity());
			 req.setRate(req.getRequirement().getRate());
			 req.setCreatedDateStr(Constants.DMY.format(req.getCreatedDate()));
			 if(req.getRequirement().getManagerUserId().getLastName() != null){
				 req.setOwnerName(req.getRequirement().getManagerUserId().getFirstName() +" "+req.getRequirement().getManagerUserId().getLastName());
			 }else{
				 req.setOwnerName(req.getRequirement().getManagerUserId().getFirstName());
			 }
			
			 if(req.getRequirement().getSpocId() != null){
				 if(!"".equalsIgnoreCase(req.getRequirement().getSpocId().getSpocUser().getLastName()) && req.getRequirement().getSpocId().getSpocUser().getLastName() != null ){
					 req.setClientName(req.getRequirement().getSpocId().getSpocUser().getFirstName() +""+req.getRequirement().getSpocId().getSpocUser().getLastName());
				 }else{
					 req.setClientName(req.getRequirement().getSpocId().getSpocUser().getFirstName());
				 }
			 }
			
			 
		 }
		 resultJson = commonUtil.toJSON(requirementSubmission);
		 return resultJson;
	}	
	
	
	/**
	 * @author Laxman
	 * this method is used for candidate submitte related resumes list
	 * @param candidateId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRequirementHistoryDetailsByCandidateId")
	public String getRequirementHistoryDetailsByCandidateId(@RequestParam(value = "candidateId", required = false) Long candidateId,
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "submissionId", required = false) Long submissionId,HttpServletRequest request) throws Exception {
		 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		 String resultJson;
		 
		 List<RequirementSubmissionHistory>  requirementSubmissionhistory =  requirementSubmissionHistoryService.getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(candidateId, loginOrgLocation.getId(), requirementId, submissionId);
		 for(RequirementSubmissionHistory rsh:requirementSubmissionhistory){
			 RequirementOffers ros =  requirementOffersService.getRequirementOfferSubmissionById(rsh.getSubmissionId().getId());
			 if(ros != null){
				 rsh.setOfferResumeid(ros.getId());
			 }
			 if(rsh.getSubmissionId().getCandidateResumes() != null){
				 rsh.setResumeid(rsh.getSubmissionId().getCandidateResumes().getId());
				 rsh.setFileName(rsh.getSubmissionId().getCandidateResumes().getFileName());
			 }
			 if(!"".equalsIgnoreCase(rsh.getSubmissionId().getOrgLocations().getOrg().toString()) && rsh.getSubmissionId().getOrgLocations().getOrg() != null){
				 rsh.setOrgLocationId(rsh.getSubmissionId().getOrgLocations().getOrg().getOrgCode());
			 }
			 rsh.setToStage(rsh.getToStage());
			 rsh.setSubmissionStage(rsh.getSubmissionId().getSubmissionStatus().toString());
			 rsh.setRequirementReson(rsh.getSubmissionId().getRejectedReason());
			 
			 StringBuilder userName  = new StringBuilder();
			 if(rsh.getSubmissionId().getCandidate().getLastName() != null){
				 rsh.setUserName(userName.append(rsh.getSubmissionId().getCandidate().getFirstName()).append(" ").append(rsh.getSubmissionId().getCandidate().getLastName()).toString());	 
			 }else{
				 rsh.setUserName(userName.append(rsh.getSubmissionId().getCandidate().getFirstName()).toString());
			 }

			 // user related data is getting
			 User user = userService.getUserDetailsByUserCode(rsh.getCreatedBy());
			 StringBuilder  loginUserName = new StringBuilder();
			 if(user.getLastName() != null){
				 rsh.setLoginUserName(loginUserName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			 }else{
				 rsh.setLoginUserName(loginUserName.append(user.getFirstName()).toString());
			 }
			 
			 //get the inteviewerName
				 if(rsh.getInterviewerId() != null && rsh.getInterviewerId().getId() != null){
					 RequirementInterviewer requirementInterviewer =requirementInterviewerService.getInterviwerstByInterviewId(rsh.getInterviewerId().getId());
					 StringBuilder interviewerName = new StringBuilder();
						 if(!"".equalsIgnoreCase(requirementInterviewer.getInterviewerUser().getLastName()) && requirementInterviewer.getInterviewerUser().getLastName() != null){
							 rsh.setInterviewerName(interviewerName.append(requirementInterviewer.getInterviewerUser().getFirstName()).append(requirementInterviewer.getInterviewerUser().getLastName()).toString());	 
						 }else{
							 rsh.setInterviewerName(interviewerName.append(requirementInterviewer.getInterviewerUser().getFirstName()).toString());
						 
					 }
				 }
				 
				 //get the client side offer history
				List<RequirementOfferHistory> requirementOfferHistory = requirementOfferHistoryService.getRequirementInternalAndIsClientOffersHistoryBySubmissionIdAndOrgLocationsId(submissionId, loginOrgLocation.getId());
				for(RequirementOfferHistory roh : requirementOfferHistory){
					StringBuilder offerClientName = new StringBuilder();
					user = userService.getUserDetailsByUserCode(roh.getCreatedBy());
					if(user.getLastName() != null){
						roh.setClientName(offerClientName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					 }else{
						 roh.setClientName(offerClientName.append(user.getFirstName()).toString());
					 }
					//this logic is date conversion
					long diff = commonUtil.currentDate().getTime() - roh.getCreatedDate().getTime();
				    Object object =  getCnadidateHistoryTimeConversion(diff,roh);
				    roh = (RequirementOfferHistory)object;
					roh.setOfferResumeId(roh.getOfferId().getId());
				    roh.setOfferSourceType(roh.getOfferSourceType());
					roh.setNotifyToVendor(roh.getNotifyToVendor());
					rsh.setRequirementOfferHistoryList(requirementOfferHistory);
				}
				
					
					
			 //get the client side interview interviewHistory 
				 List<RequirementInterviewHistory> requirementInterviewHistory = requirementInterviewHistoryService.getClientInterviewsHistoryBySubmissionIdAndOrgLocationsId(rsh.getSubmissionId().getId(),loginOrgLocation.getId());
				 for(RequirementInterviewHistory rh:requirementInterviewHistory){
					 StringBuilder clientName = new StringBuilder();
					 rh.setInterviewSourceType(rh.getInterviewSourceType());
					 user = userService.getUserDetailsByUserCode(rh.getCreatedBy());
					 if(user.getLastName() != null){
						rh.setClientName(clientName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					 }else{
						 rh.setClientName(clientName.append(user.getFirstName()).toString());
					 }
					 
				     long diff = commonUtil.currentDate().getTime() - rh.getCreatedDate().getTime();
				     Object object =  getCnadidateHistoryTimeConversion(diff,rh);
					 rh = (RequirementInterviewHistory)object;
					
					if(rh.getInterviewId() != null){
						if(rh.getInterviewId().getDateOfInterview() != null){
							rh.setDateOfInterviewStr(Constants.DMY.format(rh.getInterviewId().getDateOfInterview()));
						    }
						    if(rh.getInterviewId().getStartTime() != null){
						    	StringBuilder startTime = new StringBuilder(); 
						    	 String hours = Integer.toString(rh.getInterviewId().getStartTime().getHours());
						    	 String minutes = Integer.toString(rh.getInterviewId().getStartTime().getMinutes());
						    	 rh.setStartTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(startTime.append(hours).append(":").append(minutes).toString()));
						    }
						    if(rh.getInterviewId().getEndTime() != null){
						    	StringBuilder endTime = new StringBuilder(); 
						    	 String hours = Integer.toString(rh.getInterviewId().getEndTime().getHours());
						    	 String minutes = Integer.toString(rh.getInterviewId().getEndTime().getMinutes());
						    	 rh.setEndTimeStr(CalendarUtil.get12FormatTimeFrom24FormatTime(endTime.append(hours).append(":").append(minutes).toString()));
						    }
					}
					rh.setInterviewType(rh.getInterviewId().getInterviewType());
				 rsh.setRequirementInterviewHistoryList(requirementInterviewHistory);
				 }
				 
				 
			    long diff = commonUtil.currentDate().getTime() - rsh.getCreatedDate().getTime();
			    Object object =  getCnadidateHistoryTimeConversion(diff,rsh);
			    rsh = (RequirementSubmissionHistory)object;
			    
				//interview related data is getting
				List<RequirementInterview> RequirementInterview = requirementInterviewService.getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId(submissionId, loginOrgLocation.getId());
				for(RequirementInterview requirementInterview :RequirementInterview){
					 if(requirementInterview.getDateOfInterview() != null){
						 rsh.setInterviewDateStr(Constants.DMY.format(requirementInterview.getDateOfInterview()));
					    }
					    if(requirementInterview.getStartTime() != null){
					    	StringBuilder startTime = new StringBuilder(); 
					    	 String hours = Integer.toString(requirementInterview.getStartTime().getHours());
					    	 String minutes = Integer.toString(requirementInterview.getStartTime().getMinutes());
					    	 rsh.setInterviewStartTime(CalendarUtil.get12FormatTimeFrom24FormatTime(startTime.append(hours).append(":").append(minutes).toString()));
					    }
					    if(requirementInterview.getEndTime() != null){
					    	StringBuilder endTime = new StringBuilder(); 
					    	 String hours = Integer.toString(requirementInterview.getEndTime().getHours());
					    	 String minutes = Integer.toString(requirementInterview.getEndTime().getMinutes());
					    	 rsh.setInterviewEndTime(CalendarUtil.get12FormatTimeFrom24FormatTime(endTime.append(hours).append(":").append(minutes).toString()));
					    }
					    rsh.setInterviewType(requirementInterview.getInterviewType());
				}
				
				
		 }
		 
		 resultJson = commonUtil.toJSON(requirementSubmissionhistory);
		 return resultJson;
	}	
	
	 

	private Object getCnadidateHistoryTimeConversion(long diff, Object obj) {
		    long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if(obj instanceof RequirementInterviewHistory){
				 StringBuilder  time = new StringBuilder();
				 RequirementInterviewHistory rih = (RequirementInterviewHistory)obj;
				if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
					rih.setInterviewHistoryCreatedDate(time.append(diffSeconds).append(" seconds ago ").toString());
				}else if(diffDays == 0 && diffHours == 0){
					rih.setInterviewHistoryCreatedDate(time.append(diffMinutes).append(" minutes ago").toString());
				}else if(diffDays == 0){
					rih.setInterviewHistoryCreatedDate(time.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago ").toString());
				}else if(diffDays < 7){
					rih.setInterviewHistoryCreatedDate(time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
				}else{
					DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
					String commonDateFormat = standardFormat.format(rih.getCreatedDate());
					rih.setInterviewHistoryCreatedDate(commonDateFormat.toString());
				} 
			 return rih;
			 }else if(obj instanceof RequirementSubmissionHistory){
				 StringBuilder  time = new StringBuilder();
				 RequirementSubmissionHistory rsh = (RequirementSubmissionHistory)obj;
				if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
					rsh.setCreatedDateStr(time.append(diffSeconds).append(" seconds ago ").toString());
				}else if(diffDays == 0 && diffHours == 0){
					rsh.setCreatedDateStr(time.append(diffMinutes).append(" minutes ago").toString());
				}else if(diffDays == 0){
					rsh.setCreatedDateStr(time.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago ").toString());
				}else if(diffDays < 7){
					rsh.setCreatedDateStr(time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
				}else{
					DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
					String commonDateFormat = standardFormat.format(rsh.getCreatedDate());
					rsh.setCreatedDateStr(commonDateFormat.toString());
				}
				return rsh;
			 }else if(obj instanceof RequirementOfferHistory){
				 StringBuilder  time = new StringBuilder();
				 RequirementOfferHistory roh = (RequirementOfferHistory)obj;
				 if(diffDays == 0 && diffHours == 0 && diffMinutes == 0){
						roh.setCreateDateStr(time.append(diffSeconds).append(" seconds ago ").toString());
					}else if(diffDays == 0 && diffHours == 0){
						roh.setCreateDateStr(time.append(diffMinutes).append(" minutes ago").toString());
					}else if(diffDays == 0){
						roh.setCreateDateStr(time.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago ").toString());
					}else if(diffDays < 7){
						roh.setCreateDateStr(time.append(diffDays).append(" days ").append(diffHours).append(" hours ago ").toString());
					}else{
						DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm:ss aa");
						String commonDateFormat = standardFormat.format(roh.getCreatedDate());
						roh.setCreateDateStr(commonDateFormat.toString());
					} 
				return roh;
			 }
			return null;
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
