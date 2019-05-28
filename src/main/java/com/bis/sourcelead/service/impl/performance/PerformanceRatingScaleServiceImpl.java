package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceRatingScaleDao;
import com.bis.sourcelead.dto.performance.PerformanceRatingScaleDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceRatingLevel;
import com.bis.sourcelead.entity.performance.PerformanceRatingScale;
import com.bis.sourcelead.service.performance.PerformanceRatingScaleService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceRatingScaleServiceImpl implements PerformanceRatingScaleService {

	@Autowired
	PerformanceRatingScaleDao performanceRatingScaleDao;
	
	@Override
	public PerformanceRatingScale storePerformanceRatingScale(PerformanceRatingScaleDTO performanceRatingScaleDTO,
			Long orgLocationId, Long userId) throws Exception {
		
		OrgLocations orgLcoations = new OrgLocations();
		orgLcoations.setId(orgLocationId);
		
		User user = new User();
		user.setId(userId);
		
		PerformanceRatingScale performanceRatingScale = new PerformanceRatingScale();
		performanceRatingScale.setCreatedBy(user);
		performanceRatingScale.setCreatedDate(new Date());
		performanceRatingScale.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		performanceRatingScale.setRatingName(performanceRatingScaleDTO.getRatingName());
		PerformanceRatingLevel ratingLevel = new PerformanceRatingLevel();
		ratingLevel.setId(Long.valueOf(performanceRatingScaleDTO.getRatingLevelId()));
		performanceRatingScale.setRatingLevelId(ratingLevel);
		performanceRatingScale.setRatingScaleValue(Integer.parseInt(performanceRatingScaleDTO.getRatingScaleValue()));
		performanceRatingScale.setShortCode(performanceRatingScaleDTO.getShortCode());
		performanceRatingScale.setOrgLocationId(orgLcoations);
		performanceRatingScale.setStatus(Constants.RECORD_ACTIVE);
		performanceRatingScaleDao.savePerformanceRatingScale(performanceRatingScale);
		
		return performanceRatingScale;
	}

	@Override
	public PerformanceRatingScale savePerformanceRatingScale(PerformanceRatingScale performanceRatingScale)
			throws Exception {
		return performanceRatingScaleDao.savePerformanceRatingScale(performanceRatingScale);
	}

}
