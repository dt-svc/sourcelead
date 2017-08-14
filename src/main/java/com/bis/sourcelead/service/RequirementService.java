package com.bis.sourcelead.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.TeamMembers;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;

/**
 * @author Prasad K
 *
*/
public interface RequirementService {
	
	 
	public Requirement storeRequirement(JSONObject jsonObj,OrgLocations orgLocations,User user,MultipartFile multiFile) throws Exception;
 
	public Requirement addRequirement(Requirement requirement) throws Exception;
	
	public List<Requirement> getRequirementListByOrgLocationID(Long orgLocationId,String status,String clients,User  user, String userRoleName) throws Exception;
	
	public Requirement getRequirementByRequirementId(Long requirementId) throws Exception;
	
	public Boolean getRequirementByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public List<Requirement> getRequirementListByParentRequirementIdAndSpocId(Long requirementId, Long spocId) throws Exception;
	
	public List<Requirement> getJobTitleByJobTemplateStatusAndOrgLocationId(Long orgLocationId)throws Exception;

	public List<RequirementAssignTo> getUserAssignJobCount(Long userId, Long orgLocationId) throws Exception;
	
	public void  approverEmailScenario(User user,User approverUser,Requirement requirement, StringBuilder location,OrgLocations loginOrgLocation) throws Exception;

	public void  approverTwoEmailScenario(User loginUser, User approverUser, Requirement requirement, StringBuilder location,OrgLocations orgLocations) throws Exception;
	
	public void acceptEmailScenario(User loginUser, User approverUser, Requirement requirement,StringBuilder location,OrgLocations orgLocations) throws Exception;
	
	public void mailRequirementtoManager(User loginUser, User intiatorUser, Requirement requirement,StringBuilder location,OrgLocations loginOrgLocation) throws Exception;
	
	public void AssignToTeamMembersEmail(User loginUser, User managerUser, Requirement requirement,StringBuilder location,OrgLocations orgLocations) throws Exception;

	void rejectedEmailScenario(User loginUser, User initiatorUser, Requirement requirement,StringBuilder location,String rejectedReason,OrgLocations loginOrgLocation) throws Exception;

	void releaseMailScenario(User loginUser, Requirement requirement, OrgSpoc orgSpoc, OrgLocations loginOrgLocation,String releasetype) throws Exception;

	public Requirement getRequirementByParentRequirementId(Long requirementId) throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(Long orgLocationId, String userType, Long userId, String userCode) throws Exception;
	
	public List<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId, String userType, Long userId, String userCode,String statusType) throws Exception;
	
	public List<Requirement> getAssignedRequirementApproverListByOrgLocationIDAndUserId(Long orgLocationId,Long  userId) throws Exception;

	public List<Candidate> getRecommendationsWithScore(List<Candidate> candidateList, Requirement requirement)throws Exception;

	public List<Requirement> getRequirementListById(Long requirementId) throws Exception;
	
	public Boolean checkRequirementIsReleasedOrNot(Long requirementId,Long orgLocationId) throws Exception;
	
	Requirement validateExistingJobId(String jobId,Long requirementId, Long orgLocationId) throws Exception;
	
	Integer validateJobId(String id, Long orgLocation) throws Exception;

	public File getExportRequirementFile(List<Requirement> requirementList, String orgName, String exportType, TimeSheetSubmissionCount requirementSubCount)throws Exception;

	public Boolean validateRequirementTitle(String jobName, Long orgLocationId) throws Exception;
	
	Boolean checkLoginUserIsRequirementManagerByRequirementIdAndUserId(Long requirementId,Long userId)  throws Exception;

	public List<Requirement> getAllReleasedJobsForVendor(Long orgLocationId, Long orgRelationId, User user, OrgLocations orgLocations) throws Exception;
    
	List<Requirement> getAllRequirementTeplateJobsByOrgLocationId(Long orgLocationId, User user) throws Exception;
	
	public Requirement storeRequirementTemplate(JSONObject jsonObj,OrgLocations orgLocations,User user) throws Exception;
	
	public Requirement getRequirementTemplateByRequirementId(Long requirementId,Long orgLocationId) throws Exception;
	
	public Requirement updateRequirementTemplate(Requirement requirement) throws Exception;
	
	Integer getManagerAssignedRequirementsCount(Long userId,Long orgLocationId) throws Exception;
	
	public Integer getRequirementsCountAsApprover(Long userId,Long orgLocationId) throws Exception;
	
	public List<Requirement> getManagerAssignedRequirementsList(Long userId,Long orgLocationId) throws Exception;

	Boolean checkLoginUserAsRequirementManagerAndSubmissionStatusAsRequestOrAccepted(Long userId,Long orgLocationId)  throws Exception;
	
	public Requirement getRequirementByRequirementIdOrgLocationsId(String id,Long requirementId, Long orgLocationsId) throws Exception;
	
	public Boolean validateJobid(String id, Long orgLocationsId) throws Exception;
	
	public Requirement getRequirementByRequirementUUID(String requirementUUID) throws Exception;
	
	List<String> getAllRequirementSkillsByRequirementI(Long requirementId) throws Exception;
	
	List<String> getAllRequirementIndustryByRequirementId(Long requirementId) throws Exception;

	public Long getRequirementsListById(Long requirementId) throws Exception;
	

	public Requirement updateRequirement(Requirement requirement) throws Exception;

	public List<Requirement> getAllRequirementsByOrglocationId(Long orgLocationId)throws Exception;
	
	public List<Requirement> getAllReleasedRequirementsByParentId(Long parentRequirementId)throws Exception;

	public String getNoticePeriodByRequirementId(Long requirementId) throws Exception;
	
	// This method is if the user is requirement manager or approver or assignTo or creator   this method is for consolidated reports 
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserId(Long orgLocationId,User user)throws Exception;
		
	// This method is if the user is TeamLead this method is used for  consolidated reports
	public List<Requirement> getAllRequirementsByOrglocationIdAndUserIdAsTeamLead(Long orgLocationId ,User user)throws Exception;

	// This method is for  getting  the released requirements based on the user and orglocatinsId for consolidates reports for individual 
	
	Integer getAllRequirementsByOrglocationIdAndUserIdReleasedByUser(Long orgLocationId, User user)	throws Exception ; 

	public List<Requirement> getAllRequirementsByOrglocationIdAndRequirementWhoAreAssignedToRequirement(Long orgLocationId, TeamMembers teamMembers)	throws Exception ; 
	
	Long getCountOfAllRequirementsByCreatedUserAndOrglocationId(Long orgLocationId, User user, Date startDateUTC, Date endDateUTC) throws Exception ;

	public List<Requirement> getTotalRequirementsReleasedToSpoc(Long relationId, Long spocId, Date startDateUTC, Date endDateUTC);
	
	public List<Requirement> getRequirementsForSOWByOrgLocationId(Long orgLocationId, String sowType) throws Exception;

	public List<String> getAllRequirementEducationByRequirementId(Long requirementId);
	
	Requirement getRequirementDetailsById(Long requirementId) throws Exception;
	
	public List<Requirement> getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(Long orgLocationId, String userType, Long userId, String userCode, String numberOfDays) throws Exception;
	
	public List<Requirement> getAllReleasedJobsForVendorBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId, Date startDateUTC, Date endDateUTC) throws Exception;

	public List<Requirement> getAllReleasedJobsBasedOnUserIdAndLocationId(User user, OrgLocations orgLocations)	throws Exception ;

	public List<Requirement> getAllReleasedJobsBasedOnParetRequirementIdAndVendorLocationId(List<Long> parentreqIds,
			Long relationId) throws Exception; 
	
	public List<Requirement> getAllClientJobsBasedonRelationId(Long locationId,Long relationId,String userRoleName,User user,String status)throws Exception;

	
	public List<Requirement> getAllClosedRequirementsByStartDateAndEndDate(Date startDateUTC, Date endDateUTC) throws Exception;
	
	public String getRequirementId(OrgLocations orgLocations);
	
	public List<Requirement> getAllVendorJobsBasedonRelationId(Long locationId,Long relationId, String userRoleName,User user,String status);

	public List<String> getAllRequirementVisaByRequirementI(Long requirementId)throws Exception;
	
}
