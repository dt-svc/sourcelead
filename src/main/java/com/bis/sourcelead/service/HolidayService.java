package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.OrgLocations;

public interface HolidayService {
	

	public Holiday storeHoliday(HolidayDTO holidayDTO) throws Exception;

	public List<HolidayDTO> getAllHolidaysList(Long orgLocationId) throws Exception;
	
	Holiday getHolidaysById(String holidayId)throws Exception;

	public List<Holiday> validateHoliDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception;

	

}
