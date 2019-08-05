package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dao.LeaveDao;
import com.bis.sourcelead.dao.LeaveManagementHistoryDao;
import com.bis.sourcelead.dao.LeaveTypeDao;
import com.bis.sourcelead.dto.DayWiseLeaveReportDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.HrLeaveReportDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.LeaveReportsDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.LeaveTypeImportDTO;
import com.bis.sourcelead.dto.MobilePushNotificationDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AppliedLeaveDate;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Holiday;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.MonthlyLeaves;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;
import com.bis.sourcelead.service.AppliedLeaveDateService;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.EmailScenarioConfigurationService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.LeaveManagementHistoryService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.MobilePushNotificationService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.LeaveManagementUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


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
    

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Value("${pmsUrl}")
	private String pmsUrl;
	
	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;
    
    @Autowired
    EmailScenerioUtil emailScenerioUtil;
    
    @Autowired
	LeaveManagementUtil leaveManagementUtil;
    
    @Autowired
    EmployeesService employeesService;
    
    @Autowired
	EmailScenarioConfigurationService emailScenarioconService;
    
    @Autowired
    AssociateBalanceLeaveService associateBalanceLeaveService;
    
    @Autowired
    AssociateBalanceLeaveDao associateBalanceLeaveDao;
    
    @Autowired
    AppliedLeaveDateService  appliedLeaveDateService;  
    
    @Autowired
    ConversionUtil conversionUtil;
    
    @Autowired
    PdfUtil pdfUtil;
    
    @Autowired
	MobilePushNotificationService  mobilePushNotificationService;
    
    private static final Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);
    
	@Override
	public Leave saveLeave(LeaveDTO leaveDTO,OrgLocations orgLocations, User user) throws Exception {
		
		Leave leave = null;
		if(leaveDTO.getLeaveId() != null && leaveDTO.getLeaveId() > 0) {
			int isDeleted = appliedLeaveDateService.deleteAppliedLeaveDatesByLeaveId(leaveDTO.getLeaveId());
			leave = leaveDao.getLeaveById(leaveDTO.getLeaveId());
			leave.setUpdatedBy(user.getUserCode());
			leave.setUpdatedDate(new Date());
		}else{
			leave = new Leave();
			leave.setCreatedBy(user.getUserCode());
			leave.setCreatedDate(new Date());
		}
		
//		Leave leave = new Leave();
		leave.setOrgLocations(orgLocations);
		leave.setFromDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), leaveDTO.getFromDate()));
		if(StringUtils.isNotBlank(leaveDTO.getUser())) {
			User usr = userService.getUserById(Long.parseLong(leaveDTO.getUser()));
			leave.setUser(usr);
		}else {
			leave.setUser(user);
		}
		
		LeaveType leaveType =  leaveTypeService.getLeaveTypeByUUID(leaveDTO.getLeaveType());
		if(leaveType.getDuration().equals("Half Day")) {
			leaveDTO.setFromHalfDay(Constants.STATUS_YES);
			leaveDTO.setToDate(leaveDTO.getFromDate());
		}
		leave.setToDate(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), leaveDTO.getToDate()));
		
		if(leaveType.getCreatedBy() != null){
			User createdUser = userService.getUserByUserCode(leaveType.getCreatedBy());
			StringBuilder sb = new StringBuilder();
			if(StringUtils.isNotBlank(createdUser.getFirstName()) && StringUtils.isNotBlank(createdUser.getLastName()))
				leaveType.setCreatedUser(sb.append(createdUser.getFirstName()).append(" ").append(createdUser.getLastName()).toString());
			else
				leaveType.setCreatedUser(createdUser.getFirstName());
		}
		
		if(leaveType.getUpdatedBy() != null){
			User updatedUser = userService.getUserByUserCode(leaveType.getUpdatedBy());
			StringBuilder sb = new StringBuilder();
			if(StringUtils.isNotBlank(updatedUser.getFirstName()) && StringUtils.isNotBlank(updatedUser.getLastName()))
				leaveType.setUpdatedUser(sb.append(updatedUser.getFirstName()).append(" ").append(updatedUser.getLastName()).toString());
			else
				leaveType.setUpdatedUser(updatedUser.getFirstName());
		}

		
		leave.setLeaveType(leaveType);
		leave.setReason(leaveDTO.getReason());
		
		if(StringUtils.isNotBlank(leaveDTO.getFromHalfDay())) {
			leave.setFromHalfDay(leaveDTO.getFromHalfDay());
		}else {
			leave.setFromHalfDay(null);
		}
		if(StringUtils.isNotBlank(leaveDTO.getToHalfDay())) {
			leave.setToHalfDay(leaveDTO.getToHalfDay());
		}else {
			leave.setToHalfDay(null);
		}
		if(StringUtils.isNotBlank(leaveDTO.getFromDateFromHalfTime())) {
			leave.setFromDateFromHalfTime(leaveDTO.getFromDateFromHalfTime());
		}else {
			leave.setFromDateFromHalfTime(null);
		}
		if(StringUtils.isNotBlank(leaveDTO.getFromDateToHalfTime())) {
			leave.setFromDateToHalfTime(leaveDTO.getFromDateToHalfTime());
		}else {
			leave.setFromDateToHalfTime(null);
		}
		if(StringUtils.isNotBlank(leaveDTO.getToDateFromHalfTime())) {
			leave.setToDateFromHalfTime(leaveDTO.getToDateFromHalfTime());
		}else {
			leave.setToDateFromHalfTime(null);
		}
		if(StringUtils.isNotBlank(leaveDTO.getToDateToHalfTime())) {
			leave.setToDateToHalfTime(leaveDTO.getToDateToHalfTime());
		}else {
			leave.setToDateToHalfTime(null);
		}
		if(StringUtils.isNotBlank(leaveDTO.getNotifyToOthers())) {
			leave.setNotifyToOthers(leaveDTO.getNotifyToOthers());
		}else {
			leave.setNotifyToOthers(null);
		}
		if(leaveDTO.getFileInfo() !=null) {
			FileInfo fileinfo = leaveDTO.getFileInfo().get(0);
			leave.setFileName(fileinfo.getFileName());
			leave.setFileContentType(fileinfo.getContentType());
			leave.setFileUrl(fileinfo.getFileURL());
			try {
				FileUploadUtil.moveFile(leaveDTO.getStaticPath(), fileinfo, "leaveDocuments");
			} catch (Exception e) {
			}
		}
		
		UserRole urole = userRoleService.getUserRoleByUserIdAndLocationId(leave.getUser().getId(), orgLocations.getId());
		if(Constants.YES.equalsIgnoreCase(leaveType.getAutoApprove())){
			if("Grade".equalsIgnoreCase(leaveType.getAutoApproveType())){
				if(urole.getGradeId() != null){
					if(leaveType.getAutoApproveTypeListNames().contains(urole.getGradeId().getGradeName())){
						leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
						leave.setStatus(Constants.LEAVE_APPROVED);
					}else{
						leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
						leave.setStatus(Constants.LEAVE_PENDING);
					}
				}else{
					leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
					leave.setStatus(Constants.LEAVE_PENDING);
				}
			}else if("Role".equalsIgnoreCase(leaveType.getAutoApproveType())){
				if(leaveType.getAutoApproveTypeListNames().contains(urole.getRoles().getRoleName())){
					leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
					leave.setStatus(Constants.LEAVE_APPROVED);
				}else{
					if(!leaveDTO.getIsSuperUser()){
						leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
						leave.setStatus(Constants.LEAVE_PENDING);
					}else {
						leave.setStatus(Constants.LEAVE_APPROVED);
					}
				}
			}else {
				leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
				leave.setStatus(Constants.LEAVE_APPROVED);
			}
		}else if(leaveDTO.getIsSuperUser()){
			leave.setStatus(Constants.LEAVE_APPROVED);
		}else{
			leave.setApproverHierarchyList(getApproverList(leaveDTO,leave,user));
			leave.setStatus(Constants.LEAVE_PENDING);
		}
		
		leave.setCreatedBy(user.getUserCode());
		leave.setUpdatedBy(user.getUserCode());
		leave.setCreatedDate(new Date());
		leave.setUpdatedDate(new Date());
		int numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(leave.getFromDate(), leave.getToDate());
		leave.setNumberOfDays(numberOfdays);
		leave.setLeaveUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
//		leave.setExtraLeavesTaken(String.valueOf(0));
		Set<AppliedLeaveDate> appliedLeaveDateSet = getAppliedLeaveDateSet(leave,orgLocations,user,leaveDTO);
		leave.setAppliedLeaveDateSet(appliedLeaveDateSet);
		Leave savedLeave =  leaveDao.saveLeave(leave);
		
		Map<String,Float> monthlyLeaves = leaveManagementUtil.getMonthlyAppliedLeaves(leave,leaveDTO,user);
		saveMonthlyLeaves(monthlyLeaves,leave,user);
		
		if(savedLeave !=null) {
			LeaveManagementHistory leaveManagementHistory = leaveManagementHistoryService.saveLeaveManagementHistory(leave,user,leaveDTO);
			if(leaveDTO.getApproverUser() != null){
				for(String approverUserId :leaveDTO.getApproverUser()){
					leaveDTO.setLeaveUUID(savedLeave.getLeaveUUID());
					User approverUser = userService.getUserById(Long.parseLong(approverUserId));
					if(user.getId().longValue() != approverUser.getId().longValue())
					sendLeaveEmailToApprovers(user, leaveDTO, orgLocations,approverUser,savedLeave);
				}
			}
			
			 if(null != leaveDTO.getUser() && !"".equalsIgnoreCase(leaveDTO.getUser())){
				 User applyUser = userService.getUserById(Long.parseLong(leaveDTO.getUser()));
				 if(user.getId().longValue() != applyUser.getId().longValue()) {
						// for URL redirections from mail
					 String redirectURL = "/pendingLeaves-params-notifyLink=notifyLink-and-leaveUUID="
								+ savedLeave.getLeaveUUID() + "-and-orgLocationId=" + orgLocations.getId() + "-and-status=PENDING";						
						Long redirectOrgLocationId = orgLocations.getId(); 	 
					 sendOnBehalfEmailToEmployee(savedLeave,applyUser,user,orgLocations,redirectURL,redirectOrgLocationId);
			}
		 }
			 
		}
		
		
		return savedLeave;
	}
	
	/**
	 * Get exact applied leave dates
	 * 
	 * @param leave
	 * @param orgLocations
	 * @param user
	 * @param leaveDTO 
	 * @return Set<AppliedLeaveDate>
	 */
	private Set<AppliedLeaveDate> getAppliedLeaveDateSet(Leave leave, OrgLocations orgLocations, User user, LeaveDTO leaveDTO)
	{
		Set<AppliedLeaveDate> set = new HashSet<>();
		try {
			 Calendar cal = Calendar.getInstance();
			List<Date> dateList = leaveManagementUtil.getDaysBetweenDates(leave.getFromDate(), leave.getToDate(), orgLocations.getDateFormat());
			dateList.forEach(d -> {
				
				 String[] strArray = orgLocations.getWeekOffDays().split(",");
			     List<String> weekOffs = new ArrayList<String>(Arrays.asList(strArray));
			 	 SimpleDateFormat sf = new SimpleDateFormat("E");
				AppliedLeaveDate appliedLeaveDate = new AppliedLeaveDate();
				appliedLeaveDate.setApplyLeaveDate(d);
				 float fromMinutes = 0;
				 LeaveType  leaveType = leave.getLeaveType();
					Boolean includeWeekOffs = false;
					if(leaveType != null && (leaveType.getIncludeWeekoff().equalsIgnoreCase("Y"))) {
						includeWeekOffs = true;
					}
				 if(includeWeekOffs || !weekOffs.contains(sf.format(d))) {
					 if(leave.getFromDate().equals(d)){
							if(Constants.STATUS_YES.equalsIgnoreCase(leave.getFromHalfDay())){
								fromMinutes = leaveManagementUtil.calculateAppliedLeaveMinutes(leave.getFromDateFromHalfTime(), leave.getFromDateToHalfTime(), orgLocations.getAverageWorkingHours(), leave.getLeaveType());
//								fromHours = (float) leaveManagementUtil.calculateActualHoursForHalfDay(leave.getFromDateFromHalfTime(), leave.getFromDateToHalfTime(), orgLocations.getAverageWorkingHours(), leave.getLeaveType());
							}else{
								fromMinutes = orgLocations.getAverageWorkingHours() * 60;
							}
						}else if(leave.getToDate().equals(d)){
							if(Constants.STATUS_YES.equalsIgnoreCase(leave.getToHalfDay())){
								fromMinutes = leaveManagementUtil.calculateAppliedLeaveMinutes(leave.getToDateFromHalfTime(), leave.getToDateToHalfTime(), orgLocations.getAverageWorkingHours(), leave.getLeaveType());
//								fromHours = (float) leaveManagementUtil.calculateActualHoursForHalfDay(leave.getToDateFromHalfTime(), leave.getToDateToHalfTime(), orgLocations.getAverageWorkingHours(), leave.getLeaveType());
							}else{
								fromMinutes = orgLocations.getAverageWorkingHours() * 60;
							}
						}else{
							fromMinutes = orgLocations.getAverageWorkingHours() * 60;
						}
				 }
				appliedLeaveDate.setAppliedHours(CommonUtil.getTimeFormat(null, fromMinutes));
				appliedLeaveDate.setApplyMonthName(getMonthName(d).toUpperCase());
				appliedLeaveDate.setLeave(leave);
				appliedLeaveDate.setIsPayrollInclude(Constants.STATUS_NO);
				appliedLeaveDate.setCreatedBy(user.getId());
				appliedLeaveDate.setCreatedDate(new Date());
				appliedLeaveDate.setUpdatedBy(user.getId());
				appliedLeaveDate.setUpdatedDate(new Date());
				appliedLeaveDate.setYear(cal.get(Calendar.YEAR));
				set.add(appliedLeaveDate);
			});
		} catch (Exception e) {
			logger.error("AppliedLeaveDate failed due to " + e.getMessage());
		}
		return set;
	}
	
	/**
	 * Return month name by Date
	 * @return String
	 */
	private String getMonthName(Date date) {
		  Format formatter = new SimpleDateFormat("MMM"); 
		    String monthName = formatter.format(date);
		    return monthName;
	}
	private void sendOnBehalfEmailToEmployee(Leave savedLeave, User applyUser, User loginUser,
			OrgLocations orgLocations, String redirectURL, Long redirectOrgLocationId) throws Exception {

		EmailMessage emailMessage = new EmailMessage();

		emailMessage.setFirstName(applyUser.getFirstName());
		emailMessage.setToEmail(applyUser.getEmail());
		emailMessage.setFromEmail(defaultFromEmailAddressForProd);

		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setOrgLocationId(orgLocations.getId());

		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder employee = new StringBuilder();
			globalVarsMap.put("loginUser",
					employee.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		} else {
			globalVarsMap.put("loginUser", loginUser.getFirstName());
		}

		if (null != applyUser.getLastName() && !"".equalsIgnoreCase(applyUser.getLastName())) {
			StringBuilder leaveUser = new StringBuilder();
			globalVarsMap.put("leaveTakenEmployee",
					leaveUser.append(applyUser.getFirstName()).append(" ").append(applyUser.getLastName()).toString());
		} else {
			globalVarsMap.put("leaveTakenEmployee", applyUser.getFirstName());
		}

		if (null != savedLeave.getLeaveType()) {
			LeaveType leaveType = leaveTypeService.getLeaveTypeById(savedLeave.getLeaveType().getId());
			globalVarsMap.put("leaveType", leaveType.getName());
		}

		if (null != savedLeave.getFromDate() && null != savedLeave.getToDate()) {
			StringBuilder date = new StringBuilder();
			globalVarsMap.put("leaveDuration",
					date.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
							savedLeave.getFromDate())).append("-")
							.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),
									savedLeave.getToDate()))
							.toString());
		}

		if (null != savedLeave.getReason()) {
			globalVarsMap.put("leaveReason", savedLeave.getReason());
		}

		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("loginLink",
				stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +pmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId)
						.append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Leave</div></a>")
						.toString());
		
		EmailScenario  emailScenario = null;
		 if(orgLocations != null)
		 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ON_BEHALF_EMAIL,Constants.ENTITY_TYPE_EMAIL, orgLocations.getId());
		  if(emailScenario == null){
				emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ON_BEHALF_EMAIL,Constants.ENTITY_TYPE_EMAIL);
		   }
		
		//configured Email
		String fromMail=commonUtil.getConfiguredFromEmail(orgLocations.getId(), emailScenario);
		 if(StringUtils.isNotBlank(fromMail)){
			 emailMessage.setFromEmail(fromMail);
		 } 
		 
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		EmailScenario notificationScenario = null;
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.ON_BEHALF_EMAIL,new ArrayList<EmailAttachment>());
		notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ON_BEHALF_NOTIFICATION,
				Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationBody);
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("")
				.append(pmsUrl).append("/").append("pendingLeaves?notifyLink=notifyLink&leaveUUID="
						+ savedLeave.getLeaveUUID() + "&orgLocationId=" + orgLocations.getId() + "&status=PENDING")
				.toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, applyUser, orgLocations, notificationScenario,new HashMap<String,String>());
		

	}

	private void saveMonthlyLeaves(Map<String, Float> monthlyLeaves, Leave leave, User user)
	{
		MonthlyLeaves leaves = new MonthlyLeaves();
		leaves.setLeaveId(leave);
		for(String key : monthlyLeaves.keySet()){
			switch (key){
				case "January" : leaves.setJanuaryLeaves(monthlyLeaves.get(key));
				break;
				case "February" : leaves.setFebruaryLeaves(monthlyLeaves.get(key));
				break;
				case "March" : leaves.setMarchLeaves(monthlyLeaves.get(key));
				break;
				case "April" : leaves.setAprilLeaves(monthlyLeaves.get(key));
				break;
				case "May" : leaves.setMayLeaves(monthlyLeaves.get(key));
				break;
				case "June" : leaves.setJuneLeaves(monthlyLeaves.get(key));
				break;
				case "July" : leaves.setJulyLeaves(monthlyLeaves.get(key));
				break;
				case "August" : leaves.setAugustLeaves(monthlyLeaves.get(key));
				break;
				case "September" : leaves.setSeptLeaves(monthlyLeaves.get(key));
				break;
				case "October" : leaves.setOctoberLeaves(monthlyLeaves.get(key));
				break;
				case "November" : leaves.setNovLeaves(monthlyLeaves.get(key));
				break;
				case "December" : leaves.setDecLeaves(monthlyLeaves.get(key));
				break;
			}
		}
		leaves.setCreatedBy(user.getUserCode());
		leaves.setCreatedDate(new Date());
		leaves.setUpdatedBy(user.getUserCode());
		leaves.setUpdatedDate(new Date());
		saveMonthlyLeaves(leaves);
	}

	private Set<LeaveApproveHierarchy> getApproverList(LeaveDTO leaveDTO, Leave leave, User user) {
		Set<LeaveApproveHierarchy> approverList = new HashSet<>();
		try {
			int isDeleted = leaveDao.deleteApproversByLeaveId(leave.getId());
		} catch (Exception excp) {
			//excp.printStackTrace();
		}
		
		if(leaveDTO.getOtherApproverUser() != null) {
			List<String> allUsers = new ArrayList<>();
			if(leaveDTO.getApproverUser() != null && !leaveDTO.getApproverUser().isEmpty()){
				allUsers.addAll(leaveDTO.getApproverUser());
			}
			allUsers.addAll(leaveDTO.getOtherApproverUser());
			leaveDTO.setApproverUser(allUsers);
		}
		if(leaveDTO.getApproverUser() != null && leaveDTO.getApproverUser().size() > 0) {
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
		}
		return approverList;
	}

	@Override
	public ResponseList<LeaveDTO> getEmployeeRequestedLeavesBylocationId(LeaveDTO leaveDTO,OrgLocations orgLocations) throws Exception {

		ResponseList<LeaveApproveHierarchy> leaveApproveHierarchiesList = leaveDao.getEmployeeRequestedLeavesBylocationId(leaveDTO,orgLocations);

		ResponseList<LeaveDTO> leaveDtoList = new ResponseList<>();
		List<LeaveDTO> leavelistr = new ArrayList<>();
		if(leaveApproveHierarchiesList.getData() !=null && leaveApproveHierarchiesList.getData().size() >0) {
			for(LeaveApproveHierarchy laph : leaveApproveHierarchiesList.getData()) {
				Leave leave = leaveDao.getLeaveById(laph.getLeaveid().getId());
				LeaveDTO leaveDto = new LeaveDTO();
				Employees emp = null;
				StringBuilder sb = new StringBuilder();
				StringBuilder durationSb = new StringBuilder();
				leaveDto.setId(laph.getLeaveid().getId());
				Map<String,Float> nuberOfLeavestaken = null;
				if (leave.getUser() != null) {
					try {
						nuberOfLeavestaken = leaveDao.getNumberOfLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
						float thisMonthLeaves = leaveDao.getThisMonthLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType(),orgLocations);
						leaveDto.setActualLeavesTaken((float)nuberOfLeavestaken.get("numberOfLeavsTaken"));
						leaveDto.setThisMonthLeavesTaken(thisMonthLeaves);
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
					emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(leave.getUser().getId(), orgLocations.getId());
					
				
				durationSb.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate()))
						.append(System.getProperty("line.separator")).append(" to ")
						.append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate()));

				leaveDto.setDuration(durationSb.toString());
				leaveDto.setLeaveType(leave.getLeaveType().getName());
				leaveDto.setLeaveTypeId(leave.getLeaveType().getId());
				leaveDto.setStatus(leave.getStatus());
				leaveDto.setLeaveUUID(leave.getLeaveUUID());
				float totalLeaves = 0;
				if(Constants.YES.equalsIgnoreCase(leave.getLeaveType().getVisibleInBalanceLeaves())) {
					 totalLeaves = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(leave.getLeaveType().getId(),leave.getUser().getId(),orgLocations.getId(),orgLocations.getOrg().getId());
				}
				leaveDto.setOrgLocationId(String.valueOf(leave.getOrgLocations().getId()));
				leaveDto.setFromHalfDay(leave.getFromHalfDay());
				
				float appliedLeavesCount = leaveManagementUtil.getAppliedLeaveCount(orgLocations,leave.getLeaveType(),leave,leaveDTO.getLeaveStatus());
				leaveDto.setAppliedNoOfDays(appliedLeavesCount);
				
				float totalLeaveCount = leaveManagementUtil.getTotalAppliedLeaveCount(orgLocations, leave.getLeaveType(),leave.getUser().getId());
				
				float balLeaves = 0;
				if(totalLeaveCount >0) {
					  balLeaves = totalLeaves - totalLeaveCount;
				}else {
					  balLeaves = totalLeaves;	
				}
				
				leaveDto.setTotalBalLeaves(Float.parseFloat(leaveManagementUtil.dec.format(balLeaves)));
				
//				leaveDto.setExtraLeavesTaken(leave.getExtraLeavesTaken());
			
				if(StringUtils.isNotBlank(leave.getDeleteReason())) {
					leaveDto.setCancelReason(leave.getDeleteReason());
				}
				if(leave.getUser().getId() != null){ 
				 UserRole userrole;
				try {
					userrole = userRoleService.getUserDetailsByUserIdAndLocationId(leave.getUser().getId(), leave.getOrgLocations().getId());
					leaveDto.setEmpType(userrole.getUserType());
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
				if(leave.getApprovedBy() !=null) {
					StringBuilder sbApprover = new StringBuilder();
					if (Constants.APPROVED.equalsIgnoreCase(leaveDTO.getLeaveStatus()) || Constants.PENDING.equalsIgnoreCase(leaveDTO.getLeaveStatus()) ) {
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
					 if(leave.getApprovedBy() != null){
						 if (StringUtils.isNotBlank(leave.getApprovedBy().getLastName())) {
								leaveDto.setApprovedBy(sbCancelApp.append(leave.getApprovedBy().getFirstName()).append(" ")
										.append(leave.getApprovedBy().getLastName()).toString());
							} else {
								leaveDto.setApprovedBy(leave.getApprovedBy().getFirstName());
							}
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
				leaveDto.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getUpdatedDate()));
				leavelistr.add(leaveDto);
			}
		}
		
		leaveDtoList.setData(leavelistr);
		leaveDtoList.setRecordsFiltered(leaveApproveHierarchiesList.getRecordsFiltered());
		leaveDtoList.setRecordsTotal(leaveApproveHierarchiesList.getRecordsTotal());
		leaveDtoList.setError(leaveApproveHierarchiesList.getError());
		return leaveDtoList;
	}


	

	@Override
	public Leave getLeaveByLeavId(Long leaveId) throws Exception {
		return leaveDao.getLeaveById(leaveId);
	}

	@Override
	public boolean approveRequestLeave(OrgLocations orgLocations, User user, String leaveUUID, Long leaveTypeId,
			String approveStatus, int numberOfDays,String reason) throws Exception {

		boolean isApproved = false;
		Employees emp = null;
		Leave updaetedLeave = null;
		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory();
		Leave leave = leaveDao.getLeaveByLeaveUUId(leaveUUID);
		
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
        Date yearStartDate = commonUtil.getDateFormat(Constants.DMY.format(cal.getTime()));
        Date fromDate = commonUtil.getDateFormat(Constants.DMY.format(leave.getFromDate()));
        
		List<Leave> leaveList = getLeavesListByTypeIdAndDuration(leaveTypeId, orgLocations.getId(),leave.getUser().getId(),yearStartDate,fromDate);
//		List<Leave> leaveList = leaveDao.getLeavesListByLocationIdAndStatus(orgLocations.getId(),Constants.APPROVED,user.getId());
		float carryForwardLeaves = calculateCarryForwardLeaves(leaveList,leave.getFromDate(),yearStartDate,leave.getLeaveType(),leave);
		
		leaveManagementHistory.setFromStage(leave.getStatus());
		if (leave != null) {
			if (Constants.LEAVE_APPROVED.equals(approveStatus)) {
				leave.setStatus(Constants.LEAVE_APPROVED);
				leave.setApprovedBy(user);
//					LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeId);
//					emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(leave.getUser().getId(),orgLocations.getId());
//					int totalLeaves = leaveManagementUtil.getBalanceCountForLeaveType(leave.getLeaveType(),emp,orgLocations,leave.getUser());
					
//					int noOfExtraDays = leaveManagementUtil.getNumberOfExtraDaysByPeriod(leave.getLeaveType(),leave,orgLocations,user);
					
//					float appliedLeaves = leaveManagementUtil.calculateAppliedLeavesCountWithHalfDays(leave);
//					if("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.YES.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
//						float extraLeaves = appliedLeaves - carryForwardLeaves;
//						if(extraLeaves > 0)
//							leave.setExtraLeavesTaken(extraLeaves);
//						else
//							leave.setExtraLeavesTaken(0);
//					}else if("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.NO.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
//						leave.setExtraLeavesTaken(leave.getLeaveType().getNoOfDays() - appliedLeaves);
//					}else{
//						leave.setExtraLeavesTaken(0);
//					}
//					
//					
//					float numberOfLeavesPerYear = totalLeaves - appliedLeaves + leave.getExtraLeavesTaken();
//					leave.setBalanceLeavesCount(numberOfLeavesPerYear);
			} else {
				leave.setStatus(Constants.LEAVE_REJECTED);
				leave.setRejectedReason(reason);
				leave.setRejectedBy(user);
//				int isDeleted = appliedLeaveDateService.deleteAppliedLeaveDatesByLeaveId(leave.getId());
			}
			leave.setUpdatedBy(user.getUserCode());
			leave.setUpdatedDate(new Date());
			updaetedLeave =  leaveDao.saveLeave(leave);
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
		
		// for URL redirections from mail
		Long redirectOrgLocationId = orgLocations.getId();
		String  redirectURL = null;
		if(updaetedLeave.getStatus().equals(Constants.LEAVE_APPROVED)) {
			 redirectURL = "/approvedLeaves-params-notifyLink=notifyLink-and-leaveUUID="+leaveManagementHistory.getLeaveid().getLeaveUUID()+"-and-orgLocationId="+orgLocations.getId();}
		else {
			redirectURL ="/rejectedLeaves-params-notifyLink=notifyLink-and-leaveUUID="+leaveManagementHistory.getLeaveid().getLeaveUUID()+"-and-orgLocationId="+orgLocations.getId();
		}
			
		sendApprovedAndRejectedLeaveMailToEmployee(orgLocations, user, leaveManagementHistory,redirectOrgLocationId,redirectURL);
		/*if(isApproved == true && updaetedLeave.getLeaveType().getTimeSheetAutofill().equalsIgnoreCase(Constants.YES)) {
			timesheetRestController.linkLeaveTypeWithTimeSheet(user, orgLocations, leaveTypeId, updaetedLeave,Constants.APPROVED);
		}*/
		return isApproved;
	}

	

	private float calculateCarryForwardLeaves(List<Leave> leaveList, Date fromDate, Date yearStartDate, LeaveType leaveType, Leave currentLeave) throws Exception
	{
		Calendar sDate = Calendar.getInstance();
        Calendar eDate = Calendar.getInstance();
        sDate.setTime(fromDate);
        eDate.setTime(yearStartDate); // 
        int difInMonths = eDate.get(Calendar.MONTH) - sDate.get(Calendar.MONTH) + 1;
        int fromMonth = sDate.get(Calendar.MONTH);
		
		float carryForwardLeaves = 0;
		float janLeaves = 0;
		float febLeaves = 0;
		float marLeaves = 0;
		float aprLeaves = 0;
		float mayLeaves = 0;
		float junLeaves = 0;
		float julLeaves = 0;
		float augLeaves = 0;
		float sepLeaves = 0;
		float octLeaves = 0;
		float novLeaves = 0;
		float decLeaves = 0;
		float monthlyAllowedLeaves = 0;
		if("Experienced".equalsIgnoreCase(leaveType.getLeaveEffective())){
			Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(currentLeave.getUser().getId(), currentLeave.getOrgLocations().getId());
			if(emp.getHireDate() != null){
                Date joinDate = emp.getHireDate();
                Date currentDate = new Date();
                int diffDays = commonUtil.getNumberOfDaysBetweenTwoDates(joinDate, currentDate);
                int expDays = 0;
                if("Year".equalsIgnoreCase(leaveType.getExperiencedType())){
                    expDays = leaveType.getExperiencedDuration() * 365 ;
                }else if("Month".equalsIgnoreCase(leaveType.getExperiencedType())){
                    expDays = leaveType.getExperiencedDuration() * 30 ;
                }else {
                    expDays = leaveType.getExperiencedDuration();
                }
                if(diffDays > expDays){
                	monthlyAllowedLeaves = leaveType.getExperiencedApplicableDays(); 
                }else{
                	monthlyAllowedLeaves = leaveType.getNewJoinee(); 
                }
            }else{
            	monthlyAllowedLeaves = leaveType.getExperiencedApplicableDays(); 
            }
		}else{
			if(leaveType.getNoOfDaysPerPeriod() != null){
				monthlyAllowedLeaves = leaveType.getNoOfDaysPerPeriod();
			}else{
				if("Monthly".equalsIgnoreCase(leaveType.getPeriod())){
					monthlyAllowedLeaves = leaveType.getNoOfDays()/12;
				}else{
					monthlyAllowedLeaves = 0f;
				}
				
			}
        }
		if(Constants.YES.equalsIgnoreCase(leaveType.getLeavesCarryforward())){
			if("Monthly".equalsIgnoreCase(leaveType.getPeriod())){
				for(Leave userLeave : leaveList){
//					if("Mothly".equalsIgnoreCase(userLeave.getLeaveType().getPeriod()) && Constants.YES.equalsIgnoreCase(userLeave.getLeaveType().getLeavesCarryforward())){
						MonthlyLeaves monthlyLeaves = getMonthlyLeavesByLeaveId(userLeave.getId());
//						float monthlyTotalLeaves = difInMonths * monthlyAllowedLeaves;
						if(monthlyLeaves != null) {
							janLeaves += monthlyLeaves.getJanuaryLeaves();
							febLeaves += monthlyLeaves.getFebruaryLeaves();
							marLeaves += monthlyLeaves.getMarchLeaves();
							aprLeaves += monthlyLeaves.getAprilLeaves();
							mayLeaves += monthlyLeaves.getMayLeaves();
							junLeaves += monthlyLeaves.getJuneLeaves();
							julLeaves += monthlyLeaves.getJulyLeaves();
							augLeaves += monthlyLeaves.getAugustLeaves();
							sepLeaves += monthlyLeaves.getSeptLeaves();
							octLeaves += monthlyLeaves.getOctoberLeaves();
							novLeaves += monthlyLeaves.getNovLeaves();
							decLeaves += monthlyLeaves.getDecLeaves();
						}
//					}
				}
				
				for(int i=0;i<= fromMonth; i++){
					switch (i) {
						case 0:
							if(janLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - janLeaves);
							}
							break;
						case 1:
							if(febLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - febLeaves);
							}
							break;
						case 2:
							if(marLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - marLeaves);
							}
							break;
						case 3:
							if(aprLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - aprLeaves);
							}
							break;
						case 4:
							if(mayLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - mayLeaves);
							}
							break;
						case 5:
							if(junLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - junLeaves);
							}
							break;
						case 6:
							if(julLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - julLeaves);
							}
							break;
						case 7:
							if(augLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - augLeaves);
							}
							break;
						case 8:
							if(sepLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - sepLeaves);
							}
							break;
						case 9:
							if(octLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - octLeaves);
							}
							break;
						case 10:
							if(novLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - novLeaves);
							}
							break;
						case 11:
							if(decLeaves < monthlyAllowedLeaves){
								carryForwardLeaves += (monthlyAllowedLeaves - decLeaves);
							}
							break;
					}
				}
			}else if("Annually".equalsIgnoreCase(leaveType.getPeriod())){
				carryForwardLeaves += leaveType.getNoOfDays();
			}
		}
		
		return carryForwardLeaves;
	}

	private void sendApprovedAndRejectedLeaveMailToEmployee(OrgLocations orgLocations, User loginUser, LeaveManagementHistory leaveManagementHistory, Long redirectOrgLocationId, String redirectURL) throws Exception
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
			globalVarsMap.put("EmployeeName", leaveManagementHistory.getLeaveid().getUser().getFirstName());
		}
		else {
			globalVarsMap.put("EmployeeName", "--");
		}
		if (loginUser.getLastName() != null && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder loginName = new StringBuilder();
			globalVarsMap.put("ApproverName", loginName.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("ApproverName", loginUser.getFirstName());
		}
		if (leaveManagementHistory.getLeaveid().getFromDate() != null){
			String fromDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getFromDate());
			globalVarsMap.put("FromDate", fromDate);
		}else{
			globalVarsMap.put("FromDate", "--");
		}
		 if (leaveManagementHistory.getLeaveid().getToDate()!= null){
			 String toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getToDate());
			globalVarsMap.put("ToDate",toDate);
		}
		else {
			globalVarsMap.put("ToDate", "--");
		}
		
		if (orgLocations.getOrgLocationName() != null && !"".equalsIgnoreCase(orgLocations.getOrgLocationName())) {
			globalVarsMap.put("loginOrg", orgLocations.getOrgLocationName());
		}
		else {
			globalVarsMap.put("loginOrg", "--");
		}
			globalVarsMap.put("LeaveTypeName", leaveManagementHistory.getLeaveid().getLeaveType().getName());
			
			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +pmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>View Leave</div></a>").toString());
		
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		EmailScenario notificationScenario = null;
		
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = "";
		
		if (leaveManagementHistory.getLeaveid().getRejectedReason() != null && StringUtils.isNotBlank(leaveManagementHistory.getLeaveid().getRejectedReason())) {
			globalVarsMap.put("Reason", leaveManagementHistory.getLeaveid().getRejectedReason());
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.LEAVE_REJECTED_EMAIL,new ArrayList<EmailAttachment>());
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_REJECTED_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
			 actionLink = actionLinkBuilder.append("").append(pmsUrl).append("/").append("rejectedLeaves?notifyLink=notifyLink&leaveUUID="+leaveManagementHistory.getLeaveid().getLeaveUUID()+"&orgLocationId="+orgLocations.getId()).toString();  
		}else{
			  commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.LEAVE_APPROVER_EMAIL,new ArrayList<EmailAttachment>());
			   notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_APPROVER_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
			   actionLink = actionLinkBuilder.append("").append(pmsUrl).append("/").append("approvedLeaves?notifyLink=notifyLink&leaveUUID="+leaveManagementHistory.getLeaveid().getLeaveUUID()+"&orgLocationId="+orgLocations.getId()).toString();
		}
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario );
		notificationScenario.setBody(notificationbody);
		emailScenerioUtil.sendNotification(actionLink, loginUser, leaveManagementHistory.getLeaveid().getUser(), orgLocations, notificationScenario,new HashMap<String,String>());
		
		//Mobile PushNotification
   	    MobilePushNotificationDTO mobilePushNotificationDTO = new MobilePushNotificationDTO();
   	    mobilePushNotificationDTO.setNotificationDescritpion(notificationbody);
   	    mobilePushNotificationDTO.setNotificationTitle(Constants.NOTIFICATION_LEAVE_TITLE);
   	    mobilePushNotificationDTO.setUserId(leaveManagementHistory.getLeaveid().getUser());
		mobilePushNotificationService.saveMobilePushNotification(mobilePushNotificationDTO);
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
			List<String> approverUserIds = new ArrayList<>();
			for(LeaveApproveHierarchy laph : leave.getApproverHierarchyList()){
				StringBuilder sb = new StringBuilder();
				UserRole role = userRoleService.getUserRoleByUserIdAndLocationId(laph.getApproverUserId().getId(), orgLocationId);
				String userType = "";
				if(role != null){
					userType = role.getUserType();
				}
				if(StringUtils.isNotBlank(laph.getApproverUserId().getLastName())){
					aphlist.add(sb.append(userType).append(" ( ").append(laph.getApproverUserId().getFirstName()).append(" ").append(laph.getApproverUserId().getLastName()).append(" )").toString());					
				}else{
					aphlist.add(sb.append(userType).append(" ( ").append(laph.getApproverUserId().getFirstName()).append(" )").toString());
				}
				approverUserIds.add(String.valueOf(laph.getApproverUserId().getId()));
			}
			leaveDto.setOtherApproverUser(approverUserIds);
			leaveDto.setApproverUsers(String.join(",", aphlist));
			leaveDto.setFromDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getFromDate()));
			leaveDto.setToDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getToDate()));
			
			leaveDto.setDuration(leave.getLeaveType().getDuration());
			if(StringUtils.isNotBlank(leave.getFromHalfDay())){
				leaveDto.setFromHalfDay(leave.getFromHalfDay());
				leaveDto.setFromDateFromHalfTime(leave.getFromDateFromHalfTime());
				leaveDto.setFromDateToHalfTime(leave.getFromDateToHalfTime());
			}
			if(StringUtils.isNotBlank(leave.getToHalfDay())){
				leaveDto.setToHalfDay(leave.getToHalfDay());
				leaveDto.setToDateFromHalfTime(leave.getToDateFromHalfTime());
				leaveDto.setToDateToHalfTime(leave.getToDateToHalfTime());
			}
			
			leaveDto.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getCreatedDate()));
			
			if(StringUtils.isNotBlank(leave.getRejectedReason())){
				leaveDto.setRejectedReason(leave.getRejectedReason());
			}
			
			if(StringUtils.isNotBlank(leave.getWithdrawReason())){
				leaveDto.setWithdrawReason(leave.getWithdrawReason());
			}
			
			if(leave.getUpdatedDate() != null)
			leaveDto.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leave.getUpdatedDate()));
			leaveDto.setLeaveUUID(leave.getLeaveUUID());
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
			if(StringUtils.isNotBlank(leave.getDeleteReason())) {
				leaveDto.setCancelReason(leave.getDeleteReason());
			}
			leaveDto.setReason(leave.getReason());
			leaveDto.setFileUrl(leave.getFileUrl());
			leaveDto.setFileContentType(leave.getFileContentType());
			leaveDto.setFileName(leave.getFileName());
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
	public List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeUUID,Long orgLocationId, Long userId) {
		return leaveDao.getLeavesListByTypeIdAndLocationIdAndUserId(leaveTypeUUID,orgLocationId,userId);
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
			cal.setTime(leaveDates);
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
			leaveReportsDTO.setDuration(leave.getLeaveType().getDuration());
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
	public LeaveDTO getAllEmployeesLeavesCounts(Long orgLocationId,Long userId,String loginUserRole,Long orgId) {
		
		int requestedCounts = 0;
		int approvedCounts = 0;
		int rejectedCounts = 0;
		int cancelledCounts = 0;
		LeaveDTO leaveDto = new LeaveDTO();
		List<LeaveApproveHierarchy> leavesList = leaveDao.getAllEmployeesLeavesCounts(orgLocationId,userId,loginUserRole,orgId);
		
		List<LeaveApproveHierarchy> pendingleavesList = leavesList.stream().filter(l -> Constants.PENDING.equalsIgnoreCase(l.getLeaveid().getStatus())).collect(Collectors.toList());
		requestedCounts = pendingleavesList.size();
		List<LeaveApproveHierarchy> appleavesList = leavesList.stream().filter(l -> Constants.APPROVED.equalsIgnoreCase(l.getLeaveid().getStatus())).collect(Collectors.toList());
		approvedCounts = appleavesList.size();
		List<LeaveApproveHierarchy> rejleavesList = leavesList.stream().filter(l -> Constants.REJECTED.equalsIgnoreCase(l.getLeaveid().getStatus())).collect(Collectors.toList());
		rejectedCounts = rejleavesList.size();
		List<LeaveApproveHierarchy> canceledleavesList = leavesList.stream().filter(l -> Constants.LEAVE_CANCELLED.equalsIgnoreCase(l.getLeaveid().getStatus())).collect(Collectors.toList());
		cancelledCounts = canceledleavesList.size();
		
	/*	
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
		}*/
		leaveDto.setEmployeeRequestedCounts(String.valueOf(requestedCounts));
		leaveDto.setEmployeeApprovedCounts(String.valueOf(approvedCounts));
		leaveDto.setEmployeeRejectedCounts(String.valueOf(rejectedCounts));
		leaveDto.setEmployeeCancelledCounts(String.valueOf(cancelledCounts));
		return leaveDto;
	}

	@Override
	public LeaveDTO getAllSelfLeavesCounts(Long orgLocationId, Long userId) throws Exception {
		int selfPendingCounts = 0;
		int selfApprovedCounts = 0;
		int selfRejectedCounts = 0;
		int selfCancelledCounts = 0;
		
		float balanceLeaveCounts = 0;
		
		User user = userService.getUserById(userId);
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(userId, orgLocationId);

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
		
		List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocationId));
		List<LeaveType> roleLeaveType = new ArrayList<>();  
		
		
		if(leaveTypeList !=null && !leaveTypeList.isEmpty()) {
			   for(LeaveType leaveType : leaveTypeList) {
				   Set<Long> roleIds = new HashSet<>();
				   for(LeaveRole leaveRole : leaveType.getLeaveTypeRolesSet()) {
					   roleIds.add(leaveRole.getRoleId().getId());
				   }
				   if(roleIds.contains(userRole.getRoles().getId()) || "Y".equalsIgnoreCase(leaveType.getApplicableToUsers())) {
					   roleLeaveType.add(leaveType);
				   }
			   }
			    
			   for (LeaveType type : roleLeaveType) {
	                if ("All".equalsIgnoreCase(type.getEffectiveGender()) || (user.getGender() != null
	                        && type.getEffectiveGender().equalsIgnoreCase(user.getGender()))) {
	                    float empToatalLeaves = 0;
	                    float appliedLeaveCount = 0;
	                    if (Constants.YES.equalsIgnoreCase(type.getVisibleInBalanceLeaves())) {
	                        empToatalLeaves = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(type.getId(), user.getId(), orgLocations.getId(),orgLocations.getOrg().getId());
	                        appliedLeaveCount = leaveManagementUtil.getTotalAppliedLeaveCount(orgLocations, type, userId);
	                        if(StringUtils.isNotBlank(type.getAllowability())) {
	                        	if(appliedLeaveCount < Integer.parseInt(type.getAllowability())) {
	                        		if (appliedLeaveCount > 0) {
			                            appliedLeaveCount = empToatalLeaves - appliedLeaveCount;
			                        }
			                        else {
			                            appliedLeaveCount = empToatalLeaves;
			                        }
	                        		balanceLeaveCounts += appliedLeaveCount;
	                        	}
	                        }else {
	                        	if (appliedLeaveCount > 0) {
		                            appliedLeaveCount = empToatalLeaves - appliedLeaveCount;
		                        }
		                        else {
		                            appliedLeaveCount = empToatalLeaves;
		                        }
	                        	 balanceLeaveCounts += appliedLeaveCount;
	                        }
	                    }
	                }
	            }
		   }
		
		
		leaveDto.setSelfPendingCounts(String.valueOf(selfPendingCounts));
		leaveDto.setSelfApprovedCounts(String.valueOf(selfApprovedCounts));
		leaveDto.setSelfRejectedCounts(String.valueOf(selfRejectedCounts));
		leaveDto.setSelfCancelledCounts(String.valueOf(selfCancelledCounts));
		leaveDto.setTotalBalLeaves(Float.parseFloat(leaveManagementUtil.dec.format(balanceLeaveCounts)));
		return leaveDto;
	}
	public List<LeaveDTO> getLeaveTakersBarChat(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception {
		List<Leave> leaveList = leaveDao.getLeaveTakersBarChat(orgLocation,leaveDTO);
		HashMap<Long, LeaveDTO> reportMap = new HashMap<>();
		List<LeaveDTO> leaveDtoList = new ArrayList<>();
		int totalLeaves = 0;
		for (Leave leave1 : leaveList) {
			Leave leave = leaveDao.getLeaveById(leave1.getId());
			LeaveDTO leaveDto = reportMap.get(leave.getLeaveType().getId());
			if (leaveDto == null) {
				leaveDto = new LeaveDTO();
				totalLeaves = 0;
			}else {
				totalLeaves = Integer.valueOf(leaveDto.getTotalLeaves());
			}
			leaveDto.setLeaveType(leave.getLeaveType().getName());
			totalLeaves = totalLeaves + leave.getNumberOfDays();
			leaveDto.setTotalLeaves(String.valueOf(totalLeaves));
			leaveDto.setColorCode(leave.getLeaveType().getColorCode());
			reportMap.put(leave.getLeaveType().getId(), leaveDto);
		}

		for (Map.Entry<Long, LeaveDTO> entry : reportMap.entrySet()) {
			leaveDtoList.add(entry.getValue());
		}
		
		return leaveDtoList;
	}
	
	@Override
	public void sendLeaveEmailToApprovers(User loginUser, LeaveDTO leaveDTO, OrgLocations orgLoc, User approverUser, Leave leave) throws Exception
	{
		EmailMessage emailMessage = new EmailMessage();
		StringBuilder sb = new StringBuilder();
		emailMessage.setFirstName(approverUser.getFirstName());
		emailMessage.setToEmail(approverUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			emailMessage.setFromName(loginUser.getFirstName());
		}
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		emailMessage.setCreatedBy(loginUser.getUserCode());
		emailMessage.setOrgLocationId(orgLoc.getId());
		
		//redirection URLs
		String redirectURL = "/employeeRequestedLeaves-params-notifyLink=notifyLink-and-leaveUUID=" + leaveDTO.getLeaveUUID()+ "-and-orgLocationId=" + orgLoc.getId();
		Long redirectOrgLocationId = orgLoc.getId();
		
		//setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		LeaveType leaveType = null;
		if (null != leaveDTO.getLeaveType()) {
			leaveType = leaveTypeService.getLeaveTypeByUUID(leaveDTO.getLeaveType());
		}
		
		if (null != loginUser.getLastName() && !"".equalsIgnoreCase(loginUser.getLastName())) {
			StringBuilder employee = new StringBuilder();
			globalVarsMap.put("employeeUser", employee.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("employeeUser", loginUser.getFirstName());
		}
		User applyUser = null;
		if (null != leaveDTO.getUser() && !"".equalsIgnoreCase(leaveDTO.getUser())) {
			applyUser = userService.getUserById(Long.parseLong(leaveDTO.getUser()));
			if (null != applyUser.getLastName() && !"".equalsIgnoreCase(applyUser.getLastName())) {
				StringBuilder leaveUser = new StringBuilder();
				globalVarsMap.put("leaveUser", leaveUser.append(applyUser.getFirstName()).append(" ").append(applyUser.getLastName()).toString());
			}
			else {
				globalVarsMap.put("leaveUser", applyUser.getFirstName());
			}
		}
		if (null != approverUser.getLastName()
				&& !"".equalsIgnoreCase(approverUser.getLastName())) {
			StringBuilder manager = new StringBuilder();
			globalVarsMap.put("approverUser", manager.append(approverUser.getFirstName()).append(" ").append(approverUser.getLastName()).toString());
		}
		else {
			globalVarsMap.put("approverUser", approverUser.getFirstName());
		}
		if (null != leaveDTO.getReason()) {
			globalVarsMap.put("reason", leaveDTO.getReason());
		}
		/*Date fromDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), leaveDTO.getFromDate());
		Date toDate = commonUtil.orgLocationDateFormat(orgLoc.getDateFormat(), leaveDTO.getToDate());
		int numberOfdays = commonUtil.getNumberOfDaysBetweenTwoDates(fromDate, toDate);*/
		
		float appliedLeavesCount = leaveManagementUtil.getAppliedLeaveCount(orgLoc,leaveType,leave,Constants.LEAVE_PENDING);
		
		globalVarsMap.put("noOfDays", String.valueOf(appliedLeavesCount));
		
		if(leaveType.getName() != null)
			globalVarsMap.put("leaveType", leaveType.getName());
		
		if (null != leaveDTO.getFromDate()) {
			globalVarsMap.put("fromDate", leaveDTO.getFromDate());
		}
		if (null != leaveDTO.getToDate()) {
			globalVarsMap.put("toDate", leaveDTO.getToDate());
		}
		StringBuilder stringBuildforLink = new StringBuilder();
		globalVarsMap.put("loginLink", stringBuildforLink.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL="+pmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Leave Request</div></a>").toString());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		EmailScenario notificationScenario = null;
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(sourceleadUrl).append("/").append("employeeRequestedLeaves?notifyLink=notifyLink&leaveUUID="
				+ leaveDTO.getLeaveUUID() + "&orgLocationId=" + orgLoc.getId()).toString();
		if (applyUser != null) {
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLoc.getOrg(), orgLoc, Constants.LEAVE_EMAIL_ON_BEHALF_EMAIL, new ArrayList<EmailAttachment>());
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_EMAIL_ON_BEHALF_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		else {
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLoc.getOrg(), orgLoc, Constants.LEAVE_EMAIL_TO_APPROVERS, new ArrayList<EmailAttachment>());
			notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_NOTIFICATION_TO_APPROVERS, Constants.ENTITY_TYPE_NOTIFICATION);
		}
		String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationBody);
		emailScenerioUtil.sendNotification(actionLink, loginUser, approverUser, orgLoc, notificationScenario, new HashMap<String, String>());
}


	@Override
	public boolean getWithdrawLeave(OrgLocations orgLocations, String leaveUUID, User user, String withdrawReason) throws Exception {
		boolean isLeaveWithdraw = false;
		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory();
		Leave leave = leaveDao.getLeaveByLeaveUUId(leaveUUID);
		leaveManagementHistory.setFromStage(leave.getStatus());
		if (leave != null) {
			if (Constants.LEAVE_PENDING.equals(leave.getStatus())) {
				leave.setStatus(Constants.LEAVE_WITHDRAW);
			}else {
				leave.setStatus(Constants.LEAVE_WITHDRAW);
//				leave.setNumberOfDays("0");
			}
			leave.setWithdrawReason(withdrawReason);
			leave.setUpdatedBy(user.getUserCode());
			leave.setUpdatedDate(new Date());
			leave.setApproverUsers(leave.getApproverUsers());
			Leave updatedLeave = leaveDao.saveLeave(leave);
			if(updatedLeave !=null) {
				isLeaveWithdraw = true;
//				int isDeleted = appliedLeaveDateService.deleteAppliedLeaveDatesByLeaveId(updatedLeave.getId());
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
			
			// for URL redirections from mail
			 String redirectURL = null;											
			Long redirectOrgLocationId = orgLocations.getId(); 	 
			sendWithdrawLeaveMailToAdmin(orgLocations,leave.getId(), user, leaveManagementHistory,redirectOrgLocationId,redirectURL);
			/*if(leave != null && leave.getLeaveType() != null && leave.getOrgLocations() != null) {
				timesheetRestController.linkLeaveTypeWithTimeSheet(user,leave.getOrgLocations(),leave.getLeaveType().getId(),leave,Constants.LEAVE_WITHDRAW);
			}*/
		}
		return isLeaveWithdraw;
	}
	
	private void sendWithdrawLeaveMailToAdmin(OrgLocations orgLocations, Long leaveId, User loginUser, LeaveManagementHistory leaveManagementHistory, Long redirectOrgLocationId, String redirectURL) throws Exception
	{
		Set<LeaveApproveHierarchy> leaveApproversList = leaveManagementHistory.getLeaveid().getApproverHierarchyList();
		if(leaveApproversList != null){
			for(LeaveApproveHierarchy lmh:leaveApproversList){
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setFirstName(loginUser.getFirstName());
				if(StringUtils.isNotBlank(loginUser.getLastName()))
					emailMessage.setLastName(loginUser.getLastName());
				emailMessage.setToEmail(lmh.getApproverUserId().getEmail());
				emailMessage.setFromEmail(loginUser.getEmail());
				emailMessage.setCreatedBy(loginUser.getUserCode());
				
				if(StringUtils.isNotBlank(loginUser.getLastName())){
					StringBuilder msgBuild = new StringBuilder();
					emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				}else{
					emailMessage.setFromName(loginUser.getFirstName());
				}
				
				emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
				// setting the class filed values
				Map<String, String> globalVarsMap = new HashMap<String, String>();
				globalVarsMap.put("EmployeeName", emailMessage.getFromName());
				
				if (StringUtils.isNotBlank(lmh.getApproverUserId().getLastName())) {
					StringBuilder loginName = new StringBuilder();
					globalVarsMap.put("ApproverName", loginName.append(lmh.getApproverUserId().getFirstName()).append(" ").append(lmh.getApproverUserId().getLastName()).toString());
				}
				else {
					globalVarsMap.put("ApproverName", lmh.getApproverUserId().getFirstName());
				}
				if (leaveManagementHistory.getLeaveid().getFromDate() != null){
					String fromDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getFromDate());
					globalVarsMap.put("FromDate", fromDate);
				}else{
					globalVarsMap.put("FromDate", "--");
				}
				 if (leaveManagementHistory.getLeaveid().getToDate()!= null){
					 String toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), leaveManagementHistory.getLeaveid().getToDate());
					globalVarsMap.put("ToDate",toDate);
				}
				else {
					globalVarsMap.put("ToDate", "--");
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
					globalVarsMap.put("Reason", leaveManagementHistory.getLeaveid().getWithdrawReason());
				}else{
					globalVarsMap.put("Reason", "--");
				}
				commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.LEAVE_WITHDRAW_EMAIL,new ArrayList<EmailAttachment>());
			    notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_WITHDRAW_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
				String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
				notificationScenario.setBody(notificationbody);
				emailScenerioUtil.sendNotification(null, loginUser, lmh.getApproverUserId(), orgLocations, notificationScenario,new HashMap<String,String>());

				
				
			}
		}

	}	

	public void LeaveCancelEmailToUser(User loginUser,LeaveManagementHistory leaveManagementHistory,String reason,Long redirectOrgLocationId, String redirectURL) throws Exception{

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
			globalVarsMap.put("noOfDays", String.valueOf(leaveManagementHistory.getLeaveid().getNumberOfDays()));
			globalVarsMap.put("fromDate", ConversionUtil.getDateAsString(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),ConversionUtil.getDateAsString(leaveManagementHistory.getLeaveid().getFromDate()))));
			globalVarsMap.put("leaveType", leaveManagementHistory.getLeaveid().getLeaveType().getName());
			globalVarsMap.put("toDate", ConversionUtil.getDateAsString(commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),ConversionUtil.getDateAsString(leaveManagementHistory.getLeaveid().getToDate()))));
			globalVarsMap.put("reason", reason);
			
			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("loginURL",loginBuilder.append("<a href='").append(sourceleadLoginUrl+ "?redirectURL=" +pmsUrl+redirectURL+"&redirectOrgLocationId="+redirectOrgLocationId).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>View Leave</div></a>").toString());					
			
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		
		commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.LEAVE_CANCEL_EMAIL_TO_USER,new ArrayList<EmailAttachment>());
		EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.LEAVE_CANCEL_NOTOFICATION_TO_USER, Constants.ENTITY_TYPE_NOTIFICATION);
		String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
		notificationScenario.setBody(notificationbody);
		
		StringBuilder actionLinkBuilder = new StringBuilder();
		String actionLink = actionLinkBuilder.append("").append(pmsUrl).append("/").append("cancelLeaves?notifyLink=notifyLink&leaveUUID="+leaveManagementHistory.getLeaveid().getLeaveUUID()+"&orgLocationId="+orgLocations.getId()).toString();
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, orgLocations, notificationScenario,new HashMap<String,String>());
		

	}

	@Override
	public boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable,String id)
	{
		return leaveDao.validateNameByOrgLocationIdandFromTable(name,orgLocationId,fromTable,id);
	}

	@Override
	public boolean deleteAllLeaveRolesByParticularTableId(String from,Long fromId)
	{
		return leaveDao.deleteAllLeaveRolesByParticularTableId(from,fromId);
	}

	@Override
	public void storeHolidayOrRestrictDays(OrgLocations orgLocations,User user, Object object) throws Exception
	{
		if(object instanceof Holiday){
			Holiday holiday = (Holiday) object;
			List<Date> dateList = leaveManagementUtil.getDaysBetweenDates(holiday.getFromDate(), holiday.getToDate(), orgLocations.getDateFormat());
			deleteAllHolidayOrRestrictDayById(holiday.getId(),"Holiday");
			for(Date d : dateList){
				HolidayAndRestrictday day = new HolidayAndRestrictday();
				day.setHoliday(holiday);
				day.setDay(d);
				day.setOrgLocations(orgLocations);
				day.setCreatedBy(user.getUserCode());
				day.setCreatedDate(new Date());
				day.setUpdatedBy(user.getUserCode());
				day.setUpdatedDate(new Date());
				day.setStatus(Constants.RECORD_ACTIVE.toString());
				leaveDao.storeHolidayAndRestrictDays(day);
			}
		}else{
			RestrictDay restrict = (RestrictDay) object;
			List<Date> dateList = leaveManagementUtil.getDaysBetweenDates(restrict.getStartDate(), restrict.getEndDate(), orgLocations.getDateFormat());
			deleteAllHolidayOrRestrictDayById(restrict.getId(),"RestrictDay");
			for(Date d : dateList){
				HolidayAndRestrictday day = new HolidayAndRestrictday();
				day.setRestrictDay(restrict);
				day.setDay(d);
				day.setOrgLocations(orgLocations);
				day.setCreatedBy(user.getUserCode());
				day.setCreatedDate(new Date());
				day.setUpdatedBy(user.getUserCode());
				day.setUpdatedDate(new Date());
				day.setStatus(Constants.RECORD_ACTIVE.toString());
				leaveDao.storeHolidayAndRestrictDays(day);
			}
		}
	}

	public void deleteAllHolidayOrRestrictDayById(Long id, String type)
	{
		leaveDao.deleteAllHolidayOrRestrictDayById(id, type);
		
	}

	@Override
	public List<HolidayAndRestrictday> getAllHolidayAndRestrictDaysInDuration(String fromDate, String toDate, OrgLocations orgLocations)
	{
		return leaveDao.getAllHolidayAndRestrictDaysInDuration(fromDate,toDate,orgLocations);
	}

	@Override
	public List<User> getApproversListBasedonHierarchyExceptLoginUser(Long loginUserId, Long roleId, Long locationId) throws Exception
	{
		return leaveDao.getApproversListBasedonHierarchyExceptLoginUser(loginUserId, roleId, locationId);
	}

	@Override
	public List<HolidayAndRestrictday> getHolidayOrRestrictDaysListById(Long id, String type)
	{
		return leaveDao.getHolidayOrRestrictDaysListById(id, type);
	}

	@Override
	public List<LeaveRole> getLeaveRolesByRestrictDayId(Long restrictDayId) throws Exception
	{
		return leaveDao.getLeaveRolesByRestrictDayId(restrictDayId);
	}

	@Override
	public List<Leave> getAllLeavesByLocationIdAndStatus(Long locationId, String status)
	{
		return leaveDao.getAllLeavesByLocationIdAndStatus(locationId, status);
	}

	@Override
	public List<Leave> getLeavesListByTypeIdAndDuration(Long leaveTypeId, Long orgLocationId, Long userId, Date startDate, Date endDate)
	{
		return leaveDao.getLeavesListByTypeIdAndDuration(leaveTypeId, orgLocationId, userId, startDate, endDate);
	}

	@Override
	public MonthlyLeaves saveMonthlyLeaves(MonthlyLeaves monthlyLeaves)
	{
		return leaveDao.saveMonthlyLeaves(monthlyLeaves);
	}

	@Override
	public MonthlyLeaves getMonthlyLeavesByLeaveId(Long leaveId)
	{
		return leaveDao.getMonthlyLeavesByLeaveId(leaveId);
	}

	@Override
	public Leave getLeaveByLeaveUUId(String leaveUUID)
	{
		return leaveDao.getLeaveByLeaveUUId(leaveUUID);
	}
	
	@Override
	public File getLeaveExport(OrgLocations orgLocations,LeaveDTO leaveDTO) throws Exception {
		
		File file = null;
		List<LeaveDTO> leaveDtoList = new ArrayList<>();
		
		ResponseList<Leave> leaveResponse = getTopLeavetakers(orgLocations, leaveDTO);
		
		List<Leave> leaveList = leaveResponse.getData();
		HashMap<Long, LeaveDTO> reportMap = new HashMap<>();
		int column = 0;
		float totalbalanceLeaveCounts = 0;
		Employees emp =null;
		for (Leave leave : leaveList) {
			LeaveDTO leaveDto  = new LeaveDTO();
//			int totalLeaves = 0;
			
			emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(leave.getUser().getId(), orgLocations.getId());
//			int totalLeaves = leaveManagementUtil.getBalanceCountForLeaveType(leave.getLeaveType(),emp,orgLocations,leave.getUser());
			float totalLeaves = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(leave.getLeaveType().getId(),leave.getUser().getId(),orgLocations.getId(),orgLocations.getOrg().getId());
			float leaves = leaveManagementUtil.getTotalBalanceLeavesForNonCarryforwardLeaveType(leave.getLeaveType(),totalLeaves, emp);
			
			if(reportMap.containsKey(leave.getUser().getId())){
				if(leave.getLeaveType().getName().equalsIgnoreCase(reportMap.get(leave.getUser().getId()).getLeaveType())) 
					totalbalanceLeaveCounts = Float.valueOf(reportMap.get(leave.getUser().getId()).getBalanceleaves());
				else
					totalbalanceLeaveCounts = leaves;
			}else {
				totalbalanceLeaveCounts = leaves;
			}
			
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(leave.getUser().getLastName())) {
				sb.append(leave.getUser().getFirstName()).append(" ").append(leave.getUser().getLastName());
			} else {
				sb.append(leave.getUser().getFirstName());
			}
			
			StringBuilder date = new StringBuilder();
			if(null != leave.getFromDate() && null != leave.getToDate()) {
				String startDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),leave.getFromDate());
				String toDate = commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),leave.getToDate());
				date.append(startDate).append(" - ").append(toDate);
			} 
			
			
			float appliedLeaveCount = leaveManagementUtil.calculateAppliedLeavesCountWithHalfDays(leave);
			if(Constants.APPROVED.equalsIgnoreCase(leave.getStatus()) || Constants.PENDING.equalsIgnoreCase(leave.getStatus())) {
				totalbalanceLeaveCounts = totalbalanceLeaveCounts - (appliedLeaveCount - leave.getExtraLeavesTaken());
			}
			
//			leaveDto.setLeaveTypeNoOfDays(leave.getLeaveType().getNoOfDays());
			leaveDto.setDuration(date.toString());
			leaveDto.setUserId(emp.getEmployeeId());
			leaveDto.setEmpName(sb.toString());
			leaveDto.setEmpEmail(leave.getUser().getEmail());
			leaveDto.setLeaveType(leave.getLeaveType().getName());
			leaveDto.setStatus(leave.getStatus());
			leaveDto.setExtraLeavesTaken(leave.getExtraLeavesTaken());
			leaveDto.setBalanceleaves(String.valueOf(totalbalanceLeaveCounts));
//			totalLeaves = totalLeaves + leave.getNumberOfDays();
			leaveDto.setLeaveNoDays(appliedLeaveCount);
			
			reportMap.put(leave.getUser().getId(), leaveDto);
			leaveDtoList.add(leaveDto);
			
		}

		if (!"pdf".equalsIgnoreCase(leaveDTO.getExportType())) {
			
			Workbook createExcelworkbook = new XSSFWorkbook();
			Sheet createExcelsheet = createExcelworkbook.createSheet(orgLocations.getOrgLocationName() + "_"
					+ commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()));
			Row writeRow = null;
			createExcelsheet = getExcelSheetWithHeader(createExcelsheet, writeRow, createExcelworkbook);
		
			for (LeaveDTO leaveExcel : leaveDtoList) {
				writeRow = createExcelsheet.createRow(column + 2);
				createExcelsheet = createXlsSheet(createExcelsheet, writeRow,leaveExcel);
				column++;
			}
		
			file = File.createTempFile(orgLocations.getOrgLocationName() + Constants.LEAVE_DATA
					+ commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), new Date()), ".xls");
			file.setReadable(true);
			FileOutputStream fos = new FileOutputStream(file);
		try {
			createExcelworkbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else{
			file = getBillingPdf(leaveDTO,orgLocations,leaveDtoList);
		}
		return file;
	}
	
	private File getBillingPdf(LeaveDTO leaveDTO, OrgLocations orgLocations, List<LeaveDTO> leaveDtoList) throws Exception {
		
		String[] headers = Constants.TOP_TAKERS_LEAVE_HEADERS;
		Document document = new Document(PageSize.LETTER.rotate());

		StringBuilder fileSB = new StringBuilder();
		File file = File.createTempFile(
				fileSB.append(orgLocations.getOrgLocationName()).append(Constants.LEAVE_DATA).append(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),new Date())).toString(),
				".pdf");

		file.setReadable(true);
		try {
			// Get an instance of PdfWriter and create a Table.pdf file as an
			// output.
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

			BaseColor myBordColor = WebColors.getRGBColor("#000000");
			boldFont.setColor(myBordColor);

			StringBuilder orgNameSB = new StringBuilder();
			Paragraph para = new Paragraph(orgNameSB.append(orgLocations.getOrgLocationName()).append(" ").append(Constants.LEAVE_DATA).toString(),
					boldFont);

			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);


			PdfPTable table = new PdfPTable(headers.length);
			Font font = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE);
			PdfPCell cell1 = new PdfPCell(new Phrase(Constants.LEAVE_DATA, font));;
			cell1.setColspan(8);
			headerCellStyle(cell1);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 5, 3, 5, 5, 3, 5, 3});
			table.addCell(cell1);
			for (int i = 0; i < headers.length; i++) {
				String header = headers[i];
				PdfPCell cell = new PdfPCell();
				cell.setGrayFill(0.9f);
				cell.setPhrase(new Phrase(header, new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
				BaseColor myColor = WebColors.getRGBColor("#D3D3D3");
				cell.setBackgroundColor(myColor);
				table.addCell(cell);
			}
			table.completeRow();
			java.util.List<String[]> leaves = new ArrayList<>();
			for (LeaveDTO leavePdf : leaveDtoList) {
				leaves.add(prepareBillingToArray(leavePdf,orgLocations));
			}

			for (String[] leaveArray : leaves) {
				for (int i = 0; i < leaveArray.length; i++) {
					String datum = leaveArray[i];
					PdfPCell cell = new PdfPCell();
					cell.setPhrase(new Phrase(datum, new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
					table.addCell(cell);
				}
				table.completeRow();
			}

			document.addTitle("Leaves List Table");
			table.setSpacingBefore(15f);
			document.add(table);
			logger.info("Pdf table created !!");
		} catch (DocumentException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		} catch (FileNotFoundException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		} finally {
			document.close();
		}
		return file;
	}
	
	private String[] prepareBillingToArray(LeaveDTO leavePdf, OrgLocations locations) throws Exception {

		String billingArray[] = new String[Constants.BILLING_HEADERS.length];
		
		if(leavePdf.getUserId() != null && !"".equalsIgnoreCase(leavePdf.getUserId())){
			billingArray[0] = leavePdf.getUserId();
		}else{
			billingArray[0] = "--";
		}
		
		if(leavePdf.getEmpName() != null && !"".equalsIgnoreCase(leavePdf.getEmpName())){
			billingArray[1] = leavePdf.getEmpName();
		}else{
			billingArray[1] = "--";
		}
		
		if(leavePdf.getEmpEmail() != null && !"".equalsIgnoreCase(leavePdf.getEmpEmail())){
			billingArray[2] = leavePdf.getEmpEmail();
		}else{
			billingArray[2] = "--";
		}
		
		if(leavePdf.getLeaveType() != null && !"".equalsIgnoreCase(leavePdf.getLeaveType())){
			billingArray[3] = leavePdf.getLeaveType();
		}else{
			billingArray[3] = "--";
		}

		if(leavePdf.getDuration() != null && !"".equalsIgnoreCase(leavePdf.getDuration())){
			billingArray[4] = leavePdf.getDuration();
		}else{
			billingArray[4] = "--";
		}
		
			billingArray[5] = String.valueOf(leavePdf.getLeaveNoDays());
			
			billingArray[6] = String.valueOf(leavePdf.getExtraLeavesTaken());
			
			billingArray[7] = String.valueOf(leavePdf.getBalanceleaves());
		
		return billingArray;

	}
	
	private static void headerCellStyle(PdfPCell cell) {
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(0f);
		cell.setPaddingBottom(7f);
		cell.setBackgroundColor(new BaseColor(0, 121, 182));
		cell.setBorder(0);
		cell.setBorderWidthBottom(2f);
	}


	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook) {
		CellStyle style = commonUtil.getCellStyle(createExcelworkbook);
		String[] headers  = Constants.TOP_TAKERS_LEAVE_HEADERS;
		createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet, style,headers,0);
		return createExcelsheet;
	}
	
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, LeaveDTO leaveDto) {
		
		
		if(null != leaveDto.getUserId()){
			writeRow.createCell(0).setCellValue(leaveDto.getUserId());
		}
		
		if(null != leaveDto.getEmpName()  && !"".equalsIgnoreCase(leaveDto.getEmpName())){
			writeRow.createCell(1).setCellValue(leaveDto.getEmpName());
		}
		
		if(null != leaveDto.getEmpEmail()  && !"".equalsIgnoreCase(leaveDto.getEmpEmail())){
			writeRow.createCell(2).setCellValue(leaveDto.getEmpEmail());
		}
		
		if(null != leaveDto.getLeaveType() && !"".equalsIgnoreCase(leaveDto.getLeaveType())){
			writeRow.createCell(3).setCellValue(leaveDto.getLeaveType());
		}
		
		if(null != leaveDto.getDuration() && !"".equalsIgnoreCase(leaveDto.getDuration())){
			writeRow.createCell(4).setCellValue(leaveDto.getDuration());
		}
		
			writeRow.createCell(5).setCellValue(leaveDto.getLeaveNoDays());
		
			writeRow.createCell(6).setCellValue(leaveDto.getExtraLeavesTaken());
		
			writeRow.createCell(7).setCellValue(leaveDto.getBalanceleaves());
	return createExcelsheet;
}

	@Override
	public List<Leave> getAllLeave(){
		return leaveDao.getAllLeave();
	}

	@Override
	public List<Leave> getLeaveByUserIdOrglocationIdandStatus(User consultantUser, Long orglocationId, String approved) {
		return leaveDao.getLeaveByUserIdOrglocationIdandStatus(consultantUser.getId(),orglocationId,approved);
	}

	@Override
	public Response<LeaveDTO> saveBulkApprovedLeaves(MultipartFile multipartFile, OrgLocations orgLocation, User user, Org org)
	{
		Response<LeaveDTO> res = new Response<>();
		
		
		return res;
	}

	@Override
	public boolean reduceLeaveCount(OrgLocations orgLocations, User user, String leaveUUID, Long leaveTypeId, String status, int numberOfDays) throws Exception
	{
		boolean isApproved = false;
//		Employees emp = null;
		Leave updaetedLeave = null;
//		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory();
		Leave leave = leaveDao.getLeaveByLeaveUUId(leaveUUID);
		
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, 1);
//        Employees emp = employeesService.getEmployeeByUserIdAndOrgLocationsId(leave.getUser().getId(),orgLocations.getId());
        Date yearStartDate = null;
        if(leave.getUser().getCreatedDate().before(cal.getTime()) || leave.getUser().getCreatedDate().equals(cal.getTime())){
        	yearStartDate = commonUtil.getDateFormat(Constants.DMY.format(leave.getUser().getCreatedDate()));
        }else{
        	yearStartDate = commonUtil.getDateFormat(Constants.DMY.format(cal.getTime()));
        }
        Date fromDate = commonUtil.getDateFormat(Constants.DMY.format(new Date()));
		if (leave != null) {
			float totalLeaves = associateBalanceLeaveService.getTotalLeavesCountByUserIdAndLeaveTypeId(leaveTypeId,user.getId(),orgLocations.getId(),orgLocations.getOrg().getId());
			Map<String, Float> nuberOfLeavestaken = leaveDao.getNumberOfLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
			float appliedLeaves = leaveManagementUtil.calculateAppliedLeavesCountWithHalfDays(leave);
			if("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.YES.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
				
				List<Leave> leaveList = getLeavesListByTypeIdAndDuration(leaveTypeId, orgLocations.getId(),leave.getUser().getId(),yearStartDate,fromDate);
				float carryForwardLeaves = calculateCarryForwardLeaves(leaveList,fromDate,yearStartDate,leave.getLeaveType(),leave);
				
				Date currentMonthStartDate = leaveManagementUtil.getCurrentMonthStartDate();
				List<Leave> currentMonthLeaves = getLeavesListByTypeIdAndDuration(leaveTypeId, orgLocations.getId(),leave.getUser().getId(),fromDate,currentMonthStartDate);
				float currLeavesTaken = currentMonthLeaves.stream().mapToInt(l->l.getNumberOfDays()).sum();
				float currExtraLeaves = (float) currentMonthLeaves.stream().mapToDouble(l->l.getExtraLeavesTaken()).sum();
				if(currExtraLeaves > 0){
					leave.setExtraLeavesTaken(leave.getNumberOfDays());
				}else{
					float extraLeaves = appliedLeaves - carryForwardLeaves;
					if(extraLeaves > 0){
						leave.setExtraLeavesTaken(extraLeaves);
					}					
					else{
						leave.setExtraLeavesTaken(0);
					}
				}
				
			}else if("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.NO.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
				
				float extraLeaves = leaveManagementUtil.calculateExtraLeaves(leave.getUser().getId(),leave.getLeaveType().getId(),leave.getLeaveType().getNoOfDaysPerPeriod());
				leave.setExtraLeavesTaken(Math.abs(extraLeaves));
				
				/*float thisMonthLeaves = leaveDao.getThisMonthLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
				if((thisMonthLeaves - leave.getNumberOfDays()) > leave.getLeaveType().getNoOfDaysPerPeriod()){
					leave.setExtraLeavesTaken(Math.abs(leave.getNumberOfDays()));
				}else{
					if(leave.getLeaveType().getNoOfDaysPerPeriod() > appliedLeaves){
						leave.setExtraLeavesTaken(0);
					}else{
						float extra = 0f;
						for(Leave l : leaveList){
							l.getNumberOfDays()
						}
						leave.setExtraLeavesTaken(Math.abs(appliedLeaves - leave.getLeaveType().getNoOfDaysPerPeriod()));
					}
				}*/
			}else{
				leave.setExtraLeavesTaken(0);
			}
			
			float numberOfLeavesPerYear = totalLeaves - nuberOfLeavestaken.get("numberOfExtraLeaves");
			leave.setBalanceLeavesCount(numberOfLeavesPerYear);
			
			leave.setUpdatedBy(user.getUserCode());
			leave.setUpdatedDate(new Date());
			updaetedLeave =  leaveDao.saveLeave(leave);
			
			/*//to update applied leaves after current leave duration
			if("Monthly".equalsIgnoreCase(leave.getLeaveType().getPeriod()) && Constants.YES.equalsIgnoreCase(leave.getLeaveType().getLeavesCarryforward())){
				Map<String, Float> totalLeavestaken = leaveDao.getNumberOfLeavesTakenByUser(leave.getUser().getId(),leave.getLeaveType().getId());
				Calendar calander = Calendar.getInstance();
				calander.set(Calendar.MONTH, 11); 
				calander.set(Calendar.DAY_OF_MONTH, 31);
				Date endDate = commonUtil.getDateFormat(Constants.DMY.format(calander.getTime()));
				
				Calendar c = Calendar.getInstance(); 
				c.setTime(leave.getToDate()); 
				c.add(Calendar.DAY_OF_MONTH, 1);
				Date startDate = commonUtil.getDateFormat(Constants.DMY.format(c.getTime()));
				List<Leave> previousLeaveList = getLeavesListByTypeIdAndDuration(leaveTypeId, orgLocations.getId(),leave.getUser().getId(),yearStartDate,fromDate);
				List<Leave> leavesList = getLeavesListByTypeIdAndDuration(leaveTypeId, orgLocations.getId(),leave.getUser().getId(),startDate,endDate);
//				float count = (float) leavesList.stream().mapToDouble(l->l.getExtraLeavesTaken()).sum();
				for(Leave l : leavesList){
//					if(updaetedLeave.getId().longValue() == l.getId().longValue()){
					float appleave = leaveManagementUtil.calculateAppliedLeavesCountWithHalfDays(l);
						float carryLeaves = calculateCarryForwardLeaves(previousLeaveList,l.getFromDate(),yearStartDate,l.getLeaveType(),leave);
						float eLeaves = appleave - (carryLeaves + remainingCarryforwardedLeaves) - l.getLeaveType().getNoOfDaysPerPeriod();
						if(eLeaves > 0){
							l.setExtraLeavesTaken(eLeaves);
						}
						else
							l.setExtraLeavesTaken(0);
						leaveDao.saveLeave(l);
//					}
				}
			}*/
			
			if(updaetedLeave != null) {
				isApproved = true;
			}
		}
		return isApproved;
	}

	@Override
	public List<LeaveApproveHierarchy> getAllLeaveApproversList(Long locationId)
	{
		return leaveDao.getAllLeaveApproversList(locationId);
	}

	@Override
	public File getLeaveTypeImportTemplate(Long orgLocationId)
	{
		Workbook wb = (Workbook) new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Leave Type Import Template");
		sheet = setTemplateHeaders(sheet);
		List<LeaveType> leaveTypeList = leaveTypeService.getLeaveTypeListByLocationIdAndStatus(String.valueOf(orgLocationId));
		HSSFSheet rolehidden = (HSSFSheet) wb.createSheet("leaveTypehidden");
		HSSFRow roleRow = rolehidden.createRow(0);
		int i = 0;
		for (LeaveType leaveType : leaveTypeList) {
			try {
				HSSFCell cell = roleRow.createCell(i);
				cell.setCellValue(leaveType.getName());
			} catch (Exception e) {
			}
			i++;
		}
		Name namedRoleCel = wb.createName();
		namedRoleCel.setNameName("leaveTypehidden");
		namedRoleCel.setRefersToFormula("leaveTypehidden!$A$1:$DZ$1" + leaveTypeList.size());
		DVConstraint rolConstraint = DVConstraint.createFormulaListConstraint("leaveTypehidden");
		CellRangeAddressList roleAddressList = new CellRangeAddressList(1, 1000, 2, 2);
		HSSFDataValidation roleValidation = new HSSFDataValidation(roleAddressList, rolConstraint);
		wb.setSheetHidden(1, true);
		sheet.addValidationData(roleValidation);
		File file = new File("Leave_Type_Import_Template.xls");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			logger.error("template download failed due to " + e.getMessage());
		}
		return file;
	}

	/**
	 * Prepare Excel Sheet Header for Leave Type Import
	 * 
	 * @param sheet
	 * @return Sheet
	 */
	private Sheet setTemplateHeaders(Sheet sheet)
	{
		Row excelHeader = sheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Associate Name");
		excelHeader.createCell(1).setCellValue("Email id*");
		excelHeader.createCell(2).setCellValue("Leave Type*");
		excelHeader.createCell(3).setCellValue("Balance Leaves");
		return sheet;
	}

	@Override
	public LeaveTypeDTO saveBulkLeaveTypes(MultipartFile multipartFile, OrgLocations orgLocation, User user, Org org)
	{
		String fileName = multipartFile.getOriginalFilename();
		LeaveTypeDTO leaveTypeDTO = new LeaveTypeDTO();
		List<LeaveTypeImportDTO> list = new ArrayList<>();
		try {
			File file = new File(multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			FileInputStream fis = new FileInputStream(file);
			Workbook workbook;
			logger.debug("FIle Extension is: " + multipartFile.getOriginalFilename());
			boolean isValidated = false;
			workbook = WorkbookFactory.create(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					LeaveTypeImportDTO importDTO = new LeaveTypeImportDTO();
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) {
						isValidated = validateExeclData(row, importDTO, fileName, orgLocation);
					}
					if (isValidated) {
						if (row.getRowNum() > 0) { // Excluding column names
							if (row.getCell(0) != null) {
								String assoName = getCellValueAsString(row.getCell(0), fileName);
								importDTO.setAssociateName(assoName);
							}
							importDTO.setAssociateEmail(getCellValueAsString(row.getCell(1), fileName));
							importDTO.setLeaveTypeName(getCellValueAsString(row.getCell(2), fileName));
							if (row.getCell(3) != null && !"".equalsIgnoreCase(row.getCell(3).toString())) {
								String noOfLeaves = getCellValueAsString(row.getCell(3), fileName);
								importDTO.setNumberOfLeaves(noOfLeaves);
							}
							list.add(importDTO);
						}
					}
				}
			}
			fis.close();
		} catch (IOException e) {
			logger.error("Exception cause is : " + ExceptionUtils.getFullStackTrace(e));
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		
		int insertCount = addAssociateBalanceLeaveType(list,orgLocation,user);
		leaveTypeDTO.setUpdateCount(String.valueOf(insertCount));
		return leaveTypeDTO;
	}
	
	private int addAssociateBalanceLeaveType(List<LeaveTypeImportDTO> implist, OrgLocations orgLocation, User user)
	{
		int i =0;
		if(implist != null && implist.size() >0) {
			try {
				for(LeaveTypeImportDTO ltdto : implist) {//implist.forEach(ltdto->{
					LeaveType leaveType = leaveTypeService.getLeaveTypeByLeaveTypeName(ltdto.getLeaveTypeName(),orgLocation);
					UserRole userRole = null;
					try {
						userRole = userRoleService.getUserDetailsByEmailAndLocationId(ltdto.getAssociateEmail().trim(),orgLocation.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(userRole != null && leaveType != null) {
						List<User> userList = new ArrayList<>();
						userList.add(userRole.getUser());
						AssociateBalanceLeave associa = associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), userRole.getUser().getId(), orgLocation.getId(),orgLocation.getOrgId());
						if(associa != null) {
							int result = associateBalanceLeaveService.addOrSubAssociateBalanceLeaveType(userList, orgLocation.getId(), leaveType, 1,Float.parseFloat(ltdto.getNumberOfLeaves()),"IMPORT",Constants.UPDATE);
						}else {
							int result = associateBalanceLeaveService.addLeaveTypeToAssociate(userList,orgLocation,leaveType,user,Float.parseFloat(ltdto.getNumberOfLeaves()));
						}
						i++;
					}
				
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return i;
	}

	/**
	 * Validate Excel Data
	 * @param row
	 * @param importDTO
	 * @param fileName
	 * @param orgLocation
	 * @return
	 */
	private boolean validateExeclData(Row row, LeaveTypeImportDTO importDTO, String fileName, OrgLocations orgLocation)
	{
		boolean isValidated = false;
		if (row.getRowNum() > 0) { // Excluding column names
			if (row.getCell(1) == null && "".equalsIgnoreCase(row.getCell(1).toString())) {
				return isValidated;
			}
			if (row.getCell(2) == null || "".equalsIgnoreCase(row.getCell(2).toString())) {
				return isValidated;
			}
		}
		return true;
	}
	
	/**
	 * Read cell value as String
	 * @param cell
	 * @param fileName
	 * @return String
	 */
	private String getCellValueAsString(Cell cell, String fileName) {
		String cellValue = "";
		if(fileName.toLowerCase().endsWith("xls")){
				HSSFCell hssfCell = (HSSFCell) cell;
				if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					hssfCell.setCellType(Cell.CELL_TYPE_STRING);
					String hssfCellValue = hssfCell.getStringCellValue();
					if (hssfCellValue != null || !"".equalsIgnoreCase(hssfCellValue)) {
						cellValue = hssfCellValue;
					} 
				}else{
					cellValue = cell.getStringCellValue();
				}
			}else{
				XSSFCell xssfCell = (XSSFCell) cell;
				if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					xssfCell.setCellType(Cell.CELL_TYPE_STRING);
					String xssfCellValue = xssfCell.getStringCellValue();
					if (xssfCellValue != null || !"".equalsIgnoreCase(xssfCellValue)) {
						cellValue = xssfCellValue;
					} 
				}else{
					cellValue = cell.getStringCellValue();
				}
			}
			return cellValue;
		}

	@Override
	public Map<String, Float> getNumberOfCurrentMonthLeavesTakenByUser(Long userId, Long leaveTypeId)
	{
		return leaveDao.getNumberOfCurrentMonthLeavesTakenByUser(userId, leaveTypeId);
	}

	@Override
	public List<AppliedLeaveDateView> getLeaveByUserIdAndFromToDate(Long userId, OrgLocations orgLocation, String fromDate, String toDate)
	{
		return leaveDao.getLeaveByUserIdAndFromToDate(userId,orgLocation,fromDate,toDate);
	}

	@Override
	public void updateBalanceLeaves(Leave leave, User user, OrgLocations orgLocations)
	{
		AssociateBalanceLeave assBalLeave =  associateBalanceLeaveService.getTotalLeavesByUserIdAndLeaveTypeId(leave.getLeaveType().getId(),user.getId(),orgLocations.getId(),orgLocations.getOrg().getId());
		float balanceLeaves = Float.parseFloat(assBalLeave.getTotalLeaves()) - leave.getNumberOfDays();
		assBalLeave.setTotalLeaves(String.valueOf(balanceLeaves));
		associateBalanceLeaveService.saveOrUpdateAssocicateBalanceLeaves(assBalLeave);
	}




	@Override
	public Sheet getDailyWiseLeaveExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<LeaveReportView> leaveReportsList, OrgLocations orgLocations) throws Exception
	{

			Row writeRow = null;
	        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,leaveReportsList,orgLocations);
	        List<LeaveReportView> list = leaveReportsList.getData();
	        int column=0;
	        for(LeaveReportView lrv : list){
	       	 writeRow = createExcelsheet.createRow(column+1);
	       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,column,lrv);
	       	 column++;
	        }
			return createExcelsheet;
		
	}

	
	
	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,ResponseList<LeaveReportView> leaveReportsList, OrgLocations orgLocations) {

		
		
		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     org.apache.poi.ss.usermodel.Font font = createExcelworkbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
		 
		 writeRow = createExcelsheet.createRow(0);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 createExcelsheet.setColumnWidth(2, 5000);
		 createExcelsheet.setColumnWidth(3, 5000);
		 createExcelsheet.setColumnWidth(4, 5000);
		 createExcelsheet.setColumnWidth(5, 5000);
		 createExcelsheet.setColumnWidth(6, 5000);
		 createExcelsheet.setColumnWidth(7, 5000);
		 createExcelsheet.setColumnWidth(8, 5000);
		 createExcelsheet.setColumnWidth(9, 5000);
		 createExcelsheet.setColumnWidth(10, 5000);
		 createExcelsheet.setColumnWidth(11, 5000);
		 createExcelsheet.setColumnWidth(12, 5000);
		 createExcelsheet.setColumnWidth(13, 5000);
		 
		 String headers [] = Constants.DAILY_WISE_LEAVE_REPORT_HEADERS;
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
	}
	
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column,LeaveReportView leaveReportDTO) throws Exception {
			if(leaveReportDTO.getName() !=null){
				 writeRow.createCell(0).setCellValue(leaveReportDTO.getName()); 
			 }
			else{
				   writeRow.createCell(0).setCellValue("--");   
			   }
			   if(leaveReportDTO.getEmploymentType() !=null){
				   writeRow.createCell(1).setCellValue(leaveReportDTO.getEmploymentType());
			   }
			   else{
				   writeRow.createCell(1).setCellValue("--");   
			   }
			   if(leaveReportDTO.getUserDeptName() != null){
				   writeRow.createCell(2).setCellValue(leaveReportDTO.getUserDeptName());
			   }
			   else{
				   writeRow.createCell(2).setCellValue("--");   
			   }
			   if(leaveReportDTO.getUserRoleName() !=null){
				   writeRow.createCell(3).setCellValue(leaveReportDTO.getUserRoleName());
			   }
			   else{
				   writeRow.createCell(3).setCellValue("--");   
			   }
			   if(leaveReportDTO.getLeaveTypeName() != null){
				   writeRow.createCell(4).setCellValue(leaveReportDTO.getLeaveTypeName());
			   }
			   else{
				   writeRow.createCell(4).setCellValue("--");   
			   }
				 if(leaveReportDTO.getLeaveTypeBillable() !=null){
					 writeRow.createCell(5).setCellValue(leaveReportDTO.getLeaveTypeBillable()); 
				 }
				 else{
					   writeRow.createCell(5).setCellValue("--");   
				   }
				 if(leaveReportDTO.getDuration() != null){
					 writeRow.createCell(6).setCellValue(leaveReportDTO.getDuration());
				 }
				 else{
					   writeRow.createCell(6).setCellValue("--");   
				   }
				 if(leaveReportDTO.getFromDateStr() != null){
					   writeRow.createCell(7).setCellValue(leaveReportDTO.getFromDateStr());
				   }
				 else{
					   writeRow.createCell(7).setCellValue("--");   
				   }
				 if(leaveReportDTO.getToDateStr() != null){
					   writeRow.createCell(8).setCellValue(leaveReportDTO.getToDateStr());
				   }
				 else{
					   writeRow.createCell(8).setCellValue("--");   
				   }
				 if(leaveReportDTO.getAppliedOnDateStr() != null){
					   writeRow.createCell(9).setCellValue(leaveReportDTO.getAppliedOnDateStr());
				   }
				 else{
					   writeRow.createCell(9).setCellValue("--");   
				   }
				 if(leaveReportDTO.getReason() != null){
					   writeRow.createCell(10).setCellValue(leaveReportDTO.getReason());
				   }
				 else{
					   writeRow.createCell(10).setCellValue("--");   
				   }
				 if(leaveReportDTO.getStatus() != null){
					   writeRow.createCell(11).setCellValue(leaveReportDTO.getStatus() );
				   }
				 else{
					   writeRow.createCell(11).setCellValue("--");   
				   }
				 if(leaveReportDTO.getApprover() != null){
					   writeRow.createCell(12).setCellValue(leaveReportDTO.getApprover() );
				   }
				 else{
					   writeRow.createCell(12).setCellValue("--");   
				   }
				 if(leaveReportDTO.getAppliedOnDateStr()  != null){
					 if(leaveReportDTO.getStatus().equalsIgnoreCase("APPROVED")){
					   writeRow.createCell(13).setCellValue(leaveReportDTO.getAppliedOnDateStr());
					   }
					 else{
						 writeRow.createCell(13).setCellValue("--");
					 }
				   }
				
			
//		}
		
		return createExcelsheet;
		
	}

	@Override
	public File getDailyWiseLeavePdfFile(ResponseList<LeaveReportView> leaveReportsList,OrgLocations orgLocations) throws Exception
	{
		return pdfUtil.createDayWiseLeaveReportPdfTable(leaveReportsList,orgLocations);
	}



	@Override
	public Sheet getLeaveReportExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<LeaveReportView> leaveReportsList, OrgLocations orgLocations) throws Exception
	{
		Row writeRow = null;
	    createExcelsheet = getLeaveReportExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,leaveReportsList,orgLocations);
	    List<LeaveReportView> list = leaveReportsList.getData();
	    int column=0;
	    for(LeaveReportView lrv : list){
	   	 writeRow = createExcelsheet.createRow(column+1);
	   	 createExcelsheet = createLeaveReportXlsSheet(createExcelsheet,writeRow,column,lrv);
	   	 column++;
	    }
		return createExcelsheet;
	}
	private Sheet getLeaveReportExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,ResponseList<LeaveReportView> leaveReportsList, OrgLocations orgLocations) {

		
		
		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     org.apache.poi.ss.usermodel.Font font = createExcelworkbook.createFont();
	    font.setColor(IndexedColors.BLACK.getIndex());
	     style.setFont(font);
		 
		 writeRow = createExcelsheet.createRow(0);
		 createExcelsheet.setColumnWidth(0, 3000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 createExcelsheet.setColumnWidth(2, 5000);
		 createExcelsheet.setColumnWidth(3, 5000);
		 createExcelsheet.setColumnWidth(4, 5000);
		 createExcelsheet.setColumnWidth(5, 5000);
		 createExcelsheet.setColumnWidth(6, 5000);
		 createExcelsheet.setColumnWidth(7, 5000);
		 createExcelsheet.setColumnWidth(8, 5000);
		 createExcelsheet.setColumnWidth(9, 5000);
		 createExcelsheet.setColumnWidth(10, 5000);
		 createExcelsheet.setColumnWidth(11, 5000);
		 createExcelsheet.setColumnWidth(12, 5000);
		 
		 String headers [] = Constants.LEAVE_REPORT_HEADERS;
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,0);
	}
	private Sheet createLeaveReportXlsSheet(Sheet createExcelsheet, Row writeRow, int column,LeaveReportView leaveReportDTO) throws Exception {
		if(leaveReportDTO.getEid() !=null && !leaveReportDTO.getEid().isEmpty()){
			 writeRow.createCell(0).setCellValue(leaveReportDTO.getEid()); 
		 }
		else{
			   writeRow.createCell(0).setCellValue("--");   
		   }
		   if(leaveReportDTO.getName() !=null){
			   writeRow.createCell(1).setCellValue(leaveReportDTO.getName());
		   }
		   else{
			   writeRow.createCell(1).setCellValue("--");   
		   }
		   if(leaveReportDTO.getUserDeptName() != null){
			   writeRow.createCell(2).setCellValue(leaveReportDTO.getUserDeptName());
		   }
		   else{
			   writeRow.createCell(2).setCellValue("--");   
		   }
		   if(leaveReportDTO.getLeaveTypeName() !=null){
			   writeRow.createCell(3).setCellValue(leaveReportDTO.getLeaveTypeName());
		   }
		   else{
			   writeRow.createCell(3).setCellValue("--");   
		   }
		   if(leaveReportDTO.getReason() != null){
			   writeRow.createCell(4).setCellValue(leaveReportDTO.getReason());
		   }
		   else{
			   writeRow.createCell(4).setCellValue("--");   
		   }
			 if(leaveReportDTO.getLeaveTypeBillable() !=null){
				 writeRow.createCell(5).setCellValue(leaveReportDTO.getLeaveTypeBillable()); 
			 }
			 else{
				   writeRow.createCell(5).setCellValue("--");   
			   }
			 if(leaveReportDTO.getFromDateStr() != null){
				 writeRow.createCell(6).setCellValue(leaveReportDTO.getFromDateStr());
			 }
			 else{
				   writeRow.createCell(6).setCellValue("--");   
			   }
			 if(leaveReportDTO.getToDateStr() != null){
				   writeRow.createCell(7).setCellValue(leaveReportDTO.getToDateStr());
			   }
			 else{
				   writeRow.createCell(7).setCellValue("--");   
			   }
			 if(leaveReportDTO.getAppliedOnDateStr() != null){
				   writeRow.createCell(8).setCellValue(leaveReportDTO.getAppliedOnDateStr());
			   }
			 else{
				   writeRow.createCell(8).setCellValue("--");   
			   }
			 if(leaveReportDTO.getNoOfDays() != null){
			 writeRow.createCell(9).setCellValue(leaveReportDTO.getNoOfDays());
			 }
			 else{
				   writeRow.createCell(9).setCellValue("0.0");   
			   }
			 if(leaveReportDTO.getStatus() != null){
				   writeRow.createCell(10).setCellValue(leaveReportDTO.getStatus());
			   }
			 else{
				   writeRow.createCell(10).setCellValue("--");   
			   }
			 if(leaveReportDTO.getApprover() != null){
				   writeRow.createCell(11).setCellValue(leaveReportDTO.getApprover());
			   }
			 else{
				   writeRow.createCell(11).setCellValue("--");   
			   }
			 if(leaveReportDTO.getApprovedOnDateStr() != null){
				   writeRow.createCell(12).setCellValue(leaveReportDTO.getApprovedOnDateStr() );
			   }
			 else{
				   writeRow.createCell(12).setCellValue("--");   
			   }
			
			
		
	//}

	return createExcelsheet;

	}

	@Override
	public File getLeaveReportPdfFile(ResponseList<LeaveReportView> leaveReportsList, OrgLocations orgLocations) throws Exception
	{
		return pdfUtil.createLeaveReportPdfTable(leaveReportsList,orgLocations);
	}

	
	@Override
	public List<String> getLeaveTypeNamesByOrgLocationId(Long orgLocationId,String topleave) {
		return leaveDao.getLeaveTypeNamesByOrgLocationId(orgLocationId,topleave);
	}

	@Override
	public Map<String,List<LeaveBalanceReportDTO>> getLeaveNamesByOrgLocationId(Long orgLocationId,List<Long> userleaveTypeList,List<String> leaveTypesList,String topleave) {
		return leaveDao.getLeaveNamesByOrgLocationId(orgLocationId,userleaveTypeList,leaveTypesList,topleave);
	}

	public String getDepartmentTypeNamesByOrgLocationIdAndUserId(Long userId ,Long orgLocationId) throws Exception {
		return leaveDao.getDepartmentTypeNamesByOrgLocationIdAndUserId(userId,orgLocationId);
	}


	@Override
	public Sheet getLeaveBalanceExcelFile(Sheet createExcelsheet,Workbook createExcelworkbook,Response<LeaveBalanceReportDTO> LeaveBalanceReport,OrgLocations orgLocation,String topleave) throws Exception{
		Row writeRow = null;
        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,LeaveBalanceReport,orgLocation,topleave);
        List<LeaveBalanceReportDTO> list = (List<LeaveBalanceReportDTO>) LeaveBalanceReport.getObject().getUsers();
        int column=0;
        for(LeaveBalanceReportDTO lBR : list) {
       	 writeRow = createExcelsheet.createRow(column+1);
       	createExcelsheet = createXlsBalanceSheet(createExcelsheet,writeRow,column,lBR,LeaveBalanceReport,topleave);
       	 column++;
        }
		return createExcelsheet;
	}
	

private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,Response<LeaveBalanceReportDTO> LeaveBalanceReport,OrgLocations orgLocation,String topleave) {
	CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     org.apache.poi.ss.usermodel.Font font = createExcelworkbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
         List<String> headers = new ArrayList<String>();
 		headers.add("Employee Name");
 		headers.add("Employee Type");
 		headers.add("Department");
 		//if (topleave != null) 
 		headers.add("Role");
 		for(String str:LeaveBalanceReport.getObject().getLeaveTypeNames())
 		{
 		headers.add(str);
 		}
 		headers.add("TotalBalanceLeaves");
		 writeRow = createExcelsheet.createRow(0);
		 createExcelsheet.setColumnWidth(0, 7000);
		 createExcelsheet.setColumnWidth(1, 7000);
		 createExcelsheet.setColumnWidth(2, 7000);
		 createExcelsheet.setColumnWidth(3, 7000);
		 for(String str:LeaveBalanceReport.getObject().getLeaveTypeNames())
		 {
			 int i=4;
			 createExcelsheet.setColumnWidth(i, 5000); 
			 i++;
		 }
		 createExcelsheet.setColumnWidth(headers.size(),5000);

		 
		return createExcelsheet = commonUtil.setExcelHeader(createExcelsheet,style,headers,0);
	}

private Sheet createXlsBalanceSheet(Sheet createExcelsheet, Row writeRow, int column,LeaveBalanceReportDTO leaveBalanceReportDTO,Response<LeaveBalanceReportDTO> LeaveBalanceReport,String topleave) throws Exception {
	
	    if(leaveBalanceReportDTO.getEmpName() !=null){
		  writeRow.createCell(0).setCellValue(leaveBalanceReportDTO.getEmpName()); 
	    }else{
		   writeRow.createCell(0).setCellValue("--");   
	    }
	    if(leaveBalanceReportDTO.getEmpType() !=null){
		   writeRow.createCell(1).setCellValue(leaveBalanceReportDTO.getEmpType());
	    } else{
		   writeRow.createCell(1).setCellValue("--");   
	   }
	   if(LeaveBalanceReport.getObject().getDepart().entrySet() != null) {
		   for(Map.Entry<Long, String> k: LeaveBalanceReport.getObject().getDepart().entrySet()){
				Long ltv =   k.getKey();
				String lti = k.getValue();
				if(ltv == leaveBalanceReportDTO.getUserId().longValue()){
				  
			   writeRow.createCell(2).setCellValue(lti);
		   }
		   }
	   }else{
		   writeRow.createCell(2).setCellValue("--");   
	   }
	
	   if(leaveBalanceReportDTO.getRole() !=null){
		   writeRow.createCell(3).setCellValue(leaveBalanceReportDTO.getRole());
	   }
	   else{
		   writeRow.createCell(3).setCellValue("--");   
	   }
	   float lo =0;
	   int i=4;
	   float l=0;
	   for (String str :LeaveBalanceReport.getObject().getLeaveTypeNames()) {
			for(Map.Entry<String, Float> k: LeaveBalanceReport.getObject().getMap().entrySet()){
				String[] ltName =   k.getKey().split("-");
				if((Long.parseLong(ltName[1]) == leaveBalanceReportDTO.getUserId().longValue()) && (ltName[0].equalsIgnoreCase(str))){
					 l = k.getValue();
					 writeRow.createCell(i).setCellValue(l);
				       lo = lo+l;
					 }else if(ltName[0] == null &&str == null)
						writeRow.createCell(i).setCellValue("--");		
			          }
		i++;} 
	   writeRow.createCell(i).setCellValue(lo);
	   return createExcelsheet;
}

@Override
public File getLeaveBalancePdfFile(Response<LeaveBalanceReportDTO> LeaveBalanceReport,OrgLocations orgLocation,String topleave) throws Exception
{
	return pdfUtil.creategetLeaveBalancePdfTable(LeaveBalanceReport,orgLocation,topleave);
}


@Override
public ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromToDate(DayWiseLeaveReportDTO dayWiseLeaveReportDTO,Long orgId, Long orgLocationId,String loginUserRole)throws Exception
{
	return leaveDao.getLeaveReportsOrglocationIdAndFromToDate(dayWiseLeaveReportDTO,orgId,orgLocationId,loginUserRole);
}








@Override
public Float getTakenLeavesByOrgLocationIdAndUserId(Long leaveId,Long userId,Long orglocationId)throws Exception {
	return leaveDao.getTakenLeavesByOrgLocationIdAndUserId(leaveId,userId,orglocationId);
}

@Override
public ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromDateToDate(HrLeaveReportDTO hrLeaveReportDTO, Long orgId, Long orgLocationId,String loginUserRole) throws Exception
{
	return leaveDao.getLeaveReportsOrglocationIdAndFromToDate(hrLeaveReportDTO,orgId,orgLocationId,loginUserRole);
}

@Override
public Set<PayrollLopHis> getLeaveByFrequency(Long candidateId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation,EmployeePayroll employeePayroll)
{
	return leaveDao.getLeaveByFrequency(candidateId,freqStartDate,freqEndDate,orgLocation,employeePayroll);
}


@Override
public Set<PayrollLopHis> getLeaveByFrequencyInd(Long candidateId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation, IndEmployeePayroll employeePayroll)
{
	return leaveDao.getLeaveByFrequencyInd(candidateId,freqStartDate,freqEndDate,orgLocation,employeePayroll);
}

@Override
public List<Leave> getAllLeavesByLocationIdAndStatusAndAppliedDate(Long locationId, String status,
		Date appliedDate) {

	return leaveDao.getAllLeavesByLocationIdAndStatusAndAppliedDate(locationId, status, appliedDate);
}

@Override
public List<Leave> getAllLeavesByLocationIdAndStatusAndLeaveStartDate(Long locationId, String status,
		Date appliedDate) {

	return leaveDao.getAllLeavesByLocationIdAndStatusAndLeaveStartDate(locationId, status, appliedDate);
}

}






