package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
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
import com.bis.sourcelead.dao.AssociateNomineeDao;
import com.bis.sourcelead.dto.ApplicantRejectBackOutDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.dto.OnBoardingTemplateDocumentsDTO;
import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.OnboardingCandidateDocsStep3DTO;
import com.bis.sourcelead.dto.OnboardingCandidateSetp2DTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OnboardTasks;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmissionsView;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.OnboardStatus;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.form.TimesheetEntriesForm;
import com.bis.sourcelead.payroll.dto.AssociatePfEsiDetailsDTO;
import com.bis.sourcelead.payroll.service.AssociatePfEsiService;
import com.bis.sourcelead.service.AssociateNomineeService;
import com.bis.sourcelead.service.CandidateGlobalService;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.service.OnBoardingTemplateService;
import com.bis.sourcelead.service.OnboardTasksService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.SimpleDateFormat;

@RestController
@RequestMapping("/app")
public class CandidateOnBoardRestController {

	private static final Logger logger = LoggerFactory.getLogger(CandidateOnBoardRestController.class);

	@Value("${static.path}")
	private String staticPath;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	OnBoardingTemplateService onBoardingTemplateService;

	@Autowired
	OnBoardingTemplateDocumentsService onBoardingTemplateDocumentsService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	OnboardTasksService onboardTasksService;

	@Autowired
	UserService userService;

	@Autowired
	RequirementOffersService requirementOffersService;

	@Autowired
	CandidateService candidateService;

	@Autowired
	RolesService rolesService;

	@Autowired
	TeamService teamService;

	@Autowired
	CandidateOnBoardingService candidateOnBoardingService;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;

	@Autowired
	TaskService taskService;

	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	AssociatePfEsiService associatePfEsiService;
	
	@Autowired
	AssociateNomineeService associateNomineeService;
	
	@Autowired
	AssociateNomineeDao associateNomineeDao;
	
	@Autowired
	CandidateGlobalService candidateGlobalService;

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@RequestMapping(value = "/getOnboardingTemplatesList")
	public Response<String> getOnboardingTemplatesList(HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			String resultJson;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<OnBoardingTemplate> templatesList = onBoardingTemplateService.getOnBoardingTemplatesList(loginOrgLocation.getId());
			List<OnBoardingTemplate> onboardingTemplatesList = new ArrayList<OnBoardingTemplate>();
			// SimpleDateFormat dateFormat = loginOrgLocation.getLocFullFormDateFormatter();
			for (OnBoardingTemplate template : templatesList) {
				template.setTemplateCreatedDate(
						commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), template.getCreatedDate()));
				onboardingTemplatesList.add(template);
			}
			resultJson = commonUtil.toJSON(onboardingTemplatesList);
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


	@RequestMapping(value = { "/getOnboardTasksAssignToUsersList", "/rest/getOnboardTasksAssignToUsersList" }, method = RequestMethod.POST)
	@ResponseBody
	ResponseList<UserRoleDTO> getOnboardTasksAssignToUsersList(HttpServletRequest request) throws Exception {
		ResponseList<UserRoleDTO> responseList = new ResponseList<>();
		try {
			List<UserRoleDTO> userRoleListDTO = new ArrayList<>();
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserRole> userRoleList = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocations.getId());
			for (UserRole ur : userRoleList) {
				UserRoleDTO userRoleDTO = new UserRoleDTO();
				StringBuilder userName = new StringBuilder();
				userRoleDTO.setUserId(ur.getUser().getId());
				if (null != ur.getUser().getLastName()) {
					userRoleDTO.setUserName(
							userName.append(ur.getUser().getFirstName()).append("").append(ur.getUser().getLastName()).toString());
				} else {
					userRoleDTO.setUserName(userName.append(ur.getUser().getFirstName()).toString());
				}
				userRoleListDTO.add(userRoleDTO);
			}
			responseList.setData(userRoleListDTO);
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

	

	
	@RequestMapping(value = "/getOnboardingCandidatesList")
	public Response<String> getOnboardingCandidatesList(HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			String resultJson;
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<RequirementSubmissionsView> submissionList = requirementSubmissionService.getOfferAcceptedCandidatesListByOrgLocationId(loginOrgLocation.getId());
			List<RequirementSubmissionsView> requirementSubmissionList = new ArrayList<RequirementSubmissionsView>();
			
			if (null != submissionList && submissionList.size() > 0) {
				for (RequirementSubmissionsView submission : submissionList) {
					Date date1=new SimpleDateFormat("dd MM yyyy").parse(submission.getOfferJoinDate());  
					submission.setOfferJoinDate(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), date1));
					requirementSubmissionList.add(submission);
					CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService.getCandidateOnBoardingByCandidateIdAndOfferId(submission.getCandidateId(), submission.getOfferId());
					if (null != candidateOnBoarding) {
						submission.setOnboardUUId(candidateOnBoarding.getOnboardCandidateUUID());
						submission.setOnboardStatus(candidateOnBoarding.getCandidateOnBoardStatus());
					}
				}
			}
			resultJson = commonUtil.toJSON(requirementSubmissionList);
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


	
}