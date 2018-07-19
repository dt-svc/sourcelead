package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class LeaveManagementReportsRestController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@RequestMapping(value = "/daywiseLeaveReport")
	public ModelAndView approvedLeavesJsp(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/daywiseLeaveReport");
		return model;
	}
	
	@RequestMapping(value = "/leaveBalancesReport")
	public ModelAndView leaveBalances(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("reports/leaveBalancesReports");
		return model;
	}
	
	
	
	/*@RequestMapping(value = "/getDaywiseLeaveReportList", method = RequestMethod.GET)
	public @ResponseBody  ResponseList<UserDTO> getDaywiseLeaveReportList(@RequestParam(value="fromDate", required=false) String fromDate,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<UserDTO> userDtoList =  new ArrayList<>();
		ResponseList<UserDTO> employeeResponseList =  new ResponseList<>();
		Date[] searchDates = null;
		if(StringUtils.isNotBlank(fromDate)) {
			Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), fromDate);
    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), userDTO.getToDate());
    	    Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
    	    fromDate = TZ_DF_TS.parse(startDateString);
    	    toDate = TZ_DF_TS.parse(endDateString);
    	    searchDates = new Date[2];
    	    searchDates[0] = fromDate;
    	    searchDates[1] = toDate;
		}
		return employeeResponseList;
}*/
}
