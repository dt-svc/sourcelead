package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.SubmissionTemplateDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

/**
 * 
 * @author prasad K
 * @date 13th sept 2016
 *
 */

@Service
public class RequirementServiceImpl implements RequirementService {

	private static final Logger logger = LoggerFactory.getLogger(RequirementServiceImpl.class);

	@Autowired
	RequirementDao requirementDao;

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private SubmissionTemplateDao submissionTemplateDao;
	
	@Autowired
	private RequirementAssignToService requirementAssignToService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	OrgService orgService;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	/**
	 * @author prasad k
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Requirement storeRequirement(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception {
		
		    Requirement requirement = new Requirement();
	        String client="";
	        String requirementClient="";
	        StringBuilder location = new StringBuilder();

		    
		    if (jsonObj.has("requirementId") && !(jsonObj.getString("requirementId").isEmpty())){
				requirement.setId(Long.parseLong(jsonObj.getString("requirementId")));
			}
		    
			if(orgLocations != null){
				requirement.setOrgLocationId(orgLocations);
			}
			
			if(jsonObj.has("jobTitle")){
				requirement.setJobTitle(jsonObj.getString("jobTitle"));
			}
			
			if(jsonObj.has("jobType")){
				requirement.setJobType(jsonObj.getString("jobType"));
			}
			
			if(jsonObj.has("jobId")){
			requirement.setJobId(jsonObj.getString("jobId"));
			}
			
			if(jsonObj.has("positions")){
			requirement.setPositions(jsonObj.getString("positions"));
			}
			if(jsonObj.has("country")){
			requirement.setCountry(jsonObj.getString("country"));
			location.append(jsonObj.getString("country"));
			location.append(",");
			}
			
			if(jsonObj.has("state")){
			requirement.setState(jsonObj.getString("state"));
			location.append(jsonObj.getString("state"));
			location.append(",");
			}
			
			if(jsonObj.has("city")){
			requirement.setCity(jsonObj.getString("city"));
			location.append(jsonObj.getString("city"));
			location.append(",");
			}
			
			if(jsonObj.has("maxExp")){
			requirement.setMaxExp(jsonObj.getString("maxExp"));
			}
			
			if(jsonObj.has("minExp")){
			requirement.setMinExp(jsonObj.getString("minExp"));
			}
			
			if(jsonObj.has("rate")){
			requirement.setRate(jsonObj.getString("rate"));
			}
			
			if(jsonObj.has("rateType")){
			requirement.setRateType(jsonObj.getString("rateType"));
			} 
			
			if(jsonObj.has("description")){
			requirement.setDescription(jsonObj.getString("description"));
			}
			
			
			if(jsonObj.has("currencyCode")){
			requirement.setCurrencyCode(jsonObj.getString("currencyCode"));
			}
			
			
			if(jsonObj.has("priority")){
				requirement.setPriority(jsonObj.getString("priority"));
			}
			
			if(jsonObj.has("isTemplate")){
			requirement.setIsTemplate(jsonObj.getString("isTemplate"));
			}
			
			if(jsonObj.has("skills")){
				
				String skillId =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("skills");
					skillId = jsonArray.toString();
				}
				catch(JSONException e){
					skillId =jsonObj.getString("skills");
				}
				skillId = skillId.replaceAll("\\[", "").replaceAll("\\]","");
				
				
				skillId = skillId.replaceAll("\"", "");
		  	   requirement.setSkills(skillId);
			
			}
			
			if(jsonObj.has("education")){
				
             String educationId =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("education");
					educationId = jsonArray.toString();
				}
				catch(JSONException e){
					educationId =jsonObj.getString("education");
				}
				educationId = educationId.replaceAll("\\[", "").replaceAll("\\]","");
				educationId = educationId.replaceAll("\"", "");
				
		  	  requirement.setEducation(educationId);
			
			 }
			
			 if(multiFile != null && multiFile.getOriginalFilename() != null && !multiFile.getOriginalFilename().isEmpty()){
				 requirement.setDocFile(multiFile.getBytes());
				 requirement.setFileName(multiFile.getOriginalFilename());
				 requirement.setFileContentType(multiFile.getContentType());
			 }else{
					requirement.setIsClientJob("N");
				}
			 
			if(jsonObj.has("isClient") && jsonObj.getString("isClient").equalsIgnoreCase("Y") && StringUtils.isNotEmpty(jsonObj.getString("isClient"))){
					requirement.setIsClientJob(jsonObj.getString("isClient"));
			}
		if(jsonObj.has("selectClient") && StringUtils.isNotEmpty(jsonObj.getString("selectClient")) &&  jsonObj.has("isClient") && jsonObj.getString("isClient").equalsIgnoreCase("Y")){
		requirementClient = (jsonObj.getString("isClient"));
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(Long.parseLong(jsonObj.getString("selectClient").split(",")[0]));
		requirement.setClientId(orgRelations);
		client = jsonObj.getString("selectClient").split(",")[0];
		
	}
			if(jsonObj.has("selectContact") && StringUtils.isNotEmpty(jsonObj.getString("selectContact"))){
				OrgSpoc orgSpoc = new OrgSpoc();
				orgSpoc.setId(Long.parseLong(jsonObj.getString("selectContact")));
				requirement.setSpocId(orgSpoc);
			}
			if(jsonObj.has("clientCurrencyCode") && StringUtils.isNotEmpty(jsonObj.getString("clientCurrencyCode"))){
				requirement.setClientCurrencyCode(jsonObj.getString("clientCurrencyCode"));
			}
		 	if(jsonObj.has("clientRate") && StringUtils.isNotEmpty(jsonObj.getString("clientRate"))){
		 		requirement.setClientRate(jsonObj.getString("clientRate"));
			}
			if(jsonObj.has("clientRateType") && StringUtils.isNotEmpty(jsonObj.getString("clientRateType"))){
				requirement.setClientRateType(jsonObj.getString("clientRateType"));
			} 
			if( jsonObj.has("isClient") && jsonObj.has("clientSubmissionForm")){
				
				if(jsonObj.getString("isClient").equalsIgnoreCase("Y") && jsonObj.has("isClient") && StringUtils.isNotEmpty(jsonObj.getString("isClient")) && jsonObj.has("clientSubmissionForm") && StringUtils.isNotEmpty(jsonObj.getString("clientSubmissionForm"))){

				SubmissionTemplate submissionTemplate = submissionTemplateDao.getSubmissionTemplateById(Long.parseLong(jsonObj.getString("clientSubmissionForm")));
				submissionTemplate.setId(Long.parseLong(jsonObj.getString("clientSubmissionForm")));
				
				requirement.setClientSubmissionTemplateId(submissionTemplate);
				requirement.setClientSubmissionTemplateJsonString(submissionTemplate.getJsonString());
				//saving submission Temmplate of Client to  InternalDetails Submission Template  defaultly 
				requirement.setSubmissionTemplateId(submissionTemplate);
				requirement.setSubmissionTemplateJsonString(submissionTemplate.getJsonString());
				
				}
			}else if(jsonObj.has("applicationFrom") && StringUtils.isNotEmpty(jsonObj.getString("applicationFrom"))){
				
				SubmissionTemplate submissionTemplate = submissionTemplateDao.getSubmissionTemplateById(Long.parseLong(jsonObj.getString("applicationFrom")));
				submissionTemplate.setId(Long.parseLong(jsonObj.getString("applicationFrom")));
				requirement.setSubmissionTemplateId(submissionTemplate);
				requirement.setSubmissionTemplateJsonString(submissionTemplate.getJsonString());
			}
			
			requirement.setStatus(Constants.REQUIREMENT_STATUS);
			requirement.setCreatedDate(commonUtil.currentDate());
			requirement.setCreatedBy(user.getUserCode());
			requirement.setUpdatedBy(user.getUserCode());
			requirement.setUpdatedDate(commonUtil.currentDate());
			
			//requirement status 
			if(jsonObj.has("saveType")){            
				
				if(jsonObj.getString("saveType").equalsIgnoreCase("draft")){
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.DRAFT);
				}
				if(jsonObj.getString("saveType").equalsIgnoreCase("save")){
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
				}
				if(jsonObj.getString("saveType").equalsIgnoreCase("submitForApproval")){
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED);
				}
				
			}
			
			//approval mechnism
			HashSet<RequirementApproverHierarchy> requirementApproverHierarchyList = new HashSet<RequirementApproverHierarchy>();
			//approval saving for sequential

              if(jsonObj.has("parallelApprover")){
				
				String parallelApprovers =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("parallelApprover");
					parallelApprovers = jsonArray.toString();
				}
				catch(JSONException e){
					parallelApprovers =jsonObj.getString("parallelApprover");
				}
				parallelApprovers = parallelApprovers.replaceAll("\\[", "").replaceAll("\\]","");
				parallelApprovers = parallelApprovers.replaceAll("\"", "");
				
			     String[] approvers = parallelApprovers.split(",");
			  	  
				  for(String approver : approvers){
					    
					    RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
					    User approverUser = userService.getUserById(Long.parseLong(approver));
						User approverUserId = new User();
						approverUserId.setId(Long.parseLong(approver));
						rah.setApproverUserId(approverUserId);
						  
						rah.setRequirementId(requirement);
						
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
						rah.setLevel(1);
						  
						rah.setCreatedDate(commonUtil.currentDate());
						rah.setCreatedBy(user.getUserCode());
						rah.setUpdatedBy(user.getUserCode());
						rah.setUpdatedDate(commonUtil.currentDate());
						  
						requirementApproverHierarchyList.add(rah);
						  approverEmailScenario(user,approverUser,requirement,location);

				  }
				 
			} 
			//approval saving for parallel
			if(jsonObj.has("sequentialApprover1") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover1"))){
				 String approverNames = jsonObj.getString("sequentialApprover1");
				  if(jsonObj.has("sequentialApprover1") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover1"))){
					  User approverUser = userService.getUserById(Long.parseLong(approverNames));
					  RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
					  
					  User approverUserId = new User();
					  approverUserId.setId(Long.parseLong(jsonObj.getString("sequentialApprover1")));
					  rah.setApproverUserId(approverUserId);
					  rah.setRequirementId(requirement);
					  
					  rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
					  rah.setLevel(1);
					  
					  rah.setCreatedDate(commonUtil.currentDate());
					  rah.setCreatedBy(user.getUserCode());
					  rah.setUpdatedBy(user.getUserCode());
					  rah.setUpdatedDate(commonUtil.currentDate());
					  
					  requirementApproverHierarchyList.add(rah);
						approverEmailScenario(user, approverUser ,requirement,location);

				  }
				  
				  if(jsonObj.has("sequentialApprover2") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover2"))){
					  RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
					  
					  User approverUserId = new User();
					  approverUserId.setId(Long.parseLong(jsonObj.getString("sequentialApprover2")));
					  rah.setApproverUserId(approverUserId);
					  
					  rah.setRequirementId(requirement);
					  
					  rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
					  rah.setLevel(2);
					  
					  rah.setCreatedDate(commonUtil.currentDate());
					  rah.setCreatedBy(user.getUserCode());
					  rah.setUpdatedBy(user.getUserCode());
					  rah.setUpdatedDate(commonUtil.currentDate());
					  
					  requirementApproverHierarchyList.add(rah);
				  }
				 
			} 
			requirement.setRequirementApprover(requirementApproverHierarchyList);
			
			
			if(jsonObj.has("manager") && StringUtils.isNotEmpty(jsonObj.getString("manager"))){
				    User managerUserId = new User();
				    managerUserId.setId(Long.parseLong(jsonObj.getString("manager")));
				    requirement.setManagerUserId(managerUserId);
			} 
			if(jsonObj.has("priority") && StringUtils.isNotEmpty(jsonObj.getString("priority"))){
				  requirement.setPriority(jsonObj.getString("priority"));
			} 
			
			
			if(requirement.getId() != null){
				requirementAssignToService.deleteRequirementAssignToByRequirementId(requirement.getId());
			}
			HashSet<RequirementAssignTo> requirementAssignToList = new HashSet<RequirementAssignTo>();
			if (jsonObj.has("assignedToMaxCount")){
				String assignedToMaxCount = jsonObj.getString("assignedToMaxCount");
				String assignedTeamId = jsonObj.getString("assignedTeamId");
				Long teamId = 0L;
				Team team = null;
			if (!"undefined".equalsIgnoreCase(assignedTeamId) && !"All".equalsIgnoreCase(assignedTeamId)
					&& !"Account Manager".equalsIgnoreCase(assignedTeamId)
					&& !"HR Manager".equalsIgnoreCase(assignedTeamId) && !"Recruiter".equalsIgnoreCase(assignedTeamId)
					&& !"Admin".equalsIgnoreCase(assignedTeamId)) {
				    teamId = Long.parseLong(assignedTeamId);
				     team = teamService.getTeamByTeamId(teamId);
				}
				if(StringUtils.isNotEmpty(assignedToMaxCount)){
				int MaxCount = Integer.parseInt(assignedToMaxCount);
					for (int c = 1; c < MaxCount; c++) {
						RequirementAssignTo  requirementAssignTo = new RequirementAssignTo();
						
						User assUser = new User();
						assUser.setId(Long.parseLong(jsonObj.getString("assignUserId"+c)));
						requirementAssignTo.setAssignUserId(assUser);
						
						requirementAssignTo.setRequirementId(requirement);
						
						requirementAssignTo.setCreatedDate(commonUtil.currentDate());
						requirementAssignTo.setCreatedBy(user.getUserCode());
						requirementAssignTo.setUpdatedBy(user.getUserCode());
						requirementAssignTo.setUpdatedDate(commonUtil.currentDate());
						requirementAssignTo.setTeamId(team);
						requirementAssignToList.add(requirementAssignTo);
						AssignToTeamMembersEmail(user, assUser, requirement,location);

					}
				}
			}
			requirement.setRequirementAssignTo(requirementAssignToList);
			
			
		Requirement savedRequirement = requirementDao.storeRequirement(requirement);
		mailRequirementtoManager(user,user,requirement,location);
		return savedRequirement;
	}

	@Override
	public Requirement addRequirement(Requirement requirement) throws Exception {
		return requirementDao.storeRequirement(requirement);
	}


	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get RequirementList by orgLocationId .
	 *  
	 * @param orgLocationId This is the first paramter to getRequirementListByOrgLocationID method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	*/

	@Override
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,Long  userId) throws Exception {
		return requirementDao.getRequirementListByOrgLocationID(orgLocationId,status,clients, userId);
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
	
	@Override
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {
		Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
		requirement.setRequirementCreatedDateToString(Constants.DF_YMD.format(requirement.getCreatedDate()));
		return requirement;
	}
	
	
	/**
	 * @author Venkat Kesav
	 * @since  oct 21 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get the Requirement is released for contact or not .
	 *  
	 * @return true or false 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception {
		Boolean flag = requirementDao.getRequirementByParentRequirementIdAndSpocId(requirementId, spocId);
		return flag;
	}
	
	
	/**
	 * @author prasad k
	 * @since  sep 14 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get template status by orgLocationId .
	 *  
	 * @param requirementId This is the first paramter to getJobTemplateStatusByJobTitleAndRequirementId method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */

	@Override
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId) throws Exception {
		return requirementDao.getJobTitleByJobTemplateStatusAndOrgLocationId(orgLocationId);
	}

	@Override
	public List<RequirementAssignTo> getUserAssignJobCount(Long userId) throws Exception {
		return requirementDao.getUserAssignJobCount(userId);
	}


	@Override
	public void approverEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(approverUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(location!= null){
			 globalVarsMap.put("requirementLocation", location.toString());
			 }else{
				 globalVarsMap.put("requirementLocation","--");
			 }
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 globalVarsMap.put("requirementRate", requirement.getRate());
			 }else{
				 globalVarsMap.put("requirementRate","--");
			 }
		if(requirement.getClientId()!=null){
			 globalVarsMap.put("client", requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
		}else{
			globalVarsMap.put("client","--");
			 }
		if(approverUser.getFirstName()!= null && !"".equalsIgnoreCase(approverUser.getFirstName())){
			globalVarsMap.put("approverName", approverUser.getFirstName());
		}else{
			globalVarsMap.put("approverName","--");
		}
		if(emailMessage.getLastName()!=null && !"".equalsIgnoreCase(emailMessage.getLastName())){
			globalVarsMap.put("lastName",emailMessage.getLastName());	
		}else{
			globalVarsMap.put("lastName","--");
		}
		if(loginUser.getFirstName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName())){
			globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
			}else{
			globalVarsMap.put("jobInitiatorName","--");
		}
		if(approverUser.getEmail()!=null && !"".equalsIgnoreCase(approverUser.getEmail())){
			globalVarsMap.put("userEmailId",approverUser.getEmail());
			}else{
				globalVarsMap.put("userEmailId","--");
		}
		 globalVarsMap.put("link","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
		 if(loginUser.getFirstName()+" "+loginUser.getLastName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName()+" "+loginUser.getLastName())){
			 globalVarsMap.put("loginUserName",loginUser.getFirstName()+" "+loginUser.getLastName());
			 }else{
				 globalVarsMap.put("loginUserName","--");
			}
		 if(loginUser.getUserType()!=null && !"".equalsIgnoreCase(loginUser.getUserType())){
			 globalVarsMap.put("role", loginUser.getUserType());
			 }else{
				 globalVarsMap.put("role","--");
			}
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());
	
}
	
	
	
	@Override
	public void approverTwoEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(approverUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("requirementName", requirement.getJobTitle());
		 if(location != null){
		 globalVarsMap.put("requirementLocation", location.toString());
		 }else{
		 globalVarsMap.put("requirementLocation","--");
		 }
		 if(requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
		 globalVarsMap.put("requirementRate", requirement.getRate());
		 }else{
		 globalVarsMap.put("requirementRate","--");
		 }
		 if(requirement.getClientId()!= null){
		 globalVarsMap.put("client", requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
		 }else{
		 globalVarsMap.put("client","--");
		 }
		 if(approverUser.getFirstName()!= null && !"".equalsIgnoreCase(approverUser.getFirstName())){
		 globalVarsMap.put("approverName", approverUser.getFirstName());
		 }else{
		 globalVarsMap.put("approverName","--");
		 }
		 if(loginUser.getFirstName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName())){
		 globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
		 }else{
		 globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());	 
		 }
		 globalVarsMap.put("link","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
		 globalVarsMap.put("loginUserName",loginUser.getFirstName()+" "+loginUser.getLastName());
		 globalVarsMap.put("role", loginUser.getUserType());
		 globalVarsMap.put("approverOne", loginUser.getFirstName());
		 
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());

	}

	@SuppressWarnings("unused")
	@Override
	public void acceptEmailScenario(User loginUser, User intiatorUser, Requirement requirement) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(intiatorUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(requirement.getJobTitle()!= null  && !"".equalsIgnoreCase(requirement.getJobTitle())){
		 globalVarsMap.put("requirementName", requirement.getJobTitle()); 
		 }else{
		 globalVarsMap.put("requirementName", "--");	 
		 }
		 if( requirement.getJobId()!= null  && !"".equalsIgnoreCase( requirement.getJobId())){
		 globalVarsMap.put("requirementId-Name", requirement.getJobId());	 
		 }else{
	     globalVarsMap.put("requirementId-Name","--"); 
		 }
		 if(loginUser.getFirstName()!= null  && !"".equalsIgnoreCase(loginUser.getFirstName())){
		 globalVarsMap.put("approverName", loginUser.getFirstName());	 
		 }else{
		 globalVarsMap.put("approverName","--"); 
		 }
		 if(loginUser.getFirstName()!= null  && !"".equalsIgnoreCase(loginUser.getFirstName())){
		 globalVarsMap.put("jobInitiatorName", loginUser.getFirstName()); 
		 }else{
		 globalVarsMap.put("jobInitiatorName","--"); 
		 }
		 if(loginUser.getFirstName()+" "+loginUser.getLastName()!= null  && !"".equalsIgnoreCase( loginUser.getFirstName()+" "+loginUser.getLastName())){
		 globalVarsMap.put("loginUserName",loginUser.getFirstName()+" "+loginUser.getLastName());
		 }else{
		 globalVarsMap.put("loginUserName","--"); 
		 }
		 if(loginUser.getUserType()!= null  && !"".equalsIgnoreCase(loginUser.getUserType())){
		 globalVarsMap.put("role", loginUser.getUserType());
		 }else{
		 globalVarsMap.put("role","--"); 
		 }
		 
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVED_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());

	}

	
	@SuppressWarnings("unused")
	@Override
	public void mailRequirementtoManager(User loginUser, User managerUser, Requirement requirement,StringBuilder location) throws Exception {
	
		 managerUser = userService.getUserById(requirement.getManagerUserId().getId());
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(managerUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(managerUser.getFirstName()!= null  && !"".equalsIgnoreCase(managerUser.getFirstName())){
			 globalVarsMap.put("OwnerName", managerUser.getFirstName());
			 }else{
				 globalVarsMap.put("OwnerName","--"); 
			 }
		 if(loginUser.getFirstName()!= null  && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("JobInitiator", loginUser.getFirstName());
			 }else{
				 globalVarsMap.put("JobInitiator","--"); 
			 }
		 if(requirement.getJobTitle()!= null  && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("RequirementTitle","--"); 
			 }
		 if(location!= null){
			 globalVarsMap.put("RequirementLocation", location.toString());
			 }else{
				 globalVarsMap.put("RequirementLocation","--"); 
			 }
		 if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
			 globalVarsMap.put("RequirementRate", requirement.getRate());
			 }else{
				 globalVarsMap.put("RequirementRate","--"); 
			 }
		 if(requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp())){
			 globalVarsMap.put("Experience", requirement.getMaxExp());
			 }else{
				 globalVarsMap.put("Experience","--"); 
			 }
		 if(loginUser.getFirstName()+" "+loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()+" "+loginUser.getLastName())){
			 globalVarsMap.put("loginUserName",loginUser.getFirstName()+" "+loginUser.getLastName());
			 }else{
				 globalVarsMap.put("loginUserName","--"); 
			 }
		 if(loginUser.getUserType()!= null && !"".equalsIgnoreCase(loginUser.getUserType())){
			 globalVarsMap.put("role", loginUser.getUserType());
			 }else{
				 globalVarsMap.put("role","--"); 
			 }
		 
		 globalVarsMap.put("link","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");

		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAILREQUIREMENTTOMANAGER,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());

	}

	@SuppressWarnings("unused")
	@Override
	public void AssignToTeamMembersEmail(User loginUser, User assUser, Requirement requirement,StringBuilder location) throws Exception {
			assUser= userService.getUserById(assUser.getId());

		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(assUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(assUser.getFirstName()!= null && !"".equalsIgnoreCase(assUser.getFirstName())){
			 globalVarsMap.put("OwnerName", assUser.getFirstName());
			 }else{
				 globalVarsMap.put("OwnerName","--"); 
			 }
		 if( requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("RequirementTitle","--"); 
			 }
		 if(location!= null){
			 globalVarsMap.put("RequirementLocation", location.toString());
			 }else{
				 globalVarsMap.put("RequirementLocation","--"); 
			 }
		 if( requirement.getRate()!= null && !"".equalsIgnoreCase(requirement.getRate())){
			 globalVarsMap.put("RequirementRate", requirement.getRate());
			 }else{
				 globalVarsMap.put("RequirementRate","--"); 
			 }
		 if( requirement.getMaxExp()!= null && !"".equalsIgnoreCase( requirement.getMaxExp())){
			 globalVarsMap.put("Experience", requirement.getMaxExp());
			 }else{
				 globalVarsMap.put("Experience","--"); 
			 }
		 if( loginUser.getFirstName()+" "+loginUser.getLastName()!= null ||!"".equalsIgnoreCase( loginUser.getFirstName()+" "+loginUser.getLastName())){
			 globalVarsMap.put("LoginUser",loginUser.getFirstName()+" "+loginUser.getLastName());
			 }else{
				 globalVarsMap.put("LoginUser","--"); 
			 }
		 if( loginUser.getUserType() != null && !"".equalsIgnoreCase(loginUser.getUserType())){
			 globalVarsMap.put("Role", loginUser.getUserType());
			 }else{
				 globalVarsMap.put("Role","--"); 
			 }
		 globalVarsMap.put("link","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");

		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ASSIGNTEAMMEMBERSFORREQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());

	}


	@Override
	public void rejectedEmailScenario(User loginUser, User approverUser, Requirement requirement,
			StringBuilder location, String requirementClientName, String client, String rejectedReason)
			throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(approverUser.getEmail());
		emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		emailMessage.setFromName(loginUser.getFirstName() + " " + loginUser.getLastName());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (rejectedReason != null && !"".equalsIgnoreCase(rejectedReason)) {
			globalVarsMap.put("reason", rejectedReason);
		} else {
			globalVarsMap.put("reason", "--");
		}
		if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getUserName())) {
			globalVarsMap.put("loginUserName", loginUser.getFirstName());
		} else {
			globalVarsMap.put("loginUserName", "--");
		}
		if (approverUser.getFirstName() + " " + approverUser.getLastName() != null
				&& !"".equalsIgnoreCase(approverUser.getFirstName() + " " + approverUser.getLastName()))  {
			globalVarsMap.put("approverName", approverUser.getFirstName() + " " + approverUser.getLastName());
		} else {
			globalVarsMap.put("approverName", "--");
		}
		if (loginUser.getUserType() != null && !"".equalsIgnoreCase(loginUser.getUserType())) {
			globalVarsMap.put("role", loginUser.getUserType());
		} else {
			globalVarsMap.put("role", "--");
		}
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			globalVarsMap.put("requirementId-Name", requirement.getJobId());
		} else {
			globalVarsMap.put("requirementId-Name", "--");
		}
		if (loginUser.getUserName() != null && !"".equalsIgnoreCase(loginUser.getUserName()) ) {
			globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
		} else {
			globalVarsMap.put("jobInitiatorName", "--");
		}

		// setting the Recipient information
		ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		EmailRecipient emailRecipient = new EmailRecipient();
		emailRecipient.setEmail(emailMessage.getToEmail());
		emailRecipient.setName(emailMessage.getFirstName() + " " + emailMessage.getLastName());
		emailRecipientList.add(emailRecipient);

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.REJECTED_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_EMAIL);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = "";

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, new Org());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, emailRecipientList, new ArrayList<EmailAttachment>());
	}

	@Override
	public void releaseMailScenario(User loginUser, Requirement requirement, OrgSpoc orgSpoc,OrgLocations loginOrgLocation,String releasetype) throws Exception {
		User  vendorContactUser = null;
		
		if(releasetype.equalsIgnoreCase("Vendors")){
			vendorContactUser = userService.getUserById(orgSpoc.getOwnerUser().getId());

		}else if(releasetype.equalsIgnoreCase("CONTACTS")){
			vendorContactUser = userService.getUserById(orgSpoc.getSpocUser().getId());
		}
	 
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(vendorContactUser.getEmail());
		 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
		 emailMessage.setFromName(loginUser.getFirstName()+" "+loginUser.getLastName());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle()) ) {
			 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
			 } else {
				 globalVarsMap.put("RequirementTitle", "--");
			}
		 if (requirement.getCountry() != null && !"".equalsIgnoreCase(requirement.getCountry()) ) {
			 globalVarsMap.put("RequirementLocation",  requirement.getCountry());
			 } else {
				 globalVarsMap.put("RequirementLocation", "--");
			}
		 if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()) ) {
			 globalVarsMap.put("RequirementRate", requirement.getRate());
			 } else {
				 globalVarsMap.put("RequirementRate", "--");
			}
		 if (requirement.getClientId() != null) {
			 globalVarsMap.put("clientname", requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
			 } else {
				 globalVarsMap.put("clientname", "--");
			}
		 if (vendorContactUser.getFirstName() +""+vendorContactUser.getLastName()!= null && !"".equalsIgnoreCase(vendorContactUser.getFirstName() +""+vendorContactUser.getLastName())) {
			 globalVarsMap.put("contactname", vendorContactUser.getFirstName() +""+vendorContactUser.getLastName());
			 } else {
				 globalVarsMap.put("contactname", "--");
			}
		 if (requirement.getMaxExp()!= null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			 globalVarsMap.put("Experience", requirement.getMaxExp());
			 } else {
				 globalVarsMap.put("Experience", "--");
			}
		 globalVarsMap.put("link","<a href='"+sourceleadUrl+"/"+"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
		
		 if (loginUser.getFirstName()+" "+loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()+" "+loginUser.getLastName())) {
			 globalVarsMap.put("loginUserName",loginUser.getFirstName()+" "+loginUser.getLastName());
			 } else {
				 globalVarsMap.put("loginUserName", "--");
			}
		 
		 if (loginOrgLocation.getOrg().getOrgName() != null && !"".equalsIgnoreCase(loginOrgLocation.getOrg().getOrgName() )) {
			 globalVarsMap.put("LoginOrgName",loginOrgLocation.getOrg().getOrgName() );
			 } else {
				 globalVarsMap.put("LoginOrgName", "--");
			}
		
		 //setting the Recipient information
		 ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
		 emailRecipientList.add(emailRecipient);
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.RELEASEMECHANISM_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = "";
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, new Org());
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,new ArrayList<EmailAttachment>());

		
	}
}
