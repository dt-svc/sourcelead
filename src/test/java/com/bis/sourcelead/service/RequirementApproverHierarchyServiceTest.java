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
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.HIERARCHY_TYPE;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author shivayogi
 * @date 02/12/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RequirementApproverHierarchyServiceTest {

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	RequirementApproverHierarchyService requirementApproverHierarchyService ;
	private static final Logger logger = LoggerFactory.getLogger(RequirementApproverHierarchyServiceTest.class);
	
	@Test
	public void getRequirementApproverHierarchyList() throws Exception{
		Long requirementId=2l;
		Long approverUserId=3l;
		List<RequirementApproverHierarchy> requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyList(requirementId, approverUserId);
		for(RequirementApproverHierarchy Hierarchy:requirementApproverHierarchy){
			Hierarchy.getId();
		} 
		logger.info("success");
	}
	
	
	@Test
	public void getRequirementApproverHierarchyByRequirementId() throws Exception{
		Long requirementId=1l;
		Long approverUserId=1l;
		requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, approverUserId);
		logger.info("success");
	}
	
	@Test
	public void addRequirementApproverHierarchy() throws Exception{
		RequirementApproverHierarchy requirementApproverHierarchy= new RequirementApproverHierarchy();
		ArrayList<String> approverNames= new ArrayList<>();
		approverNames.add("suhas");
		approverNames.add("rakshith");
		approverNames.add("ajith");
		approverNames.add("pavan");
		approverNames.add("muneendra");
		requirementApproverHierarchy.setApproverNames(approverNames);
		User approverUserId= new User();
		approverUserId.setId(1l);
		requirementApproverHierarchy.setApproverUserId(approverUserId);
		requirementApproverHierarchy.setCreatedBy("shivayogi");
		requirementApproverHierarchy.setCreatedDate(commonUtil.currentDate());
		requirementApproverHierarchy.setHierarchyType(HIERARCHY_TYPE.SEQUENTIAL);
		requirementApproverHierarchy.setLevel(2);
		requirementApproverHierarchy.setRejectedReason("REJECTED");
		requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.OPEN);
		requirementApproverHierarchy.setUpdatedBy("shivayogi");
		requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
		Requirement requirementId = new Requirement();
		requirementId.setId(1l);
		requirementApproverHierarchy.setRequirementId(requirementId);
		requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
		logger.info("success");
	}
	
	
	@Test
	public void getRequirementApproverHierarachyWithHighestLevel() throws Exception{
		Long requirementId=2l;
		requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);
		logger.info("success");
	}
	
	@Test
	public void getRequirementApproverHierarchyListByRequirementId() throws Exception{
		Long requirementId=1l;
		List<RequirementApproverHierarchy> ApproverHierarchy=requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
		for(RequirementApproverHierarchy requirementApproverHierarchy:ApproverHierarchy){
			requirementApproverHierarchy.getId();
		}
		logger.info("success");
	}
	
	@Test
	public void getRequirementApproverHierarchyByRequirementIdAndMaximumLevel() throws Exception{
		Long requirementId=2l;
		requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
		logger.info("success");
	}
	
	//@Test
		public void deleteTheRequirementApproverByRequirementIdAndApproverId() throws Exception{
			RequirementApproverHierarchy requirementApproverHierarchy= new RequirementApproverHierarchy();
			requirementApproverHierarchy.setId(1l);
			requirementApproverHierarchyService.deleteTheRequirementApproverByRequirementIdAndApproverId(requirementApproverHierarchy);
			logger.info("success");
		}
}
