package com.bis.sourcelead.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	private PdfUtil pdfUtil;
	
	@Autowired
	private CommonUtil commonUtil;

	@Override
	public UserRole addUserRole(UserRole userRole) throws Exception {
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
			Long locationId,String userState,Long userId,String loginUserRole,String userCode) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndNameAndLocationId(roleId, userName, locationId,userState,userId,loginUserRole,userCode);
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
	
	public Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, List<UserRole> activeList,
			List<UserRole> inactiveList, List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,String userState)
			throws Exception {
		Row writeRow = null;
        createExcelsheet = getExcelSheetWithHeader(createExcelsheet,writeRow,createExcelworkbook,activeList,inactiveList,notLoggedInList,userState,userRolesList);
        
        int column=0;
        for(UserRole userRoles : userRolesList){
       	 writeRow = createExcelsheet.createRow(column+8);
       	 createExcelsheet = createXlsSheet(createExcelsheet,writeRow,column,userRoles);
       	 column++;
        }
		return createExcelsheet;
	}
	private Sheet getExcelSheetWithHeader(Sheet createExcelsheet, Row writeRow, Workbook createExcelworkbook,
			List<UserRole> activeList, List<UserRole> inactiveList, List<UserRole> notLoggedInList, String userState, ArrayList<UserRole> userRolesList) {

		CellStyle style = createExcelworkbook.createCellStyle();
	     style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	     style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	     Font font = createExcelworkbook.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
         style.setFont(font);
		 
		 writeRow = createExcelsheet.createRow(1);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell1 = writeRow.createCell(0);
		 cell1.setCellValue("Active :");
		 cell1.setCellStyle(style);
		 
		 Cell cell2 = writeRow.createCell(1);
		
			 if("ACTIVE".equalsIgnoreCase(userState)){
				 cell2.setCellValue(userRolesList.size());
			 }else{
				 if(activeList !=null){
				 cell2.setCellValue(activeList.size());
				 }else{
					 cell2.setCellValue("--");
				 }
			 }
		 cell2.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(2);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell3 = writeRow.createCell(0);
		 cell3.setCellValue("Inactive :");
		 cell3.setCellStyle(style);
		 
		 Cell cell4 = writeRow.createCell(1);
		
			 if("INACTIVE".equalsIgnoreCase(userState)){
				 cell4.setCellValue(userRolesList.size()); 
			 }else{
				 if(inactiveList !=null){
				 cell4.setCellValue(inactiveList.size()); 
				 }else{
					 cell4.setCellValue("--");
				 }
			 }
		 cell4.setCellStyle(style);
		 
		 writeRow = createExcelsheet.createRow(3);
		 createExcelsheet.setColumnWidth(0, 10000);
		 createExcelsheet.setColumnWidth(1, 10000);
		 
		 Cell cell5 = writeRow.createCell(0);
		 cell5.setCellValue("Not Loggedin : ");
		 cell5.setCellStyle(style);
		 
		 Cell cell6 = writeRow.createCell(1);
		
			 if("NOT_LOGGEDIN".equalsIgnoreCase(userState)){
				 cell6.setCellValue(userRolesList.size()); 
			 }else{
				 if(notLoggedInList != null){
				 cell6.setCellValue(notLoggedInList.size());
				 }else{
					 cell6.setCellValue("-"); 
				 }
			 }
		
		 cell6.setCellStyle(style);
		 String headers [] = Constants.USER_EXCEL_HEADERS;
		return createExcelsheet = commonUtil.setExcelHeaders(createExcelsheet,style,headers,7);
	}

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column, UserRole userRoles) {
		 Set<Employees> employeeSet = userRoles.getUser().getEmployee();
		 for(Employees emp : employeeSet){
			 if(emp.getEmployeeId() !=null){
				 writeRow.createCell(0).setCellValue(emp.getEmployeeId()); 
			 }
			 if(emp.getHireDate() !=null){
				 writeRow.createCell(5).setCellValue(emp.getHireDateStr()); 
			 }
			 if(emp.getRate() !=null && !"".equalsIgnoreCase(emp.getRate())){
				 writeRow.createCell(6).setCellValue(emp.getRate()+"/"+emp.getRatePer());
			 }
  	    } 
		   if(userRoles.getUser().getFirstName() !=null){
			   writeRow.createCell(1).setCellValue(userRoles.getUser().getFirstName());
		   }
		   if(userRoles.getUser().getLastName() != null){
			   writeRow.createCell(2).setCellValue(userRoles.getUser().getLastName());
		   }
		   if(userRoles.getUser().getEmail() !=null){
			   writeRow.createCell(3).setCellValue(userRoles.getUser().getEmail());
		   }
		   if(userRoles.getUser().getMobilePhone() != null){
			   writeRow.createCell(4).setCellValue(userRoles.getUser().getMobilePhone());
		   }
		   if(userRoles.getUser().getUserType() != null){
			   writeRow.createCell(8).setCellValue(userRoles.getUserType());
		   }
		   if(userRoles.getUser().getLocation() !=null){
			   writeRow.createCell(7).setCellValue(userRoles.getUser().getLocation());
		   }
		   if(userRoles.getReportingManager() !=null){
			   writeRow.createCell(9).setCellValue(userRoles.getReportingManager().getFirstName());
		   }
		   if(userRoles.getUser().getStatus() !=null){
			   writeRow.createCell(10).setCellValue(userRoles.getUserState().toString());
		   }
		return createExcelsheet;
		
	}

	@Override
	public File getPdfFile(List<UserRole> activeList,
			List<UserRole> inactiveList, List<UserRole> notLoggedInList, ArrayList<UserRole> userRolesList,
			String userState,String orgName) throws Exception {
		return pdfUtil.createPdfTable(activeList, inactiveList, notLoggedInList, userRolesList, userState,orgName);

	}	


	@Override
	public UserRole getUserRoleByUserId(Long userId) throws Exception {
		return userRoleDao.getUserRoleByUserId(userId);
	}

	@Override
	public HashSet<UserRole> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception {
		return userRoleDao.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(invoiceFrequency);
	}

	@Override
	public HashSet<UserRole> getAllUserTimeSheetSubmissionStatusCurrentWeek() {
		return userRoleDao.getAllUserTimeSheetSubmissionStatusCurrentWeek();
	}
	

}
