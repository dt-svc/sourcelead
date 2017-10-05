package com.bis.sourcelead.service.impl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dao.LeaveManagementHistoryDao;
import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.LeaveReportsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.LeaveManagementHistoryService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.itextpdf.text.log.SysoCounter;

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
    UserRoleService userRoleService;

	@Autowired
    LeaveManagementHistoryService leaveManagementHistoryService;
     
    @Autowired
    LeaveManagementHistoryDao leaveManagementHistoryDao;
    
    @Autowired
    LeaveTypeDao leaveTypeDao;
    
    @Autowired
	OrgLocationService orgLocationService;
    
    
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
		int numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(leave.getFromDate(), leave.getToDate());
		leave.setNumberOfDays(String.valueOf(numberOfdays));
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
	public List<LeaveDTO> getEmployeeRequestedLeavesBylocationId(OrgLocations orgLocations, String userId, String leaveStatus) throws Exception {

		List<LeaveApproveHierarchy> leaveApproveHierarchiesList = leaveDao.getEmployeeRequestedLeavesBylocationId(orgLocations.getId(),userId,leaveStatus);

		List<LeaveDTO> leaveDtoList = new ArrayList<>();
		leaveApproveHierarchiesList.forEach(laph -> {
			Leave leave = laph.getLeaveid();
			LeaveDTO leaveDto = new LeaveDTO();
			StringBuilder sb = new StringBuilder();
			StringBuilder durationSb = new StringBuilder();
			leaveDto.setId(laph.getLeaveid().getId());
			if (leave.getUser() != null) {
				try {
					int nuberOfLeavestaken = leaveDao.getNumberOfLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
					int thisMonthLeaves = leaveDao.getThisMonthLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
					leaveDto.setLeavesTaken(String.valueOf(nuberOfLeavestaken));
					leaveDto.setThisMonthLeaves(String.valueOf(thisMonthLeaves));
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (StringUtils.isNotBlank(leave.getUser().getLastName())) {
					sb.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
				} else {
					sb.append(leave.getUser().getFirstName());
				}

				leaveDto.setEmpName(sb.toString());
			}
			durationSb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate()))
					.append(System.getProperty("line.separator")).append(" to ")
					.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate()));

			leaveDto.setDuration(durationSb.toString());
			leaveDto.setLeaveType(leave.getLeaveType().getName());
			leaveDto.setLeaveTypeId(leave.getLeaveType().getId());
			leaveDto.setStatus(leave.getStatus());
			leaveDto.setNoOfDays(leave.getNumberOfDays());
			leaveDto.setBalanceleaves(leave.getLeaveType().getNumbefrOfLeavesPeryear());

			if(leave.getApprovedBy() !=null) {
				StringBuilder sbApprover = new StringBuilder();
				if (Constants.APPROVED.equalsIgnoreCase(leaveStatus)) {
					if (StringUtils.isNotBlank(leave.getApprovedBy().getLastName())) {
						leaveDto.setApprovedBy(sbApprover.append(leave.getApprovedBy().getFirstName()).append(" ")
								.append(leave.getApprovedBy().getLastName()).toString());
					} else {
						leaveDto.setApprovedBy(leave.getApprovedBy().getFirstName());
					}
				} 
			}
			if(leave.getRejectedBy() != null) {
				StringBuilder sbRej = new StringBuilder();
				if (Constants.REJECTED.equalsIgnoreCase(leaveStatus)) {
					if (StringUtils.isNotBlank(leave.getRejectedBy().getLastName())) {
						leaveDto.setRejectedBy(sbRej.append(leave.getRejectedBy().getFirstName()).append(" ")
								.append(leave.getRejectedBy().getLastName()).toString());
					} else {
						leaveDto.setRejectedBy(leave.getRejectedBy().getFirstName());
					}
				}
			}
			
			if(leave.getCancelledBy() != null) {
			  if (Constants.LEAVE_CANCELLED.equalsIgnoreCase(leaveStatus)){
				  StringBuilder sbCancelApp = new StringBuilder();
				if (StringUtils.isNotBlank(leave.getApprovedBy().getLastName())) {
					leaveDto.setApprovedBy(sbCancelApp.append(leave.getApprovedBy().getFirstName()).append(" ")
							.append(leave.getApprovedBy().getLastName()).toString());
				} else {
					leaveDto.setApprovedBy(leave.getApprovedBy().getFirstName());
				}
				if (StringUtils.isNotBlank(leave.getCancelledBy().getLastName())) {
					StringBuilder sbCancel = new StringBuilder();
					leaveDto.setCancelledBy(sbCancel.append(leave.getCancelledBy().getFirstName()).append(" ")
							.append(leave.getCancelledBy().getLastName()).toString());
				} else {
					leaveDto.setCancelledBy(leave.getCancelledBy().getFirstName());
				}
			}
			}
			leaveDto.setUpdatedDate(ConversionUtil.getDateAsString(leave.getUpdatedDate()));
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
				leave.setApprovedBy(user);
					LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeId);
					int numberOfLeavesPerYear = Integer.parseInt(leaveType.getNumbefrOfLeavesPeryear())
							- Integer.parseInt(numberOfDays);
					leave.setBalanceLeavesCount(numberOfLeavesPerYear);
			} else {
				leave.setStatus(Constants.LEAVE_REJECTED);
				leave.setRejectedReason(reason);
				leave.setRejectedBy(user);
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

	@Override
	public List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status,Long userId) throws Exception
	{
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<Leave> leaveList = leaveDao.getLeavesListByLocationIdAndStatus(orgLocationId,status,userId);
		for(Leave leave : leaveList){
			leave.setLeaveTypeName(leave.getLeaveType().getName());
			List<String> aphlist = new ArrayList<>();
			for(LeaveApproveHierarchy laph : leave.getApproverHierarchyList()){
				StringBuilder sb = new StringBuilder();
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(laph.getApproverUserId().getId(), orgLocationId);
				if(role != null){
					if(StringUtils.isNotBlank(laph.getApproverUserId().getLastName())){
						aphlist.add(sb.append(role.getUserType()).append(" ( ").append(laph.getApproverUserId().getFirstName()).append(" ").append(laph.getApproverUserId().getLastName()).append(" )").toString());					
					}else{
						aphlist.add(sb.append(role.getUserType()).append(" ( ").append(laph.getApproverUserId().getFirstName()).append(" )").toString());
					}
				}
			}
			leave.setApproverUsers(String.join(",", aphlist));
			leave.setFromDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate()));
			leave.setToDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate()));
			leave.setCreatedDateStr(ConversionUtil.getDateAsString(leave.getCreatedDate()));
			if(leave.getApprovedBy() != null){
				StringBuilder sb = new StringBuilder();
				if(StringUtils.isNotBlank(leave.getApprovedBy().getFirstName()) && StringUtils.isNotBlank(leave.getApprovedBy().getLastName())){
					leave.setApprovedByUser(sb.append(leave.getApprovedBy().getFirstName()).append(" ").append(leave.getApprovedBy().getLastName()).toString());
				}else{
					leave.setApprovedByUser(sb.append(leave.getApprovedBy().getFirstName()).toString());
				}
			}
			if(StringUtils.isNotBlank(leave.getUpdatedBy())){
				User user = userService.getUserByUserCode(leave.getUpdatedBy());
				StringBuilder sb = new StringBuilder();
				if(StringUtils.isNotBlank(user.getFirstName()) && StringUtils.isNotBlank(user.getLastName())){
					leave.setUpdatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					leave.setUpdatedBy(sb.append(user.getFirstName()).toString());
				}
			}
		}
		return leaveList;
	}


	@Override
	public List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeId,Long orgLocationId, Long userId) {
		return leaveDao.getLeavesListByTypeIdAndLocationIdAndUserId(leaveTypeId,orgLocationId,userId);
	}
	
	@Override
	public List<LeaveReportsDTO> getEmployeeLeavesByApproverIdAndStatus(OrgLocations orgLocations, String userId, String status, String department) throws Exception
	{
		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());
		Date currentWeekStarDate = CalendarUtil.getPreviousWeekDayByCalendar(cal, "SUN", orgLocations.getDateFormat()).getTime();
		Date currentWeekEndDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SAT", orgLocations.getDateFormat()).getTime();
		List<LeaveApproveHierarchy> leaveApproveHierarchiesList = leaveDao.getEmployeeRequestedLeavesBylocationIdAndDuration(orgLocations.getId(), userId, status, currentWeekStarDate, currentWeekEndDate,department);
		HashMap<Long, LeaveReportsDTO> reportMap = new HashMap<>();
		for (LeaveApproveHierarchy lh : leaveApproveHierarchiesList) {
			Leave leave = lh.getLeaveid();
			LeaveReportsDTO leaveReportsDTO = reportMap.get(lh.getLeaveid().getUser().getId());
			if (leaveReportsDTO == null) {
				leaveReportsDTO = new LeaveReportsDTO();
			}
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(lh.getLeaveid().getUser().getLastName())) {
				sb.append(lh.getLeaveid().getUser().getFirstName()).append(" ").append(lh.getLeaveid().getUser().getLastName());
			}
			else {
				sb.append(lh.getLeaveid().getUser().getFirstName());
			}
			leaveReportsDTO.setEmpName(sb.toString());
			if(lh.getLeaveid().getUser().getFileUrl() != null){
				leaveReportsDTO.setEmpProfileImageURL(lh.getLeaveid().getUser().getFileUrl());
			}
			UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(lh.getLeaveid().getUser().getId(), orgLocations.getId());
			leaveReportsDTO.setEmpType(role.getUserType());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(leave.getFromDate());
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(leave.getToDate());
			while (calendar.before(endCalendar)) {
				Date date = calendar.getTime();
				switch (Constants.WEEK_DAY.format(date)) {
					case "Monday":
						leaveReportsDTO.setIsMonday(Constants.YES);
						break;
					case "Tuesday":
						leaveReportsDTO.setIsTuesday(Constants.YES);
						break;
					case "Wednesday":
						leaveReportsDTO.setIsWednesday(Constants.YES);
						break;
					case "Thursday":
						leaveReportsDTO.setIsThursday(Constants.YES);
						break;
					case "Friday":
						leaveReportsDTO.setIsFriday(Constants.YES);
						break;
					case "Saturday":
						leaveReportsDTO.setIsSaturday(Constants.YES);
						break;
					case "Sunday":
						leaveReportsDTO.setIsSunday(Constants.YES);
						break;
				}
				calendar.add(Calendar.DATE, 1);
				if (date.equals(currentWeekEndDate)) {
					break;
				}
			}
			if (!leave.getToDate().after(currentWeekEndDate)) {
				switch (Constants.WEEK_DAY.format(leave.getToDate())) {
					case "Monday":
						leaveReportsDTO.setIsMonday(Constants.YES);
						break;
					case "Tuesday":
						leaveReportsDTO.setIsTuesday(Constants.YES);
						break;
					case "Wednesday":
						leaveReportsDTO.setIsWednesday(Constants.YES);
						break;
					case "Thursday":
						leaveReportsDTO.setIsThursday(Constants.YES);
						break;
					case "Friday":
						leaveReportsDTO.setIsFriday(Constants.YES);
						break;
					case "Saturday":
						leaveReportsDTO.setIsSaturday(Constants.YES);
						break;
					case "Sunday":
						leaveReportsDTO.setIsSunday(Constants.YES);
						break;
				}
			}
			if (leave.getFromHalfDay() != null) {
				switch (Constants.WEEK_DAY.format(leave.getFromDate())) {
					case "Monday":
						leaveReportsDTO.setIsMondayHalfDay(Constants.YES);
						break;
					case "Tuesday":
						leaveReportsDTO.setIsTuesdayHalfDay(Constants.YES);
						break;
					case "Wednesday":
						leaveReportsDTO.setIsWednesdayHalfDay(Constants.YES);
						break;
					case "Thursday":
						leaveReportsDTO.setIsThursdayHalfDay(Constants.YES);
						break;
					case "Friday":
						leaveReportsDTO.setIsFridayHalfDay(Constants.YES);
						break;
					case "Saturday":
						leaveReportsDTO.setIsSaturdayHalfDay(Constants.YES);
						break;
					case "Sunday":
						leaveReportsDTO.setIsSundayHalfDay(Constants.YES);
						break;
				}
			}
			if (!leave.getToDate().after(currentWeekEndDate) && leave.getToHalfDay() != null) {
				switch (Constants.WEEK_DAY.format(leave.getToDate())) {
					case "Monday":
						leaveReportsDTO.setIsMondayHalfDay(Constants.YES);
						break;
					case "Tuesday":
						leaveReportsDTO.setIsTuesdayHalfDay(Constants.YES);
						break;
					case "Wednesday":
						leaveReportsDTO.setIsWednesdayHalfDay(Constants.YES);
						break;
					case "Thursday":
						leaveReportsDTO.setIsThursdayHalfDay(Constants.YES);
						break;
					case "Friday":
						leaveReportsDTO.setIsFridayHalfDay(Constants.YES);
						break;
					case "Saturday":
						leaveReportsDTO.setIsSaturdayHalfDay(Constants.YES);
						break;
					case "Sunday":
						leaveReportsDTO.setIsSundayHalfDay(Constants.YES);
						break;
				}
			}
			reportMap.put(lh.getLeaveid().getUser().getId(), leaveReportsDTO);
		}
		List<LeaveReportsDTO> reportList = new ArrayList<>();
		for (LeaveReportsDTO lrDto : reportMap.values()) {
			reportList.add(lrDto);
		}
		return reportList;
	}

	@Override
	public ResponseList<Leave> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception {
		return leaveDao.getTopLeavetakers(orgLocation,leaveDTO);
	}

	

}
