package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;

public interface RequirementDao {

	
	public Requirement storeRequirement(Requirement requirement) throws Exception;
	
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,User user, String userRoleName) throws Exception;
	
	public Long getRequirementListByOrgLocationID(Long orgLocationId) throws Exception;
	
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception;
	
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;

	
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)throws Exception;
	
	public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception;

	public List<RequirementAssignTo> getUserAssignJobCount(Long userId, Long orgLocationId)throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;
	
	public List<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode,String statusType) throws Exception;
	
	
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId,Long  userId) throws Exception;

	public List<Requirement> getRequirementListById(Long requirementId);
	
	public Requirement checkRequirementIsReleasedOrNot(Long requirementId,Long orgLocationId) throws Exception;
	
	Requirement validateExistingJobId(String jobId,Long requirementId, Long orgLocationId) throws Exception;
	
	Integer validateJobId(String id,Long orgLocationId) throws Exception;

	public Boolean validateRequirementTitle(String jobName, Long orgLocationId);

	List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId, User user, OrgLocations orgLocations) throws Exception;
	
	Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId,Long userId)  throws Exception;
	
	List<Requirement> getAllRequirementTeplateJobsByOrgLocationId(Long orgLocationId, User user) throws Exception;
	
	public Requirement getRequirementTemplateByRequirementId(Long requirementId,Long orgLocationId) throws Exception;
	
	Integer getManagerAssignedRequirementsCount(Long userId,Long orgLocationId) throws Exception;
	
	public Integer getRequirementsCountAsApprover(Long userId,Long orgLocationId) throws Exception;
	
	public List<Requirement> getManagerAssignedRequirementsList(Long userId,Long orgLocationId) throws Exception;
	
	Boolean checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(Long userId,Long orgLocationId)  throws Exception;
	
    public Requirement getRequirementByRequirementIdOrgLocationsId(String id,Long requirementId, Long orgLocationsId) throws Exception;
	
    public Requirement getRequirementByRequirementUUID(String requirementUUID) throws Exception;
    
	public Boolean validateJobid(String id, Long orgLocationsId) throws Exception;

	List<String> getAllRequirementSkillsByRequirementI(Long requirementId)throws Exception;
	
	List<String> getAllRequirementIndustryByRequirementId(Long requirementId) throws Exception;

	public List<Requirement> getAllRequirementsByOrglocationId(Long orgLocationId) throws Exception;
	
	public List<Requirement> getAllReleasedRequirementsByParentId(Long parentRequirementId) throws Exception;

	String getNoticePeriodByRequirementId(Long requirementId) throws Exception;

	List<Requirement> getAllRequirementsByOrglocationIdAndUserId(Long orgLocationId, User user) throws Exception ;
	
	List<Requirement> getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(Long orgLocationId, User user)	throws Exception ;
	
	Integer getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(Long orgLocationId, User user)	throws Exception ; 

	List<Requirement> getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(Long orgLocationId,  TeamMembers teamMembers) throws Exception ;
	
	Long getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user, Date startDateUTC, Date endDateUTC) throws Exception ;

	public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId, Date startDateUTC, Date endDateUTC);
	
	List<Requirement> getRequirementsForSOWByOrgLocationId(Long orgLocationId, String sowType) throws Exception ;

	public List<String> getAllRequirementEducationByRequirementId(Long requirementId);
	
	Requirement getRequirementDetailsById(long parseLong)throws Exception;
	
	List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays) throws Exception;

	public List<Requirement> getAllReleasedJobsForVendorBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId,
			Date startDateUTC, Date endDateUTC);

	public List<Requirement> getAllReleasedJobsBasedOnUserIdAndLocationId(User user, OrgLocations orgLocations)throws Exception;

	public List<Requirement> getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(List<Long> parentreqIds,
			Long relationId)throws Exception;
	
	public List<Requirement> getAllClientJobsBasedonRelationId(Long locationId,Long relationId,String userRoleName,User user,String status)throws Exception;

	public Requirement checkRequirementIdNumberInDatebase(Long orgLocationId);
	
	public List<Requirement> getAllClosedRequirementsByStartDateAndEndDate(Date startDateUTC, Date endDateUTC) throws Exception;
	
	
	public List<Requirement> getAllVendorJobsBasedonRelationId(Long locationId,Long relationId, String userRoleName,User user,String status);

	public List<String> getAllRequirementVisaByRequirementI(Long requirementId)throws Exception;

	public Set<RequirementAssignTo> getRequirementAssignedToTeamByRequirementId(Long id);

	

	public List<Requirement> addGeneratedIdRecordsToJobsForOldRecords(Long orglocationId);

	public List<Projects> addGeneratedIdRecordsToProjectsForOldRecords(Long orglocationId);

	List<Candidate> addGeneratedIdRecordsToCandidatesForOldRecords(Long orglocationId);
	
	public boolean checkForOldrecordsUsingOrgLocationid(Long orglocationId,String tableName);

	Long getRequirementGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdCount);

	/*boolean checkForOldrecordsInCandidtesUsingOrgLocationid(Long orglocationId);

	boolean checkForOldrecordsInProjectsUsingOrgLocationid(Long orglocationId);*/

	
}