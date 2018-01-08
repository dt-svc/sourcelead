package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.dto.ImportTimesheetDTO;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ApplicantSubmissionDisplayStatus;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SubmissionTemplateService;
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
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	private ProjectTasksDao tasksDao;
	
	@SuppressWarnings("unchecked")
	public void dataValidationForJobs(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation) throws Exception
	{
		logger.debug("In dataValidationForJobs method...");
		    
				Boolean flag;
				
				if(dataMigrationDTOJsonCopy.getString("manager").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("manager"))) {
					errorSet.put("manager", "Manager field is missing");
				}
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
						errorSet.put("manager", dataMigrationDTOJsonCopy.getString("manager")+" is invalid Email");
					}else{
						dataMigrationDTOJsonCopy.put("manager", approverList.getId().toString());
					}
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
	}
	
	
//	@SuppressWarnings("unchecked")
	public void dataValidationForTimesheets(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation) throws Exception
	{
		logger.debug("In dataValidationForTimesheets method...");
		Projects projectName = null;
			try {
				if (!dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("undefined")) {
					projectName = projectsService.getProjectByOrgLocationsIdAndProjectName(orgLocation.getId(),dataMigrationDTOJsonCopy.get("project").toString());
					if(projectName != null) {
						dataMigrationDTOJsonCopy.put((String) dataMigrationDTOJsonCopy.get("project").toString(), projectName.getProjectName());
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
				
				if (!dataMigrationDTOJsonCopy.get("actualStartDate").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("actualStartDate").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("actualStartDate").toString().equalsIgnoreCase("undefined")) {
			         boolean monthYear = validateMonthYear(dataMigrationDTOJsonCopy.get("actualStartDate").toString());
			         if(monthYear == true) {
			        	 
			         }else {
			        	 errorSet.put("actualStartDate", "please provide proper month year format"); 
			         }
				}
				Pattern patternWeekCheck = Pattern.compile("^(\\d{0,3})$");
				
				if (!dataMigrationDTOJsonCopy.get("week1").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week1").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week1").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week1").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("week1").toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put("week1",dataMigrationDTOJsonCopy.get("week1").toString());
					}else {
						errorSet.put("week1", "week1 hours cannot be more then 120Hrs");
					}
				}
				if (!dataMigrationDTOJsonCopy.get("week2").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week2").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week2").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week2").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("week2").toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put("week2",dataMigrationDTOJsonCopy.get("week2").toString());
					}else {
						errorSet.put("week2", "week2 hours cannot be more then 120Hrs");
					}
				}
				if (!dataMigrationDTOJsonCopy.get("week3").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week3").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week3").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week3").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("week3").toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put("week3",dataMigrationDTOJsonCopy.get("week3").toString());
					}else {
						errorSet.put("week3", "week3 hours cannot be more then 120Hrs");
					}
				}
				if (!dataMigrationDTOJsonCopy.get("week4").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week4").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week4").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week4").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("week4").toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put("week4",dataMigrationDTOJsonCopy.get("week4").toString());
					}else {
						errorSet.put("week4", "week4 hours cannot be more then 120Hrs");
					}
				}
				if (!dataMigrationDTOJsonCopy.get("week5").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week5").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week5").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week5").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("week5").toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put("week5",dataMigrationDTOJsonCopy.get("week5").toString());
					}else {
						errorSet.put("week5","week5 hours cannot be more then 120Hrs");
					}
				}
				
				if (!dataMigrationDTOJsonCopy.get("billableHours").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("billableHours").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("billableHours").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("billableHours").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("billableHours").toString()) < 600L)) {
						dataMigrationDTOJsonCopy.put("billableHours",dataMigrationDTOJsonCopy.get("billableHours").toString());
					}else {
						errorSet.put("billableHours", "billableHours hours cannot be more then 600Hrs");
					}
				}
				
				
				if (!dataMigrationDTOJsonCopy.get("nonBillableHours").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("nonBillableHours").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("nonBillableHours").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("nonBillableHours").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("nonBillableHours").toString()) < 600L)) {
						dataMigrationDTOJsonCopy.put("nonBillableHours",dataMigrationDTOJsonCopy.get("nonBillableHours").toString());
					}else {
						errorSet.put("nonBillableHours", "NonbillableHours hours cannot be more then 600Hrs");
					}
				}
				
				
				if (!dataMigrationDTOJsonCopy.get("totalHours").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("totalHours").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("totalHours").toString().equalsIgnoreCase("undefined")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("totalHours").toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get("totalHours").toString()) < 600L)) {
						dataMigrationDTOJsonCopy.put("totalHours",dataMigrationDTOJsonCopy.get("totalHours").toString());
					}else {
						errorSet.put("totalHours", "totalHours hours cannot be more then 600Hrs");
					}
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		//}
			if(errorSet.isEmpty()) {
				ImportTimesheetDTO importTimeSheet =  new ImportTimesheetDTO();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				importTimeSheet = mapper.readValue(dataMigrationDTOJsonCopy.toString(),ImportTimesheetDTO.class);
				if(projectName != null){
					 
					 ProjectMembers projectMember = projectMembersService.getProjectMemberByUserIdAndProjectId(user.getId(), projectName.getId());
					 if(projectMember != null && projectMember.getProjectMemberCandidteId().getEmail().equalsIgnoreCase(importTimeSheet.getEmployeeEmail())) {
						 HashMap<String,Date> memberFrequency = projectMembersHistoryService.getAssignDateandEndDateByProjectMemberId(projectMember.getId());
						 Date assignDate = memberFrequency.get("STARTDATE");
						 Date terminatedDate = memberFrequency.get("ENDDATE");
						 if(importTimeSheet.getTaskName()== null) {
							 
						 }
						 
						 
						 if(importTimeSheet.getActualStartDate() != null) {
							String[] year = importTimeSheet.getActualStartDate().split("-");
							if(year[1] != null) {
								String years = "20"+year[1];
								 YearMonth yearMonth = YearMonth.of(Integer.parseInt(years),1); 
								 LocalDate firstOfMonth = yearMonth.atDay(1);
								 LocalDate last = yearMonth.atEndOfMonth();
								 System.out.println(last);
							}
						 }
						 
						
						 
						 
						 Date assignedStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), "12-1-2017");
						 
						 
						 
					 }else {
						 errorSet.put(dataMigrationDTOJsonCopy.getString("employeeEmail"),"not a valid peoject member.");
					 }
				}
				
				
				System.out.println(importTimeSheet);
			}else {
				
			}
		
	}
	
	
	
	private boolean validateMonthYear(String mothYearString) {
		Boolean flag =  false;
		String[] mothYearStringSplit = mothYearString.split("-");
		try {
		if(mothYearStringSplit[0] != null && StringUtils.isNotEmpty(mothYearStringSplit[0])) {
			Map<Integer,String> mothYear = new HashMap<>();
			mothYear.put(1, "Jannuary,Jan");
			mothYear.put(2, "February,Feb");
			mothYear.put(3, "March,Mar");
			mothYear.put(4, "April,Apr");
			mothYear.put(5, "May,May");
			mothYear.put(6, "June,Jun");
			mothYear.put(7, "July,Jul");
			mothYear.put(8, "August,Aug");
			mothYear.put(8, "September,Sep");
			mothYear.put(10, "October,Oct");
			mothYear.put(11, "November,Nov");
			mothYear.put(12, "December,Dec");
			for( Entry<Integer, String> my:mothYear.entrySet()) {
				String monthName [] =  my.getValue().split(",");
				try {
					 int value = Integer.parseInt(mothYearStringSplit[0]);
					 if(value == my.getKey()) {
						 flag = true;
					 }
				}catch (Exception e) {
					if(monthName[0].equalsIgnoreCase(mothYearStringSplit[0]) || monthName[1].equalsIgnoreCase(mothYearStringSplit[0])) {
						flag = true;
					}
				}
			}
		}if(mothYearStringSplit[1] != null && StringUtils.isNotEmpty(mothYearStringSplit[1])) {
			try {
				Long.parseLong(mothYearStringSplit[1]);
				flag = true;
			}catch(NumberFormatException|NullPointerException excep) {
				flag = false;
			}
		}
		}catch(Exception e) {
			flag = false;
		}
		
		return flag;	
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
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
