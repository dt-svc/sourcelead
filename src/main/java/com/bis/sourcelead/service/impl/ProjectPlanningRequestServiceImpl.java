package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.ProjectPlanningNewResourceRequestDao;
import com.bis.sourcelead.dao.ProjectPlanningRequestDao;
import com.bis.sourcelead.dao.ProjectPlanningResourceRequestDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.AssociateRequestDTO;
import com.bis.sourcelead.dto.ProjectResourceRequestDTO;
import com.bis.sourcelead.dto.ResourceRequestDTO;
import com.bis.sourcelead.dto.ResourcesDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class ProjectPlanningRequestServiceImpl implements ProjectPlanningRequestService{
	
	@Autowired 
	ProjectsDao projectsDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ProjectPlanningRequestDao projectPlanningRequestDao;
	
	@Autowired
	RolesServiceImpl rolesServiceImpl;

	@Autowired
	ProjectPlanningResourceRequestDao projectPlanningResourceRequestDao;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 CandidateDao candidateDao;
	 
	 @Autowired
	 UserRoleDao userRoleDao;

	@Autowired
	ProjectPlanningNewResourceRequestDao projectPlanningNewResourceRequestDao;
	
	protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}

	@Override
	public void saveProjectPlanningRequest(ProjectResourceRequestDTO  resourceRequestDTO, User loginUser, OrgLocations orgLocations) throws JSONException, Exception {
		
		ProjectPlanningRequest projectPlanningRequest  = null;
		if(StringUtils.isNotEmpty(resourceRequestDTO.getSavedPlanningRequestUUID())){
			projectPlanningRequest = projectPlanningRequestDao.getProjectPlanningRequestByRequestUUID(resourceRequestDTO.getSavedPlanningRequestUUID());
		}else {
			projectPlanningRequest = new ProjectPlanningRequest();
			projectPlanningRequest.setRequestType(Constants.RESOURCE_REQUEST_ASSOCIATE_REQUEST);
		}
		 
		if(StringUtils.isNotEmpty(resourceRequestDTO.getRequestId())){
			projectPlanningRequest.setRequestId(resourceRequestDTO.getRequestId());
		}
		
		if(!isNullOrEmpty(resourceRequestDTO.getProjectUUID())){
			Projects projects = projectsDao.getProjectByProjectUUID(resourceRequestDTO.getProjectUUID());
			projectPlanningRequest.setProjectId(projects);
			projectPlanningRequest.setProjectName(projects.getProjectName());
		}
//		System.out.println(resourceRequestDTO.getAssociateResources().get(1).getPercentageOfWork());

		if(!isNullOrEmpty(resourceRequestDTO.getDescription())){
			projectPlanningRequest.setDescription(resourceRequestDTO.getDescription());
		}
		
		
		
		if(!isNullOrEmpty(resourceRequestDTO.getEstimatedBudgetCurrencyType())){
			projectPlanningRequest.setEstimatedBudgetCurrency(resourceRequestDTO.getEstimatedBudgetCurrencyType());
		}
		
		
		//projectPlanningRequest
		
		projectPlanningRequest.setOrgLocationId(orgLocations);
		projectPlanningRequest.setRequestedUser(loginUser.getFullName());
		projectPlanningRequest.setCreatedBy(loginUser);
		projectPlanningRequest.setUpdatedBy(loginUser);
		projectPlanningRequest.setStatus(Constants.RECORD_ACTIVE);
		projectPlanningRequest.setRequestUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		projectPlanningRequest.setCreatedDate(new Date());
		projectPlanningRequest.setUpdatedDate(new Date());
		
		List<Long> resourceRequestIds = null;
		List<Long> approvedResources = new ArrayList<>();
		Double approvedResourcesEstBudget = (double) 0;
		Double approvedResourcesEstHours = (double) 0;
		BigDecimal approvedResourcesPercentageOfWork = new BigDecimal(0);
		if(projectPlanningRequest.getId() != null){
			 resourceRequestIds = projectPlanningResourceRequestDao.getProjectPlanningResourceRequestIdsByRequestId(projectPlanningRequest.getId());
			 Set<ProjectPlanningResourceRequest> approvedResourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
			 for(ProjectPlanningResourceRequest resourceRequest : approvedResourceRequestList){
				 if(!isNullOrEmpty(resourceRequest.getResourceStatus()) && resourceRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
					 approvedResources.add(resourceRequest.getId());
					 approvedResourcesEstBudget = approvedResourcesEstBudget+resourceRequest.getEstimatedBudget();
					 approvedResourcesEstHours = approvedResourcesEstHours+resourceRequest.getEstimatedHours();
					 approvedResourcesPercentageOfWork = approvedResourcesPercentageOfWork.add(resourceRequest.getPercentageOfWork());
				 }
			 }
		}
		
		Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = getResourceRequestList(resourceRequestDTO,loginUser,projectPlanningRequest,resourceRequestIds,orgLocations);
		projectPlanningRequest.setProjectPlanningResourceRequestList(projectPlanningResourceRequestList);
		projectPlanningRequest.setRequestedResources(projectPlanningResourceRequestList.size()+approvedResources.size());
		projectPlanningRequest.setAllocatedResources(0);
		
		if(!isNullOrEmpty(resourceRequestDTO.getEstimatedHrs())){
				projectPlanningRequest.setEstimatedHours(Double.valueOf(resourceRequestDTO.getEstimatedHrs())+approvedResourcesEstHours);
			}
			
		if(!isNullOrEmpty(resourceRequestDTO.getEstimatedBudget())){
				projectPlanningRequest.setEstimatedBudget(Double.valueOf(resourceRequestDTO.getEstimatedBudget())+approvedResourcesEstBudget);
			}
		
		
		projectPlanningRequest.setPercentageOfWork(resourceRequestDTO.getPercentageOfWork().add(approvedResourcesPercentageOfWork));
		
/*		if(resourceRequestDTO.getAssociateResources().get(1).getPercentageOfWork() != null) {
			projectPlanningRequest.setPercentageOfWork(resourceRequestDTO.getAssociateResources().get(1).getPercentageOfWork());
		}*/
		
		if(!isNullOrEmpty(resourceRequestDTO.getSaveType()) && resourceRequestDTO.getSaveType().equalsIgnoreCase("Draft")){
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_DRAFT);
		}else{
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
		}
		
	
		projectPlanningRequestDao.saveProjectPlanningRequest(projectPlanningRequest);
		
		if(null != resourceRequestIds && resourceRequestIds.size() > 0){
			for(Long resourceRequestId : resourceRequestIds){
				ProjectPlanningResourceRequest resourceRequest = projectPlanningResourceRequestDao.getProjectPlanningResourceRequestById(resourceRequestId);
				projectPlanningResourceRequestDao.deleteProjectPlanningResourceRequest(resourceRequest);
			}
		}
		
		if(!isNullOrEmpty(resourceRequestDTO.getSaveType()) && !resourceRequestDTO.getSaveType().equalsIgnoreCase("Draft")){
				sendMailToApprover(projectPlanningRequest,loginUser,orgLocations);	
		}
		
		
		
		
	}
	
	public void sendMailToApprover(ProjectPlanningRequest projectPlanningRequest,User loginUser,OrgLocations orgLocations) throws Exception{
		
		Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
		 for(ProjectPlanningResourceRequest planningResourceRequest : projectPlanningResourceRequestList){
			 
			   EmailMessage emailMessage = new EmailMessage();
		        emailMessage.setFirstName(loginUser.getFirstName());
		        emailMessage.setLastName(loginUser.getLastName());
		        emailMessage.setToEmail(planningResourceRequest.getApproverUser().getEmail());
		        emailMessage.setFromEmail(loginUser.getEmail());
		        emailMessage.setCreatedBy(loginUser.getUserCode());
		        emailMessage.setFromName(projectPlanningRequest.getRequestedUser());
		        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		       
		        Map<String, String> globalVarsMap = new HashMap<String, String>();
		        globalVarsMap.put("projectName", projectPlanningRequest.getProjectName());
		        globalVarsMap.put("approverName", planningResourceRequest.getApproverName());
		        globalVarsMap.put("loginUserName", loginUser.getFullName());
		        globalVarsMap.put("loginOrgName", orgLocations.getOrgLocationName());
		        DecimalFormat df = new DecimalFormat("0.##");    
		        globalVarsMap.put("estimatedHours", String.valueOf(df.format(planningResourceRequest.getEstimatedHours())));
		        globalVarsMap.put("estimatedBudget", planningResourceRequest.getEstimatedBudgetCurrency()+" "+planningResourceRequest.getEstimatedBudget());
		        StringBuilder linkBuild = new StringBuilder();
		        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		        
		       
		        globalVarsMap.put("resourceCount", "1");
		        String resourceTable = getRequestedResourcesTable(planningResourceRequest);
		        globalVarsMap.put("resourceTable", resourceTable);
		        
		        EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
		        
		        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		        notificationScenario.setBody(notificationbody);
		        
				commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.RESOURCE_REQUEST,new ArrayList<EmailAttachment>());

		        StringBuilder notificationLink = new StringBuilder();
		        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("requestedAssociatesList?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
		         
		        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getApproverUser(), orgLocations, notificationScenario,new HashMap<String,String>());
		 }
		
		
	}
	
	
	private String getRequestedResourcesTable(ProjectPlanningResourceRequest planningResourceRequest) throws Exception{
		 String tempHTML = null;
		 tempHTML = "<table style='display:table-cell;vertical-align:middle'>";
		 tempHTML = tempHTML+"<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Resource Name<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Pay Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Bill Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Start Date<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>End Date<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Estimated Hours<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Estimated Budget<span></th>";
		 tempHTML = tempHTML+"</tr>";
		 
				 
			 tempHTML = tempHTML+"<tr align='center'>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getCandidateName()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getPayRateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getBillRateStr()+"</td>";
			 if(planningResourceRequest.getStartDateStr() != null) {
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getStartDateStr()+"</td>";
			 }else {
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>--</td>";
			 }
			 if(planningResourceRequest.getEndDateStr() != null) {
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getEndDateStr()+"</td>";
			 }else {
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>--</td>";
			 }
			
			 if(planningResourceRequest.getEstimatedHours() != null && planningResourceRequest.getEstimatedHours() > 0) {
				 DecimalFormat df = new DecimalFormat("0.##");
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+df.format(BigDecimal.valueOf(planningResourceRequest.getEstimatedHours()))+"</td>";
			 }else {
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>--</td>";

			 }
			 if(planningResourceRequest.getEstimatedBudget() != null && planningResourceRequest.getEstimatedBudget() > 0) {
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+planningResourceRequest.getEstimatedBudgetCurrency()+" "+BigDecimal.valueOf(planningResourceRequest.getEstimatedBudget())+"</td>";
			 }else {
				 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>--</td>";
			 }
			 tempHTML = tempHTML+"</tr>";
			 
		 tempHTML = tempHTML+"</table>";
		 
		 return tempHTML;
	}
	
	
	@Override
	public void saveProjectNewResourceRequest(ResourceRequestDTO  resourceRequestDTO, User loginUser, OrgLocations orgLocations) throws JSONException, Exception {
		
		ProjectPlanningRequest projectPlanningRequest  = null;
		if(StringUtils.isNotEmpty(resourceRequestDTO.getSavedPlanningRequestUUID())){
			projectPlanningRequest = projectPlanningRequestDao.getProjectPlanningRequestByRequestUUID(resourceRequestDTO.getSavedPlanningRequestUUID());
		}else{
			projectPlanningRequest = new ProjectPlanningRequest();
			projectPlanningRequest.setRequestType(Constants.RESOURCE_REQUEST_NEW_REQUEST);
		}
		
		if(resourceRequestDTO.getRequestId() != null ){
			projectPlanningRequest.setRequestId(resourceRequestDTO.getRequestId());
		}
		
		if(!isNullOrEmpty(resourceRequestDTO.getProjectUUID())){
			Projects projects = projectsDao.getProjectByProjectUUID(resourceRequestDTO.getProjectUUID());
			projectPlanningRequest.setProjectId(projects);
			projectPlanningRequest.setProjectName(projects.getProjectName());
		}
		if(resourceRequestDTO.getDescription() != null){
			projectPlanningRequest.setDescription(resourceRequestDTO.getDescription());
		}
		if(resourceRequestDTO.getApprover() != null){
			User approverUser = userDao.getUserById(Long.valueOf(resourceRequestDTO.getApprover()));
			projectPlanningRequest.setApproverUser(approverUser);
			projectPlanningRequest.setApproverName(approverUser.getFullName());
			
		}
		
		if(resourceRequestDTO.getTotalResourcesValue() > 0){
			projectPlanningRequest.setRequestedResources(resourceRequestDTO.getTotalResourcesValue());
		}
		
		if(resourceRequestDTO.getTotalEstHours() != null){
			projectPlanningRequest.setEstimatedHours(Double.valueOf(resourceRequestDTO.getTotalEstHours()));
		}
		
		if(resourceRequestDTO.getTotalEstBudget() != null){
			projectPlanningRequest.setEstimatedBudget(Double.valueOf(resourceRequestDTO.getTotalEstBudget()));
		}
		
		if(resourceRequestDTO.getEstimatedBudgetCurrencyType() != null){
			projectPlanningRequest.setEstimatedBudgetCurrency(resourceRequestDTO.getEstimatedBudgetCurrencyType());
		}
		
		projectPlanningRequest.setOrgLocationId(orgLocations);
		projectPlanningRequest.setRequestedUser(loginUser.getFullName());
		projectPlanningRequest.setCreatedBy(loginUser);
		projectPlanningRequest.setUpdatedBy(loginUser);
		projectPlanningRequest.setStatus(Constants.RECORD_ACTIVE);
		projectPlanningRequest.setRequestUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		projectPlanningRequest.setCreatedDate(new Date());
		projectPlanningRequest.setUpdatedDate(new Date());
		
		List<Long> resourceRequestIds = null;
		if(projectPlanningRequest.getId() != null){
			 resourceRequestIds = projectPlanningNewResourceRequestDao.getProjectPlanningNewResourceRequestIdsByRequestId(projectPlanningRequest.getId());
		}
		
		Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = getNewResourceRequestList(resourceRequestDTO,loginUser,projectPlanningRequest,resourceRequestIds,orgLocations);
		projectPlanningRequest.setProjectPlanningNewResourceRequestList(projectPlanningNewResourceRequestList);
		projectPlanningRequest.setAllocatedResources(0);
		
		if(!isNullOrEmpty(resourceRequestDTO.getSaveType()) && resourceRequestDTO.getSaveType().equalsIgnoreCase("Draft")){
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_DRAFT);
		}else{
			projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
		}

		projectPlanningRequestDao.saveProjectPlanningRequest(projectPlanningRequest);
		
		if(null != resourceRequestIds && resourceRequestIds.size() > 0){
			for(Long resourceRequestId : resourceRequestIds){
				ProjectPlanningNewResourceRequest newResourceRequest = projectPlanningNewResourceRequestDao.getProjectPlanningNewResourceRequestById(resourceRequestId);
				projectPlanningNewResourceRequestDao.deleteProjectPlanningNewResourceRequest(newResourceRequest);
			}
		}
		
		if(!isNullOrEmpty(resourceRequestDTO.getSaveType()) && !resourceRequestDTO.getSaveType().equalsIgnoreCase("Draft")){
			sendMailToNewResourceRequestApprover(projectPlanningRequest,loginUser,orgLocations);
		}
		
	}
	
	
public void sendMailToNewResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest,User loginUser,OrgLocations orgLocations) throws Exception{
		
		EmailMessage emailMessage = new EmailMessage();
        emailMessage.setFirstName(loginUser.getFirstName());
        emailMessage.setLastName(loginUser.getLastName());
        emailMessage.setToEmail(projectPlanningRequest.getApproverUser().getEmail());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromEmail(loginUser.getEmail());
        emailMessage.setCreatedBy(loginUser.getUserCode());
        emailMessage.setFromName(projectPlanningRequest.getRequestedUser());
        emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
       
        Map<String, String> globalVarsMap = new HashMap<String, String>();
        globalVarsMap.put("projectName", projectPlanningRequest.getProjectName());
        globalVarsMap.put("approverName", projectPlanningRequest.getApproverName());
        globalVarsMap.put("loginUserName", loginUser.getFullName());
        globalVarsMap.put("loginOrgName", orgLocations.getOrgLocationName());
        DecimalFormat df = new DecimalFormat("0.##");  
        globalVarsMap.put("estimatedHours", String.valueOf(df.format(BigDecimal.valueOf(projectPlanningRequest.getEstimatedHours()))));
        globalVarsMap.put("estimatedBudget", projectPlanningRequest.getEstimatedBudgetCurrency()+" "+BigDecimal.valueOf(projectPlanningRequest.getEstimatedBudget()));
        StringBuilder linkBuild = new StringBuilder();
        globalVarsMap.put("loginLink",linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
        
        Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList = projectPlanningRequest.getProjectPlanningNewResourceRequestList();
        globalVarsMap.put("resourceCount", String.valueOf(projectPlanningNewResourceRequestList.size()));
        String resourceTable = getNewRequestedResourcesTable(projectPlanningNewResourceRequestList);
        globalVarsMap.put("resourceTable", resourceTable);
        
        EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.NEW_RESOURCE_REQUEST_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);
        
        String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
        notificationScenario.setBody(notificationbody);
        commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.NEW_RESOURCE_REQUEST,new ArrayList<EmailAttachment>());
        StringBuilder notificationLink = new StringBuilder();
        String actionLink = notificationLink.append(sourceleadUrl).append("/").append("viewNewResourceRequestDashborad?requestUUID="+projectPlanningRequest.getRequestUUID()).toString();
         
        emailScenerioUtil.sendNotification(actionLink, loginUser, projectPlanningRequest.getApproverUser(), orgLocations, notificationScenario,new HashMap<String,String>());
	}
	
private String getNewRequestedResourcesTable(Set<ProjectPlanningNewResourceRequest> projectPlanningNewResourceRequestList) throws Exception{
	 String tempHTML = null;
	tempHTML = " <table style='display:table-cell;vertical-align:middle;font-family:arial; font-size:12px;' width='900' cellpadding='5'>";
	tempHTML = tempHTML+"<thead>";
	tempHTML = tempHTML+"<tr align='center'>";
	tempHTML = tempHTML+"<th style='width:150px; background:#375d81; color:#fff;'>Role Name</th>";
	tempHTML = tempHTML+"<th style='width:50px; background:#375d81; color:#fff;'>No Res.</th>";
	tempHTML = tempHTML+"<th style='width:100px; background:#375d81; color:#fff;'>Experience</th>";
	tempHTML = tempHTML+"<th style='width:175px; background:#375d81; color:#fff;'>Primary Sills</th>";
	tempHTML = tempHTML+"<th style='width:240px; background:#375d81; color:#fff;'>Bill / Pay Rate</th>";
	tempHTML = tempHTML+"<th style='width:65px; background:#375d81; color:#fff;'>Duration</th>";
	tempHTML = tempHTML+"<th style='width:50px; background:#375d81; color:#fff;'>Consulting Type</th>";
	tempHTML = tempHTML+"<th style='width:65px; background:#375d81; color:#fff;'>Estimated Hours</th>";
	tempHTML = tempHTML+"<th style='width:65px; background:#375d81; color:#fff;'>Estimated Budget</th>";
	tempHTML = tempHTML+"</tr>";
	tempHTML = tempHTML+"</thead>";
	tempHTML = tempHTML+"<tbody>";
		
	 for(ProjectPlanningNewResourceRequest planningResourceRequest : projectPlanningNewResourceRequestList){
		 String starDate = "";
		 String endDate = "";
		 String billRate = "";
		 String payRate = "";
		if(planningResourceRequest.getStartDateStr() != null && planningResourceRequest.getStartDateStr() !=""){
			starDate = planningResourceRequest.getStartDateStr();
		}else{
			starDate = "--";
		}
		if(planningResourceRequest.getEndDateStr() != null && planningResourceRequest.getEndDateStr() !=""){
			endDate = planningResourceRequest.getEndDateStr();
		}else{
			endDate = "--";
		}
		if(planningResourceRequest.getBillRate() != null){
			billRate = planningResourceRequest.getBillRate();
		}else{
			billRate = "--";
		}
		if(planningResourceRequest.getPayRate() != null){
			payRate = planningResourceRequest.getPayRate();
		}else{
			payRate = "--";
		}
	 
	 tempHTML = tempHTML+" <tr align='center'>";
	 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top; vertical-align:top;'>"+planningResourceRequest.getRoleName()+"</td>";
	 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getNoOfResource()+"</td>";
	 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>";
	 tempHTML = tempHTML+"		<div style='float:left; width:80px;'>";
	 if(planningResourceRequest.getMinExperience() != null && planningResourceRequest.getMaxExperience() != null ) {
		 tempHTML = tempHTML+"			<div style='float:left; width:25px; text-align:center;'>"+planningResourceRequest.getMinExperience()+" To </div>";
		 tempHTML = tempHTML+"			<div style='float:left; width:25px; text-align:center;'>"+planningResourceRequest.getMaxExperience()+" Year(s)</div>";
	 }else if(planningResourceRequest.getMinExperience() != null && planningResourceRequest.getMaxExperience() == null){
		 tempHTML = tempHTML+"			<div style='float:left; width:60px; text-align:center;'>"+planningResourceRequest.getMinExperience()+" Year(s) </div>";
	 }else if(planningResourceRequest.getMinExperience() == null && planningResourceRequest.getMaxExperience() != null){
		 tempHTML = tempHTML+"			<div style='float:left; width:60px; text-align:center;'>"+planningResourceRequest.getMaxExperience()+" Year(s)</div>";
	 }else if(planningResourceRequest.getMinExperience() == null && planningResourceRequest.getMaxExperience() == null){
		 tempHTML = tempHTML+"			<div style='float:left; width:60px; text-align:center;'>--</div>";
	 }
	 tempHTML = tempHTML+"		</div>";
	 tempHTML = tempHTML+"	</td>";
	 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getSkills()+"</td>";
	 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>";
	 tempHTML = tempHTML+"		<div style='float:left; width:25px; font-size:12px; margin-top: 8px;'>"+planningResourceRequest.getRateCurrency()+"</div>";
	 tempHTML = tempHTML+"		<div style='float:left; width:85px;'>";
	 tempHTML = tempHTML+"			<div style='float:left; width:85px; text-align:center;'>"+billRate+"</div>";
	 tempHTML = tempHTML+"			<div style='float:left; width:85px; text-align:center;'>"+payRate+"</div>";
	 tempHTML = tempHTML+"		</div>";
	 tempHTML = tempHTML+"		<div style='float:left; width:65px; font-size:12px; margin-top: 8px;overflow: hidden; white-space: nowrap; text-overflow: ellipsis;' title='Semi Weekly'>"+planningResourceRequest.getRateType()+"</div>";
	 tempHTML = tempHTML+"	</td>";
	 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>";
	 tempHTML = tempHTML+"			<div style='width:65px; text-align:center;'>"+starDate+"</div>";
	 tempHTML = tempHTML+"			<div style='width:65px; text-align:center;'>"+endDate+"</div>";
	 tempHTML = tempHTML+"	</td>";
	 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+planningResourceRequest.getConsultingType()+"</td>";
	 if(planningResourceRequest.getEstimatedHours() != null) {
		 DecimalFormat df = new DecimalFormat("0.##");  
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+df.format(BigDecimal.valueOf(planningResourceRequest.getEstimatedHours()))+"</td>";
	 }else {
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>--</td>";

	 }
	 if(planningResourceRequest.getEstimatedBudget() != null) {
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>"+BigDecimal.valueOf(planningResourceRequest.getEstimatedBudget())+"</td>";
	 }else {
		 tempHTML = tempHTML+"	<td style='background:#f2f2f2; vertical-align:top;'>--</td>";

	 }
	 tempHTML = tempHTML+"	</tr>";
	 }
	 tempHTML = tempHTML+"</tbody>";
	 tempHTML = tempHTML+"</table>";
	 
	 return tempHTML;
}

	private Set<ProjectPlanningResourceRequest> getResourceRequestList(ProjectResourceRequestDTO resourceRequestDTO, User loginUser,ProjectPlanningRequest projectPlanningRequest,List<Long> resourceRequestIds,OrgLocations orgLocations) throws Exception {
		
		 Set<ProjectPlanningResourceRequest> resourceRequestSet = new HashSet<>();
		 List<Long> updatedResourceRequests = new ArrayList<Long>();
		 if(resourceRequestDTO.getAssociateResources() != null && !resourceRequestDTO.getAssociateResources().isEmpty()) {
		 for(AssociateRequestDTO associateRequestDTO:resourceRequestDTO.getAssociateResources()) {
			 if(StringUtils.isEmpty(associateRequestDTO.getCandidateId()))
				 continue;
			  ProjectPlanningResourceRequest resourceRequest = new ProjectPlanningResourceRequest();
			  
			  if(!isNullOrEmpty(associateRequestDTO.getResourceRequestId())){
					 resourceRequest.setId(Long.valueOf(associateRequestDTO.getResourceRequestId()));
					 ProjectPlanningResourceRequest savedresourceRequest = projectPlanningResourceRequestDao.getProjectPlanningResourceRequestById(Long.valueOf(associateRequestDTO.getResourceRequestId()));
					 resourceRequest.setRequestUUID(savedresourceRequest.getRequestUUID());
					 resourceRequest.setRejectedReason(savedresourceRequest.getRejectedReason());
					 if(!isNullOrEmpty(savedresourceRequest.getResourceStatus()) && savedresourceRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
						 resourceRequest.setResourceStatus(savedresourceRequest.getResourceStatus());
					 }
					 updatedResourceRequests.add(Long.valueOf(associateRequestDTO.getResourceRequestId()));
				 }else{
					 resourceRequest.setRequestUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				 }
			  
			  
				 Candidate candidate = candidateDao.getCandidateByCandidateId(Long.valueOf(associateRequestDTO.getCandidateId()));
				 resourceRequest.setCandidateId(candidate);
				 
				 if(!isNullOrEmpty(associateRequestDTO.getCandidateFullName())){
					 resourceRequest.setCandidateName(associateRequestDTO.getCandidateFullName());
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getCandidatePayRate())){
					 resourceRequest.setPayRate(Double.valueOf(associateRequestDTO.getCandidatePayRate()));
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getCandidatePayRateCurrency())){
					 resourceRequest.setPayRateCurrency(associateRequestDTO.getCandidatePayRateCurrency());
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getCandidatePayRateType())){
					 resourceRequest.setPayRateType(associateRequestDTO.getCandidatePayRateType());
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getBillRate())){
					 resourceRequest.setBillRate(Double.valueOf(associateRequestDTO.getBillRate()));
				 }else{
					 resourceRequest.setBillRate(null);
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getBillRateCurrency())){
					 resourceRequest.setBillRateCurrency(associateRequestDTO.getBillRateCurrency());
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getBillRateRateType())){
					 resourceRequest.setBillRateType(associateRequestDTO.getBillRateRateType());
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getRequestStartDate())){
					 Date requestStartDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),associateRequestDTO.getRequestStartDate());
					 resourceRequest.setStartDate(requestStartDate);
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getRequestEndDate())){
					 Date requestEndDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),associateRequestDTO.getRequestEndDate());
					 resourceRequest.setEndDate(requestEndDate);
				 }else{
					 resourceRequest.setEndDate(null);
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getConsultingType())){
					 resourceRequest.setConsultingType(associateRequestDTO.getConsultingType());
				 }else{
					 resourceRequest.setConsultingType(null);
				 }
				 
				 if(!isNullOrEmpty(associateRequestDTO.getEstHours())){
					 resourceRequest.setEstimatedHours(Double.valueOf(associateRequestDTO.getEstHours()));
				 }else{
					 resourceRequest.setEstimatedHours(null);
				 }
				 
				
				 if(!isNullOrEmpty(associateRequestDTO.getEstimatedBudgetCurrencyType())){
					 resourceRequest.setEstimatedBudgetCurrency(associateRequestDTO.getEstimatedBudgetCurrencyType());
				 }
				 
				 
				 if(!isNullOrEmpty(associateRequestDTO.getEstBudget())){
					 resourceRequest.setEstimatedBudget(Double.valueOf(associateRequestDTO.getEstBudget()));
				 }else{
					 resourceRequest.setEstimatedBudget(null);
				 }
				 
			     if(!isNullOrEmpty(resourceRequestDTO.getSaveType()) && resourceRequestDTO.getSaveType().equalsIgnoreCase("Draft")){
					 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_DRAFT);
				 }else{
					 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
				}
			     
			     
			     if(candidate.getConsultantUser() != null){
			    	    UserRole userRole = userRoleDao.getUserDetailsByUserIdAndLocationId(candidate.getConsultantUser().getId(), candidate.getOrgLocationId().getId()); 
			    	    if(userRole.getResourceManager() != null) {
			    	    	resourceRequest.setApproverUser(userRole.getResourceManager());
				    	    resourceRequest.setApproverName(userRole.getResourceManager().getFullName());
			    	    }else {
			    	    	resourceRequest.setApproverUser(orgLocations.getUser());
				    	    resourceRequest.setApproverName(orgLocations.getUser().getFullName());
			    	    }
			    	    
						
					}
			     
			     if(!isNullOrEmpty(resourceRequestDTO.getSaveType()) && resourceRequestDTO.getSaveType().equalsIgnoreCase("Draft")){
			    	 	resourceRequest.setRequestStatus(Constants.RESOURCE_REQUEST_DRAFT);
					}else{
						resourceRequest.setRequestStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
					}
			     
			     resourceRequest.setPercentageOfWork(associateRequestDTO.getPercentageOfWork());
			     
				 resourceRequest.setOrgLocationId(orgLocations);
				 resourceRequest.setProjectPlanningRequestId(projectPlanningRequest);
				 resourceRequest.setStatus(Constants.RECORD_ACTIVE);
				 resourceRequest.setCreatedBy(loginUser);
				 resourceRequest.setUpdatedBy(loginUser);
				 resourceRequest.setCreatedDate(new Date());
				 resourceRequest.setUpdatedDate(new Date());
				 resourceRequestSet.add(resourceRequest);
		    }
		 }
		 
		 if(null != projectPlanningRequest.getId()){
			 updatedResourceRequests.retainAll(resourceRequestIds);
			 resourceRequestIds.removeAll(updatedResourceRequests);
		 }
		 
		 return resourceRequestSet;
	}

	
	private Set<ProjectPlanningNewResourceRequest> getNewResourceRequestList(ResourceRequestDTO resourceRequestDTO, User loginUser,ProjectPlanningRequest projectPlanningRequest,List<Long> resourceRequestIds, OrgLocations orgLocations) throws Exception {
		 Set<ProjectPlanningNewResourceRequest> resourceRequestSet = new HashSet<>();
		 List<Long> updatedNewResourceRequests = new ArrayList<Long>();
		 if(resourceRequestDTO.getPlanningResources() != null && !resourceRequestDTO.getPlanningResources().isEmpty()) {
			 for(ResourcesDTO resourcesDTO:resourceRequestDTO.getPlanningResources()) {
				 if(StringUtils.isEmpty(resourcesDTO.getNoOfResources()))
					 continue;
				 ProjectPlanningNewResourceRequest resourceRequest = new ProjectPlanningNewResourceRequest();
				 resourceRequest.setNoOfResource(Integer.parseInt(resourcesDTO.getNoOfResources()));
				 if(StringUtils.isNotEmpty(resourcesDTO.getResourceRequestId())){
					 resourceRequest.setId(Long.valueOf(resourcesDTO.getResourceRequestId()));
					 
					 ProjectPlanningNewResourceRequest savedNewResourceRequest = projectPlanningNewResourceRequestDao.getProjectPlanningNewResourceRequestById(Long.valueOf(resourcesDTO.getResourceRequestId()));
					 resourceRequest.setRequestUUID(savedNewResourceRequest.getRequestUUID());
					 resourceRequest.setRejectedReason(savedNewResourceRequest.getRejectedReason());
					 
					 if(!isNullOrEmpty(savedNewResourceRequest.getResourceStatus()) && savedNewResourceRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
						 resourceRequest.setResourceStatus(savedNewResourceRequest.getResourceStatus());
					 }
					 updatedNewResourceRequests.add(Long.valueOf(resourcesDTO.getResourceRequestId()));	 
				 }else{
					 resourceRequest.setRequestUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				 }
				 
				 
				 if(StringUtils.isNotEmpty(resourcesDTO.getRoleId()) && resourcesDTO.getRoleId().equalsIgnoreCase("Other")){
						resourceRequest.setRoleId(null);
						 resourceRequest.setRoleName(resourcesDTO.getRoleId());
						 if(StringUtils.isNotEmpty(resourcesDTO.getCustomRole())){
							 resourceRequest.setOtherRole(resourcesDTO.getCustomRole());
						 }
						 
					}else if(StringUtils.isNotEmpty(resourcesDTO.getRoleId())){
						if(!resourcesDTO.getRoleId().equalsIgnoreCase("Other")){
							Roles roles = rolesServiceImpl.getRolesById(Long.parseLong(resourcesDTO.getRoleId()));
							 resourceRequest.setRoleId(roles);
							 resourceRequest.setRoleName(roles.getDisplayName());	
						}
					 }
				 
				 if(StringUtils.isNotEmpty(resourcesDTO.getMin())){
					 resourceRequest.setMinExperience(Integer.parseInt(resourcesDTO.getMin()));
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getMax())){
					 resourceRequest.setMaxExperience(Integer.parseInt(resourcesDTO.getMax()));
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getPrimarySkill())){
					 resourceRequest.setSkills(resourcesDTO.getPrimarySkill());
				 }
				 
				 if(StringUtils.isNotEmpty(resourcesDTO.getCurrencyCode())){
					 resourceRequest.setRateCurrency(resourcesDTO.getCurrencyCode());
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getBillRate())){
					 resourceRequest.setBillRate(resourcesDTO.getBillRate());
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getPayRate())){
					 resourceRequest.setPayRate(resourcesDTO.getPayRate());
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getRateType())){
					 resourceRequest.setRateType(resourcesDTO.getRateType());
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getStartDate())){
					 Date startDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),resourcesDTO.getStartDate());
					 resourceRequest.setStartDate(startDate);
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getEndDate())){
					 Date endDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),resourcesDTO.getEndDate());
					 resourceRequest.setEndDate(endDate);
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getEstimatedHours())){
					 resourceRequest.setEstimatedHours(Double.parseDouble(resourcesDTO.getEstimatedHours()));
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getEstimatedBudget())){
					 resourceRequest.setEstimatedBudget(Double.parseDouble(resourcesDTO.getEstimatedBudget()));
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getBudgetCurrency())){
					 resourceRequest.setEstimatedBudgetCurrency(resourcesDTO.getBudgetCurrency());
				 }
				 if(StringUtils.isNotEmpty(resourcesDTO.getConsulting())){
					 resourceRequest.setConsultingType(resourcesDTO.getConsulting());
				 }
				 
				 if(!isNullOrEmpty(resourceRequestDTO.getSaveType()) && resourceRequestDTO.getSaveType().equalsIgnoreCase("Draft")){
						resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_DRAFT);
					}else{
						resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL);
					}
				 
				 resourceRequest.setProjectPlanningRequestId(projectPlanningRequest);
				 resourceRequest.setStatus(Constants.RECORD_ACTIVE);
				 resourceRequest.setCreatedBy(loginUser);
				 resourceRequest.setOrgLocationId(orgLocations);
				 resourceRequest.setUpdatedBy(loginUser);
				 resourceRequest.setCreatedDate(new Date());
				 resourceRequest.setUpdatedDate(new Date());
				 resourceRequestSet.add(resourceRequest);
				 
			 }
		 }
		 if(null != projectPlanningRequest.getId()){
			 updatedNewResourceRequests.retainAll(resourceRequestIds);
			 resourceRequestIds.removeAll(updatedNewResourceRequests);
		 }
		 return resourceRequestSet;
	}
	@Override
	public List<ProjectPlanningRequest> getProjectPlanningRequestByProjectId(Long projectId) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByProjectId(projectId);
	}

	@Override
	public Integer getProjectPlanningRequestCountByProjectId(Long projectId) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestCountByProjectId(projectId);
	}

	@Override
	public ProjectPlanningRequest getProjectPlanningRequestByRequestUUID(String requestUUID) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByRequestUUID(requestUUID);
	}

	@Override
	public ProjectPlanningRequest getProjectPlanningRequestById(Long id) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestById(id);
	}

	@Override
	public List<ProjectPlanningRequest> getProjectPlanningRequestByApproverId(Long approverUserId, Long orgLocationId,String requestStatus,String userRole)
			throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByApproverId(approverUserId, orgLocationId,requestStatus,userRole);
	}

	@Override
	public ProjectPlanningRequest storeProjectPlanningRequest(ProjectPlanningRequest projectPlanningRequest)
			throws Exception {
		return projectPlanningRequestDao.saveProjectPlanningRequest(projectPlanningRequest);
	}

	@Override
	public List<ProjectPlanningRequest> getProjectPlanningRequestByApproverIdAndOrgLocationId(Long approverUserId,
			Long orgLocationId,String userRole) throws Exception {
		return projectPlanningRequestDao.getProjectPlanningRequestByApproverIdAndOrgLocationId(approverUserId, orgLocationId,userRole);
	}

}
