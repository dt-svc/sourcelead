package com.bis.sourcelead.service;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.AddAssociateDTO;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.ImmigrationCandidateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.entity.SubmissionCandidateListView;
import com.bis.sourcelead.entity.User;

/**
 * @author Ajith Matta
 *
*/
public interface CandidateService {
	
	public void storeCandidateImportLog(CandidateImportLog candidateImportLog);
	
	public List<CandidateImportLog> getCandidateImportLog(String uploadId, boolean all);
	
	/**
	 * @param jsonObj
	 * @param orgLocations
	 * @param user
	 * @param multiFile
	 * @return
	 * @throws Exception
	 */
	public Candidate storeCandidate(CandidateDTO candidateDto,OrgLocations orgLocations,User user) throws Exception;
	
	/**
	 * @param candidate
	 * @return
	 * @throws Exception
	 */
	public Candidate addCandidate(Candidate candidate) throws Exception;

	
	/**
	 * @param candidateId
	 * @return
	 * @throws Exception
	 */
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	
	/**
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId,Long orgId,Integer candidateStatus,String updatedDateRange,String candidateType,String isAssociate,String candidateFilterSkills,String availability,String candidateTypeFilter,String createdByFilter,User user) throws Exception;
	
	/**
	 * @param candidateId
	 * @param orgLocationId
	 * @return
	 * @throws Exception
	 */
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	/**
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public Candidate findByCandidateEmail(String email,Long orgLocationId) throws Exception;
	
	/**
	 * @param candidate
	 * @return
	 * @throws Exception
	 */
	public Candidate saveOrUpdateCandidate(Candidate candidate) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId,Long requirementId, String candidateType) throws Exception;

	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId)throws Exception;
	
	public List<Candidate> getAllActiveCandidatesByLocation(Long orgLocationId)throws Exception;

	public File getCandidateExcelFile(List<Candidate> candidatesList, String orgName,String isAssociate)throws Exception;

	public File getCandidatePdf(List<Candidate> candidatesList, String orgName,String isAssociate) throws Exception;
	
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;
	
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId)throws Exception;
	
	public void saveOrUpdateCandidateElkDocument(Candidate candidate) throws Exception;
	
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId, String candidateStatus);
	
	public Long getCandidatesCountByOrgLocationIDAndStatus(Long orgLocationId,String candidateStatus,String isAssociate)throws Exception;
	
	public List<Candidate> getAllActiveAssociatesByOrglocationId(Long orgLocationId, String employeementType) throws Exception;
	
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId,Long orgLocationId) throws Exception;
	
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId,Long orgLocationId)throws Exception;
	
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId,String type)throws Exception;
	
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId)throws Exception;
	
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId,String type,Long orgLocationId)throws Exception;
	
	public Integer getCandidatesCountByLocation(Long orgLocationId)throws Exception;
	
	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId,Integer datRange,Long reportingManagerUserId)throws Exception;

	List<Object> getAllUniversalSearchCandidateList(OrgLocations orgLocation, String searchValue) throws Exception;

	Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId,String candidateType) throws Exception;
	
	Candidate getCandidateByEmailAndRecruiterUserId(String email,Long recruiterUserId) throws Exception;
	
	public Candidate getCandidateById(Long candidateId) throws Exception;
	
	Candidate getCandidateByParentCandidateId(Long parentCandidateId) throws Exception;
	
	Candidate getCandidateByParentCandidateIdAndOrgLocationId(Long parentCandidateId,Long orgLocationId) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationIDAndSearchText(Long orgLocationId,Long requirementId, String candidateType,String searchApplicant) throws Exception;
	
	public Candidate storeProfessionalParseCandidate(CandidateDTO candidateDTO,OrgLocations orgLocations,User user,Long candidateId) throws Exception;

	/*
	 * This Method is to  get the consolidated reports which candidates are created on current Date for individual  user 
	 */
	public Integer getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(OrgLocations orgLocations,User user) throws Exception;

	public Long getCandidatesCountByCandidateTypeOrgLocationID(Long userId,Long orgLocationId,String candidateType);
	
	public List<Candidate> getCandidateReportsListByOrgLocationID(Long orgLocationId,String candidateType) throws Exception;

	List<Candidate> getCandidateReports(Long orgLocationId,String candidateType,String filterDate, Date startDateUTC, Date endDateUTC,String ownersList) throws Exception;
	
	public List<User> getAllCandidateOwnersByLocationIdAndCandidateType(Long orgLocationId,String candidateType)throws Exception;
	
	List<Candidate> getOwnersByCandidates(Long orgLocationId,String candidateType,String ownersList) throws Exception;
	
	List<Candidate> getHotCandidateListByTeamIdAndOrgLocationId(Long teamId,Long orgLocationId) throws Exception;
	
	Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String timePeriod) throws Exception ;

	List<Candidate> getAllActiveCandidatesCountForCampaignByLocationAndStatus(Long orgLocationId)throws Exception;
	
	BigInteger getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;
	
	Candidate getCandidateByUUID(String candidateUUID) throws Exception ;

	public List<Candidate> getAllActiveAssociatesByLocation(Long orgLocationId)throws Exception ;
	
	public List<Candidate> getAllActiveAssociatesByLocationAndAssoicatesList(Long orgLocationId,List<Long> associatesIds)throws Exception ;
	
	public List<Candidate> getAllActiveVendorAssociatesByLocationAndAssoicatesList(Long orgLocationId,Long spocId,List<Long> associatesIds)throws Exception ;
	
	public List<Candidate> getAllActiveVendorAssociatesByLocation(Long orgLocationId,Long spocId)throws Exception ;
	
    public List<Candidate> getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(Long orgLocationId,List<Long> associatesIds)throws Exception ;
	
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocation(Long orgLocationId)throws Exception ;

	public Candidate getCandidateByConsultantUserIdOrgRelationId(Long consultantUserId, Long orgLocationId, Long orgRelationId)throws Exception ;

	public Candidate getCandidateByConsultantUserId(Long consultantUserId, Long orgLocationId)throws Exception;
	
	public Long getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(Long orgLocationId,Long userId,String candidateType) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgLocationIDAndUserId(Long orgLocationId,Long userId) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgLocationIdAndOrgRelationId(Long orgLocationId,Long orgRelationId) throws Exception;
	
	public Candidate storeCandidateForUsers(CandidateDTO candidateDTO,AddUserDTO addUserDTO,User user,OrgLocations orgLocations,MasterCandidate masterCandidate,User loginUser) throws Exception;
	
	public ResponseList<CandidateDTO> getRequestedCandidatesList(CandidateDTO candidateDTO,Long orgLocationsId,List<Long> roleIds,List<Long> teamIds,String resourceType,List<Long> associatesIds,Long projectId)throws Exception ;

	public int getCandidatePercentage(Candidate can, OrgLocations loginOrgLocation) throws Exception;

	public List<Candidate> getAssociatesListForSow(Long locationId, String sowType);

	String getCandidateId(OrgLocations orgLocations);
	
	public ResponseList<CandidateDTO> getCandidateListByOrgLocationIdAndOrgRelationId(Long orgLocationId,Long orgRelationId) throws Exception;
	
	public Candidate getCandidateByShortURL(String shortURL) throws Exception;
	
	List<Candidate> getAllAssociatesByOrglocationIdAndEmploymentType(Long orgLocationId, String employeementType) throws Exception;
	
	public ResponseList<CandidateDTO> getLocationCandidatesList(CandidateDTO candidateDTO,Long orgLocationsId,List<Long> roleIds,List<Long> associatesIds,Long loginOrgLocationId)throws Exception ;
	
	public void saveOrglocationAsssociate(AddAssociateDTO addAssociateDTO, User user, OrgLocations orgLocations,Org org) throws Exception;
	
	Candidate getCandidateByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception;
	
	public Candidate getCandidateByEmail(String email) throws Exception;
	
	 List<RecruiterReportCandidateCountView> getCandidateCountsCreatedUserList(Long orgLocationId, List<String> userCodesList ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;

	 public ResponseList<SubmissionCandidateListView> getSubmissionCandidateListView(CandidateDTO candidateDTO,OrgLocations orgLocations) throws Exception;
	 
	 public CandidateDTO getCandidateDTOByCandidateId(Long candidateId) throws Exception;
	 
	 ResponseList<Candidate> getAllCandidatesByCreatedByUserCode(CandidateDTO candidateDTO,OrgLocations orgLocations,String userCode,Date[] searchDates);
	 
	 public List<CandidateDTO> getCandidateListForAddApplicant(OrgLocations orgLcoations,Long requirementId,String skills,String candidateDetails,Integer profilePercentage) throws Exception;
	 
	 public Candidate saveCandidateBulkImports(CandidateDTO candidateDTO, OrgLocations orgLocations,User user) throws Exception;

	public List<Map<String, BigInteger>> getAllActiveCandidatesByLocationName(Long locationId, String name);
	
	public List<CandidateDTO> getAllActiveCandidatesListByOrglocationId(Long orgLocationId, String employeementType) throws Exception;

	public ResponseList<CandidateMarketPlaceDTO> getGlobalMarketCandidateList(CandidateMarketPlaceDTO candidateMarketPlaceDTO, String searchValue);
	
	public List<Candidate> getAllCandidatesList() throws Exception;

	public void copyCandidateToMarketplace(String candidateIdsStr, User user) throws NumberFormatException, Exception;

	public ResponseList<CandidateDTO> getCandidatesByCandidateIds(List<String> candidateUUIds);
	
	public Candidate getCandidateDetails(Candidate candidate, CandidateDTO candidateDto, User user,OrgLocations orgLocations,StringBuffer activitySummary) throws Exception;
	
	public void saveBulkCandidates(List<Candidate> candidatesList) throws Exception;

	public void saveBulkCandidates(List<Candidate> candidatesList,Long orgLocationId) throws Exception;
	
	Long getCandidateIdByEmailAndOrgLocationId(String email,Long orgLocationId) throws Exception;
	
	List<String> getCandidateEmailsByOrgLocationId(Long orgLocationId) throws Exception;

	public void saveCandidateMarketPlaceList(List<CandidateMarketPlaceDTO> marketPlaceList, User user) throws NumberFormatException, Exception;

	public Map<String, Object[]> getAddedMarketplaceCandidates(List<Long> candidateIdsVal);

	public void updateMarketPlacePrice(Long id, String priceVal, String priceValType);
	
	public CandidateMarketPlace getCandidateMarketPlaceDetailsByIdAndOrgLocationId(Long id, Long orgLocationId)throws Exception;
	
	public void withdrawCandidateFromMarketplace(CandidateMarketPlace marketplace, String withdrawReason)throws Exception;

	public CandidateMarketPlace getCandidateMarketPlaceDetailsByUUId(String uuid)throws Exception;
	
	public void addCandidateToMarketCart(User user, CandidateMarketPlace marketplace, Long orgLocationId)throws Exception;
	
	public List<CandidateMarketPlaceDTO> getGlobalMarketplaceCandListGridCard(String[] searchCandidateIds, OrgLocations orgLocations, String candidateLocation, String searchValue);

	public void deleteCandidateMarketCartByMarketUUID(String candidatgeMarketUUID);
	
	Integer getCandidateProfilePercentageByCandidateId(Long id) throws Exception;

	public Object[] updateMarketplaceCartCountAndPrice(Long orgLocationId, String userRole, User user)throws Exception;
	
	public List<CandidateMarketPlaceCartView> getCandidateDetailsFromMarketcart(User user, String userRole, OrgLocations orgLocations, String creationStartDate, String creationEndDate)throws Exception;
	
	public void deleteCandidateFromMarketcartByCandidateUuid(String candidateUuid)throws Exception;

}
