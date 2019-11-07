package com.bis.sourcelead.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.DashboardItemsDTO;
import com.bis.sourcelead.entity.CandidateOffBoarding;
import com.bis.sourcelead.entity.CandidateOnBoarding;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Notifications;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationDepositTaxes;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.service.AssociateBankDataService;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.service.CandidateOffBoardingService;
import com.bis.sourcelead.service.CandidateOnBoardingService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ConnectionsService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.NotificationsService;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrglocationDepositTaxesService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserLoginLogoutSessionService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.UserLoginHelper;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class LoginSessionController {

	private static final Logger logger = LoggerFactory.getLogger(LoginSessionController.class);

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	ProjectMembersService projectMembersService;

	@Autowired
	UserService userService;

	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;

	@Autowired
	TabsService tabsService;

	@Autowired
	UserLoginHelper userLoginHelper;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${GoogleMaps_Location_API_KEY}")
	private String googleMapsLocationApiKey;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	private NotificationsService notificationsService;

	@Autowired
	private ProjectsService projectsService;

	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;

	@Autowired
	private ModulesService modulesService;

	@Autowired
	private SourceleadActionsService sourceleadActionsService;

	@Autowired
	private UserMailBoxService userMailBoxService;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	OrgLocationService orgLocationService;

	@Autowired
	private CountriesService countriesService;

	@Autowired
	private CandidateService candidateService;

	@Autowired
	LookUpDataService lookUpDataService;

	@Autowired
	CandidateOnBoardingService candidateOnBoardingService;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	TeamService teamService;

	@Autowired
	TeamMembersService teamMembersService;

	@Autowired
	OnBoardingTemplateDocumentsService onBoardingTemplateDocumentsService;

	@Autowired
	CandidateOffBoardingService candidateOffBoardingService;

	@Autowired
	TimeZoneService timeZoneService;

	@Autowired
	OrgService orgService;

	@Autowired
	ConnectionsService connectionsService;

	@Autowired
	UserLoginLogoutSessionService userLoginLogoutSessionService;

	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;

	@Autowired
	OrglocationDepositTaxesService orglocationDepositTaxesService;
	
	@Autowired
	AssociateBankDataService associateBankDataService;

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@RequestMapping(value = "/app/dashboard", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginSession(HttpServletRequest request, Authentication authentication,
			@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
			@RequestParam(value = "referUUID", required = false) String referUUID, HttpServletResponse response,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userRoleId", required = false) Long userRoleId,
			@RequestParam(value = "switchUserType", required = false) String switchUserType,
			@RequestParam(value = "loginOrgLocatonId", required = false) Long loginOrgLocatonId) throws Exception {

		ModelAndView model = new ModelAndView();
		if (authentication == null) {
			model.setViewName("login");
			return model;
		}
		SourceLeadSessionManager.setSessionAttribute(request, "sessionId", request.getSession().getId());

		/*if (userRoleId == null) {
			userRoleId = (Long) request.getSession().getAttribute("userRoleId");
			if (userRoleId == null)
				userRoleId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userRoleId");
			if (userRoleId != null) {
				userRole = userRoleService.getUserRoleById(userRoleId);
				user = userRole.getUser();
				orgLocations = userRole.getOrgLocations();
				request.getSession().removeAttribute("userRoleId");
			} else {
				userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
			}
		} else {
			userRole = userRoleService.getUserRoleById(userRoleId);
			user = userRole.getUser();
			orgLocations = userRole.getOrgLocations();
		}*/

		UserLoginLogoutSession userLoginSession = (UserLoginLogoutSession) request.getSession()
				.getAttribute("userLoginSession");
		if (userLoginSession != null) {
			SourceLeadSessionManager.setSessionAttribute(request, "isAuthenticated",
					userLoginSession.getAuthenticated());
		}
		
		String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		if (!roleName.equalsIgnoreCase(Constants.ROLE_NAME_PROFESSIONAL)
				&& !roleName.equalsIgnoreCase(Constants.ROLE_NAME_INDEPENDENT_RECRUITER)) {

			String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "resetPwdMsg");
			if (null != msg && !msg.isEmpty()) {
				model.addObject("msg", msg);
				SourceLeadSessionManager.removeSessionAttribute(request, "resetPwdMsg");
			}
			model.addObject("sourceleadUrl", sourceleadUrl);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String currentDate = sdf.format(new Date());
			Date todayDate = sdf.parse(currentDate);
			
			String isCompanyOnboarded = (String) SourceLeadSessionManager.getSessionAttribute(request, "isCompanyOnboarded");
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			String dateFormat = (String) SourceLeadSessionManager.getSessionAttribute(request, "dateFormat");
			Long timeZoneId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "timeZoneId");

			OrglocationDepositTaxes orgLocDepTaxes = orglocationDepositTaxesService
					.getOrglocationDepositTaxesByOrgLocationId(orgLocId);
			SourceLeadSessionManager.setSessionAttribute(request, "orglocationDepositTaxes", orgLocDepTaxes);
			
			AssociateBankData associateBankData = associateBankDataService.getAssociateBankDataByOrgLocationId(orgLocId);
			SourceLeadSessionManager.setSessionAttribute(request, "associateBankData", associateBankData);

			setLoginSession(request);

			boolean isPayRollApprover = configurePayrollApproverService
					.isLoginUserIsPayRollApprover(userId, orgLocId);
			if (isPayRollApprover == true) {
				SourceLeadSessionManager.setSessionAttribute(request, "isPayRollApprover", Constants.YES);
			} else {
				SourceLeadSessionManager.setSessionAttribute(request, "isPayRollApprover", Constants.NO);
			}


		/*	List<Long> accountUserList = new ArrayList<>();
			List<Long> userIdsList = null;
			Long productionLoginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request,
					"productionLoginUserId");
			if (productionLoginUserId != null) {
				userIdsList = userService.getUserIdsListByLinkedUserId(productionLoginUserId);
			} else {
				userIdsList = userService.getUserIdsListByLinkedUserId(userId);
			}

			if (userIdsList != null && !userIdsList.isEmpty()) {
				for (Long loginUserId : userIdsList) {
					List<Long> linkedUserIdsList = userService.getUserIdsListByLinkedUserId(loginUserId);
					accountUserList.addAll(linkedUserIdsList);
				}
			}
			userIdsList.addAll(accountUserList);
			if (productionLoginUserId != null) {
				userIdsList.add(productionLoginUserId);
				userIdsList.add(userId);
			} else {
				userIdsList.add(userId);
			}

			if (userRole.getUser().getLinkUserId() != null) {
				userIdsList.add(userRole.getUser().getLinkUserId().getId());
			}

			List<UserRole> userSpocRolesList = userRoleService.getUserAccountsListByUserId(userIdsList);
			if (userSpocRolesList.size() > 1) {
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount",
						userSpocRolesList.size());
			} else {
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", null);
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", 0);
			}*/


			if (isCompanyOnboarded != null && isCompanyOnboarded.equalsIgnoreCase(Constants.NO)) {
				List<LookUpData> bussinessTypesList = lookUpDataService
						.getLookUpDataByCategory(Constants.COMPANY_ONBOARD_BUSINESS_TYPE);
				model.addObject("bussinessTypesList", bussinessTypesList);
				List<Timezone> timezoneList = timeZoneService.getAllTimeZones();
				model.addObject("timezoneList", timezoneList);
				model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
				model.setViewName("onboard/companyOnboard");
			} else {
				CandidateOffBoarding candidateOffBoarding = candidateOffBoardingService
						.getCandidateOffBoardingByUserIdAndLocationId(userId, orgLocId);
				if (null != candidateOffBoarding) {

					String endDate = sdf.format(candidateOffBoarding.getEndDate());
					Date onboardEndDte = sdf.parse(endDate);
					if (onboardEndDte.equals(todayDate) || todayDate.after(onboardEndDte)) {
						SourceLeadSessionManager.setSessionAttribute(request, "candidateOffBoarding",
								candidateOffBoarding);
						if (candidateOffBoarding.getOffboardSubmitted().equalsIgnoreCase("N")) {
							model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
							model.setViewName("offboard/offboardStep1");
						} else {
							model.addObject("googleMapsLocationApiKey", googleMapsLocationApiKey);
							model.setViewName("offboard/offboardUser");
						}
					} else {
						model.setViewName("dashboard");
					}
				} else {

					CandidateOnBoarding candidateOnBoarding = candidateOnBoardingService
							.getCandidateOnBoardingByUserIdAndLocationId(userId, orgLocId);
					if (null != candidateOnBoarding && requirementUUID == null) {
						SourceLeadSessionManager.setSessionAttribute(request, "candidateOnBoarding",
								candidateOnBoarding);
						if (candidateOnBoarding.getManagerUserId() != null) {
							SourceLeadSessionManager.setSessionAttribute(request, "OnboardingManagerUserId",
									candidateOnBoarding.getManagerUserId().getId());
						}
						if (candidateOnBoarding.getOnBoardTeamId() != null) {
							SourceLeadSessionManager.setSessionAttribute(request, "OnboardingTeamOwnerUserId",
									candidateOnBoarding.getOnBoardTeamId());
						}
						if (candidateOnBoarding.getOnboardSubmitted().equalsIgnoreCase("N")) {
							getOnboardingDetails(candidateOnBoarding,orgLocId,dateFormat, request);
							model.setViewName("onboard/welcomeStep1");
						} else {
							model.setViewName("dashboard");
						}
					} else if (requirementUUID != null && requirementUUID != "") {
						StringBuilder redirectUrl = new StringBuilder();
						response.sendRedirect(redirectUrl.append("requirementProfileDetails?requirementUUID=")
								.append(requirementUUID).toString());
						model.setViewName("professional/professionalRequirements");
					} else {
						model.setViewName("dashboard");
						// model.setViewName("masterPage");
					}

				}
			}

			List<LookUpData> skillList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			model.addObject("skillList", skillList);
			
			List<LookUpData> educationList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS);
			model.addObject("educationList", educationList);

			Boolean isManagerWithSubmissionRateStatusAsRequestOrAccept = requirementService
					.checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(userId, orgLocId);
			model.addObject("isManagerWithSubmissionRateStatusAsRequestOrAccept",
					isManagerWithSubmissionRateStatusAsRequestOrAccept);

			Boolean isAdmin = userRoleService.isUserAdminByOrgLocationId(userId, orgLocId);
			model.addObject("isAdmin", isAdmin);
			SourceLeadSessionManager.setSessionAttribute(request, "isAdmin", isAdmin);
			model.addObject("timezone", timeZoneId);
			//setTimeZone(request, orgLocations, model);

		} else {
			commonUtil.highLightMenu(Constants.Dashboard, request);
			
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			String newlyLoginUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "newlyLoginUser");

			/*List<Long> accountUserList = new ArrayList<>();
			List<Long> userIdsList = null;
			Long productionLoginUserId = (Long) SourceLeadSessionManager.getSessionAttribute(request,
					"productionLoginUserId");
			if (productionLoginUserId != null) {
				userIdsList = userService.getUserIdsListByLinkedUserId(productionLoginUserId);
			} else {
				userIdsList = userService.getUserIdsListByLinkedUserId(userId);
			}

			if (userIdsList != null && !userIdsList.isEmpty()) {
				for (Long loginUserId : userIdsList) {
					List<Long> linkedUserIdsList = userService.getUserIdsListByLinkedUserId(loginUserId);
					accountUserList.addAll(linkedUserIdsList);
				}
			}
			userIdsList.addAll(accountUserList);
			if (productionLoginUserId != null) {
				userIdsList.add(productionLoginUserId);
				userIdsList.add(userId);
			} else {
				userIdsList.add(userId);
			}

			if (userRole.getUser().getLinkUserId() != null) {
				userIdsList.add(userRole.getUser().getLinkUserId().getId());
			}

			List<UserRole> userSpocRolesList = userRoleService.getUserAccountsListByUserId(userIdsList);
			if (userSpocRolesList.size() > 1) {
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", userSpocRolesList);
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount",
						userSpocRolesList.size());
			} else {
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesList", null);
				SourceLeadSessionManager.setSessionAttribute(request, "userSpocRolesListCount", 0);
			}*/


			if (roleName.equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL")) {
				StringBuilder redirectUrl = new StringBuilder();
				if (!isNullOrEmpty(requirementUUID) && isNullOrEmpty(referUUID)) {
					response.sendRedirect(redirectUrl.append("/app/professionalRequirements?requirementUUID=")
							.append(requirementUUID).toString());
					model.setViewName("professional/professionalRequirements");
				} else if (!isNullOrEmpty(requirementUUID) && !isNullOrEmpty(referUUID)) {
					response.sendRedirect(redirectUrl.append("/app/professionalRequirements?requirementUUID=")
							.append(requirementUUID).append("&referUUID=").append(referUUID).toString());
					model.setViewName("professional/professionalRequirements");
				} else {
					if (!isNullOrEmpty(newlyLoginUser)
							&& newlyLoginUser.equalsIgnoreCase("Y")) {
						model.setViewName("professional/professionalPraseCandidate");
					} else {
						model.setViewName("professional/professionalDashboard");
					}
				}

			} else if (roleName.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")) {
				if (!isNullOrEmpty(requirementUUID)) {
					StringBuilder redirectUrl = new StringBuilder();
					response.sendRedirect(redirectUrl.append("/app/professionalRequirements?requirementUUID=")
							.append(requirementUUID).toString());
					model.setViewName("professional/professionalRequirements");
				} else {
					model.setViewName("recruiter/recruiterDashboard");
				}
			}

			model.addObject("sourceleadUrl", sourceleadUrl);

			//setTimeZone(request, orgLocations, model);

		}
		boolean redirectFlag = false;
		if(request.getSession().getAttribute("redirectURL") != null) {
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			String redirectURL = (String) request.getSession().getAttribute("redirectURL");
			long redirectOrgLocationId = (Long) request.getSession().getAttribute("redirectOrgLocationId");
			if(orgLocId != null && orgLocId == redirectOrgLocationId) {
				redirectURL = redirectURL.replaceAll("-params-", "?").replaceAll("-and-", "&");
				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("redirectURL", redirectURL);
				model.setViewName("redirectURL");

				request.getSession().removeAttribute("redirectURL");
				request.getSession().removeAttribute("redirectOrgLocationId");
				redirectFlag = true;
			}
		}
		if(!redirectFlag) {
			boolean dashboardConfigured = false;
			Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
			Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
			DashboardItemsDTO dashboardItems = userService.getDashboardItemsByUserId(userId, orgLocId);
			if (dashboardItems != null && dashboardItems.getItemsObjectList().size() > 0) {
				dashboardConfigured = true;
			}
			model.addObject("dashboardConfigured", dashboardConfigured);
			model.addObject("dashboardItems", dashboardItems);
			if (model.getViewName().equalsIgnoreCase("dashboard") && dashboardConfigured)
				model.setViewName("dashboard_configured");
				//return new ModelAndView("redirect:/app/configureCustomDashBoardView?customDashBoardUUID=rhelzSk8");
		}
		return model;
	}

	private void setTimeZone(HttpServletRequest request, OrgLocations orgLocations, ModelAndView model)
			throws Exception {
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		SourceLeadSessionManager.setSessionAttribute(request, "timezone", timezone);
		model.addObject("timezone", timezone.getTimeZoneId());
	}

	private void setLoginSession(HttpServletRequest request) throws Exception {

		commonUtil.highLightMenu(Constants.Dashboard, request);
		
		String isSuperUser = (String) SourceLeadSessionManager.getSessionAttribute(request, "isSuperUser");
		String isSuperAdmin = (String) SourceLeadSessionManager.getSessionAttribute(request, "isSuperAdmin");
		String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Long userId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "userId");
		Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
		String orgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		String locationCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		String timeZone = (String) SourceLeadSessionManager.getSessionAttribute(request, "timeZone");

		try {
			
			if (isSuperAdmin.equalsIgnoreCase(Constants.YES) || isSuperUser.equalsIgnoreCase(Constants.YES)) {
				SourceLeadSessionManager.setSessionAttribute(request, "orgSettingsVisible", "Y");
			}

			if ("ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(roleName)) {
				SourceLeadSessionManager.setSessionAttribute(request, "enrollOrgVisible", "Y");
			}

		} catch (Exception e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		}


		Integer notificationCount = notificationsService.getNotificationsCountByUserId(userId, orgCode,
				locationCode);

		SourceLeadSessionManager.removeSessionAttribute(request, "notificationCount");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationCount", notificationCount);

		Long receivedConnectionCount = connectionsService.getConnectionReceivedCount(userId);
		SourceLeadSessionManager.removeSessionAttribute(request, "connectionReceivedCount");
		SourceLeadSessionManager.setSessionAttribute(request, "connectionReceivedCount", receivedConnectionCount);

		// get notification list by user id
		List<Notifications> notificationList = notificationsService.getAllNotificationsListByUserId(userId,
				orgCode, locationCode);
		calculateDays(notificationList, timeZone);

		SourceLeadSessionManager.removeSessionAttribute(request, "notificationList");
		SourceLeadSessionManager.setSessionAttribute(request, "notificationList", notificationList);

		Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(userId,
				orgLocId, "Inbox");
		Integer requestedUnreadMailsCount = userMailBoxService
				.getUnReadMessagesByUserIdAndLocationIdAndType(userId, orgLocId, "Requested");
		Integer totalUnReadMailsCount = inboxUnreadMailsCount + requestedUnreadMailsCount;
		SourceLeadSessionManager.setSessionAttribute(request, "unReadMailsCount", totalUnReadMailsCount);

	}

	private void calculateDays(List<Notifications> notificationList, String timezone) throws ParseException {
		for (Notifications notifications : notificationList) {
			/*
			 * //differentiating date and time Date date = notifications.getCreatedDate();
			 * String[] parts = date.toString().split(" ");
			 * notifications.setDateValue(parts[0]); final SimpleDateFormat sdf = new
			 * SimpleDateFormat("H:mm"); final Date dateObj = sdf.parse(parts[1]);
			 * notifications.setTime(new SimpleDateFormat("K:mm a").format(dateObj));
			 * 
			 * // calculating days b/w dates Date currentDate = new Date(); Date d1 =
			 * notifications.getCreatedDate(); SimpleDateFormat dateFormat = new
			 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); String strDate =
			 * dateFormat.format(currentDate); Date d2 = dateFormat.parse(strDate); long
			 * diff = d2.getTime() - d1.getTime(); long diffDays = diff / (24 * 60 * 60 *
			 * 1000); notifications.setCalDays(String.valueOf(diffDays));
			 */
			long diff = commonUtil.currentDate().getTime() - notifications.getCreatedDate().getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder time = new StringBuilder();
			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				notifications.setCalDays(time.append(diffSeconds).append(" sec ago ").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				notifications.setCalDays(
						time.append(diffMinutes).append(" min ").append(diffSeconds).append(" sec ago ").toString());
			} else if (diffDays == 0) {
				notifications.setCalDays(
						time.append(diffHours).append(" hours ").append(diffMinutes).append(" min ago ").toString());
			} else if (diffDays < 7) {
				notifications.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			} else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String currentTimeStr = Constants.TZ_DF.format(notifications.getCreatedDate());
				String currentDateInTimezone = CommonUtil.convertTimeZones("UTC", timezone,
						currentTimeStr);
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				String commonDateFormat = standardFormat.format(converteDate);
				notifications.setCalDays(commonDateFormat.toString());

			}

		}

	}

	private void setSessionUserAttributes(User user, HttpServletRequest request, String sourceleadUrl)
			throws Exception {

		SourceLeadSessionManager.setSessionAttribute(request, "sourceleadUrl", sourceleadUrl);
		SourceLeadSessionManager.setSessionAttribute(request, "user", user);
		SourceLeadSessionManager.setSessionAttribute(request, "userType", user.getUserType());
		SourceLeadSessionManager.setSessionAttribute(request, "userCode", user.getUserCode());
		SourceLeadSessionManager.setSessionAttribute(request, "userId", user.getId());

		StringBuilder userNameSB = new StringBuilder();
		if (null != user.getLastName()) {
			SourceLeadSessionManager.setSessionAttribute(request, "userName",
					userNameSB.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
		} else {
			SourceLeadSessionManager.setSessionAttribute(request, "userName",
					userNameSB.append(user.getFirstName()).toString());
		}

	}

	private void setSessionOrgLocationAttributes(OrgLocations orgLocations, HttpServletRequest request) {
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
		SourceLeadSessionManager.setSessionAttribute(request, "orgLocationId", orgLocations.getId());
		SourceLeadSessionManager.setSessionAttribute(request, "locationCode", orgLocations.getLocationCode());
		SourceLeadSessionManager.setSessionAttribute(request, "orgBasedDateFormat", orgLocations.getDateFormat());
		if (null != orgLocations.getUser() && null != orgLocations.getUser().getId()) {
			SourceLeadSessionManager.setSessionAttribute(request, "superUserId", orgLocations.getUser().getId());
		}

		SourceLeadSessionManager.setSessionAttribute(request, "country", orgLocations.getCountry());

		try {
			Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
			if (null != countries) {
				SourceLeadSessionManager.setSessionAttribute(request, "currencyCode", countries.getCurrency());
				SourceLeadSessionManager.setSessionAttribute(request, "loginCountryName", countries.getName());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static DateFormat DATEFORMAT; private void
	 * setSessionOrgLocationAttributesDateFormat(OrgLocations orgLocations){
	 * 
	 * DATEFORMAT = new SimpleDateFormat(orgLocations.getDateFormat()); }
	 */


	private void getOnboardingDetails(CandidateOnBoarding candidateOnBoarding, Long orgLocId, String dateFormat,
			HttpServletRequest request) throws Exception {

		candidateOnBoarding.setJoiningDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat,
				candidateOnBoarding.getStartDate()));

		if (null != candidateOnBoarding.getEndDate()) {
			candidateOnBoarding.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat,
					candidateOnBoarding.getEndDate()));
		}

		if (null != candidateOnBoarding.getDueDate()) {
			candidateOnBoarding.setDueDateStr(commonUtil.dateStrFromOrgDateFormatter(dateFormat,
					candidateOnBoarding.getDueDate()));
		}

		if (null != candidateOnBoarding.getManagerUserId()) {
			StringBuilder managerName = new StringBuilder();
			String manager = null;
			if (!isNullOrEmpty(candidateOnBoarding.getManagerUserId().getLastName())) {
				manager = managerName.append(candidateOnBoarding.getManagerUserId().getFirstName()).append(" ")
						.append(candidateOnBoarding.getManagerUserId().getLastName()).toString();
			} else {
				manager = managerName.append(candidateOnBoarding.getManagerUserId().getFirstName()).toString();
			}

			SourceLeadSessionManager.setSessionAttribute(request, "managerName", manager);
		}

		if (null != candidateOnBoarding.getTeamId()) {
			Team team = teamService.getTeamByTeamId(candidateOnBoarding.getTeamId().getId());
			SourceLeadSessionManager.setSessionAttribute(request, "teamName", team.getName());

			StringBuilder teamOwnerName = new StringBuilder();
			String teamOwnerUserName = null;
			if (!isNullOrEmpty(team.getOwnerUserId().getLastName())) {
				teamOwnerUserName = teamOwnerName.append(team.getOwnerUserId().getFirstName()).append(" ")
						.append(team.getOwnerUserId().getLastName()).toString();
			} else {
				teamOwnerUserName = teamOwnerName.append(team.getOwnerUserId().getFirstName()).toString();
			}

			SourceLeadSessionManager.setSessionAttribute(request, "teamOwnerName", teamOwnerUserName);

			UserRole teamOwner = userRoleService.getUserDetailsByUserIdAndLocationId(team.getOwnerUserId().getId(),
					orgLocId);
			SourceLeadSessionManager.setSessionAttribute(request, "teamOwnerRole",
					teamOwner.getRoles().getDisplayName());
			SourceLeadSessionManager.setSessionAttribute(request, "teamOwnerUserId", team.getOwnerUserId().getId());

			List<TeamMembers> teamMembersList = teamMembersService.getTeamMembersListByTeamID(team.getId(),
					orgLocId);
			List<TeamMembers> membersList = new ArrayList<TeamMembers>();
			if (null != teamMembersList) {
				for (TeamMembers members : teamMembersList) {

					StringBuilder teamMemberName = new StringBuilder();
					if (!isNullOrEmpty(members.getMemberUserId().getLastName())) {
						members.setMemberName(teamMemberName.append(members.getMemberUserId().getFirstName())
								.append(" ").append(members.getMemberUserId().getLastName()).toString());
					} else {
						members.setMemberName(
								teamMemberName.append(members.getMemberUserId().getFirstName()).toString());
					}

					UserRole memberUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(
							members.getMemberUserId().getId(), orgLocId);
					members.setRoleName(memberUserRole.getRoles().getDisplayName());

					membersList.add(members);
				}
			}
			SourceLeadSessionManager.setSessionAttribute(request, "teamMembersList", membersList);
		}

		List<OnBoardingTemplateDocuments> supportedDocumentsList = onBoardingTemplateDocumentsService
				.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(candidateOnBoarding.getId(), "Supported");
		SourceLeadSessionManager.setSessionAttribute(request, "supportedDocumentsList", supportedDocumentsList);
		SourceLeadSessionManager.setSessionAttribute(request, "supportedDocumentsListSize",
				supportedDocumentsList.size());
	}

	private List<GrantedAuthority> buildUserAuthority(List<SourceleadActions> actionsList) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		if (actionsList != null && !actionsList.isEmpty()) {
			// Build user's authorities
			for (SourceleadActions sourceleadActions : actionsList) {
				setAuths.add(new SimpleGrantedAuthority(sourceleadActions.getActionRole()));
			}
		}
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}

	@RequestMapping(value = "/app/ws/userEnrolledOrgLocations", method = RequestMethod.POST)
	public ModelAndView userEnrolledOrgLocations(@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
			HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		User user = userService.findByUserName(userName);

		List<UserRole> userSpocRolesList = null;
		if (user != null)
			userSpocRolesList = userRoleService.getUserSpocsListByUserId(user.getId());

		String ipAddress = commonUtil.getClientIpAddr(request);

		boolean alreadyVerifiedBefore = userLoginLogoutSessionService
				.validatePreviouslyAuthenticatedByUserIdAndIpAddress(ipAddress, user.getId());
		model.addObject("alreadyVerifiedBefore", alreadyVerifiedBefore);
		model.addObject("username", userName);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("requirementUUID", requirementUUID);
		model.addObject("roleList", userSpocRolesList);
		model.setViewName("userEnrolledLocations");
		return model;
	}

}