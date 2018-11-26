package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PerformanceBadgesDao;
import com.bis.sourcelead.entity.PerformanceBagdes;
import com.bis.sourcelead.service.PerformanceBadgesService;

@Service
public class PerformanceBadgesServiceImpl implements PerformanceBadgesService{
	
	@Autowired
	PerformanceBadgesDao performanceBadgesDao;

	@Override
	public List<PerformanceBagdes> getPerformanceBadgesList(Long orgLocationId) throws Exception {
		return performanceBadgesDao.getPerformanceBadgesList(orgLocationId);
	}

}
