package com.bis.sourcelead.service;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.RecruiterRrportDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.RecruiterReportAssociateCountView;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.SubmissionRequirementListView;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ApplicantDeclineDTO;
import com.bis.sourcelead.reportsDTO.ApplicantsBackoutDTO;
import com.bis.sourcelead.reportsDTO.JobFilterDTO;

/**
 * @author Prasad K
 *
 */
public interface RequirementService {

    public Requirement storeRequirement(RequirementDTO requirementDTO, OrgLocations orgLocations, User user) throws Exception;

    public Requirement addRequirement(Requirement requirement) throws Exception;

    public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId, String status, String clients, User user, String userRoleName) throws Exception;

    public Requirement getRequirementByRequirementId(Long requirementId) throws Exception;
    
    public Requirement getRequirementByRequirementIdAndSearchValue(Long requirementId,String searchValue) throws Exception;

    public Requirement getRequirementByRequirementIdAndJobTitle(Long requirementId, String url) throws Exception;

    public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;

    public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;

    public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId) throws Exception;

    public List<RequirementAssignTo> getUserAssignJobCount(Long userId, Long orgLocationId) throws Exception;

    public void approverEmailScenario(User user, User approverUser, Requirement requirement, StringBuilder location, OrgLocations loginOrgLocation) throws Exception;

    public void approverTwoEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location, OrgLocations orgLocations) throws Exception;

    public void acceptEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location, OrgLocations orgLocations) throws Exception;

    public void mailRequirementtoManager(User loginUser, User managerUser, Requirement requirement,
			StringBuilder location, OrgLocations loginOrgLocation,String redirectURL,Long redirectOrgLocationId) throws Exception;

    public void AssignToTeamMembersEmail(User loginUser, User managerUser, Requirement requirement, StringBuilder location, OrgLocations orgLocations,String redirectURL,Long redirectOrgLocationId) throws Exception;

    void rejectedEmailScenario(User loginUser, User initiatorUser, Requirement requirement, StringBuilder location, String rejectedReason, OrgLocations loginOrgLocation) throws Exception;

    void rejectedInitiatorUserEmailScenario(User loginUser, User initiatorUser, Requirement requirement, StringBuilder location, String rejectedReason, OrgLocations loginOrgLocation) throws Exception;

    void releaseMailScenario(User loginUser, Requirement requirement, OrgSpoc orgSpoc, OrgLocations loginOrgLocation, String releasetype,String redirectURL,Long redirectOrgLocationId) throws Exception;

    public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception;

    public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;

	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(Long orgLocationId, String userType, Long userId, String userCode, boolean isWalkIn, String walkinDay) throws Exception;

	public ResponseList<Requirement> getAssignedJobsPerformanceList(Long orgLocationId, String userType, Long userId, String userCode,Date startDateUTC,Date endDateUTC,JobFilterDTO jobFilterDTO) throws Exception;

    public List<String> getJobFairDaysListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId);
    
    public List<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode, String statusType) throws Exception;

    public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, Long userId) throws Exception;

    public List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement) throws Exception;

    public List<Requirement> getRequirementListById(Long requirementId) throws Exception;

    public Boolean checkRequirementIsReleasedOrNot(Long requirementId, Long orgLocationId) throws Exception;

    Requirement validateExistingJobId(String jobId, Long requirementId, Long orgLocationId) throws Exception;

    Integer validateJobId(String id, Long orgLocation) throws Exception;

    public File getExportRequirementFile(List<Requirement> requirementList, String orgName, String exportType, TimeSheetSubmissionCount requirementSubCount) throws Exception;

    public Boolean validateRequirementTitle(String jobName, Long orgLocationId) throws Exception;

    Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId, Long userId) throws Exception;

    public List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId, User user, OrgLocations orgLocations) throws Exception;

    List<Requirement> getAllRequirementTeplateJobsByOrgLocationId(Long orgLocationId, User user) throws Exception;

    public Requirement storeRequirementTemplate(RequirementDTO requirementDTO, OrgLocations orgLocations, User user) throws Exception;

    public Requirement getRequirementTemplateByRequirementId(Long requirementId, Long orgLocationId) throws Exception;

    public Requirement updateRequirementTemplate(Requirement requirement) throws Exception;

    Integer getManagerAssignedRequirementsCount(Long userId, Long orgLocationId) throws Exception;

    public Integer getRequirementsCountAsApprover(Long userId, Long orgLocationId) throws Exception;

    public List<Requirement> getManagerAssignedRequirementsList(Long userId, Long orgLocationId) throws Exception;

    Boolean checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(Long userId, Long orgLocationId) throws Exception;

    public Requirement getRequirementByRequirementIdOrgLocationsId(String id, Long requirementId, Long orgLocationsId) throws Exception;

    public Boolean validateJobid(String id, Long orgLocationsId) throws Exception;

    public Requirement getRequirementByRequirementUUID(String requirementUUID) throws Exception;

    public Requirement getRequirementByAccessibleUserIdAndRequirementUUID(Long userId, String requirementUUID) throws Exception;

    List<String> getAllRequirementSkillsByRequirementI(Long requirementId) throws Exception;

    List<String> getAllRequirementIndustryByRequirementId(Long requirementId) throws Exception;

    public Long getRequirementsListById(Long requirementId) throws Exception;

    public Requirement updateRequirement(Requirement requirement) throws Exception;

    public List<Requirement> getAllRequirementsByOrglocationId(Long orgLocationId) throws Exception;

    public List<Requirement> getAllReleasedRequirementsByParentId(Long parentRequirementId) throws Exception;

    public String getNoticePeriodByRequirementId(Long requirementId) throws Exception;

    public Requirement getRequirementByGeneratedIdAndLocationId(String generatedId,Long orgLocationId)throws Exception;
    
    // This method is if the user is requirement manager or approver or assignTo
    // or creator this method is for consolidated reports
    public List<Requirement> getAllRequirementsByOrglocationIdAndUserId(Long orgLocationId, User user) throws Exception;

    // This method is if the user is TeamLead this method is used for
    // consolidated reports
    public List<Requirement> getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(Long orgLocationId, User user) throws Exception;

    // This method is for getting the released requirements based on the user
    // and orglocatinsId for consolidates reports for individual
    Integer getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(Long orgLocationId, User user) throws Exception;

    public List<Requirement> getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(Long orgLocationId, TeamMembers teamMembers) throws Exception;

    BigInteger getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception;

    public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId, Date startDateUTC, Date endDateUTC);

    public List<Requirement> getRequirementsForSOWByOrgLocationId(Long orgLocationId, String sowType) throws Exception;

    public List<String> getAllRequirementEducationByRequirementId(Long requirementId);

    Requirement getRequirementDetailsById(Long requirementId) throws Exception;

    public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays) throws Exception;

    public List<Requirement> getRequirementListByPostedDateAndSelectedClients(Long orgLocationId, Long userId, String postedDateRange, String selectedClients);
    
    public List<Requirement> getAllReleasedJobsForVendorBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId, Date startDateUTC, Date endDateUTC) throws Exception;

    public List<Requirement> getAllReleasedJobsBasedOnUserIdAndLocationId(User user, OrgLocations orgLocations) throws Exception;

    public List<Requirement> getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(List<Long> parentreqIds, Long relationId) throws Exception;

    public List<Requirement> getAllClientJobsBasedonRelationId(Long locationId, Long relationId, String userRoleName, User user, String status) throws Exception;

    public List<Requirement> getAllClosedRequirementsByStartDateAndEndDate(Date startDateUTC, Date endDateUTC) throws Exception;

    public String getRequirementId(OrgLocations orgLocations);
    
    public String getGeneratedJobFairIdByOrgLocationId(long orgLocationsId);
    
    public String getGeneratedJobFairId(OrgLocations orgLocations);

    public List<Requirement> getAllVendorJobsBasedonRelationId(Long locationId, Long relationId, String userRoleName, User user, String status);

    public List<String> getAllRequirementVisaByRequirementI(Long requirementId) throws Exception;

    public boolean checkForOldrecordsUsingOrgLocationid(Long orglocationId) throws Exception;

    public List<Requirement> getAllRequirementsByOrlocationIdAndOpenSubmissionStatus(Long orglocationId) throws Exception;

    public List<Requirement> getAllAssinedRequirementsOrgLocationIdAndUserId(Long orglocationId, Long userId) throws Exception;

    public ResponseList<RequirementDTO> getAllRequirementsForJobCampaing(RequirementDTO requirementDTO, Long orglocationId, Long userId, String dateFormat) throws Exception;

    public List<RequirementDTO> getAllCampaignRequirementsByIdsList(List<Long> requirementIds) throws Exception;

    /*
	 * public boolean addGeneratedIdRecordsToJobsForOldRecords(Long
	 * orglocationId) throws Exception;
     */
    public void shareRecomendedApplicant(User loginUser, OrgLocations orgLocation, Requirement requirement, Candidate candidate) throws Exception;

    public Requirement getRequirementByShortURL(String shortURL) throws Exception;

    public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDateAndManagerId(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays, Long managerId) throws Exception;

    public ResponseList<RequirementDTO> getRequirementList(Long id, String search, Long location, String employmentType, int pageNo, int pageSize);
    
    public List<RequirementDTO> getAllManagerRequirementForJobSharing(Date[] searchDates,Long managerUserId,Long orgLocationId) throws Exception;
    
    List<RecruiterReportAssociateCountView> getAssociateCountsCreatedUserList(Long orgLocationId, List<Long> userIdsList ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;
    
    List<RecruiterRrportDTO> getAssociateCountsCreatedUserListByLocation(Long orgLocationId, List<Long> userIdsList ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;

    
    public ResponseList<SubmissionRequirementListView> getAllRequirementsForCandidateSubmission(RequirementDTO requirementDTO,Long userId,OrgLocations orgLocations) throws Exception;

    /*public long addRequirementToJobFairs(List<JobFairDTO> jobFairDTOList);*/
    
    /*public long addRequirementToJobFair(long jobFairId, long requirementId, String[] jobFairDates);*/
    
    public Response<String> saveJobFair(JobFairDTO jobFairDTO, User user, OrgLocations orgLocation);
    
    public ResponseList<JobFairDTO> getJobFairsList(JobFairDTO jobFairDTO, boolean includeInactive);
    
    public JobFairDTO getJobFairByUUID(String uuid);
    
    public boolean changeJobFairStatus(Long jobFairId, int status, User user, OrgLocations orgLocation);

	public List<JobFair> getJobFairsListByRequirementId(Long requirementId);

	public List<String> getJobFairDatesForCalendar(long userId, long orgLocationId, String dateFormat, boolean adminFlag);

	public List<Requirement> getJobFairRequirementsByDate(long userId, long orgLocationId, String dateFormat, String date, boolean adminFlag);

	public List<JobFairDTO> getJobFairsDTOListByRequirementId(long requirementId);
	
	public List<Requirement> getRequirementsListByJobFairId(long userId, long orgLocationId, long jobFairId, boolean adminFlag);
	
	public String getJobFairDatesByRequirementIdAndJobFairId(long requirementId, long jobFairId);
	
	public void assignGeneratedIdToJobFairs();
	
	public ResponseList<RequirementDTO> getJobListOfRecruiter(RequirementDTO requirementDTO, Long recruiterUserId,Date[] searchDates,List<Long> teamsList) throws Exception;
	
	Boolean forceDeleteForSelectedJobs(String[] uuids) throws Exception;
	
	public List<RequirementIncentive> getRequirementIncentiveListByRequirementId(long requirementId) throws Exception;
	
	public void deleteRequirementIncentiveByRequirementIdOrIds(long requirementId, String ids) throws Exception;

	public File getJobFairListOfExcel(List<JobFairDTO> jobFairDTOList, User user, OrgLocations orgLocation);

	public void sendMailToPrimaryRecruiter(Requirement requirement,User loginUser,User toUser) throws Exception;
	
	public List<Requirement> getAllReleasedJobsForVendorBasedOnOrgRelationId(Long orgLocationId, Long orgRelationId);
	
	public List<Requirement> getAllReleasedJobsBasedOnSpocId(long spocId);

	public long getQualityCandidatesForVendors(List<Long> requirementIdList, long orgRelationId, long spocId, String staus);
	
	public long getVendorSubmittedCandidates(long requirementId, long orgRelationId, long spocId, String status);

	public ApplicantDeclineDTO getApplicantDeclineCountsForVendor(List<Long> requirementIdList, long orgRelationId, long spocId);
	
	public ApplicantsBackoutDTO getApplicantBackoutCountsForVendor(List<Long> requirementIdList, long orgRelationId, long spocId);
}
