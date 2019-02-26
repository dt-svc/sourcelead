/*package com.bis.sourcelead.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.ImmigrationConstants;
import com.bis.sourcelead.entity.Lca;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.dao.ImmigrationCommonDao;
*//**
 * This is the commonutil for saving LCA activities throughout the
 * application
 * 
 * @author: PrasadK
 * 
 *//*
@Component
public class LcaActivityCommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(LcaActivityCommonUtil.class);
	
	@Autowired
	private ImmigrationCommonDao immigrationCommonDao;
	
	public void saveLcaActivities(User user, OrgLocations orgLocations, Lca lca, String actionOn, String actionDesc) throws Exception
	{
		LcaActivityHistory lcaActivityHistory = new LcaActivityHistory();
		
		lcaActivityHistory.setActionDesc(actionDesc);
		lcaActivityHistory.setActionBy(user.getFullName());
		lcaActivityHistory.setLcaId(lca);;
		lcaActivityHistory.setOrgLocationId(orgLocations);
		lcaActivityHistory.setCreatedBy(user);
		lcaActivityHistory.setUpdatedBy(user);
		lcaActivityHistory.setStatus(ImmigrationConstants.STATUS_ACTIVE);
		lcaActivityHistory.setCreatedDate(new Date());
		lcaActivityHistory.setUpdatedDate(new Date());
		immigrationCommonDao.storeLcaActivities(lcaActivityHistory);
	}
	
}
*/