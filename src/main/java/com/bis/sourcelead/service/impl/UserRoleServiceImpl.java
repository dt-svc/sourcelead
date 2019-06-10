package com.bis.sourcelead.service.impl;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.dto.UsersUsageReportDTO;
import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.AssociateDepartmentView;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserIdView;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service 
public class UserRoleServiceImpl implements UserRoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	TabsDao tabsDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	OrgSpocDao orgSpocDao;
	
	@Autowired
	RequirementDao requirementDao;
	
	@Autowired
	TeamDao teamDao;

	@Autowired
	ProjectsDao projectsDao;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	OrgDocumentsDao orgDocumentsDao;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Autowired
	CandidateService candidateService;
	

	@Override
	public UserRole addUserRole(UserRole userRole) throws Exception {
		userRole.setCreatedDate(new Date());
		userRole.setUpdatedDate(new Date());
		return userRoleDao.addUserRole(userRole);
	}

	@Override
	public UserRole getLoginUserPrimaryRole(Long userId) throws Exception{
		return userRoleDao.getLoginUserPrimaryRole(userId);
	}
	
	@Override
	public UserRole getNotPrimaryUser(Long userId) throws Exception{
		return userRoleDao.getNotPrimaryUser(userId);
	}

	@Override

	public List<UserRole> getUsersListByLocationId(Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByLocationId(locationId,userState,userId,loginUserRole,userCode);
	}

	@Override
	public List<UserRole> getUsersListByRoleAndLocationId(Long roleId,Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndLocationId(roleId, locationId,userState,userId,loginUserRole,userCode);
	}

	@Override
	public List<UserRole> getUsersListByUserNameAndLocationId(String userName, Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserNameAndLocationId(userName, locationId,userState,userId,loginUserRole,userCode);
	}

	@Override
	public List<UserRole> getUsersListByRoleAndNameAndLocationId(Long roleId, String userName,
			Long locationId,String userState,Long userId,String loginUserRole,String userCode,Integer offset,String viewType) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndNameAndLocationId(roleId, userName, locationId,userState,userId,loginUserRole,userCode,offset,viewType);
	}

	@Override
	public List<UserRole> getUsersListByUserStateAndLocationId(String userState, Long locationId,Long userId,String loginUserRole,String userCode)
			throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByUserStateAndLocationId(userState, locationId,userId,loginUserRole,userCode);
	}
	
	public List<UserRole> getUsersBasedOnUserRoles() throws Exception {
		return userRoleDao.getUsersBasedOnUserRoles();
	}

	@Override
	public List<UserRole> getApproverAssignedUsers(List<Long> projectIds, String userState, String searchByName)
			throws Exception {
		return userRoleDao.getApproverAssignedUsers(projectIds,userState,searchByName);
	}

	@Override
	public UserRole getUserDetailsByEmailAndLocationId(String email,Long locationId) throws Exception {
		return userRoleDao.getUserDetailsByEmailAndLocationId(email,locationId);
	}
	
	@Override
	public boolean isUserActive(Long userId) throws Exception {
		return userRoleDao.isUserActive(userId);
	}
	
	@Override
	public Boolean isUserAdmin(Long userId) {
		return userRoleDao.isUserAdmin(userId);
	}
	
	/*@Override
	public List<UserRole> getManagersByUserRolesAndOrgLocationId(String roleType , Long orgLocationsId) throws Exception {
		return userRoleDao.getManagersByUserRolesAndOrgLocationId(roleType, orgLocationsId);
	}
	
	@Override
	public List<UserRole> getManagersByUserIdAndUserRolesAndOrgLocationId(Long userId,String roleType , Long orgLocationsId) throws Exception {
		return userRoleDao.getManagersByUserIdAndUserRolesAndOrgLocationId(userId, roleType, orgLocationsId);
	}*/
	
	@Override
	public List<Long> getAllUserIdsByReportingManagerId(Long reportingManagerId) throws Exception {
		return userRoleDao.getAllUserIdsByReportingManagerId(reportingManagerId);
	}
	
	@Override
	public UserRole getUserRoleByUserIdAndLocationId(Long userId,Long locationId) throws Exception {
		return userRoleDao.getUserRoleByUserIdAndLocationId(userId,locationId);
	}

	@Override
	public List<UserRole> getUserSpocsListByUserId(Long userId) throws Exception {
		return userRoleDao.getUserSpocsListByUserId(userId);
	}

	@Override
	public UserRole getUserByUserId(Long userId) throws Exception {
		return userRoleDao.getUserByUserId(userId);
	}
	
	@Override
	public Long getUsersCountByOrgLocationID(Long orgLocationId) throws Exception {
		return userRoleDao.getUsersCountByOrgLocationID(orgLocationId);
	}
	@Override
	public List<UserRole> getAllUsersListByOrgLocationId(Long orgLocationId,Long loginUserId)throws Exception {
		return userRoleDao.getAllUsersListByOrgLocationId(orgLocationId,loginUserId);
	}
	
	public Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, List<UserRole> activeList,
			List<UserRole> inactiveList, List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,String userState,OrgLocations orgLocations)
			throws Exception {
		Row writeRow = null;
        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,activeList,inactiveList,notLoggedInList,userState,userRolesList,orgLocations);
        
        int column=0;
        for(UserRole userRoles : userRolesList){
       	 writeRow = createExcelsheet.createRow(column+8);
       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,column,userRoles,orgLocations);
       	 column++;
        }
		return createExcelsheet;
	}
	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			List<UserRole> activeList, List<UserRole> inactiveList, List<UserRole> notLoggedInList, String userState, ArrayList<UserRole> userRolesList,OrgLocations orgLocations) {

		
		 //userRole list developed by laxman  
		int activeCount = 0;
		int inActiveCount = 0;
		int notLoggedInCount = 0;
        for(UserRole ur:userRolesList) {
        	if("ACTIVE".equalsIgnoreCase(ur.getUserState().toString())){
        		activeCount++;
        	} 
        	if("INACTIVE".equalsIgnoreCase(ur.getUserState().toString())){
        		inActiveCount++;
        	} 
        	if("NOT_LOGGEDIN".equalsIgnoreCase(ur.getUserState().toString())){
        		notLoggedInCount++;
        	} 
        }
		
		
		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
		 
		 writeRow = createExcelsheet.createRow(0);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell0 = writeRow.createCell(0);
		 cell0.setCellValue("Orgnization :");
		 cell0.setCellStyle(style);
		 
		 Cell cell01 = writeRow.createCell(1);
		 if(orgLocations.getOrgLocationName()!=null)
		 {
			 cell01.setCellValue(orgLocations.getOrgLocationName()); 
		 }
		 else
		 {
			 cell01.setCellValue("--");
		 }
		cell01.setCellStyle(style);
		 
		
		
		 writeRow = createExcelsheet.createRow(1);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 
		 Cell cell1 = writeRow.createCell(0);
		 cell1.setCellValue("Active :");
		 cell1.setCellStyle(style);
		 
		 Cell cell2 = writeRow.createCell(1);
				 cell2.setCellValue(activeCount);
		 cell2.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(2);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell3 = writeRow.createCell(0);
		 cell3.setCellValue("Inactive :");
		 cell3.setCellStyle(style);
		 
		 Cell cell4 = writeRow.createCell(1);
				 cell4.setCellValue(inActiveCount); 
		 cell4.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(3);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell5 = writeRow.createCell(0);
		 cell5.setCellValue("Not Loggedin : ");
		 cell5.setCellStyle(style);
		 
		 Cell cell6 = writeRow.createCell(1);
				 cell6.setCellValue(notLoggedInCount); 
		 cell6.setCellStyle(style);
		 
		 String headers [] = null;
		 
		 if(orgLocations.getCountry().equalsIgnoreCase(Constants.COUNTRY_INDIA)) {
			 headers  = Constants.USER_EXCEL_HEADERS_INDIA; 
		 }else {
			 headers  = Constants.USER_EXCEL_HEADERS_NOT_INDIA;  
		 }
		 
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,7);
	}
	
	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column, UserRole userRoles,OrgLocations orgLocations) throws Exception {
//		 Set<Employees> employeeSet = userRoles.getUser().getEmployee();
		 Employees employee = employeesService.getEmployeeByUserId(userRoles.getUser().getId());
		 if(employee.getEmployeeId() != null) {
			 writeRow.createCell(0).setCellValue(employee.getEmployeeId());
		 }
		 
		  if(employee.getHireDate() !=null){
				 String dateOfJoin = commonUtil.dateStrFromOrgDateFormatter(userRoles.getOrgLocations().getDateFormat(),employee.getHireDate());
				 writeRow.createCell(1).setCellValue(dateOfJoin); 
			 }
			 
			 if(userRoles.getUser().getEmail() !=null){
			   writeRow.createCell(2).setCellValue(userRoles.getUser().getEmail());
		   }
		   
		   if(employee.getProbationPeriod() != null){
			   writeRow.createCell(3).setCellValue(employee.getProbationPeriod() );
		   }
		   
		   if(userRoles.getIsMailSent() != null && !"".equalsIgnoreCase(userRoles.getIsMailSent().toString()) && "N".equalsIgnoreCase(userRoles.getIsMailSent().toString()) && !"INACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
			   writeRow.createCell(4).setCellValue("Invite");
	   		   }else if((userRoles.getIsMailSent() != null && !"".equalsIgnoreCase(userRoles.getIsMailSent().toString()) && "Y".equalsIgnoreCase(userRoles.getIsMailSent().toString())) &&  (userRoles.getUserState() !=null && "NOT_LOGGEDIN".equalsIgnoreCase(userRoles.getUserState().toString()))){
	   			writeRow.createCell(4).setCellValue("Resend");
	   		   }else if(userRoles.getUserState() !=null && "ACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
	   			writeRow.createCell(4).setCellValue("Active");
	   		   }else if(userRoles.getUserState() !=null && "INACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
	   			writeRow.createCell(4).setCellValue("In-active");
	   		   }
		   if(employee.getConfirmationDate() !=null){
				 String confirmationDate = commonUtil.dateStrFromOrgDateFormatter(userRoles.getOrgLocations().getDateFormat(),employee.getConfirmationDate());
				 writeRow.createCell(5).setCellValue(confirmationDate); 
			 }
		   
		   if(userRoles.getUser().getFirstName() !=null){
			   writeRow.createCell(6).setCellValue(userRoles.getUser().getFirstName());
		   }
		   
		   if(userRoles.getUser().getMiddelName() !=null){
			   writeRow.createCell(7).setCellValue(userRoles.getUser().getMiddelName());
		   }
		   
		   if(userRoles.getUser().getLastName() != null){
			   writeRow.createCell(8).setCellValue(userRoles.getUser().getLastName());
		   }
		 
		   com.bis.sourcelead.entity.Candidate candidate = candidateService.getCandidateByConsultantUserId(userRoles.getUser().getId(), orgLocations.getId());
		    
		   
		   if(candidate !=null) {
			   if(candidate.getWorkLocation() != null) {
			    	writeRow.createCell(9).setCellValue(candidate.getWorkLocation().getLocation());
			    }
			   }
		   
		   if(candidate !=null) {
		   if(candidate.getResidenceAddress() != null) {
		    	writeRow.createCell(10).setCellValue(candidate.getResidenceAddress());
		    }
		   }
		   
		  
			
			 if(userRoles.getUser().getMobilePhone() != null){
			   writeRow.createCell(11).setCellValue(userRoles.getUser().getMobilePhone());
		   }
			 if(userRoles.getUser().getExtensionNumber() != null){
				   writeRow.createCell(12).setCellValue(userRoles.getUser().getExtensionNumber());
			   }
		   
		    if(userRoles.getUser().getGender() != null){
		    	writeRow.createCell(13).setCellValue(userRoles.getUser().getGender());
		    }
			
			if(userRoles.getUser().getMaritalStatus() != null) {
		    	writeRow.createCell(14).setCellValue(userRoles.getUser().getMaritalStatus());
		    }
			
			if(userRoles.getUser().getDateOfBirth() != null) {
				 String dateOfBirth = commonUtil.dateStrFromOrgDateFormatter(userRoles.getOrgLocations().getDateFormat(),userRoles.getUser().getDateOfBirth());
				 writeRow.createCell(15).setCellValue(dateOfBirth); 
			 }
			
			if(employee.getDepartmentId() != null) {
		    	writeRow.createCell(16).setCellValue(employee.getDepartmentId().getDepartmentName());
		    }
			
			if(employee.getUserEmploymentType() != null) {
		    	writeRow.createCell(17).setCellValue(employee.getUserEmploymentType());
		    }
			
			if(userRoles.getRoles() != null){
			   writeRow.createCell(18).setCellValue(userRoles.getRoles().getDisplayName());
		   }
		   
		   if(userRoles.getDesignation() != null) {
		    	writeRow.createCell(19).setCellValue(userRoles.getDesignation());
		    }
			
			if(userRoles.getGradeId() != null) {
		  writeRow.createCell(20).setCellValue(userRoles.getGradeId().getGradeName());
		  }
		  
		   if(userRoles.getReportingManager() !=null){
			   StringBuilder reportingManager = new StringBuilder();
			   if(userRoles.getReportingManager().getLastName() != null && !userRoles.getReportingManager().getLastName().isEmpty()) {
				   reportingManager = reportingManager.append(userRoles.getReportingManager().getFirstName()).append(" ").append(userRoles.getReportingManager().getLastName());
			   }else {
				   reportingManager = reportingManager.append(userRoles.getReportingManager().getFirstName());
			   }
			   writeRow.createCell(21).setCellValue(reportingManager.toString());
		   }
		   
		   if(userRoles.getResourceManager() !=null){
			   StringBuilder resourceManager = new StringBuilder();
			   if(userRoles.getResourceManager().getLastName() != null && !userRoles.getResourceManager().getLastName().isEmpty()) {
				   resourceManager = resourceManager.append(userRoles.getReportingManager().getFirstName()).append(" ").append(userRoles.getResourceManager().getLastName());
			   }else {
				   resourceManager = resourceManager.append(userRoles.getResourceManager().getFirstName());
			   }
			   writeRow.createCell(22).setCellValue(resourceManager.toString());
		   }
		   
		    if(userRoles.getShift() != null) {
		    	writeRow.createCell(23).setCellValue(userRoles.getShift().getShiftName());
		    }
			
			if(employee.getRate() !=null && !"".equalsIgnoreCase(employee.getRate())){
				 StringBuilder sb = new StringBuilder();
				 writeRow.createCell(24).setCellValue(sb.append(employee.getRate()).append("/").append(employee.getRatePer()).toString());
			 }
			 
			 if(employee.getDailyHours() != null) {
		    	writeRow.createCell(25).setCellValue(employee.getDailyHours());
		    }
			 
			 if(userRoles.getIsGlobal() != null) {
				 writeRow.createCell(26).setCellValue(userRoles.getIsGlobal());
			 }
			 
			 if(userRoles.getIsMailSent() != null) {
				 writeRow.createCell(27).setCellValue(userRoles.getIsMailSent().toString());;
			 }
			 
			 if(userRoles.getUser().getAllowEmployeeToFill() != null) {
				 writeRow.createCell(28).setCellValue(userRoles.getUser().getAllowEmployeeToFill().toString());
			 }
			

		    if(orgLocations.getCountry().equalsIgnoreCase(Constants.COUNTRY_INDIA)) {
			if(candidate !=null) { 
			 if(candidate.getPanNumber() != null) {
				 writeRow.createCell(29).setCellValue(candidate.getPanNumber());
			 }
			if(candidate.getAadharNumber() !=null){
				 writeRow.createCell(30).setCellValue(candidate.getAadharNumber()); 
			 }
			
			}
		    }else {
		    	if(candidate !=null) {
		    		if(candidate.getSsnNumber() != null)
		    	 writeRow.createCell(29).setCellValue(candidate.getSsnNumber()); 
		    	}
		    }
		    
		    return createExcelsheet;
		
	}

	@Override
	public File getPdfFile(List<UserRole> activeList,
			List<UserRole> inactiveList, List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,
			String userState,OrgLocations orgLocations) throws Exception {
		return pdfUtil.createPdfTable(activeList, inactiveList, notLoggedInList, userRolesList, userState,orgLocations);

	}	


	@Override
	public UserRole getUserRoleByUserId(Long userId) throws Exception {
		return userRoleDao.getUserRoleByUserId(userId);
	}

	@Override
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency,Long locationId) throws Exception {
		return userRoleDao.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(invoiceFrequency,locationId);
	}
	
	@Override
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(String invoiceFrequency, Long approverUserId,Long locationId) throws Exception {
		return userRoleDao.getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(invoiceFrequency,approverUserId,locationId);
	}

	@Override
	public HashSet<UserRole> getAllUserTimeSheetSubmissionStatusCurrentWeek() {
		return userRoleDao.getAllUserTimeSheetSubmissionStatusCurrentWeek();
	}

	@Override
	public UserRole getUserDetailsByUserIdAndLocationId(Long userId, Long locationId) throws Exception {
		return userRoleDao.getUserDetailsByUserIdAndLocationId(userId, locationId);
	}

	@Override
	public HashSet<ProjectMembers> getAllUserPrevoiusFridayTimeSheetSubmissionStatus(String invoiceFrequency,String dateFormat) throws Exception {
		return userRoleDao.getAllUserPrevoiusFridayTimeSheetSubmissionStatus(invoiceFrequency,dateFormat);
	}

	@Override
	public List<Long> getAllAssignedUsersIdsByReportingManagerId(Long reportingManagerId,Long orgLocationId) {
		return userRoleDao.getAllAssignedUsersIdsByReportingManagerId(reportingManagerId,orgLocationId);
	}

	@Override
	public List<UserRole> getAllAssignedUsersByReportingManagerId(Long reportingManagerId, Long orgLocationId) {
		return userRoleDao.getAllAssignedUsersByReportingManagerId(reportingManagerId, orgLocationId);
	}

	@Override
	public UserRole getChangeRolePermissions(List<Long> userRoletabIds, Long userId, Long orgLocationId) throws Exception {
		
		UserRole userRole = new UserRole();
		if(userRoletabIds.size() > 0){
		List<Tabs> tabsList = tabsDao.getTabsListByIds(userRoletabIds);
		if(tabsList != null){
			for(Tabs tabs : tabsList){
				if(tabs.getTabName().equalsIgnoreCase(Constants.Users)){
					
					/*To change reporting manager*/
					 List<Long> userIds = userRoleDao.getAllAssignedUsersIdsByReportingManagerId(userId,orgLocationId);
					 userRole.setAssignedUsersCountToReportingManager(userIds.size());
					 List<User> reportManagerUsersList = new ArrayList<User>();
					 if(userIds.size() > 0){
						 userIds.add(userId);
						 List<User> managerUsersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndUsersIds(orgLocationId, userIds, Long.valueOf(Constants.TAB_USER));
						 for(User user :managerUsersList){
							 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(),orgLocationId);
							 if(role != null){
								 user.setUserType(role.getRoles().getDisplayName());
								 reportManagerUsersList.add(user);
							 }
						 }
					 }
					 
					 userRole.setReportingManagers(reportManagerUsersList);
				}
				
				else if(tabs.getTabName().equalsIgnoreCase(Constants.Clients)){

					 /*To change vendor owner and spoc*/
					 List<Long> vendorSpocIds = orgSpocDao.getAssignedAsClientOrVendorSpocCountByUserId(userId, orgLocationId,"Vendor");
					 userRole.setAssignedAsVendorOwnerOrSpocCount(vendorSpocIds.size());
					 List<User> vendorTabUsersList = new ArrayList<User>();;
					 if(vendorSpocIds.size() > 0){
						 List<User> vendorUsersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocationId, userId, Long.valueOf(Constants.TAB_VENDOR));
						 for(User user :vendorUsersList){
							 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
							 if(role != null){
								 user.setUserType(role.getRoles().getDisplayName());
								 vendorTabUsersList.add(user);
							 }
						 }
					 }
					 userRole.setVendorUsersList(vendorTabUsersList);
					 
					 
					Integer clientDocumentsOwnerCount = orgDocumentsDao.getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(userId, orgLocationId, "Client");
					userRole.setClientDocumentsOwnerCount(clientDocumentsOwnerCount);
					List<User> clientDocumentsOwnerList = new ArrayList<User>();
					if (clientDocumentsOwnerCount > 0) {
						List<User> documentsOwnersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
								orgLocationId, userId, Long.valueOf(Constants.TAB_CLIENT));
						for (User user : documentsOwnersList) {
							UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
							if (role != null) {
								user.setUserType(role.getRoles().getDisplayName());
								clientDocumentsOwnerList.add(user);
							}
						}
					}
					userRole.setClientDocumentsOwnerList(clientDocumentsOwnerList);
				   }
				
				
				else if(tabs.getTabName().equalsIgnoreCase(Constants.Vendors)){
					
					
					 /*To change client owner and spoc*/
					 List<Long> cleintSpocIds = orgSpocDao.getAssignedAsClientOrVendorSpocCountByUserId(userId, orgLocationId,"Client");
					 userRole.setAssignedAsClientOwnerOrSpocCount(cleintSpocIds.size());
					 List<User> clientTabUsersList = new ArrayList<User>();
					 if(cleintSpocIds.size() > 0){
						 List<User> clientUsersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocationId, userId, Long.valueOf(Constants.TAB_CLIENT));
						 for(User user :clientUsersList){
							 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
							 if(role != null){
								 user.setUserType(role.getRoles().getDisplayName());
								 clientTabUsersList.add(user);
							 }
						 }
					 }
					 userRole.setClientUsersList(clientTabUsersList);
					 
					 
					 Integer vendorDocumentsOwnerCount = orgDocumentsDao.getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(userId, orgLocationId, "Vendor");
						userRole.setVendorDocumentsOwnerCount(vendorDocumentsOwnerCount);
						List<User> vendorDocumentsOwnerList = new ArrayList<User>();
						if (vendorDocumentsOwnerCount > 0) {
							List<User> documentsOwnersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(
									orgLocationId, userId, Long.valueOf(Constants.TAB_VENDOR));
							for (User user : documentsOwnersList) {
								 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
								if (role != null) {
									user.setUserType(role.getRoles().getDisplayName());
									vendorDocumentsOwnerList.add(user);
								}
							}
						}
						userRole.setVendorDocumentsOwnerList(vendorDocumentsOwnerList);
				}
				
				
				else if(tabs.getTabName().equalsIgnoreCase(Constants.JOBS)){
					
					 /*To change requirement Manager*/
					 Integer requirementsCountAsManager = requirementDao.getManagerAssignedRequirementsCount(userId, orgLocationId);
					 userRole.setRequirementsCountAsManager(requirementsCountAsManager);
					 List<User> requirementManagersList = new ArrayList<User>();
					 if(requirementsCountAsManager > 0){
						 List<User> requirementManagersUsersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocationId, userId, Long.valueOf(Constants.TAB_REQUIREMENT));
						 for(User user :requirementManagersUsersList){
							 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
							 if(role != null){
								 user.setUserType(role.getRoles().getDisplayName());
								 requirementManagersList.add(user);
							 }
						 }
					 }
					 userRole.setRequirementManagersList(requirementManagersList);
					 
					 Integer requirementsCountAsApprover = requirementDao.getRequirementsCountAsApprover(userId, orgLocationId);
					 userRole.setRequirementsCountAsApprover(requirementsCountAsApprover);
					 List<User> requirementApproversList = new ArrayList<User>();
					 if(requirementsCountAsApprover > 0){
						 List<User> requirementApproversUsersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocationId, userId, Long.valueOf(Constants.TAB_REQUIREMENT));
						 for(User user :requirementApproversUsersList){
							 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
							 if(role != null){
								 user.setUserType(role.getRoles().getDisplayName());
								 requirementApproversList.add(user);
							 }
						 }
					 }
					 userRole.setRequirementApproversList(requirementApproversList);
					 
					 Integer teamsCountAsOwner = teamDao.getTeamCountByTeamOwner(userId, orgLocationId);
					 userRole.setTeamsCountAsOwner(teamsCountAsOwner);
					 List<User> teamOwnersList = new ArrayList<User>();
					 if(teamsCountAsOwner > 0){
						 List<User> teamUsersList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocationId, userId, Long.valueOf(Constants.TAB_REQUIREMENT));
						 for(User user :teamUsersList){
							 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
							 if(role != null){
								 user.setUserType(role.getRoles().getDisplayName());
								 teamOwnersList.add(user);
							 }
						 }
					 }
					 userRole.setTeamOwnersList(teamOwnersList);
				}
				
				else if(tabs.getTabName().equalsIgnoreCase(Constants.TimeSheet_Approval)){
					
					 Integer projectApproverCount = projectsDao.getProjectApproversCountByUserIdAndOrgLocationId(userId, orgLocationId);
					 userRole.setProjectApproverCount(projectApproverCount);
					 List<User> approversOwnersList = new ArrayList<User>();
					 if(projectApproverCount > 0){
						 List<User> projectApproversList = userDao.getTabAccessUsersByOrgLocationIdAndTabIdAndNotInUsersId(orgLocationId, userId, Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
						 for(User user :projectApproversList){
							 UserRole role = userRoleDao.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
							 if(role != null){
								 user.setUserType(role.getRoles().getDisplayName());
								 approversOwnersList.add(user);
							 }
						 }
					 }
					 userRole.setProjectApproversList(approversOwnersList);
				}
			}
		}
	}
		return userRole;
	}

	@Override
	public List<UserRole> getUsersListByUserId(Long userId) throws Exception {
		return userRoleDao.getUsersListByUserId(userId);
	}

	@Override
	public Boolean isUserAdminByOrgLocationId(Long userId, Long orgLocationId) {
		return userRoleDao.isUserAdminByOrgLocationId(userId,orgLocationId);
	}

	@Override
	public List<UserRole> getAllActiveUsersListByOrgLocationId(Long orgLocationId) throws Exception {
		return userRoleDao.getAllActiveUsersListByOrgLocationId(orgLocationId);
	}
	
	@Override
	public UserRole getUserRoleById(Long userRoleId) throws Exception {
		return userRoleDao.getUserRoleById(userRoleId);
	}

	@Override
	public Boolean isUserisPrimaryForOrg(Long userId, Long orgLocationId) {
		return userRoleDao.isUserisPrimaryForOrg(userId, orgLocationId);
	}

	@Override
	public UserRole getUserDetailsByUserIdAndLocationIdAndUserType(Long userId, Long locationId, String userType) throws Exception
	{
		return userRoleDao.getUserDetailsByUserIdAndLocationIdAndUserType(userId, locationId, userType);
	}

	@Override
	public UserRole getIsProfessionalOrRecruiterExistsWithEmailAndUserType(String email, String userType) throws Exception
	{
		return userRoleDao.getIsProfessionalOrRecruiterExistsWithEmailAndUserType(email, userType);
	}

	@Override
	public UserRole getUserRoleWithoutAdminsByUserIdAndLocationId(Long userId, Long locationId) throws Exception {
		return userRoleDao.getUserRoleWithoutAdminsByUserIdAndLocationId(userId, locationId);
	}
	
	@Override
	public UserRole getUserRoleByVerficationUUID(String uuid)
	{
		return userRoleDao.getUserRoleByVerficationUUID(uuid);
	}


	@Override
	public List<ConnectionsDTO> getUserListForConnections(String searchByName,Integer offSet,Long userId) throws Exception {
		return userRoleDao.getUserListForConnections(searchByName,offSet,userId);
	}

	@Override
	public HashSet<ProjectMembers> getAllProjectMembersByProjectIdForMissingTSReports(Long projectId, Long locationId,Long userId)
			throws Exception {
		return userRoleDao.getAllProjectMembersByProjectIdForMissingTSReports(projectId, locationId,userId);
	}

	@Override
	public ResponseList<UsersUsageReportDTO> getUserRoleListByLocationIdAndUserType(UsersUsageReportDTO usersUsageReportDTO,OrgLocations orgLocation) throws Exception
	{
		Date[] freqDates = new Date[2];
		if("Custom".equalsIgnoreCase(usersUsageReportDTO.getCustomDates())){
			freqDates[0] = commonUtil.getDateFormat(usersUsageReportDTO.getFromDate());
			freqDates[1] = commonUtil.getDateFormat(usersUsageReportDTO.getToDate());
		}else{
			freqDates = reportsUtil.getJobFilterDate(usersUsageReportDTO.getCustomDates(),orgLocation);
		}
		if(!"NOT_LOGGEDIN".equalsIgnoreCase(usersUsageReportDTO.getUserType())){
		List<Long> userIds = actionHistoryService.getAllUserIdsByFrequencyBasedOnActivity(freqDates);
		usersUsageReportDTO.setUserIds(userIds);
		}
		ResponseList<UserRole> userlist = userRoleDao.getUserRoleListByLocationIdAndUserType(usersUsageReportDTO,orgLocation);
		ResponseList<UsersUsageReportDTO> responseList = new ResponseList<>();
		List<UsersUsageReportDTO> userUsageDtoList = new ArrayList<>();
		if(userlist.getData() != null){
			for(UserRole userRole : userlist.getData()){
				
				User user = userRole.getUser();
				Employees employee = employeesService.getEmployeeByUserId(user.getId());
				StringBuilder sb = new StringBuilder();
				if(employee != null){
					if(StringUtils.isNotBlank(employee.getEmployeeId())){
						sb.append(employee.getEmployeeId());
					}
				}
				
				UsersUsageReportDTO usageDto = new UsersUsageReportDTO();
				usageDto.setUserRoleId(userRole.getId());
				usageDto.setLocationId(userRole.getOrgLocations().getId());
				usageDto.setUserId(userRole.getUser().getId());
				if(StringUtils.isNotBlank(user.getLastName())){
					usageDto.setUserName(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}else{
					usageDto.setUserName(sb.append(user.getFirstName()).toString());
				}
				
				usageDto.setUserEmail(user.getEmail());
				usageDto.setUserType(user.getUserType());
				
				List<UserRole> userRoleList =  userRoleDao.getUsersListByUserId(user.getId());
				for(UserRole ur:userRoleList){
					ur.setOrgName(ur.getOrgLocations().getOrgLocationName());
				}
				usageDto.setEnrolledOrgs(userRoleList);
				usageDto.setCreatedOn(commonUtil.dateStrFromDate(user.getCreatedDate()));
				
//				UserDTO actionHistory = actionHistoryService.getAllActionHistoryByUserId(user.getId());
//				usageDto.setNoOfActivities(actionHistory.getNoOfActivities());
				
//				if(StringUtils.isNotBlank(actionHistory.getLastActivity())){
					usageDto.setLastActivity("---");
//				}
				
				List<ActionHistory>  actionHis = actionHistoryService.getAllMultipleIpsByUserId(user.getId());
				usageDto.setMultipleIps(actionHis.size());
				
				
				userUsageDtoList.add(usageDto);
			}
			responseList.setRecordsFiltered(userlist.getRecordsFiltered());
			responseList.setRecordsTotal(userlist.getRecordsTotal());
		}
		
		responseList.setData(userUsageDtoList);
		return responseList;
	}

	@Override
	public Map<String, Integer> getUserCountListByOrgLocationId(Long orgLocationId,List<Long> orgId)
	{
		return userRoleDao.getUserCountListByOrgLocationId(orgLocationId, orgId);
	}

	public List<UserRole> getAllUsersBasedOrgLocationId(Long orgLocationId) throws Exception {
		return userRoleDao.getAllUsersBasedOrgLocationId(orgLocationId);
	}
	@Override
	public ResponseList<UserRole> getDynamicUsersListByRoleAndNameAndLocationId(UserDTO userDTO,
			UserRoleDTO userRoleDTO, Long locationId, Long userId, String loginUserRole, String userCode,List<Long> orgRelationsList,List<Long> managerUserIds,List<Long> teamsList,List<Long> departmentsList)
			throws Exception {
		return userRoleDao.getDynamicUsersListByRoleAndNameAndLocationId(userDTO, userRoleDTO, locationId, userId, loginUserRole, userCode,orgRelationsList,managerUserIds,teamsList,departmentsList);
	}

	@Override
	public List<User> getAllReportingManagersAssignedToAssociates(Long orgLocationId) throws Exception {
		return userRoleDao.getAllReportingManagersAssignedToAssociates(orgLocationId);
	}

	@Override
	public List<UsersUsageReportDTO> getUsageReportUserCountListByOrgLocationId(Long orgLocationId, UsersUsageReportDTO usersUsageReportDTO, OrgLocations orgLocations) throws Exception
	{
		int Year = 0;
		int diffMonth = 11;
		Date[] freqDates = new Date[2];
		int month = 0;
		List<UsersUsageReportDTO> user = new ArrayList<>();
		if ("Custom".equalsIgnoreCase(usersUsageReportDTO.getCustomDates())) {
			freqDates[0] = commonUtil.getDateFormat(usersUsageReportDTO.getFromDate());
			freqDates[1] = commonUtil.getDateFormat(usersUsageReportDTO.getToDate());
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(freqDates[0]);
			month = startCalendar.get(Calendar.MONTH);
			Year = startCalendar.get(Calendar.YEAR);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(freqDates[1]);
			// int endMonth = endCalendar.get(Calendar.MONTH);
			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH)- startCalendar.get(Calendar.MONTH);
		}else{
			
			if (usersUsageReportDTO.getCustomDates().equalsIgnoreCase(Constants.REPORT_FILTER_CURRENT_YEAR)) {
				Year = Calendar.getInstance().get(Calendar.YEAR);
			}else if (usersUsageReportDTO.getCustomDates().equalsIgnoreCase(Constants.REPORT_FILTER_LAST_YEAR)) {
				reportsUtil.getJobFilterDate(usersUsageReportDTO.getCustomDates(), orgLocations);
				Calendar prevYear = Calendar.getInstance();
				prevYear.add(Calendar.YEAR, -1);
				Year = prevYear.get(Calendar.YEAR);
			}
		}
		
		
		for (int i = 0; i <= diffMonth; i++) {
			UsersUsageReportDTO userDto = new UsersUsageReportDTO();
			freqDates = commonUtil.getMonthStartDateEndDateByYearAndMonth(Year, month);
			Map<String, Integer> map = userRoleDao.getUsageReportUserCountListByOrgLocationId(orgLocationId, usersUsageReportDTO, orgLocations, freqDates);
			userDto.setActiveCount(map.get("ACTIVE"));
			userDto.setInactiveCount(map.get("INACTIVE"));
			userDto.setNotLoggedinCount(map.get("NOT_LOGGEDIN"));
			StringBuilder sb = new StringBuilder();
			userDto.setDates(sb.append(Year).append("-").append(month+1).toString());
			user.add(userDto);
			month += 1;
		}
		
		
		return user;
	}
	
    @Override
	public List<User> getAllProjectManagersAssignedToProjects(Long orgLocationId) throws Exception {
		return userRoleDao.getAllProjectManagersAssignedToProjects(orgLocationId);

	}

	@Override
	public List<UserRole> getAllUsersBasedOrgLocationIdAndActiveAndNotLogin(Long orgLocationId) throws Exception {
		return userRoleDao.getAllUsersBasedOrgLocationIdAndActiveAndNotLogin(orgLocationId);
	}

	@Override
	public List<AssociateDepartmentView> getUserRolesByDepartmentId(String departmentId,Long orgLocationId)
	{
		List<UserIdView> userIdView = userGroupDao.getCommaSeperatedUserId(orgLocationId);
		List<AssociateDepartmentView> associateDepartmentViewList = userRoleDao.getUserRolesByDepartmentId(departmentId,orgLocationId,userIdView);
		return associateDepartmentViewList;
	}

	@Override
	public List<UserRole> getUserAccountsListByUserId(List<Long> userId) throws Exception {
		return userRoleDao.getUserAccountsListByUserId(userId);
	}

	
	@Override
	public List<Long> getAllAssignedUsersIdsByResourceManagerId(Long resourceManagerId, Long orgLocationId) {
		return userRoleDao.getAllAssignedUsersIdsByResourceManagerId(resourceManagerId, orgLocationId);
	}

	@Override
	public List<UserRole> getAllAssignedUsersByResourceManagerId(Long resourceManagerId, Long orgLocationId) {
		return userRoleDao.getAllAssignedUsersByResourceManagerId(resourceManagerId, orgLocationId);
	}

	@Override
	public List<BigInteger> getAllUsersRoleList() throws Exception {
		return userRoleDao.getAllUsersRoleList();
	}

	@Override
	public List<UserDTO> getAllReportingManagersListAssignedToAssociates(Long orgLocationId) throws Exception {
		return userRoleDao.getAllReportingManagersListAssignedToAssociates(orgLocationId);
	}

	@Override
	public UserRole getGlobalUserReportingManagerByUserIdAndOrgId(Long userId, Long orgId)
	{
		return userRoleDao.getGlobalUserReportingManagerByUserIdAndOrgId(userId,orgId);
	}
	
	@Override
	public Boolean getIsGlobalManager(Long orgId,Long userId) throws Exception {
		return userRoleDao.getIsGlobalManager(orgId,userId);
	} 
    
	@Override
	public List<UserRole> getUserListByUserIdList(List<Long> userIdList,Long orgLocationId)
	{
		return userRoleDao.getUserListByUserIdList(userIdList,orgLocationId);
	}

	@Override
	public Boolean isUserWithMultipleOrgsAccounts(Long orgId,Long userId) {
		return userRoleDao.isUserWithMultipleOrgsAccounts(orgId,userId);
	}

	@Override
	public List<UserRole> getLoginUserAccountsList(Long userId) throws Exception {
		return userRoleDao.getLoginUserAccountsList(userId);
	}

	@Override
	public UserRole getProjectmemberUserDetailsByUserIdAndLocationId(Long userId, Long locationId, String designationType)
			throws Exception {
		return userRoleDao.getProjectmemberUserDetailsByUserIdAndLocationId(userId,locationId,designationType);
	}

	@Override
	public List<UserRole> getAllUserRoles() throws Exception {
		return userRoleDao.getAllUserRoles();
	}

	@Override
	public String getUserRoleUUIDByUserRoleId(Long userRoleId) throws Exception {
		return userRoleDao.getUserRoleUUIDByUserRoleId(userRoleId);
	}

	@Override
	public List<UserRole> getAllUserRolesByRoleId(Long roleId) throws Exception {
		return userRoleDao.getAllUserRolesByRoleId(roleId);
	}

	@Override
	public List<UserRole> getLoginUserLoginAccountsList(Long userId) throws Exception {
		return userRoleDao.getLoginUserLoginAccountsList(userId);
	}

	@Override
	public List<UserDTO> getAllActiveUsersList(Long orgLocationId) throws Exception {
		return userRoleDao.getAllActiveUsersList(orgLocationId);
	}


}
