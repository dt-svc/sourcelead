package com.bis.sourcelead.rest;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateHotList;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CandidateSkillsService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


/**
 * Handles requests for the Candidate List Service.
 * 
 * @author Sammeta David 
 * @date 26th August 2016
 *
 */
	
@RestController
public class CandidateListRestController {
	private static final Logger logger = LoggerFactory.getLogger(CandidateListRestController.class);
	
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
		
	 
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	
	 @Autowired
	 CommonUtil commonUtil;
	 

	 @Autowired
	 CandidateService candidateService;
	 
	 
	 @Autowired
	 CandidateSkillsService candidateSkillsService;
	 
	 @Autowired	
	 LookUpDataService lookUpDataService;
	
	  @Autowired
	  CandidateHotListService candidateHotListService;
	  
	  @Autowired
	  private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;
	 /**
	   * @author David and sarath kumar
	   * @since  aug 30 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting CandidatesList By OrgLocationID
	   * this method is called at onload when click on timesheet left menu
	   * 
	   * @param user This is the first parameter to getCandidatesListByOrgLocationID method
	   * @param navigation This is the second parameter to getCandidatesListByOrgLocationID method
	   * @param startDate This is the third parameter to getCandidatesListByOrgLocationID method
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @RequestMapping(value = "/getCandidatesListByOrgLocationID")
	 public String getCandidatesListByOrgLocationID(@RequestParam(value="candidateStatus", required=false) Integer  candidateStatus,@RequestParam(value="updatedDateRange", required=false) String  updatedDateRange,
			 @RequestParam(value="candidateType", required=false) String  candidateType,
			 HttpServletRequest request) throws Exception {

		String resultJson;
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Candidate> candList = new ArrayList<Candidate>();
		List<Candidate>   candidatesList = candidateService.getCandidatesListByOrgLocationID(loginOrgLocation.getId(),org.getId(),candidateStatus,updatedDateRange,candidateType);
		
		if(null != candidatesList){
			for(Candidate candidate : candidatesList){
				boolean flag = candidateHotListService.isCandidateIsFavourite(candidate.getId(), loginOrgLocation.getId());
				if(flag == true){
					candidate.setIsFavourite(Constants.YES);
				}else{
					candidate.setIsFavourite(Constants.NO);
				}
				candList.add(candidate);
			}
		}
		
		resultJson = commonUtil.toJSON(candList);
		return resultJson;
	 }
	 
	 
	  /**
	   * @author Naresh Bolisetty
	   * @since  Dec 28 2016
	   * @version 0.0.1
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 @RequestMapping(value = "/searchCandidate", method = RequestMethod.POST)
	 public String searchCandidate(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request) throws Exception {

		    MultipartFile multiFile = null;
			Iterator<String> itrator = request.getFileNames();
			if(itrator.hasNext()){
		        multiFile = request.getFile(itrator.next());
			}
			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			
			 Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			
			 BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			 
			 boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
			 
			 Long maxResults = candidateService.getCandidatesCountByOrgLocationID(loginOrgLocation.getId());
		   	 //Exclude Keyword
			 ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeKeyWord");
			 if(excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()){
			 	 BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
				 for(String keywd : excludeKeyWordArray){
					 BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
					ArrayList<MatchQueryBuilder> matchQueryBuilderList =getMatchQueryBuilderList(keywd);
					if(matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()){
						for(MatchQueryBuilder matchQueryBuilder :matchQueryBuilderList){
							excludeBoolSubQueryBuilder.should(matchQueryBuilder);
						}
						excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
					}
				}
				 boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
			  }
				
				//Any Keyword
				ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyOfKeyword");
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
				//All Keywords
				ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeywords");
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
				//Designation
				ArrayList<String> designationArray = getSearchKeywordsFromChosenSelectField(jsonObj, "designation");
				if(designationArray != null && !designationArray.isEmpty()){
					BoolQueryBuilder designationBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : designationArray){
						designationBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("designation", "*"+keywd+"*"));
					}
					boolQueryBuilder.must(designationBoolQueryBuilder);
				}
				
				//Notice_Period
				ArrayList<String> noticePeriodArray = getSearchKeywordsFromChosenSelectField(jsonObj, "noticePeriod");
				if(noticePeriodArray != null && !noticePeriodArray.isEmpty()){
					BoolQueryBuilder noticePeriodBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : noticePeriodArray){
						noticePeriodBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("noticePriod", keywd));
					}
					boolQueryBuilder.must(noticePeriodBoolQueryBuilder);
				}
			
				 //Country
				if(jsonObj.has("country") && StringUtils.isNotEmpty(jsonObj.getString("country"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("country", jsonObj.getString("country")));
			    }
				 //State
				if(jsonObj.has("state") && StringUtils.isNotEmpty(jsonObj.getString("state"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("state", jsonObj.getString("state")));
			    }
				//City
				if(jsonObj.has("city")  && StringUtils.isNotEmpty(jsonObj.getString("city"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("city", jsonObj.getString("city")));
			    }
				//minExpYears && maxExpYears
			    if(StringUtils.isNotEmpty(jsonObj.getString("minExpYears")) && StringUtils.isNotEmpty(jsonObj.getString("maxExpYears"))){
			    	String minYears= jsonObj.getString("minExpYears").replaceAll("[^0-9\\.]+", "");
			    	String maxYears= jsonObj.getString("maxExpYears").replaceAll("[^0-9\\.]+", "");
			    	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").gte(new Integer(minYears)).lte(new Integer(maxYears)));
			    	}
			    	catch(NumberFormatException e){}
			    }
				//minExpYears
			    else if(jsonObj.has("minExpYears") && StringUtils.isNotEmpty(jsonObj.getString("minExpYears"))){
			    	String minYears= jsonObj.getString("minExpYears").replaceAll("[^0-9\\.]+", "");
			    	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").gte(new Integer(minYears)));
			    	}
			    	catch(NumberFormatException e){}
			    }
			    //maxExpYears
			    else if(jsonObj.has("maxExpYears") && StringUtils.isNotEmpty(jsonObj.getString("maxExpYears"))){
                	String maxYears= jsonObj.getString("maxExpYears").replaceAll("[^0-9\\.]+", "");
                	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").lte(new Integer(maxYears)));
			    	}
			    	catch(NumberFormatException e){}
			    }
				 //Currency
			    if(jsonObj.has("currency") && StringUtils.isNotEmpty(jsonObj.getString("currency"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("expectedRateCurrencey", jsonObj.getString("currency")));
			    }
			    
			     //minRate && //axRate
			    if(jsonObj.has("minRate") && StringUtils.isNotEmpty(jsonObj.getString("minRate")) 
			    		&& jsonObj.has("maxRate") && StringUtils.isNotEmpty(jsonObj.getString("maxRate"))){
			    	String minRate= jsonObj.getString("minRate").replaceAll("[^0-9\\.]+", "");
			    	String maxRate= jsonObj.getString("maxRate").replaceAll("[^0-9\\.]+", "");
			    	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").gte(new Float(minRate)).lte(new Float(maxRate)));
			    	}
			    	catch(NumberFormatException e){}
			    }
				//minRate
			    else if(jsonObj.has("minRate") && StringUtils.isNotEmpty(jsonObj.getString("minRate"))){
			    	String minRate= jsonObj.getString("minRate").replaceAll("[^0-9\\.]+", "");
			    	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").gte(new Float(minRate)));
			    	}
			    	catch(NumberFormatException e){}
			    }
			    //maxRate
			    else if(jsonObj.has("maxRate") && StringUtils.isNotEmpty(jsonObj.getString("maxRate"))){
                	String maxRate= jsonObj.getString("maxRate").replaceAll("[^0-9\\.]+", "");
                	try{
			    		boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").lte(new Float(maxRate)));
			    	}
			    	catch(NumberFormatException e){}
			    }
				
			   //Rate Type
				if(jsonObj.has("rateType") && StringUtils.isNotEmpty(jsonObj.getString("rateType"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("rateType", jsonObj.getString("rateType")));
			    }
				
				//updatedOn
				if(jsonObj.has("updatedOn") && StringUtils.isNotEmpty(jsonObj.getString("updatedOn"))){
					try{
						 Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					     c.setTime(new Date());
					     Integer dateRange = Integer.parseInt(jsonObj.getString("updatedOn"));
				         c.add(Calendar.DATE, -dateRange);
				         Date utilDate = c.getTime();
				 	     boolQueryBuilder.must(QueryBuilders.rangeQuery("updatedDate").gte(utilDate));
					}
			    	catch(NumberFormatException e){}
			    }
				
				//Educations
				ArrayList<String> educationArray = getSearchKeywordsFromChosenSelectField(jsonObj, "education");
				if(educationArray != null && !educationArray.isEmpty()){
					BoolQueryBuilder edunBoolQueryBuilder = new BoolQueryBuilder();
					for(String keywd : educationArray){
						edunBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("education", keywd));
					}
					boolQueryBuilder.must(edunBoolQueryBuilder);
				} 
				
				//Exclude Educations
				ArrayList<String> excludeEducationArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeEducation");
				if(excludeEducationArray != null && !excludeEducationArray.isEmpty()){
					BoolQueryBuilder excludeEdunBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : excludeEducationArray){
						excludeEdunBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("education", keywd));
					}
					boolQueryBuilder.mustNot(excludeEdunBoolQueryBuilder);
				} 
				//Certifications
				ArrayList<String> certificationArray = getSearchKeywordsFromChosenSelectField(jsonObj, "certification");
				if(certificationArray != null && !certificationArray.isEmpty()){
					BoolQueryBuilder certificationBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : certificationArray){
						certificationBoolQueryBuilder.should(QueryBuilders.matchQuery("certifications", keywd));
					}
					boolQueryBuilder.must(certificationBoolQueryBuilder);
				}
				
				//Exclude Employers
				ArrayList<String> excludeEmployerArray = getSearchKeywordsFromChosenSelectField(jsonObj, "excludeEmployers");
				if(excludeEmployerArray != null && !excludeEmployerArray.isEmpty()){
					BoolQueryBuilder excldEmployerBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : excludeEmployerArray){
						excldEmployerBoolQueryBuilder.should(QueryBuilders.matchQuery("employer", keywd));
					}
					boolQueryBuilder.mustNot(excldEmployerBoolQueryBuilder);
				} 
				
				//Employers
				ArrayList<String> employerArray = getSearchKeywordsFromChosenSelectField(jsonObj, "employers");
				if(employerArray != null && !employerArray.isEmpty()){
					BoolQueryBuilder employerBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : employerArray){
						employerBoolQueryBuilder.should(QueryBuilders.matchQuery("employer", keywd));
					}
					boolQueryBuilder.must(employerBoolQueryBuilder);
				}
				 
				//applicant type
				if(jsonObj.has("applicantType") && StringUtils.isNotEmpty(jsonObj.getString("applicantType"))){
					boolQueryBuilder.must(QueryBuilders.matchQuery("candidateType", jsonObj.getString("applicantType")));
			    }
				
				
				//CandidateSkills
				ArrayList<String> candidateSkillsArray = getSearchKeywordsFromChosenSelectField(jsonObj, "candidateSkills");
				if(candidateSkillsArray != null && !candidateSkillsArray.isEmpty()){
					BoolQueryBuilder skillBoolQueryBuilder = QueryBuilders.boolQuery();
					for(String keywd : candidateSkillsArray){
						skillBoolQueryBuilder.should(QueryBuilders.matchQuery("skills", keywd));
					}
					boolQueryBuilder.must(skillBoolQueryBuilder);
				} 
				
				SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,0,maxResults.intValue(),boolQueryBuilder).getHits();
				ArrayList<Candidate>   candList = new ArrayList<>();
				if(searchHits != null && searchHits.length >0){
					for (SearchHit hit : searchHits) {
						Candidate candidate = candidateService.getCandidateByCandidateId(Long.parseLong(hit.getId()));
						boolean flag = candidateHotListService.isCandidateIsFavourite(candidate.getId(), loginOrgLocation.getId());
						if(flag == true){
							candidate.setIsFavourite(Constants.YES);
						}else{
							candidate.setIsFavourite(Constants.NO);
						}
						candList.add(candidate);
					}
				}
			 String resultJson;
			 resultJson = commonUtil.toJSON(candList);
			 return resultJson;
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
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("firstName", keywd));
		    matchQueryBuilderList.add(QueryBuilders.matchQuery("lastName", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("alternateEmail", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("country", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("city", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("state", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("address", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("designation", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("designationType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRateType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRateCurrency", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("rateType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("expectedRateCurrencey", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("summary", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("education", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("skills", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("employer", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("certifications", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("candidateType", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("noticePriod", keywd));
			//matchQueryBuilderList.add(QueryBuilders.matchQuery("currentResumeName", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("resumeContent", keywd));
			
			
			Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
			if(matcher.find()){
				Float expYears = Float.valueOf(matcher.group());
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("experienceYears", expYears));
				//matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("experienceMonths", keywd));
				String mobileKeywd =matcher.group().replaceAll("[+91]", "");
				matchQueryBuilderList.add(QueryBuilders.matchQuery("alternateMobilePhone", mobileKeywd));
				matchQueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", mobileKeywd));
			}
			if(StringUtils.isNumeric(keywd)){
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRate", keywd));
				matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("expectedRate", keywd));
			}
			
				
			return matchQueryBuilderList; 
	 }
	 
	 
	 @PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	 @RequestMapping(value = "/getHotListCandidates")
	 public String getHotListCandidates(@RequestParam(value="candidateType", required=false) String  candidateType,
			 HttpServletRequest request) throws Exception {

		String resultJson;
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Candidate> candList = new ArrayList<Candidate>();
		List<Candidate>   candidatesList = candidateService.getHotListCandidatesListByOrgLocationID(loginOrgLocation.getId(), candidateType);
		
		resultJson = commonUtil.toJSON(candidatesList);
		return resultJson;
	 }
		 
		 
	 /**
	   * This Method is for getting candidate by candidate ID
	   * @author David Raju
	   * 
	   * @param candidateId This is the first parameter to getCandidateById method
	   * @param request This is the second parameter to getCandidateById method
	   * 
	   * @return Candidate This is the third parameter to getCandidateById method
	   * 
	   * @throws Exception
	   */
	 @RequestMapping(value = "/getCandidateById" , method = RequestMethod.POST)
	 public @ResponseBody  String getCandidateById(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
			String resultJson;
			Candidate can = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId));
			StringBuilder skillsList = new StringBuilder();
			List<CandidateSkills> candidateSkills = candidateSkillsService.getCandidateSkillsByCandidateId(candidateId);
			int i = 0;
			for(CandidateSkills skilss: candidateSkills){
				if(i <= 5){
					skillsList = skillsList.append(skilss.getName());
					if(i != candidateSkills.size()-1){
						skillsList = skillsList.append(",");
					}
				}
				i++;
			}
			can.setSkillsList(skillsList.toString());
			resultJson = commonUtil.toJSON(can);
			return resultJson;
     }
			 

	 /**
	   *  This Method is for deleting CandidateImage
	   *  
	   * @author sarath kumar
	   * 
	   * @param candidateId This is the first parameter to deleteCandidateImage method
	   * @param request    This is the second parameter to deleteCandidateImage method
	   * 
	   * @return String
	   * 
	   * @throws NullPointerException
	   */
	 @RequestMapping(value = "/deleteCandidateImage" , method = RequestMethod.GET)
	   public String deleteCandidateImage(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
		   
		   OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		   Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId), loginOrgLocation.getId());
		   candidate.setDocFile(null);
		   candidate.setFileContentType(null);
		   candidate.setFileName(null);
		   candidateService.addCandidate(candidate);
		   return "success";
		   
	  }
	   
	  /**
		*  This Method is for getting CandidatesSkills By candidateID
		*  
		* @author sarath kumar
		* 
		* @param candidateId  This is the first parameter to getCandidatesSkillsBycandidateID method
		* @param request      This is the second parameter to getCandidatesSkillsBycandidateID method
		* 
		* @return String
		* 
		* @throws NullPointerException
		*/
	  @RequestMapping(value = "/getCandidatesSkillsBycandidateID")
	  public String getCandidatesSkillsBycandidateID(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
	
			String resultJson;
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<CandidateSkills>  candidateSkillsList =  candidateSkillsService.getCandidateSkillsByCandidateId(candidateId);
			Set<CandidateSkills> candidateSkillsSet = new HashSet<>(candidateSkillsList);
			resultJson = commonUtil.toJSON(candidateSkillsSet);
			return resultJson;
			
	   }
		 
	   /**
		 *  This Method is for saving Skils By candidateID
		 *  
		 * @author sarath kumar
		 * @param candidateId This is the first parameter to saveSkilsBycandidateID method
		 * @param skillName   This is the second parameter to saveSkilsBycandidateID method
		 * @param request     This is the third parameter to saveSkilsBycandidateID method
		 * 
		 * @return String
		 * 
		 * @throws NullPointerException
		 */
	   @RequestMapping(value = "/saveSkilsBycandidateID")
	   public String saveSkilsBycandidateID(@RequestParam(value="candidateId", required=false) String  candidateId,@RequestParam(value="skillName", required=false) String  skillName,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String resultJson;
			
			String[] skills = skillName.split(",");
			
   
			//skills = new HashSet<String>(Arrays.asList(skills)).toArray(new String[0]);
			
			
			for(String skill   : skills){
				
				CandidateSkills candidateSkills2 = candidateSkillsService.getSkillsByCandidateIdSkillName(candidateId, skill);
			    if(candidateSkills2 == null ){
						CandidateSkills cs = new CandidateSkills();
						Candidate  candidate =  candidateService.getCandidateByCandidateId(Long.parseLong(candidateId));
						cs.setCandidateId(candidate);
						cs.setStatus(Constants.CANDIDATE_SKILL_ACTIVE);
						cs.setOrgLocationID(loginOrgLocation);
						cs.setName(skill);
						List<String> skillList = Arrays.asList(skills);
						lookUpDataService.getSkillsBySkillsName(skillList);
						candidateSkillsService.storeCandidateSkills(cs);
				 }
			}
			return "success";
			
		 }
	   
	   
	   @RequestMapping(value = "/getSkillsExcludeExistingSkills")
	   public String getSkillsExcludeExistingSkills(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String resultJson;
			
				List<String> candidateSkillNames = candidateSkillsService.getCandidateSkillNamesByCandidateId(candidateId);
				List<LookUpData> skills = lookUpDataService.getSkillsBySkillsName(candidateSkillNames);
				resultJson = commonUtil.toJSON(skills);
				return resultJson;
		 }
	   
	   
	   /**
		 *  This Method is for remove Skill By Skill Id
		 *  
		 * @author venkat Kesav
		 * @param skillId   This is the second parameter to removeSkillBySkillId method
		 * @param request     This is the third parameter to removeSkillBySkillId method
		 * 
		 * @return String
		 * 
		 * @throws NullPointerException
		 */
	   @RequestMapping(value = "/removeSkillBySkillId")
	   public String removeSkillBySkillId(@RequestParam(value="skillId", required=false) String  skillId,HttpServletRequest request) throws Exception {
		  
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    if(StringUtils.isNotEmpty(skillId)){
		    	CandidateSkills candidateSkills = candidateSkillsService.getCandidateSkillBySkillId(Long.parseLong(skillId));
				candidateSkills.setStatus(Constants.CANDIDATE_SKILL_IN_ACTIVE);
				candidateSkills.setUpdatedBy(user.getUserCode());
				candidateSkills.setUpdatedDate(commonUtil.currentDate());
				candidateSkillsService.storeCandidateSkills(candidateSkills);
				return "success";
		    }else{
				return "exception";
			}
		 }
	   
	   
	   /**
		 * This Method is for saving  Candidate Profile
		 * 
		 * @author sarath kumar
		 * 
		 * @param candidateId  This is the first parameter to saveCandidateProfile method
		 * @param request      This is the second parameter to saveCandidateProfile method
		 * 
		 * @return String 
		 * 
		 * @throws NullPointerException
	     */
	   @RequestMapping(value = "/saveCandidateProfile" , method = RequestMethod.POST)
	   public String saveCandidateProfile(@RequestParam(value="candidateId", required=false) String candidateId ,MultipartHttpServletRequest request) throws Exception {
			
		   
		    Iterator<String> itrator = request.getFileNames();
	        MultipartFile multiFile = request.getFile(itrator.next());
		
	        OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId), loginOrgLocation.getId());
		    if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
		    	  try {
		    		  candidate.setFileContentType(multiFile.getContentType());
		    		  candidate.setDocFile(multiFile.getBytes());
		    		  candidate.setFileName(multiFile.getOriginalFilename());
			       }catch (Exception e) {
		    	      	 e.printStackTrace();
				   }
		    }
		    candidateService.addCandidate(candidate);
		    return "success";
	   }
	   
	   
	   /**
		 * This Method is for displaying  Candidate Image
		 * 
		 * @author sarath kumar
		 * 
		 * @param candidateId   This is the first parameter to displayCandidateImage method
		 * @param response      This is the second parameter to displayCandidateImage method
		 * @param session       This is the third parameter to displayCandidateImage method
		 * @param request       This is the fourth parameter to displayCandidateImage method
		 * 
		 * @return void
		 * 
		 * @throws NullPointerException
		 */ 
	   @RequestMapping(value = "/displayCandidateImage/{candidateId}" , method = RequestMethod.GET)
	     public void displayUserImage(@PathVariable Long candidateId,HttpServletRequest request,HttpServletResponse response,HttpSession session) {
	    	 
		   Candidate candidate = null;
				try {
					 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
					 candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(candidateId, loginOrgLocation.getId());
					
					if(candidate != null && candidate.getDocFile() != null)
					{
						candidate.setFileContentType("image/jpeg");
						InputStream in = new ByteArrayInputStream(candidate.getDocFile());
						BufferedImage bi = ImageIO.read(in);
						OutputStream out = response.getOutputStream();
						ImageIO.write(bi, "jpg", out);
						out.close();
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} 
				
				catch (Exception e1) {
					e1.printStackTrace();
				}
				
	  }
	   
	  /**
		 * This Method is for update Candidate details
		 * 
		 * @author David Raju
		 * 
		 * @param json   This is the first parameter to updateCandidate method
		 * @param  request    This is the second parameter to updateCandidate method
		 * 
		 * @return String
		 * 
		 * @throws NullPointerException
		 */ 
	   @RequestMapping(value = "/updateCandidate")
	   public String updateCandidate(@RequestParam(value="json", required=false) String  json,HttpServletRequest request) throws Exception {

		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    
			MultipartFile multiFile = null;
			Candidate candidate ;
			String jsonc = "{" + json + "}";
			JSONObject jsonObj = new JSONObject(jsonc);
			
			StringBuilder candidateCreateMsg = new StringBuilder();
			candidate = candidateService.storeCandidate(jsonObj,loginOrgLocation,user,multiFile);
				if(jsonObj.has("candidateId") && jsonObj.getString("candidateId") != null && !"".equals(jsonObj.getString("candidateId"))){
					SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg", candidateCreateMsg.append("Success!!  ").append(candidate.getFirstName()).append(" Details Updated").toString());

				}

			return "success";
			
		 }
		 
	   
	   /**
		 *  This Method is for update Candidate details
		 * @author David Raju
		 * @param json   This is the first parameter to updateCandidate method
		 * @param  request    This is the second parameter to updateCandidate method
		 * @return String
		 * @throws NullPointerException
		 */ 
	   @RequestMapping(value = "/deleteCandidateByCandidateId")
	   public @ResponseBody  String deleteCandidateByCandidateId(@RequestParam(value="candidateId", required=false) String  candidateId,@RequestParam(value="reason", required=false) String  reason, HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 
			Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),loginOrgLocation.getId());
			if(candidate != null){
				
					candidate.setStatus(Constants.CANDIDATE_IN_ACTIVE);
					if(StringUtils.isNotEmpty(reason)){
						candidate.setTerminateReason(reason);
					}
				
				candidate.setUpdatedBy(user.getUserCode());
				candidate.setUpdatedDate(commonUtil.currentDate());
				candidateService.saveOrUpdateCandidate(candidate);
				StringBuilder candidateRemoveMsg = new StringBuilder();
				SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg", candidateRemoveMsg.append("").append(candidate.getFirstName()).append(" Removed").toString());
	
			
			}
			return "success";
		 }
	   
	   
	   
	   @PreAuthorize("hasRole('ROLE_ADD_CANDIDATE_HOTLIST')")
	   @RequestMapping(value = "/saveCandidateAsFavourite", method = RequestMethod.POST)
		public @ResponseBody String saveCandidateAsFavourite(@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Candidate candidate = new Candidate();
			candidate.setId(candidateId);
			
			CandidateHotList hotList = candidateHotListService.getCandidateHotListByCandidateIdAndOrgLocationId(candidateId, orgLocation.getId());
			if(null != hotList){
				if(hotList.getStatus().equals(1)){
					hotList.setStatus(0);
				}else{
					hotList.setStatus(1);
				}
				hotList.setTeamId(null);
				hotList.setUpdatedBy(user.getUserCode());
				hotList.setUpdatedDate(new Date());
				candidateHotListService.saveCandidateHotList(hotList);
				
			}else{
				hotList = new CandidateHotList();
				hotList.setCandidateId(candidate);
				hotList.setOrgLocationId(orgLocation);
				hotList.setStatus(1);
				hotList.setCreatedBy(user.getUserCode());
				hotList.setCreatedDate(new Date());
				hotList.setUpdatedBy(user.getUserCode());
				hotList.setUpdatedDate(new Date());
				candidateHotListService.saveCandidateHotList(hotList);
			}
			
			
			
			return "success";
		}
	   
	   
	   @PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	   @RequestMapping(value = "/assignHotListCandiddatesToTeam", method = RequestMethod.POST)
		public @ResponseBody String assignHotListCandiddatesToTeam(@RequestParam(value = "teamId", required = false) Long teamId,
				@RequestParam(value = "hotlist", required = false) String hotlist,HttpServletRequest request) throws Exception {
			
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			String[] candidateIds = hotlist.split(",");
			Team team =new Team();
			team.setId(teamId);
			
			for(int i = 0; i < candidateIds.length; i++){
				
				CandidateHotList hotList = candidateHotListService.getCandidateHotListByCandidateIdAndOrgLocationId(Long.valueOf(candidateIds[i]), orgLocation.getId());
				hotList.setTeamId(team);
				hotList.setUpdatedBy(user.getUserCode());
				hotList.setUpdatedDate(new Date());
				candidateHotListService.saveCandidateHotList(hotList);
			}
			
			
			return "success";
		}
	   
	   
	   
	 
	   
	   
	   /**
		 *  This Method is for reActivate Candidate By CandidateId
		 * @author David Raju
		 * @param candidateId   This is the first parameter to reActivateCandidateByCandidateId method
		 * @param  reActivate    This is the second parameter to reActivateCandidateByCandidateId method
		 * @return String
		 * @throws NullPointerException
		 */ 
	   @RequestMapping(value = "/reActivateCandidateByCandidateId")
	   public @ResponseBody  String reActivateCandidateByCandidateId(@RequestParam(value="candidateId", required=false) String  candidateId,@RequestParam(value="reActivate", required=false) String  reActivate,
			                                                          HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 
			Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),loginOrgLocation.getId());
			if(candidate != null){
				if(reActivate.equalsIgnoreCase("1")){
					candidate.setStatus(Constants.CANDIDATE_ACTIVE);
				}
				candidate.setUpdatedBy(user.getUserCode());
				candidate.setUpdatedDate(commonUtil.currentDate());
				candidateService.saveOrUpdateCandidate(candidate);
			}
			return "success";
		 }
	   
	   /**
 		 * This Method is for save field Candidate details
 		 * 
 		 * @author sarath kumar
 		 * 
 		 * @param json   This is the first parameter to updateCandidate method
 		 * @param name   This is the first parameter to updateCandidate method
 		 * @param value   This is the first parameter to updateCandidate method
 		 * @param request    This is the second parameter to updateCandidate method
 		 * 
 		 * @return String
 		 * 
 		 * @throws NullPointerException
 		 */ 
	 	 @RequestMapping(value = "/fieldUpdateCandidate")
	 	 public String fieldUpdateCandidate(@RequestParam(value="candidateSkillId", required=false) String  candidateSkillId,@RequestParam(value="name", required=false) String  name,@RequestParam(value="value", required=false) String  value,HttpServletRequest request) throws Exception {
	 		       
	 		 	CandidateSkills candidateSkills =  candidateSkillsService.getCandidateSkillBySkillId(Long.parseLong(candidateSkillId));
	 		 	if("scale".equalsIgnoreCase(name)){
	 		 		candidateSkills.setScale(value);
	 		 	}
	 		 	if("totalExperience".equalsIgnoreCase(name)){
	 		 		candidateSkills.setTotalExperience(value);
	 		 	}
	 		 	candidateSkillsService.storeCandidateSkills(candidateSkills);
	 			return "success";
	 			
	 	 }
	 		 /**
	 		  * @author Rajashekar
	 		  * get candidate data to export excel and pdf
	 		  * @param candidateStatus
	 		  * @param updatedDateRange
	 		  * @param request
	 		  * @throws Exception
	 		  */
	 	 @RequestMapping(value = "/getCandidatesExportList")
		 public void getCandidatesExportList(@RequestParam(value="candidateStatus", required=false) Integer  candidateStatus,
				                             @RequestParam(value="updatedDateRange", required=false) String  updatedDateRange,
				                             @RequestParam(value="exportType", required=false) String  exportType,
				                             @RequestParam(value="candidateType", required=false) String  candidateType,HttpServletRequest request,HttpServletResponse response) throws Exception {

	 		 Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<Candidate>   candidatesList = candidateService.getCandidatesListByOrgLocationID(loginOrgLocation.getId(),org.getId(),candidateStatus,updatedDateRange,candidateType);
			File file = null;
			if(!"pdf".equalsIgnoreCase(exportType)){
			file = candidateService.getCandidateExcelFile(candidatesList,org.getOrgName());
			}else{
			file = candidateService.getCandidatePdf(candidatesList,org.getOrgName());
			}
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			
			StringBuilder filename = new StringBuilder();
			//changed string to StringBuilder(Prasad Salina)
			response.setHeader("Content-Disposition", filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
			response.getOutputStream().close();
			response.getOutputStream().flush();	
			if(file!= null){
				file.delete();
			}
		 }
	}
