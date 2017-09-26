package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.HolidayDAO;
import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.HolidayService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;

@Service
public class HolidayServiceImpl implements HolidayService{

	@Autowired
	HolidayDAO holidayDAO;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	UserService userService;


	@Override
	public Holiday storeHoliday(HolidayDTO holidayDTO) throws Exception {
		Holiday holiday = null;
		OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.parseLong(holidayDTO.getOrgLocationId()));
		User user = userService.getUserById(Long.parseLong(holidayDTO.getUserId()));
		if(StringUtils.isNotBlank(holidayDTO.getHolidayId())){
			holiday = holidayDAO.getHolidaysById(holidayDTO.getHolidayId());
			holiday.setUpdatedBy(user.getUserCode());
			holiday.setUpdatedDate(new Date());
		}else{
			holiday = new Holiday();
			holiday.setCreatedDate(new Date());
			holiday.setCreatedBy(user.getUserCode());
		}
		if(holidayDTO != null)
		{
			if(holidayDTO.getOrgLocationId() != null)
			{
				holiday.setOrgLocations(orgLocations);
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
				holiday.setHalfDayCheckBox(holidayDTO.getHalfDayCheckBox());
				holiday.setHalfDayAmPm(holidayDTO.getHalfDayAmPm());
			}else{
				holiday.setHalfDayCheckBox("--");
				holiday.setHalfDayAmPm("--");
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
			
			holiday.setStatus("1");
			
		}
		return holidayDAO.storeHoliday(holiday);
	}

	@Override
	public List<HolidayDTO> getAllHolidaysList(Long orgLocationId) {
		List<HolidayDTO> holidayDTOList = new ArrayList<HolidayDTO>();
		List<Holiday> holidaysList =  holidayDAO.getAllHolidaysList(orgLocationId);
		for(Holiday hlist : holidaysList)
		{
			HolidayDTO hDTO = new HolidayDTO();
		    hDTO.setId(hlist.getId());
			if(StringUtils.isNotBlank(hlist.getHolidayName()))
			hDTO.setHolidayName(hlist.getHolidayName());
			if(hlist.getFromDate() != null){
				hDTO.setFromDate(ConversionUtil.getDateAsString(hlist.getFromDate()));
				hDTO.setDayOfTheWeek(Constants.WEEK_DAY.format(hlist.getFromDate()));
			}
			if(hlist.getToDate() != null)
			hDTO.setToDate(ConversionUtil.getDateAsString(hlist.getToDate()));
			if(StringUtils.isNotBlank(hlist.getHalfDayCheckBox()))
			hDTO.setHalfDayCheckBox(hlist.getHalfDayCheckBox());
			if(StringUtils.isNotBlank(hlist.getHalfDayAmPm()))
			hDTO.setHalfDayAmPm(hlist.getHalfDayAmPm());
			if(StringUtils.isNotBlank(hlist.getDescription()))
		    hDTO.setDescription(hlist.getDescription());
			holidayDTOList.add(hDTO);
		}
		return holidayDTOList;
	}
	
	@Override
	public Holiday getHolidaysById(String holidayId) throws Exception {
		return holidayDAO.getHolidaysById(holidayId);
	}
	
	
}
