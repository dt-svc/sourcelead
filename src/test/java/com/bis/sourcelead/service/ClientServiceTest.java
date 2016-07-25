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
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.impl.OrgRelationsDaoImpl;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.service.impl.OrgRelationsServiceImpl;

/**
 * 
 * @author PrasadK
 * @date 08th jul 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ClientServiceTest {

	@Configuration
	static class OrgServiceTestContextConfiguration {
		@Bean
		public OrgRelationsDao OrgRelationsDao() {
			return new OrgRelationsDaoImpl();

		}

		@Bean
		public OrgRelationsService orgRelationsService() {
			return new OrgRelationsServiceImpl();
		}
	}

	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	OrgRelationsDao orgRelationsDao;

	private static final Logger logger = LoggerFactory.getLogger(ClientServiceTest.class);

	@Test
	public void addOrgRelations() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		logger.info("Client Service Test");
		orgRelations.setId(7l);
		orgRelations.setOrgName("cts");
		orgRelations.setWebSite("www.cts.com");
		orgRelations.setPhone("09866712679");
		orgRelations.setEmail("korasikha.prasad@gmail.com");
		orgRelations.setCountry("india");
		orgRelations.setState("Ap");
		orgRelations.setCity("Vizag");
		orgRelations.setRelationType("Customer Relation");
		orgRelations.setCreatedDate(new Date());
		orgRelations.setCreatedBy("PrasadK");
		orgRelations.setUpdatedDate(new Date());
		orgRelations.setUpdatedBy("ABC");
		orgRelationsService.addOrgRelations(orgRelations);
		System.out.println("Successfully Added!!!!");
	}

	
}