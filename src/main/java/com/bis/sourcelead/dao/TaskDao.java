package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Tasks;

public interface TaskDao {

	Tasks saveTasks(Tasks tasks) throws Exception;
	
	Tasks getTasksById(Long id);
	
	List<Tasks> assignedTasksListByIdANDOrgLocationId(Long OrgLocationId,Long userId)throws Exception;
	
	List<Tasks> myTasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId)throws Exception;
	
	List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId,String Status,String date)throws Exception;
}
