package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.InviteCompany;

public interface InviteCompanyService {
	
	InviteCompany saveInviteCompany(InviteCompany inviteCompany) throws Exception;
	
	String  saveInviteCompany(JSONObject jsonObj) throws Exception;
	
}
