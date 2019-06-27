package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalAssignedAssociatesDao;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalPeersDao;
import com.bis.sourcelead.dao.performance.PerformanceAppraisalTemplateDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.AppraisalAssignedAssociatesDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalAssignedAssociates;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalPeers;
import com.bis.sourcelead.entity.performance.PerformanceAppraisalTemplate;
import com.bis.sourcelead.service.performance.PerformanceAppraisalAssignedAssociatesService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceAppraisalAssignedAssociatesServiceImpl implements PerformanceAppraisalAssignedAssociatesService{
	
	@Autowired
	PerformanceAppraisalAssignedAssociatesDao performanceAppraisalAssignedAssociatesDao;
	
	@Autowired
	PerformanceAppraisalTemplateDao performanceAppraisalTemplateDao;
	
	@Autowired
	PerformanceAppraisalPeersDao performanceAppraisalPeersDao;

	@Override
	public void storePerformanceAppraisalAssignedAssociates(
			AppraisalAssignedAssociatesDTO appraisalAssignedAssociatesDTO) throws Exception {
		
		if(appraisalAssignedAssociatesDTO.getAssociateUserIds() != null && appraisalAssignedAssociatesDTO.getAppraisalTemplateUUId() != null) {
			
			OrgLocations loginOrgLocation = new OrgLocations();
			loginOrgLocation.setId(appraisalAssignedAssociatesDTO.getOrgLocationId());
			
			Org loginOrg = new Org();
			loginOrg.setId(appraisalAssignedAssociatesDTO.getOrgId());
			
			User loginUser = new User();
			loginUser.setId(appraisalAssignedAssociatesDTO.getUserId());
			
			PerformanceAppraisalTemplate appraisalTemplate = performanceAppraisalTemplateDao.getPerformanceAppraisalTemplateByUUID(appraisalAssignedAssociatesDTO.getAppraisalTemplateUUId());
			String[] assignedUserIds = appraisalAssignedAssociatesDTO.getAssociateUserIds().split(",");
			for(int i=0; i < assignedUserIds.length; i++ ) {
				PerformanceAppraisalAssignedAssociates assignedAssociates = new PerformanceAppraisalAssignedAssociates();
				assignedAssociates.setAppraisalTemplateId(appraisalTemplate);
				PerformanceAppraisalPeers appraisalPeers = performanceAppraisalPeersDao.getPerformanceAppraisalPeersByUserIdAndOrgLocationId(Long.valueOf(assignedUserIds[i]), appraisalAssignedAssociatesDTO.getOrgLocationId());
				if(appraisalPeers != null) {
					assignedAssociates.setPeerId(appraisalPeers);
				}
				User assignedUser = new User();
				assignedUser.setId(Long.valueOf(assignedUserIds[i]));
				assignedAssociates.setUserId(assignedUser);
				assignedAssociates.setOrgId(loginOrg);
				assignedAssociates.setOrgLocationId(loginOrgLocation);
				assignedAssociates.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				assignedAssociates.setStatus(Constants.RECORD_ACTIVE);
				assignedAssociates.setCreatedBy(loginUser);
				assignedAssociates.setCreatedDate(new Date());
				savePerformanceAppraisalAssignedAssociates(assignedAssociates);
			}
		}
		
	}

	@Override
	public PerformanceAppraisalAssignedAssociates savePerformanceAppraisalAssignedAssociates(
			PerformanceAppraisalAssignedAssociates performanceAppraisalAssignedAssociates) throws Exception {
		return performanceAppraisalAssignedAssociatesDao.savePerformanceAppraisalAssignedAssociates(performanceAppraisalAssignedAssociates);
	}

	@Override
	public ResponseList<AppraisalAssignedAssociatesDTO> getAppraisalAssignedAssociatesList(
			AppraisalAssignedAssociatesDTO appraisalAssignedAssociatesDTO) throws Exception {
		return performanceAppraisalAssignedAssociatesDao.getAppraisalAssignedAssociatesList(appraisalAssignedAssociatesDTO);
	}

	@Override
	public void deleteAppraisalAssignedAssociate(
			PerformanceAppraisalAssignedAssociates performanceAppraisalAssignedAssociates) throws Exception {
		
		performanceAppraisalAssignedAssociatesDao.deleteAppraisalAssignedAssociate(performanceAppraisalAssignedAssociates);
		
	}

	@Override
	public PerformanceAppraisalAssignedAssociates getPerformanceAppraisalAssignedAssociatesByUUID(String uuid)
			throws Exception {
		return performanceAppraisalAssignedAssociatesDao.getPerformanceAppraisalAssignedAssociatesByUUID(uuid);
	}

	@Override
	public boolean isGoalsConfiguredForAssignedPerformanceApprisal(String uuid) throws Exception {
		return performanceAppraisalAssignedAssociatesDao.isGoalsConfiguredForAssignedPerformanceApprisal(uuid);
	}

}
