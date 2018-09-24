package com.bis.sourcelead.payroll.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ChecksDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.BisUserMappingDao;
import com.bis.sourcelead.payroll.dao.UserPayrollDao;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.UserPayroll;
import com.bis.sourcelead.payroll.service.UserPayrollService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class UserPayrollServiceImpl implements UserPayrollService{

	@Autowired
	private UserPayrollDao userPayrollDao;
	
	@Autowired
	private ConversionUtil conversionUtil;
	
	@Autowired
	private BisUserMappingDao bisUserMappingDao;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
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
	public ResponseList<Checks> getChecksListByEmployeeId(OrgLocations orgLocations,ChecksDTO checksDTO) throws Exception {
		return userPayrollDao.getChecksListByEmployeeId(orgLocations,checksDTO);
	}

	@Override
	public Checks getUniqueChecksListByEmployeeId(String empId, Long orgLocationId,ChecksDTO checksDTO) throws Exception {
		
		OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		Checks checks = userPayrollDao.getUniqueChecksListByEmployeeId(empId, orgLocationId);
		Checks checksnew = new Checks();
		checksnew.setEmpId(empId);
		if(checks != null)
		checksnew.setEmpName(checks.getEmpName());
		checksnew.setCheckNo(checksDTO.getCheckNo());
		checksnew.setDate(conversionUtil.getDateFromString(checksDTO.getDate()));
		checksnew.setOrgLocationId(orgLocations);
		checksnew.setMonth(new SimpleDateFormat("MMM yyyy").format(conversionUtil.getDateFromString(checksDTO.getDate())));
		checksnew.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		if (checksDTO.getMemo() != null && !"".equals(checksDTO.getMemo())) {
			checksnew.setTypeOfExpense(checksDTO.getMemo());
		}
		if (checksDTO.getAmount() != null && !"".equals(checksDTO.getAmount())) {
			checksnew.setAmount(Float.parseFloat(checksDTO.getAmount()));
		}
		if (Constants.LEGAL_FEES.equalsIgnoreCase(checksDTO.getTypeOfExpense())) {
			checksnew.setLegalFees(Double.parseDouble(checksDTO.getAmount()));
		} else if (Constants.ADVANCE.equalsIgnoreCase(checksDTO.getTypeOfExpense())) {
			checksnew.setAdvance(Double.parseDouble(checksDTO.getAmount()));
		} else if (Constants.GENERAL_EXPENSES.equalsIgnoreCase(checksDTO.getTypeOfExpense())) {
			checksnew.setGeneralExpenses(Double.parseDouble(checksDTO.getAmount()));
		} else if (Constants.ELECTRONIC_WITHDRAWLS.equalsIgnoreCase(checksDTO.getTypeOfExpense())) {
			checksnew.setElectronicWithdrawals(checksDTO.getAmount());
		} else if (Constants.PAYROLL_CHKS.equalsIgnoreCase(checksDTO.getTypeOfExpense())) {
			checksnew.setPayrollChecks(Double.parseDouble(checksDTO.getAmount()));
		}
		return bisUserMappingDao.saveChecks(checksnew);
	}

	@Override
	public Object deletePayrollEntitiesByUUIDAndEntityType(Object obj)
	{
		return userPayrollDao.deletePayrollEntitiesByUUIDAndEntityType(obj);
	}

	@Override
	public Object getPayrollEntitiesByUUIDAndEntityType(String uuid, String entityType)
	{
		return userPayrollDao.getPayrollEntitiesByUUIDAndEntityType(uuid,entityType);
	}

	@Override
	public List<Checks> getChecksListByEmployeeId(Long empId, OrgLocations orgLocations) throws Exception {
		return userPayrollDao.getChecksListByEmployeeId(empId,orgLocations);
	}

}
