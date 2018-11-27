package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.PerformanceBadgesDTO;
import com.bis.sourcelead.entity.PerformanceBadges;

public interface PerformanceBadgesService {

	List<PerformanceBadges> getPerformanceBadgesList(Long orgLocationId) throws Exception;
	
	PerformanceBadges storePerformanceBadges(PerformanceBadgesDTO performanceBadgesDTO, Long orgLocationId, Long userId) throws Exception;
}
