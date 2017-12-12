package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationSignatory;
import com.bis.sourcelead.entity.User;

public interface OrglocationSignatoryService {

	OrglocationSignatory saveOrglocationSignatory(OrglocationSignatory orglocationSignatory) throws Exception;
	
	public String addOrglocationSignatory(OrglocationSignatoryDTO orglocationSignatoryDTO,User loginUser,OrgLocations orgLocations) throws Exception;
}
