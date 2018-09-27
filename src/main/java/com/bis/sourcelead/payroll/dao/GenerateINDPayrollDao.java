package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;

public interface GenerateINDPayrollDao {

	IndPaysheetFrequency getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation, User loginUser);


}
