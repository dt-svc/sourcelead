package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateBalanceLeaveService {

	Response<AssociateBalanceLeaveDTO> addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO, User loginUser, OrgLocations orgLocations);

	List<AssociateBalanceLeaveDTO> getAssociateBalanceLeavesByUserId(Long userId, OrgLocations orgLocations, User loginUser);

	boolean getAssociateBalanceByUUID(String assoBalLeaveUUID, String totalLeaves);

	int getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId);
}
