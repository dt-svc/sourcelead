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
	public PerformanceRatingLevel storePerformanceRatingScale(PerformanceRatingLevelDTO performanceRatingLevelDTO,Long orgLocationId,Long userId)
			throws Exception {
		
		OrgLocations orgLcoations = new OrgLocations();
		orgLcoations.setId(orgLocationId);
		
		User user = new User();
		user.setId(userId);
		
		PerformanceRatingLevel performanceRatingLevel = new PerformanceRatingLevel();
		performanceRatingLevel.setCreatedBy(user);
		performanceRatingLevel.setCreatedDate(new Date());
		performanceRatingLevel.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		
		performanceRatingLevel.setLevelName(performanceRatingLevelDTO.getName());
		performanceRatingLevel.setDescription(performanceRatingLevelDTO.getDescription());
		performanceRatingLevel.setOrgLocationId(orgLcoations);
		performanceRatingLevel.setStatus(Constants.RECORD_ACTIVE);
		
		return performanceRatingLevelDao.savePerformanceRatingScale(performanceRatingLevel);
	}

	@Override
	public List<PerformanceRatingLevelDTO> getPerformanceRatingLevelsListByOrgLocationId(Long orgLocationId,
			String dateFormat) throws Exception {
		return performanceRatingLevelDao.getPerformanceRatingLevelsListByOrgLocationId(orgLocationId, dateFormat);
	}


	
}
