package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;
import com.bis.sourcelead.entity.AssetServices;
import com.bis.sourcelead.reportsDTO.AssetGrowthDTO;
import com.bis.sourcelead.reportsDTO.AssetsReportDTO;
import com.bis.sourcelead.service.AssetService;

public interface AssetDao {
	
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
	
	Asset getAssetByUUID(String assetUUID) throws Exception;
	
	List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds, String workingStatus) throws Exception;
	
	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;
	
	ResponseList<Asset> getDynamicAssetFilterListByLocationId(Long orgLocationId,AssetDTO assetDTO) throws Exception;
	
	ResponseList<AssetAssign> getDynamicAssignAssetFilterListByLocationId(Long orgLocationId,AssetDTO assetDTO) throws Exception;

	List<AssetAssign> getUserBasedAssetManagementListandOrgLocation(Long orgLocationId, Long UserId) throws Exception;
	
	public Asset getAssetByAssetSnoAndId(String assetSno, Long assetId, Long orgLocationsId) throws Exception;
	
	public Asset validateAssetSno(String assetSno, Long orgLocationsId) throws Exception;
	
	public Boolean validateAssetId(String assetSno, Long orgLocationsId) throws Exception;
	
	List<AssetAssign> getAssignAssetListByAssetId(Long assetId);
	
	List<AssetAssign> getReturnedOnAssignAssetListByAssetId(Long assetId);
	
	AssetAssign saveAssetAssign(AssetAssign assetAssign) throws Exception;

	AssetAssign getAssignAssetByAssetId(Long assetId)throws Exception;
	
	AssetAssign getAssignAssetById(Long id)throws Exception;
	
	List<AssetAssign> getAssignAssetListByShiftId(Long shiftId)throws Exception;

	void deleteAssignAssetByAssignId(AssetAssign assetAssign) throws Exception;
	
	List<AssetAssign> getAllAssignAssetListByAssetId(Long assetId);

	List<AssetServices> getAssetServicesListByAssetId(Long assetId)throws Exception; 

	AssetServices saveAssetService(AssetServices assetServices) throws Exception;

	List<AssetGrowthDTO> getAssetGrowthListByOrgloationIdAndAssetGrowthDTO(Long orgLocationId,AssetGrowthDTO assetGrowthDTO,Date[] searchDates)throws Exception;


	public Boolean validateAssignAssetByAssetIdAndShiftIdAnsAssignUserId(Long assetId, Long shiftId, Long userRoleId) throws Exception;

	List<Asset> getAllAssetByOrgId(Long orgLocationsId);

	List<AssetAssign> getAssetsReportList(AssetsReportDTO assetsReportDTO, Long orgLocationId);

	void deleteAsset(Asset asset) throws Exception;

}
