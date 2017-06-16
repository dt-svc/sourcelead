package com.bis.sourcelead.payroll.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

public interface WageTaxSummaryService {
	
	WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary)throws Exception;

	String saveWagesTaxSummary(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;
}
