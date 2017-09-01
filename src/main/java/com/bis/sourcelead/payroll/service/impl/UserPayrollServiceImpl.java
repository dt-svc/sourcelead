package com.bis.sourcelead.payroll.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ChecksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.UserPayrollDao;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.util.ConversionUtil;

@Service
public class UserPayrollServiceImpl implements UserPayrollService{

	@Autowired
	private UserPayrollDao userPayrollDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Override
	public UserPayroll saveUserPayroll(UserPayroll userPayroll) throws Exception {
		return userPayrollDao.saveUserPayroll(userPayroll);
	}

	@Override
	public List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationID, Date startDate, Date endDate) throws Exception {
		return userPayrollDao.getUserPayrollsBystartDateEndDte(orgLocationID,startDate,endDate);
	}

	@Override
	public List<UserPayroll> getPayByUserId(Long userId, Long orgLocationId,String selectedYear) throws Exception {
		Date startDay = null;
		Date endDay = null;
		if(selectedYear != null && selectedYear != "" && selectedYear != "undefined"){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(selectedYear));
			cal.set(Calendar.DAY_OF_YEAR, 1);
		    startDay = cal.getTime();
			

			cal.set(Calendar.YEAR, Integer.parseInt(selectedYear));
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DAY_OF_MONTH, 31);
			endDay = cal.getTime();
		}
		return userPayrollDao.getPayByUserId(userId, orgLocationId,startDay,endDay);
	}

	@Override
	public ResponseList<Checks> getChecksList(ChecksDTO checksDTO, Long orgLocationId) throws Exception {
		return userPayrollDao.getChecksList(checksDTO, orgLocationId);
	}

	@Override
	public List<Checks> getChecksListByEmployeeId(Long empId, OrgLocations orgLocations) throws Exception {
		return userPayrollDao.getChecksListByEmployeeId(empId,orgLocations);
	}

}
