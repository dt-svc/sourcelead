package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;

public interface RestrictDayDao {

	public RestrictDay storeRestrictedDay(RestrictDay restrictDay) throws Exception;

	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId);
	
	public RestrictDay getRestrictDayById(String restrictDayId);
	
	public List<RestrictDay> validateRestrictDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception;
	
}
