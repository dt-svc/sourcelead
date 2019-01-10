package com.bis.sourcelead.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.dto.ResidenceDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateLcaJobDetails;
import com.bis.sourcelead.entity.CandidateResidence;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Immigration;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;
import com.bis.sourcelead.util.CommonUtil;

import junitx.framework.Assert;
/**
 * @author PrasadK
 * @date 26/02/2018
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ImmigrationServiceTest {
	
		 
		@Autowired
		private ImmigrationCommonService immigrationCommonService;
		
		@Mock
		private ImmigrationCommonDao mockImmigrationCommonDao;
		
		private static final Logger logger = LoggerFactory.getLogger(ImmigrationServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		@Autowired
		OrgLocationService orgLocationService;
		
		@Autowired
		LookUpDataService lookUpDataService;
		
		@Before
	    public void setUp() throws Exception {

	         MockitoAnnotations.initMocks(this);
	    }
		
	   @Test
		public void storeImmigartion() throws Exception{
			
			Immigration imm = new Immigration();
			ImmigrationDTO immigrationDTO = new ImmigrationDTO();
			User user = new User();
			OrgLocations orgLocationId = new OrgLocations();
			LookUpData lookUpdata = new LookUpData();
			Candidate candidate = new Candidate();
			Requirement req = new Requirement();
			ImmigrationTemplate immigrationTemplateId = new ImmigrationTemplate();
			
			OrgLocations attorneyOrgLocationId= new OrgLocations();
			attorneyOrgLocationId.setId(1955l);
			User spocUserId = new User();
			spocUserId.setId(6071l);
			
			OrgSpoc orgSpoc = new OrgSpoc();
			orgSpoc.setId(5179l);
			req.setId(1l);
			orgLocationId.setId(1l);
			user.setId(1l);
			candidate.setId(109047l);
			req.setId(10411l);
			imm.setId(1l);
			immigrationTemplateId.setId(152l);
			lookUpdata.setCategory("1096");
			imm.setPetitionType(lookUpdata);
			immigrationDTO.setReceiptNo("12345");
			immigrationDTO.setProcessType("Normal");
			immigrationDTO.setCountry("United States");
			immigrationDTO.setGeneratedId("HJNMIM71");
			immigrationDTO.setStatus("Active");
			immigrationDTO.setOpenDate("2019-09-11");
			immigrationDTO.setLocation("Hyderabad, Telangana, India");
			LookUpData uscisStatus = lookUpDataService.getLookUpDataByIdAndCategory(ImmigrationConstants.PETITION_STATUS,1104);
			imm.setCurrUscisStauts(uscisStatus);
			imm.setJob(req);
			imm.setImmigrationUuid("JKL6nhST");
			imm.setPlaceOfEntry("Hyderabad, Telangana, India");
			
			imm.setImmigrationTemplateId(immigrationTemplateId);
			imm.setAttorneyOrgLocationId(attorneyOrgLocationId);
			imm.setSpocUserId(spocUserId);
			imm.setOrgSpoc(orgSpoc);
			
			CandidateLcaJobDetails candidateLcaJobDetails = new CandidateLcaJobDetails();
			CandidateIdendities candidateIdendities = new CandidateIdendities();
			CandidateResumes candidateResume =new CandidateResumes();
			
			List<CandidateResidence> residenceEntityLst = new ArrayList<CandidateResidence>();
			
			when(mockImmigrationCommonDao.savePetitionDetails(imm, candidate, candidateIdendities, candidateResume, candidateLcaJobDetails, residenceEntityLst)).thenReturn(imm);
			
			//imm = immigrationCommonService.savePetitionDetails(immigrationDTO, spocUserId, orgLocationId,null);
			Assert.assertEquals(immigrationCommonService.savePetitionDetails(immigrationDTO,spocUserId,orgLocationId,null).getCountry(),"United States");
			logger.info("Pettition added successfully"+imm.getId());
		}
		
		@SuppressWarnings("deprecation")
	 //   @Test
		public void test_saveResidence() throws Exception {
			
			CandidateResidence canRes = new CandidateResidence();
			ResidenceDTO residenceDTO = new ResidenceDTO();
			//when(mockImmigrationCommonDao.saveResidenceDetails(any(CandidateResidence.class))).thenReturn(new CandidateResidence());
			when(mockImmigrationCommonDao.saveResidenceDetails(canRes)).thenReturn(canRes);
			User user = new User();
			OrgLocations orgLocationId = new OrgLocations();
			Candidate candidate = new Candidate();
			Immigration imm = new Immigration();
			imm.setId(3896l);
			OrgLocations attorneyOrgLocationId= new OrgLocations();
			attorneyOrgLocationId.setId(1955l);
			orgLocationId.setId(1l);
			user.setId(1l);
			candidate.setId(109047l);
			canRes.setCandidate(candidate);
			canRes.setLocation("Hyderabad, Telangana, India");
			canRes.setStreetAddress("Hyderabad, Telangana, India");
			canRes.setCity("Hyderabad");
			canRes.setState("Telangana");
			canRes.setCountry("India");
			canRes.setZipcode("12345");
			if (!StringUtils.isEmpty(residenceDTO.getResidenceStartDate())) {
				canRes.setStartDate((commonUtil.orgLocationDateFormat(orgLocationId.getDateFormat(),
						"2018-12-27")));
			}
			if (!StringUtils.isEmpty(residenceDTO.getResidenceEndDate())) {
				canRes.setEndDate((commonUtil.orgLocationDateFormat(orgLocationId.getDateFormat(),
						"2019-01-11")));
			}
			canRes.setStatus(Constants.RECORD_ACTIVE);
			canRes.setUpdatedBy(user);
            Assert.assertEquals(immigrationCommonService.saveResidenceDetails(residenceDTO, orgLocationId, user, candidate.getId(), imm.getId()),is(true));
            
		}
		
		
		private Object is(boolean b) {
			// TODO Auto-generated method stub
			return null;
		}

		//@Test
		public void create() throws Exception {

			CandidateResidence canRes = new CandidateResidence();
			ResidenceDTO residenceDTO = new ResidenceDTO();
			User user = new User();
			OrgLocations orgLocationId = new OrgLocations();
			Candidate candidate = new Candidate();
			Immigration imm = new Immigration();
			imm.setId(3896l);
			OrgLocations attorneyOrgLocationId= new OrgLocations();
			attorneyOrgLocationId.setId(1955l);
			
			orgLocationId.setId(1l);
			user.setId(1l);
			candidate.setId(109047l);
			canRes.setLocation("Hyderabad, Telangana, India");
			canRes.setStreetAddress("Hyderabad, Telangana, India");
			canRes.setCity("Hyderabad");
			canRes.setState("Telangana");
			canRes.setCountry("India");
			canRes.setZipcode("12345");
		    when(mockImmigrationCommonDao.saveResidenceDetails(any(CandidateResidence.class)))
		        .thenAnswer(new Answer<CandidateResidence>() {

		            public CandidateResidence answer(InvocationOnMock invocation) throws Throwable {

		            	CandidateResidence candidateResidence = (CandidateResidence) invocation.getArguments()[0];

		            	candidateResidence.setId(49l);

		                return candidateResidence;
		            }

		        });

		    assertNull(canRes.getId());

		    immigrationCommonService.saveResidenceDetails(residenceDTO, orgLocationId, user, candidate.getCandidateId(), imm.getId());

		    assertNotNull(canRes);

		    assertTrue(canRes.getId()>0);
		}
		
		//CandidateResidence List
		@SuppressWarnings({ "deprecation", "unchecked" })
		//@Test
		public void getCandidateResidenceDetailsList() throws Exception {
			
			List<CandidateResidence> candidatesMasterResidenceList = new ArrayList<CandidateResidence>();
			
			CandidateResidence canRes = new CandidateResidence(); 
			CandidateResidence canRes1 = new CandidateResidence();
			CandidateResidence canRes2 = new CandidateResidence();
			canRes1.setId(109047l);
			canRes2.setId(175685l);
			candidatesMasterResidenceList.add(canRes1);
			candidatesMasterResidenceList.add(canRes2);
			candidatesMasterResidenceList.size();
			when(mockImmigrationCommonDao.getCandidatesResidenceListByCandidateIdAndOrgLocationID(canRes.getId(),"")).thenReturn(candidatesMasterResidenceList);
			candidatesMasterResidenceList = mockImmigrationCommonDao.getCandidatesResidenceListByCandidateIdAndOrgLocationID(canRes.getId(),"");
			Assert.assertEquals( mockImmigrationCommonDao.getCandidatesResidenceListByCandidateIdAndOrgLocationID(canRes.getId(),"").size(),2);
		}

		//@Test
	    public void testDelete()       
	    {
			CandidateResidence canRes = new CandidateResidence();
			canRes.setId(48l);
			Candidate candidate = new Candidate();
			candidate.setId(175685l);
	        // set up mocked result on update...
			when(mockImmigrationCommonDao.deleteCandidateResidenceDetails(48l, 175685l)).thenReturn("success");
	        // call the method we wish to test...
	        String result= immigrationCommonService.deleteCandidateResidenceDetails(canRes.getId(), candidate.getId());  
	        // verify the method was called...
	        Assert.assertEquals("success",result);
	    }
		
		
		
	}


