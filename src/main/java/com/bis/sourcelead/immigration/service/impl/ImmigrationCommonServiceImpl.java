/**
 * 
 */
package com.bis.sourcelead.immigration.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.ImmigrationTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
import com.bis.sourcelead.immigration.service.ImmigrationCommonService;

/**
 * @author Srinivas.Sabbu
 *
 */

@Service
public class ImmigrationCommonServiceImpl implements ImmigrationCommonService {

	@Autowired
	ImmigrationCommonDao immigrationCommonDao;
	
	@Override
	public List<ImmigrationTemplate> getImmigrationTemplateList(OrgLocations orgLocations) {
		
		return immigrationCommonDao.getImmigrationTemplateList(orgLocations);
	}

}
