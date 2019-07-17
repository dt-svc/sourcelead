package com.bis.sourcelead.dao.performance;

import java.util.List;

import com.bis.sourcelead.dto.performance.PerformanceAppraisalPeersDTO;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;

public interface PerformanceAppraisalPeersDao {

	PerformanceAppraisalPeers savePerformanceAppraisalPeers(PerformanceAppraisalPeers performanceAppraisalPeers) throws Exception;
	
	PerformanceAppraisalPeers getPerformanceAppraisalPeersByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	
	List<PerformanceAppraisalPeersDTO> getAssociateAssignedPeersList(Long userId,Long orgLocationId) throws Exception;
	
	List<PerformanceAppraisalPeersDTO> getAssociateAssignedPeersListForAppraisal(String assignedUUID) throws Exception;
}
