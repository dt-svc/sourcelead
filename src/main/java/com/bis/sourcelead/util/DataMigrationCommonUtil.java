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
		dataMigrationDTOJsonCopy.keys().forEachRemaining(k -> {
			try {
				Boolean flag;
				if((k.toString().equalsIgnoreCase("applicationFrom") || k.toString().equalsIgnoreCase("manager") || k.toString().equalsIgnoreCase("priority") || k.toString().equalsIgnoreCase("jobTitle") || k.toString().equalsIgnoreCase("location")) && (dataMigrationDTOJsonCopy.get(k.toString()) == null || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null"))){
					errorSet.put(k.toString(), k.toString()+"  field is missing");
				}
				
				if(k.toString().equalsIgnoreCase("jobId") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					flag = false;
					flag = requirementService.validateJobid(dataMigrationDTOJsonCopy.get((String)k).toString(), orgLocation.getId());
					if(flag){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is Already Existed");
					}
				}
				
				/*if(k.toString().equalsIgnoreCase("applicationFrom") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					SubmissionTemplate submissionTemplate = submissionTemplateService.getSubmissionTemplateByTemplateNameByOrg(orgLocation.getId(), dataMigrationDTOJsonCopy.get((String)k).toString());
					if(null != submissionTemplate){
						dataMigrationDTOJsonCopy.put((String) k, submissionTemplate.getId().toString());
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}*/
				
				if(k.toString().equalsIgnoreCase("visa") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					List<String> visaList = lookUpDataService.getAllVisaByCategory();
					StringBuffer visaSB = new StringBuffer();
					String[] visaListArr = dataMigrationDTOJsonCopy.get((String)k).toString().trim().split(",");
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
						dataMigrationDTOJsonCopy.put((String) k, visaSB.toString());
					}else {
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid immegration type");
					}
				}
				
				if(k.toString().equalsIgnoreCase("travelAllowance") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String percent = dataMigrationDTOJsonCopy.get((String)k).toString();
					int incr = 0;
					for(String percentage :Constants.PERCENTAGES) {
						if(percentage.equalsIgnoreCase(percent)){
							incr++;
							dataMigrationDTOJsonCopy.put((String) k, percent);
						}
					}
					if(incr==0){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Travel Allowance");
					}
				}
				
				
				if(k.toString().equalsIgnoreCase("priority") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String jobPriority = dataMigrationDTOJsonCopy.get((String)k).toString().toLowerCase();
					if(jobPriority.equalsIgnoreCase("medium") || jobPriority.equalsIgnoreCase("low") || jobPriority.equalsIgnoreCase("high") || jobPriority.equalsIgnoreCase("urgent")){
						jobPriority = jobPriority.substring(0, 1).toUpperCase()+jobPriority.substring(1, jobPriority.length());
						dataMigrationDTOJsonCopy.put((String) k, jobPriority);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid");
					}
				}
				
				if(k.toString().equalsIgnoreCase("noticePeriod") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String noticePeriod = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(noticePeriod.equalsIgnoreCase("Immediately Available") || noticePeriod.equalsIgnoreCase("15 Days") || noticePeriod.equalsIgnoreCase("1 Month") || noticePeriod.equalsIgnoreCase("2 Months") || noticePeriod.equalsIgnoreCase("3 Months")){
						dataMigrationDTOJsonCopy.put((String) k, noticePeriod);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Notice Period");
					}
				}
				
				if(k.toString().equalsIgnoreCase("experienceLevel") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String experienceLevel = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(experienceLevel.equalsIgnoreCase("Level-1") || experienceLevel.equalsIgnoreCase("Level-2") || experienceLevel.equalsIgnoreCase("Level-3") || experienceLevel.equalsIgnoreCase("Level-4")){
						dataMigrationDTOJsonCopy.put((String) k, experienceLevel);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Experience Level");
					}
				}
				
				if(k.toString().equalsIgnoreCase("minExp") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String minExp = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(minExp)){
						if(Float.valueOf(minExp) <= 999){
							dataMigrationDTOJsonCopy.put((String) k, minExp);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Min Experience");
					}
				}
				
				if(k.toString().equalsIgnoreCase("maxExp") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String maxExp = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(maxExp)){
						if(Float.valueOf(maxExp) <= 999){
							dataMigrationDTOJsonCopy.put((String) k, maxExp);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid, should between 0 to 999");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Max Experience");
					}
				}
				
				if(k.toString().equalsIgnoreCase("rate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String rate = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(rate)){
						if(Long.valueOf(rate) <= 99999999){
							dataMigrationDTOJsonCopy.put((String) k, rate);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Rate");
					}
				}
				
				if(k.toString().equalsIgnoreCase("clientRate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String clientRate = dataMigrationDTOJsonCopy.get((String)k).toString();
					if(NumberUtils.isNumber(clientRate)){
						if(Long.valueOf(clientRate) <= 99999999){
							dataMigrationDTOJsonCopy.put((String) k, clientRate);
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Rate, should between 1 to 8 digits");
						}
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Rate");
					}
				}
				
				if(k.toString().equalsIgnoreCase("coolingOff") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					String coolingOff = dataMigrationDTOJsonCopy.get((String)k).toString();
					String[] coolingOffSet = {"0 Days","1 Week","2 Weeks","3 Weeks","1 Month","2 Months","3 Months","4 Months","5 Months","6 Months","7 Months","8 Months","9 Months","10 Months","11 Months","1 Year"};
					if(ArrayUtils.contains( coolingOffSet, coolingOff)){
						dataMigrationDTOJsonCopy.put((String) k, coolingOff);
					}else{
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid cooling-off period");
					}
				}
				
				if(k.toString().equalsIgnoreCase("closedDate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					try {
						DateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.get((String)k).toString());
						String closedDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put((String) k, closedDate);
					} catch (Exception e) {
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Close Date");
					}
				}
				
				if(k.toString().equalsIgnoreCase("manager") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					User approverList = userService.getTabAccessOwnersByOrgLocationIdEmailAndTabId(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString(), Long.valueOf(Constants.TAB_REQUIREMENT));
					if(approverList == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Email");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, approverList.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectClient") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(orgRelations == null){
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, orgRelations.getId().toString());
					}
				}
				
				if(k.toString().equalsIgnoreCase("selectContact") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					if(dataMigrationDTOJsonCopy.get("selectClient") != null){
						OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.get("selectClient").toString());
						if(orgRelations != null){
							List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(orgRelations.getId(), user.getId(),orgLocation.getId(),"Client");
							if(orgSpocsList != null){
								for(OrgSpoc orgspoc:orgSpocsList){
									if(dataMigrationDTOJsonCopy.get(k.toString()).toString().toLowerCase().equalsIgnoreCase(orgspoc.getSpocEmail())){
										dataMigrationDTOJsonCopy.put((String) k, orgspoc.getId().toString());
									}else{
										errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
									}
								}
							}else{
								errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
							}
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Client Contact");
						}
						
					}
				}
				
				if(k.toString().equalsIgnoreCase("clientSubmissionForm") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
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
									if(dataMigrationDTOJsonCopy.get(k.toString()).toString().toLowerCase().equalsIgnoreCase(submissiontTemplate.getTemplateName())){
										dataMigrationDTOJsonCopy.put((String) k, submissiontTemplate.getId().toString());
									}else{
										errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
									}
								}
							}else{
								errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
							}
						}else{
							errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString())+" is invalid Submission Form");
						}
					}
				}
				
				if(k.toString().equalsIgnoreCase("assignedTo") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validAssTo = new ArrayList<String>();
					StringBuffer invalidAssMembers = new StringBuffer();
					String[] assTo = dataMigrationDTOJsonCopy.get(k.toString()).toString().split(",");
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
					if(validAssTo == null){
						errorSet.put(k.toString(), "Asigned members are invalid");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, validAssTo);
					}
				}
				
				
				if(k.toString().equalsIgnoreCase("parallelApprover") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")){
					List<User> usersList = userService.getUsersByTeamTypeAndTabId(orgLocation.getId(),"", Long.valueOf(Constants.TAB_REQUIREMENT));
					List<String> validApprovers = new ArrayList<String>();
					StringBuffer invalidApprovers = new StringBuffer();
					String[] apprs = dataMigrationDTOJsonCopy.get(k.toString()).toString().split(",");
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
						errorSet.put(k.toString(), "Approvers are invalid");
					}else{
						dataMigrationDTOJsonCopy.put((String) k, validApprovers);
					}
				}
				
				if (k.toString().equalsIgnoreCase("applicationFrom") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					flag = false;
					List<SubmissionTemplate>   submissionTempletesList = submissionTemplateService.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocation.getId(),"Submission");
					for(SubmissionTemplate submissionTemplete : submissionTempletesList){
						if(submissionTemplete.getTemplateName().toLowerCase().equalsIgnoreCase(k.toString().toLowerCase())){
							flag = true;
							dataMigrationDTOJsonCopy.put((String) k, submissionTemplete.getId().toString());
						}
					}
					if (!flag) {
						errorSet.put(k.toString(), dataMigrationDTOJsonCopy.get(k.toString()) + " is invalid Application Form");
					}
				}
								
				/*String keyStr = (String)k;
				System.out.println(keyStr);
		        Object keyvalue = dataMigrationDTOJsonCopy.get(keyStr);
		        System.out.println(keyvalue);*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
