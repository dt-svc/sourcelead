package com.bis.sourcelead.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.CandidateSourceDTO;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.MarketplaceWalletDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateIdendities;
import com.bis.sourcelead.entity.CandidateImportLog;
import com.bis.sourcelead.entity.CandidateMarketCart;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.CandidateMarketWallet;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.CandidateSource;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.MarketplaceSalesOrder;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecruiterReportCandidateCountView;
import com.bis.sourcelead.entity.SourceleadDeletedCandidates;
import com.bis.sourcelead.entity.SubmissionCandidateListView;
import com.bis.sourcelead.entity.User;

public interface CandidateDao {
	public Candidate storeCandidate(Candidate candidate) throws Exception;
	
	public Candidate getCandidateByCandidateId(Long candidateId) throws Exception;
	
	Candidate getCandidateByParentCandidateId(Long parentCandidateId) throws Exception;
	
	Candidate getCandidateByParentCandidateIdAndOrgLocationId(Long parentCandidateId,Long orgLocationId) throws Exception;
	
	public Long getCandidatesCountByOrgLocationID(Long orgLocationId, String candidateStatus);
	
	public List<Candidate> getCandidatesListByOrgLocationID(Long orgLocationId, Long orgId, Integer candidateState, String updatedDateRange, String candidateType, String isAssociate,String candidateFilterSkills,String availability,String candidateTypeFilter,String createdByFilter,User user) throws Exception;
	
	public Candidate getCandidateByCandidateIdAndOrgLocationId(Long candidateId, Long orgLocationId) throws Exception;
	
	public Candidate findByCandidateEmail(String email, Long orgLocationId) throws Exception;
	
	public Long getCandidatesCountByOrgLocationIDAndStatus(Long orgLocationId, String candidateStatus, String isAssociate) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationID(Long orgLocationId, Long requirementId, String candidateType) throws Exception;
	
	public List<Candidate> getAllCandidatesByLocation(Long orgLocationId);
	
	public ResponseList<Candidate> getAllCandidatesByCreatedByUserCode(CandidateDTO candidateDTO,OrgLocations orgLocations,String userCode,Date[] searchDates);
	
	public List<Candidate> getAllActiveCandidatesByLocation(Long orgLocationId);
	
	public List<Candidate> getHotListCandidatesListByOrgLocationID(Long orgLocationId, String candidateType) throws Exception;
	
	public List<Candidate> getAllRequestedCandidatesForCampaign(List<Long> candidateId) throws Exception;
	
	public Integer getHotlistCandidatesCountByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId) throws Exception;
	
	public List<Candidate> getHotListCandidatesByTeamAndOrgLocationId(Long teamId, Long orgLocationId) throws Exception;
	
	public Integer getAllActiveCandidatesCountForCampaignByLocation(Long orgLocationId, String type) throws Exception;
	
	public Integer getAllHotlistCandidatesCountNotAssignedToTeamByLocation(Long orgLocationId) throws Exception;
	
	public Integer getAllCandidatesCountForCampaignByTeam(List<Long> teamId, String type, Long orgLocationId) throws Exception;
	
	public List<Candidate> getAllCandidatesForHotListingWhoAreTerminating(Long orgLocationId, Integer datRange, Long reportingManagerUserId) throws Exception;
	
	Candidate getProfessionalInfoByLoginUserIdAndCandidateType(Long userId, String candidateType) throws Exception;
	
	public Integer getCandidatesCountByLocation(Long orgLocationId)throws Exception;
	
	Candidate getCandidateByEmailAndRecruiterUserId(String email, Long recruiterUserId) throws Exception;
	
	public List<Candidate> getAllActiveAssociatesByOrglocationId(Long orgLocationId, String employeementType) throws Exception;
	
	public List<Candidate> getCandidatesByOrgLocationIDAndSearchText(Long orgLocationId, Long requirementId, String candidateType, String searchApplicant) throws Exception;
	
	public Long getCandidatesCountByCandidateTypeOrgLocationID(Long userId, Long orgLocationId, String candidateType);
	
	public List<Candidate> getCandidateReportsListByOrgLocationID(Long orgLocationId, String candidateType) throws Exception;
	
	List<Candidate> getCandidateReports(Long orgLocationId, String candidateType, String filterDate, Date startDateUTC, Date endDateUTC, String ownersList);
	
	List<Candidate> getHotCandidateListByTeamIdAndOrgLocationId(Long teamId, Long orgLocationId) throws Exception;
	
	List<Candidate> getAllActiveCandidatesCountForCampaignByLocationAndStatus(Long orgLocationId) throws Exception;
	
	List<Candidate> getOwnersByCandidates(Long orgLocationId, String candidateType, String ownersList);
	
	public Integer getAllCandidatedsBasedOnCreatedByAndOrglocationCodeWhichAreCreatedToday(OrgLocations orgLocations, User user) throws Exception;
	
	Long getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String timePeriod) throws Exception;
	
	public Candidate getCandidateById(Long candidateId) throws Exception;
	
	BigInteger getCountOfAllCandidatesByCreatedUserAndOrglocationId(Long orgLocationId, User user, String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception;
	
	Candidate getCandidateByUUID(String candidateUUID) throws Exception;
	
	public List<Candidate> getAllActiveAssociatesByLocation(Long orgLocationId) throws Exception;
	
	public List<Candidate> getAllActiveAssociatesByLocationAndAssoicatesList(Long orgLocationId, List<Long> associatesIds) throws Exception;
	
	public List<Candidate> getAllActiveVendorAssociatesByLocationAndAssoicatesList(Long orgLocationId, Long spocId, List<Long> associatesIds) throws Exception;
	
	List<Candidate> getAllActiveVendorAssociatesByLocation(Long orgLocationId, Long spocId) throws Exception;
	
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocationAndAssoicatesList(Long vendorOrgRelationId, List<Long> associatesIds) throws Exception;
	
	public List<Candidate> getAllActiveVendorAssociatesByOrgLocation(Long vendorOrgRelationId) throws Exception;
	
	public Candidate getCandidateByConsultantUserIdOrgRelationId(Long consultantUserId, Long orgLocationId, Long orgRelationId) throws Exception;
	
	public Candidate getCandidateByConsultantUserId(Long consultantUserId, Long orgLocationId) throws Exception;
	
	public Long getCandidatesCountByCandidateTypeOrgLocationIDAndUserId(Long orgLocationId, Long userId, String candidateType) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgLocationIDAndUserId(Long orgLocationId, Long userId) throws Exception;
	
	public List<Candidate> getCandidatesListByOrgLocationIdAndOrgRelationId(Long orgLocationId, Long orgRelationId) throws Exception;
	
	public ResponseList<CandidateDTO>  getRequestedCandidatesList(CandidateDTO candidateDTO,Long orgLocationsId, List<Long> roleIds,List<Long> teamIds, String resourceType, List<Long> associatesIds, Long projectId) throws Exception;
	
	public Candidate checkCandidateIdNumberInDatebase(Long orgLocationId);
	
	public List<Candidate> getAssociatesListForSow(Long locationId, String sowType);
	
	Long getCandidateGeneratedIdByOrgLocationId(Long orgLocationId, Integer generatedIdcount);
	
	public void storeCandidateImportLog(CandidateImportLog candidateImportLog);
	
	public List<CandidateImportLog> getCandidateImportLog(String uploadId, boolean all);
	
	public Candidate getCandidateByShortURL(String shortURL);
	
	List<Candidate> getAllAssociatesByOrglocationIdAndEmploymentType(Long orgLocationId, String employeementType) throws Exception;
	
	public ResponseList<CandidateDTO> getLocationCandidatesList(CandidateDTO candidateDTO,Long orgLocationsId,List<Long> roleIds,List<Long> associatesIds,Long loginOrgLocationId)throws Exception ;
	
	Candidate getCandidateByEmailAndOrgLocationId(String email, Long orgLocationId) throws Exception;
	
	public Candidate getCandidateByEmail(String email) throws Exception;

	public CandidateIdendities getCandidateIdentitiesNumberByCandidateId(Long id, String status);

	Candidate getAllActiveCandidatesByLocationEmail(String email, Long orgLocationId);
	
	List<RecruiterReportCandidateCountView> getCandidateCountsCreatedUserList(Long orgLocationId, List<String> userCodesList ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;
	
	public ResponseList<SubmissionCandidateListView> getSubmissionCandidateListView(CandidateDTO candidateDTO,OrgLocations orgLcoations) throws Exception;
	
	public CandidateDTO getCandidateDTOByCandidateId(Long candidateId) throws Exception;
	
	public List<CandidateDTO> getCandidateListForAddApplicant(OrgLocations orgLcoations,Long requirementId,String skills,String candidateDetails,Integer profilePercentage) throws Exception;

	List<Map<String, BigInteger>> getAllActiveCandidatesByLocationName(Long orgLocationId, String name);
	
	public List<CandidateDTO> getAllActiveCandidatesListByOrglocationId(Long orgLocationId, String employeementType) throws Exception;

	public ResponseList<CandidateMarketPlaceDTO> getGlobalMarketCandidateList(CandidateMarketPlaceDTO candidateMarketPlaceDTO, String searchValue);
	
	public List<Candidate> getAllCandidatesList() throws Exception;

	public void saveMarketPlace(CandidateMarketPlace marketPlace) throws Exception;

	public ResponseList<CandidateDTO> getCandidatesByCandidateIds(List<String> candidateIdsStr);
	
	Long getCandidateIdByEmailAndOrgLocationId(String email,Long orgLocationId) throws Exception;
	
	public void saveBulkCandidates(List<Candidate> candidatesList,Long orgLocationId) throws Exception;
	
	List<String> getCandidateEmailsByOrgLocationId(Long orgLocationId) throws Exception;

	public List<Long> getAddedMarketplaceCandidates(List<Long> candidateIdsVal);

	public CandidateMarketPlace getCandidateMarketPlaceByCandidateId(Long candidateId);

	public CandidateMarketPlace getCandidateMarketPlaceById(Long id);

	public void updateMarketPlacePrice(CandidateMarketPlace marketPlace);
	
	public CandidateMarketPlace getCandidateMarketPlaceDetailsByIdAndOrgLocationId(Long id, Long orgLocationId)throws Exception;
	
	public List<CandidateMarketPlace> getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(List<Long> id, Long orgLocationId)throws Exception;
	
	public void withdrawCandidateFromMarketplace(CandidateMarketPlace marketplace)throws Exception;
	
	public CandidateMarketPlace getCandidateMarketPlaceDetailsByUUId(String uuid)throws Exception;
	
	public int updateMarketplaceCandidates(OrgLocations orgLocations,String companyNameFlag) throws Exception;
	
	public void addCandidateToMarketCart(CandidateMarketCart marketcart)throws Exception;

	public List<CandidateMarketPlace> getGlobalMarketplaceCandListGridCard(String[] searchCandidateIds, Long orgId, String candidateLocation, String searchValue, CandidateMarketPlaceDTO candidateMarketPlaceDTO, Map<String, Date> dateValue,Date[] checkedDateArray);

	public int getMarketCartStatusByMarketIdOrgId(Long marketPlaceId, Long orgId);

	public void deleteCandidateMarketCartByMarketUUID(String candidatgeMarketUUID);
	
	Integer getCandidateProfilePercentageByCandidateId(Long id) throws Exception;
	
	public Object[] updateMarketplaceCartCountAndPrice(Long orgLocationId, String userRole, User user)throws Exception;
	
	public List<CandidateMarketPlaceCartView> getCandidateDetailsFromMarketcart(User user, String userRole, OrgLocations orgLocation, String creationStartDate, String creationEndDate, Date[] checkedDateArray, String searchValue)throws Exception;
	
	public Boolean deleteCandidateFromMarketcartByCandidateUuid(String candidateUuid)throws Exception;

	public ResponseList<CandidateMarketPlaceDTO> getGlobalDataRecommendations(
			CandidateMarketPlaceDTO candidateMarketPlaceDTO, OrgLocations orgLocations, String searchValue);
	
	public List<CandidateMarketCart> getMarketCartIdsFromMarketcart(Long orgLocationId)throws Exception;
	
	List<CandidateMarketPlace> getGlobalMarketplaceDataByOrgId(Long id, Long marketplaceId);

	public List<CandidateDTO> getCandidatesListByIds(List<Long> Ids)throws Exception;
	
	public Boolean updateMarketCartStatus(List<Long> marketCartIds, Long orgLocationId)throws Exception;
	
	public void saveCandidateMarketplacePaymentTranscationHistory(CandidateMarketPlacePaymentTransactionHistory tranHistory)throws Exception;
	
	public CandidateMarketPlacePaymentTransactionHistory getMarketplaceTransactionDetails(Long orgLocationId) throws Exception;
	
	public User getUserDetailsBySourceOrgLocationId(Long sourceOrgLocationId)throws Exception;
	
	public CandidateMarketPlace getCandidateDetailsByCandidateId(Long candidateId) throws Exception;

	public void saveToMarketplacePurchaseOrder(MarketplacePurchaseOrder newMarketplacePurchaseorder) throws Exception;

	public List<Candidate> getCandidateDetailsListById(List<Long> Ids)throws Exception;
	
	public Candidate getCandidateByMasterCandidateId(Long masterCandidateId);

	public void saveToMarketplaceSalesOrder(MarketplaceSalesOrder newMarketplaceSalesorder) throws Exception;

	public CandidateMarketPlacePaymentTransactionHistory getOrderNo(Long id) throws Exception;

	public void deleteAllCandidatesFromMarketcart(Long orgLocationId)throws Exception;
	
	public ResponseList<MarketplacePurchaseSalesDTO> getMarketplacePurchaseOrder( Long id,MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO,OrgLocations orgLocations,String searchValue, String purchaseOrderFromDate, String purchaseOrderToDate, Map<String, Date> dateValue)throws Exception;
	
	public MarketplacePurchaseOrder getPurchaseDetailByPurchaseUuid(String purchaseUuid, Long id)throws Exception;
	
	public ResponseList<MarketplacePurchaseSalesDTO> getMarketplaceSalesOrderList(MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO, OrgLocations orgLocations, String searchValue, String salesOrderFromDate, String salesOrderToDate, Map<String, Date> dateValue)throws Exception;
	
	public MarketplaceSalesOrder getMarketplaceSalesOrderBySalesUuid(Long orgLocationId, String salesUuid)throws Exception;
	
	public ResponseList<MarketplaceWalletDTO> getWalletList(MarketplaceWalletDTO marketplaceWalletDTO, String searchValue, String transactionType, Map<String, Date> dateValue);

	public List<MarketplacePurchaseOrder> getPurchaseCandidateListByOrglocation(Long id)throws Exception;

	public void saveCandidateMarketPlaceWallet(CandidateMarketWallet wallet) throws Exception;

	public Double getBalanceAmountFromWallet(Long id);

	public void updateMarketWalletAmount(Double balanceAmountValue, Long id);
	
	public CandidateMarketCart getCandidateFromMarketCartByMarketplaceId(Long marketplaceId)throws Exception;
	
	public void deleteCandidateFromMarketplaceByCandidateUuid(String candidateUuid)throws Exception;
	
	public List<Candidate> getAllAssociateByLocation(Long orgLocationId);
	
	public String getCandidatePanNumberByUserId(long userId, Long id);
	
    public Candidate getCandidateDetailsByPanNumberAndId(String panNumber, Long orgLocationId) throws Exception;
	
	public Boolean validatePanNumber(String panNumber, Long orgLocationId) throws Exception;
	
	public Candidate getCandidateDetailsByAadharNoAndId(String aadharNumber, Long orgLocationId) throws Exception;
	
	public Boolean validateAadharNumber(String aadharNumber, Long orgLocationId) throws Exception;
	
	public List<Candidate> getCandidatesForUpdateWorkLocation(Long masterCandidateId,Long orgId) throws Exception;
	
    public Candidate getCandidateDetailsBySsnNoAndId(String ssnNumber, Long orgLocationId) throws Exception;
	
	public Boolean validateSsnNumber(String ssnNumber, Long orgLocationId) throws Exception;
	
	public String getCandidateDateOfBirthByUserId(long userId, OrgLocations orgLocation);
	
	public User getConsultantUserByCandidateIdOrMasterCandidateId(Long candidateId,Long masterCandidateId) throws Exception;

	public CandidateVerificationConfig saveDuplicateCheckConfig(CandidateVerificationConfig candidateVerificationConfig);

	public CandidateVerificationConfig getCandidateVerificationConfigBy(Long orgLocationId);

	public CandidateVerificationConfig getCandidateVerificationConfigByIdAndOrglocationId(Long orgLocationId, Long id);

	public List<CandidateSource> getCandidateSourceListByOrgLocationId(Long orgLocationId, int status, String searchValue,CandidateSourceDTO candidateSourceDTO);

	public long saveCandidateSource(CandidateSource candidateSource);

	public CandidateSource getCandidateSourceById(Long id);

	public long getCandidateSourceByOrgLocationId(String name,long userId, long orgLocationId);

	public boolean deleteCandidateSourceById(long id);

	public void saveSourceleadDeletedCandidate(SourceleadDeletedCandidates sourceleadDeletedCandidates) throws Exception;
}