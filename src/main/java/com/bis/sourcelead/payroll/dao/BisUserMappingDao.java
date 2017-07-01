package com.bis.sourcelead.payroll.dao;


import java.util.List;

import com.bis.sourcelead.payroll.entity.BisUserMapping;

public interface BisUserMappingDao {

	BisUserMapping saveBisUserMapping(BisUserMapping bisUserMapping) throws Exception;

	List<BisUserMapping> getBisUserListByOrgLocationId(Long orgLocationId)throws Exception;

	BisUserMapping getBisUserById(Long importedUserId)throws Exception;

	List<BisUserMapping> getMappedUsersListByOrgLocationId(Long orgLocationId)throws Exception;




}
