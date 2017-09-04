package com.bis.sourcelead.payroll.dao;


import java.util.List;

import com.bis.sourcelead.dto.EmployeeListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;

public interface BisUserMappingDao {

	BisUserMapping saveBisUserMapping(BisUserMapping bisUserMapping) throws Exception;

	List<BisUserMapping> getBisUserListByOrgLocationId(Long orgLocationId)throws Exception;

	BisUserMapping getBisUserById(Long importedUserId)throws Exception;

	List<BisUserMapping> getMappedUsersListByOrgLocationId(Long orgLocationId)throws Exception;

	Checks saveChecks(Checks checks)throws Exception;

	ResponseList<BisUserMapping> getEmployeeList(EmployeeListDTO employeeListDTO, Long orgLocationId)throws Exception;

	List<BisUserMapping> getMasterListByEmpId(Long empId, OrgLocations orgLocations)throws Exception;





}
