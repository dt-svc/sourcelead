package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CitiesDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.service.CitiesService;

@Service
public class CitiesServiceImpl implements CitiesService {

	@Autowired
	CitiesDao citiesDao;
	
	
	@Override
	public Cities addCity(Cities cities) throws Exception {
		return citiesDao.addCity(cities);
	}

	@Override
	public Cities updateCity(Cities cities) throws Exception {
		// TODO Auto-generated method stub
		return citiesDao.updateCity(cities);
	}

	@Override
	public Cities getCity(String id) throws Exception {
		// TODO Auto-generated method stub
		return citiesDao.getCity(id);
	}

	@Override
	public Boolean deleteCity(String id) throws Exception {
		return citiesDao.deleteCity(id);
	}

	@Override
	public List<Cities> getAllCities() throws Exception {
		// TODO Auto-generated method stub
		return citiesDao.getAllCities();
	}

}
