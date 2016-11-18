package com.bis.sourcelead.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.Webgeocities;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class StatesServiceTest {

	@Autowired
	StatesService statesService;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Test
	public void getAllStatesByCountry() throws Exception {
		List<States> list= statesService.getAllStatesByCountry("India");
		logger.info("States List Size"+list.size());
	}
}
