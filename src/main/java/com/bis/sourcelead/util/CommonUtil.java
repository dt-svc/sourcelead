package com.bis.sourcelead.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

@Component
public class CommonUtil {
	
	@Autowired
	private ProjectMembersService projectMembersService;
	
	@Autowired
	private TimesheetSubmissionsService timesheetSubmissionsService;

	public void storeTimesheetSubmissionRecords(User user){
		try {
			List<ProjectMembers> projectMembers =  projectMembersService.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(user.getId());
			
			if(projectMembers != null && !projectMembers.isEmpty()){
				ArrayList<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
				for(ProjectMembers projectMember :projectMembers){
					Date nextMonday = timesheetSubmissionsService.getNextMondayByProjectMemberId(projectMember.getId());
					Date startMondayDate = null;
					if(nextMonday != null){
						startMondayDate =  nextMonday;
					}
					else{
						startMondayDate = projectMember.getProjectAssignedDate();
					}
					Calendar startCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
					startCal.setLenient(false);
					startCal.setTime(startMondayDate);
					Calendar startMonday = CalendarUtil.getCurrentWeekDayByCalendar(startCal, "MON");
					
					Calendar endMonday = CalendarUtil.getDateIncrementByincrement(startCal,28);
					if(projectMember.getProjectEndDate() != null && projectMember.getProjectEndDate().before(endMonday.getTime())){
						Calendar endCal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
						startCal.setLenient(false);
						startCal.setTime(projectMember.getProjectEndDate());
					    endMonday = CalendarUtil.getCurrentWeekDayByCalendar(endCal, "MON");
					}
					
					int totalWeeks = 0; 
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
						
						timesheetSubmissions.setSubmissionStatus(Submission_Status.getNameByValue("new"));
						timesheetSubmissions.setCreatedDate(new Date());
						timesheetSubmissions.setCreatedBy(user.getCreatedBy());
						timesheetSubmissions.setUpdatedDate(new Date());
						timesheetSubmissions.setUpdatedBy(user.getCreatedBy());
						timesheetSubmissionsList.add(timesheetSubmissions);
					}
				}
				timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
