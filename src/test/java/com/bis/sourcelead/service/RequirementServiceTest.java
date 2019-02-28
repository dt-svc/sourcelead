package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
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

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Ajith Matta
 * @date 31/08/2016
 * @author shivayogi
 * @date 02/12/2016
 * @author rakshith
 * @date 21/12/2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RequirementServiceTest {
	
	
		@Autowired
		RequirementService requirementService ;
		private static final Logger logger = LoggerFactory.getLogger(RequirementServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		//@Test
		public void storeRequirement() throws Exception{
			Requirement requirement = new Requirement();
			requirement.setId(1l);
			
			OrgLocations orglocations = new OrgLocations();
			orglocations.setId(1l);
			requirement.setOrgLocationId(orglocations);
			Org org= new Org();
			org.setId(1l);
			org.setOrgCode("123PWD");
			orglocations.setOrg(org);
			
			User user = new User();
			user.setId(1l);
			user.setFirstName("suhas");
			user.setEmail("suhas@gmail.com");
			user.setUserCode("12345");
			
			
			OrgSpoc spocId= new OrgSpoc();
			spocId.setId(1l);
			requirement.setSpocId(spocId);
			
			OrgRelations clientId = new OrgRelations();
			clientId.setId(1l);
			requirement.setClientId(clientId);
			
			
			requirement.setManagerUserId(user);
			RequirementDTO dto = new RequirementDTO();
			dto.setJobTitle("exp");
			dto.setJobType("FullTime");
			dto.setJobId("1");
			dto.setPositions("1");
			dto.setCountry("India");
			dto.setState("Telangana");
			dto.setCity("Warangal");
			dto.setMaxExp("5");
			dto.setMinExp("1");
			dto.setRate("12");
			dto.setRateType("1hr");
			dto.setDescription("hello test");
			dto.setCurrencyCode("INR");
			dto.setIsTemplate("N");
			dto.setSkills("Java");
			dto.setEducation("ssc");
			dto.setAssignedToMaxCount("1");
			dto.setManager("1");
			dto.setIsClient("N");
			dto.setClientCurrencyCode("INR");
			dto.setApplicationFrom("1");

			requirement = requirementService.storeRequirement(dto, orglocations, user);
			logger.info("storeRequirement successfully " + requirement.getCity());
			
		}
		private MultipartFile getMultiPartFile() throws IOException {
			File file = new File("src/test/resources/input.txt");
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
		
		@Test
		public void addRequirement() throws Exception{
			Requirement requirement = new Requirement();
			ArrayList<String> approverNames= new ArrayList<>();
			OrgRelations clientId = new OrgRelations();
			clientId.setId(1l);
			requirement.setClientId(clientId);
			
			OrgSpoc spocId= new OrgSpoc();
			spocId.setId(1l);
			requirement.setSpocId(spocId);
			
			approverNames.add("shiva");
			approverNames.add("rakshith");
			requirement.setApproverNames(approverNames);
			requirement.setCity("bamgalore");
			requirement.setClientContactEmail("suhas@gmil.com");
			String clientContactName="TCS";
			requirement.setClientContactName(clientContactName);
			requirement.setClientRate("20000");
			requirement.setClientRateType("INR");
			requirement.setClientSubmissionTemplate("N");
			SubmissionTemplate clientSubmissionTemplateId= new SubmissionTemplate();
			clientSubmissionTemplateId.setId(1l);
			requirement.setClientSubmissionTemplateId(clientSubmissionTemplateId);
			requirement.setCountry("INDIA");
			User user = new User();
			user.setId(1L);
			requirement.setCreatedBy(user);
			requirement.setCreatedDate(new Date());
			requirement.setCurrencyCode("IND");
			requirement.setDescription("HI HELLO WHATS UP");
			requirement.setEducation("BE");
			requirement.setIsTemplate("N");
			requirement.setJobId("2Oop");
			OrgLocations orgLocationId = new OrgLocations();
			orgLocationId.setId(1l);
			requirement.setOrgLocationId(orgLocationId );
			User managerUserId= new  User();
			managerUserId.setId(1l);
			requirement.setManagerUserId(managerUserId);
			
			requirementService.addRequirement(requirement);
		}
		
		@Test
		public void getRequirementListByOrgLocationID() throws Exception{
			String status="OPEN";
			String client = "TCS";
			Long orgLocationId=1l;
			User user = new User();
			String userRoleName = "ROLE_SOURCELEAD_ADMIN";
			List<Requirement> req = requirementService.getRequirementListByOrgLocationID(orgLocationId, status, client, user,userRoleName);
			for (Requirement requirement : req) {
				requirement.getId();
				logger.info("Successfully retrived!!!!");
		}
		}
		
		@Test
		public void getRequirementByRequirementId() throws Exception{
			Long requirementId=1l;
			requirementService.getRequirementByRequirementId(requirementId);
		}
		
		@Test
		public void getRequirementByParentRequirementIdAndSpocId() throws Exception{
			Long requirementId=1l;
			Long spocId=1l;
			requirementService.getRequirementByParentRequirementIdAndSpocId(requirementId, spocId);
		}
		
		@Test
		public void getJobTitleByJobTemplateStatusAndOrgLocationId() throws Exception{
			Long orgLocationId=1l;
			List<Requirement> jobTitle= requirementService.getJobTitleByJobTemplateStatusAndOrgLocationId(orgLocationId);
			for( Requirement requirement :jobTitle){
				requirement.getJobTitle();
			}
		}
		
		@Test
		public void getUserAssignJobCount()throws Exception{
			Long userId= 1l;
			Long orgLocationId=1l;
			List<RequirementAssignTo> requirementAssignTo = requirementService.getUserAssignJobCount(userId,orgLocationId);
			for( RequirementAssignTo assignTo:requirementAssignTo){
				assignTo.getApplicantCount();
			}
		}
		
		@Test
		public void approverEmailScenario()throws Exception{
			User user = new User();
			user.setId(1l);
			user.setEmail("suhas@gmail.com");
			user.setFirstName("suhas");
			
			User approverUser= new User();
			approverUser.setId(2l);
			approverUser.setEmail("ashwin@gmail.com");
			approverUser.setFirstName("ashwin");
			
			Requirement requirement= new Requirement();
			requirement.setId(1l);
			requirement.setJobTitle("JAVA");
			requirement.setJobId("22ER");
			
			StringBuilder location = new StringBuilder();
			location.append("INDIA");
			
			OrgLocations loginOrgLocation= new OrgLocations();
			loginOrgLocation.setId(1l);
			Org org= new Org();
			org.setOrgCode("12456");
			loginOrgLocation.setOrg(org);
			//requirementService.approverEmailScenario(user, approverUser, requirement, location, loginOrgLocation);
		}
		@Test
		public void approverTwoEmailScenario() throws Exception{
			User loginUser = new User();
			loginUser.setId(1l);
			loginUser.setEmail("suhas@gmail.com");
			loginUser.setFirstName("suhas");
			
			User approverUser= new User();
			approverUser.setId(2l);
			approverUser.setEmail("ashwin@gmail.com");
			approverUser.setFirstName("ashwin");
			
			Requirement requirement= new Requirement();
			requirement.setId(1l);
			requirement.setJobTitle("JAVA");
			requirement.setJobId("22ER");
			
			StringBuilder location = new StringBuilder();
			location.append("INDIA");
			
			OrgLocations orgLocations= new OrgLocations();
			orgLocations.setId(1l);
			Org org= new Org();
			org.setOrgCode("12456");
			orgLocations.setOrg(org);
			requirementService.approverTwoEmailScenario(loginUser, approverUser, requirement, location, orgLocations);
		}
		
		@Test
		public void acceptEmailScenario() throws Exception{
			User loginUser = new User();
			loginUser.setId(1l);
			loginUser.setEmail("suhas@gmail.com");
			loginUser.setFirstName("suhas");
			
			User approverUser= new User();
			approverUser.setId(2l);
			approverUser.setEmail("ashwin@gmail.com");
			approverUser.setFirstName("ashwin");
			
			Requirement requirement= new Requirement();
			requirement.setId(1l);
			requirement.setJobTitle("JAVA");
			requirement.setJobId("22ER");
			
			StringBuilder location = new StringBuilder();
			location.append("INDIA");
			
			OrgLocations orgLocations= new OrgLocations();
			orgLocations.setId(1l);
			Org org= new Org();
			org.setOrgCode("12456");
			orgLocations.setOrg(org);
			requirementService.acceptEmailScenario(loginUser, approverUser, requirement,null, orgLocations);
		}
		
		@Test
		public void mailRequirementtoManager() throws Exception{
			User loginUser = new User();
			loginUser.setId(1l);
			loginUser.setEmail("suhas@gmail.com");
			loginUser.setFirstName("suhas");
			
			User intiatorUser= new User();
			intiatorUser.setId(2l);
			intiatorUser.setEmail("ashwin@gmail.com");
			intiatorUser.setFirstName("ashwin");
			
			Requirement requirement= new Requirement();
			requirement.setId(1l);
			requirement.setJobTitle("JAVA");
			requirement.setJobId("22ER");
			
			StringBuilder location = new StringBuilder();
			location.append("INDIA");
			
			OrgLocations loginOrgLocation= new OrgLocations();
			loginOrgLocation.setId(1l);
			Org org= new Org();
			org.setOrgCode("12456");
			loginOrgLocation.setOrg(org);
			//requirementService.mailRequirementtoManager(loginUser, intiatorUser, requirement, location, loginOrgLocation);
		}
		
		@Test
		public void AssignToTeamMembersEmail() throws Exception{
			User loginUser = new User();
			loginUser.setId(1l);
			loginUser.setEmail("suhas@gmail.com");
			loginUser.setFirstName("suhas");
			
			User managerUser= new User();
			managerUser.setId(2l);
			managerUser.setEmail("ashwin@gmail.com");
			managerUser.setFirstName("ashwin");
			
			Requirement requirement= new Requirement();
			requirement.setId(1l);
			requirement.setJobTitle("JAVA");
			requirement.setJobId("22ER");
			
			StringBuilder location = new StringBuilder();
			location.append("INDIA");
			
			OrgLocations orgLocations= new OrgLocations();
			orgLocations.setId(1l);
			Org org= new Org();
			org.setOrgCode("12456");
			orgLocations.setOrg(org);
			//requirementService.AssignToTeamMembersEmail(loginUser, managerUser, requirement, location, orgLocations);
		}
		
		@Test
		public void rejectedEmailScenario() throws Exception{
			User loginUser = new User();
			loginUser.setId(1l);
			loginUser.setEmail("suhas@gmail.com");
			loginUser.setFirstName("suhas");
			
			User initiatorUser= new User();
			initiatorUser.setId(2l);
			initiatorUser.setEmail("ashwin@gmail.com");
			initiatorUser.setFirstName("ashwin");
			
			Requirement requirement= new Requirement();
			requirement.setId(1l);
			requirement.setJobTitle("JAVA");
			requirement.setJobId("22ER");
			
			StringBuilder location = new StringBuilder();
			location.append("INDIA");
			
			OrgLocations loginOrgLocation= new OrgLocations();
			loginOrgLocation.setId(1l);
			Org org= new Org();
			org.setOrgCode("12456");
			loginOrgLocation.setOrg(org);
			String rejectedReason="You are rejected";
			requirementService.rejectedEmailScenario(loginUser, initiatorUser, requirement, location, rejectedReason, loginOrgLocation);
		}
		
		
		@Test
		public void releaseMailScenario() throws Exception{
			User loginUser = new User();
			loginUser.setId(1l);
			loginUser.setEmail("suhas@gmail.com");
			loginUser.setFirstName("suhas");
			
			User vendorContactUser= new User();
			vendorContactUser.setId(2l);
			vendorContactUser.setEmail("ashwin@gmail.com");
			vendorContactUser.setFirstName("ashwin");
			
			Requirement requirement= new Requirement();
			requirement.setId(1l);
			requirement.setJobTitle("JAVA");
			requirement.setJobId("22ER");
			OrgSpoc spocId= new OrgSpoc();
			spocId.setId(1l);
			OrgLocations responderLocationId= new OrgLocations();
			responderLocationId.setId(1l);
			spocId.setClientLocationId(responderLocationId);
			requirement.setSpocId(spocId);;
			
			StringBuilder location = new StringBuilder();
			location.append("INDIA");
			
			OrgLocations loginOrgLocation= new OrgLocations();
			loginOrgLocation.setId(1l);
			Org org= new Org();
			org.setId(1l);
			org.setOrgCode("420");
			loginOrgLocation.setOrg(org);
			
			OrgSpoc orgSpoc= new OrgSpoc();
			User spocUser = new User();
			spocUser.setEmail("ajith@gmail.com");
			spocUser.setId(1l);
			orgSpoc.setVendorUser(spocUser);
			orgSpoc.setId(1l);
			
			String releasetype="VENDORS";
			//requirementService.releaseMailScenario(loginUser, requirement, orgSpoc, loginOrgLocation, releasetype);
		}	
		
		@Test
		public void getRequirementByParentRequirementId() throws Exception{
			Long requirementId=1l;
			//requirementService.getRequirementByParentRequirementId(requirementId);
		}
		
		@Test
		public void getAssignedRequirementListByOrgLocationIDAndUserId()throws Exception{
			Long orgLocationId=1l;
			String userType="ACTIVE";
			Long userId=1l;
			String userCode="2oo8";
			List<Requirement> assigned = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(orgLocationId, userType, userId, userCode);
			for(Requirement requirement:assigned){
				requirement.getId();
			}
		}
		
		@Test
		public void getAssignedRequirementApproverListByOrgLocationIDAndUserId() throws Exception{
			Long orgLocationId=1l;
			Long userId=1l;
			List<Requirement> RequirementApprover = requirementService.getAssignedRequirementApproverListByOrgLocationIDAndUserId(orgLocationId, userId);
			for(Requirement requirement:RequirementApprover){
				requirement.getId();
			}
		}
		
		
		@Test
		public void getRecommendationsWithScore() throws Exception{
			List<Candidate> candidateList= new ArrayList<Candidate>();
			Requirement requirement = new Requirement();
			requirement.setId(1l);
			List<Candidate> RequirementApprover = requirementService.getRecommendationsWithScore(candidateList, requirement);
			for(Candidate candidate:RequirementApprover){
				logger.info("Successfully updated!!!!"+candidate.getId());
			}
		}
		
		@Test
		public void getRequirementListById()throws Exception{
			Long requirementId=1l;
			List<Requirement> RequirementApprover = requirementService.getRequirementListById(requirementId);
			for(Requirement requirement:RequirementApprover){
				requirement.getId();
			}
		}
		@Test
		public void checkRequirementIsReleasedOrNot() throws Exception{
		Long requirementId=1l;
		Long orgLocationId=1l;
		requirementService.checkRequirementIsReleasedOrNot(requirementId, orgLocationId);
		}
		
		@Test
		public void validateJobId() throws Exception{
			Long orgLocation=1l;
			String id="1";
			requirementService.validateJobId(id, orgLocation);
		}
		
		//@Test
		public void getExportRequirementFile() throws Exception{
			String orgName="TCS";
			String exportType="xls";
			List<Requirement> requirementList= new ArrayList<>();
			for (Requirement requirement : requirementList) {
				requirement.setId(1l);
			}
			TimeSheetSubmissionCount requirementSubCount = new TimeSheetSubmissionCount();
			requirementSubCount.setSavedTimesheetSubmissionCount(1f);
			requirementService.getExportRequirementFile(requirementList, orgName, exportType, requirementSubCount);
		}
		
		@Test
		public void validateRequirementTitle() throws Exception{
			String jobName="JAVA";
			Long orgLocationId=1l;
			requirementService.validateRequirementTitle(jobName, orgLocationId);
		}
		
		@Test
		public void checkLoginUserIsRequirementManagerByRequirementIdAndUserId() throws Exception{
			Long requirementId=1l;
			Long userId=1l;
			requirementService.checkLoginUserIsRequirementManagerByRequirementIdAndUserId(requirementId, userId);
		}
		
		@Test
		public void getAllReleasedJobsForVendor()throws Exception{
			Long orgLocationId=1l;
			Long orgRelationId=1l;
			User user= new User();
			user.setId(1l);
			OrgLocations orgLocations= new OrgLocations();
			orgLocations.setId(1l);
			requirementService.getAllReleasedJobsForVendor(orgLocationId, orgRelationId, user, orgLocations);
		}
		
		@Test
		public void getAllRequirementTeplateJobsByOrgLocationId() throws Exception{
			Long orgLocationId=1l;
			User user=new User();
			user.setId(1l);
			List<Requirement> RequirementApprover = requirementService.getAllRequirementTeplateJobsByOrgLocationId(orgLocationId, user);
			logger.info("Successfully retrived!!!!"+RequirementApprover.size());

		}
		
		//@Test
		public void storeRequirementTemplate() throws Exception{
			
			RequirementDTO dto = new RequirementDTO();
			dto.setJobTitle("exp");
			dto.setJobType("FullTime");
			dto.setJobId("1");
			dto.setPositions("1");
			dto.setCountry("India");
			dto.setState("Telangana");
			dto.setCity("Warangal");
			dto.setMaxExp("5");
			dto.setMinExp("1");
			dto.setRate("12");
			dto.setRateType("1hr");
			dto.setDescription("hello test");
			dto.setCurrencyCode("INR");
			dto.setIsTemplate("N");
			dto.setSkills("Java");
			dto.setEducation("ssc");
			dto.setAssignedToMaxCount("1");
			dto.setManager("1");
			dto.setIsClient("N");
			dto.setClientCurrencyCode("INR");
			dto.setApplicationFrom("1");
			Requirement reqtemplate = new Requirement();
			reqtemplate.setId(3l);
			OrgLocations orgLocations=new OrgLocations();
			orgLocations.setId(2l);
			User user = new User();
			user.setId(1l);
			
			requirementService.storeRequirementTemplate(dto, orgLocations, user );
		}

		@Test
		public void getApproverRequirementApproverListByOrgLocationIDAndUserId() throws Exception{
			Long orgLocationId=1l;
			String userType="";
			Long userId=1l;
			String userCode="";
			String statusType="";
			List<Requirement> reqList =requirementService.getApproverRequirementApproverListByOrgLocationIDAndUserId(orgLocationId, userType, userId, userCode,statusType);
			for(Requirement req : reqList){
				req.getId();
			}
			logger.info("Successfully retrived!!!!"+reqList.size());
		}
		
		
		@Test
		public void getManagerAssignedRequirementsCount() throws Exception{
			Long userId=1l;
			Long orgLocationId=1l;
			Integer req = requirementService.getManagerAssignedRequirementsCount(userId, orgLocationId);
			logger.info("Successfully retrived!!!!"+req);
		}
		
		@Test
		public void getManagerAssignedRequirementsList() throws Exception{
			Long userId=3l;
			Long orgLocationId=1l;
			List<Requirement> reqList = requirementService.getManagerAssignedRequirementsList(userId, orgLocationId);
			for(Requirement req : reqList){
				req.getId();
			}
			logger.info("Successfully retrived!!!!"+reqList.size());
		}
		
		@Test
		public void getRequirementsCountAsApprover() throws Exception{
			Long userId=3l;
			Long orgLocationId=1l;
			Integer req = requirementService.getRequirementsCountAsApprover(userId, orgLocationId);
			logger.info("Successfully retrived!!!!"+req);
		}
		
		@Test
		public void getRequirementTemplateByRequirementId() throws Exception{
			Long requirementId=3l;
			Long orgLocationId=1l;
			Requirement req = requirementService.getRequirementTemplateByRequirementId(requirementId, orgLocationId);
			logger.info("Successfully retrived!!!!"+req.getJobTitle());
		}
		
		@Test
		public void updateRequirementTemplate() throws Exception{
			Requirement requirement = new Requirement();
			requirement.setId(3l);
			requirement.setJobTitle("this is the test job");
			OrgRelations orgRelation = new OrgRelations();
			orgRelation.setId(2l);
			User user = new User();
			user.setId(3l);
			requirement.setClientId(orgRelation);
			requirement.setManagerUserId(user);
			OrgLocations orgLocations = new OrgLocations();
			orgLocations.setId(3l);
			requirement.setOrgLocationId(orgLocations);
			OrgSpoc spoc = new OrgSpoc();
			spoc.setId(1l);
			requirement.setSpocId(spoc);
			requirementService.updateRequirementTemplate(requirement);
			logger.info("Successfully updated!!!!");
		}
}