package com.bis.sourcelead.payroll.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.payroll.entity.UserPayroll;

public interface UserPayrollService {

	UserPayroll saveUserPayroll(UserPayroll userPayroll)throws Exception;

	List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationId, Date startDate, Date endDate)throws Exception;
}
