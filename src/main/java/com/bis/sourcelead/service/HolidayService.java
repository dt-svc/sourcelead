package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.OrgLocations;

public interface HolidayService {
	

	public void storeHoliday(HolidayDTO holidayDTO) throws Exception;

	public List<Holiday> getAllHolidaysList(OrgLocations orgLoc);

	

}
