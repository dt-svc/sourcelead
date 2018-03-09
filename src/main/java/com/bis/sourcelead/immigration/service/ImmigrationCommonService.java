package com.bis.sourcelead.immigration.service;

import java.util.List;

import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;

/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationCommonService {
	
	List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations);

}
