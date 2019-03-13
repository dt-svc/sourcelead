package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TaskDao;
import com.bis.sourcelead.dto.AddTaskDTO;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;

@Service
public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Autowired
	TaskDao taskDao;

	@Autowired
	HttpServletRequest request;

	@Autowired
	UserService userService;

	@Autowired
	private CommonUtil commonUtil;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private EmailScenarioService emailScenarioService;

	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	TimeZoneService timeZoneService;

	@Value("${static.path}")
	private String staticPath;

	@Override
	public Tasks saveTasks(Tasks tasks) throws Exception {
		return taskDao.saveTasks(tasks);
	}

	@Override
	public Tasks getTasksById(Long id) {
		return taskDao.getTasksById(id);
	}

	@Override
	public List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId, Long userId, String Status,
			String date, Date converteDate, Timezone timezone) throws Exception {
		Date endDateUTC = null;
		Date startDateUTC = null;
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
		OrgLocations orgLocations = orgLocationService.getOrgLocations(OrgLocationId);
		// SimpleDateFormat simpleDateFormat =
		// orgLocations.getLocFullFormDateFormatter();
		if (null != date && "" != date) {
			// DateFormat sdf = simpleDateFormat;
			Date startTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), date);
			Date endTime = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), date);
			String customStartTimeStr = TZ_DF_CSD.format(startTime);
			String customEndTimeStr = TZ_DF_CED.format(endTime);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
					customStartTimeStr);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
					customEndTimeStr);
			startDateUTC = TZ_DF_TS.parse(startDateString);
			endDateUTC = TZ_DF_TS.parse(endDateString);

		}

		List<Tasks> taklist = taskDao.tasksListByIdAndOrgLocationIdAssignedUserId(OrgLocationId, userId, Status, date,
				startDateUTC, endDateUTC);

		Set<Tasks> taskList = new HashSet<Tasks>();
		for (Tasks tasks : taklist) {
			taskList.add(tasks);
		}

		for (Tasks tk : taklist) {
			tk.setSubject(tk.getSubject());

			if (tk.getTaskDate() != null) {

				String currentTimeStr = Constants.TZ_DF.format(tk.getTaskDate());
				String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE,
						timezone.getTimeZoneId(), currentTimeStr);
				TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
						converteDate);
				tk.setTaskDateStr(currentDateInTimezone);
			}
			if (tk.getTaskTime() != null) {
				String currentTimeStr = Constants.TZ_DF.format(tk.getTaskDate());
				String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE,
						timezone.getTimeZoneId(), currentTimeStr);
				TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = Constants.TMS.format(converteDate);
				tk.setTaskTime(currentDateInTimezone);
			}
			tk.setDescription(tk.getDescription());
			tk.setAlertType(tk.getAlertType());
			tk.setTaskStatus(tk.getTaskStatus());
			tk.setPriority(tk.getPriority());

			if (null != tk.getAssignedUserId()) {
				StringBuilder assignedName = new StringBuilder();
				if (null != tk.getAssignedUserId().getLastName()) {
					tk.setAssignedName(assignedName
							.append(tk.getAssignedUserId().getFirstName() + " " + tk.getAssignedUserId().getLastName())
							.toString());
				} else {
					tk.setAssignedName(assignedName.append(tk.getAssignedUserId().getFirstName()).toString());
				}
			}

			if (null != tk.getTaskOwnerId()) {
				StringBuilder taskOwnerName = new StringBuilder();
				if (null != tk.getAssignedUserId().getLastName()) {
					tk.setTaskOwnerName(taskOwnerName
							.append(tk.getTaskOwnerId().getFirstName() + " " + tk.getTaskOwnerId().getLastName())
							.toString());
				} else {
					tk.setTaskOwnerName(taskOwnerName.append(tk.getTaskOwnerId().getFirstName()).toString());
				}
			}

		}
		return taklist;

	}

	public String saveTasksDTO(AddTaskDTO addTaskDTO) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Timezone timezone = timeZoneService.getTimezone(loginOrgLocation.getTimezone().getId());
		// SimpleDateFormat simpleDateFormat =
		// loginOrgLocation.getLocFullFormDateFormatter();
		// String pattern = "dd-MM-yy";
		// SimpleDateFormat format = new SimpleDateFormat(pattern);

		if (addTaskDTO.getAssignTo() != null) {
			String assignOwnersId = null;
			assignOwnersId = addTaskDTO.getAssignTo().toString();
			assignOwnersId = assignOwnersId.replaceAll("\\[", "").replaceAll("\\]", "");
			String[] owners = assignOwnersId.split(",");

			for (int i = 0; i < owners.length; i++) {
				Tasks task = new Tasks();
				User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				task.setAssignedUserId(ownerUser);
				task.setOwnerOrglocationId(loginOrgLocation);
				task.setTaskOwnerId(user);
				UserRole assignedUser = userRoleService.getUserByUserId(ownerUser.getId());
				if (assignedUser != null && assignedUser.getOrgLocations() != null) {
					task.setAssignedOrglocationId(assignedUser.getOrgLocations());
				}
				if (StringUtils.isNotBlank(addTaskDTO.getPriority())) {
					task.setPriority(addTaskDTO.getPriority());
				}
				if (StringUtils.isNotBlank(addTaskDTO.getDescription())) {
					task.setDescription(addTaskDTO.getDescription());
				}

				/*
				 * if(jsonObj.has("taskDatpicker") &&
				 * !jsonObj.getString("taskDatpicker").isEmpty()){ Date taskDate =
				 * format.parse(jsonObj.getString("taskDatpicker")); task.setTaskDate(taskDate);
				 * }
				 * 
				 * if(jsonObj.has("timepicker") && !jsonObj.getString("timepicker").isEmpty()){
				 * task.setTaskTime(jsonObj.getString("timepicker")); }
				 */

				if (addTaskDTO.getTaskDatpicker() != null && addTaskDTO.getTimepicker() != null) {
					Date startTime = CalendarUtil
							.getDateFromDateAndTime(
									commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(),
											addTaskDTO.getTaskDatpicker().toString()),
									addTaskDTO.getTimepicker().toString());
					String currentTimeStr = Constants.TZ_DF.format(startTime);
					String startTimeUTC = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
							currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					task.setTaskDate(convertedDate);
					if (addTaskDTO.getTimepicker() != null) {
						String splitDate = convertedDate.toString();
						String[] timeFormat = splitDate.split(" ");
						task.setTaskTime(timeFormat[3]);
					}
				}

				if (StringUtils.isNotBlank(addTaskDTO.getRepeat())) {
					task.setTaskRepeat(addTaskDTO.getRepeat());
				}
				if (StringUtils.isNotBlank(addTaskDTO.getSubject())) {
					task.setSubject(addTaskDTO.getSubject());
				}

				// Email and Notification conditions
				if (StringUtils.isNotEmpty(addTaskDTO.getEmail())
						&& StringUtils.isNotEmpty(addTaskDTO.getNotification())) {
					task.setAlertType(addTaskDTO.getEmail() + "," + addTaskDTO.getNotification());
				} else if (StringUtils.isNotEmpty(addTaskDTO.getEmail())) {
					task.setAlertType(addTaskDTO.getEmail());
				} else if (StringUtils.isNotEmpty(addTaskDTO.getNotification())) {
					task.setAlertType(addTaskDTO.getNotification());
				}
				if (user.getUserCode() != null) {
					task.setCreatedBy(user.getUserCode());
				}
				task.setTaskStatus(Constants.TASK_STATUS_OPEN);
				task.setCreatedDate(commonUtil.currentDate());
				task.setUpdatedDate(commonUtil.currentDate());
				taskDao.saveTasks(task);
				// mail logic for users
				if (null != task.getAlertType() && !"".equalsIgnoreCase(task.getAlertType())) {
					mailToUserTask(task);
				}
			}
		}

		return "success";
	}

	/**
	 * @author shivayogi
	 * @param loginUser
	 * @param assignedUser
	 * @param tasks
	 * @param orgLocations
	 * @throws Exception
	 */
	@Override
	public void mailToUserTask(Tasks task) throws Exception {
		Timezone timezone = timeZoneService.getTimezone(task.getOwnerOrglocationId().getTimezone().getId());

		OrgLocations orglo = orgLocationService.getOrgLocations(task.getOwnerOrglocationId().getId());
		// SimpleDateFormat simpleDateFormat = orglo.getLocFullFormDateFormatter();

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(task.getTaskOwnerId().getFirstName());
		emailMessage.setLastName(task.getTaskOwnerId().getLastName());
		emailMessage.setToEmail(task.getAssignedUserId().getEmail());
		emailMessage.setFromEmail(task.getTaskOwnerId().getEmail());
		emailMessage.setCreatedBy(task.getTaskOwnerId().getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(task.getTaskOwnerId().getFirstName()).append(" ")
				.append(task.getTaskOwnerId().getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (task.getSubject() != null && !"".equalsIgnoreCase(task.getSubject())) {
			globalVarsMap.put("subject", task.getSubject());
		} else {
			globalVarsMap.put("subject", "--");
		}
		if (task.getPriority() != null && !"".equalsIgnoreCase(task.getPriority())) {
			globalVarsMap.put("priority", task.getPriority());
		} else {
			globalVarsMap.put("priority", "--");
		}
		if (null != task.getTaskDate() && !"".equalsIgnoreCase(task.getTaskDate().toString())) {
			task.setTaskDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(), task.getTaskDate()));
			if (task.getTaskDateStr() != null && !"".equalsIgnoreCase(task.getTaskDateStr().toString())) {
				globalVarsMap.put("date", task.getTaskDateStr());
			}
		} else {
			globalVarsMap.put("date", "--");
		}
		if (task.getTaskTime() != null) {
			DateFormat DF_DMY = new SimpleDateFormat("hh:mma");
			String currentTimeStr = Constants.TZ_DF.format(task.getTaskDate());
			String startTimeUTC = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(),
					currentTimeStr);
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
			String convertedDateStr = DF_DMY.format(convertedDate);
			globalVarsMap.put("time", convertedDateStr);

		}

		if (task.getDescription() != null && !"".equalsIgnoreCase(task.getDescription())) {
			globalVarsMap.put("description", task.getDescription());
		} else {
			globalVarsMap.put("description", "--");
		}
		if (task.getTaskOwnerId().getFirstName() != null && task.getTaskOwnerId().getLastName() != null
				&& !"".equalsIgnoreCase(task.getTaskOwnerId().getFirstName())
				&& !"".equalsIgnoreCase(task.getTaskOwnerId().getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser", userBuild.append(task.getTaskOwnerId().getFirstName()).append(" ")
					.append(task.getTaskOwnerId().getLastName()).toString());
		} else {
			globalVarsMap.put("LoginUser", task.getTaskOwnerId().getFirstName());
		}
		if (task.getAssignedUserId().getFirstName() != null && task.getAssignedUserId().getLastName() != null
				&& !"".equalsIgnoreCase(task.getAssignedUserId().getFirstName())
				&& !"".equalsIgnoreCase(task.getAssignedUserId().getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("assignedUser", userBuild.append(task.getAssignedUserId().getFirstName()).append(" ")
					.append(task.getAssignedUserId().getLastName()).toString());
		} else {
			globalVarsMap.put("assignedUser", task.getAssignedUserId().getFirstName());
		}

		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario emailScenario = null;
		if (orglo != null)
			emailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(
					Constants.TASK_MECHANISM_EMAIL, Constants.ENTITY_TYPE_EMAIL, orglo.getId());
		if (emailScenario == null) {
			emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TASK_MECHANISM_EMAIL,
					Constants.ENTITY_TYPE_EMAIL);
		}
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.TASK_MECHANISM_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);

		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(), globalVarsMap,
				emailScenario);
		String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(), globalVarsMap, emailScenario);
		String signature = emailScenerioUtil.buildTemplateSignature(emailScenario.getSignature(), globalVarsMap,
				emailScenario);
		String url = sourceleadUrl;

		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);

		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, task.getOwnerOrglocationId().getOrg(), task.getOwnerOrglocationId());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the Email
		if (task.getAlertType() != null && task.getAlertType().equalsIgnoreCase("Email")) {
			emailScenerioUtil.sendEmail(emailMessage, task.getOwnerOrglocationId().getId(), iAdressArray,
					new ArrayList<EmailAttachment>());
		}
		// calling the Notification
		if (task.getAlertType() != null && task.getAlertType().equalsIgnoreCase("Notification")) {
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(), task.getAssignedUserId(),
					task.getOwnerOrglocationId(), notificationScenario, new HashMap<String, String>());
		}
		// calling the Email and notification
		if (task.getAlertType() != null && task.getAlertType().equalsIgnoreCase("Email,Notification")) {
			emailScenerioUtil.sendEmail(emailMessage, task.getOwnerOrglocationId().getId(), iAdressArray,
					new ArrayList<EmailAttachment>());
			String actionLink = "";
			emailScenerioUtil.sendNotification(actionLink, task.getTaskOwnerId(), task.getAssignedUserId(),
					task.getOwnerOrglocationId(), notificationScenario, new HashMap<String, String>());
		}

	}

	@Override
	public List<Tasks> getTaskByScheduledTime(String currentTime) throws Exception {
		return taskDao.getTaskByScheduledTime(currentTime);
	}

	@Override
	public Long getTasksCompletedTodayByLocationCodeandUserId(OrgLocations orglocationCode, User user)
			throws Exception {

		return taskDao.getTasksCompletedTodayByLocationCodeandUserId(orglocationCode, user);
	}

	@Override
	public List<Tasks> getTasskByAssignedUserIdAndOrgLocationId(Long orgLocationId, Long userId) throws Exception {
		return taskDao.getTasskByAssignedUserIdAndOrgLocationId(orgLocationId, userId);
	}

	@Override
	public List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOnboardingId(Long orgLocationId, Long userId,
			Long onboardingId) throws Exception {
		return taskDao.getTasskByAssignedUserIdAndOrgLocationIdAndOnboardingId(orgLocationId, userId, onboardingId);
	}

	@Override
	public List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOffboardingId(Long orgLocationId, Long userId,
			Long offboardingId) throws Exception {
		return taskDao.getTasskByAssignedUserIdAndOrgLocationIdAndOffboardingId(orgLocationId, userId, offboardingId);
	}

	@Override
	public List<Tasks> getTasksListOffboardingId(Long offboardingId) throws Exception {
		return taskDao.getTasksListOffboardingId(offboardingId);
	}

	@Override
	public List<Tasks> getTasksListOnboardingId(Long onboardingId) throws Exception {
		return taskDao.getTasksListOnboardingId(onboardingId);
	}

	@Override
	public List<Tasks> getTaskListByRepeatStatus(String taskRepeatStatus) throws Exception {
		return taskDao.getTaskListByRepeatStatus(taskRepeatStatus);
	}

	@Override
	public List<Tasks> getTaskByAssignedUserIdAndOrgLocationIdAndVendorOnboardingId(Long orgLocationId, Long userId,
			Long vendorOnboardingId) throws Exception {
		return taskDao.getTaskByAssignedUserIdAndOrgLocationIdAndVendorOnboardingId(orgLocationId, userId,
				vendorOnboardingId);
	}

	@Override
	public Long taskCountByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId, Long userId, String status)
			throws Exception {

		return taskDao.taskCountByIdAndOrgLocationIdAssignedUserId(OrgLocationId, userId, status);
	}

	@Override
	public List<TaskDTO> getTasksListByImmigrationIdAndCandidateId(String immigrationId, User user,String taskStatus,String searchVal) throws ParseException {
		List<Tasks> tasks = taskDao.getTasksListByImmigrationIdAndCandidateId(immigrationId,user, taskStatus, searchVal);
		List<TaskDTO> taskDTOs = new ArrayList<>();
		for(Tasks task: tasks) {
			TaskDTO dto = new TaskDTO();
			BeanUtils.copyProperties(task, dto);
			DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			DateFormat dateFormatResult = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
			dto.setTaskDateStr(dateFormatResult.format(dateFormat.parse(task.getTaskDate().toString())));
			dto.setTaskOwnerName(task.getTaskOwnerId().getFullName());
			dto.setAssignedName(task.getAssignedName());
			
			taskDTOs.add(dto);
		}
		return taskDTOs;
		
	}

	@Override
	public Tasks getTaskByTaskName(String taskName) throws Exception {
		return taskDao.getTaskByTaskName(taskName);
	}

	@Override
	public void updateTaskStatus(Tasks task) throws Exception {
		if(task != null) {
			task.setStatus(Constants.RECORD_INACTIVE);
		}
		taskDao.updateTaskStatus(task);
	}
	
	
}
