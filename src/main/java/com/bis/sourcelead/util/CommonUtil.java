package com.bis.sourcelead.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * This is the commonutil for the certain common methods used throughout the application
 * @author: Naresh Bolisetty
 * 
 */
/**
 * @author BIS104
 *
 */
@Component
public class CommonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private ProjectMembersHistoryService projectMembersHistoryService;
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	
	private UserRoleService userRoleService;
	
	@Autowired
	private ProjectTasksService projectTasksService;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	private RequirementService requirementService;
	
	
	@Autowired
    private SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;
	
	
	/**
	 * This method is used to store pre-defined sourcelead tasks for the particular project
	 * @author: Naresh Bolisetty
	 * 
	 */
	
	public static Date currentDate() {
        return new Date();
    }
	
	/**
	 * Converting Org based date format as string
	 * @param orgDateFormat
	 * @param date
	 * @return String
	 */
	public String dateStrFromOrgDateFormatter(String orgDateFormat, Date date){
		String dateStr = null;
		if(date != null && StringUtils.isNotEmpty(orgDateFormat)){
			SimpleDateFormat df = new SimpleDateFormat(orgDateFormat);
			dateStr = df.format(date);
		}
		return dateStr;
	}
	
	/**
	 * Elk date format
	 * @param dateString
	 * @return
	 */
	public String elkDateFormatter(Date date){
		String dateStr = null;
		if(date != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = df.format(date);
		}
		return dateStr;
	}
	
	/**
	 *  Converting Org based date format as Util.Date
	 * @param orgDateFormat
	 * @param dateStr
	 * @return Date
	 */
	public Date orgLocationDateFormat(String orgDateFormat, String dateStr){
		DateFormat df = new SimpleDateFormat(orgDateFormat);
		Date date = null;
		 try {
		        date = df.parse(dateStr);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
			return date;
	}
	
	
	public  Date  dateFromElk(String dateString){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
	    Date date = null;
	    try {
	        date = df.parse(dateString);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return date;
	}
	
	
	public void storeSourceleadTasks(User user,Projects projects) throws Exception{
		Collection<ProjectTasks> tasksList = projectTasksService.getAllSourceleadTasks();
		if(tasksList != null && !tasksList.isEmpty()){
			for(ProjectTasks task :tasksList){
				task.setProjectId(projects);
				task.setCreatedBy(user.getCreatedBy());
				task.setCreatedDate(new Date());
				task.setUpdatedDate(new Date());
				task.setUpdatedBy(user.getCreatedBy());
			}
		}
		projectTasksService.saveAll(tasksList);
	}
	
	/**
	 * This method is used to in-activate timesheet submissions based on projectmemberId and terminationdate
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(Long memberId,Date terminationDate){
		List<TimesheetSubmissions> timesheetSubmissionsList;
		try {
			timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(memberId,terminationDate);
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				for(TimesheetSubmissions ts :timesheetSubmissionsList){
					ts.setStatus(Constants.MEMBER_IN_ACTIVE);
				}
			}
			timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	/**
	 * This method is used to activate timesheet submissions based on projectmemberId and terminationdate
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void activateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(Long memberId,Date terminationDate){
		List<TimesheetSubmissions> timesheetSubmissionsList;
		try {
			timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissionsGreaterThanProjectMemberTerminatedDateAndMemberId(memberId,terminationDate);
			if(timesheetSubmissionsList != null && !timesheetSubmissionsList.isEmpty()){
				for(TimesheetSubmissions ts :timesheetSubmissionsList){
					ts.setStatus(Constants.MEMBER_ACTIVE);
				}
			}
			timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	/**
	 * This method is used to terminate project member by project memberId and way of termination
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void terminateProjectMemberByProjectMemberId(Long projectMemberId,Primary isManuallyTerminated){
		try {
			ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMemberId);
			 if(pmh == null){
				 pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMemberId);
			 }
			pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
			pmh = projectMembersHistoryService.updateProjectMembersHistory(pmh);
			
			/**
			 * commented by Prasad salina
			 */
			inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId, pmh.getProjectTerminationDate());
			
			ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
			pm.setStatus(Constants.MEMBER_IN_ACTIVE);
			pm.setIsManuallyTerminated(isManuallyTerminated);
			projectMembersService.updateProjectMember(pm);
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to re-activate project member by project memberId and way of termination
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void reactivateProjectMemberByProjectMemberId(Long projectMemberId){
		try {
			ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMemberId);
			if(pmh != null){
				pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
				pmh = projectMembersHistoryService.updateProjectMembersHistory(pmh);
				
				activateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMemberId, pmh.getProjectTerminationDate());
				
				ProjectMembers pm = projectMembersService.getProjectMemberById(projectMemberId);
				pm.setStatus(Constants.MEMBER_ACTIVE);
				pm.setIsManuallyTerminated(null);
				projectMembersService.updateProjectMember(pm);
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to newly added admin as a level2 manager for all existing projectmembers
	 * @author: Naresh Bolisetty
	 * @throws Exception 
	 * 
	 */
	 public void addAdminAsSecondLevelManager(Long locationId,User adminUser,User loginUser) throws Exception{
		
		List<Projects> projectsList = projectsService.getProjectByOrgLocationsId(locationId);
        if(projectsList != null && !projectsList.isEmpty()){
        	for(Projects projects :projectsList){
        		if(StringUtils.isNotEmpty(projects.getStatus()) && projects.getStatus().equalsIgnoreCase(Project_Status.COMPLETED.getValue()))
        			continue;
        		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		    	pah.setProjectId(projects);
		    	pah.setApproverUserId(adminUser);
		    	pah.setLevel(2);
		    	pah.setCreatedBy(loginUser.getUserCode());
		    	pah.setCreatedDate(currentDate());
		    	pah.setUpdatedBy(loginUser.getUserCode());
		    	pah.setUpdatedDate(currentDate());
		    	pah = projectApproverHierarchyService.saveProjectApproverHierarchy(pah);
		    	List<SubmissionApproverHierarchy> sahList = submissionApproverHierarchyService.getSubmissionApproverHierarchyByApproverUserId(loginUser.getId());
		    	List<SubmissionApproverHierarchy> freshSahList = new ArrayList<SubmissionApproverHierarchy>();
		    	if(sahList != null && !sahList.isEmpty()){
		    		for(SubmissionApproverHierarchy sah :sahList){
		    			sah.setId(null);
		    			sah.setHeirarchyId(pah);
		    			freshSahList.add(sah);
		    		}
		    		submissionApproverHierarchyService.saveAllSubmissionApproverHierarchy(freshSahList);
		    	}
        	}
        }
	}
	
	/**
	 * This method is used to terminate all projectmembers whose project is closed
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void terminateAllProjectMembersByUser(User user){
		List<ProjectMembers> projectMembers;
		try {
			projectMembers = projectMembersService.getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			if(projectMembers != null && !projectMembers.isEmpty()){
				for(ProjectMembers projectMember :projectMembers){
					terminateProjectMemberByProjectMemberId(projectMember.getId(),Primary.NO);
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	/**
	 * This method is used to terminate all projectmembers whose project is closed
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void terminateAllProjectMembers(){
		List<ProjectMembers> projectMembers;
		try {
			projectMembers = projectMembersService.getAllTerminatedProjectMembersIdsByTimesheet();
			if(projectMembers != null && !projectMembers.isEmpty()){
				for(ProjectMembers projectMember :projectMembers){
					terminateProjectMemberByProjectMemberId(projectMember.getId(),Primary.NO);
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	
	/**
	 * This method is used to terminate project whose endDate is greaterthan current date
	 * @author: Naresh Bolisetty
	 * 
	 */
	public Projects terminateProject(Projects project) throws Exception{
		Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(project.getId());
		if(projectMembersList != null && !projectMembersList.isEmpty()){
			 for(ProjectMembers pojectMember : projectMembersList){
				ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pojectMember.getId());
				if(pmh == null){
					pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(pojectMember.getId());
				}
				Date currentDate = new  Date();
				Date terminationDate = null;
				if(pmh.getProjectTerminationDate() != null){
					terminationDate = pmh.getProjectTerminationDate();
				}
				else{
					terminationDate = pmh.getProjectAssignedDate();
				}
				if(currentDate.after(terminationDate)){
					pmh.setProjectTerminationDate(project.getEndDate());
					pmh = projectMembersHistoryService.updateProjectMembersHistory(pmh);
				}
				terminateProjectMemberByProjectMemberId(pojectMember.getId(),Primary.NO);
			}
		}
		project.setProjectStatus(Project_Status.COMPLETED);
		return project;
	}
	
	
	/**
	 * This method is used to Terminate Project Members whose assigned date is after project end date
	 * @author: Naresh Bolisetty
	 * @throws Exception 
	 * 
	 */
 	public void terminateProjectMembersWhoseAssignedDateOrTerminatedisGreaterThanProjectEndDate(Projects project,User user) throws Exception{
 		if(project.getEndDate() != null){
	 		List<ProjectMembers> pmList =  projectMembersService.getAllProjectMembersAssignedOrTerminateDateisGreaterThanProjectEndDate(project.getId());
		   	   for(ProjectMembers projectMember :pmList){
		   		ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMember.getId());
				 if(pmh == null)  {
					  pmh = projectMembersHistoryService.getProjectMembersRecentClosedByProjectMemberId(projectMember.getId());
				 }
				 Date terminatedDate = null;
		   		   if(pmh != null && pmh.getProjectAssignedDate().after(project.getEndDate()) || pmh.getProjectAssignedDate().equals(project.getEndDate())){
				   			 projectMember.setStatus(Constants.MEMBER_IN_ACTIVE);
				   			 projectMember.setIsManuallyTerminated(Primary.NO);
				   			 projectMember.setUpdatedDate(new Date());
					    		 projectMember.setUpdatedBy(user.getUserCode());
					    		 HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>();
							  	 
								  if(pmh != null){
									  pmh.setProjectTerminationDate(pmh.getProjectAssignedDate());
										Calendar terminateDateCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
										terminateDateCal.setLenient(false);
										terminateDateCal.setTime(pmh.getProjectTerminationDate());
										Calendar terminatedDateMonday = CalendarUtil.getPreviousWeekDayByCalendar(terminateDateCal, "MON",projectMember.getProjects().getOrgLocationsId().getDateFormat());
										terminatedDate = terminatedDateMonday.getTime();
									 
									  pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
									  pmh.setUpdatedDate(new Date());
									  pmh.setUpdatedBy(user.getUserCode());
									  memberHistoryList.add(pmh);
									  projectMember.setMemberHistoryList(memberHistoryList);
								  }
								  projectMember = projectMembersService.saveProjectMember(projectMember);
				   		 }
				   		 else{
				   			 HashSet<ProjectMembersHistory> memberHistoryList = new HashSet<ProjectMembersHistory>();
								  if(pmh != null){
									  pmh.setProjectTerminationDate(project.getEndDate());
									  terminatedDate = pmh.getProjectTerminationDate();
									  pmh.setUpdatedDate(new Date());
									  pmh.setUpdatedBy(user.getUserCode());
									  memberHistoryList.add(pmh);
									  projectMember.setMemberHistoryList(memberHistoryList);
								  }
								  projectMember = projectMembersService.saveProjectMember(projectMember);
				   		 }
		   		
				   	
				   		inactivateTimesheetSubmissionsBasedOnMemberIdAndMemberTerminationDate(projectMember.getId(),terminatedDate);
		   	   }
 		 }
	}
	
	
	/**
	 * This method is used to storeTimesheetSubmissionRecords based on projectMemberAssignDate and projectMemberEndDate(optional)
	 * @author: Naresh Bolisetty
	 * 
	 */
	/*public void storeTimesheetSubmissionRecords(User user){
		try {
			
			*//**
			 * Getting List Of all Projectmembers based on login user who is approver/employee to that particular project
			 *  who didn't have 12 weeks prior/post submission records in DB
			 * 
			 *//*
			List<ProjectMembers> projectMembers =  projectMembersService.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			
			if(projectMembers != null && !projectMembers.isEmpty()){
				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				for(ProjectMembers projectMember :projectMembers){
					
					ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMember.getId());
					
					*//**
					 * Getting startDate of coming week from the existing submission records
					 *
					 *//*
					Date nextMonday = timesheetSubmissionsService.getNextMondayByProjectMemberId(projectMember.getId());
					
					*//**
					 * Calculating startDate for the submission records of this particular projectmember
					*//*
					Date startMondayDate = null;
					if(nextMonday != null){
						startMondayDate =  nextMonday;
					}
					else{
						
						*//**
						 * If he didn't have submission records previous for this project,then ProjectAssignedDate is taken as startDate
						*//*
						startMondayDate = pmh.getProjectAssignedDate();
						
						*//**
						 * Calculating last 12th week monday date 
						*//*
						Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						currentCal.setLenient(false);
						currentCal.setTime(new Date());
						Calendar currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(currentCal, "MON");
						Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(currentMonday,-84);
						Date previousMondayDate = previousMonday.getTime();
						*//**
						 * Checking whether projectAssignDate lesserthan last 12th week Monday date,
						 * then last 12th week Monday date is taken as startdate
						*//*
				        if(startMondayDate.before(previousMondayDate)){
				    	   startMondayDate = previousMondayDate;
				        }
					}
								
					Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(startMondayDate);
					Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
					
					
					
					*//**
					 * Calculating endDate for the submission records of this particular projectmember
					*//*
					Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(new Date());
					
					*//**
					 * Calculating next 12th week mondayDate from the current week monday
					*//*
					Calendar endMonday = CalendarUtil.getDateIncrementByincrement(endCal,91);
					

					*//**
					 * checking whether projectMemberEnddate is lesserthan next 12th week monday date then 
					 * projectMemberEnddate is taken as enddate for the submission records
					*//*
					if(pmh.getProjectTerminationDate() != null && pmh.getProjectTerminationDate().before(endMonday.getTime())){
					    endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					    endCal.setLenient(false);
						endCal.setTime(pmh.getProjectTerminationDate());
					    endMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON");
					}
					
					int totalWeeks = 0; 
					Date tsStartDate = null;
					Date tsEndDate = null;
					
					Calendar tsCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					tsCal.setLenient(false);
					
					*//**
					 * Calculating totalweeks b/w startdate and enddate
					 * 
					*//*
					totalWeeks = (int)((endMonday.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
					for (int week = 0; week < totalWeeks; week++) {
						if(tsStartDate == null){
							tsCal.setTime(startMonday.getTime());
							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON").getTime();
							tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN").getTime();
						}
						else{
							tsCal.setTime(tsStartDate);
						}
						TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissionsByMemberIdAndStartDate(projectMember.getId(),tsStartDate);
						if(timesheetSubmissions != null) {
							timesheetSubmissions.setStatus(Constants.MEMBER_ACTIVE);
							timesheetSubmissionsList.add(timesheetSubmissions);
						}
						else{
							timesheetSubmissions = new TimesheetSubmissions();
							timesheetSubmissions.setProjectMembers(projectMember);
							timesheetSubmissions.setStartDate(tsStartDate);
							timesheetSubmissions.setEndDate(tsEndDate);
							timesheetSubmissions.setStatus(Constants.MEMBER_ACTIVE);
							timesheetSubmissions.setCreatedDate(new Date());
							timesheetSubmissions.setCreatedBy(user.getUserCode());
							timesheetSubmissions.setUpdatedDate(new Date());
							timesheetSubmissions.setUpdatedBy(user.getUserCode());
							
	                        List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getAllProjectApproverHierarchyByProjectId(projectMember.getProjects().getId(),projectMember.getEmployee().getUser().getId());
							HashSet<SubmissionApproverHierarchy> saHierarchyList = new HashSet<SubmissionApproverHierarchy>();
	                        if(hierarchyList != null && !hierarchyList.isEmpty()){
								for(ProjectApproverHierarchy hierarchy : hierarchyList){
									SubmissionApproverHierarchy saHierarchy = new SubmissionApproverHierarchy();
									saHierarchy.setHeirarchyId(hierarchy);
									saHierarchy.setSubmissionId(timesheetSubmissions);
									saHierarchy.setSubmissionStatus(Submission_Status.getNameByValue("new"));
									saHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));
									saHierarchy.setCreatedDate(timesheetSubmissions.getCreatedDate());
									saHierarchy.setCreatedBy(timesheetSubmissions.getCreatedBy());
									saHierarchy.setUpdatedDate(timesheetSubmissions.getUpdatedDate());
									saHierarchy.setUpdatedBy(timesheetSubmissions.getUpdatedBy());
									saHierarchyList.add(saHierarchy);
								}
								timesheetSubmissions.setApproverHierarchyList(saHierarchyList);
							}
							timesheetSubmissionsList.add(timesheetSubmissions);
						}
						tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON").getTime();
						tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN").getTime();
					}
				}
			
				*//**
				 * Storing all submissions b/w startdate and enddate
				*//*
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	public void highLightMenu(String menuName,HttpServletRequest request){
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(menuName);
		request.getSession().setAttribute("sessionData",sessionData);
	}
	
	/**
	 * Return String Date in Date format
	 * @param dateString
	 * @return
	 */
	public  Date  getDateFormat(String dateString){
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); 
	    Date date = null;
	    try {
	        date = df.parse(dateString);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return date;
	}
	
	
	   /*
	    * updating the userDetails in the Spock  if we are editing  the user
	    *  Sammeta David 
	    */
	   public void updateSpockDetails( User user ,OrgLocations orgLocations) throws Exception{
	    	 
		    	 try{
			    		 List<OrgSpoc>  orgSpockList = orgSpocService.getOrgSpocByUserIdAndOrgLocationCode(user.getId(),orgLocations.getId());
			    		  if(null != orgSpockList){
					    		 for(OrgSpoc org :orgSpockList){
					    			 orgSpocService.addOrgSpoc(org);
					    			 
					    		 } 
			    		  }
		    	 }
		    	 catch(Exception e){
		    		 e.printStackTrace();
		    	 }
	    	 
	     }
	   
	   /**
		 * This method is used to update USER DB and  ElasticSearch Documents for common user
		 *
		 * @author: Ajith Matta
		 * 
		 */
		public void commonUserUpdate(User user) {

			try {
				List<UserRole> userRole = userRoleService.getUsersListByUserId(user.getId());
				if (null != userRole) {
					for (UserRole role : userRole) {
						userService.saveOrUpdateUserElkDocument(role, null, user.getUserCode());
					}
				}
			} catch (Exception e) {
				logger.info("--exception--" + e);
			}
		}
	   
	
	 /**
	 * this method converts list to Json String
	 * @param obj
	 * @return
	 */
		public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
		
	public String getBackOfficeSummaryFilters(JSONObject jsonObj) throws Exception {
		String subquery1 = "";
		String subquery2 = "";
		String subquery3 = "";

		//only fromTransDate
		 if(jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate")) 
					&& jsonObj.has("toTransDate") && "".equals(jsonObj.getString("toTransDate"))){
			 subquery1 = "and bt.transDate >= :fromTransDate" ;
		 }
		 
		 //only toTransDate
		 else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) 
					&& jsonObj.has("fromTransDate") && "".equals(jsonObj.getString("fromTransDate"))){
				subquery1 = "and bt.transDate <= :toTransDate" ;
		 }
		 
		 //combination of dates
		 else if(jsonObj.has("toTransDate") && jsonObj.getString("toTransDate") != null && !"".equals(jsonObj.getString("toTransDate")) && 
					jsonObj.has("fromTransDate") && jsonObj.getString("fromTransDate") != null && !"".equals(jsonObj.getString("fromTransDate"))){
				subquery1 = "and bt.transDate BETWEEN :fromTransDate and :toTransDate" ;
		 }
		
		 //based on paymentType
		 if(jsonObj.has("paymentTypeId") && jsonObj.getString("paymentTypeId") != null && !"".equals(jsonObj.getString("paymentTypeId"))){
			 String paymentTypeId = jsonObj.getString("paymentTypeId").replaceAll("\\[", "").replaceAll("\\]","");
			 String[] paymentTypes = paymentTypeId.split(",");
			 if (paymentTypes != null && paymentTypes[0] != null && paymentTypes[0].length() > 0) {
				 for (String paymentType : paymentTypes) {
					 paymentType = paymentType.replace("\"", "");
					 subquery2 = subquery2 + " bt.paymentTypeId.id =" + paymentType+" OR ";
				 }
			 }
		 }
		
		if(!subquery2.equalsIgnoreCase("")){
			subquery2 = " (" + subquery2.substring(0, subquery2.length() - 4) + ")";
			subquery2 = " and " +subquery2;
		}
		
		//based on transType
		if(jsonObj.has("transType") && jsonObj.getString("transType") != null && !"".equals(jsonObj.getString("transType"))){
			   String transType = jsonObj.getString("transType").replaceAll("\\[", "").replaceAll("\\]","");
			   String[] transTypes = transType.split(",");
			   if (transTypes != null && transTypes[0] != null && transTypes[0].length() > 0) {
					 for (String trans : transTypes) {
						 trans = trans.replace("\"", "");
						 subquery3= subquery3 + " bt.transType = '"+trans+"' OR ";
					 }
				 }
		 }
		
		if(!subquery3.equalsIgnoreCase("")){
			subquery3 = " (" + subquery3.substring(0, subquery3.length() - 4) + ")";
			subquery3 = " and " +subquery3;
		}
		return  subquery1+subquery2+subquery3;
		
	}

		/**
		 * @author Rajashekar create headers for file
		 * @param createExcelsheet
		 * @param style
		 * @param headers
		 * @param rowCount 
		 * @return sheet
		 */
		public Sheet setExcelHeaders(Sheet createExcelsheet, CellStyle style, String[] headers, int rowCount) {
			Row excelHeader = createExcelsheet.createRow(rowCount);
			for (int i = 0; i < headers.length; i++) {
				Cell cell = excelHeader.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(style);
			}
			return createExcelsheet;
		}

		/**
		 * @author Rajashekar writes font style to headers
		 * @param wb
		 * @return CellStyle
		 */
		public CellStyle getCellStyle(Workbook wb) {
			CellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Font font = wb.createFont();
			font.setColor(HSSFColor.BLUE.index);
			style.setFont(font);
			return style;
		}
		


		/**
		 * HashRole is used for Java Code
		 * @author: Naresh Bolisetty
		 * 
		 */
		public boolean hasRole(String role) {
	        // get security context from thread local
			
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication == null)
	            return false;

	        for (GrantedAuthority auth : authentication.getAuthorities()) {
	            if (role.equals(auth.getAuthority()))
	                return true;
	        }

	        return false;
	    }
		
		/**
		 * convertTimeZones is used to convert one timezone to anothet timezone 
		 * @author: Prasanna Vadla
		 * 
		 */
		public static String convertTimeZones(final String fromTimeZoneString, 
		        final String toTimeZoneString, final String fromDateTime) {
		    final DateTimeZone fromTimeZone = DateTimeZone.forID(fromTimeZoneString);
		    final DateTimeZone toTimeZone = DateTimeZone.forID(toTimeZoneString);
		    final DateTime dateTime = new DateTime(fromDateTime, fromTimeZone);

		    final DateTimeFormatter outputFormatter 
		        = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(toTimeZone);
		    return outputFormatter.print(dateTime);
		}
		
		
		public static Float getCombinedHours(Float hours1,Float hours2){
			float cthHours = hours1;
			float cthHoursValue = (int) cthHours;
			float cthMinutesValue = cthHours - cthHoursValue;
			float totalCthMinutes = (cthHoursValue* 60)+(cthMinutesValue*100);

			float wthHours = hours2;
			float wthHoursValue = (int) wthHours;
			float wthMinutesValue = wthHours - wthHoursValue;
			float totalWthMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);

			float totalMinutes = totalCthMinutes+totalWthMinutes;

			float actualHours = (int) (totalMinutes / 60); //since both are ints, you get an int
			float actualMinutes = (totalMinutes % 60)/100;

			String actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
			
			return Float.parseFloat(actualTimeString);
		}
		
		public static String getHoursDisplayFormat(Float hours2){
			float cthHours = 0;
			float cthHoursValue = (int) cthHours;
			float cthMinutesValue = cthHours - cthHoursValue;
			float totalCthMinutes = (cthHoursValue* 60)+(cthMinutesValue*100);

			float wthHours = hours2;
			float wthHoursValue = (int) wthHours;
			float wthMinutesValue = wthHours - wthHoursValue;
			float totalWthMinutes = (wthHoursValue* 60)+(wthMinutesValue*100);

			float totalMinutes = totalCthMinutes+totalWthMinutes;

			float actualHours = (int) (totalMinutes / 60); //since both are ints, you get an int
			float actualMinutes = (totalMinutes % 60)/100;
			String actualTimeString = null;
			if(actualMinutes != 0){
				actualTimeString = String.format("%.2f", (actualHours+actualMinutes));
			}
			else{
				actualTimeString = String.format("%.0f", (actualHours+actualMinutes)).concat(".0");
			}
			
			return actualTimeString;
		}

		public void updateClientVendorElk(OrgRelations orgRelation, OrgLocations orgLocations, User user, String responderType) {
			try {
				  ClientVendorIndex  clientVendorIndex = new ClientVendorIndex();
				  List<OrgSpoc> orgSpocsList = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(), user.getId(), orgLocations.getId(), responderType);
				  clientVendorIndex.setNoContacts((long)orgSpocsList.size());
				  sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE,orgRelation.getId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			  
			
		}
		
		//common method for saveElkRequirement
		
		public RequirementDTO getRequirementDTO(Requirement requirement) throws Exception
		{
			RequirementDTO requirementDTO = new RequirementDTO();
			requirementDTO.setRequirementId(requirement.getId().toString());
			requirementDTO.setOrgLocationId(requirement.getOrgLocationId().getId());
			requirementDTO.setJobTitle(requirement.getJobTitle());
			requirementDTO.setJobType(requirement.getJobType());
			requirementDTO.setOtherJobType(requirement.getOtherJobType());
			requirementDTO.setIndustryName(requirement.getIndustryName());
			requirementDTO.setTravelAllowance(requirement.getTravelAllowance());
			requirementDTO.setCoolingOff(requirement.getReqCoolingOffPeriod());
			requirementDTO.setReleaseToAllUsers(requirement.getReleaseJobToAllUsers());
			requirementDTO.setProjectDurationMonths(requirement.getProjectDurationMonths());
			requirementDTO.setProjectDurationYears(requirement.getProjectDurationYears());
			requirementDTO.setIsreceivingApplicants(requirement.getIsreceivingApplicants());
			requirementDTO.setGeneratedId(requirement.getGeneratedId());
			if (null != requirement.getDescription() && !"".equals(requirement.getDescription())) {
				requirementDTO.setDescription(requirement.getDescription());
			}
			if (null != requirement.getMaxRate() && !"".equals(requirement.getMaxRate())) {
				requirementDTO.setMaxRate(requirement.getMaxRate());
			}
			if (null != requirement.getRate() && !"".equals(requirement.getRate())) {
				requirementDTO.setRate(requirement.getRate());
			}
			if (null != requirement.getRateType() && !"".equals(requirement.getRateType())) {
				requirementDTO.setRateType(requirement.getRateType());
			}
			if (null != requirement.getMinExp()) {
				requirementDTO.setMinExp(requirement.getMinExp());
			}
			if (null != requirement.getMaxExp()) {
				requirementDTO.setMaxExp(requirement.getMaxExp());
			}
			requirementDTO.setRequirementStatus(requirement.getRequirementSubmissionStatus().toString());
			// requirementIndex.setRequirementSubmissionPreviousStatus(requirement.getRequirementSubmissionPreviousStatus().toString());
			requirementDTO.setCountry(requirement.getCountry());
			requirementDTO.setState(requirement.getState());
			requirementDTO.setCity(requirement.getCity());
			requirementDTO.setLocation(requirement.getLocation());
			requirementDTO.setZipCode(requirement.getZipCode());
			requirementDTO.setReqCreatedBy(requirement.getCreatedBy().getId());
			requirementDTO.setReqUpdatedBy(requirement.getUpdatedBy());
			if (requirement.getManagerUserId() != null) {
				requirementDTO.setManagerUserId(requirement.getManagerUserId().getId().toString());
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (null != requirement.getClosedDate()) {
				String reqclosedDate = dateFormat.format(requirement.getClosedDate());
				requirementDTO.setClosedDate(reqclosedDate);
			}
			String reqcreatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementDTO.setCreatedDate(reqcreatedDate);
			String reqUpDatedDate = dateFormat.format(requirement.getCreatedDate());
			requirementDTO.setUpdatedDate(reqUpDatedDate);
			Long userId = requirement.getManagerUserId().getId();
			User ownerUser = userService.getUserById(userId);
			StringBuilder sbilder = new StringBuilder();
			if (ownerUser != null) {
				if (ownerUser.getFirstName() != null && ownerUser.getLastName() != null) {
					sbilder.append((ownerUser.getFirstName())).append(" ").append(ownerUser.getLastName());
				}
				else {
					sbilder.append((ownerUser.getFirstName()));
				}
				requirementDTO.setManager(sbilder.toString());
			}
			// to store approver of the req in elk
			if (requirementDTO.getParallelApprover() != null
					&& !requirementDTO.getParallelApprover().isEmpty()) {
				String[] reqApproverUserId = requirementDTO.getParallelApprover().toArray(new String[requirementDTO.getParallelApprover().size()]);
				requirementDTO.setReqLevel1ApproverUserId(reqApproverUserId);
			}
			if (StringUtils.isNotBlank(requirementDTO.getSequentialApprover1())) {
				String sequentialApprovers1 = requirementDTO.getSequentialApprover1();
				String sequentialApprovers2 = requirementDTO.getSequentialApprover2();
				requirementDTO.setReqLevel1ApproverUserId(sequentialApprovers1.split(","));
				requirementDTO.setReqLevel2ApproverUserId(sequentialApprovers2.split(","));
			}
			if (StringUtils.isNotBlank(requirementDTO.getAssignedToMaxCount())) {
				if (StringUtils.isNotBlank(requirementDTO.getAssignedTeamId())) {
					String assignedTeamId = requirementDTO.getAssignedTeamId();
					String[] teamOwnerUserId = assignedTeamId.split(",");
					requirementDTO.setTeamOwnerUserId(teamOwnerUserId);
				}
				if (requirementDTO.getAssignUsers() != null) {
					String[] teamAssignUserId = requirementDTO.getAssignUsers().toArray(new String[requirementDTO.getAssignUsers().size()]);
					requirementDTO.setTeamAssignUserId(teamAssignUserId);
				}
			}
			// requirementIndex.setRequirementSubmissionStatus(requirement.getRequirementSubmissionStatus().toString());
			requirementDTO.setIsTemplate(requirement.getIsTemplate());
			// education
			if (null != requirement.getEducation()) {
				String[] education = requirement.getEducation().split(",");
				requirementDTO.setEducation(Arrays.toString(education));
			}
			if (requirement.getClientId() != null) {
				Requirement requirement1 = requirementService.getRequirementByRequirementId(requirement.getId());
				StringBuilder sb = new StringBuilder();
				if (Constants.CLIENT.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					if (requirement1.getClientId().getResponderLocationId() != null) {
						requirementDTO.setClient(requirement1.getClientId().getResponderLocationId().getOrgLocationName());
					}
				}
				if (Constants.VENDOR.equalsIgnoreCase(requirement1.getClientId().getResponderType())) {
					requirementDTO.setClient(requirement1.getClientId().getLocationId().getOrgLocationName());
				}
				// Prasad salina
				if (null != requirement1.getSpocId()) {
					if (requirement1.getSpocId().getClientUser() != null
							&& requirement1.getSpocId().getClientUser().getLastName() != null
							&& !"".equals(requirement1.getSpocId().getClientUser().getLastName())) {
						sb.append(requirement1.getSpocId().getClientUser().getFirstName()).append(" ").append(requirement1.getSpocId().getClientUser().getLastName());
					}
					else {
						sb.append(requirement1.getSpocId().getClientUser().getFirstName());
					}
					requirementDTO.setClientContactName(sb.toString());
					requirementDTO.setClientContactEmail(requirement1.getSpocId().getClientUser().getEmail());
				}
				// requirementIndex.setClientContactEmail(requirement1.getClientId().getLocationId().getUser().getEmail());
			}
			// requirementIndex.setClient(requirement.getClientName());
			// skill pending
			if (null != requirement.getSkills()) {
				String[] skills = requirement.getSkills().split(",");
				requirementDTO.setSkills(Arrays.toString(skills));
			}
			if (null != requirement.getNoticePeriod()) {
				String[] noticePeriod = requirement.getNoticePeriod().split(",");
				requirementDTO.setNoticePeriod(Arrays.asList(noticePeriod));
			}
			requirementDTO.setJobId(requirement.getJobId());
			requirementDTO.setPriority(requirement.getPriority());
			requirementDTO.setCurrentRateCurrency(requirement.getCurrencyCode());
			requirementDTO.setPositions(requirement.getPositions());
			if (null != requirement.getTotalApplicants()) {
				requirementDTO.setNumberOfApplicants(requirement.getTotalApplicants().toString());
			}
			requirementDTO.setReleasedBy(requirement.getReleasedBy());
			requirementDTO.setRequirementUUID(requirement.getRequirementUUID());
			if (null != requirement.getIsPublic()) {
				requirementDTO.setIsPublicJob(requirement.getIsPublic());
			}
			else {
				requirementDTO.setIsPublicJob("N");
			}
			if (null != requirement.getIsCrowdSourcing()) {
				requirementDTO.setIsCrowdSourcingJob(requirement.getIsCrowdSourcing());
			}
			else {
				requirementDTO.setIsCrowdSourcingJob("N");
			}
			if (null != requirement.getPostedOrgName()
					&& !requirement.getPostedOrgName().isEmpty()) {
				requirementDTO.setPostedOrg(requirement.getPostedOrgName());
			}
			if (null != requirement.getAppliedProfessionals()
					&& !"".equalsIgnoreCase(requirement.getAppliedProfessionals())) {
				String[] appliedProfessionals = requirement.getAppliedProfessionals().split(",");
				requirementDTO.setAppliedProfessionals(appliedProfessionals);
			}
			if (null != requirement.getAppliedRecruiters()
					&& !"".equalsIgnoreCase(requirement.getAppliedRecruiters())) {
				String[] appliedRecruiters = requirement.getAppliedRecruiters().split(",");
				requirementDTO.setAppliedRecruiters(appliedRecruiters);
			}
			if (null != requirement.getReqlevel1ApproverStatus()
					&& !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())) {
				requirementDTO.setReqLevel1ApproverStatus(requirement.getReqlevel1ApproverStatus());
			}
			if (null != requirement.getReqlevel2ApproverStatus()
					&& !"".equalsIgnoreCase(requirement.getReqlevel1ApproverStatus())) {
				requirementDTO.setReqLevel2ApproverStatus(requirement.getReqlevel2ApproverStatus());
			}
			if (StringUtils.isNotBlank(requirement.getRewardType())) {
				requirementDTO.setRewardType(requirement.getRewardType());
			}
			if (StringUtils.isNotBlank(requirement.getRewardPercentage())) {
				requirementDTO.setRewardPercentage(requirement.getRewardPercentage());
			}
			if (StringUtils.isNotBlank(requirement.getRewardFlat())) {
				requirementDTO.setRewardFlat(requirement.getRewardFlat());
			}
			if (StringUtils.isNotBlank(requirement.getRewardFlatRateType())) {
				requirementDTO.setRewardFlatRateType(requirement.getRewardFlatRateType());
			}
			if (StringUtils.isNotBlank(requirement.getRewardGuaranteePeriod())) {
				requirementDTO.setRewardGuaranteePeriod(requirement.getRewardGuaranteePeriod());
			}
			if (null != requirement.getVisaType()) {
				String[] visaTypes = requirement.getVisaType().split(",");
				requirementDTO.setVisa(Arrays.asList(visaTypes));
			}
			if (null != requirement.getGeneratedId()
					&& !"".equalsIgnoreCase(requirement.getGeneratedId())) {
				requirementDTO.setGeneratedId(requirement.getGeneratedId());
			}
			return requirementDTO;
		}
		
		public static byte[] readBytesFromFile(String filePath)
		{
			FileInputStream fileInputStream = null;
			byte[] bytesArray = null;
			try {
				File file = new File(filePath);
				bytesArray = new byte[(int) file.length()];
				// read file into bytes[]
				fileInputStream = new FileInputStream(file);
				fileInputStream.read(bytesArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return bytesArray;
		}
		/**
		 * Get Number of days Between two given dates
		 * @param fromDate
		 * @param toDate
		 * @return
		 * @throws ParseException
		 */
		public int getNumberOfDaysBetweenTwoDates(Date fromDate, Date toDate) throws ParseException {
				long diff = toDate.getTime() - fromDate.getTime();
				int  nuberOfDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				return nuberOfDays+1;
			}
		
		
		public  Date[] getStartDateEndDateByYear(int year){
			Date[] dates = new Date[2];
		    Calendar calendarStart=Calendar.getInstance();
		    calendarStart.set(Calendar.YEAR,year);
		    calendarStart.set(Calendar.MONTH,0);
		    calendarStart.set(Calendar.DAY_OF_MONTH,1);
		    // returning the first date
		    dates[0] = calendarStart.getTime();
		    Calendar calendarEnd=Calendar.getInstance();
		    calendarEnd.set(Calendar.YEAR,year);
		    calendarEnd.set(Calendar.MONTH,11);
		    calendarEnd.set(Calendar.DAY_OF_MONTH,31);
		    // returning the last date
		    dates[1] = calendarEnd.getTime();
			return dates;
		}
		
		public  Date[] getMonthStartDateEndDateByMonth(int month) {
			Date[] dates = new Date[2];
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, month-1);
			cal.set(Calendar.DATE, 1);
			cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH));
			dates[0] = cal.getTime();
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			dates[1] = cal.getTime();
			return dates;
	}
		public  Date[] getMonthStartDateEndDateByYearAndMonth(int year,int month) {
			Date[] dates = new Date[2];
			Calendar calendar = Calendar.getInstance();
			int dateDay = 1;
			calendar.set(year, month, dateDay);
			int numOfDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			System.out.println("First Day of month: " + calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth-1);
			System.out.println("Last Day of month: " + calendar.getTime());
			return dates;
	}
		
		
}
