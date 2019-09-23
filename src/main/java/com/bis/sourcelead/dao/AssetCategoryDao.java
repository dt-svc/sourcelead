package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssetCategory;

public interface AssetCategoryDao {

	
	AssetCategory addAssetCategory(AssetCategory assetCategory) throws Exception;
	
	List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception;
	
	AssetCategory getAssetCategoryListByLocationIdAndName(Long orgLocationId, String name) throws Exception;

	AssetCategory getAssetCategoryListByLocationIdAndCatId(Long catLocationId, Long categoryId);
	
	AssetCategory getAssetCategoryByCategoryIdAndLocationId(String categoryName, Long categoryId, Long orglocationId);
	
	AssetCategory getAssetCategoryByCategoryId(Long categoryId);

	Boolean validateCategoryName(String categoryName, Long orgLocationId);
	
	List<String> getAssetCategoryStringtByLocationId(Long orgLocationId)throws Exception;

}
