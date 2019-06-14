package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalPeersDao;
import com.bis.sourcelead.dto.performance.PerformancePeersDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;
import com.bis.sourcelead.service.performance.PerformanceAppraisalPeersService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceAppraisalPeersServiceImpl implements PerformanceAppraisalPeersService{
	
	@Autowired
	PerformanceAppraisalPeersDao performanceAppraisalPeersDao;

	@Override
	public PerformanceAppraisalPeers savePerformanceAppraisalPeers(PerformanceAppraisalPeers performanceAppraisalPeers)
			throws Exception {
		return performanceAppraisalPeersDao.savePerformanceAppraisalPeers(performanceAppraisalPeers);
	}

	@Override
	public PerformanceAppraisalPeers storePerformanceAppraisalPeers(PerformancePeersDTO performancePeersDTO)
			throws Exception {
		
		  String[] peerUserIdStr = performancePeersDTO.getPeerUserIds().split(",");
		  PerformanceAppraisalPeers appraisalPeers  = null;
		   if(peerUserIdStr != null &&  peerUserIdStr.length > 0){
			   appraisalPeers = getPerformanceAppraisalPeersByUserIdAndOrgLocationId(performancePeersDTO.getAppraisalAssignedUserId(), performancePeersDTO.getOrgLocationId());
			   User loginUser = new User();
			   loginUser.setId(performancePeersDTO.getUserId());
			   
			   if(appraisalPeers == null) {
					    appraisalPeers = new PerformanceAppraisalPeers();
					    appraisalPeers.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					    User appraisalAssignedUser = new User();
						appraisalAssignedUser.setId(performancePeersDTO.getAppraisalAssignedUserId());
						appraisalPeers.setStatus(Constants.RECORD_ACTIVE);
						appraisalPeers.setUserId(appraisalAssignedUser);
						appraisalPeers.setCreatedBy(loginUser);
						appraisalPeers.setCreatedDate(new Date());
						OrgLocations loginOrgLocation = new OrgLocations();
						loginOrgLocation.setId(performancePeersDTO.getOrgLocationId());
						appraisalPeers.setOrgLocationId(loginOrgLocation);
				}else {
					appraisalPeers.setUpdatedBy(loginUser);
					appraisalPeers.setUpdatedDate(new Date());
				}
				
				appraisalPeers.setPeerUserId(performancePeersDTO.getPeerUserIds());
				appraisalPeers.setPeerCount(peerUserIdStr.length);
			   
				savePerformanceAppraisalPeers(appraisalPeers);
			}
				
		return appraisalPeers;
	}

	@Override
	public PerformanceAppraisalPeers getPerformanceAppraisalPeersByUserIdAndOrgLocationId(Long userId,
			Long orgLocationId) throws Exception {
		return performanceAppraisalPeersDao.getPerformanceAppraisalPeersByUserIdAndOrgLocationId(userId, orgLocationId);
	}

}
	
