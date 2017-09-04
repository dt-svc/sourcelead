package com.bis.sourcelead.payroll.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ChecksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.UserPayroll;

public interface UserPayrollService {

	UserPayroll saveUserPayroll(UserPayroll userPayroll)throws Exception;

	List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationId, Date startDate, Date endDate)throws Exception;
	
	List<UserPayroll> getPayByUserId(Long userId,Long orgLocationId,String selectedYear)throws Exception;

	ResponseList<Checks> getChecksList(ChecksDTO checksDTO, Long orgLocationId)throws Exception;

	List<Checks> getChecksListByEmployeeId(Long empId, OrgLocations orgLocations)throws Exception;
}
