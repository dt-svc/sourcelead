package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CampaignIndex;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementLocations;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.LoginSessionController;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class OpenJobsRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private RequirementReferService requirementReferService;

	@Autowired
	private RequirementSubmissionService requirementSubmissionService;

	@Autowired
	private SourceleadELKBaseIndexer<CampaignIndex> sourceleadELKBaseIndexer;

	@Autowired
	private RequirementService requirementService;

	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgService orgService;
	
	@Autowired
	RequirementLocationsService requirementLocationsService;

	@RequestMapping(value = { "/app/ws/getOpenJobsELK", "/app/getOpenJobsELK" })
	public @ResponseBody ResponseList<RequirementIndex> getOpenJobsELK(@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "openJobsRequirementTitle", required = false) String openJobsRequirementTitle,
			@RequestParam(value = "openJobsLocation", required = false) String openJobsLocation,
			@RequestParam(value = "openJobsExperience", required = false) String openJobsExperience,
			@RequestParam(value = "reqType", required = false, defaultValue = "") String reqType,
			@RequestParam(value = "orgLocationIds", required = false, defaultValue = "") String orgLocationIds,
			@RequestParam(value = "dates", required = false, defaultValue = "") String dates,
			@RequestParam(value = "country", required = false, defaultValue = "") String country,
			@RequestParam(value = "companies", required = false, defaultValue = "") String companies,
			@RequestParam(value = "postedOrgName", required = false, defaultValue = "") String postedOrgName,
			@RequestParam(value = "minOffset", required = false, defaultValue = "") Integer minOffset,
			HttpServletRequest request) throws Exception {

		boolean isELKSearchEnable = true;

//		String resultJson;
		ResponseList<RequirementIndex> response = new ResponseList<>();
		List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

	if("all".equalsIgnoreCase(openJobsRequirementTitle) || "all".equalsIgnoreCase(openJobsLocation)){
		
		openJobsRequirementTitle=openJobsRequirementTitle.replace("all","");
		
		openJobsLocation=openJobsLocation.replace("all","");
		
	}
	
		if ((null != openJobsRequirementTitle && !"".equalsIgnoreCase(openJobsRequirementTitle))
				|| (null != openJobsLocation && !"".equalsIgnoreCase(openJobsLocation))
				|| (null != openJobsExperience && !"".equalsIgnoreCase(openJobsExperience)
						&& !openJobsExperience.equalsIgnoreCase("undefined")
						&& !"null".equalsIgnoreCase(openJobsExperience))) {

			BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();

			BoolQueryBuilder openJobsRequirementQueryBuilder = QueryBuilders.boolQuery();
			if(StringUtils.isNotEmpty(openJobsRequirementTitle))
			 openJobsRequirementQueryBuilder.must(QueryBuilders.matchQuery("jobTitle",openJobsRequirementTitle));

			JSONObject jsonObj = new JSONObject();
			JSONObject json = jsonObj.put("jobTitle", openJobsRequirementTitle);
			if (jsonObj.has("jobTitle") && !"".equalsIgnoreCase(jsonObj.getString("jobTitle"))) {
				ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(json, "jobTitle");
				if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
					BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : anyKeyWordArray) {
						BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
						if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
							for (QueryBuilder QueryBuilder : QueryBuilderList) {
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					boolQueryBuilder.must(anyBoolQueryBuilder);
				}
			}
			
			JSONObject jsonObj1 = new JSONObject();
			JSONObject json1 = jsonObj1.put("location", openJobsLocation);
			if (jsonObj1.has("location") && !"".equalsIgnoreCase(jsonObj1.getString("location"))) {
				ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(json1, "location");
				if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
					BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : anyKeyWordArray) {
						BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
						if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
							for (QueryBuilder QueryBuilder : QueryBuilderList) {
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					boolQueryBuilder.must(anyBoolQueryBuilder);
				}
			}
			
			
			JSONObject jsonObj2 = new JSONObject();
			JSONObject json2 = jsonObj2.put("experience", openJobsExperience);
			if (jsonObj2.has("experience") && !"".equalsIgnoreCase(jsonObj2.getString("experience"))) {
				ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(json2, "experience");
				if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
					BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : anyKeyWordArray) {
						BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						
						BoolQueryBuilder anyBoolSubQueryBuilder1 = QueryBuilders.boolQuery();
						anyBoolSubQueryBuilder1.must(QueryBuilders.rangeQuery("minExperience").lte(keywd));
						anyBoolSubQueryBuilder1.must(QueryBuilders.rangeQuery("maxExperience").gte(keywd));
						anyBoolSubQueryBuilder.should(anyBoolSubQueryBuilder1);
						
						BoolQueryBuilder anyBoolSubQueryBuilder2 = QueryBuilders.boolQuery();
						anyBoolSubQueryBuilder2.must(QueryBuilders.matchPhraseQuery("minExperience", keywd));
						anyBoolSubQueryBuilder2.mustNot(QueryBuilders.existsQuery("maxExperience"));
						anyBoolSubQueryBuilder.should(anyBoolSubQueryBuilder2);
						
						BoolQueryBuilder anyBoolSubQueryBuilder3 = QueryBuilders.boolQuery();
						anyBoolSubQueryBuilder3.mustNot(QueryBuilders.existsQuery("minExperience"));
						anyBoolSubQueryBuilder3.must(QueryBuilders.rangeQuery("maxExperience").gte(keywd));
						anyBoolSubQueryBuilder.should(anyBoolSubQueryBuilder3);
						
						anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
					}
					boolQueryBuilder.must(anyBoolQueryBuilder);
				}
			}
			

			BoolQueryBuilder openJobsLocationQueryBuilder = QueryBuilders.boolQuery();
			BoolQueryBuilder openJobscombinedQueryBuilder = QueryBuilders.boolQuery();
			if(null != openJobsLocation && !"".equalsIgnoreCase(openJobsLocation)){
			openJobscombinedQueryBuilder.should(QueryBuilders.matchQuery("country", openJobsLocation));
			openJobscombinedQueryBuilder.should(QueryBuilders.matchQuery("city", openJobsLocation));
			openJobscombinedQueryBuilder.should(QueryBuilders.matchQuery("state", openJobsLocation));
			}
			openJobsLocationQueryBuilder.must(openJobscombinedQueryBuilder);

			BoolQueryBuilder combinedOpenToPublicAndCrowdSourcingQueryBuilder = QueryBuilders.boolQuery();
			combinedOpenToPublicAndCrowdSourcingQueryBuilder.should(QueryBuilders.matchQuery("isPublicJob", "Y"));
			boolQueryBuilder.must(combinedOpenToPublicAndCrowdSourcingQueryBuilder);
			boolQueryBuilder.must(combinedQueryBuilder);
			boolQueryBuilder.must(QueryBuilders.matchQuery("requirementStatus", "OPEN"));

		}
		else {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isPublicJob", "Y"));
			boolQueryBuilder.must(QueryBuilders.matchQuery("requirementStatus", "OPEN"));

			// for country filter
			if (!isNullOrEmpty(country)) {
				BoolQueryBuilder countryQueryBuilder = QueryBuilders.boolQuery();
				countryQueryBuilder.should(QueryBuilders.matchQuery("country", country));
				boolQueryBuilder.must(countryQueryBuilder);
			}

			// for dates filter
			if (!isNullOrEmpty(dates)) {
				String upOn = UpdateOnToDate(dates);
				if (!Constants.ALL_RECORDS.equalsIgnoreCase(upOn)) {
					RangeQueryBuilder qb = QueryBuilders.rangeQuery("createdDate").gte(upOn);
					boolQueryBuilder.must(qb);
				}
			}
			// for employment type filter
			String[] employmentType = null;
			if (null != reqType && !"".equals(reqType)) {
				employmentType = reqType.split("--");
			}

			List<String> empTypeList = new ArrayList<>();
			if (null != employmentType) {
				for (int i = 0; i < employmentType.length; i++) {
					empTypeList.add(employmentType[i]);
				}
			}
			if (!empTypeList.isEmpty()) {
				BoolQueryBuilder openJobsBuilder = QueryBuilders.boolQuery();
				for (String jobType : empTypeList) {
					BoolQueryBuilder openJobscombinedReqTypeQueryBuilder = QueryBuilders.boolQuery();
					openJobscombinedReqTypeQueryBuilder.should(QueryBuilders.matchQuery("jobType", jobType));
					openJobsBuilder.should(openJobscombinedReqTypeQueryBuilder);
				}
				boolQueryBuilder.must(openJobsBuilder);
			}

			
			// for jobs filter when clicked on org name
			if (postedOrgName != null && !"".equalsIgnoreCase(postedOrgName)) {
				BoolQueryBuilder postedOrgJobsBuilder = QueryBuilders.boolQuery();
				postedOrgJobsBuilder.should(QueryBuilders.matchQuery("postedOrg", postedOrgName));
				boolQueryBuilder.must(postedOrgJobsBuilder);
			}
			else{
				// for org specific job filter
				String[] Locations = null;
				if (null != companies && !"".equals(companies)) {
					Locations = companies.split(",");
				}
				
				if (null != orgLocationIds && !"".equals(orgLocationIds)) {
					Locations = orgLocationIds.split("--");
				}
				
				if (null != Locations) {
					BoolQueryBuilder orgOpenJobsBuilder = QueryBuilders.boolQuery();
					for (String locationId : Locations) {
						List<OrgLocations> list=orgLocationService.getAllOrgLocationsByOrgId(Long.parseLong(locationId));
						for(OrgLocations loc:list){
							BoolQueryBuilder openJobscombinedOrgQueryBuilder = QueryBuilders.boolQuery();
							openJobscombinedOrgQueryBuilder
									.should(QueryBuilders.matchQuery("orgLocationId", loc.getId()));
							orgOpenJobsBuilder.should(openJobscombinedOrgQueryBuilder);
						}
						
					}
					boolQueryBuilder.must(orgOpenJobsBuilder);
				}
			}

		}

		try {
			SearchHit[] searchHits = null;
			if (isELKSearchEnable) {
				if(offset < 10)
					offset = 10;
				else if(offset >= 10)
					offset += 10;
				searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX,
						Constants.ELK_REQUIREMENT_TYPE, minOffset, offset, boolQueryBuilder).getHits();
			}

			if (null != searchHits && searchHits.length > 0) {
				ObjectMapper mapper = new ObjectMapper();
				for (SearchHit hit : searchHits) {
					StringBuilder json = new StringBuilder(hit.getSourceAsString());
					mapper.setDateFormat(df);
					RequirementIndex requirementIndex = mapper.readValue(json.toString(), RequirementIndex.class);
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
		response.setData(requirementList);
//		resultJson = commonUtil.toJSON(requirementList);
		return response;

	}

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	public String UpdateOnToDate(String updatedOn) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		if ((Constants.TODAY).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, 0);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.ONE_DAY_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -1);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.THREE_DAY_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -3);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.ONE_WEEK_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -7);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.TWO_WEEK_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -14);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.ONE_MONTH_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -30);
			return dateFormat.format(cal.getTime());
		} else if ((Constants.THREE_MONTH_AGO).equalsIgnoreCase(updatedOn)) {
			cal.add(Calendar.DATE, -90);
			return dateFormat.format(cal.getTime());
		} else {
			return updatedOn;
		}
	}

	@RequestMapping(value = "/app/ws/openJobsRequiremetsAdvancedSearch", method = RequestMethod.GET)
	public @ResponseBody String openJobsRequiremetsAdvancedSearch(
			@RequestParam(value = "json", required = false) String json,
			@RequestParam(value = "offset", required = false) Integer offset, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resultJson = "";
		offset = 0;
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		List<RequirementIndex> requirementList = new ArrayList<RequirementIndex>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));

		boolQueryBuilder = getSearchRequirementsResults(boolQueryBuilder, jsonObj);

		BoolQueryBuilder combinedQueryBuilder = QueryBuilders.boolQuery();

		BoolQueryBuilder openJobsQueryBuilder = QueryBuilders.boolQuery();
		openJobsQueryBuilder.must(QueryBuilders.matchQuery("isPublicJob", "Y"));

		BoolQueryBuilder crowdSourcingJobsQueryBuilder = QueryBuilders.boolQuery();
		crowdSourcingJobsQueryBuilder.must(QueryBuilders.matchQuery("isCrowdSourcingJob", "Y"));

		combinedQueryBuilder.should(openJobsQueryBuilder);
		combinedQueryBuilder.should(crowdSourcingJobsQueryBuilder);
		boolQueryBuilder.must(combinedQueryBuilder);

		boolQueryBuilder.must(QueryBuilders.matchQuery("requirementStatus", "OPEN"));
		try {
			SearchHit[] searchHits = sourceleadELKBaseIndexer
					.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,
							offset, Constants.MAX_RESULTS_TEN, boolQueryBuilder)
					.getHits();
			if (null != searchHits && searchHits.length > 0) {
				ObjectMapper mapper = new ObjectMapper();
				for (SearchHit hit : searchHits) {
					StringBuilder json1 = new StringBuilder(hit.getSourceAsString());
					mapper.setDateFormat(df);
					RequirementIndex requirementIndex = mapper.readValue(json1.toString(), RequirementIndex.class);

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

		if (requirementList != null) {
			calculateDaysAndSkills(requirementList);
		}
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;

	}

	BoolQueryBuilder getSearchRequirementsResults(BoolQueryBuilder boolQueryBuilder, JSONObject jsonObj) {

		try {

			if (jsonObj.has("excludeKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("excludeKeyWord"))) {
				ArrayList<String> excludeKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj,
						"excludeKeyWord");
				if (excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()) {
					BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : excludeKeyWordArray) {
						BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
						if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
							for (QueryBuilder QueryBuilder : QueryBuilderList) {
								excludeBoolSubQueryBuilder.should(QueryBuilder);
							}
							excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
						}
					}
					boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
				}
			}

			// Any Keyword
			if (jsonObj.has("anyKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("anyKeyWord"))) {
				ArrayList<String> anyKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "anyKeyWord");
				if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
					BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : anyKeyWordArray) {
						BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
						if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
							for (QueryBuilder QueryBuilder : QueryBuilderList) {
								anyBoolSubQueryBuilder.should(QueryBuilder);
							}
							anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
						}
					}
					boolQueryBuilder.must(anyBoolQueryBuilder);
				}
			}

			// All Keywords
			if (jsonObj.has("allKeyWord") && !"".equalsIgnoreCase(jsonObj.getString("allKeyWord"))) {
				ArrayList<String> allKeyWordArray = getSearchKeywordsFromChosenSelectField(jsonObj, "allKeyWord");
				if (allKeyWordArray != null && !allKeyWordArray.isEmpty()) {
					BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : allKeyWordArray) {
						BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
						ArrayList<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
						if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
							for (QueryBuilder QueryBuilder : QueryBuilderList) {
								allBoolSubQueryBuilder.should(QueryBuilder);
							}
							allBoolQueryBuilder.must(allBoolSubQueryBuilder);
						}
					}
					boolQueryBuilder.must(allBoolQueryBuilder);
				}
			}

			// Requirement Title
			if (jsonObj.has("requirementTitle") && !"".equalsIgnoreCase(jsonObj.getString("requirementTitle"))) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("jobTitle", jsonObj.getString("requirementTitle")));
			}
			/*// Country
			if (jsonObj.has("country") && !"".equalsIgnoreCase(jsonObj.getString("country"))) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("country", jsonObj.getString("country")));
			}
			// State
			if (jsonObj.has("state") && !"".equalsIgnoreCase(jsonObj.getString("state"))) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("state", jsonObj.getString("state")));
			}
			// City
			if (jsonObj.has("city") && !"".equalsIgnoreCase(jsonObj.getString("city"))) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("city", jsonObj.getString("city")));
			}*/

			//multiple City
			if (jsonObj.has("city") && !"".equalsIgnoreCase(jsonObj.getString("city"))) {
				ArrayList<String> cityArray = getSearchKeywordsFromChosenSelectField(jsonObj, "city");
				if(cityArray != null && !cityArray.isEmpty()){
					BoolQueryBuilder multiCityBoolQueryBuilder = new BoolQueryBuilder();
					for(String keywd : cityArray){
						multiCityBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("city", keywd));
					}
					boolQueryBuilder.must(multiCityBoolQueryBuilder);
				} 
			}
			
			
			// currency
			if (jsonObj.has("currencyCode") && !"".equalsIgnoreCase(jsonObj.getString("currencyCode"))) {
				boolQueryBuilder
						.must(QueryBuilders.matchQuery("currentRateCurrency", jsonObj.getString("currencyCode")));
			}

			// minRate && maxRate
			if (jsonObj.has("minRate") && !"".equalsIgnoreCase(jsonObj.getString("minRate")) && jsonObj.has("maxRate")
					&& !"".equalsIgnoreCase(jsonObj.getString("maxRate"))) {
				String minRate = jsonObj.getString("minRate").replaceAll("[^0-9\\.]+", "");
				String maxRate = jsonObj.getString("maxRate").replaceAll("[^0-9\\.]+", "");
				try {
					boolQueryBuilder
							.must(QueryBuilders.rangeQuery("rate").gte(new Integer(minRate)).lte(new Integer(maxRate)));
				} catch (NumberFormatException e) {
				}
			}

			// Rate Type
			if (jsonObj.has("rateType") && !"".equalsIgnoreCase(jsonObj.getString("rateType"))) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("rateType", jsonObj.getString("rateType")));
			}

			if (jsonObj.has("noticePeriod") && !"".equalsIgnoreCase(jsonObj.getString("noticePeriod"))) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("noticePeriod", jsonObj.getString("noticePeriod")));
			}

			// minExpYears && maxExpYears
			if (jsonObj.has("minExp") && !"".equalsIgnoreCase(jsonObj.getString("minExp")) && jsonObj.has("maxExp")
					&& !"".equalsIgnoreCase(jsonObj.getString("maxExp"))) {
				String minYears = jsonObj.getString("minExp").replaceAll("[^0-9\\.]+", "");
				String maxYears = jsonObj.getString("maxExp").replaceAll("[^0-9\\.]+", "");
				try {
					boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").gte(new Integer(minYears))
							.lte(new Integer(maxYears)));
				} catch (NumberFormatException e) {
				}
			}
			// minExpYears
			else if (jsonObj.has("minExp") && !"".equalsIgnoreCase(jsonObj.getString("minExp"))) {
				String minYears = jsonObj.getString("minExp").replaceAll("[^0-9\\.]+", "");
				try {
					boolQueryBuilder.must(QueryBuilders.rangeQuery("minExperience").gte(new Integer(minYears)));
				} catch (NumberFormatException e) {
				}
			}
			// maxExpYears
			else if (jsonObj.has("maxExp") && !"".equalsIgnoreCase(jsonObj.getString("maxExp"))) {
				String maxYears = jsonObj.getString("maxExp").replaceAll("[^0-9\\.]+", "");
				try {
					boolQueryBuilder.must(QueryBuilders.rangeQuery("maxExperience").lte(new Integer(maxYears)));
				} catch (NumberFormatException e) {
				}
			}

			// Requirement Skills
			if (jsonObj.has("skills") && !"".equalsIgnoreCase(jsonObj.getString("skills"))) {
				ArrayList<String> candidateSkillsArray = getSearchKeywordsFromChosenSelectField(jsonObj, "skills");
				if (candidateSkillsArray != null && !candidateSkillsArray.isEmpty()) {
					BoolQueryBuilder skillBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : candidateSkillsArray) {
						skillBoolQueryBuilder.should(QueryBuilders.matchQuery("skills", keywd));
					}
					boolQueryBuilder.must(skillBoolQueryBuilder);
				}
			}

			// Requirement organizations
			if (jsonObj.has("organizations") && !"".equalsIgnoreCase(jsonObj.getString("organizations"))) {
				ArrayList<String> organizationArray = getSearchKeywordsFromChosenSelectField(jsonObj, "organizations");
				if (organizationArray != null && !organizationArray.isEmpty()) {
					BoolQueryBuilder skillBoolQueryBuilder = QueryBuilders.boolQuery();
					for (String keywd : organizationArray) {
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

	private ArrayList<String> getSearchKeywordsFromChosenSelectField(JSONObject jsonObj, String param)
			throws Exception {
		ArrayList<String> keyWordList = null;
		if (jsonObj.has(param)) {
			String searchKeywordString = null;
			try {
				JSONArray jsonArray = jsonObj.getJSONArray(param);
				searchKeywordString = jsonArray.toString();
			} catch (JSONException e) {
				searchKeywordString = jsonObj.getString(param);
			}

			searchKeywordString = searchKeywordString.replaceAll("\\[", "").replaceAll("\\]", "");
			keyWordList = new ArrayList<String>(Arrays.asList(searchKeywordString.split(",")));
		} else {
			keyWordList = new ArrayList<String>();
		}
		return keyWordList;
	}

	private ArrayList<QueryBuilder> getQueryBuilderList(String keywd) {

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
		if (matcher.find()) {
//			Float expYears = Float.valueOf(matcher.group());
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("minExperience", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("maxExperience", keywd));
			QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rate", keywd));
		}

		return QueryBuilderList;
	}

	public void calculateDaysAndSkills(List<RequirementIndex> requirementList) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (RequirementIndex requirement : requirementList) {
			long diff = CommonUtil.currentDate().getTime() - formatter.parse(requirement.getCreatedDate()).getTime();

			long diffDays = diff / (24 * 60 * 60 * 1000);
			StringBuilder time = new StringBuilder();

			if (diffDays == 0) {
				requirement.setCalDays("Today");
			} else if (diffDays > 0) {
				requirement.setCalDays(time.append(diffDays).append(" day(s) ago").toString());
			}

			if (null != requirement.getSkills()) {
				List<String> skillsList = new ArrayList<String>();
				String[] skills = requirement.getSkills();
				for (int i = 0; i < skills.length; i++) {
					skillsList.add(skills[i]);
				}
				requirement.setSkillsList(skillsList);
			}

		}
	}

	@RequestMapping(value = { "/app/ws/openRequirementsSummary", "/app/openRequirementsSummary" })
	public @ResponseBody Response<Requirement> openRequirementsSummary(
			@RequestParam(value = "requirementsUUID", required = false) String requirementsUUID) throws Exception {
		Response<Requirement> reponse = new Response<>();
		Requirement requirementDeatils = requirementService.getRequirementByRequirementUUID(requirementsUUID);
		Requirement requirementDeatilsSummary = requirementService.getRequirementByRequirementId(requirementDeatils.getId());
		requirementDeatilsSummary.setRequirementOrgName(requirementDeatilsSummary.getOrgLocationId().getOrgLocationName());
		requirementDeatilsSummary.setRequirementCreatedDateToString(commonUtil.dateStrFromOrgDateFormatter(requirementDeatils.getOrgLocationId().getDateFormat(), requirementDeatilsSummary.getCreatedDate()));
		
		List<String> requirementLocationsList = new ArrayList<>();
		List<RequirementLocations> locationsList = requirementLocationsService.getRequirementLocationsListByRequirementId(requirementDeatils.getId());
		if(locationsList != null && !locationsList.isEmpty()) {
			for(RequirementLocations location : locationsList) {
				requirementLocationsList.add(location.getLocation());
			}
		}
		requirementDeatilsSummary.setLocationsList(requirementLocationsList);
		reponse.setObject(requirementDeatilsSummary);
		return reponse;

	}


	@RequestMapping(value = { "/app/ws/getOrgsListforOpenJobs", "/app/getOrgsListforOpenJobs" })
	public @ResponseBody ResponseList<Org> getOrgsListforOpenJobs(
			@RequestParam(value = "alphabet", required = false) String alphabet,
			@RequestParam(value = "country", required = false) String country,@RequestParam(value = "type", required = false) String type) throws Exception {
		List<Org> orgList = null;
//		String json;
		ResponseList<Org> resList = new ResponseList<>();
		if ("none".equalsIgnoreCase(alphabet)) {
			orgList = orgService.getOpenJobsOrgs();
		} else {
			orgList = orgService.getAllOpenJobsOrgsStartsWithAlphabet(alphabet);
		}
		List<Org> toBeReturnedOrgList = new ArrayList<>();
		if("main".equalsIgnoreCase(type)){
			int i = 1;
			for (Org org : orgList) {
				if(i<=5){
					org.setNoOfDays(orgList.size());
					toBeReturnedOrgList.add(org);
				}else
					break;
				i++;
				
				
				/*if (null != country && country.equalsIgnoreCase(loc.getCountry())) {
					loc.setOrgName(loc.getOrgLocationName());
					countryfiltered.add(loc);
				} else {
					loc.setOrgName(loc.getOrgLocationName());
				}*/
			}
		}
		
		if (null != country && !"".equalsIgnoreCase(country)) {
			resList.setData(orgList);
		}
		else if("sub".equalsIgnoreCase(type)){
			resList.setData(orgList);
		}
		else {
			resList.setData(toBeReturnedOrgList);
		}
		return resList;
	}

}
