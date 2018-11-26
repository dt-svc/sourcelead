package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.PerformanceBagdes;

public interface PerformanceBadgesService {

	List<PerformanceBagdes> getPerformanceBadgesList(Long orgLocationId) throws Exception;
}
