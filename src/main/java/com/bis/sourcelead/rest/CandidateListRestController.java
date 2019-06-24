package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.CandidateSearchDTO;
import com.bis.sourcelead.dto.CandidateSourceDTO;
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSource;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.SourceleadDeletedCandidates;
import com.bis.sourcelead.entity.SourceleadTags;
import com.bis.sourcelead.entity.SubmissionCandidateListView;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.CandidateCustomFieldDataService;
import com.bis.sourcelead.service.CandidateDocumentsService;
import com.bis.sourcelead.service.CandidateEducationService;
import com.bis.sourcelead.service.CandidateEmployersService;
import com.bis.sourcelead.service.CandidateHotListService;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
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

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	CandidateService candidateService;

	@Autowired
	CandidateResumesService candidateResumesService;

	@Autowired
	CandidateEducationService candidateEducationService;

	@Autowired
	CandidateEmployersService candidateEmployersService;

	@Autowired
	CandidateDocumentsService candidateDocumentsService;
	
	@Autowired
	LookUpDataService lookUpDataService;

	@Autowired
	CandidateHotListService candidateHotListService;

	@Autowired
	CountriesService countriesService;

	@Autowired
	TimeZoneService timeZoneService;

	@Autowired
	UserService userService;

	@Autowired
	OrgLocationService orgLocationService;

	@Autowired
	CandidateActivityCommonUtil candidateActivityCommonUtil;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;

	@Autowired
	private SourceleadELKBaseIndexer<CandidateIndex> sourceleadELKBaseIndexer;

	@Autowired
	private SourceleadActionsService sourceleadActionsService;

	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;

	@Autowired
	private CandidateCustomFieldDataService candidateCustomFieldDataService;
	
	@Autowired
	SourceleadTagsService sourceleadTagsService;

	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	private Date getPreviousDate(DateTime zoned, Timezone timezone) throws ParseException {
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTime yesterDayZoned = zoned;
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}

	@RequestMapping(value = "/getUniversalCandidatesListByOrgLocationID")
	public String getUniversalCandidatesListByOrgLocationID(@RequestParam(value = "serachValue", required = false) String serachValue,
			HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String resultJson;
		List<Object> uniCandidateList = candidateService.getAllUniversalSearchCandidateList(orgLocations, serachValue);
		resultJson = commonUtil.toJSON(uniCandidateList);
		return resultJson;
	}

	/**
	 * @author Naresh Bolisetty
	 * @since Dec 28 2016
	 * @version 0.0.1
	 *
	 *
	 * @return string
	 *
	 * @throws NullPointer
	 */
	@RequestMapping(value = "/searchCandidate", method = RequestMethod.POST)
	public ResponseList<CandidateIndex> searchCandidate(CandidateSearchDTO search, HttpServletRequest request) throws Exception {
		/*
		 * This array is used to enable the sorting feature in data tables. And, these
		 * names should match the ELK Candidate index field names.
		 */
		String[] tableColumns = { "", "isAssociate", "generatedId.keyword", "firstName.keyword", "primarySkill.keyword",
				"designationType.keyword", "email.keyword", "mobilePhone.keyword","country.keyword", "updatedDate","profilePercentage","noOfJobs", "createdBy.keyword" };

		int sortColumnIndex = 9; // by default sorting using updated date
		boolean sortColumnOrder = false; // by default descending order
		if (search.getOrder() != null && !search.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(search.getOrder().get(0).get("column"));
			sortColumnOrder = !search.getOrder().get(0).get("dir").equalsIgnoreCase("desc");
		}

		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

		BoolQueryBuilder boolQueryBuilderForCount = new BoolQueryBuilder();
		if (!Constants.YES.equalsIgnoreCase(search.getIsSharedInMarketplace())) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
				boolQueryBuilderForCount.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
				
		}else{
			boolQueryBuilder.mustNot(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
			boolQueryBuilderForCount.mustNot(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
		}


		if (Constants.YES.equalsIgnoreCase(search.getIsAssociate())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", "Y"));
			boolQueryBuilderForCount.must(QueryBuilders.matchQuery("isAssociate", "Y"));
			if (StringUtils.isNotEmpty(search.getCandidateType()) && !"All".equalsIgnoreCase(search.getCandidateType())) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("isFavouriteAssociate", search.getCandidateType()));
			}
		}
		
		if (Constants.YES.equalsIgnoreCase(search.getIsSharedInMarketplace())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isSharedInMarketplace", "Y"));
			boolQueryBuilderForCount.must(QueryBuilders.matchQuery("isSharedInMarketplace", "Y"));
		}
		

		if (!isNullOrEmpty(userRole) && userRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("recruiterUserId", user.getId()));
		}

		// Exclude Keyword
		List<String> excludeKeyWordArray = search.getExcludeKeyWord();
		if (excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()) {
			BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : excludeKeyWordArray) {
				if (StringUtils.isNotBlank(keywd)) {
					BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
					List<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
					if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
						for (QueryBuilder QueryBuilder : QueryBuilderList) {
							excludeBoolSubQueryBuilder.should(QueryBuilder);
						}
						excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
					}
				}
			}
			boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
		}

		// Any Keyword
		/*
		 * List<String> anyKeyWordArray = search.getAnyOfKeyword(); if(anyKeyWordArray
		 * == null) anyKeyWordArray = new ArrayList<>();
		 * if(StringUtils.isNotEmpty(search.getSearchValue())) {
		 * anyKeyWordArray.add(search.getSearchValue()); }
		 */
		List<String> anyKeyWordArray = search.getAnyOfKeyword();
		if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
			BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : anyKeyWordArray) {
				if (StringUtils.isNotBlank(keywd)) {
					BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
					List<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
					if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
						for (QueryBuilder QueryBuilder : QueryBuilderList) {
							anyBoolSubQueryBuilder.should(QueryBuilder);
						}

						DateFormat df = loginOrgLocation.getLocFullFormDateFormatter();
						try {
							if (keywd.contains("/")) {
								keywd = keywd.replaceAll("/", "-");
							}
							Date d = df.parse(keywd);
							anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
							String dateStr = commonUtil.elkDateFormatter(d);
							anyBoolSubQueryBuilder.should(QueryBuilders.termQuery("updatedDate", dateStr));
						} catch (Exception e) {
						}

						anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
					}
				}
			}
			boolQueryBuilder.must(anyBoolQueryBuilder);
		}
		// All Keywords
		List<String> allKeyWordArray = search.getAllKeywords();
		if (allKeyWordArray == null) {
			allKeyWordArray = new ArrayList<>();
		}
		if (StringUtils.isNotEmpty(search.getSearchValue())) {
			allKeyWordArray.add(search.getSearchValue());
		}

		if (allKeyWordArray != null && !allKeyWordArray.isEmpty()) {
			BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : allKeyWordArray) {
				if (StringUtils.isNotBlank(keywd)) {
					BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
					List<QueryBuilder> QueryBuilderList = getQueryBuilderList(keywd);
					if (QueryBuilderList != null && !QueryBuilderList.isEmpty()) {
						for (QueryBuilder QueryBuilder : QueryBuilderList) {
							allBoolSubQueryBuilder.should(QueryBuilder);
						}
						DateFormat df = loginOrgLocation.getLocFullFormDateFormatter();
						try{
							if(keywd.contains("/")){
								keywd = keywd.replaceAll("/", "a");
							}
							Date d = df.parse(keywd);
							allBoolSubQueryBuilder = QueryBuilders.boolQuery();
							String dateStr = commonUtil.elkDateFormatter(d);
							allBoolSubQueryBuilder.should(QueryBuilders.termQuery("updatedDate", dateStr));
						 }
						 catch(Exception e){}
						allBoolQueryBuilder.must(allBoolSubQueryBuilder);
					}
				}
			}
			boolQueryBuilder.must(allBoolQueryBuilder);
		}
		
		
		
		// Designation
		List<String> designationArray = search.getDesignation();
		if (designationArray != null && !designationArray.isEmpty()) {
			BoolQueryBuilder designationBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : designationArray) {
				designationBoolQueryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("designation", "*" + keywd + "*"));
			}
			boolQueryBuilder.must(designationBoolQueryBuilder);
		}

		// Notice_Period
		List<String> noticePeriodArray = search.getNoticePeriod();
		if (noticePeriodArray != null && !noticePeriodArray.isEmpty()) {
			BoolQueryBuilder noticePeriodBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : noticePeriodArray) {
				noticePeriodBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("noticePriod", keywd));
			}
			boolQueryBuilder.must(noticePeriodBoolQueryBuilder);
		}

		// Generated ID
		if (StringUtils.isNotEmpty(search.getGeneratedId())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("generatedId", search.getGeneratedId()));
		}

		// Country
		if (StringUtils.isNotEmpty(search.getCountry())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("country", search.getCountry()));
		}
		// State
		if (StringUtils.isNotEmpty(search.getState())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("state", search.getState()));
		}
		// City
		if (StringUtils.isNotEmpty(search.getCity())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("city", search.getCity()));
		}

		// primary skill
		if (StringUtils.isNotEmpty(search.getPrimarySkill())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("primarySkill", search.getPrimarySkill()));
		}

		// minExpYears && maxExpYears
		String minYears = search.getMinExpYears();
		minYears = StringUtils.isNotEmpty(minYears) ? minYears.replaceAll("[^0-9\\.]+", "") : "";
		String maxYears = search.getMaxExpYears();
		maxYears = StringUtils.isNotEmpty(maxYears) ? maxYears.replaceAll("[^0-9\\.]+", "") : "";
		if (StringUtils.isNotEmpty(minYears) && StringUtils.isNotEmpty(maxYears)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").gte(new Integer(minYears)).lte(new Integer(maxYears)));
			} catch (NumberFormatException e) {
			}
		} // minExpYears
		else if (StringUtils.isNotEmpty(minYears)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").gte(new Integer(minYears)));
			} catch (NumberFormatException e) {
			}
		} // maxExpYears
		else if (StringUtils.isNotEmpty(maxYears)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").lte(new Integer(maxYears)));
			} catch (NumberFormatException e) {
			}
		}
		// Currency
		if (StringUtils.isNotEmpty(search.getCurrency())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("expectedRateCurrencey", search.getCurrency()));
		}

		String minRate = search.getMinRate();
		minRate = StringUtils.isNotEmpty(minRate) ? minRate.replaceAll("[^0-9\\.]+", "") : "";
		String maxRate = search.getMaxRate();
		maxRate = StringUtils.isNotEmpty(maxRate) ? maxRate.replaceAll("[^0-9\\.]+", "") : "";
		if (StringUtils.isNotEmpty(minRate) && StringUtils.isNotEmpty(maxRate)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").gte(new Float(minRate)).lte(new Float(maxRate)));
			} catch (NumberFormatException e) {

			}
		} // minRate
		else if (StringUtils.isNotEmpty(minRate)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").gte(new Float(minRate)));
			} catch (NumberFormatException e) {
			}
		} // maxRate
		else if (StringUtils.isNotEmpty(maxRate)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").lte(new Float(maxRate)));
			} catch (NumberFormatException e) {
			}
		}

		String updatedDateRange = search.getUpdatedDateRange();
		if (StringUtils.isNotEmpty(updatedDateRange) && !"null".equalsIgnoreCase(updatedDateRange)
				&& !updatedDateRange.equalsIgnoreCase("all") && !updatedDateRange.equalsIgnoreCase("undefined")) {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Integer dateRange = Integer.parseInt(updatedDateRange);
			c.add(Calendar.DATE, -dateRange); // Subtracting dateRange days
			String updatedDate = dateFormat.format(c.getTime());
			Date newDate = dateFormat.parse(updatedDate);
			Date endDate2 = commonUtil.getOrgLocationCurrentDate(loginOrgLocation.getDateFormat(), newDate);

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			String endDateStr = dateFormat1.format(endDate2);
			boolQueryBuilder.must(QueryBuilders.rangeQuery("updatedDate").gte(endDateStr));

		}

		// Rate Type
		if (StringUtils.isNotEmpty(search.getRateType())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("rateType", search.getRateType()));
		}

		// Designation Type
		String designationType = search.getDesignationType();
		if (StringUtils.isNotEmpty(designationType)) {

			if (designationType.equalsIgnoreCase("Part Time")) {
				boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("designationType", "Part Time"));
				boolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("designationType", "Full Time"));
			} else if (designationType.equalsIgnoreCase("Full Time")) {
				boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("designationType", "Full Time"));
				boolQueryBuilder.mustNot(QueryBuilders.matchPhrasePrefixQuery("designationType", "Part Time"));
			} else {
				boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery("designationType", designationType));
			}

			// boolQueryBuilder.must(QueryBuilders.matchQuery("designationType",
			// jsonObj.getString("designationType")));
		}

		// employmentType
		if (StringUtils.isNotEmpty(search.getEmploymnetTypes())) {
			ArrayList<String> employmnetTypeArray = getSearchKeywordsFromChosenSelectField(search.getEmploymnetTypes());
			if (employmnetTypeArray != null && !employmnetTypeArray.isEmpty()) {
				BoolQueryBuilder employmnetTypeBoolQueryBuilder = new BoolQueryBuilder();
				for (String keywd : employmnetTypeArray) {
					employmnetTypeBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("designationType", keywd));
				}
				boolQueryBuilder.must(employmnetTypeBoolQueryBuilder);
			}
		}

		if (StringUtils.isNotBlank(search.getCandidateFilterSkills())) {
			String[] filterSkills = search.getCandidateFilterSkills().split(",");
			List<String> filterSkillsList = new ArrayList<>();
			for (int i = 0; i < filterSkills.length; i++) {
				filterSkillsList.add(filterSkills[i]);
			}
			if (filterSkillsList != null && !filterSkillsList.isEmpty()) {
				BoolQueryBuilder filterSkillBoolQueryBuilder = QueryBuilders.boolQuery();
				for (String keywd : filterSkillsList) {
					filterSkillBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("skillsName", keywd));
				}
				boolQueryBuilder.must(filterSkillBoolQueryBuilder);
			}
		}

		if (StringUtils.isNotBlank(search.getAvailability())) {
			String[] avaliability = search.getAvailability().split(",");
			List<String> avaliabilityList = new ArrayList<>();
			for (int i = 0; i < avaliability.length; i++) {
				avaliabilityList.add(avaliability[i]);
			}

			BoolQueryBuilder noticePeriodBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : avaliabilityList) {
				noticePeriodBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("noticePriod", keywd));
			}
			boolQueryBuilder.must(noticePeriodBoolQueryBuilder);
		}

		
		if(search.getCandidateType() != null && search.getCandidateTypeFilter() != null){
			if(search.getCandidateType().equalsIgnoreCase("Favourite") && StringUtils.isBlank(search.getCandidateTypeFilter())) {
				BoolQueryBuilder favBoolQueryBuilder = QueryBuilders.boolQuery();
				boolQueryBuilder.must(QueryBuilders.matchQuery("isFavourite", "1"));
				favBoolQueryBuilder.should(QueryBuilders.matchQuery("isAssociate", "N"));
				favBoolQueryBuilder.should(QueryBuilders.matchQuery("isAssociate", "Y"));
				boolQueryBuilder.must(favBoolQueryBuilder);
			}
		}
		
			if(StringUtils.isNotBlank(search.getCreatedByFilter()) && search.getCreatedByFilter().equalsIgnoreCase("myCandidates")) {
				BoolQueryBuilder createdByBuilder = QueryBuilders.boolQuery();
				if (!Constants.YES.equalsIgnoreCase(search.getIsSharedInMarketplace())) {
					createdByBuilder.must(QueryBuilders.matchQuery("createdByUserCode", user.getUserCode()));
				}else{
					createdByBuilder.mustNot(QueryBuilders.matchQuery("createdByUserCode", user.getUserCode()));
				}
				boolQueryBuilder.must(createdByBuilder);
			}
		
		if(StringUtils.isNotBlank(search.getCandidateTypeFilter())) {
			String[] candidateType = search.getCandidateTypeFilter().split(",");
			BoolQueryBuilder candidateBoolQueryBuilder = QueryBuilders.boolQuery();
			BoolQueryBuilder favouriteBoolQueryBuilder = QueryBuilders.boolQuery();
			for(int i=0; i<candidateType.length; i++) {
				if(candidateType[i].equalsIgnoreCase("Candidates")) {
					candidateBoolQueryBuilder.should(QueryBuilders.matchQuery("isAssociate", "N"));
					if(search.getCandidateType().equalsIgnoreCase("Favourite")) {
						favouriteBoolQueryBuilder.should(QueryBuilders.matchQuery("isFavourite", "1"));
					}
				}
				
				if(candidateType[i].equalsIgnoreCase("Associates")) {
					candidateBoolQueryBuilder.should(QueryBuilders.matchQuery("isAssociate", "Y"));
					if(search.getCandidateType().equalsIgnoreCase("Favourite")) {
						favouriteBoolQueryBuilder.should(QueryBuilders.matchQuery("isFavourite", "1"));
					}
				}
			}
			boolQueryBuilder.must(candidateBoolQueryBuilder);
			boolQueryBuilder.must(favouriteBoolQueryBuilder);
		}
		


		// Created By
		List<String> createdByArray = search.getCreatedBy(); // getSearchKeywordsFromChosenSelectField(jsonObj, "createdByAdvanced");
		if (createdByArray != null && !createdByArray.isEmpty()) {
			BoolQueryBuilder createdByBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : createdByArray) {
				createdByBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("createdBy", keywd));
			}
			boolQueryBuilder.must(createdByBoolQueryBuilder);
		}
		
		
		if (StringUtils.isNotEmpty(search.getCreatedByCandidates())) {
			ArrayList<String> createdByArrayCandidate = getSearchKeywordsFromChosenSelectField(search.getCreatedByCandidates());
			if(search.getCreatedByCandidates().equalsIgnoreCase("myCandidates")) {
				
				if (createdByArrayCandidate != null && !createdByArrayCandidate.isEmpty()) {
					BoolQueryBuilder createdByBoolQueryBuilder = new BoolQueryBuilder();
					for (String keywd : createdByArrayCandidate) {
						createdByBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("updatedBy", user.getUserCode()));
					}
					boolQueryBuilder.must(createdByBoolQueryBuilder);
				}
			} 
			
		}

		// updatedOn
		if (StringUtils.isNotEmpty(search.getUpdatedOn())) {
			try {
				Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				c.setTime(new Date());
				Integer dateRange = Integer.parseInt(search.getUpdatedOn());
				c.add(Calendar.DATE, -dateRange);
				Date utilDate = c.getTime();
				boolQueryBuilder.must(QueryBuilders.rangeQuery("updatedDate").gte(utilDate));
			} catch (NumberFormatException e) {
			}
		}

		// Educations
		List<String> educationArray = search.getEducation();
		if (educationArray != null && !educationArray.isEmpty()) {
			BoolQueryBuilder edunBoolQueryBuilder = new BoolQueryBuilder();
			for (String keywd : educationArray) {
				edunBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("education", keywd));
			}
			boolQueryBuilder.must(edunBoolQueryBuilder);
		}

		// Exclude Educations
		List<String> excludeEducationArray = search.getExcludeEducation();
		if (excludeEducationArray != null && !excludeEducationArray.isEmpty()) {
			BoolQueryBuilder excludeEdunBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : excludeEducationArray) {
				excludeEdunBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("education", keywd));
			}
			boolQueryBuilder.mustNot(excludeEdunBoolQueryBuilder);
		}
		// Certifications
		List<String> certificationArray = search.getCertification();
		if (certificationArray != null && !certificationArray.isEmpty()) {
			BoolQueryBuilder certificationBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : certificationArray) {
				certificationBoolQueryBuilder.should(QueryBuilders.matchQuery("certifications", keywd));
			}
			boolQueryBuilder.must(certificationBoolQueryBuilder);
		}

		// Exclude Employers
		List<String> excludeEmployerArray = search.getExcludeEmployers();
		if (excludeEmployerArray != null && !excludeEmployerArray.isEmpty()) {
			BoolQueryBuilder excldEmployerBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : excludeEmployerArray) {
				excldEmployerBoolQueryBuilder.should(QueryBuilders.matchQuery("employer", keywd));
			}
			boolQueryBuilder.mustNot(excldEmployerBoolQueryBuilder);
		}

		// Employers
		List<String> employerArray = search.getEmployers();
		if (employerArray != null && !employerArray.isEmpty()) {
			BoolQueryBuilder employerBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : employerArray) {
				employerBoolQueryBuilder.should(QueryBuilders.matchQuery("employer", keywd));
			}
			boolQueryBuilder.must(employerBoolQueryBuilder);
		}

		// applicant type
		if (StringUtils.isNotEmpty(search.getApplicantType())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("candidateType", search.getApplicantType()));
		}

		if (search.getCandidateStatus() == (Constants.CANDIDATE_ACTIVE)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("status", 1));
		} else if (search.getCandidateStatus() == (Constants.CANDIDATE_IN_ACTIVE)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("status", 0));
		}

		List<String> candidateSkillsArray = search.getCandidateSkills();
		if (candidateSkillsArray != null && !candidateSkillsArray.isEmpty()) {
			BoolQueryBuilder skillBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : candidateSkillsArray) {
				skillBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("skillsName", keywd));
			}
			boolQueryBuilder.must(skillBoolQueryBuilder);
		}

		List<String> candidateVisaArray = search.getCandidateVisa();
		if (candidateVisaArray != null && !candidateVisaArray.isEmpty()) {
			BoolQueryBuilder visaBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : candidateVisaArray) {
				visaBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("visaType", keywd));
			}
			boolQueryBuilder.must(visaBoolQueryBuilder);
		}
		
		
		List<String> candidateTagsArray = search.getCandidateTags();
		if (candidateTagsArray != null && !candidateTagsArray.isEmpty()) {
			BoolQueryBuilder tagBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : candidateTagsArray) {
				tagBoolQueryBuilder.should(QueryBuilders.matchPhraseQuery("tags", keywd));
			}
			boolQueryBuilder.must(tagBoolQueryBuilder);
		}

		if (StringUtils.isNotEmpty(search.getIsAssociate()) && search.getIsAssociate().equalsIgnoreCase(Constants.YES)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", search.getIsAssociate()));
		}

		List<CandidateIndex> candidatesList = new ArrayList<CandidateIndex>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		ResponseList<CandidateIndex> response = new ResponseList<>();

		try {
			//search.setLength(100);
			SearchResponse searchResponse = null;
			if (Constants.YES.equalsIgnoreCase(search.getIsSharedInMarketplace())) {
				searchResponse = sourceleadELKBaseIndexer.searchDocumentWithGlobalSearchResult(Constants.ELK_CANDIDATE_INDEX,
						Constants.ELK_CANDIDATE_TYPE, boolQueryBuilder);
			}else{
				searchResponse = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX,
						Constants.ELK_CANDIDATE_TYPE, search.getStart(), search.getLength(), tableColumns[sortColumnIndex],
						sortColumnOrder ? SortOrder.ASC : SortOrder.DESC, boolQueryBuilder);
			}
			
			SearchHit searchHits[] = searchResponse.getHits().getHits();
			if (null != searchHits && searchHits.length > 0) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(df);
				for (SearchHit hit : searchHits) {
					StringBuilder json1 = new StringBuilder(hit.getSourceAsString());
					CandidateIndex candidateIndex = mapper.readValue(json1.toString(), CandidateIndex.class);
					if (candidateIndex.getUpdatedDate() != null) {
						Date date1 = commonUtil.dateFromElk(candidateIndex.getUpdatedDate());
						String updatedDate = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), date1);
						candidateIndex.setCandidateUpdateDateToString(updatedDate);

					}
					candidateIndex.setCreatedBy(candidateIndex.getCreatedBy());
					/*Candidate can = candidateService.getCandidateByCandidateId(candidateIndex.getCandidateId());
					if(can.getCandidateProfilePercentage() != null) {
						candidateIndex.setProfilePercentage(can.getCandidateProfilePercentage());
					}*/
					candidatesList.add(candidateIndex);
				}
				response.setRecordsFiltered(Long.valueOf(searchResponse.getHits().getTotalHits()).intValue());

				SearchResponse searchResponseForCount = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX,
						Constants.ELK_CANDIDATE_TYPE, 0, 10, "updatedDate", SortOrder.DESC, boolQueryBuilderForCount);

				response.setRecordsTotal(searchResponseForCount == null ? response.getRecordsFiltered()
						: Long.valueOf(searchResponseForCount.getHits().getTotalHits()).intValue());
			}
			response.setData(candidatesList);
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e.getMessage());
		}
		return response;
	}

	private ArrayList<String> getSearchKeywordsFromChosenSelectField(String paramValue) throws Exception {
		ArrayList<String> keyWordList = null;
		if (StringUtils.isNotEmpty(paramValue)) {
			keyWordList = new ArrayList<String>(Arrays.asList(paramValue.split(",")));
		} else {
			keyWordList = new ArrayList<String>();
		}

		return keyWordList;
	}

	private List<QueryBuilder> getQueryBuilderList(String keywd) {
		List<QueryBuilder> QueryBuilderList = new ArrayList<>();
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("firstName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("lastName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("fullName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("email", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("alternateEmail", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("country", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("city", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("state", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("address", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("designation", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("designationType", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRateType", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRateCurrency", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("rateType", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("expectedRateCurrencey", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("summary", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("education", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("skillsName", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("primarySkill", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("visaType", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("employer", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("certifications", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("candidateType", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("noticePriod", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("createdBy", keywd));
		// QueryBuilderList.add(QueryBuilders.matchQuery("currentResumeName",
		// keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("resumeContent", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("mobilePhone", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("alternateMobilePhone", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("generatedId", keywd));
		QueryBuilderList.add(QueryBuilders.matchPhraseQuery("tags", keywd));

		return QueryBuilderList;
	}
	
	
	
	@RequestMapping(value = "/getCandidateSubmittedRequirementCount" , method = RequestMethod.GET)
	 public @ResponseBody CandidateDTO getCandidateSubmittedRequirementCount(@RequestParam(value = "candidateId", required = true) Long candidateId, HttpServletRequest request) throws Exception {
			
		     OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     CandidateDTO candidateDTO = new CandidateDTO();
		     Integer submittedJobCount = requirementSubmissionService.getRequirementSubmissionCountByCandiateIdAndOrgLocationId(candidateId, orgLocation.getId());
		     Integer profilePercentage = candidateService.getCandidateProfilePercentageByCandidateId(candidateId);
		     candidateDTO.setSubmittedJobsCount(submittedJobCount);
		     candidateDTO.setProfilePercentage(profilePercentage);
			 return candidateDTO;
	}

	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/getHotListCandidates")
	public ResponseList<Candidate> getHotListCandidates(@RequestParam(value = "candidateType", required = false) String candidateType,
			HttpServletRequest request) throws Exception {
		ResponseList<Candidate> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				/* List<Candidate> candList = new ArrayList<Candidate>(); */
				List<Candidate> candidatesList = candidateService
						.getAllActiveCandidatesCountForCampaignByLocationAndStatus(loginOrgLocation.getId());
				response.setData(candidatesList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * This Method is for getting candidate by candidate ID
	 *
	 * @author David Raju
	 *
	 * @param candidateId
	 *            This is the first parameter to getCandidateById method
	 * @param request
	 *            This is the second parameter to getCandidateById method
	 *
	 * @return Candidate This is the third parameter to getCandidateById method
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getCandidateById", "/rest/getCandidateById" }, method = RequestMethod.POST)
	public @ResponseBody Response<CandidateDTO> getCandidateById(@RequestParam(value = "candidateId", required = false) String candidateId,
			HttpServletRequest request) throws Exception {
		Response<CandidateDTO> response = new Response<>();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if (user != null) {
			try {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Countries countries = countriesService.getCountriesByCountryName(loginOrgLocation.getCountry());

				CandidateDTO candidateDTO = new CandidateDTO();

				Candidate can = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId));
				
				/*
				 * CandidateSourceDTO
				 * candidateSource=candidateService.getCandidateSourceById(candidateSource.getId
				 * ().longValue(),user.getId(), loginOrgLocation);
				 */
				int percentage = candidateService.getCandidatePercentage(can, loginOrgLocation);

				LinkedHashSet<String> lhs = new LinkedHashSet<String>();
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				for (LookUpData lookUpData : skillsList) {
					lhs.add(lookUpData.getName());
				}

				if (null != can.getSkillsName() && StringUtils.isNotEmpty(can.getSkillsName())) {
					String[] csl = can.getSkillsName().split(",");
					for (String sl : csl) {
						lhs.add(sl);
					}
				}
				candidateDTO.setAllAndCandidateSkills(lhs);

				// visa type
				LinkedHashSet<String> visa = new LinkedHashSet<String>();
				List<LookUpData> visaList = lookUpDataService.getLookUpDataByCategory(Constants.VISA);
				for (LookUpData lookUpData : visaList) {
					visa.add(lookUpData.getName());
				}
				if (null != can.getVisaType() && StringUtils.isNotEmpty(can.getVisaType())) {
					String[] csl = can.getVisaType().split(",");
					for (String sl : csl) {
						visa.add(sl);
					}
				}
				candidateDTO.setAllAndCandidateVisa(visa);
				candidateDTO.setVisaType(can.getVisaType());
				
				Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(loginOrgLocation.getId());
				candidateDTO.setTagsList(tagsList);
				Set<String> candidateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(loginOrgLocation.getId(), TagEntites.CANDIDATES.getValue(), can.getId());
				if(candidateTagsList != null && !candidateTagsList.isEmpty()) {
					StringBuilder candidateTagsStr = new StringBuilder();
					for(String tags : candidateTagsList) {
						candidateTagsStr = candidateTagsStr.append(tags).append(",");
					}
					candidateTagsStr.toString();
					candidateDTO.setCandidateTags(candidateTagsStr.substring(0,candidateTagsStr.length()-1));
				}
				
				if (null != can.getExpectedRateCurrencey()) {
					candidateDTO.setExpectedRateCurrencey(can.getExpectedRateCurrencey());
				} else {
					if (null != countries && !countries.getCurrency().equalsIgnoreCase("")) {
						candidateDTO.setExpectedRateCurrencey(countries.getCurrency());
					}
				}

				if (null != can.getCurrentRateCurrency()) {
					candidateDTO.setCurrentRateCurrency(can.getCurrentRateCurrency());
				} else {
					if (null != countries && !countries.getCurrency().equalsIgnoreCase("")) {
						candidateDTO.setCurrentRateCurrency(countries.getCurrency());

					}
				}

				if (null != can.getCurrentRateType()) {
					candidateDTO.setCurrentRateType(can.getCurrentRateType());
				} else {
					if (null != countries && !countries.getCurrency().equalsIgnoreCase("")) {
						String countryCheck = countries.getName();
						switch (countryCheck) {
						case "India":
							candidateDTO.setCurrentRateType("Monthly");
							break;
						default:
							candidateDTO.setCurrentRateType("Hourly");
						}
					}
				}

				if (null != can.getOrgRelationId()) {
					candidateDTO.setVendorOrgRelationId(String.valueOf(can.getOrgRelationId().getId()));
					if (can.getOrgRelationId().getResponderLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
						candidateDTO.setVendorName(can.getOrgRelationId().getLocationId().getOrgLocationName());
					}
					if (can.getOrgRelationId().getLocationId().getId().longValue() == loginOrgLocation.getId().longValue()) {
						candidateDTO.setVendorName(can.getOrgRelationId().getResponderLocationId().getOrgLocationName());
					}

				}
				if (null != can.getSpocId()) {
					StringBuilder contactName = new StringBuilder();
					candidateDTO.setVendorSpocId(can.getSpocId().getId().toString());
					if (null != can.getSpocId().getClientUser().getLastName()) {
						candidateDTO.setVendorContactName(contactName.append(can.getSpocId().getVendorUser().getFirstName()).append(" ")
								.append(can.getSpocId().getClientUser().getLastName()).toString());
					} else {
						candidateDTO.setVendorContactName(contactName.append(can.getSpocId().getVendorUser().getFirstName()).toString());
					}

				}
				candidateDTO.setProfilePercentage(percentage);
				candidateDTO.setCountry(can.getCountry());
				candidateDTO.setCandidateType(can.getCandidateType());
				candidateDTO.setCity(can.getCity());
				candidateDTO.setState(can.getState());
				candidateDTO.setFirstName(can.getFirstName());
				candidateDTO.setLastName(can.getLastName());
				candidateDTO.setMiddleName(can.getMiddelName());
				candidateDTO.setGender(can.getGender());
				candidateDTO.setMaritalStatus(can.getMaritalStatus());
				candidateDTO.setDateofBirth(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocation.getDateFormat(), can.getDateOfBirth()));
				candidateDTO.setLocation(can.getLocation());
				candidateDTO.setSummary(can.getSummary());
				candidateDTO.setFileUrl(can.getFileUrl());
				candidateDTO.setMobilePhone(can.getMobilePhone());
				candidateDTO.setAlternateMobilePhone(can.getAlternateMobilePhone());
				candidateDTO.setSkillsName(can.getSkillsName());
				candidateDTO.setRateType(can.getRateType());
				candidateDTO.setDesignationType(can.getDesignationType());
				candidateDTO.setExpectedRate(can.getExpectedRate());
				candidateDTO.setCurrentRate(can.getCurrentRate());
				candidateDTO.setEmail(can.getEmail());
				candidateDTO.setNoticePeriod(can.getNoticePeriod());
				candidateDTO.setDesignation(can.getDesignation());
				candidateDTO.setPrimarySkill(can.getPrimarySkill());
				candidateDTO.setAddress(can.getAddress());
				candidateDTO.setExperienceMonths(can.getExperienceMonths());
				candidateDTO.setExperienceYears(can.getExperienceYears());
				candidateDTO.setZipcode(can.getZipCode());
				candidateDTO.setCandidateType(can.getCandidateType());
				candidateDTO.setStatus(can.getStatus().toString());
				candidateDTO.setTerminateReason(can.getTerminateReason());
				candidateDTO.setIsAssociate(can.getIsAssociate());
				candidateDTO.setIsProfilePublic(can.getIsProfilePublic());
				candidateDTO.setId(can.getId());
				
				if(null != can.getCandidateSource()) {
					candidateDTO.setCandidateSource(can.getCandidateSource());
					List<CandidateSource> candidateSourceList = candidateService.getCandidateSourceListByOrgLocationIdAndStatus(loginOrgLocation.getId(),1);
					candidateDTO.setCandidateSourceList(candidateSourceList);
				}
				
				if(can.getPanNumber() != null) {
	 				candidateDTO.setPanNumber(can.getPanNumber());
					}
					
				if(can.getAadharNumber() != null) {
					candidateDTO.setAadharNumber(can.getAadharNumber());
					}
					
				if(can.getSsnNumber() != null) {
						candidateDTO.setSsnNumber(can.getSsnNumber());
						}
					
				if(can.getConsultantUser() != null) {
					candidateDTO.setUserId(can.getConsultantUser().getId());
				}
				candidateDTO.setCandidateId(String.valueOf(can.getId()));
				SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_CANDIDATE);
				if (action != null && action.getId() != null) {
					List<CustomFormFieldDTO> formFieldId = new ArrayList<>();
					List<SourceleadSectionDTO> sections = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),
							loginOrgLocation.getId());
					if (sections != null && !sections.isEmpty()) {
						for (SourceleadSectionDTO sec : sections) {
							for (OrglocationFormFieldsDTO customFields : sec.getCustomFieldsList()) {
								CustomFormFieldDTO custom = new CustomFormFieldDTO();
								custom.setCustomFormFieldId(customFields.getId());
								formFieldId.add(custom);
							}
						}
						if (formFieldId != null && !formFieldId.isEmpty() && loginOrgLocation != null) {
							OrgLocations orgLoc = orgLocationService.getOrgLocations(loginOrgLocation.getId());
							Map<Long, String[]> candidateData = candidateCustomFieldDataService
									.getCandidateCustomFieldDataMapByLocationId(orgLoc.getOrg().getId(), formFieldId, can.getId());
							Map<String, String> dataMap = new HashMap<>();
							if (candidateData != null && !candidateData.isEmpty()) {
								for (Entry<Long, String[]> candidateCustomData : candidateData.entrySet()) {
									String labelName = candidateCustomData.getValue()[0];
									String labelData = candidateCustomData.getValue()[1];
									String labelId = candidateCustomData.getValue()[2];
									Long id = candidateCustomData.getKey();
									StringBuilder keyValue = new StringBuilder();
									keyValue.append(labelName).append("$").append(labelData);
									dataMap.put(id.toString(), keyValue.toString());
									candidateDTO.setCandidateCustomFieldData(dataMap);
								}
							}
						}
					}
				}

				response.setObject(candidateDTO);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		} else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	}

	/**
	 * This Method is for deleting CandidateImage
	 *
	 * @author sarath kumar
	 *
	 * @param candidateId
	 *            This is the first parameter to deleteCandidateImage method
	 * @param request
	 *            This is the second parameter to deleteCandidateImage method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/deleteCandidateImage", method = RequestMethod.GET)
	public Response<String> deleteCandidateImage(@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				// OrgLocations loginOrgLocation = (OrgLocations)
				// SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);

				StringBuffer activitySummary = new StringBuffer();
				Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId),
						loginOrgLocation.getId());
				candidate.setFileUrl(null);
				candidate.setFileContentType(null);
				activitySummary.append("File").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getFileName())
						.append(Constants.OLD_VALUE_TAG_END);
				candidate.setFileName(null);
				candidateService.addCandidate(candidate);

				candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
				candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "",
						Constants.CANDIDATE_DELETED_PROFILE_IMAGE, activitySummary.toString(), "CANDIDATE");
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setObject("success");
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * This Method is for getting CandidatesSkills By candidateID
	 *
	 * @author sarath kumar
	 *
	 * @param candidateId
	 *            This is the first parameter to getCandidatesSkillsBycandidateID
	 *            method
	 * @param request
	 *            This is the second parameter to getCandidatesSkillsBycandidateID
	 *            method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */
	// Skills Save In Candidates table Not In CandidateSkills Table

	/*
	 * @RequestMapping(value = "/getCandidatesSkillsBycandidateID") public String
	 * getCandidatesSkillsBycandidateID(@RequestParam(value="candidateId",
	 * required=false) String candidateId,HttpServletRequest request) throws
	 * Exception {
	 * 
	 * String resultJson;
	 * 
	 * OrgLocations loginOrgLocation = (OrgLocations)
	 * SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	 * List<CandidateSkills> candidateSkillsList =
	 * candidateSkillsService.getCandidateSkillsByCandidateId(candidateId);
	 * Set<CandidateSkills> candidateSkillsSet = new HashSet<>(candidateSkillsList);
	 * resultJson = commonUtil.toJSON(candidateSkillsSet); return resultJson;
	 * 
	 * }
	 * 
	 * /** This Method is for saving Skils By candidateID
	 * 
	 * @author sarath kumar
	 * 
	 * @param candidateId This is the first parameter to saveSkilsBycandidateID
	 * method
	 * 
	 * @param skillName This is the second parameter to saveSkilsBycandidateID
	 * method
	 * 
	 * @param request This is the third parameter to saveSkilsBycandidateID method
	 * 
	 * @return String
	 * 
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/saveSkilsBycandidateID")
	public Response<String> saveSkilsBycandidateID(@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "skillName", required = false) String skillName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				Candidate can = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId));

				if (null != can && null != can.getSkillsName()) {
					StringBuilder candidateSkill = new StringBuilder();
					can.setSkillsName(candidateSkill.append(can.getSkillsName()).append(",").append(skillName).toString());
					candidateService.saveOrUpdateCandidate(can);
					candidateService.saveOrUpdateCandidateElkDocument(can);
				} else if (null != can) {
					can.setSkillsName(skillName);
					candidateService.saveOrUpdateCandidate(can);
					candidateService.saveOrUpdateCandidateElkDocument(can);
				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				response.setObject("success");
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	// Skills Save In Candidates table Not In CandidateSkills Table

	/*
	 * @RequestMapping(value = "/getSkillsExcludeExistingSkills") public String
	 * getSkillsExcludeExistingSkills(@RequestParam(value="candidateId",
	 * required=false) String candidateId,HttpServletRequest request) throws
	 * Exception {
	 * 
	 * OrgLocations loginOrgLocation = (OrgLocations)
	 * SourceLeadSessionManager.getSessionAttribute(request,"orgLocations"); String
	 * resultJson;
	 * 
	 * List<String> candidateSkillNames =
	 * candidateSkillsService.getCandidateSkillNamesByCandidateId(candidateId);
	 * List<LookUpData> skills =
	 * lookUpDataService.getSkillsBySkillsName(candidateSkillNames); resultJson =
	 * commonUtil.toJSON(skills); return resultJson; }
	 */
	/**
	 * This Method is for remove Skill By Skill Id
	 *
	 * @author venkat Kesav
	 * @param skillId
	 *            This is the second parameter to removeSkillBySkillId method
	 * @param request
	 *            This is the third parameter to removeSkillBySkillId method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */
	// Skills Save In Candidates table Not In CandidateSkills Table

	/*
	 * @RequestMapping(value = "/removeSkillBySkillId") public String
	 * removeSkillBySkillId(@RequestParam(value="skillId", required=false) String
	 * skillId,HttpServletRequest request) throws Exception {
	 * 
	 * User user = (User) SourceLeadSessionManager.getSessionAttribute(request,
	 * "user"); if(StringUtils.isNotEmpty(skillId)){ CandidateSkills candidateSkills
	 * = candidateSkillsService.getCandidateSkillBySkillId(Long.parseLong(skillId));
	 * candidateSkills.setStatus(Constants.CANDIDATE_SKILL_IN_ACTIVE);
	 * candidateSkills.setUpdatedBy(user.getUserCode());
	 * candidateSkills.setUpdatedDate(commonUtil.currentDate());
	 * candidateSkillsService.storeCandidateSkills(candidateSkills); return
	 * "success"; }else{ return "exception"; } }
	 */
	/**
	 * This Method is for saving Candidate Profile
	 *
	 * @author sarath kumar
	 *
	 * @param candidateId
	 *            This is the first parameter to saveCandidateProfile method
	 * @param request
	 *            This is the second parameter to saveCandidateProfile method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/saveCandidateProfile", method = RequestMethod.POST)
	public Response<String> saveCandidateProfile(CandidateDTO candidateDTO,
			@RequestParam(value = "candidateId", required = false) String candidateId, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				StringBuffer activitySummary = new StringBuffer();
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

				candidateDTO.setStaticPath(staticPath);
				Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId),
						loginOrgLocation.getId());

				if (candidateDTO.getFileInfo() != null) {
					FileInfo info = candidateDTO.getFileInfo().get(0);
					try {
						if (info.getFileName() != null && info.getFileName() != "") {
							activitySummary.append("File").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
									.append(candidate.getFileName()).append(Constants.OLD_VALUE_TAG_END).append(" ")
									.append(Constants.NEW_VALUE_TAG_INITIATION).append(info.getFileName())
									.append(Constants.NEW_VALUE_TAG_END).append(" ");
						} else {
							activitySummary.append("File").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION)
									.append(info.getFileName()).append(Constants.NEW_VALUE_TAG_END).append(" ");
						}
						candidate.setFileContentType(info.getContentType());
						candidate.setFileUrl(info.getFileURL());
						candidate.setFileName(info.getFileName());
						FileUploadUtil.moveFile(candidateDTO.getStaticPath(), info, "profile");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				candidateService.addCandidate(candidate);

				candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
				candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "",
						Constants.CANDIDATE_UPLOAD_NEW_PROFILE_IMAGE, activitySummary.toString(), "CANDIDATE");
				Integer percentage = candidateService.getCandidatePercentage(candidate, loginOrgLocation);
				response.setObject(percentage.toString());
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * This Method is for displaying Candidate Image
	 *
	 * @author sarath kumar
	 *
	 * @param candidateId
	 *            This is the first parameter to displayCandidateImage method
	 * @param response
	 *            This is the second parameter to displayCandidateImage method
	 * @param session
	 *            This is the third parameter to displayCandidateImage method
	 * @param request
	 *            This is the fourth parameter to displayCandidateImage method
	 *
	 * @return void
	 *
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/displayCandidateImage/{candidateId}", method = RequestMethod.GET)
	public void displayUserImage(@PathVariable Long candidateId, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {

		Candidate candidate = null;
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			candidate = candidateService.getCandidateByCandidateId(candidateId);
			/*
			 * if (candidate != null && candidate.getDocFile() != null) {
			 * candidate.setFileContentType("image/jpeg"); InputStream in = new
			 * ByteArrayInputStream(candidate.getDocFile()); BufferedImage bi =
			 * ImageIO.read(in); OutputStream out = response.getOutputStream();
			 * ImageIO.write(bi, "jpg", out); out.close(); }
			 */
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * This Method is for getting CandidatesSkills By candidateID
	 *
	 * @author sarath kumar
	 *
	 * @param candidateId
	 *            This is the first parameter to getCandidatesSkillsBycandidateID
	 *            method
	 * @param request
	 *            This is the second parameter to getCandidatesSkillsBycandidateID
	 *            method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */
	// Skills Save In Candidates table Not In CandidateSkills Table
	/*
	 * @RequestMapping(value = "/getCandidatesSkillsBycandidateID") public String
	 * getCandidatesSkillsBycandidateID(@RequestParam(value="candidateId",
	 * required=false) String candidateId,HttpServletRequest request) throws
	 * Exception {
	 * 
	 * String resultJson;
	 * 
	 * OrgLocations loginOrgLocation = (OrgLocations)
	 * SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
	 * List<CandidateSkills> candidateSkillsList =
	 * candidateSkillsService.getCandidateSkillsByCandidateId(candidateId);
	 * Set<CandidateSkills> candidateSkillsSet = new HashSet<>(candidateSkillsList);
	 * resultJson = commonUtil.toJSON(candidateSkillsSet); return resultJson;
	 * 
	 * }
	 */
	/**
	 * This Method is for update Candidate details
	 *
	 * @author David Raju
	 *
	 * @param json
	 *            This is the first parameter to updateCandidate method
	 * @param request
	 *            This is the second parameter to updateCandidate method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */

	@RequestMapping(value = { "/updateCandidate", "/rest/updateCandidate" }, method = RequestMethod.POST)
	public Response<String> updateCandidate(CandidateDTO candidateDto, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(Long.valueOf(candidateDto.getLoginUserId()));
			if (user != null && Validator.validate(candidateDto)) {
				OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(Long.valueOf(candidateDto.getOrgloationId()));
				Candidate candidate = candidateService.storeCandidate(candidateDto, loginOrgLocation, user);
				if (candidate != null) {
					response.setObject("success");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));

		}
		return response;
	}

	/**
	 * This Method is for update Candidate details
	 *
	 * @author David Raju
	 * @param json
	 *            This is the first parameter to updateCandidate method
	 * @param request
	 *            This is the second parameter to updateCandidate method
	 * @return String
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/deleteCandidateByCandidateId")
	@ResponseBody
	public Response<Candidate> deleteCandidateByCandidateId(@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "candidateType", required = false) String candidateType,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception {
		Response<Candidate> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),
						loginOrgLocation.getId());
				StringBuffer activitySummary = new StringBuffer();
				Boolean flag;
				if (candidate != null) {
					if (Constants.Associates.equalsIgnoreCase(candidateType)) {
						candidate.setIsAssociate(Constants.NO);
						candidate.setIsFavouriteAssociate(Constants.RECORD_INACTIVE);
						candidate.setStatus(Constants.CANDIDATE_ACTIVE);
					}

					if (Constants.Candidates.equalsIgnoreCase(candidateType)) {
						candidate.setIsAssociate(Constants.NO);
						candidate.setStatus(Constants.CANDIDATE_IN_ACTIVE);
					}

					// candidate.setStatus(Constants.CANDIDATE_IN_ACTIVE);
					if (StringUtils.isNotEmpty(reason)) {
						candidate.setTerminateReason(reason);
						activitySummary.append("Reason").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(reason)
								.append(Constants.NEW_VALUE_TAG_END).append(" ");
					} else {
						activitySummary.append(" ");
					}
					candidate.setUpdatedBy(user.getUserCode());
					candidate.setUpdatedDate(commonUtil.currentDate());
					candidate.setIsFavourite(0);
					candidate.setTeamId(null);
					candidateService.saveOrUpdateCandidate(candidate);
					candidateService.saveOrUpdateCandidateElkDocument(candidate);

					//deleting candidate from marketplace and cart.
					flag = candidateService.deleteCandidateFromMarketcartByCandidateUuid(candidate.getCandidateUUID());
					if(flag == true){
						candidateService.deleteCandidateFromMarketplaceByCandidateUuid(candidate.getCandidateUUID());
						}
					
					candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "", Constants.CANDIDATE_DELETED,
							activitySummary.toString(), "CANDIDATE");

					StringBuilder candidateRemoveMsg = new StringBuilder();
					SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg",
							candidateRemoveMsg.append("").append(candidate.getFirstName()).append(" Removed").toString());
					response.setObject(candidate);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	@RequestMapping(value = "/deleteCandidatePermanentlyByCandidateId")
	@ResponseBody
	public Response<Candidate> deleteCandidatePermanentlyByCandidateId(@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception {
		Response<Candidate> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),
						loginOrgLocation.getId());
				StringBuffer activitySummary = new StringBuffer();
				Boolean flag;
				if (candidate != null) {
					
					SourceleadDeletedCandidates deletedCandiate = new SourceleadDeletedCandidates();
					deletedCandiate.setCandidateId(candidate);
					deletedCandiate.setOrgLocationId(loginOrgLocation);
					deletedCandiate.setEmail(candidate.getEmail());
					deletedCandiate.setFirstName(candidate.getFirstName());
					deletedCandiate.setLastName(candidate.getLastName());
					deletedCandiate.setCreatedBy(user);
					deletedCandiate.setCreatedDate(commonUtil.currentDate());
					candidateService.saveSourceleadDeletedCandidate(deletedCandiate);
					
					candidate.setIsAssociate(Constants.NO);
					candidate.setStatus(Constants.CANDIDATE_IN_ACTIVE);

					if (StringUtils.isNotEmpty(reason)) {
						candidate.setTerminateReason(reason);
						activitySummary.append("Reason").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(reason)
								.append(Constants.NEW_VALUE_TAG_END).append(" ");
					} else {
						activitySummary.append(" ");
					}
					candidate.setUpdatedBy(user.getUserCode());
					candidate.setUpdatedDate(commonUtil.currentDate());
					candidate.setIsFavourite(0);
					candidate.setTeamId(null);

					//deleting candidate from marketplace and cart.
					flag = candidateService.deleteCandidateFromMarketcartByCandidateUuid(candidate.getCandidateUUID());
					if(flag == true){
						candidateService.deleteCandidateFromMarketplaceByCandidateUuid(candidate.getCandidateUUID());
						}
					
					candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "", Constants.CANDIDATE_DELETED,
							activitySummary.toString(), "CANDIDATE");
					candidate.setOrgLocationId(null);
					candidateService.saveOrUpdateCandidate(candidate);
					OrgLocations orgLocations = new OrgLocations();
					orgLocations.setId(1L);
					candidate.setOrgLocationId(orgLocations);
					candidateService.saveOrUpdateCandidate(candidate);
					candidateService.deleteCandidateElkDocument(candidate);

					StringBuilder candidateRemoveMsg = new StringBuilder();
					SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg",
							candidateRemoveMsg.append("").append(candidate.getFirstName()).append(" Removed Successfully").toString());
					response.setObject(candidate);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	

	@PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_HOTLIST','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/saveCandidateAsFavourite", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveCandidateAsFavourite(@RequestParam(value = "candidateId", required = false) Long candidateId,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				if (null != candidate) {
					if (null == candidate.getIsFavourite() || candidate.getIsFavourite().equals(Constants.RECORD_INACTIVE)) {
						candidate.setIsFavourite(Constants.RECORD_ACTIVE);
					} else {
						candidate.setIsFavourite(Constants.RECORD_INACTIVE);
					}
					candidate.setTeamId(null);
					candidateService.saveOrUpdateCandidate(candidate);
					candidateService.saveOrUpdateCandidateElkDocument(candidate);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
					response.setObject("success");
				} else {
					response.setResponse(Result.INVALID_USER.name());
					response.setResponseMessage(Result.INVALID_USER.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/assignHotListCandiddatesToTeam", method = RequestMethod.POST)
	public @ResponseBody Response<String> assignHotListCandiddatesToTeam(@RequestParam(value = "teamId", required = false) Long teamId,
			@RequestParam(value = "hotlist", required = false) String hotlist, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				if (hotlist != null && !hotlist.isEmpty()) {
					String[] candidateIds = hotlist.split(",");
					Team team = new Team();
					team.setId(teamId);

					for (int i = 0; i < candidateIds.length; i++) {
						Candidate candidate = candidateService.getCandidateByCandidateId(Long.valueOf(candidateIds[i]));
						candidate.setTeamId(team);
						candidateService.saveOrUpdateCandidate(candidate);
						candidateService.saveOrUpdateCandidateElkDocument(candidate);
					}
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * This Method is for reActivate Candidate By CandidateId
	 *
	 * @author David Raju
	 * @param candidateId
	 *            This is the first parameter to reActivateCandidateByCandidateId
	 *            method
	 * @param reActivate
	 *            This is the second parameter to reActivateCandidateByCandidateId
	 *            method
	 * @return String
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/reActivateCandidateByCandidateId")
	@ResponseBody
	public Response<String> reActivateCandidateByCandidateId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "reActivate", required = false) String reActivate, HttpServletRequest request) throws Exception {
		// OrgLocations loginOrgLocation = (OrgLocations)
		// SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		// User user = (User) SourceLeadSessionManager.getSessionAttribute(request,
		// "user");
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
				Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),
						loginOrgLocation.getId());
				if (candidate != null) {
					if (reActivate.equalsIgnoreCase("1")) {
						candidate.setStatus(Constants.CANDIDATE_ACTIVE);
					}
					candidate.setUpdatedBy(user.getUserCode());
					candidate.setUpdatedDate(commonUtil.currentDate());
					candidateService.saveOrUpdateCandidate(candidate);

					candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "",
							Constants.CANDIDATE_REACTIVATED, "", "CANDIDATE");

					candidateService.saveOrUpdateCandidateElkDocument(candidate);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			} else if (user == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	/**
	 * This Method is for save field Candidate details
	 *
	 * @author sarath kumar
	 *
	 * @param json
	 *            This is the first parameter to updateCandidate method
	 * @param name
	 *            This is the first parameter to updateCandidate method
	 * @param value
	 *            This is the first parameter to updateCandidate method
	 * @param request
	 *            This is the second parameter to updateCandidate method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */
	// Skills Save In Candidates table Not In CandidateSkills Table

	/*
	 * @RequestMapping(value = "/fieldUpdateCandidate") public String
	 * fieldUpdateCandidate(@RequestParam(value="candidateSkillId", required=false)
	 * String candidateSkillId,@RequestParam(value="name", required=false) String
	 * name,@RequestParam(value="value", required=false) String
	 * value,HttpServletRequest request) throws Exception {
	 * 
	 * CandidateSkills candidateSkills =
	 * candidateSkillsService.getCandidateSkillBySkillId(Long.parseLong(
	 * candidateSkillId)); if("scale".equalsIgnoreCase(name)){
	 * candidateSkills.setScale(value); }
	 * if("totalExperience".equalsIgnoreCase(name)){
	 * candidateSkills.setTotalExperience(value); }
	 * candidateSkillsService.storeCandidateSkills(candidateSkills); return
	 * "success";
	 * 
	 * }
	 */
	/**
	 * @author Rajashekar get candidate data to export excel and pdf
	 * @param candidateStatus
	 * @param updatedDateRange
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCandidatesExportList")
	public void getCandidatesExportList(@RequestParam(value = "candidateStatus", required = false) Integer candidateStatus,
			@RequestParam(value = "updatedDateRange", required = false) String updatedDateRange,
			@RequestParam(value = "exportType", required = false) String exportType,
			@RequestParam(value = "candidateType", required = false) String candidateType,
			@RequestParam(value = "candidateFilterSkills", required = false) String candidateFilterSkills,
			@RequestParam(value = "availability", required = false) String availability,
			@RequestParam(value = "candidateTypeFilter", required = false) String candidateTypeFilter,
			@RequestParam(value = "createdByFilter", required = false) String createdByFilter,
			@RequestParam(value = "isAssociate", required = false) String isAssociate, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		List<Candidate> candidatesList = null;

		if (!isNullOrEmpty(userRole) && userRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")) {
			candidatesList = candidateService.getCandidatesListByOrgLocationIDAndUserId(loginOrgLocation.getId(), user.getId());
		} else {
			candidatesList = candidateService.getCandidatesListByOrgLocationID(loginOrgLocation.getId(), org.getId(), candidateStatus,
					updatedDateRange, candidateType, isAssociate,candidateFilterSkills,availability,candidateTypeFilter,createdByFilter,user);

		}

		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			file = candidateService.getCandidateExcelFile(candidatesList, loginOrgLocation.getOrgLocationName(), isAssociate);
		} else {
			file = candidateService.getCandidatePdf(candidatesList, loginOrgLocation.getOrgLocationName(), isAssociate);
		}
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);

		StringBuilder filename = new StringBuilder();
		// changed string to StringBuilder(Prasad Salina)
		response.setHeader("Content-Disposition",
				filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * This Method is for saving Skils into Candidate table By candidateID
	 *
	 * @author : venkat kesav
	 * @param candidateId
	 *            This is the first parameter to saveSkilsBycandidateID method
	 * @param skillName
	 *            This is the second parameter to saveSkilsBycandidateID method
	 * @param request
	 *            This is the third parameter to saveSkilsBycandidateID method
	 *
	 * @return String
	 *
	 * @throws NullPointerException
	 */
	@RequestMapping(value = "/saveOrUpdateOnlySkillBycandidateId")
	public Response<String> saveOrUpdateOnlySkillBycandidateId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(defaultValue = "0") long candidateId,
			@RequestParam(value = "skillName", required = false) String skillName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Candidate can = candidateService.getCandidateByCandidateId(candidateId);
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				StringBuffer activitySummary = new StringBuffer();
//				if (null != can && StringUtils.isNotEmpty(skillName)) {
					ArrayList<String> oldSkills = new ArrayList<>();
					if (StringUtils.isNotBlank(can.getSkillsName())) {
						oldSkills.addAll(Arrays.asList(can.getSkillsName().split("\\s*,\\s*")));
					}
					ArrayList<String> newSkills = new ArrayList<>(Arrays.asList(skillName.split("\\s*,\\s*")));
					ArrayList<String> actualNewSkills = new ArrayList<>();
					actualNewSkills.addAll(newSkills);
					newSkills.removeAll(oldSkills);
					oldSkills.removeAll(actualNewSkills);

					activitySummary.append("Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
							.append(String.join(",", oldSkills)).append(Constants.OLD_VALUE_TAG_END).append(" ")
							.append(Constants.NEW_VALUE_TAG_INITIATION).append(String.join(",", newSkills))
							.append(Constants.NEW_VALUE_TAG_END);
					can.setSkillsName("");
					can.setSkillsName(skillName);
					candidateService.saveOrUpdateCandidate(can);
					candidateService.saveOrUpdateCandidateElkDocument(can);
					candidateActivityCommonUtil.storeCandidateHistory(can, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_SKILL_UPDATED,
							activitySummary.toString(), "CANDIDATE");
					// return "success";
//				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
//				} else {
//					/*
//					 * ArrayList<String> oldSkills = new ArrayList<>(); if
//					 * (StringUtils.isNotBlank(can.getSkillsName())) {
//					 * oldSkills.addAll(Arrays.asList(can.getSkillsName().split("\\s*,\\s*"))); }
//					 * ArrayList<String> newSkills = new
//					 * ArrayList<>(Arrays.asList(skillName.split("\\s*,\\s*"))); ArrayList<String>
//					 * actualNewSkills = new ArrayList<>(); actualNewSkills.addAll(newSkills);
//					 * newSkills.removeAll(oldSkills); oldSkills.removeAll(actualNewSkills);
//					 * 
//					 * activitySummary.append("Skills").append(" : ").append(Constants.
//					 * OLD_VALUE_TAG_INITIATION) .append(String.join(",",
//					 * oldSkills)).append(Constants.OLD_VALUE_TAG_END).append(" ")
//					 * .append(Constants.NEW_VALUE_TAG_INITIATION).append(String.join(",",
//					 * newSkills)) .append(Constants.NEW_VALUE_TAG_END); can.setSkillsName("");
//					 * candidateService.saveOrUpdateCandidate(can);
//					 * candidateService.saveOrUpdateCandidateElkDocument(can);
//					 * candidateActivityCommonUtil.storeCandidateHistory(can, user);
//					 * candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can,
//					 * "", Constants.CANDIDATE_SKILL_UPDATED, activitySummary.toString(),
//					 * "CANDIDATE"); return "failed";
//					 */
//					response.setResponse(Result.INVALID_DATA.name());
//					response.setResponseMessage(Result.INVALID_DATA.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	
	@RequestMapping(value = "/saveOrUpdateTagsBycandidateId")
	public Response<String> saveOrUpdateTagsBycandidateId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(defaultValue = "0") long candidateId,
			@RequestParam(value = "tagName", required = false) String tagName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Candidate can = candidateService.getCandidateByCandidateId(candidateId);
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
					Set<String> candidateTagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationIdAndEntityName(orgLocations.getId(), TagEntites.CANDIDATES.getValue(), can.getId());
					ArrayList<String> newTags = new ArrayList<>(Arrays.asList(tagName.split("\\s*,\\s*")));
					ArrayList<String> actualNewTags = new ArrayList<>();
					actualNewTags.addAll(newTags);
				    Set<String> newTagsSet = new HashSet<String>(actualNewTags);
				    newTagsSet.removeAll(candidateTagsList);
				    candidateTagsList.removeAll(actualNewTags);
				    can.setCandidateTags(tagName);
				    
				    
				    if(newTagsSet != null && !newTagsSet.isEmpty() && newTagsSet.size() > 0) {
				    	for(String tag : newTagsSet) {
				    		if(StringUtils.isNotBlank(tag)) {
				    			SourceleadTags tags = new SourceleadTags();
					    		tags.setEntityType(TagEntites.CANDIDATES);
					    		tags.setEntityId(can.getId());
					    		tags.setTagName(tag);
					    		tags.setOrgLocationId(orgLocations);
					    		tags.setStatus(Constants.CANDIDATE_ACTIVE);
					    		tags.setCreatedBy(user);
					    		tags.setCreatedDate(new Date());
					    		sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    	
				    	String addedTags =  StringUtils.join(newTagsSet,",");
				    	if(addedTags != null && !addedTags.isEmpty()) {
				    		StringBuffer addedTagSummary = new StringBuffer();
					    	addedTagSummary.append("Tag(s)").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(addedTags)
							.append(Constants.NEW_VALUE_TAG_END);
					    	candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_TAG_ADDED, addedTagSummary.toString(), "CANDIDATE");
				    	}
				    	
				    }
				    
				    if(candidateTagsList != null && !candidateTagsList.isEmpty() && candidateTagsList.size() > 0) {
				    	for(String removedTags : candidateTagsList) {
				    		SourceleadTags tags = sourceleadTagsService.getSourceleadTagsByEntityIdAndTagNameAndOrgLocationId(orgLocations.getId(), TagEntites.CANDIDATES.getValue(), can.getId(),removedTags);
				    		if(tags != null) {
				    			tags.setStatus(Constants.CANDIDATE_IN_ACTIVE);
				    			tags.setUpdatedBy(user);
				    			tags.setUpdatedDate(new Date());
				    			sourceleadTagsService.saveSourceleadTags(tags);
				    		}
				    	}
				    	
				    	String removedTags =  StringUtils.join(candidateTagsList,",");
				    	StringBuffer removedTagSummary = new StringBuffer();
				    	removedTagSummary.append("Tag(s)").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(removedTags)
						.append(Constants.OLD_VALUE_TAG_END);
				    	candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_TAG_REMOVED, removedTagSummary.toString(), "CANDIDATE");
				    }
				    
			    candidateService.saveOrUpdateCandidateElkDocument(can);
			    Set<String> tagsList = sourceleadTagsService.getSourceleadTagsByOrgLocationId(orgLocations.getId());
			    SourceLeadSessionManager.removeSessionAttribute(request, "tagsList");
			    SourceLeadSessionManager.setSessionAttribute(request, "tagsList", tagsList);
				response.setResponse(Result.SUCCESS.name());

			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	@RequestMapping(value = "/getSourceleadTagsList")
	public  List<String>  getSourceleadTagsList(
			 HttpServletRequest request) throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<String> tagsList = sourceleadTagsService.sourceleadTagsListByEntitypeAndOrgloactionId(TagEntites.JOBS.getValue(),loginOrgLocation.getId());
		return tagsList;
	}
	

	@RequestMapping(value = {"/saveOrUpdateOnlyVisaBycandidateId","/rest/saveOrUpdateOnlyVisaBycandidateId"})
	public Response<String> saveOrUpdateOnlyVisaBycandidateId(@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId, @RequestParam(defaultValue = "0") long candidateId,
			@RequestParam(value = "visaName", required = false) String visaName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Candidate can = candidateService.getCandidateByCandidateId(candidateId);
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				StringBuffer activitySummary = new StringBuffer();
//				if (null != can && StringUtils.isNotEmpty(visaName)) {
					activitySummary.append("Immigration Status").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
							.append(can.getVisaType()).append(Constants.OLD_VALUE_TAG_END).append(" ")
							.append(Constants.NEW_VALUE_TAG_INITIATION).append(visaName).append(Constants.NEW_VALUE_TAG_END);
					can.setVisaType(visaName);
					candidateService.saveOrUpdateCandidate(can);
					candidateService.saveOrUpdateCandidateElkDocument(can);
					candidateActivityCommonUtil.storeCandidateHistory(can, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_VISA_UPDATED,
							activitySummary.toString(), "CANDIDATE");
					// return "success";
//				}
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/getCandidateReportsListByOrgLocationID")
	public ResponseList<Candidate> getCandidateReportsListByOrgLocationID(
			@RequestParam(value = "candidateType", required = false) String candidateType, HttpServletRequest request) throws Exception {
		ResponseList<Candidate> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				List<Candidate> candidatesList = candidateService.getCandidateReportsListByOrgLocationID(orgLocations.getId(),
						candidateType);
				/*
				 * List<Candidate> consultantsList =
				 * candidateService.getCandidateReportsListByOrgLocationID(orgLocations.getId(),
				 * "Consultant");
				 */
				response.setData(candidatesList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/getCandidateReports")
	public ResponseList<Candidate> getCandidateReports(@RequestParam(value = "reportType", required = false) String reportType,
			@RequestParam(value = "startDate", required = false) String customStartDate,
			@RequestParam(value = "endDate", required = false) String customEndDate,
			@RequestParam(value = "ownersList", required = false) String ownersList, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseList<Candidate> result = new ResponseList<>();
		try {
			User user1 = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user1 != null) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
				DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59");
				DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date endDateUTC = null;
				String filterDate = null;
				Date startDateUTC = null;
				// SimpleDateFormat simpleDateFormat =
				// orgLocation.getLocFullFormDateFormatter();
				if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)) {
					String endDate = outputFormatter1.print(zoned);
					Date endDateString = TZ_DF_TS.parse(endDate);
					endDateUTC = endDateString;
				}

				if (null != reportType && !"".equals(reportType)) {
					if ("today".equals(reportType)) {
						String startDate = outputFormatter.print(zoned);
						filterDate = startDate;
						startDateUTC = TZ_DF_TS.parse(startDate);
					} else if ("yesterday".equals(reportType)) {
						DateTime yesterDayZoned = zoned.minusDays(1);
						String yesterDayDate = outputFormatter.print(yesterDayZoned);
						// String startDateString =
						// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE,
						// yesterDayDate);
						startDateUTC = TZ_DF_TS.parse(yesterDayDate);

					} else if ("week".equals(reportType)) {
						DateTime yesterDayZoned = zoned.minusDays(7);
						String yesterDayDate = outputFormatter.print(yesterDayZoned);
						startDateUTC = TZ_DF_TS.parse(yesterDayDate);
					} else if ("twoweeks".equals(reportType)) {
						DateTime yesterDayZoned = zoned.minusDays(14);
						String yesterDayDate = outputFormatter.print(yesterDayZoned);
						startDateUTC = TZ_DF_TS.parse(yesterDayDate);
					} else if ("month".equals(reportType)) {
						DateTime yesterDayZoned = zoned.minusDays(30);
						String yesterDayDate = outputFormatter.print(yesterDayZoned);
						startDateUTC = TZ_DF_TS.parse(yesterDayDate);
					} else if ("twomonths".equals(reportType)) {
						DateTime yesterDayZoned = zoned.minusDays(60);
						String yesterDayDate = outputFormatter.print(yesterDayZoned);
						startDateUTC = TZ_DF_TS.parse(yesterDayDate);
					} else {
						if (!"".equalsIgnoreCase(customStartDate) && !"".equalsIgnoreCase(customEndDate)) {
							DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
							DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
							Date startTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), customStartDate);
							Date endTime = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), customEndDate);
							String customStartTimeStr = TZ_DF_CSD.format(startTime);
							String customEndTimeStr = TZ_DF_CED.format(endTime);
							startDateUTC = TZ_DF_TS.parse(customStartTimeStr);
							endDateUTC = TZ_DF_TS.parse(customEndTimeStr);
						}
					}
				}

				List<Candidate> freshList = new ArrayList<Candidate>();
				// List<Candidate> candidatesList =
				// candidateService.getCandidateReportsListByOrgLocationID(orgLocations.getId(),
				// candidateType);

				List<Candidate> canList = candidateService.getCandidateReports(orgLocation.getId(), null, filterDate, startDateUTC,
						endDateUTC, ownersList);

				for (Candidate candidate : canList) {
					DateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
					String currentTimeStr = Constants.TZ_DF.format(candidate.getCreatedDate());
					String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(),
							currentTimeStr);
					Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
					currentDateInTimezone = standardFormat.format(converteDate);
					candidate.setPostedDateStr(currentDateInTimezone);
					User user = userService.getUserByUserCode(candidate.getCreatedBy());
					if (!"".equalsIgnoreCase(user.getLastName()) && user.getLastName() != null && !user.getLastName().isEmpty()) {
						StringBuilder sb = new StringBuilder();
						candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
					} else {
						StringBuilder sb = new StringBuilder();
						candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").toString());
					}

					if (null != candidate.getUpdatedDate()) {
						candidate.setCandidateUpdateDateToString(
								commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), candidate.getUpdatedDate()));
					}
					freshList.add(candidate);
				}
				result.setData(freshList);
				result.setResponse(Result.SUCCESS.name());
				result.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				result.setResponse(Result.SESSION_TIMEDOUT.name());
				result.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			result.setResponse(Result.AWKWARD.name());
			result.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}

	@RequestMapping(value = "/getOwnersByCandidates")
	public ResponseList<Candidate> getOwnersByCandidates(@RequestParam(value = "ownersList", required = false) String ownersList,
			@RequestParam(value = "reportType", required = false) String reportType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseList<Candidate> result = new ResponseList<>();
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59");
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date endDateUTC = null;
			String filterDate = null;
			Date startDateUTC = null;
			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)) {
				String endDate = outputFormatter1.print(zoned);
				// String endDateString =
				// CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE,
				// endDate);
				endDateUTC = TZ_DF_TS.parse(endDate);
			}

			List<Candidate> freshList = new ArrayList<Candidate>();

			List<Candidate> canList = candidateService.getOwnersByCandidates(orgLocation.getId(), null, ownersList);
			for (Candidate candidate : canList) {
				DateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");
				String currentTimeStr = Constants.TZ_DF.format(candidate.getCreatedDate());
				String currentDateInTimezone = CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE, timezone.getTimeZoneId(),
						currentTimeStr);
				Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
				currentDateInTimezone = standardFormat.format(converteDate);
				User user = userService.getUserByUserCode(candidate.getCreatedBy());
				if (!"".equalsIgnoreCase(user.getLastName()) && user.getLastName() != null && !user.getLastName().isEmpty()) {

					StringBuilder sb = new StringBuilder();
					candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				} else {

					StringBuilder sb = new StringBuilder();
					candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").toString());
				}
				candidate.setPostedDateStr(currentDateInTimezone);
				// candidate.setCandidateUpdateDateToString(currentTimeStr);
				candidate.setCandidateUpdateDateToString(
						commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), candidate.getCreatedDate()));
				freshList.add(candidate);
			}
			result.setData(freshList);
			result.setResponse(Result.SUCCESS.name());
			result.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			result.setResponse(Result.AWKWARD.name());
			result.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return result;
	}

	/**
	 * @author Laxman store the Favourite Associate
	 * @param candidateId
	 * @param request
	 * @return success
	 * @throws Exception
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_HOTLIST','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/saveAssociateAsFavourite", method = RequestMethod.POST)
	public @ResponseBody Response<String> saveAssociateAsFavourite(@RequestParam(value = "candidateId", required = false) Long candidateId,
			HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
			if (null != candidate) {
				if (null == candidate.getIsFavouriteAssociate() || candidate.getIsFavouriteAssociate().equals(Constants.RECORD_INACTIVE)) {
					candidate.setIsFavouriteAssociate(Constants.RECORD_ACTIVE);
				} else {
					candidate.setIsFavouriteAssociate(Constants.RECORD_INACTIVE);
				}
				candidate.setTeamId(null);
				candidateService.saveOrUpdateCandidate(candidate);
				candidateService.saveOrUpdateCandidateElkDocument(candidate);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@RequestMapping(value = "/rest/saveOrUpdateOnlySkillBycandidateId")
	public Response<String> saveOrUpdateOnlySkillBycandidateIdForAndroid(
			@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "skillName", required = false) String skillName, HttpServletRequest request) throws Exception {
		Response<String> response = new Response<>();
		try {
			User user = userService.getUserById(loginUserId);
			if (user != null) {
				Candidate can = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId));
				OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				StringBuffer activitySummary = new StringBuffer();
				if (null != can && StringUtils.isNotEmpty(skillName)) {
					ArrayList<String> oldSkills = new ArrayList<>();
					if (StringUtils.isNotBlank(can.getSkillsName())) {
						oldSkills.addAll(Arrays.asList(can.getSkillsName().split("\\s*,\\s*")));
					}
					ArrayList<String> newSkills = new ArrayList<>(Arrays.asList(skillName.split("\\s*,\\s*")));
					ArrayList<String> actualNewSkills = new ArrayList<>();
					actualNewSkills.addAll(newSkills);
					newSkills.removeAll(oldSkills);
					oldSkills.removeAll(actualNewSkills);

					activitySummary.append("Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
							.append(String.join(",", oldSkills)).append(Constants.OLD_VALUE_TAG_END).append(" ")
							.append(Constants.NEW_VALUE_TAG_INITIATION).append(String.join(",", newSkills))
							.append(Constants.NEW_VALUE_TAG_END);
					// can.setSkillsName("");
					can.setSkillsName(skillName.replaceAll("\\s*,\\s*$", ""));
					candidateService.saveOrUpdateCandidate(can);
					candidateService.saveOrUpdateCandidateElkDocument(can);
					candidateActivityCommonUtil.storeCandidateHistory(can, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_SKILL_UPDATED,
							activitySummary.toString(), "CANDIDATE");
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					ArrayList<String> oldSkills = new ArrayList<>();
					if (StringUtils.isNotBlank(can.getSkillsName())) {
						oldSkills.addAll(Arrays.asList(can.getSkillsName().split("\\s*,\\s*")));
					}
					ArrayList<String> newSkills = new ArrayList<>(Arrays.asList(skillName.split("\\s*,\\s*")));
					ArrayList<String> actualNewSkills = new ArrayList<>();
					actualNewSkills.addAll(newSkills);
					newSkills.removeAll(oldSkills);
					oldSkills.removeAll(actualNewSkills);

					activitySummary.append("Skills").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION)
							.append(String.join(",", oldSkills)).append(Constants.OLD_VALUE_TAG_END).append(" ")
							.append(Constants.NEW_VALUE_TAG_INITIATION).append(String.join(",", newSkills))
							.append(Constants.NEW_VALUE_TAG_END);
					can.setSkillsName("");
					candidateService.saveOrUpdateCandidate(can);
					candidateService.saveOrUpdateCandidateElkDocument(can);
					candidateActivityCommonUtil.storeCandidateHistory(can, user);
					candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_SKILL_UPDATED,
							activitySummary.toString(), "CANDIDATE");
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	@RequestMapping(value = "/getCandidatesListForJobBulkSubmission")
	public @ResponseBody ResponseList<SubmissionCandidateListView> getCandidatesListForJobBulkSubmission(CandidateDTO candidateDTO,HttpServletRequest request) throws Exception {

	        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			String[] tableColumns = { "action", "candidateName", "email", "primarySkill", "employementType", "vendorName", "updatedDate", "location"};
			
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (candidateDTO.getOrder() != null && !candidateDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(candidateDTO.getOrder().get(0).get("column"));
				sortColumnOrder = candidateDTO.getOrder().get(0).get("dir");
			}
			candidateDTO.setSortColumn(tableColumns[sortColumnIndex]);
			candidateDTO.setSortOrder(sortColumnOrder);
			ResponseList<SubmissionCandidateListView> candidateDTOList = candidateService.getSubmissionCandidateListView(candidateDTO, orgLocations);
			return candidateDTOList;
			
		}
	@PreAuthorize("hasAnyRole('ROLE_SHARE_PROFILE','ROLE_ADD_PROFILE')")
	@RequestMapping(value = "/getGlobalDataRecommendations", method = RequestMethod.POST)
	public @ResponseBody ResponseList<CandidateMarketPlaceDTO> getGlobalDataRecommendations(CandidateMarketPlaceDTO candidateMarketPlaceDTO, @RequestParam(value = "searchValue", required = false) String searchValue, HttpServletRequest request) throws Exception {

        OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		String[] tableColumns = { "firstName", "designation", "skillsName", "currentRate", "expectedRate", "experienceYears", "location", "candidateProfilePercentage", "marketPrice"};
		
		int sortColumnIndex = 1; // by default sorting using updated date
		String sortColumnOrder = "desc"; // by default descending order
		if (candidateMarketPlaceDTO.getOrder() != null && !candidateMarketPlaceDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(candidateMarketPlaceDTO.getOrder().get(0).get("column"));
			sortColumnOrder = candidateMarketPlaceDTO.getOrder().get(0).get("dir");
		}
		candidateMarketPlaceDTO.setSortColumn(tableColumns[sortColumnIndex]);
		candidateMarketPlaceDTO.setSortOrder(sortColumnOrder);
		ResponseList<CandidateMarketPlaceDTO> candidateDTOList = candidateService.getGlobalDataRecommendations(candidateMarketPlaceDTO, orgLocations, searchValue);
		return candidateDTOList;
		
	}
	
	@RequestMapping(value = "/getPurchaseCandidatesExportList")
	public void getPurchaseCandidatesExportList(@RequestParam(value = "exportType", required = false) String exportType,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations OrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<MarketplacePurchaseOrder> 	purchaseCandidatesList = candidateService.getPurchaseCandidateListByOrglocation(OrgLocation.getId());

		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) 
		{
     		file = candidateService.getPurchaseCandidateExcelFile(purchaseCandidatesList ,OrgLocation.getId());
		} 
		else 
		{
			file = candidateService.getPurchaseCandidatePdf(purchaseCandidatesList,OrgLocation.getId());
		}
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);

		StringBuilder filename = new StringBuilder();
	
		response.setHeader("Content-Disposition",
				filename.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();
		if (file != null) {
			file.delete();
		}
	}


}
