package com.bis.sourcelead.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.OfferAcceptanceRateDTO;
import com.bis.sourcelead.dto.RecruiterRrportDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.JobFairRequirements;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.RecruiterReportAssociateCountView;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.SubmissionRequirementListView;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;

public interface RequirementDao {

	
	public Requirement storeRequirement(Requirement requirement) throws Exception;
	
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,User user, String userRoleName) throws Exception;
	
	public Long getRequirementListByOrgLocationID(Long orgLocationId) throws Exception;
	
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception;
	
	public Requirement getRequirementByRequirementIdAndSearchValue(Long requirementId,String searchValue) throws Exception;
	
	public Requirement getRequirementByRequirementIdAndJobTitle(Long requirementId, String url) throws Exception;
	
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public Requirement getRequirementByGeneratedIdAndLocationId(String generatedId,Long orgLocationId)throws Exception;
	
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)throws Exception;
	
	public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception;

	public List<RequirementAssignTo> getUserAssignJobCount(Long userId, Long orgLocationId)throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(Long orgLocationId, String userType, Long userId, String userCode, boolean isWalkIn, String walkinDay) throws Exception;
	
	public ResponseList<Requirement> getAssignedJobsPerformanceList(Long orgLocationId, String userType, Long userId, String userCode,Date startDateUTC,Date endDateUTC,JobFilterDTO jobFilterDTO) throws Exception;
	
	public List<Requirement> getJobFairDaysListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId);
	
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
    
    public Requirement getRequirementByAccessibleUserIdAndRequirementUUID(Long userId, String requirementUUID) throws Exception;
    
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
	
	BigInteger getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;

	public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId, Date startDateUTC, Date endDateUTC);
	
	List<Requirement> getRequirementsForSOWByOrgLocationId(Long orgLocationId, String sowType) throws Exception ;

	public List<String> getAllRequirementEducationByRequirementId(Long requirementId);
	
	Requirement getRequirementDetailsById(long parseLong)throws Exception;
	
	List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays) throws Exception;

	public List<Requirement> getRequirementListByPostedDateAndSelectedClients(Long orgLocationId, Long userId,
			int numberOfDays, String selectedClients);
	
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
	
	Long getJobFairGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdCount);

	/*boolean checkForOldrecordsInCandidtesUsingOrgLocationid(Long orglocationId);

	boolean checkForOldrecordsInProjectsUsingOrgLocationid(Long orglocationId);*/
	
	/**
	 * get all jobs(OPEN) based on orglocationId 
	 * This method for Job Campaigns
	 * @param orglocationId
	 * @param submissionStatus
	 * @return
	 * @throws Exception
	 */
	
	public List<Requirement> getAllRequirementsByOrlocationIdAndOpenSubmissionStatus(Long orglocationId)throws Exception;
	
	public List<Requirement> getAllAssinedRequirementsOrgLocationIdAndUserId(Long orglocationId,Long userId)throws Exception;

	public Requirement getRequirementByShortURL(String shortURL) throws Exception;

	public ResponseList<RequirementDTO> getAllRequirementsForJobCampaing(RequirementDTO requirementDTO,Long orglocationId,Long userId,String dateFormat)throws Exception;
	
	public ResponseList<RequirementDTO> getJobListOfRecruiter(RequirementDTO requirementDTO, Long recruiterUserId,Date[] searchDates,List<Long> teamsList)throws Exception;
	
	public List<RequirementDTO> getAllCampaignRequirementsByIdsList(List<Long> requirementIds)throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDateAndManagerId(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays,Long managerId) throws Exception;

    public ResponseList<RequirementDTO> getRequirementList(Long id, String search, Long location, String employmentType, int pageNo, int pageSize);
    
    public List<RequirementDTO> getAllManagerRequirementForJobSharing(Date[] searchDates,Long managerUserId,Long orgLocationId) throws Exception;
    
    List<RecruiterReportAssociateCountView> getAssociateCountsCreatedUserList(Long orgLocationId, List<Long> userIdsList ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;
    
    List<RecruiterRrportDTO> getAssociateCountsCreatedUserListByLocation(Long orgLocationId,List<Long> userIdsList, String startDateUTC, String endDateUTC, String isGlobalUser, Long orgId)throws Exception ;
    
    public ResponseList<SubmissionRequirementListView> getAllRequirementsForCandidateSubmission(RequirementDTO requirementDTO,Long userId,OrgLocations orgLocations) throws Exception;

	public long saveJobFair(JobFair jobFair);

	public JobFair getJobFair(long jobFairId);
	
	public JobFair getJobFairByName(String jobFairName, long userId, long orgLocationId);
	
	public JobFair getJobFairByUUID(String uuid);

	public ResponseList<JobFair> getJobFairList(JobFairDTO jobFairDTO, boolean includeInactive);

	public boolean changeJobFairStatus(Long jobFairId, int status);

	public JobFairRequirements getJobFairRequirementsById(Long id);
	
	public long getJobFairRequirementsIdByJobFairIdAndRequirementId(long jobFairId, long requirementId);

	public List<JobFair> getJobFairListByRequirementId(Long requirementId);

	public long saveJobFairRequirements(JobFairRequirements jobFairRequirements);
	
	public List<String> getJobFairDatesForCalendar(long userId, long orgLocationId, String dateFormat, boolean adminFlag);

	public List<Requirement> getRequirementsByJobFairDate(long userId, long orgLocationId, String dateFormat, String date, boolean adminFlag);
	
	public List<Long> getJobFairRequirementsIdListByRequirementId(long requirementId);
	
	public void deleteJobFairRequirement(long jobFairRequirementId);

	public List<Requirement> getRequirementsListByJobFairId(long userId, long orgLocationId, long jobFairId, boolean adminFlag);

	public String getJobFairDatesByRequirementIdAndJobFairId(long requirementId, long jobFairId);

	public List<JobFair> getJobFairWithNullGeneratedIdList();
	
	Boolean forceDeleteForSelectedJobs(String[] uuids);
	
	public List<RequirementIncentive> getRequirementIncentiveListByRequirementId(long requirementId) throws Exception;
	
	void deleteRequirementIncentiveByRequirementIdOrIds(long requirementId, String ids) throws Exception;
	
	public List<Requirement> getJobsListByOrgRelationAndLocation(Long orglocationId,String orgRelationIds,Date startDateUTC,Date endDateUTC) throws Exception;

	public List<Requirement> getAllReleasedJobsForVendorBasedOnOrgRelationId(Long orgLocationId, Long orgRelationId);
	
	public List<Requirement> getAllReleasedJobsBasedOnSpocId(long spocId);

	public long getQualityCandidatesForVendors(List<Long> requirementIdList, long orgRelationId, long spocId, String status);
	
	public long getVendorSubmittedCandidates(long requirementId, long orgRelationId, long spocId, String status);

	public List<RequirementSubmission> getApplicantDeclineCountsForVendor(List<Long> requirementIdList, long orgRelationId, long spocId, String submissionStatus);

}