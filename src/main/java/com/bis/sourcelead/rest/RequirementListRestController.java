package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


/**
 * Handles requests for the Candidate List Service.
 * 
 * @author Sammeta David 
 * @date 26th August 2016
 *
 */
	
@RestController
public class RequirementListRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementListRestController.class);
	
	
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
		
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 UserService userService;

	 @Autowired
	 RequirementService requirementService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 CandidateResumesService candidateResumesService;
	 
	 @Autowired
	 RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 RequirementApproverHierarchyService requirementApproverHierarchyService;
	 
	 @Autowired
	 RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	 
	 @Autowired
	 private RecommendationsService recommendationsService;
	 
	 @Autowired
	 private CandidateService candidateService;
	 
	 
	 
	 /**
	   * @author sarath kumar
	   * @since  aug 30 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting CandidatesList By OrgLocationID
	   * this method is called at onload when click on timesheet left menu
	   * 
	   * @param user This is the first parameter to getCandidatesListByOrgLocationID method
	   * @param navigation This is the second parameter to getCandidatesListByOrgLocationID method
	   * @param startDate This is the third parameter to getCandidatesListByOrgLocationID method
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 @PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT','ROLE_VIEW_REQUIREMENT')")
	 @RequestMapping(value = "/getRequirementListByOrgLocationID")
	 public String getRequirementListByOrgLocationID(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,HttpServletRequest request) throws Exception {

		String resultJson;
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Requirement>   requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocation.getId(),status,clients,user.getId());
		for(Requirement requirement : requirementList){
			if(requirement.getClientId() != null){
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
					requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
				}
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
					requirement.setClientName(requirement.getClientId().getLocationId().getOrg().getOrgName());
				}
			}
			if(requirement.getManagerUserId() != null){
				String managerFirstName = "";
				if(StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())){
					managerFirstName = requirement.getManagerUserId().getFirstName();
				}
				String managerLastName = "";
				if(StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())){
					managerLastName = requirement.getManagerUserId().getLastName();
				}
				requirement.setManager(managerFirstName+" "+managerLastName);
			}
		}
		
		ArrayList<Requirement>  requirementList1 = new ArrayList<Requirement>();
		String[] status1 = status.split(",");
		if (status1[0] != null && status1[0].length() > 0) {
			for (String stat : status1) {
				for(Requirement requirement : requirementList){
					if(stat.equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())){
						if(requirement.getClientId() != null){
							if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
								requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
							}
							if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
								requirement.setClientName(requirement.getClientId().getLocationId().getOrg().getOrgName());
							}
						}
						if(requirement.getManagerUserId() != null){
							String managerFirstName = "";
							if(StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())){
								managerFirstName = requirement.getManagerUserId().getFirstName();
							}
							String managerLastName = "";
							if(StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())){
								managerLastName = requirement.getManagerUserId().getLastName();
							}
							requirement.setManager(managerFirstName+" "+managerLastName);
						}
						
						requirementList1.add(requirement);
					}
				}
				
			}
			resultJson = commonUtil.toJSON(requirementList1);
		}else{
			
			resultJson = commonUtil.toJSON(requirementList);
		}
		
		
		
		return resultJson;
	 }
	    
	 
	 /**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get Requirement by requirementId .
	 *  
	 * @param requirementId This is the first paramter to getRequirementByRequirementId method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	 
	 @RequestMapping(value = "/getRequirementById" , method = RequestMethod.POST)
	 public @ResponseBody  String getRequirementById(@RequestParam(value="requirementId", required=false) String  requirementId,HttpServletRequest request) throws Exception {
			String resultJson = null;
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Org loginOrgCode = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			if(requirementId !=null && !requirementId.isEmpty()){
				Requirement requirement = requirementService.getRequirementByRequirementId(Long.valueOf(requirementId));
				
				// to get the RequirementApproverHierarchy to set the Status
				    Boolean isLoginUserAsApprover = false;
					RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(Long.parseLong(requirementId), user.getId());
					if(requirementApproverHierarchy!=null){
						requirement.setRequirementApproverHierarchyStatus(requirementApproverHierarchy.getRequirementApproverStatus().toString());
						isLoginUserAsApprover = true;
					}
					if(requirement.getSpocId() !=null){
						if(requirement.getClientId() !=null ){
							requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
						}
					requirement.setSpocName(requirement.getSpocId().getSpocUser().getFirstName()+" "+requirement.getSpocId().getSpocUser().getLastName());
					requirement.setSpocEmail(requirement.getSpocId().getSpocUser().getEmail());
					requirement.setSpocMobilePhone(requirement.getSpocId().getSpocUser().getMobilePhone());
					/*requirement.setClientSubmissionTemplate(requirement.getClientSubmissionTemplateId().getTemplateName());*/
					}
					if(requirement.getManagerUserId() != null && requirement.getManagerUserId().getFirstName() != null && requirement.getManagerUserId().getLastName() != null ){
						requirement.setManager(requirement.getManagerUserId().getFirstName()+" "+requirement.getManagerUserId().getLastName());	
					}
					

					if(requirement.getSubmissionTemplateId()!=null){
						requirement.setClientSubmissionTemplate(requirement.getSubmissionTemplateId().getTemplateName());
					} 
					
					List<RequirementApproverHierarchy> list = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(Long.parseLong(requirementId));
					
					Integer highestLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(Long.parseLong(requirementId));
					ArrayList<String> ar = new ArrayList<String>();
					if(list !=null && list.size() >0){
						for(RequirementApproverHierarchy approverN :list ){
							ar.add(approverN.getApproverUserId().getFirstName() +""+ approverN.getApproverUserId().getLastName());
						}
						requirement.setHighestLevel(highestLevel);
						requirement.setApproverNames(ar);
					}else{
						
						requirement.setApproverNames(ar);
					}
					
					Boolean isLoginUserAsTeamMember =   requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(Long.parseLong(requirementId), user.getId());
					requirement.setIsLoginUserAsTeamMember(isLoginUserAsTeamMember.toString());
					
					requirement.setIsLoginUserAsApprover(isLoginUserAsApprover.toString());
					
					Boolean isLoginUserAsManager =   requirementService.checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long.parseLong(requirementId), user.getId());
					requirement.setIsLoginUserAsManager(isLoginUserAsManager.toString());
					
					
					
					resultJson = commonUtil.toJSON(requirement);
				 
			}
			
			return resultJson;
			
     }
	 
	 
	 /**
		 * @author sarath kumar
		 * @since  sep 13 2016
		 * @version 0.0.1
		 * 
		 * This method is used to get Requirement by requirementId .
		 *  
		 * @param requirementId This is the first paramter to getRequirementByRequirementId method
		 * 
		 * @return Requirement 
		 * 
		 * @throws NullPointer
		 */
		 
		 @RequestMapping(value = "/getRequirementAssignedListByRequirementId" , method = RequestMethod.POST)
		 public @ResponseBody  String getRequirementAssignedListByRequirementId(@RequestParam(value="requirementId", required=false) String  requirementId,HttpServletRequest request) throws Exception {
				String resultJson = null;
				List<RequirementAssignTo> requirementAssignToList = new ArrayList<RequirementAssignTo>();
				
				if(requirementId !=null && !requirementId.isEmpty()){
			        Collection<RequirementAssignTo>  requirementAssignToListdb = requirementAssignToService.getRequirementAssignToListByRequirementId(Long.parseLong(requirementId));

			       if(requirementAssignToListdb !=null && !requirementAssignToListdb.isEmpty()){
			           for (RequirementAssignTo assing : requirementAssignToListdb) { 
			        	   RequirementAssignTo ra = new  RequirementAssignTo();
			        	   ra.setAssignedUserName(assing.getAssignUserId().getFirstName()+" "+assing.getAssignUserId().getLastName());
					       if(assing.getRoleId() != null ){
					    	   ra.setAssignedUserRole(assing.getRoleId().getDisplayName());
					       }
					       if(assing.getTeamId() != null ){
					    	   ra.setTeamName(assing.getTeamId().getName());
					       }
					       
					       if(assing.getAssignUserId()  != null){
					    	   Integer applicantCount =   requirementSubmissionService.getApplicantCountByrequirementIdandAssignedUsercode(Long.parseLong(requirementId), assing.getAssignUserId().getUserCode());
						       ra.setApplicantCount(applicantCount.toString());
					       }
					       requirementAssignToList.add(ra);
				      }
			       }
					resultJson = commonUtil.toJSON(requirementAssignToList);
				}else{
					resultJson = "failure";
				}
				return resultJson;
	     }
	 	   
		 /**
		  * @author Prasanna vadla
		  * @param requirementId
		  * @param request
		  * @return
		  * @throws Exception
		  */
		 
		 @RequestMapping(value = "/getSubmissionsByRequirementId" , method = RequestMethod.POST)
		 @ResponseBody  String getSubmissionsByRequirementId(@RequestParam(value="requirementId", required=false) String  requirementId,HttpServletRequest request) throws Exception {
				String resultJson = null;
				 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
					User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
					if(requirementId !=null && !requirementId.isEmpty()){
						List<RequirementSubmission> requirementSubmissions = requirementSubmissionService.getSubmissionsByRequirementId(Long.valueOf(requirementId),user,loginOrgLocation);
						if(requirementSubmissions != null){
							for (RequirementSubmission requirementSubmission : requirementSubmissions) {
								if(requirementSubmission.getCandidateResumes() != null){
									CandidateResumes candidateResumes = candidateResumesService.getCandidateResumeByResumeId(requirementSubmission.getCandidateResumes().getId());
									RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(requirementSubmission.getId());
									String candidateName = requirementSubmissionTemplateFields.getFirstName();
									if(requirementSubmissionTemplateFields.getLastName()!=null){
										candidateName = candidateName+" "+requirementSubmissionTemplateFields.getLastName();
									}
									requirementSubmission.setCandidateName(candidateName);
									requirementSubmission.setCandidateEmail(requirementSubmissionTemplateFields.getEmailId());
									requirementSubmission.setExpectedCTC(requirementSubmissionTemplateFields.getExpectedCTC());
									requirementSubmission.setWorkExperience(requirementSubmissionTemplateFields.getWorkExperience());
									requirementSubmission.setCandidateId(requirementSubmission.getCandidate().getId().toString());
									requirementSubmission.setPhoneNumber(requirementSubmissionTemplateFields.getPhoneNumber());
									requirementSubmission.setResumeId(requirementSubmission.getCandidateResumes().getId());
									if(requirementSubmission.getCreatedDate() != null){
										requirementSubmission.setCreatedDateStr(Constants.DF_YMD.format(requirementSubmission.getCreatedDate()));
									}
									User userVal = userService.getUserByUserCode(requirementSubmission.getCreatedBy());
									requirementSubmission.setUserName(userVal.getFirstName()+" "+userVal.getLastName());
									requirementSubmission.setResumeName(candidateResumes.getFileName());
									Candidate candidate = candidateService.getCandidateByCandidateId(requirementSubmission.getCandidate().getId());
									List<Candidate> candidateList = new ArrayList<>();
									candidateList.add(candidate);
									List<Candidate> candidateListCopy = recommendationsService.getRecommendationsWithScore(candidateList,requirementSubmission.getRequirement());
									if(candidateListCopy !=null && candidateListCopy.size() >0){
										requirementSubmission.setScore(candidateListCopy.get(0).getCandidateScore()+"%");
										logger.info("size of score is "+candidateListCopy.get(0).getCandidateScore());
									}else{
										requirementSubmission.setScore("0 %");
									}
								}
							}
						}
						
						resultJson = commonUtil.toJSON(requirementSubmissions);
					}
				return resultJson;
		 }
		 
		 @RequestMapping(value = "/saveChangedStatus" , method = RequestMethod.POST)
		 @ResponseBody  String saveChangedStatus(@RequestParam(value="requirementId", required=false) Long  requirementId,
				 @RequestParam(value="status", required=false) String  status,HttpServletRequest request) throws Exception {
			
			 
			 
			 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

			 Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
			 REQUIREMENT_SUBMISSION_STATUS previousStatus = requirement.getRequirementSubmissionStatus();
			 
			 REQUIREMENT_SUBMISSION_STATUS changedStatus = Enum.valueOf(REQUIREMENT_SUBMISSION_STATUS.class, status);
			 requirement.setRequirementSubmissionStatus(changedStatus);
			 requirement.setRequirementSubmissionPreviousStatus(previousStatus);
			 requirement.setUpdatedBy(loginUser.getUserCode());
			 requirement.setUpdatedDate(commonUtil.currentDate());
			 requirementService.addRequirement(requirement); 
			return "success";
		 }
		 
		 
		 /**
		  * @author Rajashekar
		  * used fro export xls and pdf
		  * @param status
		  * @param clients
		  * @param exportType
		  * @param request
		  * @return void
		  * @throws Exception
		  */
		 @PreAuthorize("hasAnyRole('ROLE_EXPORT_REQUIREMENTS')")
		 @RequestMapping(value = "/getRequirementExportListByOrgLocationID")
		 public void getRequirementExportListByOrgLocationID(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,@RequestParam(value="exportType", required=false) String exportType,HttpServletRequest request,HttpServletResponse response) throws Exception {

//			String resultJson;
			Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
			User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Requirement>   requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocation.getId(),status,clients,user.getId());
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			File file =null;
			
			TimeSheetSubmissionCount requirementSubCount = getRequirementCount(org,userRole,user);
			for(Requirement requirement : requirementList){
				if(requirement.getClientId() != null){
					if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
						requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
						requirement.setClientContactName(requirement.getClientId().getResponderLocationId().getUser().getFirstName());
						requirement.setClientContactEmail(requirement.getClientId().getResponderLocationId().getUser().getEmail());
					}
					if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
						requirement.setClientName(requirement.getClientId().getLocationId().getOrg().getOrgName());
						requirement.setClientContactName(requirement.getClientId().getLocationId().getUser().getFirstName());
						requirement.setClientContactEmail(requirement.getClientId().getLocationId().getUser().getEmail());
					}
				}
				if(requirement.getManagerUserId() != null){
					String managerFirstName = "";
					if(StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())){
						managerFirstName = requirement.getManagerUserId().getFirstName();
					}
					String managerLastName = "";
					if(StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())){
						managerLastName = requirement.getManagerUserId().getLastName();
					}
					requirement.setManager(managerFirstName+" "+managerLastName);
				}
			}
			
			List<Requirement>  requirementList1 = new ArrayList<Requirement>();
			String[] status1 = status.split(",");
			if (status1[0] != null && status1[0].length() > 0) {
				for (String stat : status1) {
					for(Requirement requirement : requirementList){
						if(stat.equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())){
							if(requirement.getClientId() != null){
								if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
									requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
									requirement.setClientContactName(requirement.getClientId().getResponderLocationId().getUser().getFirstName());
									requirement.setClientContactEmail(requirement.getClientId().getResponderLocationId().getUser().getEmail());
								}
								if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
									requirement.setClientName(requirement.getClientId().getLocationId().getOrg().getOrgName());
									requirement.setClientContactName(requirement.getClientId().getLocationId().getUser().getFirstName());
									requirement.setClientContactEmail(requirement.getClientId().getLocationId().getUser().getEmail());
								}
							}
							if(requirement.getManagerUserId() != null){
								String managerFirstName = "";
								if(StringUtils.isNotEmpty(requirement.getManagerUserId().getFirstName())){
									managerFirstName = requirement.getManagerUserId().getFirstName();
								}
								String managerLastName = "";
								if(StringUtils.isNotEmpty(requirement.getManagerUserId().getLastName())){
									managerLastName = requirement.getManagerUserId().getLastName();
								}
								requirement.setManager(managerFirstName+" "+managerLastName);
							}
							
							requirementList1.add(requirement);
						}
					}
					
				}
				 file = requirementService.getExportRequirementFile(requirementList1,org.getOrgName(),exportType,requirementSubCount);
			}else{
				 file = requirementService.getExportRequirementFile(requirementList,org.getOrgName(),exportType,requirementSubCount);
			}
			 String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
			
		 }
		 
		/**
		 * @author Rajashekar
		 * Returns requirements count 
		 * @param loginOrgLocations
		 * @param userRole
		 * @param user
		 * @return TimeSheetSubmissionCount pojo
		 * @throws Exception
		 */
		 private TimeSheetSubmissionCount getRequirementCount(Org loginOrgLocations, String userRole, User user) throws Exception {
			 TimeSheetSubmissionCount  timeSheetSubmissionCount = new TimeSheetSubmissionCount();
				
				Integer newCount = 0;
				Integer draftCount = 0;
				Integer submittedCount = 0;
				Integer openCount = 0;
				Integer onholdCount = 0;
				Integer cancelCount = 0;
				
				List<Requirement>   requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocations.getId(),null,null,user.getId());
		        	
		        		  for(Requirement req :requirementList){
		        			  if("NEW".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
		        				   newCount ++;
		        			  }
		        			  if("Draft".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
		        				  draftCount ++;
		       			      }
		        			  if("submitted".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
		        				  submittedCount ++;
		       			      }
		        			  if("open".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
		        				  openCount ++;
		       			      }
		        			  if("onhold".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
		        				  onholdCount ++;
		       				     
		       			      }
		        			  if("CANCEL".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
		        				    cancelCount ++;
		          				   
		        			  }
		        			  
		        		  }
		        		  
		        		  timeSheetSubmissionCount.setNewRequirementCount(newCount);
		        		  timeSheetSubmissionCount.setDraftRequirementCount(draftCount);
	   				      timeSheetSubmissionCount.setSubmittedRequirementCount(submittedCount);
	   				      timeSheetSubmissionCount.setOpenRequirementCount(openCount);
	   				      timeSheetSubmissionCount.setOnHoldRequirementCount(onholdCount);
	   				      timeSheetSubmissionCount.setCancelRequirementCount(cancelCount);
						return timeSheetSubmissionCount;
		}


		/**
		* @author sarath kumar
		* 
		* This method is used to download attachment of requirement
		* 
		* @param requirementId
		* @param request
		* 
		* @return resultJson
		* 
		* @throws Exception
		*/
		@RequestMapping(value = "/downloadRequirementAttachment", method = RequestMethod.GET)
	    public void downloadRequirementAttachment(@RequestParam(value="requirementId", required=false) String requirementId ,HttpServletResponse response) throws Exception {
			
				Requirement requirement =  requirementService.getRequirementByRequirementId(Long.parseLong(requirementId));
			    
			    response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition",
		                "attachment;filename="+requirement.getFileName());
			    
				InputStream inputStream = new ByteArrayInputStream(requirement.getDocFile());
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
     }
		
		
		
		
		 /**
		* @author Sammeta David Raju
		* 
		* This method is used get RequirementListCount Based On Status
		* 
		* @param requirementId
		* @param request
		* 
		* @return resultJson
		* 
		* @throws Exception
		*/
		@RequestMapping(value = "/getRequirementListCountBasedOnStatus", method = RequestMethod.GET)
		@ResponseBody
		public String getRequirementByListCountBasedOnStatus(HttpServletRequest request) throws Exception {

			String resultJson;
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

			
			
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			
			TimeSheetSubmissionCount  timeSheetSubmissionCount = new TimeSheetSubmissionCount();
			
			Integer newCount = 0;
			Integer draftCount = 0;
			Integer submittedCount = 0;
			Integer openCount = 0;
			Integer onholdCount = 0;
			Integer cancelCount = 0;
			
			List<Requirement>   requirementList = requirementService.getRequirementListByOrgLocationID(loginOrgLocations.getId(),null,null,user.getId());
	        	
	        		  for(Requirement req :requirementList){
	        			  if("NEW".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
	        				   newCount ++;
	        			  }
	        			  if("Draft".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
	        				  draftCount ++;
	       			      }
	        			  if("submitted".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
	        				  submittedCount ++;
	       			      }
	        			  if("open".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
	        				  openCount ++;
	       			      }
	        			  if("onhold".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
	        				  onholdCount ++;
	       				     
	       			      }
	        			  if("CANCEL".equalsIgnoreCase(req.getRequirementSubmissionStatus().toString())){
	        				    cancelCount ++;
	          				   
	        			  }
	        			  
	        		  }
	        		  
	        		  timeSheetSubmissionCount.setNewRequirementCount(newCount);
	        		  timeSheetSubmissionCount.setDraftRequirementCount(draftCount);
   				      timeSheetSubmissionCount.setSubmittedRequirementCount(submittedCount);
   				      timeSheetSubmissionCount.setOpenRequirementCount(openCount);
   				      timeSheetSubmissionCount.setOnHoldRequirementCount(onholdCount);
   				      timeSheetSubmissionCount.setCancelRequirementCount(cancelCount);

			
			resultJson = commonUtil.toJSON(timeSheetSubmissionCount);
			return resultJson;
		}
}
