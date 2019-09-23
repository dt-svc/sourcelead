package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.HolidayShift;
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

	List<Holiday> getAllHolidaysListByOrgloationIdAndShiftId(Long locationId,String shiftIds,String reportYear );
	
	List<Long> getHolidayShiftCout(Long holidayId)throws Exception;
	
	public List<HolidayShift> getHolidayShiftListByHolidayId(Long holidayId) throws Exception ;
	
	void removeHolidayShiftList(List<Long> shiftIds,Long holidayId)throws Exception ;

	void deleteHolidayByHolidayId(Long holidayId)throws Exception;

	List<HolidayAndRestrictday> getHolidayByFromToDate(Date startDate, Date endDate, OrgLocations orgLocations);
	
	public List<Holiday> getHolidaybyLocationIdAndFromdateForTimesheets(Date startDate, Date endDate, Long orgLocationId,Long userId,Long workLocationId) throws Exception;

	
}
