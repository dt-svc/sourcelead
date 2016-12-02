package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Asset;

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
	
	/**
	 * 
	 * @param orgLocationId
	 * @param categoryIds
	 * @return
	 * @throws Exception
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds) throws Exception;
	
	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;
}
