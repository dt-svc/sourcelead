package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.SubmissionTemplateDao;
import com.bis.sourcelead.dto.RequirementSearchDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RecommendedCustomizeScore;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.HIERARCHY_TYPE;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RecommendedCustomizeScoreService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TeamMembersService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
	private TeamService teamService;
	
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
    private ProjectsService projectsService;
	
	
	 @Autowired
	 private  RequirementSubmissionService requirementSubmissionService;
	 
	 /*@Autowired
	 private CandidateSkillsService candidateSkillsService;*/
	 
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
	 private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Autowired
	private RecommendedCustomizeScoreService recommendedCustomizeScoreService;
	
	@Autowired
	private TeamMembersService teamMembersService;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private ProjectsDao projectsDao;
	
	/* @Override
		public String getRequirementId(OrgLocations orgLocations) {
			
	    	Long number = 000000001L;
			String requirementId = null;
			Requirement checkNumber = requirementDao.checkRequirementIdNumberInDatebase(orgLocations.getId());
			if(checkNumber == null || checkNumber.getGeneratedId().isEmpty()){
				requirementId = number.toString();
			}else{
			String reqIdNumber = checkNumber.getGeneratedId();
			String reqIdNum = reqIdNumber.substring(2, reqIdNumber.length());
			Long reqId = Long.parseLong(reqIdNum)+1;
			requirementId = reqId.toString();
			}
			
	        final DecimalFormat decimalFormat = new DecimalFormat("000000000");                
	        String ConvetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
	        StringBuffer sb = new StringBuffer();
			return sb.append(Constants.JOB_ENTITY).append(ConvetredRequirementsId).toString();
			
		}*/
	 
	 public String getRequirementId(OrgLocations orgLocations) {
	    	String companyShortCode = null;
	    	Long number = 000000001L;
			String requirementId = null;
			Requirement checkNumber = requirementDao.checkRequirementIdNumberInDatebase(orgLocations.getId());
			if(checkNumber == null || checkNumber.getGeneratedId()== null  || checkNumber.getGeneratedId().isEmpty()){
				requirementId = number.toString();
			}else{
			String reqIdNumber = checkNumber.getGeneratedId();
			int count =0;
			for(int i=0;i< reqIdNumber.length();i++){
				char ch = reqIdNumber.charAt(i);
				if(Character.isAlphabetic(ch)){
					count++;
				}
			}
			String reqIdNum = reqIdNumber.substring(count, reqIdNumber.length());
			Long reqId = Long.parseLong(reqIdNum)+1;
			requirementId = reqId.toString();
			}
			
			final DecimalFormat decimalFormat = new DecimalFormat("000000000");                
	        String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
	        StringBuffer sb = new StringBuffer();
	        if(orgLocations.getGeneratedUserId() != null && !"".equalsIgnoreCase(orgLocations.getGeneratedUserId())){
	        	 companyShortCode = orgLocations.getGeneratedUserId();
	        }
			return sb.append(companyShortCode).append(Constants.JOB_ENTITY).append(convetredRequirementsId).toString();
			
		}
	 
	/**
	 * @author prasad k
	 * @since  aug 26 2016
	 * @version 0.0.1
	 * 
	 * This method is used to store/save Requirement in Requirement table.
	 *  
	 *  
	 * 
	 * @param jsonObj This is the first paramter to storeRequirement method
	 * @param orgLocations This is the second paramter to storeRequirement method
	 * @param user This is the third paramter to storeRequirement method
	 * @param multiFile This is the fourth paramter to storeRequirement method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	 */
	
	@Override
	public Requirement storeRequirement(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception {
		
		    Requirement requirement = null;
	        String client="";
	        String requirementClient="";
	        StringBuilder location = new StringBuilder();

		    
	        if (jsonObj.has("requirementId") && !(jsonObj.getString("requirementId").isEmpty())){
	        	
	        	requirement = requirementDao.getRequirementByRequirementId(Long.parseLong(jsonObj.getString("requirementId")));
				requirement.setId(requirement.getId());
				requirement.setUpdatedBy(user.getUserCode());
				requirement.setUpdatedDate(commonUtil.currentDate());
			}else{
				requirement = new Requirement();
				requirement.setCreatedDate(commonUtil.currentDate());
				requirement.setTotalApplicants(0);
				requirement.setCreatedBy(user);
				requirement.setUpdatedBy(user.getUserCode());
				requirement.setUpdatedDate(commonUtil.currentDate());
				requirement.setRequirementUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());
			}
		    
			if(orgLocations != null){
				requirement.setOrgLocationId(orgLocations);
			}
			
			if(jsonObj.has("jobTitle")){
				if(!"".equalsIgnoreCase(jsonObj.getString("jobTitle"))){
					requirement.setJobTitle(jsonObj.getString("jobTitle"));
				}
				
			}if(jsonObj.has("jobType") ){	
				requirement.setJobType(jsonObj.getString("jobType"));
			}if(jsonObj.has("jobOtherType") && jsonObj.has("jobType") && "Other".equalsIgnoreCase(jsonObj.getString("jobType"))){
					requirement.setOtherJobType(jsonObj.getString("jobOtherType"));
			}else{
					requirement.setOtherJobType(null);
			}if(jsonObj.has("jobId")){
					requirement.setJobId(jsonObj.getString("jobId"));
			}if(jsonObj.has("positions")){
					requirement.setPositions(jsonObj.getString("positions"));
			}if(jsonObj.has("countryReq")){
			requirement.setCountry(jsonObj.getString("countryReq"));
			location.append(jsonObj.getString("countryReq"));
			location.append(",");
			}if(jsonObj.has("state")){
				if(!jsonObj.getString("state").isEmpty()){
					requirement.setState(jsonObj.getString("state"));
					location.append(jsonObj.getString("state"));
					location.append(",");
				}else{
					location.append("--").append(",");
				}
			}if (jsonObj.has("city")) {
				if (!jsonObj.getString("city").isEmpty()) {
					requirement.setCity(jsonObj.getString("city"));
					location.append(jsonObj.getString("city"));
					location.append(",");
				} else {
					location.append("--").append(".");
				}
			}
			if(jsonObj.has("location")){
				requirement.setLocation(jsonObj.getString("location"));
			}
			if(jsonObj.has("zipCode")){
				requirement.setZipCode(jsonObj.getString("zipCode"));
			}
			if(jsonObj.has("maxExp")){
					requirement.setMaxExp(jsonObj.getString("maxExp"));
			}
			if(jsonObj.has("minExp")){
					requirement.setMinExp(jsonObj.getString("minExp"));
			}if(jsonObj.has("rate")){
					requirement.setRate(jsonObj.getString("rate"));
			}if(jsonObj.has("rateType")){
					requirement.setRateType(jsonObj.getString("rateType"));
			}if(jsonObj.has("description")){
					//requirement.setDescription(java.net.URLDecoder.decode(jsonObj.getString("description"), "utf-8"));
					requirement.setDescription(jsonObj.getString("description"));	
			}if(jsonObj.has("coolingOff")){
				requirement.setReqCoolingOffPeriod(jsonObj.getString("coolingOff"));
			}if(jsonObj.has("industryName")){
				requirement.setIndustryName(jsonObj.getString("industryName"));
			}
			
			if(jsonObj.has("projectDurationMonths")){
				requirement.setProjectDurationMonths(jsonObj.getString("projectDurationMonths"));
			}
			
			
			if(jsonObj.has("projectDurationYears")){
				requirement.setProjectDurationYears(jsonObj.getString("projectDurationYears"));
			}
			
			
			if(jsonObj.has("closedDate") && !isNullOrEmpty(jsonObj.getString("closedDate")) && !"".equalsIgnoreCase(jsonObj.getString("closedDate"))){
				try {
					 // SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
					Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
					Date startTime =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),jsonObj.getString("closedDate"));
					String currentTimeStr = Constants.TZ_DF.format(startTime);  

					String startTimeUTC= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), "UTC", currentTimeStr);
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					Date convertedDate = TZ_DF_TS.parse(startTimeUTC);
					requirement.setClosedDate(convertedDate);
					
					} catch (Exception e) {
					  e.printStackTrace();
					}
			}
			
			
			if(jsonObj.has("noticePeriod")){
				requirement.setNoticePeriod(jsonObj.getString("noticePeriod"));	
				String noticePeriod =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("noticePeriod");
					noticePeriod = jsonArray.toString();
				}
				catch(JSONException e){
					noticePeriod =jsonObj.getString("noticePeriod");
				}
				noticePeriod = noticePeriod.replaceAll("\\[", "").replaceAll("\\]","");
				noticePeriod = noticePeriod.replaceAll("\"", "");
		  	    requirement.setNoticePeriod(noticePeriod);
			}
			if(jsonObj.has("travelAllowance")){
				requirement.setTravelAllowance(jsonObj.getString("travelAllowance"));
			}
			if(jsonObj.has("currencyCode")){
				if(!"".equalsIgnoreCase(jsonObj.getString("currencyCode"))){
					requirement.setCurrencyCode(jsonObj.getString("currencyCode"));
				}
				
			}
			
				if(jsonObj.has("maxRate")){
					requirement.setMaxRate(jsonObj.getString("maxRate"));
			}
			if(jsonObj.has("priority")){
				if(!"".equalsIgnoreCase(jsonObj.getString("priority"))){
					requirement.setPriority(jsonObj.getString("priority"));
				}
				
			}
			
			if(jsonObj.has("isTemplate") && "Y".equalsIgnoreCase(jsonObj.getString("isTemplate"))){
				/*
				 * This is to store the Requirement Template  as another Requirement
				 */
			   storeRequirementTemplate(jsonObj, orgLocations, user);
			}
			
			if(jsonObj.has("skills")){
				
				String skillId =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("skills");
					skillId = jsonArray.toString();
				}
				catch(JSONException e){
					skillId =jsonObj.getString("skills");
				}
				skillId = skillId.replaceAll("\\[", "").replaceAll("\\]","");
				
				
				skillId = skillId.replaceAll("\"", "");
		  	    requirement.setSkills(skillId);
		  	 
			}else{
				requirement.setSkills(null);
			}
			
             if(jsonObj.has("visa")){
				
				String visaList =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("visa");
					visaList = jsonArray.toString();
				}
				catch(JSONException e){
					visaList =jsonObj.getString("visa");
				}
				visaList = visaList.replaceAll("\\[", "").replaceAll("\\]","");
				visaList = visaList.replaceAll("\"", "");
		  	    requirement.setVisaType(visaList);
		  	 
			}else{
				requirement.setVisaType(null);
			}
			
			if(jsonObj.has("education")){
				
             String educationId =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("education");
					educationId = jsonArray.toString();
				}
				catch(JSONException e){
					educationId =jsonObj.getString("education");
				}
				educationId = educationId.replaceAll("\\[", "").replaceAll("\\]","");
				educationId = educationId.replaceAll("\"", "");
				
		  	    requirement.setEducation(educationId);
			
			 }
			
			
			 if(multiFile != null && multiFile.getOriginalFilename() != null && !multiFile.getOriginalFilename().isEmpty()){
				 requirement.setDocFile(multiFile.getBytes());
				 requirement.setFileName(multiFile.getOriginalFilename());
				 requirement.setFileContentType(multiFile.getContentType());
			 }else{
				    if(jsonObj.has("templateId") && StringUtils.isNotEmpty(jsonObj.getString("templateId"))){
						String templateId =  jsonObj.getString("templateId");
						Requirement templateRequirement = getRequirementByRequirementId(Long.parseLong(templateId));
						requirement.setDocFile(templateRequirement.getDocFile());
						requirement.setFileName(templateRequirement.getFileName());
						requirement.setFileContentType(templateRequirement.getFileContentType());
						requirement.setIsClientJob("N");
					 }
			 }	
			 
			 
			if(jsonObj.has("isClient") && jsonObj.getString("isClient").equalsIgnoreCase("Y") && StringUtils.isNotEmpty(jsonObj.getString("isClient"))){
					requirement.setIsClientJob(jsonObj.getString("isClient"));
			}else {
				requirement.setIsClientJob("N");
			}
			
			
			
			if(jsonObj.has("isreceivingApplicants") && jsonObj.getString("isreceivingApplicants").equalsIgnoreCase("Y") && StringUtils.isNotEmpty(jsonObj.getString("isreceivingApplicants"))){
				requirement.setIsreceivingApplicants(jsonObj.getString("isreceivingApplicants"));
		}else {
			requirement.setIsreceivingApplicants("N");
		}
			
		if(jsonObj.has("selectClient") && StringUtils.isNotEmpty(jsonObj.getString("selectClient")) &&  jsonObj.has("isClient") && jsonObj.getString("isClient").equalsIgnoreCase("Y")){
		requirementClient = (jsonObj.getString("isClient"));
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(Long.parseLong(jsonObj.getString("selectClient").split(",")[0]));
		requirement.setClientId(orgRelations);
		client = jsonObj.getString("selectClient").split(",")[0];
		
	}
			if(jsonObj.has("selectContact") && StringUtils.isNotEmpty(jsonObj.getString("selectContact"))){
				OrgSpoc orgSpoc = new OrgSpoc();
				orgSpoc.setId(Long.parseLong(jsonObj.getString("selectContact")));
				requirement.setSpocId(orgSpoc);
			}
			if(jsonObj.has("clientCurrencyCode") && StringUtils.isNotEmpty(jsonObj.getString("clientCurrencyCode"))){
				requirement.setClientCurrencyCode(jsonObj.getString("clientCurrencyCode"));
			}
		 	if(jsonObj.has("clientRate") && StringUtils.isNotEmpty(jsonObj.getString("clientRate"))){
		 		requirement.setClientRate(jsonObj.getString("clientRate"));
			}
			if(jsonObj.has("clientRateType") && StringUtils.isNotEmpty(jsonObj.getString("clientRateType"))){
				requirement.setClientRateType(jsonObj.getString("clientRateType"));
			} 
			if( jsonObj.has("isClient") && jsonObj.has("clientSubmissionForm")){
				
				if(jsonObj.getString("isClient").equalsIgnoreCase("Y") && jsonObj.has("isClient") && StringUtils.isNotEmpty(jsonObj.getString("isClient")) && jsonObj.has("clientSubmissionForm") && StringUtils.isNotEmpty(jsonObj.getString("clientSubmissionForm"))){

				SubmissionTemplate submissionTemplate = submissionTemplateDao.getSubmissionTemplateById(Long.parseLong(jsonObj.getString("clientSubmissionForm")));
				submissionTemplate.setId(Long.parseLong(jsonObj.getString("clientSubmissionForm")));
				
				requirement.setClientSubmissionTemplateId(submissionTemplate);
				requirement.setClientSubmissionTemplateJsonString(submissionTemplate.getJsonString());
				//saving submission Temmplate of Client to  InternalDetails Submission Template  defaultly 
				requirement.setSubmissionTemplateId(submissionTemplate);
				requirement.setSubmissionTemplateJsonString(submissionTemplate.getJsonString());
				
				}
			}else if(jsonObj.has("applicationFrom") && StringUtils.isNotEmpty(jsonObj.getString("applicationFrom"))){
				
				SubmissionTemplate submissionTemplate = submissionTemplateDao.getSubmissionTemplateById(Long.parseLong(jsonObj.getString("applicationFrom")));
				submissionTemplate.setId(Long.parseLong(jsonObj.getString("applicationFrom")));
				requirement.setSubmissionTemplateId(submissionTemplate);
				requirement.setSubmissionTemplateJsonString(submissionTemplate.getJsonString());
			}
			
			if(jsonObj.has("releaseToAllUsers") && "Y".equalsIgnoreCase(jsonObj.getString("releaseToAllUsers"))){
				requirement.setReleaseJobToAllUsers(jsonObj.getString("releaseToAllUsers"));
			}
			
			requirement.setStatus(Constants.TEAM_ACTIVE);
			
			
			//requirement status 
			if(jsonObj.has("saveType")){   
				
				if(jsonObj.getString("saveType").equalsIgnoreCase("draft")){
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.DRAFT);
				}
				if(jsonObj.getString("saveType").equalsIgnoreCase("save")){
					requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
				}
				if(jsonObj.getString("saveType").equalsIgnoreCase("submitForApproval")){
					
					//if the Requirement is in Open Stage
					if(null != requirement.getId() ){
							if("OPEN".equalsIgnoreCase(requirement.getRequirementSubmissionStatus().getValue())){
								requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
							}else{
								requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED);	
							}
					}else{
						requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED);
					}
					
				}
				
			}
			
			//approval mechnism
			HashSet<RequirementApproverHierarchy> requirementApproverHierarchyList = new HashSet<RequirementApproverHierarchy>();
			//approval saving for sequential

              if(jsonObj.has("parallelApprover")){
				
				String parallelApprovers =null;
				try{
					JSONArray jsonArray = jsonObj.getJSONArray("parallelApprover");
					parallelApprovers = jsonArray.toString();
				}
				catch(JSONException e){
					parallelApprovers =jsonObj.getString("parallelApprover");
				}
				parallelApprovers = parallelApprovers.replaceAll("\\[", "").replaceAll("\\]","");
				parallelApprovers = parallelApprovers.replaceAll("\"", "");
				
			     String[] approvers = parallelApprovers.split(",");
			  	  
			     
			     //to delete the  unselected approvers in edit Mode
				  removedApproversInRequirementForParallel(approvers,requirement);	
				  
			     
			     for(String approver : approvers){
					  
					  RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
					  // commented as it is not in use by David Raju
					  //User approverUser = userService.getUserById(Long.parseLong(approver));
					  
					 		  
					 
					   
					    // if  the Requirement is in Edit Mode
					    if(requirement.getId() != null){
					    	   rah =  requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirement.getId(), Long.parseLong(approver));
								// In Edit Mode if We add New Approvers In The Requirement
						    	 if(rah == null){
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
	                             }else{
	                            	   rah.setCreatedDate(commonUtil.currentDate());
	 								   rah.setCreatedBy(user.getUserCode());
	 								   rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
	 								   rah.setUpdatedBy(user.getUserCode());
	 								   rah.setUpdatedDate(commonUtil.currentDate());
	                             }
					    }else{
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
			//approval saving for parallel
			if(jsonObj.has("sequentialApprover1") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover1"))){
				 String approverNames = jsonObj.getString("sequentialApprover1");
				  if(jsonObj.has("sequentialApprover1") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover1"))){
					  User approverUser = userService.getUserById(Long.parseLong(approverNames));
					  RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
					  
					  //to remove the unselected approvers in Dropdown
					  removedApproversInRequirementForSequential(approverUser,requirement,Constants.LEVEL1);
					  
					  User approverUserId = new User();
					  approverUserId.setId(Long.parseLong(jsonObj.getString("sequentialApprover1")));
					  rah.setApproverUserId(approverUserId);
					  rah.setRequirementId(requirement);
					  
					  rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
					  rah.setLevel(1);
					  
					  rah.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
					  
					  rah.setCreatedDate(commonUtil.currentDate());
					  rah.setCreatedBy(user.getUserCode());
					  rah.setUpdatedBy(user.getUserCode());
					  rah.setUpdatedDate(commonUtil.currentDate());
					  
					  requirementApproverHierarchyList.add(rah);
				  }
				  String approverNames2 = jsonObj.getString("sequentialApprover2");
				  if(jsonObj.has("sequentialApprover2") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover2"))){
					  
					  User approverUsersabc = userService.getUserById(Long.parseLong(approverNames2));
					  RequirementApproverHierarchy rah = new RequirementApproverHierarchy();
					  
					  //to remove the unselected approvers in Dropdown
					  removedApproversInRequirementForSequential(approverUsersabc,requirement,Constants.LEVEL2);
					  
					  
					  User approverUserId = new User();
					  approverUserId.setId(Long.parseLong(jsonObj.getString("sequentialApprover2")));
					  				  
					  rah.setApproverUserId(approverUserId);
					  
					
					  
					  rah.setRequirementId(requirement);
					  
					  rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.NEW);
					  rah.setLevel(2);
					  
					  rah.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
					  rah.setCreatedDate(commonUtil.currentDate());
					  rah.setCreatedBy(user.getUserCode());
					  rah.setUpdatedBy(user.getUserCode());
					  rah.setUpdatedDate(commonUtil.currentDate());
					  
					  requirementApproverHierarchyList.add(rah);
				  }
				  
				 
			}
			
			if(jsonObj.has("parallelApprover") || (jsonObj.has("sequentialApprover1") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover1")))){
				requirement.setApproverHierarchy(Constants.YES);
			}else{
				requirement.setApproverHierarchy(Constants.NO);
			}
			
			if(!jsonObj.getString("saveType").equalsIgnoreCase("draft")){
			requirement.setRequirementApprover(requirementApproverHierarchyList);
			}
			
			if(jsonObj.has("manager") && StringUtils.isNotEmpty(jsonObj.getString("manager"))){
				    User managerUserId = new User();
				    managerUserId.setId(Long.parseLong(jsonObj.getString("manager")));
				    requirement.setManagerUserId(managerUserId);
			}
			
			if(jsonObj.has("priority") && StringUtils.isNotEmpty(jsonObj.getString("priority"))){
				  requirement.setPriority(jsonObj.getString("priority"));
			} 
			
			//storing assigned team START (Venkat Kesav)
			

			List<RequirementAssignTo> oldAssigntoTeamList= null;
			if(requirement.getId()!= null){
			oldAssigntoTeamList = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(requirement.getId(), orgLocations.getId());
			}

			
			HashSet<RequirementAssignTo> requirementAssignToList = new HashSet<RequirementAssignTo>();
			if (jsonObj.has("assignedToMaxCount")){
				String assignedToMaxCount = jsonObj.getString("assignedToMaxCount");
				int teamCount = 0;
				ArrayList<String> assignedTeamId = new ArrayList<String>();
				if(jsonObj.has("assignedTeamId") && StringUtils.isNotEmpty(jsonObj.getString("assignedTeamId")) && !"undefined".equalsIgnoreCase(jsonObj.getString("assignedTeamId"))){
					String [] assignedTeamAndRoleIds = jsonObj.getString("assignedTeamId").split(",");
					for(String assignedTeamOrRoleId : assignedTeamAndRoleIds){
						if(!assignedTeamOrRoleId.contains("role") && !assignedTeamOrRoleId.contains("All")){
							assignedTeamId.add(assignedTeamOrRoleId);
							teamCount++;
				    	}
					}
				}
				
				if(StringUtils.isNotEmpty(assignedToMaxCount)){
					int MaxCount = Integer.parseInt(assignedToMaxCount);
						for (int c = 1; c < MaxCount; c++) {
							RequirementAssignTo  requirementAssignTo;
							//User assUser = userService.getUserById(Long.parseLong(jsonObj.getString("assignUserId"+c)));
							
							String [] selectedUser = jsonObj.getString("assignUserId"+c).split(",");
							
							//### to skip duplicate user IDs START>>>>>
							boolean userExists = false;
							for(RequirementAssignTo rat : requirementAssignToList){
								userExists = rat.getAssignUserId().getId().equals(Long.parseLong(selectedUser[0]));
								if(userExists){
									break;
								}
							}
							if(userExists){
								continue;
							}
							//to skip duplicate user IDs END ######
							
							User assignedUser = new User();
							assignedUser.setId(Long.parseLong(selectedUser[0]));
							
							if(requirement.getId() !=null){
								 requirementAssignTo = requirementAssignToService.getRequirementAssignToByRequirementIdandAssignedUserId(requirement.getId(), assignedUser.getId());
								if(requirementAssignTo == null){
									requirementAssignTo = new RequirementAssignTo();
								}
							}else{
								requirementAssignTo = new RequirementAssignTo();
							}
							
							Team assignedTeam = null;
							if(null != selectedUser[1] && !selectedUser[1].equalsIgnoreCase("") && !selectedUser[1].equalsIgnoreCase("undefined")){
								assignedTeam = new Team();
								assignedTeam.setId(Long.parseLong(selectedUser[1]));
							}
							requirementAssignTo.setTeamId(assignedTeam);
							requirementAssignTo.setAssignUserId(assignedUser);
							requirementAssignTo.setRequirementId(requirement);
							UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(assignedUser.getId(),orgLocations.getId());
							requirementAssignTo.setRoleId(userRole.getRoles());
							requirementAssignTo.setCreatedDate(commonUtil.currentDate());
							requirementAssignTo.setCreatedBy(user.getUserCode());
							requirementAssignTo.setUpdatedBy(user.getUserCode());
							requirementAssignTo.setUpdatedDate(commonUtil.currentDate());
							requirementAssignToList.add(requirementAssignTo);
							
								/*Team team = new Team();
								Long teamId = 0L;
								int teamReady = 0;
								for(int count = teamCount; count > 0; count--){
									teamId = Long.valueOf(assignedTeamId.get(count-1));
									Boolean isUserHasInTeam;
									if(teamReady == 0){
										isUserHasInTeam = teamMembersService.isUserBelongsCurrentTeamByUserIdAndTeamId(assUser.getId(), teamId);
										
										if(isUserHasInTeam){
											team.setId(teamId);
											requirementAssignTo.setTeamId(team);
											teamReady++;
										}
									}
								}
								
								requirementAssignTo.setAssignUserId(assUser);
								requirementAssignTo.setRequirementId(requirement);
								UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(assUser.getId(),orgLocations.getId());
								requirementAssignTo.setRoleId(userRole.getRoles());
								requirementAssignTo.setCreatedDate(commonUtil.currentDate());
								requirementAssignTo.setCreatedBy(user.getUserCode());
								requirementAssignTo.setUpdatedBy(user.getUserCode());
								requirementAssignTo.setUpdatedDate(commonUtil.currentDate());
								requirementAssignToList.add(requirementAssignTo);*/
							}
						}
				     
			}
			if(!jsonObj.getString("saveType").equalsIgnoreCase("draft")){
				requirement.setRequirementAssignTo(requirementAssignToList);
			}
			//storing assigned team END
			
			/*
			 * System Generated Id For Job
			 */
			
		//requirement.setGeneratedId(getRequirementId(orgLocations));
		
		Requirement savedRequirement = requirementDao.storeRequirement(requirement);
		
		if(!savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())){
			 List<Requirement> parentrequirement = getAllReleasedRequirementsByParentId(savedRequirement.getId());
		if(null != parentrequirement && !parentrequirement.isEmpty()){
			for(Requirement parentrequirementList :parentrequirement){
				releaseUpdatedMailScenario(user, savedRequirement, parentrequirementList.getSpocId(), orgLocations);
			}
		  }
		}
		//store recommended customizeScore
		RecommendedCustomizeScore  recommendedCustomizeScore = recommendedCustomizeScoreService.saveCustomizeScore(requirement);
	
			       if(!requirement.getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.DRAFT)){
				 		
					// mailRequirementtoManager(user,user,savedRequirement,location,orgLocations);
					/*if(jsonObj.has("sequentialApprover1") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover1"))){
						  String approverNames = jsonObj.getString("sequentialApprover1");
						  User approverUser = userService.getUserById(Long.parseLong(approverNames));
						  approverEmailScenario(user, approverUser ,savedRequirement,location,orgLocations);
				     }*/
			  
					//send mail to sequential approver1 
					if(jsonObj.has("sequentialApprover1") && StringUtils.isNotEmpty(jsonObj.getString("sequentialApprover1"))){
						  String approverNames = jsonObj.getString("sequentialApprover1");
						  User approverUser = userService.getUserById(Long.parseLong(approverNames));
						  if(!approverUser.getId().equals(user.getId())){
						  approverEmailScenario(user, approverUser ,savedRequirement,location,orgLocations);
						  }
					}
					
					//send mail to parallel approver
					if(jsonObj.has("parallelApprover")){
							
							String parallelApprovers =null;
							try{
								JSONArray jsonArray = jsonObj.getJSONArray("parallelApprover");
								parallelApprovers = jsonArray.toString();
							}
							catch(JSONException e){
								parallelApprovers =jsonObj.getString("parallelApprover");
							}
							parallelApprovers = parallelApprovers.replaceAll("\\[", "").replaceAll("\\]","");
							parallelApprovers = parallelApprovers.replaceAll("\"", "");
							
						    String[] approvers = parallelApprovers.split(",");
						    for(String approver : approvers){
						    	User approverUser = userService.getUserById(Long.parseLong(approver));
						    	if(!approverUser.getId().equals(user.getId())){
						    		approverEmailScenario(user,approverUser,savedRequirement,location,orgLocations);
						    	}
						    }
					 }
					
					
					/*shivayogi Kadagad/rakshith mahabal*/	
					// this logic is for assigned to team members when new team user is added for requirement-edit and saved.	
					if(null != savedRequirement.getId() && requirementAssignToList.size()!=0 && !savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())){
						for (RequirementAssignTo newList : requirementAssignToList) {
					       boolean found=false;

					       if(null != oldAssigntoTeamList && oldAssigntoTeamList.size()!=0){
					       for (RequirementAssignTo oldList : oldAssigntoTeamList) {
					           if ((newList.getAssignUserId().getId().equals(oldList.getAssignUserId().getId()))) {
					               found=true;
					               break;
					           }
					         }
					       }
					       if(!found){
					       	List<RequirementAssignTo> newlyAddedList = new ArrayList<RequirementAssignTo>();
					       	newlyAddedList.add(newList);
					       	for(RequirementAssignTo newly :newlyAddedList){
					       		User newlyAddedUser = newly.getAssignUserId();
					       		Set<RequirementAssignTo> updatedRequirementAssignToList = savedRequirement.getRequirementAssignTo();
									if(updatedRequirementAssignToList != null && !user.getId().equals(newlyAddedUser.getId())){
												AssignToTeamMembersEmail(user, newlyAddedUser, savedRequirement,location,orgLocations);
											}
										}
									}
					       	}
					       }
						
					
					//send mail to Assigned Team 
					//condition : if login user as team member then mail not send
					// this logic will execute when no approvers are selected for requirement.
				  if(!savedRequirement.getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED)){
					//Set<RequirementAssignTo> updatedRequirementAssignToList = savedRequirement.getRequirementAssignTo();
					
					 Set<RequirementAssignTo> teamMembers = getRequirementAssignedToTeamByRequirementId(savedRequirement.getId());
					  
						Set<RequirementAssignTo> updatedRequirementAssignToList = teamMembers;
					
					if(updatedRequirementAssignToList != null  ){
						for(RequirementAssignTo requirementAssignTo : updatedRequirementAssignToList ){
							if(savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())){
								if(requirementAssignTo.getAssignUserId() != null && !user.getId().equals(requirementAssignTo.getAssignUserId().getId())){
									AssignToTeamMembersEmail(user, requirementAssignTo.getAssignUserId(), savedRequirement,location,orgLocations);
								}
							} 
						 }
					}
				}
				  
				  	//send mail to modified Assigned Team 
					//condition : if login user as team member then mail should not send
				  if(!savedRequirement.getRequirementSubmissionStatus().equals(REQUIREMENT_SUBMISSION_STATUS.SUBMITTED)){
						Set<RequirementAssignTo> updatedRequirementAssignToList = savedRequirement.getRequirementAssignTo();
						if(updatedRequirementAssignToList != null  ){
							for(RequirementAssignTo requirementAssignTo : updatedRequirementAssignToList ){
								 if(!savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())){
									if(requirementAssignTo.getAssignUserId() != null && !user.getId().equals(requirementAssignTo.getAssignUserId().getId())){
										updatedRequirementMailToAssignedMembers(user,requirementAssignTo.getAssignUserId(), savedRequirement,location,orgLocations);
									}
								}
							 }
						}
					}
					//send mail to manger 
					//condition : login user as manager then  mail shoul not forwared
					if(savedRequirement.getManagerUserId() != null && !user.getId().equals(savedRequirement.getManagerUserId().getId()) && savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate())){
						mailRequirementtoManager(user,user,savedRequirement,location,orgLocations);
					}else if(!savedRequirement.getCreatedDate().equals(savedRequirement.getUpdatedDate()) && !user.getId().equals(savedRequirement.getManagerUserId().getId())){
							updatedRequirementMailToManager(user,user, savedRequirement,location,orgLocations);
				}
		 }		
			       
			       requirementElkUtil.saveElkRequirement(savedRequirement,jsonObj);
			    
		return savedRequirement;
	}
	private Set<RequirementAssignTo> getRequirementAssignedToTeamByRequirementId(Long id)
	{
	return requirementDao.getRequirementAssignedToTeamByRequirementId(id);
	}
		
private boolean isNullOrEmpty(String string)
	{
		// TODO Auto-generated method stub
		return false;
	}


private void updatedRequirementMailToAssignedMembers(User loginUser, User assUser, Requirement requirement,
			StringBuilder location, OrgLocations loginOrgLocation) throws Exception {
	
	 assUser= userService.getUserById(assUser.getId());
	 UserRole loginUserRole =  userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(), loginOrgLocation.getId());
	
	 EmailMessage emailMessage = new EmailMessage();
	 emailMessage.setFirstName(loginUser.getFirstName());
	 emailMessage.setLastName(loginUser.getLastName());
	 emailMessage.setToEmail(assUser.getEmail());
	 emailMessage.setFromEmail(loginUser.getEmail());
	 emailMessage.setCreatedBy(loginUser.getUserCode());
	// StringBuilder msgBuild = new StringBuilder();
	// emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
	// emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	//setting the class filed values
	 Map<String, String> globalVarsMap = new HashMap<String, String>();
	 if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId()) ) {
		 StringBuilder reqsb=new StringBuilder();
		 globalVarsMap.put("RequirementTitle",  reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		 } else {
			 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		}
	 
	 StringBuilder stringBuild = new StringBuilder();
	 if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
		 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
	 }else if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
		 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
	 }else if(requirement.getCountry()!= null && !"".equalsIgnoreCase(requirement.getCountry())){
		 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).toString());
	 }else{
		 globalVarsMap.put("RequirementLocation", "--");
	 }
	 
	 
	 StringBuilder sb = new StringBuilder();
     if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
         globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
         }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
             globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
         }
     else{
             globalVarsMap.put("RequirementRate","--");
         }
	 
	 
	 if (assUser.getFirstName() != null && assUser.getLastName()!= null && !"".equalsIgnoreCase(assUser.getFirstName()) && !"".equalsIgnoreCase(assUser.getLastName())) {
		 StringBuilder userBuild = new StringBuilder();
		 globalVarsMap.put("clientContactname", userBuild.append(assUser.getFirstName()).append("").append(assUser.getLastName()).toString());
		 } else {
			 globalVarsMap.put("clientContactname", assUser.getFirstName());
		}
	 if((requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp()))){
		 StringBuilder expBuild = new StringBuilder();
		 globalVarsMap.put("Experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		 }else if(requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("Experience", requirement.getMinExp());
		 }else if(requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp())){
			 globalVarsMap.put("Experience",requirement.getMaxExp());
		 }else{
			 globalVarsMap.put("Experience","--"); 
		 }
	 
	 if (requirement.getRequirementSubmissionStatus() != null) {
		 globalVarsMap.put("status", requirement.getRequirementSubmissionStatus().getValue());
		 } else {
			 globalVarsMap.put("status", "--");
		}
	 StringBuilder linkBuild = new StringBuilder();
	 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
	
	 if (loginUser.getFirstName() != null && loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
		 StringBuilder userBuild = new StringBuilder();
		 globalVarsMap.put("LoginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 } else {
			 globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
	 
	 if (loginOrgLocation.getOrgLocationName() != null && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName() )) {
		 globalVarsMap.put("LoginOrgName",loginOrgLocation.getOrgLocationName() );
		 } else {
			 globalVarsMap.put("LoginOrgName", "--");
		}
	
	 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
	
	 //getting the current EmailScenario by EmailScenario unique name and entity type
	 //using same migration which has been already used in updatedRequirementMailToManager method
	 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_REQUIREMENT_EMAIL_TO_MANAGER,Constants.ENTITY_TYPE_EMAIL);
	 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_REQUIREMENT_NOTIFICATION_TO_MANAGER,Constants.ENTITY_TYPE_NOTIFICATION);

	 
	 //covert subject,body and signature
	 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
	 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
	 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
	 String url = sourceleadUrl;
	 
	 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
	 notificationScenario.setBody(notificationbody);

	 //construct HTML template using required data
	 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
	 emailMessage.setSubject(subject);
	 emailMessage.setMessageBody(emailTemplate);
	 
	 //calling the sendEmail method of emailScenerioUtil
	 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
	 String actionLink="";
	 emailScenerioUtil.sendNotification(actionLink, loginUser, assUser, loginOrgLocation, notificationScenario);
	}
		


	private void updatedRequirementMailToManager(User loginUser, User managerUser, Requirement requirement, StringBuilder location, OrgLocations loginOrgLocation) throws Exception
	{
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
			StringBuilder reqsb=new StringBuilder();
			globalVarsMap.put("RequirementTitle",reqsb.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		}
		else {
			globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		}
		StringBuilder stringBuild = new StringBuilder();
		if (requirement.getCity() != null && !"".equalsIgnoreCase(requirement.getCity())) {
			globalVarsMap.put("RequirementLocation", stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
		}
		else if (requirement.getState() != null && !"".equalsIgnoreCase(requirement.getState())) {
			globalVarsMap.put("RequirementLocation", stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
		}
		else if (requirement.getCountry() != null
				&& !"".equalsIgnoreCase(requirement.getCountry())) {
			globalVarsMap.put("RequirementLocation", stringBuild.append(requirement.getCountry()).toString());
		}
		else {
			globalVarsMap.put("RequirementLocation", "--");
		}
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null
						&& !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
		}
		else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		}
		else {
			globalVarsMap.put("RequirementRate", "--");
		}
		if (managerUser.getFirstName() != null && managerUser.getLastName() != null
				&& !"".equalsIgnoreCase(managerUser.getFirstName())
				&& !"".equalsIgnoreCase(managerUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("clientContactname", userBuild.append(managerUser.getFirstName()).append("").append(managerUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("clientContactname", managerUser.getFirstName());
		}
		if ((requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()))
				&& (requirement.getMaxExp() != null
						&& !"".equalsIgnoreCase(requirement.getMaxExp()))) {
			StringBuilder expBuild = new StringBuilder();
			globalVarsMap.put("Experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		}
		else if (requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())) {
			globalVarsMap.put("Experience", requirement.getMinExp());
		}
		else if (requirement.getMaxExp() != null && !"".equalsIgnoreCase(requirement.getMaxExp())) {
			globalVarsMap.put("Experience", requirement.getMaxExp());
		}
		else {
			globalVarsMap.put("Experience", "--");
		}
		if (requirement.getRequirementSubmissionStatus() != null) {
			globalVarsMap.put("status", requirement.getRequirementSubmissionStatus().getValue());
		}
		else {
			globalVarsMap.put("status", "--");
		}
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("link", linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null
				&& !"".equalsIgnoreCase(loginUser.getFirstName())
				&& !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder userBuild = new StringBuilder();
			globalVarsMap.put("LoginUser", userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
		if (loginOrgLocation.getOrgLocationName() != null
				&& !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName())) {
			globalVarsMap.put("LoginOrgName", loginOrgLocation.getOrgLocationName());
		}
		else {
			globalVarsMap.put("LoginOrgName", "--");
		}
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_REQUIREMENT_EMAIL_TO_MANAGER, Constants.ENTITY_TYPE_EMAIL);
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATED_REQUIREMENT_NOTIFICATION_TO_MANAGER, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;
		String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, loginOrgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, loginOrgLocation, notificationScenario);
	}


private void releaseUpdatedMailScenario(User loginUser, Requirement requirement, OrgSpoc spoc,OrgLocations loginOrgLocation) throws Exception {
	
	User vendorUser = userService.getUserById(spoc.getVendorUser().getId());
	
	EmailMessage emailMessage = new EmailMessage();
	 emailMessage.setFirstName(loginUser.getFirstName());
	 emailMessage.setLastName(loginUser.getLastName());
	 emailMessage.setToEmail(vendorUser.getEmail());
	 emailMessage.setFromEmail(loginUser.getEmail());
	 emailMessage.setCreatedBy(loginUser.getUserCode());
	 StringBuilder msgBuild = new StringBuilder();
	 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
	 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
	//setting the class filed values
	 Map<String, String> globalVarsMap = new HashMap<String, String>();
	 if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle()) ) {
		 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
		 } else {
			 globalVarsMap.put("RequirementTitle", "--");
		}
	 
	 StringBuilder stringBuild = new StringBuilder();
	 if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
		 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
	 }else if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
		 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
	 }else if(requirement.getCountry()!= null && !"".equalsIgnoreCase(requirement.getCountry())){
		 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).toString());
	 }else{
		 globalVarsMap.put("RequirementLocation", "--");
	 }
	 
	 StringBuilder sb = new StringBuilder();
     if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
         globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
         }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
             globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
         }
     else{
             globalVarsMap.put("RequirementRate","--");
         }
	 if (vendorUser.getFirstName() != null && vendorUser.getLastName()!= null && !"".equalsIgnoreCase(vendorUser.getFirstName()) && !"".equalsIgnoreCase(vendorUser.getLastName())) {
		 StringBuilder userBuild = new StringBuilder();
		 globalVarsMap.put("clientContactname", userBuild.append(vendorUser.getFirstName()).append("").append(vendorUser.getLastName()).toString());
		 } else {
			 globalVarsMap.put("clientContactname", vendorUser.getFirstName());
		}
	 if((requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp()))){
		 StringBuilder expBuild = new StringBuilder();
		 globalVarsMap.put("Experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
		 }else if(requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())){
			 globalVarsMap.put("Experience", requirement.getMinExp());
		 }else if(requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp())){
			 globalVarsMap.put("Experience",requirement.getMaxExp());
		 }else{
			 globalVarsMap.put("Experience","--"); 
		 }
	 
	 if (requirement.getRequirementSubmissionStatus() != null) {
		 globalVarsMap.put("status", requirement.getRequirementSubmissionStatus().getValue());
		 } else {
			 globalVarsMap.put("status", "--");
		}
	 StringBuilder linkBuild = new StringBuilder();
	 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
	
	 if (loginUser.getFirstName() != null && loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
		 StringBuilder userBuild = new StringBuilder();
		 globalVarsMap.put("LoginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 } else {
			 globalVarsMap.put("LoginUser", loginUser.getFirstName());
		}
	 
	 if (loginOrgLocation.getOrgLocationName() != null && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName() )) {
		 globalVarsMap.put("LoginOrgName",loginOrgLocation.getOrgLocationName() );
		 } else {
			 globalVarsMap.put("LoginOrgName", "--");
		}
	 if (requirement.getManagerUserId() != null) {
		 User managerUser = userService.getUserById(requirement.getManagerUserId().getId());
		 globalVarsMap.put("requirementManagerName",managerUser.getFullName());
		 } else {
			 globalVarsMap.put("requirementManagerName", "--");
		}
	
	 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
	
	 //getting the current EmailScenario by EmailScenario unique name and entity type
	 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATEDREQUIREMENTNOTIFICATIONTOVENDOR,Constants.ENTITY_TYPE_EMAIL);
	 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.UPDATEDREQUIREMENTNOTIFICATIONTOVENDOR,Constants.ENTITY_TYPE_NOTIFICATION);

	 
	 //covert subject,body and signature
	 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
	 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
	 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
	 String url = sourceleadUrl;
	 
	 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
	 notificationScenario.setBody(notificationbody);

	 
	// OrgLocations vendorOrgLoc = requirement.getSpocId().getVendorLocationId();

	 
	 //construct HTML template using required data
	 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
	 emailMessage.setSubject(subject);
	 emailMessage.setMessageBody(emailTemplate);
	 
	 //calling the sendEmail method of emailScenerioUtil
	 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
	 String actionLink="";
	//emailScenerioUtil.sendNotification(actionLink, loginUser, vendorUser, vendorOrgLoc, notificationScenario);

	
	
		
	}

/*	private void saveElkRequirement(Requirement requirement, JSONObject jsonObj) throws Exception {
		 
		RequirementIndex requirementIndex = requirementHelper.populateRequirementIndex(requirement,jsonObj);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(requirementIndex, Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,requirement.getId().toString());
	}*/

	@Override
	public Requirement addRequirement(Requirement requirement) throws Exception {

		Requirement savedRequirement = requirementDao.storeRequirement(requirement);
		requirementElkUtil.saveElkRequirement(savedRequirement,new JSONObject());
		return savedRequirement;

	}


	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get RequirementList by orgLocationId .
	 *  
	 * @param orgLocationId This is the first paramter to getRequirementListByOrgLocationID method
	 * 
	 * @return Requirement 
	 * 
	 * @throws NullPointer
	*/

	@Override
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,User  user,String userRoleName) throws Exception {
		return requirementDao.getRequirementListByOrgLocationID(orgLocationId,status,clients,user,userRoleName);
	}

	
	@Override
	public Map<Long,List<Requirement>> getRequirementListByOrgLocationIDWithMaxResults(Long orgLocationId,String status,String clients,User  user,String userRoleName,int start,int length) throws Exception {
		return requirementDao.getRequirementListByOrgLocationIDWithMaxResults(orgLocationId,status,clients,user,userRoleName,start,length);
	}
	
	/**
	 * @author sarath kumar
	 * @since  sep 13 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get Requirement by requirementId .
	 *  
	 * @param requirementId This is the first paramter to getRequirementByRequirementId method
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


	
	
	/**
	 * @author Venkat Kesav
	 * @since  oct 21 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get the Requirement is released for contact or not .
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
	public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception {
		return requirementDao.getRequirementListByParentRequirementIdAndSpocId(requirementId, spocId);
	
	}
	/**
	 * @author prasad k
	 * @since  sep 14 2016
	 * @version 0.0.1
	 * 
	 * This method is used to get template status by orgLocationId .
	 *  
	 * @param requirementId This is the first paramter to getJobTemplateStatusByJobTitleAndRequirementId method
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
	public void approverEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location,OrgLocations loginOrgLocation) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(approverUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 StringBuilder sb = new StringBuilder();
			 if(requirement.getJobId() !=null && !"".equalsIgnoreCase(requirement.getJobId())){
			 globalVarsMap.put("requirementName", sb.append(requirement.getJobTitle()).append("-").append(requirement.getJobId()).toString());
			 }
			 else{
			 globalVarsMap.put("requirementName", requirement.getJobTitle());
			 }
			 }else{
				 globalVarsMap.put("requirementName","--");
			 }
		 if(location!= null){
			 globalVarsMap.put("requirementLocation", location.toString());
			 }else{
				 globalVarsMap.put("requirementLocation","--");
			 }
		 StringBuilder sb = new StringBuilder();
         if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
             globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
             }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
                 globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
             }
         else{
                 globalVarsMap.put("requirementRate","--");
             }
		if(requirement.getClientId()!=null){
			OrgRelations orgRelations = orgRelationsService.getRespondingPartyDetailsById(requirement.getClientId().getId());
			if(orgRelations != null){
				globalVarsMap.put("client", orgRelations.getResponderLocationId().getOrgLocationName());
			}
		}else{
			globalVarsMap.put("client","--");
		 }
		
		if(approverUser.getLastName()!= null && !"".equalsIgnoreCase(approverUser.getLastName())){
            StringBuilder approverUsersb = new StringBuilder();
            globalVarsMap.put("approverName", approverUsersb.append(approverUser.getFirstName()).append(" ").append(approverUser.getLastName()).toString());
            }else{
                globalVarsMap.put("approverName",approverUser.getFirstName());
            }
		
		
		if(emailMessage.getLastName()!=null && !"".equalsIgnoreCase(emailMessage.getLastName())){
			globalVarsMap.put("lastName",emailMessage.getLastName());	
		}else{
			globalVarsMap.put("lastName","--");
		}
		if(loginUser.getFirstName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName())){
			globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
			}else{
			globalVarsMap.put("jobInitiatorName","--");
		}
		 
		if(approverUser.getEmail()!=null && !"".equalsIgnoreCase(approverUser.getEmail())){
			globalVarsMap.put("userEmailId",approverUser.getEmail());
			}else{
				globalVarsMap.put("userEmailId","--");
		}
	
	     StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		 if(loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("loginUserName",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("loginUserName",loginUser.getLastName());
			}
		 if (loginOrgLocation.getOrgLocationName() != null && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName() )) {
			 globalVarsMap.put("role",loginOrgLocation.getOrgLocationName() );
			 } else {
				 globalVarsMap.put("role", "--");
			}
		 
		 if(requirement.getFileName() != null && !requirement.getFileName().isEmpty() ){
			 StringBuilder attachmentBuild = new StringBuilder();
	            globalVarsMap.put("attachment",attachmentBuild.append("<a href='").append(sourceleadUrl).append("/").append("downloadRequirementAttachment?requirementId=").append(requirement.getId()).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment<br/></div></a>").toString());
         }else{
        	 StringBuilder attachmentBuild = new StringBuilder();
            globalVarsMap.put(" ",attachmentBuild.append("<a href='").append(sourceleadUrl).append("/").append("downloadRequirementAttachment?requirementId=").append(requirement.getId()).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center;letter-spacing:2px;'>Attachment<br/></div></a>").toString());
         }
		 
		 //setting the Recipient information
		 /*ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);*/
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUIREMENT_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 StringBuilder actionLinkStringBuilder = new StringBuilder();
		 String actionLink= actionLinkStringBuilder.append(sourceleadUrl).append("/requirementSummary?requirementUUID=").append(requirement.getRequirementUUID()).toString();
//		 String actionLink= sourceleadUrl"/requirementSummary?requirementUUID="+requirement.getRequirementUUID()+"";
		//calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, loginOrgLocation, notificationScenario);
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
	
}
	
	
	
	@Override
	public void approverTwoEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location,OrgLocations orgLocations) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(approverUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		UserRole loginUserRole =  userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(), orgLocations.getId());

		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("requirementName", requirement.getJobTitle());
		 if(location != null){
		 globalVarsMap.put("requirementLocation", location.toString());
		 }else{
		 globalVarsMap.put("requirementLocation","--");
		 }
		
		 StringBuilder sb = new StringBuilder();
         if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
             globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
             }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
                 globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
             }
         else{
                 globalVarsMap.put("requirementRate","--");
             }
		 if(requirement.getClientId()!= null){
		 globalVarsMap.put("client", requirement.getClientId().getResponderLocationId().getOrgLocationName());
		 }else{
		 globalVarsMap.put("client","--");
		 }
		 if(approverUser.getFirstName()!= null && !"".equalsIgnoreCase(approverUser.getFirstName())){
		 globalVarsMap.put("approverName", approverUser.getFirstName());
		 }else{
		 globalVarsMap.put("approverName","--");
		 }
		 if(loginUser.getFirstName()!=null && !"".equalsIgnoreCase(loginUser.getFirstName())){
		 globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());
		 }else{
		 globalVarsMap.put("jobInitiatorName", loginUser.getFirstName());	 
		 }
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 StringBuilder userBuild = new StringBuilder();
		 globalVarsMap.put("loginUserName",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 globalVarsMap.put("role", loginUserRole.getUserType());
		 globalVarsMap.put("approverOne", loginUser.getFirstName());
		 
		 StringBuilder attachBuild = new StringBuilder();
		 globalVarsMap.put("attachment",attachBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Attachment</div></a>").toString());

		 //setting the Recipient information
		 /*ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);*/
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REQUIREMENT_SEQUENTIAL_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser,approverUser, orgLocations, notificationScenario);


	}


	@SuppressWarnings("unused")
	@Override
	public void acceptEmailScenario(User loginUser, User intiatorUser, Requirement requirement,StringBuilder requirementLocation,OrgLocations orgLocations) throws Exception {
		
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(intiatorUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 UserRole loginUserRole =  userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(), orgLocations.getId());
		 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 if( requirement.getJobId()!= null  && !"".equalsIgnoreCase( requirement.getJobId())){
				 StringBuilder userBuild1 = new StringBuilder();
				 globalVarsMap.put("requirementId-Name",userBuild1.append(requirement.getJobTitle()).append(" ").append(requirement.getJobId()).toString());

			 }else{
		     globalVarsMap.put("requirementId-Name",requirement.getJobTitle()); 
			 }
			 if(requirementLocation != null ){
				 globalVarsMap.put("requirementLocation", requirementLocation.toString());
				 }else{
				 globalVarsMap.put("requirementLocation","--");
				 }
			 if(loginUser.getFirstName()!= null  && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("approverName", loginUser.getFirstName());	 
			 }else{
			 globalVarsMap.put("approverName","--"); 
			 }
			 if(intiatorUser.getFirstName()!= null  && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("jobInitiatorName", intiatorUser.getFirstName()); 
			 }else{
			 globalVarsMap.put("jobInitiatorName","--"); 
			 }
			 
			 StringBuilder sb = new StringBuilder();
	         if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
	             globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
	             }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
	                 globalVarsMap.put("requirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
	             }
	         else{
	                 globalVarsMap.put("requirementRate","--");
	             }
				 if(requirement.getClientId()!= null){
				 globalVarsMap.put("client", requirement.getClientId().getResponderLocationId().getOrgLocationName());
				 }else{
				 globalVarsMap.put("client","--");
				 }
				 StringBuilder linkBuild = new StringBuilder();
				 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
				
			 if(loginUser.getFirstName() != null && loginUser.getLastName()!= null  && !"".equalsIgnoreCase( loginUser.getFirstName()) && !"".equalsIgnoreCase( loginUser.getLastName())){
				 StringBuilder userBuild = new StringBuilder();
				 globalVarsMap.put("loginUserName",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
			 globalVarsMap.put("loginUserName",loginUser.getFirstName()); 
			 }
			 
			 globalVarsMap.put("role", loginUserRole.getUserType());
		 
		 //setting the Recipient information
		 /*ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);*/
	
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVED_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVED_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		emailScenerioUtil.sendNotification(actionLink, loginUser,intiatorUser, orgLocations, notificationScenario);


	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * calculating the score of Resume per candidate
	 * 
	 */
	@Override
	public List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement) throws Exception {
		float expPercentage = 0; //  experience = 20%
		float locationPercentage = 0; // location 10%
		float ratePercentage = 0;    // rate 20 %
		float skillPercentage = 0; // skill = 50%
		float percentPerSkill = 0;
		List<Candidate> CandidateListcopy = new ArrayList<>();
		String reqSkills = "";
		if(requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())){
		     reqSkills = requirement.getSkills();
			String [] reqMaxSkills = reqSkills.split(",");
			 percentPerSkill = 50/(float)reqMaxSkills.length;
		}
		int reqMinExp = 0;
		int reqMaxExp = 0;
		float percentPerMonthExp = 0;
		if(requirement.getMinExp() !=null && !"".equalsIgnoreCase(requirement.getMinExp()) && requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
			 reqMinExp = Integer.parseInt(requirement.getMinExp());
			 reqMaxExp = Integer.parseInt(requirement.getMaxExp());
			int reqExp = reqMaxExp *12 ;
			 percentPerMonthExp = 20/(float)reqExp;
		}
		String reqCity = "";
		if(requirement.getCity() != null && !"".equalsIgnoreCase(requirement.getCity())){
			 reqCity = requirement.getCity();
		}
		float reqPerMonthSal = 0;
		if(requirement.getRate() !=null && !"".equalsIgnoreCase(requirement.getRate())){
			int reqRate = Integer.parseInt(requirement.getRate());
			String reqRateType = requirement.getRateType();
			if("Hourly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate *24;
				reqPerMonthSal = perDaySal * 30;
			}else if("Weekly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /7;
				reqPerMonthSal = perDaySal * 30;
			}else if("Bi-weekly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /14;
				reqPerMonthSal = perDaySal * 30;
			}else if("Semi-Monthly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /15;
				reqPerMonthSal = perDaySal * 30;
			}else if("Monthly".equalsIgnoreCase(reqRateType)){
				reqPerMonthSal = reqRate;
			}else if("Bi-Monthly".equalsIgnoreCase(reqRateType)){
				int perDaySal = reqRate /60;
				reqPerMonthSal = perDaySal * 30;
			}else{
				reqPerMonthSal = reqRate /12;
			}
		}
		
		for(Candidate candidate : candidateList){
			int matchedSkillCount = 0;
//			List<String> candidateSkillList  = null;
			if(null != candidate.getSkillsName()){
				String[] skills = candidate.getSkillsName().split(",");
				
				for(String skillName : skills){
					if(skillName !=null && !"".equalsIgnoreCase(skillName)){
						if(reqSkills.contains(skillName)){
							matchedSkillCount++;
						}
						
					}
				}
				//candidateSkillList = new ArrayList<String>(Arrays.asList(skills));
			}
			
			List<RequirementSubmission> reqList = requirementSubmissionService.getRequirementPerCandidate(candidate.getId());
			CandidateResumes candidateResume = candidateResumeSerivce.getCandidateLatesResumes(candidate.getId(), 1);
//			if(candidateSkillList != null && candidateSkillList.size() >0){
				
				
//			}
			skillPercentage = matchedSkillCount*percentPerSkill;
			if(candidate.getExperienceYears() != null && !"".equalsIgnoreCase(candidate.getExperienceYears()) && candidate.getExperienceMonths()!=null && !"".equalsIgnoreCase(candidate.getExperienceMonths())){
				int  candidateExpMonths = Integer.parseInt(candidate.getExperienceYears())*12 + Integer.parseInt(candidate.getExperienceMonths());
				if(candidateExpMonths < reqMinExp *12){
					expPercentage = 0;
				}else if(candidateExpMonths >= reqMinExp *12 && candidateExpMonths <= reqMaxExp *12){
					expPercentage = percentPerMonthExp*(float)candidateExpMonths;
				}else{
					expPercentage = 20;
				}
			}
			if(candidate.getCity() != null && !"".equalsIgnoreCase(candidate.getCity())){
				if(candidate.getCity().equalsIgnoreCase(reqCity)){
					locationPercentage = 10;
				}
			}
			
			String candidateRateType = candidate.getRateType();
			int reqRate = 200;
			float perMonthCandidateSal = 0;
			if("Hourly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate *24;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Weekly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /7;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Bi-weekly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /14;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Semi-Monthly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /15;
				perMonthCandidateSal = perDaySal * 30;
			}else if("Monthly".equalsIgnoreCase(candidateRateType)){
				perMonthCandidateSal = reqRate;
			}else if("Bi-Monthly".equalsIgnoreCase(candidateRateType)){
				int perDaySal = reqRate /60;
				perMonthCandidateSal = perDaySal * 30;
			}else{
				perMonthCandidateSal = reqRate /12;
			}
			
			if(perMonthCandidateSal<=reqPerMonthSal){
				ratePercentage = 20;
			}
			float totalPercentage = skillPercentage+expPercentage+locationPercentage+ratePercentage;
			double roundOff = Math.round(totalPercentage * 100.0) / 100.0;
			String candidateScore = String.valueOf(roundOff);
			candidate.setCandidateScore(candidateScore);
			candidate.setNoOfReq(reqList.size());
			if(candidateResume.getTitle() !=null && !"".equalsIgnoreCase(candidateResume.getTitle())){
				candidate.setCandidateResume(candidateResume.getTitle());
			}else{
				candidate.setCandidateResume(candidateResume.getFileName());
			}
			
			if(skillPercentage !=0 && totalPercentage !=0 ){
				CandidateListcopy.add(candidate);
			}
		}
		 Collections.sort(CandidateListcopy, new Comparator() {  
	            @Override  
	            public int compare(Object obj1, Object obj2) {  
	            	Candidate candidate1 = (Candidate)obj1;  
	            	Candidate candidate2 = (Candidate)obj2; 
	            	return candidate2.getCandidateScore().compareToIgnoreCase(candidate1.getCandidateScore());  
	            }  
	        });
		return CandidateListcopy;
	}

	
	@SuppressWarnings("unused")
	@Override
	public void mailRequirementtoManager(User loginUser, User managerUser, Requirement requirement,StringBuilder location,OrgLocations loginOrgLocation) throws Exception {
	
		 if(requirement.getManagerUserId() != null ){
			managerUser = userService.getUserById(requirement.getManagerUserId().getId());
		 }
		
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(managerUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if(managerUser.getFirstName()!= null && !"".equalsIgnoreCase(managerUser.getFirstName())){
             StringBuilder sb = new StringBuilder();
             globalVarsMap.put("OwnerName", sb.append(managerUser.getFirstName()).append(" ").append(managerUser.getLastName()).toString());
             }else{
                 globalVarsMap.put("OwnerName","--");
             }
		 if(loginUser.getFirstName()!= null  && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 globalVarsMap.put("JobInitiator", loginUser.getFirstName());
			 }else{
				 globalVarsMap.put("JobInitiator","--"); 
			 }
		 if(requirement.getJobTitle()!= null  && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("RequirementTitle","--"); 
			 }
		 if(location!= null){
			 globalVarsMap.put("RequirementLocation", location.toString());
			 }else{
				 globalVarsMap.put("RequirementLocation","--"); 
			 }

		 StringBuilder sb = new StringBuilder();
         if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
             globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
             }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
                 globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
             }
         else{
                 globalVarsMap.put("RequirementRate","--");
             }
		 if((requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("Experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
			 }else if(requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())){
				 globalVarsMap.put("Experience", requirement.getMinExp());
			 }else if(requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp())){
				 globalVarsMap.put("Experience",requirement.getMaxExp());
			 }else{
				 globalVarsMap.put("Experience","--"); 
			 }
		 
		  if(loginUser.getFirstName() != null && loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("LoginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }
		  else if(loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("LoginUser",userBuild.append(loginUser.getFirstName()).toString());
		  	 }
		 else{
			 globalVarsMap.put("LoginUser","--"); 
			 }
		  
		 if(loginUser.getUserType()!= null && !"".equalsIgnoreCase(loginUser.getUserType())){
			 globalVarsMap.put("Role", loginUser.getUserType());
			 }else{
				 globalVarsMap.put("Role","--"); 
			 }
		 if (loginOrgLocation.getOrgLocationName() != null && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName() )) {
			 globalVarsMap.put("Role",loginOrgLocation.getOrgLocationName() );
			 } else {
				 globalVarsMap.put("Role", "--");
			}
		 	if(requirement.getDescription() != null && !"".equalsIgnoreCase(requirement.getDescription())){
		 	globalVarsMap.put("description",requirement.getDescription()); 	
		 	}else{
			globalVarsMap.put("description","--"); 
		 	}
		 	if(requirement.getPriority() != null && !"".equalsIgnoreCase(requirement.getPriority())){
		 		globalVarsMap.put("priority",requirement.getPriority());
		 	}else{
				 globalVarsMap.put("priority","--"); 
		 	}
		 	if(requirement.getPositions() != null && !"".equalsIgnoreCase(requirement.getPositions())){
		 		globalVarsMap.put("positions",requirement.getPositions());
		 	}else{
				 globalVarsMap.put("positions","--"); 
		 	}
		 	if(requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())){
		 		globalVarsMap.put("skills",requirement.getSkills());
		 	}else{
				 globalVarsMap.put("skills","--"); 
		 	}
		 
		 
		 StringBuilder linkBuild = new StringBuilder();
		 StringBuilder attachBuild = new StringBuilder();
		 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("attachment",attachBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Attachment</div></a>").toString());


		 //setting the Recipient information
		 /*ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);*/
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAILREQUIREMENTTOMANAGER,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.MAILREQUIREMENTTOMANAGER,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		 emailScenerioUtil.sendNotification(actionLink, loginUser, managerUser, loginOrgLocation, notificationScenario);


	}

	@SuppressWarnings("unused")
	@Override
	public void AssignToTeamMembersEmail(User loginUser, User assUser, Requirement requirement,StringBuilder location,OrgLocations orgLocations) throws Exception {
			assUser= userService.getUserById(assUser.getId());
			User manager = userService.getUserById(requirement.getManagerUserId().getId());
			UserRole loginUserRole =  userRoleService.getUserDetailsByUserIdAndLocationId(loginUser.getId(), orgLocations.getId());
		 EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(assUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 
		 if(assUser.getFirstName()!= null && !"".equalsIgnoreCase(assUser.getFirstName())){
             StringBuilder sb = new StringBuilder();
             globalVarsMap.put("OwnerName", sb.append(assUser.getFirstName()).append(" ").append(assUser.getLastName()).toString());
             }else{
                 globalVarsMap.put("OwnerName","--");
             }
		 
		 if( requirement.getJobTitle()!= null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
			 }else{
				 globalVarsMap.put("RequirementTitle","--"); 
			 }
		 if( requirement.getJobId()!= null && !"".equalsIgnoreCase(requirement.getJobId())){
			 StringBuilder jId = new StringBuilder();
			 globalVarsMap.put("jobTitleId", jId.append(requirement.getJobTitle()).append(" ").append(requirement.getJobId()).toString());
			 }else{
				 globalVarsMap.put("jobTitleId",requirement.getJobTitle()); 
			 }
		 
		 if(location!= null){
			 globalVarsMap.put("RequirementLocation", location.toString());
			 }else{
				 globalVarsMap.put("RequirementLocation","--"); 
			 }
		 StringBuilder sb = new StringBuilder();
         if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
             globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
             }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
                 globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
             }
         else{
                 globalVarsMap.put("RequirementRate","--");
             }
		 if((requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("Experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
			 }else if(requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())){
				 globalVarsMap.put("Experience", requirement.getMinExp());
			 }else if(requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp())){
				 globalVarsMap.put("Experience",requirement.getMaxExp());
			 }else{
				 globalVarsMap.put("Experience","--"); 
			 }
		 if( loginUser.getFirstName() != null && loginUser.getLastName()!= null ||!"".equalsIgnoreCase( loginUser.getFirstName()) || !"".equalsIgnoreCase(loginUser.getLastName())){
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("LoginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("LoginUser",loginUser.getFirstName()); 
			 }
		 if( loginUserRole.getUserType() != null && !"".equalsIgnoreCase(loginUserRole.getUserType())){
			 globalVarsMap.put("Role",loginUserRole.getUserType());
			 }else{
				 globalVarsMap.put("Role","--"); 
			 }
		 if(requirement.getDescription() != null && !"".equalsIgnoreCase(requirement.getDescription())){
			 	globalVarsMap.put("description",requirement.getDescription()); 	
			 	}else{
				globalVarsMap.put("description","--"); 
			 	}
		 if(requirement.getPriority() != null && !"".equalsIgnoreCase(requirement.getPriority())){
		 		globalVarsMap.put("priority",requirement.getPriority());
		 	}else{
				 globalVarsMap.put("priority","--"); 
		 	}
		 	if(requirement.getPositions() != null && !"".equalsIgnoreCase(requirement.getPositions())){
		 		globalVarsMap.put("positions",requirement.getPositions());
		 	}else{
				 globalVarsMap.put("positions","--"); 
		 	}
		 	if(requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())){
		 		globalVarsMap.put("skills",requirement.getSkills());
		 	}else{
				 globalVarsMap.put("skills","--"); 
		 	}
		 	
		 	if(manager.getLastName() != null && !"".equalsIgnoreCase(manager.getLastName())){
		 	StringBuffer muId = new StringBuffer();
		 	globalVarsMap.put("manager",muId.append(manager.getFirstName()).append(" ").append(manager.getLastName()).toString());
		 	}else{
			globalVarsMap.put("manager",manager.getFirstName());
		 	}
		 
		 StringBuilder linkBuild = new StringBuilder();
		 StringBuilder attachBuild = new StringBuilder();
		 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		 globalVarsMap.put("attachment",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Attachment</div></a>").toString());


		 //setting the Recipient information
		 /*ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);*/
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ASSIGNTEAMMEMBERSFORREQUIREMENT,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ASSIGNTEAMMEMBERSFORREQUIREMENT,Constants.ENTITY_TYPE_NOTIFICATION);

		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		String actionLink="";
		emailScenerioUtil.sendNotification(actionLink, loginUser,assUser, orgLocations, notificationScenario);


	}


	@Override
	public void rejectedEmailScenario(User loginUser, User initiatorUser, Requirement requirement,StringBuilder location,String rejectedReason,OrgLocations loginOrgLocation)throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(requirement.getManagerUserId().getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
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
			globalVarsMap.put("loginUserName", loginName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("loginUserName", loginUser.getFirstName());
		}
		StringBuilder userBuild = new StringBuilder();
		if (loginUser.getFirstName() != null && loginUser.getLastName() != null	&& !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName()))  {
			globalVarsMap.put("approverName", userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else if(loginUser.getFirstName() != null && !"".equalsIgnoreCase(loginUser.getFirstName())) {
			globalVarsMap.put("approverName", userBuild.append(loginUser.getFirstName()).toString());
		}else{
			globalVarsMap.put("approverName","--");
		}
		
		
		UserRole userRole = userRoleService.getUserRoleByUserId(loginUser.getId());
		if(null != userRole.getRoles()){
		if (userRole.getRoles().getDisplayName() != null && !"".equalsIgnoreCase(userRole.getRoles().getDisplayName())) {
			globalVarsMap.put("role", userRole.getRoles().getDisplayName());
		} }else {
			globalVarsMap.put("role", "--");
		}
		
		StringBuilder appendString = new StringBuilder();
		if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId()) && (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle()))) {
			globalVarsMap.put("requirementId-Name",appendString.append(requirement.getJobId()).append("-").append(requirement.getJobTitle()).toString());
		} else if(requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			globalVarsMap.put("requirementId-Name", requirement.getJobTitle());
		}else if(requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())){
			globalVarsMap.put("requirementId-Name", requirement.getJobId());
		}else{
			globalVarsMap.put("requirementId-Name", requirement.getJobId());
		}
		if (requirement.getManagerUserId().getLastName() != null && !"".equalsIgnoreCase(requirement.getManagerUserId().getLastName()) ) {
			StringBuilder managerName = new StringBuilder();
			globalVarsMap.put("jobInitiatorName", managerName.append(requirement.getManagerUserId().getFirstName()).append(" ").append(requirement.getManagerUserId().getLastName()).toString());
		} else {
			globalVarsMap.put("jobInitiatorName", requirement.getManagerUserId().getFirstName());
		}
		if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle()) ) {
			globalVarsMap.put("requirementName", requirement.getJobTitle());
		} else {
			globalVarsMap.put("requirementName", "--");
		}
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

		// setting the Recipient information
		/*ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		EmailRecipient emailRecipient = new EmailRecipient();
		emailRecipient.setEmail(emailMessage.getToEmail());
		StringBuilder recipBuild = new StringBuilder();
		emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		emailRecipientList.add(emailRecipient);*/
		
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());

		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECTED_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_EMAIL);
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.REJECTED_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);


		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;
		
		String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
		notificationScenario.setBody(notificationbody);


		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);

		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());
		String actionLink="";
		emailScenerioUtil.sendNotification(actionLink, loginUser, requirement.getManagerUserId(), loginOrgLocation, notificationScenario);
	}
	
	@Override
	public void releaseMailScenario(User loginUser, Requirement requirement, OrgSpoc orgSpoc,OrgLocations loginOrgLocation,String releasetype) throws Exception {
		User  vendorContactUser = null;
		
		if(releasetype.equalsIgnoreCase("VENDORS")){
			vendorContactUser = userService.getUserById(orgSpoc.getVendorUser().getId());

		}else if(releasetype.equalsIgnoreCase("CONTACTS")){
			vendorContactUser = userService.getUserById(orgSpoc.getVendorUser().getId());
		}
	 
		EmailMessage emailMessage = new EmailMessage();
		 emailMessage.setFirstName(loginUser.getFirstName());
		 emailMessage.setLastName(loginUser.getLastName());
		 emailMessage.setToEmail(vendorContactUser.getEmail());
		 emailMessage.setFromEmail(loginUser.getEmail());
		 emailMessage.setCreatedBy(loginUser.getUserCode());
		 StringBuilder msgBuild = new StringBuilder();
		 emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle()) ) {
			 globalVarsMap.put("RequirementTitle", requirement.getJobTitle());
			 } else {
				 globalVarsMap.put("RequirementTitle", "--");
			}
		 
		 StringBuilder stringBuild = new StringBuilder();
		 if(requirement.getCity()!= null && !"".equalsIgnoreCase(requirement.getCity())){
			 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).append(",").append(requirement.getCity()).toString());
		 }else if(requirement.getState()!= null && !"".equalsIgnoreCase(requirement.getState())){
			 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).append(",").append(requirement.getState()).toString());
		 }else if(requirement.getCountry()!= null && !"".equalsIgnoreCase(requirement.getCountry())){
			 globalVarsMap.put("RequirementLocation",stringBuild.append(requirement.getCountry()).toString());
		 }else{
			 globalVarsMap.put("RequirementLocation", "--");
		 }
		 
		 
		 StringBuilder sb = new StringBuilder();
         if((requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())) && (requirement.getMaxRate()!= null  && !"".equalsIgnoreCase(requirement.getMaxRate()))){
             globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
             }else if(requirement.getRate()!= null  && !"".equalsIgnoreCase(requirement.getRate())){
                 globalVarsMap.put("RequirementRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
             }
         else{
                 globalVarsMap.put("RequirementRate","--");
             }
		 if (requirement.getClientId() != null) {
			 globalVarsMap.put("clientname", requirement.getClientId().getLocationId().getOrgLocationName());
			 } else {
				 globalVarsMap.put("clientname", "--");
			}
		 if (vendorContactUser.getFirstName() != null && vendorContactUser.getLastName()!= null && !"".equalsIgnoreCase(vendorContactUser.getFirstName()) && !"".equalsIgnoreCase(vendorContactUser.getLastName())) {
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("contactname", userBuild.append(vendorContactUser.getFirstName()).append("").append(vendorContactUser.getLastName()).toString());
			 } else {
				 globalVarsMap.put("contactname", vendorContactUser.getFirstName());
			}
		 if((requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())) && (requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp()))){
			 StringBuilder expBuild = new StringBuilder();
			 globalVarsMap.put("Experience", expBuild.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).toString());
			 }else if(requirement.getMinExp()!= null  && !"".equalsIgnoreCase(requirement.getMinExp())){
				 globalVarsMap.put("Experience", requirement.getMinExp());
			 }else if(requirement.getMaxExp()!= null  && !"".equalsIgnoreCase(requirement.getMaxExp())){
				 globalVarsMap.put("Experience",requirement.getMaxExp());
			 }else{
				 globalVarsMap.put("Experience","--"); 
			 }
		 StringBuilder linkBuild = new StringBuilder();
		 globalVarsMap.put("link",linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());
		
		 if (loginUser.getFirstName() != null && loginUser.getLastName()!= null && !"".equalsIgnoreCase(loginUser.getFirstName()) && !"".equalsIgnoreCase(loginUser.getLastName())) {
			 StringBuilder userBuild = new StringBuilder();
			 globalVarsMap.put("LoginUser",userBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 } else {
				 globalVarsMap.put("LoginUser", loginUser.getFirstName());
			}
		 
		 if (loginOrgLocation.getOrgLocationName() != null && !"".equalsIgnoreCase(loginOrgLocation.getOrgLocationName() )) {
			 globalVarsMap.put("LoginOrgName",loginOrgLocation.getOrgLocationName() );
			 } else {
				 globalVarsMap.put("LoginOrgName", "--");
			}
		
		 //setting the Recipient information
		 /*ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
		 EmailRecipient emailRecipient = new EmailRecipient();
		 emailRecipient.setEmail(emailMessage.getToEmail());
		 StringBuilder recipBuild = new StringBuilder();
		 emailRecipient.setName(recipBuild.append(emailMessage.getFirstName()).append(" ").append(emailMessage.getLastName()).toString());
		 emailRecipientList.add(emailRecipient);*/
		 
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.RELEASEMECHANISM_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_EMAIL);
		 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.RELEASEMECHANISM_EMAIL_NOTIFICATION,Constants.ENTITY_TYPE_NOTIFICATION);

		 
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = sourceleadUrl;
		 
		 String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(),globalVarsMap);
		 notificationScenario.setBody(notificationbody);

		 
		 OrgLocations vendorOrgLoc = requirement.getSpocId().getVendorLocationId();

		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, loginOrgLocation.getOrg(),loginOrgLocation);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,loginOrgLocation.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		 String actionLink="";
		emailScenerioUtil.sendNotification(actionLink, loginUser, vendorContactUser, vendorOrgLoc, notificationScenario);

		
	}
	
	@Override
    public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception {
        return requirementDao.getRequirementByParentRequirementId(requirementId);
    }
	
	
	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode)
			throws Exception {
		
			return requirementDao.getAssignedRequirementListByOrgLocationIDAndUserId(orgLocationId, userType, userId, userCode);
		
	}
	
	
	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(Long orgLocationId, String userType, Long userId, String userCode)
			throws Exception {
		
			return requirementDao.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(orgLocationId, userType, userId, userCode);
		
	}
	
	@Override
	public List<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode,String statusType)
			throws Exception {
		
			return requirementDao.getApproverRequirementApproverListByOrgLocationIDAndUserId(orgLocationId, userType, userId, userCode,statusType);
		
	}
	
	
	@Override
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, Long userId)
			throws Exception {
		
			return requirementDao.getAssignedRequirementApproverListByOrgLocationIDAndUserId(orgLocationId,userId);
			
	}
	
	@Override
	public List<Requirement> getRequirementListById(Long requirementId)throws Exception {
			return requirementDao.getRequirementListById(requirementId);
	}

	@Override
    public Requirement validateExistingJobId(String jobId, Long requirementId, Long orgLocationId) throws Exception {
        return requirementDao.validateExistingJobId(jobId,requirementId,orgLocationId);
    }
	
	@Override
	public Integer validateJobId(String id,Long orgLocation) throws Exception {
		return requirementDao.validateJobId(id,orgLocation);
	}


	@Override
	public File getExportRequirementFile(List<Requirement> requirementList, String orgName,String exportType,TimeSheetSubmissionCount requirementSubCount) throws Exception {
		
		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			Workbook createExcelworkbook = new XSSFWorkbook();
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			Sheet createExcelsheet = createExcelworkbook.createSheet(orgName + "_" + dateFormat.format(new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook,requirementSubCount);
			int column = 0;
			for (Requirement requirement : requirementList) {
				writeRow = createExcelsheet.createRow(column + 13);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow, requirement);
				column++;
			}
			 /*file = new File(orgName + "_Requirements_" + dateFormat.format(new Date()) + ".xls");*/
			 file = File.createTempFile(orgName + "_Requirements_" + dateFormat.format(new Date()),".xls");
			 file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
			try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			file = pdfUtil.getRequirementPdf(requirementList,orgName,requirementSubCount);
		}
			return file;	 
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, Requirement requirement) {
		
		if(requirement.getJobId() != null){
			 writeRow.createCell(0).setCellValue(requirement.getJobId()); 
		}
		if(requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())){
			 writeRow.createCell(1).setCellValue(requirement.getJobTitle()); 
		}
		if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp()) && requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
			StringBuilder sb = new StringBuilder();
			writeRow.createCell(2).setCellValue(sb.append(requirement.getMinExp()).append("-").append(requirement.getMaxExp()).append(" years").toString());
		}else{
			if(requirement.getMinExp() != null && !"".equalsIgnoreCase(requirement.getMinExp())){
				StringBuilder sb = new StringBuilder();
				writeRow.createCell(2).setCellValue(sb.append("0 - ").append(requirement.getMinExp()).append("years").toString());
			}else{
				if(requirement.getMaxExp() !=null && !"".equalsIgnoreCase(requirement.getMaxExp())){
					StringBuilder sb = new StringBuilder();
					writeRow.createCell(2).setCellValue(sb.append("0 - ").append(requirement.getMaxExp()).append("years").toString());
				}
			}
		}
		
		if(requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()) && requirement.getRateType() != null && !"".equalsIgnoreCase(requirement.getRateType())){
			StringBuilder sb = new StringBuilder(); 
			writeRow.createCell(3).setCellValue(sb.append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString()); 
		}
		if(requirement.getCountry() != null && !"".equalsIgnoreCase(requirement.getCountry())){
			 writeRow.createCell(4).setCellValue(requirement.getCountry()); 
		}
		if(requirement.getSkills() != null && !"".equalsIgnoreCase(requirement.getSkills())){
			 writeRow.createCell(5).setCellValue(requirement.getSkills()); 
		}
		if(requirement.getManager() != null && !"".equalsIgnoreCase(requirement.getManager())){
			 writeRow.createCell(6).setCellValue(requirement.getManager()); 
		}
		if(requirement.getPriority() != null && !"".equalsIgnoreCase(requirement.getPriority())){
			 writeRow.createCell(7).setCellValue(requirement.getPriority()); 
		}
		if(requirement.getSubmissionTemplateId() != null ){
			 writeRow.createCell(8).setCellValue(requirement.getSubmissionTemplateId().getTemplateName()); 
		}
		if(requirement.getClientName() != null && !"".equalsIgnoreCase(requirement.getClientName())){
			 writeRow.createCell(9).setCellValue(requirement.getClientName()); 
		}
		if(requirement.getClientContactName() != null && !"".equalsIgnoreCase(requirement.getClientContactName())){
			 writeRow.createCell(10).setCellValue(requirement.getClientContactName()); 
		}
		if(requirement.getClientContactEmail() != null && !"".equalsIgnoreCase(requirement.getClientContactEmail())){
			 writeRow.createCell(11).setCellValue(requirement.getClientContactEmail()); 
		}
		if(requirement.getRequirementSubmissionStatus() != null && !"".equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())){
			 writeRow.createCell(12).setCellValue(requirement.getRequirementSubmissionStatus().toString()); 
 		}
		if(requirement.getCreatedDate() != null && !"".equalsIgnoreCase(requirement.getCreatedDate().toString())){
			 writeRow.createCell(13).setCellValue(commonUtil.dateStrFromOrgDateFormatter(requirement.getOrgLocationId().getDateFormat(), requirement.getCreatedDate())); 
		}
		return createExcelsheet;
	}

	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook, TimeSheetSubmissionCount requirementSubCount) {
		
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
		cell11.setCellValue("Closed : ");
		cell11.setCellStyle(style);

		Cell cell12 = writeRow.createCell(1);
		cell12.setCellValue(requirementSubCount.getCancelRequirementCount());
		cell12.setCellStyle(style);
		String headers [] = Constants.REQUIREMENT_EXCEL_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,8);

		return createExcelsheet;
	}

	@Override
	public Boolean checkRequirementIsReleasedOrNot(Long requirementId,Long orgLocationId) throws Exception {
		   
			Requirement  req = requirementDao.checkRequirementIsReleasedOrNot(requirementId,orgLocationId);
			Boolean requirementReleased = true;
			if(req!=null){
				
				return requirementReleased;
			}else{
				
				return false;
			}

	}
	
	@Override
	public Boolean validateRequirementTitle(String jobName,Long orgLocationId) throws Exception {
		return requirementDao.validateRequirementTitle(jobName, orgLocationId);
	}
	
	
	
	

	public void removedApproversInRequirementForParallel(String[] approver , Requirement requirement) throws Exception{
		
		if(requirement.getId() !=null){
			ArrayList<String> oldApproversList = new ArrayList<String>();
			ArrayList<String> newApproversList = new ArrayList<String>();
			ArrayList<String> uniqueApproversList = new ArrayList<String>();
			ArrayList<String> removedApproversList = new ArrayList<String>();
			
			for(String app : approver){
				 newApproversList.add(app);
			 }
			
			// bringing the old  requirementapprovalHierarachy  list based on the requirementID
			
			List<RequirementApproverHierarchy> requirementApproverHierarchies = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
			if(null != requirementApproverHierarchies){
				for(RequirementApproverHierarchy rah :requirementApproverHierarchies){
					oldApproversList.add(rah.getApproverUserId().getId().toString());
				}
				
				// seperating the lists
				if(null != oldApproversList){
					for (String item : oldApproversList) {
			    	    if (newApproversList.contains(item)) {
			    	    	uniqueApproversList.add(item);
			    	    } else {
			    	    	removedApproversList.add(item);
			    	    }
			    	}
					
					
					// Removing  the unselected approvals from  the RequirementApprovalHierarachy
					if(null != removedApproversList){
						for (String item : removedApproversList) {
							requirementApproverHierarchyService.deleteTheRequirementApproverByRequirementIdAndApproverId(Long.parseLong(item),requirement.getId());
						}
					}
				}
			}
		}
		
		
		
	}
	
	
	
	/*public void removedApproversInRequirementForParallel(String[] approver , Requirement requirement) throws Exception{
		
		if(requirement.getId() !=null){
			
			List<RequirementApproverHierarchy> requirementApproverHierarchies = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
			
			if(requirementApproverHierarchies != null ){
			    for(String app : approver){
					for(RequirementApproverHierarchy rah:requirementApproverHierarchies){  
							if(!app.equalsIgnoreCase(rah.getApproverUserId().getId().toString())){
								requirementApproverHierarchyService.deleteTheRequirementApproverByRequirementIdAndApproverId(rah);
							}
					 }
				 }
			}
		}
		
		
		
	}*/

	public void removedApproversInRequirementForSequential(User approver , Requirement requirement,Integer level) throws Exception{
		
				if(requirement.getId() !=null){
					List<RequirementApproverHierarchy> requirementApproverHierarchies = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
				    	if(requirementApproverHierarchies != null ){
							     for(RequirementApproverHierarchy rah:requirementApproverHierarchies){
							            if(approver.getId() != rah.getApproverUserId().getId() && level.equals(rah.getLevel())){
							            	requirementApproverHierarchyDao.deleteTheRequirementApproverByRequirementIdAndApproverId(rah);
							              }
							
								    }
						}
				}
		
		
	}

	@Override
	public Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId, Long userId)
			throws Exception {
		 	return  requirementDao.checkLoginUserIsRequirementManagerByRequirementIdAndUserId(requirementId, userId);
	}
	

	public List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId, User user,OrgLocations orgLocations) throws Exception{
	return requirementDao.getAllReleasedJobsForVendor(orgLocationId, orgRelationId, user,orgLocations);
	}


	@Override
	public List<Requirement> getAllRequirementTeplateJobsByOrgLocationId(Long orgLocationId, User user) throws Exception {
		
		List<Requirement>  list = requirementDao.getAllRequirementTeplateJobsByOrgLocationId(orgLocationId, user);
		
		for(Requirement req :list){
			if(req.getCreatedBy() != null){
				String lastName = "";
				if(StringUtils.isNotEmpty(req.getCreatedBy().getLastName())){
					lastName = " "+req.getCreatedBy().getLastName();
				}
				req.setCreatedByName(req.getCreatedBy().getFirstName()+lastName);
			}
			req.setRequirementCreatedDateToString(commonUtil.elkDateFormatter(req.getCreatedDate()));
		}
			
		return list;
	}

	@Override
	public Requirement storeRequirementTemplate(JSONObject jsonObj, OrgLocations orgLocations, User user)
			throws Exception {
		
		
		 Requirement reqTemplate = null;

        if (jsonObj.has("requirementId") && !(jsonObj.getString("requirementId").isEmpty())){
        	
        	  reqTemplate = requirementDao.getRequirementTemplateByRequirementId(Long.parseLong(jsonObj.getString("requirementId")), orgLocations.getId());
        	  
        	  if(reqTemplate == null){
        		  reqTemplate = new Requirement();
        	  }
			
		}else{
			reqTemplate = new Requirement();
		}
		
        if(jsonObj.getString("isTemplate") != null && !"".equalsIgnoreCase(jsonObj.getString("jobTitle"))){
		 reqTemplate.setIsTemplate(jsonObj.getString("isTemplate"));
        }
        
		 if(orgLocations != null){
			 reqTemplate.setOrgLocationId(orgLocations);
			}
		 
		 if(jsonObj.has("jobTitle") && !"".equalsIgnoreCase(jsonObj.getString("jobTitle"))){
			 reqTemplate.setJobTitle(jsonObj.getString("jobTitle"));
		}
			
		 if(jsonObj.has("jobType") && !"".equalsIgnoreCase(jsonObj.getString("jobType"))){	
					reqTemplate.setJobType(jsonObj.getString("jobType"));
			}	
			
		if(jsonObj.has("jobOtherType") && jsonObj.has("jobType") && "Other".equalsIgnoreCase(jsonObj.getString("jobType"))){
				reqTemplate.setOtherJobType(jsonObj.getString("jobOtherType"));
			}else{
				reqTemplate.setOtherJobType(null);
			}
			
			if(jsonObj.has("maxExp") && !"".equalsIgnoreCase(jsonObj.getString("maxExp"))){
				reqTemplate.setMaxExp(jsonObj.getString("maxExp"));
				}else{
					reqTemplate.setMaxExp(null);
				}
				
				if(jsonObj.has("minExp") && !"".equalsIgnoreCase(jsonObj.getString("minExp"))){
					reqTemplate.setMinExp(jsonObj.getString("minExp"));
				}else{
					reqTemplate.setMinExp(null);
				}
				
				if(jsonObj.has("rate") && !"".equalsIgnoreCase(jsonObj.getString("rate"))){
					reqTemplate.setRate(jsonObj.getString("rate"));
				}
				
				if(jsonObj.has("rateType") && !"".equalsIgnoreCase(jsonObj.getString("rateType"))){
					reqTemplate.setRateType(jsonObj.getString("rateType"));
				} 
				
				if(jsonObj.has("industryName") && !"".equalsIgnoreCase(jsonObj.getString("industryName"))){
					reqTemplate.setIndustryName(jsonObj.getString("industryName"));
				}
					if(jsonObj.has("maxRate") && !"".equalsIgnoreCase(jsonObj.getString("maxRate"))){
					reqTemplate.setMaxRate(jsonObj.getString("maxRate"));
				}
					if(jsonObj.has("travelAllowance") && !"".equalsIgnoreCase(jsonObj.getString("travelAllowance"))){
						reqTemplate.setTravelAllowance(jsonObj.getString("travelAllowance"));
					}
				if(jsonObj.has("description") && !"".equalsIgnoreCase(jsonObj.getString("description"))){
					reqTemplate.setDescription(jsonObj.getString("description"));
				}
				
				if(jsonObj.has("coolingOff") && !"".equalsIgnoreCase(jsonObj.getString("coolingOff"))){
					reqTemplate.setReqCoolingOffPeriod(jsonObj.getString("coolingOff"));
				}

				if(jsonObj.has("noticePeriod")&& !"".equalsIgnoreCase(jsonObj.getString("noticePeriod"))){
					reqTemplate.setNoticePeriod(jsonObj.getString("noticePeriod"));	
					String noticePeriod =null;
					try{
						JSONArray jsonArray = jsonObj.getJSONArray("noticePeriod");
						noticePeriod = jsonArray.toString();
					}catch(JSONException e){
						noticePeriod =jsonObj.getString("noticePeriod");
					}
					noticePeriod = noticePeriod.replaceAll("\\[", "").replaceAll("\\]","");
					noticePeriod = noticePeriod.replaceAll("\"", "");
					reqTemplate.setNoticePeriod(noticePeriod);
				}
				
				if(jsonObj.has("currencyCode") && !"".equalsIgnoreCase(jsonObj.getString("currencyCode"))){
					reqTemplate.setCurrencyCode(jsonObj.getString("currencyCode"));
				}
				
	             if(jsonObj.has("skills") && !"".equalsIgnoreCase(jsonObj.getString("skills"))){
					
					String skillId =null;
					try{
						JSONArray jsonArray = jsonObj.getJSONArray("skills");
						skillId = jsonArray.toString();
					}
					catch(JSONException e){
						skillId =jsonObj.getString("skills");
					}
					skillId = skillId.replaceAll("\\[", "").replaceAll("\\]","");
					
					
					skillId = skillId.replaceAll("\"", "");
					reqTemplate.setSkills(skillId);
					
				}else{
					reqTemplate.setSkills(null);	
				}
				
				if(jsonObj.has("education") && !"".equalsIgnoreCase(jsonObj.getString("education"))){
					
	             String educationId =null;
					try{
						JSONArray jsonArray = jsonObj.getJSONArray("education");
						educationId = jsonArray.toString();
					}
					catch(JSONException e){
						educationId =jsonObj.getString("education");
					}
					educationId = educationId.replaceAll("\\[", "").replaceAll("\\]","");
					educationId = educationId.replaceAll("\"", "");
					
					reqTemplate.setEducation(educationId);
					
				 }else{
					 reqTemplate.setEducation(null);
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
	public Requirement getRequirementTemplateByRequirementId(Long requirementId,Long orgLocationId) throws Exception {
		return requirementDao.getRequirementTemplateByRequirementId(requirementId,orgLocationId);
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
	public Integer getRequirementsCountAsApprover(Long userId,Long orgLocationId) throws Exception {
		return requirementDao.getRequirementsCountAsApprover(userId,orgLocationId);
	}

	@Override
	public List<Requirement> getManagerAssignedRequirementsList(Long userId, Long orgLocationId) throws Exception {
		return requirementDao.getManagerAssignedRequirementsList(userId, orgLocationId);
	}
	

	@Override
	public Boolean checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(Long userId,
			Long orgLocationId) throws Exception {
		
		return requirementDao.checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(userId, orgLocationId);
	
	}

	@Override
	public Requirement getRequirementByRequirementIdOrgLocationsId(String id, Long requirementId, Long orgLocationsId)
			throws Exception {
		return requirementDao.getRequirementByRequirementIdOrgLocationsId(id, requirementId,orgLocationsId);
	}

	@Override
	public Boolean validateJobid(String id, Long orgLocationsId) throws Exception {
		return requirementDao.validateJobid(id,orgLocationsId);
	}
	
	@Override
	public List<String> getAllRequirementSkillsByRequirementI(Long requirementId) throws Exception {
		return requirementDao.getAllRequirementSkillsByRequirementI(requirementId);
	}
	
	@Override
	public List<String> getAllRequirementIndustryByRequirementId(Long requirementId) throws Exception {
		return requirementDao.getAllRequirementIndustryByRequirementId(requirementId);
	}
/*	@Override
	public List<String> getAllSkillsBySkillCategory() throws Exception{
		return lookUpDataService.getAllSkillsBySkillCategory();
		
	}
*/
	@Override
	public ResponseList<Requirement> searchRequirement(OrgLocations orgLocation, RequirementSearchDTO search,Org org,String userRole,User user) throws Exception {

		ResponseList<Requirement> resp= new ResponseList<>();
		/*
		 * This array is used to enable the sorting feature in data tables. And, these names should match the ELK Candidate index field names.
		 */
		String[] tableColumns = {"", "jobTitle", "client", "jobType", "country", "", "manager", "createdDate", "", ""};
		
		int sortColumnIndex = 8; //by default sorting using updated date
		boolean sortColumnOrder = false; //by default descending order
		if(search.getOrder() != null && !search.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(search.getOrder().get(0).get("column"));
			sortColumnOrder = !search.getOrder().get(0).get("dir").equalsIgnoreCase("desc");
		}
		
		
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		 
		boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
		
		BoolQueryBuilder boolQueryBuilderForCount = new BoolQueryBuilder();
		boolQueryBuilderForCount.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
	   	 //Exclude Keyword
//		 if(jsonObj.has("excludeKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("excludeKeyWord"))){
//			 ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
//			 if(excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()){
//			 	 BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
//				 for(String keywd : excludeKeyWordArray){
//					 BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
//					ArrayList<MatchQueryBuilder> matchQueryBuilderList = getMatchQueryBuilderList(keywd);
//					if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
//						for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
//							excludeBoolSubQueryBuilder.should(matchQueryBuilder);
//						}
//						excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
//					}
//				}
//				 boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
//			  } 
//		 }
			
			//Any Keyword
//		 if(jsonObj.has("anyKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("anyKeyWord"))){
//			 ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyKeyWord");
//				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
//					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
//					 for(String keywd : anyKeyWordArray){
//						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
//						ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
//						if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
//							for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
//								anyBoolSubQueryBuilder.should(matchQueryBuilder);
//							}
//							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
//						}
//					}
//					 boolQueryBuilder.must(anyBoolQueryBuilder);
//				} 
//		 }
		
		//All Keywords
//		 if(jsonObj.has("allKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("allKeyWord"))){
//				ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeyWord");
//				if(allKeyWordArray != null && !allKeyWordArray.isEmpty()){
//					 BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
//					 for(String keywd : allKeyWordArray){
//						 BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
//						ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
//						if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
//							for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
//								allBoolSubQueryBuilder.should(matchQueryBuilder);
//							}
//							allBoolQueryBuilder.must(allBoolSubQueryBuilder);
//						}
//					}
//					 boolQueryBuilder.must(allBoolQueryBuilder);
//				}
//		 }
			
			
			//Requirement Title
			if(StringUtils.isNotEmpty(search.getRequirementTitle())){
				boolQueryBuilder.must(QueryBuilders.matchQuery("jobTitle", search.getRequirementTitle()));
		    }
			// jobId
			if(StringUtils.isNotEmpty(search.getJobId())){
				boolQueryBuilder.must(QueryBuilders.matchQuery("jobId", search.getJobId()));
		    }
		   //Country
			if(StringUtils.isNotEmpty(search.getCountry())){
				boolQueryBuilder.must(QueryBuilders.matchQuery("country", search.getCountry()));
		    }
			 //State
			if(StringUtils.isNotEmpty(search.getState())){
				boolQueryBuilder.must(QueryBuilders.matchQuery("state", search.getState()));
		    }
			//City
			if(StringUtils.isNotEmpty(search.getCity())){
				boolQueryBuilder.must(QueryBuilders.matchQuery("city", search.getCity()));
		    }
			
			if(StringUtils.isNotEmpty(search.getNoticePeriod())){
				ArrayList<String> noticePeriodArray = getSearchKeywordsFromChosenSelectField(search.getNoticePeriod(), "noticePeriod");
				if(noticePeriodArray != null && !noticePeriodArray.isEmpty()){
					BoolQueryBuilder edunBoolQueryBuilder = new BoolQueryBuilder();
					for(String keywd : noticePeriodArray){
						edunBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("noticePeriod", keywd));
					}
					boolQueryBuilder.must(edunBoolQueryBuilder);
				} 
			}
			
			
			
			// currency
//		    if(jsonObj.has("currencyCode") && !"".equalsIgnoreCase(jsonObj.getString("currencyCode"))){
//				boolQueryBuilder.must(QueryBuilders.matchQuery("currentRateCurrency", jsonObj.getString("currencyCode")));
//		    }
		    
		     //rate
//		    if(jsonObj.has("rate") && !"".equalsIgnoreCase(jsonObj.getString("rate"))){
//		    	String rate= jsonObj.getString("rate").replaceAll("[^0-9\\.]+", "");
//		    	try{
//		    		boolQueryBuilder.must(QueryBuilders.rangeQuery("rate").gte(new Float(rate)));
//		    	}
//		    	catch(NumberFormatException e){}
//		    }
			
			
		   //Rate Type
//			if(jsonObj.has("rateType") && !"".equalsIgnoreCase(jsonObj.getString("rateType"))){
//				boolQueryBuilder.must(QueryBuilders.matchQuery("rateType", jsonObj.getString("rateType")));
//		    }
			
			
			//minExpYears && maxExpYears
//		    if(jsonObj.has("minExp") && !"".equalsIgnoreCase(jsonObj.getString("minExp")) && jsonObj.has("maxExp")  && !"".equalsIgnoreCase(jsonObj.getString("maxExp"))){
//		    	String minYears= jsonObj.getString("minExp").replaceAll("[^0-9\\.]+", "");
//		    	String maxYears= jsonObj.getString("maxExp").replaceAll("[^0-9\\.]+", "");
//		    	try{
//		    		boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").gte(new Integer(minYears)).lte(new Integer(maxYears)));
//		    	}
//		    	catch(NumberFormatException e){}
//		    }
			//minExpYears
//		    else if(jsonObj.has("minExp") && !"".equalsIgnoreCase(jsonObj.getString("minExp"))){
//		    	String minYears= jsonObj.getString("minExp").replaceAll("[^0-9\\.]+", "");
//		    	try{
//		    		boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").gte(new Integer(minYears)));
//		    	}
//		    	catch(NumberFormatException e){}
//		    }
		    //maxExpYears
//		    else if(jsonObj.has("maxExp") && !"".equalsIgnoreCase(jsonObj.getString("maxExp"))){
//           	String maxYears= jsonObj.getString("maxExp").replaceAll("[^0-9\\.]+", "");
//           	try{
//		    		boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").lte(new Integer(maxYears)));
//		    	}
//		    	catch(NumberFormatException e){}
//		    }
			
		    //requirementStatu
			if(StringUtils.isNotEmpty(search.getRequirementStatus())){
				ArrayList<String> requirementStatusArray = getSearchKeywordsFromChosenSelectField(search.getRequirementStatus(), "requirementStatus");
				if(requirementStatusArray != null && !requirementStatusArray.isEmpty()){
					BoolQueryBuilder reqStatusBoolQueryBuilder = new BoolQueryBuilder();
					for(String keywd : requirementStatusArray){
						reqStatusBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
					}
					boolQueryBuilder.must(reqStatusBoolQueryBuilder);
				} 
			}
			
			
			if(StringUtils.isNotEmpty(search.getUpdatedOn())){
				String updatedOn = search.getUpdatedOn();
				String upOn = UpdateOnToDate(updatedOn);
				if(!Constants.ALL_RECORDS.equalsIgnoreCase(upOn)){
				 RangeQueryBuilder qb = QueryBuilders.rangeQuery("createdDate").gte(upOn);
				boolQueryBuilder.must(qb);
				}
			}
			//Educations
//			if(jsonObj.has("education") && !"".equalsIgnoreCase(jsonObj.getString("education"))){
//				ArrayList<String> educationArray = getSearchKeywordsFromChosenSelectField(jsonObj, "education");
//				if(educationArray != null && !educationArray.isEmpty()){
//					BoolQueryBuilder edunBoolQueryBuilder = new BoolQueryBuilder();
//					for(String keywd : educationArray){
//						edunBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("education", keywd));
//					}
//					boolQueryBuilder.must(edunBoolQueryBuilder);
//				} 
//			}
			
			//client
			if(StringUtils.isNotEmpty(search.getClient())){
				ArrayList<String> employerArray = getSearchKeywordsFromChosenSelectField(search.getClient(), "client");
				if(employerArray != null && !employerArray.isEmpty()){
					BoolQueryBuilder employerBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : employerArray){
						employerBoolQueryBuilder.should(QueryBuilders.matchQuery("client", keywd));
					}
					boolQueryBuilder.must(employerBoolQueryBuilder);
				}
			}
			//Requirement Skills
//			if(jsonObj.has("skills") && !"".equalsIgnoreCase(jsonObj.getString("skills"))){
//				ArrayList<String> candidateSkillsArray = getSearchKeywordsFromChosenSelectField(jsonObj, "skills");
//				if(candidateSkillsArray != null && !candidateSkillsArray.isEmpty()){
//					BoolQueryBuilder skillBoolQueryBuilder = QueryBuilders.boolQuery();
//					for(String keywd : candidateSkillsArray){
//						skillBoolQueryBuilder.should(QueryBuilders.matchQuery("skills", keywd));
//					}
//					boolQueryBuilder.must(skillBoolQueryBuilder);
//				} 	
//			}
			
			SearchResponse searchResponse = sourceleadELKBaseIndexer
					.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,
							search.getStart(), search.getLength(), tableColumns[sortColumnIndex], sortColumnOrder ? SortOrder.ASC : SortOrder.DESC, boolQueryBuilder);
			SearchHit searchHits[] = searchResponse.getHits().getHits();
			//boolQueryBuilderForCount
			List<Requirement>   reqList = new LinkedList<>();
			if(searchHits != null && searchHits.length >0){
				for (SearchHit hit : searchHits) {
					String sourceAsString = hit.getSourceAsString();
					ObjectMapper mapper = new ObjectMapper();
					if (sourceAsString != null) {
						Requirement requirement = null;
						RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
		            	if(userRole != null&&( "ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	else if(requirementIndex.getReqCreatedBy().equals(user.getId()) && requirementIndex.getIsTemplate()!=null && requirementIndex.getIsTemplate().equalsIgnoreCase("Y"))
		            	{
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	
		            	else if((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
		            			||((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString()))
		            			&& ("APPROVED".equalsIgnoreCase(requirementIndex.getReqLevel1ApproverStatus()) || "OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()) || "SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
		            			)){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	else if(requirementIndex.getReqLevel2ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel2ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())
		            			&&(requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&"APPROVED".equalsIgnoreCase(requirementIndex.getReqLevel2ApproverStatus()))
		            			|| (requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())
		            			&&("APPROVED".equalsIgnoreCase(requirementIndex.getReqLevel2ApproverStatus())||"OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()) || "SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
		            			)){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	
		            	else if(requirementIndex.getManagerUserId().equalsIgnoreCase(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	
		            	else if(requirementIndex.getTeamOwnerUserId()!=null&&Arrays.asList(requirementIndex.getTeamOwnerUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	else if(requirementIndex.getTeamAssignUserId()!=null&&Arrays.asList(requirementIndex.getTeamAssignUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
		            		//&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	
		            	
		            	if(requirement !=null){
		            		reqList.add(requirement);
		            	}
		            }
				}
			}
		 resp.setRecordsFiltered(Long.valueOf(searchResponse.getHits().getTotalHits()).intValue());
		 SearchResponse searchResponseForCount = sourceleadELKBaseIndexer
				.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_INDEX,
						0, 10, "createdDate", SortOrder.DESC, boolQueryBuilderForCount);
		
		 resp.setRecordsTotal(searchResponseForCount == null ? resp.getRecordsFiltered() : Long.valueOf(searchResponseForCount.getHits().getTotalHits()).intValue());
	     resp.setData(reqList);

      return resp;
		
	}
	 private Requirement getData(RequirementIndex requirementIndex, User user,OrgLocations orgLocations) throws Exception {
		 Requirement requirement = new Requirement();
		requirement.setId(requirementIndex.getRequirementId());
     	requirement.setJobId(requirementIndex.getJobId());
     	requirement.setJobType(requirementIndex.getJobType());
     	requirement.setOtherJobType(requirementIndex.getOtherJobType());
     	requirement.setJobTitle(requirementIndex.getJobTitle());
     	String convertedStirng = requirementIndex.getCreatedDate();
     	
     	DateFormat elkFormat = new SimpleDateFormat("yyyy-MM-dd"); 
     	Date elkDate = elkFormat.parse(convertedStirng);
     	requirement.setPostedDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),elkDate));
     	if(requirementIndex.getMinExperience() !=null){
     		requirement.setMinExp(requirementIndex.getMinExperience().toString());	
     	}
     	if(requirementIndex.getSkills() !=null){
     		String skills = getReqSkills(requirementIndex.getSkills());
     		requirement.setSkills(skills);
     	}
     	if(requirementIndex.getMaxExperience() !=null){
     		requirement.setMaxExp(requirementIndex.getMaxExperience().toString());
     	}
     	requirement.setPostedOnStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),elkDate));
     	requirement.setClientName(requirementIndex.getClient());
     	requirement.setCountry(requirementIndex.getCountry());
     	requirement.setState(requirementIndex.getState());
     	requirement.setCity(requirementIndex.getCity());
     	requirement.setSearchType("Requirement");
     	requirement.setPositions(requirementIndex.getNumberOfPositions());
     	if(null != requirementIndex.getNumberOfApplicants()){
     		requirement.setTotalApplicants(Integer.parseInt(requirementIndex.getNumberOfApplicants()));
     	}
     	requirement.setManager(requirementIndex.getManager());
     	if(StringUtils.isNotEmpty(requirementIndex.getRequirementStatus())){
         	requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.valueOf(requirementIndex.getRequirementStatus()));
     	}
     	Boolean isLoginUserAsTeamMember =  requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementIndex.getRequirementId(), user.getId());
		requirement.setIsLoginUserAsTeamMember(isLoginUserAsTeamMember.toString());

       if(StringUtils.isNotEmpty(requirementIndex.getReleasedBy())){
			List<Long> userId = new ArrayList<Long>();
			for (String s : requirementIndex.getReleasedBy().split(",")){
				userId.add(Long.parseLong(s));
			 }
     		requirement.setRequirementReleasedUserIds(userId);
		}
       if(null != requirementIndex.getRequirementUUID()){
   		requirement.setRequirementUUID(requirementIndex.getRequirementUUID());	
   	   }
     	return requirement;
	}

	private String getReqSkills(String[] skills) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<skills.length;i++){
			sb.append(skills[i]).append(",");
		}
		String str = sb.toString();
		return str.substring(0, str.length()-1);
	}

	private ArrayList<String> getSearchKeywordsFromChosenSelectField(String paramValue,String param) throws Exception {
		 ArrayList<String> keyWordList = null;
		 if(StringUtils.isNotEmpty(paramValue)){
			 keyWordList = new ArrayList<String>(Arrays.asList(paramValue.split(",")));
		 }
		 else{
			 keyWordList = new ArrayList<String>(); 
		 }
		
        return keyWordList;
    }
	 
	private ArrayList<String> getSearchValuesFromChosenSelectField(String searchValue) {
		 ArrayList<String> keyWordList = null;
		 if(searchValue !=null && !"".equalsIgnoreCase(searchValue)){
			 String searchKeywordString = searchValue.replaceAll("\\[", "").replaceAll("\\]","");
		     keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		 }
         return keyWordList;
	}
	
	
	 private ArrayList<MatchQueryBuilder> getMatchQueryBuilderList(String keywd){
		   
		   ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("client", keywd));
			
			matchQueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("jobId", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("clientContactName", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("clientContactEmail", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("manager", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("priority", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("numberOfPositions", keywd));
			
			
			Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(matcher.find()){
				Float expYears = Float.valueOf(matcher.group());
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			}

				
			return matchQueryBuilderList; 
	 }


	 private ArrayList<MatchQueryBuilder> getMatchQueryBuilderListForUniversalSearch(String keywd){
		   
		   ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("client", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("priority", keywd));
			Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(matcher.find()){
				Float expYears = Float.valueOf(matcher.group());
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
			}

				
			return matchQueryBuilderList; 
	 }

	 

	 public String UpdateOnToDate(String updatedOn){
		
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     if((Constants.ONE_DAY_AGO).equalsIgnoreCase(updatedOn)){
	        cal.add(Calendar.DATE, -1);    
	        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_DAY_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -3);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_WEEK_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -7);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.TWO_WEEK_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -14);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_MONTH_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -30);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_MONTH_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -90);    
		        return  dateFormat.format(cal.getTime());
		 }else{
			 return updatedOn;
		 }
	 }
 
	 

	@Override
	public List<Object> getAllUniversalSearchRequirementList(OrgLocations orgLocation, String searchValue,String userRole, User user) throws Exception {

		List<Object>   searchList = new ArrayList<>();
		 Long maxResults = requirementDao.getRequirementListByOrgLocationID(orgLocation.getId());
		 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
		 if(searchValue != null && !"".equalsIgnoreCase(searchValue)){
			 ArrayList<String> anyKeyWordArray = getSearchValuesFromChosenSelectField(searchValue);
				if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
					 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : anyKeyWordArray){
						 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderListForUniversalSearch(keywd);
						if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
							for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
								anyBoolSubQueryBuilder.should(matchQueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilder.must(anyBoolQueryBuilder);
				} 
		 }
		 
		 
		 
		 
		 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
			
			if(searchHits != null && searchHits.length >0){
				for (SearchHit hit : searchHits) {
					String sourceAsString = hit.getSourceAsString();
					ObjectMapper mapper = new ObjectMapper();
					if (sourceAsString != null) {
						Requirement requirement = null;
						RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
		            	if(userRole != null&&( "ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	else if(requirementIndex.getReqCreatedBy().equals(user.getId()) && requirementIndex.getIsTemplate()!=null && requirementIndex.getIsTemplate().equalsIgnoreCase("Y"))
		            	{
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	
		            	else if((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
		            			||((requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString()))
		            			&& ("APPROVED".equalsIgnoreCase(requirementIndex.getReqLevel1ApproverStatus()) || "OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
		            			)){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	else if(requirementIndex.getReqLevel2ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel2ApproverUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())
		            			&&(requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())&&"APPROVED".equalsIgnoreCase(requirementIndex.getReqLevel2ApproverStatus()))
		            			|| (requirementIndex.getReqLevel1ApproverUserId()!=null&&Arrays.asList(requirementIndex.getReqLevel1ApproverUserId()).contains(user.getId().toString())
		            			&&("APPROVED".equalsIgnoreCase(requirementIndex.getReqLevel2ApproverStatus())||"OPEN".equalsIgnoreCase(requirementIndex.getRequirementStatus()))
		            			)){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	
		            	else if(requirementIndex.getManagerUserId().equalsIgnoreCase(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	
		            	else if(requirementIndex.getTeamOwnerUserId()!=null&&Arrays.asList(requirementIndex.getTeamOwnerUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	else if(requirementIndex.getTeamAssignUserId()!=null&&Arrays.asList(requirementIndex.getTeamAssignUserId()).contains(user.getId().toString())&&!"DRAFT".equalsIgnoreCase(requirementIndex.getRequirementStatus())&&!"SUBMITTED".equalsIgnoreCase(requirementIndex.getRequirementStatus())){
		            		requirement = getData(requirementIndex,user,orgLocation);
		            	}
		            	if(null != requirementIndex.getRequirementUUID()){
		            		requirement.setRequirementUUID(requirementIndex.getRequirementUUID());
		            	}
		            	searchList.add(requirement);
		            }
				}
			}
			
		return searchList;
	
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
		return requirementDao.getAllRequirementsByOrglocationIdAndUserId(orgLocationId,user);
	}

	@Override
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(Long orgLocationId, User user)
			throws Exception {
		return requirementDao.getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(orgLocationId,user);
	}
	
	@Override
	public Integer getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(Long orgLocationId, User user)	throws Exception {
		return requirementDao.getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(orgLocationId,user);
	}

	@Override
	public List<Requirement> getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(
			Long orgLocationId,  TeamMembers teamMembers) throws Exception {
		return requirementDao.getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(orgLocationId,teamMembers);
	}


	@Override
	public Long getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user,
			 Date startDateUTC, Date endDateUTC) throws Exception {
		
		return requirementDao.getCountOfAllRequirementsByCreatedUserAndOrglocationId(orgLocationId, user, startDateUTC, endDateUTC);
	}


	@Override
	public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId, Date startDateUTC, Date endDateUTC) {
		return requirementDao.getTotalRequirementsReleasedToSpoc(relationId, spocId,startDateUTC,endDateUTC);
	}
	
	@Override
	public List<Requirement> getRequirementsForSOWByOrgLocationId(
			Long orgLocationId, String sowType) throws Exception {
		return requirementDao.getRequirementsForSOWByOrgLocationId(orgLocationId, sowType);
	}


	@Override
	public List<String> getAllRequirementEducationByRequirementId(Long requirementId){
		return requirementDao.getAllRequirementEducationByRequirementId(requirementId);
	}
	
	@Override
	public Requirement getRequirementDetailsById(Long requirementId) throws Exception {
		return requirementDao.getRequirementDetailsById(requirementId);
	}
	
	@Override
	public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays)
			throws Exception {
		
			return requirementDao.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(orgLocationId, userType, userId, userCode, numberOfDays);
		
	}


	@Override
	public List<Requirement> getAllReleasedJobsForVendorBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId,
			Date startDateUTC, Date endDateUTC) throws Exception {
		return requirementDao.getAllReleasedJobsForVendorBasedOnStartAndEndDates(orgLocationId, orgRelationId, startDateUTC, endDateUTC);
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
	public List<Requirement> getAllClientJobsBasedonRelationId(Long locationId, Long relationId,String userRoleName,User user,String status) throws Exception
	{
		
		return requirementDao.getAllClientJobsBasedonRelationId(locationId, relationId,userRoleName,user,status);
	}
	
	@Override
	public List<Requirement> getAllClosedRequirementsByStartDateAndEndDate(Date startDateUTC, Date endDateUTC) throws Exception
	{
		return requirementDao.getAllClosedRequirementsByStartDateAndEndDate(startDateUTC, endDateUTC);
	}
	
	
	
	@Override
	public List<Requirement> getAllVendorJobsBasedonRelationId(Long locationId,Long relationId, String userRoleName,User user,String status)  {
		return requirementDao.getAllVendorJobsBasedonRelationId(locationId,relationId, userRoleName,user,status);
	}


	@Override
	public List<String> getAllRequirementVisaByRequirementI(Long requirementId) throws Exception {
		return requirementDao.getAllRequirementVisaByRequirementI(requirementId);
	}

	@Override
	public boolean checkForOldrecordsUsingOrgLocationid(Long orglocationId) throws Exception {
		  boolean flag = requirementDao.checkForOldrecordsUsingOrgLocationid(orglocationId,"");
		  if(flag){
			 flag = addGeneratedIdRecordsToJobsForOldRecords(orglocationId);
		  }
		  return flag;
	}

	public boolean addGeneratedIdRecordsToJobsForOldRecords(Long orglocationId) throws Exception {
		List<Requirement> requirementList =  requirementDao.addGeneratedIdRecordsToJobsForOldRecords(orglocationId);
		List<Candidate> candidateList =  requirementDao.addGeneratedIdRecordsToCandidatesForOldRecords(orglocationId);
		List<Projects> projectList =  requirementDao.addGeneratedIdRecordsToProjectsForOldRecords(orglocationId);
		boolean data = false ;
		int jobsGeneratedId = 0;
		int candidateGeneratedId = 0;
		int projectsGeneratedId = 0;
		String finalJobsGeneratedId = null;
		String finalCandidatesGeneratedId = null;
		String finalProjectsGeneratedId = null;
		// for jobs
		OrgLocations orgLocation = orgLocationService.getOrgLocations(orglocationId);
		if(!requirementList.isEmpty() && requirementList!= null){
		 for(Requirement req:requirementList){
			if(req.getGeneratedId() == null){
				if(orgLocation.getGeneratedUserId()!= null && !orgLocation.getGeneratedUserId().equalsIgnoreCase("")){
					jobsGeneratedId = jobsGeneratedId + 1;
					final DecimalFormat decimalFormat = new DecimalFormat("0");                
			        String ConvetredjobsId = decimalFormat.format(jobsGeneratedId);
			        StringBuffer sb = new StringBuffer();
			        finalJobsGeneratedId = sb.append(orgLocation.getGeneratedUserId().toUpperCase()).append(Constants.JOB_ENTITY).append(ConvetredjobsId).toString();
			        req.setGeneratedId(finalJobsGeneratedId);
			        requirementDao.storeRequirement(req);
			        data =  true;
				}
			}
		}
		 System.out.println("Requirement data successfully");
	 }if(!candidateList.isEmpty() && candidateList!= null){
		for(Candidate candidate:candidateList){
			if(candidate.getGeneratedId() == null){
				if(orgLocation.getGeneratedUserId()!= null && !orgLocation.getGeneratedUserId().equalsIgnoreCase("")){
					candidateGeneratedId = candidateGeneratedId + 1;
					final DecimalFormat decimalFormat = new DecimalFormat("0");                
			        String ConvetredjobsId = decimalFormat.format(candidateGeneratedId);
			        StringBuffer sb = new StringBuffer();
			        finalCandidatesGeneratedId = sb.append(orgLocation.getGeneratedUserId().toUpperCase()).append(Constants.CANDIDATE_ENTITY).append(ConvetredjobsId).toString();
			        candidate.setGeneratedId(finalCandidatesGeneratedId);
			        candidateDao.storeCandidate(candidate);
			        data =  true;
				}
			}
		}
		System.out.println("candidate data successfully");
	}if(!projectList.isEmpty() && projectList!= null){
		for(Projects projects:projectList){
			if(projects.getGeneratedId() == null){
				if(orgLocation.getGeneratedUserId()!= null && !orgLocation.getGeneratedUserId().equalsIgnoreCase("")){
					projectsGeneratedId = projectsGeneratedId + 1;
					final DecimalFormat decimalFormat = new DecimalFormat("0");                
			        String ConvetredjobsId = decimalFormat.format(projectsGeneratedId);
			        StringBuffer sb = new StringBuffer();
			        finalProjectsGeneratedId = sb.append(orgLocation.getGeneratedUserId().toUpperCase()).append(Constants.PROJECT_ENTITY).append(ConvetredjobsId).toString();
			        projects.setGeneratedId(finalProjectsGeneratedId);
			        projectsDao.updateProject(projects);
			        data =  true;
				}
			}
		} 
	}
		return data;
   }
}