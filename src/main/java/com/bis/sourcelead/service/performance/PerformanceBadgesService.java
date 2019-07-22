package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.entity.performance.PerformanceBadges;

public interface PerformanceBadgesService {

	List<PerformanceBadges> getPerformanceBadgesList(Long orgLocationId) throws Exception;
	
	PerformanceBadges storePerformanceBadges(PerformanceBadgesDTO performanceBadgesDTO, Long orgLocationId, Long userId) throws Exception;
	
	PerformanceBadgesDTO getPerformanceBadgesById(Long id) throws Exception;
	
	PerformanceBadges savePerformanceBadges(PerformanceBadges performanceBadges) throws Exception;
	
	PerformanceBadges getPerformanceBadgesDetailsById(Long id) throws Exception;
	
	boolean isPerformanceBadgeNameExisted(Long orgLocationId, String badgeName) throws Exception;
}
