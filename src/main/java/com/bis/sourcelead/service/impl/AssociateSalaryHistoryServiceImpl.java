package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.AssociateSalaryHistoryDTO;
import com.bis.sourcelead.entity.AssociateSalaryHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reports.dao.AssociateSalaryHistoryDao;
import com.bis.sourcelead.service.AssociateSalaryHistoryService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class AssociateSalaryHistoryServiceImpl implements AssociateSalaryHistoryService{

	
	@Autowired
	AssociateSalaryHistoryDao associateSalaryHistoryDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public List<AssociateSalaryHistoryDTO> findAssociateSalHistory(Long associateSalid,OrgLocations orgLocation)
	{
		List<AssociateSalaryHistory> associSalHisList = associateSalaryHistoryDao.findAssociateSalHistory(associateSalid);
		
		if(associSalHisList != null && associSalHisList.size() >0) {
			List<AssociateSalaryHistoryDTO> list = new ArrayList<>();
			associSalHisList.forEach(his->{
				AssociateSalaryHistoryDTO prevAssHisDto = new AssociateSalaryHistoryDTO();
				prevAssHisDto.setPayType(his.getPreviousPayType());
				prevAssHisDto.setPayFrequency(his.getPreviousPayFrequency());
				prevAssHisDto.setStandardHours(his.getPreviousStandardHours());
				prevAssHisDto.setAnnualSalary(his.getPreviousAnnualSalary());
				prevAssHisDto.setGrossSalary(his.getPreviousGrossSalary());
				prevAssHisDto.setHourlyRate(his.getPreviousHourlyRate());
				if(his.getEffectiveDate() !=null) {
					prevAssHisDto.setEffectiveDate(commonUtil.getOrglocationDateFormatHHMMSS(orgLocation.getDateFormat(),his.getEffectiveDate()));	
				}
				if(StringUtils.isNotBlank(his.getIncreasePercentage())) {
					prevAssHisDto.setIncreasePercentage(his.getIncreasePercentage());
				}
				list.add(prevAssHisDto);
				
				AssociateSalaryHistoryDTO assHisDto = new AssociateSalaryHistoryDTO();
				assHisDto.setPayType(his.getPreviousPayType());
				assHisDto.setPayFrequency(his.getPreviousPayFrequency());
				assHisDto.setStandardHours(his.getPreviousStandardHours());
				assHisDto.setAnnualSalary(his.getPreviousAnnualSalary());
				assHisDto.setGrossSalary(his.getPreviousGrossSalary());
				assHisDto.setHourlyRate(his.getPreviousHourlyRate());
				if(his.getEffectiveDate() !=null) {
					assHisDto.setEffectiveDate(commonUtil.getOrglocationDateFormatHHMMSS(orgLocation.getDateFormat(),his.getEffectiveDate()));	
				}
				if(StringUtils.isNotBlank(his.getIncreasePercentage())) {
					assHisDto.setIncreasePercentage(his.getIncreasePercentage());
				}
				
			});
		}
		return null;
	}
}
