package com.bis.sourcelead.service.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceAppraisalPeersDTO;
import com.bis.sourcelead.dto.performance.PerformancePeersDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;

public interface PerformanceAppraisalPeersService {

	PerformanceAppraisalPeers savePerformanceAppraisalPeers(PerformanceAppraisalPeers performanceAppraisalPeers) throws Exception;
	
	PerformanceAppraisalPeers storePerformanceAppraisalPeers(PerformancePeersDTO performancePeersDTO) throws Exception;
	
	PerformanceAppraisalPeers getPerformanceAppraisalPeersByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	List<PerformanceAppraisalPeersDTO> getAssociateAssignedPeersList(Long userId,Long orgLocationId) throws Exception;
	
	List<PerformanceAppraisalPeersDTO> getAssociateAssignedPeersListForAppraisal(String assignedUUID) throws Exception;
}
