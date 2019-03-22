package com.bis.sourcelead.dao;


import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bis.sourcelead.dto.DayWiseLeaveReportDTO;
import com.bis.sourcelead.dto.HrLeaveReportDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AppliedLeaveDateView;
import com.bis.sourcelead.entity.HolidayAndRestrictday;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.LeaveReportView;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.MonthlyLeaves;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PayrollLopHis;
import com.bis.sourcelead.reportsDTO.LeaveBalanceReportDTO;

public interface LeaveDao {

	Leave saveLeave(Leave leave) throws Exception;

	ResponseList<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(LeaveDTO leaveDTO, OrgLocations orgLocations) throws ParseException;

	List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId);

	Leave getLeaveById(Long leaveId) throws Exception;

	List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndDuration(Long locationId, String userId, String status, Date currentWeekStarDate, Date currentWeekEndDate, String department, Date leaveDate) throws Exception;

	List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeUUID, Long orgLocationId, Long userId);
	
	Map<String, Float> getNumberOfLeavesTakenByUser(Long userId, Long leaveTypeId) throws Exception;

	float getThisMonthLeavesTakenByUser(Long userId, LeaveType leaveType, OrgLocations orgLocations);

	ResponseList<Leave> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception;

	List<LeaveApproveHierarchy> getAllEmployeesLeavesCounts(Long userId,Long orgLocationId,String loginUserRole, Long orgId);

	List<Leave> getAllSelfLeavesCounts(Long orgLocationId, Long userId);
	
	List<Leave> getLeaveTakersBarChat(OrgLocations orgLocation, LeaveDTO leaveDTO)throws Exception;

	List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndUserIdAndStatus(Long locationId, String userId, String status);

	boolean validateNameByOrgLocationIdandFromTable(String name, Long orgLocationId, String fromTable, String id);

	boolean deleteAllLeaveRolesByParticularTableId(String from, Long fromId);

	void storeHolidayAndRestrictDays(HolidayAndRestrictday day);

	List<HolidayAndRestrictday> getAllHolidayAndRestrictDaysInDuration(String fromDate, String toDate, OrgLocations orgLocations);

	List<User> getApproversListBasedonHierarchyExceptLoginUser(Long loginUserId, Long roleId, Long locationId) throws Exception;

	List<HolidayAndRestrictday> getHolidayOrRestrictDaysListById(Long id, String type);

	List<LeaveRole> getLeaveRolesByRestrictDayId(Long restrictDayId);

	void deleteAllHolidayOrRestrictDayById(Long id, String type);

	List<Leave> getAllLeavesByLocationIdAndStatus(Long locationId, String status);

	List<Leave> getLeavesListByTypeIdAndDuration(Long leaveTypeId, Long orgLocationId, Long userId, Date startDate, Date endDate);

	MonthlyLeaves saveMonthlyLeaves(MonthlyLeaves monthlyLeaves);

	MonthlyLeaves getMonthlyLeavesByLeaveId(Long leaveId);

	Leave getLeaveByLeaveUUId(String leaveUUID);

	List<Leave> getAllLeave();

	List<Leave> getLeaveByUserIdOrglocationIdandStatus(Long id, Long orglocationId, String approved);

	List<LeaveApproveHierarchy> getAllLeaveApproversList(Long locationId);

	Map<String, Float> getNumberOfCurrentMonthLeavesTakenByUser(Long userId, Long leaveTypeId);

	List<AppliedLeaveDateView> getLeaveByUserIdAndFromToDate(Long userId, OrgLocations orgLocation, String fromDate, String toDate);

	int deleteApproversByLeaveId(Long leaveId);

	
	ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromToDate(HrLeaveReportDTO hrLeaveReportDTO,Long orgId, Long orgLocationId,String loginUserRole)throws Exception;
	
	ResponseList<LeaveReportView> getLeaveReportsOrglocationIdAndFromToDate(DayWiseLeaveReportDTO dayWiseLeaveReportDTO,Long orgId, Long orgLocationId,String loginUserRole)throws Exception;
	
	List<String> getLeaveTypeNamesByOrgLocationId(Long orgLocationId,String topleave);
	

	Map<String,List<LeaveBalanceReportDTO>> getLeaveNamesByOrgLocationId(Long orgLocationId,List<Long> userleaveTypeList,List<String> leaveTypesList,String topleave) ;

	String getDepartmentTypeNamesByOrgLocationIdAndUserId(Long userId,Long orgLocationId);

	 Float getTakenLeavesByOrgLocationIdAndUserId(Long leaveId,Long userId,Long orglocationId)throws Exception;

	 Set<PayrollLopHis> getLeaveByFrequency(Long candidateId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocations, EmployeePayroll employeePayroll);
	 
	 Set<PayrollLopHis> getLeaveByFrequencyInd(Long candidateId, Date freqStartDate, Date freqEndDate, OrgLocations orgLocations, IndEmployeePayroll employeePayroll);

    List<Leave> getAllLeavesByLocationIdAndStatusAndAppliedDate(Long locationId, String status, Date appliedDate);
    
    List<Leave> getAllLeavesByLocationIdAndStatusAndLeaveStartDate(Long locationId, String status, Date appliedDate);
}
