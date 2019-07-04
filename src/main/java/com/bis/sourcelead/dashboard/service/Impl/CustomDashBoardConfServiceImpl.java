package com.bis.sourcelead.dashboard.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dashboard.dao.CustomDashBoardConfDao;
import com.bis.sourcelead.dashboard.dto.CustomDashBoardConfDTO;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardAssociatesConf;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardRoleConfiguration;
import com.bis.sourcelead.dashboard.service.CustomDashboardConfService;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class CustomDashBoardConfServiceImpl implements CustomDashboardConfService{
	
	@Autowired
	private CustomDashBoardConfDao customDashBoardConfDao;
	
	@Override
	public CustomDashBoardConfiguration saveCustomDashBoardConf(CustomDashBoardConfDTO customDashBoardConfDTO, OrgLocations orgLocation,Long loginUsrId)
	{
		CustomDashBoardConfiguration customDashBoardConf = null;
		
		try {
			if(StringUtils.isNotEmpty(customDashBoardConfDTO.getCustomDashBoardConfId())) {
				customDashBoardConf = getCustomDashboardConfById(Long.parseLong(customDashBoardConfDTO.getCustomDashBoardConfId()),orgLocation.getId());
			}else {
				customDashBoardConf = new CustomDashBoardConfiguration();
			}
			customDashBoardConf.setDashboardName(customDashBoardConfDTO.getDashBoardName());
			customDashBoardConf.setDescription(customDashBoardConfDTO.getDescription());
			customDashBoardConf.setApplicableFor(customDashBoardConfDTO.getApplicableFor());
			customDashBoardConf.setOrgLocations(orgLocation);
			customDashBoardConf.setStatus(1);
			Set<CustomDashBoardRoleConfiguration> dashBoardRoleConfSet = getDashBoardRoleConfSet(customDashBoardConf,customDashBoardConfDTO.getRoles(),loginUsrId);
			customDashBoardConf.setDashBoardRoleConf(dashBoardRoleConfSet);
			Set<CustomDashBoardAssociatesConf> dashBoardAssociatesConfSet  = getDashBoardAssociatesSet(customDashBoardConf,customDashBoardConfDTO.getAssociates(),loginUsrId);
			customDashBoardConf.setDashboardConfUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			customDashBoardConf.setCreatedDate(new Date());
			customDashBoardConf.setUpdatedDate(new Date());
			customDashBoardConf.setCreatedBy(loginUsrId);
			customDashBoardConf.setUpdatedBy(loginUsrId);
			customDashBoardConf.setDashBoardAssociatesConf(dashBoardAssociatesConfSet);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return customDashBoardConfDao.saveCustomDashBoardConf(customDashBoardConf);
	}

	
	/**
	 * populating Dash Board Associates Configuration
	 * @param customDashBoardConf
	 * @param roles
	 * @param loginUsrId 
	 * @return Set<CustomDashBoardRoleConfiguration>
	 */
	private Set<CustomDashBoardAssociatesConf> getDashBoardAssociatesSet(CustomDashBoardConfiguration customDashBoardConf, String associates, Long loginUsrId)
	{
		Set<CustomDashBoardAssociatesConf>  dashBoardAssociatesConf = new HashSet<>();
		try {
			if(StringUtils.isNotBlank(associates)) {
				String [] assoArr = associates.split(",");
				if(customDashBoardConf.getId() != null) {
					int deleted = customDashBoardConfDao.deleteExistedDashBoardAssociates(customDashBoardConf.getId());
				}
				for(String userId : assoArr) {
					CustomDashBoardAssociatesConf customDashBoardAssociatesConf = new CustomDashBoardAssociatesConf();
					customDashBoardAssociatesConf.setDashBoardConfId(customDashBoardConf);
					customDashBoardAssociatesConf.setUserId(Long.parseLong(userId));
					customDashBoardAssociatesConf.setStatus(1);
					customDashBoardAssociatesConf.setCreatedDate(new Date());
					customDashBoardAssociatesConf.setUpdatedDate(new Date());
					customDashBoardAssociatesConf.setCreatedBy(loginUsrId);
					customDashBoardAssociatesConf.setUpdatedBy(loginUsrId);
					dashBoardAssociatesConf.add(customDashBoardAssociatesConf);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return dashBoardAssociatesConf;
	}


	/**
	 * populating Dash Board  Roles Configuration
	 * @param customDashBoardConf
	 * @param roles
	 * @param loginUsrId 
	 * @return Set<CustomDashBoardRoleConfiguration>
	 */
	private Set<CustomDashBoardRoleConfiguration> getDashBoardRoleConfSet(CustomDashBoardConfiguration customDashBoardConf, String roles, Long loginUsrId)
	{
		Set<CustomDashBoardRoleConfiguration> customDashBoardRoleConfSet = new HashSet<>();
		try {
			if(StringUtils.isNotBlank(roles)) {
				String [] roleArr = roles.split(",");
				if(customDashBoardConf.getId() != null) {
					int deleted = customDashBoardConfDao.deleteExistedDashBoardRoles(customDashBoardConf.getId());
				}
				for(String roleId : roleArr) {
					CustomDashBoardRoleConfiguration customDashBoardRoleConf = new CustomDashBoardRoleConfiguration();
					customDashBoardRoleConf.setDashBoardConfId(customDashBoardConf);
					customDashBoardRoleConf.setRoleId(Long.parseLong(roleId));
					customDashBoardRoleConf.setStatus(1);
					customDashBoardRoleConf.setCreatedDate(new Date());
					customDashBoardRoleConf.setUpdatedDate(new Date());
					customDashBoardRoleConf.setCreatedBy(loginUsrId);
					customDashBoardRoleConf.setUpdatedBy(loginUsrId);
					customDashBoardRoleConfSet.add(customDashBoardRoleConf);
				}	
				
				Set<CustomDashBoardRoleConfiguration> existedRoles = customDashBoardConf.getDashBoardRoleConf();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return customDashBoardRoleConfSet;
	}


	@Override
	public ResponseList<CustomDashBoardConfDTO> getCustomConfDashBoardByLocationId(Long locationId)
	{
		ResponseList<CustomDashBoardConfDTO> response = new ResponseList<>();
		try {
			List<CustomDashBoardConfiguration> dashBoardConfList = customDashBoardConfDao.getCustomConfDashBoardByLocationId(locationId);	
			List<CustomDashBoardConfDTO> resList = new ArrayList<>();
			if(dashBoardConfList != null) {
				for(CustomDashBoardConfiguration dashConf : dashBoardConfList) {
					CustomDashBoardConfDTO dashBoardConfDTO = new CustomDashBoardConfDTO();
					dashBoardConfDTO.setCustomDashBoardConfId(String.valueOf(dashConf.getId()));
					dashBoardConfDTO.setDashBoardName(dashConf.getDashboardName());
					dashBoardConfDTO.setDescription(dashConf.getDescription());
					dashBoardConfDTO.setCustomDashBoardConfUUID(dashConf.getDashboardConfUUID());
					resList.add(dashBoardConfDTO);
					response.setData(resList);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}


	@Override
	public CustomDashBoardConfiguration getCustomDashboardConfByUUID(String customDashBoardUUID, Long orgLocationId)
	{
		return customDashBoardConfDao.getCustomDashboardConfByUUID(customDashBoardUUID,orgLocationId);
	}
	
	@Override
	public CustomDashBoardConfiguration getCustomDashboardConfById(Long customDashBoardConfID, Long orgLocationId)
	{
		return customDashBoardConfDao.getCustomDashboardConfById(customDashBoardConfID,orgLocationId);
	}
	
}

