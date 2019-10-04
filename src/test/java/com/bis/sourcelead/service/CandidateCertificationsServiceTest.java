package com.bis.sourcelead.service;

import java.util.ArrayList;
import java.util.List;

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
import com.bis.sourcelead.dto.CandidateCertificationDTO;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author shivayogi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class CandidateCertificationsServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(CandidateCertificationsServiceTest.class);
	@Autowired
	CandidateCertificationsService candidateCertificationsService;

	@Test
	public void storeCandidateCertifications() throws Exception {
		CandidateCertifications candidateCertifications = new CandidateCertifications();

		CandidateGlobal candidateId = new CandidateGlobal();
		candidateId.setId(1l);
		candidateCertifications.setCandidateId(candidateId);

		OrgLocations orgLocationId = new OrgLocations();
		orgLocationId.setId(1l);

		candidateCertifications.setOrgLocationId(orgLocationId);
		User user = new User();

		CandidateCertificationDTO candidateCertificationDTO = new CandidateCertificationDTO();
		candidateCertificationDTO.setName("pradeep");
		candidateCertificationDTO.setProvider("Businessinelli");
		candidateCertificationDTO.setScore("10");
		candidateCertificationDTO.setScoreType("GPA");
		candidateCertificationDTO.setUpdatedBy("pradeep");
		candidateCertificationDTO.setValidFrom("22-01-2001");
		candidateCertificationDTO.setValidTo("20-01-2001");
		candidateCertificationDTO.setUpdatedDate("09-01-2001");
		candidateCertificationsService.storeCandidateCertifications(candidateCertificationDTO, user, orgLocationId, 1l);
		logger.info("CandidateCertificationsServiceTest added successfully");
	}

	@Test
	public void getCandidateCertificationListByCandidateIdAndOrgLocationID() throws Exception {
		List<CandidateCertifications> candidateCertifications = new ArrayList<CandidateCertifications>();
		CandidateCertifications candidate = new CandidateCertifications();
		candidate.setId(1l);
		candidate.setName("suhas");
		candidate.setProvider("businessintelli");
		candidate.setScore("10");
		candidate.setScoreType("gpa");
		CandidateGlobal candidateId = new CandidateGlobal();
		candidateId.setId(1l);
		candidate.setCandidateId(candidateId);
		OrgLocations orgLocationId = new OrgLocations();
		orgLocationId.setId(3l);
		candidate.setOrgLocationId(orgLocationId);

		candidateCertifications.add(candidate);

		logger.info("CandidateCertificationsServiceTest added successfully" + candidateCertifications.size());
	}
	
	
	@Test
	public void getCandidateCertificateBycandidateIdandCertificateId() throws Exception{
		CandidateCertifications candidateCertifications = new CandidateCertifications();
		candidateCertifications = candidateCertificationsService.getCandidateCertificateBycandidateIdandCertificateId(1l,1l);
		logger.info("getCandidateCertificateBycandidateIdandCertificateId " + candidateCertifications.getProvider());
		
	}
	
	@Test
	public void removeCandidateCertificateBycandidateIdandcandidateCertificateId() throws Exception{
		CandidateCertifications candidateCertifications = new CandidateCertifications();
		User user = new  User();
		user.setId(1l);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1l);
		candidateCertifications = candidateCertificationsService.removeCandidateCertificateBycandidateIdandcandidateCertificateId(1l, 1l, user, orgLocations);
		logger.info("removeCandidateCertificateBycandidateIdandcandidateCertificateId " + candidateCertifications.getName());
		
	}
}
