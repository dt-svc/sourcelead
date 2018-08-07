package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CountriesDao;
import com.bis.sourcelead.dao.StatesDao;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.service.StatesService;

@Service
public class StatesServiceImpl implements StatesService {

	@Autowired
	CountriesDao countriesDao;

	@Autowired
	StatesDao statesDao;

	@Override
	public List<States> getAllStatesByCountry(String countryName) throws Exception {
		Countries countries = countriesDao.getCountriesByCountryName(countryName);
		List<States> statesList = null;
		if (null != countries) {
			statesList = statesDao.getAllStatesByCountryCode(countries.getCode2());
		}
		return statesList;
	}

	@Override
	public States getStateById(long stateId) {
		try {
			return statesDao.getStateById(stateId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getStateCodeByCountryCodeAndState(String countryCode, String state)
	{
		return statesDao.getStateCodeByCountryCodeAndState(countryCode,state);
	}


}