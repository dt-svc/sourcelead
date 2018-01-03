package com.bis.sourcelead.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.dao.ApplicantsBySourceDao;
import com.bis.sourcelead.service.TimeZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReportsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportsUtil.class);
	
	@Autowired
	ApplicantsBySourceDao applicantsBySourceDao;
	
	@Autowired
	private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	
     public List<RequirementDTO> getAllRequirementsByOrgLocationId(Long orgLocationId,User user,String userRole,HttpServletRequest request){
		
		List<RequirementDTO> requirementDTOList = new ArrayList<>();
		List<Long> requirementIdsList = new ArrayList<>();
		
		try {
			
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			boolQueryBuilder.must(QueryBuilders.matchQuery("orgLocationId", orgLocationId));
			
			if(userRole != null&& !("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole))){
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
						requirementDTOList.add(requirementDTO);
					}
				}
				
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -14);    
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
		logger.info("From Date : " + dates[0]+" / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 15 days
	 * @return Date[]  Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last15DaysDates(OrgLocations orgLocations) throws Exception {
		/*Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.DATE, -15);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);*/
		
		
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
		logger.info("From Date : " + dates[0]+" / To Date : " + dates[1]);
		
		return dates;
	}
	
	/**
	 * Get from and to date of last 1 Month
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 */
	public Date[] last1MonthDates(OrgLocations orgLocations) throws Exception {
		/*Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.MONTH, -1);
		dates[0] = cal.getTime();*/
		
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
		
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
		return dates;
	}
	
	/**
	 * Get from and to date of last 3 Months
	 * 
	 * @return Date[] Date[0] = start date ,Date[1] = End date
	 * @throws Exception 
	 */
	public Date[] last3MonthDates(OrgLocations orgLocations) throws Exception {
		/*Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[1] = cal.getTime();
		cal.add(Calendar.MONTH, -3);
		dates[0] = cal.getTime();
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);*/
		
		
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
		
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
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
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
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
		logger.info("From Date : " + dates[0] + " / To Date : " + dates[1]);
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

	public    Date[] tenDays(int days) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date[] dates = new Date[2];
		Calendar cal = Calendar.getInstance();
		dates[0] = sdf.parse(sdf.format(cal.getTime()));
		cal.add(Calendar.DATE, +days);
		dates[1] = sdf.parse(sdf.format(cal.getTime()));
		logger.info("From Date : " + dates[0]+" / To Date : " + dates[1]);
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
		logger.info("From Date : " + dates[0]+" / To Date : " + dates[1]);
		return dates;
	}
	

	public  Date above365Days() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -366);
		Date date = sdf.parse(sdf.format(cal.getTime()));
		return date;
	}

	public Date[] getCustomDates(String fromDate, String toDate) throws ParseException {
		   Date[]  dates = new Date[2];
		   dates[0] =new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);  
		   dates[1] =new SimpleDateFormat("yyyy-MM-dd").parse(toDate);  
		return dates;
	}

	
	
}