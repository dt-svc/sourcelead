package com.bis.sourcelead.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTaskCustomFieldData;
import com.bis.sourcelead.entity.ProjectTaskDocuments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskCustomFieldDataService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DecimalFormat;

@Service
public class ProjectTasksServiceImpl implements ProjectTasksService {

	@Autowired
	private ProjectTasksDao tasksDao;

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	EmailScenarioService emailScenarioService;
		
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${pmsUrl}")
	private String pmsUrl;
	
	@Autowired
	private ProjectTaskActivityService projectTaskActivityService;
	
	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;
	
	@Autowired
	private ProjectTaskCustomFieldDataService projectTaskCustomFieldDataService;
	
	@Autowired
	PdfUtil pdfUtil;
	
	@Autowired
	ProjectAssignTasksService projectAssignTasksService;
	
	@Override
	public ProjectTasks addTasks(ProjectTasks tasks) throws Exception {
		tasks.setUpdatedDate(commonUtil.currentDate());
		return tasksDao.addTasks(tasks);
	}

	@Override
	public ProjectTasks updateTasks(ProjectTasks tasks) throws Exception {
		tasks.setUpdatedDate(commonUtil.currentDate());
		return tasksDao.updateTasks(tasks);
	}

	@Override
	public ProjectTasks getTasksId(String id) throws Exception {
		return tasksDao.getTasksId(id);
	}

	@Override
	public ProjectTasks validateTaskNameByTaskId(Long taskId, String taskName,Long projectId) throws Exception {
		return tasksDao.validateTaskNameByTaskId(taskId,taskName,projectId);
	}
	
	@Override
	public Boolean deleteTask(String id) throws Exception {
		Boolean status = tasksDao.deleteTask(id);
		return status;
	}

	@Override
	public List<ProjectTasks> getAllTasks() throws Exception {
		List<ProjectTasks> list = tasksDao.getAllTasks();
		return list;
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectId(Long projectId) throws Exception {
		List<ProjectTasks> list = tasksDao.getAllTasksByProjectId(projectId);
		return list;
	}
	
	
	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndExistedProjectTaskIds(Long projectId,
			List<Long> avilableProjectTaskIds) throws Exception {
		
		List<ProjectTasks> list = tasksDao.getAllTasksByProjectIdAndExistedProjectTaskIds(projectId, avilableProjectTaskIds);
		return list;
		
	}
	

	@Override
	public Collection<ProjectTasks> getAllSourceleadTasks() throws Exception {
		
		Collection<ProjectTasks> list = tasksDao.getAllSourceleadTasks();
		return list;
		
	}

	
	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskType(Long projectId, Long TaskType) throws Exception {
		List<ProjectTasks> taskList = tasksDao.getAllTasksByProjectIdAndTaskType(projectId,TaskType);
		return taskList;
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndSubTaskType(Long projectId, Long subTaskType) throws Exception {
		List<ProjectTasks> taskList = tasksDao.getAllTasksByProjectIdAndSubTaskType(projectId,subTaskType);
		return taskList;
	}
	
	@Override
	public void saveAll(Collection<ProjectTasks> taskList) throws Exception {
		tasksDao.saveAll(taskList);
	}
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Autowired
	 private ProjectsService projectsService;
	
	@Autowired
	 private UserService userService;
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndTaskTypeAndTaskStatus(Long projectId, String taskType, String taskStatus) throws Exception {
		List<ProjectTasks> allTaskList = tasksDao.getAllTasksByProjectIdAndTaskTypeAndTaskStatus(projectId,taskType,taskStatus);
		return allTaskList;
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndParentTaskId(Long projectId, Long parentTaskId) throws Exception {
		List<ProjectTasks> allparentTaskList = tasksDao.getAllTasksByProjectIdAndParentTaskId(projectId,parentTaskId);
		return allparentTaskList;
	}
	
 
	
	public String getProjectTaskGenaratedId(Projects projects) throws Exception {
//		String projectNmae = projects.getProjectName();
//		String companyShortCode = null;
		Integer generatedIdcount = 0;
		Long number = 1L;
		String requirementId = null;
		/*String[] splitName = projects.getProjectName().split(" ");
		int count = 0;
		StringBuilder sb = new StringBuilder();
		String splitProjectName ="";
		switch (splitName.length) {
		  case 1:
				  switch( projectNmae.length()) {
	                    case 2:
	            			for (int j = 0; j < projectNmae.length(); j++) {
	            				projectNmae.charAt(j);
	            				count++;
	            			}
	            			sb.append(projectNmae.substring(0, count));
	            			splitProjectName = sb.toString().toUpperCase();
	                        break;
	                    default:
	            			sb.append(projectNmae.substring(0, 2));
	            			 splitProjectName = sb.toString().toUpperCase();
		                }
		        break;
		  case 2: 
				String first = splitName[0].substring(0, 1);
				String second = splitName[1].substring(0, 1);
				 splitProjectName = sb.append(first).append(second).toString().toUpperCase();
		        break;
		  default:
				for (String s : projectNmae.split(" ")){
					sb.append(s.charAt(0));
				}
				 splitProjectName = sb.toString().substring(0, 2).toUpperCase();
		        break;
		}
		*/
		
		/*if(StringUtils.isNotBlank(projects.getOrgLocationsId().getGeneratedUserId())) {
			companyShortCode = projects.getOrgLocationsId().getGeneratedUserId();
		}else {
			companyShortCode = "";
		}*/
		StringBuilder sbuilder = new StringBuilder();
//		sbuilder.append(companyShortCode).append(splitProjectName);
		sbuilder.append(projects.getProjectShortCode());
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Integer checkNumber = tasksDao.getProjectTasksCountByProjectId(projects.getId()); 
		if (checkNumber == null) {
			requirementId = number.toString();
		} else {
			Integer reqId = checkNumber + 1;
			requirementId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
		
		return sbuilder.append(convetredRequirementsId).toString();
	}
	
	public String saveProjectTasksDTOByProjectId(Long projectId, ProjectTasksDTO projectTasksDTO, Long userId) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		ProjectTasks  tasks  = new ProjectTasks();
		
		LookUpData taskType = lookUpDataService.getTasksTypeByUniqueKey(projectTasksDTO.getTaskstype());
		tasks.setTaskType(taskType);
		tasks.setTaskName(projectTasksDTO.getTaskName());
		tasks.setBillableType(projectTasksDTO.getTaskBillableType());
		Projects projects = projectsService.getProjectById(projectId);
		tasks.setProjectId(projects);
		
		if(projectTasksDTO.getEpic() != null ) {
			ProjectTasks parentTaks = tasksDao.getTasksId(projectTasksDTO.getEpic());
			if(parentTaks != null) {
				tasks.setParentTaskId(parentTaks);
			}
		}
		if(projectTasksDTO.getUserStory() != null ){
			ProjectTasks parentTaks = tasksDao.getTasksId(projectTasksDTO.getUserStory());
			if(parentTaks != null) {
				tasks.setParentTaskId(parentTaks);
			}
		}
		if(projectTasksDTO.getSubTask() != null){
			ProjectTasks parentSubTask = tasksDao.getTasksId(projectTasksDTO.getSubTask());
			if(parentSubTask != null) {
				tasks.setParentTaskId(parentSubTask);
			}
		}
		
		if(projectTasksDTO.getDuDate() != null && !projectTasksDTO.getDuDate().isEmpty()){
	    		Date duDate = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),projectTasksDTO.getDuDate());
	    		tasks.setDueDate(duDate);
		}
		if(projectTasksDTO.getEstimatedTime() != null && !projectTasksDTO.getEstimatedTime().isEmpty()) {
			tasks.setEstimateTime(projectTasksDTO.getEstimatedTime());
		}

		if(projectTasksDTO.getPriority() != null){
			LookUpData priority = lookUpDataService.getTasksById(projectTasksDTO.getPriority());
			tasks.setPriority(priority);
		}
		if(projectTasksDTO.getSeverity() != null){
			LookUpData severity = lookUpDataService.getTasksById(projectTasksDTO.getSeverity());
			tasks.setSeverity(severity);
		}
		if(projectTasksDTO.getTaskStatus() != null){
			LookUpData taskStatus = lookUpDataService.getTasksById(projectTasksDTO.getTaskStatus());
			tasks.setTaskStatus(taskStatus);
		}
		
		if(projectTasksDTO.getTaskDescription() != null){
			tasks.setDescription(projectTasksDTO.getTaskDescription());
		}
		if(projectTasksDTO.getCurrencyType() != null){
			tasks.setBusinessCurrency(projectTasksDTO.getCurrencyType());
		}
		if(projectTasksDTO.getBusinessValue() != null){
			tasks.setBusinessValue(projectTasksDTO.getBusinessValue());
		}
		
		if(projectTasksDTO.getTaskOwner() != null){
			User user = userService.getUserById(projectTasksDTO.getTaskOwner());
			if(user != null) {
				tasks.setOwnerUserId(user);
			}
		}
		if(projectTasksDTO .getFileInfo() != null &&  !projectTasksDTO.getFileInfo().isEmpty()) {
			List<ProjectTaskDocuments> projDocDBList = projectTasksDTO .getFileInfo().stream()
					.map(bo -> new ProjectTaskDocuments(bo.getFileName(), bo.getFileURL(),bo.getContentType(),tasks)).collect(Collectors.toList());
	        tasks.getProjectTaskDocuments().addAll(projDocDBList);
	        for(FileInfo fileInfo : projectTasksDTO.getFileInfo()){
				FileUploadUtil.moveFile(projectTasksDTO.getStaticPath(), fileInfo, "tasks");
			}
		}
        Set<ProjectAssignTask> assignProjetcTasks = getAssignProjectTasks(projectTasksDTO,tasks,loginOrgLocation.getId()); 
        tasks.setProjectAssignTask(assignProjetcTasks);
        User user = userService.getUserById(userId);
        tasks.setCreatedBy(user.getUserCode());
        tasks.setPercentage(Constants.CANDIDATE_IN_ACTIVE);
        tasks.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
        if(projects.getProjectShortCode() != null) {
            tasks.setGeneratedId(getProjectTaskGenaratedId(projects));
        }
        tasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
        tasks.setCreatedDate(commonUtil.currentDate());
        ProjectTasks savedTask  = tasksDao.addTasks(tasks);
        
        if(projectTasksDTO.getCustomFormData() !=null) {
			for( CustomFormFieldDTO customFields:projectTasksDTO.getCustomFormData()) {
				if(customFields.getCustomFormFieldId() > 0 &&StringUtils.isNotEmpty(customFields.getCustomFormFieldData())) {
					ProjectTaskCustomFieldData projectTaskCustomFields = null;
					if(customFields.getEntityId()!= null && customFields.getEntityId() > 0) {
						projectTaskCustomFields = projectTaskCustomFieldDataService.getProjectTaskCustomFieldDataById(customFields.getEntityId());
					}else {
						projectTaskCustomFields = new ProjectTaskCustomFieldData();
					}
				projectTaskCustomFields.setProjectTask(savedTask);
				OrglocationFormFields orgFormField = orglocationFormFieldsService.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
				projectTaskCustomFields.setOrglocationsFormFields(orgFormField);
				projectTaskCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
				projectTaskCustomFieldDataService.saveProjectTaskCustomFieldData(projectTaskCustomFields);
				}
				
			}	
		}

        String redirectURL = "/projectTaskSummary-params-taskUUID="+tasks.getProjectTasksUUID()+"-and-projectClientUUID="+projectTasksDTO.getProjectClientUUID();
		long redirectOrgLocationId = tasks.getProjectId().getOrgLocationsId().getId();
		
        //onwer Task email
        if(savedTask.getOwnerUserId() != null) {
        	if(user.getId().longValue() != savedTask.getOwnerUserId().getId().longValue()) {
                SendMailtoAssigntoMembersaboutAddingTask(null,projectTasksDTO,savedTask,loginOrgLocation,user, redirectURL, redirectOrgLocationId);
        	}
        }
        
        //Assign task emails
        for (ProjectAssignTask projectAssignTask : assignProjetcTasks) {
        	if(loginOrgLocation.getId().longValue() == projectAssignTask.getProjectMemberId().getProjectMemberCandidteId().getOrgLocationId().getId()) {
                SendMailtoAssigntoMembersaboutAddingTask(projectAssignTask,projectTasksDTO,savedTask,loginOrgLocation,user, redirectURL, redirectOrgLocationId);
        	}
        }
        return "success";
	}

	public void SendMailtoAssigntoMembersaboutAddingTask(ProjectAssignTask projectAssignTask, ProjectTasksDTO projectTasksDTO, ProjectTasks tasks, OrgLocations loginOrgLocation, User loginUser, String redirectURL, long redirectOrgLocationId) throws Exception
	{ 
		String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		User user = null;
		if(projectAssignTask == null) {
			user = tasks.getOwnerUserId();
		}else {
			user = projectAssignTask.getProjectMemberId().getProjectMemberCandidteId().getConsultantUser();
		}
		StringBuilder fromNameBuilder = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		if(projectAssignTask == null) {
			emailMessage.setToEmail(tasks.getOwnerUserId().getEmail());
		}else {
			emailMessage.setToEmail(projectAssignTask.getProjectMemberId().getEmpEmail());
		}
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setFromName(fromNameBuilder.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName())
				&& !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser", userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
		
		if(projectAssignTask == null) {
			globalVarsMap.put("Assignedmember", tasks.getOwnerUserId().getFullName());
		}else {
			if (projectAssignTask.getProjectMemberId().getEmpName() != null
					&& !"".equalsIgnoreCase(projectAssignTask.getProjectMemberId().getEmpName())) {
				globalVarsMap.put("Assignedmember", projectAssignTask.getProjectMemberId().getEmpName());
			}
		}
		
		if (tasks.getPriority() != null) {
			String priority = tasks.getPriority().getName();
			if (priority != null && !"".equalsIgnoreCase(priority)) {
				globalVarsMap.put("Priority", priority);
			}
		}
		if (tasks.getSeverity() != null) {
			globalVarsMap.put("Severity", tasks.getSeverity().getName());
		}
		globalVarsMap.put("TaskTitle", tasks.getTaskName());
		globalVarsMap.put("ProjectName", tasks.getProjectId().getProjectName());
		String dueDate = "";
		SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
		if (tasks.getDueDate() != null) {
			dueDate = dateFormat.format(tasks.getDueDate());
		}
		if(tasks.getGeneratedId() != null) {
			globalVarsMap.put("taskId", tasks.getGeneratedId());
		}else {
			globalVarsMap.put("taskId", "--");
		}
		
		
		globalVarsMap.put("DueDate", dueDate);
		if (tasks.getOwnerUserId() != null) {
			if (tasks.getOwnerUserId().getFirstName() != null
					&& tasks.getOwnerUserId().getLastName() != null
					&& !"".equalsIgnoreCase(tasks.getOwnerUserId().getFirstName())
					&& !"".equalsIgnoreCase(tasks.getOwnerUserId().getLastName())) {
				StringBuilder userBuild = new StringBuilder();
				globalVarsMap.put("Owner", userBuild.append(tasks.getOwnerUserId().getFirstName()).append(" ").append(tasks.getOwnerUserId().getLastName()).toString());
			}
			else {
				globalVarsMap.put("Owner", tasks.getOwnerUserId().getFirstName());
			}
		}
		globalVarsMap.put("LoginOrgName", loginOrgLocation.getOrgLocationName());
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl + "?redirectURL=" +pmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Task</div></a>").toString());
		// calling the sendEmail method of emailScenerioUtil
		if (null != loginOrgLocation.getId())
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation, Constants.MAIL_ASSIGNEMPLOYEE_PROJECTTASK,new ArrayList<EmailAttachment>());
		// calling the sendNotification method of emailScenerioUtil
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAIL_ASSIGNEMPLOYEE_PROJECTTASK, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(pmsUrl).append("/").append("projectTaskSummary?"
				+ "taskUUID=" + tasks.getProjectTasksUUID() + "&projectClientUUID="
				+ projectTasksDTO.getProjectClientUUID()).toString();
		if(user != null) {
			emailScenerioUtil.sendNotification(actionLink, loginUser, user, loginOrgLocation, notificationScenario, new HashMap<String, String>());
		}
		
	}

	private Set<ProjectAssignTask> getAssignProjectTasks(ProjectTasksDTO projectTasksDTO, ProjectTasks tasks, Long loginOrgLocationId) throws Exception {
		 Set<ProjectAssignTask> projectAssignTaskSet = new HashSet<>();
		 
		if(projectTasksDTO != null && projectTasksDTO.getAssignTaskId() != null){
			String[] assign = projectTasksDTO.getAssignTaskId().split(",");
			for(String memberId : assign)
			{
				ProjectMembers pm = projectMembersService.getProjectMemberById(Long.parseLong(memberId.trim()));
				List<ProjectMembers> pmList = projectMembersService.getAllProjectMembersListByMasterCandidateId(pm.getMasterCandidateId().getId(), tasks.getProjectId().getId());
						if(pmList != null && !pmList.isEmpty()) {
							for(ProjectMembers members : pmList) {
								ProjectAssignTask assignTask = new ProjectAssignTask();
								assignTask.setProjectTaskId(tasks);
								
								assignTask.setProjectMemberId(members);
								assignTask.setStatus(Constants.MEMBER_ACTIVE);
								
								assignTask.setCreatedDate(CommonUtil.currentDate());
								projectAssignTaskSet.add(assignTask);
							}
						}
				
			}
		}
		return projectAssignTaskSet;
	}

	@Override
	public Set<ProjectTasks> prepareProjectTasksListByJson(JSONObject jsonObj,  HttpServletRequest request,
			Projects projects) throws Exception {
		 
		    HashSet<ProjectTasks> projectTasksList = new HashSet<ProjectTasks>(); 
		    if(jsonObj.has("maxTsRowNum")){
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);

			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				if(jsonObj.has(c+"taskName")){
					if(jsonObj.has(c+"taskDescription")){
						tasks.setDescription(jsonObj.getString(c+"taskDescription"));
					}
						tasks.setTaskName(jsonObj.getString(c+"taskName"));
						tasks.setBillableType(jsonObj.getString(c+"taskBillableType"));
						tasks.setCreatedDate(new Date());
						tasks.setUpdatedDate(new Date());
						tasks.setProjectId(projects);
						projectTasksList.add(tasks);
			  }
			}
		    }
		   return projectTasksList;
		
	}

	
	@Override
	public void updateTaskPriority(TaskDTO taskDTO,OrgLocations loginOrgLocation) throws Exception {
		 User user = userService.getUserById(taskDTO.getUserId());
		 ProjectTasks projectTasks = tasksDao.getTasksId(taskDTO.getTaskId());
		 StringBuffer activity = new StringBuffer();
		 StringBuffer  activitySummary = new StringBuffer();
		 LookUpData  oldData =null;
		 LookUpData  nullOldData =null;
		 LookUpData data  = null;
		 if(taskDTO.getTaskpriorityId() != null) {
			data = lookUpDataService.getTasksById(taskDTO.getTaskpriorityId());
			if(projectTasks.getPriority() != null) {
				oldData = lookUpDataService.getTasksById(projectTasks.getPriority().getId());
			}
		    activity.append("Priority");
		    
			 projectTasks.setPriority(data);
		}else {
			nullOldData = lookUpDataService.getTasksById(projectTasks.getPriority().getId());
			activity.append("Priority updated from");
			projectTasks.setPriority(null);
		}
		 projectTasks.setUpdatedDate(commonUtil.currentDate());
		 tasksDao.updateTasks(projectTasks);
		 if(oldData != null) {
			 activitySummary.append(oldData.getName()).append(" to ").append(data.getName());
		 }else {
			 if(data != null) {
				 activitySummary.append("Unassigned").append(" to ").append(data.getName());
			 }else {
				 activitySummary.append(nullOldData.getName()).append(" to ").append("Unassigned");
			 }
			 
		 }
		 projectTaskActivityService.savePriorityProjectTaskActivitiesByLookupData(activity.toString(), activitySummary.toString(),projectTasks,user,data);
		
		//Update Task emails (Assign members,owner,Created Project task)
		updatedTaskEmail(taskDTO,activity.toString(),activitySummary.toString(),user,loginOrgLocation,projectTasks);

	}

	@Override
	public void updateTaskSeverity(TaskDTO taskDTO,OrgLocations loginOrgLocation)throws Exception {
		 User user = userService.getUserById(taskDTO.getUserId());
		 ProjectTasks projectTasks = tasksDao.getTasksId(taskDTO.getTaskId());
		 StringBuffer activity = new StringBuffer();
		 StringBuffer activitySummary = new StringBuffer();
		 LookUpData  oldData =null;
		 LookUpData  nullOldData =null;
		 LookUpData data  = null;
		if(taskDTO.getTaskSeverityId() != null) {
            data = lookUpDataService.getTasksById(taskDTO.getTaskSeverityId());
            if(projectTasks.getSeverity() != null) {
            	oldData = lookUpDataService.getTasksById(projectTasks.getSeverity().getId());
            }
		    activity.append("Severity");
			projectTasks.setSeverity(data);
		}else {
			nullOldData = lookUpDataService.getTasksById(projectTasks.getSeverity().getId());
			activity.append("Severity updated from");
			projectTasks.setSeverity(null);
		}
		 projectTasks.setUpdatedDate(commonUtil.currentDate());
		 tasksDao.updateTasks(projectTasks);
		 if(oldData != null) {
			 activitySummary.append(oldData.getName()).append(" to ").append(data.getName());
		 }else {
			 if(data != null) {
				 activitySummary.append("Unassigned").append(" to ").append(data.getName());
			 }else {
				 activitySummary.append(nullOldData.getName()).append(" to ").append("Unassigned");
			 }
			 
		 }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		 
		 //Update Task emails (Assign members,owner,Created Project task)
		 updatedTaskEmail(taskDTO,activity.toString(),activitySummary.toString(),user,loginOrgLocation,projectTasks);

	}

	@Override
	public void updateTaskStatus(TaskDTO taskDTO,OrgLocations loginOrgLocation)throws Exception {
		    User user = userService.getUserById(taskDTO.getUserId());
		    ProjectTasks projectTasks = tasksDao.getTasksId(taskDTO.getTaskId());
		    LookUpData  data = lookUpDataService.getTasksById(taskDTO.getTaskStatusId());
		    
		    LookUpData  oldData = lookUpDataService.getTasksById(projectTasks.getTaskStatus().getId());
		    StringBuffer activity = new StringBuffer();
		    StringBuffer activitySummary = new StringBuffer();
		    activity.append("Status updated from");
		    
			projectTasks.setTaskStatus(data);
		    projectTasks.setUpdatedDate(commonUtil.currentDate());
		    tasksDao.updateTasks(projectTasks);
		   activitySummary.append(oldData.getName()).append(" to ").append(data.getName());
		   projectTaskActivityService.saveProjectTaskActivitiesByLookupData(activity.toString(), activitySummary.toString(),projectTasks,user,data);
		
		   updatedTaskEmail(taskDTO,activity.toString(),activitySummary.toString(),user,loginOrgLocation,projectTasks);
	}

	

	@Override
	public void updateTaskName(String taskName, String taskId, Long userId) throws Exception {
		    User user = userService.getUserById(userId);
		    ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		    //Task name activity	
		    ProjectTasks  oldData = tasksDao.getTasksId(taskId);
		    StringBuffer activity = new StringBuffer();
		    StringBuffer activitySummary = new StringBuffer();
		    activity.append("Task Name updated from");
		    
		    projectTasks.setTaskName(taskName);
		    projectTasks.setUpdatedDate(commonUtil.currentDate());
		    tasksDao.updateTasks(projectTasks);
		    
		    activitySummary.append(oldData.getTaskName()).append(" to ").append(projectTasks.getTaskName());
			projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}

	@Override
	public void updateTaskPercentage(Integer percentage, String taskId, Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		
		 //Task Percentage activity	
		 ProjectTasks  oldData = tasksDao.getTasksId(taskId);
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Task Percentage updated from");
	     projectTasks.setPercentage(percentage);
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	    
	     if(oldData.getPercentage() != null) {
		     activitySummary.append(oldData.getPercentage()).append(" to ").append(projectTasks.getPercentage());
	     }else {
		     activitySummary.append('0').append(" to ").append(projectTasks.getPercentage());
	     }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}
	
	
	
	@Override
	public Long getProjectTasksGeneratedIdByProjectId(Long projectId, Integer generatedIdCount) throws Exception {
		return tasksDao.getProjectTasksGeneratedIdByProjectId(projectId, generatedIdCount);
	}

	@Override
	public ProjectTasks getProjectTasksByUUID(String taskUUD) throws Exception {
		return tasksDao.getProjectTasksByUUID(taskUUD);
	}

	@Override
	public Integer getProjectTasksListCountByProjectIdAndStatus(Long projectId, String status) throws Exception {
		  return tasksDao.getProjectTasksListCountByProjectIdAndStatus(projectId,status);
	}

	@Override
	public List<ProjectTasks> getOverdueTaskListByProjectId(Long projectId, Date startDateUTC, Date endDateUTC) throws Exception {
		return tasksDao.getOverdueTaskListByProjectId(projectId,startDateUTC,endDateUTC);
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectIdAndFilter(Long projectId,  Date startDateUTC, Date endDateUTC) throws Exception {
		return tasksDao.getAllTasksByProjectIdAndFilter(projectId, startDateUTC, endDateUTC);
	}

	@Override
	public void updateBillingType(String billing, String taskId, Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTasks projectTasks = tasksDao.getTasksId(taskId);
		
		 //Task Billing Type activity	
		 ProjectTasks  oldData = tasksDao.getTasksId(taskId);
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Billing Type updated from");
	     projectTasks.setBillableType(billing);
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	     
	     activitySummary.append(oldData.getBillableType()).append(" to ").append(projectTasks.getBillableType());
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
	}

	@Override
	public ResponseList<ProjectTasks> getAllProjectTasksByProjectIdAndTaskTypeAndTaskStatus(ProjectTasksDTO projectTasksDTO, Long projectId, String taskType, String taskStatus, String userRole,String assignedMembers) throws Exception {
		return tasksDao.getAllProjectTasksByProjectIdAndTaskTypeAndTaskStatus(projectTasksDTO,projectId,taskType,taskStatus,userRole,assignedMembers);
	}

	@Override
	public ProjectTasks getProjectTasksByProjectIdAndProjectIdAndTaskName(Long projectId, String taskName)
			throws Exception {
		return tasksDao.getProjectTasksByProjectIdAndProjectIdAndTaskName(projectId, taskName);
	}

	@Override
	public void updateDueDate(TaskDTO taskDTO,OrgLocations loginOrgLocation) throws Exception {
		User user = userService.getUserById(taskDTO.getUserId());
		ProjectTasks projectTasks = tasksDao.getTasksId(taskDTO.getTaskId());	
		
		//Task Due Date 
		 ProjectTasks  oldData = tasksDao.getTasksId(taskDTO.getTaskId());
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Due Date updated from");
	     if(taskDTO.getEditDueDate() != null && taskDTO.getEditDueDate() != "" && taskDTO.getEditDueDate() != "undefined") {
		     projectTasks.setDueDate(commonUtil.orgLocationDateFormat(projectTasks.getProjectId().getOrgLocationsId().getDateFormat(), taskDTO.getEditDueDate()));
	     }else {
		     projectTasks.setDueDate(null);
	     }
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	     if(projectTasks.getDueDate() != null && oldData.getDueDate() != null){
		     activitySummary.append(commonUtil.dateStrFromOrgDateFormatter(oldData.getProjectId().getOrgLocationsId().getDateFormat(), oldData.getDueDate())).append(" to ").append(commonUtil.dateStrFromOrgDateFormatter(projectTasks.getProjectId().getOrgLocationsId().getDateFormat(), projectTasks.getDueDate()));
	     }else  if(projectTasks.getDueDate() != null) {
		     activitySummary.append("Unassigned").append(" to ").append(commonUtil.dateStrFromOrgDateFormatter(projectTasks.getProjectId().getOrgLocationsId().getDateFormat(), projectTasks.getDueDate()));
	     }else if(oldData.getDueDate() != null){
		     activitySummary.append(commonUtil.dateStrFromOrgDateFormatter(oldData.getProjectId().getOrgLocationsId().getDateFormat(), oldData.getDueDate())).append(" to ").append("Unassigned");
	     }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
	     
		//Update Task emails (Assign members,owner,Created Project task)
		updatedTaskEmail(taskDTO,activity.toString(),activitySummary.toString(),user,loginOrgLocation,projectTasks);

	}

	@Override
	public void updateEstimatedTime(TaskDTO taskDTO,OrgLocations loginOrgLocation) throws Exception {
		User user = userService.getUserById(taskDTO.getUserId());
		ProjectTasks projectTasks = tasksDao.getTasksId(taskDTO.getTaskId());	
		
		//Task Estimated time 
		 ProjectTasks  oldData = tasksDao.getTasksId(taskDTO.getTaskId());
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Estimated Time updated from");
	     if(taskDTO.getEstimatedTime() != null && taskDTO.getEstimatedTime() != "" && taskDTO.getEstimatedTime() != "undefined") {
		     projectTasks.setEstimateTime(taskDTO.getEstimatedTime());
	     }else {
		     projectTasks.setEstimateTime(null);
	     }
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	     if(projectTasks.getEstimateTime() != null && oldData.getEstimateTime() != null){
		     activitySummary.append(oldData.getEstimateTime()).append(" to ").append(projectTasks.getEstimateTime());
	     }else  if(projectTasks.getEstimateTime() != null) {
		     activitySummary.append("Unassigned").append(" to ").append(projectTasks.getEstimateTime());
	     }else if(oldData.getEstimateTime() != null){
		     activitySummary.append(oldData.getEstimateTime()).append(" to ").append("Unassigned");
	     }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		 
		//Update Task emails (Assign members,owner,Created Project task)
		updatedTaskEmail(taskDTO,activity.toString(),activitySummary.toString(),user,loginOrgLocation,projectTasks);
	}

	@Override
	public void updateOwner(Long ownerId, String taskId, Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTasks projectTasks = tasksDao.getTasksId(taskId);	
		//Task owner 
		 ProjectTasks  oldData = tasksDao.getTasksId(taskId);
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Owner updated from");
	     if(ownerId != null) {
	    	 User ownerUser = userService.getUserById(ownerId);
		     projectTasks.setOwnerUserId(ownerUser);;
	     }else {
		     projectTasks.setOwnerUserId(null);
	     }
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	     
	     if(projectTasks.getOwnerUserId()  != null && oldData.getOwnerUserId() != null){
		     activitySummary.append(oldData.getOwnerUserId().getFullName()).append(" to ").append(projectTasks.getOwnerUserId().getFullName());
	     }else  if(projectTasks.getOwnerUserId() != null) {
		     activitySummary.append("Unassigned").append(" to ").append(projectTasks.getOwnerUserId().getFullName());
	     }else if(oldData.getOwnerUserId() != null){
		     activitySummary.append(oldData.getOwnerUserId().getFullName()).append(" to ").append("Unassigned");
	     }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
	     
		
	}

	@Override
	public void updateEpic(Long epicId, String taskId, Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTasks projectTasks = tasksDao.getTasksId(taskId);	
		//Task Epic 
		 ProjectTasks  oldData = tasksDao.getTasksId(taskId);
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("Epic updated from");
	     if(epicId != null) {
	    	 ProjectTasks parentTaskId = tasksDao.getTasksId(String.valueOf(epicId));
		     projectTasks.setParentTaskId(parentTaskId);
	     }else {
		     projectTasks.setParentTaskId(null);
	     }
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	     
	     if(projectTasks.getParentTaskId()  != null && oldData.getParentTaskId() != null){
		     activitySummary.append(oldData.getParentTaskId().getGeneratedId() +"-"+oldData.getParentTaskId().getTaskName()).append(" to ").append(projectTasks.getParentTaskId().getGeneratedId() +"-"+projectTasks.getParentTaskId().getTaskName());
	     }else  if(projectTasks.getParentTaskId() != null) {
		     activitySummary.append("Unassigned").append(" to ").append(projectTasks.getParentTaskId().getGeneratedId() +"-"+projectTasks.getParentTaskId().getTaskName());
	     }else if(oldData.getParentTaskId() != null){
		     activitySummary.append(oldData.getParentTaskId().getGeneratedId() +"-"+oldData.getParentTaskId().getTaskName()).append(" to ").append("Unassigned");
	     }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
		
	}

	@Override
	public void updateUserStory(Long userStoryId, String taskId, Long userId) throws Exception {
		User user = userService.getUserById(userId);
		ProjectTasks projectTasks = tasksDao.getTasksId(taskId);	
		//Task User Story 
		 ProjectTasks  oldData = tasksDao.getTasksId(taskId);
	     StringBuffer activity = new StringBuffer();
	     StringBuffer activitySummary = new StringBuffer();
	     activity.append("UserStory updated from");
	     if(userStoryId != null) {
	    	 ProjectTasks parentTaskId = tasksDao.getTasksId(String.valueOf(userStoryId));
		     projectTasks.setParentTaskId(parentTaskId);
	     }else {
		     projectTasks.setParentTaskId(null);
	     }
	     projectTasks.setUpdatedDate(commonUtil.currentDate());
	     tasksDao.updateTasks(projectTasks);
	     
	     if(projectTasks.getParentTaskId()  != null && oldData.getParentTaskId() != null){
		     activitySummary.append(oldData.getParentTaskId().getGeneratedId() +"-"+oldData.getParentTaskId().getTaskName()).append(" to ").append(projectTasks.getParentTaskId().getGeneratedId() +"-"+projectTasks.getParentTaskId().getTaskName());
	     }else  if(projectTasks.getParentTaskId() != null) {
		     activitySummary.append("Unassigned").append(" to ").append(projectTasks.getParentTaskId().getGeneratedId() +"-"+projectTasks.getParentTaskId().getTaskName());
	     }else if(oldData.getParentTaskId() != null){
		     activitySummary.append(oldData.getParentTaskId().getGeneratedId() +"-"+oldData.getParentTaskId().getTaskName()).append(" to ").append("Unassigned");
	     }
		 projectTaskActivityService.saveProjectTaskActivities(activity.toString(), activitySummary.toString(),projectTasks,user);
	}

	@Override
	public File getTaskPdfFile(ResponseList<ProjectTasks> taskList, OrgLocations orgLocations) throws Exception {
		return pdfUtil.createProjectTasksPdfTable(taskList, orgLocations);
	}
	
	@Override
	public Sheet getTaskExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<ProjectTasks> taskList, OrgLocations orgLocations) throws Exception {
		Row writeRow = null;
        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,taskList,orgLocations);
        java.util.List<ProjectTasks> list =  taskList.getData();
        int column=0;
        for(ProjectTasks tasks : list){
       	 writeRow = createExcelsheet.createRow(column+1);
       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,column,tasks,orgLocations);
       	 column++;
        }
		return createExcelsheet;
	}


	
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column, ProjectTasks projectTassks, OrgLocations orgLocations) {
		
		if(projectTassks.getGeneratedId() != null) {
			 writeRow.createCell(0).setCellValue(projectTassks.getGeneratedId() +"-"+ projectTassks.getTaskName()); 
		}else {
			 writeRow.createCell(0).setCellValue(projectTassks.getTaskName()); 
		}

		 //Task Type
		 writeRow.createCell(1).setCellValue(projectTassks.getTaskTypes()); 

		 //Task Priority
		 if(projectTassks.getTaskPriotiry() != null) {
			 writeRow.createCell(2).setCellValue(projectTassks.getTaskPriotiry());
			}else {
				 writeRow.createCell(2).setCellValue("--");
			}
		 
		 //Task Assigned to 
		 if(projectTassks.getTaskAssignedUsers() != null) {
				String assignedTo = projectTassks.getTaskAssignedUsers().toString().replaceAll("\\[", "").replaceAll("\\]","");
				 writeRow.createCell(3).setCellValue(assignedTo);
			}else {
				 writeRow.createCell(3).setCellValue("--");
			}

		 //Task Due Date
		 if(projectTassks.getDueDate() != null) {
			 writeRow.createCell(4).setCellValue(commonUtil.dateStrFromDate(projectTassks.getDueDate()));
		 }else {
			 writeRow.createCell(4).setCellValue("--");
		 }
		 
		 //Task Estimated Time
		 if(projectTassks.getEstimateTime() != null) {
			 writeRow.createCell(5).setCellValue(projectTassks.getEstimateTime());
		 }else {
			 writeRow.createCell(5).setCellValue("--");
		 }
		 
		 //Task Severity
		 if(projectTassks.getTaskSeverity() != null) {
			 writeRow.createCell(6).setCellValue(projectTassks.getTaskSeverity());
		 }else {
			 writeRow.createCell(6).setCellValue("--");
		 }
		 
		 //Task owner name
		 if(projectTassks.getTaskOwnerName() != null) {
			 writeRow.createCell(7).setCellValue(projectTassks.getTaskOwnerName());
		 }else {
			 writeRow.createCell(7).setCellValue("--");
		 }
		 
		 //Task billing type
		 if(projectTassks.getBillableType() != null) {
			 writeRow.createCell(8).setCellValue(projectTassks.getBillableType());
		 }
		 
		 //Task business values
		 if(projectTassks.getBusinessValue() != null) {
			 writeRow.createCell(9).setCellValue(projectTassks.getBusinessCurrency() +" "+ projectTassks.getBusinessValue());
		 }else {
			 writeRow.createCell(9).setCellValue("--");
		 }
		 
		 //Task Status
		 writeRow.createCell(10).setCellValue(projectTassks.getTaskStaus());

		 //Task Description
		 if(projectTassks.getDescription() != null) {
			 String strippedText = projectTassks.getDescription().replaceAll("<font(.*?);\">", " ").replaceAll("</font>", "").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "\n").replaceAll("&nbsp;", "");
			 writeRow.createCell(11).setCellValue(strippedText);
		 }else {
			 writeRow.createCell(11).setCellValue("--");
		 }
		 
		 
		return createExcelsheet;
	}
	
	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, ResponseList<ProjectTasks> taskList, OrgLocations orgLocations)throws Exception {
		 CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        String headers [] = Constants.PROJECT_TASKS_EXCEL_HEADERS;
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
	}

	@Override
	public ProjectTasks getProjectTaskByleaveTypeId(long leaveTypeId,Long projectId) {
		return tasksDao.getProjectTaskByleaveTypeId(leaveTypeId,projectId);
	}

	@Override
	public List<ProjectTasks> getTasksListByLeaveType(Long levaTypeId) throws Exception {
		return tasksDao.getTasksListByLeaveType(levaTypeId);
	}
	
	
	
   /**
    * @author Laxman	
    * @param taskDTO
    * @param activity
    * @param activitySummary
    * @param user
    * @param loginOrgLocation
    * @param projectTasks
    * @throws Exception
    */
	private void updatedTaskEmail(TaskDTO taskDTO, String activity, String activitySummary,User user,OrgLocations loginOrgLocation, ProjectTasks projectTasks) throws Exception {
		// Assign members Email
		ProjectTasks projectTask = tasksDao.getTasksId(taskDTO.getTaskId());
		String[] assignid = taskDTO.getAssignValue().split(",");
		 List<Long> assignUserids = new ArrayList<Long>();
			 if(!taskDTO.getAssignValue().isEmpty() && assignid.length > 0) {
				 for(int i=0; i<assignid.length;i++) {
					 assignUserids.add(Long.valueOf(assignid[i]));
				 }
			 }
		ProjectAssignTask  assignTasks = null;
		
		 //project Task ownerEmail
		   if(projectTasks.getOwnerUserId() != null) {
			   if(user.getId().longValue() != projectTasks.getOwnerUserId().getId().longValue()) {
					 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"ownerEmail");
			   }
		   }
		   
		   
	   if(assignUserids.size() > 0) {
		   for (Long assignedMemeber :assignUserids) {
				  assignTasks = new ProjectAssignTask();
				 ProjectMembers pm = projectMembersService.getProjectMemberById(assignedMemeber);
				 assignTasks.setProjectMemberId(pm);
				 if(pm.getCandidateId().longValue() != taskDTO.getCandidateId().longValue()) {
					if(projectTasks.getOwnerUserId() == null) {
						 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"assignEmail");
					}else if(projectTasks.getOwnerUserId() != null && pm.getEmployee() == null) {
						 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"assignEmail");
					}else if(projectTasks.getOwnerUserId() != null && pm.getEmployee().getUser().getId().longValue() != projectTasks.getOwnerUserId().getId().longValue()  ) {
						 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"assignEmail");
					}
				 }
			 }
	   }
	   
	  
	   
	//Project Task createby Email
	   if(projectTask.getCreatedBy() != null) {
		   if(projectTasks.getOwnerUserId() != null) {
			   if(!user.getUserCode().equals(projectTask.getCreatedBy()) && !projectTasks.getOwnerUserId().getUserCode().equals(projectTask.getCreatedBy())) {
					 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"createdByEmail");
			   }
		   }else if(!user.getUserCode().equals(projectTask.getCreatedBy())) {
				 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"createdByEmail");
		   }
	   }
	   
	}
	
	
	/**
	 * @author Laxman
	 * @param projectAssignTask
	 * @param projectClientUUID
	 * @param tasks
	 * @param loginOrgLocation
	 * @param loginUser
	 * @param activity
	 * @param activitySummary
	 * @param emailType
	 * @throws Exception
	 */

	private void sendUpdatedTaskEmails(ProjectAssignTask projectAssignTask, String projectClientUUID,	ProjectTasks tasks, OrgLocations loginOrgLocation, User loginUser, String activity, String activitySummary,String emailType) throws Exception {
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
		 
		globalVarsMap.put("activity", activity);
		globalVarsMap.put("activityDescrption", activitySummary);
		// calling the sendEmail method of emailScenerioUtil
		if (null != loginOrgLocation.getId())
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation, Constants.UPDATED_TASK_EMAIL,new ArrayList<EmailAttachment>());
		// calling the sendNotification method of emailScenerioUtil
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_TASK_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getSubject(), globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationBody);
		notificationScenario.setOrgCode(loginOrgCode);
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(pmsUrl).append("/").append("projectTaskSummary?"
				+ "taskUUID=" + tasks.getProjectTasksUUID() + "&projectClientUUID="+ projectClientUUID).toString();
		if(user != null) {
			emailScenerioUtil.sendNotification(actionLink, loginUser, user, loginOrgLocation, notificationScenario, new HashMap<String, String>());
		}		
		
	}

	@Override
	public List<ProjectTasks> getTasksListByLookupIdAndCategoryType(Long lookupId, String taskType) throws Exception {
		return tasksDao.getTasksListByLookupIdAndCategoryType(lookupId, taskType);
	}

	@Override
	public ResponseList<ProjectTasks> getOverdueTaskReportListByOrglocationId(ProjectTasksDTO  projectTasksDTO,Date[] searchDates) throws Exception {
		return tasksDao.getOverdueTaskReportListByOrglocationId(projectTasksDTO,searchDates);
	}
	
	
	
	
	@Override
	public void updateTaskStatusList(TaskDTO taskDTO, OrgLocations loginOrgLocation) throws Exception {
		User user = userService.getUserById(taskDTO.getUserId());
	    ProjectTasks projectTasks = tasksDao.getTasksId(taskDTO.getTaskId());
	    LookUpData  data = lookUpDataService.getTasksById(taskDTO.getTaskStatusId());
	    
	    LookUpData  oldData = lookUpDataService.getTasksById(projectTasks.getTaskStatus().getId());
	    StringBuffer activity = new StringBuffer();
	    StringBuffer activitySummary = new StringBuffer();
	    activity.append("Status updated from");
	    
		projectTasks.setTaskStatus(data);
	    projectTasks.setUpdatedDate(commonUtil.currentDate());
	    tasksDao.updateTasks(projectTasks);
	   activitySummary.append(oldData.getName()).append(" to ").append(data.getName());
	   projectTaskActivityService.saveProjectTaskActivitiesByLookupData(activity.toString(), activitySummary.toString(),projectTasks,user,data);
	
	   updatedTaskStatusListEmail(taskDTO,activity.toString(),activitySummary.toString(),user,loginOrgLocation,projectTasks);
		
	}
	

	private void updatedTaskStatusListEmail(TaskDTO taskDTO, String activity, String activitySummary,User user,OrgLocations loginOrgLocation, ProjectTasks projectTasks) throws Exception {

		// Assign members Email
		ProjectTasks projectTask = tasksDao.getTasksId(taskDTO.getTaskId());
		List<ProjectAssignTask> projectAssignTaskLis = projectAssignTasksService.getProjectAssignTaskListByProjectTaskId(projectTask.getId());
		 List<Long> assignUserids = new ArrayList<Long>();
		 if(projectAssignTaskLis != null) {
			 for(ProjectAssignTask pa:projectAssignTaskLis) {
				 assignUserids.add(pa.getProjectMemberId().getId());
			 }
		 }
		ProjectAssignTask  assignTasks = null;
	   if(assignUserids.size() > 0) {
		   for (Long assignedMemeber :assignUserids) {
				  assignTasks = new ProjectAssignTask();
				 ProjectMembers pm = projectMembersService.getProjectMemberById(assignedMemeber);
				 assignTasks.setProjectMemberId(pm);
				 if(pm.getCandidateId().longValue() != taskDTO.getCandidateId().longValue()) {
					if(projectTasks.getOwnerUserId() == null) {
						 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"assignEmail");
					}else if(projectTasks.getOwnerUserId() != null && pm.getEmployee() == null) {
						 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"assignEmail");
					}else if(projectTasks.getOwnerUserId() != null && pm.getEmployee().getUser().getId().longValue() != projectTasks.getOwnerUserId().getId().longValue()  ) {
						 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"assignEmail");
					}
				 }
			 }
	   }
	   
	   //project Task ownerEmail
	   if(projectTasks.getOwnerUserId() != null) {
		   if(user.getId().longValue() != projectTasks.getOwnerUserId().getId().longValue()) {
				 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"ownerEmail");
		   }
	   }
	   
	   
	//Project Task createby Email
	   if(projectTask.getCreatedBy() != null) {
		   if(projectTasks.getOwnerUserId() != null) {
			   if(!user.getUserCode().equals(projectTask.getCreatedBy()) && !projectTasks.getOwnerUserId().getUserCode().equals(projectTask.getCreatedBy())) {
					 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"createdByEmail");
			   }
		   }else if(!user.getUserCode().equals(projectTask.getCreatedBy())) {
				 sendUpdatedTaskEmails(assignTasks,taskDTO.getProjectClientUUID(),projectTask,loginOrgLocation,user,activity,activitySummary,"createdByEmail");
		   }
	   }
	   
	
		
	}

	@Override
	public ProjectTasks getTasksById(Long projectsTaskId)
	{
		return tasksDao.getTasksById(projectsTaskId);
	}

}