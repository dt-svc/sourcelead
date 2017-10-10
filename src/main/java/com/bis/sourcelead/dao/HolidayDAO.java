package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.OrgLocations;

public interface HolidayDAO {

	 Holiday storeHoliday(Holiday holiday)throws Exception;

	List<Holiday> getAllHolidaysList(Long id);
	
	Holiday getHolidaysById(String holidayId)throws Exception;
	
	Holiday saveDeleteHoliday(Holiday holidays) throws Exception;

	List<Holiday> validateHoliDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception;
	
}
