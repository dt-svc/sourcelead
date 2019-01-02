package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dao.PayrollScheduleDao;
import com.bis.sourcelead.payroll.dto.AssociateBankDataDTO;
import com.bis.sourcelead.payroll.dto.PayrollScheduleCalenderDTO;
import com.bis.sourcelead.payroll.dto.PayrollScheduleDTO;
import com.bis.sourcelead.payroll.entity.AssociateBankData;
import com.bis.sourcelead.payroll.entity.USPayScheduleDate;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;
import com.bis.sourcelead.payroll.service.PayrollScheduleService;
import com.bis.sourcelead.payroll.util.PayrollUtil;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PayrollScheduleServiceImpl implements PayrollScheduleService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollScheduleServiceImpl.class);
	
	@Autowired
	PayrollScheduleDao payrollScheduleDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PayrollUtil payrollUtil;

	@Override
	public Response<PayrollScheduleDTO> savePayrollSchedule(PayrollScheduleDTO payrollScheduleDTO,OrgLocations orgLocations,User loginUser) {
		Response<PayrollScheduleDTO> resObj = new Response<>();
		try {
			if (Validator.validate(payrollScheduleDTO)) {
				UsPayrollSchedule usPayrollSchedule = null;
				if(StringUtils.isNotBlank(payrollScheduleDTO.getPayrollScheduleUUID())) {
					usPayrollSchedule = payrollScheduleDao.getPayScheduleByUUID(payrollScheduleDTO.getPayrollScheduleUUID(), orgLocations.getId());
				}else {
					usPayrollSchedule = new UsPayrollSchedule();
				}
				usPayrollSchedule.setOrgLocation(orgLocations);
				if(StringUtils.isNotBlank(payrollScheduleDTO.getPayPeriodStartDate())) {
					usPayrollSchedule.setPayPeriodStartDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollScheduleDTO.getPayPeriodStartDate()));
				}
				if(StringUtils.isNotBlank(payrollScheduleDTO.getNextPayMonth())) {
					usPayrollSchedule.setNextPayMonth(payrollScheduleDTO.getNextPayMonth());
				}
				usPayrollSchedule.setPayCheckDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollScheduleDTO.getPayCheckDate()));
				usPayrollSchedule.setRunPayrollDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollScheduleDTO.getRunPayDate()));
				usPayrollSchedule.setStatus(1);
				usPayrollSchedule.setCreatedBy(loginUser.getId());
				usPayrollSchedule.setUpdatedBy(loginUser.getId());
				usPayrollSchedule.setPayFrequency(payrollScheduleDTO.getPayFrequency());
				usPayrollSchedule.setCreatedDate(new Date());
				usPayrollSchedule.setUpdatedDate(new Date());
				usPayrollSchedule.setPayrollScheduleUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				
				if(payrollScheduleDTO.getNotifyMember() != null) {
					usPayrollSchedule.setNotifyMember(payrollScheduleDTO.getNotifyMember());
				}else {
					usPayrollSchedule.setNotifyMember("0");
				}
				
				List<PayrollScheduleCalenderDTO> dates  = payrollUtil.getPayScheduleWeeksByDate(usPayrollSchedule,orgLocations);
				Set<USPayScheduleDate> set = new HashSet<>();
				for(PayrollScheduleCalenderDTO dto : dates) {
					USPayScheduleDate  uSPayScheduleDate = new USPayScheduleDate(); 
					uSPayScheduleDate.setCheckDate(dto.getCheckDate());
					uSPayScheduleDate.setPayRunDate(dto.getRunDate());
					uSPayScheduleDate.setPayPeriodStartDate(dto.getPayPeriodStartDate());
					uSPayScheduleDate.setPayPeriodEndDate(dto.getPayPeriodEndDate());
					uSPayScheduleDate.setScheduleId(usPayrollSchedule);
					uSPayScheduleDate.setStatus(1);
					uSPayScheduleDate.setCreatedDate(new Date());
					uSPayScheduleDate.setUpdatedDate(new Date());
					uSPayScheduleDate.setCreatedBy(loginUser.getId());
					uSPayScheduleDate.setUpdatedBy(loginUser.getId());
					set.add(uSPayScheduleDate);
				}
				usPayrollSchedule.setScheduleDates(set);	
				UsPayrollSchedule savedUsPayrollSchedule = payrollScheduleDao.savePayrollSchedule(usPayrollSchedule, orgLocations);
				if (savedUsPayrollSchedule != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					payrollScheduleDTO.setId(savedUsPayrollSchedule.getId());
					resObj.setObject(payrollScheduleDTO);
				}
			}
			else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return resObj;
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

	@Override
	public List<PayrollScheduleDTO> findAllPayrollScheduleList(Long orgLocationId,String orgDateFormat)
	{
		List<PayrollScheduleDTO> schedulelist = new ArrayList<>();
		try {
			List<UsPayrollSchedule> payrollScheduleList = payrollScheduleDao.findAllPayrollScheduleList(orgLocationId);
			for(UsPayrollSchedule schedule : payrollScheduleList) {
				PayrollScheduleDTO scheduleDTO = new PayrollScheduleDTO();
				scheduleDTO.setId(schedule.getId());
				scheduleDTO.setPayFrequency(schedule.getPayFrequency());
				scheduleDTO.setPayCheckDate(commonUtil.dateStrFromOrgDateFormatter(orgDateFormat, schedule.getPayCheckDate()));
				if(schedule.getPayPeriodStartDate() != null) {
					scheduleDTO.setPayPeriodStartDate(commonUtil.dateStrFromOrgDateFormatter(orgDateFormat, schedule.getPayPeriodStartDate()));
				}
				if(StringUtils.isNotBlank(schedule.getNextPayMonth())) {
					scheduleDTO.setNextPayMonth(schedule.getNextPayMonth());
				}
				scheduleDTO.setRunPayDate(commonUtil.dateStrFromOrgDateFormatter(orgDateFormat, schedule.getRunPayrollDate()));
				scheduleDTO.setPayrollScheduleUUID(schedule.getPayrollScheduleUUID());
				schedulelist.add(scheduleDTO);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return schedulelist;
	}

	@Override
	public UsPayrollSchedule getPayScheduleByUUID(String payrollScheduleUUID,Long orgLocationId)
	{
		return payrollScheduleDao.getPayScheduleByUUID(payrollScheduleUUID,orgLocationId);
	}

	@Override
	public UsPayrollSchedule findScheduleByFrequency(String payFrequency, Long orgLocationId,String scheduleUUID)
	{
		return payrollScheduleDao.findScheduleByFrequency(payFrequency,orgLocationId,scheduleUUID);
	}
	@Override
	public List<UsPayrollSchedule> getAllPayrollScheduleByOrgLocationId(Long orgLocationId) {
		return payrollScheduleDao.getAllPayrollScheduleByOrgLocationId(orgLocationId);
	}

}
