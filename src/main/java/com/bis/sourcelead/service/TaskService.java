package com.bis.sourcelead.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.AddTaskDTO;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;

public interface TaskService {

	Tasks saveTasks(Tasks tasks) throws Exception;
	
	Tasks getTasksById(Long id);
	
	 String  saveTasksDTO(AddTaskDTO addTaskDTO) throws Exception;
	
	List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId,String Status,String date, Date converteDate, Timezone timezone)throws Exception;
	
	List<Tasks> getTaskByScheduledTime(String currentTime)throws Exception;
	
	List<Tasks> getTaskListByRepeatStatus(String taskRepeatStatus)throws Exception;
	
	List<Tasks> getTasskByAssignedUserIdAndOrgLocationId(Long orgLocationId,Long userId)throws Exception;

	void mailToUserTask(Tasks task) throws Exception;

	Long getTasksCompletedTodayByLocationCodeandUserId(OrgLocations orglocationCode ,User user) throws Exception;
	
	List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOnboardingId(Long orgLocationId,Long userId,Long onboardingId)throws Exception;
	
	List<Tasks> getTasskByAssignedUserIdAndOrgLocationIdAndOffboardingId(Long orgLocationId,Long userId,Long offboardingId)throws Exception;
	
	List<Tasks> getTasksListOffboardingId(Long offboardingId)throws Exception;
	
	List<Tasks> getTasksListOnboardingId(Long onboardingId)throws Exception;
	
	List<Tasks> getTaskByAssignedUserIdAndOrgLocationIdAndVendorOnboardingId(Long orgLocationId,Long userId,Long vendorOnboardingId)throws Exception;
	
	Long taskCountByIdAndOrgLocationIdAssignedUserId(Long orgLocationId,Long userId,String Status)throws Exception;

	List<TaskDTO> getTasksListByImmigrationIdAndCandidateId(String immigrationId, User user, String taskStatus, String searchVal) throws ParseException;
	
	public Tasks getTaskByTaskName(String taskName)throws Exception;
	
	public void updateTaskStatus(Tasks task)throws Exception;
}
