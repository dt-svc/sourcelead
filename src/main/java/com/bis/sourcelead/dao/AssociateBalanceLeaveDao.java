package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateBalanceLeaveDao {

	int addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO, User loginUser, OrgLocations orgLocations);

	ResponseList<AssociateBalanceLeaveView> getAssociateBalanceLeavesByUserId(Long userId, Long orgLocationId);

	AssociateBalanceLeave getAssociateBalanceByUUID(String assoBalLeaveUUID);

	int updateAssociateBalLeave(String assoBalLeaveUUID, String totalLeaves);

	AssociateBalanceLeave getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId, Long orgLocationId);
}
