package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.impl.OrgDaoImpl;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.form.OrgSettings;
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

	@Test
	public void addOrganiztion() throws Exception {
		Org organization = new Org();
		organization.setId(1l);
		organization.setDocFile(new byte[123]);
		organization.setOrgName("BIS");
		organization.setOrgCode("dell.com");
		organization.setOrgType("IT");
		organization.setStatus(Status.ACTIVE);
		organization.setCreatedDate(new Date());
		organization.setCreatedBy("Prasad");
		organization.setWebSite("www.dell.com");
		organization.setUpdatedDate(new Date());
		organization.setUpdatedBy("YYY");
		User user = new User();
		user.setId(3l);
		organization.setUser(user );
		organization = orgService.addOrganization(organization);
		logger.info("addOrganiztion" + organization.getWebSite());
	}

	@Test
	public void updateOrg() throws Exception {
		Org organization = new Org();
		User user = new User();
		user.setId(1l);
		//logger.info("Test service for update org");
		organization.setId(2L);
		organization.setOrgName("HP");
		organization.setOrgCode("wwwbiscom");
		organization.setOrgType("IT");
		organization.setStatus(Status.ACTIVE);
		organization.setCreatedDate(new Date());
		organization.setCreatedBy("sairam");
		organization.setWebSite("www.business.com");
		organization.setUpdatedDate(new Date());
		organization.setUpdatedBy("Prasad");
		organization.setUser(user);
		organization = orgService.updateOrg(organization);
		logger.info("updateOrg " +organization.getId());
	}

	@Test
	public void findOrgById() throws Exception {
		
		 Org organization = new Org();
		
		organization = orgService.findOrgByID(1l);
		logger.debug("findOrgById" + organization.getOrgName());
	}

	

	@Test
	public void getOrgs() throws Exception {
		 List<Org> organization = new ArrayList<Org>();
		 organization = orgService.getOrgs();
		logger.info("getOrgs "+ organization.size());
	}
	
	@Test
	public void websiteValidation() throws Exception{
		
		
		String abc = orgService.websiteValidation("www.gmail.com","www.facebook.com");
		logger.info("websiteValidation "+abc);
	}
	
	@Test
	public void saveOrgSettings() throws Exception{
		
		Org organization = new Org();
		organization.setId(1l);
		organization.setDocFile(new byte[4096]);
		organization.setCreatedBy("raju");
		organization.setStatus(Status.INACTIVE);
		OrgSettings orgSettings = new OrgSettings();
		MultipartFile multipartFile = getMultiPartFile();
		orgSettings.setFile(multipartFile);
		
		logger.info(" saveOrgSettings"+multipartFile.getSize()); 
	}
	private MultipartFile getMultiPartFile() throws IOException {
		File file = new File("src/test/resources/input.txt");
	    FileInputStream input = new FileInputStream(file);
	    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
		return multipartFile;
	}
	
	@Test
	public void getOrgByOrgCode() throws Exception{
		
		Org organization = new Org();
		organization.setOrgCode("slead");
		organization = orgService.getOrgByOrgCode(organization.getOrgCode());
		logger.info("getOrgByOrgCode "+organization.getWebSite());
		
	}
	
	@Test
	public void isClientNameValid() throws Exception{
		
	//	Boolean b =orgService.isClientNameValid("tcs",1L);
	//	
	}
	
	@Test
	public void getEnrolledOrgs() throws Exception{
		List<Org> organization = new ArrayList<>();
		organization = orgService.getEnrolledOrgs(2l, "Active", "srinivas");
		logger.info("getEnrolledOrgs "+organization.size());
		
	}

	//@Test
		public void deletOrg() throws Exception {
			//Long l = 3L;
			Boolean deletOrg=orgService.deleteOrg(2l);
			if(deletOrg != null)
			{
			logger.info("deletOrg "+ deletOrg);
			}
		}
}
