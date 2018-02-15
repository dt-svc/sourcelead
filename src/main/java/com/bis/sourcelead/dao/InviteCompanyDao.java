package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.InviteCompany;
import com.bis.sourcelead.entity.ScheduleMaintenance;

public interface InviteCompanyDao {
	
	/**
	 * @author Laxman
	 * @param InviteCompany
	 * @return
	 * @throws Exception
	 */
	 InviteCompany saveInviteCompany(InviteCompany inviteCompany) throws Exception;

	void storeScheduleMaintenance(ScheduleMaintenance maintenance);

	ScheduleMaintenance getScheduleStatus(Date currentDatePlusFive);
	
}
