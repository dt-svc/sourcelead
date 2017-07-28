package com.bis.sourcelead.payroll.dao;


import java.util.List;

import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;

public interface BisUserMappingDao {

	BisUserMapping saveBisUserMapping(BisUserMapping bisUserMapping) throws Exception;

	List<BisUserMapping> getBisUserListByOrgLocationId(Long orgLocationId)throws Exception;

	BisUserMapping getBisUserById(Long importedUserId)throws Exception;

	List<BisUserMapping> getMappedUsersListByOrgLocationId(Long orgLocationId)throws Exception;

	Checks saveChecks(Checks checks)throws Exception;

	List<BisUserMapping> getEmployeeList(Long orgLocationId)throws Exception;





}
