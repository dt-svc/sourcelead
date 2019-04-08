package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTaskCommentsDao;
import com.bis.sourcelead.dto.ProjectTaskDocumentDTO;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTaskComments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTaskCommentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class ProjectTaskCommentsServiceImpl implements ProjectTaskCommentsService{

	@Autowired
	ProjectTaskCommentsDao projectTaskCommentsDao;
	
	@Autowired
	 CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectTasksService projectTasksService;

	@Autowired
	ProjectMembersService projectMembersService;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EmailScenarioService emailScenarioService;
		
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Override
	public ProjectTaskComments saveProjectTaskComments(ProjectTaskComments projectTaskComments) throws Exception {
		return projectTaskCommentsDao.saveProjectTaskComments(projectTaskComments);
	}

	@Override
	public List<ProjectTaskComments> getProjectTaskCommentsByProjectTaskId(Long projectTaskId) throws Exception {
		return projectTaskCommentsDao.getProjectTaskCommentsByProjectTaskId(projectTaskId);
	}

	@Override
	public void storeprojectComments(ProjectTaskDocumentDTO projectTaskDocumentDTO,TaskDTO taskDTO, OrgLocations loginOrgLocation) throws Exception {
		User user = userService.getUserById(Long.valueOf(projectTaskDocumentDTO.getLoginUserId()));
		ProjectTasks tasks = projectTasksService.getTasksId(projectTaskDocumentDTO.getProjectTaskId());
		ProjectTaskComments projectTaskComments = new ProjectTaskComments();
		projectTaskComments.setProjectTaskId(tasks);
		projectTaskComments.setComment(projectTaskDocumentDTO.getTaskComment());
		projectTaskComments.setCreatedUserName(user.getFullName());
		projectTaskComments.setUserId(user);
		projectTaskComments.setCreatedDate(commonUtil.currentDate());
		projectTaskCommentsDao.saveProjectTaskComments(projectTaskComments);
		
		 //Update Task emails (Assign members,owner,Created Project task)
		 updatedTaskCommentEmail(projectTaskDocumentDTO,taskDTO,user,loginOrgLocation,tasks);
		
		
	}

	private void updatedTaskCommentEmail(ProjectTaskDocumentDTO projectTaskDocumentDTO, TaskDTO taskDTO, User user, OrgLocations loginOrgLocation, ProjectTasks projectTasks) throws Exception {
		
				ProjectTasks projectTask = projectTasksService.getTasksId(projectTaskDocumentDTO.getProjectTaskId());
				ProjectAssignTask  assignTasks = null;
				List<Long> assignUserids = new ArrayList<Long>();
					//Mentioned UserIds Email 
					Set<Long> userIdSet = new HashSet<>();
					String[] mentionedId = projectTaskDocumentDTO.getMentionedUserIds().split(",");
					 if(!projectTaskDocumentDTO.getMentionedUserIds().isEmpty() && mentionedId.length > 0) {
						 for(int i=0; i<mentionedId.length;i++) {
							 if(mentionedId[i] != null && !userIdSet.contains(Long.valueOf(mentionedId[i]))) {
								 userIdSet.add(Long.valueOf(mentionedId[i]));
								 assignUserids.add(Long.valueOf(mentionedId[i]));
							 }
						 }
					  }
					// Assign members Email
					String[] assignid = taskDTO.getAssignValue().split(",");
						 if( !taskDTO.getAssignValue().isEmpty() && assignid.length > 0) {
							 for(int i=0; i<assignid.length;i++) {
								 if(assignid[i] != null && !assignUserids.contains(Long.valueOf(assignid[i]))) {
									 assignUserids.add(Long.valueOf(assignid[i]));
								 }
							 }
						 }
				
				 if(assignUserids.size() > 0) {
					   for (Long assignedMemeber :assignUserids) {
							  assignTasks = new ProjectAssignTask();
							 ProjectMembers pm = projectMembersService.getProjectMemberById(assignedMemeber);
							 assignTasks.setProjectMemberId(pm);
							  
							 if(pm.getCandidateId().longValue() != taskDTO.getCandidateId().longValue()) {
								if(projectTasks.getOwnerUserId() == null) {
									 sendUpdatedTaskCommentEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,projectTaskDocumentDTO,"assignEmail");
								}else if( projectTasks.getOwnerUserId() != null && pm.getEmployee() == null) {
									 sendUpdatedTaskCommentEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,projectTaskDocumentDTO,"assignEmail");
								}else if( projectTasks.getOwnerUserId() != null && pm.getEmployee().getUser().getId().longValue() != projectTasks.getOwnerUserId().getId().longValue() ) {
									 sendUpdatedTaskCommentEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,projectTaskDocumentDTO,"assignEmail");
								}
							 }
						 }
				   }
				 
			   //project Task ownerEmail
			   if(projectTasks.getOwnerUserId() != null) {
				   if(user.getId().longValue() != projectTasks.getOwnerUserId().getId().longValue()) {
					   sendUpdatedTaskCommentEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,projectTaskDocumentDTO,"ownerEmail");
				   }
			   }
			   
			//Project Task createby Email
			   if(projectTask.getCreatedBy() != null) {
				   if(projectTasks.getOwnerUserId() != null) {
					   if(!user.getUserCode().equals(projectTask.getCreatedBy()) && !projectTasks.getOwnerUserId().getUserCode().equals(projectTask.getCreatedBy())) {
						   sendUpdatedTaskCommentEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,projectTaskDocumentDTO,"createdByEmail");
					   } 
				   }else if(!user.getUserCode().equals(projectTask.getCreatedBy())) {
					   sendUpdatedTaskCommentEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,projectTaskDocumentDTO,"createdByEmail");
				   }
				   
			   }
			   
	}

	private void sendUpdatedTaskCommentEmails(ProjectAssignTask projectAssignTask, String projectClientUUID, ProjectTasks tasks, OrgLocations loginOrgLocation, User loginUser, ProjectTaskDocumentDTO projectTaskDocumentDTO, String emailType) throws Exception {
		String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		User user = null;
		if(emailType.equalsIgnoreCase("assignEmail")) {
			user = projectAssignTask.getProjectMemberId().getProjectMemberCandidteId().getConsultantUser();
		}else if(emailType.equalsIgnoreCase("ownerEmail")) {
			user = 	tasks.getOwnerUserId();
		}else if(emailType.equalsIgnoreCase("createdByEmail")) {
			User taskCreatedUser = userService.getUserByUserCode(tasks.getCreatedBy());
			user = 	taskCreatedUser;
		}
		StringBuilder fromNameBuilder = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		if(emailType.equalsIgnoreCase("assignEmail")) {
			emailMessage.setToEmail(projectAssignTask.getProjectMemberId().getEmpEmail());
		}else if(emailType.equalsIgnoreCase("ownerEmail")) {
			emailMessage.setToEmail(tasks.getOwnerUserId().getEmail());
		}else if(emailType.equalsIgnoreCase("createdByEmail")) {
			User taskCreatedUser = userService.getUserByUserCode(tasks.getCreatedBy());
			emailMessage.setToEmail(taskCreatedUser.getEmail());
		}
		
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		if(emailType.equalsIgnoreCase("assignEmail")) {
			if (projectAssignTask.getProjectMemberId().getEmpName() != null
					&& !"".equalsIgnoreCase(projectAssignTask.getProjectMemberId().getEmpName())) {
				globalVarsMap.put("assignMemberName", projectAssignTask.getProjectMemberId().getEmpName());
			}
		}else if(emailType.equalsIgnoreCase("ownerEmail")) {
			globalVarsMap.put("assignMemberName", tasks.getOwnerUserId().getFullName());
		}else if(emailType.equalsIgnoreCase("createdByEmail")) {
			User taskCreatedUser = userService.getUserByUserCode(tasks.getCreatedBy());
			globalVarsMap.put("assignMemberName", taskCreatedUser.getFullName());
		}
		
		
		globalVarsMap.put("loginUser", loginUser.getFullName());
		
		if(tasks.getGeneratedId() != null) {
			globalVarsMap.put("taskId", tasks.getGeneratedId());
		}else {
			globalVarsMap.put("taskId", "--");
		}
		globalVarsMap.put("taskTitle", tasks.getTaskName());
		globalVarsMap.put("projectName", tasks.getProjectId().getProjectName());
		globalVarsMap.put("taskComment", projectTaskDocumentDTO.getTaskComment());

		globalVarsMap.put("loginOrg", loginOrgLocation.getOrgLocationName());
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("loginLink", linkBuild.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		// calling the sendEmail method of emailScenerioUtil
		if (null != loginOrgLocation.getId())
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation, Constants.ADD_TASK_COMMENT_EMAIL,new ArrayList<EmailAttachment>());
		// calling the sendNotification method of emailScenerioUtil
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ADD_TASK_COMMENT_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getSubject(), globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(sourceleadUrl).append("/").append("projectTaskSummary?"
				+ "taskUUID=" + tasks.getProjectTasksUUID() + "&projectClientUUID="+ projectClientUUID+"&active=notificationComment").toString();
		if(user != null) {
			emailScenerioUtil.sendNotification(actionLink, loginUser, user, loginOrgLocation, notificationScenario, new HashMap<String, String>());
		}		
	}
	
}
