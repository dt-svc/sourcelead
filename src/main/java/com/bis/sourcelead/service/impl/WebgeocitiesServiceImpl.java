package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CountriesDao;
import com.bis.sourcelead.dao.StatesDao;
import com.bis.sourcelead.dao.WebgeocitiesDao;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.Webgeocities;
import com.bis.sourcelead.service.WebgeocitiesService;

/**
 * 
 * @author Prasad Salina
 *
 */
@Service
public class WebgeocitiesServiceImpl implements WebgeocitiesService{
	
	@Autowired
	CountriesDao countriesDao;
	
	@Autowired
	StatesDao statesDao;
	
	@Autowired
	WebgeocitiesDao webgeocitiesDao;
	
	private static final Logger logger = LoggerFactory.getLogger(WebgeocitiesServiceImpl.class);

	@Override
	public List<Webgeocities> getAllCitiesByStateAndCountry(String stateName, String countryName) throws Exception {
		Countries countries = countriesDao.getCountriesByCountryName(countryName);
		List<String> stateCodes = statesDao.getStatesCodesByStateName(stateName);
		List<Webgeocities> citiesList = webgeocitiesDao.getAllCitiesByStateCodeCountryCode(stateCodes, countries.getCode2());
		return citiesList;
	}



}
