package com.bis.sourcelead.indPayroll.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.indPayroll.dao.AssociatePayrollPayheadHistoryDao;
import com.bis.sourcelead.indPayroll.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
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
			apd.setPayrollPayheadShortCode(pay.getPayrollPayhead().getShortCode());
			apd.setPayrollPayheadType(pay.getPayrollPayhead().getType());
			totalList.add(apd);
		});
		res.setData(totalList);
		
		return res;
	}
	
	@Override
	public ResponseList<AssociatePayrollPayheadHistoryDTO> getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(String empPayrollUUID) throws Exception {
		List<AssociatePayrollPayheadHistory> associatePayrollList = associatePayrollPayheadHistoryDao.getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(empPayrollUUID);
		ResponseList<AssociatePayrollPayheadHistoryDTO> res = new ResponseList<>();
		List<AssociatePayrollPayheadHistoryDTO> totalList = new ArrayList<>();
		associatePayrollList.forEach(pay -> {
			AssociatePayrollPayheadHistoryDTO apd = new AssociatePayrollPayheadHistoryDTO();
			apd.setPayrollPayheadName(pay.getPayrollPayhead().getName());
			apd.setMonthly(pay.getMonthly());
			apd.setPayrollPayheadShortCode(pay.getPayrollPayhead().getShortCode());
			apd.setPayrollPayheadType(pay.getPayrollPayhead().getType());
			totalList.add(apd);
		});
		res.setData(totalList);
		
		return res;
	}
	
	
	@Override
	public List<AssociatePayrollPayheadHistory> getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(String paySheetFrequencyUUID)throws Exception {
		
		return associatePayrollPayheadHistoryDao.getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(paySheetFrequencyUUID);
	}

	@Override
	public List<AssociatePayrollPayheadHistory> getLatestAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId)
			throws Exception {
		return associatePayrollPayheadHistoryDao.getLatestAssociatePayrollPayheadByEmpPayrollId(empPayrollId);
	}

	@Override
	public IndEmployeePayroll saveIndEmployeePayroll(IndEmployeePayroll indEmployeePayroll) throws Exception {
		return associatePayrollPayheadHistoryDao.saveIndEmployeePayroll(indEmployeePayroll);
	}
	
}

	
