package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.RestrictDayDTO;
import com.bis.sourcelead.entity.RestrictDay;

public interface RestrictDayService {

	public RestrictDay storeRestrictedDay(RestrictDayDTO restrictDayDTO) throws Exception;

	public List<RestrictDay> getRestrictDayListByOrgLocationId(Long orgLocationId) throws Exception;

	public RestrictDay getRestrictDayByUUID(String restrictUUID);
}
