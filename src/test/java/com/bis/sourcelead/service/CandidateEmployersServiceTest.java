package com.bis.sourcelead.service;

import java.util.List;
import java.util.Set;

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
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class CandidateEmployersServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(CandidateEmployersServiceTest.class);
	@Autowired
	CandidateEmployersService candidateEmployersService;
	
	@Test
	public void storeCandidateEmployers() throws Exception{
		CandidateEmployers candidateEmployers = new CandidateEmployers();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "shivayogi");
		jsonObject.put("address", "Madhapur");
		jsonObject.put("isCurrentPosition", "ACTIVE");
		jsonObject.put("specialization", "ElectroniceAndCommunication");
		jsonObject.put("designation", "Developer");
		jsonObject.put("startYear", "2001");
		jsonObject.put("startMonth", "FEB");
		jsonObject.put("endYear", "2010");
		jsonObject.put("endMonth", "DEC");
		Candidate candidateId = new Candidate();
		candidateId.setId(1l);
		candidateEmployers.setCandidateId(candidateId );
		OrgLocations orglocationId = new OrgLocations();
		orglocationId.setId(1l);
		candidateEmployers.setOrgLocationId(orglocationId);
		User user = new  User();
		long candidateid = 1l;
		CandidateEmployers candidateEmp = candidateEmployersService.storeCandidateEmployers(jsonObject, user,orglocationId,candidateid);
		logger.info("CandidateEmployer added successfully"+candidateEmp.getId());
	}

	@SuppressWarnings("unused")
	@Test
	public void getCandidatesEmployersListByCandidateIdAndOrgLocationID() throws Exception{
		
		Long candidateId=1l;
		Long orgLocationId = 1l;
		List<CandidateEmployers> candidateEmployers = candidateEmployersService.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
		for(CandidateEmployers candidateEmp:candidateEmployers){
			logger.info("getCandidateEducationListByCandidateIdAndOrgLocationID added successfully"+candidateEmployers.size());
		}
	}
	@SuppressWarnings("unused")
	@Test
	public void getCandidateEmployersDetails() throws Exception{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("candidateEmployers", "yes");
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1l);
		Candidate candidate = new Candidate();
		candidate.setId(1l);
		String userCode ="AOO2";
		Set<CandidateEmployers> canEmp = candidateEmployersService.getCandidateEmployersDetails(jsonObject, orgLocations, candidate, userCode);
		for(CandidateEmployers candidateEmp:canEmp){
			logger.info("getCandidateEmployersDetails added successfully"+canEmp.size());
		}
	}
	@Test
	public void removeCandidateEmployerBycandidateIdandCandidateEmployerID() throws Exception{
		Long candidateId=1l;
		Long candidateEmployerId=2l;
		CandidateEmployers candidateEmployers = candidateEmployersService.removeCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, candidateEmployerId);
		logger.info("removeCandidateEmployerBycandidateIdandCandidateEmployerID removed successfully"+candidateEmployers.getStatus());
	}
	@Test
	public void getCandidateEmployerBycandidateIdandCandidateEmployerID() throws Exception{
		Long candidateId=1l;
		Long candidateEmployerId=3l;
		CandidateEmployers candidateEmployers = candidateEmployersService.getCandidateEmployerBycandidateIdandCandidateEmployerID(candidateId, candidateEmployerId);
		logger.info("getCandidateEmployerBycandidateIdandCandidateEmployerID added successfully"+candidateEmployers.getStatus());
	}
}
