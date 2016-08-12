package com.bis.sourcelead.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Countries;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class CountriesServiceTest {
	
	@Autowired
	CountriesService countriesService;
	
	@Test
	public void getCountriesByCountryName() throws Exception{
		String name = "India";
		Countries countries = countriesService.getCountriesByCountryName(name);
		System.out.println("Countries..>> "+countries.getName());
	}
}
