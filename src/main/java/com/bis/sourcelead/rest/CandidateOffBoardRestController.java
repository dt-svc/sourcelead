package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateOffBoardingDTO;
import com.bis.sourcelead.dto.CandidatedOffboardingDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OffBoardingDocumentsDTO;
import com.bis.sourcelead.dto.OffBoardingTemplateDTO;
import com.bis.sourcelead.dto.OffboardTasksDTO;
import com.bis.sourcelead.dto.OffboardingCandidateSetpsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.entity.OffBoardingTemplate;
import com.bis.sourcelead.entity.OffboardCheckList;
import com.bis.sourcelead.entity.OffboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.OnboardStatus;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OffBoardingDocumentsService;
import com.bis.sourcelead.service.OffBoardingTemplateService;
import com.bis.sourcelead.service.OffboardCheckListService;
import com.bis.sourcelead.service.OffboardTasksService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.rest.TimesheetRestController;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class CandidateOffBoardRestController {

	private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);

	@Value("${static.path}")
	private String staticPath;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	UserService userService;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	OffBoardingTemplateService offBoardingTemplateService;

	@Autowired
	OffBoardingDocumentsService offBoardingDocumentsService;

	@Autowired
	OffboardTasksService offboardTasksService;

	@Autowired
	OffboardCheckListService offboardCheckListService;

	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;

	@Autowired
	TaskService taskService;

	@Autowired
	private OrgLocationService orgLocationService;

	@Autowired
	CandidateOnBoardingService candidateOnBoardingService;

	@Autowired
	EmailScenarioService emailScenarioService;
	@Autowired
	EmailScenerioUtil emailScenerioUtil;

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@RequestMapping(value = "/getUsersForOffboard")
	public Response<String> getUsersForOffboard(HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loginUser != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<User> usersList = userService.getUsersByOrgLocationIdExceptLoggedInUser(loginOrgLocation.getId(), loginUser.getId());
				List<User> orgUsersList = new ArrayList<User>();
				if (null != usersList && usersList.size() > 0) {
					for (User user : usersList) {
						StringBuilder userName = new StringBuilder();
						if (null != user.getLastName()) {
							user.setUserName(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
						} else {
							user.setUserName(userName.append(user.getFirstName()).toString());
						}
						orgUsersList.add(user);
					}
				}
				response.setObject(commonUtil.toJSON(usersList));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/getOffboardUserDetailsById")
	public Response<String> getOffboardUserDetailsById(@RequestParam(value = "offboardUserId", required = false) Long offboardUserId,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(offboardUserId);
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				Employees employees = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), loginOrgLocation.getId());
				// date format
				// SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
				if (null != employees) {
					if (null != employees.getHireDate()) {
						user.setDateOfJoin(
								commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), employees.getHireDate()));
					}
					if (!isNullOrEmpty(employees.getUserEmploymentType())) {
						if (!isNullOrEmpty(employees.getDesignationOtherType())) {
							user.setEmploymentType(employees.getDesignationOtherType());
						} else {
							user.setEmploymentType(employees.getUserEmploymentType());
						}

					}
				}
				UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), loginOrgLocation.getId());
				user.setRole(userRole.getRoles().getDisplayName());

				CandidateOnBoarding onboarding = candidateOnBoardingService.getCandidateOnBoardingByUserIdAndLocationId(offboardUserId,
						loginOrgLocation.getId());
				if (null != onboarding) {
					user.setNoticePeriod(onboarding.getTerminationNotice());
				}
				response.setObject(commonUtil.toJSON(user));
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/getOffboardingTemplatesList")
	public Response<String> getOffboardingTemplatesList(HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			String resultJson;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

			List<OffBoardingTemplate> templatesList = offBoardingTemplateService.getOffBoardingTemplatesList(loginOrgLocation.getId());
			List<OffBoardingTemplate> offboardingTemplatesList = new ArrayList<OffBoardingTemplate>();
			// SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
			for (OffBoardingTemplate template : templatesList) {
				template.setTemplateCreatedDate(
						commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), template.getCreatedDate()));
				offboardingTemplatesList.add(template);
			}
			resultJson = commonUtil.toJSON(offboardingTemplatesList);
			response.setObject(resultJson);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/createNewOffBoardingTemplate", method = RequestMethod.POST)
	public @ResponseBody Response<OffBoardingTemplate> createNewOffBoardingTemplate(OffBoardingTemplateDTO offBoardingTemplateDTO,
			HttpServletRequest request) throws Exception {
		Response<OffBoardingTemplate> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(offBoardingTemplateDTO)) {
				OffBoardingTemplate offBoardingTemplate = null;
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (StringUtils.isNotBlank(offBoardingTemplateDTO.getTemplateId())) {
					offBoardingTemplate = offBoardingTemplateService
							.getOffBoardingTemplateById(Long.valueOf(offBoardingTemplateDTO.getTemplateId()));
					offBoardingTemplate.setTemplateName(offBoardingTemplateDTO.getTemplateName());
					offBoardingTemplate.setDescription(offBoardingTemplateDTO.getDescription().trim());
					offBoardingTemplate.setUpdatedBy(user.getUserCode());
					offBoardingTemplate.setUpdatedDate(new Date());
				} else {
					offBoardingTemplate = new OffBoardingTemplate();
					offBoardingTemplate.setTemplateName(offBoardingTemplateDTO.getTemplateName());
					offBoardingTemplate.setDescription(offBoardingTemplateDTO.getDescription().trim());
					offBoardingTemplate.setOrgLocationId(orgLocations);

					StringBuilder userName = new StringBuilder();
					if (!isNullOrEmpty(user.getLastName())) {
						offBoardingTemplate
								.setCreatedUser(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					} else {
						offBoardingTemplate.setCreatedUser(userName.append(user.getFirstName()).toString());
					}
					offBoardingTemplate.setTemplateStatus("Draft");
					offBoardingTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
					offBoardingTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					offBoardingTemplate.setCreatedBy(user.getUserCode());
					offBoardingTemplate.setCreatedDate(new Date());
					offBoardingTemplate.setUpdatedBy(user.getUserCode());
					offBoardingTemplate.setUpdatedDate(new Date());
				}
				offBoardingTemplate = offBoardingTemplateService.saveOffBoardingTemplate(offBoardingTemplate);
				response.setObject(offBoardingTemplate);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = { "/getOffboardTemplateById", "/rest/getOffboardTemplateById" }, method = RequestMethod.POST)
	public @ResponseBody Response<OffBoardingTemplateDTO> getOffboardTemplateById(
			@RequestParam(value = "templateId", required = false) Long templateId, HttpServletRequest request) throws Exception {
		Response<OffBoardingTemplateDTO> response = new Response<>();
		try {
			OffBoardingTemplateDTO offBoardingTemplateDTO = new OffBoardingTemplateDTO();
			OffBoardingTemplate offBoardingTemplate = offBoardingTemplateService.getOffBoardingTemplateById(templateId);
			offBoardingTemplateDTO.setTemplateName(offBoardingTemplate.getTemplateName());
			offBoardingTemplateDTO.setDescription(offBoardingTemplate.getDescription());
			// resultJson = commonUtil.toJSON(offBoardingTemplate);
			response.setObject(offBoardingTemplateDTO);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveOffboardingTemplateDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveOffboardingTemplateDocuments(OffBoardingTemplateDTO offBoardingTemplateDTO,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loginUser != null) {
				offBoardingTemplateDTO.setStaticPath(staticPath);
				// Resignation / Termination Letter documents saving
				if (offBoardingTemplateDTO.getResignationFileInfo() != null && offBoardingTemplateDTO.getResignationFileInfo().size() > 0) {
					OffBoardingTemplate template = offBoardingTemplateService
							.getOffBoardingTemplateById(Long.valueOf(offBoardingTemplateDTO.getTemplateId()));
					for (FileInfo fileInfo : offBoardingTemplateDTO.getResignationFileInfo()) {
						OffBoardingDocuments documents = new OffBoardingDocuments();
						if (fileInfo != null && fileInfo.getFileURL() != null) {
							documents.setFileName(fileInfo.getFileName());
							documents.setFileContentType(fileInfo.getContentType());
							documents.setFileURL(fileInfo.getFileURL());
						}
						documents.setOffboardingTemplateId(template);
						documents.setDocumentType("Resignation");
						documents.setStatus(Constants.CANDIDATE_ACTIVE);
						documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						documents.setCreatedDate(CommonUtil.currentDate());
						documents.setCreatedBy(loginUser.getUserCode());
						documents.setUpdatedDate(CommonUtil.currentDate());
						documents.setUpdatedBy(loginUser.getUserCode());
						offBoardingDocumentsService.saveOffBoardingDocuments(documents);
						FileUploadUtil.moveFile(offBoardingTemplateDTO.getStaticPath(), fileInfo,
								"Offboard-Template-Resignation-Documents");
					}
				}

				// Offboard Pack documents saving
				if (offBoardingTemplateDTO.getOffboardPackFileInfo() != null
						&& offBoardingTemplateDTO.getOffboardPackFileInfo().size() > 0) {
					OffBoardingTemplate template = offBoardingTemplateService
							.getOffBoardingTemplateById(Long.valueOf(offBoardingTemplateDTO.getTemplateId()));
					for (FileInfo fileInfo : offBoardingTemplateDTO.getOffboardPackFileInfo()) {
						OffBoardingDocuments documents = new OffBoardingDocuments();
						if (fileInfo != null && fileInfo.getFileURL() != null) {
							documents.setFileName(fileInfo.getFileName());
							documents.setFileContentType(fileInfo.getContentType());
							documents.setFileURL(fileInfo.getFileURL());
						}
						documents.setOffboardingTemplateId(template);
						documents.setDocumentType("Offboard Pack");
						documents.setStatus(Constants.CANDIDATE_ACTIVE);
						documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						documents.setCreatedDate(CommonUtil.currentDate());
						documents.setCreatedBy(loginUser.getUserCode());
						documents.setUpdatedDate(CommonUtil.currentDate());
						documents.setUpdatedBy(loginUser.getUserCode());
						offBoardingDocumentsService.saveOffBoardingDocuments(documents);
						FileUploadUtil.moveFile(offBoardingTemplateDTO.getStaticPath(), fileInfo,
								"Offboard-Template-OffboardPack-Documents");
					}
				}
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = { "/getOffboardTemplateDocumentByTemplateId",
			"/rest/getOffboardTemplateDocumentByTemplateId" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<OffBoardingDocumentsDTO> getOffboardTemplateDocumentByTemplateId(
			@RequestParam(value = "templateId", required = false) Long templateId,
			@RequestParam(value = "documentType", required = false) String documentType, HttpServletRequest request) throws Exception {
		ResponseList<OffBoardingDocumentsDTO> responseList = new ResponseList<>();
		try {
			List<OffBoardingDocumentsDTO> documentsListDTO = new ArrayList<>();
			List<OffBoardingDocuments> documentsList = offBoardingDocumentsService.getOffBoardingDocumentsByTemplateId(templateId,
					documentType);
			for (OffBoardingDocuments offboardDocs : documentsList) {
				OffBoardingDocumentsDTO offBoardingDocumentsDTO = new OffBoardingDocumentsDTO();
				offBoardingDocumentsDTO.setId(offboardDocs.getId());
				offBoardingDocumentsDTO.setFileName(offboardDocs.getFileName());
				offBoardingDocumentsDTO.setFileURL(offboardDocs.getFileURL());
				offBoardingDocumentsDTO.setDocumentUUID(offboardDocs.getDocumentUUID());
				documentsListDTO.add(offBoardingDocumentsDTO);
			}
			// resultJson = commonUtil.toJSON(documentsList);
			responseList.setData(documentsListDTO);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return responseList;
	}

	@RequestMapping(value = "/validateOffboardingTemplateName", method = RequestMethod.POST)
	@ResponseBody
	Response<String> validateOffboardingTemplateName(@RequestParam(value = "templateName", required = false) String templateName,
			@RequestParam(value = "templateId", required = false) String templateId, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			OffBoardingTemplate offBoardingTemplate = null;
			if (!isNullOrEmpty(templateId)) {
				offBoardingTemplate = offBoardingTemplateService.getOffBoardingTemplateById(Long.parseLong(templateId));
				if (offBoardingTemplate.getTemplateName().equalsIgnoreCase(templateName)) {
					response.setObject("valid");
				} else {
					offBoardingTemplate = offBoardingTemplateService.getOffBoardingTemplateByTemplateNameAndOrgLocationId(templateName,
							orgLocations.getId());
					if (null != offBoardingTemplate) {
						response.setObject("invalid");
					} else {
						response.setObject("valid");
					}
				}
			} else {
				offBoardingTemplate = offBoardingTemplateService.getOffBoardingTemplateByTemplateNameAndOrgLocationId(templateName,
						orgLocations.getId());
				if (null != offBoardingTemplate) {
					response.setObject("invalid");
				} else {
					response.setObject("valid");
				}
			}
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = { "/getOffboardTasksListByTemplateId", "/rest/getOffboardTasksListByTemplateId" }, method = RequestMethod.POST)
	@ResponseBody
	ResponseList<OffboardTasksDTO> getOffboardTasksListByTemplateId(@RequestParam(value = "templateId", required = false) Long templateId,
			HttpServletRequest request) throws Exception {
		ResponseList<OffboardTasksDTO> responseList = new ResponseList<>();
		try {
			List<OffboardTasks> tasksList = offboardTasksService.getOffboardTasksByTemplateId(templateId);
			List<OffboardTasksDTO> offboardtasksListDTO = new ArrayList<OffboardTasksDTO>();
			for (OffboardTasks tasks : tasksList) {
				OffboardTasksDTO offboardTasksDTO = new OffboardTasksDTO();
				List<String> usersList = new ArrayList<String>();
				if (!isNullOrEmpty(tasks.getAssignToUsers())) {
					String[] userIds = tasks.getAssignToUsers().split(",");
					for (int i = 0; i < userIds.length; i++) {
						StringBuilder userName = new StringBuilder();
						User user = userService.getUserById(Long.valueOf(userIds[i]));
						String assignedUser = null;
						if (null != user.getLastName()) {
							assignedUser = userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
						} else {
							assignedUser = userName.append(user.getFirstName()).toString();
						}
						usersList.add(assignedUser);
					}
				}
				StringBuilder dueAfter = new StringBuilder();
				if (null != tasks.getDueAfterMonths()) {
					dueAfter = dueAfter.append(tasks.getDueAfterMonths()).append(" Month(s) ");
				}
				if (null != tasks.getDueAfterDays()) {
					dueAfter = dueAfter.append(tasks.getDueAfterDays()).append(" Day(s) ");
				}
				if (null != tasks.getDueAfterHours()) {
					dueAfter = dueAfter.append(tasks.getDueAfterHours()).append(" Hours ");
				}
				if (null != tasks.getDueAfterMinutes()) {
					dueAfter = dueAfter.append(tasks.getDueAfterMinutes()).append(" Minutes");
				}
				offboardTasksDTO.setDueAfterString(dueAfter.toString());
				offboardTasksDTO.setUsersList(usersList);
				offboardTasksDTO.setPriority(tasks.getPriority());
				offboardTasksDTO.setId((tasks.getId()).toString());
				offboardTasksDTO.setTaskName(tasks.getTaskName());
				offboardTasksDTO.setTaskRepeat(tasks.getTaskRepeat());
				offboardtasksListDTO.add(offboardTasksDTO);
			}
			responseList.setData(offboardtasksListDTO);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return responseList;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/saveAndFinishOffboardTemplate", method = RequestMethod.POST)
	@ResponseBody
	Response<String> saveAndFinishOffboardTemplate(@RequestParam(value = "templateId", required = false) Long templateId,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null){
				OffBoardingTemplate template = offBoardingTemplateService.getOffBoardingTemplateById(templateId);
				OffBoardingTemplate temp=null;
				if (null != template) {
					template.setTemplateStatus("Finished");
					temp=offBoardingTemplateService.saveOffBoardingTemplate(template);
					SourceLeadSessionManager.setSessionAttribute(request, "OffboardCreateMsg", "Success!! New OffBoard Template created");
				}
				if(temp != null){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
			}else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
			
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveOffBoardTasks", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveOffBoardTasks(OffboardTasksDTO offboardTasksDTO, HttpServletRequest request)
			throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null && Validator.validate(offboardTasksDTO)) {
				OffboardTasks offboardTasks = new OffboardTasks();
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskName())) {
					offboardTasks.setTaskName(offboardTasksDTO.getTaskName());
				}
				OffBoardingTemplate template = new OffBoardingTemplate();
				template.setId(Long.valueOf(offboardTasksDTO.getTemplateId()));
				offboardTasks.setOffboardingTemplateId(template);
				if (StringUtils.isNotBlank(offboardTasksDTO.getOnboardAssignedTo())) {
					offboardTasks.setAssignToUsers(offboardTasksDTO.getOnboardAssignedTo());
				} else {
					offboardTasks.setAssignToCandidate(Constants.YES);
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskPriority())) {
					offboardTasks.setPriority(offboardTasksDTO.getTaskPriority());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskRepeat())) {
					offboardTasks.setTaskRepeat(offboardTasksDTO.getTaskRepeat());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskMonths())) {
					offboardTasks.setDueAfterMonths(Integer.parseInt(offboardTasksDTO.getTaskMonths()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskDays())) {
					offboardTasks.setDueAfterDays(Integer.parseInt(offboardTasksDTO.getTaskDays()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskHours())) {
					offboardTasks.setDueAfterHours(Integer.parseInt(offboardTasksDTO.getTaskHours()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskMinutes())) {
					offboardTasks.setDueAfterMinutes(Integer.parseInt(offboardTasksDTO.getTaskMinutes()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskNotification())) {
					offboardTasks.setTaskNotification(offboardTasksDTO.getTaskNotification());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskEmail())) {
					offboardTasks.setTaskEmail(offboardTasksDTO.getTaskEmail());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskdescription())) {
					offboardTasks.setDescription(offboardTasksDTO.getTaskdescription());
				}
				offboardTasks.setStatus(Constants.CANDIDATE_ACTIVE);
				offboardTasks.setCreatedDate(CommonUtil.currentDate());
				offboardTasks.setCreatedBy(user.getUserCode());
				offboardTasks.setUpdatedDate(CommonUtil.currentDate());
				offboardTasks.setUpdatedBy(user.getUserCode());
				offboardTasksService.saveOffboardTasks(offboardTasks);
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	/*
	 * @RequestMapping(value = "/downloadOffboardDocument", method =
	 * RequestMethod.GET) public void
	 * downloadOffboardDocument(@RequestParam(value="documentUUID", required=false)
	 * String documentUUID ,HttpServletResponse response) throws Exception {
	 * 
	 * 
	 * OffBoardingDocuments templateDocuments =
	 * offBoardingDocumentsService.getOffBoardingDocumentsBydocumentUUID(
	 * documentUUID);
	 * 
	 * response.setContentType("application/octet-stream"); StringBuilder fileName =
	 * new StringBuilder(); response.setHeader("Content-Disposition",
	 * fileName.append("attachment; filename=\"").append(templateDocuments.
	 * getFileName()).append("\"").toString()); InputStream inputStream = new
	 * ByteArrayInputStream(templateDocuments.getDocFile()); try { OutputStream
	 * outStream = response.getOutputStream();
	 * 
	 * byte[] buffer = new byte[4096]; int bytesRead = -1;
	 * 
	 * while ((bytesRead = inputStream.read(buffer)) != -1) {
	 * outStream.write(buffer, 0, bytesRead); }
	 * 
	 * inputStream.close(); outStream.close();
	 * 
	 * } catch (FileNotFoundException e) {
	 * logger.error(ExceptionUtils.getFullStackTrace(e)); }
	 * 
	 * }
	 */

	@RequestMapping(value = "/deleteOffBoardingDocumentByDocumentUUID", method = RequestMethod.POST)
	public @ResponseBody Response<String> deleteOffBoardingDocumentByDocumentUUID(
			@RequestParam(value = "documentUUID", required = false) String documentUUID, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OffBoardingDocuments templateDocuments = offBoardingDocumentsService.getOffBoardingDocumentsBydocumentUUID(documentUUID);
			if (null != templateDocuments) {
				templateDocuments.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				offBoardingDocumentsService.saveOffBoardingDocuments(templateDocuments);
			}
			response.setObject("success");
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/loadOffboardCheckListByTemplateId", method = RequestMethod.GET)
	public Response<String> loadOffboardCheckListByTemplateId(@RequestParam(value = "templateId", required = false) Long templateId)
			throws Exception {
		Response<String> response = new Response<>();
		try {
				OffboardCheckList checkList = offboardCheckListService.getOffboardCheckListByTemplateId(templateId);
				if (null != checkList) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					response.setObject(checkList.getCheckListJsonString());
				}
		 }catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
				e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveOffboardTemplateCheckList", method = RequestMethod.POST)
	public Response<String> saveOffboardTemplateCheckList(
			@RequestParam(value = "checkListTemplateId", required = false) String checkListTemplateId,
			@RequestParam(value = "jsonStringData", required = false) String jsonStringData, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OffboardCheckList offboardCheckList = null;
				OffBoardingTemplate template = null;
				if (checkListTemplateId != null) {
					offboardCheckList = offboardCheckListService.getOffboardCheckListByTemplateId(Long.valueOf(checkListTemplateId));
					if (offboardCheckList == null) {
						offboardCheckList = new OffboardCheckList();
					}

					template = new OffBoardingTemplate();
					template.setId(Long.valueOf(checkListTemplateId));
					offboardCheckList.setOffboardingTemplateId(template);
					offboardCheckList.setCheckListJsonString(jsonStringData);
					offboardCheckList.setStatus(Constants.CANDIDATE_ACTIVE);
					offboardCheckList.setCreatedBy(user.getUserCode());
					offboardCheckList.setCreatedDate(new Date());
					offboardCheckList.setUpdatedBy(user.getUserCode());
					offboardCheckList.setUpdatedDate(new Date());
					offboardCheckListService.saveOffboardCheckList(offboardCheckList);
				}
				response.setObject("home");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/getOffboardingTemplatesForCandidateOffBoarding")
	public Response<String> getOffboardingTemplatesForCandidateOffBoarding(HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<OffBoardingTemplate> teamplatesList = offBoardingTemplateService
					.getOffBoardingFinishedTemplatesList(loginOrgLocation.getId());
			response.setObject(commonUtil.toJSON(teamplatesList));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveOffboardingCandidateDetails", method = RequestMethod.POST)
	public @ResponseBody Response<CandidateOffBoarding> saveOffboardingCandidateDetails(CandidateOffBoardingDTO candidateOffBoardingDTO,
			HttpServletRequest request) throws Exception {
		Response<CandidateOffBoarding> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				CandidateOffBoarding candidateOffBoarding = candidateOffBoardingService
						.saveOffBoardingCandidatesDetails(candidateOffBoardingDTO, loginOrgLocation, user);
				response.setObject(candidateOffBoarding);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = { "/getCandidateOffboardDocumentByOffboardId",
			"/rest//getCandidateOffboardDocumentByOffboardId" }, method = RequestMethod.POST)
	public @ResponseBody ResponseList<OffBoardingDocumentsDTO> getCandidateOffboardDocumentByOffboardId(
			@RequestParam(value = "offboardId", required = false) Long offboardId,
			@RequestParam(value = "documentType", required = false) String documentType, HttpServletRequest request) throws Exception {
		ResponseList<OffBoardingDocumentsDTO> responseList = new ResponseList<>();
		try {
			List<OffBoardingDocumentsDTO> offboardingDocumentsListDTO = new ArrayList<OffBoardingDocumentsDTO>();
			List<OffBoardingDocuments> documentsList = null;
			Integer documentsCount = offBoardingDocumentsService.getOffboardingDocumentsByOffboardingId(offboardId);
			if (documentsCount > 0) {
				documentsList = offBoardingDocumentsService.getOffBoardingDocumentsByCandidateOffBoardingId(offboardId, documentType);
				if (null != documentsList && documentsList.size() > 0) {
					for (OffBoardingDocuments documents : documentsList) {
						documents.setOffboardDocumentType("Candidate Documents");
						offboardingDocumentsListDTO.add(getOffboardingDocuments(documents));
					}
				}
			} else {
				CandidateOffBoarding offboarding = candidateOffBoardingService.getCandidateOffBoardingById(offboardId);
				List<Long> templateIdsList = new ArrayList<Long>();
				if (!isNullOrEmpty(offboarding.getOffboardTemplateId())) {
					for (String s : offboarding.getOffboardTemplateId().split(",")) {
						templateIdsList.add(Long.parseLong(s));
					}
					documentsList = offBoardingDocumentsService.getOffBoardingDocumentsByTemplateIdsList(templateIdsList, documentType);
					if (null != documentsList && documentsList.size() > 0) {
						for (OffBoardingDocuments documents : documentsList) {
							documents.setOffboardDocumentType("Template Documents");
							offboardingDocumentsListDTO.add(getOffboardingDocuments(documents));
						}
					}
				}
			}
			responseList.setData(offboardingDocumentsListDTO);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return responseList;
	}

	public OffBoardingDocumentsDTO getOffboardingDocuments(OffBoardingDocuments offBoardingDocuments) {
		OffBoardingDocumentsDTO offBoardingDocumentsDTO = new OffBoardingDocumentsDTO();
		offBoardingDocumentsDTO.setId(offBoardingDocuments.getId());
		offBoardingDocumentsDTO.setFileName(offBoardingDocuments.getFileName());
		offBoardingDocumentsDTO.setFileURL(offBoardingDocuments.getFileURL());
		offBoardingDocumentsDTO.setDocumentUUID(offBoardingDocuments.getDocumentUUID());
		offBoardingDocumentsDTO.setOffboardDocumentType(offBoardingDocuments.getOffboardDocumentType());
		return offBoardingDocumentsDTO;
	}

	@RequestMapping(value = "/getOffboardCandidateList")
	public ResponseList<CandidateOffBoardingDTO> getOffboardCandidateList(CandidateOffBoardingDTO candidateOffBoardingDTO,
			@RequestParam("locationCode") String locationCode) throws Exception {

		OrgLocations loginOrgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);

		String[] tableColumns = { "firstName", "endDate", "effectiveDate", "offboardStage", "offboardStatus" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (candidateOffBoardingDTO.getOrder() != null && !candidateOffBoardingDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(candidateOffBoardingDTO.getOrder().get(0).get("column"));
			sortColumnOrder = candidateOffBoardingDTO.getOrder().get(0).get("dir");
		}
		candidateOffBoardingDTO.setSortColumn(tableColumns[sortColumnIndex]);
		candidateOffBoardingDTO.setSortOrder(sortColumnOrder);
		ResponseList<CandidateOffBoardingDTO> candidateOffBoardingList = candidateOffBoardingService
				.getCandidateOffBoardingList(candidateOffBoardingDTO, loginOrgLocation);
		return candidateOffBoardingList;
	}

	@RequestMapping(value = { "/loadOffboardingCandidateDetails", "/rest/loadOffboardingCandidateDetails" }, method = RequestMethod.POST)
	public Response<CandidateOffBoardingDTO> loadOffboardingCandidateDetails(
			@RequestParam(value = "offBoardingUUID", required = false) String offBoardingUUID,
			@RequestParam(value = "locationCode") String locationCode) throws Exception {
		Response<CandidateOffBoardingDTO> response = new Response<>();
		try {
			StringBuilder candidateName = new StringBuilder();
			CandidateOffBoardingDTO candidateOffBoardingDTO = new CandidateOffBoardingDTO();
			// date format
			OrgLocations locations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			// SimpleDateFormat dateFormat = locations.getLocFullFormDateFormatter();
			CandidateOffBoarding candidateOffBoarding = candidateOffBoardingService.getCandidateOffBoardingByOffboardUUID(offBoardingUUID);
			if (null != candidateOffBoarding) {
				candidateOffBoardingDTO.setOffboardUser((candidateOffBoarding.getUserId().getId()).toString());
				if (null != candidateOffBoarding.getDateOfJoin()) {
					candidateOffBoardingDTO.setDateOfJoinStr(
							commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(), candidateOffBoarding.getDateOfJoin()));
				}
				if (null != candidateOffBoarding.getEndDate()) {
					candidateOffBoardingDTO.setEndDateStr(
							commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(), candidateOffBoarding.getEndDate()));
				}
				if (null != candidateOffBoarding.getEffectiveDate()) {
					candidateOffBoardingDTO.setEffectiveDateStr(
							commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(), candidateOffBoarding.getEffectiveDate()));
				}
				if (null != candidateOffBoarding.getLastName()) {
					candidateOffBoardingDTO.setCandidateName(candidateName.append(candidateOffBoarding.getFirstName()).append(" ")
							.append(candidateOffBoarding.getLastName()).toString());
				} else {
					candidateOffBoardingDTO.setCandidateName(candidateName.append(candidateOffBoarding.getFirstName()).toString());
				}
				if (null != candidateOffBoarding.getFirstName()) {
					candidateOffBoardingDTO.setFirstName(candidateOffBoarding.getFirstName());
				}
				if (null != candidateOffBoarding.getLastName()) {
					candidateOffBoardingDTO.setLastName(candidateOffBoarding.getLastName());
				}
				if (null != candidateOffBoarding.getMobileNumber()) {
					candidateOffBoardingDTO.setMobileNumber(candidateOffBoarding.getMobileNumber());
				}
				if (null != candidateOffBoarding.getEmail()) {
					candidateOffBoardingDTO.setEmail(candidateOffBoarding.getEmail());
				}
				if (null != candidateOffBoarding.getRole()) {
					candidateOffBoardingDTO.setRole(candidateOffBoarding.getRole());
				}
				if (null != candidateOffBoarding.getEmploymentType()) {
					candidateOffBoardingDTO.setEmploymentType(candidateOffBoarding.getEmploymentType());
				}
				if (null != candidateOffBoarding.getReason()) {
					candidateOffBoardingDTO.setReason(candidateOffBoarding.getReason());
				}
				if (null != candidateOffBoarding.getNoticePeriod()) {
					candidateOffBoardingDTO.setNoticePeriod(candidateOffBoarding.getNoticePeriod());
				}
				if (null != candidateOffBoarding.getHaveToWorkInNoticePeriod()) {
					candidateOffBoardingDTO.setHaveToWorkInNoticePeriod(candidateOffBoarding.getHaveToWorkInNoticePeriod());
				}
				if (null != candidateOffBoarding.getExplanation()) {
					candidateOffBoardingDTO.setExplanation(candidateOffBoarding.getExplanation());
				}
				if (null != candidateOffBoarding.getEffectiveDate()) {
					candidateOffBoardingDTO.setEffectiveDate(candidateOffBoarding.getEffectiveDate().toString());
				}
				if (null != candidateOffBoarding.getOffboardTemplateId()) {
					candidateOffBoardingDTO.setOffboardTemplateId(candidateOffBoarding.getOffboardTemplateId());
				}
				if (null != candidateOffBoarding.getId()) {
					candidateOffBoardingDTO.setSavedoffboardId(candidateOffBoarding.getId().toString());
				}
				if (candidateOffBoarding.getRating() != null) {
					candidateOffBoardingDTO.setRating(candidateOffBoarding.getRating());
				}
			}
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
			response.setObject(candidateOffBoardingDTO);
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveCandidatedOffboardingDocuments", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidatedOffboardingDocuments(CandidatedOffboardingDTO candidatedOffboardingDTO,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (loginUser != null) {
				candidatedOffboardingDTO.setStaticPath(staticPath);
				CandidateOffBoarding offBoarding = candidateOffBoardingService
						.getCandidateOffBoardingById(Long.valueOf(candidatedOffboardingDTO.getCandidateoffboardingId()));
				// Resignation / Termination Letter documetns saving....
				if (candidatedOffboardingDTO.getResignationFileInfo() != null
						&& candidatedOffboardingDTO.getResignationFileInfo().size() > 0) {
					for (FileInfo fileInfo : candidatedOffboardingDTO.getResignationFileInfo()) {
						OffBoardingDocuments documents = new OffBoardingDocuments();
						if (fileInfo != null && fileInfo.getFileURL() != null) {
							documents.setFileName(fileInfo.getFileName());
							documents.setFileContentType(fileInfo.getContentType());
							documents.setFileURL(fileInfo.getFileURL());
						}
						documents.setCandidateOffBoardingId(offBoarding);
						documents.setDocumentType("Resignation");
						documents.setStatus(Constants.CANDIDATE_ACTIVE);
						documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						documents.setCreatedDate(CommonUtil.currentDate());
						documents.setCreatedBy(loginUser.getUserCode());
						documents.setUpdatedDate(CommonUtil.currentDate());
						documents.setUpdatedBy(loginUser.getUserCode());
						offBoardingDocumentsService.saveOffBoardingDocuments(documents);
						FileUploadUtil.moveFile(candidatedOffboardingDTO.getStaticPath(), fileInfo,
								"Employee-Offboard-Resignation-Documents");
					}
				}
				// Resignation / Termination Letter documetns updating....
				if (StringUtils.isNotBlank(candidatedOffboardingDTO.getSavedResignationAttcahments())) {
					String[] savedDocumentIds = candidatedOffboardingDTO.getSavedResignationAttcahments().split(",");
					for (int i = 0; i < savedDocumentIds.length; i++) {
						OffBoardingDocuments templateDocument = offBoardingDocumentsService
								.getOffBoardingDocumentsById(Long.valueOf(savedDocumentIds[i]));
						OffBoardingDocuments documents = new OffBoardingDocuments();
						documents.setCandidateOffBoardingId(offBoarding);
						documents.setFileName(templateDocument.getFileName());
						documents.setFileContentType(templateDocument.getFileContentType());
						documents.setDocumentStatus(OnboardStatus.NEW);
						documents.setFileURL(templateDocument.getFileURL());
						documents.setDocumentType(templateDocument.getDocumentType());
						documents.setStatus(Constants.CANDIDATE_ACTIVE);
						documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						documents.setCreatedDate(CommonUtil.currentDate());
						documents.setCreatedBy(loginUser.getUserCode());
						documents.setUpdatedDate(CommonUtil.currentDate());
						documents.setUpdatedBy(loginUser.getUserCode());
						offBoardingDocumentsService.saveOffBoardingDocuments(documents);
					}
				}
				// Offboard Package documetns saving....
				if (candidatedOffboardingDTO.getOffboardPackFileInfo() != null
						&& candidatedOffboardingDTO.getOffboardPackFileInfo().size() > 0) {
					for (FileInfo fileInfo : candidatedOffboardingDTO.getOffboardPackFileInfo()) {
						OffBoardingDocuments documents = new OffBoardingDocuments();
						if (fileInfo != null && fileInfo.getFileURL() != null) {
							documents.setFileName(fileInfo.getFileName());
							documents.setFileContentType(fileInfo.getContentType());
							documents.setFileURL(fileInfo.getFileURL());
						}
						documents.setCandidateOffBoardingId(offBoarding);
						documents.setDocumentType("Offboard Pack");
						documents.setStatus(Constants.CANDIDATE_ACTIVE);
						documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						documents.setCreatedDate(CommonUtil.currentDate());
						documents.setCreatedBy(loginUser.getUserCode());
						documents.setUpdatedDate(CommonUtil.currentDate());
						documents.setUpdatedBy(loginUser.getUserCode());
						offBoardingDocumentsService.saveOffBoardingDocuments(documents);
						FileUploadUtil.moveFile(candidatedOffboardingDTO.getStaticPath(), fileInfo,
								"Employee-Offboard-OffboardPack-Documents");
					}
				}
				// Offboard Package documetns updating....
				if (StringUtils.isNotBlank(candidatedOffboardingDTO.getSavedOffboardPackAttcahments())) {
					String[] savedDocumentIds = candidatedOffboardingDTO.getSavedOffboardPackAttcahments().split(",");
					for (int i = 0; i < savedDocumentIds.length; i++) {
						OffBoardingDocuments templateDocument = offBoardingDocumentsService
								.getOffBoardingDocumentsById(Long.valueOf(savedDocumentIds[i]));
						OffBoardingDocuments documents = new OffBoardingDocuments();
						documents.setCandidateOffBoardingId(offBoarding);
						documents.setFileName(templateDocument.getFileName());
						documents.setFileContentType(templateDocument.getFileContentType());
						documents.setDocumentStatus(OnboardStatus.NEW);
						documents.setFileURL(templateDocument.getFileURL());
						documents.setDocumentType(templateDocument.getDocumentType());
						documents.setStatus(Constants.CANDIDATE_ACTIVE);
						documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						documents.setCreatedDate(CommonUtil.currentDate());
						documents.setCreatedBy(loginUser.getUserCode());
						documents.setUpdatedDate(CommonUtil.currentDate());
						documents.setUpdatedBy(loginUser.getUserCode());
						offBoardingDocumentsService.saveOffBoardingDocuments(documents);
					}
				}
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/loadOffboardCheckListByOffboardingId", method = RequestMethod.GET)
	public Response<String> loadOffboardCheckListByOffboardingId(
			@RequestParam(value = "offboardingId", required = false) Long offboardingId) throws Exception {
		Response<String> response = new Response<>();
		try {
			Integer checkListCount = offboardCheckListService.getOffboardCheckListCountByOffboardId(offboardingId);
			List<OffboardCheckList> offboardCheckList = new ArrayList<OffboardCheckList>();
			if (checkListCount > 0) {
				OffboardCheckList checkList = offboardCheckListService.getOffboardCheckListByOffboardingId(offboardingId);
				offboardCheckList.add(checkList);
			} else {
				CandidateOffBoarding offboarding = candidateOffBoardingService.getCandidateOffBoardingById(offboardingId);
				if (!isNullOrEmpty(offboarding.getOffboardTemplateId())) {
					String[] templateIds = offboarding.getOffboardTemplateId().split(",");
					for (int i = 0; i < templateIds.length; i++) {
						OffboardCheckList checkList = offboardCheckListService
								.getOffboardCheckListByTemplateId(Long.valueOf(templateIds[i]));
						offboardCheckList.add(checkList);
					}
				}
			}
			response.setObject(commonUtil.toJSON(offboardCheckList));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveCandidateOffboardCheckList", method = RequestMethod.POST)
	public Response<String> saveCandidateOffboardCheckList(
			@RequestParam(value = "checkListoffboardId", required = false) String checkListoffboardId,
			@RequestParam(value = "jsonStringData", required = false) String jsonStringData, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OffboardCheckList offboardCheckList = null;
				CandidateOffBoarding offboarding = null;
				if (StringUtils.isNotBlank(checkListoffboardId)) {
					offboardCheckList = offboardCheckListService.getOffboardCheckListByOffboardingId(Long.valueOf(checkListoffboardId));
					if (offboardCheckList == null) {
						offboardCheckList = new OffboardCheckList();
					}
					offboarding = new CandidateOffBoarding();
					offboarding.setId(Long.valueOf(checkListoffboardId));
					offboardCheckList.setCandidateOffBoardingId(offboarding);
					offboardCheckList.setCheckListJsonString(jsonStringData);
					offboardCheckList.setStatus(Constants.CANDIDATE_ACTIVE);
					offboardCheckList.setCreatedBy(user.getUserCode());
					offboardCheckList.setCreatedDate(new Date());
					offboardCheckList.setUpdatedBy(user.getUserCode());
					offboardCheckList.setUpdatedDate(new Date());
					offboardCheckListService.saveOffboardCheckList(offboardCheckList);
				}
				response.setObject("home");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = { "/getCandidateOffboardTasksList", "/rest/getCandidateOffboardTasksList" }, method = RequestMethod.POST)
	@ResponseBody
	ResponseList<OffboardTasksDTO> getCandidateOffboardTasksList(@RequestParam(value = "offboardId", required = false) Long offboardId,
			HttpServletRequest request) throws Exception {
		ResponseList<OffboardTasksDTO> responseList = new ResponseList<>();
		try {
			List<OffboardTasks> templateTasksList = null;
			List<OffboardTasks> tasksList = new ArrayList<OffboardTasks>();
			List<OffboardTasks> candidateTasksList = offboardTasksService.getOffBoardAddedTasksListByCandidateOffBoardingId(offboardId);
			tasksList.addAll(candidateTasksList);
			if (candidateTasksList.size() == 0) {
				CandidateOffBoarding offboarding = candidateOffBoardingService.getCandidateOffBoardingById(offboardId);
				List<Long> templateIdsList = new ArrayList<Long>();
				if (!isNullOrEmpty(offboarding.getOffboardTemplateId())) {
					for (String s : offboarding.getOffboardTemplateId().split(",")) {
						templateIdsList.add(Long.parseLong(s));
					}
					templateTasksList = offboardTasksService.getOffboardTasksByTemplateIdList(templateIdsList);
					if (null != templateTasksList) {
						tasksList.addAll(templateTasksList);
					}
				}
			}
			List<OffboardTasksDTO> offboardtasksListDTO = new ArrayList<OffboardTasksDTO>();
			for (OffboardTasks tasks : tasksList) {
				List<String> usersList = new ArrayList<String>();
				if (!isNullOrEmpty(tasks.getAssignToUsers())) {
					String[] userIds = tasks.getAssignToUsers().split(",");
					for (int i = 0; i < userIds.length; i++) {
						StringBuilder userName = new StringBuilder();
						User user = userService.getUserById(Long.valueOf(userIds[i]));
						String assignedUser = null;
						if (null != user.getLastName()) {
							assignedUser = userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString();
						} else {
							assignedUser = userName.append(user.getFirstName()).toString();
						}
						usersList.add(assignedUser);
					}
				}
				StringBuilder dueAfter = new StringBuilder();
				if (null != tasks.getDueAfterMonths()) {
					dueAfter = dueAfter.append(tasks.getDueAfterMonths()).append(" Month(s) ");
				}
				if (null != tasks.getDueAfterDays()) {
					dueAfter = dueAfter.append(tasks.getDueAfterDays()).append(" Day(s) ");
				}
				if (null != tasks.getDueAfterHours()) {
					dueAfter = dueAfter.append(tasks.getDueAfterHours()).append(" Hours ");
				}
				if (null != tasks.getDueAfterMinutes()) {
					dueAfter = dueAfter.append(tasks.getDueAfterMinutes()).append(" Minutes");
				}
				tasks.setDueAfterString(dueAfter.toString());
				tasks.setUsersList(usersList);
				if (null != tasks.getCandidateOffBoardingId()) {
					tasks.setOffboardTaskType("Candidate Task");
				} else if (null != tasks.getOffboardingTemplateId()) {
					tasks.setOffboardTaskType("Template Task");
				}
				offboardtasksListDTO.add(getOffboardtasks(tasks));
			}
			responseList.setData(offboardtasksListDTO);
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return responseList;
	}

	public OffboardTasksDTO getOffboardtasks(OffboardTasks offboardTasks) {
		OffboardTasksDTO offboardTasksDTO = new OffboardTasksDTO();
		offboardTasksDTO.setId((offboardTasks.getId()).toString());
		offboardTasksDTO.setPriority(offboardTasks.getPriority());
		offboardTasksDTO.setTaskRepeat(offboardTasks.getTaskRepeat());
		offboardTasksDTO.setTaskName(offboardTasks.getTaskName());
		offboardTasksDTO.setDueAfterString(offboardTasks.getDueAfterString());
		offboardTasksDTO.setUsersList(offboardTasks.getUsersList());
		offboardTasksDTO.setOffboardTaskType(offboardTasks.getOffboardTaskType());
		return offboardTasksDTO;
	}

	@RequestMapping(value = "/deleteOffboardTaskByTaskId", method = RequestMethod.POST)
	public @ResponseBody Response<String> deleteOffboardTaskByTaskId(@RequestParam(value = "taskId", required = false) Long taskId,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OffboardTasks offboardTasks = offboardTasksService.getOffboardTasksByTaskId(taskId);
			if (null != offboardTasks) {
				offboardTasks.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				offboardTasksService.saveOffboardTasks(offboardTasks);
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.INVALID_USER.name());
				response.setResponseMessage(Result.INVALID_USER.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveCandidateOffBoardTasks", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateOffBoardTasks(OffboardTasksDTO offboardTasksDTO,
			@RequestParam(value = "tasks", required = false) String tasks, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OffboardTasks offboardTasks = new OffboardTasks();
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskName())) {
					offboardTasks.setTaskName(offboardTasksDTO.getTaskName());
				}
				CandidateOffBoarding offBoarding = new CandidateOffBoarding();
				if (offboardTasksDTO.getOffboardTaskId() != null) {
					offBoarding.setId(Long.valueOf(offboardTasksDTO.getOffboardTaskId()));
				}
				offboardTasks.setCandidateOffBoardingId(offBoarding);
				if (StringUtils.isNotBlank(offboardTasksDTO.getOffboardAssignedTo())) {
					offboardTasks.setAssignToUsers(offboardTasksDTO.getOffboardAssignedTo());
				} else {
					offboardTasks.setAssignToCandidate(Constants.YES);
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskPriority())) {
					offboardTasks.setPriority(offboardTasksDTO.getTaskPriority());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskRepeat())) {
					offboardTasks.setTaskRepeat(offboardTasksDTO.getTaskRepeat());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskMonths())) {
					offboardTasks.setDueAfterMonths(Integer.parseInt(offboardTasksDTO.getTaskMonths()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskDays())) {
					offboardTasks.setDueAfterDays(Integer.parseInt(offboardTasksDTO.getTaskDays()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskHours())) {
					offboardTasks.setDueAfterHours(Integer.parseInt(offboardTasksDTO.getTaskHours()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskMinutes())) {
					offboardTasks.setDueAfterMinutes(Integer.parseInt(offboardTasksDTO.getTaskMinutes()));
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskNotification())) {
					offboardTasks.setTaskNotification(offboardTasksDTO.getTaskNotification());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskEmail())) {
					offboardTasks.setTaskEmail(offboardTasksDTO.getTaskEmail());
				}
				if (StringUtils.isNotBlank(offboardTasksDTO.getTaskdescription())) {
					offboardTasks.setDescription(offboardTasksDTO.getTaskdescription());
				}
				offboardTasks.setStatus(Constants.CANDIDATE_ACTIVE);
				offboardTasks.setCreatedDate(CommonUtil.currentDate());
				offboardTasks.setCreatedBy(user.getUserCode());
				offboardTasks.setUpdatedDate(CommonUtil.currentDate());
				offboardTasks.setUpdatedBy(user.getUserCode());
				offboardTasks.setIsTaskSumbitted(Constants.NO);
				offboardTasksService.saveOffboardTasks(offboardTasks);
				if (!isNullOrEmpty(tasks)) {
					String[] taksIds = tasks.split(",");
					for (int i = 0; i < taksIds.length; i++) {
						offboardTasks = offboardTasksService.getOffboardTasksByTaskId(Long.valueOf(taksIds[i]));
						OffboardTasks candidateTask = new OffboardTasks();
						candidateTask.setTaskName(offboardTasks.getTaskName());
						candidateTask.setCandidateOffBoardingId(offBoarding);
						candidateTask.setAssignToUsers(offboardTasks.getAssignToUsers());
						candidateTask.setAssignToCandidate(offboardTasks.getAssignToCandidate());
						candidateTask.setPriority(offboardTasks.getPriority());
						candidateTask.setTaskRepeat(offboardTasks.getTaskRepeat());
						candidateTask.setDueAfterMonths(offboardTasks.getDueAfterMonths());
						candidateTask.setDueAfterDays(offboardTasks.getDueAfterDays());
						candidateTask.setDueAfterHours(offboardTasks.getDueAfterHours());
						candidateTask.setDueAfterMinutes(offboardTasks.getDueAfterMinutes());
						candidateTask.setTaskNotification(offboardTasks.getTaskNotification());
						candidateTask.setTaskEmail(offboardTasks.getTaskEmail());
						candidateTask.setDescription(offboardTasks.getDescription());
						candidateTask.setStatus(Constants.CANDIDATE_ACTIVE);
						candidateTask.setCreatedDate(CommonUtil.currentDate());
						candidateTask.setCreatedBy(user.getUserCode());
						candidateTask.setUpdatedDate(CommonUtil.currentDate());
						candidateTask.setUpdatedBy(user.getUserCode());
						offboardTasksService.saveOffboardTasks(candidateTask);
					}
				}
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveAndFinishCandidateOffboard", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveAndFinishCandidateOffboard(
			@RequestParam(value = "offboardId", required = false) Long offboardId,
			@RequestParam(value = "tasks", required = false) String tasks, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				CandidateOffBoarding offBoarding = candidateOffBoardingService.getCandidateOffBoardingById(offboardId);
				if (!isNullOrEmpty(tasks)) {
					String[] taksIds = tasks.split(",");
					for (int i = 0; i < taksIds.length; i++) {
						OffboardTasks offboardTasks = offboardTasksService.getOffboardTasksByTaskId(Long.valueOf(taksIds[i]));
						OffboardTasks candidateTask = new OffboardTasks();
						candidateTask.setTaskName(offboardTasks.getTaskName());
						candidateTask.setCandidateOffBoardingId(offBoarding);
						candidateTask.setAssignToUsers(offboardTasks.getAssignToUsers());
						candidateTask.setAssignToCandidate(offboardTasks.getAssignToCandidate());
						candidateTask.setPriority(offboardTasks.getPriority());
						candidateTask.setTaskRepeat(offboardTasks.getTaskRepeat());
						candidateTask.setDueAfterMonths(offboardTasks.getDueAfterMonths());
						candidateTask.setDueAfterDays(offboardTasks.getDueAfterDays());
						candidateTask.setDueAfterHours(offboardTasks.getDueAfterHours());
						candidateTask.setDueAfterMinutes(offboardTasks.getDueAfterMinutes());
						candidateTask.setTaskNotification(offboardTasks.getTaskNotification());
						candidateTask.setTaskEmail(offboardTasks.getTaskEmail());
						candidateTask.setDescription(offboardTasks.getDescription());
						candidateTask.setIsTaskSumbitted(Constants.NO);
						candidateTask.setStatus(Constants.CANDIDATE_ACTIVE);
						candidateTask.setCreatedDate(CommonUtil.currentDate());
						candidateTask.setCreatedBy(user.getUserCode());
						candidateTask.setUpdatedDate(CommonUtil.currentDate());
						candidateTask.setUpdatedBy(user.getUserCode());
						offboardTasksService.saveOffboardTasks(candidateTask);
					}
				}
				candidateOffBoardingService.terminateOffboardingUser(offBoarding, user, loginOrgLocation);
				response.setObject("success");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveOffboardingCandidateStep1Details", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveOffboardingCandidateStep1Details(OffboardingCandidateSetpsDTO offboardingCandidateSetpsDTO,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try{
			if(user !=null){
				if(Validator.validate(offboardingCandidateSetpsDTO)){
					OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					CandidateOffBoarding offboarding = candidateOffBoardingService.getCandidateOffBoardingByUserIdAndLocationId(user.getId(),
							loginOrgLocation.getId());
					if (null != offboarding) {
						offboarding.setRating(Integer.parseInt(offboardingCandidateSetpsDTO.getRating()));
						if (StringUtils.isNotBlank(offboardingCandidateSetpsDTO.getSellProperty())
								&& offboardingCandidateSetpsDTO.getSellProperty().equalsIgnoreCase("Y")) {
							offboarding.setWantedToSellProperty(Constants.YES);
						} else {
							offboarding.setWantedToSellProperty(Constants.NO);
						}
						if (StringUtils.isNotBlank(offboardingCandidateSetpsDTO.getSublease())
								&& offboardingCandidateSetpsDTO.getSublease().equalsIgnoreCase("Y")) {
							offboarding.setWantedToSubLease(Constants.YES);
						} else {
							offboarding.setWantedToSubLease(Constants.NO);
						}
						if (StringUtils.isNotBlank(offboardingCandidateSetpsDTO.getItemsStorage())
								&& offboardingCandidateSetpsDTO.getItemsStorage().equalsIgnoreCase("Y")) {
							offboarding.setLookingForPublicStorage(Constants.YES);
						} else {
							offboarding.setLookingForPublicStorage(Constants.NO);
						}
						if (StringUtils.isNotBlank(offboardingCandidateSetpsDTO.getAccommodation())
								&& offboardingCandidateSetpsDTO.getAccommodation().equalsIgnoreCase("Y")) {
							offboarding.setLookingForAccommodation(Constants.YES);
						} else {
							offboarding.setLookingForAccommodation(Constants.NO);
						}
						if (StringUtils.isNotBlank(offboardingCandidateSetpsDTO.getSurveyReason())) {
							offboarding.setSurveyReason(offboardingCandidateSetpsDTO.getSurveyReason());
						}
						if (StringUtils.isNotBlank(offboardingCandidateSetpsDTO.getSurveyExplanation())) {
							offboarding.setSurveyExplanation(offboardingCandidateSetpsDTO.getSurveyExplanation());
						}
						candidateOffBoardingService.saveCandidateOffBoarding(offboarding);
					}
					response.setObject("success");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					
				}else{
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
				
			}else{
				
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				
			}
			
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		
		return response;
	}

	@RequestMapping(value = "/getOffboardCheckListByOffBoardId", method = RequestMethod.GET)
	public Response<String> getOffboardCheckListByOffBoardId(@RequestParam(value = "offboardId", required = false) Long offboardId,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OffboardCheckList offboardCheckList = offboardCheckListService.getOffboardCheckListByOffboardingId(offboardId);
			response.setObject(commonUtil.toJSON(offboardCheckList));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;

	}

	@RequestMapping(value = "/getCandidateOffBoardCheckListByOffboardUUID", method = RequestMethod.GET)
	public Response<String> getCandidateOffBoardCheckListByOffboardUUID(
			@RequestParam(value = "offboardUUID", required = false) String offboardUUID, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			CandidateOffBoarding candidateOffBoarding = candidateOffBoardingService.getCandidateOffBoardingByOffboardUUID(offboardUUID);
			OffboardCheckList offboardCheckList = offboardCheckListService
					.getOffboardCheckListByOffboardingId(candidateOffBoarding.getId());
			response.setObject(commonUtil.toJSON(offboardCheckList));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/saveOffboardingCheckList", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveOffboardingCheckList(
			@RequestParam(value = "jsonStringValues", required = false) String jsonStringValues,
			@RequestParam(value = "offboardId", required = false) Long offboardId, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			OffboardCheckList offboardCheckList = offboardCheckListService.getOffboardCheckListByOffboardingId(offboardId);
			offboardCheckList.setCheckListJsonValueString(jsonStringValues);
			offboardCheckListService.saveOffboardCheckList(offboardCheckList);
			response.setObject("success");
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/sendOffboardCandidateToDashboard", method = RequestMethod.POST)
	public @ResponseBody Response<String> sendOffboardCandidateToDashboard(@RequestParam(value = "taskId", required = false) Long taskId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Response<String> result = new Response<>();
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			CandidateOffBoarding offboarding = candidateOffBoardingService.getCandidateOffBoardingByUserIdAndLocationId(user.getId(),
					loginOrgLocation.getId());
			offboarding.setOffboardSubmitted(Constants.YES);
			candidateOffBoardingService.saveCandidateOffBoarding(offboarding);
			// mail for offboard candidates
			mailToOffboardCandidateReportingManager(offboarding, user, loginOrgLocation);
			result.setObject("success");
			result.setResponse(Result.SUCCESS.name());
			result.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			result.setResponse(Result.AWKWARD.name());
			result.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return result;
	}

	public void mailToOffboardCandidateReportingManager(CandidateOffBoarding candidateOffBoarding, User loginUser,
			OrgLocations orgLocations) throws Exception {
		// SimpleDateFormat dateFormat=orgLocations.getLocFullFormDateFormatter();
		User toUser = null;
		UserRole userRole = userRoleService.getUserByUserId(loginUser.getId());
		if (userRole.getReportingManager() != null && userRole.getReportingManager().getId() != null) {
			toUser = userService.getUserById(userRole.getReportingManager().getId());
		} else {
			toUser = userService.getUserById(userRole.getOrgLocations().getUser().getId());
		}
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();

		globalVarsMap.put("loginUser", emailMessage.getFromName());

		StringBuilder name = new StringBuilder();
		if (candidateOffBoarding.getLastName() != null && candidateOffBoarding.getLastName() != ""
				&& candidateOffBoarding.getLastName() != "undefined") {
			globalVarsMap.put("applicantName", name.append(toUser.getFirstName()).append(" ").append(toUser.getLastName()).toString());

		} else {
			globalVarsMap.put("applicantName", candidateOffBoarding.getFirstName());
		}
		globalVarsMap.put("companyName", orgLocations.getOrg().getOrgName());

		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("loginLink", linkBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());


		// getting the current EmailScenario by EmailScenario unique name and entity
		// type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.OFFBOARDINGPROCESSAPPLICANTTERMINATIONMAILTOREPORTINGMANAGER, Constants.ENTITY_TYPE_NOTIFICATION);


		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,notificationScenario);
		notificationScenario.setBody(notificationbody);

		// OrgLocations vendorOrgLoc = requirement.getSpocId().getVendorLocationId();


		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,userRole.getOrgLocations().getOrg(),userRole.getOrgLocations(),Constants.OFFBOARDINGPROCESSAPPLICANTTERMINATIONMAILTOREPORTINGMANAGER,new ArrayList<EmailAttachment>());
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, orgLocations, notificationScenario,
				new HashMap<String, String>());

	}

	@RequestMapping(value = "/getOffboardCandidateTasksByOffboardUUID", method = RequestMethod.GET)
	public @ResponseBody Response<String> getOffboardCandidateTasksByOffboardUUID(
			@RequestParam(value = "offboardUUID", required = false) String offboardUUID,
			@RequestParam(value = "locationCode", required = false) String locationCode) throws Exception {
		Response<String> response = new Response<>();
		try {
			CandidateOffBoarding offboarding = candidateOffBoardingService.getCandidateOffBoardingByOffboardUUID(offboardUUID);
			OrgLocations orgLocations = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			// SimpleDateFormat simpleDateFormat =
			// orgLocations.getLocFullFormDateFormatter();
			List<Tasks> tasksList = taskService.getTasksListOffboardingId(offboarding.getId());
			List<Tasks> taskList = new ArrayList<Tasks>();
			if (null != tasksList && tasksList.size() > 0) {
				for (Tasks tasks : tasksList) {
					tasks.setTaskDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), tasks.getTaskDate()));
					StringBuilder userName = new StringBuilder();
					if (null != tasks.getAssignedUserId().getLastName()) {
						tasks.setAssignedName(userName.append(tasks.getAssignedUserId().getFirstName()).append(" ")
								.append(tasks.getAssignedUserId().getLastName()).toString());
					} else {
						tasks.setAssignedName(userName.append(tasks.getAssignedUserId().getFirstName()).toString());
					}
					taskList.add(tasks);
				}
			}
			response.setObject(commonUtil.toJSON(taskList));
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/getOffboardCandidateDocumentsByOffboardUUID", method = RequestMethod.GET)
	public @ResponseBody Response<String> getOffboardCandidateDocumentsByOffboardUUID(
			@RequestParam(value = "offboardUUID", required = false) String offboardUUID, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Response<String> result = new Response<>();
		try {
			CandidateOffBoarding offboarding = candidateOffBoardingService.getCandidateOffBoardingByOffboardUUID(offboardUUID);
			List<OffBoardingDocuments> resignationDocumentsList = offBoardingDocumentsService
					.getOffBoardingDocumentsByCandidateOffBoardingId(offboarding.getId(), "Resignation");
			List<OffBoardingDocuments> offboardPackDocumentsList = offBoardingDocumentsService
					.getOffBoardingDocumentsByCandidateOffBoardingId(offboarding.getId(), "Offboard Pack");
			OffBoardingDocuments documents = new OffBoardingDocuments();
			documents.setResignationDocumentsList(resignationDocumentsList);
			documents.setOffboardPackDocumentsList(offboardPackDocumentsList);
			result.setObject(commonUtil.toJSON(documents));
			result.setResponse(Result.SUCCESS.name());
			result.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			result.setResponse(Result.AWKWARD.name());
			result.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/getEndDateByEffectiveDateAndNoticePeriod", method = RequestMethod.GET)
	public @ResponseBody Response<String> getEndDateByEffectiveDateAndNoticePeriod(
			@RequestParam(value = "noticePeriod", required = false) String noticePeriod,
			@RequestParam(value = "effectiveDate", required = false) String effectiveDate,
			@RequestParam(value = "dateFormat", required = false) String dateFormat, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			String[] notice = noticePeriod.split(" ");
			String endDateStr = null;
			try {
				Date date = commonUtil.orgLocationDateFormat(dateFormat, effectiveDate);
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				if (noticePeriod.equalsIgnoreCase("15 Days")) {
					c.add(Calendar.DATE, Integer.parseInt(notice[0]));
				} else {
					c.add(Calendar.MONTH, Integer.parseInt(notice[0]));
				}
				endDateStr = commonUtil.dateStrFromOrgDateFormatter(dateFormat, c.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.setObject(endDateStr);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return response;
	}

}
