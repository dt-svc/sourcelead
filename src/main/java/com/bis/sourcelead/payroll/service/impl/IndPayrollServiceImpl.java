package com.bis.sourcelead.payroll.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.service.IndPayrollService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class IndPayrollServiceImpl  implements IndPayrollService{
	
	private static final Logger logger = LoggerFactory.getLogger(IndPayrollServiceImpl.class);
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	IndPaysheetFrequencyDao indPaysheetFrequencyDao;
	
	
	@Override
	public ResponseList<IndPaysheetFrequencyDTO> getPayPeriodListByOrgLocationId(OrgLocations orgLocation,
			IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception {
		ResponseList<IndPaysheetFrequency> frequencyList = indPaysheetFrequencyDao.getIndPaysheetFrequencyListByOrgLocationId(orgLocation, indPaysheetFrequencyDTO);
		ResponseList<IndPaysheetFrequencyDTO> res = new ResponseList<>();
		List<IndPaysheetFrequencyDTO> totalList = new ArrayList<>();
		frequencyList.getData().forEach(pay -> {
			IndPaysheetFrequencyDTO pfd = new IndPaysheetFrequencyDTO();
			
			pfd.setPayRunId(String.valueOf(pay.getPayRunId()));
			pfd.setGroupId(String.valueOf(pay.getUserGroup().getId()));
			pfd.setGroupName(pay.getUserGroup().getGroupName());
			StringBuilder sbDate = new StringBuilder();
			pfd.setPayPeriod(sbDate.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyStartDate())).append(" / ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyEndDate())).toString());
			pfd.setPayDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getPayCheckDate()));
			//pfd.setType(pay.getType());
			//Set<EmployeePayroll> payRollSet = pay.getEmployeepayroll();
			//double sum = payRollSet.stream().filter(paySet -> paySet.getPayPeriodNum() != null && Integer.parseInt(paySet.getPayPeriodNum()) == pay.getPayPeriodNum()).mapToDouble(i->i.getNetPay().doubleValue()).sum();
			//pfd.setTotal(BigDecimal.valueOf(sum));
			pfd.setApproverStatus(pay.getApproverStatus());

			totalList.add(pfd);
		});
		res.setData(totalList);
		res.setRecordsFiltered(totalList.size());
		res.setRecordsTotal(totalList.size());
		return res;
	}
	
	
}

	
