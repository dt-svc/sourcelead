package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.OrglocationSignatoryDao;
import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationSignatory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.OrglocationSignatoryService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class OrglocationSignatoryServiceImpl implements OrglocationSignatoryService{

	@Autowired
    OrglocationSignatoryDao orglocationSignatoryDao;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;
	
	 
	@Override
	public OrglocationSignatory saveOrglocationSignatory(OrglocationSignatory orglocationSignatory) throws Exception {
		return orglocationSignatoryDao.saveOrglocationSignatory(orglocationSignatory);
	}

	@Override
	public String addOrglocationSignatory(OrglocationSignatoryDTO orglocationSignatoryDTO, User loginUser,
			OrgLocations orgLocations) throws Exception {
		
		String result = "invalid";
		
		try {
			
			OrglocationSignatory signatory = new OrglocationSignatory();
			if(StringUtils.isNotBlank(orglocationSignatoryDTO.getEmail())) {
				signatory.setFirstName(orglocationSignatoryDTO.getFirstName());
				signatory.setLastName(orglocationSignatoryDTO.getLastName());
				signatory.setEmail(orglocationSignatoryDTO.getEmail());
				signatory.setSsn(Integer.parseInt(orglocationSignatoryDTO.getSsn()));
				signatory.setPhone(orglocationSignatoryDTO.getPhoneNumber());
				UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(orglocationSignatoryDTO.getEmail(), orgLocations.getId());
				signatory.setUserRoleId(userRole);
				signatory.setUserId(userRole.getUser());
				signatory.setDesignation(orglocationSignatoryDTO.getDesignationType());
				if(StringUtils.isNotBlank(orglocationSignatoryDTO.getDesignationOtherType())) {
					signatory.setDesignationOtherType(orglocationSignatoryDTO.getDesignationOtherType());
				}
			}else {
				
				signatory.setFirstName(loginUser.getFirstName());
				signatory.setLastName(loginUser.getLastName());
				signatory.setEmail(loginUser.getEmail());
				if(loginUser.getSsn() != null) {
					signatory.setSsn(loginUser.getSsn());
				}
				
				if(StringUtils.isNotBlank(loginUser.getMobilePhone())) {
					signatory.setPhone(loginUser.getMobilePhone());
				}
				
				
				UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(loginUser.getEmail(), orgLocations.getId());
				signatory.setUserRoleId(userRole);
				signatory.setUserId(userRole.getUser());
				
			}
			signatory.setOrgLocationId(orgLocations);
			signatory.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			signatory.setStatus(Constants.RECORD_ACTIVE);
			signatory.setCreatedBy(loginUser);
			signatory.setUpdatedBy(loginUser);
			signatory.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
			signatory.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
			orglocationSignatoryDao.saveOrglocationSignatory(signatory);
			
			orgLocations.setIsCompanyOnboarded(Constants.YES);
			orgLocationsDao.updateOrgLocations(orgLocations);
			
			result = "success";
		} catch (Exception e) {
			
		}
		
		return result;
	}

}
