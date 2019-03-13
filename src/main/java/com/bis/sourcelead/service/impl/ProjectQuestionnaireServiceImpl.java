package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.ProjectQuestionnaireDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ProjectAdditionalDetailsDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.ProjectQuestionnaire;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectQuestionnaireService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class ProjectQuestionnaireServiceImpl implements ProjectQuestionnaireService{
	
	@Autowired
	ProjectQuestionnaireDao projectQuestionnaireDao;
	
	@Autowired
	ProjectsDao projectsDao;
	
	@Autowired
	ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	@Autowired
	ProjectClientService projectClientService;
	
	@Autowired
	UserDao userDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	    
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserRoleService userRoleService;
	    
	
    protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	 }

	@Override
	public void saveProjectQuestionnaireDetails(ProjectAdditionalDetailsDTO projectAdditionalDetailsDTO,Long projectId, User user,
			OrgLocations orgLocations) throws Exception {
		
		ProjectQuestionnaire questionnarie = null;
		questionnarie = getProjectQuestionnaireByProjectId(projectId);
		
		if(questionnarie == null){
			questionnarie = new ProjectQuestionnaire();
			questionnarie.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
		Projects projects = projectsDao.getProjectById(projectId);
		questionnarie.setProjectId(projects);
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getProjectLocation())){
			questionnarie.setProjectLocation(projectAdditionalDetailsDTO.getProjectLocation());
		}else{
			questionnarie.setProjectLocation(null);
		}
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getProjectLocation())){
			questionnarie.setIndustry(projectAdditionalDetailsDTO.getIndustry());
			if(projectAdditionalDetailsDTO.getIndustry().equalsIgnoreCase("Others")){
				questionnarie.setIndustryOtherType(projectAdditionalDetailsDTO.getOtherIndustry());
			}else{
				questionnarie.setIndustryOtherType(null);
			}
		}else{
			questionnarie.setIndustry(null);
			questionnarie.setIndustryOtherType(null);
		}
		
		if(!isNullOrEmpty(projectAdditionalDetailsDTO.getScope())){
			questionnarie.setProjectScope(projectAdditionalDetailsDTO.getScope());
			if(projectAdditionalDetailsDTO.getScope().equalsIgnoreCase("Others")){
				questionnarie.setProjectOtherScope(projectAdditionalDetailsDTO.getOtherScope());
			}else{
				questionnarie.setProjectOtherScope(null);
			}
		}else{
			questionnarie.setProjectScope(null);
			questionnarie.setProjectOtherScope(null);
		}
		
	   if(projectAdditionalDetailsDTO.getCategory() != null && projectAdditionalDetailsDTO.getCategory().size() > 0){
	    	StringBuilder categoryBuilder = new StringBuilder();
	    	for(String category : projectAdditionalDetailsDTO.getCategory()){
	    		categoryBuilder.append(category.trim()).append(",");
	    	}
	    	String categoryStr = categoryBuilder.toString();
	    	
	    	questionnarie.setEmploymentCategory(categoryBuilder.substring(0, categoryStr.length()-1)); 
	    }else{
	    	questionnarie.setEmploymentCategory(null);
	    }
	   
	   if(!isNullOrEmpty(projectAdditionalDetailsDTO.getTeamSize())){
		   questionnarie.setTeamSize(Integer.parseInt(projectAdditionalDetailsDTO.getTeamSize()));
	   }
	   
	   
	   if(projectAdditionalDetailsDTO.getFrontEnd() != null && projectAdditionalDetailsDTO.getFrontEnd().size() > 0){
	    	StringBuilder frontEndBuilder = new StringBuilder();
	    	for(String frontEnd : projectAdditionalDetailsDTO.getFrontEnd()){
	    		frontEndBuilder.append(frontEnd.trim()).append(",");
	    	}
	    	String frontEndStr = frontEndBuilder.toString();
	    	questionnarie.setFrontEnd(frontEndBuilder.substring(0, frontEndStr.length()-1)); 
	    }else{
	    	questionnarie.setFrontEnd(null);
	    }
	   
	   
	   if(projectAdditionalDetailsDTO.getBackEnd() != null && projectAdditionalDetailsDTO.getBackEnd().size() > 0){
	    	StringBuilder backEndBuilder = new StringBuilder();
	    	for(String backEnd : projectAdditionalDetailsDTO.getBackEnd()){
	    		backEndBuilder.append(backEnd.trim()).append(",");
	    	}
	    	String backEndStr = backEndBuilder.toString();
	    	questionnarie.setBackEnd(backEndBuilder.substring(0, backEndStr.length()-1)); 
	    }else{
	    	questionnarie.setBackEnd(null);
	    }
	   
	   if(projectAdditionalDetailsDTO.getProjectDatabase() != null && projectAdditionalDetailsDTO.getProjectDatabase().size() > 0){
	    	StringBuilder dataBaseBuilder = new StringBuilder();
	    	for(String database : projectAdditionalDetailsDTO.getProjectDatabase()){
	    		dataBaseBuilder.append(database.trim()).append(",");
	    	}
	    	String dataBaseStr = dataBaseBuilder.toString();
	    	questionnarie.setDataBase(dataBaseBuilder.substring(0, dataBaseStr.length()-1)); 
	    }else{
	    	questionnarie.setDataBase(null);
	    }
	   
	   

	   if(projectAdditionalDetailsDTO.getOtherTechnologies() != null && projectAdditionalDetailsDTO.getOtherTechnologies().size() > 0){
	    	StringBuilder otherTechBuilder = new StringBuilder();
	    	for(String otherTech : projectAdditionalDetailsDTO.getOtherTechnologies()){
	    		otherTechBuilder.append(otherTech.trim()).append(",");
	    	}
	    	String otherTechStr = otherTechBuilder.toString();
	    	questionnarie.setOtherTechnologies(otherTechBuilder.substring(0, otherTechStr.length()-1)); 
	    }else{
	    	questionnarie.setOtherTechnologies(null);
	    }
	   
	   
	   if(projectAdditionalDetailsDTO.getTestManagement() != null && projectAdditionalDetailsDTO.getTestManagement().size() > 0){
	    	StringBuilder testManagementBuilder = new StringBuilder();
	    	for(String teamManagement : projectAdditionalDetailsDTO.getTestManagement()){
	    		testManagementBuilder.append(teamManagement.trim()).append(",");
	    	}
	    	String testManagementStr = testManagementBuilder.toString();
	    	questionnarie.setTestManagement(testManagementBuilder.substring(0, testManagementStr.length()-1)); 
	    }else{
	    	questionnarie.setTestManagement(null);
	    }
		
	   if(!isNullOrEmpty(projectAdditionalDetailsDTO.getExecutionModel())){
		   questionnarie.setExecutionModel(projectAdditionalDetailsDTO.getExecutionModel());
	   }else{
		   questionnarie.setExecutionModel(null);
	   }
	   

	   if(projectAdditionalDetailsDTO.getAgileMethodologies() != null && projectAdditionalDetailsDTO.getAgileMethodologies().size() > 0){
	    	StringBuilder agileMethodologiesBuilder = new StringBuilder();
	    	for(String agileMethod : projectAdditionalDetailsDTO.getAgileMethodologies()){
	    		agileMethodologiesBuilder.append(agileMethod.trim()).append(",");
	    	}
	    	String ageilMethodsStr = agileMethodologiesBuilder.toString();
	    	questionnarie.setAgileMethodologies(agileMethodologiesBuilder.substring(0, ageilMethodsStr.length()-1)); 
	    }else{
	    	questionnarie.setAgileMethodologies(null);
	    }
	   questionnarie.setOrgLocationId(orgLocations);
	   questionnarie.setCreatedBy(user.getUserCode());
	   questionnarie.setCreatedDate(new Date());
	   questionnarie.setUpdatedBy(user.getUserCode());
	   questionnarie.setUpdatedDate(new Date());
	   
	   saveProjectQuestionnaire(questionnarie);
	   
	   projects.setIsProjectAdditionalDetailsFilled(Constants.YES);
	   if(!isNullOrEmpty(projectAdditionalDetailsDTO.getMailToManagers()) && 
			   projectAdditionalDetailsDTO.getMailToManagers().equalsIgnoreCase(Constants.YES)){
		   projects.setIsMailToProjectManager(Status.Y);
	   }else{
		   projects.setIsMailToProjectManager(Status.N);
	   }
	  
	   if(!isNullOrEmpty(projectAdditionalDetailsDTO.getMailToTeamMembers()) && 
			   projectAdditionalDetailsDTO.getMailToTeamMembers().equalsIgnoreCase(Constants.YES)){
		   projects.setIsMailToTeamMembers(Status.Y);
	   }else{
		   projects.setIsMailToTeamMembers(Status.N);
	   }
	   
	   if(projectAdditionalDetailsDTO.getSaveType().equalsIgnoreCase("Finish")){
		   projects.setProjectMode("Finished");
	   }
	   
	   projectsDao.saveProject(projects);
	   
	   if(projects.getProjectMode().equalsIgnoreCase("Finished")){
		   
		   
		   List<ProjectMembers> projectMembersList = null;
		   
		   if(projects.getIsMailToProjectManager().equals(Status.Y) || 
				   projects.getIsMailToTeamMembers().equals(Status.Y)){
			   projectMembersList = projectMembersDao.getAllProjectMembersByProjectId(projects.getId());
		   }
		  
		   
		   if(projects.getIsMailToProjectManager().equals(Status.Y)){
			   String teamMembersTable = "";
			   if(projectMembersList != null && projectMembersList.size() > 0){
				   teamMembersTable = getTeamMembersTable(projectMembersList);
			   }else{
				   teamMembersTable = "";
			   }
			   List<ProjectApproverHierarchy> projectApproverHierarchiesList = projectApproverHierarchyDao.getApproverUsersByProjectId(projects.getId());
			   if(projectApproverHierarchiesList != null && projectApproverHierarchiesList.size() > 0){
				   for(ProjectApproverHierarchy approverHierarchy : projectApproverHierarchiesList){
						   sendEmailToProjectManager(projects, user, approverHierarchy.getApproverUserId(), orgLocations, orgLocations.getOrg().getOrgCode(),teamMembersTable);
				   }
			   }
		   }
		   
		   if(projects.getIsMailToTeamMembers().equals(Status.Y)){
			   List<ProjectApproverHierarchy> approverHierarchy = projectApproverHierarchyDao.getApproverUsersByProjectId(projects.getId());
			   StringBuilder managers = new StringBuilder();
			  
			   for (ProjectApproverHierarchy projectApproverHierarchy : approverHierarchy) {
				 StringBuilder fullname=new StringBuilder();
				 if(projectApproverHierarchy.getApproverUserId().getLastName()!=null && projectApproverHierarchy.getApproverUserId().getLastName()!="")
				   managers=managers.append(fullname.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" ").append(projectApproverHierarchy.getApproverUserId().getLastName())).append(" & ");
				 else
					 managers=managers.append(projectApproverHierarchy.getApproverUserId().getFirstName()).append(" & ");
			}
			  String projectManagers=managers.toString();
			  String projectmanagerss= managers.substring(0, projectManagers.length()-2);
		 
			 
			   if(projectMembersList != null && projectMembersList.size() > 0){
				   for(ProjectMembers projectMembers : projectMembersList){
			        	if(orgLocations.getId().longValue() == projectMembers.getProjectMemberCandidteId().getOrgLocationId().getId()) {
					        sendEmailToTeamMembers(projects, projectMembers, user, projectmanagerss, orgLocations, orgLocations.getOrg().getOrgCode());
			        	}
				   }
			   }
		   }
		   
	   }
	
	}
	
	
	
	
	private String getTeamMembersTable(List<ProjectMembers> projectMembersList) throws Exception{
		
		
		 String tempHTML = null;
		 
		 
		 tempHTML = "<table style='display:table-cell;vertical-align:middle'>";
		 tempHTML = tempHTML+"<tr align='center'>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Resource Name<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Pay Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Bill Rate<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>Start Date<span></th>";
		 tempHTML = tempHTML+"<th style='padding:5px 10px' width='20%' bgcolor='#5bace8'><span>End Date<span></th>";
		 tempHTML = tempHTML+"</tr>";
		 
		 for(ProjectMembers projectMembers : projectMembersList){
			
			 String endDate = "";
			 if(projectMembers.getEndDateStr() != null && !projectMembers.getEndDateStr().isEmpty()){
				 endDate = projectMembers.getEndDateStr(); 
			 }else{
				 endDate = "-----";
			 }
			 
			 tempHTML = tempHTML+"<tr align='center'>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+projectMembers.getCandidateName()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+projectMembers.getPayRateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+projectMembers.getBillRateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+projectMembers.getStartDateStr()+"</td>";
			 tempHTML = tempHTML+"<td style='padding:5px 10px' bgcolor='#f2f2f2'>&nbsp;"+endDate+"</td>";
			 tempHTML = tempHTML+"</tr>";
			 
		 }
		 tempHTML = tempHTML+"</table>";
		 
		 return tempHTML;
	}

	@Override
	public ProjectQuestionnaire saveProjectQuestionnaire(ProjectQuestionnaire projectQuestionnaire) throws Exception {
		return projectQuestionnaireDao.saveProjectQuestionnaire(projectQuestionnaire);
	}

	@Override
	public ProjectQuestionnaire getProjectQuestionnaireByProjectId(Long projectId) throws Exception {
		return projectQuestionnaireDao.getProjectQuestionnaireByProjectId(projectId);
	}

	
	
	
	private void sendEmailToTeamMembers(Projects projects, ProjectMembers projectMembers, User loginUser, String managers, OrgLocations loginOrgLocation, String loginOrgCode) throws Exception {
	  	 StringBuilder fromNameBuilder=new StringBuilder();
	     EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(projectMembers.getProjectMemberCandidteId().getFirstName());
		 emailMessage.setLastName(projectMembers.getProjectMemberCandidteId().getLastName());
		 emailMessage.setToEmail(projectMembers.getProjectMemberCandidteId().getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 //setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 StringBuilder sb=new StringBuilder();
		 String name="";
		 if(loginUser.getLastName()!=null)
		 {
		 name=sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString();
		 }
		 else
		 {
		name= sb.append(loginUser.getFirstName()).toString();
		 }
		 globalVarsMap.put("LoginUserFirstName",name);
//	 globalVarsMap.put("loginUserLastName",loginUser.getLastName());
		 globalVarsMap.put("managersName",managers.toString());
//		globalVarsMap.put("managerLastName",projectManager.getLastName());
		 globalVarsMap.put("EmployeeFirstName",emailMessage.getFirstName());
//		 globalVarsMap.put("employeeLastName",emailMessage.getLastName());
		 globalVarsMap.put("ProjectName", projects.getProjectName());
		 globalVarsMap.put("OrgName",loginOrgLocation.getOrgLocationName());
		 globalVarsMap.put("userStartDate",projectMembers.getStartDateStr());
		 globalVarsMap.put("projectEndDate",projectMembers.getEndDateStr());
		 StringBuilder loginURLBuilder=new StringBuilder();
		 globalVarsMap.put("loginURL",loginURLBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 
		 //calling the sendEmail method of emailScenerioUtil
		 if(null != projects.getOrgLocationsId())
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_TEAM_MEMBERS,new ArrayList<EmailAttachment>());
		 
		//calling the sendNotification method of emailScenerioUtil
		EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_TEAM_MEMBERS,Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		//String url = null;
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("projectSummary?projectUUID="+projects.getProjectUUID()+"&projectClientUUID="+projectMembers.getProjectClientUUID()).toString();
	
		emailScenerioUtil.sendNotification(actionLink, loginUser, projectMembers.getProjectMemberCandidteId().getConsultantUser(), loginOrgLocation, notificationScenario,new HashMap<String,String>());
	}
	
	
	  private void sendEmailToProjectManager(Projects projects, User loginUser, User projectManager, OrgLocations loginOrgLocation, String loginOrgCode,String teamMembersTable) throws Exception {
		  
		  	UserRole userRole = userRoleService.getUserByUserId(projectManager.getId());
		  
		     ProjectClients pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(),projects.getOrgLocationsId().getId());
		  	 StringBuilder fromNameBuilder=new StringBuilder();
		     EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(projectManager.getFirstName());
			 emailMessage.setLastName(projectManager.getLastName());
			 emailMessage.setToEmail(projectManager.getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 emailMessage.setCreatedBy(loginUser.getUserCode());
			 emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
			
			//URL for page redirection 
			String redirectURL = "/projectSummary-params-projectUUID="+projects.getProjectUUID()+"-and-projectClientUUID="+pc.getUuid();
			Long redirectOrgLocationId = userRole.getOrgLocations().getId();
			 
			 //setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("LoginUserFirstName",loginUser.getFirstName());
			 globalVarsMap.put("LoginUserLastName",loginUser.getLastName());
			 globalVarsMap.put("ManagerFirstName",projectManager.getFirstName());
			 globalVarsMap.put("ManagerLastName",projectManager.getLastName());
			 globalVarsMap.put("TeamMembers",teamMembersTable);
			 globalVarsMap.put("ProjectName", projects.getProjectName());
			 globalVarsMap.put("OrgName",loginOrgLocation.getOrgLocationName());
			 	 
			 StringBuilder loginURLBuilder=new StringBuilder();			 
			 globalVarsMap.put("loginURL",loginURLBuilder.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append
					 ("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Project</div></a>").toString());
			
			 //calling the sendEmail method of emailScenerioUtil
			 if(null != projects.getOrgLocationsId())
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,loginOrgLocation.getOrg(),loginOrgLocation,Constants.MAIL_PROJECT_MANAGER,new ArrayList<EmailAttachment>());
			 
			//calling the sendNotification method of emailScenerioUtil
			EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_PROJECT_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(),globalVarsMap,notificationScenario);
			notificationScenario.setBody(notificationBody);
			notificationScenario.setOrgCode(loginOrgCode);
			//String url = null;
			StringBuilder actionLinkBuilder = new StringBuilder();
			String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("projectSummary?projectUUID="+projects.getProjectUUID()+"&projectClientUUID="+pc.getUuid()).toString();
			emailScenerioUtil.sendNotification(actionLink, loginUser, projectManager, loginOrgLocation, notificationScenario,new HashMap<String,String>());
		}
}
