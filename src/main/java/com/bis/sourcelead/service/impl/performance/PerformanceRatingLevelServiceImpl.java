package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceRatingLevelDao;
import com.bis.sourcelead.dto.performance.PerformanceRatingLevelDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceRatingLevel;
import com.bis.sourcelead.service.performance.PerformanceRatingLevelService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceRatingLevelServiceImpl implements PerformanceRatingLevelService {
	
	@Autowired
	PerformanceRatingLevelDao performanceRatingLevelDao;

	@Override
	public PerformanceRatingLevel storePerformanceRatingLevel(PerformanceRatingLevelDTO performanceRatingLevelDTO,Long orgLocationId,Long userId)
			throws Exception {
		
		
		PerformanceRatingLevel performanceRatingLevel  = null;
		
		User user = new User();
		user.setId(userId);
		
		if(performanceRatingLevelDTO.getRatingLevelId() != null) {
			performanceRatingLevel = performanceRatingLevelDao.getPerformanceRatingLevelById(performanceRatingLevelDTO.getRatingLevelId());
			performanceRatingLevel.setUpdatedBy(user);
			performanceRatingLevel.setUpdatedDate(new Date());
		}else {
			performanceRatingLevel = new PerformanceRatingLevel();
			performanceRatingLevel.setCreatedBy(user);
			performanceRatingLevel.setCreatedDate(new Date());
			performanceRatingLevel.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			OrgLocations orgLcoations = new OrgLocations();
			orgLcoations.setId(orgLocationId);
			performanceRatingLevel.setOrgLocationId(orgLcoations);
		}
		performanceRatingLevel.setLevelName(performanceRatingLevelDTO.getName());
		performanceRatingLevel.setDescription(performanceRatingLevelDTO.getDescription());
		performanceRatingLevel.setStatus(Constants.RECORD_ACTIVE);
		
		return performanceRatingLevelDao.savePerformanceRatingLevel(performanceRatingLevel);
	}

	@Override
	public List<PerformanceRatingLevelDTO> getPerformanceRatingLevelsListByOrgLocationId(Long orgLocationId,
			String dateFormat) throws Exception {
		return performanceRatingLevelDao.getPerformanceRatingLevelsListByOrgLocationId(orgLocationId, dateFormat);
	}

	@Override
	public boolean isRatingLevelNameExisted(Long orgLocationId, String ratingLevelName) throws Exception {
	
		return performanceRatingLevelDao.isRatingLevelNameExisted(orgLocationId, ratingLevelName);
	}

	@Override
	public PerformanceRatingLevelDTO getPerformanceRatingLevelDetailsById(Long ratingLevelId) throws Exception {
		
		return performanceRatingLevelDao.getPerformanceRatingLevelDetailsById(ratingLevelId);
	}

	@Override
	public PerformanceRatingLevel getPerformanceRatingLevelById(Long ratingLevelId) throws Exception {
		return performanceRatingLevelDao.getPerformanceRatingLevelById(ratingLevelId);
	}

	@Override
	public PerformanceRatingLevel savePerformanceRatingLevel(PerformanceRatingLevel PerformanceRatingLevel)
			throws Exception {
		return performanceRatingLevelDao.savePerformanceRatingLevel(PerformanceRatingLevel);
	}


	
}
