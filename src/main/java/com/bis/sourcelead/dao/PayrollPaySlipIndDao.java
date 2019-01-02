package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.PayrollPaySlipInd;

public interface PayrollPaySlipIndDao {
	
	/**
	 * @author Laxman
	 * @param department
	 * @return
	 * @throws Exception
	 */
	PayrollPaySlipInd savePayrollPaySlipInd(PayrollPaySlipInd payrollPaySlipInd) throws Exception;

	PayrollPaySlipInd getPayrollPayslipByUUID(String uuid)throws Exception;

    PayrollPaySlipInd validatePayslipNameByUUIDAndPayslipName(String uuid,String payslipName)throws Exception;
	
	Boolean validatePayslipNameByOrglocationIdAndPayslipName(Long orglocationId,String paySlipName)throws Exception;
	
	List<PayrollPaySlipInd> getIndPayrollPaySlipListByOrglocationInd(Long orglocationId) throws Exception;

	PayrollPaySlipInd deletePermissionStatsuByDeletePermission(String deletePermission, Long orgLocationId)throws Exception;
	
	PayrollPaySlipInd getActivePayslipByOrglocationId(Long orgLocationId)throws Exception;
}

