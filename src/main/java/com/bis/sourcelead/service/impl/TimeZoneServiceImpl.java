package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CitiesDao;
import com.bis.sourcelead.dao.TimeZoneDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.CitiesService;
import com.bis.sourcelead.service.TimeZoneService;

@Service
public class TimeZoneServiceImpl implements TimeZoneService {

	@Autowired
	TimeZoneDao timeZoneDao;
	
	
	@Override
	public List<Timezone> getAllTimeZones() throws Exception {
		return timeZoneDao.getAllTimeZones();
	}


	@Override
	public Timezone getTimezone(Long id) throws Exception {
		Timezone timezone = timeZoneDao.getTimezone(id); 
		return timezone;
	}




}
