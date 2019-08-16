package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.SortedArraySet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.SubmissionTemplateDao;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.JobFairRequirementsDTO;
import com.bis.sourcelead.dto.RecruiterRrportDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementIncentiveDTO;
import com.bis.sourcelead.dto.RequirementLocationsDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.JobCustomFieldData;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.JobFairRequirements;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.OrglocationFormFields;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.RecruiterReportAssociateCountView;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SubmissionRequirementListView;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.HIERARCHY_TYPE;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.JobCustomFieldDataService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.OrglocationFormFieldsService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

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
	CandidateService candidateService;
	@Autowired
	private RequirementAssignToService requirementAssignToService;
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	@Autowired
	UserService userService;
	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
	@Autowired
	EmailScenarioService emailScenarioService;
	@Autowired
	RequirementApproverHierarchyDao requirementApproverHierarchyDao;
	@Autowired
	OrgService orgService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RequirementSubmissionService requirementSubmissionService;
	@Autowired
	private CandidateResumesService candidateResumeSerivce;
	@Autowired
	private OrgRelationsService orgRelationsService;
	@Autowired
	LookUpDataService lookUpDataService;
	@Autowired
	private PdfUtil pdfUtil;
	@Autowired
	private RequirementElkUtil requirementElkUtil;
	@Autowired
	private ProjectsService projectsService;
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	@Value("${atsUrl}")
	private String atsUrl;	
	@Autowired
	private RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	OrgLocationService orgLocationService;
	@Autowired
	private CandidateDao candidateDao;
	@Autowired
	private ProjectsDao projectsDao;
	@Autowired
	private JobCustomFieldDataService jobCustomFieldDataService;

	@Autowired
	private ProjectClientService projectClientService;

	@Autowired
	private OrglocationFormFieldsService orglocationFormFieldsService;

	@Autowired
	private RequirementLocationsService requirementLocationsService;
	@Autowired
	private OrgSpocService orgSpocService;

	@Override
	public String getRequirementId(OrgLocations orgLocations) {
		String companyShortCode = null;
		Long number = 1L;
		String requirementId = null;
		Integer generatedIdcount = 0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.JOB_ENTITY);
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Long checkNumber = requirementDao.getRequirementGeneratedIdByOrgLocationId(orgLocations.getId(),
				generatedIdcount);
		if (checkNumber == null) {
			requirementId = number.toString();
		} else {
			Long reqId = checkNumber + 1;
			requirementId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
		StringBuffer sb = new StringBuffer();
		if (orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())) {
			companyShortCode = orgLocations.getGeneratedUserId();
		}
		return sb.append(companyShortCode).append(Constants.JOB_ENTITY).append(convetredRequirementsId).toString();
	}
	
	@Override
	public String getGeneratedJobFairIdByOrgLocationId(long orgLocationsId) {
		try {
			OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationsId);
			if(orgLocations!=null) {
				return getGeneratedJobFairId(orgLocations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getGeneratedJobFairId(OrgLocations orgLocations) {
		String companyShortCode = null;
		Long number = 1L;
		String requirementId = null;
		Integer generatedIdcount = 0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.JOB_FAIR);
		String generatedUsreId = sbuilder.toString();
		for (int i = 0; i < generatedUsreId.length(); i++) {
			char ch = generatedUsreId.charAt(i);
			if (Character.isAlphabetic(ch)) {
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount + 1;
		Long checkNumber = requirementDao.getJobFairGeneratedIdByOrgLocationId(orgLocations.getId(), generatedIdcount);
		if (checkNumber == null) {
			requirementId = number.toString();
		} else {
			Long reqId = checkNumber + 1;
			requirementId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");
		String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
		StringBuffer sb = new StringBuffer();
		if (orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())) {
			companyShortCode = orgLocations.getGeneratedUserId();
		}
		return sb.append(companyShortCode).append(Constants.JOB_FAIR).append(convetredRequirementsId).toString();
	}

	/**
	 * @author prasad k
	 * @since aug 26 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to store/save Requirement in Requirement table.
	 * 
	 * 
	 * 
	 * @param jsonObj
	 *            This is the first paramter to storeRequirement method
	 * @param orgLocations
	 *            This is the second paramter to storeRequirement method
	 * @param user
	 *            This is the third paramter to storeRequirement method
	 * @param multiFile
	 *            This is the fourth paramter to storeRequirement method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */
	@Override
	public Requirement storeRequirement(RequirementDTO requirementDTO, OrgLocations orgLocations, User user)
			throws Exception {
		Requirement requirement = null;
		String client = "";
		String requirementClient = "";
		orgLocations = orgLocationService.getOrgLocationById(orgLocations.getId());
		Date date = new Date();
		StringBuilder location = new StringBuilder();
		if (StringUtils.isNotBlank(requirementDTO.getRequirementId())) {
			requirement = requirementDao
					.getRequirementByRequirementId(Long.parseLong(requirementDTO.getRequirementId()));
			requirement.setId(requirement.getId());
			requirement.setUpdatedBy(user.getUserCode());
			requirement.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
		} else {
			requirement = new Requirement();
			requirement.setGeneratedId(getRequirementId(orgLocations));
			requirement.setCreatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			requirement.setTotalApplicants(0);
			requirement.setCreatedBy(user);
			requirement.setUpdatedBy(user.getUserCode());
			requirement.setUpdatedDate(commonUtil.orgLocationCommonDate(date, orgLocations));
			requirement.setRequirementUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());

			if (StringUtils.isNotBlank(requirementDTO.getJobTitle())) {
				String jobTitle = "";
				jobTitle = requirementDTO.getJobTitle().replaceAll("[^a-zA-Z0-9]+", "-").toLowerCase();
				if (jobTitle.startsWith("-")) {
					jobTitle = jobTitle.substring(1);
				}
				if (jobTitle.endsWith("-")) {
					jobTitle = jobTitle.substring(0, jobTitle.length() - 1);
				}
				requirement.setUrl(jobTitle);
			}
		}
		
		if(requirementDTO.getProfilePercentage() != null) {
			requirement.setProfilePercentage(Integer.parseInt(requirementDTO.getProfilePercentage()));
		}else {
			requirement.setProfilePercentage(orgLocations.getProfilePercentage());
		}
		//WORKFLOWID added by SRINIVAS SABBU on 24-May-2019.
		// below lines is commented for to move in next release build.
		/*if (StringUtils.isNotBlank(requirementDTO.getWorkflow())) {
			RequirementWorkFlow workflow = new RequirementWorkFlow();
			workflow.setId(Long.parseLong(requirementDTO.getWorkflow()));
			requirement.setWorkflowId(workflow);
		}*/
		if (orgLocations != null) {
			requirement.setOrgLocationId(orgLocations);
		}
		if (StringUtils.isNotBlank(requirementDTO.getJobTitle())) {
			if (!"".equalsIgnoreCase(requirementDTO.getJobTitle())) {
				requirement.setJobTitle(requirementDTO.getJobTitle());
			}
		}
		if (StringUtils.isNotBlank(requirementDTO.getProcess())) {
			if (!"".equalsIgnoreCase(requirementDTO.getProcess())) {
				requirement.setProcess(requirementDTO.getProcess());
			}
		}
		if (StringUtils.isNotBlank(requirementDTO.getJobType())) {
			requirement.setJobType(requirementDTO.getJobType());
		}
		if (StringUtils.isNotBlank(requirementDTO.getOtherJobType())
				&& StringUtils.isNotBlank(requirementDTO.getJobType())
				&& "Other".equalsIgnoreCase(requirementDTO.getJobType())) {
			requirement.setOtherJobType(requirementDTO.getOtherJobType());
		} else {
			requirement.setOtherJobType(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getJobId())) {
			requirement.setJobId(requirementDTO.getJobId());
		} else {
			requirement.setJobId(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getPositions())) {
			requirement.setPositions(requirementDTO.getPositions());
		} else {
			requirement.setPositions(null);
		}
		/*
		 * if (StringUtils.isNotBlank(requirementDTO.getCountryReq())) {
		 * requirement.setCountry(requirementDTO.getCountryReq());
		 * location.append(requirementDTO.getCountryReq()); location.append(","); } if
		 * (StringUtils.isNotBlank(requirementDTO.getState())) { if
		 * (!requirementDTO.getState().isEmpty()) {
		 * requirement.setState(requirementDTO.getState());
		 * location.append(requirementDTO.getState()); location.append(","); } else {
		 * location.append("--").append(","); } } if
		 * (StringUtils.isNotBlank(requirementDTO.getCity())) { if
		 * (!requirementDTO.getCity().isEmpty()) {
		 * requirement.setCity(requirementDTO.getCity());
		 * location.append(requirementDTO.getCity()); location.append(","); } else {
		 * location.append("--").append("."); } } if
		 * (StringUtils.isNotBlank(requirementDTO.getLocation())) {
		 * requirement.setLocation(requirementDTO.getLocation()); } if
		 * (StringUtils.isNotBlank(requirementDTO.getZipCode())) {
		 * requirement.setZipCode(requirementDTO.getZipCode()); } else{
		 * requirement.setZipCode(null); }
		 */
		if (StringUtils.isNotBlank(requirementDTO.getMaxExp())) {
			requirement.setMaxExp(requirementDTO.getMaxExp());
		} else {
			requirement.setMaxExp(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getMinExp())) {
			requirement.setMinExp(requirementDTO.getMinExp());
		} else {
			requirement.setMinExp(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getRate())) {
			requirement.setRate(requirementDTO.getRate());
		} else {
			requirement.setRate(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getRateType())) {
			requirement.setRateType(requirementDTO.getRateType());
		}
		if (StringUtils.isNotBlank(requirementDTO.getDescription())) {
			// requirement.setDescription(java.net.URLDecoder.decode(jsonObj.getString("description"),
			// "utf-8"));
			requirement.setDescription(requirementDTO.getDescription());
		} else {
			requirement.setDescription(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getCoolingOff())) {
			requirement.setReqCoolingOffPeriod(requirementDTO.getCoolingOff());
		}
		if (StringUtils.isNotBlank(requirementDTO.getIndustryName())) {
			requirement.setIndustryName(requirementDTO.getIndustryName());
		} else {
			requirement.setIndustryName(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getProjectClientID())) {
			ProjectClients projects = projectClientService
					.getProjectClientsByProjectID(Long.parseLong(requirementDTO.getProjectClientID()));
			requirement.setProjectClientID(projects);
		} else {
			requirement.setProjectClientID(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getProjectDurationMonths())) {
			requirement.setProjectDurationMonths(requirementDTO.getProjectDurationMonths());
		} else {
			requirement.setProjectDurationMonths(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getProjectDurationYears())) {
			requirement.setProjectDurationYears(requirementDTO.getProjectDurationYears());
		} else {
			requirement.setProjectDurationYears(null);
		}

		if (StringUtils.isNotBlank(requirementDTO.getClosedDate())
				&& !"".equalsIgnoreCase(requirementDTO.getClosedDate())) {
			try {
				Date convertedDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),
						requirementDTO.getClosedDate());
				requirement.setClosedDate(convertedDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			requirement.setClosedDate(null);
		}
		if (requirementDTO.getNoticePeriod() != null && requirementDTO.getNoticePeriod().size() > 0) {
			String np = String.join(",", requirementDTO.getNoticePeriod());
			requirement.setNoticePeriod(np);
		} else {
			requirement.setNoticePeriod(null);
		}
		
		requirement.setWalkIn(requirementDTO.isWalkIn() && requirementDTO.getWalkInDays() != null && !requirementDTO.getWalkInDays().isEmpty());
		requirement.setWalkInDays(requirementDTO.isWalkIn() ? requirementDTO.getWalkInDays() : "");
		
		if (StringUtils.isNotBlank(requirementDTO.getTravelAllowance())) {
			requirement.setTravelAllowance(requirementDTO.getTravelAllowance());
		}
		if (StringUtils.isNotBlank(requirementDTO.getCurrencyCode())) {
			if (!"".equalsIgnoreCase(requirementDTO.getCurrencyCode())) {
				requirement.setCurrencyCode(requirementDTO.getCurrencyCode());
			}
		}
		if (StringUtils.isNotBlank(requirementDTO.getMaxRate())) {
			requirement.setMaxRate(requirementDTO.getMaxRate());
		} else {
			requirement.setMaxRate(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getPriority())) {
			if (!"".equalsIgnoreCase(requirementDTO.getPriority())) {
				requirement.setPriority(requirementDTO.getPriority());
			}
		}
		if (StringUtils.isNotBlank(requirementDTO.getIsTemplate())
				&& "Y".equalsIgnoreCase(requirementDTO.getIsTemplate())) {
			/*
			 * This is to store the Requirement Template as another Requirement
			 */
			storeRequirementTemplate(requirementDTO, orgLocations, user);
		}
		if (StringUtils.isNotBlank(requirementDTO.getSkills())) {
			requirement.setSkills(requirementDTO.getSkills());
		} else {
			requirement.setSkills(null);
		}
		if (requirementDTO.getVisa() != null && requirementDTO.getVisa().size() > 0) {
			String visaList = String.join(",", requirementDTO.getVisa());
			requirement.setVisaType(visaList);
		} else {
			requirement.setVisaType(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getEducation())) {
			requirement.setEducation(requirementDTO.getEducation());
		} else {
			requirement.setEducation(null);
		}
		if (requirementDTO.getFileInfo() != null && !requirementDTO.getFileInfo().isEmpty()) {
			FileInfo fileInfo = requirementDTO.getFileInfo().get(0);
			requirement.setFileName(fileInfo.getFileName());
			requirement.setFileContentType(fileInfo.getContentType());
			requirement.setFileURL(fileInfo.getFileURL());
			if (StringUtils.isEmpty(requirementDTO.getImportValue())) {
				FileUploadUtil.moveFile(requirementDTO.getStaticPath(), fileInfo, "requirementDocuments");
			}
		} else {
			if (StringUtils.isNotBlank(requirementDTO.getTemplateId())) {
				String templateId = requirementDTO.getTemplateId();
				Requirement templateRequirement = getRequirementByRequirementId(Long.parseLong(templateId));
				requirement.setFileURL(templateRequirement.getFileURL());
				requirement.setFileName(templateRequirement.getFileName());
				requirement.setFileContentType(templateRequirement.getFileContentType());
				requirement.setIsClientJob("N");
			}
		}
		if (StringUtils.isNotBlank(requirementDTO.getIsClient())
				&& requirementDTO.getIsClient().equalsIgnoreCase("Y")) {
			requirement.setIsClientJob(requirementDTO.getIsClient());
		} else {
			requirement.setIsClientJob("N");
		}
		if (StringUtils.isNotBlank(requirementDTO.getIsreceivingApplicants())
				&& requirementDTO.getIsreceivingApplicants().equalsIgnoreCase("Y")) {
			requirement.setIsreceivingApplicants(requirementDTO.getIsreceivingApplicants());
		} else {
			requirement.setIsreceivingApplicants("N");
		}
		if (StringUtils.isNotBlank(requirementDTO.getSelectClient())
				&& StringUtils.isNotBlank(requirementDTO.getIsClient())
				&& requirementDTO.getIsClient().equalsIgnoreCase("Y")) {
			requirementClient = (requirementDTO.getIsClient());
			OrgRelations orgRelations = new OrgRelations();
			orgRelations.setId(Long.parseLong(requirementDTO.getSelectClient().split(",")[0]));
			requirement.setClientId(orgRelations);
			client = requirementDTO.getSelectClient().split(",")[0];
		}
		
		if (StringUtils.isNotBlank(requirementDTO.getSelectContact())) {
			OrgSpoc orgSpoc = orgSpocService.getOrgSpocById(Long.parseLong(requirementDTO.getSelectContact()));
			requirement.setSpocId(orgSpoc);
		}
		if (StringUtils.isNotBlank(requirementDTO.getClientCurrencyCode())) {
			requirement.setClientCurrencyCode(requirementDTO.getClientCurrencyCode());
		}
		if (StringUtils.isNotBlank(requirementDTO.getClientRate())) {
			requirement.setClientRate(requirementDTO.getClientRate());
		}
		if (StringUtils.isNotBlank(requirementDTO.getClientMaxRate())) {
			requirement.setClientMaxRate(requirementDTO.getClientMaxRate());
		}
		if (StringUtils.isNotBlank(requirementDTO.getClientRateType())) {
			requirement.setClientRateType(requirementDTO.getClientRateType());
		}
		if (StringUtils.isNotBlank(requirementDTO.getExperienceLevel())) {
			LookUpData experienceLevel = lookUpDataService.getTasksTypeByUniqueKey(requirementDTO.getExperienceLevel());
			requirement.setExperienceLevel(experienceLevel);
		} else {

			requirement.setExperienceLevel(null);
		}
		
		if (StringUtils.isNotBlank(requirementDTO.getJobRate())) {
			requirement.setJobRate(requirementDTO.getJobRate());
		}else {
			requirement.setJobRate(null);
		}
		
		if (StringUtils.isNotBlank(requirementDTO.getIncentivized())) {
			requirement.setIncentivized(requirementDTO.getIncentivized());
		}else {
			requirement.setIncentivized("N");
		}
		
		if (StringUtils.isNotBlank(requirementDTO.getPayOutPeriod())) {
			requirement.setPayOutPeriod(requirementDTO.getPayOutPeriod());
		}else {
			requirement.setPayOutPeriod(null);
		}
		
		HashSet<RequirementIncentive> requirementIncentiveList = new HashSet<RequirementIncentive>();
		if (requirementDTO.getRequirementIncentive() != null && !requirementDTO.getRequirementIncentive().isEmpty() && StringUtils.equals(requirementDTO.getIncentivized(), "Y")) {
			for (RequirementIncentiveDTO incentiveDTO : requirementDTO.getRequirementIncentive()) {
				if (StringUtils.isNotBlank(incentiveDTO.getJoiners()) && StringUtils.isNotBlank(incentiveDTO.getJoinersAmount())) {
					RequirementIncentive requirementIncentive = new RequirementIncentive();
					requirementIncentive.setRequirementId(requirement);
					if (incentiveDTO.getId() != null) {
						requirementIncentive.setId(incentiveDTO.getId());
					}
					if (incentiveDTO.getJoiners() != null && !incentiveDTO.getJoiners().isEmpty()) {
						requirementIncentive.setJoiners(incentiveDTO.getJoiners());
					}

					if (incentiveDTO.getJoinersAmount() != null && !incentiveDTO.getJoinersAmount().isEmpty()) {
						requirementIncentive.setJoinersAmount(incentiveDTO.getJoinersAmount());
					}
					
					if (incentiveDTO.getJoinersAmountType() != null && !incentiveDTO.getJoinersAmountType().isEmpty()) {
						requirementIncentive.setJoinersAmountType(incentiveDTO.getJoinersAmountType());
					}
					requirementIncentive.setStatus(Constants.RECORD_ACTIVE);
					requirementIncentive.setCreatedDate(commonUtil.currentDate());
					requirementIncentive.setCreatedBy(user);
					if (incentiveDTO.getId() != null) {
						requirementIncentive.setUpdatedBy(user);
						requirementIncentive.setUpdatedDate(commonUtil.currentDate());
					}
					requirementIncentiveList.add(requirementIncentive);
				}
			}
			this.deleteRequirementIncentiveByRequirementIdOrIds(0l, requirementDTO.getArrDeletedIds());
		}else if(requirement != null && requirement.getId() != null && !StringUtils.equals(requirementDTO.getIncentivized(), "Y")) {
			this.deleteRequirementIncentiveByRequirementIdOrIds(requirement.getId(), "");
		}
		requirement.setRequirementIncentive(requirementIncentiveList);
		
		
		if (StringUtils.isNotBlank(requirementDTO.getIsClient())
				&& StringUtils.isNotBlank(requirementDTO.getClientSubmissionForm())) {
			if (requirementDTO.getIsClient().equalsIgnoreCase("Y")
					&& StringUtils.isNotBlank(requirementDTO.getIsClient())
					&& StringUtils.isNotBlank(requirementDTO.getClientSubmissionForm())) {
				SubmissionTemplate submissionTemplate = submissionTemplateDao
						.getSubmissionTemplateById(Long.parseLong(requirementDTO.getClientSubmissionForm()));
				submissionTemplate.setId(Long.parseLong(requirementDTO.getClientSubmissionForm()));
				requirement.setClientSubmissionTemplateId(submissionTemplate);
				requirement.setClientSubmissionTemplateJsonString(submissionTemplate.getJsonString());
				// saving submission Temmplate of Client to InternalDetails
				// Submission Template defaultly
				requirement.setSubmissionTemplateId(submissionTemplate);
				requirement.setSubmissionTemplateJsonString(submissionTemplate.getJsonString());
			}
		} else if (StringUtils.isNotBlank(requirementDTO.getApplicationFrom())) {
			SubmissionTemplate submissionTemplate = submissionTemplateDao
					.getSubmissionTemplateById(Long.parseLong(requirementDTO.getApplicationFrom()));
			submissionTemplate.setId(Long.parseLong(requirementDTO.getApplicationFrom()));
			requirement.setSubmissionTemplateId(submissionTemplate);
			requirement.setSubmissionTemplateJsonString(submissionTemplate.getJsonString());
		}
		if (StringUtils.isNotBlank(requirementDTO.getReleaseToAllUsers())
				&& "Y".equalsIgnoreCase(requirementDTO.getReleaseToAllUsers())) {
			requirement.setReleaseJobToAllUsers(requirementDTO.getReleaseToAllUsers());
		}
		if (StringUtils.isNotBlank(requirementDTO.getMedicalClearence())) {
			requirement.setMedicalClearence(requirementDTO.getMedicalClearence());
		}
		if (StringUtils.isNotBlank(requirementDTO.getSecurityClearance())) {
			requirement.setSecurityClearance(requirementDTO.getSecurityClearance());
		}
		if (StringUtils.isNotBlank(requirementDTO.getOverTime())) {
			requirement.setOverTime(requirementDTO.getOverTime());
		}
		if (StringUtils.isNotBlank(requirementDTO.getBackgroundCheck())) {
			requirement.setBackgroundCheck(requirementDTO.getBackgroundCheck());
		}
		if (StringUtils.isNotBlank(requirementDTO.getGender())) {
			requirement.setGender(requirementDTO.getGender());
		}

		requirement.setStatus(Constants.TEAM_ACTIVE);
		// requirement status
		if (StringUtils.isNotBlank(requirementDTO.getSaveType())) {
			if (requirementDTO.getSaveType().equalsIgnoreCase("draft")) {
				requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.DRAFT);
			}
			if (requirementDTO.getSaveType().equalsIgnoreCase("save")) {
				requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
			}
			if (requirementDTO.getSaveType().equalsIgnoreCase("submitForApproval")) {
				// if the Requirement is in Open Stage
				if (null != requirement.getId()) {
					if ("OPEN".equalsIgnoreCase(requirement.getRequirementSubmissionStatus().getValue())) {
						requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
					} else {
						requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED);
					}
				} else {
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED);
				}
			}
		}
		// approval mechnism
		HashSet<RequirementApproverHierarchy> requirementApproverHierarchyList = new HashSet<RequirementApproverHierarchy>();
		// approval saving for sequential
		if (requirementDTO.getParallelApprover() != null && requirementDTO.getParallelApprover().size() > 0) {
			List<String> approvers = requirementDTO.getParallelApprover();
			removedApproversInRequirementForParallel(approvers, requirement);
			for (String approver : approvers) {
				RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
				// commented as it is not in use by David Raju
				// User approverUser =
				// userService.getUserById(Long.parseLong(approver));
				// if the Requirement is in Edit Mode
				if (requirement.getId() != null) {
					rah = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(
							requirement.getId(), Long.parseLong(approver));
					// In Edit Mode if We add New Approvers In The Requirement
					if (rah == null) {
						rah = new RequirementApproverHierarchy();
						User approverUserId = new User();
						approverUserId.setId(Long.parseLong(approver));
						rah.setApproverUserId(approverUserId);
						rah.setRequirementId(requirement);
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
						rah.setLevel(1);
						rah.setHierarchyType(HIERARCHY_TYPE.PARALLEL);
						rah.setCreatedDate(commonUtil.currentDate());
						rah.setCreatedBy(user.getUserCode());
						rah.setUpdatedBy(user.getUserCode());
						rah.setUpdatedDate(commonUtil.currentDate());
					} else {
						rah.setCreatedDate(commonUtil.currentDate());
						rah.setCreatedBy(user.getUserCode());
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
						rah.setUpdatedBy(user.getUserCode());
						rah.setUpdatedDate(commonUtil.currentDate());
					}
				} else {
					User approverUserId = new User();
					approverUserId.setId(Long.parseLong(approver));
					rah.setApproverUserId(approverUserId);
					rah.setRequirementId(requirement);
					rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
					rah.setLevel(1);
					rah.setHierarchyType(HIERARCHY_TYPE.PARALLEL);
					rah.setCreatedDate(commonUtil.currentDate());
					rah.setCreatedBy(user.getUserCode());
					rah.setUpdatedBy(user.getUserCode());
					rah.setUpdatedDate(commonUtil.currentDate());
				}
				requirementApproverHierarchyList.add(rah);
			}
		}
		// approval saving for parallel
		if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1())) {
			String approverNames = requirementDTO.getSequentialApprover1();
			if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1())) {
				User approverUser = userService.getUserById(Long.parseLong(approverNames));
				RequirementApproverHierarchy rah = new RequirementApproverHierarchy();

				// to remove the unselected approvers in Dropdown
				removedApproversInRequirementForSequential(approverUser, requirement, Constants.LEVEL1);
				if (requirement.getId() != null) {
					rah = requirementApproverHierarchyService
							.getRequirementApproverHierarchyByRequirementId(requirement.getId(), approverUser.getId());
					if (rah == null) {
						rah = new RequirementApproverHierarchy();
						User approverUserId = new User();
						approverUserId.setId(Long.parseLong(requirementDTO.getSequentialApprover1()));
						rah.setApproverUserId(approverUserId);
						rah.setRequirementId(requirement);
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
						rah.setLevel(1);
						rah.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
						rah.setCreatedDate(commonUtil.currentDate());
						rah.setCreatedBy(user.getUserCode());
						rah.setUpdatedBy(user.getUserCode());
						rah.setUpdatedDate(commonUtil.currentDate());

					} else {
						rah.setCreatedDate(commonUtil.currentDate());
						rah.setCreatedBy(user.getUserCode());
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
						rah.setUpdatedBy(user.getUserCode());
						rah.setUpdatedDate(commonUtil.currentDate());
					}
				} else {
					User approverUserId = new User();
					approverUserId.setId(Long.parseLong(requirementDTO.getSequentialApprover1()));
					rah.setApproverUserId(approverUserId);
					rah.setRequirementId(requirement);
					rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
					rah.setLevel(1);
					rah.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
					rah.setCreatedDate(commonUtil.currentDate());
					rah.setCreatedBy(user.getUserCode());
					rah.setUpdatedBy(user.getUserCode());
					rah.setUpdatedDate(commonUtil.currentDate());
				}
				requirementApproverHierarchyList.add(rah);
			}

			if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover2())) {
				String approverNames2 = requirementDTO.getSequentialApprover2();
				User approverUsersabc = userService.getUserById(Long.parseLong(approverNames2));
				RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
				// to remove the unselected approvers in Dropdown
				removedApproversInRequirementForSequential(approverUsersabc, requirement, Constants.LEVEL2);
				if (requirement.getId() != null) {
					rah = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(
							requirement.getId(), approverUsersabc.getId());
					if (rah == null) {
						rah = new RequirementApproverHierarchy();
						User approverUserId = new User();
						approverUserId.setId(Long.parseLong(requirementDTO.getSequentialApprover2()));
						rah.setApproverUserId(approverUserId);
						rah.setRequirementId(requirement);
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
						rah.setLevel(2);
						rah.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
						rah.setCreatedDate(commonUtil.currentDate());
						rah.setCreatedBy(user.getUserCode());
						rah.setUpdatedBy(user.getUserCode());
						rah.setUpdatedDate(commonUtil.currentDate());

					} else {
						rah.setCreatedDate(commonUtil.currentDate());
						rah.setCreatedBy(user.getUserCode());
						rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
						rah.setUpdatedBy(user.getUserCode());
						rah.setUpdatedDate(commonUtil.currentDate());
					}
				} else {
					User approverUserId = new User();
					approverUserId.setId(Long.parseLong(requirementDTO.getSequentialApprover2()));
					rah.setApproverUserId(approverUserId);
					rah.setRequirementId(requirement);
					rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
					rah.setLevel(2);
					rah.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
					rah.setCreatedDate(commonUtil.currentDate());
					rah.setCreatedBy(user.getUserCode());
					rah.setUpdatedBy(user.getUserCode());
					rah.setUpdatedDate(commonUtil.currentDate());

				}
				requirementApproverHierarchyList.add(rah);
			}
		}
		if (requirementDTO.getParallelApprover() != null
				|| (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1()))) {
			requirement.setApproverHierarchy(Constants.YES);
		} else {
			requirement.setApproverHierarchy(Constants.NO);
		}
		if (requirementDTO.getSaveType() != null && !requirementDTO.getSaveType().equalsIgnoreCase("draft")) {
			requirement.setRequirementApprover(requirementApproverHierarchyList);
		}
		if (StringUtils.isNotBlank(requirementDTO.getManager())) {
			User managerUserId = new User();
			managerUserId.setId(Long.parseLong(requirementDTO.getManager()));
			requirement.setManagerUserId(managerUserId);
		}
		if (requirementDTO.getPrimaryRecruiterUserId() > 0) {
			User primaryRecruiterUser = new User();
			primaryRecruiterUser.setId(requirementDTO.getPrimaryRecruiterUserId());
			requirement.setPrimaryRecruiterUserId(primaryRecruiterUser);
		} else {
			requirement.setPrimaryRecruiterUserId(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getPriority())) {
			requirement.setPriority(requirementDTO.getPriority());
		}
		// storing assigned team START (Venkat Kesav)
		List<RequirementAssignTo> oldAssigntoTeamList = null;
		if (requirement.getId() != null) {
			oldAssigntoTeamList = requirementAssignToService
					.getAssignedToMembersByRequirementIdAndOrgLocationId(requirement.getId(), orgLocations.getId());
		}
		HashSet<RequirementAssignTo> requirementAssignToList = new HashSet<RequirementAssignTo>();
		/*
		 * if (StringUtils.isNotBlank(requirementDTO.getAssignedToMaxCount())) { String
		 * assignedToMaxCount = requirementDTO.getAssignedToMaxCount();
		 */
		if (requirementDTO.getAssignedTeamId() != null) {
			for (Iterable<String> iterable : Iterables
					.partition(Splitter.on(",").split(requirementDTO.getAssignedTeamId()), 2)) {
				if (!iterable.toString().equalsIgnoreCase("[]")) {
					String userId = Joiner.on(",").join(iterable).split(",")[0];
					String teamId = Joiner.on(",").join(iterable).split(",")[1];

					RequirementAssignTo requirementAssignTo;
					User assignedUser = new User();
					assignedUser.setId(Long.parseLong(userId));
					if (requirement.getId() != null) {
						requirementAssignTo = requirementAssignToService
								.getRequirementAssignToByRequirementIdandAssignedUserId(requirement.getId(),
										assignedUser.getId());
						if (requirementAssignTo == null) {
							requirementAssignTo = new RequirementAssignTo();
						}
					} else {
						requirementAssignTo = new RequirementAssignTo();
					}
					if (StringUtils.isNotEmpty(teamId) && !teamId.equalsIgnoreCase("undefined") && !teamId.equalsIgnoreCase("null")) {
						Team assignedTeam = new Team();
						assignedTeam.setId(Long.parseLong(teamId));
						requirementAssignTo.setTeamId(assignedTeam);
					}

					requirementAssignTo.setAssignUserId(assignedUser);
					requirementAssignTo.setRequirementId(requirement);
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(assignedUser.getId(),
							orgLocations.getId());
					requirementAssignTo.setRoleId(userRole.getRoles());
					requirementAssignTo.setStatus(Constants.MEMBER_ACTIVE);
					requirementAssignTo.setCreatedDate(commonUtil.currentDate());
					requirementAssignTo.setCreatedBy(user.getUserCode());
					requirementAssignTo.setUpdatedBy(user.getUserCode());
					requirementAssignTo.setUpdatedDate(commonUtil.currentDate());
					requirementAssignToList.add(requirementAssignTo);
				}
			}
		}

		if (StringUtils.isNotBlank(requirementDTO.getRequirementId())) {

			List<String> savedUUIDList = requirementLocationsService
					.getRequirementLocationsUUIDByRequirementId(Long.parseLong(requirementDTO.getRequirementId()));
			List<String> UUIDList = new ArrayList<>();
			if (requirementDTO.getSavedLocationInfo() != null && !requirementDTO.getSavedLocationInfo().isEmpty()) {
				for (RequirementLocationsDTO savedLocationDTO : requirementDTO.getSavedLocationInfo()) {
					if (savedLocationDTO.getLocationUUID() != null && !savedLocationDTO.getLocationUUID().isEmpty()) {
						RequirementLocations reqLocation = requirementLocationsService
								.getRequirementLocationsByLocationUUID(savedLocationDTO.getLocationUUID());
						if (reqLocation != null) {
							reqLocation.setLocation(savedLocationDTO.getLocationName());

							if (savedLocationDTO.getCountry() != null && !savedLocationDTO.getCountry().isEmpty()) {
								reqLocation.setCountry(savedLocationDTO.getCountry());
							}

							if (savedLocationDTO.getState() != null && !savedLocationDTO.getState().isEmpty()) {
								reqLocation.setState(savedLocationDTO.getState());
							}

							if (savedLocationDTO.getCity() != null && !savedLocationDTO.getCity().isEmpty()) {
								reqLocation.setCity(savedLocationDTO.getCity());
							}

							if (savedLocationDTO.getZipcode() != null && !savedLocationDTO.getZipcode().isEmpty()) {
								reqLocation.setZipCode(savedLocationDTO.getZipcode());
							}

							if (savedLocationDTO.getLongitude() != null && !savedLocationDTO.getLongitude().isEmpty()) {
								reqLocation.setLongitude(savedLocationDTO.getLongitude());
							}

							if (savedLocationDTO.getLatitude() != null && !savedLocationDTO.getLatitude().isEmpty()) {
								reqLocation.setLatitude(savedLocationDTO.getLatitude());
							}
							requirementLocationsService.saveRequirementLocations(reqLocation);
						}
						UUIDList.add(savedLocationDTO.getLocationUUID());
					}
				}
			}

			if (UUIDList != null && !UUIDList.isEmpty()) {
				UUIDList.retainAll(savedUUIDList);
				savedUUIDList.removeAll(UUIDList);
			}

			if (savedUUIDList != null && !savedUUIDList.isEmpty()) {
				for (String UUID : savedUUIDList) {
					RequirementLocations reqLocation = requirementLocationsService
							.getRequirementLocationsByLocationUUID(UUID);
					requirementLocationsService.deleteRequirementLocations(reqLocation);
				}
			}

		}

		HashSet<RequirementLocations> requirementLocationsList = new HashSet<RequirementLocations>();
		if (requirementDTO.getLocationInfo() != null && !requirementDTO.getLocationInfo().isEmpty()) {
			for (RequirementLocationsDTO locationDTO : requirementDTO.getLocationInfo()) {
				if (StringUtils.isNotBlank(locationDTO.getLocationName())) {
					RequirementLocations requirementLocations = new RequirementLocations();
					requirementLocations.setRequirementId(requirement);
					requirementLocations.setLocation(locationDTO.getLocationName());

					if (locationDTO.getCountry() != null && !locationDTO.getCountry().isEmpty()) {
						requirementLocations.setCountry(locationDTO.getCountry());
					}

					if (locationDTO.getState() != null && !locationDTO.getState().isEmpty()) {
						requirementLocations.setState(locationDTO.getState());
					}

					if (locationDTO.getCity() != null && !locationDTO.getCity().isEmpty()) {
						requirementLocations.setCity(locationDTO.getCity());
					}

					if (locationDTO.getZipcode() != null && !locationDTO.getZipcode().isEmpty()) {
						requirementLocations.setZipCode(locationDTO.getZipcode());
					}

					if (locationDTO.getLongitude() != null && !locationDTO.getLongitude().isEmpty()) {
						requirementLocations.setLongitude(locationDTO.getLongitude());
					}

					if (locationDTO.getLatitude() != null && !locationDTO.getLatitude().isEmpty()) {
						requirementLocations.setLatitude(locationDTO.getLatitude());
					}

					if (locationDTO.getCountry() == null || locationDTO.getCountry().isEmpty()) {
						String[] jobLocation = locationDTO.getLocationName().split(",");
						if (jobLocation.length > 2) {
							requirementLocations.setCity(jobLocation[0]);
							requirementLocations.setState(jobLocation[1]);
							requirementLocations.setCountry(jobLocation[2]);
						} else if (jobLocation.length == 2) {
							requirementLocations.setCity(jobLocation[0]);
							requirementLocations.setState(jobLocation[1]);
						} else if (jobLocation.length == 1) {
							requirementLocations.setCity(jobLocation[0]);
						}
					}

					requirementLocations.setStatus(Constants.RECORD_ACTIVE);
					requirementLocations.setLocationUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					requirementLocationsList.add(requirementLocations);
				}
			}
		}

		requirement.setRequirementLocations(requirementLocationsList);

		/*
		 * int teamCount = 0; ArrayList<String> assignedTeamId = new
		 * ArrayList<String>(); if
		 * (StringUtils.isNotBlank(requirementDTO.getAssignedTeamId())) { String[]
		 * assignedTeamAndRoleIds = requirementDTO.getAssignedTeamId().split(","); for
		 * (String assignedTeamOrRoleId : assignedTeamAndRoleIds) { if
		 * (!assignedTeamOrRoleId.contains("role") &&
		 * !assignedTeamOrRoleId.contains("All")) {
		 * assignedTeamId.add(assignedTeamOrRoleId); teamCount++; } } } if
		 * (requirementDTO.getAssignUsers() != null &&
		 * !requirementDTO.getAssignUsers().isEmpty()) { for (String assignUserId :
		 * requirementDTO.getAssignUsers()) { if (StringUtils.isNotEmpty(assignUserId))
		 * { RequirementAssignTo requirementAssignTo; // User assUser = //
		 * userService.getUserById(Long.parseLong(jsonObj.getString("assignUserId"+c)));
		 * String[] selectedUser = assignUserId.split(","); // ### to skip duplicate
		 * user IDs START>>>>> boolean userExists = false; for (RequirementAssignTo rat
		 * : requirementAssignToList) { userExists =
		 * rat.getAssignUserId().getId().equals(Long.parseLong(selectedUser[0])); if
		 * (userExists) { break; } } if (userExists) { continue; } // to skip duplicate
		 * user IDs END ###### User assignedUser = new User();
		 * assignedUser.setId(Long.parseLong(selectedUser[0])); if (requirement.getId()
		 * != null) { requirementAssignTo = requirementAssignToService.
		 * getRequirementAssignToByRequirementIdandAssignedUserId(requirement.getId(),
		 * assignedUser.getId()); if (requirementAssignTo == null) { requirementAssignTo
		 * = new RequirementAssignTo(); } } else { requirementAssignTo = new
		 * RequirementAssignTo(); } Team assignedTeam = null; if (null !=
		 * selectedUser[1] && !selectedUser[1].equalsIgnoreCase("") &&
		 * !selectedUser[1].equalsIgnoreCase("undefined")) { assignedTeam = new Team();
		 * assignedTeam.setId(Long.parseLong(selectedUser[1])); }
		 * requirementAssignTo.setTeamId(assignedTeam);
		 * requirementAssignTo.setAssignUserId(assignedUser);
		 * requirementAssignTo.setRequirementId(requirement); UserRole userRole =
		 * userRoleService.getUserRoleByUserIdAndLocationId(assignedUser.getId(),
		 * orgLocations.getId()); requirementAssignTo.setRoleId(userRole.getRoles());
		 * requirementAssignTo.setCreatedDate(commonUtil.currentDate());
		 * requirementAssignTo.setCreatedBy(user.getUserCode());
		 * requirementAssignTo.setUpdatedBy(user.getUserCode());
		 * requirementAssignTo.setUpdatedDate(commonUtil.currentDate());
		 * requirementAssignToList.add(requirementAssignTo); } } }
		 */
		/* } */
		if (requirementDTO.getSaveType() != null && !requirementDTO.getSaveType().equalsIgnoreCase("draft")) {
			requirement.setRequirementAssignTo(requirementAssignToList);
		}
		// storing assigned team END
		/*
		 * System Generated Id For Job
		 */

		Requirement savedRequirement = requirementDao.storeRequirement(requirement);

		List<RequirementLocations> requirementLocationList = requirementLocationsService
				.getRequirementLocationsListByRequirementId(savedRequirement.getId());
		if (requirementLocationList != null && !requirementLocationList.isEmpty()) {
			for (RequirementLocations requirementLocation : requirementLocationList) {
				location.append(requirementLocation.getLocation()).append("<br>");
			}
		}

		if (requirementDTO.getCustomFormData() != null) {
			for (CustomFormFieldDTO customFields : requirementDTO.getCustomFormData()) {
				if (customFields != null && customFields.getCustomFormFieldId() > 0) {
					JobCustomFieldData jobCustomFields = null;
					if (customFields.getEntityId() != null && customFields.getEntityId() > 0) {
						jobCustomFields = jobCustomFieldDataService
								.getJobCustomFieldDataById(customFields.getEntityId());
					} else {
						jobCustomFields = new JobCustomFieldData();
					}
					jobCustomFields.setRequirement(savedRequirement);
					OrglocationFormFields orgFormField = orglocationFormFieldsService
							.getOrglocationFormFieldsById(customFields.getCustomFormFieldId());
					jobCustomFields.setOrglocationsFormFields(orgFormField);
					jobCustomFields.setCustomFormFieldData(customFields.getCustomFormFieldData());
					jobCustomFieldDataService.savejobCustomFieldData(jobCustomFields);
				}

			}
		}

		if (!savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())) {
			List<Requirement> parentrequirement = getAllReleasedRequirementsByParentId(savedRequirement.getId());
			if (null != parentrequirement && !parentrequirement.isEmpty()) {
				for (Requirement parentrequirementList : parentrequirement) {
					releaseUpdatedMailScenario(user, savedRequirement, parentrequirementList.getSpocId(), orgLocations);
				}
			}
		}
		// for URL redirections from mail
		String redirectURL = "/requirementSummary-params-requirementUUID="+savedRequirement.getRequirementUUID();
		Long redirectOrgLocationId = orgLocations.getId();
		
		// store recommended customizeScore
		RecommendedCustomizeScore recommendedCustomizeScore = recommendedCustomizeScoreService
				.saveCustomizeScore(requirement);
		if (!requirement.getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.DRAFT)) {			
			 								
			// mailRequirementtoManager(user,user,savedRequirement,location,orgLocations);
			// send mail to sequential approver1
			if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1())) {
				String approverNames = requirementDTO.getSequentialApprover1();
				User approverUser = userService.getUserById(Long.parseLong(approverNames));
				if (!approverUser.getId().equals(user.getId())) {
					
					approverEmailScenario(user, approverUser, savedRequirement, location, orgLocations);
				}
			}
			// send mail to parallel approver
			if (requirementDTO.getParallelApprover() != null && requirementDTO.getParallelApprover().size() > 0) {
				List<String> approvers = requirementDTO.getParallelApprover();
				for (String approver : approvers) {
					User approverUser = userService.getUserById(Long.parseLong(approver));
					if (!approverUser.getId().equals(user.getId())) {
						approverEmailScenario(user, approverUser, savedRequirement, location, orgLocations);
					}
				}
			}
			/* shivayogi Kadagad/rakshith mahabal */
			// this logic is for assigned to team members when new team user is
			// added for requirement-edit and saved.
			if (null != savedRequirement.getId() && requirementAssignToList.size() != 0
					&& !savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())) {
				for (RequirementAssignTo newList : requirementAssignToList) {
					boolean found = false;
					if (null != oldAssigntoTeamList && oldAssigntoTeamList.size() != 0) {
						for (RequirementAssignTo oldList : oldAssigntoTeamList) {
							if ((newList.getAssignUserId().getId().equals(oldList.getAssignUserId().getId()))) {
								found = true;
								break;
							}
						}
					}
					if (!found) {
						List<RequirementAssignTo> newlyAddedList = new ArrayList<RequirementAssignTo>();
						newlyAddedList.add(newList);
						for (RequirementAssignTo newly : newlyAddedList) {
							User newlyAddedUser = newly.getAssignUserId();
							Set<RequirementAssignTo> updatedRequirementAssignToList = savedRequirement
									.getRequirementAssignTo();
							if (updatedRequirementAssignToList != null
									&& !user.getId().equals(newlyAddedUser.getId())) {
								AssignToTeamMembersEmail(user, newlyAddedUser, savedRequirement, location,
										orgLocations,redirectURL,redirectOrgLocationId);
							}
						}
					}
				}
			}
			// send mail to Assigned Team
			// condition : if login user as team member then mail not send
			// this logic will execute when no approvers are selected for
			// requirement.
			if (!savedRequirement.getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED)) {
				// Set<RequirementAssignTo> updatedRequirementAssignToList =
				// savedRequirement.getRequirementAssignTo();
				Set<RequirementAssignTo> teamMembers = getRequirementAssignedToTeamByRequirementId(
						savedRequirement.getId());
				Set<RequirementAssignTo> updatedRequirementAssignToList = teamMembers;
				if (updatedRequirementAssignToList != null) {
					for (RequirementAssignTo requirementAssignTo : updatedRequirementAssignToList) {
						if (savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())) {
							if (requirementAssignTo.getAssignUserId() != null
									&& !user.getId().equals(requirementAssignTo.getAssignUserId().getId())) {
								AssignToTeamMembersEmail(user, requirementAssignTo.getAssignUserId(), savedRequirement,
										location, orgLocations,redirectURL,redirectOrgLocationId);
							}
						}
					}
				}
			}
			// send mail to modified Assigned Team
			// condition : if login user as team member then mail should not
			// send
			if (!savedRequirement.getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED)) {
				Set<RequirementAssignTo> updatedRequirementAssignToList = savedRequirement.getRequirementAssignTo();
				if (updatedRequirementAssignToList != null) {
					for (RequirementAssignTo requirementAssignTo : updatedRequirementAssignToList) {
						if (!savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())) {
							if (requirementAssignTo.getAssignUserId() != null
									&& !user.getId().equals(requirementAssignTo.getAssignUserId().getId())) {
								updatedRequirementMailToAssignedMembers(user, requirementAssignTo.getAssignUserId(),
										savedRequirement, location, orgLocations,redirectURL,redirectOrgLocationId);
							}
						}
					}
				}
			}
			// send mail to manger
			// condition : login user as manager then mail shoul not forwared
			 
				
			if (savedRequirement.getManagerUserId() != null
					&& !user.getId().equals(savedRequirement.getManagerUserId().getId())
					&& savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())) {
				mailRequirementtoManager(user, user, savedRequirement, location, orgLocations,redirectURL,redirectOrgLocationId);
			} else if (!savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())
					&& !user.getId().equals(savedRequirement.getManagerUserId().getId())) {
				updatedRequirementMailToManager(user, user, savedRequirement, location, orgLocations,redirectURL,redirectOrgLocationId);
			}
		}
		requirementElkUtil.saveElkRequirement(savedRequirement, requirementDTO);
		
		if (requirementDTO.isJobFair()) {
			if (requirementDTO.getJobFairDates() != null && requirementDTO.getJobFairDates().length > 0) {
				List<Long> deleteIdsList = requirementDao.getJobFairRequirementsIdListByRequirementId(savedRequirement.getId());
				if(deleteIdsList == null) {
					deleteIdsList = new LinkedList<>();
				}
				Map<Long, String> reqMap = new HashMap<>();
				for (String dateString : requirementDTO.getJobFairDates()) {
					if (dateString.contains(":")) {
						reqMap.put(Long.parseLong(dateString.split(":")[0]), "");
					} else if (dateString.contains(",") && dateString.split(",").length > 1) {
						for (String id : dateString.split(",")) {
							reqMap.put(Long.parseLong(id), "");
						}
					} else {
						reqMap.put(Long.parseLong(dateString), "");
					}
				}
				for (Long id : reqMap.keySet()) {
					String dates = "";
					for (String dateString : requirementDTO.getJobFairDates()) {
						if (dateString.contains(":") && (Long.parseLong(dateString.split(":")[0]) == id) && dateString.split(":").length > 1) {
							dates += (dates.isEmpty() ? "" : ",") + dateString.split(":")[1];
						}
					}
					reqMap.put(id, dates);
				}
				for (Long id : reqMap.keySet()) {
					/*System.out.println("Map ID : " + id + " values : " + reqMap.get(id));*/
					JobFair jobFair = requirementDao.getJobFair(id);
					if (jobFair != null) {
						boolean addFlag = false;
						JobFairRequirements jobFairRequirements = null;
						long jobFairRequirementId = requirementDao.getJobFairRequirementsIdByJobFairIdAndRequirementId(jobFair.getId(),
								savedRequirement.getId());
						jobFairRequirements = requirementDao.getJobFairRequirementsById(jobFairRequirementId);
						if (jobFairRequirements == null) {
							addFlag = true;
							jobFairRequirements = new JobFairRequirements();
							jobFairRequirements.setJobFair(jobFair);
							jobFairRequirements.setRequirement(savedRequirement);
						}

						jobFairRequirements.setFairDates(reqMap.get(id));
						if (addFlag) {
							jobFair.getJobFairRequirementList().add(jobFairRequirements);
							requirementDao.saveJobFair(jobFair);
						} else {
							requirementDao.saveJobFairRequirements(jobFairRequirements);
						}
						deleteIdsList.remove(jobFairRequirementId);
					}
				}
				if(!deleteIdsList.isEmpty()) {
					for(Long jobFairRequirementId : deleteIdsList) {
						requirementDao.deleteJobFairRequirement(jobFairRequirementId);
					}
				}
			}
		} else {
			List<Long> jobFairRequirementIdList = requirementDao.getJobFairRequirementsIdListByRequirementId(savedRequirement.getId());
			if(jobFairRequirementIdList!= null) {
				for(Long jobFairRequirementId : jobFairRequirementIdList) {
					requirementDao.deleteJobFairRequirement(jobFairRequirementId);
				}
			}
		}
		return savedRequirement;
	}

	private Set<RequirementAssignTo> getRequirementAssignedToTeamByRequirementId(Long id) {
		return requirementDao.getRequirementAssignedToTeamByRequirementId(id);
	}

	private boolean isNullOrEmpty(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	private void updatedRequirementMailToAssignedMembers(User loginUser, User assUser, Requirement requirement,
			StringBuilder location, OrgLocations loginOrgLocation,String redirectURL,Long redirectOrgLocationId) throws Exception {
		assUser = userService.getUserById(assUser.getId());
		UserRole loginUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(),
				loginOrgLocation.getId());
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(assUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		// StringBuilder msgBuild = new StringBuilder();
		// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append("
		// ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			StringBuilder reqsb = new StringBuilder();
			globalVarsMap.put("RequirementTitle",
					reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		} else {
			globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		}
		if (location != null) {
			globalVarsMap.put("RequirementLocation", location.toString());
		} else {
			globalVarsMap.put("RequirementLocation", "--");
		}
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("RequirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("RequirementRate", "--");
		}

		if (requirement.getSpocId() != null) {
			globalVarsMap.put("clientContactname", requirement.getSpocId().getClientLocationId().getOrgLocationName());
		}

		/*
		 * if (assUser.getFirstName() != null && assUser.getLastName() != null &&
		 * !"".equalsIgnoreCase(assUser.getFirstName()) &&
		 * !"".equalsIgnoreCase(assUser.getLastName())) { StringBuilder userBuild = new
		 * StringBuilder(); globalVarsMap.put("clientContactname",
		 * userBuild.append(assUser.getFirstName()).append("").append(assUser.
		 * getLastName()).toString()); } else { globalVarsMap.put("clientContactname",
		 * assUser.getFirstName()); }
		 */

		if ((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()))
				&& (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp()))) {
			StringBuilder expBuild = new StringBuilder();
			globalVarsMap.put("Experience",
					expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		} else if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
			globalVarsMap.put("Experience", requirement.getMinExp());
		} else if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			globalVarsMap.put("Experience", requirement.getMaxExp());
		} else {
			globalVarsMap.put("Experience", "--");
		}
		if (requirement.getRequirementSubmissionStatus() != null) {
			globalVarsMap.put("status", requirement.getRequirementSubmissionStatus().getValue());
		} else {
			globalVarsMap.put("status", "--");
		}
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Job</div></a>")
				.toString());
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
		if (loginOrgLocation.getOrgLocationName() != null
				&& !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())) {
			globalVarsMap.put("LoginOrgName", loginOrgLocation.getOrgLocationName());
		} else {
			globalVarsMap.put("LoginOrgName", "--");
		}
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		// using same migration which has been already used in
		// updatedRequirementMailToManager method
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.UPDATED_REQUIREMENT_NOTIFICATION_TO_MANAGER, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
				Constants.UPDATED_REQUIREMENT_EMAIL_TO_MANAGER, new ArrayList<EmailAttachment>());
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, assUser, loginOrgLocation, notificationScenario,
				new HashMap<String, String>());
	}

	private void updatedRequirementMailToManager(User loginUser, User managerUser, Requirement requirement,
			StringBuilder location, OrgLocations loginOrgLocation,String redirectURL,Long redirectOrgLocationId) throws Exception {
		if (requirement.getManagerUserId() != null) {
			managerUser = userService.getUserById(requirement.getManagerUserId().getId());
		}
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(managerUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		// StringBuilder msgBuild = new StringBuilder();
		// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append("
		// ").append(loginUser.getLastName()).toString());
		// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			StringBuilder reqsb = new StringBuilder();
			globalVarsMap.put("RequirementTitle",
					reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		} else {
			globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		}
		
		
		
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String reqLocation = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							reqLocation = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							reqLocation = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			globalVarsMap.put("RequirementLocation", reqLocation);
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("RequirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("RequirementRate", "--");
		}
		if (requirement.getSpocId() != null) {
			globalVarsMap.put("clientContactname", requirement.getSpocId().getClientLocationId().getOrgLocationName());
		}
		globalVarsMap.put("managerName", managerUser.getFullName());
		/*
		 * if (managerUser.getFirstName() != null && managerUser.getLastName() != null
		 * && !"".equalsIgnoreCase(managerUser.getFirstName()) &&
		 * !"".equalsIgnoreCase(managerUser.getLastName())) { StringBuilder userBuild =
		 * new StringBuilder(); globalVarsMap.put("clientContactname",
		 * userBuild.append(managerUser.getFirstName()).append("").append(managerUser.
		 * getLastName()).toString()); } else { globalVarsMap.put("clientContactname",
		 * managerUser.getFirstName()); }
		 */
		if ((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()))
				&& (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp()))) {
			StringBuilder expBuild = new StringBuilder();
			globalVarsMap.put("Experience",
					expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		} else if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
			globalVarsMap.put("Experience", requirement.getMinExp());
		} else if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			globalVarsMap.put("Experience", requirement.getMaxExp());
		} else {
			globalVarsMap.put("Experience", "--");
		}
		if (requirement.getRequirementSubmissionStatus() != null) {
			globalVarsMap.put("status", requirement.getRequirementSubmissionStatus().getValue());
		} else {
			globalVarsMap.put("status", "--");
		}
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Job</div></a>")
				.toString());
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
		if (loginOrgLocation.getOrgLocationName() != null
				&& !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())) {
			globalVarsMap.put("LoginOrgName", loginOrgLocation.getOrgLocationName());
		} else {
			globalVarsMap.put("LoginOrgName", "--");
		}
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.UPDATED_REQUIREMENT_NOTIFICATION_TO_MANAGER, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
				Constants.UPDATED_REQUIREMENT_EMAIL_TO_MANAGER, null);
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, loginOrgLocation, notificationScenario,
				new HashMap<String, String>());
	}

	private void releaseUpdatedMailScenario(User loginUser, Requirement requirement, OrgSpoc spoc,
			OrgLocations loginOrgLocation) throws Exception {
		User vendorUser = userService.getUserById(spoc.getVendorUser().getId());
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		if (loginUser.getLastName() != null) {
			emailMessage.setLastName(loginUser.getLastName());
		}
		emailMessage.setToEmail(vendorUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		
		//URL for page redirection
		String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
		Long redirectOrgLocationId = spoc.getVendorLocationId().getId();
				
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		} else {
			globalVarsMap.put("RequirementTitle", "--");
		}

		List<String> requirementLocationsList = new ArrayList<>();
		List<RequirementLocations> locationsList;
		String location = "";
		StringBuilder requirementBuilder = new StringBuilder();
		try {
			locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
			if (locationsList != null && !locationsList.isEmpty()) {
				for (RequirementLocations locationsJob : locationsList) {
					requirementLocationsList.add(locationsJob.getLocation());
					if (locationsJob.getLocation() != null && locationsJob.getLocation() != null) {
						location = requirementBuilder.append(locationsJob.getLocation()).toString();
					} else {
						location = "--";
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		globalVarsMap.put("RequirementLocation", location);
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("RequirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("RequirementRate", "--");
		}
		if (vendorUser.getFirstName() != null && vendorUser.getLastName() != null
				&& !"".equalsIgnoreCase(vendorUser.getFirstName()) && !"".equalsIgnoreCase(vendorUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("clientContactname",
					userBuild.append(vendorUser.getFirstName()).append("").append(vendorUser.getLastName()).toString());
		} else {
			globalVarsMap.put("clientContactname", vendorUser.getFirstName());
		}
		if ((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()))
				&& (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp()))) {
			StringBuilder expBuild = new StringBuilder();
			globalVarsMap.put("Experience",
					expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		} else if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
			globalVarsMap.put("Experience", requirement.getMinExp());
		} else if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			globalVarsMap.put("Experience", requirement.getMaxExp());
		} else {
			globalVarsMap.put("Experience", "--");
		}
		if (requirement.getRequirementSubmissionStatus() != null) {
			globalVarsMap.put("status", requirement.getRequirementSubmissionStatus().getValue());
		} else {
			globalVarsMap.put("status", "--");
		}		
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append
				("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Updated Job</div></a>")
				.toString());		
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
		if (loginOrgLocation.getOrgLocationName() != null
				&& !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())) {
			globalVarsMap.put("LoginOrgName", loginOrgLocation.getOrgLocationName());
		} else {
			globalVarsMap.put("LoginOrgName", "--");
		}
		if (requirement.getManagerUserId() != null) {
			User managerUser = userService.getUserById(requirement.getManagerUserId().getId());
			globalVarsMap.put("requirementManagerName", managerUser.getFullName());
		} else {
			globalVarsMap.put("requirementManagerName", "--");
		}
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.UPDATEDREQUIREMENTNOTIFICATIONTOVENDOR, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// OrgLocations vendorOrgLoc =
		// requirement.getSpocId().getVendorLocationId();
		// construct HTML template using required data
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
				Constants.UPDATEDREQUIREMENTNOTIFICATIONTOVENDOR, new ArrayList<EmailAttachment>());
		String actionLink = "";
		// emailScenerioUtil.sendNotification(actionLink, loginUser, vendorUser,
		// vendorOrgLoc, notificationScenario);
	}

	/*
	 * private void saveElkRequirement(Requirement requirement, JSONObject jsonObj)
	 * throws Exception {
	 * 
	 * RequirementIndex requrementIndex =
	 * requirementHelper.populateRequirementIndex(requirement,jsonObj);
	 * sourceleadELKBaseIndexer.saveOrUpdateElkDocument(requirementIndex,
	 * Constants.ELK_REQUIREMENT_INDEX,
	 * Constants.ELK_REQUIREMENT_TYPE,requirement.getId().toString()); }
	 */
	@Override
	public Requirement addRequirement(Requirement requirement) throws Exception {
		Requirement savedRequirement = requirementDao.storeRequirement(requirement);
		RequirementDTO requirementDTO = commonUtil.getRequirementDTO(savedRequirement);
		requirementElkUtil.saveElkRequirement(savedRequirement, requirementDTO);
		return savedRequirement;
	}

	/**
	 * @author sarath kumar
	 * @since sep 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get RequirementList by orgLocationId .
	 * 
	 * @param orgLocationId
	 *            This is the first paramter to getRequirementListByOrgLocationID
	 *            method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */
	@Override
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId, String status, String clients,
			User user, String userRoleName) throws Exception {
		return requirementDao.getRequirementListByOrgLocationID(orgLocationId, status, clients, user, userRoleName);
	}

	/**
	 * @author sarath kumar
	 * @since sep 13 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get Requirement by requirementId .
	 * 
	 * @param requirementId
	 *            This is the first paramter to getRequirementByRequirementId method
	 * 
	 * @return Requirement
	 * 
	 * @throws NullPointer
	 */
	@Override
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception {
		Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
		return requirement;
	}
	
	@Override
	public Requirement getRequirementByRequirementIdAndSearchValue(Long requirementId,String searchValue) throws Exception {
		return requirementDao.getRequirementByRequirementIdAndSearchValue(requirementId,searchValue);
	}

	/**
	 * @author Venkat Kesav
	 * @since oct 21 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get the Requirement is released for contact
	 *          or not .
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

	@Override
	public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId)
			throws Exception {
		return requirementDao.getRequirementListByParentRequirementIdAndSpocId(requirementId, spocId);
	}

	/**
	 * @author prasad k
	 * @since sep 14 2016
	 * @version 0.0.1
	 * 
	 *          This method is used to get template status by orgLocationId .
	 * 
	 * @param requirementId
	 *            This is the first paramter to
	 *            getJobTemplateStatusByJobTitleAndRequirementId method
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
	public List<RequirementAssignTo> getUserAssignJobCount(Long userId, Long orgLocationId) throws Exception {
		return requirementDao.getUserAssignJobCount(userId, orgLocationId);
	}

	@Override
	public void approverEmailScenario(User loginUser, User approverUser, Requirement requirement,
			StringBuilder location, OrgLocations loginOrgLocation) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(approverUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			StringBuilder sb = new StringBuilder();
			if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
				globalVarsMap.put("requirementName",
						sb.append(requirement.getJobTitle()).append("-").append(requirement.getJobId()).toString());
			} else {
				globalVarsMap.put("requirementName", requirement.getJobTitle());
			}
		} else {
			globalVarsMap.put("requirementName", "--");
		}
		List<String> requirementLocationsList = new ArrayList<>();
		List<RequirementLocations> locationsList;
		String Reqlocation = "";
		StringBuilder requirementBuilder = new StringBuilder();
		try {
			locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
			if (locationsList != null && !locationsList.isEmpty()) {
				for (RequirementLocations locationsJob : locationsList) {
					requirementLocationsList.add(locationsJob.getLocation());
					if (locationsJob.getLocation() != null && locationsJob.getLocation() != null) {
						Reqlocation = requirementBuilder.append(locationsJob.getLocation()).toString();
					} else {
						Reqlocation = "--";
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (location != null) {
			globalVarsMap.put("requirementLocation", location.toString());
		} else {
			globalVarsMap.put("requirementLocation", "--");
		}
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("requirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("requirementRate", "--");
		}
		if (requirement.getClientId() != null) {
			OrgRelations orgRelations = orgRelationsService
					.getRespondingPartyDetailsById(requirement.getClientId().getId());
			if (orgRelations != null) {
				globalVarsMap.put("client", orgRelations.getResponderLocationId().getOrgLocationName());
			}
		} else {
			globalVarsMap.put("client", "--");
		}
		if (approverUser.getLastName() != null && !"".equalsIgnoreCase(approverUser.getLastName())) {
			StringBuilder approverUsersb = new StringBuilder();
			globalVarsMap.put("approverName", approverUsersb.append(approverUser.getFirstName()).append(" ")
					.append(approverUser.getLastName()).toString());
		} else {
			globalVarsMap.put("approverName", approverUser.getFirstName());
		}
		if (emailMessage.getLastName() != null && !"".equalsIgnoreCase(emailMessage.getLastName())) {
			globalVarsMap.put("lastName", emailMessage.getLastName());
		} else {
			globalVarsMap.put("lastName", "--");
		}
		if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
		} else {
			globalVarsMap.put("jobInitiatorName", "--");
		}
		if (approverUser.getEmail() != null && !"".equalsIgnoreCase(approverUser.getEmail())) {
			globalVarsMap.put("userEmailId", approverUser.getEmail());
		} else {
			globalVarsMap.put("userEmailId", "--");
		}
		
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());
		if (loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("loginUserName",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("loginUserName", loginUser.getFirstName());
		}
		if (loginOrgLocation.getOrgLocationName() != null
				&& !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())) {
			globalVarsMap.put("role", loginOrgLocation.getOrgLocationName());
		} else {
			globalVarsMap.put("role", "--");
		}
		if (requirement.getFileName() != null && !requirement.getFileName().isEmpty()) {
			StringBuilder attachmentBuild = new StringBuilder();
			globalVarsMap.put("attachment", attachmentBuild.append("<a href='").append(atsUrl).append("/")
					.append("downloadRequirementAttachment?requirementId=").append(requirement.getId())
					.append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment<br/></div></a>")
					.toString());
		} else {
			StringBuilder attachmentBuild = new StringBuilder();
			globalVarsMap.put(" ", attachmentBuild.append("<a href='").append(sourceleadUrl).append("/")
					.append("downloadRequirementAttachment?requirementId=").append(requirement.getId())
					.append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment<br/></div></a>")
					.toString());
		}
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.REQUIREMENT_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		StringBuilder actionLinkStringBuilder = new StringBuilder();
		String actionLink = actionLinkStringBuilder.append(atsUrl).append("/requirementSummary?requirementUUID=")
				.append(requirement.getRequirementUUID()).toString();
		// String actionLink=
		// sourceleadUrl"/requirementSummary?requirementUUID="+requirement.getRequirementUUID()+"";
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendNotification(actionLink, loginUser, approverUser, loginOrgLocation, notificationScenario,
				new HashMap<String, String>());
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
				Constants.REQUIREMENT_EMAIL_NOTIFICATION, new ArrayList<EmailAttachment>());
	}

	@Override
	public void approverTwoEmailScenario(User loginUser, User approverUser, Requirement requirement,
			StringBuilder location, OrgLocations orgLocations) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(approverUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		UserRole loginUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(),
				orgLocations.getId());
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("requirementName", requirement.getJobTitle());

		List<String> requirementLocationsList = new ArrayList<>();
		List<RequirementLocations> locationsList;
		String Reqlocation = "";
		StringBuilder requirementBuilder = new StringBuilder();
		try {
			locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
			if (locationsList != null && !locationsList.isEmpty()) {
				for (RequirementLocations locationsJob : locationsList) {
					requirementLocationsList.add(locationsJob.getLocation());
					if (locationsJob.getLocation() != null && locationsJob.getLocation() != null) {
						Reqlocation = requirementBuilder.append(locationsJob.getLocation()).toString();
					} else {
						Reqlocation = "--";
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Reqlocation != null) {
			globalVarsMap.put("requirementLocation", Reqlocation);
		} else {
			globalVarsMap.put("requirementLocation", "--");
		}
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("requirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("requirementRate", "--");
		}
		if (requirement.getClientId() != null) {
			globalVarsMap.put("client", requirement.getClientId().getResponderLocationId().getOrgLocationName());
		} else {
			globalVarsMap.put("client", "--");
		}
		if (approverUser.getFirstName() != null && !"".equalsIgnoreCase(approverUser.getFirstName())) {
			globalVarsMap.put("approverName", approverUser.getFirstName());
		} else {
			globalVarsMap.put("approverName", "--");
		}
		if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
		} else {
			globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
		}
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());
		StringBuilder userBuild = new StringBuilder();
		globalVarsMap.put("loginUserName",
				userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		globalVarsMap.put("role", loginUserRole.getUserType());
		globalVarsMap.put("approverOne", loginUser.getFirstName());
		StringBuilder attachBuild = new StringBuilder();
		globalVarsMap.put("attachment", attachBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Attachment</div></a>")
				.toString());
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations,
				Constants.REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION, new ArrayList<EmailAttachment>());
		StringBuilder notificationLink = new StringBuilder();
		String actionLink = notificationLink.append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();

		emailScenerioUtil.sendNotification(actionLink, loginUser, approverUser, orgLocations, notificationScenario,
				new HashMap<String, String>());
	}

	@SuppressWarnings("unused")
	@Override
	public void acceptEmailScenario(User loginUser, User intiatorUser, Requirement requirement,
			StringBuilder requirementLocation, OrgLocations orgLocations) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(intiatorUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		//Url for redirection
			String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
			Long redirectOrgLocationId = orgLocations.getId();
		
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		UserRole loginUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(),
				orgLocations.getId());
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			StringBuilder userBuild1 = new StringBuilder();
			globalVarsMap.put("requirementId-Name",
					userBuild1.append(requirement.getJobTitle()).append(" ").append(requirement.getJobId()).toString());
		} else {
			globalVarsMap.put("requirementId-Name", requirement.getJobTitle());
		}
		
		
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String location = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							location = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							location = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			globalVarsMap.put("requirementLocation", location);
		if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("approverName", loginUser.getFirstName());
		} else {
			globalVarsMap.put("approverName", "--");
		}
		if (intiatorUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("jobInitiatorName", intiatorUser.getFirstName());
		} else {
			globalVarsMap.put("jobInitiatorName", "--");
		}
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("requirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("requirementRate", "--");
		}
		if (requirement.getClientId() != null) {
			globalVarsMap.put("client", requirement.getClientId().getResponderLocationId().getOrgLocationName());
		} else {
			globalVarsMap.put("client", "--");
		}		
		
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("loginURL", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Approved Job</div></a>")
				.toString());
			
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("loginUserName",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("loginUserName", loginUser.getFirstName());
		}
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("requirementName", requirement.getJobTitle());
		} else {
			globalVarsMap.put("requirementName", "--");
		}
		globalVarsMap.put("role", loginUserRole.getUserType());
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.APPROVED_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations,
				Constants.APPROVED_EMAIL_NOTIFICATION, new ArrayList<EmailAttachment>());
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, intiatorUser, orgLocations, notificationScenario,
				new HashMap<String, String>());
	}

	@SuppressWarnings("unchecked")
	/**
	 * calculating the score of Resume per candidate
	 * 
	 */
	@Override
	public List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement)
			throws Exception {
		float expPercentage = 0; // experience = 20%
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0; // rate 20 %
		float skillPercentage = 0; // skill = 50%
		float percentPerSkill = 0;
		List<Candidate> CandidateListcopy = new ArrayList<>();
		String reqSkills = "";
		if (requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())) {
			reqSkills = requirement.getSkills();
			String[] reqMaxSkills = reqSkills.split(",");
			percentPerSkill = 50 / (float) reqMaxSkills.length;
		}
		int reqMinExp = 0;
		int reqMaxExp = 0;
		float percentPerMonthExp = 0;
		if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())
				&& requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			reqMinExp = Integer.parseInt(requirement.getMinExp());
			reqMaxExp = Integer.parseInt(requirement.getMaxExp());
			int reqExp = reqMaxExp * 12;
			percentPerMonthExp = 20 / (float) reqExp;
		}
		String reqCity = "";
		if (requirement.getCity() != null && !"".equalsIgnoreCase(requirement.getCity())) {
			reqCity = requirement.getCity();
		}
		float reqPerMonthSal = 0;
		if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			int reqRate = Integer.parseInt(requirement.getRate());
			String reqRateType = requirement.getRateType();
			if ("Hourly".equalsIgnoreCase(reqRateType)) {
				int perDaySal = reqRate * 24;
				reqPerMonthSal = perDaySal * 30;
			} else if ("Weekly".equalsIgnoreCase(reqRateType)) {
				int perDaySal = reqRate / 7;
				reqPerMonthSal = perDaySal * 30;
			} else if ("Bi-weekly".equalsIgnoreCase(reqRateType)) {
				int perDaySal = reqRate / 14;
				reqPerMonthSal = perDaySal * 30;
			} else if ("Semi-Monthly".equalsIgnoreCase(reqRateType)) {
				int perDaySal = reqRate / 15;
				reqPerMonthSal = perDaySal * 30;
			} else if ("Monthly".equalsIgnoreCase(reqRateType)) {
				reqPerMonthSal = reqRate;
			} else if ("Bi-Monthly".equalsIgnoreCase(reqRateType)) {
				int perDaySal = reqRate / 60;
				reqPerMonthSal = perDaySal * 30;
			} else {
				reqPerMonthSal = reqRate / 12;
			}
		}
		for (Candidate candidate : candidateList) {
			int matchedSkillCount = 0;
			// List<String> candidateSkillList = null;
			if (null != candidate.getSkillsName()) {
				String[] skills = candidate.getSkillsName().split(",");
				for (String skillName : skills) {
					if (skillName != null && !"".equalsIgnoreCase(skillName)) {
						if (reqSkills.contains(skillName)) {
							matchedSkillCount++;
						}
					}
				}
				// candidateSkillList = new
				// ArrayList<String>(Arrays.asList(skills));
			}
			List<RequirementSubmission> reqList = requirementSubmissionService
					.getRequirementPerCandidate(candidate.getId());
			CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
			// if(candidateSkillList != null && candidateSkillList.size() >0){
			// }
			skillPercentage = matchedSkillCount * percentPerSkill;
			if (candidate.getExperienceYears() != null && !"".equalsIgnoreCase(candidate.getExperienceYears())
					&& candidate.getExperienceMonths() != null
					&& !"".equalsIgnoreCase(candidate.getExperienceMonths())) {
				int candidateExpMonths = Integer.parseInt(candidate.getExperienceYears()) * 12
						+ Integer.parseInt(candidate.getExperienceMonths());
				if (candidateExpMonths < reqMinExp * 12) {
					expPercentage = 0;
				} else if (candidateExpMonths >= reqMinExp * 12 && candidateExpMonths <= reqMaxExp * 12) {
					expPercentage = percentPerMonthExp * (float) candidateExpMonths;
				} else {
					expPercentage = 20;
				}
			}
			if (candidate.getCity() != null && !"".equalsIgnoreCase(candidate.getCity())) {
				if (candidate.getCity().equalsIgnoreCase(reqCity)) {
					locationPercentage = 10;
				}
			}
			String candidateRateType = candidate.getRateType();
			int reqRate = 200;
			float perMonthCandidateSal = 0;
			if ("Hourly".equalsIgnoreCase(candidateRateType)) {
				int perDaySal = reqRate * 24;
				perMonthCandidateSal = perDaySal * 30;
			} else if ("Weekly".equalsIgnoreCase(candidateRateType)) {
				int perDaySal = reqRate / 7;
				perMonthCandidateSal = perDaySal * 30;
			} else if ("Bi-weekly".equalsIgnoreCase(candidateRateType)) {
				int perDaySal = reqRate / 14;
				perMonthCandidateSal = perDaySal * 30;
			} else if ("Semi-Monthly".equalsIgnoreCase(candidateRateType)) {
				int perDaySal = reqRate / 15;
				perMonthCandidateSal = perDaySal * 30;
			} else if ("Monthly".equalsIgnoreCase(candidateRateType)) {
				perMonthCandidateSal = reqRate;
			} else if ("Bi-Monthly".equalsIgnoreCase(candidateRateType)) {
				int perDaySal = reqRate / 60;
				perMonthCandidateSal = perDaySal * 30;
			} else {
				perMonthCandidateSal = reqRate / 12;
			}
			if (perMonthCandidateSal <= reqPerMonthSal) {
				ratePercentage = 20;
			}
			float totalPercentage = skillPercentage + expPercentage + locationPercentage + ratePercentage;
			double roundOff = Math.round(totalPercentage * 100.0) / 100.0;
			String candidateScore = String.valueOf(roundOff);
			candidate.setCandidateScore(candidateScore);
			candidate.setNoOfReq(reqList.size());
			if (candidateResume.getTitle() != null && !"".equalsIgnoreCase(candidateResume.getTitle())) {
				candidate.setCandidateResume(candidateResume.getTitle());
			} else {
				candidate.setCandidateResume(candidateResume.getFileName());
			}
			if (skillPercentage != 0 && totalPercentage != 0) {
				CandidateListcopy.add(candidate);
			}
		}
		Collections.sort(CandidateListcopy, new Comparator() {
			@Override
			public int compare(Object obj1, Object obj2) {
				Candidate candidate1 = (Candidate) obj1;
				Candidate candidate2 = (Candidate) obj2;
				return candidate2.getCandidateScore().compareToIgnoreCase(candidate1.getCandidateScore());
			}
		});
		return CandidateListcopy;
	}

	@SuppressWarnings("unused")
	@Override
	public void mailRequirementtoManager(User loginUser, User managerUser, Requirement requirement,
			StringBuilder location, OrgLocations loginOrgLocation,String redirectURL,Long redirectOrgLocationId) throws Exception {

		if (requirement.getManagerUserId() != null) {
			managerUser = userService.getUserById(requirement.getManagerUserId().getId());
		}
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(managerUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (managerUser.getFirstName() != null && !"".equalsIgnoreCase(managerUser.getFirstName())) {
			StringBuilder sb = new StringBuilder();
			globalVarsMap.put("Ownername",
					sb.append(managerUser.getFirstName()).append(" ").append(managerUser.getLastName()).toString());
		} else {
			globalVarsMap.put("Ownername", "--");
		}
		if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("JobInitiator", loginUser.getFirstName());
		} else {
			globalVarsMap.put("JobInitiator", "--");
		}
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		} else {
			globalVarsMap.put("RequirementTitle", "--");
		}
		if (location != null) {
			globalVarsMap.put("RequirementLocation", location.toString());
		} else {
			globalVarsMap.put("RequirementLocation", "--");
		}
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("RequirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("RequirementRate", "--");
		}
		if ((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()))
				&& (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp()))) {
			StringBuilder expBuild = new StringBuilder();
			globalVarsMap.put("Experience",
					expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		} else if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
			globalVarsMap.put("Experience", requirement.getMinExp());
		} else if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			globalVarsMap.put("Experience", requirement.getMaxExp());
		} else {
			globalVarsMap.put("Experience", "--");
		}
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser", userBuild.append(loginUser.getFirstName()).toString());
		} else {
			globalVarsMap.put("LoginUser", "--");
		}
		if (loginUser.getUserType() != null && !"".equalsIgnoreCase(loginUser.getUserType())) {
			globalVarsMap.put("Role", loginUser.getUserType());
		} else {
			globalVarsMap.put("Role", "--");
		}
		if (loginOrgLocation.getOrgLocationName() != null
				&& !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())) {
			globalVarsMap.put("Role", loginOrgLocation.getOrgLocationName());
		} else {
			globalVarsMap.put("Role", "--");
		}
		if (requirement.getDescription() != null && !"".equalsIgnoreCase(requirement.getDescription())) {
			globalVarsMap.put("description", requirement.getDescription());
		} else {
			globalVarsMap.put("description", "--");
		}
		if (requirement.getPriority() != null && !"".equalsIgnoreCase(requirement.getPriority())) {
			globalVarsMap.put("priority", requirement.getPriority());
		} else {
			globalVarsMap.put("priority", "--");
		}
		if (requirement.getPositions() != null && !"".equalsIgnoreCase(requirement.getPositions())) {
			globalVarsMap.put("positions", requirement.getPositions());
		} else {
			globalVarsMap.put("positions", "--");
		}
		if (requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())) {
			globalVarsMap.put("skills", requirement.getSkills());
		} else {
			globalVarsMap.put("skills", "--");
		}
		StringBuilder linkBuild = new StringBuilder();
		StringBuilder attachBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl + "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Job</div></a>")
				.toString());
		globalVarsMap.put("attachment", attachBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Attachment</div></a>")
				.toString());
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.MAILREQUIREMENTTOMANAGER, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
				Constants.MAILREQUIREMENTTOMANAGER, new ArrayList<EmailAttachment>());
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, loginOrgLocation, notificationScenario,
				new HashMap<String, String>());
	}

	@SuppressWarnings("unused")
	@Override
	public void AssignToTeamMembersEmail(User loginUser, User assUser, Requirement requirement, StringBuilder location,
			OrgLocations orgLocations,String redirectURL,Long redirectOrgLocationId) throws Exception {
		assUser = userService.getUserById(assUser.getId());
		User manager = userService.getUserById(requirement.getManagerUserId().getId());
		UserRole loginUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(),
				orgLocations.getId());
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(assUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (assUser.getFirstName() != null && !"".equalsIgnoreCase(assUser.getFirstName())) {
			StringBuilder sb = new StringBuilder();
			globalVarsMap.put("Ownername",
					sb.append(assUser.getFirstName()).append(" ").append(assUser.getLastName()).toString());
		} else {
			globalVarsMap.put("Ownername", "--");
		}
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		} else {
			globalVarsMap.put("RequirementTitle", "--");
		}
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			StringBuilder jId = new StringBuilder();
			globalVarsMap.put("jobTitleId",
					jId.append(requirement.getJobTitle()).append(" ").append(requirement.getJobId()).toString());
		} else {
			globalVarsMap.put("jobTitleId", requirement.getJobTitle());
		}
		
		 List<String> requirementLocationsList = new ArrayList<>();
			List<RequirementLocations> locationsList;
			String reqLocation = "";
			StringBuilder requirementBuilder=new StringBuilder();
			try {
				locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
				if(locationsList != null && !locationsList.isEmpty()) {
					for(RequirementLocations locationsJob : locationsList) {
						requirementLocationsList.add(locationsJob.getLocation());
						if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
							reqLocation = requirementBuilder.append(locationsJob.getLocation()).toString();
						}else{
							reqLocation = "--";
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			globalVarsMap.put("RequirementLocation", reqLocation);
		
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("RequirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("RequirementRate", "--");
		}
		if ((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()))
				&& (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp()))) {
			StringBuilder expBuild = new StringBuilder();
			globalVarsMap.put("Experience",
					expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		} else if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
			globalVarsMap.put("Experience", requirement.getMinExp());
		} else if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			globalVarsMap.put("Experience", requirement.getMaxExp());
		} else {
			globalVarsMap.put("Experience", "--");
		}
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				|| !"".equalsIgnoreCase(loginUser.getFirstName()) || !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
		if (loginUserRole.getUserType() != null && !"".equalsIgnoreCase(loginUserRole.getUserType())) {
			globalVarsMap.put("Role", loginUserRole.getUserType());
		} else {
			globalVarsMap.put("Role", "--");
		}
		if (requirement.getDescription() != null && !"".equalsIgnoreCase(requirement.getDescription())) {
			globalVarsMap.put("description", requirement.getDescription());
		} else {
			globalVarsMap.put("description", "--");
		}
		if (requirement.getPriority() != null && !"".equalsIgnoreCase(requirement.getPriority())) {
			globalVarsMap.put("priority", requirement.getPriority());
		} else {
			globalVarsMap.put("priority", "--");
		}
		if (requirement.getPositions() != null && !"".equalsIgnoreCase(requirement.getPositions())) {
			globalVarsMap.put("positions", requirement.getPositions());
		} else {
			globalVarsMap.put("positions", "--");
		}
		if (requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())) {
			globalVarsMap.put("skills", requirement.getSkills());
		} else {
			globalVarsMap.put("skills", "--");
		}
		if (manager.getLastName() != null && !"".equalsIgnoreCase(manager.getLastName())) {
			StringBuffer muId = new StringBuffer();
			globalVarsMap.put("manager",
					muId.append(manager.getFirstName()).append(" ").append(manager.getLastName()).toString());
		} else {
			globalVarsMap.put("manager", manager.getFirstName());
		}
		StringBuilder linkBuild = new StringBuilder();
		StringBuilder attachBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Job</div></a>")
				.toString());
		globalVarsMap.put("attachment", linkBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Attachment</div></a>")
				.toString());
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.ASSIGNTEAMMEMBERSFORREQUIREMENT, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations,
				Constants.ASSIGNTEAMMEMBERSFORREQUIREMENT, new ArrayList<EmailAttachment>());

		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, assUser, orgLocations, notificationScenario,
				new HashMap<String, String>());
	}

	@Override
	public void rejectedEmailScenario(User loginUser, User initiatorUser, Requirement requirement,
			StringBuilder location, String rejectedReason, OrgLocations loginOrgLocation) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(requirement.getManagerUserId().getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		//Url for redirection
		String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
		Long redirectOrgLocationId = loginOrgLocation.getId();
		
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (rejectedReason != null && !"".equalsIgnoreCase(rejectedReason)) {
			globalVarsMap.put("reason", rejectedReason);
		} else {
			globalVarsMap.put("reason", "--");
		}
		if (loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder loginName = new StringBuilder();
			globalVarsMap.put("loginUserName",
					loginName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("loginUserName", loginUser.getFirstName());
		}
		StringBuilder userBuild = new StringBuilder();
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			globalVarsMap.put("approverName",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("approverName", userBuild.append(loginUser.getFirstName()).toString());
		} else {
			globalVarsMap.put("approverName", "--");
		}
		UserRole userRole = userRoleService.getUserRoleByUserId(loginUser.getId());
		if (null != userRole.getRoles()) {
			if (userRole.getRoles().getDisplayName() != null
					&& !"".equalsIgnoreCase(userRole.getRoles().getDisplayName())) {
				globalVarsMap.put("role", userRole.getRoles().getDisplayName());
			}
		} else {
			globalVarsMap.put("role", "--");
		}
		StringBuilder appendString = new StringBuilder();
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())
				&& (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle()))) {
			globalVarsMap.put("requirementId-Name", appendString.append(requirement.getJobId()).append("-")
					.append(requirement.getJobTitle()).toString());
		} else if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("requirementId-Name", requirement.getJobTitle());
		} else if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			globalVarsMap.put("requirementId-Name", requirement.getJobId());
		} else {
			globalVarsMap.put("requirementId-Name", requirement.getJobId());
		}
		if (requirement.getManagerUserId().getLastName() != null
				&& !"".equalsIgnoreCase(requirement.getManagerUserId().getLastName())) {
			StringBuilder managerName = new StringBuilder();
			globalVarsMap.put("jobInitiatorName", managerName.append(requirement.getManagerUserId().getFirstName())
					.append(" ").append(requirement.getManagerUserId().getLastName()).toString());
		} else {
			globalVarsMap.put("jobInitiatorName", requirement.getManagerUserId().getFirstName());
		}
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("requirementName", requirement.getJobTitle());
		} else {
			globalVarsMap.put("requirementName", "--");
		}	
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Rejected Job</div></a>")
				.toString());		
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.REJECTED_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, userRole.getOrgLocations().getOrg(),
				userRole.getOrgLocations(), Constants.REJECTED_EMAIL_NOTIFICATION, new ArrayList<EmailAttachment>());

		StringBuilder notificationLink = new StringBuilder();
		String actionLink = "";
		actionLink = notificationLink.append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();

		emailScenerioUtil.sendNotification(actionLink, loginUser, requirement.getManagerUserId(), loginOrgLocation,
				notificationScenario, new HashMap<String, String>());
	}

	@Override
	public void rejectedInitiatorUserEmailScenario(User loginUser, User initiatorUser, Requirement requirement,
			StringBuilder location, String rejectedReason, OrgLocations loginOrgLocation) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(initiatorUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (rejectedReason != null && !"".equalsIgnoreCase(rejectedReason)) {
			globalVarsMap.put("reason", rejectedReason);
		} else {
			globalVarsMap.put("reason", "--");
		}
		if (loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder loginName = new StringBuilder();
			globalVarsMap.put("loginUserName",
					loginName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("loginUserName", loginUser.getFirstName());
		}
		StringBuilder userBuild = new StringBuilder();
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			globalVarsMap.put("approverName",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else if (loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("approverName", userBuild.append(loginUser.getFirstName()).toString());
		} else {
			globalVarsMap.put("approverName", "--");
		}
		UserRole userRole = userRoleService.getUserRoleByUserId(loginUser.getId());
		if (null != userRole.getRoles()) {
			if (userRole.getRoles().getDisplayName() != null
					&& !"".equalsIgnoreCase(userRole.getRoles().getDisplayName())) {
				globalVarsMap.put("role", userRole.getRoles().getDisplayName());
			}
		} else {
			globalVarsMap.put("role", "--");
		}
		StringBuilder appendString = new StringBuilder();
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())
				&& (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle()))) {
			globalVarsMap.put("requirementId-Name", appendString.append(requirement.getJobId()).append("-")
					.append(requirement.getJobTitle()).toString());
		} else if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("requirementId-Name", requirement.getJobTitle());
		} else if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
			globalVarsMap.put("requirementId-Name", requirement.getJobId());
		} else {
			globalVarsMap.put("requirementId-Name", requirement.getJobId());
		}
		if (requirement.getManagerUserId().getLastName() != null
				&& !"".equalsIgnoreCase(requirement.getManagerUserId().getLastName())) {
			StringBuilder managerName = new StringBuilder();
			globalVarsMap.put("jobInitiatorName", managerName.append(initiatorUser.getFirstName()).append(" ")
					.append(initiatorUser.getLastName()).toString());
		} else {
			globalVarsMap.put("jobInitiatorName", initiatorUser.getFirstName());
		}
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("requirementName", requirement.getJobTitle());
		} else {
			globalVarsMap.put("requirementName", "--");
		}
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.REJECTED_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);

		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		// calling the sendEmail method of emailScenerioUtil
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
				Constants.REJECTED_EMAIL_NOTIFICATION, new ArrayList<EmailAttachment>());

		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, requirement.getManagerUserId(), loginOrgLocation,
				notificationScenario, new HashMap<String, String>());
	}

	@Override
	public void releaseMailScenario(User loginUser, Requirement requirement, OrgSpoc orgSpoc,
			OrgLocations loginOrgLocation, String releasetype,String redirectURL,Long redirectOrgLocationId) throws Exception {
		User vendorContactUser = null;
		if (releasetype.equalsIgnoreCase("VENDORS")) {
			vendorContactUser = userService.getUserById(orgSpoc.getVendorUser().getId());
		} else if (releasetype.equalsIgnoreCase("CONTACTS")) {
			vendorContactUser = userService.getUserById(orgSpoc.getVendorUser().getId());
		}
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(vendorContactUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		} else {
			globalVarsMap.put("RequirementTitle", "--");
		}

		StringBuilder stringBuild = new StringBuilder();
		List<RequirementLocations> locationsList = requirementLocationsService
				.getRequirementLocationsListByRequirementId(requirement.getId());
		if (locationsList != null && !locationsList.isEmpty()) {
			for (RequirementLocations location : locationsList) {
				stringBuild.append(location.getLocation()).append("<br>");
			}
			globalVarsMap.put("RequirementLocation", stringBuild.toString());
		} else {
			globalVarsMap.put("RequirementLocation", "--");
		}

		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("RequirementRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("RequirementRate", "--");
		}
		if (requirement.getClientId() != null) {
			globalVarsMap.put("clientname", requirement.getClientId().getLocationId().getOrgLocationName());
		} else {
			globalVarsMap.put("clientname", "--");
		}
		if (vendorContactUser.getFirstName() != null && vendorContactUser.getLastName() != null
				&& !"".equalsIgnoreCase(vendorContactUser.getFirstName())
				&& !"".equalsIgnoreCase(vendorContactUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("contactname", userBuild.append(vendorContactUser.getFirstName()).append("")
					.append(vendorContactUser.getLastName()).toString());
		} else {
			globalVarsMap.put("contactname", vendorContactUser.getFirstName());
		}
		if ((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()))
				&& (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp()))) {
			StringBuilder expBuild = new StringBuilder();
			globalVarsMap.put("Experience",
					expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		} else if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
			globalVarsMap.put("Experience", requirement.getMinExp());
		} else if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			globalVarsMap.put("Experience", requirement.getMaxExp());
		} else {
			globalVarsMap.put("Experience", "--");
		}
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +atsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Job</div></a>")
				.toString());
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser",
					userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
		if (loginOrgLocation.getOrgLocationName() != null
				&& !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())) {
			globalVarsMap.put("LoginOrgName", loginOrgLocation.getOrgLocationName());
		} else {
			globalVarsMap.put("LoginOrgName", "--");
		}
		// setting the Recipient information
		/*
		 * ArrayList<EmailRecipient> emailRecipientList = new
		 * ArrayList<EmailRecipient>(); EmailRecipient emailRecipient = new
		 * EmailRecipient(); emailRecipient.setEmail(emailMessage.getToEmail());
		 * StringBuilder recipBuild = new StringBuilder();
		 * emailRecipient.setName(recipBuild.append(emailMessage.getFirstName())
		 * .append(" ").append(emailMessage.getLastName()).toString());
		 * emailRecipientList.add(emailRecipient);
		 */
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.RELEASEMECHANISM_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature

		/*
		 * String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),
		 * globalVarsMap); String body =
		 * emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		 * String signature =
		 * emailScenerioUtil.buildSignature(emailScenario.getSignature(),
		 * globalVarsMap); String url = sourceleadUrl;
		 */

		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		OrgLocations vendorOrgLoc = requirement.getSpocId().getVendorLocationId();
		// construct HTML template using required data
		// String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,
		// emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),
		// loginOrgLocation);
		// emailMessage.setSubject(subject);
		// emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil

		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, loginOrgLocation.getOrg(), loginOrgLocation,
				Constants.RELEASEMECHANISM_EMAIL_NOTIFICATION, new ArrayList<EmailAttachment>());

		// emailScenerioUtil.sendEmail(emailMessage, loginOrgLocation.getId(),
		// iAdressArray, new ArrayList<EmailAttachment>());
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContactUser, vendorOrgLoc, notificationScenario,
				new HashMap<String, String>());
	}

	@Override
	public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception {
		return requirementDao.getRequirementByParentRequirementId(requirementId);
	}

	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType,
			Long userId, String userCode) throws Exception {
		return requirementDao.getAssignedRequirementListByOrgLocationIDAndUserId(orgLocationId, userType, userId,
				userCode);
	}

	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(
			Long orgLocationId, String userType, Long userId, String userCode, boolean isWalkIn, String walkinDay) throws Exception {
		return requirementDao.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(orgLocationId,
				userType, userId, userCode, isWalkIn, walkinDay);
	}
	
	@Override
	public List<String> getJobFairDaysListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId) {
		List<String> datesList = new LinkedList<>();
		try {
			List<Requirement> requirementList = requirementDao.getJobFairDaysListByOrgLocationIDAndUserId(orgLocationId,
					userType, userId);
			if (requirementList != null && !requirementList.isEmpty()) {
				for (Requirement requirement : requirementList) {
					for(String day:requirement.getWalkInDays().split(";")) {
						if(!datesList.contains(day))
							datesList.add(day);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datesList;
	}

	@Override
	public List<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId,
			String userType, Long userId, String userCode, String statusType) throws Exception {
		return requirementDao.getApproverRequirementApproverListByOrgLocationIDAndUserId(orgLocationId, userType,
				userId, userCode, statusType);
	}

	@Override
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, Long userId)
			throws Exception {
		return requirementDao.getAssignedRequirementApproverListByOrgLocationIDAndUserId(orgLocationId, userId);
	}

	@Override
	public List<Requirement> getRequirementListById(Long requirementId) throws Exception {
		return requirementDao.getRequirementListById(requirementId);
	}

	@Override
	public Requirement validateExistingJobId(String jobId, Long requirementId, Long orgLocationId) throws Exception {
		return requirementDao.validateExistingJobId(jobId, requirementId, orgLocationId);
	}

	@Override
	public Integer validateJobId(String id, Long orgLocation) throws Exception {
		return requirementDao.validateJobId(id, orgLocation);
	}

	@Override
	public File getExportRequirementFile(List<Requirement> requirementList, String orgName, String exportType,
			TimeSheetSubmissionCount requirementSubCount) throws Exception {
		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet createExcelsheet = createExcelworkbook.createSheet(orgName + "_" + dateFormat.format(new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,
					requirementSubCount);
			int column = 0;
			for (Requirement requirement : requirementList) {
				writeRow = createExcelsheet.createRow(column + 13);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow, requirement);
				column++;
			}
			/*
			 * file = new File(orgName + "_Requirements_" + dateFormat.format(new Date()) +
			 * ".xls");
			 */
			file = File.createTempFile(orgName + "_Jobs_" + dateFormat.format(new Date()), ".xls");
			file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file = pdfUtil.getRequirementPdf(requirementList, orgName, requirementSubCount);
		}
		return file;
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, Requirement requirement) throws Exception {
		 java.util.List<String> requirementLocationsList = new ArrayList<>();
		 java.util.List<RequirementLocations> locationsList;
		locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirement.getId());
		if (requirement.getJobId() != null) {
			writeRow.createCell(0).setCellValue(requirement.getJobId());
		}
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			writeRow.createCell(1).setCellValue(requirement.getJobTitle());
		}
		if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())
				&& requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			StringBuilder sb = new StringBuilder();
			writeRow.createCell(2).setCellValue(sb.append(requirement.getMinExp()).append("-")
					.append(requirement.getMaxExp()).append(" years").toString());
		} else {
			if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
				StringBuilder sb = new StringBuilder();
				writeRow.createCell(2)
						.setCellValue(sb.append("0 - ").append(requirement.getMinExp()).append("years").toString());
			} else {
				if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
					StringBuilder sb = new StringBuilder();
					writeRow.createCell(2)
							.setCellValue(sb.append("0 - ").append(requirement.getMaxExp()).append("years").toString());
				}
			}
		}
		if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())
				&& requirement.getRateType() != null && !"".equalsIgnoreCase(requirement.getRateType())) {
			StringBuilder sb = new StringBuilder();
			writeRow.createCell(3).setCellValue(
					sb.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		}
		String location = "";
		StringBuilder requirementBuilder=new StringBuilder();
			if(locationsList != null && !locationsList.isEmpty()) {
				for(RequirementLocations locationsJob : locationsList) {
					requirementLocationsList.add(locationsJob.getLocation());
					if(locationsJob.getLocation() !=null && locationsJob.getLocation() !=null){
						location = requirementBuilder.append(locationsJob.getLocation()).append(" ").toString();
						writeRow.createCell(4).setCellValue(location);
					}else{
						writeRow.createCell(4).setCellValue("--");
					}
				}
			}
/*		
		if (requirement.getCountry() != null && !"".equalsIgnoreCase(requirement.getCountry())) {
			writeRow.createCell(4).setCellValue(requirement.getCountry());
		}*/
		if (requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())) {
			writeRow.createCell(5).setCellValue(requirement.getSkills());
		}
		if (requirement.getManager() != null && !"".equalsIgnoreCase(requirement.getManager())) {
			writeRow.createCell(6).setCellValue(requirement.getManager());
		}
		if (requirement.getPriority() != null && !"".equalsIgnoreCase(requirement.getPriority())) {
			writeRow.createCell(7).setCellValue(requirement.getPriority());
		}
		/*if (requirement.getSubmissionTemplateId() != null) {
			writeRow.createCell(8).setCellValue(requirement.getSubmissionTemplateId().getTemplateName());
		}*/
		if (requirement.getClientName() != null && !"".equalsIgnoreCase(requirement.getClientName())) {
			writeRow.createCell(8).setCellValue(requirement.getClientName());
		}
		if (requirement.getClientContactName() != null && !"".equalsIgnoreCase(requirement.getClientContactName())) {
			writeRow.createCell(9).setCellValue(requirement.getClientContactName());
		}
		if (requirement.getClientContactEmail() != null && !"".equalsIgnoreCase(requirement.getClientContactEmail())) {
			writeRow.createCell(10).setCellValue(requirement.getClientContactEmail());
		}
		if (requirement.getRequirementSubmissionStatus() != null
				&& !"".equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())) {
			writeRow.createCell(11).setCellValue(requirement.getRequirementSubmissionStatus().toString());
		}
		if (requirement.getRequirementCreatedDateToString() != null && !"".equalsIgnoreCase(requirement.getRequirementCreatedDateToString().toString())) {
			writeRow.createCell(12).setCellValue(requirement.getRequirementCreatedDateToString());
		}
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			TimeSheetSubmissionCount requirementSubCount) {
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		writeRow = createExcelsheet.createRow(1);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell1 = writeRow.createCell(0);
		cell1.setCellValue("Draft :");
		cell1.setCellStyle(style);
		Cell cell2 = writeRow.createCell(1);
		cell2.setCellValue(requirementSubCount.getDraftRequirementCount());
		cell2.setCellStyle(style);
		writeRow = createExcelsheet.createRow(2);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell3 = writeRow.createCell(0);
		cell3.setCellValue("Open :");
		cell3.setCellStyle(style);
		Cell cell4 = writeRow.createCell(1);
		cell4.setCellValue(requirementSubCount.getOpenRequirementCount());
		cell4.setCellStyle(style);
		writeRow = createExcelsheet.createRow(3);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell5 = writeRow.createCell(0);
		cell5.setCellValue("New : ");
		cell5.setCellStyle(style);
		Cell cell6 = writeRow.createCell(1);
		cell6.setCellValue(requirementSubCount.getNewRequirementCount());
		cell6.setCellStyle(style);
		writeRow = createExcelsheet.createRow(4);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell7 = writeRow.createCell(0);
		cell7.setCellValue("Submitted : ");
		cell7.setCellStyle(style);
		Cell cell8 = writeRow.createCell(1);
		cell8.setCellValue(requirementSubCount.getSubmittedRequirementCount());
		cell8.setCellStyle(style);
		writeRow = createExcelsheet.createRow(5);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell9 = writeRow.createCell(0);
		cell9.setCellValue("On Hold : ");
		cell9.setCellStyle(style);
		Cell cell10 = writeRow.createCell(1);
		cell10.setCellValue(requirementSubCount.getOnHoldRequirementCount());
		cell10.setCellStyle(style);
		writeRow = createExcelsheet.createRow(6);
		createExcelsheet.setColumnWidth(0, 10000);
		createExcelsheet.setColumnWidth(1, 10000);
		Cell cell11 = writeRow.createCell(0);
		cell11.setCellValue("Cancel : ");
		cell11.setCellStyle(style);
		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue(requirementSubCount.getCancelRequirementCount());
		cell12.setCellStyle(style);
		String headers[] = Constants.REQUIREMENT_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 8);
		return createExcelsheet;
	}

	@Override
	public Boolean checkRequirementIsReleasedOrNot(Long requirementId, Long orgLocationId) throws Exception {
		Requirement req = requirementDao.checkRequirementIsReleasedOrNot(requirementId, orgLocationId);
		Boolean requirementReleased = true;
		if (req != null) {
			return requirementReleased;
		} else {
			return false;
		}
	}

	@Override
	public Boolean validateRequirementTitle(String jobName, Long orgLocationId) throws Exception {
		return requirementDao.validateRequirementTitle(jobName, orgLocationId);
	}

	public void removedApproversInRequirementForParallel(List<String> approver, Requirement requirement)
			throws Exception {
		if (requirement.getId() != null) {
			ArrayList<String> oldApproversList = new ArrayList<String>();
			ArrayList<String> newApproversList = new ArrayList<String>();
			ArrayList<String> uniqueApproversList = new ArrayList<String>();
			ArrayList<String> removedApproversList = new ArrayList<String>();
			for (String app : approver) {
				newApproversList.add(app);
			}
			// bringing the old requirementapprovalHierarachy list based on the
			// requirementID
			List<RequirementApproverHierarchy> requirementApproverHierarchies = requirementApproverHierarchyService
					.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
			if (null != requirementApproverHierarchies) {
				for (RequirementApproverHierarchy rah : requirementApproverHierarchies) {
					oldApproversList.add(rah.getApproverUserId().getId().toString());
				}
				// seperating the lists
				if (null != oldApproversList) {
					for (String item : oldApproversList) {
						if (newApproversList.contains(item)) {
							uniqueApproversList.add(item);
						} else {
							removedApproversList.add(item);
						}
					}
					// Removing the unselected approvals from the
					// RequirementApprovalHierarachy
					if (null != removedApproversList) {
						for (String item : removedApproversList) {
							requirementApproverHierarchyService
									.deleteTheRequirementApproverByRequirementIdAndApproverId(Long.parseLong(item),
											requirement.getId());
						}
					}
				}
			}
		}
	}

	/*
	 * public void removedApproversInRequirementForParallel(String[] approver ,
	 * Requirement requirement) throws Exception{
	 * 
	 * if(requirement.getId() !=null){
	 * 
	 * List<RequirementApproverHierarchy> requirementApproverHierarchies =
	 * requirementApproverHierarchyService.
	 * getRequirementApproverHierarchyListByRequirementId(requirement.getId());
	 * 
	 * if(requirementApproverHierarchies != null ){ for(String app : approver){
	 * for(RequirementApproverHierarchy rah:requirementApproverHierarchies){
	 * if(!app.equalsIgnoreCase(rah.getApproverUserId().getId().toString())){
	 * requirementApproverHierarchyService.
	 * deleteTheRequirementApproverByRequirementIdAndApproverId(rah); } } } } }
	 * 
	 * 
	 * 
	 * }
	 */
	public void removedApproversInRequirementForSequential(User approver, Requirement requirement, Integer level)
			throws Exception {
		if (requirement.getId() != null) {
			List<RequirementApproverHierarchy> requirementApproverHierarchies = requirementApproverHierarchyService
					.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
			if (requirementApproverHierarchies != null) {
				for (RequirementApproverHierarchy rah : requirementApproverHierarchies) {
					if (approver.getId() != rah.getApproverUserId().getId() && level.equals(rah.getLevel())) {
						requirementApproverHierarchyDao.deleteTheRequirementApproverByRequirementIdAndApproverId(rah);
					}
				}
			}
		}
	}

	@Override
	public Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		return requirementDao.checkLoginUserIsRequirementManagerByRequirementIdAndUserId(requirementId, userId);
	}

	public List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId, User user,
			OrgLocations orgLocations) throws Exception {
		return requirementDao.getAllReleasedJobsForVendor(orgLocationId, orgRelationId, user, orgLocations);
	}

	@Override
	public List<Requirement> getAllRequirementTeplateJobsByOrgLocationId(Long orgLocationId, User user)
			throws Exception {
		List<Requirement> list = requirementDao.getAllRequirementTeplateJobsByOrgLocationId(orgLocationId, user);
		for (Requirement req : list) {
			if (req.getCreatedBy() != null) {
				String lastName = "";
				if (StringUtils.isNotEmpty(req.getCreatedBy().getLastName())) {
					lastName = " " + req.getCreatedBy().getLastName();
				}
				req.setCreatedByName(req.getCreatedBy().getFirstName() + lastName);
			}
			req.setRequirementCreatedDateToString(commonUtil.elkDateFormatter(req.getCreatedDate()));
		}
		return list;
	}

	@Override
	public Requirement storeRequirementTemplate(RequirementDTO requirementDTO, OrgLocations orgLocations, User user)
			throws Exception {
		Requirement reqTemplate = null;
		if (StringUtils.isNotBlank(requirementDTO.getRequirementId())) {
			reqTemplate = requirementDao.getRequirementTemplateByRequirementId(
					Long.parseLong(requirementDTO.getRequirementId()), orgLocations.getId());
			if (reqTemplate == null) {
				reqTemplate = new Requirement();
			}
		} else {
			reqTemplate = new Requirement();
		}
		if (StringUtils.isNotBlank(requirementDTO.getIsTemplate())
				&& !"".equalsIgnoreCase(requirementDTO.getIsTemplate())) {
			reqTemplate.setIsTemplate(requirementDTO.getIsTemplate());
		}
		if (orgLocations != null) {
			reqTemplate.setOrgLocationId(orgLocations);
		}
		if (StringUtils.isNotBlank(requirementDTO.getJobTitle())) {
			reqTemplate.setJobTitle(requirementDTO.getJobTitle());
		}
		if (StringUtils.isNotBlank(requirementDTO.getJobType())) {
			reqTemplate.setJobType(requirementDTO.getJobType());
		} else {
			reqTemplate.setJobType(null);
			reqTemplate.setOtherJobType(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getJobOtherType())
				&& StringUtils.isNotBlank(requirementDTO.getJobType())
				&& "Other".equalsIgnoreCase(requirementDTO.getJobType())) {
			reqTemplate.setOtherJobType(requirementDTO.getJobOtherType());
		} else {
			reqTemplate.setOtherJobType(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getMaxExp())) {
			reqTemplate.setMaxExp(requirementDTO.getMaxExp());
		} else {
			reqTemplate.setMaxExp(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getMinExp())) {
			reqTemplate.setMinExp(requirementDTO.getMinExp());
		} else {
			reqTemplate.setMinExp(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getProjectDurationYears())) {
			reqTemplate.setProjectDurationYears(requirementDTO.getProjectDurationYears());
		} else {
			reqTemplate.setProjectDurationYears(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getProjectDurationMonths())) {
			reqTemplate.setProjectDurationMonths(requirementDTO.getProjectDurationMonths());
		} else {
			reqTemplate.setProjectDurationMonths(null);
		}

		if (StringUtils.isNotBlank(requirementDTO.getRate())) {
			reqTemplate.setRate(requirementDTO.getRate());
		}
		if (StringUtils.isNotBlank(requirementDTO.getRateType())) {
			reqTemplate.setRateType(requirementDTO.getRateType());
		}
		if (StringUtils.isNotBlank(requirementDTO.getIndustryName())) {
			reqTemplate.setIndustryName(requirementDTO.getIndustryName());
		}
		if (StringUtils.isNotBlank(requirementDTO.getMaxRate())) {
			reqTemplate.setMaxRate(requirementDTO.getMaxRate());
		}
		if (StringUtils.isNotBlank(requirementDTO.getTravelAllowance())) {
			reqTemplate.setTravelAllowance(requirementDTO.getTravelAllowance());
		}
		if (StringUtils.isNotBlank(requirementDTO.getDescription())) {
			reqTemplate.setDescription(requirementDTO.getDescription());
		}
		if (StringUtils.isNotBlank(requirementDTO.getCoolingOff())) {
			reqTemplate.setReqCoolingOffPeriod(requirementDTO.getCoolingOff());
		}
		if (requirementDTO.getNoticePeriod() != null && requirementDTO.getNoticePeriod().size() > 0) {
			String np = String.join(",", requirementDTO.getNoticePeriod());
			reqTemplate.setNoticePeriod(np);
			/*
			 * reqTemplate.setNoticePeriod(jsonObj.getString("noticePeriod")); String
			 * noticePeriod =null; try{ JSONArray jsonArray =
			 * jsonObj.getJSONArray("noticePeriod"); noticePeriod = jsonArray.toString();
			 * }catch(JSONException e){ noticePeriod =jsonObj.getString("noticePeriod"); }
			 * noticePeriod = noticePeriod.replaceAll("\\[", "").replaceAll("\\]","");
			 * noticePeriod = noticePeriod.replaceAll("\"", "");
			 * reqTemplate.setNoticePeriod(noticePeriod);
			 */
		}
		if (StringUtils.isNotBlank(requirementDTO.getCurrencyCode())) {
			reqTemplate.setCurrencyCode(requirementDTO.getCurrencyCode());
		}
		if (StringUtils.isNotBlank(requirementDTO.getSkills())) {
			reqTemplate.setSkills(requirementDTO.getSkills());
		} else {
			reqTemplate.setSkills(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getEducation())) {
			reqTemplate.setEducation(requirementDTO.getEducation());
		} else {
			reqTemplate.setEducation(null);
		}
		if (StringUtils.isNotBlank(requirementDTO.getMedicalClearence())) {
			reqTemplate.setMedicalClearence(requirementDTO.getMedicalClearence());
		}
		if (StringUtils.isNotBlank(requirementDTO.getSecurityClearance())) {
			reqTemplate.setSecurityClearance(requirementDTO.getSecurityClearance());
		}
		if (StringUtils.isNotBlank(requirementDTO.getOverTime())) {
			reqTemplate.setOverTime(requirementDTO.getOverTime());
		}
		if (StringUtils.isNotBlank(requirementDTO.getBackgroundCheck())) {
			reqTemplate.setBackgroundCheck(requirementDTO.getBackgroundCheck());
		}
		if (StringUtils.isNotBlank(requirementDTO.getGender())) {
			reqTemplate.setGender(requirementDTO.getGender());
		}
		if (StringUtils.isNotBlank(requirementDTO.getExperienceLevel())) {
			LookUpData experienceLevel = lookUpDataService.getTasksTypeByUniqueKey(requirementDTO.getExperienceLevel());
			reqTemplate.setExperienceLevel(experienceLevel);
		} else {

			reqTemplate.setExperienceLevel(null);
		}
		reqTemplate.setStatus(Constants.MEMBER_ACTIVE);
		reqTemplate.setCreatedDate(commonUtil.currentDate());
		reqTemplate.setCreatedBy(user);
		reqTemplate.setUpdatedBy(user.getUserCode());
		reqTemplate.setUpdatedDate(commonUtil.currentDate());
		requirementDao.storeRequirement(reqTemplate);
		return reqTemplate;
	}

	@Override
	public Requirement getRequirementTemplateByRequirementId(Long requirementId, Long orgLocationId) throws Exception {
		return requirementDao.getRequirementTemplateByRequirementId(requirementId, orgLocationId);
	}

	@Override
	public Requirement updateRequirementTemplate(Requirement requirement) throws Exception {
		return requirementDao.storeRequirement(requirement);
	}

	@Override
	public Integer getManagerAssignedRequirementsCount(Long userId, Long orgLocationId) throws Exception {
		return requirementDao.getManagerAssignedRequirementsCount(userId, orgLocationId);
	}

	@Override
	public Integer getRequirementsCountAsApprover(Long userId, Long orgLocationId) throws Exception {
		return requirementDao.getRequirementsCountAsApprover(userId, orgLocationId);
	}

	@Override
	public List<Requirement> getManagerAssignedRequirementsList(Long userId, Long orgLocationId) throws Exception {
		return requirementDao.getManagerAssignedRequirementsList(userId, orgLocationId);
	}

	@Override
	public Boolean checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(Long userId,
			Long orgLocationId) throws Exception {
		return requirementDao.checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(userId,
				orgLocationId);
	}

	@Override
	public Requirement getRequirementByRequirementIdOrgLocationsId(String id, Long requirementId, Long orgLocationsId)
			throws Exception {
		return requirementDao.getRequirementByRequirementIdOrgLocationsId(id, requirementId, orgLocationsId);
	}

	@Override
	public Boolean validateJobid(String id, Long orgLocationsId) throws Exception {
		return requirementDao.validateJobid(id, orgLocationsId);
	}

	@Override
	public List<String> getAllRequirementSkillsByRequirementI(Long requirementId) throws Exception {
		return requirementDao.getAllRequirementSkillsByRequirementI(requirementId);
	}

	@Override
	public List<String> getAllRequirementIndustryByRequirementId(Long requirementId) throws Exception {
		return requirementDao.getAllRequirementIndustryByRequirementId(requirementId);
	}
	/*
	 * @Override public List<String> getAllSkillsBySkillCategory() throws Exception{
	 * return lookUpDataService.getAllSkillsBySkillCategory();
	 * 
	 * }
	 */

	private Requirement getData(RequirementIndex requirementIndex, User user, OrgLocations orgLocations)
			throws Exception {
		Requirement requirement = new Requirement();
		requirement.setId(requirementIndex.getRequirementId());
		requirement.setJobId(requirementIndex.getJobId());
		requirement.setJobType(requirementIndex.getJobType());
		requirement.setOtherJobType(requirementIndex.getOtherJobType());
		requirement.setJobTitle(requirementIndex.getJobTitle());
		String convertedStirng = requirementIndex.getCreatedDate();
		DateFormat elkFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date elkDate = elkFormat.parse(convertedStirng);
		requirement.setPostedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), elkDate));
		if (requirementIndex.getMinExperience() != null) {
			requirement.setMinExp(requirementIndex.getMinExperience().toString());
		}
		if (requirementIndex.getSkills() != null) {
			String skills = getReqSkills(requirementIndex.getSkills());
			requirement.setSkills(skills);
		}
		if (requirementIndex.getMaxExperience() != null) {
			requirement.setMaxExp(requirementIndex.getMaxExperience().toString());
		}
		requirement.setPostedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), elkDate));
		requirement.setClientName(requirementIndex.getClient());
		requirement.setCountry(requirementIndex.getCountry());
		requirement.setState(requirementIndex.getState());
		requirement.setCity(requirementIndex.getCity());
		requirement.setSearchType("Requirement");
		requirement.setPositions(requirementIndex.getNumberOfPositions());
		if (null != requirementIndex.getNumberOfApplicants()) {
			requirement.setTotalApplicants(Integer.parseInt(requirementIndex.getNumberOfApplicants()));
		}
		requirement.setManager(requirementIndex.getManager());
		if (StringUtils.isNotEmpty(requirementIndex.getRequirementStatus())) {
			requirement.setRequirementSubmissionStatus(
					REQUIREMENT_SUBMISSION_STATUS.valueOf(requirementIndex.getRequirementStatus()));
		}
		Boolean isLoginUserAsTeamMember = requirementAssignToService
				.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementIndex.getRequirementId(),
						user.getId());
		requirement.setIsLoginUserAsTeamMember(isLoginUserAsTeamMember.toString());
		if (StringUtils.isNotEmpty(requirementIndex.getReleasedBy())) {
			List<Long> userId = new ArrayList<Long>();
			for (String s : requirementIndex.getReleasedBy().split(",")) {
				userId.add(Long.parseLong(s));
			}
			requirement.setRequirementReleasedUserIds(userId);
		}
		if (null != requirementIndex.getRequirementUUID()) {
			requirement.setRequirementUUID(requirementIndex.getRequirementUUID());
		}
		return requirement;
	}

	private String getReqSkills(String[] skills) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < skills.length; i++) {
			sb.append(skills[i]).append(",");
		}
		String str = sb.toString();
		return str.substring(0, str.length() - 1);
	}

	private ArrayList<String> getSearchValuesFromChosenSelectField(String searchValue) {
		ArrayList<String> keyWordList = null;
		if (searchValue != null && !"".equalsIgnoreCase(searchValue)) {
			String searchKeywordString = searchValue.replaceAll("\\[", "").replaceAll("\\]", "");
			keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		}
		return keyWordList;
	}

	private ArrayList<QueryBuilder> getQueryBuilderListForUniversalSearch(String keywd) {
		ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		QueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("client", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
		QueryBuilderList.add(QueryBuilders.matchQuery("priority", keywd));
		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
		if (matcher.find()) {
			Float expYears = Float.valueOf(matcher.group());
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
		}
		return QueryBuilderList;
	}

	public String UpdateOnToDate(String updatedOn) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		if ((Constants.ONE_DAY_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -1);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.THREE_DAY_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -3);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.ONE_WEEK_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -7);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.TWO_WEEK_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -14);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.ONE_MONTH_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -30);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.THREE_MONTH_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -90);
			return dateFormat.format(cal.getTime());
		} else {
			return updatedOn;
		}
	}

	@Override
	public Long getRequirementsListById(Long orgLocationId) throws Exception {
		return requirementDao.getRequirementListByOrgLocationID(orgLocationId);
	}

	@Override
	public Requirement getRequirementByRequirementUUID(String requirementUUID) throws Exception {
		return requirementDao.getRequirementByRequirementUUID(requirementUUID);
	}

	@Override
	public Requirement updateRequirement(Requirement requirement) throws Exception {
		return requirementDao.storeRequirement(requirement);
	}

	@Override
	public List<Requirement> getAllRequirementsByOrglocationId(Long orgLocationId) throws Exception {
		return requirementDao.getAllRequirementsByOrglocationId(orgLocationId);
	}

	@Override
	public List<Requirement> getAllReleasedRequirementsByParentId(Long parentRequirementId) throws Exception {
		return requirementDao.getAllReleasedRequirementsByParentId(parentRequirementId);
	}

	@Override
	public String getNoticePeriodByRequirementId(Long requirementId) throws Exception {
		return requirementDao.getNoticePeriodByRequirementId(requirementId);
	}

	@Override
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserId(Long orgLocationId, User user)
			throws Exception {
		return requirementDao.getAllRequirementsByOrglocationIdAndUserId(orgLocationId, user);
	}

	@Override
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(Long orgLocationId, User user)
			throws Exception {
		return requirementDao.getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(orgLocationId, user);
	}

	@Override
	public Integer getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(Long orgLocationId, User user)
			throws Exception {
		return requirementDao.getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(orgLocationId, user);
	}

	@Override
	public List<Requirement> getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(
			Long orgLocationId, TeamMembers teamMembers) throws Exception {
		return requirementDao.getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(orgLocationId,
				teamMembers);
	}

	@Override
	public BigInteger getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC,
			String endDateUTC,String isGlobalUser,Long orgId) throws Exception {
		return requirementDao.getCountOfAllRequirementsByCreatedUserAndOrglocationId(orgLocationId, user, startDateUTC,
				endDateUTC,isGlobalUser,orgId);
	}

	@Override
	public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId, Date startDateUTC,
			Date endDateUTC) {
		return requirementDao.getTotalRequirementsReleasedToSpoc(relationId, spocId, startDateUTC, endDateUTC);
	}

	@Override
	public List<Requirement> getRequirementsForSOWByOrgLocationId(Long orgLocationId, String sowType) throws Exception {
		return requirementDao.getRequirementsForSOWByOrgLocationId(orgLocationId, sowType);
	}

	@Override
	public List<String> getAllRequirementEducationByRequirementId(Long requirementId) {
		return requirementDao.getAllRequirementEducationByRequirementId(requirementId);
	}

	@Override
	public Requirement getRequirementDetailsById(Long requirementId) throws Exception {
		return requirementDao.getRequirementDetailsById(requirementId);
	}

	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId,
			String userType, Long userId, String userCode, String numberOfDays) throws Exception {
		return requirementDao.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(orgLocationId, userType,
				userId, userCode, numberOfDays);
	}
	
	@Override
	public List<Requirement> getRequirementListByPostedDateAndSelectedClients(Long orgLocationId, Long userId,
			String postedDateRange, String selectedClients) {
		int numberOfDays = 0;
		switch (postedDateRange) {
		case "today":
			numberOfDays = 1;
			break;
		case "yesterday":
			numberOfDays = 2;
			break;
		case "week":
			numberOfDays = 7;
			break;
		case "twoweeks":
			numberOfDays = 2 * 7;
			break;
		case "month":
			numberOfDays = 30;
			break;
		case "twomonths":
			numberOfDays = 2 * 30;
			break;
		case "all":
			numberOfDays = -1;
			break;
		default:
			numberOfDays = 1;
		}
		return requirementDao.getRequirementListByPostedDateAndSelectedClients(orgLocationId, userId, numberOfDays,
				selectedClients);
	}

	@Override
	public List<Requirement> getAllReleasedJobsForVendorBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId,
			Date startDateUTC, Date endDateUTC) throws Exception {
		return requirementDao.getAllReleasedJobsForVendorBasedOnStartAndEndDates(orgLocationId, orgRelationId,
				startDateUTC, endDateUTC);
	}

	@Override
	public List<Requirement> getAllReleasedJobsBasedOnUserIdAndLocationId(User user, OrgLocations orgLocations)
			throws Exception {
		return requirementDao.getAllReleasedJobsBasedOnUserIdAndLocationId(user, orgLocations);
	}

	@Override
	public List<Requirement> getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(List<Long> parentreqIds,
			Long relationId) throws Exception {
		return requirementDao.getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(parentreqIds, relationId);
	}

	@Override
	public List<Requirement> getAllClientJobsBasedonRelationId(Long locationId, Long relationId, String userRoleName,
			User user, String status) throws Exception {
		return requirementDao.getAllClientJobsBasedonRelationId(locationId, relationId, userRoleName, user, status);
	}

	@Override
	public List<Requirement> getAllClosedRequirementsByStartDateAndEndDate(Date startDateUTC, Date endDateUTC)
			throws Exception {
		return requirementDao.getAllClosedRequirementsByStartDateAndEndDate(startDateUTC, endDateUTC);
	}

	@Override
	public List<Requirement> getAllVendorJobsBasedonRelationId(Long locationId, Long relationId, String userRoleName,
			User user, String status) {
		return requirementDao.getAllVendorJobsBasedonRelationId(locationId, relationId, userRoleName, user, status);
	}

	@Override
	public List<String> getAllRequirementVisaByRequirementI(Long requirementId) throws Exception {
		return requirementDao.getAllRequirementVisaByRequirementI(requirementId);
	}

	@Override
	public boolean checkForOldrecordsUsingOrgLocationid(Long orglocationId) throws Exception {
		boolean flag = requirementDao.checkForOldrecordsUsingOrgLocationid(orglocationId, "");
		if (!flag) {
			flag = addGeneratedIdRecordsToJobsForOldRecords(orglocationId);
		}
		return flag;
	}

	public boolean addGeneratedIdRecordsToJobsForOldRecords(Long orglocationId) throws Exception {
		List<Requirement> requirementList = requirementDao.addGeneratedIdRecordsToJobsForOldRecords(orglocationId);
		List<Candidate> candidateList = requirementDao.addGeneratedIdRecordsToCandidatesForOldRecords(orglocationId);
		List<Projects> projectList = requirementDao.addGeneratedIdRecordsToProjectsForOldRecords(orglocationId);
		boolean data = false;
		int jobsGeneratedId = 0;
		int candidateGeneratedId = 0;
		int projectsGeneratedId = 0;
		String finalJobsGeneratedId = null;
		String finalCandidatesGeneratedId = null;
		String finalProjectsGeneratedId = null;
		// for jobs
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orglocationId);
		if (requirementList != null && !requirementList.isEmpty()) {
			for (Requirement req : requirementList) {
				if (req.getGeneratedId() == null || (req.getGeneratedId() != null
						&& req.getGeneratedId().substring(0, 4).equalsIgnoreCase("null"))) {
					if (orgLocation.getGeneratedUserId() != null
							&& !orgLocation.getGeneratedUserId().equalsIgnoreCase("")) {
						jobsGeneratedId = jobsGeneratedId + 1;
						final DecimalFormat decimalFormat = new DecimalFormat("0");
						String ConvetredjobsId = decimalFormat.format(jobsGeneratedId);
						StringBuffer sb = new StringBuffer();
						finalJobsGeneratedId = sb.append(orgLocation.getGeneratedUserId().toUpperCase())
								.append(Constants.JOB_ENTITY).append(ConvetredjobsId).toString();
						req.setGeneratedId(finalJobsGeneratedId);
						Requirement savedRequirement = requirementDao.storeRequirement(req);
						requirementElkUtil.saveElkRequirement(savedRequirement, null);
						data = true;
					}
				}
			}
			System.out.println("Requirement data successfully");
		}
		if (candidateList != null && !candidateList.isEmpty()) {
			for (Candidate candidate : candidateList) {
				if (candidate.getGeneratedId() == null || (candidate.getGeneratedId() != null
						&& candidate.getGeneratedId().substring(0, 4).equalsIgnoreCase("null"))) {
					if (orgLocation.getGeneratedUserId() != null
							&& !orgLocation.getGeneratedUserId().equalsIgnoreCase("")) {
						candidateGeneratedId = candidateGeneratedId + 1;
						final DecimalFormat decimalFormat = new DecimalFormat("0");
						String ConvetredjobsId = decimalFormat.format(candidateGeneratedId);
						StringBuffer sb = new StringBuffer();
						finalCandidatesGeneratedId = sb.append(orgLocation.getGeneratedUserId().toUpperCase())
								.append(Constants.CANDIDATE_ENTITY).append(ConvetredjobsId).toString();
						candidate.setGeneratedId(finalCandidatesGeneratedId);
						Candidate savedCanddidate = candidateDao.storeCandidate(candidate);
						candidateService.saveOrUpdateCandidateElkDocument(savedCanddidate);
						data = true;
					}
				}
			}
			System.out.println("candidate data successfully");
		}
		if (projectList != null && !projectList.isEmpty()) {
			for (Projects projects : projectList) {
				if (projects.getGeneratedId() == null || (projects.getGeneratedId() != null
						&& projects.getGeneratedId().substring(0, 4).equalsIgnoreCase("null"))) {
					if (orgLocation.getGeneratedUserId() != null
							&& !orgLocation.getGeneratedUserId().equalsIgnoreCase("")) {
						projectsGeneratedId = projectsGeneratedId + 1;
						final DecimalFormat decimalFormat = new DecimalFormat("0");
						String ConvetredjobsId = decimalFormat.format(projectsGeneratedId);
						StringBuffer sb = new StringBuffer();
						finalProjectsGeneratedId = sb.append(orgLocation.getGeneratedUserId().toUpperCase())
								.append(Constants.PROJECT_ENTITY).append(ConvetredjobsId).toString();
						projects.setGeneratedId(finalProjectsGeneratedId);
						Projects savedProjects = projectsDao.updateProject(projects);
						projectsService.saveOrUpdateProjectElkDocument(savedProjects);
						data = true;
					}
				}
			}
		}
		return data;
	}

	@Override
	public Requirement getRequirementByShortURL(String shortURL) throws Exception {
		return requirementDao.getRequirementByShortURL(shortURL);
	}

	@Override
	public Requirement getRequirementByRequirementIdAndJobTitle(Long requirementId, String title) throws Exception {
		Requirement requirement = requirementDao.getRequirementByRequirementIdAndJobTitle(requirementId, title);
		return requirement;
	}

	public List<Requirement> getAllRequirementsByOrlocationIdAndOpenSubmissionStatus(Long orglocationId)
			throws Exception {
		return requirementDao.getAllRequirementsByOrlocationIdAndOpenSubmissionStatus(orglocationId);
	}

	@Override
	public List<Requirement> getAllAssinedRequirementsOrgLocationIdAndUserId(Long orglocationId, Long userId)
			throws Exception {
		return requirementDao.getAllAssinedRequirementsOrgLocationIdAndUserId(orglocationId, userId);
	}

	@Override
	public ResponseList<RequirementDTO> getAllRequirementsForJobCampaing(RequirementDTO requirementDTO,
			Long orglocationId, Long userId, String dateFormat) throws Exception {
		return requirementDao.getAllRequirementsForJobCampaing(requirementDTO, orglocationId, userId, dateFormat);
	}
	
	@Override
	public ResponseList<RequirementDTO> getJobListOfRecruiter(RequirementDTO requirementDTO, Long recruiterUserId,Date[] searchDates,List<Long> teamsList) throws Exception {
		return requirementDao.getJobListOfRecruiter(requirementDTO, recruiterUserId, searchDates, teamsList);
	}

	@Override
	public List<RequirementDTO> getAllCampaignRequirementsByIdsList(List<Long> requirementIds) throws Exception {
		return requirementDao.getAllCampaignRequirementsByIdsList(requirementIds);

	}

	@Override
	public void shareRecomendedApplicant(User loginUser, OrgLocations orgLocation, Requirement requirement,
			Candidate candidate) throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(candidate.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			StringBuilder sb = new StringBuilder();
			if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
				globalVarsMap.put("jobName",
						sb.append(requirement.getJobTitle()).append("-").append(requirement.getJobId()).toString());
			} else {
				globalVarsMap.put("jobName", requirement.getJobTitle());
			}
		}

		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null && !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("jobRate",
					sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ")
							.append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType())
							.toString());
		} else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("jobRate", sb.append(requirement.getCurrencyCode()).append(" ")
					.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		} else {
			globalVarsMap.put("jobRate", "--");
		}
		if (requirement.getDescription() != null && !"".equalsIgnoreCase(requirement.getDescription())) {
			globalVarsMap.put("jobDescription", requirement.getDescription());
		} else {
			globalVarsMap.put("jobDescription", requirement.getDescription());
		}

		if (requirement.getMinExp() != null && requirement.getMaxExp() != null) {
			globalVarsMap.put("jobExperience",
					"Min Exp  " + requirement.getMinExp() + " - " + "Max Exp  " + requirement.getMaxExp());
		} else if (requirement.getMinExp() != null) {
			globalVarsMap.put("jobExperience", "Min Exp  " + requirement.getMinExp());
		} else if (requirement.getMaxExp() != null) {
			globalVarsMap.put("jobExperience", "Max Exp  " + requirement.getMaxExp());
		}
		if (candidate.getLastName() != null) {
			StringBuilder candidateName = new StringBuilder();
			globalVarsMap.put("candidateName", candidateName.append(candidate.getFirstName()).append(" ")
					.append(candidate.getLastName()).toString());
		} else {
			globalVarsMap.put("candidateName", candidate.getFirstName());
		}
		globalVarsMap.put("loginOrgName", orgLocation.getOrgLocationName());
		globalVarsMap.put("loginUserName", loginUser.getFullName());
		String locationTab = getloaactionList(requirement.getId());
		globalVarsMap.put("jobLocation", locationTab);

		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("viewJobLink", linkBuild.append("<a href='").append(sourceleadUrl).append("/")
				.append("jobCampaignSummary?requirementUUID=").append(requirement.getRequirementUUID())
				.append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Job</div></a>")
				.toString());
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
				Constants.SHARE_RECOMENDED_APPLICANT_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);

		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocation.getOrg(), orgLocation,
				Constants.SHARE_RECOMENDED_APPLICANT, new ArrayList<EmailAttachment>());
		// calling the sendNotification method of emailScenerioUtil
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append(atsUrl).append("/jobCampaignSummary?requirementUUID=")
				.append(requirement.getRequirementUUID()).toString();
		if (candidate.getConsultantUser() != null)
			emailScenerioUtil.sendNotification(actionLink, loginUser, candidate.getConsultantUser(), orgLocation,
					notificationScenario, new HashMap<String, String>());

	}

	private String getloaactionList(Long requirementId) throws Exception {
		String tempHTML = "";
		String url = sourceleadUrl;
		if (sourceleadUrl.indexOf("/app") != -1) {
			url = sourceleadUrl.split("/app")[0];
		}
		List<RequirementLocations> locationsList = requirementLocationsService
				.getRequirementLocationsListByRequirementId(requirementId);
		if (locationsList != null && !locationsList.isEmpty()) {
			for (RequirementLocations location : locationsList) {
				tempHTML = tempHTML + "<span><span><img src='" + url
						+ "/resources/images/location-icon.png' style='width:20px;height: 20px;position: relative;top: 5px;' alet='Location' title='Location'></span>"
						+ location.getLocation() + "</span>";
			}
		}
		return tempHTML;
	}

	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDateAndManagerId(
			Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays, Long managerId)
			throws Exception {
		return requirementDao.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDateAndManagerId(orgLocationId,
				userType, userId, userCode, numberOfDays, managerId);
	}

	@Override
	public Requirement getRequirementByAccessibleUserIdAndRequirementUUID(Long userId, String requirementUUID)
			throws Exception {
		return requirementDao.getRequirementByAccessibleUserIdAndRequirementUUID(userId, requirementUUID);
	}

	@Override
	public ResponseList<RequirementDTO> getRequirementList(Long id, String search, Long location, String employmentType,
			int pageNo, int pageSize) {
		return requirementDao.getRequirementList(id, search, location, employmentType, pageNo, pageSize);
	}

	@Override
	public List<RequirementDTO> getAllManagerRequirementForJobSharing(Date[] searchDates, Long managerUserId,
			Long orgLocationId) throws Exception {
		return requirementDao.getAllManagerRequirementForJobSharing(searchDates, managerUserId, orgLocationId);
	}

	@Override
	public Requirement getRequirementByGeneratedIdAndLocationId(String generatedId, Long orgLocationId)
			throws Exception {
		return requirementDao.getRequirementByGeneratedIdAndLocationId(generatedId, orgLocationId);
	}
	
	@Override
	public  List<RecruiterRrportDTO> getAssociateCountsCreatedUserListByLocation(Long orgLocationId,
			List<Long> userIdsList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)
			throws Exception {
		return requirementDao.getAssociateCountsCreatedUserListByLocation(orgLocationId, userIdsList, startDateUTC, endDateUTC, isGlobalUser, orgId);
	}

	@Override
	public List<RecruiterReportAssociateCountView> getAssociateCountsCreatedUserList(Long orgLocationId,
			List<Long> userIdsList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)
			throws Exception {
		return requirementDao.getAssociateCountsCreatedUserList(orgLocationId, userIdsList, startDateUTC, endDateUTC, isGlobalUser, orgId);
	}

	@Override
	public ResponseList<SubmissionRequirementListView> getAllRequirementsForCandidateSubmission(RequirementDTO requirementDTO,
			Long userId, OrgLocations orgLocations) throws Exception {
		return requirementDao.getAllRequirementsForCandidateSubmission(requirementDTO, userId, orgLocations);
	}

	/*private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");*/
	
	/*@Override
	public long addRequirementToJobFairs(List<JobFairDTO> jobFairDTOList) {
		try {
			for (JobFairDTO jobFairDTO : jobFairDTOList) {
				JobFair jobFair = requirementDao.getJobFair(jobFairDTO.getId());

				JobFairRequirements jobFairRequirements = null;
				if (jobFairDTO.getJobFairRequirementId() > 0) {
					jobFairRequirements = requirementDao.getJobFairRequirementsByJobFairIdAndRequirementId(jobFairDTO.getId(), jobFairDTO.getRequirementId());
				} else {
					jobFairRequirements = new JobFairRequirements();
				}
				jobFairRequirements.setJobFair(jobFair);
				jobFairRequirements.setRequirement(requirementDao.getRequirementByRequirementId(jobFairDTO.getRequirementId()));
				String fairDates = "";
				for (String fairDate : jobFairDTO.getRequirementFairDates()) {
					fairDates += (fairDates.isEmpty() ? "" : ",") + fairDate;
				}
				jobFairRequirements.setFairDates(fairDates);
				jobFair.getJobFairRequirementList().add(jobFairRequirements);
				return requirementDao.saveJobFair(jobFair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}*/
	
/*	@Override
	public long addRequirementToJobFair(long jobFairId, long requirementId, String jobFairDates[]) {
		try {
			JobFair jobFair = requirementDao.getJobFair(jobFairId);
			Requirement requirement = requirementDao.getRequirementByRequirementId(requirementId);
			if (jobFair != null && requirement != null) {
				JobFairRequirements jobFairRequirements = new JobFairRequirements();
				jobFairRequirements.setJobFair(jobFair);
				jobFairRequirements.setRequirement(requirement);
				String fairDates = "";
				for (String date : jobFairDates) {
					fairDates += (fairDates.isEmpty() ? "" : ",") + date;
				}
				jobFairRequirements.setFairDates(fairDates);
				jobFair.getJobFairRequirementList().add(jobFairRequirements);
				return requirementDao.saveJobFair(jobFair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}*/
	
	@Override
	public Response<String> saveJobFair(JobFairDTO jobFairDTO, User user, OrgLocations orgLocations) {
		Response<String> response = new Response<>();
		try {
			JobFair jobFair = null;
			JobFair savedJobFair = requirementDao.getJobFairByName(jobFairDTO.getName(), user.getId(), orgLocations.getId());
			if (savedJobFair != null && savedJobFair.getId() != jobFairDTO.getId()) {
				response.setResponse(Result.DUPLICATE_NAME.name());
				response.setResponseMessage(Result.DUPLICATE_NAME.getValue());
				return response;
			} else {
				
				if (jobFairDTO.getId() > 0) {
					jobFair = requirementDao.getJobFair(jobFairDTO.getId());
					jobFair.setUpdatedBy(user);
					jobFair.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
				} else {
					jobFair = new JobFair();
					jobFair.setGeneratedId(getGeneratedJobFairId(orgLocations));
					jobFair.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
					jobFair.setDescription("");
					jobFair.setStatus(1);
					jobFair.setCreatedBy(user);
					jobFair.setCreatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocations));
				}
				
				List<Long> deleteIdsList = new LinkedList<>();
				if (jobFair.getJobFairRequirementList() != null) {
				    for (JobFairRequirements jobFairRequirements : jobFair.getJobFairRequirementList()) {
				        deleteIdsList.add(jobFairRequirements.getId());
				    }
				}
				
				jobFair.setName(jobFairDTO.getName());
				jobFair.setBudget(Double.parseDouble(jobFairDTO.getBudget()));
				jobFair.setDescription(jobFairDTO.getDescription());
				jobFair.setUser(user);
				jobFair.setOrgLocations(orgLocations);
				jobFair.setStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), jobFairDTO.getStartDate()));
				jobFair.setEndDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), jobFairDTO.getEndDate()));
				jobFair.setPostedFrom(jobFairDTO.getPostedFrom());
				jobFair.setSelectedClient(jobFairDTO.getSelectedClient());
				jobFair.setDateFormat(orgLocations.getDateFormat());
				List<JobFairRequirements> jobFairRequirementList = new LinkedList<>();
				JSONArray jsonArray = new JSONArray(jobFairDTO.getRequirementJSONData());
				if (jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						if (jsonObject != null) {
							Long id = jsonObject.getLong("id");
							Long requirementId = jsonObject.getLong("requirementId");
							String fairDates = jsonObject.getString("fairDates");
							String finalFairDates = "";
							SimpleDateFormat sdf = new SimpleDateFormat(orgLocations.getDateFormat());
							if(fairDates != null && !fairDates.isEmpty()) {
								for(String fairDate : fairDates.split(",")) {
									try {
										sdf.parse(fairDate);
										finalFairDates += (finalFairDates.isEmpty() ? "" : ",") + fairDate;
									} catch (Exception e) {
//										System.out.println("JobFair Date exception for : " + fairDate);
									}
								}
							}
							
							JobFairRequirements jobFairRequirements = null;
							if (id > 0) {
								jobFairRequirements = requirementDao.getJobFairRequirementsById(id);
							} else {
								jobFairRequirements = new JobFairRequirements();
							}
							jobFairRequirements.setJobFair(jobFair);
							Requirement requirement = getRequirementByRequirementId(requirementId);
							jobFairRequirements.setRequirement(requirement);
							jobFairRequirements.setFairDates(finalFairDates);
							jobFairRequirementList.add(jobFairRequirements);
							
							deleteIdsList.remove(jobFairRequirements.getId());
						}
					}
				}
				jobFair.setJobFairRequirementList(jobFairRequirementList);
				jobFair.setVenue(jobFairDTO.getVenue());
				jobFair.setNotifyTo(jobFairDTO.getNotifyTo());
				
				if(!deleteIdsList.isEmpty()) {
					for(Long jobFairRequirementId : deleteIdsList) {
						requirementDao.deleteJobFairRequirement(jobFairRequirementId);
					}
				}
				
				long result = requirementDao.saveJobFair(jobFair);
				if (result > 0) {
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@Override
	public ResponseList<JobFairDTO> getJobFairsList(JobFairDTO jobFairDTO, boolean includeInactive) {
		ResponseList<JobFairDTO> jobFairDTOList = new ResponseList<>();
		try {
			ResponseList<JobFair> jobFairList = requirementDao.getJobFairList(jobFairDTO, includeInactive);
			List<JobFairDTO> dtoList  = new LinkedList<>();
			if (jobFairList != null && !jobFairList.getData().isEmpty()) {
				for (JobFair jobFair : jobFairList.getData()) {
					dtoList.add(getJobFairDetails(jobFair));
				}
			}
			jobFairDTOList.setData(dtoList);
			jobFairDTOList.setRecordsTotal(jobFairList.getRecordsTotal());
			jobFairDTOList.setRecordsFiltered(jobFairList.getRecordsFiltered());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobFairDTOList;
	}

	@Override
	public JobFairDTO getJobFairByUUID(String uuid) {
		JobFairDTO jobFairDTO = new JobFairDTO();
		try {
			JobFair jobFair = requirementDao.getJobFairByUUID(uuid);
			if (jobFair != null) {
				jobFairDTO = getJobFairDetails(jobFair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobFairDTO;
	}

	private JobFairDTO getJobFairDetails(JobFair jobFair) {
		JobFairDTO jobFairDTO = new JobFairDTO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(jobFair.getDateFormat());
			
			jobFairDTO.setId(jobFair.getId());
			jobFairDTO.setUuid(jobFair.getUuid());
			jobFairDTO.setGeneratedId(jobFair.getGeneratedId());
			jobFairDTO.setName(jobFair.getName());
			jobFairDTO.setBudget(new BigDecimal(jobFair.getBudget()).toPlainString());
			jobFairDTO.setDescription(jobFair.getDescription());
			jobFairDTO.setStartDate(sdf.format(jobFair.getStartDate()));
			jobFairDTO.setEndDate(sdf.format(jobFair.getEndDate()));
			if(jobFair.getCreatedBy() != null) {
				jobFairDTO.setCreatedByUserId(jobFair.getCreatedBy().getId());
				jobFairDTO.setCreatedDate(jobFair.getCreatedDate());
			}
			if(jobFair.getUpdatedBy() != null) {
				jobFairDTO.setUpdatedByUserId(jobFair.getUpdatedBy().getId());
				jobFairDTO.setUpdatedDate(jobFair.getUpdatedDate());
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(jobFair.getOrgLocations().getDateFormat());
			if(jobFair.getStartDate() != null) {
				jobFairDTO.setStartDateStr(dateFormat.format(jobFair.getStartDate()));
			}
			if(jobFair.getEndDate() != null) {
				jobFairDTO.setEndDateStr(dateFormat.format(jobFair.getEndDate()));
			}
			jobFairDTO.setPostedFrom(jobFair.getPostedFrom());
			jobFairDTO.setSelectedClient(jobFair.getSelectedClient());
			jobFairDTO.setVenue(jobFair.getVenue());
			jobFairDTO.setNotifyTo(jobFair.getNotifyTo());
			jobFairDTO.setStatus(jobFair.getStatus());
			jobFairDTO.setDateFormat(jobFair.getDateFormat());
			long applicantCount = 0;
			long offeredCount = 0;
			List<JobFairRequirementsDTO> jobFairRequirementsDTOList = new LinkedList<>();
			String requirements = "";
			for (JobFairRequirements jobFairRequirements : jobFair.getJobFairRequirementList()) {
				requirements += (requirements.isEmpty() ? "" : ",") + jobFairRequirements.getFairDates();
				JobFairRequirementsDTO jobFairRequirementsDTO = new JobFairRequirementsDTO();
				jobFairRequirementsDTO.setId(jobFairRequirements.getId());
				jobFairRequirementsDTO.setJobFairId(jobFairRequirements.getJobFair().getId());
				jobFairRequirementsDTO.setJobFairName(jobFairRequirements.getJobFair().getName());
				jobFairRequirementsDTO.setRequirementId(jobFairRequirements.getRequirement().getId());
				jobFairRequirementsDTO.setRequirementTitle(jobFairRequirements.getRequirement().getJobTitle());
				applicantCount += jobFairRequirements.getRequirement().getTotalApplicants();
				RequirementSubmissionsCountDTO rsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(jobFairRequirements.getRequirement().getId(), jobFair.getUser(), jobFair.getOrgLocations(), "OFFERED");
				offeredCount += rsCount.getOffersCount();
				jobFairRequirementsDTO.setFairDates(jobFairRequirements.getFairDates());
				jobFairRequirementsDTOList.add(jobFairRequirementsDTO);
			}
			jobFairDTO.setApplicantCount(applicantCount);
			jobFairDTO.setOfferedCount(offeredCount);
			jobFairDTO.setJobFairRequirementList(jobFairRequirementsDTOList);
			jobFairDTO.setRequirementJSONData(requirements);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobFairDTO;
	}

	@Override
	public boolean changeJobFairStatus(Long jobFairId, int status, User user, OrgLocations orgLocation) {
		try {
			JobFair jobFair = requirementDao.getJobFair(jobFairId);
			jobFair.setStatus(status);
			jobFair.setUpdatedBy(user);
			jobFair.setUpdatedDate(commonUtil.orgLocationCommonDate(new Date(), orgLocation));
//			return requirementDao.changeJobFairStatus(jobFairId, status);
			long result = requirementDao.saveJobFair(jobFair);
			return result > 0; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<JobFair> getJobFairsListByRequirementId(Long requirementId) {
		List<JobFair> jobFairList = new LinkedList<>();
		try {
			jobFairList = requirementDao.getJobFairListByRequirementId(requirementId);
			if(jobFairList!=null && !jobFairList.isEmpty()) {
				for(JobFair jobFair : jobFairList) {
					SimpleDateFormat sdf = new SimpleDateFormat(jobFair.getDateFormat());
					jobFair.setStartDateString(sdf.format(jobFair.getStartDate()));
					jobFair.setEndDateString(sdf.format(jobFair.getEndDate()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobFairList;
	}
	
	

	@Override
	public List<JobFairDTO> getJobFairsDTOListByRequirementId(long requirementId) {
		List<JobFairDTO> jobFairDTOList = new LinkedList<>();
		try {
			List<JobFair> jobFairList = getJobFairsListByRequirementId(requirementId);
			if(jobFairList!=null && !jobFairList.isEmpty()) {
				for(JobFair jobFair : jobFairList) {
					jobFairDTOList.add(getJobFairDetails(jobFair));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobFairDTOList;
	}

	@Override
	public List<String> getJobFairDatesForCalendar(long userId, long orgLocationId, String dateFormat, boolean adminFlag) {
		List<String> jobFairDatesList = new LinkedList<>();
		try {
			int i = 1;
			SimpleDateFormat dateFormatSDF = new SimpleDateFormat(dateFormat);
			SimpleDateFormat convertToFormatSDF = new SimpleDateFormat("yyyy/MM/dd");
			Set<String> jobFairDatesSet = new SortedArraySet<>();
			List<String> jobFairDates = requirementDao.getJobFairDatesForCalendar(userId, orgLocationId, dateFormat, adminFlag);
			if (jobFairDates != null && !jobFairDates.isEmpty()) {
				for (String fairDate : jobFairDates) {
					for (String date : fairDate.split(",")) {
						if(!date.isEmpty()) {
							String formattedDate = convertToFormatSDF.format(dateFormatSDF.parse(date));
							jobFairDatesSet.add(date);
							
							if (!jobFairDatesList.contains(formattedDate))
								jobFairDatesList.add(formattedDate);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobFairDatesList;
	}

	@Override
	public List<Requirement> getJobFairRequirementsByDate(long userId, long orgLocationId, String dateFormat, String date, boolean adminFlag) {
		List<Requirement> requirementList = new LinkedList<>();
		try {
			requirementList = requirementDao.getRequirementsByJobFairDate(userId, orgLocationId, dateFormat, date, adminFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return requirementList;
	}
	
	@Override
	public List<Requirement> getRequirementsListByJobFairId(long userId, long orgLocationId, long jobFairId, boolean adminFlag) {
		try {
			return requirementDao.getRequirementsListByJobFairId(userId, orgLocationId, jobFairId, adminFlag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getJobFairDatesByRequirementIdAndJobFairId(long requirementId, long jobFairId) {
		try {
			return requirementDao.getJobFairDatesByRequirementIdAndJobFairId(requirementId, jobFairId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void assignGeneratedIdToJobFairs() {
		try {
			System.out.println("---- Adding Generated Id for JobFair starts -----");

			List<JobFair> jobFairList = requirementDao.getJobFairWithNullGeneratedIdList();
			for (JobFair jobFair : jobFairList) {
				String generatedId = getGeneratedJobFairId(jobFair.getOrgLocations());
				jobFair.setGeneratedId(generatedId);
				requirementDao.saveJobFair(jobFair);
				System.out.println("Added " + generatedId + " to JobFair '" + jobFair.getName()+"'");
			}

			System.out.println("---- Adding Generated Id for JobFair Ends -----");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
        
	@Override
	public Boolean forceDeleteForSelectedJobs(String[] uuids) throws Exception
	{
		Boolean flag = requirementDao.forceDeleteForSelectedJobs(uuids);
		return flag;
	}

	@Override
	public List<RequirementIncentive> getRequirementIncentiveListByRequirementId(long requirementId) throws Exception {
		return requirementDao.getRequirementIncentiveListByRequirementId(requirementId);
	}  
	
	@Override
	public void deleteRequirementIncentiveByRequirementIdOrIds(long requirementId, String ids) throws Exception {
		 requirementDao.deleteRequirementIncentiveByRequirementIdOrIds(requirementId, ids);
	}
	
	@Override
	public File getJobFairListOfExcel(List<JobFairDTO> jobFairDTOList, User user, OrgLocations orgLocation) {
		File file = null;
		try {
			Workbook createExcelworkbook = new XSSFWorkbook();
			DateFormat dateFormat = new SimpleDateFormat(orgLocation.getDateFormat());
			Sheet createExcelsheet = createExcelworkbook
					.createSheet(orgLocation.getOrgLocationName() + "_" + dateFormat.format(new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook);
			int column = 0;
			for (JobFairDTO reportList : jobFairDTOList) {
				writeRow = createExcelsheet.createRow(column + 1);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow, reportList, orgLocation, user);
				column++;
			}
			file = File.createTempFile(orgLocation.getOrgLocationName() + "_JobFair_" + dateFormat.format(new Date()), ".xlsx");
			file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			createExcelworkbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook) {
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String[] headers = Constants.JOBFAIR_EXPORT_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style, headers, 0);
		return createExcelsheet;
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, JobFairDTO jobFairDTO, OrgLocations orgLocation,
			User loginUser) throws Exception {

		writeRow.createCell(0).setCellValue(jobFairDTO.getGeneratedId() != null && !jobFairDTO.getGeneratedId().isEmpty()
						? jobFairDTO.getGeneratedId()
						: "----");
		createExcelsheet.autoSizeColumn(0);

		writeRow.createCell(1).setCellValue(jobFairDTO.getName() != null && !jobFairDTO.getName().isEmpty() 
						? jobFairDTO.getName() 
						: "----");
		createExcelsheet.autoSizeColumn(1);

		writeRow.createCell(2).setCellValue(jobFairDTO.getStartDate() != null && !jobFairDTO.getStartDate().isEmpty()
						? jobFairDTO.getStartDate()
						: "----");
		createExcelsheet.autoSizeColumn(2);

		writeRow.createCell(3).setCellValue(jobFairDTO.getEndDate() != null && !jobFairDTO.getEndDate().isEmpty() 
						? jobFairDTO.getEndDate()
						: "----");
		createExcelsheet.autoSizeColumn(3);

		writeRow.createCell(4).setCellValue(jobFairDTO.getJobFairRequirementList() != null && !jobFairDTO.getJobFairRequirementList().isEmpty()
						? jobFairDTO.getJobFairRequirementList().size()
						: 0);
		createExcelsheet.autoSizeColumn(4);

		writeRow.createCell(5).setCellValue(jobFairDTO.getApplicantCount());
		createExcelsheet.autoSizeColumn(5);

		writeRow.createCell(6).setCellValue(jobFairDTO.getOfferedCount());
		createExcelsheet.autoSizeColumn(6);

		writeRow.createCell(7).setCellValue(jobFairDTO.getVenue() != null && !jobFairDTO.getVenue().isEmpty() 
						? jobFairDTO.getVenue()
						: "----");
		createExcelsheet.autoSizeColumn(7);

		writeRow.createCell(8).setCellValue(jobFairDTO.getStatus() == 1 ? "OPEN" : "CLOSE");
		createExcelsheet.autoSizeColumn(8);

		return createExcelsheet;
	}
	
	@Override
	public ResponseList<Requirement> getAssignedJobsPerformanceList(Long orgLocationId, String userType, Long userId,
			String userCode,Date startDateUTC,Date endDateUTC,JobFilterDTO jobFilterDTO) throws Exception {
		return requirementDao.getAssignedJobsPerformanceList(orgLocationId,userType,userId,userCode,startDateUTC,endDateUTC,jobFilterDTO);
	}

	//-----------------------------code for email sending when job status changed and mail send to primaryRecruiter--------------------------------------------------
	
	@Override
	public void sendMailToPrimaryRecruiter(Requirement requirement,User loginUser,User toUser) throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(toUser.getFirstName());
		emailMessage.setLastName(toUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(
				msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("jobName", requirement.getJobTitle());
		} else {
			globalVarsMap.put("jobName", "--");
		}
		
		if (requirement.getRequirementSubmissionPreviousStatus() != null) {
			globalVarsMap.put("PreviousStatus", requirement.getRequirementSubmissionPreviousStatus().toString());
		} else {
			globalVarsMap.put("PreviousStatus", "--");
		}
		
		if (requirement.getRequirementSubmissionStatus() != null) {
			globalVarsMap.put("CurrentStatus", requirement.getRequirementSubmissionStatus().toString());
		} else {
			globalVarsMap.put("CurrentStatus", "--");
		}
		
		if (requirement.getOrgLocationId().getOrgLocationName() != null && !"".equalsIgnoreCase(requirement.getOrgLocationId().getOrgLocationName())) {
			globalVarsMap.put("LoginOrgName", requirement.getOrgLocationId().getOrgLocationName());
		} else {
			globalVarsMap.put("LoginOrgName", "--");
		}
		
		if (toUser.getLastName() != null && !"".equalsIgnoreCase(toUser.getLastName())) {
			StringBuilder sb = new StringBuilder();
			globalVarsMap.put("toUser", sb.append(toUser.getFirstName()).append(" ").append(toUser.getLastName()).toString());
		} else {
			globalVarsMap.put("toUser", toUser.getFirstName());
		}
		
		
		if (loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder loginSb = new StringBuilder();
			globalVarsMap.put("loginUser", loginSb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("loginUser", loginUser.getFirstName());
		}

		
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("loginLink", linkBuild.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>")
				.toString());
		
	for(RequirementAssignTo reqAssignto : requirement.getRequirementAssignTo()) {
		
	}
		 
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.CHANGE_JOB_STATUS_EMAIL, Constants.ENTITY_TYPE_EMAIL);
		// covert subject,body and signature

		 

		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getSubject(), globalVarsMap,
				notificationScenario);
		notificationScenario.setBody(notificationbody);
		
		UserRole toUserOrg = userRoleService.getUserByUserId(toUser.getId());

		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, requirement.getOrgLocationId().getOrg(), requirement.getOrgLocationId(),Constants.CHANGE_JOB_STATUS_EMAIL, new ArrayList<EmailAttachment>());

		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(atsUrl).append("/")
				.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, toUserOrg.getOrgLocations(), notificationScenario,
				new HashMap<String, String>());	
	}
	
	@Override
	public List<Requirement> getAllReleasedJobsForVendorBasedOnOrgRelationId(Long orgLocationId, Long orgRelationId) {
		try {
			return requirementDao.getAllReleasedJobsForVendorBasedOnOrgRelationId(orgLocationId, orgRelationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Requirement> getAllReleasedJobsBasedOnSpocId(long spocId) {
		try {
			return requirementDao.getAllReleasedJobsBasedOnSpocId(spocId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getQualityCandidatesForVendors(List<Long> requirementIdList, long orgRelationId, long spocId, String status) {
		try {
			return requirementDao.getQualityCandidatesForVendors(requirementIdList, orgRelationId, spocId, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public ApplicantDeclineDTO getApplicantDeclineCountsForVendor(List<Long> requirementIdList, long orgRelationId, long spocId) {
		ApplicantDeclineDTO applicantDeclineDTO = new ApplicantDeclineDTO();
		try {
		 	List<RequirementSubmission> requirementSubmissionList = requirementDao.getApplicantDeclineCountsForVendor(requirementIdList, orgRelationId, spocId, "REJECTED");
		 	if(requirementSubmissionList != null && !requirementSubmissionList.isEmpty()) {
		 		int wrongSkillsCount = requirementSubmissionList.stream().filter(rs->rs.getRejectedCategoryType() != null && rs.getRejectedCategoryType().equalsIgnoreCase("Wrong Skill Sets")).collect(Collectors.toList()).size();
		 		int unsubitablePersonalityCount = requirementSubmissionList.stream().filter(rs->rs.getRejectedCategoryType() != null && rs.getRejectedCategoryType().equalsIgnoreCase("Unsuitable Personality")).collect(Collectors.toList()).size();
		 		int badFitCount = requirementSubmissionList.stream().filter(rs->rs.getRejectedCategoryType() != null && rs.getRejectedCategoryType().equalsIgnoreCase("Bad Fit")).collect(Collectors.toList()).size();
		 		int salaryCount = requirementSubmissionList.stream().filter(rs->rs.getRejectedCategoryType() != null && rs.getRejectedCategoryType().equalsIgnoreCase("Salary")).collect(Collectors.toList()).size();
		 		int interviewRejectionCount = requirementSubmissionList.stream().filter(rs->rs.getRejectedCategoryType() != null && rs.getRejectedCategoryType().equalsIgnoreCase("Interview Rejection")).collect(Collectors.toList()).size();
		 		int otherCount = requirementSubmissionList.stream().filter(rs->rs.getRejectedCategoryType() != null && rs.getRejectedCategoryType().equalsIgnoreCase("Other")).collect(Collectors.toList()).size();
		 		int totalCount = wrongSkillsCount + unsubitablePersonalityCount + badFitCount + salaryCount + interviewRejectionCount + otherCount;
		 		
				applicantDeclineDTO.setSkillsCount(new BigInteger(wrongSkillsCount + ""));
				applicantDeclineDTO.setUnsuitableCount(new BigInteger(unsubitablePersonalityCount + ""));
				applicantDeclineDTO.setBadfitCount(new BigInteger(badFitCount + ""));
				applicantDeclineDTO.setSalaryCount(new BigInteger(salaryCount + ""));
				applicantDeclineDTO.setInterviewCount(new BigInteger(interviewRejectionCount + ""));
				applicantDeclineDTO.setOtherCount(new BigInteger(otherCount + ""));
				applicantDeclineDTO.setTotalCount(new BigInteger(totalCount + ""));
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicantDeclineDTO;
	}
	
	@Override
	public ApplicantsBackoutDTO getApplicantBackoutCountsForVendor(List<Long> requirementIdList, long orgRelationId, long spocId) {
		ApplicantsBackoutDTO applicantBackoutDTO = new ApplicantsBackoutDTO();
		try {
		 	List<RequirementSubmission> requirementSubmissionList = requirementDao.getApplicantDeclineCountsForVendor(requirementIdList, orgRelationId, spocId, "BACKOUT");
		 	if(requirementSubmissionList != null && !requirementSubmissionList.isEmpty()) {
		 		int currentCompanyCount = requirementSubmissionList.stream().filter(rs->rs.getBackOutCategoryType() != null && rs.getBackOutCategoryType().equalsIgnoreCase("Current Company")).collect(Collectors.toList()).size();
		 		int compensationCount = requirementSubmissionList.stream().filter(rs->rs.getBackOutCategoryType() != null && rs.getBackOutCategoryType().equalsIgnoreCase("Compensation")).collect(Collectors.toList()).size();
		 		int betterOfferCount = requirementSubmissionList.stream().filter(rs->rs.getBackOutCategoryType() != null && rs.getBackOutCategoryType().equalsIgnoreCase("Better Offer")).collect(Collectors.toList()).size();
		 		int coldFeetCount = requirementSubmissionList.stream().filter(rs->rs.getBackOutCategoryType() != null && rs.getBackOutCategoryType().equalsIgnoreCase("Cold Feet")).collect(Collectors.toList()).size();
		 		int personalCount = requirementSubmissionList.stream().filter(rs->rs.getBackOutCategoryType() != null && rs.getBackOutCategoryType().equalsIgnoreCase("Personal")).collect(Collectors.toList()).size();
		 		int otherCount = requirementSubmissionList.stream().filter(rs->rs.getBackOutCategoryType() != null && rs.getBackOutCategoryType().equalsIgnoreCase("Other")).collect(Collectors.toList()).size();
				int totalCount = currentCompanyCount + compensationCount + betterOfferCount + coldFeetCount + personalCount + otherCount;
		 		applicantBackoutDTO.setCurrentCompany(new BigInteger(currentCompanyCount + ""));
				applicantBackoutDTO.setCompensation(new BigInteger(compensationCount + ""));
				applicantBackoutDTO.setBetterOffer(new BigInteger(betterOfferCount + ""));
				applicantBackoutDTO.setColdFeet(new BigInteger(coldFeetCount + ""));
				applicantBackoutDTO.setPersonal(new BigInteger(personalCount + ""));
				applicantBackoutDTO.setOther(new BigInteger(otherCount + ""));
		 		applicantBackoutDTO.setTotalCount(new BigInteger(totalCount + ""));
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicantBackoutDTO;
	}

	@Override
	public long getVendorSubmittedCandidates(long requirementId, long orgRelationId, long spocId, String status) {
		return requirementDao.getVendorSubmittedCandidates(requirementId, orgRelationId, spocId, status);
	}
	
	
}
















