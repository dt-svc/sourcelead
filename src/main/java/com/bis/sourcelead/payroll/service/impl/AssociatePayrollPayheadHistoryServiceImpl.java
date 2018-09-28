package com.bis.sourcelead.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.payroll.dao.AssociatePayrollPayheadHistoryDao;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class AssociatePayrollPayheadHistoryServiceImpl  implements AssociatePayrollPayheadHistoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(AssociatePayrollPayheadHistoryServiceImpl.class);
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	AssociatePayrollPayheadHistoryDao associatePayrollPayheadHistoryDao;
	
	
	@Override
	public ResponseList<AssociatePayrollPayheadHistoryDTO> getUpdatedAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception {
		List<AssociatePayrollPayheadHistory> associatePayrollList = associatePayrollPayheadHistoryDao.getUpdatedAssociatePayrollPayheadByEmpPayrollId(empPayrollId);
		ResponseList<AssociatePayrollPayheadHistoryDTO> res = new ResponseList<>();
		
		List<AssociatePayrollPayheadHistoryDTO> totalList = new ArrayList<>();
		associatePayrollList.forEach(pay -> {
			AssociatePayrollPayheadHistoryDTO apd = new AssociatePayrollPayheadHistoryDTO();
			apd.setPayrollPayheadName(pay.getPayrollPayhead().getName());
			apd.setMonthly(pay.getMonthly());
			

			totalList.add(apd);
		});
		res.setData(totalList);
		
		return res;
	}
	
	
}

	
