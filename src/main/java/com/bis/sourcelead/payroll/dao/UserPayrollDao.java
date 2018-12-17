package com.bis.sourcelead.payroll.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ChecksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.UserPayroll;

public interface UserPayrollDao {

	UserPayroll saveUserPayroll(UserPayroll userPayroll)throws Exception;

	List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationID, Date startDate, Date endDate)throws Exception;

	List<UserPayroll> getPayByUserId(Long userId, Long orgLocationId, Date startDay, Date endDay)throws Exception;

	ResponseList<Checks> getChecksList(ChecksDTO checksDTO, Long orgLocationId)throws Exception;

	List<Checks> getChecksListByEmployeeId(Long empId, OrgLocations orgLocations)throws Exception;

	Checks getUniqueChecksListByEmployeeId(String empId, Long orgLocationId)throws Exception;

	Object deletePayrollEntitiesByUUIDAndEntityType(Object obj);

	Object getPayrollEntitiesByUUIDAndEntityType(String uuid, String entityType);

	ResponseList<Checks> getChecksListByEmployeeId(OrgLocations orgLocations, ChecksDTO checksDTO);

}
