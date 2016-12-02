package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Asset;

public interface AssetDao {
	
	Asset saveAsset(Asset asset) throws Exception;
	
	/**
	 * @author Prasad salina
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds) throws Exception;
	
	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;

}
