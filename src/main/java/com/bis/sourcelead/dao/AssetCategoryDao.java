package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssetCategory;

public interface AssetCategoryDao {

	
	AssetCategory addAssetCategory(AssetCategory assetCategory) throws Exception;
	
	List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception;

	AssetCategory getAssetCategoryListByLocationIdAndCatId(Long catLocationId, Long categoryId);
}
