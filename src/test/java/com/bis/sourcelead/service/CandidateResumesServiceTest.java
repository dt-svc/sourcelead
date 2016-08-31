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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.CandidateResumesServiceImpl;




/**
 * @author Ajith Matta
 * @date 31/08/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class CandidateResumesServiceTest {
	
	@Configuration
	static class CandidateResumesServiceTestContextConfiguration {
		@Bean
		public CandidateResumesService andidateResumesService() {
			return new CandidateResumesServiceImpl();
		}
		}
		
		@Autowired
		CandidateResumesService candidateResumesService ;
		private static final Logger logger = LoggerFactory.getLogger(CandidateResumesServiceTest.class);
		
		@Test
		public void storeCandidateResume() throws Exception{
			CandidateResumes candidateResumes = new CandidateResumes();
			Candidate candidate = new Candidate();
			User user = new User();
			OrgLocations orgLocationId = new OrgLocations();
			orgLocationId.setId(1l);
			user.setId(1l);
			candidate.setId(1l);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("title", "exp");
			jsonObj.put("description", "hello hi");
			candidateResumes.setId(1l);
			candidateResumes.setStatus("CANDIDATE_ACTIVE");
			candidate.setOrgLocationId(orgLocationId);
						
			MultipartFile multipartFile = getMultiPartFile();
						
			candidateResumes = candidateResumesService.storeCandidateResume(jsonObj,user,candidate,orgLocationId,multipartFile);
			logger.info("success fully store " +candidateResumes.getStatus());
			
		}
		private MultipartFile getMultiPartFile() throws IOException {
			File file = new File("src/test/resources/input.txt");
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
	}

