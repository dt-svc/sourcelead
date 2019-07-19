package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceBadgesDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.performance.PerformanceBadgesService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
		
		User user = new User();
		user.setId(userId);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		
		PerformanceBadges performanceBadges = null;
		if(performanceBadgesDTO.getBadgeId() != null) {
			performanceBadges = performanceBadgesDao.getPerformanceBadgesById(performanceBadgesDTO.getBadgeId());
			performanceBadges.setUpdatedBy(user);
			performanceBadges.setUpdatedDate(new Date());
		}else {
			performanceBadges = new PerformanceBadges();
			performanceBadges.setCreatedBy(user);
			performanceBadges.setCreatedDate(new Date());
			performanceBadges.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		
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
		performanceBadges.setStatus(Constants.RECORD_ACTIVE);
		performanceBadges.setOrgLocationId(orgLocations);
		
		return performanceBadgesDao.storePerformanceBadges(performanceBadges);
	}

	@Override
	public PerformanceBadgesDTO getPerformanceBadgesById(Long id) throws Exception {
		PerformanceBadges performanceBadges = performanceBadgesDao.getPerformanceBadgesById(id);
		PerformanceBadgesDTO badgeDTO = new PerformanceBadgesDTO();
		badgeDTO.setBadgeId(performanceBadges.getId());
		badgeDTO.setName(performanceBadges.getName());
		badgeDTO.setDescription(performanceBadges.getDescription());
		badgeDTO.setFileURL(performanceBadges.getFileURL());
		return badgeDTO;
	}

	@Override
	public PerformanceBadges savePerformanceBadges(PerformanceBadges performanceBadges) throws Exception {
		return performanceBadgesDao.storePerformanceBadges(performanceBadges);
	}

	@Override
	public PerformanceBadges getPerformanceBadgesDetailsById(Long id) throws Exception {
		 PerformanceBadges badge = performanceBadgesDao.getPerformanceBadgesById(id);
		 return badge;
	}

	@Override
	public boolean isPerformanceBadgeNameExisted(Long orgLocationId, String badgeName) throws Exception {
		return performanceBadgesDao.isPerformanceBadgeNameExisted(orgLocationId, badgeName);
	}

}
