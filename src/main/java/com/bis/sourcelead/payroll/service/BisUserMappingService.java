package com.bis.sourcelead.payroll.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.EmployeeListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;

public interface BisUserMappingService {


	Integer saveBisUserData(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;

	List<BisUserMapping> getBisUserListByOrgLocationId(Long orgLocationId)throws Exception;

	BisUserMapping mergeUser(JSONObject jsonObj, Long orgLocationId)throws Exception;

	List<BisUserMapping> getMappedUsersListByOrgLocationId(Long orgLocationId)throws Exception;

	Integer saveChecksData(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;

	ResponseList<BisUserMapping> getEmployeeList(EmployeeListDTO employeeListDTO, Long orgLocationId)throws Exception;

	List<BisUserMapping> getMasterListByEmpId(Long empId, OrgLocations orgLocations)throws Exception;
	
	int getBisUserMappingVersionByLocationId(Long locationId);
	
	int getChecksVersionByLocationId(Long locationId);

}
