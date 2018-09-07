package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;

public interface AssociateBalanceLeaveService {

	Response<AssociateBalanceLeaveDTO> addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO, User loginUser, OrgLocations orgLocations);

	List<AssociateBalanceLeaveDTO> getAssociateBalanceLeavesByUserId(Long userId, OrgLocations orgLocations, User loginUser);

	boolean getAssociateBalanceByUUID(String assoBalLeaveUUID, String totalLeaves);
	
	AssociateBalanceLeave getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId,Long userId,Long orgLocationId, Long orgId);

	float getTotalLeavesCountByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId, Long orgLocationId, Long orgId);

	int addLeaveTypeToAssociate(List<User> userList, OrgLocations orgLocation, LeaveType leaveType, User loginUser, float noOfLeaves);

	int updateAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType,int satus);

	int addOrSubAssociateBalanceLeaveType(List<User> userList, Long locationId, LeaveType leaveType, int status, float diffLeaves, String actionType, String methodType);

	AssociateBalanceLeave saveOrUpdateAssocicateBalanceLeaves(AssociateBalanceLeave assBalLeave);

	int updateAssociateAccuralBalanceLeaveCount(List<AssociateBalanceLeaveDTO> assBalList);

	int calculateAndUpdateAssociateBalanceLeaves(OrgLocations orgLocation, LeaveType leaveType, Date startDate, Date endDate) throws Exception;
	
	List<AppliedLeaveDateView> getExcludeHolidayLeaveCountList(List<AppliedLeaveDateView> appliedLeaves, OrgLocations orgLocations, AssociateBalanceLeaveView l, Date startDate, Date endDaet);

	int updateAssociateBalanceLeaves(User updatedUser, OrgLocations orgLocations, Employees employees, UserRole userRole);
	
	int updateAssociateDateOfJoinBalanceLeaveType(Long orgLocationId, User user, int status, String methodType);
	
	int addAssociateLeaveType(User loginUser, Long orgLocationId, Employees employee, UserRole userRole);

	int updateLeaveTypeAssociateBalances(List<User> updatedUserList, LeaveType leaveType, LeaveTypeDTO leaveTypeDTO);
	
	double getExcludeHolidayLeaveCount(List<AppliedLeaveDateView> appliedLeaves, OrgLocations orgLocations, AssociateBalanceLeaveView l, Date startDate, Date endDaet, LeaveType leaveType);
	
}
