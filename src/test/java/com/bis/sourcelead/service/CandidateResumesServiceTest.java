package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
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
public class CandidateResumesServiceTest {
	
		
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
			jsonObj.put("docFile", "input.txt");
			candidateResumes.setId(1l);
			candidateResumes.setStatus("Constants.CANDIDATE_ACTIVE");
			candidate.setOrgLocationId(orgLocationId);
						
			MultipartFile multipartFile = getMultiPartFile();
						
			candidateResumes = candidateResumesService.storeCandidateResume(jsonObj,user,candidate,"1",orgLocationId,multipartFile);
			logger.info("success fully store " +candidateResumes.getStatus());
			
		}
		private MultipartFile getMultiPartFile() throws IOException {
			File file = new File("src/test/resources/input.txt");
			Boolean j = file.exists();
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
		@Test
		public void getCandidateResumesDetails() throws Exception{
			HashSet<CandidateResumes> candidateResumes = new HashSet<>();
			OrgLocations orglocations = new OrgLocations();
			Candidate candidate = new Candidate();
			candidate.setId(10l);
			orglocations.setId(1l);
			MultipartFile multipartFile = getMultiPartFile();
			
			candidateResumes = candidateResumesService.getCandidateResumesDetails(orglocations,"3hxku7yG",candidate,multipartFile);
			logger.info("getCandidateResumesDetails" + candidateResumes.size());
		}
		private MultipartFile getMultiPartFile1() throws IOException {
			File file = new File("src/test/resources/input.txt");
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
		@Test
		public void getCandidateResumesListByCandidateIdAndStatus() throws Exception{
			
			List<CandidateResumes> candidateResumesList = new ArrayList<CandidateResumes>();
			candidateResumesList = candidateResumesService.getCandidateResumesListByCandidateIdAndStatus(1l,"ACTIVE");
			logger.info("getCandidateResumesListByCandidateIdAndStatus " + candidateResumesList.size());
		}
		
		@Test
		public void updateCandidateResume() throws Exception{
			CandidateResumes candidateResumes = new CandidateResumes();
			OrgLocations orgLocations = new OrgLocations();
			Candidate candidate = new Candidate();
			candidate.setId(1l);
			orgLocations.setId(1l);
			candidate.setOrgLocationId(orgLocations);
			candidateResumes.setCandidateId(candidate);
			candidateResumes.setId(1l);
			candidateResumes.setOrgLocationId(orgLocations);
			candidateResumes.setDescription("hello shiva ");
			candidateResumes = candidateResumesService.updateCandidateResume(candidateResumes);
			logger.info("candidateResumes " + candidateResumes.getCandidateId());
		}
		
		@Test
		public void getCandidateResumeByResumeId() throws Exception{
			CandidateResumes candidateResumes = new CandidateResumes();
			candidateResumes = candidateResumesService.getCandidateResumeByResumeId(2l);
			logger.info("getCandidateResumeByResumeId " + candidateResumes.getDescription());
			
		}
		
	}

