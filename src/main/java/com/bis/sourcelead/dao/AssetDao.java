package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Asset;

public interface AssetDao {
	
	Asset saveAsset(Asset asset) throws Exception;
	
	List<Asset> getAssetListByLocationId(Long orgLocationId) throws Exception;

}
