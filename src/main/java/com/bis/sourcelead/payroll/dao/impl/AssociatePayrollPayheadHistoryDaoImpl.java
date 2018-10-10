package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.payroll.dao.AssociatePayrollPayheadHistoryDao;

@Repository
public class AssociatePayrollPayheadHistoryDaoImpl implements AssociatePayrollPayheadHistoryDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<AssociatePayrollPayheadHistory> getUpdatedAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociatePayrollPayheadHistory apph where apph.indEmployeePayrollId.id = :empPayrollId and apph.status = '1' ";  
		List<AssociatePayrollPayheadHistory> associatePayrollPayheadList =  session.createQuery(query).setLong("empPayrollId", empPayrollId).list();
	return associatePayrollPayheadList;
	}
	
	
	@Override
	@Transactional
	public List<AssociatePayrollPayheadHistory> getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(String empPayrollUUID) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociatePayrollPayheadHistory apph where apph.indEmployeePayrollId.indEmpPayrollUUID = :empPayrollUUID and apph.status = '1' ";  
		List<AssociatePayrollPayheadHistory> associatePayrollPayheadList =  session.createQuery(query).setString("empPayrollUUID", empPayrollUUID).list();
	return associatePayrollPayheadList;
	}
	
	
	@Override
	@Transactional
	public List<AssociatePayrollPayheadHistory> getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(String paySheetFrequencyUUID)throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociatePayrollPayheadHistory apph where apph.indEmployeePayrollId.indPaysheetFrequency.paySheetFrequencyUUID = :paySheetFrequencyUUID and apph.status = '1' ";  
		List<AssociatePayrollPayheadHistory> associatePayrollPayheadList =  session.createQuery(query).setString("paySheetFrequencyUUID", paySheetFrequencyUUID).list();
	return associatePayrollPayheadList;
	}

}
