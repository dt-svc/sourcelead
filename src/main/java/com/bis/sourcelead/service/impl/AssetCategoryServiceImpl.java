package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssetCategoryDao;
import com.bis.sourcelead.dto.AssetCategoryDTO;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssetCategoryService;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService{

	@Autowired
	AssetCategoryDao assetCategoryDao;
	
	@Override
	public AssetCategory addAssetCategory(AssetCategoryDTO assetCategoryDTO,OrgLocations orgLocation,User user,String categoryId,String catLocationId) throws Exception {
		AssetCategory assetCategory=null;
		
		if(!categoryId.equalsIgnoreCase("null")){
			 assetCategory = assetCategoryDao.getAssetCategoryListByLocationIdAndCatId(Long.parseLong(catLocationId), Long.parseLong(categoryId));
			 assetCategory.setId(Long.parseLong(categoryId));
		}
		else{
			assetCategory = new AssetCategory();
			assetCategory.setCreatedDate(new Date());
			assetCategory.setCreatedBy(user.getUserCode());
		}
		assetCategory.setCategoryName(assetCategoryDTO.getCategoryName());
		if(assetCategoryDTO.getCategoryName() != null){
			assetCategory.setDescription(assetCategoryDTO.getDescription());
		}
		assetCategory.setStatus(Constants.RECORD_ACTIVE);
		if(assetCategory !=null && assetCategory.getDefaultCategory()!=Constants.RECORD_ACTIVE){
			assetCategory.setDefaultCategory(Constants.DEFAULT_CATEGORY);
		}
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

	@Override
	public Boolean validateCategoryName(String categoryName, Long orgLocationId) {
		return assetCategoryDao.validateCategoryName(categoryName, orgLocationId);
	}

	@Override
	public AssetCategory addAssetCategory(AssetCategory assetCategory) throws Exception {
		return assetCategoryDao.addAssetCategory(assetCategory);
		
	}

	@Override
	public AssetCategory getAssetCategoryListByLocationIdAndName(Long orgLocationId, String name)throws Exception {
		return assetCategoryDao.getAssetCategoryListByLocationIdAndName(orgLocationId, name);
	}

	@Override
	public AssetCategory getAssetCategoryByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		return assetCategoryDao.getAssetCategoryByCategoryId(categoryId);
	}

	@Override
	public AssetCategory getAssetCategoryByCategoryIdAndLocationId(String categoryName, Long categoryId,
			Long orglocationId) {
		return assetCategoryDao.getAssetCategoryByCategoryIdAndLocationId(categoryName,categoryId,orglocationId);
	}

	@Override
	public List<String> getAssetCategoryStringtByLocationId(Long orgLocationId) throws Exception {
		return assetCategoryDao.getAssetCategoryStringtByLocationId(orgLocationId);
	}

}
