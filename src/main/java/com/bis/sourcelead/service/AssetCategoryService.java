package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.AssetCategoryDTO;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssetCategoryService {

	AssetCategory addAssetCategory(AssetCategoryDTO assetCategoryDTO, OrgLocations orgLocation, User user, String categoryId, String catLocationId) throws Exception;
	
	List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception;
	
	AssetCategory getAssetCategoryListByLocationIdAndName(Long orgLocationId,String Name) throws Exception;

	AssetCategory getAssetCategoryListByLocationIdAndCatId(Long catLocationId, Long categoryId);
	
	AssetCategory getAssetCategoryByCategoryId(Long categoryId);
	
	AssetCategory getAssetCategoryByCategoryIdAndLocationId(String categoryName, Long categoryId, Long orglocationId);

	Boolean validateCategoryName(String categoryName, Long orgLocationId);

	AssetCategory addAssetCategory(AssetCategory assetCategory) throws Exception;

	List<String> getAssetCategoryStringtByLocationId(Long orgLocationId)throws Exception;
}
