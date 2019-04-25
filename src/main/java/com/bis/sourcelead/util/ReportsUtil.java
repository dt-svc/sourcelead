package com.bis.sourcelead.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.jfree.data.time.Hour;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.EmployeeVerificationTemplate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OfferLetterTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Component
public class ReportsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsUtil.class);
	
	@Autowired
	ApplicantsBySourceDao applicantsBySourceDao;
	
	@Autowired
	private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private EmployeesService employeesService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	
     public List<RequirementDTO> getAllRequirementsByOrgLocationId(Long orgLocationId,User user,String userRole,HttpServletRequest request){
		
		List<RequirementDTO> requirementDTOList = new ArrayList<>();
		List<Long> requirementIdsList = new ArrayList<>();
		
		try {
			
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocationId));
			
			if(userRole != null && !("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
				BoolQueryBuilder assignedBoolQueryBuilder = QueryBuilders.boolQuery();
				
				//For Level1 Approver
				BoolQueryBuilder reqLevel1ApproverUserIdQueryBuilder = QueryBuilders.boolQuery();
				reqLevel1ApproverUserIdQueryBuilder.must(QueryBuilders.termQuery("reqLevel1ApproverUserId", user.getId().toString()));
				reqLevel1ApproverUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(reqLevel1ApproverUserIdQueryBuilder);
				
				//For Requirement Manager
				BoolQueryBuilder reqManagerUserIdQueryBuilder = QueryBuilders.boolQuery();
				reqManagerUserIdQueryBuilder.must(QueryBuilders.termQuery("managerUserId",user.getId().toString()));
				reqManagerUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(reqManagerUserIdQueryBuilder);
				
				//For Team Owner
				BoolQueryBuilder teamOwnerQueryBuilder = QueryBuilders.boolQuery();
				teamOwnerQueryBuilder.must(QueryBuilders.termQuery("teamOwnerUserId", user.getId().toString()));
				teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(teamOwnerQueryBuilder);
				
				//For Team AssignedUser
				BoolQueryBuilder teamAssignedUserQueryBuilder = QueryBuilders.boolQuery();
				teamAssignedUserQueryBuilder.must(QueryBuilders.termQuery("teamAssignUserId", user.getId().toString()));
				teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
				assignedBoolQueryBuilder.should(teamAssignedUserQueryBuilder);
				
				
				boolQueryBuilder.must(assignedBoolQueryBuilder);
			}
			
			Integer maxResults = applicantsBySourceDao.getRequirementsCount(orgLocationId);
			
			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResults,boolQueryBuilder).getHits();
			if(searchHits != null && searchHits.length >0){
				for (SearchHit hit : searchHits) {
					String sourceAsString = hit.getSourceAsString();
					ObjectMapper mapper = new ObjectMapper();
					if (sourceAsString != null) {
						RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
						RequirementDTO requirementDTO = new RequirementDTO();
						requirementDTO.setRequirementId(String.valueOf(requirementIndex.getRequirementId()));
						requirementDTO.setJobTitle(requirementIndex.getJobTitle());
						requirementDTO.setGeneratedId(requirementIndex.getGeneratedId());
						requirementDTOList.add(requirementDTO);
					}
				}
				
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -60);    
		    String updatedOn = dateFormat.format(cal.getTime());
		    RangeQueryBuilder qb = QueryBuilders.rangeQuery("createdDate").gte(updatedOn);
			boolQueryBuilder.must(qb);
			SearchHit[] searchHits2 = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResults,boolQueryBuilder).getHits();
			if(searchHits2 != null && searchHits2.length >0){
				for (SearchHit hit : searchHits2) {
					String sourceAsString = hit.getSourceAsString();
					ObjectMapper mapper = new ObjectMapper();
					if (sourceAsString != null) {
						RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
						requirementIdsList.add(Long.valueOf(requirementIndex.getRequirementId()));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SourceLeadSessionManager.setSessionAttribute(request, "requirementIdsList", requirementIdsList);
		return requirementDTOList;
	}
     
     private String getDatesFromFilter(DateTime zoned, DateTimeFormatter outputFormatter,int numberOfDays) throws ParseException
 	{
 		DateTime yesterDayZoned = zoned.minusDays(numberOfDays);
 		String yesterDayDate = outputFormatter.print(yesterDayZoned);
 		return yesterDayDate;
 	}	
     
     public List<RequirementDTO> getAllRequirementsByOrgLocationIdForJobsPipeLine(JobFilterDTO jobFilterDTO,OrgLocations orgLocation,User user,String userRole,HttpServletRequest request){
 		
 		List<RequirementDTO> requirementDTOList = new ArrayList<>();
 		List<Long> requirementIdsList = new ArrayList<>();
 		String endDate = null;
 		String startDate = null;
 		try {
 			
 			DateTime zoned = new DateTime(commonUtil.elkDateFormatter(new Date())); //get date
 			DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
 			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(jobFilterDTO.getJobCreatedDateRange())	&& !"".equals(jobFilterDTO.getJobCreatedDateRange())) {
 				 endDate = outputFormatter.print(zoned);
 			}
 			if (null != jobFilterDTO.getJobCreatedDateRange() && !"".equals(jobFilterDTO.getJobCreatedDateRange())) {
 			if (!Constants.CUSTOM_REPORTS.equalsIgnoreCase(jobFilterDTO.getJobCreatedDateRange())) {
 			if (Constants.ONE_MONTH.equals(jobFilterDTO.getJobCreatedDateRange())) {
 				startDate = getDatesFromFilter(zoned, outputFormatter, 30);
			}
 			else if(Constants.TWO_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
 				startDate = getDatesFromFilter(zoned, outputFormatter, 60);
			}
 			else if(Constants.THREE_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
 				startDate = getDatesFromFilter(zoned, outputFormatter, 90);
			}
 			else if(Constants.SIX_MONTHS.equals(jobFilterDTO.getJobCreatedDateRange())) {
 				startDate = getDatesFromFilter(zoned, outputFormatter, 180);
			}
 			else if(Constants.ONE_YEAR.equals(jobFilterDTO.getJobCreatedDateRange())) {
 				startDate = getDatesFromFilter(zoned, outputFormatter, 365);
			}
 			}
 			else {
				if (StringUtils.isNotBlank(jobFilterDTO.getStartDateStr())
						&& StringUtils.isNotBlank(jobFilterDTO.getEndDateStr())
						&& !"undefined".equalsIgnoreCase(jobFilterDTO.getStartDateStr())
						&& !"undefined".equalsIgnoreCase(jobFilterDTO.getEndDateStr())) {
					
					Date startDate1 = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getStartDateStr());
					Date endDate1 = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), jobFilterDTO.getEndDateStr());
					
					startDate = commonUtil.elkDateFormatter(startDate1);
					endDate = commonUtil.elkDateFormatter(endDate1);
					
				}
				else {
					startDate = getDatesFromFilter(zoned, outputFormatter, 30);
				}
			}
 			}
 			
 			
 			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
 			boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocation.getId()));
 			BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
 			if(StringUtils.isNotBlank(jobFilterDTO.getClientIds())){
 				//For OrgRelation
 				BoolQueryBuilder jobQueryBuilder = QueryBuilders.boolQuery();
 				jobQueryBuilder.must(QueryBuilders.matchQuery("orgRelationId", jobFilterDTO.getClientIds()));
 				filterQueryBuilder.should(jobQueryBuilder);	
 				boolQueryBuilder.must(filterQueryBuilder);
 			}
 			

 			if(userRole != null && !("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
 				BoolQueryBuilder assignedBoolQueryBuilder = QueryBuilders.boolQuery();
 				
 				//For Level1 Approver
 				BoolQueryBuilder reqLevel1ApproverUserIdQueryBuilder = QueryBuilders.boolQuery();
 				reqLevel1ApproverUserIdQueryBuilder.must(QueryBuilders.termQuery("reqLevel1ApproverUserId", user.getId().toString()));
 				reqLevel1ApproverUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
 				assignedBoolQueryBuilder.should(reqLevel1ApproverUserIdQueryBuilder);
 				
 				//For Requirement Manager
 				BoolQueryBuilder reqManagerUserIdQueryBuilder = QueryBuilders.boolQuery();
 				reqManagerUserIdQueryBuilder.must(QueryBuilders.termQuery("managerUserId",user.getId().toString()));
 				reqManagerUserIdQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
 				assignedBoolQueryBuilder.should(reqManagerUserIdQueryBuilder);
 				
 				//For Team Owner
 				BoolQueryBuilder teamOwnerQueryBuilder = QueryBuilders.boolQuery();
 				teamOwnerQueryBuilder.must(QueryBuilders.termQuery("teamOwnerUserId", user.getId().toString()));
 				teamOwnerQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
 				assignedBoolQueryBuilder.should(teamOwnerQueryBuilder);
 				
 				//For Team AssignedUser
 				BoolQueryBuilder teamAssignedUserQueryBuilder = QueryBuilders.boolQuery();
 				teamAssignedUserQueryBuilder.must(QueryBuilders.termQuery("teamAssignUserId", user.getId().toString()));
 				teamAssignedUserQueryBuilder.mustNot(QueryBuilders.matchQuery("requirementStatus", "DRAFT"));
 				assignedBoolQueryBuilder.should(teamAssignedUserQueryBuilder);
 				
 				
 				boolQueryBuilder.must(assignedBoolQueryBuilder);
 			}
 			
 			Integer maxResults = applicantsBySourceDao.getRequirementsCount(orgLocation.getId());
 			
 			RangeQueryBuilder qb = QueryBuilders.rangeQuery("createdDate").gte(startDate).lte(endDate);
			boolQueryBuilder.must(qb);
 			
 			SearchHit[] searchHits = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResults,boolQueryBuilder).getHits();
 			if(searchHits != null && searchHits.length >0){
 				for (SearchHit hit : searchHits) {
 					String sourceAsString = hit.getSourceAsString();
 					ObjectMapper mapper = new ObjectMapper();
 					if (sourceAsString != null) {
 						RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
 						RequirementDTO requirementDTO = new RequirementDTO();
 						requirementDTO.setRequirementId(String.valueOf(requirementIndex.getRequirementId()));
 						requirementDTO.setJobTitle(requirementIndex.getJobTitle());
 						requirementDTO.setGeneratedId(requirementIndex.getGeneratedId());
 						requirementDTOList.add(requirementDTO);
 					}
 				}
 				
 			}
 			
 			
 			SearchHit[] searchHits2 = sourceleadELKBaseIndexer.searchDocumentWithMaxResults(Constants.ELK_REQUIREMENT_INDEX, Constants.ELK_REQUIREMENT_TYPE,0,maxResults,boolQueryBuilder).getHits();
 			if(searchHits2 != null && searchHits2.length >0){
 				for (SearchHit hit : searchHits2) {
 					String sourceAsString = hit.getSourceAsString();
 					ObjectMapper mapper = new ObjectMapper();
 					if (sourceAsString != null) {
 						RequirementIndex requirementIndex = mapper.readValue(sourceAsString, RequirementIndex.class);
 						requirementIdsList.add(Long.valueOf(requirementIndex.getRequirementId()));
 					}
 				}
 			}
 			
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		
 		SourceLeadSessionManager.setSessionAttribute(request, "requirementIdsList", requirementIdsList);
 		return requirementDTOList;
 	}
     
     
     
     
	public Date[] today(OrgLocations orgLocations) throws Exception {
	
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime toDayZoned = zoned.minusDays(0);
		String todayDate = outputFormatter.print(toDayZoned);
		Date todayDateDateUTC = TZ_DF_TS.parse(todayDate);
		
		Date[] dates = new Date[2];
		dates[0] = todayDateDateUTC;
		dates[1] = startDateUTC;
		return dates;
	}
	
public Date[] yesterdayDates(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime oneDayZoned = zoned.minusDays(1);
		String oneDaysDate = outputFormatter.print(oneDayZoned);
		Date oneDateUTC = TZ_DF_TS.parse(oneDaysDate);
		
		Date[] dates = new Date[2];
		dates[0] = oneDateUTC;
		dates[1] = oneDateUTC;
		return dates;
	}
	
	
	/**
	 * Get from and to date of last 7 days
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 * @throws Exception 
	 */
	public Date[] last7Days(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime sevenDayZoned = zoned.minusDays(7);
		String sevenDaysDate = outputFormatter.print(sevenDayZoned);
		Date sevenDateUTC = TZ_DF_TS.parse(sevenDaysDate);
		
		Date[] dates = new Date[2];
		dates[0] = sevenDateUTC;
		dates[1] = startDateUTC;
		return dates;
	}
	
	/**
	 * Get from and to date of last week
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 * @throws Exception 
	 */
	public Date[] lastWeek(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime startDayZoned = zoned.minusWeeks(2);
		String sevenDaysStartDate = outputFormatter.print(startDayZoned);
		Date dateStartUTC = TZ_DF_TS.parse(sevenDaysStartDate);
		
		DateTime endDayZoned = zoned.minusWeeks(1);
		String sevenDaysEndDate = outputFormatter.print(endDayZoned);
		Date dateEndUTC = TZ_DF_TS.parse(sevenDaysEndDate);
		
		
		Date[] dates = new Date[2];
		dates[0] = dateStartUTC;
		dates[1] = dateEndUTC;
		return dates;
	}
	
	/**
	 * Get from and to date of last month
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 * @throws Exception 
	 */
	public Date[] lastMonth(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime startDayZoned = zoned.minusMonths(2);
		String sevenDaysStartDate = outputFormatter.print(startDayZoned);
		Date dateStartUTC = TZ_DF_TS.parse(sevenDaysStartDate);
		
		DateTime endDayZoned = zoned.minusMonths(1);
		String sevenDaysEndDate = outputFormatter.print(endDayZoned);
		Date dateEndUTC = TZ_DF_TS.parse(sevenDaysEndDate);
		
		
		Date[] dates = new Date[2];
		dates[0] = dateStartUTC;
		dates[1] = dateEndUTC;
		return dates;
	}
	
	/**
	 * Get from and to date of last 15 days
	 * @return Date[]  Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last15DaysDates(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime fifteenDayZoned = zoned.minusDays(15);
		String fifteenDaysDate = outputFormatter.print(fifteenDayZoned);
		Date fifteenDateUTC = TZ_DF_TS.parse(fifteenDaysDate);
		
		Date[] dates = new Date[2];
		dates[0] = fifteenDateUTC;
		dates[1] = startDateUTC;
		
		return dates;
	}
	
	/**
	 * Get from and to date of last 1 Month
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last1MonthDates(OrgLocations orgLocations) throws Exception {
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime oneMonthZoned = zoned.minusDays(30);
		String oneMonthDate = outputFormatter.print(oneMonthZoned);
		Date oneMonthDateUTC = TZ_DF_TS.parse(oneMonthDate);
		
		Date[] dates = new Date[2];
		dates[0] = oneMonthDateUTC;
		dates[1] = startDateUTC;
		
		return dates;
	}
	
	/**
	 * Get from and to date of current Month
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] currentMonthDates(OrgLocations orgLocations) throws Exception {
		
		Calendar c = Calendar.getInstance(); // this takes current date
		c.set(Calendar.DAY_OF_MONTH, 1);
		
		//System.out.println(c.getTime());
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		Date oneMonthDateUTC = c.getTime();
		Date[] dates = new Date[2];
		dates[0] = oneMonthDateUTC;
		dates[1] = startDateUTC;
		
		return dates;
	}
	
	
	private int Hour_(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Date[] last2MonthDates(OrgLocations orgLocations) throws Exception {
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime oneMonthZoned = zoned.minusDays(30);
		String oneMonthDate = outputFormatter.print(oneMonthZoned);
		Date oneMonthDateUTC = TZ_DF_TS.parse(oneMonthDate);
		
		Date[] dates = new Date[2];
		dates[0] = oneMonthDateUTC;
		dates[1] = startDateUTC;
		
		return dates;
	}
	
	/**
	 * Get from and to date of last 3 Months
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 * @throws Exception 
	 */
	public Date[] last3MonthDates(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime threeMonthZoned = zoned.minusDays(60);
		String threeMonthDate = outputFormatter.print(threeMonthZoned);
		Date threeMonthDateUTC = TZ_DF_TS.parse(threeMonthDate);
		
		Date[] dates = new Date[2];
		dates[0] = threeMonthDateUTC;
		dates[1] = startDateUTC;
		
		return dates;
	}
	
	/**
	 * Get from and to date of last 6 Months
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 * @throws Exception 
	 */
	public Date[] last6MonthDates(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime sixMonthZoned = zoned.minusMonths(6);
		String sixMonthDate = outputFormatter.print(sixMonthZoned);
		Date sixMonthDateUTC = TZ_DF_TS.parse(sixMonthDate);
		
		Date[] dates = new Date[2];
		dates[0] = sixMonthDateUTC;
		dates[1] = startDateUTC;
		
		return dates;
	}
	
	
   public Date[] last2Weeks(OrgLocations orgLocations) throws Exception {
		
		Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd");
		String startDate = outputFormatter.print(zoned);
		Date startDateUTC = TZ_DF_TS.parse(startDate);
		
		DateTime sevenDayZoned = zoned.minusDays(7);
		String sevenDaysDate = outputFormatter.print(sevenDayZoned);
		Date sevenDateUTC = TZ_DF_TS.parse(sevenDaysDate);
		
		Date[] dates = new Date[2];
		dates[0] = sevenDateUTC;
		dates[1] = startDateUTC;
		return dates;
	}

	/**
	 * Get from and to date of last 1 Year
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] lastYearDates() {
		Date[] dates = new Date[2];
		Calendar calendarStart = Calendar.getInstance();
		int previousYear = calendarStart.get(Calendar.YEAR) - 1;
		calendarStart.set(Calendar.YEAR, previousYear);
		calendarStart.set(Calendar.MONTH, 0);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = calendarStart.getTime();
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.YEAR, previousYear);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
		dates[1] = calendarEnd.getTime();
		return dates;

	}
	
	/**
	 * Get from and to date of current  Date
	 * 
	 * @return Date[] Date[0] = Start Date ,Date[1] = End date
	 */
	public Date[] yearToDate() {
		Date[] dates = new Date[2];
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		dates[1] = cal.getTime();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		dates[0] = cal.getTime();
		return dates;

	}
	
	
	
	
	public Date[] getJobFilterDate(String dateSelection,OrgLocations orgLocations) throws Exception {
		Date[] dates = new Date[2];
		Date[] searchDates = null;
		Date startDate = null;
		Date endDate = null;
		if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
			searchDates = last7Days(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_15_DAYS)) {
			searchDates = last15DaysDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_MONTH)) {
			searchDates = currentMonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_1_MONTH)) {
			searchDates = last1MonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_3_MONTHS)) {
			searchDates = last3MonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_YTD)) {
			searchDates = yearToDate();
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_YEAR)) {
			searchDates = lastYearDates();
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_1_WEEK)) {
			searchDates = last7Days(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_2_WEEKS)) {
			searchDates = last2Weeks(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_2_MONTHS)) {
			searchDates = last2MonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_TODAY)) {
			searchDates = today(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_YESTERDAY)) {
			searchDates = yesterdayDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_Last_Week)) {
			searchDates = lastWeek(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_1_Month_Ago)) {
			searchDates = lastMonth(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_6_MONTHS)) {
			searchDates = last6MonthDates(orgLocations);
		}
		
		
		
		
		startDate = new java.sql.Date(searchDates[0].getTime());
		endDate = new java.sql.Date(searchDates[1].getTime());
		dates[0] = startDate;
		dates[1] = endDate;
		return dates;
	}

	public    Date[] tenDays(int days) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[0] = sdf.parse(sdf.format(cal.getTime()));
		cal.add(Calendar.DATE, +days);
		dates[1] = sdf.parse(sdf.format(cal.getTime()));
		return dates;
	}

	public   Date[] apAgingPeriodDays(int sDays, int days) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, +sDays);
		dates[0] = sdf.parse(sdf.format(cal.getTime()));
		Calendar currentCal = Calendar.getInstance();
		currentCal.add(Calendar.DATE, +days);
		dates[1] = sdf.parse(sdf.format(currentCal.getTime()));
		return dates;
	}
	

	public  Date above365Days() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -366);
		Date date = sdf.parse(sdf.format(cal.getTime()));
		return date;
	}

	public Date[] getCustomDates(String fromDate, String toDate,OrgLocations orgLocations) throws ParseException {
		   Date[]  dates = new Date[2];
		 /*  dates[0] =new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);  
		   dates[1] =new SimpleDateFormat("yyyy-MM-dd").parse(toDate);  */
		   dates[0] =new SimpleDateFormat(orgLocations.getDateFormat()).parse(fromDate);  
		   dates[1] =new SimpleDateFormat(orgLocations.getDateFormat()).parse(toDate);  
		   
		return dates;
	}

	
	
	public Date[] getProjectFilterDate(String dateSelection,OrgLocations orgLocations) throws Exception {
		Date[] dates = new Date[2];
		Date[] searchDates = null;
		Date startDate = null;
		Date endDate = null;
		if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_7_DAYS)) {
			searchDates = last7Days(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_15_DAYS)) {
			searchDates = last15DaysDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_1_MONTH)) {
			searchDates = last1MonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_3_MONTHS)) {
			searchDates = last3MonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_YTD)) {
			searchDates = yearToDate();
		}else if(dateSelection.equalsIgnoreCase(Constants.REPORT_FILTER_LAST_YEAR)) {
			searchDates = lastYearDates();
		} 
		
		startDate = new java.sql.Date(searchDates[0].getTime());
		endDate = new java.sql.Date(searchDates[1].getTime());
		dates[0] = startDate;
		dates[1] = endDate;
		return dates;
	}
	
	public Date[] customeDates(OrgLocations orgLocations,String customStartDate,String customEndDate) throws Exception {
		    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
			DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
			DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
			Date fromDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), customStartDate);
 	        Date toDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), customEndDate);
			String customStartTimeStr = TZ_DF_CSD.format(fromDate);
			String customEndTimeStr = TZ_DF_CED.format(toDate);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
			String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
			fromDate = TZ_DF_TS.parse(startDateString);
			toDate = TZ_DF_TS.parse(endDateString);
			
			Date[] dates = new Date[2];
			dates[0] = fromDate;
			dates[1] = toDate;
		
		return dates;
	}
	
	public Date[] projectUpToDates(OrgLocations orgLocations,Date customStartDate,Date customEndDate) throws Exception {
	    Timezone timezone = timeZoneService.getTimezone(orgLocations.getTimezone().getId());
		DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
		Date fromDate =  commonUtil.orgLocationCommonDate(customStartDate,orgLocations);
	    Date toDate =  commonUtil.orgLocationCommonDate(customEndDate,orgLocations);
		String customStartTimeStr = TZ_DF_CSD.format(fromDate);
		String customEndTimeStr = TZ_DF_CED.format(toDate);
		String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
		String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
		fromDate = TZ_DF_TS.parse(startDateString);
		toDate = TZ_DF_TS.parse(endDateString);
		
		Date[] dates = new Date[2];
		dates[0] = fromDate;
		dates[1] = toDate;
	
	return dates;
}
	
	
	
	public Date[] requestedOrgFilterDate(String dateSelection,OrgLocations orgLocations) throws Exception {
		Date[] dates = new Date[2];
		Date[] searchDates = null;
		Date startDate = null;
		Date endDate = null;
		
		if(dateSelection.equalsIgnoreCase(Constants.ORG_REQUEST_FILTER_ONE_WEEK_AGO)) {
			searchDates = last7Days(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.ORG_REQUEST_FILTER_TWO_WEEK_AGO)) {
			searchDates = last15DaysDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.ORG_REQUEST_FILTER_ONE_MONTH_AGO)) {
			searchDates = last1MonthDates(orgLocations);
		}else if(dateSelection.equalsIgnoreCase(Constants.ORG_REQUEST_FILTER_THREE_MONTHS_AGO)) {
			searchDates = last3MonthDates(orgLocations);
		}
		
		startDate = new java.sql.Date(searchDates[0].getTime());
		endDate = new java.sql.Date(searchDates[1].getTime());
		dates[0] = startDate;
		dates[1] = endDate;
		return dates;
	}
	@SuppressWarnings("deprecation")
	public void viewCompleteletterheadData(String templateContent, ContractsTemplate contractsTemplate, HttpServletResponse response) throws Exception {

		org.jsoup.nodes.Document doc = Jsoup.parse(templateContent);
	    doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);    
        String finalString=doc.html();
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    LetterHead letterHead = contractsTemplate.getLetterHead();
	    Document document = null;
		if (letterHead != null && StringUtils.isNotBlank(letterHead.getTemplateType())) {
			Rectangle pageSize;
			if ("A4".equalsIgnoreCase(letterHead.getTemplateType())) {
				 pageSize = new Rectangle(PageSize.A4);
				 pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
				
			} else if ("Letter".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("A6".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("Legal".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LEGAL);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			}
			  PdfWriter writer = PdfWriter.getInstance(document, byteArray);
			    document.open();
			    
			    
			    PdfPTable header = new PdfPTable(1);
			       header.setWidths(new int[]{2});
			       header.setTotalWidth(527);
			       header.setLockedWidth(true);
			       header.getDefaultCell().setFixedHeight(40);
			       header.getDefaultCell().setBorder(Rectangle.BOTTOM);
			       header.getDefaultCell().setBorderColor(BaseColor.BLUE);
			       header.addCell(getCell(letterHead));
			       document.add(header);
			       
			       Paragraph p = new Paragraph(letterHead.getHeaderText(),new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
			       p.setAlignment(2);
			       document.add(p);

			       PdfPTable footer = new PdfPTable(1);
			       // set defaults
					footer.setWidths(new int[]{2});
					footer.setTotalWidth(527);
					footer.setLockedWidth(true);
					footer.getDefaultCell().setFixedHeight(40);
					footer.getDefaultCell().setBorder(Rectangle.TOP);
					footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
			   	
					PdfPCell footerCell = new PdfPCell();
					Paragraph p1 = new Paragraph(letterHead.getFooterText(),new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
					if("Left".equalsIgnoreCase(letterHead.getAlignFooterText())) {
				    	p1.setAlignment(Element.ALIGN_LEFT);
				    }else if("Right".equalsIgnoreCase(letterHead.getAlignFooterText())){
				    	p1.setAlignment(Element.ALIGN_RIGHT);
				    }else {
				    	p1.setAlignment(Element.ALIGN_CENTER);
				    }
							
					if(letterHead.getFooterBgColor() != null && letterHead.getFooterBgColor() != "") {
				    	BaseColor myColor = WebColors.getRGBColor(letterHead.getFooterBgColor());
				    	footerCell.setBackgroundColor(myColor);
				    }
					footerCell.addElement(p1);
			     	footer.addCell(footerCell).setPaddingTop(-4);
					
		   	
				   	// add placeholder for total page count
				   	PdfPCell totalPageCount = new PdfPCell();
				   	totalPageCount.setBorder(Rectangle.TOP);
				   	totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
				   	footer.addCell(totalPageCount);
				   	
				   	// write page
				   	PdfContentByte canvas = writer.getDirectContent();
				   	canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
				   	footer.writeSelectedRows(0, -1, 34, 50, canvas);
				   	canvas.endMarkedContentSequence();
				   	
				    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(finalString));
				    document.close();
			} else {
			   document = new Document();
			    PdfWriter.getInstance(document, byteArray);
		  	    document.open();
		  	    HTMLWorker htmlWorker = new HTMLWorker(document);
		  	    htmlWorker.parse(new StringReader(templateContent));
		  	    document.close();
		}
	    
	   	byte[] pdfBytes = byteArray.toByteArray();
	   	response.setHeader("Content-disposition","inline; filename=Letterhead.pdf");
	   	response.setContentType("application/pdf");
	   	response.setContentLength(pdfBytes.length);
	     ServletOutputStream sos = response.getOutputStream();
	     sos.write(pdfBytes);
	     sos.flush();
	     sos.close();
		
	}
	
	private PdfPCell getCell(LetterHead letterHead) throws Exception
	{
		 PdfPCell cell = new PdfPCell();
		    
		    if(letterHead.getHeaderBgColor() != null && letterHead.getHeaderBgColor() != "") {
		    	BaseColor myColor = WebColors.getRGBColor(letterHead.getHeaderBgColor());
		    	cell.setBackgroundColor(myColor);
		    }
		    
		    String removedUrl = StringUtils.removeEnd(staticPath, staticPath.substring(staticPath.lastIndexOf("/")));
		    Image img1 = Image.getInstance(removedUrl+letterHead.getFileURL());
		    img1.setAbsolutePosition(50,50);
		    
		    if("Right".equalsIgnoreCase(letterHead.getAlignLogo())) {
		    	cell.setPaddingRight(0);
		    	cell.setPaddingLeft(476);
		    }else if("Left".equalsIgnoreCase(letterHead.getAlignLogo())){
		    	cell.setPaddingRight(476);
		    	cell.setPaddingLeft(0);
		    }else {
		    	cell.setPaddingRight(233);
		    	cell.setPaddingLeft(233);
		    }
		    
	    	cell.addElement(img1);
		    return cell;  
		
		
	}
	
	@SuppressWarnings("static-access")
	public String replaceAllTemplatePlaceHolders(String templateContent, EmployeeVerificationTemplate empTemplate, String toAddress, User user, OrgLocations orgLocation) throws Exception {
        
		Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(user.getId(), orgLocation.getId());
		UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(user.getId(), orgLocation.getId());
	         Pattern logEntry = Pattern.compile("\\[(.*?)\\]");
	            Matcher matchPattern = logEntry.matcher(templateContent);
	            StringBuffer sb = new StringBuffer();
	            while(matchPattern.find()) {
	                if(matchPattern.group().equalsIgnoreCase("[Issued_Date]")){
	                    matchPattern.appendReplacement(sb, conversionUtil.getDateAsddMMMYYYY(new Date()));
	                }else if(matchPattern.group().equalsIgnoreCase("[To_Whom_It_May_Concern]")){
	                	if(StringUtils.isNotEmpty(toAddress))
	                    matchPattern.appendReplacement(sb, toAddress);
	                	else
	                	matchPattern.appendReplacement(sb, "--");
	                }else if(matchPattern.group().equalsIgnoreCase("[Company_Location]")){
						if (StringUtils.isNotEmpty(orgLocation.getCountry()))
							matchPattern.appendReplacement(sb, orgLocation.getCountry());
						else
							matchPattern.appendReplacement(sb, "--");   
	                }else if(matchPattern.group().equalsIgnoreCase("[Company_Name]")){
	                	if(StringUtils.isNotEmpty(orgLocation.getOrgLocationName()))
	                    matchPattern.appendReplacement(sb, orgLocation.getOrgLocationName());
	                	else
		                	matchPattern.appendReplacement(sb, "--");
		            }else if(matchPattern.group().equalsIgnoreCase("[Company_Email]")){
		            	if (StringUtils.isNotEmpty(orgLocation.getUser().getEmail()))
							matchPattern.appendReplacement(sb, orgLocation.getUser().getEmail());
						else
		            	matchPattern.appendReplacement(sb,"--");	
		            }else if(matchPattern.group().equalsIgnoreCase("[Company_Contact]")){
		            	if(StringUtils.isNotEmpty(orgLocation.getPhone()))
	                	matchPattern.appendReplacement(sb,orgLocation.getPhone());
		            	else
		                	matchPattern.appendReplacement(sb, "--");
	                }else if(matchPattern.group().equalsIgnoreCase("[Associate_Name]")){
	                	StringBuilder name = new StringBuilder();
	                	if(StringUtils.isNotEmpty(user.getLastName()))
	                	matchPattern.appendReplacement(sb,name.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
	                	else
	                	matchPattern.appendReplacement(sb,user.getFirstName());
		            }else if(matchPattern.group().equalsIgnoreCase("[Associate_Designation_Role]")){
		            	if(StringUtils.isNotEmpty(userRole.getUserType()))
	                    matchPattern.appendReplacement(sb,userRole.getUserType());
		            	else
		                	matchPattern.appendReplacement(sb, "--");
	                }else if(matchPattern.group().equalsIgnoreCase("[Associate_Employment_Type]")){
	                	if(StringUtils.isNotEmpty(employee.getUserEmploymentType()))
		                    matchPattern.appendReplacement(sb,employee.getUserEmploymentType());
			            	else
	                	matchPattern.appendReplacement(sb, "--");
	                }else if(matchPattern.group().equalsIgnoreCase("[SSN]")){
		               matchPattern.appendReplacement(sb, "--");
	                }else if(matchPattern.group().equalsIgnoreCase("[PAN]")){
		               matchPattern.appendReplacement(sb, "--");
	                }else if(matchPattern.group().equalsIgnoreCase("[Pay_Rate]")){
		               matchPattern.appendReplacement(sb, "--");
	                }else if(matchPattern.group().equalsIgnoreCase("[Start_Date]")){
	                   if(employee.getHireDate() != null)
		               matchPattern.appendReplacement(sb, commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),employee.getHireDate()));
	                   else
	                   matchPattern.appendReplacement(sb, "--");
		            }else if(matchPattern.group().equalsIgnoreCase("[End_Date]")){
		            	
		            	if(Constants.INACTIVE.equalsIgnoreCase(userRole.getUserState().toString())) {
		            		matchPattern.appendReplacement(sb,commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),userRole.getUpdatedDate()));
		    			}else {
		    				matchPattern.appendReplacement(sb,"Till Date");
		    			}
	                }else if(matchPattern.group().equalsIgnoreCase("[Signatory_Name]")){
	                	StringBuilder signatoryName = new StringBuilder();
	                	if(StringUtils.isNotEmpty(empTemplate.getSignatory().getLastName()))
	                	matchPattern.appendReplacement(sb,signatoryName.append(empTemplate.getSignatory().getFirstName()).append(" ").append(empTemplate.getSignatory().getLastName()).toString());
	                	else
	                	matchPattern.appendReplacement(sb,empTemplate.getSignatory().getFirstName());
		            }else if(matchPattern.group().equalsIgnoreCase("[Signatory_Designation]")){
						if (StringUtils.isNotEmpty(userRole.getDesignation()))
							matchPattern.appendReplacement(sb, userRole.getDesignation());
						else
							matchPattern.appendReplacement(sb,"");
		            }else if(matchPattern.group().equalsIgnoreCase("[Signatory_Email]")){
		               matchPattern.appendReplacement(sb, empTemplate.getSignatory().getEmail());
		            }else if(matchPattern.group().equalsIgnoreCase("[Signatory_Contact]")){
			           matchPattern.appendReplacement(sb, empTemplate.getSignatory().getMobilePhone());
		            }else if(matchPattern.group().equalsIgnoreCase("[Signature]")){
		               matchPattern.appendReplacement(sb,"--");
		            }
	                }
	            	   matchPattern.appendTail(sb);
	            	   return sb.toString();
	
}

	public void viewOfferLetterHeadData(String templateContent, OfferLetterTemplate offerLetterTemplate, HttpServletResponse response) throws Exception {

		org.jsoup.nodes.Document doc = Jsoup.parse(templateContent);
	    doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);    
        String finalString=doc.html();
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	   
	    Document document = null;
	    
	    LetterHead  letterHead = offerLetterTemplate.getLetterHeadId();
	    
		if (letterHead != null && StringUtils.isNotBlank(letterHead.getTemplateType())) {
			Rectangle pageSize;
			if ("A4".equalsIgnoreCase(letterHead.getTemplateType())) {
				 pageSize = new Rectangle(PageSize.A4);
				 pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
				
			} else if ("Letter".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("A6".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("Legal".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LEGAL);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			}
			  PdfWriter writer = PdfWriter.getInstance(document, byteArray);
			    document.open();
			    
			    
			    PdfPTable header = new PdfPTable(1);
			       header.setWidths(new int[]{2});
			       header.setTotalWidth(527);
			       header.setLockedWidth(true);
			       header.getDefaultCell().setFixedHeight(40);
			       header.getDefaultCell().setBorder(Rectangle.BOTTOM);
			       header.getDefaultCell().setBorderColor(BaseColor.BLUE);
			       header.addCell(getCell(letterHead));
			       document.add(header);
			       
			       Paragraph p = new Paragraph(letterHead.getHeaderText(),new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
			       p.setAlignment(2);
			       document.add(p);

			       PdfPTable footer = new PdfPTable(1);
			       // set defaults
					footer.setWidths(new int[]{2});
					footer.setTotalWidth(527);
					footer.setLockedWidth(true);
					footer.getDefaultCell().setFixedHeight(40);
					footer.getDefaultCell().setBorder(Rectangle.TOP);
					footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
			   	
					PdfPCell footerCell = new PdfPCell();
					Paragraph p1 = new Paragraph(letterHead.getFooterText(),new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
					if("Left".equalsIgnoreCase(letterHead.getAlignFooterText())) {
				    	p1.setAlignment(Element.ALIGN_LEFT);
				    }else if("Right".equalsIgnoreCase(letterHead.getAlignFooterText())){
				    	p1.setAlignment(Element.ALIGN_RIGHT);
				    }else {
				    	p1.setAlignment(Element.ALIGN_CENTER);
				    }
							
					if(letterHead.getFooterBgColor() != null && letterHead.getFooterBgColor() != "") {
				    	BaseColor myColor = WebColors.getRGBColor(letterHead.getFooterBgColor());
				    	footerCell.setBackgroundColor(myColor);
				    }
					footerCell.addElement(p1);
			     	footer.addCell(footerCell).setPaddingTop(-4);
					
		   	
				   	// add placeholder for total page count
				   	PdfPCell totalPageCount = new PdfPCell();
				   	totalPageCount.setBorder(Rectangle.TOP);
				   	totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
				   	footer.addCell(totalPageCount);
				   	
				   	// write page
				   	PdfContentByte canvas = writer.getDirectContent();
				   	canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
				   	footer.writeSelectedRows(0, -1, 34, 50, canvas);
				   	canvas.endMarkedContentSequence();
				   	
				    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(finalString));
				    document.close();
			} else {
				    document = new Document();
				    PdfWriter.getInstance(document, byteArray);
			  	    document.open();
			  	    HTMLWorker htmlWorker = new HTMLWorker(document);
			  	    htmlWorker.parse(new StringReader(templateContent));
			  	    document.close();
			  } 
	  
		    
		   	byte[] pdfBytes = byteArray.toByteArray();
		   	response.setHeader("Content-disposition","inline; filename=Offer Letter Template.pdf");
		   	response.setContentType("application/pdf");
		   	response.setContentLength(pdfBytes.length);
		     ServletOutputStream sos = response.getOutputStream();
		     sos.write(pdfBytes);
		     sos.flush();
		     sos.close();
		
	  }

	@SuppressWarnings("deprecation")
	public void viewEmployeeCompleteletterheadData(String templateContent, EmployeeVerificationTemplate empTemplate, HttpServletResponse response) throws Exception {

		org.jsoup.nodes.Document doc = Jsoup.parse(templateContent);
	    doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);    
        String finalString=doc.html();
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    LetterHead letterHead = empTemplate.getLetterHead();
	    Document document = null;
		if (letterHead != null && StringUtils.isNotBlank(letterHead.getTemplateType())) {
			Rectangle pageSize;
			if ("A4".equalsIgnoreCase(letterHead.getTemplateType())) {
				 pageSize = new Rectangle(PageSize.A4);
				 pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
				
			} else if ("Letter".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("A6".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("Legal".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LEGAL);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			}
			  PdfWriter writer = PdfWriter.getInstance(document, byteArray);
			    document.open();
			    
			    
			    PdfPTable header = new PdfPTable(1);
			       header.setWidths(new int[]{2});
			       header.setTotalWidth(527);
			       header.setLockedWidth(true);
			       header.getDefaultCell().setFixedHeight(40);
			       header.getDefaultCell().setBorder(Rectangle.BOTTOM);
			       header.getDefaultCell().setBorderColor(BaseColor.BLUE);
			       header.addCell(getCell(letterHead));
			       document.add(header);
			       
			       Paragraph p = new Paragraph(letterHead.getHeaderText(),new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
			       p.setAlignment(2);
			       document.add(p);

			       PdfPTable footer = new PdfPTable(1);
			       // set defaults
					footer.setWidths(new int[]{2});
					footer.setTotalWidth(527);
					footer.setLockedWidth(true);
					footer.getDefaultCell().setFixedHeight(40);
					footer.getDefaultCell().setBorder(Rectangle.TOP);
					footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
			   	
					PdfPCell footerCell = new PdfPCell();
					Paragraph p1 = new Paragraph(letterHead.getFooterText(),new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
					if("Left".equalsIgnoreCase(letterHead.getAlignFooterText())) {
				    	p1.setAlignment(Element.ALIGN_LEFT);
				    }else if("Right".equalsIgnoreCase(letterHead.getAlignFooterText())){
				    	p1.setAlignment(Element.ALIGN_RIGHT);
				    }else {
				    	p1.setAlignment(Element.ALIGN_CENTER);
				    }
							
					if(letterHead.getFooterBgColor() != null && letterHead.getFooterBgColor() != "") {
				    	BaseColor myColor = WebColors.getRGBColor(letterHead.getFooterBgColor());
				    	footerCell.setBackgroundColor(myColor);
				    }
					footerCell.addElement(p1);
			     	footer.addCell(footerCell).setPaddingTop(-4);
					
		   	
				   	// add placeholder for total page count
				   	PdfPCell totalPageCount = new PdfPCell();
				   	totalPageCount.setBorder(Rectangle.TOP);
				   	totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
				   	footer.addCell(totalPageCount);
				   	
				   	// write page
				   	PdfContentByte canvas = writer.getDirectContent();
				   	canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
				   	footer.writeSelectedRows(0, -1, 34, 50, canvas);
				   	canvas.endMarkedContentSequence();
				   	
				    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(finalString));
				    document.close();
			} else {
			   document = new Document();
			    PdfWriter.getInstance(document, byteArray);
		  	    document.open();
		  	    HTMLWorker htmlWorker = new HTMLWorker(document);
		  	    htmlWorker.parse(new StringReader(templateContent));
		  	    document.close();
		}
	    
	   	byte[] pdfBytes = byteArray.toByteArray();
	   	response.setHeader("Content-disposition","inline; filename=Letterhead.pdf");
	   	response.setContentType("application/pdf");
	   	response.setContentLength(pdfBytes.length);
	     ServletOutputStream sos = response.getOutputStream();
	     sos.write(pdfBytes);
	     sos.flush();
	     sos.close();
		
	}

	@SuppressWarnings("deprecation")
	public void viewLetterHeadCompleteletterheadData(String templateContent, LetterHead letterHead, HttpServletResponse response) throws Exception {

		org.jsoup.nodes.Document doc = Jsoup.parse(templateContent);
	    doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);    
        String finalString=doc.html();
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    Document document = null;
		if (letterHead != null && StringUtils.isNotBlank(letterHead.getTemplateType())) {
			Rectangle pageSize;
			if ("A4".equalsIgnoreCase(letterHead.getTemplateType())) {
				 pageSize = new Rectangle(PageSize.A4);
				 pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
				
			} else if ("Letter".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("A6".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LETTER);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			} else if ("Legal".equalsIgnoreCase(letterHead.getTemplateType())) {
				pageSize = new Rectangle(PageSize.LEGAL);
				pageSize.setBackgroundColor(WebColors.getRGBColor(letterHead.getTemplateBgColor()));
				document = new Document(pageSize, Float.parseFloat(letterHead.getPaddingLeft()),
						Float.parseFloat(letterHead.getPaddingRight()), Float.parseFloat(letterHead.getPaddingTop()),
						Float.parseFloat(letterHead.getPaddingBottom()));
			}
			  PdfWriter writer = PdfWriter.getInstance(document, byteArray);
			    document.open();
			    
			    
			    PdfPTable header = new PdfPTable(1);
			       header.setWidths(new int[]{2});
			       header.setTotalWidth(527);
			       header.setLockedWidth(true);
			       header.getDefaultCell().setFixedHeight(40);
			       header.getDefaultCell().setBorder(Rectangle.BOTTOM);
			       header.getDefaultCell().setBorderColor(BaseColor.BLUE);
			       header.addCell(getCell(letterHead));
			       document.add(header);
			       
			       Paragraph p = new Paragraph(letterHead.getHeaderText(),new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL));
			       p.setAlignment(2);
			       document.add(p);

			       PdfPTable footer = new PdfPTable(1);
			       // set defaults
					footer.setWidths(new int[]{2});
					footer.setTotalWidth(527);
					footer.setLockedWidth(true);
					footer.getDefaultCell().setFixedHeight(40);
					footer.getDefaultCell().setBorder(Rectangle.TOP);
					footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
			   	
					PdfPCell footerCell = new PdfPCell();
					Paragraph p1 = new Paragraph(letterHead.getFooterText(),new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
					if("Left".equalsIgnoreCase(letterHead.getAlignFooterText())) {
				    	p1.setAlignment(Element.ALIGN_LEFT);
				    }else if("Right".equalsIgnoreCase(letterHead.getAlignFooterText())){
				    	p1.setAlignment(Element.ALIGN_RIGHT);
				    }else {
				    	p1.setAlignment(Element.ALIGN_CENTER);
				    }
							
					if(letterHead.getFooterBgColor() != null && letterHead.getFooterBgColor() != "") {
				    	BaseColor myColor = WebColors.getRGBColor(letterHead.getFooterBgColor());
				    	footerCell.setBackgroundColor(myColor);
				    }
					footerCell.addElement(p1);
			     	footer.addCell(footerCell).setPaddingTop(-4);
					
		   	
				   	// add placeholder for total page count
				   	PdfPCell totalPageCount = new PdfPCell();
				   	totalPageCount.setBorder(Rectangle.TOP);
				   	totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
				   	footer.addCell(totalPageCount);
				   	
				   	// write page
				   	PdfContentByte canvas = writer.getDirectContent();
				   	canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
				   	footer.writeSelectedRows(0, -1, 34, 50, canvas);
				   	canvas.endMarkedContentSequence();
				   	
				    XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(finalString));
				    document.close();
			} else {
			   document = new Document();
			    PdfWriter.getInstance(document, byteArray);
		  	    document.open();
		  	    HTMLWorker htmlWorker = new HTMLWorker(document);
		  	    htmlWorker.parse(new StringReader(templateContent));
		  	    document.close();
		}
	    
	   	byte[] pdfBytes = byteArray.toByteArray();
	   	response.setHeader("Content-disposition","inline; filename=Letterhead.pdf");
	   	response.setContentType("application/pdf");
	   	response.setContentLength(pdfBytes.length);
	     ServletOutputStream sos = response.getOutputStream();
	     sos.write(pdfBytes);
	     sos.flush();
	     sos.close();
		
	}
}