package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;

public interface CandidateDao {

	
	public Candidate storeCandidate(Candidate candidate) throws Exception;
	
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId,String candidateStatus);
	
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId, Integer candidateState,String updatedDateRange,String candidateType,String isAssociate) throws Exception;
	
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	public Candidate findByCandidateEmail(String email,Long orgLocationId) throws Exception;
	
	public Long getCandidatesCountByOrgLocationIDAndStatus(Long orgLocationId,String candidateStatus,String isAssociate)throws Exception;
	 
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId, String candidateType) throws Exception;

	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId);
	
	public List<Candidate> getAllActiveCandidatesByLocation(Long orgLocationId);
	
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;
	
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId)throws Exception;
	
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId,Long orgLocationId) throws Exception;
	
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId,Long orgLocationId)throws Exception;
	
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId,String type)throws Exception;
	
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId)throws Exception;
	
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId,String type,Long orgLocationId)throws Exception;

	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId,Integer datRange, Long reportingManagerUserId)throws Exception;
	
	Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId,String candidateType) throws Exception;
	
	Candidate getCandidateByEmailAndRecruiterUserId(String email,Long recruiterUserId) throws Exception;
	
	public List<Candidate> getAllActiveAssociatesByOrglocationId(Long orgLocationId,String employeementType) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationIDAndSearchText(Long orgLocationId,Long requirementId, String candidateType,String searchApplicant) throws Exception;
    
	public Long getCandidatesCountByCandidateTypeOrgLocationID(Long userId,Long orgLocationId,String candidateType);
	
	public List<Candidate> getCandidateReportsListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;
	
	List<Candidate> getCandidateReports(Long orgLocationId,String candidateType,String filterDate, Date startDateUTC, Date endDateUTC,String ownersList);
	
	List<Candidate> getHotCandidateListByTeamIdAndOrgLocationId(Long teamId,Long orgLocationId) throws Exception;
	
	List<Candidate> getAllActiveCandidatesCountForCampaignByLocationAndStatus(Long orgLocationId) throws Exception;
	
	List<Candidate> getOwnersByCandidates(Long orgLocationId,String candidateType,String ownersList);

	public Integer getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(OrgLocations orgLocations,
			User user) throws Exception;

	Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String timePeriod) throws Exception ;

	public Candidate getCandidateById(Long candidateId) throws Exception;
	
	Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, Date startDateUTC, Date endDateUTC) throws Exception ;
	
	Candidate getCandidateByUUID(String candidateUUID) throws Exception ;

	public List<Candidate> getAllActiveAssociatesByLocation(Long orgLocationId)throws Exception ;
	
	public List<Candidate> getAllActiveAssociatesByLocationAndAssoicatesList(Long orgLocationId,List<Long> associatesIds)throws Exception ;
	
	public List<Candidate> getAllActiveVendorAssociatesByLocationAndAssoicatesList(Long orgLocationId,Long spocId,List<Long> associatesIds)throws Exception ;
	
	List<Candidate> getAllActiveVendorAssociatesByLocation(Long orgLocationId,Long spocId)throws Exception ;
	
    public List<Candidate> getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(Long vendorOrgRelationId,List<Long> associatesIds)throws Exception ;
	
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocation(Long vendorOrgRelationId)throws Exception ;

	public Candidate getCandidateByConsultantUserIdOrgRelationId(Long consultantUserId, Long orgLocationId, Long orgRelationId)throws Exception ;

	public Candidate getCandidateByConsultantUserId(Long consultantUserId, Long orgLocationId)throws Exception ;
	
	public Long getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(Long orgLocationId,Long userId,String candidateType) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgLocationIDAndUserId(Long orgLocationId,Long userId) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgLocationIdAndOrgRelationId(Long orgLocationId,Long orgRelationId) throws Exception;
	
	public List<Candidate> getRequestedCandidatesList(Long orgLocationsId,List<Long> roleIds,String resourceType)throws Exception ;

}