package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface LeaveService {

	Leave saveLeave(LeaveDTO leaveDTO, OrgLocations orgLocations, User user) throws Exception;

}
