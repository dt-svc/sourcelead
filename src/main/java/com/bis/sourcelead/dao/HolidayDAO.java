package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;

public interface HolidayDAO {

	 Holiday storeHoliday(Holiday holiday)throws Exception;

	List<Holiday> getAllHolidaysList(Long id);
	
	List<Holiday> getAllHolidaysListBasedOnYear(Long id,Long reportYear);
	
	Holiday getHolidaysById(String holidayId)throws Exception;
	
	Holiday saveDeleteHoliday(Holiday holidays) throws Exception;

	List<Holiday> validateHoliDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception;

	List<Holiday> getHolidaybyLocationIdAndFromdate(Date fromDate,  Date endDate,Long orgLocationId)throws Exception;

	Holiday getHolidaysByUUID(String holidayUUID);

	List<ProjectTasks> getHolidayProjectTaskByorgLocationIdAndHolidayId(Long orglocationId, Long holidayId);

	List<Holiday> getAllHoliday();

	
	
}
