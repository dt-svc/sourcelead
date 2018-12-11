package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;


public interface PayrollScheduleDao {

	UsPayrollSchedule savePayrollSchedule(UsPayrollSchedule UsPayrollSchedule, OrgLocations orgLocations);

	UsPayrollSchedule getWeeklyFrequenciesBySelectedDate(String dateFrequency, OrgLocations orgLocations);

	List<UsPayrollSchedule> findAllPayrollScheduleList(Long orgLocationId);

	UsPayrollSchedule getPayScheduleByUUID(String payrollScheduleUUID, Long orgLocationId);

	int deleteExistedNotifyMembers(Long payScheduleId);

	UsPayrollSchedule findScheduleByFrequency(String payFrequency, Long orgLocationId, String scheduleUUID);
	
	List<UsPayrollSchedule> getAllPayrollScheduleByOrgLocationId(Long orgLocationId);

}
