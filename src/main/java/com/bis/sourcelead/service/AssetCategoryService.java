package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssetCategoryService {

	AssetCategory addAssetCategory(JSONObject jsonObj, OrgLocations orgLocation, User user, Long categoryId, Long catLocationId) throws Exception;
	
	List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception;

	AssetCategory getAssetCategoryListByLocationIdAndCatId(Long catLocationId, Long categoryId);
}
