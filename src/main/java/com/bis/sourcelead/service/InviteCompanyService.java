package com.bis.sourcelead.service;

import java.text.ParseException;
import java.util.List;

import com.bis.sourcelead.dto.InviteCompanyDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.InviteCompany;

public interface InviteCompanyService {
	
	InviteCompany saveInviteCompany(InviteCompany inviteCompany) throws Exception;
	
	String  saveInviteCompany(InviteCompanyDTO inviteCompanyDTO) throws Exception;

	void storeScheduleMaintenance(ScheduleMaintenanceDTO maintenanceDTO) throws ParseException;

	ScheduleMaintenanceDTO getScheduleStatus() throws ParseException;

	ResponseList<ScheduleMaintenanceDTO> getScheduleMaintenanceList(ScheduleMaintenanceDTO scheduleMaintenanceDTO, String searchValue) throws ParseException;

	void deleteScheduleMaintenance(String id);
	
}
