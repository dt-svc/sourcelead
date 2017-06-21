package com.bis.sourcelead.payroll.dao;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.AccountDeductions;

public interface AccountDeductionsDao {

	AccountDeductions saveAccountDeductions(AccountDeductions payCycle)throws Exception;

}
