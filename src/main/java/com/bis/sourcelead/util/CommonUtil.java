package com.bis.sourcelead.util;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

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
	private ProjectMembersHistoryService projectMembersHistoryService;
	
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
	
	public static Date currentDate() {
        return new Date();
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
	public void terminateProjectMemberByProjectMemberId(Long projectMemberId,Primary isManuallyTerminated){
		try {
			ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMemberId);
			pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_CLOSED);
			pmh = projectMembersHistoryService.updateProjectMembersHistory(pmh);
			
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
	
	public Projects terminateProject(Projects project) throws Exception{
		Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(project.getId());
		if(projectMembersList != null && !projectMembersList.isEmpty()){
			 for(ProjectMembers pojectMember : projectMembersList){
				ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(pojectMember.getId());
				Date currentDate = new  Date();
				Date terminationDate = pmh.getProjectTerminationDate();
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
	 * This method is used to storeTimesheetSubmissionRecords based on projectMemberAssignDate and projectMemberEndDate(optional)
	 * @author: Naresh Bolisetty
	 * 
	 */
	public void storeTimesheetSubmissionRecords(User user){
		try {
			
			/**
			 * Getting List Of all Projectmembers based on login user who is approver/employee to that particular project
			 *  who didn't have 12 weeks prior/post submission records in DB
			 * 
			 */
			List<ProjectMembers> projectMembers =  projectMembersService.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			
			if(projectMembers != null && !projectMembers.isEmpty()){
				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				for(ProjectMembers projectMember :projectMembers){
					
					ProjectMembersHistory pmh = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectMemberId(projectMember.getId());
					
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
						startMondayDate = pmh.getProjectAssignedDate();
						
						/**
						 * Calculating last 12th week monday date 
						*/
						Calendar currentCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						currentCal.setLenient(false);
						currentCal.setTime(new Date());
						Calendar currentMonday = CalendarUtil.getCurrentWeekDayByCalendar(currentCal, "MON");
						Calendar previousMonday = CalendarUtil.getDateIncrementByincrement(currentMonday,-84);
						Date previousMondayDate = previousMonday.getTime();
						/**
						 * Checking whether projectAssignDate lesserthan last 12th week Monday date,
						 * then last 12th week Monday date is taken as startdate
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
					 * Calculating next 12th week mondayDate from the current week monday
					*/
					Calendar endMonday = CalendarUtil.getDateIncrementByincrement(endCal,91);
					

					/**
					 * checking whether projectMemberEnddate is lesserthan next 12th week monday date then 
					 * projectMemberEnddate is taken as enddate for the submission records
					*/
					if(pmh.getProjectTerminationDate() != null && pmh.getProjectTerminationDate().before(endMonday.getTime())){
					    endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					    endCal.setLenient(false);
						endCal.setTime(pmh.getProjectTerminationDate());
					    endMonday = CalendarUtil.getNextWeekDayByCalendar(endCal, "MON");
					}
					
					int totalWeeks = 0; 
					/**
					 * Calculating totalweeks b/w startdate and enddate
					 * 
					*/
					totalWeeks = (int)((endMonday.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
					long startMondayInMilliSeconds = startMonday.getTimeInMillis();
					for (int week = 0; week < totalWeeks; week++) {
						Calendar currentMondayOfTheWeek = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						long weekVal = 7l * 24l * 60l * 60l * week * 1000l; 
						currentMondayOfTheWeek.setTimeInMillis(startMondayInMilliSeconds + weekVal);
						
						TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissionsByMemberIdAndStartDate(projectMember.getId(), currentMondayOfTheWeek.getTime());
						if(timesheetSubmissions != null) {
							timesheetSubmissions.setStatus(Constants.MEMBER_ACTIVE);
							timesheetSubmissionsList.add(timesheetSubmissions);
						}
						else{
							timesheetSubmissions = new TimesheetSubmissions();
							timesheetSubmissions.setProjectMembers(projectMember);
							
							Calendar currentWeekEndDate = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
							currentWeekEndDate.setTimeInMillis(currentMondayOfTheWeek.getTimeInMillis() + (6l * 24l * 60l * 60l * 1000l));
							
							Calendar submissionStartCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
							submissionStartCal.setLenient(false);
							submissionStartCal.setTime(currentWeekEndDate.getTime());
							Calendar tsMondayCal = CalendarUtil.getCurrentWeekDayByCalendar(submissionStartCal, "MON");
							Calendar tsSundayCal = CalendarUtil.getCurrentWeekDayByCalendar(submissionStartCal, "SUN");
							
							timesheetSubmissions.setStartDate(tsMondayCal.getTime());
							timesheetSubmissions.setEndDate(tsSundayCal.getTime());
							timesheetSubmissions.setStatus(Constants.MEMBER_ACTIVE);
							timesheetSubmissions.setCreatedDate(new Date());
							timesheetSubmissions.setCreatedBy(user.getUserCode());
							timesheetSubmissions.setUpdatedDate(new Date());
							timesheetSubmissions.setUpdatedBy(user.getUserCode());
							
	                        List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getAllProjectApproverHierarchyByProjectId(projectMember.getProjects().getId());
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
}
