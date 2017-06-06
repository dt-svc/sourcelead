package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.JobAlert;

public interface JobAlertService {

	 JobAlert saveJobAlert(JobAlert jobalert) throws Exception;
		
	 JobAlert getJobAlertById(Long jobalertId) throws Exception;
	
	 List<JobAlert>  getJobAlertByuserId(Long userId) throws Exception;
	
	String  saveJobAlert(JSONObject jsonObj) throws Exception;
	
}
