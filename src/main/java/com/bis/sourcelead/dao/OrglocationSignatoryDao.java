package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrglocationSignatory;

public interface OrglocationSignatoryDao {

	OrglocationSignatory saveOrglocationSignatory(OrglocationSignatory orglocationSignatory) throws Exception;
	
	OrglocationSignatory getOrglocationSignatoryByOrglocationId(Long orgLocationId) throws Exception;
	
	OrglocationSignatory getOrglocationSignatoryByUUID(String uuid) throws Exception;
}
