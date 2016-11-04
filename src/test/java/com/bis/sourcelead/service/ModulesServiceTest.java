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

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ModulesServiceTest {
	
	@Autowired
	ModulesService modulesService;
	

	private static final Logger logger = LoggerFactory.getLogger(ModulesServiceTest.class);

	@Test
	public void getModulesIdByModuleName() throws Exception{
		
		String moduleName = "SOURCELEAD_DEFAULT_MODULE";
		List<Long> modules = modulesService.getModulesIdByModuleName(moduleName);
		
		logger.info("Retrieved list size is.."+modules.size());
		}
}
