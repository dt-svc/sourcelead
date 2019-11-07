package com.bis.sourcelead.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateEducationDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateGlobal;
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
	
	//@Test
	public void storeCandidateEducation() throws Exception{
		CandidateEducation candidateEducation = new CandidateEducation();
		
		CandidateEducationDTO candidateEducationDTO = new  CandidateEducationDTO();
		candidateEducationDTO.setInstitute("NEWHORIZON");
		candidateEducationDTO.setName("BE");
		candidateEducationDTO.setSpecialization("ElectroniceAndCommunication");
		candidateEducationDTO.setScore("A");
		candidateEducationDTO.setScoreType("percentage");
		candidateEducationDTO.setAddress("Madhapur");
		candidateEducationDTO.setStartDate("22-01-2001");
		candidateEducationDTO.setEndDate("02-01-2001");
		candidateEducationDTO.setCreatedDate("09-01-2001");
		candidateEducationDTO.setCreatedBy("shiva");
		candidateEducationDTO.setUpdatedBy("yogi");
		CandidateGlobal candidateId = new CandidateGlobal();
		candidateId.setId(1l);
		candidateEducation.setCandidateId(candidateId );
		OrgLocations orglocationId = new OrgLocations();
		orglocationId.setId(1l);
		candidateEducation.setOrglocationId(orglocationId);
		User user = new  User();
		user.setId(1l);
		candidateEducationService.storeCandidateEducation(candidateEducationDTO, 1l, 1l,user);
		logger.info("candidateEducationTest added successfully");
	}
	//@Test	
	public void getCandidateEducationListByCandidateIdAndOrgLocationID() throws Exception{
			Long candidateId = 1l;
			Long orgLocationId =1l;
			List <CandidateEducation> candidateedu =candidateEducationService.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
			for(CandidateEducation education :candidateedu){
				logger.info("getCandidateEducationListByCandidateIdAndOrgLocationID added successfully"+education.getId());
			}
		}
	
	
	
	//@Test
	public void removeCandidateEducationBycandidateIdandcandidateEducationId() throws Exception{
		Long candidateId = 1l;
		Long candidateEducationId = 1l;
		User user = new  User();
		user.setId(1l);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1l);
		CandidateEducation candidateEducation = candidateEducationService.removeCandidateEducationBycandidateIdandcandidateEducationId(candidateId, candidateEducationId, user, orgLocations);
		logger.info("removeCandidateEducationBycandidateIdandcandidateEducationId"+candidateEducation);
	}
	//@Test
	public void getCandidateEducationBycandidateIdandEducationId() throws Exception{
		Long candidateId = 1l;
		Long candidateEducationId = 1l;
		CandidateEducation candidateEducation = candidateEducationService.getCandidateEducationBycandidateIdandEducationId(candidateId, candidateEducationId);
		logger.info("getCandidateEducationBycandidateIdandEducationId"+candidateEducation);
	}
	
	@Test   
	public void validateEducationName() throws Exception{
		
		
		Candidate can = new Candidate();
		Long candidateId = 1l;
		can.setId(candidateId);
		Set<CandidateEducation> candidateEducation= new HashSet<>();
		CandidateEducation canEdu = new CandidateEducation();
		canEdu.setName("Associate of Arts and Sciences (A.A.S)");
		candidateEducation.add(canEdu);
		can.setCandidateEducation(candidateEducation);
		Boolean bool = candidateEducationService.validateEducationName(can.getId(), can.getCandidateEducation().iterator().next().getName());
		System.out.println("education name"+" "+bool.booleanValue());
	}
	

}








	


