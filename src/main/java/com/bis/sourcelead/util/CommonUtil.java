package com.bis.sourcelead.util;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
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
	private SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Autowired
	private ProjectsService projectsService;
	
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
	 * This method is used to terminate project member by project memberId and way of termination
	 * @author: Naresh Bolisetty
	 * 
	 */
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
										Calendar terminatedDateMonday = CalendarUtil.getPreviousWeekDayByCalendar(terminateDateCal, "MON");
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
					Date tsStartDate = null;
					Date tsEndDate = null;
					
					Calendar tsCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					tsCal.setLenient(false);
					
					/**
					 * Calculating totalweeks b/w startdate and enddate
					 * 
					*/
					totalWeeks = (int)((endMonday.getTimeInMillis()-startMonday.getTimeInMillis())/(7l * 24l * 60l * 60l * 1000l));
					for (int week = 0; week < totalWeeks; week++) {
						if(tsStartDate == null){
							tsCal.setTime(startMonday.getTime());
							tsStartDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "MON").getTime();
							tsEndDate = CalendarUtil.getCurrentWeekDayByCalendar(tsCal, "SUN").getTime();
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
						tsStartDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "MON").getTime();
						tsEndDate = CalendarUtil.getNextWeekDayByCalendar(tsCal, "SUN").getTime();
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
