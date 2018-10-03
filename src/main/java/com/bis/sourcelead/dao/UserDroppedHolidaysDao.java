package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.UserDroppedHolidays;

public interface UserDroppedHolidaysDao {
	
	public UserDroppedHolidays addUserDroppedHolidays(UserDroppedHolidays userDroppedHolidays) throws Exception;
	
	public List<Long> getDroppedHolidayIdsListByUserIdAndLocationId(Long userId,Long locationId) throws Exception;
}