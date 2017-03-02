package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementReportService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * 
 * @author root
 * @since 28-02-2017
 * Handles reports for Requirement
 */
@RestController
public class RequirementReportRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementReportRestController.class);
	   
	 @Autowired
	 RequirementService requirementService;
	
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Autowired
	 RequirementReportService requirementReportService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	
	 @RequestMapping(value = "/getRequirementReports")
	 public String getRequirementListByOrgLocationID(@RequestParam(value="reportType", required=false) String reportType,HttpServletRequest request,HttpServletResponse response) throws Exception{

		 String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		    OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
			DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			String currentDateInTimezone = outputFormatter.print(zoned);
			
			DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
			
			Date startTime =  commonUtil.getDateFormat(currentDateInTimezone);
			String startTimeTS = Constants.TZ_DF.format(startTime); 
			String endTimeTS = END_TZ_DF.format(startTime);
			String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),"UTC", startTimeTS);
			
			
			
			String filterDate = null;
			if(null != reportType && !"".equals(reportType)){
				if("today".equals(reportType)){
					filterDate = currentDateInTimezone;
				}else if("yesterday".equals(reportType)){
					DateTime yesterDayZoned = zoned.minusDays(1);
					filterDate = outputFormatter.print(yesterDayZoned);
				}else if("week".equals(reportType)){
					DateTime weekZoned = zoned.minusDays(7);
					filterDate = outputFormatter.print(weekZoned);
				}else if("twoweeks".equals(reportType)){
					DateTime twoWeekZoned = zoned.minusDays(14);
					filterDate = outputFormatter.print(twoWeekZoned);
				}else if("month".equals(reportType)){
					DateTime month = zoned.minusDays(30);
					filterDate = outputFormatter.print(month);
				}else if("twomonths".equals(reportType)){
					DateTime month = zoned.minusDays(60);
					filterDate = outputFormatter.print(month);
				}else{
					
				}
			}
			List<Requirement> requirementList = requirementReportService.getRequirementReports(userRoleName,orgLocation,user,filterDate);
		 
			return null;
		 
	 }
}
