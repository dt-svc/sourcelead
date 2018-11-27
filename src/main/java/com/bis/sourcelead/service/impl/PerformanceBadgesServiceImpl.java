package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.PerformanceBadgesDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PerformanceBadges;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.PerformanceBadgesService;
import com.bis.sourcelead.util.FileUploadUtil;

@Service
public class PerformanceBadgesServiceImpl implements PerformanceBadgesService{
	
	@Autowired
	PerformanceBadgesDao performanceBadgesDao;

	@Override
	public List<PerformanceBadges> getPerformanceBadgesList(Long orgLocationId) throws Exception {
		return performanceBadgesDao.getPerformanceBadgesList(orgLocationId);
	}

	@Override
	public PerformanceBadges storePerformanceBadges(PerformanceBadgesDTO performanceBadgesDTO, Long orgLocationId, 
			Long userId) throws Exception {
		
		PerformanceBadges performanceBadges = new PerformanceBadges();
		performanceBadges.setName(performanceBadgesDTO.getName());
		performanceBadges.setDescription(performanceBadgesDTO.getDescription());
		
		if(performanceBadgesDTO.getFileInfo() != null && !performanceBadgesDTO.getFileInfo().isEmpty()){
			FileInfo fileInfo = performanceBadgesDTO.getFileInfo().get(0);
			
			if(fileInfo != null){
				performanceBadges.setFileName(fileInfo.getFileName());
				performanceBadges.setFileContentType(fileInfo.getContentType());
				performanceBadges.setFileURL(fileInfo.getFileURL());
					FileUploadUtil.moveFile(performanceBadgesDTO.getStaticPath(), fileInfo, "badgeImages");
				}
			}
		performanceBadges.setStatus(Constants.CANDIDATE_ACTIVE);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		performanceBadges.setOrgLocationId(orgLocations);
		
		User user = new User();
		user.setId(userId);
		performanceBadges.setCreatedBy(user);
		performanceBadges.setCreatedDate(new Date());
		
		return performanceBadgesDao.storePerformanceBadges(performanceBadges);
	}

}
