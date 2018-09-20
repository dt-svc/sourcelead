package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.PayrollScheduleDao;
import com.bis.sourcelead.payroll.dto.PayrollScheduleDTO;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;
import com.bis.sourcelead.payroll.service.PayrollScheduleService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class PayrollScheduleServiceImpl implements PayrollScheduleService {
	
	@Autowired
	PayrollScheduleDao payrollScheduleDao;
	
	@Autowired
	CommonUtil commonUtil;

	@Override
	public UsPayrollSchedule savePayrollSchedule(PayrollScheduleDTO payrollScheduleDTO,
			OrgLocations orgLocations,User loginUser) {
		
		UsPayrollSchedule usPayrollSchedule = new UsPayrollSchedule();

		usPayrollSchedule.setOrgLocation(orgLocations);
		usPayrollSchedule.setPayScheduleDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollScheduleDTO.getPayScheduleDate()));
		usPayrollSchedule.setPayScheduleType(payrollScheduleDTO.getPayScheduleType());
		usPayrollSchedule.setStatus(1);
		usPayrollSchedule.setCreatedBy(loginUser.getId());
		usPayrollSchedule.setUpdatedBy(loginUser.getId());
		usPayrollSchedule.setCreatedDate(new Date());
		usPayrollSchedule.setUpdatedDate(new Date());
		
		
		return payrollScheduleDao.savePayrollSchedule(usPayrollSchedule, orgLocations);
	}

	@Override
	public List<String> getWeeklyFrequenciesBySelectedDate(String selectedDate, String orgLocDateFormat,String dateFrequency) throws Exception {
		
		Date select = commonUtil.getDateFormat(selectedDate);
		Date currentDate;
		Calendar c = Calendar.getInstance();
		currentDate = c.getTime();
		Date temp1, temp2;
		String tempStr1 = "", tempStr2 = "";
		c.setTime(select);
		
		List<String> datesList = new ArrayList<>();
		for(int i = 0; i < 52; i++) {
			if(datesList.size() < 4) {
				Date temp = c.getTime();
				if(Constants.WEEKLY.equalsIgnoreCase(dateFrequency)) {
				c.add(Calendar.DATE, 6);
				}else if(Constants.BI_WEEKLY.equalsIgnoreCase(dateFrequency)) {
					c.add(Calendar.DATE, 13);
				}else if(Constants.MONTHLY.equalsIgnoreCase(dateFrequency)) {
					c.add(Calendar.DATE, 29);
				}else if(Constants.SEMI_MONTHLY.equalsIgnoreCase(dateFrequency)) {
					c.add(Calendar.DATE, 14);
				}
//				temp1 = commonUtil.getOrgLocationCurrentDate(orgLocDateFormat,temp);
				tempStr1 = commonUtil.dateStrFromOrgDateFormatter(orgLocDateFormat,temp);
				temp2 = commonUtil.getOrgLocationCurrentDate(orgLocDateFormat,c.getTime());
				tempStr2 = commonUtil.dateStrFromOrgDateFormatter(orgLocDateFormat,c.getTime());
				if(!currentDate.after(temp2)) {
					StringBuilder sb = new StringBuilder();
					sb.append(tempStr1).append(" - ").append(tempStr2);
					datesList.add(sb.toString());
				}
				c.add(Calendar.DATE, 1);
			}else {
				break;
			}
		}
		return datesList;
	}

	@Override
	public UsPayrollSchedule getWeeklyFrequenciesBySelectedDate(String dateFrequency,OrgLocations orgLocations) throws Exception {
		
		return payrollScheduleDao.getWeeklyFrequenciesBySelectedDate(dateFrequency,orgLocations);
	}

}
