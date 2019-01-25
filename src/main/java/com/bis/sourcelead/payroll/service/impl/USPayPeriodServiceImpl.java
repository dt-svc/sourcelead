package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.USPayPeriodDao;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.entity.USPayPeriod;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;
import com.bis.sourcelead.payroll.service.USPayPeriodService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class USPayPeriodServiceImpl implements USPayPeriodService{

	@Autowired
	USPayPeriodDao uSPayPeriodDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public USPayPeriod getPayPeriodNumber(Long payFrequencyId)
	{
		return uSPayPeriodDao.getPayPeriodNumber(payFrequencyId);
	}
	@Override
	public USPayPeriod savePayPeriodNumber(USPayPeriod uSPayPeriod)
	{
		return uSPayPeriodDao.savePayPeriodNumber(uSPayPeriod);
	}
	@Override
	public ResponseList<PaysheetFrequencyDTO> getPayPeriodListByOrgLocationId(OrgLocations orgLocation,
			PaysheetFrequencyDTO paysheetFrequencyDTO) throws Exception {
		ResponseList<USPayPeriod> periodList = uSPayPeriodDao.getPayPeriodListByOrgLocationId(orgLocation,paysheetFrequencyDTO);
		ResponseList<PaysheetFrequencyDTO> res = new ResponseList<>();
		List<PaysheetFrequencyDTO> totalList = new ArrayList<>();
		periodList.getData().forEach(pay -> {
			PaysheetFrequencyDTO pfd = new PaysheetFrequencyDTO();
			pfd.setPayPeriodNum(pay.getPayPeriodNum());
			pfd.setGroupName(pay.getPaysheetFrequency().getUserGroup().getGroupName());
			pfd.setPaySheetFrequencyuuid(pay.getPaysheetFrequency().getPaySheetFrequencyUUID());
			pfd.setPayFrequency(pay.getPaysheetFrequency().getUserGroup().getPayFrequency());
			pfd.setPayDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getPaysheetFrequency().getPayCheckDate()));
			pfd.setPaySheetFrequencyType(pay.getPaysheetFrequency().getType());
			pfd.setApproverStatus(pay.getApproverStatus());
			
			StringBuilder sbDate = new StringBuilder();
			pfd.setPayPeriod(sbDate.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getPaysheetFrequency().getFrequencyStartDate())).append(" / ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getPaysheetFrequency().getFrequencyEndDate())).toString());
		
			Set<EmployeePayroll> payRollSet = pay.getPaysheetFrequency().getEmployeepayroll();
			double sum = payRollSet.stream().filter(paySet -> paySet.getPayPeriodNum() != null && Integer.parseInt(paySet.getPayPeriodNum()) == pay.getPayPeriodNum()).mapToDouble(i->i.getNetPay().doubleValue()).sum();
			pfd.setTotal(BigDecimal.valueOf(sum));
			pfd.setGroupId(String.valueOf(pay.getPaysheetFrequency().getUserGroup().getId()));
			if(sum > 0)
			totalList.add(pfd);
		});
		res.setData(totalList);
		res.setRecordsFiltered(totalList.size());
		res.setRecordsTotal(totalList.size());
		return res;
	}
	
	@Override
	public int updatedPayperiod(String payPeriodNum)
	{
		int isUpdated = 0;
		try {
			isUpdated = uSPayPeriodDao.updatedPayperiod(payPeriodNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	@Override
	public USPayPeriod getPayPeriodByOrglocationId(OrgLocations orgLocation,Long payfrequencyId) {
		return uSPayPeriodDao.getPayPeriodByOrglocationId(orgLocation, payfrequencyId);
	}
	@Override
	public List<EmployeePayroll> getEmployeePayrollListByPayperiodNum(Integer payPeriodNum) {
		return uSPayPeriodDao.getEmployeePayrollListByPayperiodNum(payPeriodNum);
	}
	
}
