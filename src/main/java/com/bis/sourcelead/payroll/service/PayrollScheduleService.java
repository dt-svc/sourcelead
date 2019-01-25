package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayrollScheduleDTO;
import com.bis.sourcelead.payroll.entity.UsPayrollSchedule;

public interface PayrollScheduleService {
	
	Response<PayrollScheduleDTO> savePayrollSchedule(PayrollScheduleDTO payrollScheduleDTO, OrgLocations orgLocations, User loginUser);

	List<String> getWeeklyFrequenciesBySelectedDate(String selectedDate, String orgLocDateFormat, String DateFrequency)
			throws Exception;

	UsPayrollSchedule getWeeklyFrequenciesBySelectedDate(String dateFrequency, OrgLocations orgLocation) throws Exception;

	List<PayrollScheduleDTO> findAllPayrollScheduleList(Long orgLocationId, String orgDateFormat);

	UsPayrollSchedule getPayScheduleByUUID(String payrollScheduleUUID, Long orgLocationId);

	UsPayrollSchedule findScheduleByFrequency(String payFrequency, Long orgLocationId, String scheduleUUID);
	
	List<UsPayrollSchedule> getAllPayrollScheduleByOrgLocationId(Long orgLocationId);

}
