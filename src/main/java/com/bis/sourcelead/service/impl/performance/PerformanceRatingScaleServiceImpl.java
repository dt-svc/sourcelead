package com.bis.sourcelead.service.impl.performance;

import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.performance.PerformanceRatingScaleDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceRatingScale;
import com.bis.sourcelead.service.performance.PerformanceRatingScaleService;

@Service
public class PerformanceRatingScaleServiceImpl implements PerformanceRatingScaleService {

	@Override
	public PerformanceRatingScale storePerformanceRatingScale(PerformanceRatingScaleDTO performanceRatingScaleDTO,
			Long orgLocationId, Long userId) throws Exception {
		
		OrgLocations orgLcoations = new OrgLocations();
		orgLcoations.setId(orgLocationId);
		
		User user = new User();
		user.setId(userId);
		
		return null;
	}

}
