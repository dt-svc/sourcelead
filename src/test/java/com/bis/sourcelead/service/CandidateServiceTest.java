package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Ajith Matta
 * @date 31/08/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class CandidateServiceTest {
	
	
		@Autowired
		CandidateService candidateService ;
		private static final Logger logger = LoggerFactory.getLogger(CandidateServiceTest.class);
		
		
		//@Test
		public void storeCandidate() throws Exception{
			
			Candidate candidate = new Candidate();
			User user = new User();
			OrgLocations orgLocationId = new OrgLocations();
			orgLocationId.setId(1l);
			user.setId(1l);
			candidate.setId(1l);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("firstName", "ramya");
			jsonObj.put("lastName", "reddy");
			jsonObj.put("country", "india");
			jsonObj.put("state", "telnagana");
			jsonObj.put("city", "wgl");
			jsonObj.put("phone", "24578965");
			jsonObj.put("email", "ajith@gmail.com");
			jsonObj.put("currentRate", "12");
			jsonObj.put("currentRateCurrency", "INR");
			jsonObj.put("designation", "develpoer");
			jsonObj.put("expectedRate", "123");
			jsonObj.put("expectedRateCurrencey", "INR");
			jsonObj.put("summary", "hello");
			jsonObj.put("designationType", "exp");
			jsonObj.put("experienceYears", "3");
			jsonObj.put("experienceMonths", "4");
			jsonObj.put("address", "wgl");
						
			MultipartFile multipartFile = getMultiPartFile();
						
			candidate = candidateService.storeCandidate(jsonObj,orgLocationId,user,multipartFile);
			logger.info("success fully store " +candidate.getCity());
			
		}
		private MultipartFile getMultiPartFile() throws IOException {
			File file = new File("src/test/resources/input.txt");
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
		/*@Test
		public void addCandidate() throws Exception{
			Candidate candidate = new Candidate();
			OrgLocations orglocations = new OrgLocations();
			orglocations.setId(2l);
			candidate.setId(2l);
			candidate.setFirstName("david");
			candidate.setLastName("raju");
			candidate.setEmail("raju@gmail.com");
			candidate.setFileContentType("input.txt");
			candidate.setOrgLocationId(orglocations);
			candidate.setMobilePhone("547896654");
			candidate.setCountry("usa");
			candidate.setState("telangana");
			candidate.setCity("waragal");
			candidate = candidateService.addCandidate(candidate);
			logger.info("addCandidate successfully " +candidate.getLastName());
		}*/
		
		//@Test
		public void getCandidateByCandidateId() throws Exception{
			Candidate candidate = new Candidate();
			candidate = candidateService.getCandidateByCandidateId(1l);
			logger.info("getCandidateByCandidateId " + candidate.getFirstName());
		}
		
		//@Test
		public void getCandidatesListByOrgLocationID() throws Exception{
			
			List<Candidate> candidateList = new ArrayList<>();
			candidateList = candidateService.getCandidatesListByOrgLocationID(1l,1l,"Active");
			logger.info("getCandidatesListByOrgLocationID " + candidateList.size());

			
		}
		
		//@Test
		public void getCandidateByCandidateIdAndOrgLocationId() throws Exception{
			Candidate candidate = new Candidate();
			candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(1l,1l);
			logger.info("getCandidateByCandidateIdAndOrgLocationId " + candidate.getEmail());
			
		}
		//@Test
		public void findByCandidateEmail() throws Exception{
		Candidate candidate = new Candidate();
		candidate = candidateService.findByCandidateEmail("ajith@gmail.com",1l);
		logger.info("findByCandidateEmail "+ candidate.getId());
				}
		//@Test
		public void saveOrUpdateCandidate() throws Exception{
			
			Candidate candidate = new Candidate();
			OrgLocations orglocations = new OrgLocations();
			orglocations.setId(1l);
			candidate.setId(1l);
			candidate.setFirstName("swathi");
			candidate.setLastName("reddy");
			candidate.setEmail("swathi@gmail.com");
			candidate.setFileContentType("input.txt");
			candidate.setOrgLocationId(orglocations);
			candidate.setMobilePhone("547896654");
			candidate.setCountry("usa");
			candidate.setState("telangana");
			candidate.setCity("waragal");
			candidate = candidateService.addCandidate(candidate);
			logger.info("saveOrUpdateCandidate successfully " +candidate.getLastName());
			
		}
	}

