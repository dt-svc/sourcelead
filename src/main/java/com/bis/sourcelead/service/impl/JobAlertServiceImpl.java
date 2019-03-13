package com.bis.sourcelead.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.JobAlertDao;
import com.bis.sourcelead.dto.JobAlertDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.JobAlertService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class JobAlertServiceImpl implements JobAlertService{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	JobAlertDao jobAlertDao;
	
	 @Autowired
	 private CommonUtil commonUtil;

	@Override
	public JobAlert saveJobAlert(JobAlert jobalert) throws Exception {
		return jobAlertDao.saveJobAlert(jobalert);
	}

	@Override
	public JobAlert getJobAlertById(Long jobalertId) throws Exception {
		return jobAlertDao.getJobAlertById(jobalertId);
	}

	@Override
	public List<JobAlert> getJobAlertByuserId(Long userId) throws Exception {
		return jobAlertDao.getJobAlertByuserId(userId);
	}

	@Override
	public String saveJobAlert(JobAlertDTO  jobAlertDTO) throws Exception {
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	JobAlert jobAlert = null;
	 	if(StringUtils.isNotBlank(jobAlertDTO.getSavedJobAlertId())){
	 		jobAlert = jobAlertDao.getJobAlertById(Long.valueOf(jobAlertDTO.getSavedJobAlertId()));
	 	}else{
	 		jobAlert = new JobAlert();
		 	jobAlert.setJobAlertUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	 	}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getAlertTitle())){
	 		jobAlert.setAlertTitle(jobAlertDTO.getAlertTitle());
		}
	 	if(StringUtils.isNotBlank(jobAlertDTO.getEmail())){
	 		jobAlert.setEmail(jobAlertDTO.getEmail());
		}else{
			jobAlert.setEmail(user.getEmail());
		}
	 	if(StringUtils.isNotBlank(jobAlertDTO.getCountry())){
	 		jobAlert.setCountry(jobAlertDTO.getCountry());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getState())){
	 		jobAlert.setState(jobAlertDTO.getState());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getCity()) ){
	 		jobAlert.setCity(jobAlertDTO.getCity());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getLocation())){
	 		jobAlert.setLocation(jobAlertDTO.getLocation());
		}
	 	
		if(StringUtils.isNotBlank(jobAlertDTO.getZipcode())){
	 		jobAlert.setZipCode(jobAlertDTO.getZipcode());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getExperience())){
	 		jobAlert.setExperience(jobAlertDTO.getExperience());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getRateCurrency())){
	 		jobAlert.setRateCurrency(jobAlertDTO.getRateCurrency());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getRate())){
	 		jobAlert.setRate(jobAlertDTO.getRate());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getRateType())){
	 		jobAlert.setRateType(jobAlertDTO.getRateType());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getIndustry())){
	 		jobAlert.setIndustryName(jobAlertDTO.getIndustry());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getSkills())){
			jobAlert.setSkillsName(jobAlertDTO.getSkills());
		}
	 	
	 	if(StringUtils.isNotBlank(jobAlertDTO.getDuration())){
	 		jobAlert.setDuration(jobAlertDTO.getDuration());
		}
	 	if(null != user){
	 		jobAlert.setUserId(user);
	 		jobAlert.setCreatedBy(user.getUserCode());
	 	}
	 	jobAlert.setStatus(Constants.RECORD_ACTIVE);
		jobAlert.setCreatedDate(commonUtil.currentDate());
		jobAlertDao.saveJobAlert(jobAlert);
		return "success";
	}

	@Override
	public List<JobAlert> getJobAlertByEmail(String email) throws Exception
	{
		return jobAlertDao.getJobAlertByEmail(email);
	}

	@Override
	public List<JobAlert> getConfiruredJobsAlertsList(String duration) throws Exception
	{
		return jobAlertDao.getConfiruredJobsAlertsList(duration);
	}

	@Override
	public JobAlert getJobAlertByUUID(String jobAlertUUID) throws Exception
	{
		return jobAlertDao.getJobAlertByUUID(jobAlertUUID);
	}

	@Override
	public void deleteJobAlert(JobAlert jobAlert) throws Exception
	{
		jobAlertDao.deleteJobAlert(jobAlert);
	}

	@Override
	public Integer getJobAlertCountByEmail(String email) throws Exception
	{
		return jobAlertDao.getJobAlertCountByEmail(email);
	}

	@Override
	public List<JobAlert> getJobAlertByEmailAndAlertDuration(String email, String duration) throws Exception {
		return jobAlertDao.getJobAlertByEmailAndAlertDuration(email, duration);
	}

	
}
