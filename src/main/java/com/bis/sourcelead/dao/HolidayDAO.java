package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Option;

public interface HolidayDAO {

	 Holiday storeHoliday(Holiday holiday)throws Exception;

	List<Holiday> getAllHolidaysList(Long id);
	
	Holiday getHolidaysById(String holidayId)throws Exception;
	
	Holiday saveDeleteHoliday(Holiday holidays)throws Exception;
	
}
