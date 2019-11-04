package com.bis.sourcelead.service;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Before;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.MarketplaceWalletDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateMarketCart;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.CandidateMarketWallet;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.MarketplaceSalesOrder;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.impl.CandidateServiceImpl;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.CandidateController;

import org.mockito.Spy;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Ajith Matta,Rakshith
 * @date 31/08/2016
 * @date 06/12/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class CandidateServiceTest {
	
	
		@Autowired
		CandidateService candidateService ;
		private static final Logger logger = LoggerFactory.getLogger(CandidateServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		@Autowired
		OrgLocationService orgLocationService;
		
		@Test
		public void storeCandidate() throws Exception{
			
			Candidate candidate = new Candidate();
			User user = new User();
			OrgLocations orgLocationId = new OrgLocations();
			orgLocationId.setId(1l);
			user.setId(1l);
			candidate.setId(1l);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("firstName", "sudeep");
			jsonObj.put("lastName", "reddy");
			jsonObj.put("country", "india");
			jsonObj.put("state", "telnagana");
			jsonObj.put("city", "nezamabad");
			jsonObj.put("phone", "24578965");
			jsonObj.put("email", "sudeep@gmail.com");
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
			jsonObj.put("status","1");
			jsonObj.put("createdBy", "srinivas");
			jsonObj.put("updatedBy", "srinivas");
			jsonObj.put("createdDate", "commonUtil.currentDate()");
						
			MultipartFile multipartFile = getMultiPartFile();
						
//			candidate = candidateService.storeCandidate(jsonObj,orgLocationId,user,multipartFile);
//			logger.info("success fully store " +candidate.getCity());
			
		}
		private MultipartFile getMultiPartFile() throws IOException {
			File file = new File("src/test/resources/input.txt");
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
		@Test
		public void addCandidate() throws Exception{
			Candidate candidate = new Candidate();
			OrgLocations orglocations = new OrgLocations();
			orglocations.setId(2l);
			candidate.setId(1l);
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
		}
		
		@Test
		public void getCandidateByCandidateId() throws Exception{
			Candidate candidate = new Candidate();
			candidate = candidateService.getCandidateByCandidateId(1l);
			logger.info("getCandidateByCandidateId " + candidate.getFirstName());
		}
		
		@Test
		public void getCandidatesListByOrgLocationID() throws Exception{
			
			List<Candidate> candidateList = new ArrayList<>();
			candidateList = candidateService.getCandidatesListByOrgLocationID(1l,1l,Constants.CANDIDATE_ACTIVE,"","All","Y","","","",null,null);
			logger.info("getCandidatesListByOrgLocationID " + candidateList.size());

			
		}
		
		@Test
		public void getCandidateByCandidateIdAndOrgLocationId() throws Exception{
			Candidate candidate = new Candidate();
			candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(1l,1l);
			logger.info("getCandidateByCandidateIdAndOrgLocationId " + candidate.getEmail());
			
		}
		@Test
		public void findByCandidateEmail() throws Exception{
		Candidate candidate = new Candidate();
		candidate = candidateService.findByCandidateEmail("ajith@gmail.com",1l);
		logger.info("findByCandidateEmail "+ candidate.getId());
				}
		@Test
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
			candidate.setStatus(Constants.CANDIDATE_ACTIVE);
			candidate.setCreatedDate(commonUtil.currentDate());
			candidate.setUpdatedDate(commonUtil.currentDate());
			candidate = candidateService.addCandidate(candidate);
			logger.info("saveOrUpdateCandidate successfully " +candidate.getLastName());
			
		}
		
		@Test
		public void getCandidatesByOrgLocationID() throws Exception {
			
			List<Candidate> candidatesList = candidateService.getCandidatesByOrgLocationID(1l,1l,"Candidate");
			for(Candidate candidate : candidatesList){
				candidate.getId();
				System.out.println("rakshith"+candidate.getId());
			}
			logger.info("saveOrUpdateCandidate successfully " +candidatesList.size());

		}
		
		@Test
		public void getAllCandidatesByLocation() throws Exception{
			
			Candidate can = new Candidate();
			can.setId(1l);
			Long orgLocationId=1l;
			List<Candidate> candidatesList = candidateService.getAllCandidatesByLocation(orgLocationId);
			for(Candidate candidate : candidatesList){
				candidate.getId();
				System.out.println("rakshith"+candidate.getId());
			}
			
		}
		
		@Test
		public void getCandidateExcelFile() throws Exception{
			
			List<CandidateGlobal> candidatesList = new ArrayList<>();
			OrgLocations orgLocationId=new OrgLocations();
			CandidateGlobal can= new CandidateGlobal();
			can.setId(1l);
			can.setFirstName("rak");
			can.setFileName("rak");
			
			orgLocationId.setId(1l);
			can.setOrgLocationId(orgLocationId);
			
			CandidateGlobal can1= new CandidateGlobal();
			can1.setId(1l);
			can1.setFirstName("rakshith");
			can1.setFileName("rakshith");
			
			candidatesList.add(can);
			candidatesList.add(can1);
			
			String orgName="bis";
			File file = candidateService.getCandidateExcelFile(candidatesList ,orgName,"Y");
			System.out.println("rakshith"+file.getName());
		}
		
		@Test
		public void getCandidatePdf() throws Exception{
			List<CandidateGlobal> candidatesList = new ArrayList<>();
			OrgLocations orgLocationId=new OrgLocations();
			orgLocationId.setId(1l);
			CandidateGlobal can= new CandidateGlobal();
			can.setId(1l);
			can.setFileName("pdf");
			can.setOrgLocationId(orgLocationId);
			
			CandidateGlobal can1= new CandidateGlobal();
			can1.setId(1l);
			can1.setFileName("pdf2");
			
			candidatesList.add(can);
			candidatesList.add(can1);
			
			String orgName="bis";
			File file = candidateService.getCandidatePdf(candidatesList, orgName,"Y");
			System.out.println("rakshith"+file.getName());
			

		}
		
}


