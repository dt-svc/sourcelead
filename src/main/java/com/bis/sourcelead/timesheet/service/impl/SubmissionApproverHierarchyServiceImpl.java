package com.bis.sourcelead.timesheet.service.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.ProjectApproverHierarchyDao;
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimesheetApprovalSubmissionDetailsDTO;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.timesheet.dao.SubmissionApproverHierarchyDao;
import com.bis.sourcelead.timesheet.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class SubmissionApproverHierarchyServiceImpl implements SubmissionApproverHierarchyService {
	
	@Autowired
	SubmissionApproverHierarchyDao submissionApproverHierarchyDao;
	
	@Autowired
    ProjectApproverHierarchyDao projectApproverHierarchyDao;
	
	@Autowired
    EmployeesDao  employeesDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	ProjectMembersDao projectMembersDao;
	
	
	@Override
	public SubmissionApproverHierarchy addSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		submissionApproverHierarchy.setUpdatedDate(CommonUtil.currentDate());
		return submissionApproverHierarchyDao.addSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy updateSubmissionApproverHierarchy(
			SubmissionApproverHierarchy submissionApproverHierarchy) throws Exception {
		submissionApproverHierarchy.setUpdatedDate(CommonUtil.currentDate());
		return submissionApproverHierarchyDao.updateSubmissionApproverHierarchy(submissionApproverHierarchy);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchy(String id) throws Exception {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchy(id);
	}

	@Override
	public Boolean deleteSubmissionApproverHierarchy(String id) throws Exception {
		return submissionApproverHierarchyDao.deleteSubmissionApproverHierarchy(id);
	}

	@Override
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchies() throws Exception {
		return submissionApproverHierarchyDao.getAllSubmissionApproverHierarchies();
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployee(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployee(submissionId);
	}
	
	@Override
	public SubmissionApproverHierarchy getSubmissionLowLevelApproverHierarchyForEmployee(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionLowLevelApproverHierarchyForEmployee(submissionId);
	}
	
	
	@Override
	public List<SubmissionApproverHierarchy>getSubmissionApproverHierarchyBySubmissionId(Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyBySubmissionId(submissionId);
	}
	
	

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForApprover(Long submissionId,
			Long approverUserId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForApprover(submissionId, approverUserId);
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForEmployeeIdAndStartDate(Long empId,
			Long approverUserId,Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeIdAndStartDate(empId, approverUserId, submissionId);
	}

	@Override
	public void updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(Long projectMemberId,Date startDate,Date endDate,String status) {
		submissionApproverHierarchyDao.updateSubmissionStatusByProjectMemberIdStartDateAndEndDate(projectMemberId, startDate, endDate, status);
		
	}
	
	@Override
	public void updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(Long projectId,Long masterCandidateId,Date startDate,Date endDate,String status) {
		submissionApproverHierarchyDao.updateSubmissionStatusByProjectIdMasterCandidateIdStartDateAndEndDate(projectId, masterCandidateId,startDate, endDate, status);
		
	}
	
	@Override
	public User updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(Long projectMemberId,Date startDate,Date endDate,String reason, User loginUser, String type, String isSuperUser) throws Exception {
		User reopendUser = submissionApproverHierarchyDao.updateSubmissionStatusByProjectMemberIdStartDateAndEndDateWithReOpenReason(projectMemberId, startDate, endDate,reason,loginUser,type,isSuperUser);
		return reopendUser;
	}
	
	
	

	@Override
	public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForTheSubmission(Long submissionId,String approvalStatus) {
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForTheSubmission(submissionId,approvalStatus);
	}
	
	
	@Override
    public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectMember(Long projectMemberId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus) {
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForProjectMember(projectMemberId,frequencyStartDate,frequencyEndDate,approvalStatus);
	}
	
	@Override
    public SubmissionApproverHierarchy isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(Long projectId,Long masterCandidateId,Date frequencyStartDate,Date frequencyEndDate,String approvalStatus) {
		return submissionApproverHierarchyDao.isThereAnyApproverChangesStatusForProjectAndMasterCandidateId(projectId,masterCandidateId,frequencyStartDate,frequencyEndDate,approvalStatus);
	}
	
	

	@Override
	public ResponseList<SubmissionApproverHierarchy> getTotalTimesheetSubmissionListByUserIdAndOrgLocationIdAndStatusBasedOnUserRoleLogin(
			Long userId, Long orgLocationId, String status,String userRole,String search,int start,int length,String isGlobalUser) throws Exception {
		ResponseList<SubmissionApproverHierarchy> list = new ResponseList<SubmissionApproverHierarchy>();
	
         if(status != null){
 				 if("APPROVED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginWithSearchValue(userId, orgLocationId,  status,search,start,length,isGlobalUser);
  				}
                 if("REJECTED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginWithSearchValue(userId, orgLocationId, status,search,start,length,isGlobalUser);
  				}
                 if("SUBMITTED".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginWithSearchValue(userId, orgLocationId, status,search,start,length,isGlobalUser);
  				}
                 if("DRAFT".equalsIgnoreCase(status)){
     				list = submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLoginWithSearchValue(userId, orgLocationId, status,search,start,length,isGlobalUser);
  				}
 		}
         return list;
	}
	

	@Override
	public Float getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String approvalStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByuserIdAndProjectIdForApprover(userId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(Long userId, Long projectId,
			String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(userId, projectId, submissionStatus);
	}

	@Override
	public Float getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId,
			String approvalStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedHoursByMasterCandidateIdAndProjectIdForEmployee(masterCandidateId, projectId, approvalStatus);
	}

	@Override
	public Float getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(Long masterCandidateId, Long projectId,
			String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedHoursByMasterCandidateIdAndProjectIdForEmployee(masterCandidateId, projectId, submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalApprovedOrRejectedListByMasterCandidateIdForEmployeeLogin(userId,submissionStatus);
	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(Long userId, String submissionStatus) {
		return submissionApproverHierarchyDao.getTotalDraftAndSubmittedListByMasterCandidateIdForEmployeeLogin(userId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalApprovedOrRejectedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}

	@Override
	public List<SubmissionApproverHierarchy> getTotalDraftAndSubmittedListByuserIdAndOrgLocationIdForApproverLogin(
			Long userId, Long orgLocationId, String submissionStatus) {
		
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(userId,orgLocationId,submissionStatus);

	}


	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByApproverUserId(Long approverUserId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByApproverUserId(approverUserId);
	}

	@Override
	public void saveAllSubmissionApproverHierarchy(List<SubmissionApproverHierarchy> submissionApproverHierarchyList)
			throws Exception {
		submissionApproverHierarchyDao.saveAllSubmissionApproverHierarchy(submissionApproverHierarchyList);
		
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectMemberId(
			Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMemberId);
	}
	
	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(
			Date frequencyStartDate, Date frequencyEndDate, Long projectId,Long masterCandidateId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeByProjectIdAndMasterCandidateId(frequencyStartDate, frequencyEndDate, projectId,masterCandidateId);
	}
	
	/*@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin (Long approverUserId,
			Long projectMemberId, Date startDate, Date endDate) throws Exception{
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLogin(approverUserId,projectMemberId, startDate, endDate);
	}*/
	
	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(Long approverUserId,Long projectId,Long pmMasterCandidateId,Long locationsId,Date startDate, Date endDate,String isGlobalUser) throws Exception
	{
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(approverUserId, projectId, pmMasterCandidateId, locationsId, startDate, endDate,isGlobalUser);
	}
	@Override
	public void updateApprovalStatusByApproverForProjectMemberId(Long projectMemberId, Long approverUserId,
			Date startDate, Date endDate, String status, String rejectedReason,OrgLocations orglocations,String isGlobalUser) throws Exception {
		 submissionApproverHierarchyDao.updateApprovalStatusByApproverForProjectMemberId(projectMemberId, approverUserId, startDate, endDate, status, rejectedReason,orglocations,isGlobalUser);
	}

	@Override
	public Boolean getCronRemainderForEmployeeByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate,
			Long masterCandidateId,Long projectId) {
		return  submissionApproverHierarchyDao.getCronRemainderForEmployeeByProjectMemberId(frequencyStartDate, frequencyEndDate,masterCandidateId,projectId);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(
			Long approverUserId,Long locationsId, Long projectMemberId, Date startDate, Date endDate,String isGlobalUser) throws Exception {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(approverUserId,locationsId, projectMemberId, startDate, endDate,isGlobalUser);
	}

	@Override
	public Map<String, Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(
			Long approverUserId,Long locationsId, Long projectMemberId, Date startDate, Date endDate) throws Exception {
		return submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForApproverLoginForTsReports(approverUserId,locationsId, projectMemberId, startDate, endDate);
	}

	@Override
	public Map<String, Float> getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(Long projectMemberId,Long locationsId,
			Date startDate, Date endDate,String isGlobalUser) throws Exception {
		return submissionApproverHierarchyDao.getTotalHoursByProjectMemberIdStartDateAndEndDateForInvoice(projectMemberId,locationsId, startDate, endDate,isGlobalUser);
	}

	@Override
	public List<SubmissionApproverHierarchy> getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(Long projectId,Long orgLocationsId, Date frequencyStartDate, Date frequencyEndDate,String invoiceType) throws Exception
	{
		return submissionApproverHierarchyDao.getApprovedSubmissionApproverHierarchiesByProjectIdFrequencyStartDateAndFrequencyEndDate(projectId,orgLocationsId,frequencyStartDate,frequencyEndDate,invoiceType);
	}

	@Override
	public void updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(Long submissionHierarchyId,String invoiceType,String status,Invoice invoice) throws Exception {
		 submissionApproverHierarchyDao.updateAllSubmissionApproverHierarchyInvoiceStatusBySubmissionApproverHierarchyId(submissionHierarchyId,invoiceType,status,invoice);
	}

	@Override
	public Boolean isReopenButtonVisible(Date frequencyStartDate,Date frequencyEndDate,Long projectId,Long masterCandidateId,Long locationId,Long orgId,String isGlobalAccess){
		return submissionApproverHierarchyDao.isReopenButtonVisible(frequencyStartDate, frequencyEndDate, projectId, masterCandidateId,locationId,orgId,isGlobalAccess);
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyByProjectApproverHierarchyId(Long projectApproverHierarchyId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectApproverHierarchyId(projectApproverHierarchyId);
	}
	
	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyByProjectApproverHierarchyIdAndSubmissionId(Long projectApproverHierarchyId,Long submissionId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByProjectApproverHierarchyIdAndSubmissionId(projectApproverHierarchyId,submissionId);
	}

	@Override
	public SubmissionApproverHierarchy getSubmissionApproverHierarchyForEmployeeBySubmissionId(Long submissionId,
			Long masterCandidateId) {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyForEmployeeBySubmissionId(submissionId, masterCandidateId);
	}

	@Override
	public Boolean getPendingForApprovalTimesheetReportByProjectMemberId(Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId) {
		return submissionApproverHierarchyDao.getPendingForApprovalTimesheetReportByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMemberId);
	}

	@Override
	public List<SubmissionApproverHierarchy> getAllSubmissionApproverHierarchiesByProjectMemberId(
			Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId,Long locationId) throws Exception {
		return submissionApproverHierarchyDao.getAllSubmissionApproverHierarchiesByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMemberId,locationId);
	}

	@Override
	public Boolean isMissingTimesheetPerProjectByMasterCandidateIdAndProjectId(Date frequencyStartDate,
			Date frequencyEndDate, Long masterCandidateId, Long projectId) {
		return submissionApproverHierarchyDao.isMissingTimesheetPerProjectByMasterCandidateIdAndProjectId(frequencyStartDate, frequencyEndDate, masterCandidateId, projectId);
	}

	@Override
	public ResponseList<TimesheetApprovalSubmissionDetailsDTO> getSubmissionApproverHierarchyByTimesheetFrequencyOrgLocationsIdAndApproverUserId(
			Set<Long> projectIds,Boolean isAdmin,String timesheetFrequency, Date startDate, Date endDate, Long clientId, Long projectId, String status,
			Long locationsId, Long orgId, Long approverUserId, String searchValue, String projectName, int start,
			int length,int totalRows,  String isGlobalUser, String tableFor,
			String dateFormat) throws Exception {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByTimesheetFrequencyOrgLocationsIdAndApproverUserId(projectIds,isAdmin,timesheetFrequency, startDate, endDate, clientId, projectId, status, locationsId, orgId, approverUserId, searchValue, projectName, start, length,totalRows, isGlobalUser, tableFor, dateFormat);
	}

	@Override
	public LinkedHashMap<Date, Float> getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(
			Long projectId, Long pmMasterCandidateId, Date startDate, Date endDate, DateFormat format,
			String dateFormat) throws Exception {
		return submissionApproverHierarchyDao.getSubmissionApproverHierarchyByMasterCandidateIdStartDateAndEndDateForApproverLogin(projectId, pmMasterCandidateId, startDate, endDate, format, dateFormat);
	}

	@Override
	public List<SubmissionApproverHierarchy> getSubmissionApproverHierarchyForPendinApproveTimesheetsByProjectMemberId(
			Date frequencyStartDate, Date frequencyEndDate, Long projectMemberId) {
		return  submissionApproverHierarchyDao.getSubmissionApproverHierarchyForPendinApproveTimesheetsByProjectMemberId(frequencyStartDate, frequencyEndDate, projectMemberId);
	}
	

}
