package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.JobAlert;

public interface JobAlertDao {
	
	/**
	 * @author Laxman
	 * @param JobAlert
	 * @return
	 * @throws Exception
	 */
	 JobAlert saveJobAlert(JobAlert jobalert) throws Exception;
	
	 JobAlert getJobAlertById(Long jobalertId) throws Exception;
	
	 List<JobAlert>  getJobAlertByuserId(Long userId) throws Exception;
	 
	 List<JobAlert>  getJobAlertByEmail(String email) throws Exception;
	 
	 List<JobAlert>  getConfiruredJobsAlertsList(String duration) throws Exception;
	 
	 JobAlert getJobAlertByUUID(String jobAlertUUID) throws Exception;
	 
	 public void deleteJobAlert(JobAlert jobAlert) throws Exception;
	 
	 Integer getJobAlertCountByEmail(String email) throws Exception;

	 List<JobAlert>  getJobAlertByEmailAndAlertDuration(String email,String duration) throws Exception;
}
