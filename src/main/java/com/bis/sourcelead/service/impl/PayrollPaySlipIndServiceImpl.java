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
		}
		
		pps.setPaySlipName(payrollPaySlipIndDTO.getPaySlipName());
		pps.setPaySlipDescription(payrollPaySlipIndDTO.getPaySlipDescription());

		
		//Company releated code
		if(payrollPaySlipIndDTO.getCompanyName() != null && payrollPaySlipIndDTO.getCompanyName() != "") {
			pps.setCompanyName(payrollPaySlipIndDTO.getCompanyName());
		}
		
		if(payrollPaySlipIndDTO.getCompanyAddress() != null && payrollPaySlipIndDTO.getCompanyAddress() != "") {
			pps.setCompanyAddress(payrollPaySlipIndDTO.getCompanyAddress());
		}
		if(payrollPaySlipIndDTO.getCompanyContactDetails() != null && payrollPaySlipIndDTO.getCompanyContactDetails() != "") {
			pps.setCompanyContactDetails(payrollPaySlipIndDTO.getCompanyContactDetails());
		}
		if(payrollPaySlipIndDTO.getCompanyLogo() != null && payrollPaySlipIndDTO.getCompanyLogo() != "") {
			pps.setCompanyLogo(payrollPaySlipIndDTO.getCompanyLogo());
		}
		
		
		//Employee Reated code
		if(payrollPaySlipIndDTO.getEmployeeId() != null && payrollPaySlipIndDTO.getEmployeeId() != "") {
			pps.setEmployeeId(payrollPaySlipIndDTO.getEmployeeId());
		}
		if(payrollPaySlipIndDTO.getEmployeeType() != null && payrollPaySlipIndDTO.getEmployeeType() != "") {
			pps.setEmployeeType(payrollPaySlipIndDTO.getEmployeeType());
		}
		if(payrollPaySlipIndDTO.getEmpProject() != null && payrollPaySlipIndDTO.getEmpProject() != "") {
			pps.setEmployeeProject(payrollPaySlipIndDTO.getEmpProject());
		}
		if(payrollPaySlipIndDTO.getEmployeePfNumber() != null && payrollPaySlipIndDTO.getEmployeePfNumber() !="") {
			pps.setEmployeePfNumber(payrollPaySlipIndDTO.getEmployeePfNumber());
		}
		if(payrollPaySlipIndDTO.getEmployeeEsiNumber() != null && payrollPaySlipIndDTO.getEmployeeEsiNumber() != "") {
			pps.setEmployeeEsiNumber(payrollPaySlipIndDTO.getEmployeeEsiNumber());
		}	
		if(payrollPaySlipIndDTO.getEmployeeBankDetails() != null && payrollPaySlipIndDTO.getEmployeeBankDetails() !="") {
			pps.setEmployeeBankDetails(payrollPaySlipIndDTO.getEmployeeBankDetails());
		}
		if(payrollPaySlipIndDTO.getEmployeeDesignation() != null && payrollPaySlipIndDTO.getEmployeeDesignation() != "") {
			pps.setEmployeeDesignation(payrollPaySlipIndDTO.getEmployeeDesignation());
		}
		if(payrollPaySlipIndDTO.getEmployeeName() != null && payrollPaySlipIndDTO.getEmployeeName() != "") {
			pps.setEmployeeName(payrollPaySlipIndDTO.getEmployeeName());
		}
		if(payrollPaySlipIndDTO.getEmpContactDetails() != null && payrollPaySlipIndDTO.getEmpContactDetails() != "") {
			pps.setEmployeeContactDetails(payrollPaySlipIndDTO.getEmpContactDetails());
		}
		if(payrollPaySlipIndDTO.getEmpJoiningdate() != null && payrollPaySlipIndDTO.getEmpJoiningdate() != "") {
			pps.setEmployeeJoinDate(payrollPaySlipIndDTO.getEmpJoiningdate());
		}
		if(payrollPaySlipIndDTO.getEmployeeLocation() != null && payrollPaySlipIndDTO.getEmployeeLocation() != "") {
			pps.setEmployeeLocation(payrollPaySlipIndDTO.getEmployeeLocation());
		}
		
		//Leave Related code
		if(payrollPaySlipIndDTO.getLeaveAttended() != null && payrollPaySlipIndDTO.getLeaveAttended() != "") {
			pps.setLeaveAttended(payrollPaySlipIndDTO.getLeaveAttended());
		}
		if(payrollPaySlipIndDTO.getLeaveLeaves() != null && payrollPaySlipIndDTO.getLeaveLeaves() != "") {
			pps.setLeaveLeaves(payrollPaySlipIndDTO.getLeaveLeaves());
		}
		if(payrollPaySlipIndDTO.getLeaveLop() != null && payrollPaySlipIndDTO.getLeaveLop() != "") {
			pps.setLeaveLop(payrollPaySlipIndDTO.getLeaveLop());
		}
		
		
		if(payrollPaySlipIndDTO.getPreviousMonth() != null && payrollPaySlipIndDTO.getPreviousMonth() != "") {
			pps.setPreviousMonthPaid(payrollPaySlipIndDTO.getPreviousMonth());
		}
		if(payrollPaySlipIndDTO.getRegularHours() != null && payrollPaySlipIndDTO.getRegularHours() != "") {
			pps.setRegularHours(payrollPaySlipIndDTO.getRegularHours());
		}
		if(payrollPaySlipIndDTO.getOvertimeHours() != null && payrollPaySlipIndDTO.getOvertimeHours() != "") {
			pps.setOvertimeHours(payrollPaySlipIndDTO.getOvertimeHours());
		}
		
		pps.setOrgLocationId(payrollPaySlipIndDTO.getOrgLocation());
		pps.setStatus(Constants.RECORD_ACTIVE);
		pps.setCreatedDate(new Date());
		pps.setCreatedBy(payrollPaySlipIndDTO.getUser());
		
		payrollPaySlipIndDao.savePayrollPaySlipInd(pps);
		
		return "success";
	}


	
}
