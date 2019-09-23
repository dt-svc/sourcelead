package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.entity.performance.PerformanceBadges;

public interface PerformanceBadgesDao {
	
	List<PerformanceBadges> getPerformanceBadgesList(Long orgLocationId) throws Exception;
	
	PerformanceBadges storePerformanceBadges(PerformanceBadges performanceBadges) throws Exception;
	
	PerformanceBadges getPerformanceBadgesById(Long id) throws Exception;

}
