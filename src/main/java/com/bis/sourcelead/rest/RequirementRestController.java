package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.index.helper.RequirementHelper;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.CombinedList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.RecommendationsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadRolesService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
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
	
	@Value("${static.path}")
	private String staticPath;

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
	 
	 @Autowired
	 private RolesService roleService;
	 
	 @Autowired
	 private SourceleadRolesService sourceleadRolesService;
	 
	 @Autowired
	 private RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 private LookUpDataService lookUpDataService;

	 @Autowired
	 RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 private RequirementElkUtil requirementElkUtil;
	 
	/**
	 * @author prasad k
	 * @since sept 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save Requirement in Requirement
	 *          table.
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeRequirement method
	 * @param orgLocations
	 *            This is the second paramter to storeRequirement method
	 * @param user
	 *            This is the third paramter to storeRequirement method
	 * @param multiFile
	 *            This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/storeRequirement", method = RequestMethod.POST)
	public @ResponseBody String storeRequirement(RequirementDTO requirementDTO, HttpServletRequest request, @RequestParam(value = "saveType", required = false) String saveType) throws Exception
	{
		if (Validator.validate(requirementDTO)) {
			requirementDTO.setStaticPath(staticPath);
		//	FileUploadUtil.fillUploadedFileInfo(requirementDTO, requirementDTO.getFileInfo());
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Requirement requirement = requirementService.storeRequirement(requirementDTO, orgLocation, user);
			if (requirementDTO.getSaveType().equalsIgnoreCase("saveType")
					&& (null == (requirementDTO.getRequirementId())
							|| "".equalsIgnoreCase(requirementDTO.getRequirementId().toString()))) {
				StringBuilder reqUpdateMsg = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg.append("Success!! New ").append(requirement.getJobTitle()).append(" job  saved to Draft").toString());
			}
			else if (requirementDTO.getSaveType().equalsIgnoreCase("save")
					&& (null == (requirementDTO.getRequirementId())
							|| "".equalsIgnoreCase(requirementDTO.getRequirementId().toString()))) {
				StringBuilder reqUpdateMsg1 = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg1.append("Success!! New ").append(requirement.getJobTitle()).append(" job  saved").toString());
			}
			else if (requirementDTO.getSaveType().equalsIgnoreCase("submitForApproval")
					&& (null == (requirementDTO.getRequirementId())
							|| "".equalsIgnoreCase(requirementDTO.getRequirementId().toString()))) {
				StringBuilder reqUpdateMsg2 = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg2.append("Success!! New ").append(requirement.getJobTitle()).append(" job  submitted for approval").toString());
			}
			else if ((requirementDTO.getRequirementId() != null)
					&& !(requirementDTO.getRequirementId().isEmpty())
					&& (requirementDTO.getSaveType().equalsIgnoreCase("save"))) {
				StringBuilder reqUpdateMs3 = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMs3.append("Success!! ").append(requirement.getJobTitle()).append(" details updated").toString());
			}
			else if ((requirementDTO.getRequirementId() != null)
					&& !(requirementDTO.getRequirementId().isEmpty())
					&& (requirementDTO.getSaveType().equalsIgnoreCase("submitForApproval"))) {
				StringBuilder reqUpdateMsg4 = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMsg4.append("Success!! ").append(requirement.getJobTitle()).append(" Updated and Submitted ").toString());
			}
			else if ((requirementDTO.getRequirementId() != null)
					&& !(requirementDTO.getRequirementId().isEmpty())
					&& (requirementDTO.getSaveType().equalsIgnoreCase("draft"))) {
				StringBuilder reqUpdateMs5 = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "reqCreateMsg", reqUpdateMs5.append("Success!!  ").append(requirement.getJobTitle()).append(" Updated in Draft Stage").toString());
			}
			return "success";
		}
		else
			return "MissingData";
	}
	
	/**
	 * This method is used for to create template and stored in Requirement
	 * table
	 * 
	 * @param json
	 * @param request
	 * @return success msg
	 * @throws NullPointer
	 */
	@PreAuthorize("hasAnyRole('ROLE_CREATE_REQUIREMENT_TEMPLATE','ROLE_VIEW_EDIT_REQUIREMENT_TEMPLATE')")
	@RequestMapping(value = "/storeRequirementTemplate", method = RequestMethod.POST)
	public @ResponseBody String storeRequirementTemplate(RequirementDTO requirementDTO, HttpServletRequest request) throws Exception
	{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Requirement requirement = requirementService.storeRequirementTemplate(requirementDTO, orgLocation, user);
		StringBuilder reqTemplateSuccessMessage = new StringBuilder();
		SourceLeadSessionManager.setSessionAttribute(request, "userCreateMsgTemplate", reqTemplateSuccessMessage.append("Success!! ").append(requirementDTO.getJobTitle()).append(" template  saved").toString());
		return "success";
	}
	
	
	/**
	 * @author sarath kumaar
	 * @since oct 18 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to accept/Reject Requirement in Requirement
	 *          table.
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeRequirement method
	 * @param orgLocations
	 *            This is the second paramter to storeRequirement method
	 * @param user
	 *            This is the third paramter to storeRequirement method
	 * @param multiFile
	 *            This is the fourth paramter to storeRequirement method
	 * 
	 * @return success msg
	 * 
	 * @throws NullPointer
	 */

	@RequestMapping(value = "/requirementAcceptOrReject", method = RequestMethod.POST)
	public @ResponseBody String requirementAcceptOrReject(
			@RequestParam(value = "requirementId", required = false) Long requirementId,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "isLoginUserAsAdmin", required = false) String isLoginUserAsAdmin,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request)
			throws Exception {

		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
		User managerUser=userService.getUserById(requirement.getManagerUserId().getId());

		RequirementApproverHierarchy requirementApproverHierarchy = null;
		//rejected reason save in requirementApproverHierarchy table only who can reject the requirement
		if(isLoginUserAsAdmin.equalsIgnoreCase("true")){
			requirementApproverHierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
			requirementApproverHierarchy.setRejectedReason(reason);
	    	requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
		}else{
		requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());
		
		 if(status.equalsIgnoreCase("REJECTED")  &&  !reason.equalsIgnoreCase("") && !reason.isEmpty()){
	    	requirementApproverHierarchy.setRejectedReason(reason);
	    	requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
	    }
		}
		//  This for the Requirement Location 
		StringBuilder location = new StringBuilder();
		if(null != requirement.getCity() && !requirement.getCity().equalsIgnoreCase("")){
			location.append(requirement.getCity()).append(" ,");
		 }
		if(null != requirement.getState() && !requirement.getState().equalsIgnoreCase("")){
			location.append(requirement.getState()).append(" ,");
		 }
		
		 location.append(requirement.getCountry()).append(" ");
		
		
		// requirement created user

					// to get RequirementApproverHierarchy with maximum Level
					Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

					if (null != maxLevel) {
						
						// approve the requirement from admin login. 
						if(isLoginUserAsAdmin.equalsIgnoreCase("true")){
							if (status.equalsIgnoreCase("APPROVED")) {
								requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
							} else if (status.equalsIgnoreCase("REJECTED")) {
								requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
							}
							requirement.setUpdatedBy(loginUser.getUserCode());
							requirement.setUpdatedDate(commonUtil.currentDate());
							requirementService.addRequirement(requirement);
							List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
							for (RequirementApproverHierarchy rah : list1) {
								if (status.equalsIgnoreCase("APPROVED")) {
									rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
								} else if (status.equalsIgnoreCase("REJECTED")) {
									rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
								}
								rah.setUpdatedBy(loginUser.getUserCode());
								rah.setUpdatedDate(commonUtil.currentDate());
								requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
							}
							/*This logic is to send mail to assigned team members after approver accepted the requirement request(parallel approver)*/
							RequirementApproverHierarchy hierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
					    	 List<RequirementAssignTo> reqAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(requirementId,orgLocation.getId());
				    			if(null != hierarchy && "APPROVED".equals(hierarchy.getRequirementApproverStatus().toString())){
				    				for(RequirementAssignTo reqAssign:reqAssignTo ){
											if(null != reqAssign.getAssignUserId() && !loginUser.getId().equals(reqAssign.getAssignUserId().getId())){
												requirementService.AssignToTeamMembersEmail(loginUser, reqAssign.getAssignUserId(), requirement,new StringBuilder(),orgLocation);
											}
				    				}
				    		}
						}
						else if (maxLevel.equals(requirementApproverHierarchy.getLevel())) {
								if (status.equalsIgnoreCase("APPROVED")) {
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
								} else if (status.equalsIgnoreCase("REJECTED")) {
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
								}
									requirement.setUpdatedBy(loginUser.getUserCode());
									requirement.setUpdatedDate(commonUtil.currentDate());
									requirementService.addRequirement(requirement);
									List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
									for (RequirementApproverHierarchy rah : list1) {
										if (status.equalsIgnoreCase("APPROVED")) {
											rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
										} else if (status.equalsIgnoreCase("REJECTED")) {
											rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
										}
										rah.setUpdatedBy(loginUser.getUserCode());
										rah.setUpdatedDate(commonUtil.currentDate());
										requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
									}
								// this is for sending email to requirement initiator
								if (status.equalsIgnoreCase("APPROVED")) {
									
									if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
										if(!requirement.getCreatedBy().getId().equals(managerUser.getId())){
									requirementService.acceptEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location ,orgLocation);
									requirementService.acceptEmailScenario(loginUser, managerUser, requirement, location ,orgLocation);
										}else{
									requirementService.acceptEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location ,orgLocation);
										}
									}
								} else if (status.equalsIgnoreCase("REJECTED")) {
									
									if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
										if(!requirement.getCreatedBy().getId().equals(managerUser.getId())){
									requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
									requirementService.rejectedEmailScenario(loginUser, managerUser, requirement, location, reason,orgLocation);
										}
										else{
											
											requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
										}
									}
									
									}
								 /*This logic is to send mail to assigned team members after approver accepted the requirement request(parallel approver)*/
								RequirementApproverHierarchy hierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
						    	 List<RequirementAssignTo> reqAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(requirementId,orgLocation.getId());
					    			if(null != hierarchy && "APPROVED".equals(hierarchy.getRequirementApproverStatus().toString())){
					    				for(RequirementAssignTo reqAssign:reqAssignTo ){
												if(null != reqAssign.getAssignUserId() && !loginUser.getId().equals(reqAssign.getAssignUserId().getId())){
													requirementService.AssignToTeamMembersEmail(loginUser, reqAssign.getAssignUserId(), requirement,new StringBuilder(),orgLocation);
												}
					    				}
					    		}
						}else{

							    RequirementApproverHierarchy level1approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
								User approverUser = userService.getUserById(level1approver.getApproverUserId().getId());
								if (status.equalsIgnoreCase("APPROVED")) {
									if (null != level1approver) {
										// this is for sending email to second level approver
										if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
										requirementService.approverTwoEmailScenario(loginUser, approverUser, requirement, location,	orgLocation);
									}
									}
									requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
									requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED.toString());
								} else if (status.equalsIgnoreCase("REJECTED")) {
									// this is for sending email to requirement initiator
									requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
									requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
									requirementApproverHierarchy.setRejectedReason(reason);
									if(loginUser.getId().longValue() != requirement.getManagerUserId().getId().longValue()){
									requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
									}
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
									
									// This is  to update  the level2 Approver Record For RejectedS
								    RequirementApproverHierarchy level2approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(requirementApproverHierarchy.getId(), requirementId);
								    level2approver.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
								    requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
								    level2approver.setUpdatedBy(loginUser.getUserCode());
								    level2approver.setUpdatedDate(commonUtil.currentDate());
									requirementApproverHierarchyService.addRequirementApproverHierarchy(level2approver);
								}
								requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
								requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
								requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
								requirementService.addRequirement(requirement);
						}
						
					}
					RequirementDTO requirementDTO = commonUtil.getRequirementDTO(requirement);
					requirementElkUtil.saveElkRequirement(requirement,requirementDTO);
			
					return "success";
	}
					
		// Old Code			
		/*// to get  RequirementApproverHierarchy with maximum Level

		RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService
				.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());

		// requirement created user
		User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());

		// to get RequirementApproverHierarchy with maximum Level

		Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

		if (maxLevel != null) {
			if (maxLevel.equals(requirementApproverHierarchy.getLevel())) {
				if (status.equalsIgnoreCase("APPROVED")) {
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
				} else if (status.equalsIgnoreCase("REJECTED")) {
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
				}
				requirement.setUpdatedBy(loginUser.getUserCode());
				requirement.setUpdatedDate(commonUtil.currentDate());
				requirementService.addRequirement(requirement);
				List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
				for (RequirementApproverHierarchy rah : list1) {
					if (status.equalsIgnoreCase("APPROVED")) {
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.OPEN);
					} else if (status.equalsIgnoreCase("REJECTED")) {
						rah.setRejectedReason(reason);
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
					}
					rah.setUpdatedBy(loginUser.getUserCode());
					rah.setUpdatedDate(commonUtil.currentDate());
					requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
				}
				// this is for sending email to requirement initiator
				if (status.equalsIgnoreCase("APPROVED")) {
					requirementService.acceptEmailScenario(loginUser, intiatorUser, requirement, orgLocation);
				} else if (status.equalsIgnoreCase("REJECTED")) {
					requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, null, reason,orgLocation);
				}
			} else {

				RequirementApproverHierarchy level2approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
				User approverUser = userService.getUserById(level2approver.getApproverUserId().getId());
				if (status.equalsIgnoreCase("APPROVED")) {
					if (level2approver != null) {
						// this is for sending email to second level approver
						requirementService.approverTwoEmailScenario(loginUser, approverUser, requirement, null,	orgLocation);
					}
					requirementApproverHierarchy
							.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.OPEN);
				} else if (status.equalsIgnoreCase("REJECTED")) {
					// this is for sending email to requirement initiator
					requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
					requirementApproverHierarchy.setRejectedReason(reason);
					requirementService.rejectedEmailScenario(loginUser, intiatorUser, requirement, null, reason,orgLocation);

				}
				requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
				requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
				requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
			}
	
		}

		*/
	

		/*
		 * if(status.equalsIgnoreCase("REJECTED")){ if(maxLevel!=null){
		 * if(maxLevel.equals(requirementApproverHierarchy.getLevel())){
		 * requirement.setRequirementSubmissionStatus(
		 * REQUIREMENT_SUBMISSION_STATUS.REJECTED);
		 * requirement.setUpdatedBy(loginUser.getUserCode());
		 * requirement.setUpdatedDate(commonUtil.currentDate());
		 * requirementService.addRequirement(requirement);
		 * List<RequirementApproverHierarchy> list1 =
		 * requirementApproverHierarchyService.
		 * getRequirementApproverHierarchyListByRequirementId(requirementId);
		 * for(RequirementApproverHierarchy rah :list1){
		 * rah.setRequirementApproverStatus(
		 * REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
		 * rah.setUpdatedBy(loginUser.getUserCode());
		 * if(rah.getApproverUserId().getId() == loginUser.getId()){
		 * requirementApproverHierarchy.setRejectedReason(reason); }
		 * rah.setUpdatedDate(commonUtil.currentDate());
		 * requirementApproverHierarchyService.addRequirementApproverHierarchy(
		 * rah); }
		 * 
		 * 
		 * // Email for Reject requirement from approver level Two to Initiator.
		 * 
		 * requirementService.rejectedEmailScenario(loginUser, intiatorUser,
		 * requirement, null, reason,orgLocation); }else{
		 * requirementApproverHierarchy.setRequirementApproverStatus(
		 * REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
		 * requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
		 * requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate())
		 * ; requirementApproverHierarchy.setRejectedReason(reason);
		 * requirementApproverHierarchyService.addRequirementApproverHierarchy(
		 * requirementApproverHierarchy);
		 * 
		 * // Email for Reject requirement from approver level One to Initiator.
		 * requirementService.rejectedEmailScenario(loginUser, intiatorUser,
		 * requirement, null, reason,orgLocation); //User approverUser =
		 * userService.getUserById(requirementApproverHierarchy.
		 * getApproverUserId().getId());
		 * //requirementService.approverTwoEmailScenario(loginUser,
		 * approverUser,requirement,null,orgLocation); } } }
		 */

	

	/**
	 * @author punnana sarath kumar
	 * @since sept 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save Requirement in Requirement
	 *          table.
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeRequirement method
	 * @param orgLocations
	 *            This is the second paramter to storeRequirement method
	 * @param user
	 *            This is the third paramter to storeRequirement method
	 * @param multiFile
	 *            This is the fourth paramter to storeRequirement method
	 * 
	 * @return Candidate
	 * 
	 * @throws NullPointer
	 */

	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public @ResponseBody String getUserList(@RequestParam(value = "teamType", required = false) String teamType,
			HttpServletRequest request) throws Exception {

		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

      //		List<User> usersList = userService.getOwnersByOrgLocationId(orgLocation.getId());
		
	    // This is made static as requirment action id is 146
		
		ArrayList<User> mynewList =  new ArrayList<User>();
		List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),teamType, Long.valueOf(Constants.TAB_REQUIREMENT));
       
		//   Commented because Team Lead not required to show in team Members List By Default he is added in to the  RequirementAssignTo
		
		/*
		// adding team owner as  a Team Member
		if(!teamType.equalsIgnoreCase("") && !teamType.equalsIgnoreCase("undefined") && !teamType.contains("role") && !teamType.contains("All")){
			Team team = teamService.getTeamByTeamId(Long.parseLong(teamType));
			usersList.add(team.getOwnerUserId());
		}*/
		
		
		for(User user : usersList){
	            List<RequirementAssignTo> assign = requirementService.getUserAssignJobCount(user.getId(),orgLocation.getId());
	            user.setAssignJobCount(assign.size());
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocation.getId());
				if(null != userRole){
					user.setUserType(userRole.getUserType());
					 mynewList.add(user);
				}
		   }
		
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;
		
		/*List<User> usersList = userService.getUsersByTeamType(orgLocation.getId(),teamType);
		for(User user : usersList){

		// List<User> usersList =
		// userService.getOwnersByOrgLocationId(orgLocation.getId());
		List<User> usersList = userService.getUsersByTeamType(orgLocation.getId(), teamType);
		for (User user : usersList) {

			List<RequirementAssignTo> assign = requirementService.getUserAssignJobCount(user.getId());

			user.setAssignJobCount(assign.size());
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocation.getId());
			if (userRole != null) {
				user.setUserType(userRole.getUserType());
			}
		}
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;*/
	}

	@RequestMapping(value = "/getApproverTwoByUserId", method = RequestMethod.POST)
	public @ResponseBody String getApproverTwoByUserId(
			@RequestParam(value = "currentApproverId", required = false) Long currentApproverId,
			HttpServletRequest request) throws Exception {
		String resultJson = null;

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		List<User> userList = userService.getUsersByTeamTypeAndNoCurrentUser(currentApproverId, orgLocation.getId());
		resultJson = toJSON(userList);
		return resultJson;
	}

	/**
	 * Handles Vendor Contacts
	 * 
	 * @param json
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	//TODO Dont delete
	/*@PreAuthorize("hasRole('ROLE_CREATE_CLIENT_CONTACT')")
	@RequestMapping(value = "/saveClientContact", method = RequestMethod.POST)
	@ResponseBody
	public String saveClientContact(@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "orgRelationId", required = false) String orgRelationId,
			@RequestParam(value = "clientLocationId", required = false) String clientLocationId,
			HttpServletRequest request) throws Exception {

		String responderType = "Client";
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		orgSpocService.addVendorContacts(jsonObj, user, orgLocations, orgRelationId, clientLocationId, responderType,
				org);

		List<OrgSpoc> orgSpocsList = orgSpocService.getAllSpocsByOrgRelationId(Long.parseLong(orgRelationId),
				user.getId(), orgLocations.getId(),"Client");
		for (OrgSpoc orgSpoc : orgSpocsList) {
			String ownerLastName = "";
			if (null != orgSpoc.getVendorUser() && null != orgSpoc.getVendorUser().getLastName()) {
				ownerLastName = orgSpoc.getVendorUser().getLastName();
			}
			StringBuilder spocNameSB = new StringBuilder();
			StringBuilder ownerNameSB = new StringBuilder();
			orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString());
			orgSpoc.setSpocEmail(orgSpoc.getClientUser().getEmail());
			orgSpoc.setSpocMobilePhone(orgSpoc.getClientUser().getMobilePhone());
			orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(ownerLastName).toString());
			orgSpoc.setOwnerUserId(orgSpoc.getVendorUser().getId().toString());
			orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
		}
		return commonUtil.toJSON(orgSpocsList);
	}*/

	/**
	 * @author Venkat Kesav
	 * @since sept 21 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to getTeamsList.
	 * 
	 * @param request
	 * @return resultJson
	 * 
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/getTeamsList", method = RequestMethod.POST)
	public @ResponseBody String getTeamsList(@RequestParam(value = "reqId", required = false) Long reqId, HttpServletRequest request) throws Exception {
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		CombinedList combinedList = new CombinedList();
		
		List<RequirementAssignTo> requirementAssignTo = null;
		if(!Objects.isNull(reqId)){
			requirementAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(reqId, orgLocation.getId());
		}

		Boolean isTeamViewAccess = commonUtil.hasRole("ROLE_VIEW_TEAM");
		if (isTeamViewAccess) {
			List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocation.getId());
			if (null != teamsList) {
				for(Team team : teamsList){
					
					if(null != requirementAssignTo){
						if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() != null && t.getTeamId().getId().equals(team.getId()))) {
							team.setIsTeamAssigned("Y");
						}
					}
				}
				combinedList.setTeams(teamsList);
			}
		}

		List<Roles> rolesList = roleService.getTabAccessRolesListByOrgLocationIdAndTabId(orgLocation.getId(),
				Long.valueOf(Constants.TAB_REQUIREMENT));
		if (null != rolesList) {
			for(Roles role : rolesList){
				if(null != requirementAssignTo){
					if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() == null && t.getRoleId() != null && t.getRoleId().getId().equals(role.getId()))) {
						role.setIsRoleAssigned("Y");
					}
				}
			}
			combinedList.setRoles(rolesList);
		}
		
		
		

		resultJson = commonUtil.toJSON(combinedList);
		return resultJson;
	}

	/**
	 * @author Rajashekar Recommendations
	 * @param requirementId
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_RECOMMENDATIONS')")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRequirementRecommendationsByRequirementId", method = RequestMethod.POST)
	public @ResponseBody List<CandidateIndex> getRequirementRecommendationsByRequirementId(
			@RequestParam(value = "requirementId", required = false) Long requirementId, @RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(value = "count", required = false) Long offset)
			throws Exception {
		String resultJson;
		//OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<CandidateIndex> candidateListCopy = null;
		RecommendedCustomizeScore  recommendedCustomizeScore = recommendedCustomizeScoreService.getRecommendedCustomizeScoreByRequirementId(requirementId);
	    if(null != recommendedCustomizeScore){ 
		//candidateListCopy = recommendationsService.getRecommendationsUpdateWithScoreByUsingElk(orgLocation.getId(), recommendedCustomizeScore,offset);
	    	candidateListCopy = recommendationsService.getRecommendationsUpdateWithScoreByUsingElk(orgLocationId, recommendedCustomizeScore, requirementId,null);
	    }
		return candidateListCopy;

	}

	
	@PreAuthorize("hasAnyRole('ROLE_RECOMMENDATIONS')")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRecommendationsCountBasedOnRequirementId", method = RequestMethod.POST)
	public @ResponseBody String getRecommendationsCountBasedOnRequirementId(
			@RequestParam(value = "requirementId", required = false) Long requirementId, HttpServletRequest request)
			throws Exception {
		 String resultJson;
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long maxResults = candidateService.getCandidatesCountByOrgLocationID(orgLocation.getId(),null);
		 BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
		 boolQueryBuilder.must(QueryBuilders.matchQuery("status",1));
		 Long counts = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getTotalHits();
		resultJson = commonUtil.toJSON(counts);
		return resultJson;

	}
	
	
	/**
	 * @David Raju Sammeta
	 * 
	 *        This Method getRequirementWhichIsReleasedOrNot is Used to check
	 *        whether the requirement is released Or Not
	 * 
	 * @param requirementId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_CREAE_OFFER')")
	@RequestMapping(value = "/getRequirementWhichIsReleasedOrNot", method = RequestMethod.POST)
	public @ResponseBody String getRequirementWhichIsReleasedOrNot(
			@RequestParam(value = "requirementId", required = false) Long requirementId, HttpServletRequest request)
			throws Exception {
		String resultJson = null;

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		Boolean flag = requirementService.checkRequirementIsReleasedOrNot(requirementId, orgLocation.getId());
		resultJson = toJSON(flag);
		return resultJson;
	}

	/**
	 * prasad K This method is check job id duplicate or not
	 * 
	 * @param jobId
	 * @return
	 * @throws Exception
	 */


	 @RequestMapping(value = "/validateJobId",  method = RequestMethod.POST)
	    public @ResponseBody Boolean validateJobId(@RequestParam(value="jobId", required=false) String jobId,
	    		@RequestParam(value="requirementid", required=false) Long requirementid,HttpServletRequest request) throws Exception {
		       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		       Requirement requirement = null;
		       if(null != requirementid){
		    	   requirement  = requirementService.getRequirementByRequirementIdOrgLocationsId(jobId, requirementid, orgLocation.getId());
		       }
		        Boolean flag = false;
			    if(requirement == null){
			    	flag = requirementService.validateJobid(jobId, orgLocation.getId());
			    }
				return flag;
		    }
	 
	 
	 @RequestMapping(value = "/validateExistingJobId",  method = RequestMethod.POST)
	    public @ResponseBody Boolean validateExistingJobId(@RequestParam(value="jobId", required=false) String jobId,
	    		@RequestParam(value="requirementid", required=false) Long requirementid,HttpServletRequest request) throws Exception {
		       OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		        Boolean flag = requirementService.validateJobid(jobId, orgLocation.getId());
				return flag;
		    }

	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	 @RequestMapping(value = "/validatetemplateName",  method = RequestMethod.POST)
	    public @ResponseBody String validatejobName(@RequestParam(value="templateName", required=false) String templateName,HttpServletRequest request) throws Exception {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Boolean validatejobName = requirementService.validateRequirementTitle(templateName,orgLocation.getId());
				return validatejobName.toString();
		    }
	 
	 
	    @RequestMapping(value = "/getRequirementAndLookUpSkills",  method = RequestMethod.POST)
	    public @ResponseBody String getRequirementAndLookUpSkills(@RequestParam(value="requirementid", required=false) Long requirementid) throws Exception {
	    	
	    	List<String> allReqSkills = requirementService.getAllRequirementSkillsByRequirementI(requirementid);		
		  	List<String> skillsList = lookUpDataService.getAllSkillsBySkillCategory();
			if(null != allReqSkills  && !allReqSkills.isEmpty() && !allReqSkills.contains(null) && !allReqSkills.contains("")){
						String[] skills = allReqSkills.get(0).split(","); 
						for(String skill : skills){ 
							if(!skillsList.contains(skill)){
									skillsList.add(skill);
							}
						}  
					}
 	
	    	String resultJson; 
		  	resultJson = commonUtil.toJSON(skillsList);
			return resultJson;
		    }

	    
	    @RequestMapping(value = "/getCandidateHistoryList", method = RequestMethod.POST)
		public @ResponseBody String getCandidateHistoryList(
				@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request)
				throws Exception {
			String resultJson = null;
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementHistoryListByCandidateIdAndOrgLocationID(candidateId,orgLocation.getId());
			for(RequirementSubmission reqSubmission : reqList){
				if(null != reqSubmission.getRequirement().getJobTitle() && !"".equalsIgnoreCase(reqSubmission.getRequirement().getJobTitle())){
					reqSubmission.setRequirementName(reqSubmission.getRequirement().getJobTitle());	
				}
				reqSubmission.setRequirementJobId(reqSubmission.getRequirement().getJobId());
				reqSubmission.setRequirementStage(reqSubmission.getSubmissionStatus());
				if(null != reqSubmission.getRequirement().getSpocId()){
					StringBuilder clientName = new StringBuilder();
					 if(!"".equalsIgnoreCase(reqSubmission.getRequirement().getSpocId().getClientUser().getLastName()) && null != reqSubmission.getRequirement().getSpocId().getClientUser().getLastName() ){
						 reqSubmission.setClientName(clientName.append(reqSubmission.getRequirement().getSpocId().getClientUser().getFirstName()).append(" ").append(reqSubmission.getRequirement().getSpocId().getClientUser().getLastName()).toString());
					 }else{
						 reqSubmission.setClientName(clientName.append(reqSubmission.getRequirement().getSpocId().getClientUser().getFirstName()).toString());
					 }
				 }
			}
			resultJson = toJSON(reqList);
			return resultJson;
		}
	    
	    @RequestMapping(value = "/getEditRequirementDetails", method = RequestMethod.GET)
		public @ResponseBody String getEditRequirementDetails(@RequestParam(value="requirementId", required=false) String requirementId,HttpServletRequest request,HttpServletResponse response) throws Exception {
	    	
	    	Requirement requirement = null;
	    	if(null !=requirementId && "" !=requirementId && "undefined" !=requirementId){
	    	requirement =	requirementService.getRequirementDetailsById(Long.parseLong(requirementId));
	    	}
			return commonUtil.toJSON(requirement);
		} 
}