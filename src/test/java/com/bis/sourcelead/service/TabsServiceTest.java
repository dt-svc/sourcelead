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
import com.bis.sourcelead.entity.Tabs;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class TabsServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(TabsServiceTest.class);

	@Autowired
	TabsService tabsService;
	
	@Test
	public void getTabsListByLoginUserRoleIdAndOrgLocationId() throws Exception{
		List<Tabs> TabsListByLoginUser = tabsService.getTabsListByLoginUserRoleIdAndOrgLocationId(1l, 1l);
		logger.info("getTabsListByLoginUserRoleIdAndOrgLocationId added successfully"+TabsListByLoginUser.size());
	}
	
	@Test
	public void getSourceLeadTabsListByOrgId() throws Exception{
		List<Tabs> SourceLeadTabsList =tabsService.getSourceLeadTabsListByOrgId(1l, 1l);
		logger.info("getSourceLeadTabsListByOrgId added successfully"+SourceLeadTabsList.size());
	}
}