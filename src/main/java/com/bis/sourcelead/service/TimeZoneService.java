package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Timezone;

public interface TimeZoneService {

	 public Timezone getTimezone(Long id)throws Exception;
	 
    public List<Timezone> getAllTimeZones()throws Exception; 
}