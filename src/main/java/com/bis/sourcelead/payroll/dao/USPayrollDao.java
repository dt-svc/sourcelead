package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.LookUpData;

public interface USPayrollDao {

	List<LookUpData> findAllDeduction();
	
}
