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
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementInterviewer;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author shivayogi
 * @date 02/12/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RequirementInterviewerServiceTest {

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementInterviewerService requirementInterviewerService ;
	private static final Logger logger = LoggerFactory.getLogger(RequirementInterviewerServiceTest.class);
	
	@Test
	public void getRequirementApproverHierarchyList() throws Exception{
		Long requirementInterviewId=1l;
		List<RequirementInterviewer> Interviewer = requirementInterviewerService.getInterviwersListByInterviewId(requirementInterviewId);
		for(RequirementInterviewer reqInterviewer:Interviewer){
			reqInterviewer.getId();
		} 
		logger.info("success");
	}
}