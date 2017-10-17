package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dao.LeaveManagementHistoryDao;
import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.LeaveReportsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LeaveManagementHistoryService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
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
    UserRoleService userRoleService;

	@Autowired
    LeaveManagementHistoryService leaveManagementHistoryService;
     
    @Autowired
    LeaveManagementHistoryDao leaveManagementHistoryDao;
    
    @Autowired
    LeaveTypeDao leaveTypeDao;
    
    @Autowired
	OrgLocationService orgLocationService;
    
    @Autowired
	private EmailScenarioService emailScenarioService;
    
    @Value("${sourceleadUrl}")
    private String sourceleadUrl;
    
    @Autowired
    EmailScenerioUtil emailScenerioUtil;
    
    
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
			sendLeaveEmailToApprovers(user, leaveDTO, orgLocations);
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
	public ResponseList<LeaveDTO> getEmployeeRequestedLeavesBylocationId(LeaveDTO leaveDTO,OrgLocations orgLocations) throws Exception {

		ResponseList<LeaveApproveHierarchy> leaveApproveHierarchiesList = leaveDao.getEmployeeRequestedLeavesBylocationId(leaveDTO,orgLocations);

		ResponseList<LeaveDTO> leaveDtoList = new ResponseList<>();
		List<LeaveDTO> leavelistr = new ArrayList<>();
		if(leaveApproveHierarchiesList.getData() !=null && leaveApproveHierarchiesList.getData().size() >0) {
			leaveApproveHierarchiesList.getData().forEach(laph -> {
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
				leaveDto.setOrgLocationId(String.valueOf(leave.getOrgLocations().getId()));
				if(leave.getUser().getId() != null){ 
				 UserRole userrole;
				try {
					userrole = userRoleService.getUserRoleByUserId(leave.getUser().getId());
					leaveDto.setEmpType(userrole.getUserType());
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
				if(leave.getApprovedBy() !=null) {
					StringBuilder sbApprover = new StringBuilder();
					if (Constants.APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
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
					if (Constants.REJECTED.equalsIgnoreCase(leaveDTO.getLeaveStatus())) {
						if (StringUtils.isNotBlank(leave.getRejectedBy().getLastName())) {
							leaveDto.setRejectedBy(sbRej.append(leave.getRejectedBy().getFirstName()).append(" ")
									.append(leave.getRejectedBy().getLastName()).toString());
						} else {
							leaveDto.setRejectedBy(leave.getRejectedBy().getFirstName());
						}
					}
				}
				     leaveDto.setReason(leave.getReason());
				     leaveDto.setRejectedReason(leave.getRejectedReason());
				
				if(leave.getCancelledBy() != null) {
				  if (Constants.LEAVE_CANCELLED.equalsIgnoreCase(leaveDTO.getLeaveStatus())){
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
				leavelistr.add(leaveDto);
			});
		}
		
		leaveDtoList.setData(leavelistr);
		leaveDtoList.setRecordsFiltered(leaveApproveHierarchiesList.getRecordsFiltered());
		leaveDtoList.setRecordsTotal(leaveApproveHierarchiesList.getRecordsTotal());
		leaveDtoList.setError(leaveApproveHierarchiesList.getError());
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
					int numberOfLeavesPerYear = Integer.parseInt(leaveType.getNumbefrOfLeavesPeryear()) - Integer.parseInt(numberOfDays);
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
		leaveManagementHistory.setToStage(leave.getStatus());
		leaveManagementHistory.setStatus(leave.getStatus());
		leaveManagementHistory.setCreatedBy(user.getUserCode());
		leaveManagementHistory.setCreatedDate(new Date());
		leaveManagementHistory.setUpdatedBy(user.getUserCode());
		leaveManagementHistory.setUpdatedDate(new Date());
		leaveManagementHistoryDao.saveLeaveManagementHistory(leaveManagementHistory);
		sendApprovedAndRejectedLeaveMailToEmployee(orgLocations, user, leaveManagementHistory);
		return isApproved;
	}

	

	private void sendApprovedAndRejectedLeaveMailToEmployee(OrgLocations orgLocations, User loginUser, LeaveManagementHistory leaveManagementHistory) throws Exception
	{
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(leaveManagementHistory.getLeaveid().getUser().getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (leaveManagementHistory.getLeaveid().getUser().getFirstName() != null && !"".equalsIgnoreCase(leaveManagementHistory.getLeaveid().getUser().getFirstName())) {
			globalVarsMap.put("employeeName", leaveManagementHistory.getLeaveid().getUser().getFirstName());
		}
		else {
			globalVarsMap.put("employeeName", "--");
		}
		if (loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder loginName = new StringBuilder();
			globalVarsMap.put("approverName", loginName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("approverName", loginUser.getFirstName());
		}
		if (leaveManagementHistory.getLeaveid().getFromDate() != null){
			String fromDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getFromDate());
			globalVarsMap.put("fromDate", fromDate);
		}else{
			globalVarsMap.put("fromDate", "--");
		}
		 if (leaveManagementHistory.getLeaveid().getToDate()!= null){
			 String toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getToDate());
			globalVarsMap.put("toDate",toDate);
		}
		else {
			globalVarsMap.put("toDate", "--");
		}
		
		if (orgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())) {
			globalVarsMap.put("loginOrg", orgLocations.getOrgLocationName());
		}
		else {
			globalVarsMap.put("loginOrg", "--");
		}
		if (leaveManagementHistory.getLeaveid().getLeaveType().getName() != null && !"".equalsIgnoreCase(leaveManagementHistory.getLeaveid().getLeaveType().getName())) {
			globalVarsMap.put("leaveTypeName", leaveManagementHistory.getLeaveid().getLeaveType().getName());
		}
		else {
			globalVarsMap.put("leaveTypeName", "--");
		}
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		EmailScenario emailScenario = null;
		
		EmailScenario notificationScenario = null;
		
		if (leaveManagementHistory.getLeaveid().getReason() != null && !"".equalsIgnoreCase(leaveManagementHistory.getLeaveid().getReason())) {
			globalVarsMap.put("reason", leaveManagementHistory.getLeaveid().getReason());
			  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_REJECTED_EMAIL, Constants.ENTITY_TYPE_EMAIL);
			   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_REJECTED_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		}else{
			  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_APPROVER_EMAIL, Constants.ENTITY_TYPE_EMAIL);
			   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_APPROVER_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		
		  

//		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.APPROVER_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
		notificationScenario.setBody(notificationbody);
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, null, orgLocations.getOrg(), orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
		String actionLink = null;
		emailScenerioUtil.sendNotification(actionLink, loginUser, leaveManagementHistory.getLeaveid().getUser(), orgLocations, notificationScenario);
		
	}

	@Override
	public ResponseList<LeaveDTO> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status,Long userId) throws Exception
	{
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		List<Leave> leaveList = leaveDao.getLeavesListByLocationIdAndStatus(orgLocationId,status,userId);
		ResponseList<LeaveDTO> responseList = new ResponseList<>();
		List<LeaveDTO> leaveDtoList = new ArrayList<>();
		for(Leave leave : leaveList){
			LeaveDTO leaveDto = new LeaveDTO();
			leaveDto.setId(leave.getId());
			leaveDto.setLeaveType(leave.getLeaveType().getName());
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
			leaveDto.setApproverUsers(String.join(",", aphlist));
			leaveDto.setFromDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate()));
			leaveDto.setToDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate()));
			leaveDto.setCreatedDate(ConversionUtil.getDateAsString(leave.getCreatedDate()));
			if(leave.getApprovedBy() != null){
				StringBuilder sb = new StringBuilder();
				if(StringUtils.isNotBlank(leave.getApprovedBy().getFirstName()) && StringUtils.isNotBlank(leave.getApprovedBy().getLastName())){
					leaveDto.setApprovedByUser(sb.append(leave.getApprovedBy().getFirstName()).append(" ").append(leave.getApprovedBy().getLastName()).toString());
				}else{
					leaveDto.setApprovedByUser(sb.append(leave.getApprovedBy().getFirstName()).toString());
				}
			}
			if(StringUtils.isNotBlank(leave.getUpdatedBy())){
				User user = userService.getUserByUserCode(leave.getUpdatedBy());
				StringBuilder sb = new StringBuilder();
				if(StringUtils.isNotBlank(user.getFirstName()) && StringUtils.isNotBlank(user.getLastName())){
					leaveDto.setUpdatedBy(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					leaveDto.setUpdatedBy(sb.append(user.getFirstName()).toString());
				}
			}
			leaveDto.setReason(leave.getReason());
			leaveDto.setStatus(leave.getStatus());
			Date currentDate = commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date());
			if(currentDate.before(leave.getFromDate()) || currentDate.equals(leave.getFromDate())) {
				leaveDto.setIsWithdrawEnable("Y");
			}
			leaveDtoList.add(leaveDto);
		}
		responseList.setData(leaveDtoList);
		return responseList;
	}


	@Override
	public List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeId,Long orgLocationId, Long userId) {
		return leaveDao.getLeavesListByTypeIdAndLocationIdAndUserId(leaveTypeId,orgLocationId,userId);
	}
	
	@Override
	public List<LeaveReportsDTO> getEmployeeLeavesByApproverIdAndStatus(OrgLocations orgLocations, String userId, String status, String department, String leaveDate) throws Exception
	{

		
		Date currentWeekStarDate = null, currentWeekEndDate = null;

		Calendar cal = Calendar.getInstance(Constants.TIMEZONE_CENTRAL);
		cal.setLenient(false);
		cal.setTime(new Date());
		Date leaveDates = null;
		
		if(!StringUtils.isNotBlank(leaveDate)){
			currentWeekStarDate = CalendarUtil.getPreviousWeekDayByCalendar(cal, "SUN", orgLocations.getDateFormat()).getTime();
			currentWeekEndDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SAT", orgLocations.getDateFormat()).getTime();
		}else{
			leaveDates = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),leaveDate);
		}
		List<LeaveApproveHierarchy> leaveApproveHierarchiesList = leaveDao.getEmployeeRequestedLeavesBylocationIdAndDuration(orgLocations.getId(), userId, status, currentWeekStarDate, currentWeekEndDate,department,leaveDates);
			currentWeekStarDate = CalendarUtil.getPreviousWeekDayByCalendar(cal, "SUN", orgLocations.getDateFormat()).getTime();
			currentWeekEndDate = CalendarUtil.getCurrentWeekDayByCalendar(cal, "SAT", orgLocations.getDateFormat()).getTime();
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

	@Override
	public LeaveDTO getAllEmployeesLeavesCounts(Long orgLocationId,Long userId) {
		
		int requestedCounts = 0;
		int approvedCounts = 0;
		int rejectedCounts = 0;
		int cancelledCounts = 0;
		LeaveDTO leaveDto = new LeaveDTO();
		List<LeaveApproveHierarchy> leavesList = leaveDao.getAllEmployeesLeavesCounts(orgLocationId,userId);
		for (LeaveApproveHierarchy lah : leavesList) {
			if(Constants.PENDING.equalsIgnoreCase(lah.getLeaveid().getStatus())) {
				requestedCounts = requestedCounts + 1;
			}
			if(Constants.APPROVED.equalsIgnoreCase(lah.getLeaveid().getStatus())) {
				approvedCounts = approvedCounts + 1;
			}
			if(Constants.REJECTED.equalsIgnoreCase(lah.getLeaveid().getStatus())) {
				rejectedCounts = rejectedCounts + 1;
			}
			if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(lah.getLeaveid().getStatus())) {
				cancelledCounts = cancelledCounts + 1;
			}
		}
		leaveDto.setEmployeeRequestedCounts(String.valueOf(requestedCounts));
		leaveDto.setEmployeeApprovedCounts(String.valueOf(approvedCounts));
		leaveDto.setEmployeeRejectedCounts(String.valueOf(rejectedCounts));
		leaveDto.setEmployeeCancelledCounts(String.valueOf(cancelledCounts));
		return leaveDto;
	}

	@Override
	public LeaveDTO getAllSelfLeavesCounts(Long orgLocationId, Long userId) {
		int selfPendingCounts = 0;
		int selfApprovedCounts = 0;
		int selfRejectedCounts = 0;
		int selfCancelledCounts = 0;
		LeaveDTO leaveDto = new LeaveDTO();
		List<Leave> leavesList = leaveDao.getAllSelfLeavesCounts(orgLocationId,userId);
		for (Leave leave : leavesList) {
			if(Constants.PENDING.equalsIgnoreCase(leave.getStatus())) {
				selfPendingCounts = selfPendingCounts + 1;
			}
			if(Constants.APPROVED.equalsIgnoreCase(leave.getStatus())) {
				selfApprovedCounts = selfApprovedCounts + 1;
			}
			if(Constants.REJECTED.equalsIgnoreCase(leave.getStatus())) {
				selfRejectedCounts = selfRejectedCounts + 1;
			}
			if(Constants.LEAVE_CANCELLED.equalsIgnoreCase(leave.getStatus())) {
				selfCancelledCounts = selfCancelledCounts + 1;
			}
		}
		leaveDto.setSelfPendingCounts(String.valueOf(selfPendingCounts));
		leaveDto.setSelfApprovedCounts(String.valueOf(selfApprovedCounts));
		leaveDto.setSelfRejectedCounts(String.valueOf(selfRejectedCounts));
		leaveDto.setSelfCancelledCounts(String.valueOf(selfCancelledCounts));
		return leaveDto;
	}
	public List<LeaveDTO> getLeaveTakersBarChat(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception {
		List<Leave> leaveList = leaveDao.getLeaveTakersBarChat(orgLocation,leaveDTO);
		HashMap<String, LeaveDTO> reportMap = new HashMap<>();
		List<LeaveDTO> leaveDtoList = new ArrayList<>();
		int totalLeaves = 0;
		for (Leave leave : leaveList) {
			LeaveDTO leaveDto = reportMap.get(leave.getLeaveType().getId());
			if (leaveDto == null) {
				leaveDto = new LeaveDTO();
				totalLeaves = 0;
			}
			leaveDto.setLeaveType(leave.getLeaveType().getName());
			totalLeaves = totalLeaves + Integer.parseInt(leave.getNumberOfDays());
			leaveDto.setTotalLeaves(String.valueOf(leave.getNumberOfDays()));
			leaveDto.setColorCode(leave.getLeaveType().getColorCode());
			reportMap.put(leave.getLeaveType().getId(), leaveDto);
		}

		for (Map.Entry<String, LeaveDTO> entry : reportMap.entrySet()) {
			leaveDtoList.add(entry.getValue());
		}
		
		return leaveDtoList;
	}
	
	@Override
	public void sendLeaveEmailToApprovers(User loginUser, LeaveDTO leaveDTO, OrgLocations orgLoc) throws Exception {
		
		 EmailMessage emailMessage = new EmailMessage();
		
		 if(null != leaveDTO.getApproverUser()){
		for(String approverUserId :leaveDTO.getApproverUser()){
			User approverUser = userService.getUserById(Long.parseLong(approverUserId));
			 StringBuilder sb = new StringBuilder();
			 emailMessage.setFirstName(approverUser.getFileName());
			 emailMessage.setToEmail(approverUser.getEmail());
			 emailMessage.setFromEmail(loginUser.getEmail());
			 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
				 emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 emailMessage.setFromName(loginUser.getFirstName());
			 }
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

			 emailMessage.setCreatedBy(loginUser.getUserCode());
			 emailMessage.setOrgLocationId(orgLoc.getId());
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 if(null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())){
				 StringBuilder employee =new StringBuilder();
				 globalVarsMap.put("employeeUser", employee.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			 }else{
				 globalVarsMap.put("employeeUser",loginUser.getFirstName());
			 }
			 
			if(null != approverUser.getFileName() && !"".equalsIgnoreCase(approverUser.getFileName())){
				 StringBuilder manager =new StringBuilder();
				 globalVarsMap.put("approverUser", manager.append(approverUser).append(" ").toString());
			 }else{
				 globalVarsMap.put("approverUser",approverUser.getFirstName());
			 }
			 
			 if(null != leaveDTO.getReason()){
			 globalVarsMap.put("description",leaveDTO.getReason());
			 }
			 
			 if(null != leaveDTO.getNoOfDays()){
			 globalVarsMap.put("noOfDays",leaveDTO.getNoOfDays());
			 }
			 
			 if(null != leaveDTO.getLeaveType()){
				 LeaveType leaveType =  leaveTypeService.getLeaveTypeById(leaveDTO.getLeaveType());
				 globalVarsMap.put("leaveType",leaveType.getName());
				 }
			 
			 if(null != leaveDTO.getFromDate()){
				 globalVarsMap.put("fromDate",leaveDTO.getFromDate());
			 }

			 if(null != leaveDTO.getToDate()){
				 globalVarsMap.put("toDate",leaveDTO.getToDate());
			 }
			 
				StringBuilder stringBuildforLink = new StringBuilder();
				globalVarsMap.put("loginLink",stringBuildforLink.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>").toString());

			 
			 //setting the Recipient information
			 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
			 
			 //getting the current EmailScenario by EmailScenario unique name and entity type
			 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_EMAIL_TO_APPROVERS,Constants.ENTITY_TYPE_EMAIL);
			 EmailScenario  notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_NOTIFICATION_TO_APPROVERS,Constants.ENTITY_TYPE_NOTIFICATION);
			 //covert subject,body and signature
			 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
			 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			 String url = sourceleadUrl;
			 
			 //construct HTML template using required data
			 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,orgLoc.getOrg(),orgLoc);
			 emailMessage.setSubject(subject);
			 emailMessage.setMessageBody(emailTemplate);
			 
			 //calling the sendEmail method of emailScenerioUtil
			 emailScenerioUtil.sendEmail(emailMessage,orgLoc.getId(),iAdressArray,new ArrayList<EmailAttachment>());
			//calling the sendNotification method of emailScenerioUtil
				String notificationBody = emailScenerioUtil.buildBody(notificationScenario.getBody(),globalVarsMap);
				notificationScenario.setBody(notificationBody);
				emailScenerioUtil.sendNotification(null,loginUser, approverUser, orgLoc, notificationScenario);
		}
}
}


	@Override
	public boolean getWithdrawLeave(OrgLocations orgLocations, String leaveId, User user, String withdrawReason) throws Exception {
		boolean isLeaveWithdraw = false;
		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory();
		Leave leave = leaveDao.getLeaveById(leaveId);
		leaveManagementHistory.setFromStage(leave.getStatus());
		if (leave != null) {
			if (Constants.LEAVE_PENDING.equals(leave.getStatus())) {
				leave.setStatus(Constants.LEAVE_WITHDRAW);
			}else {
				leave.setStatus(Constants.LEAVE_WITHDRAW);
				leave.setNumberOfDays("0");
			}
			leave.setWithdrawReason(withdrawReason);
			leave.setUpdatedBy(user.getUserCode());
			leave.setUpdatedDate(new Date());
			leave.setApproverUsers(leave.getApproverUsers());
			Leave updatedLeave = leaveDao.saveLeave(leave);
			if(updatedLeave !=null) {
				isLeaveWithdraw = true;
			}
			leaveManagementHistory.setLeaveid(leave);
			leaveManagementHistory.setType(Constants.LEAVE);
			leaveManagementHistory.setToStage(leave.getStatus());
			leaveManagementHistory.setStatus(leave.getStatus());
			leaveManagementHistory.setCreatedBy(user.getUserCode());
			leaveManagementHistory.setCreatedDate(new Date());
			leaveManagementHistory.setUpdatedBy(user.getUserCode());
			leaveManagementHistory.setUpdatedDate(new Date());
			leaveManagementHistoryDao.saveLeaveManagementHistory(leaveManagementHistory);
			sendWithdrawLeaveMailToAdmin(orgLocations,leaveId, user, leaveManagementHistory);
		}
		
		

		return isLeaveWithdraw;
	}
	
	private void sendWithdrawLeaveMailToAdmin(OrgLocations orgLocations, String leaveId, User loginUser, LeaveManagementHistory leaveManagementHistory) throws Exception
	{
		Set<LeaveApproveHierarchy> leaveManagementHistoryList = leaveManagementHistory.getLeaveid().getApproverHierarchyList();
		if(leaveManagementHistoryList != null){
			for(LeaveApproveHierarchy lmh:leaveManagementHistoryList){
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setFirstName(loginUser.getFirstName());
				emailMessage.setLastName(loginUser.getLastName());
				emailMessage.setToEmail(lmh.getApproverUserId().getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				emailMessage.setCreatedBy(loginUser.getUserCode());
				StringBuilder msgBuild = new StringBuilder();
				emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				// setting the class filed values
				Map<String, String> globalVarsMap = new HashMap<String, String>();
				if (leaveManagementHistory.getLeaveid().getUser().getFirstName() != null && !"".equalsIgnoreCase(leaveManagementHistory.getLeaveid().getUser().getFirstName())) {
					globalVarsMap.put("employeeName", leaveManagementHistory.getLeaveid().getUser().getFirstName());
				}
				else {
					globalVarsMap.put("employeeName", "--");
				}
				if (loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())) {
					StringBuilder loginName = new StringBuilder();
					globalVarsMap.put("approverName", loginName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				}
				else {
					globalVarsMap.put("approverName", loginUser.getFirstName());
				}
				if (leaveManagementHistory.getLeaveid().getFromDate() != null){
					String fromDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getFromDate());
					globalVarsMap.put("fromDate", fromDate);
				}else{
					globalVarsMap.put("fromDate", "--");
				}
				 if (leaveManagementHistory.getLeaveid().getToDate()!= null){
					 String toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getToDate());
					globalVarsMap.put("toDate",toDate);
				}
				else {
					globalVarsMap.put("toDate", "--");
				}
				
				if (orgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())) {
					globalVarsMap.put("loginOrg", orgLocations.getOrgLocationName());
				}
				else {
					globalVarsMap.put("loginOrg", "--");
				}
		 
				InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
				EmailScenario emailScenario = null;
				
				EmailScenario notificationScenario = null;
				
				if (leaveManagementHistory.getLeaveid().getWithdrawReason() != null && !"".equalsIgnoreCase(leaveManagementHistory.getLeaveid().getWithdrawReason())) {
					globalVarsMap.put("withdrawReason", leaveManagementHistory.getLeaveid().getWithdrawReason());
					  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_WITHDRAW_EMAIL, Constants.ENTITY_TYPE_EMAIL);
					   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_WITHDRAW_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
				}else{
					globalVarsMap.put("withdrawReason", "--");
//					  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_APPROVER_EMAIL, Constants.ENTITY_TYPE_EMAIL);
//					   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_WITHDRAW_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
				}
				
				  

//				EmailScenario notificationScenarioo = emailScenarioService.getEmailScenarioByUniqueName(Constants.WITHDRAW_EMAIL_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
				// covert subject,body and signature
				String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
				String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
				String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
				String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
				notificationScenario.setBody(notificationbody);
				// construct HTML template using required data
				String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, null, orgLocations.getOrg(), orgLocations);
				emailMessage.setSubject(subject);
				emailMessage.setMessageBody(emailTemplate);
				// calling the sendEmail method of emailScenerioUtil
				emailScenerioUtil.sendEmail(emailMessage, orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
				String actionLink = null;
				emailScenerioUtil.sendNotification(actionLink, loginUser, leaveManagementHistory.getLeaveid().getUser(), orgLocations, notificationScenario);
				
				
			}
		}

	}	

	public void LeaveCancelEmailToUser(User loginUser,LeaveManagementHistory leaveManagementHistory,String reason) throws Exception{

		User toUser = leaveManagementHistory.getLeaveid().getUser();
		OrgLocations orgLocations = leaveManagementHistory.getLeaveid().getOrgLocations();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
			if(toUser.getLastName() != null && toUser.getLastName() !=""){
			StringBuilder fNameBulid= new StringBuilder();
			globalVarsMap.put("employeeUser", fNameBulid.append(toUser.getFirstName()).append(" ").append(toUser.getLastName()).toString());
			}else{
			globalVarsMap.put("employeeUser", toUser.getFirstName());
			}
			if(loginUser.getLastName() != null && loginUser.getLastName() !=""){
			StringBuilder fName= new StringBuilder();
			globalVarsMap.put("approverUser", fName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
			}else{
			globalVarsMap.put("approverUser", loginUser.getFirstName());
			}
			globalVarsMap.put("noOfDays", leaveManagementHistory.getLeaveid().getNumberOfDays());
			globalVarsMap.put("fromDate", ConversionUtil.getDateAsString(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),ConversionUtil.getDateAsString(leaveManagementHistory.getLeaveid().getFromDate()))));
			globalVarsMap.put("leaveType", leaveManagementHistory.getLeaveid().getLeaveType().getLeaveType());
			globalVarsMap.put("toDate", ConversionUtil.getDateAsString(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),ConversionUtil.getDateAsString(leaveManagementHistory.getLeaveid().getToDate()))));
			globalVarsMap.put("reason", reason);
			
			
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_CANCEL_EMAIL_TO_USER, Constants.ENTITY_TYPE_EMAIL);
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_CANCEL_NOTOFICATION_TO_USER, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;
		String notificationbody = emailScenerioUtil.buildTempBody(notificationScenario.getBody(), globalVarsMap);
		notificationScenario.setBody(notificationbody);
		// OrgLocations vendorOrgLoc =
		// requirement.getSpocId().getVendorLocationId();
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, orgLocations.getOrg(), orgLocations);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, orgLocations.getId(), iAdressArray, new ArrayList<EmailAttachment>());
		String actionLink = "";
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, orgLocations, notificationScenario);

	}

	@Override
	public boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable)
	{
		return leaveDao.validateNameByOrgLocationIdandFromTable(name,orgLocationId,fromTable);
	}

}
