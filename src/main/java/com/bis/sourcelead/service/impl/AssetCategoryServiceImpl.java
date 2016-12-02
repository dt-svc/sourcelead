package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssetCategoryDao;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssetCategoryService;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService{

	@Autowired
	AssetCategoryDao assetCategoryDao;
	
	@Override
	public AssetCategory addAssetCategory(JSONObject jsonObj,OrgLocations orgLocation,User user,Long categoryId,Long catLocationId) throws Exception {
		AssetCategory assetCategory=null;
		
		if(categoryId !=null){
			 assetCategory = assetCategoryDao.getAssetCategoryListByLocationIdAndCatId(catLocationId, categoryId);
			 assetCategory.setId(categoryId);
		}
		else{
			assetCategory = new AssetCategory();
			assetCategory.setCreatedDate(new Date());
			assetCategory.setCreatedBy(user.getUserCode());
		}
		assetCategory.setCategoryName(jsonObj.getString("categoryName"));
		if(jsonObj.has("description") && !isNullOrEmpty(jsonObj.getString("description"))){
			assetCategory.setDescription(jsonObj.getString("description"));
		}
		assetCategory.setStatus(Constants.RECORD_ACTIVE);
		assetCategory.setDefaultCategory(Constants.DEFAULT_CATEGORY);
		assetCategory.setOrgLocations(orgLocation);
		assetCategory.setUpdatedBy(user.getUserCode());
		assetCategory.setUpdatedDate(new Date());
		
		return assetCategoryDao.addAssetCategory(assetCategory);
	}
	
	@Override
	public List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception {
		return assetCategoryDao.getAssetCategoryListByLocationId(orgLocationId);
	}

	/**
	 * check given string empty or null
	 * @param str
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

	@Override
	public AssetCategory getAssetCategoryListByLocationIdAndCatId(Long catLocationId, Long categoryId) {
		return assetCategoryDao.getAssetCategoryListByLocationIdAndCatId(catLocationId,categoryId);
	}

}
