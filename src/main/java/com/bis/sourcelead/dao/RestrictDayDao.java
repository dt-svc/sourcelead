package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.RestrictDay;

public interface RestrictDayDao {

	public RestrictDay storeRestrictedDay(RestrictDay restrictDay) throws Exception;

	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId);
	
	public RestrictDay getRestrictDayByUUID(String restrictUUID);
	
	public List<RestrictDay> validateRestrictDayDates(String fromDate, String toDate, Long orgLocationId) throws Exception;
	
}
