package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Timezone;

public interface TimeZoneDao {

	public Timezone getTimezone(Long id)throws Exception;
	
    public List<Timezone> getAllTimeZones()throws Exception;

}