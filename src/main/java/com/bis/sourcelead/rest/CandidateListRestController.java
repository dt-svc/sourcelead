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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import com.bis.sourcelead.dto.CandidateSearchDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
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
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CandidateActivityCommonUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;
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
	 
	/* @Autowired
	 CandidateSkillsService candidateSkillsService;*/
	 
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
	  
	  protected boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	
	private Date getPreviousDate(DateTime zoned, Timezone timezone) throws ParseException {
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateTime yesterDayZoned = zoned;
		String yesterDayDate = outputFormatter.print(yesterDayZoned);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE,
				yesterDayDate);
		return TZ_DF_TS.parse(startDateString);
	}

	@RequestMapping(value = "/getUniversalCandidatesListByOrgLocationID")
	public String getUniversalCandidatesListByOrgLocationID(
			@RequestParam(value = "serachValue", required = false) String serachValue, HttpServletRequest request)
			throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
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
	public ResponseList<CandidateIndex> searchCandidate(CandidateSearchDTO search, HttpServletRequest request)
			throws Exception {
		/*
		 * This array is used to enable the sorting feature in data tables. And, these names should match the ELK Candidate index field names.
		 */
		String[] tableColumns = {"", "firstName", "primarySkill", "designationType", "email", "mobilePhone", "country", "updatedDate"};
		
		int sortColumnIndex = 7; //by default sorting using updated date
		boolean sortColumnOrder = false; //by default descending order
		if(search.getOrder() != null && !search.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(search.getOrder().get(0).get("column"));
			sortColumnOrder = !search.getOrder().get(0).get("dir").equalsIgnoreCase("desc");
		}

		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

		boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));
		
		BoolQueryBuilder boolQueryBuilderForCount = new BoolQueryBuilder();

		boolQueryBuilderForCount.must(QueryBuilders.matchQuery("orgLocationId", loginOrgLocation.getId()));

		if (StringUtils.isNotEmpty(search.getCandidateType()) && !"All".equalsIgnoreCase(search.getCandidateType())) {
			if (Constants.NO.equalsIgnoreCase(search.getIsAssociate())) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("isFavourite", search.getCandidateType()));
			}
		}

		if (Constants.YES.equalsIgnoreCase(search.getIsAssociate())) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", "Y"));
			boolQueryBuilderForCount.must(QueryBuilders.matchQuery("isAssociate", "Y"));
			if (StringUtils.isNotEmpty(search.getCandidateType()) && !"All".equalsIgnoreCase(search.getCandidateType())) {
				boolQueryBuilder.must(QueryBuilders.matchQuery("isFavouriteAssociate", search.getCandidateType()));
			}
		}

		if (!isNullOrEmpty(userRole) && userRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("recruiterUserId", user.getId()));
		}

		// Exclude Keyword
		List<String> excludeKeyWordArray = search.getExcludeKeyWord();
		if (excludeKeyWordArray != null && !excludeKeyWordArray.isEmpty()) {
			BoolQueryBuilder excludeBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : excludeKeyWordArray) {
				BoolQueryBuilder excludeBoolSubQueryBuilder = QueryBuilders.boolQuery();
				List<MatchQueryBuilder> matchQueryBuilderList = getMatchQueryBuilderList(keywd);
				if (matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()) {
					for (MatchQueryBuilder matchQueryBuilder : matchQueryBuilderList) {
						excludeBoolSubQueryBuilder.should(matchQueryBuilder);
					}
					excludeBoolQueryBuilder.should(excludeBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.mustNot(excludeBoolQueryBuilder);
		}

		// Any Keyword
		List<String> anyKeyWordArray = search.getAnyOfKeyword();
		if(anyKeyWordArray == null)
			anyKeyWordArray = new ArrayList<>();
		if(StringUtils.isNotEmpty(search.getSearchValue())) {
			anyKeyWordArray.add(search.getSearchValue());
		}
		if (anyKeyWordArray != null && !anyKeyWordArray.isEmpty()) {
			BoolQueryBuilder anyBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : anyKeyWordArray) {
				BoolQueryBuilder anyBoolSubQueryBuilder = QueryBuilders.boolQuery();
				List<MatchQueryBuilder> matchQueryBuilderList = getMatchQueryBuilderList(keywd);
				if (matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()) {
					for (MatchQueryBuilder matchQueryBuilder : matchQueryBuilderList) {
						anyBoolSubQueryBuilder.should(matchQueryBuilder);
					}
					anyBoolQueryBuilder.should(anyBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.must(anyBoolQueryBuilder);
		}
		// All Keywords
		List<String> allKeyWordArray = search.getAllKeywords();
		if (allKeyWordArray != null && !allKeyWordArray.isEmpty()) {
			BoolQueryBuilder allBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : allKeyWordArray) {
				BoolQueryBuilder allBoolSubQueryBuilder = QueryBuilders.boolQuery();
				List<MatchQueryBuilder> matchQueryBuilderList = getMatchQueryBuilderList(keywd);
				if (matchQueryBuilderList != null && !matchQueryBuilderList.isEmpty()) {
					for (MatchQueryBuilder matchQueryBuilder : matchQueryBuilderList) {
						allBoolSubQueryBuilder.should(matchQueryBuilder);
					}
					allBoolQueryBuilder.must(allBoolSubQueryBuilder);
				}
			}
			boolQueryBuilder.must(allBoolQueryBuilder);
		}
		// Designation
		List<String> designationArray = search.getDesignation();
		if (designationArray != null && !designationArray.isEmpty()) {
			BoolQueryBuilder designationBoolQueryBuilder = QueryBuilders.boolQuery();
			for (String keywd : designationArray) {
				designationBoolQueryBuilder
						.should(QueryBuilders.matchPhrasePrefixQuery("designation", "*" + keywd + "*"));
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
		String minYears = search.getMinRate();
		minYears = StringUtils.isNotEmpty(minYears) ? minYears.replaceAll("[^0-9\\.]+", "") : "";
		String maxYears = search.getMaxRate();
		maxYears = StringUtils.isNotEmpty(maxYears) ? maxYears.replaceAll("[^0-9\\.]+", "") : "";
		if (StringUtils.isNotEmpty(minYears) && StringUtils.isNotEmpty(maxYears)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").gte(new Integer(minYears))
						.lte(new Integer(maxYears)));
			} catch (NumberFormatException e) {
			}
		}
		// minExpYears
		else if (StringUtils.isNotEmpty(minYears)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("experienceYears").gte(new Integer(minYears)));
			} catch (NumberFormatException e) {
			}
		}
		// maxExpYears
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
				boolQueryBuilder
						.must(QueryBuilders.rangeQuery("expectedRate").gte(new Float(minRate)).lte(new Float(maxRate)));
			} catch (NumberFormatException e) {

			}
		}
		// minRate
		else if (StringUtils.isNotEmpty(minRate)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").gte(new Float(minRate)));
			} catch (NumberFormatException e) {
			}
		}
		// maxRate
		else if (StringUtils.isNotEmpty(maxRate)) {
			try {
				boolQueryBuilder.must(QueryBuilders.rangeQuery("expectedRate").lte(new Float(maxRate)));
			} catch (NumberFormatException e) {
			}
		}

		String updatedDateRange = search.getUpdatedDateRange();
		if (StringUtils.isNotEmpty(updatedDateRange) && !"null".equalsIgnoreCase(updatedDateRange)
				&& !updatedDateRange.equalsIgnoreCase("all") && !updatedDateRange.equalsIgnoreCase("undefined")) {
			DateTime zoned = new DateTime(DateTimeZone.forID(loginOrgLocation.getTimezone().getTimeZoneId()));
			Date endDate = getPreviousDate(zoned, loginOrgLocation.getTimezone());
			java.sql.Date endDate1 = new java.sql.Date(endDate.getTime());
			boolQueryBuilder.must(QueryBuilders.rangeQuery("updatedDate").gte(endDate1));
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
		

		if (StringUtils.isNotEmpty(search.getIsAssociate())
				&& search.getIsAssociate().equalsIgnoreCase(Constants.YES)) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("isAssociate", search.getIsAssociate()));
		}

		List<CandidateIndex> candidatesList = new ArrayList<CandidateIndex>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		ResponseList<CandidateIndex> response = new ResponseList<>();

		try {

			SearchResponse searchResponse = sourceleadELKBaseIndexer
					.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,
							search.getStart(), search.getLength(), tableColumns[sortColumnIndex], sortColumnOrder ? SortOrder.ASC : SortOrder.DESC, boolQueryBuilder);
			
			SearchHit searchHits[] = searchResponse.getHits().getHits();
			if (null != searchHits && searchHits.length > 0) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(df);
				for (SearchHit hit : searchHits) {
					StringBuilder json1 = new StringBuilder(hit.getSourceAsString());
					CandidateIndex candidateIndex = mapper.readValue(json1.toString(), CandidateIndex.class);
					if (candidateIndex.getUpdatedDate() != null) {
						Date date1 = Constants.DF_YMD.parse(candidateIndex.getUpdatedDate());
						String updatedDate = LoginSessionController.DATEFORMAT.format(date1);
						candidateIndex.setCandidateUpdateDateToString(updatedDate);
					}
					candidatesList.add(candidateIndex);
				}
				response.setRecordsFiltered(Long.valueOf(searchResponse.getHits().getTotalHits()).intValue());
				
				SearchResponse searchResponseForCount = sourceleadELKBaseIndexer
						.searchDocumentWithMaxResults(Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,
								0, 10, "updatedDate", SortOrder.DESC, boolQueryBuilderForCount);
				
				response.setRecordsTotal(searchResponseForCount == null ? response.getRecordsFiltered() : Long.valueOf(searchResponseForCount.getHits().getTotalHits()).intValue());
			}
			response.setData(candidatesList);
		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e.getMessage());
		}
		return response;
	}

	private List<MatchQueryBuilder> getMatchQueryBuilderList(String keywd) {
		List<MatchQueryBuilder> matchQueryBuilderList = new ArrayList<MatchQueryBuilder>();
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
		matchQueryBuilderList.add(QueryBuilders.matchQuery("skillsName", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("visaType", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("employer", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("certifications", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("candidateType", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("noticePriod", keywd));
		// matchQueryBuilderList.add(QueryBuilders.matchQuery("currentResumeName",
		// keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("resumeContent", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", keywd));
		matchQueryBuilderList.add(QueryBuilders.matchQuery("alternateMobilePhone", keywd));

		Matcher matcher = Pattern.compile("\\d+").matcher(keywd);
		if (matcher.find()) {
			Float expYears = Float.valueOf(matcher.group());
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("experienceYears", expYears));
			// matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("experienceMonths",
			// keywd));
			String mobileKeywd = matcher.group().replaceAll("[+91]", "");
			matchQueryBuilderList.add(QueryBuilders.matchQuery("alternateMobilePhone", mobileKeywd));
			matchQueryBuilderList.add(QueryBuilders.matchQuery("mobilePhone", mobileKeywd));
		}
		if (StringUtils.isNumeric(keywd)) {
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("currentRate", keywd));
			matchQueryBuilderList.add(QueryBuilders.matchPhraseQuery("expectedRate", keywd));
		}

		return matchQueryBuilderList;
	}

	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/getHotListCandidates")
	public String getHotListCandidates(@RequestParam(value = "candidateType", required = false) String candidateType,
			HttpServletRequest request) throws Exception {

		String resultJson;
		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		List<Candidate> candList = new ArrayList<Candidate>();
		List<Candidate> candidatesList = candidateService
				.getAllActiveCandidatesCountForCampaignByLocationAndStatus(loginOrgLocation.getId());

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
			String resultJson ="" ;
			
			 OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			 Countries countries = countriesService.getCountriesByCountryName(loginOrgLocation.getCountry());
			
			Candidate can = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId));
			
			int percentage = candidateService.getCandidatePercentage(can,loginOrgLocation);
			
			LinkedHashSet<String> lhs = new LinkedHashSet<String>();
			List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
			for(LookUpData lookUpData :skillsList){
				lhs.add(lookUpData.getName());
			}

	
		if (null != can.getSkillsName() && StringUtils.isNotEmpty(can.getSkillsName())) {
			String[] csl = can.getSkillsName().split(",");
			for (String sl : csl) {
				lhs.add(sl);
			}
		}
		can.setAllAndCandidateSkills(lhs);

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
		can.setAllAndCandidateVisa(visa);

		if (null != can.getExpectedRateCurrencey()) {
			can.setExpectedRateCurrencey(can.getExpectedRateCurrencey());
		} else {
			if (null != countries && !countries.getCurrency().equalsIgnoreCase("")) {
				can.setExpectedRateCurrencey(countries.getCurrency());
			}
		}

		if (null != can.getCurrentRateCurrency()) {
			can.setCurrentRateCurrency(can.getCurrentRateCurrency());
		} else {
			if (null != countries && !countries.getCurrency().equalsIgnoreCase("")) {
				can.setCurrentRateCurrency(countries.getCurrency());

			}
		}

		if (null != can.getCurrentRateType()) {
			can.setCurrentRateType(can.getCurrentRateType());
		} else {
			if (null != countries && !countries.getCurrency().equalsIgnoreCase("")) {
				String countryCheck = countries.getName();
				switch (countryCheck) {
				case "India":
					can.setCurrentRateType("Monthly");
					break;
				default:
					can.setCurrentRateType("Hourly");
				}
			}
		}

		if (null != can.getOrgRelationId()) {
			can.setVendorOrgRelationId(can.getOrgRelationId().getId().toString());
			if (can.getOrgRelationId().getResponderLocationId().getId() == loginOrgLocation.getId()) {
				can.setVendorName(can.getOrgRelationId().getLocationId().getOrgLocationName());
			}
			if (can.getOrgRelationId().getLocationId().getId() == loginOrgLocation.getId()) {
				can.setVendorName(can.getOrgRelationId().getResponderLocationId().getOrgLocationName());
			}

		}
		if (null != can.getSpocId()) {
			StringBuilder contactName = new StringBuilder();
			can.setVendorSpocId(can.getSpocId().getId().toString());
			if (null != can.getSpocId().getClientUser().getLastName()) {
				can.setVendorContactName(contactName.append(can.getSpocId().getVendorUser().getFirstName()).append(" ")
						.append(can.getSpocId().getClientUser().getLastName()).toString());
			} else {
				can.setVendorContactName(contactName.append(can.getSpocId().getVendorUser().getFirstName()).toString());
			}

		}
		can.setProfilePercentage(percentage);
		resultJson = commonUtil.toJSON(can);
		return resultJson;
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
	public String deleteCandidateImage(@RequestParam(value = "candidateId", required = false) String candidateId,
			HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId),
				loginOrgLocation.getId());
		candidate.setDocFile(null);
		candidate.setFileContentType(null);
		candidate.setFileName(null);
		candidateService.addCandidate(candidate);
		return "success";

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
	public String saveSkilsBycandidateID(@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "skillName", required = false) String skillName, HttpServletRequest request)
			throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String resultJson;

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
		return "success";
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
	public String saveCandidateProfile(@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			MultipartHttpServletRequest request) throws Exception {

		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		StringBuffer activitySummary = new StringBuffer();
		//OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
		User user = userService.getUserById(loginUserId);
		Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.parseLong(candidateId),
				loginOrgLocation.getId());
		if (null != multiFile.getContentType()
				&& !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")) {
			try {
				if(candidate.getFileName() != null && candidate.getFileName() != "" && !candidate.getFileName().equalsIgnoreCase(multiFile.getOriginalFilename())){
					activitySummary.append("File").append(" : ").append(Constants.OLD_VALUE_TAG_INITIATION).append(candidate.getFileName()).append(Constants.OLD_VALUE_TAG_END).append(" ").append(Constants.NEW_VALUE_TAG_INITIATION).append(multiFile.getOriginalFilename()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}else{
					activitySummary.append("File").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(multiFile.getOriginalFilename()).append(Constants.NEW_VALUE_TAG_END).append(" ");
				}
				candidate.setFileContentType(multiFile.getContentType());
				candidate.setDocFile(multiFile.getBytes());
				candidate.setFileName(multiFile.getOriginalFilename());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		candidateService.addCandidate(candidate);
		
		candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
		candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "", Constants.CANDIDATE_UPLOAD_NEW_PROFILE_IMAGE, activitySummary.toString(), "CANDIDATE");
		
		return "success";
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
	public void displayUserImage(@PathVariable Long candidateId, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		Candidate candidate = null;
		try {
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
					"orgLocations");
			candidate = candidateService.getCandidateByCandidateId(candidateId);
			if (candidate != null && candidate.getDocFile() != null) {
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
	 
	 
	 // Skills Save In Candidates table Not In CandidateSkills Table
	 
	 
	 /* @RequestMapping(value = "/getCandidatesSkillsBycandidateID")
	  public String getCandidatesSkillsBycandidateID(@RequestParam(value="candidateId", required=false) String  candidateId,HttpServletRequest request) throws Exception {
	
			String resultJson;
			
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<CandidateSkills>  candidateSkillsList =  candidateSkillsService.getCandidateSkillsByCandidateId(candidateId);
			Set<CandidateSkills> candidateSkillsSet = new HashSet<>(candidateSkillsList);
			resultJson = commonUtil.toJSON(candidateSkillsSet);
			return resultJson;
			
	   }
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
	@RequestMapping(value = "/updateCandidate")
	public String updateCandidate(@RequestParam(value = "json", required = false) String json,
			HttpServletRequest request) throws Exception {

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		MultipartFile multiFile = null;
		Candidate candidate;
		String jsonc = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(jsonc);

		StringBuilder candidateCreateMsg = new StringBuilder();
		candidate = candidateService.storeCandidate(jsonObj, loginOrgLocation, user, multiFile);
		/*
		 * if(jsonObj.has("candidateId") && jsonObj.getString("candidateId") != null &&
		 * !"".equals(jsonObj.getString("candidateId"))){
		 * SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg",
		 * candidateCreateMsg.append("Success!!  ").append(candidate.getFirstName()).
		 * append(" Details Updated").toString());
		 * 
		 * }
		 */

		return "success";

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
	public @ResponseBody String deleteCandidateByCandidateId(
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "candidateType", required = false) String candidateType,
			@RequestParam(value = "reason", required = false) String reason, HttpServletRequest request)
			throws Exception {
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String resultJson;
		Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId),
				loginOrgLocation.getId());
		StringBuffer activitySummary = new StringBuffer();
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
				activitySummary.append("Reason").append(" : ").append(Constants.NEW_VALUE_TAG_INITIATION).append(reason).append(Constants.NEW_VALUE_TAG_END).append(" ");
			}else{
				activitySummary.append(" ");
			}
			candidate.setUpdatedBy(user.getUserCode());
			candidate.setUpdatedDate(commonUtil.currentDate());
			candidate.setIsFavourite(0);
			candidate.setTeamId(null);
			candidateService.saveOrUpdateCandidate(candidate);
			candidateService.saveOrUpdateCandidateElkDocument(candidate);
			
			candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
			candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "", Constants.CANDIDATE_DELETED, activitySummary.toString(), "CANDIDATE");
			
			StringBuilder candidateRemoveMsg = new StringBuilder();
			SourceLeadSessionManager.setSessionAttribute(request, "candidateCreateMsg",
					candidateRemoveMsg.append("").append(candidate.getFirstName()).append(" Removed").toString());

		}
		resultJson = commonUtil.toJSON(candidate);
		return resultJson;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_HOTLIST','ROLE_CROWDSOURCING_CANDIDATES')")
	@RequestMapping(value = "/saveCandidateAsFavourite", method = RequestMethod.POST)
	public @ResponseBody String saveCandidateAsFavourite(
			@RequestParam(value = "candidateId", required = false) Long candidateId, HttpServletRequest request)
			throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);

		if (null != candidate) {
			if (null == candidate.getIsFavourite() || candidate.getIsFavourite().equals(Constants.RECORD_ACTIVE)) {
				candidate.setIsFavourite(Constants.RECORD_INACTIVE);
			} else {
				candidate.setIsFavourite(Constants.RECORD_ACTIVE);
			}

			candidate.setTeamId(null);
			candidateService.saveOrUpdateCandidate(candidate);
			candidateService.saveOrUpdateCandidateElkDocument(candidate);

		}

		return "success";
	}

	@PreAuthorize("hasRole('ROLE_ASSIGN_HOTLIST_TO_TEAM')")
	@RequestMapping(value = "/assignHotListCandiddatesToTeam", method = RequestMethod.POST)
	public @ResponseBody String assignHotListCandiddatesToTeam(
			@RequestParam(value = "teamId", required = false) Long teamId,
			@RequestParam(value = "hotlist", required = false) String hotlist, HttpServletRequest request)
			throws Exception {

		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		String[] candidateIds = hotlist.split(",");
		Team team = new Team();
		team.setId(teamId);

		for (int i = 0; i < candidateIds.length; i++) {

			Candidate candidate = candidateService.getCandidateByCandidateId(Long.valueOf(candidateIds[i]));
			candidate.setTeamId(team);
			candidateService.saveOrUpdateCandidate(candidate);
			candidateService.saveOrUpdateCandidateElkDocument(candidate);
		}

		return "success";
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
	public @ResponseBody String reActivateCandidateByCandidateId(
			@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "reActivate", required = false) String reActivate, HttpServletRequest request)
			throws Exception {
		//OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		//User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		User user = userService.getUserById(loginUserId);
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocations(orgLocationId);
		Candidate candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(Long.valueOf(candidateId), loginOrgLocation.getId());
		if (candidate != null) {
			if (reActivate.equalsIgnoreCase("1")) {
				candidate.setStatus(Constants.CANDIDATE_ACTIVE);
			}
			candidate.setUpdatedBy(user.getUserCode());
			candidate.setUpdatedDate(commonUtil.currentDate());
			candidateService.saveOrUpdateCandidate(candidate);
			
			candidateActivityCommonUtil.storeCandidateHistory(candidate, user);
			candidateActivityCommonUtil.storeCandidateActivity(user, loginOrgLocation, candidate, "", Constants.CANDIDATE_REACTIVATED, "", "CANDIDATE");
			
			candidateService.saveOrUpdateCandidateElkDocument(candidate);
		}
		return "success";
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
	public void getCandidatesExportList(
			@RequestParam(value = "candidateStatus", required = false) Integer candidateStatus,
			@RequestParam(value = "updatedDateRange", required = false) String updatedDateRange,
			@RequestParam(value = "exportType", required = false) String exportType,
			@RequestParam(value = "candidateType", required = false) String candidateType,
			@RequestParam(value = "isAssociate", required = false) String isAssociate, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		List<Candidate> candidatesList = null;

		if (!isNullOrEmpty(userRole) && userRole.equalsIgnoreCase("ROLE_CROWDSOURCING_RECRUITER")) {
			candidatesList = candidateService.getCandidatesListByOrgLocationIDAndUserId(loginOrgLocation.getId(),
					user.getId());
		} else {
			candidatesList = candidateService.getCandidatesListByOrgLocationID(loginOrgLocation.getId(), org.getId(),
					candidateStatus, updatedDateRange, candidateType, isAssociate);
		}

		File file = null;
		if (!"pdf".equalsIgnoreCase(exportType)) {
			file = candidateService.getCandidateExcelFile(candidatesList, loginOrgLocation.getOrgLocationName(),
					isAssociate);
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
	public String saveOrUpdateOnlySkillBycandidateId(
			@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "skillName", required = false) String skillName, HttpServletRequest request)
			throws Exception {
		Candidate can = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId));
		User user = userService.getUserById(loginUserId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		StringBuffer activitySummary = new StringBuffer();
		if (null != can && StringUtils.isNotEmpty(skillName)) {
			can.setSkillsName("");
			can.setSkillsName(skillName);
			candidateService.saveOrUpdateCandidate(can);
			candidateService.saveOrUpdateCandidateElkDocument(can);
			candidateActivityCommonUtil.storeCandidateHistory(can, user);
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_SKILL_UPDATED, activitySummary.toString(), "CANDIDATE");
			return "success";
		} else {
			can.setSkillsName("");
			candidateService.saveOrUpdateCandidate(can);
			candidateService.saveOrUpdateCandidateElkDocument(can);
			return "failed";
		}
	}

	@RequestMapping(value = "/saveOrUpdateOnlyVisaBycandidateId")
	public String saveOrUpdateOnlyVisaBycandidateId(
			@RequestParam(value = "loginUserId", required = false) Long loginUserId,
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "candidateId", required = false) String candidateId,
			@RequestParam(value = "visaName", required = false) String visaName, HttpServletRequest request)
			throws Exception {
		Candidate can = candidateService.getCandidateByCandidateId(Long.valueOf(candidateId));
		User user = userService.getUserById(loginUserId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		StringBuffer activitySummary = new StringBuffer();
		if (null != can && StringUtils.isNotEmpty(visaName)) {
			can.setVisaType(visaName);
			candidateService.saveOrUpdateCandidate(can);
			candidateService.saveOrUpdateCandidateElkDocument(can);
			candidateActivityCommonUtil.storeCandidateHistory(can, user);
			candidateActivityCommonUtil.storeCandidateActivity(user, orgLocations, can, "", Constants.CANDIDATE_VISA_UPDATED, activitySummary.toString(), "CANDIDATE");
			return "success";
		} else {
			candidateService.saveOrUpdateCandidate(can);
			candidateService.saveOrUpdateCandidateElkDocument(can);
			return "failed";
		}
	}

		   @RequestMapping(value = "/getCandidateReportsListByOrgLocationID")
			 public String getCandidateReportsListByOrgLocationID(@RequestParam(value="candidateType", required=false) String  candidateType,
					 HttpServletRequest request) throws Exception {
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String resultJson;
				List<Candidate> candidatesList = candidateService.getCandidateReportsListByOrgLocationID(orgLocations.getId(), candidateType);
				/* List<Candidate> consultantsList = candidateService.getCandidateReportsListByOrgLocationID(orgLocations.getId(), "Consultant");*/
				
				resultJson = commonUtil.toJSON(candidatesList);
				return resultJson;
			 }
		   
		   
		   @RequestMapping(value = "/getCandidateReports")
			 public String getCandidateReports(@RequestParam(value="reportType", required=false) String reportType,
					 @RequestParam(value="startDate", required=false) String customStartDate,
					 @RequestParam(value="endDate", required=false) String customEndDate,
					 @RequestParam(value="ownersList", required=false) String ownersList,
					 HttpServletRequest request,HttpServletResponse response) throws Exception{


				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
				 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
				 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 Date endDateUTC =null ;
				 String filterDate = null;
				 Date startDateUTC = null;
				 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)){
					 String endDate = outputFormatter1.print(zoned);
					 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
					 endDateUTC = TZ_DF_TS.parse(endDateString);
				 }
					
					if(null != reportType && !"".equals(reportType)){
						if("today".equals(reportType)){
							String startDate = outputFormatter.print(zoned);
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
							filterDate = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
							startDateUTC = TZ_DF_TS.parse(startDateString);
						}else if("yesterday".equals(reportType)){
							DateTime yesterDayZoned = zoned.minusDays(1);
							String yesterDayDate = outputFormatter.print(yesterDayZoned);
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
							startDateUTC = TZ_DF_TS.parse(startDateString);
							
						}else if("week".equals(reportType)){
							DateTime yesterDayZoned = zoned.minusDays(7);
							String yesterDayDate = outputFormatter.print(yesterDayZoned);
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
							startDateUTC = TZ_DF_TS.parse(startDateString);
						}else if("twoweeks".equals(reportType)){
							DateTime yesterDayZoned = zoned.minusDays(14);
							String yesterDayDate = outputFormatter.print(yesterDayZoned);
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
							startDateUTC = TZ_DF_TS.parse(startDateString);
						}else if("month".equals(reportType)){
							DateTime yesterDayZoned = zoned.minusDays(30);
							String yesterDayDate = outputFormatter.print(yesterDayZoned);
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
							startDateUTC = TZ_DF_TS.parse(startDateString);
						}else if("twomonths".equals(reportType)){
							DateTime yesterDayZoned = zoned.minusDays(60);
							String yesterDayDate = outputFormatter.print(yesterDayZoned);
							String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, yesterDayDate);
							startDateUTC = TZ_DF_TS.parse(startDateString);
						}else{
							 if(!"".equalsIgnoreCase(customStartDate)&& !"".equalsIgnoreCase(customEndDate)){
								DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd'T'00:00:00");
								DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
								Date startTime =  LoginSessionController.DATEFORMAT.parse(customStartDate);
								Date endTime =  LoginSessionController.DATEFORMAT.parse(customEndDate);
								String customStartTimeStr = null;
								String customEndTimeStr = null;
								if(null !=startTime){
								 customStartTimeStr = TZ_DF_CSD.format(startTime);  
								}
								
								if(null != endTime){
								 customEndTimeStr = TZ_DF_CED.format(endTime);  
								}
								String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customStartTimeStr);
								String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, customEndTimeStr);
								startDateUTC = TZ_DF_TS.parse(startDateString);
								endDateUTC = TZ_DF_TS.parse(endDateString);
							 }
							
						}
					}
					
					List<Candidate> freshList = new ArrayList<Candidate>();
//					List<Candidate> candidatesList = candidateService.getCandidateReportsListByOrgLocationID(orgLocations.getId(), candidateType);
					
					List<Candidate> canList = candidateService.getCandidateReports(orgLocation.getId(), null, filterDate, startDateUTC, endDateUTC,ownersList);
					
					for(Candidate candidate : canList){
						 DateFormat standardFormat = new SimpleDateFormat ("yyyy-MM-dd");
						String currentTimeStr = Constants.TZ_DF.format(candidate.getCreatedDate());  
						String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
						currentDateInTimezone = standardFormat.format(converteDate);
						candidate.setPostedDateStr(currentDateInTimezone);
						User user  = userService.getUserByUserCode(candidate.getCreatedBy());
						if(!"".equalsIgnoreCase(user.getLastName()) && user.getLastName()!=null && !user.getLastName().isEmpty() ){
							
							StringBuilder sb =  new StringBuilder();
							candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
						}else{
							
							StringBuilder sb =  new StringBuilder();
							candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").toString());
						}
						
						if(null != candidate.getUpdatedDate()){
							candidate.setCandidateUpdateDateToString(LoginSessionController.DATEFORMAT.format(candidate.getUpdatedDate()));
						}
						 
						freshList.add(candidate);
					}
				    String reportsList = commonUtil.toJSON(freshList);
				   
					return reportsList;
			 }
		   
		   
		   @RequestMapping(value = "/getOwnersByCandidates")
			 public String getOwnersByCandidates(@RequestParam(value="ownersList", required=false) String ownersList,
					 @RequestParam(value="reportType", required=false) String reportType,
					 HttpServletRequest request,HttpServletResponse response) throws Exception{

				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
				 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
				 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
				 DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 Date endDateUTC =null ;
				 String filterDate = null;
				 Date startDateUTC = null;
				 if(!Constants.CUSTOM_REPORTS.equalsIgnoreCase(reportType)){
					 String endDate = outputFormatter1.print(zoned);
					 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
					 endDateUTC = TZ_DF_TS.parse(endDateString);
				 }
					
				List<Candidate> freshList = new ArrayList<Candidate>();
					
				List<Candidate> canList = candidateService.getOwnersByCandidates(orgLocation.getId(), null,ownersList);
					for(Candidate candidate : canList){
						 DateFormat standardFormat = new SimpleDateFormat ("yyyy-MM-dd");
						String currentTimeStr = Constants.TZ_DF.format(candidate.getCreatedDate());  
						String currentDateInTimezone= CommonUtil.convertTimeZones(Constants.UTC_TIMEZONE ,timezone.getTimeZoneId(), currentTimeStr);
						Date converteDate = TZ_DF_TS.parse(currentDateInTimezone);
						currentDateInTimezone = standardFormat.format(converteDate);
						User user  = userService.getUserByUserCode(candidate.getCreatedBy());
						if(!"".equalsIgnoreCase(user.getLastName()) && user.getLastName()!=null && !user.getLastName().isEmpty() ){
							
							StringBuilder sb =  new StringBuilder();
							candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
						}else{
							
							StringBuilder sb =  new StringBuilder();
							candidate.setCreatedBy(sb.append(user.getFirstName()).append(" ").toString());
						}
						candidate.setPostedDateStr(currentDateInTimezone);
						candidate.setCandidateUpdateDateToString(currentTimeStr);
						freshList.add(candidate);
					}
				    String reportsList = commonUtil.toJSON(freshList);
					return reportsList;
			 }

		   
		   
	   /**
	    * @author Laxman
	    * store the Favourite Associate 
	    * @param candidateId
	    * @param request
	    * @return success
	    * @throws Exception
	    */
		   
		   @PreAuthorize("hasAnyRole('ROLE_ADD_CANDIDATE_HOTLIST','ROLE_CROWDSOURCING_CANDIDATES')")
		   @RequestMapping(value = "/saveAssociateAsFavourite", method = RequestMethod.POST)
			public @ResponseBody String saveAssociateAsFavourite(@RequestParam(value = "candidateId", required = false) Long candidateId,HttpServletRequest request) throws Exception {
				Candidate candidate = candidateService.getCandidateByCandidateId(candidateId);
				if(null != candidate){
					if(null == candidate.getIsFavouriteAssociate() || candidate.getIsFavouriteAssociate().equals(Constants.RECORD_ACTIVE)){
						candidate.setIsFavouriteAssociate(Constants.RECORD_INACTIVE);
					}else{
						candidate.setIsFavouriteAssociate(Constants.RECORD_ACTIVE);
					}
					candidate.setTeamId(null);
					candidateService.saveOrUpdateCandidate(candidate);
					candidateService.saveOrUpdateCandidateElkDocument(candidate);
					
				}
				
				return "success";
			
	}

}