package com.bis.sourcelead.indPayroll.daoImpl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.indPayroll.dao.AssociatePayrollPayheadHistoryDao;
import com.bis.sourcelead.indPayroll.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.dao.impl.AssociateTaxDaoImpl;

@Repository
public class AssociatePayrollPayheadHistoryDaoImpl implements AssociatePayrollPayheadHistoryDao{

private static final Logger logger = LoggerFactory.getLogger(AssociateTaxDaoImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${SourceLead.100}")
	private String notFound;
	@Value("${SourceLead.101}")
	private String noRecords;
	@Value("${SourceLead.102}")
	private String statusUpdate;
	@Value("${SourceLead.103}")
	private String statusDelete;
	@Value("${SourceLead.106}")
	private String duplicateValue;
	
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
	public List<AssociatePayrollPayheadHistory> getLatestAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from AssociatePayrollPayheadHistory apph left join fetch apph.indEmployeePayrollId iemp left join fetch apph.payrollPayhead app where iemp.id = :empPayrollId and apph.status = '1' and apph.updatedDate "
				+ " IN(select Max(subapph.updatedDate) from AssociatePayrollPayheadHistory subapph  where subapph.indEmployeePayrollId.id = :empPayrollId and subapph.payrollPayhead.id = app.id and subapph.status = '1') ";  
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


	@Override
	@Transactional
	public AssociatePayrollPayheadHistory saveAssociatePayheadAdjustInd(AssociatePayrollPayheadHistory associatePayrollPayheadHistory)
			throws Exception {
		logger.debug("Adding AssociatePayheadAdjustInd");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (associatePayrollPayheadHistory != null) {
				session.saveOrUpdate(associatePayrollPayheadHistory);
			}
			logger.debug("AssociatePayheadAdjustInd Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"ASSOCIATE_PAYROLL_PAYHEAD_HISTORY"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return associatePayrollPayheadHistory;
	}
	
	
	@Override
	@Transactional
	public IndEmployeePayroll saveIndEmployeePayroll(IndEmployeePayroll indEmployeePayroll)
			throws Exception {
		logger.debug("Adding IndEmployeePayroll");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (indEmployeePayroll != null) {
				session.saveOrUpdate(indEmployeePayroll);
			}
			logger.debug("IndEmployeePayroll Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"IND_EMPLOYEE_PAYROLL"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return indEmployeePayroll;
	}

}
