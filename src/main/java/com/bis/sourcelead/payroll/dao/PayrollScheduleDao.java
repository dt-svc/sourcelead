package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;


public interface PayrollScheduleDao {

	UsPayrollSchedule savePayrollSchedule(UsPayrollSchedule UsPayrollSchedule, OrgLocations orgLocations);

	UsPayrollSchedule getWeeklyFrequenciesBySelectedDate(String dateFrequency, OrgLocations orgLocations);

}
