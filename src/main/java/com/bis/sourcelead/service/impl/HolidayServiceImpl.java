package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Service
public class HolidayServiceImpl implements HolidayService{

	@Autowired
	HolidayDAO holidayDAO;
	
	@Autowired
	CommonUtil commonUtil;


	@Override
	public void storeHoliday(HolidayDTO holidayDTO) throws Exception {

		Holiday holiday = new Holiday();
		if(holidayDTO != null)
		{
			if(holidayDTO.getOrgLocationId() != null)
			{
				holiday.setOrgLocationId(holidayDTO.getOrgLocationId().longValue());
			}else{
				holiday.setHolidayName("--");
			}
			if(StringUtils.isNotBlank(holidayDTO.getHolidayName()))
			{
				holiday.setHolidayName(holidayDTO.getHolidayName());
			}else{
				holiday.setHolidayName("--");
			}
			if(holidayDTO.getFromDate() != null)
			{
				holiday.setFromDate(commonUtil.getDateFormat(holidayDTO.getFromDate()));
			}else{
				holiday.setFromDate(null);
			}
			if(holidayDTO.getToDate() != null)
			{
				holiday.setToDate(commonUtil.getDateFormat(holidayDTO.getToDate()));
			}else{
				holiday.setToDate(null);
			}
			if(StringUtils.isNotBlank(holidayDTO.getHalfDayCheckBox()))
			{
				holiday.setHalfDay(holidayDTO.getHalfDayCheckBox());
			}else{
				holiday.setHalfDay("--");
			}
			if(StringUtils.isNotBlank(holidayDTO.getDescription()))
			{
				holiday.setDescription(holidayDTO.getDescription());
			}else{
				holiday.setDescription("--");
			}
			if(StringUtils.isNotBlank(holidayDTO.getHolidayStatus()))
			{
				holiday.setHolidayStatus(holidayDTO.getHolidayStatus());
			}else{
				holiday.setHolidayStatus("--");
			}
			
		}
		holidayDAO.addHoliday(holiday);
	}

	@Override
	public List<Holiday> getAllHolidaysList(OrgLocations orgLoc) {

		List<Holiday> holidaysList =  holidayDAO.getAllHolidaysList(orgLoc.getId());
		return holidaysList;
	}
	
	
}
