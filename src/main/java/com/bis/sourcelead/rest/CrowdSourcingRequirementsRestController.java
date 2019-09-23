package com.bis.sourcelead.rest;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.BlogCategoryDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ContactsGroupDTO;
import com.bis.sourcelead.dto.CrowdSourcingUserBankAccountsDTO;
import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementReferDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadBlogDTO;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.BlogCategory;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.ContactsGroup;
import com.bis.sourcelead.entity.CrowdSourcingUserBankAccounts;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.entity.RequirementOfferHistory;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.SavedJobs;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ContactsGroupService;
import com.bis.sourcelead.service.CrowdSourcingUserBankAccountsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOfferHistoryService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionHistoryService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SavedJobsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@RestController
@RequestMapping("/app")
public class CrowdSourcingRequirementsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(CrowdSourcingRequirementsRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
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
	
	@Autowired
	RequirementOfferHistoryService requirementOfferHistoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SavedJobsService savedJobsService;
	
	@Autowired
	TimeZoneService timeZoneService;
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	ContactsGroupService contactsGroupService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
	public String getAllOrgs(@PathVariable Long id) throws Exception {
		
		
		return commonUtil.toJSON(userService.getUserById(id));
	}
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody String getAllUsers() throws Exception {
		//userService.getUsers()
		//getAllOrgLocations
		return commonUtil.toJSON(userService.getUsers());
	}
	
	
	@RequestMapping(value = "/getCrowdSourcingRequirements", method = RequestMethod.POST)
	public @ResponseBody String getCrowdSourcingRequirements(@RequestParam(value = "requirementType", required = false) String requirementType,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "postedOn", required = false) String postedOn,
			@RequestParam(value = "jobTitleSearch", required = false) String jobTitleSearch,
			@RequestParam(value = "jobLocationSearch", required = false) String jobLocationSearch,
			@RequestParam(value = "postedOrgName", required = false) String postedOrgName,
			HttpServletRequest request) throws Exception {

		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		boolean isELKSearchEnable = true;
		
	    String resultJson;
	    List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    df.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	    if(isNullOrEmpty(requirementType) || (!isNullOrEmpty(requirementType) && !requirementType.equalsIgnoreCase("ReferredToMe") && !requirementType.equalsIgnoreCase("SavedJobs"))){
	    
	    BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
	     
	     if(requirementType.equalsIgnoreCase("allJobs")){
	    	 BoolQueryBuilder allJobsBoolQueryBuilder = QueryBuilders.boolQuery();
	    	  if(!loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
	    		  allJobsBoolQueryBuilder.should(QueryBuilders.matchQuery("isPublicJob", "Y"));
	    	  }
	    	 
	    	 allJobsBoolQueryBuilder.should(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
	    	 boolQueryBuilder.must(allJobsBoolQueryBuilder);
	    	 
	    	 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("appliedProfessionals", user.getId()));
	    	 List<RequirementRefer> referList = requirementReferService.getLoginUserReferToMeListByUserId(user.getId());
	    	 if(null != referList && referList.size() > 0){
	    		 for(RequirementRefer refer : referList){
	    			 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementId", refer.getRequirementId().getId()));
	    		 }
	    	 }
	     }
	    
	    if(requirementType.equalsIgnoreCase("OpenJobs")){
	    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isPublicJob", "Y"));
	    	 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("appliedProfessionals", user.getId()));
	    	 List<RequirementRefer> referList = requirementReferService.getLoginUserReferToMeListByUserId(user.getId());
	    	 if(null != referList && referList.size() > 0){
	    		 for(RequirementRefer refer : referList){
	    			 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementId", refer.getRequirementId().getId()));
	    		 }
	    	 }
	     }
	    
	     else if (requirementType.equalsIgnoreCase("CrowdSourcingJobs")){
	    	 boolQueryBuilder.must(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
	    	 List<RequirementRefer> referList = requirementReferService.getLoginUserReferToMeListByUserId(user.getId());
	    	 if(null != referList && referList.size() > 0){
	    		 for(RequirementRefer refer : referList){
	    			 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementId", refer.getRequirementId().getId()));
	    		 }
	    	 }
	     }
	     
	     else if(requirementType.equalsIgnoreCase("ReferredByMe")){
	    	 
	    	  if(loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")){
	    	     boolQueryBuilder.mustNot(QueryBuilders.matchQuery("isPublicJob", "Y"));
	    	  }
			
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
	     
	   //for jobs filter when clicked on org name 
		if(postedOrgName != null && !"".equalsIgnoreCase(postedOrgName) && !"undefined".equalsIgnoreCase(postedOrgName)){
			BoolQueryBuilder postedOrgJobsBuilder = QueryBuilders.boolQuery();
			postedOrgJobsBuilder.should(QueryBuilders.matchQuery("orgLocationId", postedOrgName));
			boolQueryBuilder.must(postedOrgJobsBuilder);
		}
		 
	    
	     BoolQueryBuilder anyStatusBoolQueryBuilder = QueryBuilders.boolQuery();
	     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "NEW"));
	     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
	     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "APPROVED"));
		 boolQueryBuilder.must(anyStatusBoolQueryBuilder);
		
	   
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
	 	    	    
	 	    	   if (requirementType.equalsIgnoreCase("CrowdSourcingJobs") || requirementType.equalsIgnoreCase("ReferredByMe")){
	 	    		   if(null !=  requirementIndex.getAppliedProfessionals()){
	 	    			  List<Long> userId = new ArrayList<Long>();
		 					for (String s : requirementIndex.getAppliedProfessionals()){
		 						userId.add(Long.parseLong(s));
		 					 }
		 					boolean flag = userId.contains(user.getId());
		 					if(flag == true && loginUserRole.equalsIgnoreCase("ROLE_CROWDSOURCING_PROFESSIONAL")){
		 						requirementIndex.setIsJobApplied(Constants.YES);
		 						RequirementSubmission submission = null;
		 						 submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
		 		 	    		 if(submission == null) {
		 		 	    			submission = requirementSubmissionService.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
		 		 	    		 }
		 						 if(null != submission){
		 		 	    			  requirementIndex.setSubmissionId(submission.getId());
		 		 	    		   }
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
	 		    			 RequirementSubmission submission = null;
	 		    			 submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
	 		    			 if(submission == null) {
		 		 	    			submission = requirementSubmissionService.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
		 		 	    		 }
	 		    			 
	 		    			 if(null != submission){
	 		 	    			  requirementIndex.setRequirementAppliedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),submission.getCreatedDate()));
	 		 	    			  requirementIndex.setSubmissionId(submission.getId());
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
	 	    	    
	 	    	    SavedJobs savedJobs = savedJobsService.getSavedJobsByrequirementIdAndUserId(requirementIndex.getRequirementId(), user.getId());
	 	    	    if(null != savedJobs){
	 	    	    	requirementIndex.setIsJobSaved(Constants.YES);
	 	    	    }else{
	 	    	    	requirementIndex.setIsJobSaved(Constants.NO);
	 	    	    }
	 	    	    
	 	    	    requirementIndex.setNoOfSearchHits(searchHits.length);
	 	    	    List<String> requirementLocationsList = new ArrayList<>();
	 	    	    List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirementIndex.getRequirementId());
	 	    	    if(locationsList != null && !locationsList.isEmpty()) {
	 	    	    	for(RequirementLocations locations : locationsList) {
	 	    	    		requirementLocationsList.add(locations.getLocation());
	 	    	    	}
	 	    	    }
	 	    	    requirementIndex.setLocationsList(requirementLocationsList);
	 	    	    
	 	    	    requirementList.add(requirementIndex);
	 	    	}
	 	    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    
	    }else{
	    	
	    	 if(requirementType.equalsIgnoreCase("ReferredToMe")){
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
    					 if(StringUtils.isNotEmpty(refer.getRequirementId().getMinExp())) {
    						 requirementIndex.setMinExperience(Float.parseFloat(refer.getRequirementId().getMinExp()));
    					 }
    					 if(StringUtils.isNotEmpty(refer.getRequirementId().getMaxExp())) {
    						 requirementIndex.setMaxExperience(Float.parseFloat(refer.getRequirementId().getMaxExp())); 
    					 }
    					 requirementIndex.setCountry(refer.getRequirementId().getCountry());
    					 requirementIndex.setState(refer.getRequirementId().getState());
    					 requirementIndex.setCity(refer.getRequirementId().getCity());
    					 requirementIndex.setIsCrowdSourcingJob(refer.getRequirementId().getIsCrowdSourcing());
    					 if(!isNullOrEmpty(refer.getRequirementId().getRate())){
    						 requirementIndex.setRate(Float.parseFloat(refer.getRequirementId().getRate()));
    					 }
    					
    					 requirementIndex.setRateType(refer.getRequirementId().getRateType());
    					 requirementIndex.setCurrentRateCurrency(refer.getRequirementId().getCurrencyCode());
    					 
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
    		 						RequirementSubmission submission = null;
    		 						 submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
    		 						 if(submission == null) {
    			 		 	    			submission = requirementSubmissionService.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
    			 		 	    		 } 
    		 						 if(null != submission){
	  		 		 	    			  requirementIndex.setSubmissionId(submission.getId());
	  		 		 	    		   }
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
    					  requirementIndex.setReferUUID(refer.getReferUUID());
    					  
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
						  requirementIndex.setRequirementUUID(refer.getRequirementId().getRequirementUUID());
						  requirementIndex.setIsJobSaved(Constants.YES);
						  
						   SavedJobs savedJobs = savedJobsService.getSavedJobsByrequirementIdAndUserId(refer.getRequirementId().getId(), user.getId());
			 	    	    if(null != savedJobs){
			 	    	    	requirementIndex.setIsJobSaved(Constants.YES);
			 	    	    }else{
			 	    	    	requirementIndex.setIsJobSaved(Constants.NO);
			 	    	    }
			 	    	    
			 	    	    List<String> requirementLocationsList = new ArrayList<>();
			 	    	    List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(refer.getRequirementId().getId());
			 	    	    if(locationsList != null && !locationsList.isEmpty()) {
			 	    	    	for(RequirementLocations locations : locationsList) {
			 	    	    		requirementLocationsList.add(locations.getLocation());
			 	    	    	}
			 	    	    }
			 	    	    requirementIndex.setLocationsList(requirementLocationsList);
						  
    					    requirementList.add(requirementIndex);
	    			 } 
    			 }else{
    				 isELKSearchEnable = false;
    			 }
    			 
    		 }
	    	 
	    	 
	    	 if(requirementType.equalsIgnoreCase("SavedJobs")){
	    		 
	    		 List<SavedJobs> savedJobsList = savedJobsService.getSavedJobsListByUserId(user.getId(),jobTitleSearch,jobLocationSearch,offset,Constants.MAX_RESULTS_TEN,postedOn);
	    		 if(null != savedJobsList && savedJobsList.size() > 0){
	    			 
	    			 for(SavedJobs savedJobs : savedJobsList){
	    				 
	    				 
	    				 RequirementIndex requirementIndex = new RequirementIndex();
    					 requirementIndex.setRequirementId(savedJobs.getRequirementId().getId());
    					 requirementIndex.setJobTitle(savedJobs.getRequirementId().getJobTitle());
    					 requirementIndex.setJobId(savedJobs.getRequirementId().getJobId());
    					 requirementIndex.setPostedOrg(savedJobs.getRequirementId().getPostedOrgName());
    					 requirementIndex.setJobType(savedJobs.getRequirementId().getJobType());
    					 requirementIndex.setJobId(savedJobs.getRequirementId().getJobId());
    					 if(StringUtils.isNotEmpty(savedJobs.getRequirementId().getMinExp())) {
    						 requirementIndex.setMinExperience(Float.parseFloat(savedJobs.getRequirementId().getMinExp()));
    					 }
    					 if(StringUtils.isNotEmpty(savedJobs.getRequirementId().getMaxExp())) {
    						 requirementIndex.setMaxExperience(Float.parseFloat(savedJobs.getRequirementId().getMaxExp()));
    					 }
    					 requirementIndex.setCountry(savedJobs.getRequirementId().getCountry());
    					 requirementIndex.setState(savedJobs.getRequirementId().getState());
    					 requirementIndex.setCity(savedJobs.getRequirementId().getCity());
    					 requirementIndex.setIsCrowdSourcingJob(savedJobs.getRequirementId().getIsCrowdSourcing());
    					 if(!isNullOrEmpty(savedJobs.getRequirementId().getRate())){
    						 requirementIndex.setRate(Float.parseFloat(savedJobs.getRequirementId().getRate()));
    					 }
    					
    					 requirementIndex.setRateType(savedJobs.getRequirementId().getRateType());
    					 requirementIndex.setCurrentRateCurrency(savedJobs.getRequirementId().getCurrencyCode());
    					 
    					 if(StringUtils.isNotBlank(savedJobs.getRequirementId().getVisaType())) {
    						 requirementIndex.setVisaTypes(savedJobs.getRequirementId().getVisaType().split(","));	 
    					 }
    					 
    					 requirementIndex.setIndustryName(savedJobs.getRequirementId().getIndustryName());
    					 
    					 if(!isNullOrEmpty(savedJobs.getRequirementId().getSkills())){
    						 String[] skills = savedJobs.getRequirementId().getSkills().split(",");
    						 List<String> skillsList = new ArrayList<String>();
    						 for(int i = 0 ; i < skills.length; i++ ){
    							 skillsList.add(skills[i]);
    						 }
        					 requirementIndex.setSkillsList(skillsList);
    					 }
    					 
    					  if(!isNullOrEmpty(savedJobs.getRequirementId().getAppliedProfessionals())){
    	 	    			  List<Long> userId = new ArrayList<Long>();
    		 					for (String s : savedJobs.getRequirementId().getAppliedProfessionals().split(",")){
    		 						userId.add(Long.parseLong(s));
    		 					 }
    		 					boolean flag = userId.contains(user.getId());
    		 					if(flag == true){
    		 						requirementIndex.setIsJobApplied(Constants.YES);
    		 						RequirementSubmission submission = null;
    		 						 submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
	  		 		 	    		   
    		 						 if(submission == null) {
 			 		 	    			submission = requirementSubmissionService.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
 			 		 	    		 } 
    		 						 
    		 						 if(null != submission){
	  		 		 	    			  requirementIndex.setSubmissionId(submission.getId());
	  		 		 	    		   }
    		 					}else{
    		 						requirementIndex.setIsJobApplied(Constants.NO);
    		 					} 
    	 	    		     }else{
    	 	    			    requirementIndex.setIsJobApplied(Constants.NO);
    	 	    		      }
    					  
    					  
    					  if(null != savedJobs.getReferId()){
    						 
    						  StringBuilder referedUser = new StringBuilder();
        					  if(!isNullOrEmpty(savedJobs.getReferId().getReferFromUser().getLastName())){
        						  referedUser = referedUser.append(savedJobs.getReferId().getReferFromUser().getFirstName()).append(" ").append(savedJobs.getReferId().getReferFromUser().getLastName());
        					  }else{
        						  referedUser = referedUser.append(savedJobs.getReferId().getReferFromUser().getFirstName());
        					  }
        					  requirementIndex.setReferedUser(referedUser.toString());
        					  requirementIndex.setReferId(savedJobs.getReferId().getId());
        					  
        					  if(null != savedJobs.getReferId().getReferStatus() && savedJobs.getReferId().getReferStatus().equalsIgnoreCase(Constants.REFER_JOB_APPLIED)){
        						  requirementIndex.setIsReferedRequiremetnApplied(Constants.YES);
        					  }else{
        						  requirementIndex.setIsReferedRequiremetnApplied(Constants.NO);
        					  }
    					  }
    					 
    					  requirementIndex.setNoOfSearchHits(savedJobsList.size());
    					  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    					  requirementIndex.setCreatedDate(dateFormat.format(savedJobs.getRequirementId().getCreatedDate()));
    					  
    					  requirementIndex.setRewardType(savedJobs.getRequirementId().getRewardType());
    					  requirementIndex.setRewardPercentage(savedJobs.getRequirementId().getRewardPercentage());
    					  requirementIndex.setRewardFlat(savedJobs.getRequirementId().getRewardFlat());
    					  requirementIndex.setRewardFlatRateType(savedJobs.getRequirementId().getRewardFlatRateType());
    					  requirementIndex.setRewardGuaranteePeriod(savedJobs.getRequirementId().getRewardGuaranteePeriod());
						  requirementIndex.setRequirementUUID(savedJobs.getRequirementId().getRequirementUUID());
						  requirementIndex.setIsJobSaved(Constants.YES);
						  requirementIndex.setSavedJobUUID(savedJobs.getSavedJobUUID());
						  
						  List<String> requirementLocationsList = new ArrayList<>();
			 	    	    List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(savedJobs.getRequirementId().getId());
			 	    	    if(locationsList != null && !locationsList.isEmpty()) {
			 	    	    	for(RequirementLocations locations : locationsList) {
			 	    	    		requirementLocationsList.add(locations.getLocation());
			 	    	    	}
			 	    	    }
			 	    	    requirementIndex.setLocationsList(requirementLocationsList);
			 	    	    
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
	    
	     if((Constants.TODAY).equalsIgnoreCase(updatedOn)){
	        cal.add(Calendar.DATE, 0);    
	        return  dateFormat.format(cal.getTime());
		 }
	     else if((Constants.ONE_DAY_AGO).equalsIgnoreCase(updatedOn)){
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
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
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
			@RequestParam(value = "con", required = false) String con, MultipartHttpServletRequest request,
			@RequestParam(value = "locationMapFile", required = false) CommonsMultipartFile locationMapFileData)
			throws Exception {

		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		
		MultipartFile multiFile = null;
		//to get parse resume
		if(locationMapFileData != null && locationMapFileData.getSize() > 0 ){
			Iterator<String> itrator = request.getFileNames();
			if (itrator.hasNext()) {
				multiFile = request.getFile(itrator.next());
			}
		}
		//to get the manually uploaded resume
		else{
			final Map files = request.getFileMap();
		    multiFile = (MultipartFile)files.get("newUploadBtn");
		}
		
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		RequirementSubmission requirementSubmission = requirementSubmissionService.saveRequirementSubmissionFromCrowdSourcing(jsonObj,user, con,orgLocations);
		RequirementSubmissionHistory requirementSubmissionHistory = new RequirementSubmissionHistory();
		requirementSubmissionHistory.setToStage(requirementSubmission.getSubmissionStatus().toString());
		requirementSubmissionHistory.setFromStage("NEW");
		
		requirementSubmissionHistoryService.updateRequirementHistory(requirementSubmission, user,requirementSubmissionHistory, requirementSubmission.getCreatedBy());
		Requirement requirement = requirementSubmission.getRequirement();
		Integer count = requirementSubmissionService.getNumberOfApplicantsForRequirement(requirement.getOrgLocationId().getId(), requirement.getId());
		requirement.setTotalApplicantsForRequirement(count);
		
	    RequirementDTO requirementDTO=commonUtil.getRequirementDTO(requirement);
		requirementElkUtil.saveElkRequirement(requirement,requirementDTO);
		return "success";

	}
	

	/*@PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")*/
	@RequestMapping(value = "/saveBankAccountDetails", method = RequestMethod.POST)
	public Response<String> saveBankAccountDetails(CrowdSourcingUserBankAccountsDTO csuBankAccountsDTO,HttpServletRequest request)
			throws Exception {


		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		CrowdSourcingUserBankAccounts bankAccount = null;
		Response<String> response=new Response<>();
		
		
		try{
			if(user != null){
				if(Validator.validate(csuBankAccountsDTO)){
					bankAccount = crowdSourcingUserBankAccountsService.getUserBankAccountByUserId(user.getId());	
					if(null == bankAccount){
						bankAccount = new CrowdSourcingUserBankAccounts();
						bankAccount.setAccountUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						bankAccount.setCreatedDate(new Date());
					}
					
					bankAccount.setAccountUserId(user);
					bankAccount.setAccountHolderName(csuBankAccountsDTO.getAccountHolderName());
					bankAccount.setCountry(csuBankAccountsDTO.getCountry());
					bankAccount.setCurrency(csuBankAccountsDTO.getCurrency());
					
					if(StringUtils.isNotBlank(csuBankAccountsDTO.getBankName())){
					  bankAccount.setBankName(csuBankAccountsDTO.getBankName());
					}
					
					bankAccount.setAccountNumber(csuBankAccountsDTO.getAccountNumber());
					
					bankAccount.setBankCode(csuBankAccountsDTO.getBankCode());
					bankAccount.setCreatedBy(user.getUserCode());
					bankAccount.setUpdatedBy(user.getUserCode());
					bankAccount.setUpdatedDate(new Date());
					
					crowdSourcingUserBankAccountsService.addUserBankAccounts(bankAccount);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					
				}else{
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
						
				}
			}else{
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		return response;

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
		    	 List<RequirementRefer> referList = requirementReferService.getLoginUserReferToMeListByUserId(user.getId());
		    	 if(null != referList && referList.size() > 0){
		    		 for(RequirementRefer refer : referList){
		    			 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementId", refer.getRequirementId().getId()));
		    		 }
		    	 }
		     }else if(requirementType.equalsIgnoreCase("allJobs")){
		    	 BoolQueryBuilder allJobsBoolQueryBuilder = QueryBuilders.boolQuery();
		    		  allJobsBoolQueryBuilder.should(QueryBuilders.matchQuery("isPublicJob", "Y"));
		    	 
		    	 allJobsBoolQueryBuilder.should(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));
		    	 boolQueryBuilder.must(allJobsBoolQueryBuilder);
		    	 
		    	 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("appliedProfessionals", user.getId()));
		    	 List<RequirementRefer> referList = requirementReferService.getLoginUserReferToMeListByUserId(user.getId());
		    	 if(null != referList && referList.size() > 0){
		    		 for(RequirementRefer refer : referList){
		    			 boolQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementId", refer.getRequirementId().getId()));
		    		 }
		    	 }
		     }
		     
		     boolQueryBuilder = getSearchRequirementsResults(boolQueryBuilder,jsonObj);   
		     
		     BoolQueryBuilder anyStatusBoolQueryBuilder = QueryBuilders.boolQuery();
		     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "NEW"));
		     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
		     anyStatusBoolQueryBuilder.should(QueryBuilders.matchQuery("requirementStatus", "APPROVED"));
			 boolQueryBuilder.must(anyStatusBoolQueryBuilder);
			
				
			   
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
				 						RequirementSubmission submission = null;
				 						 submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
			 		 	    		    
				 						 if(submission == null) {
		 			 		 	    			submission = requirementSubmissionService.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
		 			 		 	    		 } 
				 						 
				 						 if(null != submission){
			 		 	    			  requirementIndex.setSubmissionId(submission.getId());
			 		 	    		     }
				 					}else{
				 						requirementIndex.setIsJobApplied(Constants.NO);
				 					} 
			 	    		     }else{
			 	    			    requirementIndex.setIsJobApplied(Constants.NO);
			 	    		      }
			 	    	   }
			 	    	    
			 	    	   if (requirementType.equalsIgnoreCase("AppliedJobs")){
			 	    		  RequirementSubmission submission = null;
			 	    		   submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
			 	    		  
			 	    		  if(submission == null) {
		 		 	    			submission = requirementSubmissionService.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(requirementIndex.getRequirementId(), candidateId);
		 		 	    		 } 
			 	    		   if(null != submission){
			 	    			  requirementIndex.setRequirementAppliedDate(commonUtil.elkDateFormatter(submission.getCreatedDate()));
			 	    		   }
			 	    	   }
			 	    	   
			 	    	    SavedJobs savedJobs = savedJobsService.getSavedJobsByrequirementIdAndUserId(requirementIndex.getRequirementId(), user.getId());
			 	    	    if(null != savedJobs){
			 	    	    	requirementIndex.setIsJobSaved(Constants.YES);
			 	    	    }else{
			 	    	    	requirementIndex.setIsJobSaved(Constants.NO);
			 	    	    }
			 	    	   
			 	    	    requirementIndex.setNoOfSearchHits(searchHits.length);
			 	    	    
			 	    	    
			 	    	   List<String> requirementLocationsList = new ArrayList<>();
			 	  		List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirementIndex.getRequirementId());
			 	  		if(locationsList != null && !locationsList.isEmpty()) {
			 	  			for(RequirementLocations location : locationsList) {
			 	  				requirementLocationsList.add(location.getLocation());
			 	  			}
			 	  		}
			 	          
			 	  		requirementIndex.setLocationsList(requirementLocationsList);
			 	  		
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
						ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
						if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
							for(QueryBuilder QueryBuilder :QueryBuilderList){
								excludeBoolSubQueryBuilder.should(QueryBuilder);
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
							ArrayList<QueryBuilder> QueryBuilderList =getQueryBuilderList(keywd);
							if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
								for(QueryBuilder QueryBuilder :QueryBuilderList){
									anyBoolSubQueryBuilder.should(QueryBuilder);
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
							ArrayList<QueryBuilder> QueryBuilderList =getQueryBuilderList(keywd);
							if(QueryBuilderList != null && !QueryBuilderList.isEmpty()){
								for(QueryBuilder QueryBuilder :QueryBuilderList){
									allBoolSubQueryBuilder.should(QueryBuilder);
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
					boolQueryBuilder.must(QueryBuilders.matchQuery("country", jsonObj.getString("country").split(",")[0]));
			    }
				 //State
				if(jsonObj.has("state") && !"".equalsIgnoreCase(jsonObj.getString("state"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("state", jsonObj.getString("state")));
			    }
				
				//mukltiple cities
				if(jsonObj.has("city")  && !"".equalsIgnoreCase(jsonObj.getString("city"))){
					ArrayList<String> cityArray = getSearchKeywordsFromChosenSelectField(jsonObj, "city");
					if(cityArray != null && !cityArray.isEmpty()){
						BoolQueryBuilder cityBoolQueryBuilder = QueryBuilders.boolQuery();
						for(String keywd : cityArray){
							cityBoolQueryBuilder.should(QueryBuilders.matchQuery("city", keywd));
						}
						boolQueryBuilder.must(cityBoolQueryBuilder);
					} 	
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
				
				//Requirement Organizations
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
	 
	 
	 private ArrayList<QueryBuilder> getQueryBuilderList(String keywd){
		   
		   ArrayList<QueryBuilder> QueryBuilderList = new ArrayList<QueryBuilder>();
		    QueryBuilderList.add(QueryBuilders.matchQuery("jobTitle", keywd));
		    QueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("requirementStatus", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("client", keywd));
			
			QueryBuilderList.add(QueryBuilders.matchQuery("jobType", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("jobId", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("clientContactName", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("clientContactEmail", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("manager", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("priority", keywd));
			QueryBuilderList.add(QueryBuilders.matchQuery("numberOfPositions", keywd));
			
			
			Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(matcher.find()){
				Float expYears = Float.valueOf(matcher.group());
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", expYears));
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", expYears));
				QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", expYears));
			}

				
			return QueryBuilderList; 
	 }
	 
	 
	    @PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_CANDIDATES')")
		@RequestMapping(value = "/getRecruiterSubmittiedInterviews")
		public RequirementInterviewHistory getRecruiterSubmittiedInterviews(@RequestParam(value = "dateRange", required = true) String dateRange,@RequestParam(value = "userId", required = true) Long userId,@RequestParam(value = "timezoneId", required = false) Long timezoneId,@RequestParam(value = "locationCode", required = false) String locationCode) throws Exception {
			String resultJson;
			
			String currentTime = null;
			Timezone timezone = null;
  			if(timezoneId != null){
				timezone = timeZoneService.getTimezone(timezoneId);
			}
  			 OrgLocations orglo = orgLocationService.getOrgLocationsByLocationCode(locationCode);
  	        SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
  			Date startUTCDate = null;
			Date endUTCDate = null;
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
			Date startTime =  commonUtil.orgLocationDateFormat(orglo.getDateFormat(),dateRange);
			
			String startTimeTS = Constants.TZ_DF.format(startTime); 
			String endTimeTS = END_TZ_DF.format(startTime);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startTimeTS);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
				 endUTCDate = TZ_DF_TS.parse(endDateString);
				 
			List<RequirementInterviewHistory> requirementInterviewList  = requirementInterviewHistoryService.getAllRecruiterSubmittedInterviews(dateRange,userId,currentTime,startUTCDate,endUTCDate,timezone);
			
			for(RequirementInterviewHistory interviewHistory : requirementInterviewList){
				if(null != interviewHistory.getInterviewId()){
						interviewHistory.setInterviewType(interviewHistory.getInterviewId().getInterviewType());	
						interviewHistory.setLocation(interviewHistory.getInterviewId().getLocation());	
				 }
				if(null != interviewHistory.getOrgLocationId()){
					if(null != interviewHistory.getOrgLocationId().getOrg()){
						if(null != interviewHistory.getOrgLocationId().getOrgLocationName()){
							interviewHistory.setCompanyName(interviewHistory.getOrgLocationId().getOrgLocationName());
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
				
				//changed as per the timezone
  				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm aa");
  				DateFormat eventstartTimeFroDashBoard = new SimpleDateFormat( "hh:mm aa");
  				if(null != timezone.getTimeZoneId() && null != interviewHistory.getInterviewId() && null != interviewHistory.getInterviewId().getId() && null != interviewHistory.getInterviewId().getStartTime()){
  					String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getStartTime());  
  					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
  					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
  					currentDateInTimezone = standardFormat.format(converteDate);
  					String eventstartTimeFroDashBoardStr = eventstartTimeFroDashBoard.format(converteDate);
  					interviewHistory.setInterviewStartDateTimeStr(currentDateInTimezone);
  					interviewHistory.setStartTimeStr(eventstartTimeFroDashBoardStr);
  				}
  				
  				
  				if(null != timezone.getTimeZoneId() && null != interviewHistory.getInterviewId() && null != interviewHistory.getInterviewId().getId() && null != interviewHistory.getInterviewId().getEndTime()){
  					String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getEndTime());  
  					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
  					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
  					String eventstartTimeFroDashBoardStr = eventstartTimeFroDashBoard.format(converteDate);
  					interviewHistory.setEndTimeStr(eventstartTimeFroDashBoardStr);
  				}
  				else{
  					interviewHistory.setEndTimeStr("--");
  				}
				
			}
			
			
			
			   DateFormat week_Day = new SimpleDateFormat ("EEEE");
  			   String dayOfweek = week_Day.format(startTime);
				SimpleDateFormat formatterRec = new SimpleDateFormat("MMM dd yyyy");
				String selectedDate  = formatterRec.format(startTime);
				RequirementInterviewHistory reqInterviewHistory =  new RequirementInterviewHistory();
				
				reqInterviewHistory.setInterviewerList(requirementInterviewList);
				
				reqInterviewHistory.setDayOfweek(dayOfweek);
				reqInterviewHistory.setSelectedDate(selectedDate);
			return reqInterviewHistory;
		}
	    
	    
	    
	        @PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_PROFILE')")
	  		@RequestMapping(value = "/getProfessionalInterviewsEventDashBoard")
	  		public RequirementInterviewHistory getProfessionalInterviewsEventDashBoard(@RequestParam(value = "dateRange", required = true) String dateRange, @RequestParam(value = "candidateId", required = true) Long candidateId,@RequestParam(value = "timezoneId", required = false) Long timezoneId) throws Exception {
	  			 
	  			String currentTime = null;
				Timezone timezone = null;
	  			if(timezoneId != null){
					timezone = timeZoneService.getTimezone(timezoneId);
				}
	  			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
	  			Date startUTCDate = null;
				Date endUTCDate = null;
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
				Date startTime =  commonUtil.orgLocationDateFormat(candidate.getOrgLocationId().getDateFormat(),dateRange);
				
				String startTimeTS = Constants.TZ_DF.format(startTime); 
				String endTimeTS = END_TZ_DF.format(startTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startTimeTS);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
					 startUTCDate = TZ_DF_TS.parse(startDateString);
					 endUTCDate = TZ_DF_TS.parse(endDateString);
	  			
	  			List<RequirementInterviewHistory> requirementInterviewList = requirementInterviewHistoryService.getAllProfessinalInterviews(dateRange,candidateId,currentTime,startUTCDate,endUTCDate,timezone);
	  			
	  			for(RequirementInterviewHistory interviewHistory : requirementInterviewList){
	  				if(null != interviewHistory.getInterviewId()){
	  						interviewHistory.setInterviewType(interviewHistory.getInterviewId().getInterviewType());	
	  						interviewHistory.setLocation(interviewHistory.getInterviewId().getLocation());	
	  				 }
	  				if(null != interviewHistory.getOrgLocationId()){
	  					if(null != interviewHistory.getOrgLocationId().getOrg()){
	  						if(null != interviewHistory.getOrgLocationId().getOrgLocationName()){
	  							interviewHistory.setCompanyName(interviewHistory.getOrgLocationId().getOrgLocationName());
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
	  				
	  			//changed as per the timezone
	  				DateFormat standardFormat = new SimpleDateFormat( "MMM dd, yyyy hh:mm aa");
	  				DateFormat eventstartTimeFroDashBoard = new SimpleDateFormat( "hh:mm aa");
	  				if(null != timezone.getTimeZoneId() && null != interviewHistory.getInterviewId() && null != interviewHistory.getInterviewId().getId() && null != interviewHistory.getInterviewId().getStartTime()){
	  					String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getStartTime());  
	  					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
	  					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
	  					currentDateInTimezone = standardFormat.format(converteDate);
	  					String eventstartTimeFroDashBoardStr = eventstartTimeFroDashBoard.format(converteDate);
	  					interviewHistory.setInterviewStartDateTimeStr(currentDateInTimezone);
	  					interviewHistory.setStartTimeStr(eventstartTimeFroDashBoardStr);
	  				}
	  				
	  				
	  				if(null != timezone.getTimeZoneId() && null != interviewHistory.getInterviewId() && null != interviewHistory.getInterviewId().getId() && null != interviewHistory.getInterviewId().getEndTime()){
	  					String currentTimeStr = Constants.TZ_DF.format(interviewHistory.getInterviewId().getEndTime());  
	  					String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE  ,timezone.getTimeZoneId(), currentTimeStr);
	  					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
	  					String eventstartTimeFroDashBoardStr = eventstartTimeFroDashBoard.format(converteDate);
	  					interviewHistory.setEndTimeStr(eventstartTimeFroDashBoardStr);
	  				}
	  				else{
	  					interviewHistory.setEndTimeStr("--");
	  				}
	  				
	  			}
	  			DateFormat week_Day = new SimpleDateFormat ("EEEE");
	  			String dayOfweek = week_Day.format(startTime);
  				SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
  				String selectedDate  = formatter.format(startTime);
  				RequirementInterviewHistory reqInterviewHistory =  new RequirementInterviewHistory();
  				
  				reqInterviewHistory.setInterviewerList(requirementInterviewList);
  				
  				reqInterviewHistory.setDayOfweek(dayOfweek);
  				reqInterviewHistory.setSelectedDate(selectedDate);
	  			return reqInterviewHistory;
	  		}
	 
	 
	 /*@PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")*/
		@RequestMapping(value = "/submitReferToJob", method = RequestMethod.POST)
		public String submitReferToJob(RequirementReferDTO requirementReferDTO,
				HttpServletRequest request,HttpServletResponse response)
				throws Exception {

			/*String json1 = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(json1);*/

			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			requirementReferService.saveReferCandidate(user, orgLocations, requirementReferDTO);
						
			return "success";

		}

	/* @PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")*/
		@RequestMapping(value = "/submitUserContactsToRequirement", method = RequestMethod.POST)
		public String submitUserContactsToRequirement(ReferContactsDTO referContactsDTO,
				HttpServletRequest request,HttpServletResponse response)
				throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			requirementReferService.submitUserContactsToRequirement(user, orgLocations, referContactsDTO);
						
			return "success";
		}
	 
	 
	 
		@RequestMapping(value = "/addCrowdSourcingUserContact", method = RequestMethod.POST)
		public Response<String> addCrowdSourcingUserContact(ReferContactsDTO referContactsDTO,
				HttpServletRequest request,HttpServletResponse response)
				throws Exception {
                
			    Response<String> contactRresponse = new Response<>();
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				try {
					if (user != null) {

						if (Validator.validate(referContactsDTO)) {
							referContactsService.saveuserContacts(user, referContactsDTO);
							contactRresponse.setResponse(Result.SUCCESS.name());
							contactRresponse.setResponseMessage(Result.SUCCESS.getValue());
						}else {
							contactRresponse.setResponse(Result.INVALID_DATA.name());
							contactRresponse.setResponseMessage(Result.INVALID_DATA.getValue());
						}

						}else {
							contactRresponse.setResponse(Result.SESSION_TIMEDOUT.name());
							contactRresponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
						}
					
				} catch (Exception e) {
					contactRresponse.setResponse(Result.AWKWARD.name());
					contactRresponse.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
						
			    return contactRresponse;
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
	 
	 
	 
	    @RequestMapping(value = "/storeProfessionalParseCandidate", method = RequestMethod.POST)
		public @ResponseBody Response<String> storeCandidate(CandidateDTO candidateDTO,
				@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
			

		 Response<String> response = new Response<>(); 
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 if (user != null) {
			 try {
					if (Validator.validate(candidateDTO)) {
						 candidateDTO.setStaticPath(staticPath);
							//FileUploadUtil.fillUploadedFileInfo(candidateDTO, candidateDTO.getFileInfo());
							OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
							Candidate candidate = candidateService.storeProfessionalParseCandidate(candidateDTO,orgLocation,user,candidateId);
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						response.setObject("DataMissing");
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				} catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
				} else{
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				return response; 
		 }

	 
		@RequestMapping(value = "/getRefercontactList", method = RequestMethod.GET)
		public  ResponseList<ReferContactsDTO> getRefercontactList(ReferContactsDTO referContactsDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
			
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
      		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			ResponseList<ReferContactsDTO> responseList = new ResponseList<>();
			try {
				
				if (user != null && orgLocation != null) {
					
				String[] tableColumns = {"contactName", "groups", "email", "phone", "source","getRefercontactList"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "desc"; //by default referContactsDTO order
				if(referContactsDTO.getOrder() != null && !referContactsDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(referContactsDTO.getOrder().get(0).get("column"));
					sortColumnOrder = referContactsDTO.getOrder().get(0).get("dir");
				}
				referContactsDTO.setSortColumn(tableColumns[sortColumnIndex]);
				referContactsDTO.setSortOrder(sortColumnOrder);
	      		
				responseList = referContactsService.getReferContactsListByUserIdAndOrglocationId(referContactsDTO,user.getId(), orgLocation.getId());
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				
				}
			} catch (Exception e) {
				responseList.setResponse(Result.AWKWARD.name());
				responseList.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return responseList;

		}
		
		@RequestMapping(value = "/refercontactListToGroup", method = RequestMethod.POST)
		public @ResponseBody String assignHotListCandiddatesToTeam(
				@RequestParam(value = "groupId", required = false) Long groupId,
				@RequestParam(value = "contactsList", required = false) String contactsList, HttpServletRequest request)
				throws Exception {

			contactsGroupService.saveContactsReferContactGroup(groupId, contactsList);

			return "success";
		}
		
		
		@RequestMapping(value = "/getGroups", method = RequestMethod.POST)
		public @ResponseBody String getGroups(HttpServletRequest request) throws Exception
		{
			String resultJson = null;
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<ContactsGroup> contactsGroupList = contactsGroupService.getContactsGroupListByUserId(user.getId());
			resultJson = toJSON(contactsGroupList);
			return resultJson;
					
		}
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getJobSummaryDoNutChart", method = RequestMethod.GET)
		public String getJobSummaryDoNutChart(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobSummaryStartDate = null;
			Date jobSummaryEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobSummaryStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
				jobSummaryEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
			
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
		@RequestMapping(value = "/getRecruiterJobSummaryDoNutChart", method = RequestMethod.GET)
		public String getRecruiterJobSummaryDoNutChart(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,HttpServletRequest request) throws Exception {
			String resultJson = null;
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobSummaryStartDate = null;
			Date jobSummaryEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobSummaryStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
				jobSummaryEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
				
			
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
				
				Integer appliedJobs = requirementSubmissionService.getRequiterReferJobAppliedCount(user.getId(), jobSummaryStartDate, jobSummaryEndDate);
				refer.setAppliedJobs(appliedJobs);
				
				Integer referByMe = requirementReferService.getReferJobsByReferType(user.getId(), "referByMe", jobSummaryStartDate, jobSummaryEndDate);
				refer.setReferByMeJobs(referByMe);
				
				Integer submittedByMe = requirementSubmissionService.getRecruiterSubmittedCandidateCountByStartDateAndEndDate(user.getId(), jobSummaryStartDate, jobSummaryEndDate);
				refer.setSubmittedByMeJobs(submittedByMe);
				
				
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
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<RequirementRefer> referList = new ArrayList<RequirementRefer>();
			
			
			Date jobSummaryStartDate = null;
			Date jobSummaryEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobSummaryStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
				jobSummaryEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
			
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
		@RequestMapping(value = "/getRecruiterJobSummarySerialChart", method = RequestMethod.GET)
		public String getRecruiterJobSummarySerialChart(@RequestParam(value = "startDate", required = false) String startDate,
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
		        
		        
		        Integer appliedJobs = requirementSubmissionService.getReferByMeJobAppliedCountByappliedDate(user.getId(), resultDate);
				refer.setAppliedJobs(appliedJobs);
				
				Integer referByMe = requirementReferService.getReferJobsByReferTypeByReferedDate(user.getId(), "referByMe", resultDate);
				refer.setReferByMeJobs(referByMe);
				
				Integer submittedByMe = requirementSubmissionService.getRecruiterSubmittedJobCountByappliedDate(user.getId(), resultDate);
				refer.setSubmittedByMeJobs(submittedByMe);
				 
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
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobPortletsStartDate = null;
			Date jobPortletsEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobPortletsStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
				jobPortletsEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
			
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
				
				Integer referedToMeJobs = requirementReferService.getReferJobsByReferType(user.getId(),"referToMe",jobPortletsStartDate, jobPortletsEndDate);
				refer.setReferToMeJobs(referedToMeJobs);
				
				Integer appliedJobs = requirementSubmissionService.getProfessionalJobAppliedCount(candidateId, jobPortletsStartDate, jobPortletsEndDate);
				refer.setAppliedJobs(appliedJobs);
				
				Integer shortlistCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"SHORTLIST",jobPortletsStartDate, jobPortletsEndDate);
				refer.setShortlistJobs(shortlistCount);
				
				Integer offeredCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"OFFERMADE",jobPortletsStartDate, jobPortletsEndDate);
				refer.setOfferedJobs(offeredCount);
				
				Integer rejectedCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"REJECTED",jobPortletsStartDate, jobPortletsEndDate);
				refer.setRejectedJobs(rejectedCount);
				
				Integer interviewsCount = requirementSubmissionService.getProfessionalJobSubmisssionsCountBySubmissionType(candidateId,"INTERVIEW",jobPortletsStartDate, jobPortletsEndDate);
				refer.setInterviews(interviewsCount);
				
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getRecruiterSubmttedJobSummaryPortlets", method = RequestMethod.GET)
		public String getRecruiterSubmttedJobSummaryPortlets(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobPortletsStartDate = null;
			Date jobPortletsEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobPortletsStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
				jobPortletsEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
			
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
				
				
				Integer submittedByMe = requirementSubmissionService.getRecruiterSubmittedCandidateCountByStartDateAndEndDate(user.getId(), jobPortletsStartDate, jobPortletsEndDate);
				refer.setSubmittedByMeJobs(submittedByMe);
				
				Integer shortlistCount = requirementSubmissionService.getRecruiterSubmittedCandidatesCountByStage(user.getId(),"SHORTLIST",jobPortletsStartDate, jobPortletsEndDate);
				refer.setShortlistJobs(shortlistCount);
				
				Integer offeredCount = requirementSubmissionService.getRecruiterSubmittedCandidatesCountByStage(user.getId(),"OFFERMADE",jobPortletsStartDate, jobPortletsEndDate);
				refer.setOfferedJobs(offeredCount);
				
				Integer rejectedCount = requirementSubmissionService.getRecruiterSubmittedCandidatesCountByStage(user.getId(),"REJECTED",jobPortletsStartDate, jobPortletsEndDate);
				refer.setRejectedJobs(rejectedCount);
				
				Integer interviewsCount = requirementSubmissionService.getRecruiterSubmittedCandidatesCountByStage(user.getId(),"INTERVIEW",jobPortletsStartDate, jobPortletsEndDate);
				refer.setInterviews(interviewsCount);
				
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getReferByMeJobSummaryPortlets", method = RequestMethod.GET)
		public String getReferByMeJobSummaryPortlets(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobPortletsStartDate = null;
			Date jobPortletsEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobPortletsStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), startDate);
				jobPortletsEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), endDate);
			
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
				
				Integer referedByMeJobs = requirementReferService.getReferredByMeCountByStartDateAndEndDate(user.getId(),jobPortletsStartDate, jobPortletsEndDate);
				refer.setReferByMeJobs(referedByMeJobs);
				
				Integer appliedJobs = requirementSubmissionService.getReferByMeJobAppliedCandidateCount(user.getId(), jobPortletsStartDate, jobPortletsEndDate);
				refer.setAppliedJobs(appliedJobs);
				
				Integer shortlistCount = requirementSubmissionService.getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(user.getId(),"SHORTLIST",jobPortletsStartDate, jobPortletsEndDate);
				refer.setShortlistJobs(shortlistCount);
				
				Integer offeredCount = requirementSubmissionService.getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(user.getId(),"OFFERMADE",jobPortletsStartDate, jobPortletsEndDate);
				refer.setOfferedJobs(offeredCount);
				
				Integer rejectedCount = requirementSubmissionService.getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(user.getId(),"REJECTED",jobPortletsStartDate, jobPortletsEndDate);
				refer.setRejectedJobs(rejectedCount);
				
				Integer interviewsCount = requirementSubmissionService.getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(user.getId(),"INTERVIEW",jobPortletsStartDate, jobPortletsEndDate);
				refer.setInterviews(interviewsCount);
				
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getJobSummaryPortletsList", method = RequestMethod.GET)
		public String getJobSummaryPortletsList(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "requirementStatus", required = false) String status,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobPortletsStartDate = null;
			Date jobPortletsEndDate = null;
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
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
				if(status.equalsIgnoreCase("referToMe")){
					List<RequirementRefer>  referedToMeJobs = requirementReferService.getReferJobsListByReferType(user.getId(),status,jobPortletsStartDate, jobPortletsEndDate);
					for(RequirementRefer rr: referedToMeJobs){
						rr.setRequirementName(rr.getRequirementId().getJobTitle());
						rr.setRequirementJobId(rr.getRequirementId().getJobId());
						rr.setRequirementUUID(rr.getRequirementId().getRequirementUUID());
						rr.setRequirementOrgName(rr.getRequirementId().getOrgLocationId().getOrgLocationName());
						rr.setRequirementJobType(rr.getRequirementId().getJobType());
						
						 List<String> requirementLocationsList = new ArrayList<>();
						 List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(rr.getRequirementId().getId());
						 if(locationsList != null && !locationsList.isEmpty()) {
						 for(RequirementLocations location : locationsList) {
						 requirementLocationsList.add(location.getLocation());
						 }
						 }
						 rr.setLocationsList(requirementLocationsList);
						 
						rr.setRequirementCountry(rr.getRequirementId().getCountry());
						rr.setRequirementState(rr.getRequirementId().getState());
						rr.setRequirementCity(rr.getRequirementId().getCity());
						rr.setRequirementRate(rr.getRequirementId().getRate());
						rr.setRequirementRateType(rr.getRequirementId().getRateType());
						rr.setRequirementSubmissionStatus(rr.getRequirementId().getRequirementSubmissionStatus().toString());
						RequirementSubmission rs = null;
						rs=requirementSubmissionService.getRequirementSubmissionByRequirementIdAndCandidateId(rr.getRequirementId().getId(),candidateId);
						
						if(rs == null) {
							rs = requirementSubmissionService.getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(rr.getRequirementId().getId(), candidateId);
 		 	    		 } 
						if(null != rs){
							rr.setSubmissionStatus(rs.getSubmissionStatus().toString());	
						}
						
						StringBuilder referredUser = new StringBuilder();
						if(null != rr.getReferFromUser().getLastName()){
							rr.setReferredBy(referredUser.append(rr.getReferFromUser().getFirstName()).append(" ").append(rr.getReferFromUser().getLastName()).toString());	
						}else{
							rr.setReferredBy(referredUser.append(rr.getReferFromUser().getFirstName()).toString());
						}
						rr.setReferredOn(commonUtil.dateStrFromOrgDateFormatter(candidate.getOrgLocationId().getDateFormat(),rr.getCreatedDate()));
						
					}
					refer.setReferToMeList(referedToMeJobs);
				}else{
					List<RequirementSubmission> crowdSourcingPortLetList = requirementSubmissionService.getProfessionalJobSubmisssionsCountListBySubmissionType(candidateId,status,jobPortletsStartDate, jobPortletsEndDate);
					for(RequirementSubmission rs : crowdSourcingPortLetList){
						rs.setRequirementName(rs.getRequirement().getJobTitle());
						rs.setRequirementJobId(rs.getRequirement().getJobId());
						rs.setRequirementUUID(rs.getRequirement().getRequirementUUID());
						rs.setOrgName(rs.getRequirement().getOrgLocationId().getOrgLocationName());
						rs.setEmployeeType(rs.getRequirement().getJobType());
						
						List<String> requirementLocationsList = new ArrayList<>();
						 List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(rs.getRequirement().getId());
						 if(locationsList != null && !locationsList.isEmpty()) {
						 for(RequirementLocations location : locationsList) {
						 requirementLocationsList.add(location.getLocation());
						 }
						 }
						 rs.setLocationsList(requirementLocationsList);
						
						rs.setCountry(rs.getRequirement().getCountry());
						rs.setState(rs.getRequirement().getState());
						rs.setCity(rs.getRequirement().getCity());
						rs.setRate(rs.getRequirement().getRate());
						rs.setRateType(rs.getRequirement().getRateType());
						rs.setSubmissionStatus(rs.getSubmissionStatus());
						rs.setRequirementSubmissionStatus(rs.getRequirement().getRequirementSubmissionStatus().toString());
						RequirementRefer requirementRefer = requirementReferService.getAppliedRequirementReferByRequirementIdAndUserId(rs.getRequirement().getId(), user.getId());
						if(null != requirementRefer){
							StringBuilder referredUser = new StringBuilder();
							if(null != requirementRefer.getReferFromUser().getLastName()){
								rs.setReferredBy(referredUser.append(requirementRefer.getReferFromUser().getFirstName()).append(" ").append(requirementRefer.getReferFromUser().getLastName()).toString());	
							}else{
								rs.setReferredBy(referredUser.append(requirementRefer.getReferFromUser().getFirstName()).toString());
							}
							rs.setReferredOn(commonUtil.dateStrFromOrgDateFormatter(candidate.getOrgLocationId().getDateFormat(),requirementRefer.getCreatedDate()));
							refer.setReferUUID(requirementRefer.getReferUUID());
						}
						
					}
					refer.setCrowdSourcingPortLetList(crowdSourcingPortLetList);
					
				}
				
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		/*@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")*/
		@RequestMapping(value = "/getReferalListByRequirementId", method = RequestMethod.GET)
		public String getReferalListByRequirementId(@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
				HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
			List<RequirementRefer> requirementReferList = new ArrayList<RequirementRefer>();
			RequirementRefer requirementRefer = new RequirementRefer();
			
			List<RequirementRefer> referList = requirementReferService.getLoginUserReferredListByUserId(user.getId(), requirement.getId());
			if(null != referList && referList.size() > 0){
				for(RequirementRefer refer : referList){
					
					if(null != refer.getReferToUser()){
						StringBuilder userName = new StringBuilder();
						if(!isNullOrEmpty(refer.getReferToUser().getLastName())){
							userName = userName.append(refer.getReferToUser().getFirstName()).append(" ").append(refer.getReferToUser().getLastName());
						}else{
							userName = userName.append(refer.getReferToUser().getFirstName());
						}
						refer.setUserName(userName.toString());
						refer.setReferToEmail(refer.getReferToUser().getEmail());
						if(!isNullOrEmpty(refer.getReferToUser().getMobilePhone())){
							refer.setPhoneNumber(refer.getReferToUser().getMobilePhone());
						}
					}
					
					if(!isNullOrEmpty(refer.getReferStatus()) && refer.getReferStatus().equalsIgnoreCase("Applied")){
						RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionByRequirementIdAndUserId(refer.getRequirementId().getId(), refer.getReferToUser().getId());
						if(null != submission){
							refer.setRequirementSubmissionStatus(submission.getSubmissionStatus().toString());
							refer.setRejectedReason(submission.getRejectedReason());
						}
					}
					requirementReferList.add(refer);
				}
			}
			requirementRefer.setReferByMeJobsList(requirementReferList);
			
			resultJson = commonUtil.toJSON(requirementRefer);
			return resultJson;

		}
		
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_CANDIDATES')")
		@RequestMapping(value = "/getRecruiterRequirementSubmissionsList", method = RequestMethod.GET)
		public String getRecruiterRequirementSubmissionsList(@RequestParam(value = "requirementUUID", required = false) String requirementUUID,
				HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
			List<RequirementSubmission> reqSubmissionList = new ArrayList<RequirementSubmission>();
			RequirementSubmission requirementSubmission = new RequirementSubmission();
			
			List<RequirementSubmission> submissionList = requirementSubmissionService.getRecruiterSubmittedCandidatesList(user.getId(), requirement.getId());
			if(null != submissionList && submissionList.size() > 0){
				for(RequirementSubmission submission : submissionList){
					StringBuilder userName = new StringBuilder();
					if(!isNullOrEmpty(submission.getCandidate().getLastName())){
						userName = userName.append(submission.getCandidate().getFirstName()).append(" ").append(submission.getCandidate().getLastName());
					}else{
						userName = userName.append(submission.getCandidate().getFirstName());
					}
					submission.setCandidateName(userName.toString());
					submission.setCandidateEmail(submission.getCandidate().getEmail());
					submission.setPhoneNumber(submission.getCandidate().getMobilePhone());
					submission.setCandidateId(submission.getCandidate().getId().toString());
					submission.setRequirementId(submission.getRequirement().getId());
					submission.setRequirementName(submission.getRequirement().getJobTitle());
					
					reqSubmissionList.add(submission);
				}
			}
			requirementSubmission.setRequirementSubmissionList(reqSubmissionList);
			
			resultJson = commonUtil.toJSON(requirementSubmission);
			return resultJson;

		}
		
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getReferByMeJobSummaryPortletList", method = RequestMethod.GET)
		public String getReferByMeJobSummaryPortletList(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "requirementStatus", required = false) String status,@RequestParam(value = "locationCode", required = false) String locationCode,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			OrgLocations orglo = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobPortletsStartDate = null;
			Date jobPortletsEndDate = null;
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				jobPortletsStartDate =  commonUtil.orgLocationDateFormat(orglo.getDateFormat(),startDate);
				jobPortletsEndDate =  commonUtil.orgLocationDateFormat(orglo.getDateFormat(),endDate);
			
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
				if(status.equalsIgnoreCase("referByMe")){
					List<RequirementRefer> referedByMeJobsList = requirementReferService.getReferredByMeCountListByStartDateAndEndDate(user.getId(),jobPortletsStartDate, jobPortletsEndDate); 
					for(RequirementRefer rr : referedByMeJobsList){
						if(null != rr.getReferToUser()){
							StringBuilder referToUser = new StringBuilder();
							if(null != rr.getReferToUser().getLastName() && !"".equalsIgnoreCase(rr.getReferToUser().getLastName())){
								rr.setCandidateName(referToUser.append(rr.getReferToUser().getFirstName()).append(" ").append(rr.getReferToUser().getLastName()).toString());	
							}else{
								rr.setCandidateName(referToUser.append(rr.getReferToUser().getFirstName()).toString());
							}
							rr.setCandidateEmail(rr.getReferToUser().getEmail());
							rr.setCandidatePhoneNumber(rr.getReferToUser().getMobilePhone());
						}else{
							StringBuilder referToUser = new StringBuilder();
							if(null != rr.getReferedUserLastName() && !"".equalsIgnoreCase(rr.getReferedUserLastName())){
								rr.setCandidateName(referToUser.append(rr.getReferedUserFirstName()).append(" ").append(rr.getReferedUserLastName()).toString());	
							}else{
								rr.setCandidateName(referToUser.append(rr.getReferedUserFirstName()).toString());
							}
							rr.setCandidateEmail(rr.getReferToEmail());
						}
						
						rr.setRequirementName(rr.getRequirementId().getJobTitle());
						rr.setRequirementJobId(rr.getRequirementId().getJobId());
						rr.setRequirementUUID(rr.getRequirementId().getRequirementUUID());
						rr.setRequirementState(rr.getRequirementId().getRequirementSubmissionStatus().toString());
						rr.setReferredOn(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),rr.getCreatedDate()));
						
						/*RequirementRefer requirementRefer = requirementReferService.getAppliedRequirementReferByRequirementIdAndUserId(rr.getRequirementId().getId(), user.getId());
						if(null != requirementRefer){
							rr.setReferredOn(LoginSessionController.DATEFORMAT.format(requirementRefer.getCreatedDate()));
						}*/
					}
					refer.setReferByMeJobsList(referedByMeJobsList);
					
					
				}else{
				List<RequirementSubmission> referredByMeList =requirementSubmissionService.getReferByMeProfessionalJobSubmisssionsCountListBySubmissionType(user.getId(),status,jobPortletsStartDate, jobPortletsEndDate);
				for(RequirementSubmission rs : referredByMeList){
					StringBuilder candidateName = new StringBuilder();
					if(null != rs.getCandidate().getLastName()){
						rs.setCandidateName(candidateName.append(rs.getCandidate().getFirstName()).append(" ").append(rs.getCandidate().getLastName()).toString());	
					}else{
						rs.setCandidateName(candidateName.append(rs.getCandidate().getFirstName()).toString());
					}
					rs.setCandidateEmail(rs.getCandidate().getEmail());
					rs.setPhoneNumber(rs.getCandidate().getMobilePhone());
					rs.setRequirementName(rs.getRequirement().getJobTitle());
					rs.setRequirementJobId(rs.getRequirement().getJobId());
					rs.setRequirementUUID(rs.getRequirement().getRequirementUUID());
					rs.setRequirementSubmissionStatus(rs.getRequirement().getRequirementSubmissionStatus().toString());
					rs.setSubmissionStatus(rs.getSubmissionStatus());
					RequirementRefer requirementRefer = requirementReferService.getAppliedRequirementReferByRequirementIdAndUserId(rs.getRequirement().getId(), user.getId());
					if(null != requirementRefer){
						rs.setReferredOn(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),requirementRefer.getCreatedDate()));
					}
					
				}
				refer.setReferredByMeList(referredByMeList);
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		@RequestMapping(value = "/getProfessionalSubmissionDetails", method = RequestMethod.POST)
		public String getProfessionalSubmissionDetails(@RequestParam(value = "submissionId", required = false) Long submissionId,
				HttpServletRequest request) throws Exception {

			String resultJson = null;

			RequirementSubmission submission = requirementSubmissionService.getRequirementSubmissionById(submissionId);
			
			resultJson = toJSON(submission);
			return resultJson;

		}
		
		@PreAuthorize("hasAnyRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getRecruiterSubmttedJobSummaryPortletList", method = RequestMethod.GET)
		public String getRecruiterSubmttedJobSummaryPortletList(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "requirementStatus", required = false) String status,@RequestParam(value = "locationCode", required = false) String locationCode,HttpServletRequest request) throws Exception {
			String resultJson = null;
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orglo = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			RequirementRefer refer = new RequirementRefer();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date jobPortletsStartDate = null;
			Date jobPortletsEndDate = null;
			
			if(!isNullOrEmpty(startDate) && !isNullOrEmpty(endDate)){
				//Custom StartDate and EndDate
				
				jobPortletsStartDate =  commonUtil.orgLocationDateFormat(orglo.getDateFormat(),startDate);
				jobPortletsEndDate =  commonUtil.orgLocationDateFormat(orglo.getDateFormat(),endDate);
			
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
				
				List<RequirementSubmission> submittedList = requirementSubmissionService.getRecruiterSubmittedCandidatesCountListByStage(user.getId(),status,jobPortletsStartDate, jobPortletsEndDate);
				for(RequirementSubmission rs : submittedList){
					StringBuilder candidateName = new StringBuilder();
					if(null != rs.getCandidate().getLastName()){
						rs.setCandidateName(candidateName.append(rs.getCandidate().getFirstName()).append(" ").append(rs.getCandidate().getLastName()).toString());	
					}else{
						rs.setCandidateName(candidateName.append(rs.getCandidate().getFirstName()).toString());
					}
					rs.setCandidateEmail(rs.getCandidate().getEmail());
					rs.setPhoneNumber(rs.getCandidate().getMobilePhone());
					rs.setRequirementName(rs.getRequirement().getJobTitle());
					rs.setRequirementUUID(rs.getRequirement().getRequirementUUID());
					rs.setRequirementJobType(rs.getRequirement().getJobType());
					rs.setRequirementSubmissionStatus(rs.getRequirement().getRequirementSubmissionStatus().toString());
					rs.setCreatedDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),rs.getCreatedDate()));
					rs.setSubmissionStatus(rs.getSubmissionStatus());
					
				}
				refer.setSubmittedList(submittedList);
				
			}catch (ParseException e) {
				e.printStackTrace();
			}
			
			resultJson = commonUtil.toJSON(refer);
			return resultJson;

		}
		
		
		
		@PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getAllCrowdSourcingInterviews" , method = RequestMethod.POST)
		 public @ResponseBody  String getAllCrowdSourcingInterviews(@RequestParam(value="submissionId", required=false) Long submissionId, HttpServletRequest request) throws Exception {
			
			 String resultJson = null;
			 OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 List<RequirementInterviewHistory> interviewsList = requirementInterviewHistoryService.getCrowdSorurcingInterviews(submissionId,orgLocations);
			 resultJson = commonUtil.toJSON(interviewsList);
			 return resultJson;
		   }
		
		
		@PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")
		@RequestMapping(value = "/getCrowdSourcingOfferDetails")
		public @ResponseBody String getCrowdSourcingOfferDetails(@RequestParam(value="submissionId", required=false) Long submissionId,@RequestParam(value="locationCode", required=false) String locationCode,
				                                              HttpServletRequest request) throws Exception {
			
			
			String resultJson = null;
			
			List<RequirementOfferHistory> offersList = requirementOfferHistoryService.getCrowdSourcingOfferDetailsBySubmissionId(submissionId,locationCode);
			for(RequirementOfferHistory offers : offersList) {
				offers.setSubmissionStatusStr(offers.getSubmissionId().getSubmissionStatus().toString());
			}
			resultJson = commonUtil.toJSON(offersList);
			return resultJson ;
			
		}
		
		
		/*@PreAuthorize("hasRole('ROLE_CROWDSOURCING_REQUIREMENTS')")*/
		@RequestMapping(value = "/getCrowdSourcingUserContacts")
		public @ResponseBody String getCrowdSourcingUserContacts(@RequestParam(value="requirementId", required=false) Long requirementId,
				                                              HttpServletRequest request) throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
			String resultJson = null;
			List<ReferContacts> contactsList = referContactsService.getUserContactsListByUserIdAndRequirementId(user.getId(), requirementId);
			List<ReferContacts>  userContactsList = new ArrayList<ReferContacts>();
					
				if(null != contactsList){
					 for (ReferContacts contacts : contactsList) {
						
						StringBuilder nameBuilder = new StringBuilder();
						
						if(StringUtils.isNotBlank(contacts.getLastName())){
							contacts.setContactUserName(nameBuilder.append(contacts.getFirstName()).append(" ").append(contacts.getLastName()).toString());
						}else{
							contacts.setContactUserName(contacts.getFirstName());
						}
						userContactsList.add(contacts);
					}
				}
			 
			resultJson = commonUtil.toJSON(userContactsList);
			return resultJson;
		}
		
		
		 @RequestMapping(value = "/valdateRecruiterSubmittedCandidateEmail" , method = RequestMethod.POST)
		 public @ResponseBody  String valdateRecruiterSubmittedCandidateEmail(@RequestParam(value="email", required=false) String email,@RequestParam(value="requirementId", required=false) Long requirementId,HttpServletRequest request) throws Exception {
			 	
			 	String result = null;
			 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 	RequirementSubmission requirementSubmission = requirementSubmissionService.validateRecruiterSubmittedCandidateEmail(email,requirementId,user.getId());
			 	
			 	if (requirementSubmission != null) {
					result = "true";
				}else{
					result = "false";
				}
				return result;
		   }
		 
		 
		 @RequestMapping(value = "/setCrowdSourcingProfessionalProfileAsPublicVisibility" , method = RequestMethod.POST)
		 public @ResponseBody  String setCrowdSourcingProfessionalProfileAsPublicVisibility(HttpServletRequest request) throws Exception {
			 	
			 	Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
			 	Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			 	
			 	if (null != candidate) {
			 		if(isNullOrEmpty(candidate.getIsProfilePublic()) || candidate.getIsProfilePublic().equalsIgnoreCase("N")){
			 			candidate.setIsProfilePublic(Constants.YES);
			 		}else{
			 			candidate.setIsProfilePublic(Constants.NO);
			 		}
			 		
			 		candidateService.addCandidate(candidate);
					candidateService.saveOrUpdateCandidateElkDocument(candidate);
			 	}
			 	return "Success";
		 }
		 
		 
		 
		 @RequestMapping(value = "/displayContactUserImage/{contactUserId}", method = RequestMethod.GET)
			public void displayContactUserImage(@PathVariable Long contactUserId, HttpServletRequest request, HttpServletResponse response,
					HttpSession session) {

				ReferContacts referContacts = null;
				try {
					referContacts = referContactsService.getReferContactsById(contactUserId);

					/*if (referContacts != null && referContacts.getDocFile() != null) {
						InputStream in = new ByteArrayInputStream(referContacts.getDocFile());
						BufferedImage bi = ImageIO.read(in);
						OutputStream out = response.getOutputStream();
						ImageIO.write(bi, "jpg", out);
						out.close();
					}
*/
				} catch (IOException e) {
					e.printStackTrace();
				}

				catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		 
		 
		 
		 @RequestMapping(value = "/saveSourceleadCrowdSourcingJob" , method = RequestMethod.POST)
		 public @ResponseBody  String saveSourceleadCrowdSourcingJob(@RequestParam(value="requirementUUID", required=false) String requirementUUID,
				 @RequestParam(value="referId", required=false) String referId,HttpServletRequest request) throws Exception {
			 	
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 
			 Requirement requirement = requirementService.getRequirementByRequirementUUID(requirementUUID);
			 
			 if(null != requirement){
				 
				 SavedJobs savedJobs = new SavedJobs();
				 savedJobs.setRequirementId(requirement);
				 savedJobs.setUserId(user);
				 savedJobs.setOrgLocationId(orgLocation);
				 savedJobs.setSavedJobUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				 if(!isNullOrEmpty(referId) && !referId.equalsIgnoreCase("undefined")){
					 RequirementRefer refer = new RequirementRefer();
					 refer.setId(Long.parseLong(referId));
					 savedJobs.setReferId(refer);
				 }
				 savedJobs.setStatus(1);
				 savedJobs.setCreatedBy(user.getUserCode());
				 savedJobs.setCreatedDate(new Date());
				 savedJobs.setUpdatedBy(user.getUserCode());
				 savedJobs.setUpdatedDate(new Date());
				 savedJobsService.addSavedJobs(savedJobs);
			 }
			 
			 
			 return "Success";
		 }
		 
		 
		 @RequestMapping(value = "/deleteSourceleadSavedJob" , method = RequestMethod.POST)
		 public @ResponseBody  String deleteSourceleadSavedJob(@RequestParam(value="savedJobUUID", required=false) String savedJobUUID,
				 HttpServletRequest request) throws Exception {
			 	
			 
			 SavedJobs savedJobs = savedJobsService.getSavedJobsByUUID(savedJobUUID);
			 if(null != savedJobs){
				 savedJobsService.deleteSavedJobs(savedJobs);
			 }
			 
			 return "Success";
		 }
		 
		 
		@RequestMapping(value = "/addContactsGroup", method = RequestMethod.POST)
		public @ResponseBody Response<String> addContactsGroup(ContactsGroupDTO contactsGroupDTO,
					HttpServletRequest request,HttpServletResponse response)
					throws Exception {
			
			Response<String> contactsGroupResponse = new Response<>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			try {
				if (user != null) {

					if (Validator.validate(contactsGroupDTO)) {
						contactsGroupService.addContactsGroup(contactsGroupDTO, user);
						contactsGroupResponse.setResponse(Result.SUCCESS.name());
						contactsGroupResponse.setResponseMessage(Result.SUCCESS.getValue());
					} else {
						contactsGroupResponse.setResponse(Result.INVALID_DATA.name());
						contactsGroupResponse.setResponseMessage(Result.INVALID_DATA.getValue());
					}

				} else {
					contactsGroupResponse.setResponse(Result.SESSION_TIMEDOUT.name());
					contactsGroupResponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				} catch (Exception e) {
					contactsGroupResponse.setResponse(Result.AWKWARD.name());
					contactsGroupResponse.setResponseMessage(Result.AWKWARD.getValue());
					logger.error(ExceptionUtils.getFullStackTrace(e));
				}
			 
			return contactsGroupResponse;

			}
		
		   @RequestMapping(value = "/validateGroupName",  method = RequestMethod.GET)
		   public @ResponseBody Boolean validateGroupName(@RequestParam(value="groupName", required=false) String groupName,
				   @RequestParam(value="groupId", required=false) Long groupId,HttpServletRequest request) throws Exception {
			    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    ContactsGroup contactsGroup = null;
			    if(groupId != null){
			    	contactsGroup =contactsGroupService.validateGrouppNameByGroupNameAndGroupId(groupName, groupId);
			    }
			    Boolean flag = false;
		    	   if(null == contactsGroup){
		    		   flag = contactsGroupService.validateGroupNameByGroupNameAndUserId(groupName, user.getId());
		    		   
		    	   }
				return flag;
		   }
			
		@RequestMapping(value = "/getContactsGroupList", method = RequestMethod.POST)
		public List<ContactsGroup> getContactsGroupList(ContactsGroupDTO contactsGroupDTO,
					HttpServletRequest request,HttpServletResponse response)
					throws Exception {

				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				List<ContactsGroup> contactsGroupList =	contactsGroupService.getContactsGroupListByUserId(user.getId());
							
				return contactsGroupList;

			}
		
		@RequestMapping(value = "/getContactsGroupByGroupID", method = RequestMethod.POST, headers = "Accept=application/json")
		public @ResponseBody String getContactsGroupByGroupID(@RequestParam(value = "groupId", required = false) Long groupId, HttpServletRequest request) throws Exception
		{
			String resultJson = null;
			ContactsGroup contactsGroup = contactsGroupService.getContactsGroupByGroupId(groupId);
			if (contactsGroup != null) {
				resultJson = commonUtil.toJSON(contactsGroup);
			}
			return resultJson;
		}
		
		@RequestMapping(value = "/deleteContactsGroupById", method = RequestMethod.POST, headers = "Accept=application/json")
		public Response<String> deleteContactsGroupById(@RequestParam(value = "groupId", required = false) Long groupId, HttpServletRequest request) throws Exception
		{
			
			Response<String> response = new Response<>();
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			try {
				if (loginUser != null) {
				if(groupId >0) {
				contactsGroupService.deleteContactsGroupById(groupId);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());	
				}
				}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
				}
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			
			return response;
		}
		
		public String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
		
		@RequestMapping(value = "/getContactById", method = RequestMethod.POST)
		public  ReferContactsDTO getContactById(ReferContactsDTO referContactsDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
			ReferContactsDTO referContacDTO=new ReferContactsDTO();
			
      		ReferContacts referContacts= referContactsService.getReferContactsById(referContactsDTO.getId());
      		if(referContacts != null){
      			if(referContacts.getId() != null){
      				referContacDTO.setId(referContacts.getId());
      			}
      			if(referContacts.getFirstName() != null){
      				referContacDTO.setFirstName(referContacts.getFirstName());
      			}
      			if(referContacts.getLastName() != null){
      				referContacDTO.setLastName(referContacts.getLastName());
      			}
      			if(referContacts.getContactUserEmail() != null){
      				referContacDTO.setEmail(referContacts.getContactUserEmail());
      			}
      			if(referContacts.getCompanyName() != null){
      				referContacDTO.setCompanyName(referContacts.getCompanyName());
      			}
      			if(referContacts.getVendorType() != null){
      				referContacDTO.setContactType("Vendor");
      			}
      			if(referContacts.getCandidateType() != null){
      				referContacDTO.setContactType("Candidate");
      			}
      			if(referContacts.getClientType() != null){
      				referContacDTO.setContactType("Client");
      			}
      			if(referContacts.getOtherType() != null){
      				referContacDTO.setContactType("Other");
      			}
      			if(referContacts.getContactUserPhone() != null){
      				referContacDTO.setPhone(referContacts.getContactUserPhone().trim());
      			}
      		}
      		
      		return referContacDTO;
		}
		
		@RequestMapping(value = "/deleteContact", method = RequestMethod.POST)
		public  Response<String> deleteContact(ReferContactsDTO referContactsDTO,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
			Response<String> contactactResponse = new Response<>();
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			try {
				if (loginUser != null) {
				if(referContactsDTO.getId() >0) {
					referContactsService.deleteContactById(referContactsDTO.getId());
					contactactResponse.setResponse(Result.SUCCESS.name());
					contactactResponse.setResponseMessage(Result.SUCCESS.getValue());
				}else {
					contactactResponse.setResponse(Result.INVALID_DATA.name());
					contactactResponse.setResponseMessage(Result.INVALID_DATA.getValue());	
				}
				}else {
					contactactResponse.setResponse(Result.SESSION_TIMEDOUT.name());
					contactactResponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
				}
			}catch (Exception e) {
				contactactResponse.setResponse(Result.AWKWARD.name());
				contactactResponse.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			
			return contactactResponse;
			
		}
}
