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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
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
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.ImportTimesheetDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.Shift;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.NOTICE_PERIOD;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.service.AssetCategoryService;
import com.bis.sourcelead.service.AssetService;
import com.bis.sourcelead.service.AssociateGradesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.ShiftService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.workflow.entity.RequirementWorkFlow;
import com.bis.sourcelead.workflow.service.RequirementWorkFlowService;
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
	
	@Autowired
	private AssetService assetService;
	
	/*@Autowired
	private CalendarUtil calendarUtil;*/
	
	@Autowired
	private ProjectTasksService  projectTasksService;
	
	@Autowired
	private UserRoleService  userRoleService;
	
	@Autowired
	private TimesheetApprovalFrequencyGeneratorUtil timesheetApprovalFrequencyGeneratorUtil;
	
	@Autowired
	private TimesheetFrequencyGeneratorUtil timesheetFrequencyGeneratorUtil;
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService; 
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private TimeSheetDayEntriesDao timesheetDayEntriesDao;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired 
	private CountriesService countriesService;
	
	@Autowired
	ImmigrationCommonService immigrationCommonService;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private AssociateGradesService associateGradesService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementWorkFlowService requirementWorkflowService;
	
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
					if(ArrayUtils.contains(Constants.PERCENTAGES, percent.toLowerCase())) {
						dataMigrationDTOJsonCopy.put("travelAllowance", percent);
					}else {
						dataMigrationDTOJsonCopy.put("travelAllowance", "0%");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("priority").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("priority"))) {
					String jobPriority = dataMigrationDTOJsonCopy.getString("priority").toLowerCase();
					if(ArrayUtils.contains(Constants.PRIORITIES, jobPriority)) {
						jobPriority = jobPriority.substring(0, 1).toUpperCase()+jobPriority.substring(1, jobPriority.length());
						dataMigrationDTOJsonCopy.put("priority", jobPriority);
					}else {
						dataMigrationDTOJsonCopy.put("priority", Constants.DEFAULTPRIORITY);
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("noticePeriod").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("noticePeriod"))) {
					String noticePeriod = dataMigrationDTOJsonCopy.getString("noticePeriod");
					if(noticePeriod.equalsIgnoreCase("Immediately Available") || noticePeriod.equalsIgnoreCase("15 Days") || noticePeriod.equalsIgnoreCase("1 Month") || noticePeriod.equalsIgnoreCase("2 Months") || noticePeriod.equalsIgnoreCase("3 Months")){
//						dataMigrationDTOJsonCopy.put("noticePeriod", noticePeriod);
						List<String> noticePeriodList = new ArrayList<>();
						for(String npString : noticePeriod.split(",")) {
							switch (npString.toLowerCase()) {
								case "immediately available":
									noticePeriodList.add(NOTICE_PERIOD.IMMEDIATELY_AVAILABLE.getValue());
									break;
								case "15 days" :
									noticePeriodList.add(NOTICE_PERIOD.DAYS_15.getValue());
									break;
								case "1 month" :
									noticePeriodList.add(NOTICE_PERIOD.MONTH_1.getValue());
									break;
								case "2 months" :
									noticePeriodList.add(NOTICE_PERIOD.MONTHS_2.getValue());
									break;
								case "3 months" :
									noticePeriodList.add(NOTICE_PERIOD.MONTHS_3.getValue());
									break;
							}
						}
						dataMigrationDTOJsonCopy.put("noticePeriod", noticePeriodList);
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
							Countries countries = countriesService.getCountriesByCountryName(orgLocation.getCountry());
							if(null != countries){
								dataMigrationDTOJsonCopy.put("currencyCode", countries.getCurrency());
							}
							dataMigrationDTOJsonCopy.put("rateType", "Monthly");
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
					String coolingOff = dataMigrationDTOJsonCopy.getString("coolingOff").toLowerCase();
					if(ArrayUtils.contains(Constants.COOLINGOFFSET, coolingOff)) {
						String[] coolingOffPart = coolingOff.split(" ");
						coolingOffPart[1] = coolingOffPart[1].substring(0, 1).toUpperCase()+coolingOffPart[1].substring(1, coolingOffPart[1].length());
						dataMigrationDTOJsonCopy.put("coolingOff", coolingOffPart[0]+" "+coolingOffPart[1]);
					}else if(StringUtils.isNotEmpty(orgLocation.getCoolingOffPeriod())){
						dataMigrationDTOJsonCopy.put("coolingOff", orgLocation.getCoolingOffPeriod());
					}else {
						dataMigrationDTOJsonCopy.put("coolingOff", Constants.DEFAULT_COOLINGOFF_PERIOD);
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
						dataMigrationDTOJsonCopy.put("manager", user.getId().toString());
					}else{
						dataMigrationDTOJsonCopy.put("manager", approverList.getId().toString());
					}
				}else if(dataMigrationDTOJsonCopy.getString("manager").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("manager"))) {
					dataMigrationDTOJsonCopy.put("manager", user.getId().toString());
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
				
				if(!dataMigrationDTOJsonCopy.getString("selectClient").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("selectClient"))) {
					OrgRelations orgRelations = orgRelationsService.getClientsByOrgLocationIdAndName(orgLocation.getId(), dataMigrationDTOJsonCopy.getString("selectClient"));
					if(orgRelations == null){
						errorSet.put("selectClient", dataMigrationDTOJsonCopy.getString("selectClient")+" is invalid Client");
					}else{
						dataMigrationDTOJsonCopy.put("selectClient", orgRelations.getId().toString());
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
			/** below lines is commented for to move in next release build.!--//WORKFLOWID added by SRINIVAS SABBU on 24-May-2019.
			if(!dataMigrationDTOJsonCopy.getString("workflow").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("workflow"))) {
				List<RequirementWorkFlow> workflowList = requirementWorkflowService.getRequirementWorkFlowByOrgLocationId(orgLocation);
				boolean found = false;
				for(RequirementWorkFlow workflow: workflowList) {
					if(workflow.getWorkFlowName().equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("workflow").trim())) {
						dataMigrationDTOJsonCopy.put("workflow", workflow.getId().toString());
						found = true;
					}
				}
				if(!found){
					errorSet.put("workflow", dataMigrationDTOJsonCopy.getString("workflow")+" is invalid Workflow");
				}
			}*/
			
			
		if (!dataMigrationDTOJsonCopy.getString("docFile").equals("null")
				&& StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("docFile"))) {
			File source = new File(dataMigrationDTOJsonCopy.getString("docFile"));
			if (source.exists()) {
				String fileName = source.getName().replace("&", "").replace("%", "").replace("?", "");
				String extenstion = null;
				String[] extensionArr = { ".doc", ".pdf", ".txt", ".docx", ".jpeg", ".jpg", ".gif",
						".png" };
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
						String fileUrl = "/slead-static" + "/" + orgLocation.getOrg().getId() + "/"
								+ orgLocation.getId() + "/" + "requirementDocuments" + "/"
								+ fileName + time + extenstion;
						File dest = new File(url);
						/*if (!dest.exists()) {
							dest.mkdirs();
						}*/
							
						try {
							Files.copy(source.toPath(), dest.toPath());
							dataMigrationDTOJsonCopy.put("fileURL", fileUrl);
							dataMigrationDTOJsonCopy.put("fileName", fileName + time + extenstion);
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
		}
	}
	
			@SuppressWarnings("unchecked")
			public void dataValidationForRequirementSubmisssionTemplate(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation,String jsonString) throws Exception
			{
				
				JSONArray jsonarray = new JSONArray(jsonString);
				 for(int i=0; i<jsonarray.length(); i++){
				        JSONObject obj = jsonarray.getJSONObject(i);
				        if(obj.getString("req").equalsIgnoreCase("1")) {
				        	String label = obj.getString("label").replaceAll("\\s+","");
			        		if(!dataMigrationDTOJsonCopy.has(label)) {
				        		errorSet.put(label, obj.getString("label")+" field is missing");
							}
				        }
				 }
				 
				 
				 
			        if(!dataMigrationDTOJsonCopy.get("EmailId").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("EmailId").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("EmailId").toString().equalsIgnoreCase("undefined")) {
			        	Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
								+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			        	Matcher matcher = pattern.matcher(dataMigrationDTOJsonCopy.get("EmailId").toString().trim());
						if(matcher.matches() == false) {
							errorSet.put("EmailId", "Invalid Email");
						}
					}
			        
			        
			    	String mobileRegex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
					if (dataMigrationDTOJsonCopy.has("PhoneNumber") && !dataMigrationDTOJsonCopy.get("PhoneNumber").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("PhoneNumber").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("PhoneNumber").toString().equalsIgnoreCase("undefined")) {
						boolean flag = false;
						String mobNum = dataMigrationDTOJsonCopy.get("PhoneNumber").toString();
						if (mobNum.length() > 9 && mobNum.length() <= 20) {
							flag = mobNum.matches(mobileRegex);
						}
						if(flag == false) {
							errorSet.put("PhoneNumber", "Invalid Mobile Phone");
						}
						
					}
					
					if (dataMigrationDTOJsonCopy.has("AlternateNumber") && !dataMigrationDTOJsonCopy.get("AlternateNumber").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("AlternateNumber").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("AlternateNumber").toString().equalsIgnoreCase("undefined")) {
						boolean flag = false;
						String alternatemobNum = dataMigrationDTOJsonCopy.get("AlternateNumber").toString();
						if (alternatemobNum.length() > 9 && alternatemobNum.length() <= 20) {
							flag = alternatemobNum.matches(mobileRegex);
						}
						if(flag == false) {
							errorSet.put("AlternateNumber", "Invalid Alternate Mobile Phone");
						}
						
					}
					
					String rateRegEx = "^[0-9\b]+$";
					if (dataMigrationDTOJsonCopy.has("CurrentCTC") && !dataMigrationDTOJsonCopy.get("CurrentCTC").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("CurrentCTC").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("CurrentCTC").toString().equalsIgnoreCase("undefined")) {
						boolean flag = false;
						String expectedRate = dataMigrationDTOJsonCopy.get("CurrentCTC").toString();
						if (expectedRate.length() > 0 && expectedRate.length() <= 8) {
							flag = expectedRate.matches(rateRegEx);
						}
						if(flag == false) {
							errorSet.put("CurrentCTC", "CurrentCTC Allow Only Integers and length 0-8");
						}
						
					}
					
					if (dataMigrationDTOJsonCopy.has("ExpectedCTC") && !dataMigrationDTOJsonCopy.get("ExpectedCTC").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("ExpectedCTC").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("ExpectedCTC").toString().equalsIgnoreCase("undefined")) {
						boolean flag = false;
						String currentRate = dataMigrationDTOJsonCopy.get("ExpectedCTC").toString();
						if (currentRate.length() > 0 && currentRate.length() <= 8) {
							flag = currentRate.matches(rateRegEx);
						}
						if(flag == false) {
							errorSet.put("ExpectedCTC", "ExpectedCTC Allow Only Integers and length 0-8");
						}
						
					}
					
					Candidate candidate = candidateService.findByCandidateEmail(dataMigrationDTOJsonCopy.getString("EmailId"),orgLocation.getId());
					if(candidate != null) {
						RequirementSubmission requirementSubmission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(Long.valueOf(dataMigrationDTOJsonCopy.getString("requirementId")), candidate.getId());
						if(requirementSubmission != null) {
							errorSet.put("EmailId", "Candidate already Submitted.!");
						}
					}
				
			}
	
			
			public void dataValidationForTimesheets(HashMap< String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, DataFormatter objDefaultFormat, User user, OrgLocations orgLocation, String timeFrequency) throws Exception
			{
				logger.debug("In dataValidationForTimesheets method...");
				Projects project = null;
					try {
						if (!dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("project").toString().equalsIgnoreCase("undefined")) {
							project = projectsService.getProjectByProjectName(dataMigrationDTOJsonCopy.get("project").toString());
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
						if(timeFrequency.equalsIgnoreCase("Monthly") || timeFrequency.equalsIgnoreCase("BiMonthly")) {
							validateTimesheetMonthlyAndByMonthlyFrequency(dataMigrationDTOJsonCopy,errorSet);
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
						if(project != null && (project.getInvoiceFrequency().equalsIgnoreCase("Weekly") || project.getInvoiceFrequency().equalsIgnoreCase("Monthly") || project.getInvoiceFrequency().equalsIgnoreCase("Semi-Monthly") || project.getInvoiceFrequency().equalsIgnoreCase("Bi-Weekly"))){
						ProjectTasks projectTask = projectTasksService.getProjectTasksByProjectIdAndProjectIdAndTaskName(project.getId(),"System Task Billable");
						 if(projectTask != null) {
							 User employeeUser =  userService.findByEmail(importTimeSheet.getEmployeeEmail());
							if(employeeUser != null) {
							  Employees employee = employeesService.getEmployeeByUserId(employeeUser.getId());
							 ProjectMembers projectMember = projectMembersService.getProjectMemberByEmpIdAndProjectId(employee.getId(), project.getId());
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
										 Date weekEndDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),"15-1-2018"/*importTimeSheet.getActualEndDate()*/);
										 dateList.put("STARTDATE", weekStartDate);
										 dateList.put("ENDDATE", weekEndDate);
										 timeSheetImportMonthSaving(importTimeSheet,assignDate,terminatedDate,null,orgLocation,project,projectMember,projectTask,errorSet,dataMigrationDTOJsonCopy,user,weekStartDate,weekEndDate,dateList,timeFrequency);
										
									}else {
										errorSet.put("actualStartDate","please provide proper month and Year to add records."); 
									}
							 }else {
								 errorSet.put(dataMigrationDTOJsonCopy.getString("employeeEmail"),"not a valid project member.");
							 }
						  }else {
							  errorSet.put("employeeEmail","not record exists with "+dataMigrationDTOJsonCopy.getString("employeeEmail"));
						  }
						}
					}else {
						errorSet.put("taskName", "required task name not found");
					}
				}
			}
			
			
			

private void validateTimesheetMonthlyAndByMonthlyFrequency(JSONObject dataMigrationDTOJsonCopy,	HashMap<String, String> errorSet) throws JSONException {
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
	if (!dataMigrationDTOJsonCopy.get("days").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("days").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("days").toString().equalsIgnoreCase("undefined")) {
	
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
	if (!dataMigrationDTOJsonCopy.get("week6").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("week6").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("week5").toString().equalsIgnoreCase("undefined")) {
		Matcher matcherWeek = patternWeekCheck.matcher(dataMigrationDTOJsonCopy.get("week6").toString());
		if(matcherWeek.matches() == true && (Float.parseFloat(dataMigrationDTOJsonCopy.get("week6").toString()) < 120L)) {
			dataMigrationDTOJsonCopy.put("week6",dataMigrationDTOJsonCopy.get("week5").toString());
		}else {
			errorSet.put("week6","week6 hours cannot be more then 120Hrs");
		}
	}
	
}

private void timeSheetImportMonthSaving(ImportTimesheetDTO importTimeSheet, Date assignDate, Date terminatedDate, String[] year, OrgLocations orgLocation, Projects project, ProjectMembers projectMember, ProjectTasks projectTask, HashMap<String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, User user, Date startDate, Date endDate, Map<String, Date> dateList, String timeFrequency) throws Exception {
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
							 Date subStartDate = key;
							 Date subEndDate = value;
							 Date frequencyStartDate = null;
							 Date frequencyEndDate =  null;
							 if(project.getInvoiceFrequency().equalsIgnoreCase("Weekly")){
								 SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
						    	 String day_Of_Date = simpleDateformat.format(key);
						    	 if(!day_Of_Date.equalsIgnoreCase("MON")) {
						    		  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
									  tsCal.setLenient(false);
									  tsCal.setTime(key);
									  subStartDate= CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocation.getDateFormat()).getTime();
						    	 }
						    	 
						    	 String last_day_Of_Date = simpleDateformat.format(value);
						    	 if(!last_day_Of_Date.equalsIgnoreCase("SUN")) {
						    		  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
									  tsCal.setLenient(false);
									  tsCal.setTime(value);
									  subEndDate= CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocation.getDateFormat()).getTime();
						    	 }
						    	 //avgHours
					    		  frequencyStartDate = subStartDate;
								  frequencyEndDate = subEndDate;
								  
								  int noOfBillableHours =  CalendarUtil.getNumberOfBillableDates(key,value); 
					    		  float averageHours = getAverageHours(count,importTimeSheet,noOfBillableHours,timeFrequency);
					    		  saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,subStartDate,subEndDate,key,value,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
					    		  System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
						    		 
							 }  else if(project.getInvoiceFrequency().equalsIgnoreCase("Semi-Monthly")){
								   Date empMonth15thDate = CalendarUtil.getMonth15thDateByGivenDate(startDate,Constants.CURRENT_MONTH).get("STARTDATE");
								   if(!CalendarUtil.isDateBetweenTwoDates(key,value, empMonth15thDate)){
									   if(value.before(empMonth15thDate)) {
										   frequencyStartDate = startDate;
										   frequencyEndDate = empMonth15thDate;
									   }else {
										   frequencyStartDate = CalendarUtil.getMonth16thDateByGivenDate(startDate,Constants.CURRENT_MONTH).get("STARTDATE");
										   frequencyEndDate = endDate;
									   }
									   int noOfBillableHours =  CalendarUtil.getNumberOfBillableDates(key,value); 
							    	   float averageHours = getAverageHours(count,importTimeSheet,noOfBillableHours,timeFrequency);
							    	   saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,subStartDate,subEndDate,key,value,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
								   }else {
									   if(value.after(empMonth15thDate) || key.equals(empMonth15thDate)) {
										   subStartDate = key;
										   subEndDate = empMonth15thDate;
										   frequencyStartDate = startDate;
										   frequencyEndDate = empMonth15thDate;
										  
										   int noOfBillableHours =  CalendarUtil.getNumberOfBillableDates(key,value); 
								    	   float averageHours = getAverageHours(count,importTimeSheet,noOfBillableHours,timeFrequency);
								    	   saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,subStartDate,subEndDate,subStartDate,subEndDate,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
								    	   System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
								    	   
								    	   subStartDate = CalendarUtil.getMonth16thDateByGivenDate(startDate,Constants.CURRENT_MONTH).get("STARTDATE");
										   subEndDate = value;
										   frequencyStartDate = CalendarUtil.getMonth16thDateByGivenDate(startDate,Constants.CURRENT_MONTH).get("STARTDATE");;
										   frequencyEndDate = endDate;
										   
								    	   saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,subStartDate,subEndDate,subStartDate,subEndDate,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
									   }else {
										   subStartDate = key;
										   subEndDate = value;
										   frequencyStartDate = startDate;
										   frequencyEndDate = empMonth15thDate;
										   int noOfBillableHours =  CalendarUtil.getNumberOfBillableDates(key,value); 
								    	   float averageHours = getAverageHours(count,importTimeSheet,noOfBillableHours,timeFrequency);
								    	   saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,subStartDate,subEndDate,key,value,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
								    	   System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
									   }
								   }
								   
						    	   System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
							 }  
							 else if(project.getInvoiceFrequency().equalsIgnoreCase("Bi-Weekly")) {
								 
									 SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
							    	 String day_Of_Date = simpleDateformat.format(key);
							    	 if(!day_Of_Date.equalsIgnoreCase("MON")) {
							    		  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
										  tsCal.setLenient(false);
										  tsCal.setTime(key);
										  subStartDate= CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON",orgLocation.getDateFormat()).getTime();
							    	 }
							    	 
							    	 String last_day_Of_Date = simpleDateformat.format(value);
							    	 if(!last_day_Of_Date.equalsIgnoreCase("SUN")) {
							    		  Calendar tsCal = Calendar.getInstance(TimeZone.getDefault());
										  tsCal.setLenient(false);
										  tsCal.setTime(value);
										  subEndDate= CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN",orgLocation.getDateFormat()).getTime();
							    	 }
							    	 //avgHours
							    	 Date biWeeklyStartMondayDate = timesheetFrequencyGeneratorUtil.getBiweeklyStartMonDayDateByUsingProjectStartDateAndCurrentDateForTsImports(project,key);
					    		    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
									cal.setLenient(false);
									cal.setTime(biWeeklyStartMondayDate);
									Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(cal, "MON",orgLocation.getDateFormat());
									frequencyStartDate = startMonday.getTime();
									Calendar endSunday = CalendarUtil.getDateIncrementByincrement(cal,13);
									frequencyEndDate = endSunday.getTime();
								  
								  int noOfBillableHours =  CalendarUtil.getNumberOfBillableDates(key,value); 
					    		  float averageHours = getAverageHours(count,importTimeSheet,noOfBillableHours,timeFrequency);
					    		  saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,subStartDate,subEndDate,key,value,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
					    		  System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
							 }
							 else if(project.getInvoiceFrequency().equalsIgnoreCase("Monthly")){
								   frequencyStartDate = startDate;
								   frequencyEndDate = endDate;
								  
								   int noOfBillableHours =  CalendarUtil.getNumberOfBillableDates(key,value); 
						    	   float averageHours = getAverageHours(count,importTimeSheet,noOfBillableHours,timeFrequency);
						    	   saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,subStartDate,subEndDate,key,value,averageHours,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
						    	   System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
							 }
							 
							
				    		 count++;
						}
					}
			 }else if(timeFrequency.equalsIgnoreCase("Weekly")) {
		    		   saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,startDate,endDate,startDate,endDate,0f,user,orgLocation,startDate,endDate,importTimeSheet,timeFrequency);
			 }else if(timeFrequency.equalsIgnoreCase("Monthly") || timeFrequency.equalsIgnoreCase("BiMonthly")) {
				 int flag = 0;
				 if(frequencyDropDown != null && !frequencyDropDown.isEmpty()){
						for (Map.Entry<String,String> entry : frequencyDropDown.entrySet()) {
							 Date  key = format.parse(entry.getKey());
							 Date  value = format.parse(entry.getValue());
				    		 Date frequencyStartDate = monthStartDate;
							 Date frequencyEndDate = monthEndDate;
							 Date weekStartDate = key;
							 Date weekEndDate = value;
							 while(weekStartDate.before(weekEndDate) || weekStartDate.equals(weekEndDate)){
								 SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
						    	 String day_Of_Date = simpleDateformat.format(weekStartDate);
								 
								 if(day_Of_Date.equalsIgnoreCase("sun")) {
									 importTimeSheet.setSun(importTimeSheet.getDays().get(flag));
								 }else if(day_Of_Date.equalsIgnoreCase("mon")) {
									 importTimeSheet.setMon(importTimeSheet.getDays().get(flag));
								 }else if(day_Of_Date.equalsIgnoreCase("tue")) {
									 importTimeSheet.setTue(importTimeSheet.getDays().get(flag));
								 }else if(day_Of_Date.equalsIgnoreCase("wed")) {
									 importTimeSheet.setWed(importTimeSheet.getDays().get(flag));
								 }else if(day_Of_Date.equalsIgnoreCase("thu")) {
									 importTimeSheet.setThu(importTimeSheet.getDays().get(flag));
								 } else if(day_Of_Date.equalsIgnoreCase("fri")) {
									 importTimeSheet.setFri(importTimeSheet.getDays().get(flag));
								 }else if(day_Of_Date.equalsIgnoreCase("sat")) {
									 importTimeSheet.setSat(importTimeSheet.getDays().get(flag));
								 }
								 weekStartDate = CalendarUtil.getNextDate(weekStartDate);
								 flag++;
							 }
							 saveImportedTimeSheetRecords(project,projectTask.getProjectTasksUUID(),projectMember,key,value,key,value,0f,user,orgLocation,frequencyStartDate,frequencyEndDate,importTimeSheet,timeFrequency);
				    		 System.out.println(projectMember.getProjectMemberCandidteId().getFullName());
						}
					}
			 }
			 
		 }else {
			 errorSet.put(dataMigrationDTOJsonCopy.getString("Month-Year"),"not in assgined project member Date"); 
		 }
	}

		

private void saveImportedTimeSheetRecords(Projects project,String projectTaskUUID,ProjectMembers projectMember, Date subStartDate, Date subEndDate,Date actualStartDate, Date actualEndDate,float averageHours, User loginUser, OrgLocations orgLocation, Date frequencyStartDate, Date frequencyEndDate, ImportTimesheetDTO importTimeSheet, String timeFrequency) throws Exception {
	 TimesheetSubmissions timesheetSubmissions  = timesheetSubmissionsService.getAllCandidateTimesheetSubmissionsByProjectIdAndMasterCandidateId(subStartDate,subEndDate,projectMember.getProjects().getId(),projectMember.getMasterCandidateId().getId());
 	if(timesheetSubmissions == null){
 		timesheetSubmissions =  new TimesheetSubmissions();
	    	timesheetSubmissions.setStartDate(subStartDate);
	    	timesheetSubmissions.setEndDate(subEndDate);
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
						//Additional Fields are added for de-normalization
						saHierarchy.setProjectId(timesheetSubmissions.getProjects());
						saHierarchy.setMasterCandidateId(timesheetSubmissions.getMasterCandidateId().getId());
						saHierarchy.setProjectClientId(hierarchy.getProjectClientsId());
						saHierarchy.setOrgLocationId(hierarchy.getProjectClientsId().getOrgLocationsId().getId());
						saHierarchy.setOrgId(hierarchy.getProjectClientsId().getOrgLocationsId().getOrg().getId());
						ProjectMembers pm = projectMembersService.getProjectMembersByMasterCandidateIdAndProjectClientId(timesheetSubmissions.getMasterCandidateId().getId(), hierarchy.getProjectClientsId().getId());
						if(pm != null) {
							saHierarchy.setProjectMemberId(pm);
							saHierarchy.setCandidateId(pm.getCandidateId());
							User consultantUser = candidateService.getConsultantUserByCandidateIdOrMasterCandidateId(pm.getProjectMemberCandidteId().getId(),pm.getProjectMemberCandidteId().getMasterCandidateId().getId());
							saHierarchy.setConsultantUser(consultantUser);
						}
						
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
				
				  if(timeFrequency.equalsIgnoreCase("Weekly") ||timeFrequency.equalsIgnoreCase("Monthly") || timeFrequency.equalsIgnoreCase("BiMonthly")) {
					averageHours = getAverageHours(weekCount,importTimeSheet,0,timeFrequency);
				  }
				  ProjectTasks projectTasks = projectTasksService.getProjectTasksByUUID(projectTaskUUID);
				  TimeSheetDayEntries  timesheetDayEntries = timesheetDayEntriesDao.getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(timesheetSubmissions.getId(), projectTasks.getId(), weekStartDate,"Regular");
				  if(timesheetDayEntries == null){
					 timesheetDayEntries = new TimeSheetDayEntries();
					 timesheetDayEntries.setBillableHours("00:00");
					 timesheetDayEntries.setNonBillableHours("00:00");
					 timesheetDayEntries.setTotalHours("00:00");
					 timesheetDayEntries.setIsManuallyAdded(Constants.NO);
				    }
				  	
					String taskType =projectTasks.getBillableType();
					timesheetDayEntries.setProjectTask(projectTasks);
					String hours =CalendarUtil.getTimesheetHoursDisplayFormat(averageHours,null).replace(".", ":");
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
					
					timesheetDayEntries.setStatus(1);
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
				case 6:
					if(importTimeSheet.getWeek6() != null && StringUtils.isNotEmpty(importTimeSheet.getWeek6())) {
					 AverageHours = calculateHoursAndMin(Double.parseDouble(importTimeSheet.getWeek6()),noOfBillableHours);
					 break;
					}else {
						AverageHours = 0D;
						break;
					}
				}
			}else if(type.equalsIgnoreCase("Weekly") || type.equalsIgnoreCase("Monthly") || type.equalsIgnoreCase("BiMonthly")) {
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
			
			double res2 = 0;
			if(days > 0) {
				res2 = new BigDecimal(res1).divide(new BigDecimal(days),2, RoundingMode.HALF_UP).doubleValue();
			}
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
				
				if(!dataMigrationDTOJsonCopy.getString("workingStatus").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("workingStatus"))) {
					String status = dataMigrationDTOJsonCopy.getString("workingStatus").toLowerCase();
					if(status.equalsIgnoreCase("working") || status.equalsIgnoreCase("out of service")){
						// capitalize the words starting letter
						status=	WordUtils.capitalizeFully(status);
						dataMigrationDTOJsonCopy.put("workingStatus", status);
					}else{
						errorSet.put("workingStatus", dataMigrationDTOJsonCopy.getString("workingStatus")+" is invalid Working Status");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("sno").equals("sno") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("sno"))) {
					String serialNo = dataMigrationDTOJsonCopy.getString("sno");
				       Asset asset = assetService.validateAssetSno(dataMigrationDTOJsonCopy.getString("sno"), orgLocation.getId());
					 	if (asset != null) {
					 		errorSet.put("sno", dataMigrationDTOJsonCopy.getString("sno")+" is Already Existed");
						}else{
							dataMigrationDTOJsonCopy.put("sno", serialNo);

						}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("issueDate").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("issueDate"))) {
					try {
						DateFormat formatter = new SimpleDateFormat(orgLocation.getDateFormat()); 
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.getString("issueDate"));
						String closedDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put("issueDate", closedDate);
					} catch (Exception e) {
						errorSet.put("issueDate", dataMigrationDTOJsonCopy.getString("issueDate")+" is invalid Close Date");
					}
				}
				
				
				if(!dataMigrationDTOJsonCopy.getString("depreciationDate").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("depreciationDate"))) {
					try {
						DateFormat formatter = new SimpleDateFormat(orgLocation.getDateFormat()); 
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.getString("depreciationDate"));
						String closedDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put("depreciationDate", closedDate);
					} catch (Exception e) {
						errorSet.put("depreciationDate", dataMigrationDTOJsonCopy.getString("depreciationDate")+" is invalid Close Date");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("purchaseDate").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("purchaseDate"))) {
					try {
						DateFormat formatter = new SimpleDateFormat(orgLocation.getDateFormat()); 
						Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.getString("purchaseDate"));
						String closedDate = formatter.format(date);
						dataMigrationDTOJsonCopy.put("purchaseDate", closedDate);
					} catch (Exception e) {
						errorSet.put("purchaseDate", dataMigrationDTOJsonCopy.getString("purchaseDate")+" is invalid Close Date");
					}
				}
				
				
				String rateRegEx = "^[0-9\b]+$";
				if(!dataMigrationDTOJsonCopy.getString("cost").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("cost"))) {
					boolean flag = false;
					String cost = dataMigrationDTOJsonCopy.getString("cost");
					if (cost.length() > 0 && cost.length() <= 8) {
						flag = cost.matches(rateRegEx);
					}
					if(flag == false) {
						errorSet.put("cost", "Cost Allow Only Incorrect float value and length 1-8");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("depreciationAmount").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("depreciationAmount"))) {
					boolean flag = false;
					String depreciationAmount = dataMigrationDTOJsonCopy.getString("depreciationAmount");
					if (depreciationAmount.length() > 0 && depreciationAmount.length() <= 8) {
						flag = depreciationAmount.matches(rateRegEx);
					}
					if(flag == false) {
						errorSet.put("depreciationAmount", "Depreciation Amount Allow Only Incorrect float value and length 1-8");
					}
				}
			  
				if(!dataMigrationDTOJsonCopy.getString("vendorDetails").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("vendorDetails"))) {
					int length = dataMigrationDTOJsonCopy.getString("vendorDetails").trim().length();	
					if(length < 101 && length > 1 ) {
						dataMigrationDTOJsonCopy.put("vendorDetails", dataMigrationDTOJsonCopy.getString("vendorDetails").trim());
					}else {
						errorSet.put("vendorDetails", dataMigrationDTOJsonCopy.getString("vendorDetails").trim()+"  should be between 1-100 characters");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("antiVirus").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("antiVirus"))) {
					int length = dataMigrationDTOJsonCopy.getString("antiVirus").trim().length();	
					if(length < 101 && length > 1 ) {
						dataMigrationDTOJsonCopy.put("antiVirus", dataMigrationDTOJsonCopy.getString("antiVirus").trim());
					}else {
						errorSet.put("antiVirus", "Anti-Virus  " + dataMigrationDTOJsonCopy.getString("antiVirus").trim()+"  should be between 1-100 characters");
					}
				}
				
				
				if(!dataMigrationDTOJsonCopy.getString("osKey").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("osKey"))) {
					int length = dataMigrationDTOJsonCopy.getString("osKey").trim().length();	
					if(length < 51 && length > 5 ) {
						dataMigrationDTOJsonCopy.put("osKey  ", dataMigrationDTOJsonCopy.getString("osKey").trim());
					}else {
						errorSet.put("osKey", "OS Key"+dataMigrationDTOJsonCopy.getString("osKey").trim()+"  should be between 5-100 characters");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("operatingSystem").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("operatingSystem"))) {
					int length = dataMigrationDTOJsonCopy.getString("operatingSystem").trim().length();	
					if(length < 101 && length > 1 ) {
						dataMigrationDTOJsonCopy.put("operatingSystem", dataMigrationDTOJsonCopy.getString("operatingSystem").trim());
					}else {
						errorSet.put("operatingSystem", "Operating System "+ dataMigrationDTOJsonCopy.getString("operatingSystem").trim()+"  should be between 1-100 characters");
					}
				}
				
				if(!dataMigrationDTOJsonCopy.getString("macAddress").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("macAddress"))) {
					int length = dataMigrationDTOJsonCopy.getString("macAddress").trim().length();	
					if(length < 51 && length > 8 ) {
						dataMigrationDTOJsonCopy.put("macAddress", dataMigrationDTOJsonCopy.getString("macAddress").trim());
					}else {
						errorSet.put("macAddress", "MAC Address  "+dataMigrationDTOJsonCopy.getString("macAddress").trim()+"  should be between 8-50 characters");
					}
				}
				
			}
		
		
		
		public void dataValidationForProjectTasks(HashMap<String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation,String isGlobalAccess) throws Exception {
			 Projects project = projectsService.getProjectByProjectUUID(dataMigrationDTOJsonCopy.getString("projectUUID"));
			if(dataMigrationDTOJsonCopy.getString("taskstype").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskstype"))) {
				errorSet.put("taskstype", "Taskstype field is missing");
			}
			if(dataMigrationDTOJsonCopy.getString("taskName").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskName"))) {
				errorSet.put("taskName", "Task Title field is missing");
			}
			
			if(dataMigrationDTOJsonCopy.getString("taskStatus").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskStatus"))) {
				errorSet.put("taskStatus", "TaskStatus field is missing");
			} 
			
			if(dataMigrationDTOJsonCopy.getString("taskBillableType").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskBillableType"))) {
				errorSet.put("taskBillableType", "Task BillableType field is missing");
			} 
			
			//Project Type Epic
			if(!dataMigrationDTOJsonCopy.getString("taskstype").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskstype"))) {
				List<LookUpData> taskTypeList = lookUpDataService.getLookUpDataByCategory(Constants.TASKTYPE);
				int incr = 0;
				for(LookUpData taskType:taskTypeList) {
					if(taskType.getName().trim().toLowerCase().equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("taskstype").trim().toLowerCase())) {
						incr++;
						dataMigrationDTOJsonCopy.put("taskstype", taskType.getUniqueKey());
					} 
				}
				if(incr<1) {
					errorSet.put("taskstype", dataMigrationDTOJsonCopy.getString("taskstype").trim()+" Invalid Task Type");
				}
			}
			
			//Project Type User Story
			if(!dataMigrationDTOJsonCopy.getString("epic").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("epic"))) {
				if(dataMigrationDTOJsonCopy.getString("taskstype").trim().toLowerCase().equalsIgnoreCase("UserStory")) {
					LookUpData lookupdata = lookUpDataService.getTasksTypeByUniqueKey("Epic");
					Collection<ProjectTasks> taskList = projectTasksService.getAllTasksByProjectIdAndTaskType(project.getId(), lookupdata.getId());
					int incr = 0;
					for(ProjectTasks pt :taskList) {
						if(pt.getTaskName().trim().toLowerCase().equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("epic").trim().toLowerCase())) {
							incr++;
							dataMigrationDTOJsonCopy.put("epic", pt.getId());
						} 
					}
					if(incr<1) {
						errorSet.put("epic", dataMigrationDTOJsonCopy.getString("epic").trim()+" Invalid Epic Name");
					}
				}
			}
			
			//Project Type Bug
			if(!dataMigrationDTOJsonCopy.getString("userStory").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("userStory"))) {
				if(dataMigrationDTOJsonCopy.getString("taskstype").trim().toLowerCase().equalsIgnoreCase("bug")) {
					String userStory = dataMigrationDTOJsonCopy.getString("userStory").trim().toLowerCase();
					LookUpData lookupdata = lookUpDataService.getTasksTypeByUniqueKey("UserStory");
					Collection<ProjectTasks> taskList = projectTasksService.getAllTasksByProjectIdAndTaskType(project.getId(), lookupdata.getId());
					int incr = 0;
					for(ProjectTasks pt :taskList) {
						if(pt.getTaskName().equalsIgnoreCase(userStory)) {
							incr++;
							dataMigrationDTOJsonCopy.put("userStory", pt.getId());
						} 
					}
					if(incr<1) {
						errorSet.put("userStory", dataMigrationDTOJsonCopy.getString("userStory").trim()+" Invalid UserStory Name");
					}
				}
				
			}
			
			
			//Project Type Sub Tasks
			if(!dataMigrationDTOJsonCopy.getString("subTask").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("subTask"))) {
				if(dataMigrationDTOJsonCopy.getString("taskstype").trim().toLowerCase().equalsIgnoreCase("SubTask")) {
					LookUpData lookupdata = lookUpDataService.getTasksTypeByUniqueKey("SubTask");
					Collection<ProjectTasks> taskList = projectTasksService.getAllTasksByProjectIdAndTaskType(project.getId(), lookupdata.getId());
					if(taskList != null) {
						int incr = 0;
						for(ProjectTasks pt :taskList) {
							if(pt.getTaskName().trim().toLowerCase().equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("subTask").trim().toLowerCase())) {
								incr++;
								dataMigrationDTOJsonCopy.put("subTask", pt.getId());
							} 
						}
						if(incr<1) {
							errorSet.put("subTask", dataMigrationDTOJsonCopy.getString("subTask").trim()+" Invalid SubTask Name");
						}
					}else {
						errorSet.put("subTask", dataMigrationDTOJsonCopy.getString("subTask").trim()+" Task Name is required");
					}
				}
			}
			
			
			
			//Project Task  Title
			if(!dataMigrationDTOJsonCopy.getString("taskName").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskName"))) {
				int length = dataMigrationDTOJsonCopy.getString("taskName").trim().length();	
				if(length < 251 && length > 1 ) {
					dataMigrationDTOJsonCopy.put("taskName", dataMigrationDTOJsonCopy.getString("taskName").trim());
				}else {
					errorSet.put("taskName", dataMigrationDTOJsonCopy.getString("taskName").trim()+"  should be between 2-250 characters");
				}
			}
			
			//Assigned To 
			if(!dataMigrationDTOJsonCopy.getString("assignTaskId").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("assignTaskId"))) {
			 List<ProjectMembers> projectAssignedList = projectsService.getProjectEmployeeListByProjectIdAndStatus(project.getId(),"Active",orgLocation,null,null,isGlobalAccess);
			 int incr = 0;
			 String[] emails = dataMigrationDTOJsonCopy.getString("assignTaskId").trim().replace("\n","").toLowerCase().split(",");
			 ArrayList<String> validEmails = new ArrayList<>();
			 for(String email:emails) {
				 if (isValid(email)) {
					 validEmails.add(email);
				 }
			 }
			 if(validEmails.size() > 0 ) {
				 StringBuffer assignedSB = new StringBuffer();
				 for(ProjectMembers pm:projectAssignedList) {
					 if(validEmails.contains(pm.getMasterCandidateId().getEmail().trim().toLowerCase())) {
						 incr++;
							if(incr > 1) {
								assignedSB.append(",");
							}
							assignedSB.append(pm.getId());
			    	  } 
				 } 
				 if(incr > 0) {
						 dataMigrationDTOJsonCopy.put("assignTaskId", assignedSB.toString());
				 }else {
					 errorSet.put("assignTaskId", dataMigrationDTOJsonCopy.getString("assignTaskId").trim()+" Assigned emails are not matchings");
				 }
			 }else {
					errorSet.put("assignTaskId", dataMigrationDTOJsonCopy.getString("assignTaskId").trim()+" Invalid Email Formate");
			  }
			}
			
			//Project Status
			
			if(!dataMigrationDTOJsonCopy.getString("taskStatus").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskStatus"))) {
				int incr = 0;
				List<LookUpData> taskStatusList = lookUpDataService.getLookUpDataByCategory(Constants.TASKSTATUS);
				for(LookUpData taskStatus:taskStatusList) {
					if(taskStatus.getName().trim().toLowerCase().equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("taskStatus").trim().toLowerCase())) {
						incr++;
						dataMigrationDTOJsonCopy.put("taskStatus", taskStatus.getId().toString());
					} 
				}
				if(incr<1) {
					errorSet.put("taskStatus", dataMigrationDTOJsonCopy.getString("taskStatus").trim()+" Invalid Task Status");
				}
			} 
			
			//Project priority
			 List<LookUpData> taskPriorityList = lookUpDataService.getLookUpDataByCategory(Constants.TASKPRIORITY);
			if(!dataMigrationDTOJsonCopy.getString("priority").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("priority"))) {
             String priority = dataMigrationDTOJsonCopy.getString("priority").trim().toLowerCase(); 
			 int incr = 0;
				for(LookUpData taskPriority:taskPriorityList) {
					if(taskPriority.getName().trim().toLowerCase().equalsIgnoreCase(priority)) {
						incr++;
						dataMigrationDTOJsonCopy.put("priority", taskPriority.getId().toString());
					} 
				}
				if(incr<1) {
					errorSet.put("priority", dataMigrationDTOJsonCopy.getString("priority").trim()+" Invalid Priority");
				}
			}else {
				for(LookUpData taskPriority:taskPriorityList) {
					if(taskPriority.getName().trim().toLowerCase().equalsIgnoreCase("p1")) {
						dataMigrationDTOJsonCopy.put("priority", taskPriority.getId().toString());
					} 
				}
			}

			//Task due date
			if(!dataMigrationDTOJsonCopy.getString("duDate").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("duDate"))) {
					dataMigrationDTOJsonCopy.put("duDate", dataMigrationDTOJsonCopy.getString("duDate").trim());
			}
			
			//Project estimates time
			if(!dataMigrationDTOJsonCopy.getString("estimatedTime").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("estimatedTime"))) {
				dataMigrationDTOJsonCopy.put("estimatedTime", dataMigrationDTOJsonCopy.getString("estimatedTime").trim());
			}
			
			
			//Project Severity
			if(!dataMigrationDTOJsonCopy.getString("severity").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("severity"))) {
				 List<LookUpData> taskSeverityList = lookUpDataService.getLookUpDataByCategory(Constants.TASKSEVERITY);
	             String severity = dataMigrationDTOJsonCopy.getString("severity").trim().toLowerCase(); 

				 int incr = 0;
					for(LookUpData taslSeverity :taskSeverityList) {
						if(taslSeverity.getName().trim().toLowerCase().equalsIgnoreCase(severity)) {
							incr++;
							dataMigrationDTOJsonCopy.put("severity", taslSeverity.getId().toString());
						} 
					}
					if(incr<1) {
						errorSet.put("severity", dataMigrationDTOJsonCopy.getString("severity").trim()+" Invalid Severity");
					}
				}
			
			//Project task Owner
			if(!dataMigrationDTOJsonCopy.getString("taskOwner").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskOwner"))) {
		      List<User> approversList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocation.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
				int incr = 0;
		      for(User ownerUser:approversList) {
		    	  //Email Validation.
		    	  if (isValid(dataMigrationDTOJsonCopy.getString("taskOwner"))) {
		    		  if(ownerUser.getEmail().trim().toLowerCase().equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("taskOwner").trim().toLowerCase())) {
							incr++;
							dataMigrationDTOJsonCopy.put("taskOwner", ownerUser.getId().toString());
						} 
		    	  }
				}
		      if(incr<1) {
					errorSet.put("taskOwner", dataMigrationDTOJsonCopy.getString("taskOwner").trim()+" Invalid Owner");
				}
			}
			
			
			//Project Status
			if(!dataMigrationDTOJsonCopy.getString("taskBillableType").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskBillableType"))) {
				String billableType = dataMigrationDTOJsonCopy.getString("taskBillableType").trim().toLowerCase(); 
				if(billableType.equalsIgnoreCase("billable")) {
					dataMigrationDTOJsonCopy.put("taskBillableType", "Billable");
				}else if(billableType.equalsIgnoreCase("nonbillable") || billableType.equalsIgnoreCase("non-billable")) {
					dataMigrationDTOJsonCopy.put("taskBillableType", "Non-Billable");
				}else {
					errorSet.put("taskBillableType", dataMigrationDTOJsonCopy.getString("taskBillableType").trim()+" Invalid Billing Type");
				}
			}
			
			//Project Task Business Currency
			if(!dataMigrationDTOJsonCopy.getString("currencyType").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("currencyType"))) {
				String currency = dataMigrationDTOJsonCopy.getString("currencyType").trim().toLowerCase();
				if(currency.equalsIgnoreCase("INR")) {
					dataMigrationDTOJsonCopy.put("currencyType", "INR");
				}else if(currency.equalsIgnoreCase("AUD")) {
					dataMigrationDTOJsonCopy.put("currencyType", "AUD");
				}else if(currency.equalsIgnoreCase("USD")) {
					dataMigrationDTOJsonCopy.put("currencyType", "USD");
				}else if(currency.equalsIgnoreCase("GBP")) {
					dataMigrationDTOJsonCopy.put("currencyType", "GBP");
				}else if(currency.equalsIgnoreCase("SGD")) {
					dataMigrationDTOJsonCopy.put("currencyType", "SGD");
				}else if(currency.equalsIgnoreCase("EUR")) {
					dataMigrationDTOJsonCopy.put("currencyType", "EUR");
				}else {
					errorSet.put("currencyType", dataMigrationDTOJsonCopy.getString("currencyType").trim()+" Invalid currency Type");
				}
			}
			
			
			//Project Task Business Value
			if(!dataMigrationDTOJsonCopy.getString("businessValue").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("businessValue"))) {
				String value = dataMigrationDTOJsonCopy.getString("businessValue");
				if(NumberUtils.isNumber(value)){
					if(Integer.valueOf(value) <= 99999999){
						dataMigrationDTOJsonCopy.put("businessValue", dataMigrationDTOJsonCopy.getString("businessValue").trim());
					}else {
						errorSet.put("businessValue", dataMigrationDTOJsonCopy.getString("businessValue").trim()+" Incorrect integer value");
					}
				}
			}
			
			// Project Task Description
			if(!dataMigrationDTOJsonCopy.getString("taskDescription").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("taskDescription"))) {
				int length = dataMigrationDTOJsonCopy.getString("taskDescription").trim().length();	
				if(length < 251 && length > 1 ) {
					dataMigrationDTOJsonCopy.put("taskDescription", dataMigrationDTOJsonCopy.getString("taskDescription").trim());
				}else {
					errorSet.put("taskDescription", dataMigrationDTOJsonCopy.getString("taskDescription").trim()+"  should be between 2-250 characters");
				}
			}
			
			
		}
		
		
		public void dataValidationForImmigration(HashMap<String, String> errorSet, JSONObject dataMigrationDTOJsonCopy,
				User user, OrgLocations orgLocation) throws Exception {
			
			    boolean orgFound = false;
			    
			    if(dataMigrationDTOJsonCopy.getString("beneficiaryFirstName").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("beneficiaryFirstName"))) {
					errorSet.put("beneficiaryFirstName", "Beneficiary First Name is missing");
				}
			    
			    if(dataMigrationDTOJsonCopy.getString("beneficiaryLastName").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("beneficiaryLastName"))) {
					errorSet.put("beneficiaryLastName", "Beneficiary Last Nam is missing");
				}
			
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("beneficiaryEmail")) || dataMigrationDTOJsonCopy.getString("beneficiaryEmail").equals("null")) {
				errorSet.put("beneficiaryEmail", "Beneficiary Email is missing"); // need to validate email address.
				} else {
				String email = dataMigrationDTOJsonCopy.getString("beneficiaryEmail").trim();
				boolean emailVaild = isValidEmailAddress(email);
				if (emailVaild) {
				Candidate candidateByEmail = candidateDao.getAllActiveCandidatesByLocationEmail(email, orgLocation.getId());
				if (candidateByEmail == null) {
				errorSet.put("beneficiaryEmail", "Beneficiary Email is not found");
				dataMigrationDTOJsonCopy.put("candidateId", "null");
				} else {
				dataMigrationDTOJsonCopy.put("candidateId", candidateByEmail.getId());
				// System.out.println("candidateId
				// INSIDE---->>>>>"+dataMigrationDTOJsonCopy.getString("candidateId"));
				}
				} else {
				errorSet.put("beneficiaryEmail", "Beneficiary Email is not a valid email address");
				}
				}
				
				if (!dataMigrationDTOJsonCopy.getString("beneficiaryPhone").equals("null")) {
					String mobileNumber = dataMigrationDTOJsonCopy.getString("beneficiaryPhone");
					if (mobileNumber != null && !"".equalsIgnoreCase(mobileNumber)) {
					boolean isValidated = isMobileNumberValid(mobileNumber);
					if (isValidated == false) {
					errorSet.put("beneficiaryPhone", "Phone Number is not valid");
					}
					}
					}

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("dateOfBirth")) || dataMigrationDTOJsonCopy.getString("dateOfBirth").equals("null")) {
				errorSet.put("dateOfBirth", "DOB is missing");
				}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("dateOfBirth"))) {
				errorSet.put("dateOfBirth", "DOB is invalid date");
				}

				/* if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("maritalStatus"))) {
				errorSet.put("maritalStatus", "Marital status is missing");/// dropdown value.
				} else {
				String maritalStatus = dataMigrationDTOJsonCopy.getString("maritalStatus");
				if (!ImmigrationConstants.MARRAIAGE_STATUS_LIST.contains(maritalStatus)) {
				errorSet.put("maritalStatus", "Marital status is invalid input");/// dropdown value.
				}
				}*/
				if (!dataMigrationDTOJsonCopy.getString("experience").equals("null")) {
					String exp = dataMigrationDTOJsonCopy.getString("experience");//.matches("/(^[0-9]+\\.[0-9]+?)+/g");
					
					Pattern p = Pattern.compile("^(\\d*\\.)?\\d([ a-zA-Z0-9]?)+$");//. represents single character  
					Matcher m = p.matcher(exp);  
					boolean isMatched = m.matches();  
						if(!isMatched){
						errorSet.put("experience", "experience data should be like 3.0 years or 3 years or 1.2 only Allowed");
						}
					}
				
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("petitionType")) || dataMigrationDTOJsonCopy.getString("petitionType").equals("null")) {
				  errorSet.put("petitionType", "Petition Type is missing"); /// dropdown value.

				} else {

				LookUpData data = lookUpDataService.getLookUpdataByCategoryAndName(Constants.VISA,
				dataMigrationDTOJsonCopy.getString("petitionType"));
				if (data == null) {
				errorSet.put("petitionType", "Petition Type is not found"); /// dropdown value.
				} else {
				// dataMigrationDTOJsonCopy.setString("petitionType", data.getId().toString());
				dataMigrationDTOJsonCopy.put("petitionType", data.getId());
				}

				}
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("template")) || dataMigrationDTOJsonCopy.getString("template").equals("null")) {
				errorSet.put("template", "Template is missing"); /// dropdown value.
				} else {
				Long tempId = immigrationCommonService.getTemplateDetailsByNameAndOrgLoacationId(
				dataMigrationDTOJsonCopy.getString("template"), orgLocation);
				if (tempId == null)
				errorSet.put("template", "Template name is not found");
				else
				dataMigrationDTOJsonCopy.put("template", tempId);

				}
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("immigrationJobId"))) {
				errorSet.put("immigrationJobId", "Job is missing"); /// dropdown value.
				} else {
				Requirement requirement = requirementService.getRequirementByGeneratedIdAndLocationId(
				dataMigrationDTOJsonCopy.getString("immigrationJobId"), orgLocation.getId());
				if (requirement == null) {
				errorSet.put("immigrationJobId", "Job is not found"); /// dropdown value.
				} else {
				// dataMigrationDTOJsonCopy.setString("petitionType", data.getId().toString());
				dataMigrationDTOJsonCopy.put("immigrationJobId", requirement.getId());
				}

				}
				
				if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("passportNo"))
				&& !dataMigrationDTOJsonCopy.getString("passportNo").equals("null") && !dataMigrationDTOJsonCopy.getString("candidateId").equals("null")) {
				boolean flag = immigrationCommonService.validateCandidatePassport(Long.parseLong(dataMigrationDTOJsonCopy.getString("candidateId")),
				dataMigrationDTOJsonCopy.getString("passportNo") );
				if(!flag) {

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("passportCountry")) || dataMigrationDTOJsonCopy.getString("passportCountry").equals("null")) {
				errorSet.put("passportCountry", "passportCountry is invalid input");/// dropdown value.
				}

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("passportStartDate")) || dataMigrationDTOJsonCopy.getString("passportStartDate").equals("null")) {
				errorSet.put("passportStartDate", "passportStartDate is mandatory if you have entered passportNo");/// dropdown value.
				}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("passportStartDate"))) {
				errorSet.put("passportStartDate", "passportStartDate is invalid date");
				}

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("passportEndDate")) || dataMigrationDTOJsonCopy.getString("passportEndDate").equals("null")) {
				errorSet.put("passportEndDate", "passportEndDate is mandatory if you have entered passportNo");/// dropdown value.
				}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("passportEndDate"))) {
				errorSet.put("passportEndDate", "passportEndDate is invalid date");
				}
				}else {
				errorSet.put("passportNo", "passportNo is already exist.");
				}
				}


				if (StringUtils.isNotBlank(dataMigrationDTOJsonCopy.getString("visaNo"))
				&& !dataMigrationDTOJsonCopy.getString("visaNo").equals("null") && !dataMigrationDTOJsonCopy.getString("candidateId").equals("null")) {
				//boolean flag = immigrationCommonService.validateCandidatePassportVisa(Long.parseLong(dataMigrationDTOJsonCopy.getString("candidateId")),
				//.ID_TYPE_VISA,dataMigrationDTOJsonCopy.getString("visaNo") );
				//if(!flag) {

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("visaCountry")) || dataMigrationDTOJsonCopy.getString("visaCountry").equals("null")) {
				errorSet.put("visaCountry", "visaCountry is mandatory if you have entered visano");/// dropdown value.
				}

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("visaStartDate")) || dataMigrationDTOJsonCopy.getString("visaStartDate").equals("null")){
				errorSet.put("visaStartDate", "visaStartDate is mandatory if you have entered visano");/// dropdown value.
				}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("visaStartDate"))) {
				errorSet.put("visaStartDate", "visaStartDate is invalid date");
				}

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("visaEndDate")) || dataMigrationDTOJsonCopy.getString("visaEndDate").equals("null")) {
				errorSet.put("visaEndDate", "visaEndDate is mandatory if you have entered visano");/// dropdown value.
				}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("visaEndDate"))) {
				errorSet.put("visaEndDate", "visaEndDate is invalid date");
				}

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("visaType")) || dataMigrationDTOJsonCopy.getString("visaType").equals("null")) {
				errorSet.put("visaType", "Visa Type is missing"); /// dropdown value.
				} else {
				LookUpData data = lookUpDataService.getLookUpdataByCategoryAndName(Constants.VISA,
				dataMigrationDTOJsonCopy.getString("visaType"));
				if (data == null) {
				errorSet.put("visaType", "Visa Type is not found"); /// dropdown value.
				} else {
				// dataMigrationDTOJsonCopy.setString("petitionType", data.getId().toString());
				dataMigrationDTOJsonCopy.put("visaType",data.getName());
				}

				}
				//}else {
			//	errorSet.put("visaNo", "visaNo is already exist.");
			//	}

				}


				if (!dataMigrationDTOJsonCopy.getString("dateOfArrival").equals("null") && !CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("dateOfArrival"))) {
				errorSet.put("dateOfArrival", "dateOfArrival is invalid date");
				}

				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("openOn")) || dataMigrationDTOJsonCopy.getString("openOn").equals("null")) {
				errorSet.put("openOn", "Open On is missing");
				}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("openOn"))) {
				errorSet.put("openOn", "openOn is invalid date");
				}


				if (!dataMigrationDTOJsonCopy.getString("lcaStartDate").equals("null") && !CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("lcaStartDate"))) {
				errorSet.put("lcaStartDate", "lcaStartDate is invalid date");
				}

				if (!dataMigrationDTOJsonCopy.getString("lcaEndDate").equals("null") && !CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("lcaEndDate"))) {
				errorSet.put("lcaEndDate", "lcaEndDate is invalid date");
				}

				if (!dataMigrationDTOJsonCopy.getString("prevLcaStartDate").equals("null") && !CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("prevLcaStartDate"))) {
				errorSet.put("prevLcaStartDate", "prevLcaStartDate is invalid date");
				}

				if (!dataMigrationDTOJsonCopy.getString("prevLcaEndDate").equals("null") && !CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("prevLcaEndDate"))) {
				errorSet.put("prevLcaEndDate", "prevLcaEndDate is invalid date");
				}


				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("status"))) {
				errorSet.put("status", "Status is missing");
				} else {

				LookUpData data = lookUpDataService.getLookUpdataByCategoryAndName(ImmigrationConstants.PETITION_STATUS,
				dataMigrationDTOJsonCopy.getString("status"));
				if (data == null) {
				errorSet.put("status", "status is not found"); /// dropdown value.
				} else {
				dataMigrationDTOJsonCopy.put("status", data.getId());
				}

				}
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("company"))) {// company start
				errorSet.put("company", "Company is missing"); /// dropdown value.
				} else {
				List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
				
				for (OrgRelations rel : vendorsList) {
				if (StringUtils.isNotBlank(orgLocation.getIsLawFirm())
				&& orgLocation.getIsLawFirm().equalsIgnoreCase("Y")) {
				if (rel.getLocationId().getOrgLocationName()
				.equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("company"))) {
				dataMigrationDTOJsonCopy.put("company", rel.getId());
				orgFound = true;
				break;
				/// dropdown value.
				}
				} else {
				if (rel.getResponderLocationId().getOrgLocationName()
				.equalsIgnoreCase(dataMigrationDTOJsonCopy.getString("company"))) {
				dataMigrationDTOJsonCopy.put("company", rel.getId());
				orgFound = true;
				break;
				/// dropdown value.
				}
				}
				}
				if (!orgFound)
				errorSet.put("company", "Company is not found"); //

				} // company end
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("companyContactEmail"))) {
				errorSet.put("companyContactEmail", "Attorney/Contact email is missing"); /// dropdown value.

				} else if(orgFound){
				String email = dataMigrationDTOJsonCopy.getString("companyContactEmail");
				String venodorId = dataMigrationDTOJsonCopy.getString("company");
				boolean isSpocFound = false;
				boolean emailVaild = isValidEmailAddress(email);
				if (emailVaild && StringUtils.isNotBlank(venodorId)) {
				// User userByEmail = userService.findByEmailAndOrgLocationsId(email,
				// orgLocation.getId());
				List<OrgSpoc> vendorContactsList = orgSpocService.getOrgSpocByOrgRelationId(Long.parseLong(venodorId));
				if (vendorContactsList != null) {
				for (OrgSpoc vendorContacts : vendorContactsList) {
				if (StringUtils.isNotBlank(orgLocation.getIsLawFirm())
				&& orgLocation.getIsLawFirm().equalsIgnoreCase("Y")) {
				if (vendorContacts.getClientUser().getEmail().equalsIgnoreCase(email)) {
				dataMigrationDTOJsonCopy.put("companyContactEmail", vendorContacts.getId());
				isSpocFound = true;
				break;
				}
				} else {
				if (vendorContacts.getVendorUser().getEmail().equalsIgnoreCase(email)) {
				dataMigrationDTOJsonCopy.put("companyContactEmail", vendorContacts.getId());
				isSpocFound = true;
				break;
				}
				}
				}
				if (!isSpocFound)
				errorSet.put("companyContactEmail", "Attorney/Contact email is not found");
				}

				} else {
				errorSet.put("companyContactEmail", "Attorney/Contact email is not a valid email address");
				}

				}

				}
		
		public void dataValidationForLCA(HashMap<String, String> errorSet, JSONObject dataMigrationDTOJsonCopy,
				User user, OrgLocations orgLocation) throws Exception {
			
			   System.out.println(" inside LCA Data validation block method Need to add code for validation ==========>");
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("newLcaNumber")) || dataMigrationDTOJsonCopy.getString("newLcaNumber").equals("null")) {
					errorSet.put("newLcaNumber", "LCA Number is missing"); 
					
					}else {
						/* Boolean lcaNumber=Pattern.matches("[I][-]{1}[0-9]{3}[-]{1}[0-9]{5}[-]{1}[0-9]{6}", dataMigrationDTOJsonCopy.getString("newLcaNumber"));
						 if(lcaNumber == false) {
							 errorSet.put("newLcaNumber", "In Valid LCA Number");
						 }*/
						Boolean isValidated = immigrationCommonService.validateLcaNumber(dataMigrationDTOJsonCopy.getString("newLcaNumber"), orgLocation.getId());
						if(isValidated == true){
							errorSet.put("newLcaNumber", "LCA Number already existed");
						}
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("newLcaTitle")) || dataMigrationDTOJsonCopy.getString("newLcaTitle").equals("null")) {
					errorSet.put("newLcaTitle", "LCA Title is missing"); 
					}else {
						Boolean isValidated = immigrationCommonService.validateLcaTitle(dataMigrationDTOJsonCopy.getString("newLcaTitle"), orgLocation.getId());
						if(isValidated == true){
							errorSet.put("newLcaTitle", "LCA Title already existed");
						}
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("noOfPos")) || dataMigrationDTOJsonCopy.getString("noOfPos").equals("null")) {
					errorSet.put("noOfPos", "No.of Positions is missing"); 
					}
			   
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("currClientLocation")) || dataMigrationDTOJsonCopy.getString("currClientLocation").equals("null")) {
					errorSet.put("currClientLocation", "Current Client Location is missing"); 
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("socCode")) || dataMigrationDTOJsonCopy.getString("socCode").equals("null")) {
					errorSet.put("socCode", "Soc Code is missing"); 
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("socTitle")) || dataMigrationDTOJsonCopy.getString("socTitle").equals("null")) {
					errorSet.put("socTitle", "Soc Title is missing"); 
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("socJobTitle")) || dataMigrationDTOJsonCopy.getString("socJobTitle").equals("null")) {
					errorSet.put("socJobTitle", "Job Title is missing"); 
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("newLcaStartDate"))) {
					errorSet.put("newLcaStartDate", "LCA Start Date is missing"); 
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("newLcaEndDate"))) {
					errorSet.put("newLcaEndDate", "LCA End Date is missing"); 
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("wageLevel")) || dataMigrationDTOJsonCopy.getString("wageLevel").equals("null")) {
					errorSet.put("wageLevel", "Wage Level is missing"); 
					}else if(!dataMigrationDTOJsonCopy.getString("wageLevel").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("wageLevel"))) {
						String wageLevel = dataMigrationDTOJsonCopy.getString("wageLevel").trim().toLowerCase();
						if(wageLevel.equalsIgnoreCase("Level 1")) {
							dataMigrationDTOJsonCopy.put("wageLevel", "Level 1");
						}else if(wageLevel.equalsIgnoreCase("Level 2")) {
							dataMigrationDTOJsonCopy.put("wageLevel", "Level 2");
						}else if(wageLevel.equalsIgnoreCase("Level 3")) {
							dataMigrationDTOJsonCopy.put("wageLevel", "Level 3");
						}else if(wageLevel.equalsIgnoreCase("Level 4")) {
							dataMigrationDTOJsonCopy.put("wageLevel", "Level 4");
						}else {
							errorSet.put("wageLevel", dataMigrationDTOJsonCopy.getString("wageLevel").trim()+" - Invalid Wage Level");
						}
					}
			   
			   // Prevailing wage Currence validation
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("prevailWageCurr")) || dataMigrationDTOJsonCopy.getString("prevailWageCurr").equals("null")) {
					errorSet.put("prevailWageCurr", "prevailing Wage Currency is missing"); 
					}else if(!dataMigrationDTOJsonCopy.getString("prevailWageCurr").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("prevailWageCurr"))) {
					String currency = dataMigrationDTOJsonCopy.getString("prevailWageCurr").trim().toLowerCase();
					if(currency.equalsIgnoreCase("INR")) {
						dataMigrationDTOJsonCopy.put("prevailWageCurr", "INR");
					}else if(currency.equalsIgnoreCase("AUD")) {
						dataMigrationDTOJsonCopy.put("prevailWageCurr", "AUD");
					}else if(currency.equalsIgnoreCase("USD")) {
						dataMigrationDTOJsonCopy.put("prevailWageCurr", "USD");
					}else if(currency.equalsIgnoreCase("GBP")) {
						dataMigrationDTOJsonCopy.put("prevailWageCurr", "GBP");
					}else if(currency.equalsIgnoreCase("SGD")) {
						dataMigrationDTOJsonCopy.put("prevailWageCurr", "SGD");
					}else if(currency.equalsIgnoreCase("EUR")) {
						dataMigrationDTOJsonCopy.put("prevailWageCurr", "EUR");
					}else {
						errorSet.put("prevailWageCurr", dataMigrationDTOJsonCopy.getString("prevailWageCurr").trim()+" - Invalid currency Type");
					}
				}
				
			 //PrevailingWage value
				String rateRegEx = "^[0-9\b]+$";
				
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("prevailWageRate")) || dataMigrationDTOJsonCopy.getString("prevailWageRate").equals("null")) {
					errorSet.put("prevailWageRate", "prevailing Wage Rate is missing"); 
					
				  }else if (!dataMigrationDTOJsonCopy.get("prevailWageRate").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("prevailWageRate").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("prevailWageRate").toString().equalsIgnoreCase("undefined")) {
					
					  boolean flag = false;
					String prevailWageRate = dataMigrationDTOJsonCopy.get("prevailWageRate").toString();
					if (prevailWageRate.length() > 0 && prevailWageRate.length() <= 8) {
						flag = prevailWageRate.matches(rateRegEx);
					}
					if(flag == false) {
						errorSet.put("prevailWageRate", "Prevailing Wage Allow Only Integers and length 0-8");
					}
					
				}
				//Prevail Wage RatePer
				
				if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("prevailWageRatePer")) || dataMigrationDTOJsonCopy.getString("prevailWageRate").equals("null")) {
					errorSet.put("prevailWageRate", "prevail Wage Rate is missing"); 
					
				  }else if(!dataMigrationDTOJsonCopy.getString("prevailWageRatePer").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("prevailWageRatePer"))) {
					String ratePer = dataMigrationDTOJsonCopy.getString("prevailWageRatePer").trim().toLowerCase();
					if(ratePer.equalsIgnoreCase("Hourly")) {
						dataMigrationDTOJsonCopy.put("prevailWageRatePer", "Hourly");
					}else if(ratePer.equalsIgnoreCase("Weekly")) {
						dataMigrationDTOJsonCopy.put("prevailWageRatePer", "Weekly");
					}else if(ratePer.equalsIgnoreCase("Bi-Weekly")) {
						dataMigrationDTOJsonCopy.put("prevailWageRatePer", "Bi-Weekly");
					}else if(ratePer.equalsIgnoreCase("Semi-Monthly")) {
						dataMigrationDTOJsonCopy.put("prevailWageRatePer", "Semi-Monthly");
					}else if(ratePer.equalsIgnoreCase("Monthly")) {
						dataMigrationDTOJsonCopy.put("prevailWageRatePer", "Monthly");
					}else if(ratePer.equalsIgnoreCase("Bi-Monthly")) {
						dataMigrationDTOJsonCopy.put("prevailWageRatePer", "Bi-Monthly");
					}else if(ratePer.equalsIgnoreCase("Yearly")) {
						dataMigrationDTOJsonCopy.put("prevailWageRatePer", "Yearly");
					}else {
						errorSet.put("prevailWageRatePer", dataMigrationDTOJsonCopy.getString("prevailWageRatePer").trim()+" Invalid RatePer Type");
					}
				}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("newLcaStartDate")) || dataMigrationDTOJsonCopy.getString("newLcaStartDate").equals("null")) {
					errorSet.put("newLcaStartDate", "Start Date is missing"); 
					}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("newLcaStartDate"))) {
						errorSet.put("newLcaStartDate", "Start Date is invalid date");
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("newLcaEndDate")) || dataMigrationDTOJsonCopy.getString("newLcaEndDate").equals("null")) {
					errorSet.put("newLcaEndDate", "End Date is missing"); 
					}else if(!CommonUtil.isValidDate(dataMigrationDTOJsonCopy.getString("newLcaEndDate"))) {
						errorSet.put("newLcaEndDate", "End Date is invalid date");
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("lcaStatus")) || dataMigrationDTOJsonCopy.getString("lcaStatus").equals("null")) {
					errorSet.put("lcaStatus", "LCA Status is missing"); 
					}else if(!dataMigrationDTOJsonCopy.getString("lcaStatus").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("lcaStatus"))) {
						String lcaStatus = dataMigrationDTOJsonCopy.getString("lcaStatus").trim().toLowerCase();
						if(lcaStatus.equalsIgnoreCase("Initiated")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Initiated");
						 }else if(lcaStatus.equals("in progress") || lcaStatus.equalsIgnoreCase("In-progress") || lcaStatus.equalsIgnoreCase("In progress") || lcaStatus.equalsIgnoreCase("inprogress") || lcaStatus.equalsIgnoreCase("Inprogress")) {
							 dataMigrationDTOJsonCopy.put("lcaStatus", "In progress");
						}else if(lcaStatus.equalsIgnoreCase("Certified")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Certified");
						}else if(lcaStatus.equalsIgnoreCase("Denied")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Denied");
						}else if(lcaStatus.equalsIgnoreCase("Withdrawn")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Withdrawn");
						}else if(lcaStatus.equalsIgnoreCase("Voided")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Voided");
						}else if(lcaStatus.equalsIgnoreCase("Invalidated")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Invalidated");
						}else if(lcaStatus.equalsIgnoreCase("Rejected")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Rejected");
						}else if(lcaStatus.equalsIgnoreCase("Expired")) {
							dataMigrationDTOJsonCopy.put("lcaStatus", "Expired");
						}else {
							errorSet.put("lcaStatus", dataMigrationDTOJsonCopy.getString("lcaStatus").trim()+" Invalid LCA Status");
						}
					}
			   
			   if (StringUtils.isBlank(dataMigrationDTOJsonCopy.getString("newStatus")) || dataMigrationDTOJsonCopy.getString("newStatus").equals("null")) {
					errorSet.put("newStatus", "Status is missing"); 
					}else if(!dataMigrationDTOJsonCopy.getString("newStatus").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("newStatus"))) {
						String status = dataMigrationDTOJsonCopy.getString("newStatus").trim().toLowerCase();
						if(status.equalsIgnoreCase("Active")) {
							dataMigrationDTOJsonCopy.put("status", "Active");
						}else if(status.equalsIgnoreCase("In-Active")) {
							dataMigrationDTOJsonCopy.put("status", "Active");
						}else {
							errorSet.put("status", dataMigrationDTOJsonCopy.getString("status").trim()+" Invalid Status");
						}
					}
			
		}
		
		
		public void dataValidationForAsscoaites(HashMap<String, String> errorSet, JSONObject dataMigrationDTOJsonCopy, User user, OrgLocations orgLocation, String isGlobalAccess) throws Exception {

			//First Name
			if(dataMigrationDTOJsonCopy.getString("firstName").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("firstName"))) {
				errorSet.put("firstName", "First Name field is missing");
			}
			
			
			//Email
			Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			
			if (!dataMigrationDTOJsonCopy.get("email").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("email").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("email").toString().equalsIgnoreCase("undefined")) {
				Matcher matcher = pattern.matcher(dataMigrationDTOJsonCopy.get("email").toString().trim());
				if(matcher.matches() == false) {
					errorSet.put("email", "Invalid Email");
				}
			}
			if(dataMigrationDTOJsonCopy.getString("email").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("email"))) {
				errorSet.put("email", "Email field is missing");
			}else {
				User userEmail = userService.findByEmail(dataMigrationDTOJsonCopy.getString("email").toString().trim());
				if (userEmail != null) {
					errorSet.put("email", "Associate already exist.!");
				}
			}
			

			
			//vendor & Vendor Contact
			if (!dataMigrationDTOJsonCopy.get("vendorcontact").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("vendorcontact").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("vendorcontact").toString().equalsIgnoreCase("undefined")) {
				Matcher matcher = pattern.matcher(dataMigrationDTOJsonCopy.get("vendorcontact").toString().trim());
				if(matcher.matches() == false) {
					errorSet.put("vendorcontact", "Invalid Vendor Contact Email");
				}
				
				if (!dataMigrationDTOJsonCopy.get("vendors").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("vendors").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("vendor").toString().equalsIgnoreCase("undefined")) {
					List<OrgRelations> vendorsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
					Long vendorId = 0l;
					for(OrgRelations rel : vendorsList) {
						if((dataMigrationDTOJsonCopy.get("vendors").toString().equalsIgnoreCase(rel.getLocationId().getOrgLocationName())) || (dataMigrationDTOJsonCopy.get("vendor").toString().equalsIgnoreCase(rel.getResponderLocationId().getOrgLocationName()))) {
							vendorId = rel.getId();
							break;
						}
					}
					
					List<OrgSpoc> vendorContactsList = orgSpocService.getOrgSpocByOrgRelationId(vendorId);
					for(OrgSpoc vendorContacts : vendorContactsList) {
						if(!dataMigrationDTOJsonCopy.get("vendorcontact").toString().equalsIgnoreCase(vendorContacts.getVendorUser().getEmail())) {
							errorSet.put("vendorcontact", "No contact exists with entered email");
						}
					}
					if(vendorContactsList == null || vendorContactsList.size() == 0) {
						errorSet.put("vendorcontact","No Active contacts exists with this vendor");
					}
				}
				
				if(dataMigrationDTOJsonCopy.getString("vendors").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("vendors"))) {
					errorSet.put("vendors", "Vendor field is missing for Vendor");
				}
			}
			
			//Phone number
			String mobileRegex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
			if (!dataMigrationDTOJsonCopy.get("phoneNumber").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("phoneNumber").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("phoneNumber").toString().equalsIgnoreCase("undefined")) {
				boolean flag = false;
				String mobNum = dataMigrationDTOJsonCopy.get("phoneNumber").toString();
				if (mobNum.length() > 9 && mobNum.length() <= 20) {
					flag = mobNum.matches(mobileRegex);
				}
				if(flag == false) {
					errorSet.put("phoneNumber", "Invalid Phone Number");
				}
				
			}
			
			//Extension Number
			if (!dataMigrationDTOJsonCopy.get("extensionNumber").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("extensionNumber").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("extensionNumber").toString().equalsIgnoreCase("undefined")) {
				boolean flag = false;
				String alternatemobNum = dataMigrationDTOJsonCopy.get("extensionNumber").toString();
				if (alternatemobNum.length() > 1 && alternatemobNum.length() <= 5) {
					flag = alternatemobNum.matches(mobileRegex);
				}
				if(flag == false) {
					errorSet.put("extensionNumber", "Invalid Extension Number");
				}
				
			}
			
						
			//Gender
			if(!dataMigrationDTOJsonCopy.getString("genderValue").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("genderValue"))) {
				String gender = dataMigrationDTOJsonCopy.getString("genderValue").trim();
				
				if(Arrays.asList(Constants.GENDER).contains(gender)) {
					dataMigrationDTOJsonCopy.put("genderValue", gender);
				}else {
					errorSet.put("genderValue", dataMigrationDTOJsonCopy.getString("gender").trim()+" Invalid Gender Type");
				}
				
			}
			
			//currency 
			if(!dataMigrationDTOJsonCopy.getString("currentCurrency").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("currentCurrency"))) {
				String currency = dataMigrationDTOJsonCopy.getString("currentCurrency").trim();
				
				if(Arrays.asList(Constants.RATE_CURRENCY).contains(currency)) {
					dataMigrationDTOJsonCopy.put("currentCurrency", currency);
				}else {
					errorSet.put("currentCurrency", dataMigrationDTOJsonCopy.getString("currentCurrency").trim()+" Invalid currency Type");
				}
				
			}
			
			//Rate value
			String rateRegEx = "^[0-9\b]+$";
			if (!dataMigrationDTOJsonCopy.get("rate").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("rate").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("rate").toString().equalsIgnoreCase("undefined")) {
				boolean flag = false;
				String currentRate = dataMigrationDTOJsonCopy.get("rate").toString();
				if (currentRate.length() > 0 && currentRate.length() <= 8) {
					flag = currentRate.matches(rateRegEx);
				}
				if(flag == false) {
					errorSet.put("rate", "Rate Allow Only Integers and length 0-8");
				}
				
			}
			//RatePer
			if(!dataMigrationDTOJsonCopy.getString("ratePer").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("ratePer"))) {
				String ratePer = dataMigrationDTOJsonCopy.getString("ratePer").trim();
				if(Arrays.asList(Constants.RATEPER_LIST).contains(ratePer)) {
					dataMigrationDTOJsonCopy.put("ratePer", ratePer);
				}else {
					errorSet.put("ratePer", dataMigrationDTOJsonCopy.getString("ratePer").trim()+" Invalid RatePer Type");
				}
				
			}
			
			//DateOfJoin
			if(!dataMigrationDTOJsonCopy.getString("dateofJoin").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("dateofJoin"))) {
				try {
					DateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
					Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.getString("dateofJoin"));
					String dateofjoin = formatter.format(date);
					dataMigrationDTOJsonCopy.put("dateofJoin", dateofjoin);
				} catch (Exception e) {
					errorSet.put("dateofJoin", dataMigrationDTOJsonCopy.getString("dateofJoin")+" is invalid Date of join");
				}
			}
			

			//Date of Birth
			if(!dataMigrationDTOJsonCopy.getString("dateOfBirth").equals("null") && StringUtils.isNotEmpty(dataMigrationDTOJsonCopy.getString("dateOfBirth"))) {
				try {
					DateFormat formatter = new SimpleDateFormat("DD-MM-yyyy"); 
					Date date = (Date)formatter.parse(dataMigrationDTOJsonCopy.getString("dateOfBirth"));
					String dateofbirth = formatter.format(date);
					dataMigrationDTOJsonCopy.put("dateOfBirth", dateofbirth);
				} catch (Exception e) {
					errorSet.put("dateOfBirth", dataMigrationDTOJsonCopy.getString("dateofBirth")+" is invalid Date Of Birth");
				}
			}
			
			//Employment type
			if(dataMigrationDTOJsonCopy.getString("designationType").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("designationType"))) {
				errorSet.put("designationType", "Designation Type field is missing");
			}else {
				   if(!dataMigrationDTOJsonCopy.getString("designationType").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("designationType"))) {
					String designationType = dataMigrationDTOJsonCopy.getString("designationType");
					
					if(Arrays.asList(Constants.EMPLOYMENT_TYPES).contains(designationType)) {
						dataMigrationDTOJsonCopy.put("designationType", designationType);
					}else {
						errorSet.put("designationType", dataMigrationDTOJsonCopy.getString("designationType").trim()+" Invalid DesignationType Type");
					}
			  }
			}
			
			//Shift
			if(dataMigrationDTOJsonCopy.getString("shiftId").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("shiftId"))) {
				errorSet.put("shiftId", "Shift field is missing");
			}else {
				Boolean isValidated = shiftNameValidation(dataMigrationDTOJsonCopy.getString("shiftId"),orgLocation.getId());
				if(isValidated == false){
					errorSet.put("shiftId", "Shift is not existed");
				}
			}

			//Designation
			if(dataMigrationDTOJsonCopy.getString("designation").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("designation"))) {
				errorSet.put("designation", "Designation Type field is missing");
			}else {
				Boolean isValidated = isFnameLnameValid(dataMigrationDTOJsonCopy.getString("designation"));
				if(isValidated == false){
					errorSet.put("designation", "should contain above 2 char and should not allowed special charecters");
				}
			}
			
			//Department
			if(!dataMigrationDTOJsonCopy.getString("department").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("department"))) {
	    		Boolean   flag = departmentService.validateDepartmentnameByOrgLocationIdAndDepartmentName(orgLocation.getId(), dataMigrationDTOJsonCopy.getString("department"));
	    		if(flag == false) {
					errorSet.put("department", "Deparment is not existed");
	    		}
			}
			
			//Roles
			if(dataMigrationDTOJsonCopy.getString("roleType").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("roleType"))) {
				errorSet.put("roleType", "Role type field is missing");
			}else {
				Roles roles = rolesService.getRolesByRoleDisplayNameAndOrgLocationId(dataMigrationDTOJsonCopy.getString("roleType"),orgLocation.getId());
				if (null == roles) {
					errorSet.put("roleType", "Role is not existed");
				}
			}
		
			//Grade
			if(!dataMigrationDTOJsonCopy.getString("grade").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("grade"))) {
				
				boolean flag = associateGradesService.isGradeExistsByGradeNameAndOrgLocationId(dataMigrationDTOJsonCopy.getString("grade"), orgLocation.getId());
				
				if(flag == true) {
					dataMigrationDTOJsonCopy.put("grade", dataMigrationDTOJsonCopy.getString("grade"));
				}else {
					errorSet.put("grade", "Invalid Grade");
				}
				
			}
			

			//Manager
			if (!dataMigrationDTOJsonCopy.get("reportingManager").toString().equalsIgnoreCase("") && !dataMigrationDTOJsonCopy.get("reportingManager").toString().equalsIgnoreCase("null") && !dataMigrationDTOJsonCopy.get("email").toString().equalsIgnoreCase("undefined")) {
				Matcher matcher = pattern.matcher(dataMigrationDTOJsonCopy.get("reportingManager").toString().trim());
				if(matcher.matches() == false) {
					errorSet.put("reportingManager", "Invalid Email");
				}
			}
			
			if(dataMigrationDTOJsonCopy.getString("reportingManager").equals("null") || StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("reportingManager"))) {
				errorSet.put("reportingManager", "Email field is missing");
			}else {
				User userEmail = userService.findByEmail(dataMigrationDTOJsonCopy.getString("reportingManager").toString().trim());
				if (userEmail == null) {
					errorSet.put("reportingManager", "Manager is not a current org user");
				}
			}
			
			
			//Day hours
			if(!dataMigrationDTOJsonCopy.getString("dayHours").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("dayHours"))) {
				 String DayHours = dataMigrationDTOJsonCopy.getString("dayHours"); 
				 String result = isDayHourValid(DayHours);
					if(result.split(",")[0] == "false"){
						 errorSet.put("dayHours", "Day Hours should less than 24 Hours");
					} else {
						dataMigrationDTOJsonCopy.put("dayHours", result.split(",")[1]);
					}
			}
			
			//Employee ID
			if(!dataMigrationDTOJsonCopy.getString("employeeId").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("employeeId"))) {
				Boolean flag = employeesService.validateEmployeeId(dataMigrationDTOJsonCopy.getString("employeeId"),orgLocation.getId());
				if(flag == true) {
					  errorSet.put("employeeId", ""+dataMigrationDTOJsonCopy.getString("employeeId")+"is Already Existed");
				}
			}
			
			
			
			// Send Mail to Associate
			if(!dataMigrationDTOJsonCopy.getString("isMailSent").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("isMailSent"))) {
				String sendEmailtype = dataMigrationDTOJsonCopy.getString("isMailSent").trim();
				
				if(Arrays.asList(Constants.YES_NO).contains(sendEmailtype)) {
					dataMigrationDTOJsonCopy.put("isMailSent", sendEmailtype);
				}else {
					errorSet.put("isMailSent"," Invalid Is Mail Sent Type");
				}
				
			}
			
			//Is Global Manager
			if(!dataMigrationDTOJsonCopy.getString("isGlobalAccess").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("isGlobalAccess"))) {
				String globleType = dataMigrationDTOJsonCopy.getString("isGlobalAccess").trim();
				
				if(Arrays.asList(Constants.YES_NO).contains(globleType)) {
					dataMigrationDTOJsonCopy.put("isGlobalAccess", globleType);
				}else {
					errorSet.put("isGlobalAccess"," Invalid Is Global Access Type");
				}
				
			}
			
			
			//Marital Status
			if(!dataMigrationDTOJsonCopy.getString("maritalStatus").equals("null") &&  !StringUtils.isEmpty(dataMigrationDTOJsonCopy.getString("maritalStatus"))) {
				String maritalStatus = dataMigrationDTOJsonCopy.getString("maritalStatus").trim();
				
				if(Arrays.asList(Constants.MARITAL_STATUS).contains(maritalStatus)) {
					dataMigrationDTOJsonCopy.put("maritalStatus", maritalStatus);
				}else {
					errorSet.put("maritalStatus"," Invalid Marital Status");
				}
				
			}
			
			
			
		}
		
		
		 

		private boolean isFnameLnameValid(String name){
			boolean flag = false;
			boolean istabSpace = name.matches("^[A-Za-z][A-Z a-z 0-9]*$");
			if(istabSpace==false){
				flag = false;
			}else{
				if(name.length() > 2 && name.length() <= 200){
					flag = true;
				}
		}
			
			return flag;
		}
		
		private boolean shiftNameValidation(String shiftName, Long loginLoationId) {
			List<Shift> shiftList  = shiftService.getShiftList(loginLoationId);
			Shift shift = shiftList.stream().filter(x -> shiftName.equalsIgnoreCase(x.getShiftName())).findAny().orElse(null); 
			 boolean flag = false;
			 if(shift != null)
				 flag= true;
			return flag;
		}
		
		//validate Email For Excel Written by Laxman
		private static boolean isValid(String email) {
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

			Pattern pat = Pattern.compile(emailRegex);
			if (email == null)
				return false;
			return pat.matcher(email).matches();
		}

	private String isDayHourValid(String dayHours) {
		Pattern pattern;
		Matcher matcher;
		String TIME12HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
		pattern = Pattern.compile(TIME12HOURS_PATTERN);
		int hours = 0;
		int mins = 0;
		if (dayHours.contains(":")) {
			hours = Integer.parseInt(dayHours.split(":")[0]);
			mins = Integer.parseInt(dayHours.split(":")[1]);
		} else {
			hours = Integer.parseInt(dayHours);
		}
		String finalTime = (hours < 10 ? "0" + hours : hours) + ":" + (mins < 10 ? "0" + mins : mins);
		matcher = pattern.matcher(finalTime);
		boolean result = matcher.matches();
		return result + "," + finalTime;
	}

		
	private String[] getExperienceYearsAndMonths(String experience) {
	    
		return experience.split("[\\p{Punct}\\s]+");
		
	}	
	
	private boolean isMobileNumberValid(String mobileNmber)
	{
		String regex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
		boolean flag;
		String mobNum = mobileNmber.trim();
		if (mobNum.length() > 9 && mobNum.length() <= 20) {
			flag = mobNum.matches(regex);
		}
		else {
			flag = false;
		}
		return flag;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void dataValidationForCandidateBulkImports(HashMap< String, String> errorSet, org.json.simple.JSONObject dataMigrationDTOJsonCopy,OrgLocations orgLocation,List<OrgRelations> vendorsList) throws Exception
	{
		
		if(dataMigrationDTOJsonCopy.get("email") != null) {
			Long candidateId = candidateService.getCandidateIdByEmailAndOrgLocationId(dataMigrationDTOJsonCopy.get("email").toString(), orgLocation.getId());
			if (candidateId != null) {
				errorSet.put("email", "Candidate already existed");
			}else {
				
				Matcher matcher = null;
				Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
				
				matcher = pattern.matcher(dataMigrationDTOJsonCopy.get("email").toString().trim());
				if(matcher.matches() == false) {
					errorSet.put("email", "Invalid Email");
				}
				
				if(dataMigrationDTOJsonCopy.get("firstName") == null) {
					errorSet.put("firstName", "First Name field is missing");
				}
				
				
				String mobileRegex = "([\\.\\-\\s+\\/()]*[0-9]*\\.?[\\.\\-\\s+\\/()]*){8,15}";
				if (dataMigrationDTOJsonCopy.get("mobilePhone") != null) {
					boolean flag = false;
					String mobNum = dataMigrationDTOJsonCopy.get("mobilePhone").toString();
					if (mobNum.length() > 9 && mobNum.length() <= 20) {
						flag = mobNum.matches(mobileRegex);
					}
					if(flag == false) {
						errorSet.put("mobilePhone", "Invalid Mobile Phone");
					}
					
				}
				
				if (dataMigrationDTOJsonCopy.get("alternateMobilePhone") != null) {
					boolean flag = false;
					String alternatemobNum = dataMigrationDTOJsonCopy.get("alternateMobilePhone").toString();
					if (alternatemobNum.length() > 9 && alternatemobNum.length() <= 20) {
						flag = alternatemobNum.matches(mobileRegex);
					}
					if(flag == false) {
						errorSet.put("alternateMobilePhone", "Invalid Alternate Mobile Phone");
					}
					
				}
				
				String rateRegEx = "^[0-9\b]+$";
				if (dataMigrationDTOJsonCopy.get("expectedRate") != null) {
					boolean flag = false;
					String expectedRate = dataMigrationDTOJsonCopy.get("expectedRate").toString();
					if (expectedRate.length() > 0 && expectedRate.length() <= 8) {
						flag = expectedRate.matches(rateRegEx);
					}
					if(flag == false) {
						errorSet.put("expectedRate", "Expected Rate Allow Only Integers and length 0-8");
					}
					
				}
				
				if (dataMigrationDTOJsonCopy.get("currentRate") != null) {
					boolean flag = false;
					String currentRate = dataMigrationDTOJsonCopy.get("currentRate").toString();
					if (currentRate.length() > 0 && currentRate.length() <= 8) {
						flag = currentRate.matches(rateRegEx);
					}
					if(flag == false) {
						errorSet.put("currentRate", "Current Rate Allow Only Integers and length 0-8");
					}
					
				}
				
				//pan,aadhar number validation
				if (orgLocation.getCountry().equalsIgnoreCase("India")) {
					String panNumberRegEx = "^([A-Z]){5}([0-9]){4}([A-Z]){1}?$";
					if (dataMigrationDTOJsonCopy.get("panNumber") != null) {
						boolean flag = false;
						String panNumber = dataMigrationDTOJsonCopy.get("panNumber").toString();
						if (panNumber.length() == 10) {
							flag = panNumber.matches(panNumberRegEx);
							Candidate candidate=candidateService.getCandidateDetailsByPanNumberAndId(panNumber, orgLocation.getId());
						    if(candidate != null) {
						    	errorSet.put("panNumber", "Duplicate Pan Number");
						    }
						}
						if(flag == false) {
							errorSet.put("panNumber", "Invalid Pan Number ");
						}
					}
				}
				
				if (orgLocation.getCountry().equalsIgnoreCase("India")) {
				String aadharNoRegEx = "^\\d{4}\\s?\\d{4}\\s?\\d{4}$";
					if (dataMigrationDTOJsonCopy.get("aadharNumber") != null) {
						boolean flag = false;
						String aadharNo = dataMigrationDTOJsonCopy.get("aadharNumber").toString();
						if (aadharNo.length() == 12) {
							flag = aadharNo.matches(aadharNoRegEx);
							Candidate candidate=candidateService.getCandidateDetailsByAadharNoAndId(aadharNo, orgLocation.getId());
						    if(candidate != null) {
						    	errorSet.put("aadharNumber", "Duplicate Aadhar Number");
						    }
						}
						if(flag == false) {
							errorSet.put("aadharNumber", "Invalid Aadhar Number ");
						}
					}
				}
				
				if (orgLocation.getCountry().equalsIgnoreCase("United States")) {
					
					String ssnNoRegEx = "^(\\d{3}-?\\d{2}-?\\d{4}|XXX-XX-XXXX)$";
					if (dataMigrationDTOJsonCopy.get("ssnNumber") != null) {
						boolean flag = false;
						String ssnNo = dataMigrationDTOJsonCopy.get("ssnNumber").toString().replaceAll("-", "");
						if (ssnNo.length() == 9) {
							flag = ssnNo.matches(ssnNoRegEx);
							Candidate candidate=candidateService.getCandidateDetailsBySsnNoAndId(ssnNo, orgLocation.getId());
						    if(candidate != null) {
						    	errorSet.put("ssnNumber", "Duplicate SSN Number");
						    }
						}
						if(flag == false) {
							errorSet.put("ssnNumber", "Invalid SSN Number ");
						}
					}
				
				}
				
			}
		}else {
			errorSet.put("email", "Email field is missing");
		}
		
		
		
	}
		
	}