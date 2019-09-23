package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dao.OrglocationSignatoryDao;
import com.bis.sourcelead.dto.OrglocationSignatoryDTO;
import com.bis.sourcelead.dto.SingnatoryDTO;
import com.bis.sourcelead.entity.Asset;
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
	public boolean getUserByUserId(Long userId, Long orgLocationsId) throws Exception {
		
		return orglocationSignatoryDao.getUserByUserId(userId, orgLocationsId);
	}
	
	@Override
	public OrglocationSignatory getUserBySignatoryUuId(Long userId, String uuid, Long orgLocationsId) throws Exception {
		
		return orglocationSignatoryDao.getUserBySignatoryUuId(userId, uuid, orgLocationsId);
	}
	
	

	@Override
	public String addOrglocationSignatory(OrglocationSignatoryDTO orglocationSignatoryDTO, User loginUser,
			OrgLocations orgLocations) throws Exception {
		
		String result = "invalid";
		
		try {
			OrglocationSignatory signatory = null;
			
			if(StringUtils.isNotBlank(orglocationSignatoryDTO.getUuid())) {
				signatory = orglocationSignatoryDao.getOrglocationSignatoryByUUID(orglocationSignatoryDTO.getUuid());
			}else {
				signatory = new OrglocationSignatory();
				signatory.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
				signatory.setStatus(Constants.RECORD_ACTIVE);
			}
			if(!"orgSettings".equalsIgnoreCase(orglocationSignatoryDTO.getSignatoryFromType())){
				if(StringUtils.isNotBlank(orglocationSignatoryDTO.getEmail())) {
					signatory.setFirstName(orglocationSignatoryDTO.getFirstName());
					signatory.setLastName(orglocationSignatoryDTO.getLastName());
					signatory.setEmail(orglocationSignatoryDTO.getEmail());
					signatory.setSsn(orglocationSignatoryDTO.getSsn());
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
			}else{
				UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(orglocationSignatoryDTO.getAssociateId(), orgLocations.getId());
				signatory.setUserRoleId(userRole);
				signatory.setUserId(userRole.getUser());
				signatory.setAuthorizedType(String.join(",", orglocationSignatoryDTO.getAuthorizedType()));
				if(orglocationSignatoryDTO.getAuthorizedAmount()!="") {
				signatory.setAuthorizedAmount(Long.parseLong(orglocationSignatoryDTO.getAuthorizedAmount()));
				}
			}
			
			signatory.setOrgLocationId(orgLocations);
			signatory.setCreatedBy(loginUser);
			signatory.setUpdatedBy(loginUser);
			signatory.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
			signatory.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
			orglocationSignatoryDao.saveOrglocationSignatory(signatory);
			
			if(!"orgSettings".equalsIgnoreCase(orglocationSignatoryDTO.getSignatoryFromType())){
				orgLocations.setIsCompanyOnboarded(Constants.YES);
				orgLocationsDao.updateOrgLocations(orgLocations);
			}
			
			result = "success";
		} catch (Exception e) {
			
		}
		
		
		return result;
	}
	

	@Override
	public OrglocationSignatory getOrglocationSignatoryByOrglocationId(Long orgLocationId) throws Exception {
		return orglocationSignatoryDao.getOrglocationSignatoryByOrglocationId(orgLocationId);
	}

	@Override
	public OrglocationSignatory getOrglocationSignatoryByUUID(String uuid) throws Exception {
		return orglocationSignatoryDao.getOrglocationSignatoryByUUID(uuid);
	}

	@Override
	public List<OrglocationSignatoryDTO> getAllSignatoriesByLocationId(Long locationId) throws Exception
	{
		List<OrglocationSignatory> signatoryList = orglocationSignatoryDao.getAllSignatoriesByLocationId(locationId);
		List<OrglocationSignatoryDTO> list = new ArrayList<>();
		
		for(OrglocationSignatory sign : signatoryList){
			OrglocationSignatoryDTO signDto = new OrglocationSignatoryDTO();
			signDto.setAssociateId(sign.getUserId().getId());
			signDto.setFirstName(sign.getUserId().getFirstName());
			if(StringUtils.isNotBlank(sign.getUserId().getLastName()))
				signDto.setLastName(sign.getUserId().getLastName());
			signDto.setEmail(sign.getUserId().getEmail());
			signDto.setRoleType(sign.getUserRoleId().getRoles().getDisplayName());
			if(sign.getUserId().getSsn() != null)
				signDto.setSsn(sign.getUserId().getSsn().toString());
			if(sign.getAuthorizedAmount() != null)
				signDto.setAuthorizedAmount(sign.getAuthorizedAmount().toString());
			signDto.setUuid(sign.getUuid());
			signDto.setStatus(sign.getStatus().toString());
			list.add(signDto);
		}
		
		return list;
	}


	@Override
	public String addCompanyOrglocationSignatory(SingnatoryDTO singnatoryDTO, User loginUser, OrgLocations orgLocations) throws Exception
	{

		
		String result = "invalid";
		
		try {
			
			OrglocationSignatory signatory = new OrglocationSignatory();
			signatory.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			signatory.setStatus(Constants.RECORD_ACTIVE);
			signatory.setFirstName(singnatoryDTO.getFirstName());
			signatory.setLastName(singnatoryDTO.getLastName());
			signatory.setEmail(singnatoryDTO.getEmail());
			signatory.setSsn(singnatoryDTO.getSsn());
			
			UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(singnatoryDTO.getEmail(), orgLocations.getId());
			signatory.setUserRoleId(userRole);
			signatory.setUserId(userRole.getUser());
			if(StringUtils.isNotBlank(singnatoryDTO.getDesignation())) {
			signatory.setDesignation(singnatoryDTO.getDesignation());
			}
			if(StringUtils.isNotBlank(singnatoryDTO.getPhoneNumber())) {
				signatory.setPhone(singnatoryDTO.getPhoneNumber());
			}
			orgLocations.setIsCompanyOnboarded(Constants.YES);
			orgLocationsDao.updateOrgLocations(orgLocations);
			saveOrglocationSignatory(signatory);
			
			result = "success";
		} catch (Exception e) {
			
		}
		
		return result;
	
	}


	@Override
	public String addLoginUserAsCompanyOrglocationSignatory(User loginUser, OrgLocations orgLocations)
			throws Exception {
		
		String result = "invalid";
		
		try {
			
			OrglocationSignatory signatory = new OrglocationSignatory();
			signatory.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			signatory.setStatus(Constants.RECORD_ACTIVE);
			signatory.setFirstName(loginUser.getFirstName());
			signatory.setLastName(loginUser.getLastName());
			signatory.setEmail(loginUser.getEmail());
			signatory.setSsn(loginUser.getSsn());
			
			UserRole userRole = userRoleService.getUserDetailsByEmailAndLocationId(loginUser.getEmail(), orgLocations.getId());
			signatory.setUserRoleId(userRole);
			signatory.setUserId(userRole.getUser());
		
			if(StringUtils.isNotBlank(loginUser.getMobilePhone())) {
				signatory.setPhone(loginUser.getMobilePhone());
			}
			orgLocations.setIsCompanyOnboarded(Constants.YES);
			orgLocationsDao.updateOrgLocations(orgLocations);
			saveOrglocationSignatory(signatory);
			
			result = "success";
		} catch (Exception e) {
			
		}
		return result;
	}

}
