package com.bis.sourcelead.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.dto.AssetDTO;
import com.bis.sourcelead.entity.Asset;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

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
	 * @param workingStatus 
	 * @return
	 * @throws Exception
	 * To get the asset list with filters.
	 * This method used in BackOfficeRestController
	 */
	List<Asset> getAssetFilterListByLocationId(Long orgLocationId, String categoryIds, String workingStatus) throws Exception;
	
	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;
	
	public void saveAssetDetails(AssetDTO assetDTO,String assetId, User user,OrgLocations orgLocations) throws Exception;
}
