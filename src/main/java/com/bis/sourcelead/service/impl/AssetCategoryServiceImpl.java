package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssetCategoryDao;
import com.bis.sourcelead.entity.AssetCategory;
import com.bis.sourcelead.service.AssetCategoryService;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService{

	@Autowired
	AssetCategoryDao assetCategoryDao;
	
	@Override
	public AssetCategory addAssetCategory(AssetCategory assetCategory) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<AssetCategory> getAssetCategoryListByLocationId(Long orgLocationId) throws Exception {
		return assetCategoryDao.getAssetCategoryListByLocationId(orgLocationId);
	}

	

}
