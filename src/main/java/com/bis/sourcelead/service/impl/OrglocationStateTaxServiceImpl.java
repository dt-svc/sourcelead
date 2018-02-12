package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrglocationStateTaxDao;
import com.bis.sourcelead.dto.FederalAndStateTaxDTO;
import com.bis.sourcelead.dto.StateTaxesList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationStateTax;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrglocationStateTaxService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class OrglocationStateTaxServiceImpl implements OrglocationStateTaxService{

	@Autowired
	OrglocationStateTaxDao orglocationStateTaxDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public OrglocationStateTax saveOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception {
		return orglocationStateTaxDao.saveOrglocationStateTax(orglocationStateTax);
	}

	@Override
	public String addOrglocationStateTax(FederalAndStateTaxDTO federalAndStateTaxDTO, OrgLocations orgLocations,
			User user) throws Exception {
		
		String result = "";
		
		if(federalAndStateTaxDTO.getSavedStateTaxesList() != null && federalAndStateTaxDTO.getSavedStateTaxesList().size() > 0) {
			try {
				for(StateTaxesList stateTaxesList : federalAndStateTaxDTO.getSavedStateTaxesList()) {
					if(stateTaxesList.getStateName() != null && !stateTaxesList.getStateName().isEmpty()) {
						OrglocationStateTax orglocationStateTax = orglocationStateTaxDao.getOrglocationStateTaxByUUID(stateTaxesList.getUuid());
						if(orglocationStateTax != null) {
							orglocationStateTax.setState(stateTaxesList.getStateName());
							orglocationStateTax.setStateTaxAccountNumber(stateTaxesList.getStateTaxAccountNumber());
							orglocationStateTax.setStateUnemploymentTax(stateTaxesList.getStateUnemploymentTax());
							orglocationStateTax.setCreatedBy(user);
							orglocationStateTax.setUpdatedBy(user);
							orglocationStateTax.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
							orglocationStateTax.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
							orglocationStateTaxDao.saveOrglocationStateTax(orglocationStateTax);
						}
					}
					result = "success";
				}
			}catch (Exception e) {
				 result = "failed";
			}
		}
		
		
		if(federalAndStateTaxDTO.getStateTaxesList() != null && federalAndStateTaxDTO.getStateTaxesList().size() > 0) {
			
			try {
				for(StateTaxesList stateTaxesList : federalAndStateTaxDTO.getStateTaxesList()) {
					if(stateTaxesList.getStateName() != null && !stateTaxesList.getStateName().isEmpty()){
						OrglocationStateTax orglocationStateTax = new OrglocationStateTax();
						orglocationStateTax.setState(stateTaxesList.getStateName());
						orglocationStateTax.setOrgLocationId(orgLocations);
						orglocationStateTax.setStateTaxAccountNumber(stateTaxesList.getStateTaxAccountNumber());
						orglocationStateTax.setStateUnemploymentTax(stateTaxesList.getStateUnemploymentTax());
						orglocationStateTax.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						orglocationStateTax.setStatus(Constants.RECORD_ACTIVE);
						orglocationStateTax.setCreatedBy(user);
						orglocationStateTax.setUpdatedBy(user);
						orglocationStateTax.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
						orglocationStateTax.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
						orglocationStateTaxDao.saveOrglocationStateTax(orglocationStateTax);
					}
					result = "success";
				}
			} catch (Exception e) {
				   result = "failed";
			}
		}
		return result;
	}

	@Override
	public List<OrglocationStateTax> getOrglocationStateTaxListByOrgLocationId(Long orgLocationId) throws Exception {
		return orglocationStateTaxDao.getOrglocationStateTaxListByOrgLocationId(orgLocationId);
	}

	@Override
	public OrglocationStateTax getOrglocationStateTaxByUUID(String UUID) throws Exception {
		return orglocationStateTaxDao.getOrglocationStateTaxByUUID(UUID);
	}

	@Override
	public void deleteOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception {
		orglocationStateTaxDao.deleteOrglocationStateTax(orglocationStateTax);		
	}
	
}
