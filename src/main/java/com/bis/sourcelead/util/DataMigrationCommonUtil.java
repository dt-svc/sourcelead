package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.joda.time.Days;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ImportTimesheetDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
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
	
	
	@SuppressWarnings("unchecked")
	public void dataValidationForTimesheets(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, FormulaEvaluator objFormulaEvaluator, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation) throws Exception
	{
		logger.debug("In dataValidationForTimesheets method...");
		//dataMigrationDTOJsonCopy.keys().forEachRemaining(k -> {
		Iterator<?> keys = dataMigrationDTOJsonCopy.keys();
		Projects projectName = null;
		while( keys.hasNext() ) {
			String k = (String)keys.next();
			try {
				System.out.println(k.toString()+" "+dataMigrationDTOJsonCopy.get(k.toString()).toString());
				if((k.toString().equalsIgnoreCase("employeeName") || k.toString().equalsIgnoreCase("employeeEmail") || k.toString().equalsIgnoreCase("project") || k.toString().equalsIgnoreCase("actualStartDate") || k.toString().equalsIgnoreCase("actualEndDate")) && (dataMigrationDTOJsonCopy.get(k.toString()) == null || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") || dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null"))){
					errorSet.put(k.toString(), k.toString()+"  field is missing");
				}
				if (k.toString().equalsIgnoreCase("project") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					projectName = projectsService.getProjectByOrgLocationsIdAndProjectName(orgLocation.getId(),dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(projectName != null) {
						dataMigrationDTOJsonCopy.put((String) k, projectName.getProjectName());
					}else {
						errorSet.put(k.toString(), k.toString()+"  not a vaid Project");
					}
				}
					
				if (k.toString().equalsIgnoreCase("employeeEmail") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					 List<ProjectMembers> projectMember = projectMembersService.getAllProjectsByOrgLocationId(orgLocation.getId());
					 for(ProjectMembers proMember:projectMember) {
						 if(proMember.getProjectMemberCandidteId().getEmail().equalsIgnoreCase(k.toString())) {
							 dataMigrationDTOJsonCopy.put((String) k,proMember.getProjectMemberCandidteId().getEmail());
							 break;
						 }
					 }
				}
				DateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
				Pattern pattern = Pattern.compile("^(\\d{0,4}(\\-|\\/{1})\\d{0,4}(\\-|\\/{1})\\d{0,4})$");
				if (k.toString().equalsIgnoreCase("actualStartDate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
			        Matcher matcher = pattern.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
			        if(matcher.matches() == true) {
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.get((String)k).toString());
						String startDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put((String) k, startDate);
			        	/*dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());*/
			        }else {
			        	errorSet.put(k.toString(), k.toString()+"  invalid date");
			        }
				}
				if (k.toString().equalsIgnoreCase("actualEndDate") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
			        Matcher matcher = pattern.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
			        if(matcher.matches() == true) {
			        	Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.get((String)k).toString());
						String endDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put((String) k, endDate);
			        }else {
			        	errorSet.put(k.toString(), k.toString()+"  invalid date");
			        }
				}
				Pattern patternWeekCheck = Pattern.compile("^(\\d{0,3})$");
				if (k.toString().equalsIgnoreCase("week1") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 120Hrs");
					}
				}
				if (k.toString().equalsIgnoreCase("week2") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 120Hrs");
					}
				}
				if (k.toString().equalsIgnoreCase("week3") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 120Hrs");
					}
				}
				if (k.toString().equalsIgnoreCase("week4") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 120Hrs");
					}
				}
				if (k.toString().equalsIgnoreCase("week5") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 120L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 120Hrs");
					}
				}
				if (k.toString().equalsIgnoreCase("billableHours") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 600L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 600Hrs");
					}
				}
				if (k.toString().equalsIgnoreCase("nonBillableHours") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 600L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 600Hrs");
					}
				}
				if (k.toString().equalsIgnoreCase("totalHours") && dataMigrationDTOJsonCopy.get(k.toString()) != null && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get(k.toString()).toString().equalsIgnoreCase("null")) {
					Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get(k.toString()).toString());
					if(matcherWeek.matches() == true && (Long.parseLong(dataMigrationDTOJsonCopy.get(k.toString()).toString()) < 600L)) {
						dataMigrationDTOJsonCopy.put((String) k,dataMigrationDTOJsonCopy.get(k.toString()).toString());
					}else {
						errorSet.put(k.toString(), k.toString()+"  hours cannot be more then 600Hrs");
					}
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
						 Date assignedStartDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), importTimeSheet.getActualStartDate());
						 Date assignedEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), importTimeSheet.getActualEndDate());
						 if(assignedStartDate.after(assignDate) && assignedStartDate.before(terminatedDate)) {
							 if(terminatedDate == null) {
								 terminatedDate = assignedEndDate;
							 }else if(assignedEndDate.after(assignDate) && assignedEndDate.before(terminatedDate)) {
								 
							 }
						 }
					 }else {
						 errorSet.put(dataMigrationDTOJsonCopy.getString("employeeEmail"),"not a valid peoject member.");
					 }
				}
				
				
				System.out.println(importTimeSheet);
			}else {
				
			}
		
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
