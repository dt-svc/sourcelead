package com.bis.sourcelead.payroll.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.payroll.dao.UserPayrollDao;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.service.UserPayrollService;

@Service
public class UserPayrollServiceImpl implements UserPayrollService{

	@Autowired
	private UserPayrollDao userPayrollDao;
	
	@Override
	public UserPayroll saveUserPayroll(UserPayroll userPayroll) throws Exception {
		return userPayrollDao.saveUserPayroll(userPayroll);
	}

	@Override
	public List<UserPayroll> getUserPayrollsBystartDateEndDte(Long orgLocationID, Date startDate, Date endDate) throws Exception {
		return userPayrollDao.getUserPayrollsBystartDateEndDte(orgLocationID,startDate,endDate);
	}

	@Override
	public List<UserPayroll> getPayByUserId(Long userId, Long orgLocationId) throws Exception {
		return userPayrollDao.getPayByUserId(userId, orgLocationId);
	}

	@Override
	public List<Checks> getChecksList(Long userId, Long orgLocationId) throws Exception {
		return userPayrollDao.getChecksList(userId, orgLocationId);
	}

}
