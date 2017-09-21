package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Holiday;

public interface HolidayDAO {

	 Holiday addHoliday(Holiday holiday)throws Exception;

	List<Holiday> getAllHolidaysList(Long id);
	
}
