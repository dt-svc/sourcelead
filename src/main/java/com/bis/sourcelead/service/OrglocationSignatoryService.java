package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.dto.SingnatoryDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationSignatory;
import com.bis.sourcelead.entity.User;

public interface OrglocationSignatoryService {

	OrglocationSignatory saveOrglocationSignatory(OrglocationSignatory orglocationSignatory) throws Exception;
	
	public String addOrglocationSignatory(OrglocationSignatoryDTO orglocationSignatoryDTO,User loginUser,OrgLocations orgLocations) throws Exception;
	
	public String addCompanyOrglocationSignatory(SingnatoryDTO singnatoryDTO,User loginUser,OrgLocations orgLocations) throws Exception;

	OrglocationSignatory getOrglocationSignatoryByOrglocationId(Long orgLocationId) throws Exception;
	
	OrglocationSignatory getOrglocationSignatoryByUUID(String uuid) throws Exception;

	List<OrglocationSignatoryDTO> getAllSignatoriesByLocationId(Long locationId) throws Exception;
	
	public boolean getUserByUserId(Long userId, Long orgLocationsId) throws Exception;
	
	public OrglocationSignatory getUserBySignatoryUuId(Long userId, String uuid, Long orgLocationsId) throws Exception;
	
	public String addLoginUserAsCompanyOrglocationSignatory(User loginUser,OrgLocations orgLocations) throws Exception;
}
