package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.HolidayDTO;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.HolidayShift;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;

public interface HolidayService {
	

	public Holiday storeHoliday(HolidayDTO holidayDTO) throws Exception;

	public List<HolidayDTO> getAllHolidaysList(Long orgLocationId) throws Exception;
	
	public List<HolidayDTO> getAllHolidaysListBasedOnYear(Long orgLocationId,Long reportYear) throws Exception;
	
	Holiday getHolidaysById(String holidayId)throws Exception;

	public List<Holiday> validateHoliDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception;

	public List<Holiday> getHolidaybyLocationIdAndFromdate(Date fromDate,Date endDate,Long orgLocationId)throws Exception;

	public Holiday getHolidaysByUUID(String holidayUUID);

	public List<ProjectTasks> getHolidayProjectTaskByorgLocationIdAndHolidayId(Long orglocationId, Long holidayId);

	public List<Holiday> getAllHoliday();
	
	List<HolidayDTO> getAllHolidaysListByOrgloationIdAndShiftId(Long locationId,String shiftIds,String reportYear)throws Exception;

	public List<HolidayShift> getHolidayShiftListByHolidayId(Long holidayId) throws Exception;
		
	List<Long> getHolidayShiftCout(Long holidayId)throws Exception;

	void deleteHolidayByHolidayId(Long holidayId)throws Exception;

	public List<HolidayAndRestrictday> getHolidayByFromToDate(Date startDate, Date endDate, OrgLocations orgLocations);

}
