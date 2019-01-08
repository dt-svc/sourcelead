package com.bis.sourcelead.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.AssetServiceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssetServices;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.AssetGrowthDTO;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;

public interface AssetService {

	Asset saveAsset(Asset asset) throws Exception;
	
	/**
	 * @author Prasad salina
	 * @param Id
	 * @return
	 * @throws Exception
	 * To get the asset by Id.
	 * This method used in BackOfficeRestController
	 */
	Asset getAssetById(Long Id) throws Exception;
	
	Asset getAssetByUUID(String  assetUUID) throws Exception;
	
	public Asset getAssetByAssetSnoAndId(String assetSno, Long assetId, Long orgLocationsId) throws Exception;
	
	public Asset validateAssetSno(String assetSno, Long orgLocationsId) throws Exception;
	
	public Boolean validateAssetId(String assetSno, Long orgLocationsId) throws Exception;

	
	/**
	 * 
	 * @param orgLocationId
	 * @param categoryIds
	 * @param workingStatus 
	 * @return
	 * @throws Exception
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds, String workingStatus) throws Exception;
	
	ResponseList<Asset> getDynamicAssetFilterListByLocationId(Long orgLocationId,AssetDTO assetDTO) throws Exception;
	
	ResponseList<AssetAssign> getDynamicAssignAssetFilterListByLocationId(Long orgLocationId,AssetDTO assetDTO) throws Exception;

	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;
	
	public Asset saveAssetDetails(AssetDTO assetDTO,User user,OrgLocations orgLocations) throws Exception;
	
	List<AssetAssign> getUserBasedAssetManagementListandOrgLocation(Long orgLocationId, Long UserId) throws Exception;
	
	List<AssetAssign> getAssignAssetListByAssetId(Long assetId);
	
	AssetAssign saveAssetAssign(AssetAssign assetAssign) throws Exception;
	
	AssetAssign getAssignAssetByAssetId(Long assetId)throws Exception;
	
	List<AssetAssign> getAssignAssetListByShiftId(Long shiftId)throws Exception;
	
	void deleteAssignAssetByAssignId(AssetAssign assetAssign) throws Exception;
	
	List<AssetAssign> getReturnedOnAssignAssetListByAssetId(Long assetId);
	
	List<AssetAssign> getAllAssignAssetListByAssetId(Long assetId);
	
	AssetServices saveAssetService(AssetServices assetServices) throws Exception;
	
	List<AssetServices> getAssetServicesListByAssetId(Long assetId)throws Exception; 
	
	public AssetServices saveAssetServiceDetails(AssetServiceDTO assetServiceDTO,OrgLocations orgLocations) throws Exception;
	
	List<AssetGrowthDTO> getAssetGrowthListByOrgloationIdAndAssetGrowthDTO(Long orgLocationId,AssetGrowthDTO assetGrowthDTO,Date[] searchDates)throws Exception;
	
	public Boolean validateAssignAssetByAssetIdAndShiftIdAnsAssignUserId(Long assetId, Long shiftId, Long userRoleId) throws Exception;
	

	List<Asset> getAllAssetByOrgId(Long orgLocationId) throws Exception;

	ResponseList<AssetsReportDTO> getAssetReportList(AssetsReportDTO assetsReportDTO, Long id)throws Exception;

	File getAssetsReportListOfExcel(List<AssetsReportDTO> data, User user, OrgLocations orgLocation,String isGlobalAccess) throws FileNotFoundException;

	void deleteAsset(Asset asset)throws Exception;
}
