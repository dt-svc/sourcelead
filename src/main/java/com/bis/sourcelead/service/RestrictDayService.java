package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.RestrictDayDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;

public interface RestrictDayService {

	public RestrictDay storeRestrictedDay(RestrictDayDTO restrictDayDTO) throws Exception;

	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId) throws Exception;

	public RestrictDay getRestrictDayById(Long restrictDayId);

	public List<RestrictDay> validateRestrictDayDates(String fromDate, String toDate, OrgLocations orgLocations) throws Exception;

	public boolean checkDuplicateRestrictDay(RestrictDayDTO restrictDayDTO) throws Exception;

	public RestrictDay getRestrictDayByUUID(String restrictUUID);
}
