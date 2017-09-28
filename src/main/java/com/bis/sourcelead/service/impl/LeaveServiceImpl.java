package com.bis.sourcelead.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OMGVMCID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dao.LeaveManagementHistoryDao;
import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LeaveManagementHistoryService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
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
    
    @Autowired
    LeaveManagementHistoryService leaveManagementHistoryService;
    
    
    @Autowired
    LeaveManagementHistoryDao leaveManagementHistoryDao;
    
    @Autowired
    LeaveTypeDao leaveTypeDao;
    
    
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
			leave.setFileName(fileinfo.getFileName());
			leave.setFileContentType(fileinfo.getContentType());
			leave.setFileUrl(fileinfo.getFileURL());
			FileUploadUtil.moveFile(leaveDTO.getStaticPath(), fileinfo, "leaveDocuments");
		}
		leave.setStatus(Constants.LEAVE_PENDING);
		leave.setCreatedBy(user.getUserCode());
		leave.setUpdatedBy(user.getUserCode());
		leave.setCreatedDate(new Date());
		leave.setUpdatedDate(new Date());
		Leave savedLeave =  leaveDao.saveLeave(leave);
		if(savedLeave !=null) {
			LeaveManagementHistory leaveManagementHistory = leaveManagementHistoryService.saveLeaveManagementHistory(leave,user);
		}
		return savedLeave;
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

	@Override
	public List<LeaveDTO> getEmployeeRequestedLeavesBylocationId(OrgLocations orgLocations, String userId) throws Exception {
		List<LeaveApproveHierarchy> leaveApproveHierarchiesList = leaveDao.getEmployeeRequestedLeavesBylocationId(orgLocations.getId(),userId);
		List<LeaveDTO> leaveDtoList = new ArrayList<>();
		leaveApproveHierarchiesList.forEach(laph -> {
			Leave laeav = laph.getLeaveid();
			LeaveDTO leaveDto = new LeaveDTO();
			StringBuilder sb = new StringBuilder();
			StringBuilder durationSb = new StringBuilder();
			leaveDto.setId(laph.getLeaveid().getId());
			if (laeav.getUser() != null) {
				if (StringUtils.isNotBlank(laeav.getUser().getLastName())) {
					sb.append(laeav.getUser().getFirstName()).append(" ").append(laeav.getUser().getLastName());
				} else {
					sb.append(laeav.getUser().getFirstName());
				}

				leaveDto.setEmpName(sb.toString());
			}
			durationSb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), laeav.getFromDate()))
					.append(System.getProperty("line.separator"))
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), laeav.getToDate()));

			leaveDto.setDuration(durationSb.toString());
			leaveDto.setLeaveType(laeav.getLeaveType().getName());
			leaveDto.setLeaveTypeId(laeav.getLeaveType().getId());
			leaveDto.setStatus(laeav.getStatus());
			try {
				Long numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(laeav.getFromDate(), laeav.getToDate());
				leaveDto.setNoOfDays(String.valueOf(numberOfdays));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			leaveDto.setBalanceleaves(laeav.getLeaveType().getNumbefrOfLeavesPeryear());
			leaveDtoList.add(leaveDto);
		});
		return leaveDtoList;
	}


	@Override
	public Leave getLeaveByLeavId(String leaveId) throws Exception {
		return leaveDao.getLeaveById(leaveId);
	}

	@Override
	public boolean approveRequestLeave(OrgLocations orgLocations, User user, String leaveId, String leaveTypeId,
			String approveStatus, String numberOfDays,String reason) throws Exception {

		boolean isApproved = false;
		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory();
		Leave leave = leaveDao.getLeaveById(leaveId);
		leaveManagementHistory.setFromStage(leave.getStatus());
		if (leave != null) {
			if (Constants.LEAVE_APPROVED.equals(approveStatus)) {
				leave.setStatus(Constants.LEAVE_APPROVED);
					LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeId);
					int numberOfLeavesPerYear = Integer.parseInt(leaveType.getNumbefrOfLeavesPeryear())
							- Integer.parseInt(numberOfDays);
					leaveType.setNumbefrOfLeavesPeryear(String.valueOf(numberOfLeavesPerYear));
					leaveTypeDao.addLeaveType(leaveType);
			} else {
				leave.setStatus(Constants.LEAVE_REJECTED);
				leave.setRejectedReason(reason);
			}
			leave.setUpdatedBy(user.getUserCode());
			leave.setUpdatedDate(new Date());
			Leave updaetedLeave = leaveDao.saveLeave(leave);
			if(updaetedLeave != null) {
				isApproved = true;
			}
		}
		leaveManagementHistory.setLeaveid(leave);
		leaveManagementHistory.setType(Constants.LEAVE);
		leaveManagementHistory.setStatus(leave.getStatus());
		leaveManagementHistory.setCreatedBy(user.getUserCode());
		leaveManagementHistory.setCreatedDate(new Date());
		leaveManagementHistory.setUpdatedBy(user.getUserCode());
		leaveManagementHistory.setUpdatedDate(new Date());
		leaveManagementHistoryDao.saveLeaveManagementHistory(leaveManagementHistory);
		return isApproved;
	}

}
