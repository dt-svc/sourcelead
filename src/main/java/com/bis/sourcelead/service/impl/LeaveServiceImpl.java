package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OMGVMCID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.FileUploadUtil;

@Service
public class LeaveServiceImpl implements LeaveService{
	
    @Autowired
    CommonUtil commonUtil;
	
    @Autowired
    LeaveTypeService leaveTypeService;
    
    @Autowired
    LeaveDao  leaveDao;
    
    @Autowired
    UserService userService;
    
	@Override
	public Leave saveLeave(LeaveDTO leaveDTO,OrgLocations orgLocations, User user) throws Exception {
		Leave leave = new Leave();
		leave.setOrgLocations(orgLocations);
		leave.setFromDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), leaveDTO.getFromDate()));
		if(StringUtils.isNotBlank(leaveDTO.getUser())) {
			User usr = userService.getUserById(Long.parseLong(leaveDTO.getUser()));
			leave.setUser(usr);
		}else {
			leave.setUser(user);
		}
		leave.setToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), leaveDTO.getToDate()));
		LeaveType leaveType =  leaveTypeService.getLeaveTypeById(leaveDTO.getLeaveType());
		leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
		leave.setLeaveType(leaveType);
		leave.setReason(leaveDTO.getReason());
		if(StringUtils.isNotBlank(leaveDTO.getFromHalfDay())) {
			leave.setFromHalfDay(leaveDTO.getFromHalfDay());
		}
		if(StringUtils.isNotBlank(leaveDTO.getToHalfDay())) {
			leave.setToHalfDay(leaveDTO.getToHalfDay());
		}
		if(leaveDTO.getFileInfo() !=null) {
			FileInfo fileinfo = leaveDTO.getFileInfo().get(0);
			leave.setFileUrl(fileinfo.getFileURL());
			FileUploadUtil.moveFile(leaveDTO.getStaticPath(), fileinfo, "leaveDocuments");
		}
		leave.setStatus("1");
		leave.setCreatedBy(user.getUserCode());
		leave.setUpdatedBy(user.getUserCode());
		leave.setCreatedDate(new Date());
		leave.setUpdatedDate(new Date());
		return leaveDao.saveLeave(leave);
	}

	private Set<LeaveApproveHierarchy> getApproverList(LeaveDTO leaveDTO, Leave leave, User user) {
		Set<LeaveApproveHierarchy> approverList = new HashSet<>();
		leaveDTO.getApproverUser().forEach(userId->{
			try {
				LeaveApproveHierarchy leaveApproveHierarchy = new LeaveApproveHierarchy();
				User approveruser = userService.getUserById(Long.parseLong(userId));
				leaveApproveHierarchy.setApproverUserId(approveruser);
				leaveApproveHierarchy.setLeaveid(leave);
				leaveApproveHierarchy.setStatus("1");
				leaveApproveHierarchy.setCreatedBy(user.getUserCode());
				leaveApproveHierarchy.setUpdatedBy(user.getUserCode());
				leaveApproveHierarchy.setCreatedDate(new Date());
				leaveApproveHierarchy.setUpdatedDate(new Date());
				approverList.add(leaveApproveHierarchy);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return approverList;
	}

}
