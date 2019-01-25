package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.PayrollPaySlipIndDao;
import com.bis.sourcelead.dto.PayrollPaySlipIndDTO;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PayrollPaySlipIndServiceImpl implements PayrollPaySlipIndService{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	PayrollPaySlipIndDao payrollPaySlipIndDao;
	
	 @Autowired
	 private CommonUtil commonUtil;

	@Override
	public PayrollPaySlipInd savePayrollPaySlipInd(PayrollPaySlipInd payrollPaySlipInd) throws Exception {
		return payrollPaySlipIndDao.savePayrollPaySlipInd(payrollPaySlipInd);
	}


	@Override
	public List<PayrollPaySlipInd> getIndPayrollPaySlipListByOrglocationInd(Long orglocationId) throws Exception {
		return payrollPaySlipIndDao.getIndPayrollPaySlipListByOrglocationInd(orglocationId);
	}
	

	@Override
	public PayrollPaySlipInd getPayrollPayslipByUUID(String uuid) throws Exception {
		return payrollPaySlipIndDao.getPayrollPayslipByUUID(uuid);
	}

	@Override
	public PayrollPaySlipInd validatePayslipNameByUUIDAndPayslipName(String uuid, String payslipName) throws Exception {
		return payrollPaySlipIndDao.validatePayslipNameByUUIDAndPayslipName(uuid, payslipName);
	}


	@Override
	public Boolean validatePayslipNameByOrglocationIdAndPayslipName(Long orglocationId, String paySlipName) throws Exception {
		return payrollPaySlipIndDao.validatePayslipNameByOrglocationIdAndPayslipName(orglocationId, paySlipName);
	}
	
	
	@Override
	public String savePayrollPaySlipIndDTO(PayrollPaySlipIndDTO payrollPaySlipIndDTO) throws Exception {
		PayrollPaySlipInd pps = null;
		
		if(payrollPaySlipIndDTO.getUuid() != null && payrollPaySlipIndDTO.getUuid()!= "") {
			pps = payrollPaySlipIndDao.getPayrollPayslipByUUID(payrollPaySlipIndDTO.getUuid());
			pps.setUpdatedBy(payrollPaySlipIndDTO.getUser());
			pps.setUpdatedDate(new Date());
		}else {
			 pps = new PayrollPaySlipInd();
			 pps.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
			 pps.setIsActive(Constants.NO);
			 pps.setDeletePermission(Constants.YES);
		}
		
		pps.setPaySlipName(payrollPaySlipIndDTO.getPaySlipName());
		pps.setPaySlipDescription(payrollPaySlipIndDTO.getPaySlipDescription());

		
		//Company releated code
			pps.setCompanyName(payrollPaySlipIndDTO.getCompanyName());
			pps.setCompanyAddress(payrollPaySlipIndDTO.getCompanyAddress());
			pps.setCompanyContactDetails(payrollPaySlipIndDTO.getCompanyContactDetails());
			pps.setCompanyLogo(payrollPaySlipIndDTO.getCompanyLogo());
		
		//Employee Reated code
			pps.setEmployeeId(payrollPaySlipIndDTO.getEmployeeId());
			pps.setEmployeeType(payrollPaySlipIndDTO.getEmployeeType());
			pps.setEmployeeUanNumber(payrollPaySlipIndDTO.getEmployeeUanNumber());
			pps.setEmployeePfNumber(payrollPaySlipIndDTO.getEmployeePfNumber());
			pps.setEmployeeEsiNumber(payrollPaySlipIndDTO.getEmployeeEsiNumber());
			pps.setEmployeeBankDetails(payrollPaySlipIndDTO.getEmployeeBankDetails());
			pps.setEmployeeDesignation(payrollPaySlipIndDTO.getEmployeeDesignation());
			pps.setEmployeeName(payrollPaySlipIndDTO.getEmployeeName());
			pps.setEmployeeContactDetails(payrollPaySlipIndDTO.getEmpContactDetails());
			pps.setEmployeeJoinDate(payrollPaySlipIndDTO.getEmpJoiningdate());
			pps.setEmployeeLocation(payrollPaySlipIndDTO.getEmployeeLocation());
			pps.setEmployeePanNumber(payrollPaySlipIndDTO.getEmployeePanNumber());
			
		
		//Leave Related code
			pps.setLeaveAttended(payrollPaySlipIndDTO.getLeaveAttended());
			pps.setLeaveLeaves(payrollPaySlipIndDTO.getLeaveLeaves());
			pps.setLeaveLop(payrollPaySlipIndDTO.getLeaveLop());
		
		
			pps.setPreviousMonthPaid(payrollPaySlipIndDTO.getPreviousMonth());
			pps.setRegularHours(payrollPaySlipIndDTO.getRegularHours());
			pps.setOvertimeHours(payrollPaySlipIndDTO.getOvertimeHours());
		
			pps.setOrgLocationId(payrollPaySlipIndDTO.getOrgLocation());
			pps.setStatus(Constants.RECORD_ACTIVE);
			pps.setCreatedDate(new Date());
			pps.setCreatedBy(payrollPaySlipIndDTO.getUser());
		
		payrollPaySlipIndDao.savePayrollPaySlipInd(pps);
		
		return "success";
	}


	@Override
	public PayrollPaySlipInd deletePermissionStatsuByDeletePermission(String deletePermission,Long orgLocationId) throws Exception {
		return payrollPaySlipIndDao.deletePermissionStatsuByDeletePermission(deletePermission,orgLocationId);
	}


	@Override
	public PayrollPaySlipInd getActivePayslipByOrglocationId(Long orgLocationId) throws Exception {
		return payrollPaySlipIndDao.getActivePayslipByOrglocationId(orgLocationId);
	}


	
}
