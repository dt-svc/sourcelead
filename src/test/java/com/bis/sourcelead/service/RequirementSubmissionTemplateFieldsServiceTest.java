package com.bis.sourcelead.service;

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
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Rakshith
 * @date 01/12/2016
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RequirementSubmissionTemplateFieldsServiceTest {
	
		
	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionTemplateFieldsServiceTest.class);
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	

	@Test
	public void storeRequirementSubmissionTemplateFields() throws Exception{
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = new RequirementSubmissionTemplateFields();
		requirementSubmissionTemplateFields.setAlternateNumber("7760467294");
		requirementSubmissionTemplateFields.setCity("bangalore");
		requirementSubmissionTemplateFields.setCountry("India");
		requirementSubmissionTemplateFields.setCreatedBy("me");
		requirementSubmissionTemplateFields.setCreatedDate(commonUtil.currentDate());
		requirementSubmissionTemplateFields.setFirstName("rakshith");
		requirementSubmissionTemplateFields.setPhoneNumber("9964639665");
		requirementSubmissionTemplateFields.setEmailId("rakshithm@businessintelli.com");
		
		requirementSubmissionTemplateFieldsService.storeRequirementSubmissionTemplateFields(requirementSubmissionTemplateFields);
	}
	
	@Test
	public void getSubmissionsTemplateFieldsByRequirementSubmissionsId() throws Exception{
		
		Long id =1l;
		requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(id);
	}
}
