package com.bis.sourcelead.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.dto.ImportTimesheetDTO;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * This is the commonutil for validating data migration
 * application
 * 
 * @author: Venkat Kesav
 * 
 */
@Component
public class DataMigrationCommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(DataMigrationCommonUtil.class);
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequirementService requirementService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private SubmissionTemplateService submissionTemplateService;

	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private AssetCategoryService assetCategoryService;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	/*@Autowired
	private CalendarUtil calendarUtil;*/
	
	@Autowired
	private ProjectTasksService  projectTasksService;
	
	@Autowired
	private TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService; 
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private TimeSheetDayEntriesDao timesheetDayEntriesDao;
	
	
	
	@SuppressWarnings("unchecked")
	public void dataValidationForJobs(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation) throws Exception
	{
		logger.debug("In dataValidationForJobs method...");
		    
				Boolean flag;
				
				if(dataMigrationDTOJsonCopy.getString("priority").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("priority"))) {
					errorSet.put("priority", "Priority field is missing");
				}
				if(dataMigrationDTOJsonCopy.getString("jobTitle").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("jobTitle"))) {
					errorSet.put("jobTitle", "JobTitle field is missing");
				}
				if(dataMigrationDTOJsonCopy.getString("location").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("location"))) {
					errorSet.put("location", "Address field is missing");
				}
				
				if(!dataMigrationDTOJsonCopy.getString("jobId").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("jobId"))) {
					flag = false;
					flag = requirementService.validateJobid(dataMigrationDTOJsonCopy.getString("jobId"), orgLocation.getId());
					if(flag){
						errorSet.put("jobId", dataMigrationDTOJsonCopy.getString("jobId")+" is Already Existed");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("visa").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("visa"))) {
					List<String> visaList = lookUpDataService.getAllVisaByCategory();
					StringBuffer visaSB = new StringBuffer();
					String[] visaListArr = dataMigrationDTOJsonCopy.getString("visa").trim().split(",");
					int incr = 0;
					for(String visaArr : visaListArr) {
						for(String visa : visaList) {
							if(visaArr.toLowerCase().equalsIgnoreCase(visa.toLowerCase())) {
								incr++;
								if(incr > 1) {
									visaSB.append(",");
								}
								visaSB.append(visaArr);
							}
						}
					}
					if(incr>0) {
						dataMigrationDTOJsonCopy.put("visa", visaSB.toString());
					}else {
						errorSet.put("visa", dataMigrationDTOJsonCopy.getString("visa").trim()+" is invalid immigration type");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("travelAllowance").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("travelAllowance"))) {
					String percent = dataMigrationDTOJsonCopy.getString("travelAllowance");
					int incr = 0;
					for(String percentage :Constants.PERCENTAGES) {
						if(percentage.equalsIgnoreCase(percent)){
							incr++;
							dataMigrationDTOJsonCopy.put("travelAllowance", percent);
						}
					}
					if(incr==0){
						errorSet.put("travelAllowance", dataMigrationDTOJsonCopy.getString("travelAllowance")+" is invalid Travel Allowance");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("priority").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("priority"))) {
					String jobPriority = dataMigrationDTOJsonCopy.getString("priority").toLowerCase();
					if(jobPriority.equalsIgnoreCase("medium") || jobPriority.equalsIgnoreCase("low") || jobPriority.equalsIgnoreCase("high") || jobPriority.equalsIgnoreCase("urgent")){
						jobPriority = jobPriority.substring(0, 1).toUpperCase()+jobPriority.substring(1, jobPriority.length());
						dataMigrationDTOJsonCopy.put("priority", jobPriority);
					}else{
						errorSet.put("priority", dataMigrationDTOJsonCopy.getString("priority")+" is invalid Priority");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("noticePeriod").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("noticePeriod"))) {
					String noticePeriod = dataMigrationDTOJsonCopy.getString("noticePeriod");
					if(noticePeriod.equalsIgnoreCase("Immediately Available") || noticePeriod.equalsIgnoreCase("15 Days") || noticePeriod.equalsIgnoreCase("1 Month") || noticePeriod.equalsIgnoreCase("2 Months") || noticePeriod.equalsIgnoreCase("3 Months")){
						dataMigrationDTOJsonCopy.put("noticePeriod", noticePeriod);
					}else{
						errorSet.put("noticePeriod", dataMigrationDTOJsonCopy.getString("noticePeriod")+" is invalid Notice period");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("experienceLevel").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("experienceLevel"))) {
					String experienceLevel = dataMigrationDTOJsonCopy.getString("experienceLevel");
					if(experienceLevel.equalsIgnoreCase("Level-1") || experienceLevel.equalsIgnoreCase("Level-2") || experienceLevel.equalsIgnoreCase("Level-3") || experienceLevel.equalsIgnoreCase("Level-4")){
						dataMigrationDTOJsonCopy.put("experienceLevel", experienceLevel);
					}else{
						errorSet.put("experienceLevel", dataMigrationDTOJsonCopy.getString("experienceLevel")+" is invalid Experience Level");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("minExp").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("minExp"))) {
					String minExp = dataMigrationDTOJsonCopy.getString("minExp");
					if(NumberUtils.isNumber(minExp)){
						if(Float.valueOf(minExp) <= 999){
							dataMigrationDTOJsonCopy.put("minExp", minExp);
						}else{
							errorSet.put("minExp", dataMigrationDTOJsonCopy.getString("minExp")+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put("minExp", dataMigrationDTOJsonCopy.getString("minExp")+" is invalid Min Experience");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("maxExp").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("maxExp"))) {
					String maxExp = dataMigrationDTOJsonCopy.getString("maxExp");
					if(NumberUtils.isNumber(maxExp)){
						if(Float.valueOf(maxExp) <= 999){
							dataMigrationDTOJsonCopy.put("maxExp", maxExp);
						}else{
							errorSet.put("maxExp", dataMigrationDTOJsonCopy.getString("maxExp")+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put("maxExp", dataMigrationDTOJsonCopy.getString("maxExp")+" is invalid Max Experience");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("maxExp").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("maxExp")) && !dataMigrationDTOJsonCopy.getString("minExp").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("minExp"))) {
					String maxExp = dataMigrationDTOJsonCopy.getString("maxExp");
					String minExp = dataMigrationDTOJsonCopy.getString("minExp");
					if(NumberUtils.isNumber(minExp) && NumberUtils.isNumber(maxExp)){
						if(Float.valueOf(minExp) <= 999 && Float.valueOf(maxExp) <= 999){
							if(Float.valueOf(minExp) > Float.valueOf(maxExp)) {
								errorSet.put("maxExpminExp", "Max experience should be greater than Min experience");
							}
						}
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("rate").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("rate"))) {
					String rate = dataMigrationDTOJsonCopy.getString("rate");
					if(NumberUtils.isNumber(rate)){
						if(Long.valueOf(rate) <= 99999999){
							dataMigrationDTOJsonCopy.put("rate", rate);
						}else{
							errorSet.put("rate", dataMigrationDTOJsonCopy.getString("rate")+" is invalid Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put("rate", dataMigrationDTOJsonCopy.getString("rate")+" is invalid Rate");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("clientRate").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("clientRate"))) {
					String clientRate = dataMigrationDTOJsonCopy.getString("clientRate");
					if(NumberUtils.isNumber(clientRate)){
						if(Long.valueOf(clientRate) <= 99999999){
							dataMigrationDTOJsonCopy.put("clientRate", clientRate);
						}else{
							errorSet.put("clientRate", dataMigrationDTOJsonCopy.getString("clientRate")+" is invalid Client Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put("clientRate", dataMigrationDTOJsonCopy.getString("clientRate")+" is invalid Client Rate");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("coolingOff").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("coolingOff"))) {
					String coolingOff = dataMigrationDTOJsonCopy.getString("coolingOff");
					String[] coolingOffSet = {"0 Days","1 Week","2 Weeks","3 Weeks","1 Month","2 Months","3 Months","4 Months","5 Months","6 Months","7 Months","8 Months","9 Months","10 Months","11 Months","1 Year"};
					if(ArrayUtils.contains( coolingOffSet, coolingOff)){
						dataMigrationDTOJsonCopy.put("coolingOff", coolingOff);
					}else{
						errorSet.put("coolingOff", dataMigrationDTOJsonCopy.getString("coolingOff")+" is invalid cooling-off period");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("closedDate").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("closedDate"))) {
					try {
						DateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.getString("closedDate"));
						String closedDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put("closedDate", closedDate);
					} catch (Exception e) {
						errorSet.put("closedDate", dataMigrationDTOJsonCopy.getString("closedDate")+" is invalid Close Date");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("manager").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("manager"))) {
					User approverList = userService.getTabAccessOwnersByOrgLocationIdEmailAndTabId(orgLocation.getId(), dataMigrationDTOJsonCopy.getString("manager"), Long.valueOf(Constants.TAB_REQUIREMENT));
					if(approverList == null){
						errorSet.put("manager", dataMigrationDTOJsonCopy.getString("manager")+" is invalid manager Email");
					}else{
						dataMigrationDTOJsonCopy.put("manager", approverList.getId().toString());
					}
				}else if(dataMigrationDTOJsonCopy.getString("manager").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("manager"))) {
					dataMigrationDTOJsonCopy.put("manager", user.getId().toString());
				}
				
				if(!dataMigrationDTOJsonCopy.getString("selectClient").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("selectClient"))) {
					OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.getString("selectClient"));
					if(orgRelations == null){
						errorSet.put("selectClient", dataMigrationDTOJsonCopy.getString("selectClient")+" is invalid Client");
					}else{
						dataMigrationDTOJsonCopy.put("selectClient", orgRelations.getId().toString());
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("selectContact").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("selectContact"))) {
					if(dataMigrationDTOJsonCopy.get("selectClient") != null){
						OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get("selectClient").toString());
						if(orgRelations != null){
							List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelations.getId(), user.getId(),orgLocation.getId(),"Client");
							if(orgSpocsList != null){
								for(OrgSpoc orgspoc:orgSpocsList){
									if(dataMigrationDTOJsonCopy.getString("selectContact").toLowerCase().equalsIgnoreCase(orgspoc.getClientUser().getEmail())){
										dataMigrationDTOJsonCopy.put("selectContact", orgspoc.getId().toString());
									}else{
										errorSet.put("selectContact", dataMigrationDTOJsonCopy.getString("selectContact")+" is invalid Client Contact");
									}
								}
							}else{
								errorSet.put("selectContact", dataMigrationDTOJsonCopy.getString("selectContact")+" is invalid Client Contact");
							}
						}else{
							errorSet.put("selectContact", dataMigrationDTOJsonCopy.getString("selectContact")+" is invalid Client Contact");
						}
						
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("clientSubmissionForm").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("clientSubmissionForm"))) {
					if(dataMigrationDTOJsonCopy.get("selectClient") != null && dataMigrationDTOJsonCopy.get("selectClient") != ""){
						OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get("selectClient").toString());
						if(orgRelations != null){
							Long clientLocationId = null;
							if(orgRelations.getLocationId().getId() == orgLocation.getId()){
								clientLocationId = orgRelations.getResponderLocationId().getId();
							}
							if(orgRelations.getResponderLocationId().getId() == orgLocation.getId()){
								clientLocationId = orgRelations.getLocationId().getId();
							}
							List<SubmissionTemplate> submissiontTemplateList  = submissionTemplateService.getSubmissionTemplateByClientLocationIdAndOrgLocationIdAndTemplateType(clientLocationId, orgLocation.getId(),"Submission");
							if(submissiontTemplateList != null){
								for(SubmissionTemplate submissiontTemplate:submissiontTemplateList){
									if(dataMigrationDTOJsonCopy.getString("clientSubmissionForm").toLowerCase().equalsIgnoreCase(submissiontTemplate.getTemplateName())){
										dataMigrationDTOJsonCopy.put("clientSubmissionForm", submissiontTemplate.getId().toString());
									}else{
										errorSet.put("clientSubmissionForm", dataMigrationDTOJsonCopy.getString("clientSubmissionForm")+" is invalid Submission Form");
									}
								}
							}else{
								errorSet.put("clientSubmissionForm", dataMigrationDTOJsonCopy.getString("clientSubmissionForm")+" is invalid Submission Form");
							}
						}else{
							errorSet.put("clientSubmissionForm", dataMigrationDTOJsonCopy.getString("clientSubmissionForm")+" is invalid Submission Form");
						}
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("assignedTo").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("assignedTo"))) {
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validAssTo = new ArrayList<String>();
					StringBuffer invalidAssMembers = new StringBuffer();
					String[] assTo = dataMigrationDTOJsonCopy.getString("assignedTo").split(",");
					//int assToCount = assTo.length;
					//int matchedCount = 0;
					for(User assignUser : usersList){
						for(String userEmail : assTo){
							if(assignUser.getEmail().toLowerCase().equalsIgnoreCase(userEmail.trim().toLowerCase())){
								validAssTo.add(assignUser.getId().toString()+",undefined");
								//matchedCount++;
							}else{
								invalidAssMembers.append(userEmail).append(", ");
							}
						}
					}
					if(validAssTo.size() > 0){
						errorSet.put("assignedTo", "Assigned members are invalid");
					}else{
						dataMigrationDTOJsonCopy.put("assignedTo", validAssTo);
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("parallelApprover").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("parallelApprover"))) {
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validApprovers = new ArrayList<String>();
					StringBuffer invalidApprovers = new StringBuffer();
					String[] apprs = dataMigrationDTOJsonCopy.getString("parallelApprover").split(",");
					for(User appUser : usersList){
						for(String userEmail : apprs){
							if(appUser.getEmail().toLowerCase().equalsIgnoreCase(userEmail.trim().toLowerCase())){
								validApprovers.add(appUser.getId().toString());
							}else{
								invalidApprovers.append(userEmail).append(", ");
							}
						}
					}
					if(validApprovers == null){
						errorSet.put("parallelApprover", "Approvers are invalid");
					}else{
						dataMigrationDTOJsonCopy.put("parallelApprover", validApprovers);
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("applicationFrom").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("applicationFrom"))) {
					flag = false;
					List<SubmissionTemplate>   submissionTempletesList = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocation.getId(),"Submission");
					for(SubmissionTemplate submissionTemplete : submissionTempletesList){
						if(submissionTemplete.getTemplateName().toLowerCase().equalsIgnoreCase("applicationFrom".toLowerCase())){
							flag = true;
							dataMigrationDTOJsonCopy.put("applicationFrom", submissionTemplete.getId().toString());
						}
					}
					if (!flag) {
						errorSet.put("applicationFrom", dataMigrationDTOJsonCopy.getString("applicationFrom") + " is invalid Application Form");
					}
				}
		
			if((dataMigrationDTOJsonCopy.getString("selectClient").equals(null) && !dataMigrationDTOJsonCopy.get("selectContact").equals(null)) || (dataMigrationDTOJsonCopy.get("selectClient").equals(null) && !dataMigrationDTOJsonCopy.get("clientSubmissionForm").equals(null))) {
				errorSet.put("selectClient", "Client is missing");
			}
			if(StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("selectClient")) && StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("selectContact"))) {
				errorSet.put("selectContact", "Client contact is missing");
			}
			if(StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("selectClient")) && StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("clientSubmissionForm"))) {
				errorSet.put("clientSubmissionForm", "Client submission from is missing");
			}
			
			if(!dataMigrationDTOJsonCopy.getString("requirementSubmissionStatus").equals("null")) {
				REQUIREMENT_SUBMISSION_STATUS status = null;
				try {
				status =	REQUIREMENT_SUBMISSION_STATUS.getNameByValue(dataMigrationDTOJsonCopy.getString("requirementSubmissionStatus").toLowerCase());
				if(status != null)
				dataMigrationDTOJsonCopy.put("requirementSubmissionStatus", status.toString());
				} catch (Exception e) {
					errorSet.put("requirementSubmissionStatus", "Invalid Status");
				}
			}else {
				dataMigrationDTOJsonCopy.put("requirementSubmissionStatus", REQUIREMENT_SUBMISSION_STATUS.OPEN);
			}
			
			
			if(!dataMigrationDTOJsonCopy.getString("projectClientID").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("projectClientID"))) {
				Projects projects = projectsService.getProjectByOrgLocationsIdAndProjectName(orgLocation.getId(), dataMigrationDTOJsonCopy.getString("projectClientID").trim());
				if(projects != null) {
					dataMigrationDTOJsonCopy.put("projectClientID", projects.getId().toString());
				}else{
					errorSet.put("projectClientID", dataMigrationDTOJsonCopy.getString("projectClientID")+" is invalid Project");
				}
			}
			
			
		/*if (!dataMigrationDTOJsonCopy.getString("docFile").equals("null")
				&& StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("docFile"))) {
			File source = new File(dataMigrationDTOJsonCopy.getString("docFile"));
			if (source.exists()) {
				String fileName = source.getName().replace("&", "").replace("%", "").replace("?", "");
				String extenstion = null;
				String[] extensionArr = { "doc", "pdf", "txt", "docx", "jpeg", "jpg", "gif",
						"png" };
				try {
					extenstion = fileName.substring(fileName.lastIndexOf("."));
					if (!ArrayUtils.contains(extensionArr, extenstion.toLowerCase())) {
						errorSet.put("docFile", "Invalid file format, should accept only .doc,pdf,txt,docx,jpeg,jpg,gif,png");
					}
					else {
						fileName = fileName.substring(0, fileName.lastIndexOf("."));
						long time = new Date().getTime();
						String url = staticPath + "/" + orgLocation.getOrg().getId() + "/"
								+ orgLocation.getId() + "/" + "requirementDocuments" + "/"
								+ fileName + time + extenstion;
						File dest = new File(url);
						if (!dest.exists())
							dest.mkdirs();
						try {
							Files.copy(source.toPath(), dest.toPath());
						} catch (IOException e) {
							errorSet.put("docFile", "Error occured while importing attachemt");
						}
					}
				} catch (Exception e) {
					errorSet.put("docFile", "Invalid file path, should provide along with extension (doc,pdf,txt,docx,jpeg,jpg,gif,png)");
				}
			}
			else if (!source.exists()) {
				errorSet.put("docFile", "Invalid file path for attachment");
			}
		}*/
	}
			public void dataValidationForTimesheets(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation, String timeFrequency) throws Exception
			{
				logger.debug("In dataValidationForTimesheets method...");
				Projects project = null;
					try {
						if (!dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("undefined")) {
							project = projectsService.getProjectByOrgLocationsIdAndProjectName(orgLocation.getId(),dataMigrationDTOJsonCopy.get("project").toString());
							if(project != null) {
								dataMigrationDTOJsonCopy.put((String) dataMigrationDTOJsonCopy.get("project").toString(), project.getProjectName());
							}else {
								errorSet.put("project", dataMigrationDTOJsonCopy.get("project").toString()+"  not a vaid Project");
							}
						}else {
							errorSet.put("project"," project field is missing");
						}
						
						if (!dataMigrationDTOJsonCopy.get("employeeEmail").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("employeeEmail").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("employeeEmail").toString().equalsIgnoreCase("undefined")) {
							 boolean emailVaild = isValidEmailAddress(dataMigrationDTOJsonCopy.get("employeeEmail").toString());
							if(emailVaild == true) {		 
								dataMigrationDTOJsonCopy.put("employeeEmail",dataMigrationDTOJsonCopy.get("employeeEmail"));
							}else {
								errorSet.put("employeeEmail", dataMigrationDTOJsonCopy.get("employeeEmail").toString()+"  invalid date");
							}
						}
						
						if(timeFrequency.equalsIgnoreCase("MonthlyWeekWise")) {
							validateTimesheetMonthWeeklyFrequency(dataMigrationDTOJsonCopy,errorSet);
						}
						if(timeFrequency.equalsIgnoreCase("Weekly")) {
							validateTimesheetWeeklyFrequency(dataMigrationDTOJsonCopy,errorSet);
						}
						Pattern patternWeekTotalCheck = Pattern.compile("^\\d{0,3}(\\.\\d{1,2})?$");
						if (!dataMigrationDTOJsonCopy.get("billableHours").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("billableHours").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("billableHours").toString().equalsIgnoreCase("undefined")) {
							Matcher matcherWeek = patternWeekTotalCheck.matcher(dataMigrationDTOJsonCopy.get("billableHours").toString());
							if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("billableHours").toString()) < 600L)) {
								dataMigrationDTOJsonCopy.put("billableHours",dataMigrationDTOJsonCopy.get("billableHours").toString());
							}else {
								errorSet.put("billableHours", "billableHours hours cannot be more then 600Hrs");
							}
						}
						
						
						if (!dataMigrationDTOJsonCopy.get("nonBillableHours").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("nonBillableHours").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("nonBillableHours").toString().equalsIgnoreCase("undefined")) {
							Matcher matcherWeek = patternWeekTotalCheck.matcher(dataMigrationDTOJsonCopy.get("nonBillableHours").toString());
							if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("nonBillableHours").toString()) < 600L)) {
								dataMigrationDTOJsonCopy.put("nonBillableHours",dataMigrationDTOJsonCopy.get("nonBillableHours").toString());
							}else {
								errorSet.put("nonBillableHours", "NonbillableHours hours cannot be more then 600Hrs");
							}
						}
						
						
						if (!dataMigrationDTOJsonCopy.get("totalHours").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("totalHours").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("totalHours").toString().equalsIgnoreCase("undefined")) {
							Matcher matcherWeek = patternWeekTotalCheck.matcher(dataMigrationDTOJsonCopy.get("totalHours").toString());
							if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("totalHours").toString()) < 600L)) {
								dataMigrationDTOJsonCopy.put("totalHours",dataMigrationDTOJsonCopy.get("totalHours").toString());
							}else {
								errorSet.put("totalHours", "totalHours hours cannot be more then 600Hrs");
							}
						}
						if (!dataMigrationDTOJsonCopy.get("weekComments").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("weekComments").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("weekComments").toString().equalsIgnoreCase("undefined")) {
								dataMigrationDTOJsonCopy.put("weekComments",dataMigrationDTOJsonCopy.get("weekComments").toString());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(errorSet.isEmpty()) {
						
						ImportTimesheetDTO importTimeSheet =  new ImportTimesheetDTO();
						ObjectMapper mapper = new ObjectMapper();
						mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
						mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						importTimeSheet = mapper.readValue(dataMigrationDTOJsonCopy.toString(),ImportTimesheetDTO.class);
						if(project != null && (project.getInvoiceFrequency().equalsIgnoreCase("Weekly") || project.getInvoiceFrequency().equalsIgnoreCase("Monthly"))){
						ProjectTasks projectTask = projectTasksService.getProjectTasksByProjectIdAndProjectIdAndTaskName(project.getId(),"System Task Billable");
						 if(projectTask != null) {
							 User employeeUser =  userService.findByEmail(importTimeSheet.getEmployeeEmail());
							if(employeeUser != null) {
							 ProjectMembers projectMember = projectMembersService.getProjectMemberByEmpIdAndProjectId(employeeUser.getId(), project.getId());
							 if(projectMember != null && projectMember.getProjectMemberCandidteId().getEmail().equalsIgnoreCase(importTimeSheet.getEmployeeEmail())) {
								 HashMap<String,Date> memberFrequency = projectMembersHistoryService.getAssignDateandEndDateByProjectMemberId(projectMember.getId());
								 Date assignDate = memberFrequency.get("STARTDATE");
								 Date terminatedDate = memberFrequency.get("ENDDATE");
								 if(importTimeSheet.getMonthYear() != null) {
									 String[] year = null;
									if(importTimeSheet.getMonthYear()!= null && StringUtils.isNotBlank(importTimeSheet.getMonthYear())) { 
										 year = importTimeSheet.getMonthYear().split("-");
									}
								 
									if(year[1] != null) {
										 String month = getMothNumberFromMonthName(importTimeSheet.getMonthYear());
										 if(month != null && StringUtils.isNotBlank(month) && !month.equalsIgnoreCase("0")) {
											 Map<String,Date> dateList =  CalendarUtil.getMonthStartDateAndEndDateFromGivenMonthStringForTsReports(month,year[1]);
											 Date monthStartDate = dateList.get("STARTDATE");
											 Date monthEndDate = dateList.get("ENDDATE");
											 timeSheetImportMonthSaving(importTimeSheet,assignDate,terminatedDate,year,orgLocation,project,projectMember,projectTask,errorSet,dataMigrationDTOJsonCopy,user,monthStartDate,monthEndDate,dateList,timeFrequency);
										 }
									}else {
										 errorSet.put("actualStartDate","please provide month to add records."); 
									 }
									}else if(importTimeSheet.getActualStartDate() != null && importTimeSheet.getActualEndDate() != null) {
										 Map<String,Date> dateList = new HashMap<>();
										 Date weekStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),"1-1-2018" /*importTimeSheet.getActualStartDate()*/);
										 Date weekEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),"7-1-2018"/*importTimeSheet.getActualEndDate()*/);
										 dateList.put("STARTDATE", weekStartDate);
										 dateList.put("ENDDATE", weekEndDate);
										 timeSheetImportMonthSaving(importTimeSheet,assignDate,terminatedDate,null,orgLocation,project,projectMember,projectTask,errorSet,dataMigrationDTOJsonCopy,user,weekStartDate,weekEndDate,dateList,timeFrequency);
										
									}else {
										errorSet.put("actualStartDate","please provide proper month and Year to add records."); 
									}
							 }else {
								 errorSet.put(dataMigrationDTOJsonCopy.getString("employeeEmail"),"not a valid peoject member.");
							 }
						  }else {
							  errorSet.put("employeeEmail","not record exists with "+dataMigrationDTOJsonCopy.getString("employeeEmail"));
						  }
						}
					}else {
						
					}
				}else {
						
				}
			}
			
			
			

private void validateTimesheetWeeklyFrequency(JSONObject dataMigrationDTOJsonCopy,HashMap<String, String> errorSet) throws JSONException {
		Pattern patternWeekCheck = Pattern.compile("^\\d{0,2}(\\.\\d{1,2})?$");
		if (!dataMigrationDTOJsonCopy.get("actualStartDate").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("actualStartDate").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("actualStartDate").toString().equalsIgnoreCase("undefined")) {
	        	 String monthYear = getMothNumberFromMonthName(dataMigrationDTOJsonCopy.get("actualStartDate").toString());
		         if(monthYear != null && StringUtils.isNotBlank(monthYear) &&  !monthYear.equalsIgnoreCase("0")) {
		        	 
		         } 
	         }
	         else {
	        	 errorSet.put("actualStartDate", "please provide proper month year format"); 
	         }
		
		if (!dataMigrationDTOJsonCopy.get("actualEndDate").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("actualEndDate").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("actualEndDate").toString().equalsIgnoreCase("undefined")) {
        	 String monthYear = getMothNumberFromMonthName(dataMigrationDTOJsonCopy.get("actualEndDate").toString());
	         if(monthYear != null && StringUtils.isNotBlank(monthYear) &&  !monthYear.equalsIgnoreCase("0")) {
	        	 
	         } 
         }
         else {
        	 errorSet.put("actualEndDate", "please provide proper month year format"); 
         }
		if (!dataMigrationDTOJsonCopy.get("mon").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("mon").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("mon").toString().equalsIgnoreCase("undefined")) {
			Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("mon").toString());
			if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("mon").toString()) < 24L)) {
				dataMigrationDTOJsonCopy.put("mon",dataMigrationDTOJsonCopy.get("mon").toString());
			}else {
				errorSet.put("mon", "Mon hours cannot be more then 24Hrs");
			}
		}
		if (!dataMigrationDTOJsonCopy.get("tue").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("tue").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("tue").toString().equalsIgnoreCase("undefined")) {
			Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("tue").toString());
			if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("tue").toString()) < 24L)) {
				dataMigrationDTOJsonCopy.put("tue",dataMigrationDTOJsonCopy.get("tue").toString());
			}else {
				errorSet.put("tue", "tue hours cannot be more then 24Hrs");
			}
		}
		if (!dataMigrationDTOJsonCopy.get("wed").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("wed").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("wed").toString().equalsIgnoreCase("undefined")) {
			Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("wed").toString());
			if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("wed").toString()) < 24L)) {
				dataMigrationDTOJsonCopy.put("wed",dataMigrationDTOJsonCopy.get("wed").toString());
			}else {
				errorSet.put("wed", "Wed hours cannot be more then 24Hrs");
			}
		}
		if (!dataMigrationDTOJsonCopy.get("thu").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("thu").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("thu").toString().equalsIgnoreCase("undefined")) {
			Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("thu").toString());
			if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("thu").toString()) < 24L)) {
				dataMigrationDTOJsonCopy.put("thu",dataMigrationDTOJsonCopy.get("thu").toString());
			}else {
				errorSet.put("thu", "Thu hours cannot be more then 24Hrs");
			}
		}
		if (!dataMigrationDTOJsonCopy.get("fri").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("fri").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("fri").toString().equalsIgnoreCase("undefined")) {
			Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("fri").toString());
			if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("fri").toString()) < 24L)) {
				dataMigrationDTOJsonCopy.put("fri",dataMigrationDTOJsonCopy.get("fri").toString());
			}else {
				errorSet.put("fri", "Fri hours cannot be more then 24Hrs");
			}
		}
		if (!dataMigrationDTOJsonCopy.get("sat").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("sat").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("sat").toString().equalsIgnoreCase("undefined")) {
			Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("sat").toString());
			if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("sat").toString()) < 24L)) {
				dataMigrationDTOJsonCopy.put("sat",dataMigrationDTOJsonCopy.get("sat").toString());
			}else {
				errorSet.put("sat", "Sat hours cannot be more then 24Hrs");
			}
		}
		if (!dataMigrationDTOJsonCopy.get("sun").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("sun").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("sun").toString().equalsIgnoreCase("undefined")) {
			Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("sun").toString());
			if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("sun").toString()) < 24L)) {
				dataMigrationDTOJsonCopy.put("sun",dataMigrationDTOJsonCopy.get("sun").toString());
			}else {
				errorSet.put("sun", "Sun hours cannot be more then 24Hrs");
			}
		}
		
}


private void validateTimesheetMonthWeeklyFrequency(JSONObject dataMigrationDTOJsonCopy, HashMap<String, String> errorSet) throws JSONException {
	if (dataMigrationDTOJsonCopy.get("monthYear") != null && !dataMigrationDTOJsonCopy.get("monthYear").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("monthYear").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("monthYear").toString().equalsIgnoreCase("undefined")) {
    	 String monthYear = getMothNumberFromMonthName(dataMigrationDTOJsonCopy.get("monthYear").toString());
         if(monthYear != null && StringUtils.isNotBlank(monthYear) &&  !monthYear.equalsIgnoreCase("0")) {
        	 
         } 
     }
     else {
    	 errorSet.put("monthYear", "please provide proper month year format"); 
     }
	Pattern patternWeekCheck = Pattern.compile("^\\d{0,2}(\\.\\d{1,2})?$");
	
	if (!dataMigrationDTOJsonCopy.get("week1").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week1").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week1").toString().equalsIgnoreCase("undefined")) {
		Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week1").toString());
		if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("week1").toString()) < 120L)) {
			dataMigrationDTOJsonCopy.put("week1",dataMigrationDTOJsonCopy.get("week1").toString());
		}else {
			errorSet.put("week1", "week1 hours cannot be more then 120Hrs");
		}
	}
	if (!dataMigrationDTOJsonCopy.get("week2").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week2").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week2").toString().equalsIgnoreCase("undefined")) {
		Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week2").toString());
		if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("week2").toString()) < 120L)) {
			dataMigrationDTOJsonCopy.put("week2",dataMigrationDTOJsonCopy.get("week2").toString());
		}else {
			errorSet.put("week2", "week2 hours cannot be more then 120Hrs");
		}
	}
	if (!dataMigrationDTOJsonCopy.get("week3").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week3").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week3").toString().equalsIgnoreCase("undefined")) {
		Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week3").toString());
		if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("week3").toString()) < 120L)) {
			dataMigrationDTOJsonCopy.put("week3",dataMigrationDTOJsonCopy.get("week3").toString());
		}else {
			errorSet.put("week3", "week3 hours cannot be more then 120Hrs");
		}
	}
	if (!dataMigrationDTOJsonCopy.get("week4").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week4").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week4").toString().equalsIgnoreCase("undefined")) {
		Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week4").toString());
		if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("week4").toString()) < 120L)) {
			dataMigrationDTOJsonCopy.put("week4",dataMigrationDTOJsonCopy.get("week4").toString());
		}else {
			errorSet.put("week4", "week4 hours cannot be more then 120Hrs");
		}
	}
	if (!dataMigrationDTOJsonCopy.get("week5").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week5").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week5").toString().equalsIgnoreCase("undefined")) {
		Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week5").toString());
		if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("week5").toString()) < 120L)) {
			dataMigrationDTOJsonCopy.put("week5",dataMigrationDTOJsonCopy.get("week5").toString());
		}else {
			errorSet.put("week5","week5 hours cannot be more then 120Hrs");
		}
	}
	
}

private void timeSheetImportMonthSaving(ImportTimesheetDTO importTimeSheet, Date assignDate, Date terminatedDate, String[] year, OrgLocations orgLocation, Projects project, ProjectMembers projectMember, ProjectTasks projectTask, HashMap<String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, User user, Date startDate, Date endaDate, Map<String, Date> dateList, String timeFrequency) throws Exception {
		 Date monthStartDate = dateList.get("STARTDATE");
		 Date monthEndDate = dateList.get("ENDDATE");
		 Boolean vaidateProjectMemberStartDate = null;
		 Boolean vaidateProjectMemberEndDate = null;
		 if(terminatedDate != null) {
			  vaidateProjectMemberStartDate = CalendarUtil.isDateBetweenTwoDates(assignDate,terminatedDate, dateList.get("STARTDATE"));
		 }else {
			 vaidateProjectMemberStartDate =  (dateList.get("STARTDATE").after(assignDate) || dateList.get("STARTDATE").equals(assignDate));
		 }
		 if(terminatedDate != null) {
			 vaidateProjectMemberEndDate = CalendarUtil.isDateBetweenTwoDates(assignDate,terminatedDate, dateList.get("ENDDATE"));
		 }else {
			 vaidateProjectMemberEndDate =  (dateList.get("ENDDATE").after(assignDate) || dateList.get("STARTDATE").equals(assignDate));
		 }
		 
			 
		 if(vaidateProjectMemberStartDate == true && vaidateProjectMemberEndDate == true) {
			 Map<String,String> frequencyDropDown =  timesheetApprovalFrequencyGeneratorUtil.getTabFrequencyListFromProjectInvoiceFrequency(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),dateList.get("STARTDATE")), commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),dateList.get("ENDDATE")),orgLocation.getDateFormat());
			 SimpleDateFormat format = new SimpleDateFormat(orgLocation.getDateFormat());
			 int count = 1;
			 if(timeFrequency.equalsIgnoreCase("MonthlyWeekWise")) {
				 if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
						for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
							 Date  key = format.parse(entry.getKey());
							 Date  value = format.parse(entry.getValue());
				    		 int noOfBillableHours =  CalendarUtil.getNumberOfBillableDates(key,value); 
				    		  //avgHours
				    		 Date frequencyStartDate = monthStartDate;
							 Date frequencyEndDate = monthEndDate;
				    		 float averageHours = getAverageHours(count,importTimeSheet,noOfBillableHours,timeFrequency);
				    		 saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,key,value,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
				    		 System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
				    		 count++;
						}
					}
			 }else if(timeFrequency.equalsIgnoreCase("Weekly")) {
				/* int weekCount = 1;
				 List<Date> weekdateList = CalendarUtil.getListOfDaysBetweenTwoDates(startDate,endaDate);
				 for(Date weekList:weekdateList) {*/
					  /* Date frequencyStartDate =startDate;
					   Date frequencyEndDate = endaDate;
		    		   float averageHours = getAverageHours(weekCount,importTimeSheet,0,timeFrequency);*/
		    		   saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,startDate,endaDate,0f,user,orgLocation,startDate,endaDate,importTimeSheet,timeFrequency);
		    		  /* System.out.println(projectMember.getProjectMemberCandidteId().getFullName());*/
		    		   /*weekCount++;
				 }*/
			 }
		 }else {
			 errorSet.put(dataMigrationDTOJsonCopy.getString("Month-Year"),"not in assgined project member Date"); 
		 }
	}

		

private void saveImportedTimeSheetRecords(Projects project,String projectTaskUUID,ProjectMembers projectMember, Date actualStartDate, Date actualEndDate,float averageHours, User loginUser, OrgLocations orgLocation, Date frequencyStartDate, Date frequencyEndDate, ImportTimesheetDTO importTimeSheet, String timeFrequency) throws Exception {
	 TimesheetSubmissions timesheetSubmissions  = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(actualStartDate,actualEndDate,projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
 	if(timesheetSubmissions == null){
 		timesheetSubmissions =  new TimesheetSubmissions();
	    	timesheetSubmissions.setStartDate(actualStartDate);
	    	timesheetSubmissions.setEndDate(actualEndDate);
	    	timesheetSubmissions.setProjectMembers(projectMember);
	    	timesheetSubmissions.setProjects(projectMember.getProjects());
	    	timesheetSubmissions.setMasterCandidateId(projectMember.getMasterCandidateId());
	    	timesheetSubmissions.setStatus(Constants.RECORD_ACTIVE+"");
	    	timesheetSubmissions.setCreatedDate(CommonUtil.currentDate());
	    	timesheetSubmissions.setCreatedBy(loginUser.getUserCode());
	    	timesheetSubmissions.setWeekcomments("");
 	}
 	timesheetSubmissions.setUpdatedDate(CommonUtil.currentDate());
 	timesheetSubmissions.setUpdatedBy(loginUser.getUserCode());
 	ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
 	List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMember.getMasterCandidateId().getId(), projectMember.getProjects().getId());
 	HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
     if(hierarchyList != null && !hierarchyList.isEmpty()){
				for(ProjectApproverHierarchy hierarchy : hierarchyList){
					SubmissionApproverHierarchy saHierarchy = null;
					if(timesheetSubmissions != null && timesheetSubmissions.getId() != null) {
						saHierarchy = submissionApproverHierarchyService.getSubmissionApproverHierarchyForApprover(timesheetSubmissions.getId(), hierarchy.getApproverUserId().getId());
					}
				    if(saHierarchy == null){
				    	saHierarchy = new SubmissionApproverHierarchy();
						saHierarchy.setHeirarchyId(hierarchy);
						saHierarchy.setSubmissionId(timesheetSubmissions);
						if(frequencyStartDate != null)
							saHierarchy.setFrequencyStartDate(frequencyStartDate);
						if(frequencyEndDate != null)
					    saHierarchy.setFrequencyEndDate(frequencyEndDate);
						saHierarchy.setSubmissionStatus(Submission_Status.DRAFT);
						saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.NEW);
						saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
						saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
						saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
						saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
						saHierarchy.setStatus(Constants.RECORD_ACTIVE+"");
				    }
					saHierarchyList.add(saHierarchy);
				}
				timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
		}
		timesheetSubmissionsList.add(timesheetSubmissions);
		timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		
		 Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		 cal.setLenient(false);
		 cal.setTime(actualStartDate);
		 Calendar startMonday = null;
		 Calendar endSunday = null;
		 Date weekStartDate = null;
		 Date weekEndDate = null;
		 
		 
		SimpleDateFormat sf = new SimpleDateFormat("E"); 
		if(sf.format(actualStartDate).equalsIgnoreCase("SUN")) {
			startMonday = CalendarUtil.getPreviousWeekDayByCalendar(cal, "MON",orgLocation.getDateFormat());
			endSunday= CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "SUN",orgLocation.getDateFormat());
		}else {
			startMonday = CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "MON",orgLocation.getDateFormat());
			endSunday= CalendarUtil.getCurrentWeekDayByCalendarForTimesheets(cal, "SUN",orgLocation.getDateFormat());
		}
		weekStartDate = startMonday.getTime();
		weekEndDate = endSunday.getTime();
			
			
		Long total = 0l;
		Long totalBillableHours = 0l;
		Long totalNonBillableHours = 0l;
		int weekCount = 1;
		while(weekStartDate.before(weekEndDate) || weekStartDate.equals(weekEndDate)){
				
				  if(timeFrequency.equalsIgnoreCase("Weekly")) {
					averageHours = getAverageHours(weekCount,importTimeSheet,0,timeFrequency);
				  }
				  ProjectTasks projectTasks = projectTasksService.getProjectTasksByUUID(projectTaskUUID);
				  TimeSheetDayEntries  timesheetDayEntries = timesheetDayEntriesDao.getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(timesheetSubmissions.getId(), projectTasks.getId(), weekStartDate);
				  if(timesheetDayEntries == null){
					 timesheetDayEntries = new TimeSheetDayEntries();
					 timesheetDayEntries.setBillableHours("00:00");
					 timesheetDayEntries.setNonBillableHours("00:00");
					 timesheetDayEntries.setTotalHours("00:00");
					 timesheetDayEntries.setIsManuallyAdded(Constants.NO);
				    }
				  	
					String taskType =projectTasks.getBillableType();
					timesheetDayEntries.setProjectTask(projectTasks);
					String hours =CalendarUtil.getTimesheetHoursDisplayFormat(averageHours).replace(".", ":");
					SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
			    	 String day_Of_Date = simpleDateformat.format(weekStartDate);
					if(CalendarUtil.isDateBetweenTwoDates(actualStartDate,actualEndDate, weekStartDate) && (!day_Of_Date.equals("Sat") && !day_Of_Date.equals("Sun"))){
         			timesheetDayEntries.setIsManuallyAdded(Constants.YES);
         			total = getTotalHoursFormDayHours(hours, total);
         			   if(taskType.equalsIgnoreCase("Billable")) {
		         				//timesheetDayEntries.setComments(comments);
		             			timesheetDayEntries.setBillableHours(hours);
		     					timesheetDayEntries.setNonBillableHours("00:00");
		     					timesheetDayEntries.setTotalHours(hours);
			        				totalBillableHours = getTotalHoursFormDayHours(hours,totalBillableHours);
             			}else{
		             			//timesheetDayEntries.setComments(comments);
		             			timesheetDayEntries.setBillableHours("00:00");
		     					timesheetDayEntries.setNonBillableHours(hours);
		     					timesheetDayEntries.setTotalHours(hours);
		     					totalNonBillableHours = getTotalHoursFormDayHours(hours,totalNonBillableHours);
             			}
					}else{
	         			total = getTotalHoursFormDayHours(timesheetDayEntries.getTotalHours(), total);
	         			totalBillableHours = getTotalHoursFormDayHours(timesheetDayEntries.getBillableHours(),totalBillableHours);
	         			totalNonBillableHours = getTotalHoursFormDayHours(timesheetDayEntries.getNonBillableHours(),totalNonBillableHours);
					}
					
					if(StringUtils.isEmpty(timesheetDayEntries.getBillableHours())) {
						timesheetDayEntries.setBillableHours("00:00");
					}
					
					if(StringUtils.isEmpty(timesheetDayEntries.getNonBillableHours())) {
						timesheetDayEntries.setNonBillableHours("00:00");
					}
					
					if(StringUtils.isEmpty(timesheetDayEntries.getTotalHours())) {
						timesheetDayEntries.setTotalHours("00:00");
					}
					
					
					timesheetDayEntries.setTaskType(taskType);
					timesheetDayEntries.setTimesheetSubmissions(timesheetSubmissions);
					timesheetDayEntries.setTimesheetDate(weekStartDate);
					timesheetDayEntries.setCreatedDate(CommonUtil.currentDate());
					timesheetDayEntries.setCreatedBy(loginUser.getCreatedBy());
				
				
				timesheetDayEntries.setUpdatedBy(loginUser.getCreatedBy());
				timesheetDayEntries.setUpdatedDate(CommonUtil.currentDate());
				timesheetDayEntriesDao.saveTimesheetDayEntries(timesheetDayEntries);
				weekStartDate = CalendarUtil.getNextDate(weekStartDate);
				weekCount++;
		}
		
		if(total != null){
			/**
			 * Total Hours Calculation
			 */
         int actualHours = (int) (total / 60);
         int actualMinutes =   (int)(total % 60);
         String actualMinutesStr = ""+actualMinutes;
         String startWithZero = "";
         if(StringUtils.isNotEmpty(actualMinutesStr) && actualMinutesStr.length()==1)
         	startWithZero ="0";	
         	String actualTimeString =  actualHours+"."+startWithZero+actualMinutes;
			timesheetSubmissions.setTotalHours(actualTimeString);
			
			/**
			 * Billable Hours Calculation
			 */
			int actualBillableHours = (int) (totalBillableHours / 60);
			int actualBillableMinutes =   (int)(totalBillableHours % 60);
         String actualBillableMinutesStr = ""+actualBillableMinutes;
         String billableStartWithZero = "";
         if(StringUtils.isNotEmpty(actualBillableMinutesStr) && actualBillableMinutesStr.length()==1)
         	billableStartWithZero ="0";	
         	String actualBillableTimeString =  actualBillableHours+"."+billableStartWithZero+actualBillableMinutes;
			timesheetSubmissions.setBillableHours(actualBillableTimeString);
			timesheetSubmissions.setWeekcomments(importTimeSheet.getWeekComments());
			/**
			 * Non-Billable Hours Calculation
			 */
			int actualNonBillableHours = (int) (totalNonBillableHours / 60);
         int actualNonBillableMinutes =   (int)(totalNonBillableHours % 60);
         String actualNonBillableMinutesStr = ""+actualNonBillableMinutes;
         String nonBillableStartWithZero = "";
         if(StringUtils.isNotEmpty(actualNonBillableMinutesStr) && actualNonBillableMinutesStr.length()==1)
         	nonBillableStartWithZero ="0";	
         String actualNonBillableTimeString =  actualNonBillableHours+"."+nonBillableStartWithZero+actualNonBillableMinutes;
			timesheetSubmissions.setNonBillableHours(actualNonBillableTimeString);
			timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		}
}


		private Long getTotalHoursFormDayHours(String dayHours,Long total){
			String hours = "00";
			String min = "00";
			if(StringUtils.isNotEmpty(dayHours)){
			    String[] parts = dayHours.split(":");
				hours = parts[0]; 
				min = parts[1]; 
			}
			Long  localHours = Long.parseLong(hours) * 60;
			Long  localHoursMinute = Long.parseLong(min);
			Long  finalmin = localHours + localHoursMinute;
			total = total + finalmin;
			return total;
		}

			private float getAverageHours(int count, ImportTimesheetDTO importTimeSheet, int noOfBillableHours,String type) throws ParseException {
				Double AverageHours = null;
				if(type.equalsIgnoreCase("MonthlyWeekWise")) {
				switch (count) {
				case 1:
					if(importTimeSheet.getWeek1() != null && StringUtils.isNotEmpty(importTimeSheet.getWeek1())) {
						AverageHours =calculateHoursAndMin(Double.parseDouble(importTimeSheet.getWeek1()),noOfBillableHours);
						 break;
					}else {
						AverageHours = 0D;
						break;
					}
					 
				case 2:
					if(importTimeSheet.getWeek2() != null && StringUtils.isNotEmpty(importTimeSheet.getWeek2())) {
					 AverageHours = calculateHoursAndMin(Double.parseDouble(importTimeSheet.getWeek2()),noOfBillableHours);
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 3:
					if(importTimeSheet.getWeek3() != null && StringUtils.isNotEmpty(importTimeSheet.getWeek3())) {
					 AverageHours = calculateHoursAndMin(Double.parseDouble(importTimeSheet.getWeek3()),noOfBillableHours);
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 4:
					if(importTimeSheet.getWeek4() != null && StringUtils.isNotEmpty(importTimeSheet.getWeek4())) { 
					AverageHours = calculateHoursAndMin(Double.parseDouble(importTimeSheet.getWeek4()),noOfBillableHours);
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 5:
					if(importTimeSheet.getWeek5() != null && StringUtils.isNotEmpty(importTimeSheet.getWeek5())) {
					 AverageHours = calculateHoursAndMin(Double.parseDouble(importTimeSheet.getWeek5()),noOfBillableHours);
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				}
			}else if(type.equalsIgnoreCase("Weekly")) {
				switch (count) {
				case 1:
					if(importTimeSheet.getMon() != null && StringUtils.isNotEmpty(importTimeSheet.getMon())) {
						AverageHours = Double.parseDouble(importTimeSheet.getMon());
						 break;
					}else {
						AverageHours = 0D;
						break;
					}
					 
				case 2:
					if(importTimeSheet.getTue() != null && StringUtils.isNotEmpty(importTimeSheet.getTue())) {
						AverageHours = Double.parseDouble(importTimeSheet.getTue());
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 3:
					if(importTimeSheet.getWed() != null && StringUtils.isNotEmpty(importTimeSheet.getWed())) {
						AverageHours = Double.parseDouble(importTimeSheet.getWed());
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 4:
					if(importTimeSheet.getThu() != null && StringUtils.isNotEmpty(importTimeSheet.getThu())) { 
						AverageHours = Double.parseDouble(importTimeSheet.getThu());
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 5:
					if(importTimeSheet.getFri() != null && StringUtils.isNotEmpty(importTimeSheet.getFri())) {
						AverageHours = Double.parseDouble(importTimeSheet.getFri());
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 6:
					if(importTimeSheet.getSat() != null && StringUtils.isNotEmpty(importTimeSheet.getSat())) {
						AverageHours = Double.parseDouble(importTimeSheet.getSat());
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				case 7:
					if(importTimeSheet.getSun() != null && StringUtils.isNotEmpty(importTimeSheet.getSun())) {
						AverageHours = Double.parseDouble(importTimeSheet.getSun());
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				}
			}
				BigDecimal bd = new BigDecimal(AverageHours);
			    bd = bd.setScale(2, RoundingMode.HALF_UP);
				return bd.floatValue();
			}

			private String getMothNumberFromMonthName(String monthName) {
				Pattern pattern = Pattern.compile("^(\\d{4,4})$");
				Matcher matcher = null;
				String[] mothYearStringSplit = monthName.split("-");
				switch (mothYearStringSplit[0]) {
				case "Jan":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "Jannuary";
					}else {
						return "0";
					}
				case "Feb":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "February";
					}else {
						return "0";
					}
				case "Mar":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "March";
					}else {
						return "0";
					}
				case "Apr":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "April";
					}else {
						return "0";
					}
				case "May":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "May";
					}else {
						return "0";
					}
				case "Jun":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "June";
					}else {
						return "0";
					}
				case "Jul":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "July";
					}else {
						return "0";
					}
				case "Aug":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "August";
					}else {
						return "0";
					}
				case "Sep":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "September";
					}else {
						return "0";
					}
				case "Oct":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "October";
					}else {
						return "0";
					}
				case "Nov":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "November";
					}else {
						return "0";
					}
				case "Dec":
					matcher = pattern.matcher(mothYearStringSplit[1]);
					if(matcher.matches() == true) {
						return "December";
					}else {
						return "0";
					}
				default:
					return "0";
				}
			}

			public static boolean isValidEmailAddress(String email) {
				   boolean result = true;
				   try {
				      InternetAddress emailAddr = new InternetAddress(email);
				      emailAddr.validate();
				   } catch (AddressException ex) {
				      result = false;
				   }
				   return result;
				}
			
			
		private Double calculateHoursAndMin(double total,int days) {	
			double res1 = getMinutes(total);
			double res2 = new BigDecimal(res1).divide(new BigDecimal(days),2, RoundingMode.HALF_UP).doubleValue();
			double res3 = new BigDecimal(res2).divide(new BigDecimal(60), 5, RoundingMode.HALF_UP).doubleValue();
			double res4 = getMinutes1(res3);
			return res4;
		}	
			private static double getMinutes(double val) {
			return Math.ceil(((Math.floor(val) * 60) + ((val-Math.floor(val)) * 100)));
		}

			private static double getMinutes1(double val) {
			return Math.floor(val) + (Math.floor(((val-Math.floor(val)) * 60))/100);
			}
			
			
			
		public void dataValidationForAssets(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation) throws Exception
			{
				
				if(dataMigrationDTOJsonCopy.getString("category").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("category"))) {
					errorSet.put("category", "Category is missing");
				}
				if(dataMigrationDTOJsonCopy.getString("workingStatus").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("workingStatus"))) {
					errorSet.put("workingStatus", "Working Status is missing");
				}
				if(dataMigrationDTOJsonCopy.getString("sno").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("sno"))) {
					errorSet.put("sno", "Serial Number is missing");
				}
				
				if(!dataMigrationDTOJsonCopy.getString("category").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("category"))) {
					AssetCategory  category= assetCategoryService.getAssetCategoryListByLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.getString("category").trim());
					if(category == null){
						errorSet.put("category", dataMigrationDTOJsonCopy.getString("category")+" is Not Exist");
					}else{
						dataMigrationDTOJsonCopy.put("category", category.getId().toString());	
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("shiftAEmployee").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("shiftAEmployee"))) {
					User category= userService.findByEmailAndOrgLocationsId(dataMigrationDTOJsonCopy.getString("shiftAEmployee"), orgLocation.getId());
					
						dataMigrationDTOJsonCopy.put("shiftAEmployee", category.getId().toString());	
					
				}
				
				if(!dataMigrationDTOJsonCopy.getString("shiftBEmployee").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("shiftBEmployee"))) {
					User category= userService.findByEmailAndOrgLocationsId(dataMigrationDTOJsonCopy.getString("shiftBEmployee"), orgLocation.getId());
					
						dataMigrationDTOJsonCopy.put("shiftBEmployee", category.getId().toString());	
					
				}
				
				if(!dataMigrationDTOJsonCopy.getString("workingStatus").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("workingStatus"))) {
					String status = dataMigrationDTOJsonCopy.getString("workingStatus").toLowerCase();
					if(status.equalsIgnoreCase("working") || status.equalsIgnoreCase("out of service")){
						status = status.substring(0, 1).toUpperCase()+status.substring(1, status.length());
						dataMigrationDTOJsonCopy.put("workingStatus", status);
					}else{
						errorSet.put("workingStatus", dataMigrationDTOJsonCopy.getString("workingStatus")+" is invalid Working Status");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("sno").equals("sno") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("sno"))) {
					String serialNo = dataMigrationDTOJsonCopy.getString("sno");
						dataMigrationDTOJsonCopy.put("sno", serialNo);
				}
				
			}

	}
