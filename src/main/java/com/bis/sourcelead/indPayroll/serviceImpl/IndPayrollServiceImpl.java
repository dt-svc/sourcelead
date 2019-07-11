package com.bis.sourcelead.indPayroll.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.indPayroll.dao.IndPaysheetFrequencyDao;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.indPayroll.service.IndPayrollService;
import com.bis.sourcelead.payroll.entity.EmployeeSessionIndPayroll;
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
		ResponseList<IndPaysheetFrequencyDTO> res = new ResponseList<>();
		try {
			ResponseList<IndPaysheetFrequency> frequencyList = indPaysheetFrequencyDao.getIndPaysheetFrequencyListByOrgLocationId(orgLocation, indPaysheetFrequencyDTO);
			List<IndPaysheetFrequencyDTO> totalList = new ArrayList<>();
			frequencyList.getData().forEach(pay -> {
				IndPaysheetFrequencyDTO pfd = new IndPaysheetFrequencyDTO();
				pfd.setId(pay.getId());
				pfd.setPaySheetFrequencyUUID(pay.getPaySheetFrequencyUUID());
				pfd.setPayRunId(String.valueOf(pay.getPayRunId()));
				pfd.setGroupId(String.valueOf(pay.getUserGroup().getId()));
				pfd.setGroupName(pay.getUserGroup().getGroupName());
				StringBuilder sbDate = new StringBuilder();
				pfd.setPayPeriod(sbDate.append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyStartDate())).append(" / ").append(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getFrequencyEndDate())).toString());
				pfd.setPayDate(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), pay.getPayCheckDate()));
				Set<IndEmployeePayroll> payRollSet = pay.getIndEmployeepayroll();
				double sum = payRollSet.stream().mapToDouble(i->i.getNetPay().doubleValue()).sum();
				pfd.setTotal(BigDecimal.valueOf(sum));
				pfd.setApproverStatus(pay.getApproverStatus());
				totalList.add(pfd);
			});
			res.setData(totalList);
			res.setRecordsFiltered(totalList.size());
			res.setRecordsTotal(totalList.size());
			res.setResponse(Result.SUCCESS.name());
			res.setResponseMessage(Result.SUCCESS.getValue());
		}catch (Exception e) {
			res.setResponse(Result.AWKWARD.name());
			res.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return res;
	}

	@Override
	public EmployeeSessionIndPayroll saveEmployeeSessionIndPayroll(EmployeeSessionIndPayroll employeeSessionIndPayroll) throws Exception {
		return indPaysheetFrequencyDao.saveEmployeeSessionIndPayroll(employeeSessionIndPayroll);
	}

	@Override
	public List<EmployeeSessionIndPayroll> getEmployeeSessionList(String paySheetFrequencyUUID) throws Exception {
		return indPaysheetFrequencyDao.getEmployeeSessionList(paySheetFrequencyUUID);
	}

	@Override
	public void deleteIndPayrollCaheRecoedByPaySheetFrequencyUUID(List<EmployeeSessionIndPayroll> indPayrollCahe) {
		indPaysheetFrequencyDao.deleteIndPayrollCaheRecoedByPaySheetFrequencyUUID(indPayrollCahe);
	}

	@Override
	public EmployeeSessionIndPayroll updateThePayHeadHistoryJsonObjectBYExclueUUID(String excludeUUID) throws Exception {
		return indPaysheetFrequencyDao.updateThePayHeadHistoryJsonObjectBYExclueUUID(excludeUUID);
	}
	
	
}

	
