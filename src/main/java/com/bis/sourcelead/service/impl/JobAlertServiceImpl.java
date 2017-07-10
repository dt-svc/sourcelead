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
	public String saveJobAlert(JSONObject jsonObj) throws Exception {
	 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	JobAlert jobAlert = null;
	 	if(jsonObj.has("savedJobAlertId") && !"".equalsIgnoreCase(jsonObj.getString("savedJobAlertId"))){
	 		jobAlert = jobAlertDao.getJobAlertById(Long.valueOf(jsonObj.getString("savedJobAlertId")));
	 		
	 	}else{
	 		jobAlert = new JobAlert();
		 	jobAlert.setJobAlertUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
	 	}
	 	
	 	
	 	if(jsonObj.has("alertTitle") && !"".equalsIgnoreCase(jsonObj.getString("alertTitle"))){
	 		jobAlert.setAlertTitle(jsonObj.getString("alertTitle"));
		}
	 	if(jsonObj.has("email") && !"".equalsIgnoreCase(jsonObj.getString("email"))){
	 		jobAlert.setEmail(jsonObj.getString("email"));
		}else{
			jobAlert.setEmail(user.getEmail());
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
	 	
	 	if(jsonObj.has("location") && !"".equalsIgnoreCase(jsonObj.getString("location"))){
	 		jobAlert.setLocation(jsonObj.getString("location"));
		}
	 	
		if(jsonObj.has("zipcode") && !"".equalsIgnoreCase(jsonObj.getString("zipcode"))){
	 		jobAlert.setZipCode(jsonObj.getString("zipcode"));
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
