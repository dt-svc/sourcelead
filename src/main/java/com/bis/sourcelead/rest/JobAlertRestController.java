package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
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
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.JobAlertDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.JobAlert;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.JobAlertService;
import com.bis.sourcelead.service.JobAlertsHistoryService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.timesheet.rest.TimesheetRestController;
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
    
    @Autowired
    JobAlertsHistoryService jobAlertsHistoryService;
    
	@Autowired
	SourceleadELKBaseIndexer sourceleadELKBaseIndexer;
    
    protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
    
    
    @RequestMapping(value = "/app/getJobAlertList")
	 public String getJobAlertList(HttpServletRequest request) throws Exception {
		String resultJson;
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<JobAlert> jobAlertList = jobAlertService.getJobAlertByEmail(loginUser.getEmail());
		resultJson = commonUtil.toJSON(jobAlertList);
		return resultJson;
	 }
		 
    @RequestMapping(value = {"/app/ws/storeJobAlert" ,"/app/storeJobAlert"}, method = RequestMethod.POST)
    @ResponseBody
   	public Response<String> storeJobAlert(JobAlertDTO  jobAlertDTO, HttpServletRequest request, HttpServletResponse response) throws Exception
   	{
    	Response<String> responseJobAlert=new Response<>();
    	try{
    		if(Validator.validate(jobAlertDTO)){
    			String jobAlert =  jobAlertService.saveJobAlert(jobAlertDTO);
    			responseJobAlert.setResponse(Result.SUCCESS.name());
    			responseJobAlert.setResponseMessage(Result.SUCCESS.getValue());
    		}else{
    			responseJobAlert.setResponse(Result.INVALID_DATA.name());
    			responseJobAlert.setResponseMessage(Result.INVALID_DATA.getValue());
    		}
    	}catch (Exception e) {
    		responseJobAlert.setResponse(Result.AWKWARD.name());
    		responseJobAlert.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseJobAlert;
   	}
    
    
	 @RequestMapping(value = "/app/getJobAlertDetails", method = RequestMethod.GET)
	 public String getJobAlertDetails(@RequestParam(value="jobAlertUUID", required=false) String jobAlertUUID,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 
		 JobAlert jobAlert = jobAlertService.getJobAlertByUUID(jobAlertUUID);
	
		 resultJson = commonUtil.toJSON(jobAlert);
		 return resultJson;
	 }
	 
	 
	 @RequestMapping(value = "/app/deleteJobAlert", method = RequestMethod.POST)
	 public String deleteJobAlert(@RequestParam(value="jobAlertUUID", required=false) String jobAlertUUID,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 
		 JobAlert jobAlert = jobAlertService.getJobAlertByUUID(jobAlertUUID);
		 if(null != jobAlert){
			 jobAlertsHistoryService.addJobAlertsHistory(jobAlert, user);
			 jobAlertService.deleteJobAlert(jobAlert);
		 }
	
		 resultJson = commonUtil.toJSON(jobAlert);
		 return resultJson;
	 }
	 
	 
	 @RequestMapping(value = {"/app/ws/getJobDetailsForJobAlert" ,"/app/getJobDetailsForJobAlert"}, method = RequestMethod.GET)
	 public String getJobDetailsForJobAlert(@RequestParam(value="requirementUUID", required=false) String requirementUUID,HttpServletRequest request) throws Exception{ 
		 String resultJson;
		 
		 Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
	
		 resultJson = commonUtil.toJSON(requirement);
		 return resultJson;
	 }
	 
	 
	 @RequestMapping(value = {"/app/ws/validateAlertUserEmail","/app/validateAlertUserEmail"}, method = RequestMethod.GET)
	 public String validateAlertUserEmail(@RequestParam(value="email", required=false) String email,HttpServletRequest request) throws Exception{ 
		 
		 Integer jobAlertCount = jobAlertService.getJobAlertCountByEmail(email);
		 if(jobAlertCount >= 5){
			 return "Invalid";
		 }else{
			 return "valid";
		 }
	 }
		
	 
	 @RequestMapping(value = {"/getAllJobsAlertList" ,"/app/getAllJobsAlertList"})
	 	public String getAllJobsAlertList(@RequestParam(value="jobAlertUUID", required=false) String jobAlertUUID,HttpServletRequest request) throws Exception {
			    ModelAndView model = new ModelAndView();
			    String jobAlertsTable = null;
			    List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
			    
			    JobAlert jobAlert = jobAlertService.getJobAlertByUUID(jobAlertUUID);
				try{
					if(null != jobAlert){
							
						     BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
							 BoolQueryBuilder anyStatusBoolQueryBuilder = QueryBuilders.boolQuery();
						     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "NEW"));
						     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
						     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "APPROVED"));
							 boolQueryBuilder.must(anyStatusBoolQueryBuilder);
							 
							 BoolQueryBuilder anyReleaseBoolQueryBuilder = QueryBuilders.boolQuery();
							 anyReleaseBoolQueryBuilder.should(QueryBuilders.matchQuery("isPublicJob", "Y"));
							 anyReleaseBoolQueryBuilder.should(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
							 boolQueryBuilder.must(anyReleaseBoolQueryBuilder);
							 
							 
							 if(!isNullOrEmpty(jobAlert.getCity())){
								 boolQueryBuilder.must(QueryBuilders.matchQuery("city", jobAlert.getCity()));
							 }
							 
							 if(!isNullOrEmpty(jobAlert.getExperience())){
								 boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").gte(0).lte(new Integer(jobAlert.getExperience())));
							 }
							 
							 if(!isNullOrEmpty(jobAlert.getRate())){
								 boolQueryBuilder.must(QueryBuilders.matchQuery("currentRateCurrency", jobAlert.getRateCurrency()));
								 boolQueryBuilder.must(QueryBuilders.matchQuery("rateType", jobAlert.getRateType()));
								 boolQueryBuilder.must(QueryBuilders.rangeQuery("maxRate").gte(0).lte(new Integer(jobAlert.getRate())));
							 }
							 
							 if(!isNullOrEmpty(jobAlert.getIndustryName())){
								 boolQueryBuilder.must(QueryBuilders.matchQuery("industryName", jobAlert.getIndustryName().trim()));
							 }
							 
							 if(!isNullOrEmpty(jobAlert.getSkillsName())){
								 
								 ArrayList<String> skillsOrKeyWordList = null;
								 skillsOrKeyWordList = new ArrayList<String>(Arrays.asList(jobAlert.getSkillsName().split(",")));
								 if(skillsOrKeyWordList != null && !skillsOrKeyWordList.isEmpty()){
									 BoolQueryBuilder anySkillOrKeyWordBoolQueryBuilder = QueryBuilders.boolQuery();
									 
									 for(String keywd : skillsOrKeyWordList){
										 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
										ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
										if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
											for(QueryBuilder QueryBuilder :QueryBuilderList){
												anyBoolSubQueryBuilder.should(QueryBuilder);
											}
											anySkillOrKeyWordBoolQueryBuilder.should(anyBoolSubQueryBuilder);
										}
									}
									 boolQueryBuilder.must(anySkillOrKeyWordBoolQueryBuilder);
								 }
								 
							 }
							 
							 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0, Constants.MAX_RESULTS_TWENTY,boolQueryBuilder).getHits();

					 	     if(null != searchHits && searchHits.length >0){
					 	    	ObjectMapper mapper = new ObjectMapper();
					 	    	for (SearchHit hit : searchHits) {
					 	    		StringBuilder json = new StringBuilder(hit.getSourceAsString());
					 	    	    RequirementIndex requirementIndex = mapper.readValue(json.toString(), RequirementIndex.class);
					 	    	    requirementIndex.setJobAlertTitle(jobAlert.getAlertTitle());
					 	    	    requirementList.add(requirementIndex);
					 	    	 }
					 	     }
						
						  jobAlertsTable = getJobAlertsTable(requirementList);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			    
			    
	 			return jobAlertsTable;
	 	}
		
		
		private ArrayList<QueryBuilder> getQueryBuilderList(String keywd){
			   
			   ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
			    QueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
			    QueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
				QueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
				QueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
				QueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
				QueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
				
				QueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
				QueryBuilderList.add(QueryBuilders.matchQuery("jobId", keywd));
				
				QueryBuilderList.add(QueryBuilders.matchQuery("industryName", keywd));
				
				QueryBuilderList.add(QueryBuilders.matchQuery("description", keywd));
				
				
				Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
				if(matcher.find()){
					Float expYears = Float.valueOf(matcher.group());
					QueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
					QueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
					QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
				}

					
				return QueryBuilderList; 
		 }
		
		
		
		 private String getJobAlertsTable(List<RequirementIndex> requirementList) throws Exception{
			 String tempHTML = null;
			 
				 
				 tempHTML = "<table align='center' style='border-spacing:0;font-family:Arial, sans-serif;color:#333333;Margin:0 auto;width:100%; overflow-x:auto;border-collapse:collapse; border:1px solid #ddd;' >";
				 tempHTML = tempHTML+"<tbody>";
				 tempHTML = tempHTML+"<tr style='border:0;border-collapse:collapse;'>";
				 tempHTML = tempHTML+"<td style='padding-top:10px;padding-bottom:0px;padding-right:10px;padding-left:10px;background-color:#ffffff;'>";
				 tempHTML = tempHTML+"<table width='100%' style='border-spacing:0;font-family:sans-serif;color:#333333'>";
				 tempHTML = tempHTML+"<tbody>";
				
				 for(RequirementIndex requirementIndex : requirementList){
					 
							 
							 StringBuilder jobTitle = new StringBuilder();
							 if(!isNullOrEmpty(requirementIndex.getJobId())){
								 jobTitle = jobTitle.append(requirementIndex.getJobId()).append("-").append(requirementIndex.getJobTitle());
							 }else{
								 jobTitle = jobTitle.append(requirementIndex.getJobTitle());
							 }
							 
							 StringBuilder experience = new StringBuilder();
							 
							 if(requirementIndex.getMinExperience() !=  null && requirementIndex.getMaxExperience() != null){
								 experience = experience.append("(").append(requirementIndex.getMinExperience()).append("-").append(requirementIndex.getMaxExperience()).append(" Years").append(")");
							 }
							 else if(requirementIndex.getMinExperience() !=  null && requirementIndex.getMaxExperience() == null){
								 experience = experience.append("(").append(requirementIndex.getMinExperience()).append(" Years").append(")");
							 }
							 else if(requirementIndex.getMinExperience() ==  null && requirementIndex.getMaxExperience() != null){
								 experience = experience.append("(").append(requirementIndex.getMaxExperience()).append(" Years").append(")");
							 }
							 
							 StringBuilder location = new StringBuilder();
							 if(!isNullOrEmpty(requirementIndex.getCity())){
								 location = location.append(requirementIndex.getCity());
							 }
							 
							 StringBuilder jobSkills = new StringBuilder();
							 String skills = null;
							 if(null != requirementIndex.getSkills()){
								 String[] skillsList = requirementIndex.getSkills();
								 for(String skill : skillsList){
									 jobSkills = jobSkills.append(skill).append(", ");
								 }
								 
								 jobSkills.deleteCharAt(jobSkills.length()-2) ;
								 skills = jobSkills.toString();
							 }else{
								 skills = "----";
							 }
							 
							 
							 tempHTML = tempHTML+"<tr>";
							 tempHTML = tempHTML+"<td style='width:100%;padding:0px 0px 10px 0px;text-align:left;border-bottom:1px solid #ddd; line-height: 18px;'>";
							 tempHTML = tempHTML+"<p style='Margin:0;font-size:14px;Margin-bottom:2px;color:#1976d2;padding:0;text-decoration:none;''>";
							 tempHTML = tempHTML+"<a style='text-decoration:none;' href='"+sourceleadUrl+"/"+"jobAlertRequirementSummary?requirementUUID="+requirementIndex.getRequirementUUID()+"'> "+jobTitle.toString()+"  "+experience.toString()+" </a>";
							 tempHTML = tempHTML+"</p>";
							 tempHTML = tempHTML+"<p style='Margin:0;font-size:13px;Margin-bottom:1px;padding:0;'>"+requirementIndex.getPostedOrg()+" </p>";
							 tempHTML = tempHTML+"<p style='Margin:0;font-size:13px;Margin-bottom:1px;color:#757575;padding:0;'> "+location.toString()+" </p>";
							 tempHTML = tempHTML+"<p style='Margin:0;Margin-bottom:0px;color:#757575;font-size:13px;padding:0;'>";
							 tempHTML = tempHTML+"<b>Key skills:</b> "+skills+" </p>";
							 tempHTML = tempHTML+"</td>";
							 tempHTML = tempHTML+"</tr>";
				 }
					
				 
				 tempHTML = tempHTML+"</tbody>";
				 tempHTML = tempHTML+"</table>";
				 tempHTML = tempHTML+"</td>";
				 tempHTML = tempHTML+"</tr>";;
				 tempHTML = tempHTML+"<tr style='background-color:#ffffff;min-height:32px;'>";
				 tempHTML = tempHTML+"<td style='padding-top:15px;padding-bottom:15px;padding-right:10px;padding-left:10px;'>";
				 tempHTML = tempHTML+"<table width='100%' style='border-spacing:0;font-family:sans-serif;color:#ffffff;'>";
				 tempHTML = tempHTML+"<tbody>";
				 tempHTML = tempHTML+"<tr>";
				 tempHTML = tempHTML+"<td style='cursor:pointer;padding-bottom:10px;padding-left:0;vertical-align:top;'>";
				 
				 tempHTML = tempHTML+"</tr>";
				 tempHTML = tempHTML+"</tbody>";
				 tempHTML = tempHTML+"</table>";
				 tempHTML = tempHTML+"</td>";
				 tempHTML = tempHTML+"</tr>";
				 tempHTML = tempHTML+"</tbody>";
				 tempHTML = tempHTML+"</table>";
			 
			 return tempHTML;
		 }
		

}
