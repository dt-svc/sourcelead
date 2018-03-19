package com.bis.sourcelead.immigration.service;

import java.util.List;

import com.bis.sourcelead.dto.ImmigrationDTO;
import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonService {
	
	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);

	void savePetitionDetails(ImmigrationDTO immigrationDTO, User user, OrgLocations orgLocations) throws Exception;

}
