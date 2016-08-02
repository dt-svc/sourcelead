package com.bis.sourcelead.service;

import java.util.Date;

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
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.impl.OrgDaoImpl;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.impl.OrgServiceImpl;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class OrgServiceTest {

	@Configuration
	static class OrgServiceTestContextConfiguration {

		@Bean
		public OrgService orgService() {
			return new OrgServiceImpl();
		}
	}

	@Autowired
	OrgService orgService;

	private static final Logger logger = LoggerFactory.getLogger(OrgServiceTest.class);

	//@Test
	public void addOrganiztion() throws Exception {
		Org organization = new Org();
		logger.info("Test service");
		organization.setOrgName("BIS");
		organization.setOrgCode("dell.com");
		organization.setOrgType("IT");
		organization.setStatus(Status.ACTIVE);
		organization.setCreatedDate(new Date());
		organization.setCreatedBy("Prasad");
		organization.setWebSite("www.dell.com");
		organization.setUpdatedDate(new Date());
		organization.setUpdatedBy("YYY");
		orgService.addOrganization(organization);
	}

	//@Test
	public void updateOrg() throws Exception {
		Org organization = new Org();
		logger.info("Test service for update org");
		organization.setId(3L);
		organization.setOrgName("HP");
		organization.setOrgCode("wwwbiscom");
		organization.setOrgType("IT");
		organization.setStatus(Status.ACTIVE);
		organization.setCreatedDate(new Date());
		organization.setCreatedBy("sairam");
		organization.setWebSite("www.business.com");
		organization.setUpdatedDate(new Date());
		organization.setUpdatedBy("Prasad");
		orgService.updateOrg(organization);
		logger.info("Org Update successfully.!!");
	}

	// @Test
	public void findOrgById() throws Exception {
		Long l = 3L;
		orgService.findOrgByID(l);
		logger.debug("Record found Successfully.!");
	}

	@Test
	public void deletOrg() throws Exception {
		Long l = 3L;
		orgService.deleteOrg(l);
		logger.info("Org deleted Successfully.!");
	}

	// @Test
	public void getOrgs() throws Exception {
		orgService.getOrgs();
	}
}
