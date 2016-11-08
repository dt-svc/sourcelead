package com.bis.sourcelead.service;

import java.util.Date;
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

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Approval_Mechanism_Type;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class SubmissionApproverHierarchyServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(SubmissionApproverHierarchyServiceTest.class);

	@Autowired
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Test
	public void addSubmissionApproverHierarchy() throws Exception{
		SubmissionApproverHierarchy submissionApproverHierarchy = new SubmissionApproverHierarchy();
		submissionApproverHierarchy.setId(1l);
		submissionApproverHierarchy.setActionPerformedBy("emplpoyer");
		submissionApproverHierarchy.setCreatedBy("shiv");
		submissionApproverHierarchy.setCreatedDate(new Date());
		submissionApproverHierarchy.setMechanismType(Approval_Mechanism_Type.PARALLEL);
		submissionApproverHierarchy.setRejectedReason("asddfghjkl");
		submissionApproverHierarchy.setStatus("Active");
		submissionApproverHierarchy.setSubmissionApprovalStatus(Submission_ApprovalStatus.APPROVED);
		TimesheetSubmissions submissionId = new TimesheetSubmissions();
		submissionId.setId(1l);
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		submissionId.setProjectMembers(projectMembers );
		User approverUserId = new User();
		approverUserId.setId(1l);
		submissionId.setApproverUserId(approverUserId );
		submissionApproverHierarchy.setSubmissionId(submissionId);
		submissionApproverHierarchy.setUpdatedBy("shiv");
		submissionApproverHierarchy.setUpdatedDate(new Date());
		ProjectApproverHierarchy projectApproverHierarchy = new ProjectApproverHierarchy();
		projectApproverHierarchy.setId(1l);
		submissionApproverHierarchy.setHeirarchyId(projectApproverHierarchy);
		submissionApproverHierarchyService.addSubmissionApproverHierarchy(submissionApproverHierarchy);
		logger.info("submissionApproverHierarchy added successfully");
		
	}
	@Test
	public void updateSubmissionApproverHierarchy() throws Exception{
		SubmissionApproverHierarchy submissionApproverHierarchy = new SubmissionApproverHierarchy();
		TimesheetSubmissions submissionId = new TimesheetSubmissions();
		submissionId.setId(1l);
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		submissionApproverHierarchy.setSubmissionId(submissionId );
		ProjectApproverHierarchy hierarchyId = new ProjectApproverHierarchy();
		hierarchyId.setId(1l);
		submissionApproverHierarchy.setHeirarchyId(hierarchyId );
		submissionApproverHierarchy.setRejectedReason("hi Hello");
		submissionApproverHierarchyService.updateSubmissionApproverHierarchy(submissionApproverHierarchy);
		logger.info("updateSubmissionApproverHierarchy added successfully");
	}
	@Test
	public void getSubmissionApproverHierarchy() throws Exception{
		String id = "16";
		SubmissionApproverHierarchy Hierarchy = submissionApproverHierarchyService.getSubmissionApproverHierarchy(id);
		logger.info("getSubmissionApproverHierarchy added successfully"+ Hierarchy);
	}
	
	@Test
	public void getAllSubmissionApproverHierarchies() throws Exception{
		List<SubmissionApproverHierarchy> getAllSubmissionApprover = submissionApproverHierarchyService.getAllSubmissionApproverHierarchies();
		logger.info("getAllSubmissionApproverHierarchies added successfully"+getAllSubmissionApprover.size());
	}
	@Test
	public void getSubmissionApproverHierarchyForEmployee() throws Exception{
		SubmissionApproverHierarchy SubmissionApprover= submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployee(15l);
		logger.info("getSubmissionApproverHierarchyForEmployee added successfully"+SubmissionApprover);
	}
	@Test
	public void getSubmissionApproverHierarchyForApprover() throws Exception{
		SubmissionApproverHierarchy SubmissionForApprover = submissionApproverHierarchyService.getSubmissionApproverHierarchyForApprover(1l,1l);
		logger.info("getSubmissionApproverHierarchyForApprover added successfully"+SubmissionForApprover);
	}
	@Test
	public void getSubmissionApproverHierarchyForEmployeeIdAndStartDate() throws Exception{
		List<SubmissionApproverHierarchy> SubmissionApproverHierarchyForEmployeeId = submissionApproverHierarchyService.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(20l,1l,1l);
		logger.info("getSubmissionApproverHierarchyForEmployeeIdAndStartDate added successfully"+SubmissionApproverHierarchyForEmployeeId.size());
	}
	@Test
	public void updateSubmissionStatus() throws Exception{
		submissionApproverHierarchyService.updateSubmissionStatus(1l,"ACTIVE");
		logger.info("updateSubmissionStatus added successfully");
	}
	@Test
	public void updateApprovalStatusForApprover() throws Exception{
		submissionApproverHierarchyService.updateApprovalStatusForApprover(20l, 1l, new Date(), "ACTIVE","Rejected Reason");
		logger.info("updateApprovalStatusForApprover added successfully");
	}
	@Test
	public void isThereAnyApproverChangesStatusForTheSubmission() throws Exception{
		SubmissionApproverHierarchy isThereAnyApprover = submissionApproverHierarchyService.isThereAnyApproverChangesStatusForTheSubmission(1l, "APPROVED");
		logger.info("isThereAnyApproverChangesStatusForTheSubmission added successfully"+isThereAnyApprover);
	}
	@Test
	public void getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover() throws Exception{
		Float TotalApprovedOrRejectedHours = submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(20l, 1l, "REJECTED");
		logger.info("getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover added successfully"+TotalApprovedOrRejectedHours.floatValue());
	}
	@Test
	public void getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover() throws Exception{
		submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(20l, 1l, "ACTIVE");
		logger.info("getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover added successfully");
	}
	@Test
	public void getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee() throws Exception{
		Float TotalApprovedOrRejectedHours =  submissionApproverHierarchyService.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee(20l, 1l, "ACTIVE");
		logger.info("getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForEmployee added successfully"+TotalApprovedOrRejectedHours.floatValue());
	}
	@Test
	public void getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee() throws Exception{
		Float TotalDraftAndSubmittedHours = submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(20l, 1l, "ACTIVE");
		logger.info("getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee added successfully"+TotalDraftAndSubmittedHours.floatValue());
	}
	@Test
	public void deleteSubmissionApproverHierarchy() throws Exception{
		String id = "16";
		Boolean deleteSubmission = submissionApproverHierarchyService.deleteSubmissionApproverHierarchy(id);
		logger.info("deleteSubmissionApproverHierarchy added successfully");
	}
}