package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.entity.OrglocationSignatory;

public interface OrglocationSignatoryDao {

	OrglocationSignatory saveOrglocationSignatory(OrglocationSignatory orglocationSignatory) throws Exception;
	
	OrglocationSignatory getOrglocationSignatoryByOrglocationId(Long orgLocationId) throws Exception;
	
	OrglocationSignatory getOrglocationSignatoryByUUID(String uuid) throws Exception;

	List<OrglocationSignatory> getAllSignatoriesByLocationId(Long locationId) throws Exception;
	
	public boolean getUserByUserId(Long userId, Long orgLocationsId) throws Exception;
	
	public OrglocationSignatory getUserBySignatoryUuId(Long userId, String uuid, Long orgLocationsId) throws Exception;

}
