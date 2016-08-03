package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

/**
 * This is the commonutil for the certain common methods used throughout the application
 * @author: Naresh Bolisetty
 * 
 */
@Component
public class CommonUtil {
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	private ProjectTasksService projectTasksService;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	/**
	 * This method is used to store pre-defined sourcelead tasks for the particular project
	 * @author: Naresh Bolisetty
	 * 
	 */
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
	 * This method is used to storeTimesheetSubmissionRecords based on projectMemberAssignDate and projectMemberEndDate(optional)
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void storeTimesheetSubmissionRecords(User user){
		try {
			
			/**
			 * Getting List Of all Projectmembers based on login user who is approver/employee to that particular project
			 *  who didn't have 4 weeks prior/post submission records in DB
			 * 
			 */
			List<ProjectMembers> projectMembers =  projectMembersService.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			
			if(projectMembers != null && !projectMembers.isEmpty()){
				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				for(ProjectMembers projectMember :projectMembers){
					
					/**
					 * Getting startDate of coming week from the existing submission records
					 *
					 */
					Date nextMonday = timesheetSubmissionsService.getNextMondayByProjectMemberId(projectMember.getId());
					
					/**
					 * Calculating startDate for the submission records of this particular projectmember
					*/
					Date startMondayDate = null;
					if(nextMonday != null){
						startMondayDate =  nextMonday;
					}
					else{
						
						/**
						 * If he didn't have submission records previous for this project,then ProjectAssignedDate is taken as startDate
						*/
						startMondayDate = projectMember.getProjectAssignedDate();
						
						/**
						 * Calculating last 4th week monday date 
						*/
						Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						currentCal.setLenient(false);
						currentCal.setTime(new Date());
						Calendar currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(currentCal, "MON");
						Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(currentMonday,-28);
						Date previousMondayDate = previousMonday.getTime();
						/**
						 * Checking whether projectAssignDate lesserthan last 4th week Monday date,
						 * then last 4th week Monday date is taken as startdate
						*/
				        if(startMondayDate.before(previousMondayDate)){
				    	   startMondayDate = previousMondayDate;
				        }
					}
								
					Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(startMondayDate);
					Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
					
					
					
					/**
					 * Calculating endDate for the submission records of this particular projectmember
					*/
					Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(new Date());
					
					/**
					 * Calculating next 4th week mondayDate from the current week monday
					*/
					Calendar endMonday = CalendarUtil.getDateIncrementByincrement(endCal,35);
					

					/**
					 * checking whether projectMemberEnddate is lesserthan next 4th week monday date then 
					 * projectMemberEnddate is taken as enddate for the submission records
					*/
					if(projectMember.getProjectEndDate() != null && projectMember.getProjectEndDate().before(endMonday.getTime())){
					    endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						startCal.setLenient(false);
						startCal.setTime(projectMember.getProjectEndDate());
					    endMonday = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "MON");
					}
					
					int totalWeeks = 0; 
					/**
					 * Calculating totalweeks b/w startdate and enddate
					 * 
					*/
					totalWeeks = (int)((endMonday.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
					long startMondayInMilliSeconds = startMonday.getTimeInMillis();
					for (int week = 0; week < totalWeeks; week++) {
						Calendar currentMondayOfTheWeek = Calendar.getInstance();
						long weekVal = 7l * 24l * 60l * 60l * week * 1000l; 
						currentMondayOfTheWeek.setTimeInMillis(startMondayInMilliSeconds + weekVal);
						
						TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
						
						timesheetSubmissions.setProjectMembers(projectMember);
						timesheetSubmissions.setStartDate(currentMondayOfTheWeek.getTime());
						timesheetSubmissions.setEndDate(new Date());
						
						Calendar currentWeekEndDate = Calendar.getInstance();
						currentWeekEndDate.setTimeInMillis(currentMondayOfTheWeek.getTimeInMillis() + (6l * 24l * 60l * 60l * 1000l));
						timesheetSubmissions.setEndDate(currentWeekEndDate.getTime());
						
						/*SubmissionApproverHierarchy 
						timesheetSubmissions.setSubmissionStatus(Submission_Status.getNameByValue("new"));
						timesheetSubmissions.setSubmissionApprovalStatus(Submission_ApprovalStatus.getNameByValue("new"));*/
						
						
						timesheetSubmissions.setCreatedDate(new Date());
						timesheetSubmissions.setCreatedBy(user.getUserCode());
						timesheetSubmissions.setUpdatedDate(new Date());
						timesheetSubmissions.setUpdatedBy(user.getUserCode());
						
                        List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectId(projectMember.getProjects().getId());
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
				}
			
				/**
				 * Storing all submissions b/w startdate and enddate
				*/
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
