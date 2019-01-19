package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.JobAlertDTO;
import com.bis.sourcelead.entity.JobAlert;

public interface JobAlertService {

	 JobAlert saveJobAlert(JobAlert jobalert) throws Exception;
		
	 JobAlert getJobAlertById(Long jobalertId) throws Exception;
	
	 List<JobAlert>  getJobAlertByuserId(Long userId) throws Exception;
	
	 String  saveJobAlert(JobAlertDTO  jobAlertDTO) throws Exception;
	 
	 List<JobAlert>  getJobAlertByEmail(String email) throws Exception;
	 
	 List<JobAlert>  getConfiruredJobsAlertsList(String duration) throws Exception;
	 
	 JobAlert getJobAlertByUUID(String jobAlertUUID) throws Exception;
	 
	 public void deleteJobAlert(JobAlert jobAlert) throws Exception;
	 
	 Integer getJobAlertCountByEmail(String email) throws Exception;
	 
	 List<JobAlert>  getJobAlertByEmailAndAlertDuration(String email,String duration) throws Exception;
	
}
