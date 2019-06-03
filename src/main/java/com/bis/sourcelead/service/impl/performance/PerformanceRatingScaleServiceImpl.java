package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.impl.performance.PerformanceRatingScaleDaoImpl;
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
		
		
		User user = new User();
		user.setId(userId);
		
		PerformanceRatingScale performanceRatingScale = null;
		
		if(performanceRatingScaleDTO.getRatingScaleId() != null) {
			performanceRatingScale = getPerformanceRatingScaleByID(performanceRatingScaleDTO.getRatingScaleId());
		}else {
			performanceRatingScale = new PerformanceRatingScale();
			performanceRatingScale.setCreatedBy(user);
			performanceRatingScale.setCreatedDate(new Date());
			performanceRatingScale.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			
			OrgLocations orgLcoations = new OrgLocations();
			orgLcoations.setId(orgLocationId);
			performanceRatingScale.setOrgLocationId(orgLcoations);
			
			performanceRatingScale.setStatus(Constants.RECORD_ACTIVE);
		}
		
		
		performanceRatingScale.setRatingName(performanceRatingScaleDTO.getRatingName());
		PerformanceRatingLevel ratingLevel = new PerformanceRatingLevel();
		ratingLevel.setId(Long.valueOf(performanceRatingScaleDTO.getRatingLevelId()));
		performanceRatingScale.setRatingLevelId(ratingLevel);
		performanceRatingScale.setRatingScaleValue(performanceRatingScaleDTO.getRatingScaleValue());
		performanceRatingScale.setShortCode(performanceRatingScaleDTO.getShortCode());
		performanceRatingScale.setDescription(performanceRatingScaleDTO.getDescription());
		performanceRatingScaleDao.savePerformanceRatingScale(performanceRatingScale);
		
		return performanceRatingScale;
	}

	@Override
	public PerformanceRatingScale savePerformanceRatingScale(PerformanceRatingScale performanceRatingScale)
			throws Exception {
		return performanceRatingScaleDao.savePerformanceRatingScale(performanceRatingScale);
	}

	@Override
	public List<PerformanceRatingScaleDTO> getPerformanceRatingScaleListByOrgLocationId(Long orgLocationId,
			String dateFormat) throws Exception {
		
		return performanceRatingScaleDao.getPerformanceRatingScaleListByOrgLocationId(orgLocationId, dateFormat); 

	}

	@Override
	public boolean isRatingScaleNameExisted(Long orgLocationId, String ratingScaleName) throws Exception {
	
		return performanceRatingScaleDao.isRatingScaleNameExisted(orgLocationId, ratingScaleName);
	}

	@Override
	public boolean isRatingShortCodeExisted(Long orgLocationId, String ratingShortCode) throws Exception {
		
		return performanceRatingScaleDao.isRatingShortCodeExisted(orgLocationId, ratingShortCode);
	}

	@Override
	public PerformanceRatingScale getPerformanceRatingScaleByID(Long ratingScaleId) throws Exception {
		return performanceRatingScaleDao.getPerformanceRatingScaleByID(ratingScaleId);
	}

	@Override
	public PerformanceRatingScaleDTO getPerformanceRatingScaleDTOByScaleId(Long ratingScaleId) throws Exception {
		return performanceRatingScaleDao.getPerformanceRatingScaleDTOByScaleId(ratingScaleId);
	}

}
