package com.bis.sourcelead.payroll.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.UserPayroll;

public interface UserPayrollDao {

	UserPayroll saveUserPayroll(UserPayroll userPayroll)throws Exception;

	List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationID, Date startDate, Date endDate)throws Exception;

	List<UserPayroll> getPayByUserId(Long userId, Long orgLocationId, Date startDay, Date endDay)throws Exception;

	List<Checks> getChecksList(Long userId, Long orgLocationId)throws Exception;

}
