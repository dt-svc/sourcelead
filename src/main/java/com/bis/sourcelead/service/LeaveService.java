package com.bis.sourcelead.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.DayWiseLeaveReportDTO;
import com.bis.sourcelead.dto.HrLeaveReportDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.LeaveReportsDTO;
import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.MonthlyLeaves;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;


public interface LeaveService {

	Leave saveLeave(LeaveDTO leaveDTO, OrgLocations orgLocations, User user) throws Exception;

	ResponseList<LeaveDTO> getEmployeeRequestedLeavesBylocationId(LeaveDTO leaveDTO, OrgLocations orgLocations)throws Exception;

	Leave getLeaveByLeavId(Long leaveId) throws Exception;

	boolean approveRequestLeave(OrgLocations orgLocations, User user, String leaveUUID, Long leaveTypeId,String approveStatus, int numberOfDays, String reason) throws Exception;

	ResponseList<LeaveDTO> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId) throws Exception;

	List<LeaveReportsDTO> getEmployeeLeavesByApproverIdAndStatus(OrgLocations orgLocations, String userId, String status, String department, String leaveDate) throws Exception;
	
	List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeUUID, Long orgLocationId, Long userId);

	ResponseList<Leave> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception;


	LeaveDTO getAllEmployeesLeavesCounts(Long orgLocationId,Long userId, String loginUserRole, Long orgId);

	LeaveDTO getAllSelfLeavesCounts(Long orgLocationId, Long userId) throws Exception;

	List<LeaveDTO> getLeaveTakersBarChat(OrgLocations orgLocation, LeaveDTO leaveDTO)throws Exception;

	void sendLeaveEmailToApprovers(User loginUser, LeaveDTO leaveDTO, OrgLocations orgLoc, User approverUser,Leave savedLeave) throws Exception;
	
	void LeaveCancelEmailToUser(User loginUser, LeaveManagementHistory leaveManagementHistory,String reason, Long redirectOrgLocationId, String redirectURL) throws Exception;

	boolean getWithdrawLeave(OrgLocations orgLocations, String leaveUUID, User user, String reason) throws Exception;

	boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable, String id);

	boolean deleteAllLeaveRolesByParticularTableId(String from,Long fromId);

	void storeHolidayOrRestrictDays(OrgLocations orgLocations,User user,Object object) throws Exception;

	List<HolidayAndRestrictday> getAllHolidayAndRestrictDaysInDuration(String fromDate, String string, OrgLocations orgLocations);

	List<User> getApproversListBasedonHierarchyExceptLoginUser(Long loginUserId, Long roleId, Long locationId) throws Exception;

	List<HolidayAndRestrictday> getHolidayOrRestrictDaysListById(Long id, String type);

	List<LeaveRole> getLeaveRolesByRestrictDayId(Long restrictDayId)throws Exception;
	
	public void deleteAllHolidayOrRestrictDayById(Long id,String type);

	List<Leave> getAllLeavesByLocationIdAndStatus(Long locationId, String status);

	List<Leave> getLeavesListByTypeIdAndDuration(Long leaveTypeId, Long orgLocationId, Long userId, Date startDate, Date endDate);

	MonthlyLeaves saveMonthlyLeaves(MonthlyLeaves monthlyLeaves);
	
	MonthlyLeaves getMonthlyLeavesByLeaveId(Long leaveId);

	Leave getLeaveByLeaveUUId(String leaveUUID);
	
	File getLeaveExport(OrgLocations orgLocations, LeaveDTO leaveDTO) throws Exception;
	
	List<Leave> getAllLeave();

	List<Leave> getLeaveByUserIdOrglocationIdandStatus(User consultantUser, Long orglocationId, String approved);

	Response<LeaveDTO> saveBulkApprovedLeaves(MultipartFile multipartFile, OrgLocations orgLocation, User user, Org org);

	boolean reduceLeaveCount(OrgLocations orgLocations, User user, String leaveUUID, Long leaveTypeId, String status, int numberOfDays) throws Exception;

	List<LeaveApproveHierarchy> getAllLeaveApproversList(Long locationId);

	File getLeaveTypeImportTemplate(Long orgLocationId);

	LeaveTypeDTO saveBulkLeaveTypes(MultipartFile multipartFile, OrgLocations orgLocation, User user, Org org);

	Map<String, Float> getNumberOfCurrentMonthLeavesTakenByUser(Long userId, Long leaveTypeId);

	List<AppliedLeaveDateView> getLeaveByUserIdAndFromToDate(Long userId, OrgLocations orgLocation, String fromDate, String toDate);


	void updateBalanceLeaves(Leave leave, User user, OrgLocations orgLocations);

	public File getDailyWiseLeavePdfFile(ResponseList<LeaveReportView>  leaveReportsList,OrgLocations orgLocations) throws Exception;
	

	
	public Sheet getLeaveReportExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<LeaveReportView>  leaveReportsList,OrgLocations orgLocations) throws Exception;
	
	public File getLeaveReportPdfFile(ResponseList<LeaveReportView>  leaveReportsList,OrgLocations orgLocations) throws Exception;

	ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromToDate(DayWiseLeaveReportDTO dayWiseLeaveReportDTO,Long orgId, Long orgLocationId,String loginUserRole)throws Exception;

	List<String> getLeaveTypeNamesByOrgLocationId(Long OrgLocationId,String topleave);
	
	ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromDateToDate(HrLeaveReportDTO rLeaveReportDTO,Long orgId, Long orgLocationId,String loginUserRole)throws Exception;

	Map<String,List<LeaveBalanceReportDTO>> getLeaveNamesByOrgLocationId(Long orgLocationId,List<Long> userleaveTypeList,List<String> leaveTypesList,String topleave);
	
	String getDepartmentTypeNamesByOrgLocationIdAndUserId(Long userId ,Long orgLocationId) throws Exception ;
	
    Sheet getLeaveBalanceExcelFile(Sheet createExcelsheet,Workbook createExcelworkbook,Response<LeaveBalanceReportDTO> LeaveBalanceReport,OrgLocations orgLocation,String topleave)throws Exception;
    File getLeaveBalancePdfFile(Response<LeaveBalanceReportDTO> LeaveBalanceReport,OrgLocations orgLocation,String topleave) throws Exception;
	
	public Sheet getDailyWiseLeaveExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<LeaveReportView>  leaveReportsList,OrgLocations orgLocations) throws Exception;
	
	
    Float getTakenLeavesByOrgLocationIdAndUserId(Long leaveId,Long userId,Long orglocationId)throws Exception;

    Set<PayrollLopHis> getLeaveByFrequency(Long candidateId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation, EmployeePayroll employeePayroll);
    
    Set<PayrollLopHis> getLeaveByFrequencyInd(Long candidateId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocation, IndEmployeePayroll employeePayroll);
	
	List<Leave> getAllLeavesByLocationIdAndStatusAndAppliedDate(Long locationId, String status, Date appliedDate);
	
	List<Leave> getAllLeavesByLocationIdAndStatusAndLeaveStartDate(Long locationId, String status, Date appliedDate);
}