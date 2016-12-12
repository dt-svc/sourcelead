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
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Ajith Matta
 * @date 31/08/2016
 * @author shivayogi
 * @date 02/12/2016
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RequirementAssignToServiceTest {
	
	
		@Autowired
		RequirementAssignToService requirementAssignToService ;
		private static final Logger logger = LoggerFactory.getLogger(RequirementAssignToServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		@Test
		public void storeRequirement() throws Exception{
			Long requirementId=1l;
			List<RequirementAssignTo> AssignTo =requirementAssignToService.getRequirementAssignToListByRequirementId(requirementId);
			for(RequirementAssignTo requirementAssignTo:AssignTo){
				requirementAssignTo.getId();
				logger.info("success");
			}
		}
			@Test
			public void getRequirementAssignToByRequirementIdandAssignToId() throws Exception{
				Long requirementId=1l;
				Long assignToId=1l;
				requirementAssignToService.getRequirementAssignToByRequirementIdandAssignToId(requirementId, assignToId);
					logger.info("success");
				}
			
			
			@Test
			public void getAssignedTeamMembersCountByTeamMemberUserId() throws Exception{
				Long assignUserId=1l;
				String jobType="OPEN";
				requirementAssignToService.getAssignedTeamMembersCountByTeamMemberUserId(assignUserId, jobType);
					logger.info("success");
			}
			
			@Test
			public void getRequirementAssignToByRequirementId() throws Exception{
				Long requirementId=1l;
				requirementAssignToService.getRequirementAssignToByRequirementId(requirementId);
					logger.info("success");
			}
			
			@Test
			public void checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId() throws Exception{
				Long requirementId=1l;
				Long userId=1l;
				requirementAssignToService.checkLoginUserIsRequirementAssignedToByRequirementIdAndUserId(requirementId, userId);
					logger.info("success");
			}
			
			@Test
			public void deleteRequirementAssignToByRequirementId() throws Exception{
				Long requirementId=1l;
				requirementAssignToService.deleteRequirementAssignToByRequirementId(requirementId);
					logger.info("success");
			}
	}
		

