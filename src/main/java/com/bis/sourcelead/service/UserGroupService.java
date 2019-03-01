package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;

public interface UserGroupService {

	Response<UserGroup> createUserGroup(OrgLocations orgLocations, UserGroupDTO userGroupDTO, User loginUser);

	ResponseList<UserGroupDTO> findAllUserGroup(UserGroupDTO userGroupDTO, OrgLocations orgLocation);

	Response<UserGroup> validateUserGroupIdOrGroupName(OrgLocations orgLocations, UserGroupDTO userGroupDTO);

	List<UserGroup> getAllUserGroupsByLocationId(OrgLocations orgLocation);

	Response<UserGroupDTO> findUserGroupById(String groupUUID);

	Response<UserGroupDTO> deleteUserGroupByUUID(String groupUUID, User loginUser);

	List<UserGroup> getAllUserGroupsByLocationIdAndPayFrequency(OrgLocations orgLocation, String payFrequency);

	List<PayPeriodDTO> getPayPeriodsByGroupId(Long groupId, OrgLocations orgLocation) throws Exception;

	List<UserGroup> getAllUserGroupsByLocationIdAndDepartmentId(OrgLocations orgLocation, String userId);

	List<UserGroup> getAllUserGroupsByLocationIdAndPayrollFrequency(OrgLocations orgLocation, String autoPayrollFrequency);
	
	List<IndPaysheetFrequency> getIndPayPeriodsByGroupId(Long groupId, OrgLocations orgLocation) throws Exception;
}
