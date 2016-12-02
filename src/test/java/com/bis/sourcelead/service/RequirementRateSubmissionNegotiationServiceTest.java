package com.bis.sourcelead.service;

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
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Rakshith
 * @date 01/12/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RequirementRateSubmissionNegotiationServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(RequirementSubmissionTemplateFieldsServiceTest.class);

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementRateSubmissionNegotiationService requirementRateSubmissionNegotiationService;
	
	//@Test
	public void saveRateNegotiation(){
		
		RequirementRateSubmissionNegotiation rateNegotiation = new RequirementRateSubmissionNegotiation();
		rateNegotiation.setCreatedBy("me");
		rateNegotiation.setCreatedDate(commonUtil.currentDate());
		rateNegotiation.setCurrentRateType("hourly");
		rateNegotiation.setCurrentRate("inr");
		rateNegotiation.setStatus("open");
		rateNegotiation.setUpdatedBy("you");
		rateNegotiation.setUpdatedDate(commonUtil.currentDate());
		RequirementSubmission requirementsubmissionId= new RequirementSubmission();
		requirementsubmissionId.setId(1l);
		rateNegotiation.setRequirementsubmissionId(requirementsubmissionId);
		requirementRateSubmissionNegotiationService.saveRateNegotiation(rateNegotiation );
	}
	
	//@Test
	public void getRequirementRateSubmissionById(){
		
		Long ratesubmissionId = 1l;
		requirementRateSubmissionNegotiationService.getRequirementRateSubmissionById(ratesubmissionId );
		
	}
	
	@Test
	public void getRequirementRateSubmissionByOrgLocationsId() throws Exception{
		
		Long orgLocationsId = 1l;
		List<RequirementRateSubmissionNegotiation> reqRateSubmissionNegotiation = requirementRateSubmissionNegotiationService.getRequirementRateSubmissionByOrgLocationsId(orgLocationsId );
		for(RequirementRateSubmissionNegotiation rrsn :reqRateSubmissionNegotiation ){
		rrsn.getId();
	}
	}
	
	
}
