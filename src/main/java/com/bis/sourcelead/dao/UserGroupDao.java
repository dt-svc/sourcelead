package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserIdView;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;

public interface UserGroupDao {

	UserGroup saveUserGroup(UserGroup userGroup);

	List<UserIdView> getCommaSeperatedUserId(Long orgLocationId);

	ResponseList<UserGroup> findAllUserGroup(UserGroupDTO userGroupDTO, OrgLocations orgLocation);

	boolean validateUserGroupIdOrGroupName(Long locationId, UserGroupDTO userGroupDTO);

	List<UserGroup> getAllUserGroupsByLocationId(OrgLocations orgLocation);

	UserGroup findUserGroupById(String groupUUID);

	UserGroup getUserGroupByGroupId(long id);

	List<TotalHoursDTO> getTotalHoursByCandidateId(Long candidateId, Date actualStartDate, Date actualEndDate);

	List<UserGroup> getAllUserGroupsByLocationIdAndPayFrequency(OrgLocations orgLocation, String payFrequency);

	List<PayPeriodDTO> getPayPeriodsByGroupId(Long groupId);

	List<UserGroup> getAllUserGroupsByLocationIdAndDepartmentId(OrgLocations orgLocation, String userId);

	List<UserGroup> getAllUserGroupsByLocationIdAndPayrollFrequency(OrgLocations orgLocation, String autoPayrollFrequency);
	
	List<IndPaysheetFrequency> getIndPayPeriodsByGroupId(Long groupId, OrgLocations orgLocation);
}
