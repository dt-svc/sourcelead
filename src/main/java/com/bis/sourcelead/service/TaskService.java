package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Tasks;

public interface TaskService {

	Tasks saveTasks(Tasks tasks) throws Exception;
	
	Tasks getTasksById(Long id);
	
	String  saveTasks(JSONObject jsonObj) throws Exception;
	
	List<Tasks> assignedTasksListByIdANDOrgLocationId(Long OrgLocationId,Long userId)throws Exception;
	
	List<Tasks> myTasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId)throws Exception;
	
	List<Tasks> tasksListByIdAndOrgLocationIdAssignedUserId(Long OrgLocationId,Long userId,String Status,String date)throws Exception;
	
}
