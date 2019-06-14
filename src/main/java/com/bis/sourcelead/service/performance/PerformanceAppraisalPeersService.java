package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.performance.PerformancePeersDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;

public interface PerformanceAppraisalPeersService {

	PerformanceAppraisalPeers savePerformanceAppraisalPeers(PerformanceAppraisalPeers performanceAppraisalPeers) throws Exception;
	
	PerformanceAppraisalPeers storePerformanceAppraisalPeers(PerformancePeersDTO performancePeersDTO) throws Exception;
	
	PerformanceAppraisalPeers getPerformanceAppraisalPeersByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
}
