package com.bis.sourcelead.service.impl.performance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalPeersDao;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;
import com.bis.sourcelead.service.performance.PerformanceAppraisalPeersService;

@Service
public class PerformanceAppraisalPeersServiceImpl implements PerformanceAppraisalPeersService{
	
	@Autowired
	PerformanceAppraisalPeersDao performanceAppraisalPeersDao;

	@Override
	public PerformanceAppraisalPeers savePerformanceAppraisalPeers(PerformanceAppraisalPeers performanceAppraisalPeers)
			throws Exception {
		return performanceAppraisalPeersDao.savePerformanceAppraisalPeers(performanceAppraisalPeers);
	}

}
