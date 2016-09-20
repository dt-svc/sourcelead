package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Ajith Matta
 * @date 31/08/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class RequirementServiceTest {
	
	
		@Autowired
		RequirementService requirementService ;
		private static final Logger logger = LoggerFactory.getLogger(RequirementServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		@Test
		public void storeRequirement() throws Exception{
			Requirement requirement = new Requirement();
			OrgLocations orglocations = new OrgLocations();
			User user = new User();
			Org org  = new Org();
			org.setId(1l);
			orglocations.setOrg(org  );
			orglocations.setId(1l);
			user.setId(1l);
			//requirement.setId(1l);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("jobTitle","exp");
			jsonObject.put("jobType", "FullTime");
			jsonObject.put("jobId","1");
			jsonObject.put("positions", "1");
			jsonObject.put("country","India");
			jsonObject.put("state", "Telangana");
			jsonObject.put("city","Warangal");
			jsonObject.put("maxExp", "5");
			jsonObject.put("minExp","1");
			jsonObject.put("rate","12");
			jsonObject.put("rateType", "1hr");
			jsonObject.put("description","hello test");
			jsonObject.put("currencyCode","INR");
			jsonObject.put("isTemplate", "test");
			jsonObject.put("skills","Java");
			jsonObject.put("education","ssc");
			
			MultipartFile multipartFile = getMultiPartFile();
			requirement = requirementService.storeRequirement(jsonObject, orglocations, user, multipartFile);
			logger.info("storeRequirement successfully " + requirement.getCity());
			
		}
		private MultipartFile getMultiPartFile() throws IOException {
			File file = new File("src/test/resources/input.txt");
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
		
	}

