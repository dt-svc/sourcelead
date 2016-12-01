package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.AssetCategory;

public interface AssetCategoryService {

	AssetCategory addAssetCategory(AssetCategory assetCategory) throws Exception;
	
	List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception;
}
