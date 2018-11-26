package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.PerformanceBagdes;

public interface PerformanceBadgesDao {
	
	List<PerformanceBagdes> getPerformanceBadgesList(Long orgLocationId) throws Exception;

}
