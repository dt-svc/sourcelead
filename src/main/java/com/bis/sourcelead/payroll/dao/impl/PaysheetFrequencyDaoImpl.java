package com.bis.sourcelead.payroll.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.PaysheetFrequencyDao;
import com.bis.sourcelead.payroll.dto.MyPayrollSummaryDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.util.PayrollUtil;

@Repository
public class PaysheetFrequencyDaoImpl implements PaysheetFrequencyDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	PayrollUtil payrollUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeePayroll> getMyPayrollListByCandidateId(Long candidateId)
	{
		List<EmployeePayroll> payrollList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			payrollList = session.createQuery("from EmployeePayroll payroll where payroll.candidate.id=:candidateId and payroll.submitForAproverStatus = 'APPROVED'")
													   .setLong("candidateId", candidateId)
													   .list();
		}catch (Exception e) {
		e.printStackTrace();
		}
		return payrollList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PaysheetFrequency> getPaysheetFrequencyDraftDataByOrgLocationId(Long orgLocationId) {
		
		List<PaysheetFrequency> paySheetFreqList = null;
		try {
			Session session = this.sessionFactory.getCurrentSession();
			paySheetFreqList = session.createQuery("from PaysheetFrequency pay where pay.orgLocation.id=:orgLocationId")
					   .setLong("orgLocationId", orgLocationId)
					   .list();	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return paySheetFreqList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation,String approverStatus)
	{
		List<EmployeePayroll> payrollList = null;
		try {
			Date[] ytdDates = payrollUtil.getYTDDates(orgLocation);
			Session session = this.sessionFactory.getCurrentSession();
			payrollList = session.createQuery("from EmployeePayroll payroll where payroll.candidate.id=:candidateId and payroll.payCheckDate >= :startDate and payroll.payCheckDate <= :endDate  and payroll.submitForAproverStatus = :status")
													   .setLong("candidateId", candidate.getId())
													   .setString("status", approverStatus)
													   .setDate("startDate", ytdDates[0])
													   .setDate("endDate", ytdDates[1])
													   .list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payrollList;
	}
}
