package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.JobAlertService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class JobAlertRestController {
	
private static final Logger logger = LoggerFactory.getLogger(TimesheetRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
    @Autowired
	CommonUtil commonUtil;
    
    @Autowired
    JobAlertService jobAlertService;
    
    @Autowired
    RequirementService requirementService;
    
    protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
    
    
    @RequestMapping(value = "/getJobAlertList")
	 public String getJobAlertList(HttpServletRequest request) throws Exception {
		String resultJson;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<JobAlert> jobAlertList = jobAlertService.getJobAlertByEmail(loginUser.getEmail());
		resultJson = commonUtil.toJSON(jobAlertList);
		return resultJson;
	 }
		 
    @RequestMapping(value = "/storeJobAlert", method = RequestMethod.POST)
   	public @ResponseBody  void storeJobAlert(@RequestParam(value="json", required=false) String json,HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
       	 String json1 = "{"+json+"}";
   		    JSONObject jsonObj = new JSONObject(json1);
       	  String jobAlert =  jobAlertService.saveJobAlert(jsonObj);
   	}
    
    
	 @RequestMapping(value = "/getJobAlertDetails", method = RequestMethod.GET)
	 public String getJobAlertDetails(@RequestParam(value="jobAlertUUID", required=false) String jobAlertUUID,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 
		 JobAlert jobAlert = jobAlertService.getJobAlertByUUID(jobAlertUUID);
	
		 resultJson = commonUtil.toJSON(jobAlert);
		 return resultJson;
	 }
	 
	 
	 @RequestMapping(value = "/deleteJobAlert", method = RequestMethod.POST)
	 public String deleteJobAlert(@RequestParam(value="jobAlertUUID", required=false) String jobAlertUUID,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 
		 JobAlert jobAlert = jobAlertService.getJobAlertByUUID(jobAlertUUID);
		 if(null != jobAlert){
			 jobAlertService.deleteJobAlert(jobAlert);
		 }
	
		 resultJson = commonUtil.toJSON(jobAlert);
		 return resultJson;
	 }
	 
	 
	 @RequestMapping(value = "/getJobDetailsForJobAlert", method = RequestMethod.GET)
	 public String getJobDetailsForJobAlert(@RequestParam(value="requirementUUID", required=false) String requirementUUID,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 
		 Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
	
		 resultJson = commonUtil.toJSON(requirement);
		 return resultJson;
	 }
		

}
