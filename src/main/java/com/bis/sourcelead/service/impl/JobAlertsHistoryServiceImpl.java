package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.JobAlertsHistoryDao;
import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.JobAlertsHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.JobAlertsHistoryService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class JobAlertsHistoryServiceImpl implements JobAlertsHistoryService {

	@Autowired
	JobAlertsHistoryDao jobAlertsHistoryDao;
	
	@Override
	public JobAlertsHistory saveJobAlertsHistory(JobAlertsHistory jobAlertsHistory) throws Exception
	{
		return jobAlertsHistoryDao.saveJobAlertsHistory(jobAlertsHistory);
	}

	@Override
	public void addJobAlertsHistory(JobAlert jobAlert,User user) throws Exception
	{
		JobAlertsHistory jobAlertsHistory = new JobAlertsHistory();
		jobAlertsHistory.setAlertTitle(jobAlert.getAlertTitle());
		jobAlertsHistory.setUserId(jobAlert.getUserId());
		jobAlertsHistory.setSkillsName(jobAlert.getSkillsName());
		jobAlertsHistory.setCountry(jobAlert.getCountry());
		jobAlertsHistory.setState(jobAlert.getState());
		jobAlertsHistory.setCity(jobAlert.getCity());
		jobAlertsHistory.setExperience(jobAlert.getExperience());
		jobAlertsHistory.setRate(jobAlert.getRate());
		jobAlertsHistory.setRateType(jobAlert.getRateType());
		jobAlertsHistory.setRateCurrency(jobAlert.getRateCurrency());
		jobAlertsHistory.setIndustryName(jobAlert.getIndustryName());
		jobAlertsHistory.setDuration(jobAlert.getDuration());
		jobAlertsHistory.setEmail(jobAlert.getEmail());
		jobAlertsHistory.setJobAlertUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		jobAlertsHistory.setStatus(1);
		jobAlertsHistory.setCreatedBy(user.getUserCode());
		jobAlertsHistory.setCreatedDate(new Date());
		jobAlertsHistory.setUpdatedBy(user.getUserCode());
		jobAlertsHistory.setUpdatedDate(new Date());
		jobAlertsHistoryDao.saveJobAlertsHistory(jobAlertsHistory);
	}
	
}
