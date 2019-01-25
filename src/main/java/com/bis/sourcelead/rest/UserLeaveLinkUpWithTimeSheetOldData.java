package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@RestController
@RequestMapping("/app")
public class UserLeaveLinkUpWithTimeSheetOldData {

	 @Autowired
	 private LeaveService leaveService;
	
	 @Autowired
	 private HolidayService holidayService;
	 
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 private ProjectsService projectsService;
	 
	 @Autowired
	 private LookUpDataService lookUpDataService;
	 
	 @Autowired
	 private CommonUtil commonUtil;

	 @Autowired
	 private LeaveTypeService leaveTypeService;
	 
	 @Autowired
	 private ProjectTasksDao projectTasksDao;
	 
	//@RequestMapping(value ="/ws/userLeaveOldDateToTimesheet")
	public String userLeaveOldDateToTimesheet() throws Exception {
		
		List<Long> leaveType = new ArrayList<Long>();
		leaveType.add(3l);
		leaveType.add(4l);
		
		
		for(Long leaveTypeIds:leaveType) {
			LeaveType leaveTypeDTO = leaveTypeService.getLeaveTypeById(leaveTypeIds);
			List<Projects> projectsList = projectsService.getAllProjectByOrgLocationsId(leaveTypeDTO.getOrgLocations().getId());
			if(projectsList != null && projectsList.size() > 0) {
				for(Projects projects : projectsList) {
					ProjectTasks  tasks  = new ProjectTasks();
					LookUpData taskType = lookUpDataService.getTasksTypeByUniqueKey(Constants.GENERAL);
					tasks.setGeneratedId(commonUtil.getProjectTaskGenaratedId(projects));
					tasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					tasks.setTaskName(leaveTypeDTO.getName());
					tasks.setDescription(leaveTypeDTO.getName());
					if(leaveTypeDTO.getLeaveType().equalsIgnoreCase("Non Billable")) {
						tasks.setBillableType("Non-Billable");
					}else {
						tasks.setBillableType("Billable");
					}
					tasks.setProjectId(projects);
					tasks.setTaskType(taskType);
					tasks.setLeaveType(leaveTypeDTO);
					LookUpData taskStatus = lookUpDataService.getTasksById(Constants.TaskStatusID);
					tasks.setTaskStatus(taskStatus);
					tasks.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
					tasks.setCreatedBy(leaveTypeDTO.getCreatedBy());
					tasks.setCreatedDate(new Date());
					tasks.setUpdatedBy(leaveTypeDTO.getCreatedBy());
					tasks.setUpdatedDate(new Date());
					projectTasksDao.addTasks(tasks);
					
				}
			}
		}
		System.out.println("added leave type to project task");
		
		
		/*List<Leave> leaves = leaveService.getAllLeave();
		if(leaves != null && !leaves.isEmpty()) {
			for(Leave leave:leaves) {
				User leaveUserId = null; 
				for(LeaveApproveHierarchy approverUser:leave.getApproverHierarchyList()) {
					leaveUserId =  approverUser.getApproverUserId();
				}
				if(leaveUserId != null && leave != null) {
					//timesheetRestController.linkLeaveTypeWithTimeSheet(leaveUserId,leave.getOrgLocations(), leave.getLeaveType().getId(), leave,null);
				}
			}
		}*/
		System.out.println("added old leave type to timesheet");
		
		List<Holiday> holidayList = holidayService.getAllHoliday();
		if(holidayList != null && !holidayList.isEmpty()) {
		for(Holiday holiday:holidayList) {
			List<Projects> projectsList = projectsService.getAllProjectByOrgLocationsId(holiday.getOrgLocations().getId());
			if(projectsList != null && projectsList.size() > 0) {
				for(Projects projects : projectsList) {
					ProjectTasks  tasks  = new ProjectTasks();
					LookUpData taskType = lookUpDataService.getTasksTypeByUniqueKey(Constants.GENERAL);
					tasks.setGeneratedId(commonUtil.getProjectTaskGenaratedId(projects));
					tasks.setProjectTasksUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					tasks.setTaskName(holiday.getHolidayName());
					tasks.setDescription(holiday.getHolidayName());
					tasks.setBillableType("Billable");
					tasks.setProjectId(projects);
					tasks.setTaskType(taskType);
					tasks.setHoliday(holiday);
					LookUpData taskStatus = lookUpDataService.getTasksById(Constants.TaskStatusID);
					tasks.setTaskStatus(taskStatus);
					tasks.setStatus(String.valueOf(Constants.CANDIDATE_ACTIVE));
					tasks.setCreatedBy(holiday.getCreatedBy());
					tasks.setCreatedDate(new Date());
					tasks.setUpdatedBy(holiday.getCreatedBy());
					tasks.setUpdatedDate(new Date());
					projectTasksDao.addTasks(tasks);
					
				}
			}
		}
	}
		System.out.println("added holiday to projectTask");
		/*if(holidayList != null && !holidayList.isEmpty()) {
			for(Holiday holiday:holidayList) {
				if(holiday != null) {
					User user = userService.getUserByUserCode(holiday.getCreatedBy());
					if(user!= null) {
						//timesheetRestController.linkHolidaysWithTimeSheet(user,holiday.getOrgLocations(),holiday);
					}
				}
			}
		}*/
		System.out.println("added old holiday data to timesheet");
		
		return "success";
		
	} 
}
