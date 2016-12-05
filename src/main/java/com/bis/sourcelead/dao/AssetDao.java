package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Asset;

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
	
	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;

}
