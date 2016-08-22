package com.bis.sourcelead.service;

import java.util.Date;

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
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.EmailNotification;
import com.bis.sourcelead.entity.EmailScenario;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class EmailScenarioServiceTest {
	
	@Autowired
	EmailScenarioService emailScenarioService;
	private static final Logger logger = LoggerFactory.getLogger(EmailScenarioServiceTest.class);
	
	@Test
	public void save() throws Exception{
		EmailScenario emailScenario = new EmailScenario();
		emailScenario.setScenarioName("Testing service");
		emailScenario.setEntityType("Email");
		emailScenario.setUniqueScenarioName("testService");
		emailScenario.setDescription("this is used for testing service");
		emailScenario.setSubject("Testing Service");
		emailScenario.setTemplateType("org");
		emailScenario.setSignature("SourceLead");
		emailScenario.setBody("this is used for testing service");
		emailScenario.setCallToActionText("Sourcelead");
		emailScenario.setButtonText("Sourcelead");
		emailScenario.setIsTemplateModified("N");
		emailScenario.setCreatedDate(new Date());
		emailScenario.setCreatedBy("Source");
		emailScenario.setOrgCode("hangon");
		emailScenario.setLocationCode("sourceLead");
		emailScenario.setUseLogo("LOG");
		emailScenario.setDisclaimerText("Disclaimer");
		emailScenarioService.save(emailScenario);
		logger.info("save " +emailScenario.getId());
	}
	
	@Test
	public void getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode() throws Exception{
		EmailScenario emailScenario = new EmailScenario();
		emailScenario.setUniqueScenarioName("testService");
		emailScenario.setEntityType("Email");
		emailScenario.setOrgCode("hangon");
		emailScenario.setLocationCode("sourceLead");
		emailScenario = emailScenarioService.getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(emailScenario.getUniqueScenarioName(), emailScenario.getEntityType(), emailScenario.getOrgCode(), emailScenario.getLocationCode());
		logger.info("EmailScenario by given particulars is...>>"+emailScenario.getScenarioName());
	}
	
	@Test
	public void getEmailScenarioByUniqueName() throws Exception{
		EmailScenario emailScenario = new EmailScenario();
		emailScenario.setUniqueScenarioName("testService");
		emailScenario.setEntityType("Email");
		emailScenario = emailScenarioService.getEmailScenarioByUniqueName(emailScenario.getUniqueScenarioName(), emailScenario.getEntityType());
		logger.info("EmailScenario by given particulars is...>>"+emailScenario.getId());
	}
}
