package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.ClientImportsServiceImpl;

/**
 * 
 * @author Rajashekar
 * @date 19th Sep 2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ClientImportsServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientImportsServiceTest.class);
	
	@Configuration
	static class ClientImportsContextConfiguration {

		@Bean
		public ClientImportsService clientImportsService() {
			logger.debug("Intantiate teh ClientImportsService objetc");;
			return new ClientImportsServiceImpl();
		}
	}
	@Autowired
	ClientImportsService clientImportsService;
	
	@Test
	public void clientImportTest() throws Exception{
		User user = new User();
		user.setId(4L);
		Org org = new Org();
		org.setId(3L);
		org.setUser(user);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(4L);
		orgLocations.setOrg(org);
		orgLocations.setUser(user);
		File file = new File("/root/Downloads/clients_import_template.xls");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
	    String isMailCheck = "checked";
	    String responderType = "Client";
	    clientImportsService.clientBulkSaving(multipartFile, user, orgLocations, org, isMailCheck, responderType);
	}
	

}
