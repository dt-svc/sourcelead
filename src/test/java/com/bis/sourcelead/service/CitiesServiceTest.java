package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.CitiesServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class CitiesServiceTest {

	
	@Configuration
	static class CitiesServiceConfigTest {
		@Bean
		public CitiesService citiesService() {
			return new CitiesServiceImpl();
		}
				

	}
	
	@Autowired
	CitiesService citiesService;

	private static final Logger logger = LoggerFactory.getLogger(CitiesServiceTest.class);

	@Test
	public void addCities() throws Exception{
		
		Cities city = new Cities();
		city.setCountryCode("US");
		city.setCityName("KMR");
		city.setStateCode("TG");
		
		citiesService.addCity(city);
		logger.info("Successfully Added!!!!");
	}
	
	@Test
	public void getCity() throws Exception{
		Cities cities =	citiesService.getCity("US");
		logger.info("get value of ind"+cities.getCountryCode());
	}

	@Test
	public void updateCity() throws Exception{
		Cities city = new Cities();
		city.setCountryCode("IND");
		city.setCityName("HYD");
		city.setStateCode("TG");
		citiesService.updateCity(city);
		logger.info("Successfully updated!!!!");
				
	}
			
	@Test
	public void getAllCities() throws Exception{
		List<Cities> cityList  = citiesService.getAllCities();
		logger.info("getAllCities "+cityList.size());
	}
	//@Test
		public void deleteCity() throws Exception{
			Boolean flag =	citiesService.deleteCity("US");
			logger.info("deleteCity successfully "+ flag);
		}
	
}
