package com.bis.sourcelead.dao;

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
	
}
