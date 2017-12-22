package com.bis.sourcelead.reports.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.dao.FinanceDao;
import com.bis.sourcelead.reports.service.FinanceService;
import com.bis.sourcelead.reportsDTO.APAgingDTO;

@Service
public class FinanceServiceImpl implements FinanceService{

	
	@Autowired
	FinanceDao financeDao;
	
	@Override
	public ResponseList<APAgingDTO> getApAgingListByLocation(OrgLocations orgLocation,APAgingDTO apAgingDTO) {
		
		financeDao.getApAgingListByLocation(orgLocation,apAgingDTO);
		
		return null;
	}

}
