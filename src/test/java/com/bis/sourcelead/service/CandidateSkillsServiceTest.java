package com.bis.sourcelead.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;
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
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateSkills;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.CommonUtil;




/**
 * @author Ajith Matta
 * @date 01/09/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class CandidateSkillsServiceTest {
	
		
		@Autowired
		CandidateSkillsService candidateSkillsService ;
		
		@Autowired
		CommonUtil commonUtil  ;
		
		
		private static final Logger logger = LoggerFactory.getLogger(CandidateSkillsServiceTest.class);
		
		//@Test
		public void storeCandidateSkills( ) throws Exception{
			CandidateSkills candidateSkills = new CandidateSkills();
			OrgLocations orgLocations = new OrgLocations();
			Candidate candidate = new Candidate();
			candidate.setId(1l);
			orgLocations.setId(1l);
			candidateSkills.setId(1l);
			candidateSkills.setCandidateId(candidate);
			candidateSkills.setOrgLocationID(orgLocations);
			candidateSkills.setName("Java");
			candidateSkills.setTotalExperience("5");
			candidateSkills.setUpdatedBy("Ajith");
			candidateSkills.setCreatedBy("reddy");
			candidateSkills.setCreatedDate(commonUtil.currentDate());
			candidateSkills.setUpdatedDate(commonUtil.currentDate());
			candidateSkills.setScale("4");
			candidateSkills = candidateSkillsService.storeCandidateSkills(candidateSkills);
			logger.info("storeCandidateSkills successfully " + candidateSkills.getCreatedDate());
			
			
		}
		
		//@Test
		public void getCandidateSkillsByCandidateId() throws Exception{
			List<CandidateSkills> candidateSkillsList = new ArrayList<>();
			candidateSkillsList = candidateSkillsService.getCandidateSkillsByCandidateId("1l");
			logger.info("getCandidateSkillsByCandidateId " + candidateSkillsList.size());
			
		}
		
		//@Test
		public void getCandidateSkillsDetails() throws Exception{
			
			HashSet<CandidateSkills> candidateSkills = new HashSet<>();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("java","java");
			jsonObject.put(".net",".net");
			Candidate candidate = new Candidate();
			OrgLocations orglocations = new OrgLocations();
			candidate.setId(1l);
			orglocations.setId(1l);
			CandidateSkills candidateSkil = new CandidateSkills();
			candidateSkil.setCandidateId(candidate);
			candidateSkil.setOrgLocationID(orglocations);
			
			candidateSkills = candidateSkillsService.getCandidateSkillsDetails(jsonObject,orglocations,candidate,"reddy");
			logger.info("getCandidateSkillsDetails " + candidateSkills.size());
			
		}
		
		//@Test
		public void getCandidateSkillBySkillId() throws Exception{
			CandidateSkills candidateSkills = new CandidateSkills();
			candidateSkills = candidateSkillsService.getCandidateSkillBySkillId(1l);
			logger.info("getCandidateSkillBySkillId " + candidateSkills.getName());
		}
		
		
	}

