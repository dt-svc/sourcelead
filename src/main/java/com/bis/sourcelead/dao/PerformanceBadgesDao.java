package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.PerformanceBadges;

public interface PerformanceBadgesDao {
	
	List<PerformanceBadges> getPerformanceBadgesList(Long orgLocationId) throws Exception;
	
	PerformanceBadges storePerformanceBadges(PerformanceBadges performanceBadges) throws Exception;

}
