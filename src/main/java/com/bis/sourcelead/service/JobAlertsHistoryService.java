package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.JobAlertsHistory;
import com.bis.sourcelead.entity.User;

public interface JobAlertsHistoryService {
	
	JobAlertsHistory saveJobAlertsHistory(JobAlertsHistory jobAlertsHistory) throws Exception;
	
	public void addJobAlertsHistory(JobAlert jobAlert,User user) throws Exception;
}
