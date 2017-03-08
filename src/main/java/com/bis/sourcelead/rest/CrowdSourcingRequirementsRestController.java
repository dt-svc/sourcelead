package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CrowdSourcingUserBankAccountsService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
public class CrowdSourcingRequirementsRestController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	RequirementSubmissionHistoryService requirementSubmissionHistoryService;

	@Autowired
	RequirementElkUtil requirementElkUtil;
	
	@Autowired
	SourceleadELKBaseIndexer<CampaignIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	RequirementInterviewHistoryService requirementInterviewHistoryService;
	
	@Autowired
	RequirementReferService requirementReferService;
	
	@Autowired
	ReferContactsService referContactsService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	CrowdSourcingUserBankAccountsService crowdSourcingUserBankAccountsService;
	
	@Autowired
	RequirementInterviewService requirementInterviewService;
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	
	@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
	@RequestMapping(value = "/getCrowdSourcingRequirements")
	public @ResponseBody String getCrowdSourcingRequirements(@RequestParam(value = "requirementType", required = false) String requirementType,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "postedOn", required = false) String postedOn,
			@RequestParam(value = "jobTitleSearch", required = false) String jobTitleSearch,
			@RequestParam(value = "jobLocationSearch", required = false) String jobLocationSearch,
			@RequestParam(value = "referJobType", required = false) String referJobType,
			HttpServletRequest request) throws Exception {

		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		boolean isELKSearchEnable = true;
		
	    String resultJson;
	    List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    df.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	    if(isNullOrEmpty(referJobType) || (!isNullOrEmpty(referJobType) && !referJobType.equalsIgnoreCase("ReferredToMe"))){
	    
	    BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
	     if(requirementType.equalsIgnoreCase("OpenJobs")){
	    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isPublicJob", "Y"));
	    	 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("appliedProfessionals", user.getId()));
	     }
	     else if (requirementType.equalsIgnoreCase("CrowdSourcingJobs")){
	    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
	    	 
	    	 if(!isNullOrEmpty(referJobType)){
	    		 if(referJobType.equalsIgnoreCase("ReferredByMe")){
	    			 List<Long> referedRequirementList = requirementReferService.getReferByMeRequirementsByUserId(user.getId(), offset,Constants.MAX_RESULTS_TEN);
	    			 if(referedRequirementList.size() > 0){
	    				 BoolQueryBuilder subBoolQueryBuilder = new BoolQueryBuilder();
	    				 for(int i=0; i<referedRequirementList.size(); i++){
	    					 subBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementId", referedRequirementList.get(i)));
		    			 } 
	    				 
	    				 boolQueryBuilder.must(subBoolQueryBuilder);
	    				 
	    			 }else{
	    				 isELKSearchEnable = false;
	    			 }
	    			 
	    		 }
	    	 }
	    	
	    	 
	     }
	     else if (requirementType.equalsIgnoreCase("AppliedJobs")){
	    	 if(!isNullOrEmpty(loginUserRole)){
	    		 if(loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL")){
	    			 boolQueryBuilder.must(QueryBuilders.matchQuery("appliedProfessionals", user.getId()));
	    		 }else if(loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
	    			 boolQueryBuilder.must(QueryBuilders.matchQuery("appliedRecruiters", user.getId()));
	    		 }
	    	 }
	    	
	     }
	     
	     if(!isNullOrEmpty(postedOn)){
				String upOn = UpdateOnToDate(postedOn);
				if(!Constants.ALL_RECORDS.equalsIgnoreCase(upOn)){
				 RangeQueryBuilder qb = QueryBuilders.rangeQuery("createdDate").gte(upOn);
				boolQueryBuilder.must(qb);
				}
			}
	     
	     if(!isNullOrEmpty(jobTitleSearch)){
	    	 BoolQueryBuilder anyJobSearchBoolQueryBuilder = QueryBuilders.boolQuery();
	    	 anyJobSearchBoolQueryBuilder.should(QueryBuilders.matchQuery("jobTitle", jobTitleSearch));
	    	 anyJobSearchBoolQueryBuilder.should(QueryBuilders.matchQuery("jobId", jobTitleSearch));
	    	 boolQueryBuilder.must(anyJobSearchBoolQueryBuilder);
	     }
	     
	     if(!isNullOrEmpty(jobLocationSearch)){
	    	 BoolQueryBuilder anyLocationBoolQueryBuilder = QueryBuilders.boolQuery();
	    	 anyLocationBoolQueryBuilder.should(QueryBuilders.matchQuery("country", jobLocationSearch));
	    	 anyLocationBoolQueryBuilder.should(QueryBuilders.matchQuery("state", jobLocationSearch));
	    	 anyLocationBoolQueryBuilder.should(QueryBuilders.matchQuery("city", jobLocationSearch));
	    	 boolQueryBuilder.must(anyLocationBoolQueryBuilder);
	     }
		 
		 boolQueryBuilder.must(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
		
	   
	    try {
	    	SearchHit[] searchHits = null;
	    	if(isELKSearchEnable){
	    		 searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,offset, Constants.MAX_RESULTS_TEN,boolQueryBuilder).getHits();
	    	}
	    	
	 	     if(null != searchHits && searchHits.length >0){
	 	    	ObjectMapper mapper = new ObjectMapper();
	 	    	for (SearchHit hit : searchHits) {
	 	    		StringBuilder json = new StringBuilder(hit.getSourceAsString());
	 	    		mapper.setDateFormat(df);
	 	    	    RequirementIndex requirementIndex = mapper.readValue(json.toString(), RequirementIndex.class);
	 	    	    
	 	    	   if (requirementType.equalsIgnoreCase("CrowdSourcingJobs")){
	 	    		   if(null !=  requirementIndex.getAppliedProfessionals()){
	 	    			  List<Long> userId = new ArrayList<Long>();
		 					for (String s : requirementIndex.getAppliedProfessionals()){
		 						userId.add(Long.parseLong(s));
		 					 }
		 					boolean flag = userId.contains(user.getId());
		 					if(flag == true){
		 						requirementIndex.setIsJobApplied(Constants.YES);
		 					}else{
		 						requirementIndex.setIsJobApplied(Constants.NO);
		 					} 
	 	    		     }else{
	 	    			    requirementIndex.setIsJobApplied(Constants.NO);
	 	    		      }
	 	    	   }
	 	    	    
	 	    	   if (requirementType.equalsIgnoreCase("AppliedJobs")){
	 	    		  if(!isNullOrEmpty(loginUserRole)){
	 		    		 if(loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL")){
	 		    			 RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
	 		 	    		   if(null != submission){
	 		 	    			  requirementIndex.setRequirementAppliedDate(Constants.DF_YMD.format(submission.getCreatedDate()));
	 		 	    		   }
	 		    		 }else if(loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
	 		    			 Integer submissionCount = requirementSubmissionService.getRecruiterSubmissionsCountByRecruiterIdAndRequirementId(requirementIndex.getRequirementId(), user.getId());
	 		    			 requirementIndex.setRecruiterSubmissionsCount(submissionCount);
	 		    		 }
	 	    		  }
	 	    		  
	 	    		   
	 	    		   RequirementRefer requirementRefer = requirementReferService.getAppliedRequirementReferByRequirementIdAndUserId(requirementIndex.getRequirementId(), user.getId());
	 	    		   if(null != requirementRefer){
	 	    			   StringBuilder referedUser = new StringBuilder();
	 	    			   if(!isNullOrEmpty(requirementRefer.getReferFromUser().getLastName())){
	 	    				  referedUser = referedUser.append(requirementRefer.getReferFromUser().getFirstName()).append(" ").append(requirementRefer.getReferFromUser().getLastName());
	 	    			   }else{
	 	    				  referedUser = referedUser.append(requirementRefer.getReferFromUser().getFirstName());
	 	    			   }
	 	    			  requirementIndex.setReferedUser(referedUser.toString());
	 	    		   }
	 	    		   
	 	    	   }
	 	    	    requirementIndex.setNoOfSearchHits(searchHits.length);
	 	    	    requirementList.add(requirementIndex);
	 	    	}
	 	    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    
	    }else{
	    	
	    	 if(referJobType.equalsIgnoreCase("ReferredToMe")){
    			 List<RequirementRefer> referedToRequirementList = requirementReferService.getReferedToMeRequirementsByUserId(user.getId(), offset,Constants.MAX_RESULTS_TEN,jobTitleSearch,jobLocationSearch,postedOn);
    			 if(null != referedToRequirementList && referedToRequirementList.size() > 0){
    				 for(RequirementRefer refer : referedToRequirementList){
    					 RequirementIndex requirementIndex = new RequirementIndex();
    					 requirementIndex.setRequirementId(refer.getRequirementId().getId());
    					 requirementIndex.setJobTitle(refer.getRequirementId().getJobTitle());
    					 requirementIndex.setJobId(refer.getRequirementId().getJobId());
    					 requirementIndex.setPostedOrg(refer.getRequirementId().getPostedOrgName());
    					 requirementIndex.setJobType(refer.getRequirementId().getJobType());
    					 requirementIndex.setJobId(refer.getRequirementId().getJobId());
    					 requirementIndex.setMinExperience(refer.getRequirementId().getMinExp());
    					 requirementIndex.setMaxExperience(refer.getRequirementId().getMaxExp());
    					 requirementIndex.setCountry(refer.getRequirementId().getCountry());
    					 requirementIndex.setState(refer.getRequirementId().getState());
    					 requirementIndex.setCity(refer.getRequirementId().getCity());
    					 if(!isNullOrEmpty(refer.getRequirementId().getRate())){
    						 requirementIndex.setRate(Float.parseFloat(refer.getRequirementId().getRate()));
    					 }
    					
    					 requirementIndex.setRateType(refer.getRequirementId().getRateType());
    					 
    					 if(!isNullOrEmpty(refer.getRequirementId().getSkills())){
    						 String[] skills = refer.getRequirementId().getSkills().split(",");
    						 List<String> skillsList = new ArrayList<String>();
    						 for(int i = 0 ; i < skills.length; i++ ){
    							 skillsList.add(skills[i]);
    						 }
        					 requirementIndex.setSkillsList(skillsList);
    					 }
    					 
    					  if(!isNullOrEmpty(refer.getRequirementId().getAppliedProfessionals())){
    	 	    			  List<Long> userId = new ArrayList<Long>();
    		 					for (String s : refer.getRequirementId().getAppliedProfessionals().split(",")){
    		 						userId.add(Long.parseLong(s));
    		 					 }
    		 					boolean flag = userId.contains(user.getId());
    		 					if(flag == true){
    		 						requirementIndex.setIsJobApplied(Constants.YES);
    		 					}else{
    		 						requirementIndex.setIsJobApplied(Constants.NO);
    		 					} 
    	 	    		     }else{
    	 	    			    requirementIndex.setIsJobApplied(Constants.NO);
    	 	    		      }
    					  
    					  StringBuilder referedUser = new StringBuilder();
    					  if(!isNullOrEmpty(refer.getReferFromUser().getLastName())){
    						  referedUser = referedUser.append(refer.getReferFromUser().getFirstName()).append(" ").append(refer.getReferFromUser().getLastName());
    					  }else{
    						  referedUser = referedUser.append(refer.getReferFromUser().getFirstName());
    					  }
    					  requirementIndex.setReferedUser(referedUser.toString());
    					  requirementIndex.setReferId(refer.getId());
    					  
    					  requirementIndex.setNoOfSearchHits(referedToRequirementList.size());
    					  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    					  requirementIndex.setCreatedDate(dateFormat.format(refer.getRequirementId().getCreatedDate()));
    					  
    					  
    					  if(null != refer.getReferStatus() && refer.getReferStatus().equalsIgnoreCase(Constants.REFER_JOB_APPLIED)){
    						  requirementIndex.setIsReferedRequiremetnApplied(Constants.YES);
    					  }else{
    						  requirementIndex.setIsReferedRequiremetnApplied(Constants.NO);
    					  }
    					  
    					  requirementIndex.setRewardType(refer.getRequirementId().getRewardType());
    					  requirementIndex.setRewardPercentage(refer.getRequirementId().getRewardPercentage());
    					  requirementIndex.setRewardFlat(refer.getRequirementId().getRewardFlat());
    					  requirementIndex.setRewardFlatRateType(refer.getRequirementId().getRewardFlatRateType());
    					  requirementIndex.setRewardGuaranteePeriod(refer.getRequirementId().getRewardGuaranteePeriod());
    					 
    					 requirementList.add(requirementIndex);
	    			 } 
    			 }else{
    				 isELKSearchEnable = false;
    			 }
    			 
    		 }
	    }
	    
	    if(requirementList != null){
			calculateDaysAndSkills(requirementList);
		}
	   
	    resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	}
	
	 public String UpdateOnToDate(String updatedOn){
			
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     if((Constants.ONE_DAY_AGO).equalsIgnoreCase(updatedOn)){
	        cal.add(Calendar.DATE, -1);    
	        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_DAY_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -3);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_WEEK_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -7);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.TWO_WEEK_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -14);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.ONE_MONTH_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -30);    
		        return  dateFormat.format(cal.getTime());
		 }else if((Constants.THREE_MONTH_AGO).equalsIgnoreCase(updatedOn)){
			 cal.add(Calendar.DATE, -90);    
		        return  dateFormat.format(cal.getTime());
		 }else{
			 return updatedOn;
		 }
	 }
	
	
	public void calculateDaysAndSkills(List<RequirementIndex> requirementList) throws ParseException {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (RequirementIndex requirement : requirementList) {
			long diff = commonUtil.currentDate().getTime() - formatter.parse(requirement.getCreatedDate()).getTime();
			
			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder  time = new StringBuilder();
			
			if(diffDays == 0){
				requirement.setCalDays("Today");
			}
			else if(diffDays > 0){
				requirement.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			}
			
			
			if(null != requirement.getSkills()){
				List<String> skillsList = new ArrayList<String>();
				String[] skills = requirement.getSkills();
				for(int i=0; i< skills.length; i++){
					skillsList.add(skills[i]);
				}
				requirement.setSkillsList(skillsList);
			}

		}

	}
	
	
	
	
	@PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
	@RequestMapping(value = "/applyToJobFromCrowdSourcing", method = RequestMethod.POST)
	public String applyToJobFromCrowdSourcing(@RequestParam(value = "jsonData", required = false) String json,
			@RequestParam(value = "con", required = false) String con, MultipartHttpServletRequest request)
			throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		RequirementSubmission requirementSubmission = requirementSubmissionService.saveRequirementSubmissionFromCrowdSourcing(jsonObj,user, con);
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().getValue());
		requirementSubmissionHistory.setFromStage("NEW");
		
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory);
		Requirement requirement = requirementSubmission.getRequirement();
		Integer count = requirementSubmissionService.getNumberOfApplicantsForRequirement(requirement.getOrgLocationId().getId(), requirement.getId());
		requirement.setTotalApplicantsForRequirement(count);
		requirementElkUtil.saveElkRequirement(requirement,new JSONObject());
		return "success";

	}
	
	@PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
	@RequestMapping(value = "/saveBankAccountDetails", method = RequestMethod.POST)
	public String saveBankAccountDetails(@RequestParam(value = "jsonData", required = false) String json,HttpServletRequest request)
			throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		CrowdSourcingUserBankAccounts bankAccount = null;
		
		bankAccount = crowdSourcingUserBankAccountsService.getUserBankAccountByUserId(user.getId());
		
		if(null == bankAccount){
			bankAccount = new CrowdSourcingUserBankAccounts();
			bankAccount.setAccountUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			bankAccount.setCreatedDate(new Date());
		}
		
		bankAccount.setAccountUserId(user);
		bankAccount.setAccountHolderName(jsonObj.getString("accountHolderName"));
		bankAccount.setCountry(jsonObj.getString("country"));
		bankAccount.setCurrency(jsonObj.getString("currency"));
		if(jsonObj.has("bankName")){
		  bankAccount.setBankName(jsonObj.getString("bankName"));
		}
		bankAccount.setAccountNumber(jsonObj.getString("accountNumber"));
		bankAccount.setBankCode(jsonObj.getString("bankCode"));
		bankAccount.setCreatedBy(user.getUserCode());
		bankAccount.setUpdatedBy(user.getUserCode());
		bankAccount.setUpdatedDate(new Date());
		
		crowdSourcingUserBankAccountsService.addUserBankAccounts(bankAccount);
		
		return "success";

	}
	
	
	 @RequestMapping(value = "/searchCrowdSourcingRequiremets",method = RequestMethod.GET)
		public @ResponseBody  String searchCrowdSourcingRequiremets(@RequestParam(value = "json", required = false) String json,
				@RequestParam(value = "requirementType", required = false) String requirementType,
				@RequestParam(value = "offset", required = false) Integer offset,
				HttpServletRequest request,HttpServletResponse response) throws Exception {
		String resultJson = "";
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		    List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
		    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		    
		     if(requirementType.equalsIgnoreCase("OpenJobs")){
		    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isPublicJob", "Y"));
		    	 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("appliedProfessionals", user.getId()));
		     }
		     else if (requirementType.equalsIgnoreCase("CrowdSourcingJobs")){
		    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
		     }
		     
		     boolQueryBuilder = getSearchRequirementsResults(boolQueryBuilder,jsonObj);   
		     
		     boolQueryBuilder.must(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
				
			   
			    try {
			    	 SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,offset, Constants.MAX_RESULTS_TEN,boolQueryBuilder).getHits();
			 	     if(null != searchHits && searchHits.length >0){
			 	    	ObjectMapper mapper = new ObjectMapper();
			 	    	for (SearchHit hit : searchHits) {
			 	    		StringBuilder json1 = new StringBuilder(hit.getSourceAsString());
			 	    		mapper.setDateFormat(df);
			 	    	    RequirementIndex requirementIndex = mapper.readValue(json1.toString(), RequirementIndex.class);
			 	    	    
			 	    	   if (requirementType.equalsIgnoreCase("CrowdSourcingJobs")){
			 	    		   if(null !=  requirementIndex.getAppliedProfessionals()){
			 	    			  List<Long> userId = new ArrayList<Long>();
				 					for (String s : requirementIndex.getAppliedProfessionals()){
				 						userId.add(Long.parseLong(s));
				 					 }
				 					boolean flag = userId.contains(user.getId());
				 					if(flag == true){
				 						requirementIndex.setIsJobApplied(Constants.YES);
				 					}else{
				 						requirementIndex.setIsJobApplied(Constants.NO);
				 					} 
			 	    		     }else{
			 	    			    requirementIndex.setIsJobApplied(Constants.NO);
			 	    		      }
			 	    	   }
			 	    	    
			 	    	   if (requirementType.equalsIgnoreCase("AppliedJobs")){
			 	    		   RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
			 	    		   if(null != submission){
			 	    			  requirementIndex.setRequirementAppliedDate(Constants.DF_YMD.format(submission.getCreatedDate()));
			 	    		   }
			 	    	   }
			 	    	    requirementIndex.setNoOfSearchHits(searchHits.length);
			 	    	    requirementList.add(requirementIndex);
			 	    	}
			 	    }
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			     
			    if(requirementList != null){
					calculateDaysAndSkills(requirementList);
				}
			    resultJson = commonUtil.toJSON(requirementList);
				return resultJson;
	    
	    
     }
	 
	 
	 BoolQueryBuilder getSearchRequirementsResults(BoolQueryBuilder boolQueryBuilder,JSONObject jsonObj){
			
			 try {
			
			 if(jsonObj.has("excludeKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("excludeKeyWord"))){
				 ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
				 if(excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()){
				 	 BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
					 for(String keywd : excludeKeyWordArray){
						 BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<MatchQueryBuilder> matchQueryBuilderList = getMatchQueryBuilderList(keywd);
						if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
							for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
								excludeBoolSubQueryBuilder.should(matchQueryBuilder);
							}
							excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
						}
					}
					 boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
				  } 
			 }
				
				//Any Keyword
			 if(jsonObj.has("anyKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("anyKeyWord"))){
				 ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyKeyWord");
					if(anyKeyWordArray != null && !anyKeyWordArray.isEmpty()){
						 BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : anyKeyWordArray){
							 BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
							if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
								for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
									anyBoolSubQueryBuilder.should(matchQueryBuilder);
								}
								anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
							}
						}
						 boolQueryBuilder.must(anyBoolQueryBuilder);
					} 
			 }
			
			//All Keywords
			 if(jsonObj.has("allKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("allKeyWord"))){
					ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeyWord");
					if(allKeyWordArray != null && !allKeyWordArray.isEmpty()){
						 BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
						 for(String keywd : allKeyWordArray){
							 BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
							ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
							if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
								for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
									allBoolSubQueryBuilder.should(matchQueryBuilder);
								}
								allBoolQueryBuilder.must(allBoolSubQueryBuilder);
							}
						}
						 boolQueryBuilder.must(allBoolQueryBuilder);
					}
			 }
				
				
				//Requirement Title
				if(jsonObj.has("requirementTitle") && !"".equalsIgnoreCase(jsonObj.getString("requirementTitle"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("jobTitle", jsonObj.getString("requirementTitle")));
			    }
			 //Country
				if(jsonObj.has("country") && !"".equalsIgnoreCase(jsonObj.getString("country"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("country", jsonObj.getString("country")));
			    }
				 //State
				if(jsonObj.has("state") && !"".equalsIgnoreCase(jsonObj.getString("state"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("state", jsonObj.getString("state")));
			    }
				//City
				if(jsonObj.has("city")  && !"".equalsIgnoreCase(jsonObj.getString("city"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("city", jsonObj.getString("city")));
			    }
				
				// currency
			    if(jsonObj.has("currencyCode") && !"".equalsIgnoreCase(jsonObj.getString("currencyCode"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("currentRateCurrency", jsonObj.getString("currencyCode")));
			    }
			    
			  //minRate && maxRate
			    if(jsonObj.has("minRate") && !"".equalsIgnoreCase(jsonObj.getString("minRate")) && jsonObj.has("maxRate")  && !"".equalsIgnoreCase(jsonObj.getString("maxRate"))){
			    	String minRate= jsonObj.getString("minRate").replaceAll("[^0-9\\.]+", "");
			    	String maxRate= jsonObj.getString("maxRate").replaceAll("[^0-9\\.]+", "");
			    	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("rate").gte(new Integer(minRate)).lte(new Integer(maxRate)));
			    	}
			    	catch(NumberFormatException e){}
			    }
				
				
			   //Rate Type
				if(jsonObj.has("rateType") && !"".equalsIgnoreCase(jsonObj.getString("rateType"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("rateType", jsonObj.getString("rateType")));
			    }
				
				
				//minExpYears && maxExpYears
			    if(jsonObj.has("minExp") && !"".equalsIgnoreCase(jsonObj.getString("minExp")) && jsonObj.has("maxExp")  && !"".equalsIgnoreCase(jsonObj.getString("maxExp"))){
			    	String minYears= jsonObj.getString("minExp").replaceAll("[^0-9\\.]+", "");
			    	String maxYears= jsonObj.getString("maxExp").replaceAll("[^0-9\\.]+", "");
			    	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").gte(new Integer(minYears)).lte(new Integer(maxYears)));
			    	}
			    	catch(NumberFormatException e){}
			    }
				//minExpYears
			    else if(jsonObj.has("minExp") && !"".equalsIgnoreCase(jsonObj.getString("minExp"))){
			    	String minYears= jsonObj.getString("minExp").replaceAll("[^0-9\\.]+", "");
			    	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").gte(new Integer(minYears)));
			    	}
			    	catch(NumberFormatException e){}
			    }
			    //maxExpYears
			    else if(jsonObj.has("maxExp") && !"".equalsIgnoreCase(jsonObj.getString("maxExp"))){
	           	String maxYears= jsonObj.getString("maxExp").replaceAll("[^0-9\\.]+", "");
	           	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").lte(new Integer(maxYears)));
			    	}
			    	catch(NumberFormatException e){}
			    }
				
			   
				
				//Requirement Skills
				if(jsonObj.has("skills") && !"".equalsIgnoreCase(jsonObj.getString("skills"))){
					ArrayList<String> candidateSkillsArray = getSearchKeywordsFromChosenSelectField(jsonObj, "skills");
					if(candidateSkillsArray != null && !candidateSkillsArray.isEmpty()){
						BoolQueryBuilder skillBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : candidateSkillsArray){
							skillBoolQueryBuilder.should(QueryBuilders.matchQuery("skills", keywd));
						}
						boolQueryBuilder.must(skillBoolQueryBuilder);
					} 	
				}
				
				//Requirement Skills
				if(jsonObj.has("organizations") && !"".equalsIgnoreCase(jsonObj.getString("organizations"))){
					ArrayList<String> organizationArray = getSearchKeywordsFromChosenSelectField(jsonObj, "organizations");
					if(organizationArray != null && !organizationArray.isEmpty()){
						BoolQueryBuilder skillBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : organizationArray){
							skillBoolQueryBuilder.should(QueryBuilders.matchQuery("postedOrg", keywd));
						}
						boolQueryBuilder.must(skillBoolQueryBuilder);
					} 	
				}
				
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			return boolQueryBuilder;
			
		}
	 
	 
	 
	 private ArrayList<String> getSearchKeywordsFromChosenSelectField(JSONObject jsonObj,String param) throws Exception {
		 ArrayList<String> keyWordList = null;
		 if(jsonObj.has(param)){
			 String searchKeywordString = null;
			 try{
	   		   JSONArray jsonArray = jsonObj.getJSONArray(param);
	   		   searchKeywordString = jsonArray.toString();
		   	  }
		   	  catch(JSONException e){
		   		  searchKeywordString= jsonObj.getString(param);
		   	  }
			 
		     searchKeywordString = searchKeywordString.replaceAll("\\[", "").replaceAll("\\]","");
		     keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		 }
		 else{
			 keyWordList = new ArrayList<String>();
		 }
         return keyWordList;
     }
	 
	 
	 private ArrayList<MatchQueryBuilder> getMatchQueryBuilderList(String keywd){
		   
		   ArrayList<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("client", keywd));
			
			matchQueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("jobId", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("clientContactName", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("clientContactEmail", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("manager", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("priority", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("numberOfPositions", keywd));
			
			
			Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(matcher.find()){
				Float expYears = Float.valueOf(matcher.group());
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			}

				
			return matchQueryBuilderList; 
	 }
	 
	 
	 
	 @PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_PROFILE')")
		@RequestMapping(value = "/getProfessionalInterviews")
		public String getProfessionalInterviews(String dateRange, HttpServletRequest request) throws Exception {
			String resultJson;
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<RequirementInterviewHistory> requirementInterviewList = null;
			String currentTime = null;
			String userType = null;
			String userCode = user.getUserCode();
			Long userId = user.getId();
			
		   requirementInterviewList = requirementInterviewHistoryService.getAllProfessinalInterviews(loginOrgLocations.getId(), dateRange, currentTime, userType, userId, userCode,candidateId);
			
			for(RequirementInterviewHistory interviewHistory : requirementInterviewList){
				if(null != interviewHistory.getInterviewId()){
						interviewHistory.setInterviewType(interviewHistory.getInterviewId().getInterviewType());	
						interviewHistory.setLocation(interviewHistory.getInterviewId().getLocation());	
				 }
				if(null != interviewHistory.getOrgLocationId()){
					if(null != interviewHistory.getOrgLocationId().getOrg()){
						if(null != interviewHistory.getOrgLocationId().getOrg().getOrgName()){
							interviewHistory.setCompanyName(interviewHistory.getOrgLocationId().getOrg().getOrgName());
						}
					}
				}
				if(null != interviewHistory.getSubmissionId()){
					if(null != interviewHistory.getSubmissionId().getRequirement()){
						interviewHistory.setRequirementTitle(interviewHistory.getSubmissionId().getRequirement().getJobTitle());
						interviewHistory.setRequirementUUID(interviewHistory.getSubmissionId().getRequirement().getRequirementUUID());
					}
				}
				interviewHistory.setRequirementId(interviewHistory.getSubmissionId().getRequirement().getId().toString());
				
				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm aa");
				String commonDateFormat = standardFormat.format(interviewHistory.getInterviewId().getStartTime());
				interviewHistory.setInterviewStartDateTimeStr(commonDateFormat.toString());
				
				
				
				
				
			}
			
			
			resultJson = commonUtil.toJSON(requirementInterviewList);
			return resultJson;
		}
	 
	 
	 
	 
	 @PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/submitReferToJob", method = RequestMethod.POST)
		public String submitReferToJob(@RequestParam(value = "json", required = false) String json,
				HttpServletRequest request,HttpServletResponse response)
				throws Exception {

			String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);

			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			requirementReferService.saveReferCandidate(user, orgLocations, jsonObj);
						
			return "success";

		}
	 
	 
	 @RequestMapping(value = "/getRecruiterCandidatesList", method = RequestMethod.POST)
		public @ResponseBody String getRecruiterCandidatesList(
				@RequestParam(value = "requirementId", required = false) Long requirementId,
				@RequestParam(value = "candidateType", required = false) String candidateType, HttpServletRequest request) throws Exception {
			String resultJson = null;
			String candidateStatus = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
			
			List<Long> candidateIdsList = requirementSubmissionService.getSubmittedCandidateIdsByRequirementId(requirementId);
			Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
			
			    List<CandidateIndex> candidatesList = new ArrayList<CandidateIndex>();
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    df.setTimeZone(TimeZone.getTimeZone("GMT"));
		        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		        boolQueryBuilder.must(QueryBuilders.matchQuery("recruiterUserId", user.getId()));
		        boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocations.getId()));
		        boolQueryBuilder.must(QueryBuilders.matchQuery("candidateType", "Candidate"));
		        boolQueryBuilder.must(QueryBuilders.matchQuery("status", 1));
		        
		        if(!isNullOrEmpty(candidateType) && candidateType.equalsIgnoreCase("FavCandidates")){
		        	boolQueryBuilder.must(QueryBuilders.matchQuery("isFavourite", 1));
		        }
		        
		        if(null != requirement && !isNullOrEmpty(requirement.getSkills())){
					String[] skills = requirement.getSkills().split(",");
					BoolQueryBuilder skillsBoolQuery = QueryBuilders.boolQuery();
					for(int i=0; i < skills.length; i++){
						skillsBoolQuery.should(QueryBuilders.matchQuery("skillsName", skills[i]));
					}
					boolQueryBuilder.must(skillsBoolQuery);
				}
		        
		        Long maxResults = candidateService.getCandidatesCountByOrgLocationID(orgLocations.getId(),candidateStatus);
			
			if(null != candidateIdsList && candidateIdsList.size() > 0){
				BoolQueryBuilder candidateBoolQueryBuilder = QueryBuilders.boolQuery();
				for(Long candidateId : candidateIdsList){
					candidateBoolQueryBuilder.should(QueryBuilders.matchQuery("candidateId", candidateId));
				}
				boolQueryBuilder.mustNot(candidateBoolQueryBuilder);
			}
			
	        try {
				SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
				    if(null != searchHits && searchHits.length >0){
				    	ObjectMapper mapper = new ObjectMapper();
				    	for (SearchHit hit : searchHits) {
				    		String json = hit.getSourceAsString();
				    		mapper.setDateFormat(df);
				    	    CandidateIndex candidateIndex = mapper.readValue(json, CandidateIndex.class);
				    	    
				    	    candidatesList.add(candidateIndex);
				    	}
				    	
				    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			resultJson = toJSON(candidatesList);
			return resultJson;
		}
	 
	 
		@RequestMapping(value = "/getRefercontactList", method = RequestMethod.POST)
		public String getRefercontactList(HttpServletRequest request,HttpServletResponse response) throws Exception {
			String resultJson = null;
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<ReferContacts> referContactList = referContactsService.getReferContactsListByUserIdAndOrglocationId(user.getId(), orgLocations.getId());
			for(ReferContacts rc :referContactList){
				if(null != rc.getContactUser()){
					StringBuilder username = new StringBuilder();
					if(null != rc.getContactUser().getLastName()){
						rc.setContactUserName(username.append(rc.getContactUser().getFirstName()).append(" ").append(rc.getContactUser().getLastName()).toString());	
					}else{
						rc.setContactUserName(username.append(rc.getContactUser().getLastName()).toString());
					}
					rc.setContactUserEmail(rc.getContactUser().getEmail());
					rc.setContactUserPhone(rc.getContactUser().getMobilePhone());
				}else{
					StringBuilder contactName = new StringBuilder();
					if(null != rc.getLastName()){
						rc.setContactUserName(contactName.append(rc.getFirstName()).append(" ").append(rc.getLastName()).toString());
					}else{
						rc.setContactUserName(contactName.append(rc.getLastName()).toString());
					}
					rc.setContactUserEmail(rc.getContactUserEmail());
					rc.setContactUserPhone(rc.getContactUserPhone());
				}
				
			}
			resultJson = commonUtil.toJSON(referContactList);
			return resultJson;

		}
		
		
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getJobSummaryDoNutChart", method = RequestMethod.GET)
		public String getJobSummaryDoNutChart(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobSummaryStartDate = null;
			Date jobSummaryEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobSummaryStartDate =  sdf.parse(startDate);
				jobSummaryEndDate =  sdf.parse(endDate);
			
			}else{
				
				//For This Month
				
				Calendar c = Calendar.getInstance();   
	    	    c.set(Calendar.DAY_OF_MONTH, 1);
	    	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    	    startDate = df.format(c.getTime());
	    	    
	    	    int lastDate = c.getActualMaximum(Calendar.DATE);
	    	    c.set(Calendar.DATE, lastDate);	
	    	    endDate = df.format(c.getTime());
	    	    
	    	    jobSummaryStartDate =  sdf.parse(startDate);
				jobSummaryEndDate =  sdf.parse(endDate);
			}
			
    		
			try{
				
				Integer appliedJobs = requirementSubmissionService.getProfessionalJobAppliedCount(candidateId, jobSummaryStartDate, jobSummaryEndDate);
				refer.setAppliedJobs(appliedJobs);
				
				Integer referToMe = requirementReferService.getReferJobsByReferType(user.getId(), "referToMe", jobSummaryStartDate, jobSummaryEndDate);
				refer.setReferToMeJobs(referToMe);
				
				Integer referByMe = requirementReferService.getReferJobsByReferType(user.getId(), "referByMe", jobSummaryStartDate, jobSummaryEndDate);
				refer.setReferByMeJobs(referByMe);
				
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getJobSummarySerialChart", method = RequestMethod.GET)
		public String getJobSummarySerialChart(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			
			List<RequirementRefer> referList = new ArrayList<RequirementRefer>();
			
			
			Date jobSummaryStartDate = null;
			Date jobSummaryEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				jobSummaryStartDate = df.parse(startDate);
				jobSummaryEndDate =  df.parse(endDate);
			
			}else{
				
	    	    
	    	    Calendar c = Calendar.getInstance();   
	    		c.add(Calendar.DATE, -15);
	    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    	    startDate = df.format(c.getTime());

	    	    Date date = new Date();
	    	    endDate = df.format(date);
	    	    
	    	    jobSummaryStartDate = df.parse(startDate);
				jobSummaryEndDate =  df.parse(endDate);
			
			}
    		
			 Calendar calendar = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
			 calendar.setLenient(false);
			 calendar.setTime(jobSummaryStartDate);
		     while (calendar.getTime().before(jobSummaryEndDate) || calendar.getTime().equals(jobSummaryEndDate))
		     {
		        Date resultDate = calendar.getTime();
		        RequirementRefer refer = new RequirementRefer();
		        
		        
		        Integer appliedJobs = requirementSubmissionService.getProfessionalJobAppliedCountByappliedDate(candidateId, resultDate);
				refer.setAppliedJobs(appliedJobs);
				
				Integer referByMe = requirementReferService.getReferJobsByReferTypeByReferedDate(user.getId(), "referByMe", resultDate);
				refer.setReferByMeJobs(referByMe);
				
				Integer referToMe = requirementReferService.getReferJobsByReferTypeByReferedDate(user.getId(), "referToMe", resultDate);
				refer.setReferToMeJobs(referToMe);
				 
				refer.setDateString(Constants.SHORT_DF_MM_DD.format(resultDate));
				
				referList.add(refer);
				
				calendar.add(Calendar.DATE, 1);
				
		       
		     }

				
			
			resultJson = commonUtil.toJSON(referList);
			return resultJson;

		}
		
		
		
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getJobSummaryPortlets", method = RequestMethod.GET)
		public String getJobSummaryPortlets(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobPortletsStartDate = null;
			Date jobPortletsEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobPortletsStartDate =  sdf.parse(startDate);
				jobPortletsEndDate =  sdf.parse(endDate);
			
			}else{
				
				//For This Month
				
				Calendar c = Calendar.getInstance();   
	    	    c.set(Calendar.DAY_OF_MONTH, 1);
	    	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    	    startDate = df.format(c.getTime());
	    	    
	    	    int lastDate = c.getActualMaximum(Calendar.DATE);
	    	    c.set(Calendar.DATE, lastDate);	
	    	    endDate = df.format(c.getTime());
	    	    
	    	    jobPortletsStartDate =  sdf.parse(startDate);
	    	    jobPortletsEndDate =  sdf.parse(endDate);
			}
			
    		
			try{
				
				Integer appliedJobs = requirementSubmissionService.getProfessionalJobAppliedCount(candidateId, jobPortletsStartDate, jobPortletsEndDate);
				refer.setAppliedJobs(appliedJobs);
				
				Integer shortlistCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"SHORTLIST",jobPortletsStartDate, jobPortletsEndDate);
				refer.setShortlistJobs(shortlistCount);
				
				Integer offeredCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"OFFERMADE",jobPortletsStartDate, jobPortletsEndDate);
				refer.setOfferedJobs(offeredCount);
				
				Integer hiredCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"OFFERHIRED",jobPortletsStartDate, jobPortletsEndDate);
				refer.setHiredJobs(hiredCount);
				
				Integer rejectedCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"REJECTED",jobPortletsStartDate, jobPortletsEndDate);
				refer.setRejectedJobs(rejectedCount);
				
				Integer interviewsCount = requirementInterviewService.getInterviewCountByCandidateId(candidateId, "INTERVIEW", jobPortletsStartDate, jobPortletsEndDate);
				refer.setInterviews(interviewsCount);
				
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		
		public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
}
