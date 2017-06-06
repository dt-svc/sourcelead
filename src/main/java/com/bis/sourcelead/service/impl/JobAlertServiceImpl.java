package com.bis.sourcelead.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.DepartmentDao;
import com.bis.sourcelead.dao.JobAlertDao;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.DepartmentService;
import com.bis.sourcelead.service.JobAlertService;
import com.bis.sourcelead.util.CommonUtil;
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
	public String saveJobAlert(JSONObject jsonObj) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	JobAlert jobAlert = new JobAlert();
	 	
	 	if(jsonObj.has("alertTitle") && !"".equalsIgnoreCase(jsonObj.getString("alertTitle"))){
	 		jobAlert.setAlertTitle(jsonObj.getString("alertTitle"));
		}
	 	
	 	if(jsonObj.has("country") && !"".equalsIgnoreCase(jsonObj.getString("country"))){
	 		jobAlert.setCountry(jsonObj.getString("country"));
		}
	 	
	 	if(jsonObj.has("state") && !"".equalsIgnoreCase(jsonObj.getString("state"))){
	 		jobAlert.setState(jsonObj.getString("state"));
		}
	 	
	 	if(jsonObj.has("city") && !"".equalsIgnoreCase(jsonObj.getString("city"))){
	 		jobAlert.setCity(jsonObj.getString("city"));
		}
	 	
	 	if(jsonObj.has("experience") && !"".equalsIgnoreCase(jsonObj.getString("experience"))){
	 		jobAlert.setExperience(jsonObj.getString("experience"));
		}
	 	
	 	if(jsonObj.has("rateCurrency") && !"".equalsIgnoreCase(jsonObj.getString("rateCurrency"))){
	 		jobAlert.setRateCurrency(jsonObj.getString("rateCurrency"));
		}
	 	
	 	
	 	if(jsonObj.has("rate") && !"".equalsIgnoreCase(jsonObj.getString("rate"))){
	 		jobAlert.setRate(jsonObj.getString("rate"));
		}
	 	
	 	if(jsonObj.has("rateType") && !"".equalsIgnoreCase(jsonObj.getString("rateType"))){
	 		jobAlert.setRateType(jsonObj.getString("rateType"));
		}
	 	
	 	if(jsonObj.has("industry") && !"".equalsIgnoreCase(jsonObj.getString("industry"))){
	 		jobAlert.setIndustryName(jsonObj.getString("industry"));
		}
	 	
	 	if(jsonObj.has("skills")){
			String skillId =null;
			try{
				JSONArray jsonArray = jsonObj.getJSONArray("skills");
				skillId = jsonArray.toString();
			}
			catch(JSONException e){
				skillId =jsonObj.getString("skills");
			}
			skillId = skillId.replaceAll("\\[", "").replaceAll("\\]","");
			
			
			skillId = skillId.replaceAll("\"", "");
			jobAlert.setSkillsName(skillId);
	  	 
		}
	 
	 	
	 	if(jsonObj.has("duration") && !"".equalsIgnoreCase(jsonObj.getString("duration"))){
	 		jobAlert.setDuration(jsonObj.getString("duration"));
		}
	 	jobAlert.setEmail(user.getEmail());
	 	jobAlert.setUserId(user);
	 	jobAlert.setStatus(Constants.RECORD_ACTIVE);
		jobAlert.setCreatedDate(commonUtil.currentDate());
		jobAlert.setCreatedBy(user.getUserCode());
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

	
}
