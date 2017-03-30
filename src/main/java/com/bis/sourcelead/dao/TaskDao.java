package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.User;

public interface TaskDao {

	Tasks saveTasks(Tasks tasks) throws Exception;
	
	Tasks getTasksById(Long id);
	
	List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId,String Status,String date)throws Exception;
	
	List<Tasks> getTaskByScheduledTime(String currentTime)throws Exception;
	
	Long getTasksCompletedTodayByLocationCodeandUserId(OrgLocations orglocationCode ,User user) throws Exception;

}
