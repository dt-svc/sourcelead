package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ClientVendorDTO;
import com.bis.sourcelead.dto.EmailAccountSettingsDTO;
import com.bis.sourcelead.dto.HotListEmailCampaignDTO;
import com.bis.sourcelead.dto.JobListEmailCampaignDTO;
import com.bis.sourcelead.dto.ReadMailDTO;
import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CombinedList;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserMailBox;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.UserSignature;
import com.bis.sourcelead.service.CampaignService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailAccountSettingsService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmailService;
import com.bis.sourcelead.service.HotListEmailCampaignService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadSignatureService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserMailBoxService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.UserSignatureService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@RequestMapping("/app")
public class MailBoxRestController {

	private static final Logger logger = LoggerFactory.getLogger(MailBoxRestController.class);

	@Value("${static.path}")
	private String staticPath;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	UserSignatureService userSignatureService;

	@Autowired
	HotListEmailCampaignService hotListEmailCampaignService;

	@Autowired
	UserMailBoxService userMailBoxService;

	@Autowired
	CandidateService candidateService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	EmailService emailService;

	@Autowired
	SourceleadSignatureService sourceleadSignatureService;

	@Autowired
	private RequirementAssignToService requirementAssignToService;

	@Autowired
	private RolesService roleService;

	@Autowired
	TeamService teamService;

	@Autowired
	ReferContactsService referContactsService;

	@Autowired
	RequirementService requirementService;

	@Autowired
	EmailAccountSettingsService emailAccountSettingsService;

	@Autowired
	CampaignService campaignService;

	@Autowired
	EmailScenarioService emailScenarioService;

	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/getClientContactsEmailsByOrgRelationId", method = RequestMethod.POST)
	@ResponseBody
	String getClientContactsEmailsByOrgRelationId(ClientVendorDTO clientVendorDTO, HttpServletRequest request) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String spocEmails = null;
		String[] recipientEmail = clientVendorDTO.getRecipientsEmails().split(",");
		List<String> selectedRecipiantEmails = new ArrayList<>();
		if (clientVendorDTO.getRecipientsEmails() != null && !clientVendorDTO.getRecipientsEmails().isEmpty()) {
			for (int i = 0; i < recipientEmail.length; i++) {
				selectedRecipiantEmails.add(recipientEmail[i]);
			}
		}

		String[] relationsId = clientVendorDTO.getOrgRelationIds().split(",");
		StringBuilder emails = new StringBuilder();
		for (int i = 0; i < relationsId.length; i++) {
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocContactEmailsByOrgRelationIdAndUserId(user.getId(),
					Long.valueOf(relationsId[i]), orgLocations.getId());
			for (OrgSpoc orgSpoc : orgSpocList) {
				if (!selectedRecipiantEmails.contains(orgSpoc.getClientUser().getEmail())) {
					emails.append(orgSpoc.getClientUser().getEmail()).append(",");
				}

			}
		}
		emails.toString();
		if (emails.length() > 0) {
			spocEmails = emails.substring(0, emails.length() - 1);
		}

		return spocEmails;
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/getVendorContactsEmailsByOrgRelationId", method = RequestMethod.POST)
	@ResponseBody
	String getVendorContactsEmailsByOrgRelationId(@RequestParam(value = "orgRelationIds", required = false) String orgRelationIds,
			HttpServletRequest request) throws Exception {

		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String spocEmails = "";
		String[] relationsId = orgRelationIds.split(",");
		StringBuilder emails = new StringBuilder();
		for (int i = 0; i < relationsId.length; i++) {
			List<OrgSpoc> orgSpocList = orgSpocService.getOrgSpocContactEmailsByOrgRelationIdAndUserId(user.getId(),
					Long.valueOf(relationsId[i]), orgLocations.getId());
			for (OrgSpoc orgSpoc : orgSpocList) {
				emails.append(orgSpoc.getVendorUser().getEmail()).append(",");
			}
		}
		emails.toString();
		if (emails.length() > 0) {
			spocEmails = emails.substring(0, emails.length() - 1);
		}

		return commonUtil.toJSON(spocEmails);
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/sendHotListCampaign", method = RequestMethod.POST)
	public String sendHotListCampaign(HotListEmailCampaignDTO hotListEmailCampaignDTO, MultipartHttpServletRequest request)
			throws Exception {

		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multiFile = request.getFile(itrator.next());
		}

		hotListEmailCampaignDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		hotListEmailCampaignService.sendHotListEmailCampaign(hotListEmailCampaignDTO, user, orgLocation, multiFile);
		return "success";
	}

	/**
	 * This method for jobs campaigns
	 * 
	 * @param jobListEmailCampaignDTO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendJobListCampaign", method = RequestMethod.POST)
	public String sendJobListCampaign(JobListEmailCampaignDTO jobListEmailCampaignDTO, MultipartHttpServletRequest request)
			throws Exception {
		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multiFile = request.getFile(itrator.next());
		}
		jobListEmailCampaignDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		hotListEmailCampaignService.sendJobListEmailCampaign(jobListEmailCampaignDTO, user, orgLocation, multiFile);
		return "success";
	}

	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/sendEmailToUsers", method = RequestMethod.POST)
	public String sendEmailToUsers(HotListEmailCampaignDTO hotListEmailCampaignDTO, MultipartHttpServletRequest request) throws Exception {

		MultipartFile multiFile = null;
		Iterator<String> itrator = request.getFileNames();
		if (itrator.hasNext()) {
			multiFile = request.getFile(itrator.next());
		}

		hotListEmailCampaignDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		emailService.sendEmail(hotListEmailCampaignDTO, user, orgLocation, multiFile);

		return "success";
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/saveUserSignature", method = RequestMethod.POST)
	public String saveUserSignature(@RequestParam(value = "json", required = false) String json, HttpServletRequest request)
			throws Exception {

		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		userSignatureService.setUserSignatureDetails(jsonObj, user, orgLocation);

		return "success";
	}

	@RequestMapping(value = "/sendRequestForResume", method = RequestMethod.POST)
	public String sendRequestForResume(HotListEmailCampaignDTO hotListEmailCampaignDTO,
			@RequestParam(value = "candidatesIds", required = false) String candidatesIds,
			@RequestParam(value = "UUID", required = false) String UUID, HttpServletRequest request) throws Exception {
		hotListEmailCampaignDTO.setStaticPath(staticPath);
		userMailBoxService.setRequestForResumeDetails(hotListEmailCampaignDTO, candidatesIds, UUID);

		return "success";
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/saveMailAsFavourite", method = RequestMethod.POST)
	public String saveMailAsFavourite(@RequestParam(value = "mailboxId", required = false) String mailboxId) throws Exception {

		UserMailBox mailBox = userMailBoxService.getUserMailBoxById(mailboxId);
		if (null != mailBox) {
			if (mailBox.getIsFavourite().equals(1)) {
				mailBox.setIsFavourite(0);
			} else {
				mailBox.setIsFavourite(1);
			}

			userMailBoxService.saveUserMailBox(mailBox);
			userMailBoxService.saveOrUpdateUserMailBoxELK(mailBox);
		}

		return "success";
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/sendMailsToTrash", method = RequestMethod.POST)
	public String sendMailsToTrash(@RequestParam(value = "mailIds", required = false) String mailIds, HttpServletRequest request)
			throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		UserMailBox mailbox = new UserMailBox();
		String resultJson;

		if (!isNullOrEmpty(mailIds)) {
			String[] mailBoxIds = mailIds.split(",");
			for (int i = 0; i < mailBoxIds.length; i++) {

				UserMailBox userMailBox = userMailBoxService.getUserMailBoxById(mailBoxIds[i]);
				userMailBox.setIsFavourite(0);
				if (!userMailBox.getMailStatus().equalsIgnoreCase("Trash")) {
					userMailBox.setMailStatus("Trash");
				} else {
					userMailBox.setMailStatus("Deleted");
					userMailBox.setStatus(0);
				}

				userMailBox.setUpdatedBy(user.getCreatedBy());
				userMailBox.setUpdatedDate(new Date());
				userMailBoxService.saveUserMailBox(userMailBox);
				userMailBoxService.saveOrUpdateUserMailBoxELK(userMailBox);
			}
		}

		Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(), orgLocations.getId(),
				"Inbox");
		Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(),
				orgLocations.getId(), "Requested");
		mailbox.setUnreadInboxCount(inboxUnreadMailsCount);
		mailbox.setUnreadRequestedCount(requestedUnreadMailsCount);

		resultJson = commonUtil.toJSON(mailbox);
		return resultJson;
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/getHotListedCandidateDetailsByUUID")
	public @ResponseBody String getHotListedCandidateDetailsByUUID(@RequestParam(value = "UUID", required = false) String UUID,
			@RequestParam(value = "type", required = false) String type, HttpServletRequest request) throws Exception {

		String resultJson;
		List<Candidate> hotListCandidatesList = new ArrayList<>();
		UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
		if (userMailbox != null && !isNullOrEmpty(userMailbox.getCandidateId())) {
			String[] candidateId = userMailbox.getCandidateId().split(",");
			for (int i = 0; i < candidateId.length; i++) {
				Candidate candidate = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId[i]));
				hotListCandidatesList.add(candidate);
			}
		}

		resultJson = commonUtil.toJSON(hotListCandidatesList);
		return resultJson;

	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/getEmailDetails")
	public @ResponseBody String getEmailDetails(@RequestParam(value = "UUID", required = false) String UUID,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "mailType", required = false) String mailType, HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		String resultJson;

		UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
		List<Candidate> reqCandidatesList = new ArrayList<Candidate>();
		List<UserMailBox> mailBoxList = new ArrayList<UserMailBox>();

		if (userMailbox != null) {

			UserMailBox previousMail = userMailBoxService.getUserPreviousMail(userMailbox.getId(), user.getId(), orgLocation.getId(), type,
					mailType);

			UserMailBox nextMail = userMailBoxService.getUserNextMail(userMailbox.getId(), user.getId(), orgLocation.getId(), type,
					mailType);

			userMailbox.setIsMailRead(1);
			userMailBoxService.saveUserMailBox(userMailbox);
			userMailBoxService.saveOrUpdateUserMailBoxELK(userMailbox);

			Integer inboxUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(),
					orgLocation.getId(), "Inbox");
			Integer requestedUnreadMailsCount = userMailBoxService.getUnReadMessagesByUserIdAndLocationIdAndType(user.getId(),
					orgLocation.getId(), "Requested");
			userMailbox.setUnreadInboxCount(inboxUnreadMailsCount);
			userMailbox.setUnreadRequestedCount(requestedUnreadMailsCount);
			userMailbox.setTotalUnReadMailsCount(inboxUnreadMailsCount + requestedUnreadMailsCount);
			SourceLeadSessionManager.setSessionAttribute(request, "unReadMailsCount", inboxUnreadMailsCount + requestedUnreadMailsCount);
			userMailbox.setCandidatesList(reqCandidatesList);
			if (null != nextMail) {
				userMailbox.setNextMailUUID(nextMail.getUuid());
			}

			if (null != previousMail) {
				userMailbox.setPreviousMailUUID(previousMail.getUuid());
			}

			mailBoxList.add(userMailbox);
			calculateDays(mailBoxList);

		}
		resultJson = commonUtil.toJSON(mailBoxList);
		return resultJson;
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/downloadMailAttachment", method = RequestMethod.GET)
	public void downloadMailAttachment(@RequestParam(value = "UUID", required = false) String UUID, HttpServletResponse response)
			throws Exception {

		UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);

		/*
		 * response.setContentType("application/octet-stream"); StringBuilder fileName =
		 * new StringBuilder(); response.setHeader("Content-Disposition",
		 * fileName.append("attachment; filename=\"").append(userMailbox.getFileName()).
		 * append("\"").toString());
		 * 
		 * InputStream inputStream = new ByteArrayInputStream(userMailbox.getDocFile());
		 * try { OutputStream outStream = response.getOutputStream();
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
		 */
	}

	@RequestMapping(value = "/validateCampaignToEmail", method = RequestMethod.POST)
	public String validateCampaignToEmail(@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "UUID", required = false) String UUID, HttpServletRequest request) throws Exception {

		String result = "Invalid";
		UserMailBox userMailBox = userMailBoxService.getUserMailBoxByUUID(UUID);
		if (userMailBox != null) {
			String[] toEmail = userMailBox.getToMail().split(",");
			for (int i = 0; i < toEmail.length; i++) {
				if (email.equalsIgnoreCase(toEmail[i].replaceAll("\"", ""))) {
					result = "success";
				}
			}

		}

		return result;
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/getSignatures", method = RequestMethod.POST)
	public @ResponseBody String getSignatures(HttpServletRequest request) throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<UserSignature> singaturesList = userSignatureService.getUserSignatureListByUserIdAndLocationId(user.getId(),
				orgLocations.getId());
		resultJson = toJSON(singaturesList);
		return resultJson;
	}

	@PreAuthorize("hasAnyRole('ROLE_HOTLIST_CAMPAIGN','ROLE_MAIL_BOX','ROLE_JOB_CAMPAIGN')")
	@RequestMapping(value = "/deleteReadMail", method = RequestMethod.POST)
	public String deleteReadMail(@RequestParam(value = "UUID", required = false) String UUID,
			@RequestParam(value = "type", required = false) String type, HttpServletRequest request) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		UserMailBox mailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
		if (null != mailbox && null != type) {
			if (type.equalsIgnoreCase(Constants.MAIL_TRASH)) {
				mailbox.setMailStatus(Constants.MAIL_DELETE);
				mailbox.setStatus(0);
			} else {
				mailbox.setMailStatus(Constants.MAIL_TRASH);
			}
			userMailBoxService.saveUserMailBox(mailbox);
			userMailBoxService.saveOrUpdateUserMailBoxELK(mailbox);
		}
		return "success";
	}

	@RequestMapping(value = { "/getRequestedCandidates", "/getRequestedCandidates" }, method = RequestMethod.POST)
	public @ResponseBody String getRequestedCandidates(@RequestParam(value = "UUID", required = false) String UUID,
			HttpServletRequest request) throws Exception {
		String resultJson = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		UserMailBox userMailbox = userMailBoxService.getUserMailBoxByUUID(UUID);
		List<Long> candidateId = new ArrayList<Long>();
		for (String s : userMailbox.getCandidateId().split(",")) {
			candidateId.add(Long.parseLong(s));
		}

		List<Candidate> reqCandidatesList = new ArrayList<Candidate>();
		List<Candidate> candidatesList = candidateService.getAllRequestedCandidatesForCampaign(candidateId);
		if (null != candidatesList) {

			for (Candidate candidate : candidatesList) {
				StringBuilder skills = new StringBuilder();
				String candidateSkills = null;
				// List<CandidateSkills> skillsList =
				// candidateSkillsService.getCandidateSkillsByCandidateId(candidate.getId().toString());
				String skillsNames = candidate.getSkillsName();
				if (null != skillsNames) {
					// List<String> skillsList = new
					// ArrayList<String>(Arrays.asList(skillsNames.split(",")));
					String[] skillsList = skillsNames.split(",");
					if (skillsList.length > 0) {
						for (String skill : skillsList) {
							skills.append(skill).append(", ");
						}
						skills.toString();
						candidateSkills = skills.substring(0, skills.length() - 2);
					}

				}

				StringBuilder candidateName = new StringBuilder();
				String experienceInYears = null;
				String experienceInMonths = null;
				StringBuilder totalExp = new StringBuilder();
				StringBuilder rate = new StringBuilder();

				StringBuilder nameBuilder = new StringBuilder();
				StringBuilder expBuilder = new StringBuilder();
				StringBuilder rateBuilder = new StringBuilder();

				if (userMailbox.getNameFields().equalsIgnoreCase("firstname")) {
					candidateName = nameBuilder.append(candidate.getFirstName());
				} else if (userMailbox.getNameFields().equalsIgnoreCase("firstnameandlastName")) {
					if (!isNullOrEmpty(candidate.getLastName())) {
						candidateName = nameBuilder.append(candidate.getFirstName()).append(" ").append(candidate.getLastName());
					} else {
						candidateName = nameBuilder.append(candidate.getFirstName());
					}
				} else if (userMailbox.getNameFields().equalsIgnoreCase("firstname1stletterlastname")) {
					if (!isNullOrEmpty(candidate.getLastName())) {
						candidateName = nameBuilder.append(candidate.getFirstName()).append(" ")
								.append(String.valueOf(candidate.getLastName().charAt(0)));
					} else {
						candidateName = nameBuilder.append(candidate.getFirstName());
					}

				} else if (userMailbox.getNameFields().equalsIgnoreCase("1letterfirstnamelastname")) {
					if (!isNullOrEmpty(candidate.getLastName())) {
						candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0))).append(" ")
								.append(candidate.getLastName());
					} else {
						candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0)));
					}

				} else if (userMailbox.getNameFields().equalsIgnoreCase("1letterfirstname1letterlastname")) {
					if (!isNullOrEmpty(candidate.getLastName())) {
						candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0))).append(" ")
								.append(String.valueOf(candidate.getLastName().charAt(0)));
					} else {
						candidateName = nameBuilder.append(String.valueOf(candidate.getFirstName().charAt(0)));
					}

				}

				if (!isNullOrEmpty(candidate.getExperienceYears())) {
					experienceInYears = candidate.getExperienceYears();
				}

				if (!isNullOrEmpty(candidate.getExperienceMonths())) {
					experienceInMonths = candidate.getExperienceMonths();
				} else {
					experienceInMonths = "0";
				}

				if (isNullOrEmpty(candidate.getExperienceMonths()) && isNullOrEmpty(candidate.getExperienceMonths())) {
					totalExp = expBuilder.append("-----");
				} else {
					totalExp = expBuilder.append(experienceInYears).append(" Years ").append(experienceInMonths).append(" Months");
				}

				if (!isNullOrEmpty(candidate.getExpectedRate())) {
					rate = rateBuilder.append(candidate.getExpectedRate()).append(" /").append(candidate.getRateType());
				} else {
					rate = rateBuilder.append("-----");
				}
				candidate.setCandidateName(candidateName.toString());
				candidate.setTotalExp(totalExp.toString());
				candidate.setRate(rate.toString());
				candidate.setSkills(candidateSkills);

				reqCandidatesList.add(candidate);
			}
		}

		resultJson = toJSON(reqCandidatesList);
		return resultJson;
	}

	@RequestMapping(value = "/getMailingUserList", method = RequestMethod.POST)
	public @ResponseBody String getMailingUserList(@RequestParam(value = "teamType", required = false) String teamType,
			HttpServletRequest request) throws Exception {

		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> usersList = userService.getUsersByTeamType(orgLocation.getId(), teamType);
		for (User user : usersList) {
			UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocation.getId());
			if (userRole != null) {
				user.setUserType(userRole.getUserType());
			}
		}
		resultJson = commonUtil.toJSON(usersList);
		return resultJson;
	}

	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/saveOrgSignature", method = RequestMethod.POST)
	public String saveOrgSignature(@RequestParam(value = "json", required = false) String json, HttpServletRequest request)
			throws Exception {

		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		sourceleadSignatureService.saveOrgSignatrue(jsonObj, orgLocation, user);

		return "success";
	}

	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/getSignaturePreview", method = RequestMethod.POST)
	public String getSignaturePreview(HttpServletRequest request) throws Exception {

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String signature = sourceleadSignatureService.buildSignature(user, orgLocation);

		return signature;
	}

	@PreAuthorize("hasRole('ROLE_VIEW_EDIT_ORG_SETTINGS')")
	@RequestMapping(value = "/restoreSignature", method = RequestMethod.POST)
	public String restoreSignature(HttpServletRequest request) throws Exception {

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		sourceleadSignatureService.restoreSignature(user, orgLocation);

		return "success";
	}

	@SuppressWarnings("static-access")
	public void calculateDays(List<UserMailBox> mailBoxList) throws ParseException {
		for (UserMailBox userMailBox : mailBoxList) {
			long diff = commonUtil.currentDate().getTime() - userMailBox.getCreatedDate().getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder time = new StringBuilder();

			if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
				userMailBox.setCalDays(time.append(diffSeconds).append(" sec ago ").toString());
			} else if (diffDays == 0 && diffHours == 0) {
				userMailBox.setCalDays(time.append(diffMinutes).append(" min ").append(diffSeconds).append(" sec ago ").toString());
			} else if (diffDays == 0) {
				userMailBox.setCalDays(time.append(diffHours).append(" hours ").append(diffMinutes).append(" min ago ").toString());
			} else if (diffDays < 7) {
				userMailBox.setCalDays(time.append(diffDays).append(" day(s) ").toString());
			} else {
				DateFormat standardFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
				String commonDateFormat = standardFormat.format(userMailBox.getCreatedDate());
				userMailBox.setCalDays(commonDateFormat.toString());
			}

		}

	}

	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/getUserMailBoxDetailsByUUID")
	public String getUserMailBoxDetailsByUUID(@RequestParam(value = "UUID", required = false) String UUID, HttpServletRequest request)
			throws Exception {

		String resultJson = null;
		UserMailBox mailBox = userMailBoxService.getUserMailBoxByUUID(UUID);

		resultJson = toJSON(mailBox);
		return resultJson;

	}

	@RequestMapping(value = "/getComposeMailTeamsList", method = RequestMethod.POST)
	public @ResponseBody String getComposeMailTeamsList(@RequestParam(value = "reqId", required = false) Long reqId,
			HttpServletRequest request) throws Exception {
		String resultJson;
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		CombinedList combinedList = new CombinedList();

		List<RequirementAssignTo> requirementAssignTo = null;
		if (!Objects.isNull(reqId)) {
			requirementAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(reqId,
					orgLocation.getId());
		}

		List<Team> teamsList = teamService.getTeamListByOrgLocationID(orgLocation.getId());
		if (null != teamsList) {
			for (Team team : teamsList) {

				if (null != requirementAssignTo) {
					if (requirementAssignTo.stream().anyMatch(t -> t.getTeamId() != null && t.getTeamId().getId().equals(team.getId()))) {
						team.setIsTeamAssigned("Y");
					}
				}
			}
			combinedList.setTeams(teamsList);
		}

		List<Roles> rolesList = roleService.getTabAccessRolesListByOrgLocationIdAndTabId(orgLocation.getId(),
				Long.valueOf(Constants.TAB_REQUIREMENT));
		if (null != rolesList) {
			for (Roles role : rolesList) {
				if (null != requirementAssignTo) {
					if (requirementAssignTo.stream()
							.anyMatch(t -> t.getTeamId() == null && t.getRoleId() != null && t.getRoleId().getId().equals(role.getId()))) {
						role.setIsRoleAssigned("Y");
					}
				}
			}
			combinedList.setRoles(rolesList);
		}

		resultJson = commonUtil.toJSON(combinedList);
		return resultJson;
	}

	@RequestMapping(value = "/getUserContactsListForMalling", method = RequestMethod.GET)
	public ResponseList<ReferContactsDTO> getUserContactsListForMalling(ReferContactsDTO referContactsDTO, HttpServletRequest request)
			throws Exception {

		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		String[] tableColumns = { "ischeck", "contactName", "email", "groups", "source" };
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default referContactsDTO order
		if (referContactsDTO.getOrder() != null && !referContactsDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(referContactsDTO.getOrder().get(0).get("column"));
			sortColumnOrder = referContactsDTO.getOrder().get(0).get("dir");
		}
		referContactsDTO.setSortColumn(tableColumns[sortColumnIndex]);
		referContactsDTO.setSortOrder(sortColumnOrder);

		ResponseList<ReferContactsDTO> contactsDTOList = referContactsService.getReferContactsListByUserIdAndOrglocationId(referContactsDTO,
				user.getId(), orgLocation.getId());

		return contactsDTOList;
	}

	@RequestMapping(value = "/getAllJobCampaignsList", method = RequestMethod.GET)
	public List<RequirementDTO> getAllJobCampaignsList(@RequestParam(value = "jobCampaignUUID", required = false) String jobCampaignUUID)
			throws Exception {

		List<Long> requirementIds = new ArrayList<>();
		UserMailBox mailBox = userMailBoxService.getUserMailBoxByUUID(jobCampaignUUID);
		if (mailBox != null && mailBox.getRequirementId() != null) {
			String[] reqIds = mailBox.getRequirementId().split(",");
			for (int i = 0; i < reqIds.length; i++) {
				requirementIds.add(Long.valueOf(reqIds[i]));
			}
		}

		List<RequirementDTO> requirementDTOList = requirementService.getAllCampaignRequirementsByIdsList(requirementIds);
		return requirementDTOList;
	}

	public String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	@RequestMapping(value = "/addEmailAccount", method = RequestMethod.POST)
	public Response<Boolean> addEmailAccount(EmailAccountSettingsDTO emailAccountSettingsDTO, HttpServletRequest request) throws Exception {
		Response<Boolean> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			response = emailAccountSettingsService.StoreEmailAccountSettings(emailAccountSettingsDTO, user, orgLocations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/deleteEmailAccount", method = RequestMethod.POST)
	public @ResponseBody String deleteEmailAccount(Long id, HttpServletRequest request) throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long userId = user.getId();
		emailAccountSettingsService.deleteEmailAccount(id, userId);
		return "success";

	}

	@RequestMapping(value = "/getEmailAccount", method = RequestMethod.POST)
	@ResponseBody
	public EmailAccountSettingsDTO getEmailAccount(Long id, HttpServletRequest request) throws Exception {
		EmailAccountSettingsDTO emailAccountSettingsDTO = new EmailAccountSettingsDTO();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user != null) {
			Long userId = user.getId();
			emailAccountSettingsDTO = emailAccountSettingsService.getEmailAccount(id, userId);
		}
		return emailAccountSettingsDTO;
	}

	@RequestMapping(value = "/emailAccountRead", method = RequestMethod.POST)
	public @ResponseBody ResponseList<String> emailAccountRead(Long id, HttpServletRequest request) throws Exception {
		ResponseList<String> responseList = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long userId = user.getId();
		responseList = emailAccountSettingsService.emailAccountRead(id, userId);
		return responseList;
	}

	@RequestMapping(value = "/emailFolderRead", method = RequestMethod.POST)
	public @ResponseBody ResponseList<ReadMailDTO> emailFolderRead(Long id, @RequestParam(defaultValue = "") String folderName,
			HttpServletRequest request, Paginator paginator) throws Exception {
		ResponseList<ReadMailDTO> responseList = new ResponseList<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Long userId = user.getId();
		folderName = folderName.replaceAll("-and-", "&");
		responseList = emailAccountSettingsService.emailFolderRead(id, folderName, userId, orgLocation.getId(), paginator);
		return responseList;
	}

	@RequestMapping(value = "/deleteEmailsByUID", method = RequestMethod.POST)
	public @ResponseBody Response<Boolean> deleteEmailsByUID(HttpServletRequest request,
			@RequestParam(defaultValue = "0") long emailAccountId, @RequestParam(defaultValue = "") long[] messageIds,
			@RequestParam(defaultValue = "") String folderName, @RequestParam(defaultValue = "false") boolean deleteForever)
			throws Exception {
		Response<Boolean> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		folderName = folderName.replaceAll("-and-", "&");
		response = emailAccountSettingsService.deleteEmailsByUID(emailAccountId, user.getId(), messageIds, folderName, deleteForever);
		return response;
	}

	@RequestMapping(value = "/getEmailByUID", method = RequestMethod.POST)
	public @ResponseBody ReadMailDTO getEmailByUID(HttpServletRequest request, @RequestParam(defaultValue = "0") long emailAccountId,
			long uid, @RequestParam(defaultValue = "") String folderName) throws Exception {
		ReadMailDTO readMailDTO = null;
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (user != null && org != null && orgLocation != null) {
			folderName = folderName.replaceAll("-and-", "&");
			readMailDTO = emailAccountSettingsService.getEmailByUID(emailAccountId, user.getId(), org.getId(), orgLocation.getId(), uid,
					folderName, staticPath);
		}
		return readMailDTO;
	}

	@PreAuthorize("hasRole('ROLE_MAIL_BOX')")
	@RequestMapping(value = "/sendEmailFromMailBox", method = RequestMethod.POST)
	public Response<Boolean> sendEmailFromMailBox(MultipartHttpServletRequest request, ReadMailDTO readMailDTO) throws Exception {
		Response<Boolean> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		if (user != null && org != null && orgLocation != null) {
			return emailService.sendEmailFromMailBox(readMailDTO, user.getId(), org.getId(), orgLocation.getId(), staticPath);
		}
		return response;
	}

	@RequestMapping(value = "/getEmailConfiguration")
	@ResponseBody
	public ModelAndView getEmailConfiguration(HttpServletRequest request, @RequestParam(defaultValue = "0") long id) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				EmailAccountSettingsDTO emailAccountSettingsDTO = emailAccountSettingsService.getEmailAccount(id, user.getId());
				modelAndView.addObject("emailAccount", emailAccountSettingsDTO);
				modelAndView.addObject("sourceleadUrl", sourceleadUrl);
				modelAndView.setViewName("emailConfiguration");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
}