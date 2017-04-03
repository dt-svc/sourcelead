package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Tasks;

public interface TaskDao {

	Tasks saveTasks(Tasks tasks) throws Exception;
	
	Tasks getTasksById(Long id);
	
	List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId,String Status,String date, Date startDateUTC, Date endDateUTC)throws Exception;
	
	List<Tasks> getTaskByScheduledTime(String currentTime)throws Exception;
}
