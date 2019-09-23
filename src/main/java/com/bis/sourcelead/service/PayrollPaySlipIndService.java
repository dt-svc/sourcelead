package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.PayrollPaySlipIndDTO;
import com.bis.sourcelead.entity.PayrollPaySlipInd;

public interface PayrollPaySlipIndService {

	PayrollPaySlipInd savePayrollPaySlipInd(PayrollPaySlipInd payrollPaySlipInd) throws Exception;

	String  savePayrollPaySlipIndDTO(PayrollPaySlipIndDTO  PayrollPaySlipIndDTO) throws Exception;
	
	PayrollPaySlipInd getPayrollPayslipByUUID(String uuid)throws Exception;
	
	PayrollPaySlipInd validatePayslipNameByUUIDAndPayslipName(String uuid,String payslipName)throws Exception;
	
	Boolean validatePayslipNameByOrglocationIdAndPayslipName(Long orglocationId,String paySlipName)throws Exception;
	
	List<PayrollPaySlipInd> getIndPayrollPaySlipListByOrglocationInd(Long orglocationId) throws Exception;
	
	PayrollPaySlipInd deletePermissionStatsuByDeletePermission(String deletePermission,Long orgLocationId)throws Exception;
	
	PayrollPaySlipInd getActivePayslipByOrglocationId(Long orgLocationId)throws Exception;

	
}
