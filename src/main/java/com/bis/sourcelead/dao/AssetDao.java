package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.AssetAssign;

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

}
