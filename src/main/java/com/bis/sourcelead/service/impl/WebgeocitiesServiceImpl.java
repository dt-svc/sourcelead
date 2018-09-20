package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CountriesDao;
import com.bis.sourcelead.dao.StatesDao;
import com.bis.sourcelead.dao.WebgeocitiesDao;
import com.bis.sourcelead.entity.Countries;
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
	

	@Override
	public List<Webgeocities> getAllCitiesByStateAndCountry(String stateName, String countryName) throws Exception {
		Countries countries = countriesDao.getCountriesByCountryName(countryName);
		List<Webgeocities> citiesList = null;
		if(null != countries){
		List<String> stateCodes = statesDao.getStatesCodesByStateName(stateName);
		citiesList = webgeocitiesDao.getAllCitiesByStateCodeCountryCode(stateCodes, countries.getCode2());
		}
		return citiesList;
	}


	@Override
	public List<Webgeocities> getAllCitiesByCountryCode(String countryCode) throws Exception {
		return webgeocitiesDao.getAllCitiesByCountryCode(countryCode);
		
	}



}
