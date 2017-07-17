package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class ReportRestController {

	private static final Logger logger = LoggerFactory.getLogger(ReportRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	EmployeesService employeesService;

	@Autowired
	UserService userService;

	@Autowired
	ProjectsService projectsService;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	OrgSpocService orgSpocService;

	@Autowired
	RequirementService requirementService;

	@Autowired
	TeamService teamService;

	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService;
    
	@Autowired
	CandidateService candidateService;

	@Autowired
	RolesService rolesService;

	@Autowired
	TabsService tabsService;
	
	@Autowired
	OrgRelationsService orgRelationService;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementReportService requirementReportService;
	
	@Autowired
	TimeZoneService timeZoneService;

	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;
	
	@Autowired
	ClientVendorContactsIndexHelper clientVendorContactsIndexHelper;
	
	
	/**
	 * To get All the Users by filtering
	 * 
	 * @param request
	 * @param userRole
	 * @param searchByName
	 * @param userState
	 * @param loginUserRole
	 * @return
	 */

	@RequestMapping(value = "/getAllUsersByOrgLocationsId")
	public @ResponseBody String getAllUsersByOrgLocationsId(@RequestParam(value="timePeriod", required=false) String timePeriod,@RequestParam(value="customStartDate", required=false) String customStartDate,@RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {

	    String resultJson;
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    
		 Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 String filterDate = null;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(timePeriod)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != timePeriod && !"".equals(timePeriod)){
				if("today".equals(timePeriod)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("yesterday".equals(timePeriod)){
					DateTime yesterDayZoned = zoned.minusDays(1);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
					
				}else if("week".equals(timePeriod)){
					DateTime yesterDayZoned = zoned.minusDays(7);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twoweeks".equals(timePeriod)){
					DateTime yesterDayZoned = zoned.minusDays(14);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("month".equals(timePeriod)){
					DateTime yesterDayZoned = zoned.minusDays(30);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if("twomonths".equals(timePeriod)){
					DateTime yesterDayZoned = zoned.minusDays(60);
					String yesterDayDate = outputFormatter.print(yesterDayZoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else{
					 if("custom".equalsIgnoreCase(timePeriod) && !"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
						DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
						Date startTime =  commonUtil.getDateFormat(customStartDate);
						Date endTime =  commonUtil.getDateFormat(customEndDate);
						String customStartTimeStr = null;
						String customEndTimeStr = null;
						if(null !=startTime){
						 customStartTimeStr = TZ_DF_CSD.format(startTime);  
						}
						
						if(null != endTime){
						 customEndTimeStr = TZ_DF_CED.format(endTime);  
						}
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
						startDateUTC = TZ_DF_TS.parse(startDateString);
						endDateUTC = TZ_DF_TS.parse(endDateString);
					 }
					
				}
			}

			List<User> usersList = userService.getUsersListByOrglocationId(orgLocations.getId());
			List<User> orgUsersList = new ArrayList<User>(); 
			StringBuilder userName = new StringBuilder();
			if(null != usersList){
				for(User user : usersList){
					UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
					user.setUserState(userRole.getUserState());
					user.setRole(userRole.getRoles().getDisplayName());
					if(!isNullOrEmpty(userRole.getUser().getLastName())){
						userName = userName.append(userRole.getUser().getFirstName()).append(" ").append(userRole.getUser().getLastName());
						userRole.setUserName(userName.toString());
						
					}else{
						userRole.setUserName(userRole.getUser().getFirstName());
					}
					
					long totalRequirements = requirementService.getCountOfAllRequirementsByCreatedUserAndOrglocationId(orgLocations.getId(), user, startDateUTC, endDateUTC);
					user.setUserRequirementsCount(totalRequirements);
					
					long totalCandidates = candidateService.getCountOfAllCandidatesByCreatedUserAndOrglocationId(orgLocations.getId(), user, startDateUTC, endDateUTC);
					user.setUserCandidatesCount(totalCandidates);
					
					long totalApplicants = requirementSubmissionHistoryService.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocations.getId(), user,"SUBMISSION", startDateUTC, endDateUTC);
					user.setUserApplicantsCount(totalApplicants);
					
					long totalShortlistedApplicants = requirementSubmissionHistoryService.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocations.getId(), user,"SHORTLIST", startDateUTC, endDateUTC);
					user.setUserShortlisteApplicantsCount(totalShortlistedApplicants);
					
					long totalSubmittedApplicants = requirementSubmissionHistoryService.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocations.getId(), user,"SUBMIT_TO_CLIENT", startDateUTC, endDateUTC);
					user.setUserSubmittedApplicantsCount(totalSubmittedApplicants);
					
					long totalInterviewedApplicants = requirementSubmissionHistoryService.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocations.getId(), user, "INTERVIEW", startDateUTC, endDateUTC);
					user.setUserInterviewedApplicantsCount(totalInterviewedApplicants);
					
					long totalOfferedApplicants = requirementSubmissionHistoryService.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocations.getId(), user, "OFFERMADE", startDateUTC, endDateUTC);
					user.setUserOfferedApplicantsCount(totalOfferedApplicants);
					
					long totalRejectedApplicants = requirementSubmissionHistoryService.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocations.getId(), user, "rejected", startDateUTC, endDateUTC);
					user.setUserRejectedApplicantsCount(totalRejectedApplicants);
					
					long userApplicantsBackOutCount = requirementSubmissionHistoryService.getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(orgLocations.getId(), user, "BACKOUT", startDateUTC, endDateUTC);
					user.setUserApplicantsBackOutCount(userApplicantsBackOutCount);
					
					
					orgUsersList.add(user);
				}
			}
		    resultJson = commonUtil.toJSON(orgUsersList);
			return resultJson;
		}

	

	@RequestMapping(value = "/getVendorRequirementReports")
	public @ResponseBody String getVendorRequirementReports(@RequestParam(value="reportType", required=false) String reportType,
			@RequestParam(value="customStartDate", required=false) String customStartDate, @RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {
		
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    
		 Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)&&!"".equalsIgnoreCase(reportType)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
				}else{
					 if(!"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
						DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
						Date startTime =  commonUtil.getDateFormat(customStartDate);
						Date endTime =  commonUtil.getDateFormat(customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
						startDateUTC = TZ_DF_TS.parse(startDateString);
						endDateUTC = TZ_DF_TS.parse(endDateString);
					 }
					
				}
			}
		
	    List<OrgRelations> vendorslist = orgRelationService.getAllVendorsListByOrgLocationId(orgLocations.getId());
	    
	    for(OrgRelations orgrel : vendorslist){
	    	int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
			int backoutSum = 0;
			long vendorLocationId = 0L;
	    	List<Requirement> reqList = requirementService.getAllReleasedJobsForVendorBasedOnStartAndEndDates(orgLocations.getId(), orgrel.getId(),startDateUTC,endDateUTC);
	    	if(orgrel.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
	    		vendorLocationId = orgrel.getLocationId().getId();
			}if(orgrel.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				vendorLocationId = orgrel.getResponderLocationId().getId();
			}
	    	List<RequirementSubmission> reqSubmissionList = requirementSubmissionService.getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(orgLocations.getId(),vendorLocationId,startDateUTC,endDateUTC);
	    	orgrel.setSubmittedCount(reqSubmissionList.size());
	    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
		    	for(RequirementSubmission reqSub : reqSubmissionList){
					int applicantsCount = 0;
					int requestSentCount = 0;
					int shortlistedCount = 0;
					int submitToClientCount = 0;
					int interviewsCount = 0;
					int offeredCount = 0;
					int rejectedCount = 0;
					int backoutCount = 0;
					List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
					if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
						for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
							if("submission".equalsIgnoreCase(rsh.getToStage())){
								applicantsCount++;
							}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
								shortlistedCount++;
							}else if("interview".equalsIgnoreCase(rsh.getToStage())){
								interviewsCount++;
							}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
								offeredCount++;
							}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
								requestSentCount++;
							}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
								submitToClientCount++;
							}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
								rejectedCount++;
							}else if("backout".equalsIgnoreCase(rsh.getToStage())){
								backoutCount++;
							}
						}
						 applicantsSum = applicantsSum+applicantsCount;
						 requestSentSum = requestSentSum+requestSentCount;
						 shortlistedSum = shortlistedSum+shortlistedCount;
						 submitToClientSum = submitToClientSum+submitToClientCount;
						 interviewsSum = interviewsSum+interviewsCount;
						 offeredSum = offeredSum+offeredCount;
						 rejectedSum = rejectedSum+rejectedCount;
						 backoutSum = backoutSum + backoutCount;
					}
				}
	    	}
	    	
	    	orgrel.setReqReleasedCount(reqList.size());
	    	if(orgrel.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
	    		orgrel.setVendorName(orgrel.getLocationId().getOrgLocationName());
			}if(orgrel.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
				orgrel.setVendorName(orgrel.getResponderLocationId().getOrgLocationName());
			}
	    	orgrel.setShortlistedCount(shortlistedSum);
	    	orgrel.setInterviewedCount(interviewsSum);
	    	orgrel.setOfferedCount(offeredSum);
	    	orgrel.setRejectedCount(rejectedSum);
	    	orgrel.setBackoutCount(backoutSum);
	    }
	    
		return commonUtil.toJSON(vendorslist);
	}
	
	
	@RequestMapping(value = "/getVendorSpocsReports")
	public @ResponseBody String getVendorSpocsReports(@RequestParam(value="reportType", required=false) String reportType,@RequestParam(value="relationId", required=false) Long relationId,
			@RequestParam(value="customStartDate", required=false) String customStartDate, @RequestParam(value="customEndDate", required=false) String customEndDate,
			HttpServletRequest request) throws Exception {
		
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	    
	    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
		 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		 Date endDateUTC =null ;
		 Date startDateUTC = null;
		 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)&&!"".equalsIgnoreCase(reportType)){
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endDateUTC = TZ_DF_TS.parse(endDateString);
		 }
			
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					String startDate = outputFormatter.print(zoned);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
					startDateUTC = TZ_DF_TS.parse(startDateString);
				}else if(Constants.YESTER_DAY.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 1);
				}else if(Constants.ONE_WEEK.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 7);
				}else if(Constants.TWO_WEEKS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 14);
				}else if(Constants.ONE_MONTH.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 30);
				}else if(Constants.TWO_MONTHS.equals(reportType)){
					startDateUTC = getPreviousDate(zoned, outputFormatter, TZ_DF_TS, timezone, 60);
				}else{
					 if(!"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
						DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
						DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
						Date startTime =  commonUtil.getDateFormat(customStartDate);
						Date endTime =  commonUtil.getDateFormat(customEndDate);
						String customStartTimeStr = TZ_DF_CSD.format(startTime);  
						String customEndTimeStr = TZ_DF_CED.format(endTime);  
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
						startDateUTC = TZ_DF_TS.parse(startDateString);
						endDateUTC = TZ_DF_TS.parse(endDateString);
					 }
					
				}
			}
	    
		List<OrgSpoc> orgSpocsList  = orgSpocService.getAllSpocsByOrgRelationId(relationId, loginUser.getId(),orgLocations.getId(),"Vendor");
		
//	    List<OrgSpoc> vendorSpocsList = orgSpocService.getSpoclistByDomain(relationId,"");
	    for(OrgSpoc spoc : orgSpocsList){
	    	int applicantsSum = 0;
			int requestSentSum = 0;
			int shortlistedSum = 0;
			int submitToClientSum = 0;
			int interviewsSum = 0;
			int offeredSum = 0;
			int rejectedSum = 0;
			int backoutSum = 0;
	    	List<Requirement> reqList = requirementService.getTotalRequirementsReleasedToSpoc(relationId,spoc.getId(),startDateUTC,endDateUTC);
	    	spoc.setReqReleasedCount(reqList.size());
	    	   List<RequirementSubmission> reqSubmissionList = requirementSubmissionService.getVendorSpocSubmissions(spoc.getVendorUser().getUserCode(),orgLocations.getId(),startDateUTC,endDateUTC);
	    	   spoc.setSubmittedCount(reqSubmissionList.size());
		    	if(null != reqSubmissionList && reqSubmissionList.size() > 0){
			    	for(RequirementSubmission reqSub : reqSubmissionList){
						int applicantsCount = 0;
						int requestSentCount = 0;
						int shortlistedCount = 0;
						int submitToClientCount = 0;
						int interviewsCount = 0;
						int offeredCount = 0;
						int rejectedCount = 0;
						int backoutCount = 0;
						List<RequirementSubmissionHistory> requirementSubmissionHistoryList = requirementReportService.getApplicantsCountsByDateRange(reqSub.getId(),startDateUTC,endDateUTC);
						if(null != requirementSubmissionHistoryList && requirementSubmissionHistoryList.size() >0){
							for(RequirementSubmissionHistory rsh : requirementSubmissionHistoryList){
								if("submission".equalsIgnoreCase(rsh.getToStage())){
									applicantsCount++;
								}else if("shortList".equalsIgnoreCase(rsh.getToStage())){
									shortlistedCount++;
								}else if("interview".equalsIgnoreCase(rsh.getToStage())){
									interviewsCount++;
								}else if("offermade".equalsIgnoreCase(rsh.getToStage())){
									offeredCount++;
								}else if("request_sent".equalsIgnoreCase(rsh.getToStage())){
									requestSentCount++;
								}else if("submit_to_client".equalsIgnoreCase(rsh.getToStage())){
									submitToClientCount++;
								}else if("rejected".equalsIgnoreCase(rsh.getToStage())){
									rejectedCount++;
								}else if("backout".equalsIgnoreCase(rsh.getToStage())){
									backoutCount++;
								}
							}
							 applicantsSum = applicantsSum+applicantsCount;
							 requestSentSum = requestSentSum+requestSentCount;
							 shortlistedSum = shortlistedSum+shortlistedCount;
							 submitToClientSum = submitToClientSum+submitToClientCount;
							 interviewsSum = interviewsSum+interviewsCount;
							 offeredSum = offeredSum+offeredCount;
							 rejectedSum = rejectedSum+rejectedCount;
							 backoutSum = backoutSum + backoutCount;
						}
					}
		    	}
		    	if(null != spoc.getVendorUser().getLastName()){
		    		StringBuilder sb = new StringBuilder();
		    		spoc.setSpocName(sb.append(spoc.getVendorUser().getFirstName()).append(" ").append(spoc.getVendorUser().getLastName()).toString());
		    	}else{
		    		spoc.setSpocName(spoc.getVendorUser().getFirstName());
		    	}
		    	spoc.setShortlistedCount(shortlistedSum);
		    	spoc.setInterviewedCount(interviewsSum);
		    	spoc.setOfferedCount(offeredSum);
		    	spoc.setRejectedCount(rejectedSum);
		    	spoc.setBackoutCount(backoutSum);
	    }
	    
		return commonUtil.toJSON(orgSpocsList);
	}
	
	
	private Date getPreviousDate(DateTime zoned, DateTimeFormatter outputFormatter, DateFormat TZ_DF_TS, Timezone timezone, int numberOfDays) throws ParseException
	{
		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}
	
	
	private String toJSON(Object obj) {
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
			}
		});
		return xstream.toXML(obj);
	}

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}
