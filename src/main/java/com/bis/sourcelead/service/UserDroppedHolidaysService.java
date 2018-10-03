package com.bis.sourcelead.service;


import java.util.List;

import com.bis.sourcelead.entity.UserDroppedHolidays;
public interface UserDroppedHolidaysService  {
	
	public UserDroppedHolidays addUserDroppedHolidays(UserDroppedHolidays userDroppedHolidays) throws Exception;
	
	public UserDroppedHolidays getUserDroppedHolidaysFromUserIdlocationIdAndHolidayId(Long userId,Long locationId,Long holidayId) throws Exception;
	
	public List<Long> getDroppedHolidayIdsListByUserIdAndLocationId(Long userId,Long locationId) throws Exception;
	
}	

