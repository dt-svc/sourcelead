package com.bis.sourcelead.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.RequirementDao;
import com.bis.sourcelead.dao.TabsDao;
import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dao.UserRoleDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.PdfUtil;
import com.bis.sourcelead.web.LoginSessionController;

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
			Long locationId,String userState,Long userId,String loginUserRole,String userCode,Integer offset) throws Exception {
		logger.debug("In UserRoleService class...");
		return userRoleDao.getUsersListByRoleAndNameAndLocationId(roleId, userName, locationId,userState,userId,loginUserRole,userCode,offset);
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

	private Sheet createXlsSheet(Sheet createExcelsheet, Row writeRow, int column, UserRole userRoles) throws Exception {
//		 Set<Employees> employeeSet = userRoles.getUser().getEmployee();
		 Employees empl = employeesService.getEmployeeByUserId(userRoles.getUser().getId());
			 if(empl.getEmployeeId() !=null){
				 writeRow.createCell(0).setCellValue(empl.getEmployeeId()); 
			 }
			 if(empl.getHireDate() !=null){
				 String dateOfJoin = LoginSessionController.DATEFORMAT.format(empl.getHireDate());
				 writeRow.createCell(5).setCellValue(dateOfJoin); 
			 }
			 if(empl.getRate() !=null && !"".equalsIgnoreCase(empl.getRate())){
				 StringBuilder sb = new StringBuilder();
				 writeRow.createCell(6).setCellValue(sb.append(empl.getRate()).append("/").append(empl.getRatePer()).toString());
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
		   if(userRoles.getRoles() != null){
			   writeRow.createCell(8).setCellValue(userRoles.getRoles().getDisplayName());
		   }
		   if(userRoles.getUser().getLocation() !=null){
			   writeRow.createCell(7).setCellValue(userRoles.getUser().getLocation());
		   }
		   if(userRoles.getReportingManager() !=null){
			   writeRow.createCell(9).setCellValue(userRoles.getReportingManager().getFirstName());
		   }
		   
		   if(userRoles.getUser().getIsMailSent() != null && !"".equalsIgnoreCase(userRoles.getUser().getIsMailSent().toString()) && "N".equalsIgnoreCase(userRoles.getUser().getIsMailSent().toString()) && !"INACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
			   writeRow.createCell(10).setCellValue("Invite");
	   		   }else if((userRoles.getUser().getIsMailSent() != null && !"".equalsIgnoreCase(userRoles.getUser().getIsMailSent().toString()) && "Y".equalsIgnoreCase(userRoles.getUser().getIsMailSent().toString())) &&  (userRoles.getUserState() !=null && "NOT_LOGGEDIN".equalsIgnoreCase(userRoles.getUserState().toString()))){
	   			writeRow.createCell(10).setCellValue("Resend");
	   		   }else if(userRoles.getUserState() !=null && "ACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
	   			writeRow.createCell(10).setCellValue("Active");
	   		   }else if(userRoles.getUserState() !=null && "INACTIVE".equalsIgnoreCase(userRoles.getUserState().toString())){
	   			writeRow.createCell(10).setCellValue("In-active");
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
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception {
		return userRoleDao.getAllUserPrevoiusWeekTimeSheetSubmissionStatus(invoiceFrequency);
	}
	
	@Override
	public HashSet<ProjectMembers> getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(String invoiceFrequency, Long approverUserId) throws Exception {
		return userRoleDao.getAllUserPrevoiusWeekTimeSheetSubmissionStatusByApproverUserId(invoiceFrequency,approverUserId);
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
	public HashSet<ProjectMembers> getAllUserPrevoiusFridayTimeSheetSubmissionStatus(String invoiceFrequency) throws Exception {
		return userRoleDao.getAllUserPrevoiusFridayTimeSheetSubmissionStatus(invoiceFrequency);
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

}
