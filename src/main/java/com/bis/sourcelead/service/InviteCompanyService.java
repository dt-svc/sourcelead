package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.InviteCompanyDTO;
import com.bis.sourcelead.entity.InviteCompany;

public interface InviteCompanyService {
	
	InviteCompany saveInviteCompany(InviteCompany inviteCompany) throws Exception;
	
	String  saveInviteCompany(InviteCompanyDTO inviteCompanyDTO) throws Exception;
	
}
