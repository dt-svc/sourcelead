package com.bis.sourcelead.dumpRest;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AppliedLeaveDateDao;
import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.util.LeaveManagementUtil;

@Component
@RequestMapping("/app")
public class DumpPublicRestCalls {
	
	private static final Logger logger = LoggerFactory.getLogger(DumpPublicRestCalls.class);
	
	@Autowired
	private LeaveService leaveService;
	
	@Autowired
	private LeaveManagementUtil leaveManagementUtil;
	
	@Autowired
	private AppliedLeaveDateDao appliedLeaveDateDao;
	
	@Autowired
	HolidayDAO holidayDao;
	
	@RequestMapping(value = "/ws/dumpAllAppliedLeaveDatesData", method = RequestMethod.GET)
	@ResponseBody
	public List<AppliedLeaveDate> dumpAllAppliedLeaveDatesData() {
		
		logger.info("Start Time"+java.time.LocalDateTime.now());
		List<AppliedLeaveDate> list = new ArrayList<>();
		List<Leave> leavesList = leaveService.getAllLeave();
		leavesList.forEach(leave -> {
			boolean existed = appliedLeaveDateDao.checkForExistedLeave(leave);
			if(!existed) {
				  list.add(getAppliedLeaveDateSet(leave));
			}
		});
		logger.info("End Time"+java.time.LocalDateTime.now());
		return list;
		
	}
	
	private AppliedLeaveDate getAppliedLeaveDateSet(Leave leave)
	{
		Set<AppliedLeaveDate> set = new HashSet<>();
		AppliedLeaveDate apld = null;
		try {
			 Calendar cal = Calendar.getInstance();
			List<Date> dateList = leaveManagementUtil.getDaysBetweenLeaveDates(leave.getFromDate(), leave.getToDate());
			for(Date d : dateList) {
				AppliedLeaveDate appliedLeaveDate = new AppliedLeaveDate();
				appliedLeaveDate.setApplyLeaveDate(d);
				appliedLeaveDate.setApplyMonthName(getMonthName(d).toUpperCase());
				appliedLeaveDate.setLeave(leave);
				appliedLeaveDate.setIsPayrollInclude(Constants.STATUS_NO);
//				appliedLeaveDate.setCreatedBy(user.getId());
				appliedLeaveDate.setCreatedDate(new Date());
//				appliedLeaveDate.setUpdatedBy(user.getId());
				appliedLeaveDate.setUpdatedDate(new Date());
				appliedLeaveDate.setYear(cal.get(Calendar.YEAR));
				set.add(appliedLeaveDate);
				apld = appliedLeaveDateDao.saveAppliedLeaveDates(appliedLeaveDate);
			}
			return apld;
		} catch (Exception e) {
			logger.error("AppliedLeaveDate failed due to " + e.getMessage());
		}
		return apld;
	}
	
	private String getMonthName(Date date) {
		  Format formatter = new SimpleDateFormat("MMM"); 
		    String monthName = formatter.format(date);
		    return monthName;
	}

	@RequestMapping(value = "/ws/dumpOrgAvgHrsForAllHolidays", method = RequestMethod.GET)
	@ResponseBody
	public List<Holiday> dumpOrgAvgHrsForAllHolidays() throws Exception {
		logger.info("Start Time"+java.time.LocalDateTime.now());
		List<Holiday> list = holidayDao.getAllHoliday();
		for(Holiday day : list){
			day.setOrgAvgHrs(day.getOrgLocations().getAverageWorkingHours());
			holidayDao.storeHoliday(day);
		}
		logger.info("End Time"+java.time.LocalDateTime.now());
		return list;
	}

}
