package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.LeaveTypeEditDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;

public interface AssociateBalanceLeaveDao {

	int addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO, User loginUser, OrgLocations orgLocations);

	ResponseList<AssociateBalanceLeaveView> getAssociateBalanceLeavesByUserId(Long userId, Long orgLocationId);

	AssociateBalanceLeave getAssociateBalanceByUUID(String assoBalLeaveUUID);

	int updateAssociateBalLeave(String assoBalLeaveUUID, String totalLeaves);

	AssociateBalanceLeave getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId, Long orgLocationId, Long orgId);

	int updateAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType,int status,float diffLeaves,String actionType, String methodType);

	AssociateBalanceLeave saveOrUpdateAssocicateBalanceLeaves(AssociateBalanceLeave assBalLeave);

	int updateAssociateAccuralBalanceLeaveCount(List<AssociateBalanceLeaveDTO> assBalList);

	List<AssociateBalanceLeave> getAssoBalLeavesByUserId(Long userId, Long orgLocationId);
	
	int updateAssociateDateOfJoinBalanceLeaveType(Long orgLocationId, User user, int status, String methodType);

	int addAssociateLeaveType(User loginUser, Long orgLocationId, Employees employee, UserRole userRole);

	int updateLeaveTypeAssociateBalances(List<LeaveTypeEditDTO> list);

	int saveAssociateBalLeaveHistory(List<AssociateBalanceLeaveDTO> assBalList, Date startDate, Date endDate);

}
