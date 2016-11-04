package com.bis.sourcelead.service;

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

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Shivayogi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)

public class CandidateEducationServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(CandidateEducationServiceTest.class);
	
	@Autowired
	CandidateEducationService candidateEducationService;
	
	@Test
	public void storeCandidateEducation() throws Exception{
		CandidateEducation candidateEducation = new CandidateEducation();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("institute", "NEWHORIZON");
		jsonObject.put("name", "BE");
		jsonObject.put("specialization", "ElectroniceAndCommunication");
		jsonObject.put("grade", "A");
		jsonObject.put("gradeType", "percentage");
		jsonObject.put("address", "Madhapur");
		jsonObject.put("startDate", "22-01-2001");
		jsonObject.put("endDate", "02-01-2001");
		jsonObject.put("createdDate", "09-01-2001");
		jsonObject.put("createdBy", "shiva");
		jsonObject.put("updatedBy", "yogi");
		Candidate candidateId = new Candidate();
		candidateId.setId(1l);
		candidateEducation.setCandidateId(candidateId );
		OrgLocations orglocationId = new OrgLocations();
		orglocationId.setId(1l);
		candidateEducation.setOrglocationId(orglocationId);
		User user = new  User();
		user.setId(1l);
		candidateEducationService.storeCandidateEducation(jsonObject, 1l, 1l,user);
		logger.info("candidateEducationTest added successfully");
	}
	@Test	
	public void getCandidateEducationListByCandidateIdAndOrgLocationID() throws Exception{
			Long candidateId = 1l;
			Long orgLocationId =1l;
			List <CandidateEducation> candidateedu =candidateEducationService.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
			for(CandidateEducation education :candidateedu){
				logger.info("getCandidateEducationListByCandidateIdAndOrgLocationID added successfully"+education.getId());
			}
		}
	
	@Test
	public void getCandidateEducationDetails() throws Exception{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("candidateEducation", "NEWHORIZON");
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1l);
		Candidate candidate = new Candidate();
		candidate.setId(1l);
		String userCode = "A001";
		HashSet<CandidateEducation> candidateEducations = candidateEducationService.getCandidateEducationDetails(jsonObject, orgLocations, candidate, userCode);
		for(@SuppressWarnings("unused") CandidateEducation education :candidateEducations){
			logger.info("getCandidateEducationDetails added successfully"+candidateEducations.size());
		}
	}
	
	@Test
	public void removeCandidateEducationBycandidateIdandcandidateEducationId() throws Exception{
		Long candidateId = 1l;
		Long candidateEducationId = 1l;
		CandidateEducation candidateEducation = candidateEducationService.removeCandidateEducationBycandidateIdandcandidateEducationId(candidateId, candidateEducationId);
		logger.info("removeCandidateEducationBycandidateIdandcandidateEducationId"+candidateEducation);
	}
	@Test
	public void getCandidateEducationBycandidateIdandEducationId() throws Exception{
		Long candidateId = 1l;
		Long candidateEducationId = 1l;
		CandidateEducation candidateEducation = candidateEducationService.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		logger.info("getCandidateEducationBycandidateIdandEducationId"+candidateEducation);
	}
	}










	


