package com.bis.sourcelead.payroll.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class IndPaysheetFrequencyServiceImpl implements IndPaysheetFrequencyService{
	
	private static final Logger logger = LoggerFactory.getLogger(IndPaysheetFrequencyServiceImpl.class);

	@Autowired
	IndPaysheetFrequencyDao indPaysheetFrequencyDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public ResponseList<IndPaysheetFrequency> getIndPaysheetFrequencyListByOrgLocationId(OrgLocations orgLocation,
			IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception {
		return indPaysheetFrequencyDao.getIndPaysheetFrequencyListByOrgLocationId(orgLocation, indPaysheetFrequencyDTO);
	}

	@Override
	public IndPaysheetFrequency addIndPaysheetFrequency(IndPaysheetFrequency paySheetFreq) {
		return indPaysheetFrequencyDao.addIndPaysheetFrequency(paySheetFreq);
	}
	
	@Override
	public ResponseList<IndEmployeePayroll> getIndEmployeePayrollListByPaysheetFrequencyUuid(String paysheetFrequencyUuid) throws Exception {
		return indPaysheetFrequencyDao.getIndEmployeePayrollListByPaysheetFrequencyUuid(paysheetFrequencyUuid);
	}
	
	@Override
	public Response<IndEmployeePayroll> getIndEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception {
		return indPaysheetFrequencyDao.getIndEmployeePayrollByEmpPayrollUUID(empPayrollUUID);
	}

	@Override
	public Response<IndPaysheetFrequencyDTO> getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID, OrgLocations orgLocation)
			throws Exception {
		Response<IndPaysheetFrequencyDTO> response = new Response<>();
		IndPaysheetFrequency freq = indPaysheetFrequencyDao.getIndPaysheetFrequencyByEmpPayrollUUID(payrollFrequencyUUID);
		if(freq != null) {
			IndPaysheetFrequencyDTO freqDTO = new IndPaysheetFrequencyDTO();
			freqDTO.setId(freq.getId().toString());
			freqDTO.setGroupId(freq.getUserGroup().getId().toString());
			freqDTO.setGroupName(freq.getUserGroup().getGroupName());
			freqDTO.setFrequencyStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),freq.getFrequencyStartDate()));
			freqDTO.setFrequencyEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),freq.getFrequencyEndDate()));
			freqDTO.setPayCheckDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(),freq.getPayCheckDate()));
			freqDTO.setPaySheetFrequencyUUID(freq.getPaySheetFrequencyUUID());
			response.setObject(freqDTO);
		}
		return response;
	}
}
