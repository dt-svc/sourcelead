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

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.CitiesServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
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

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Test
	public void addCities() throws Exception {

		Cities city = new Cities();
		city.setCountryCode("APfddfe");
		city.setCityName("SBCerddr");
		city.setStateCode("HYrdde");
		Cities cityser= citiesService.addCity(city);
		System.out.println("Successfully Added!!!!");
		logger.info("Cities added..."+ cityser.getCityName());
	}

	@Test
	public void getCity() throws Exception {
		Cities cities = citiesService.getCity("AP");
		logger.info(" get City added..."+  cities.getCountryCode());
	}

	@Test
	public void updateCity() throws Exception {
		Cities city = new Cities();
		city.setCountryCode("IND");
		city.setCityName("HYD");
		city.setStateCode("TG");
		Cities cities =citiesService.updateCity(city);
		logger.info(" updated Cities..."+  cities.getCityName());

	}

	@Test
	public void getAllCities() throws Exception {
		List<Cities> cityList = citiesService.getAllCities();
		logger.info(" get All Cities..."+  cityList.get(0));
	}

	 @Test
	public void deleteCity() throws Exception {
		Boolean flag = citiesService.deleteCity("APe");
		logger.info(" get All Cities..."+  flag.booleanValue());
		
	}
}
