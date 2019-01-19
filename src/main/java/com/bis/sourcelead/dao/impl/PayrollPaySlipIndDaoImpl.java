package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.PayrollPaySlipIndDao;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class PayrollPaySlipIndDaoImpl implements PayrollPaySlipIndDao{
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
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
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollPaySlipIndDaoImpl.class);

	@Override
	public PayrollPaySlipInd savePayrollPaySlipInd(PayrollPaySlipInd payrollPaySlipInd) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollPaySlipInd != null) {
				session.saveOrUpdate(payrollPaySlipInd);
			}
			logger.debug("Saving PayrollPaySlipInd");
		} catch (Exception exception) {
			 StringBuilder message= new StringBuilder(MessageFormat.format(duplicateValue,"IND_PAYSLIP"));
				throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollPaySlipInd;
	}

	@Override
	public List<PayrollPaySlipInd> getIndPayrollPaySlipListByOrglocationInd(Long orglocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPaySlipInd ppi  where ppi.orgLocationId.id =:orglocationId and ppi.status = 1 "; 
		List<PayrollPaySlipInd> indPayrollPayList = session.createQuery(query).setLong("orglocationId", orglocationId).list();
		return indPayrollPayList;
	}

	@Override
	public PayrollPaySlipInd getPayrollPayslipByUUID(String uuid) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPaySlipInd ppsi  where ppsi.uuid =:uuid and ppsi.status = 1 "; 
		List<PayrollPaySlipInd> indPayrollPayList = session.createQuery(query).setString("uuid", uuid).list();
		PayrollPaySlipInd  payrollPaySlipInd = null;
		if(indPayrollPayList != null && !indPayrollPayList.isEmpty())
		{
			payrollPaySlipInd = (PayrollPaySlipInd) indPayrollPayList.iterator().next();
		}
		return payrollPaySlipInd;
	}

	
	@Override
	public PayrollPaySlipInd validatePayslipNameByUUIDAndPayslipName(String uuid, String payslipName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPaySlipInd ppsi  where ppsi.uuid =:uuid and ppsi.paySlipName =:payslipName   and ppsi.status = 1 "; 
		List<PayrollPaySlipInd> indPayrollPayList = session.createQuery(query).setString("uuid", uuid).setString("payslipName", payslipName).list();
		PayrollPaySlipInd  payrollPaySlipInd = null;
		if(indPayrollPayList != null && !indPayrollPayList.isEmpty())
		{
			payrollPaySlipInd = (PayrollPaySlipInd) indPayrollPayList.iterator().next();
		}
		return payrollPaySlipInd;
	}

	@Override
	public Boolean validatePayslipNameByOrglocationIdAndPayslipName(Long orglocationId, String paySlipName) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
			 String query = "from PayrollPaySlipInd ppsi where ppsi.orgLocationId.id =:orglocationId and ppsi.paySlipName =:paySlipName and ppsi.status = 1 ";
				List<PayrollPaySlipInd> payslipIndList = session.createQuery(query) .setString("paySlipName", paySlipName).setLong("orglocationId", orglocationId).list();
				if (payslipIndList != null && payslipIndList.size() > 0) {
					flag = true;
				}
		return flag;
	}

	@Override
	public PayrollPaySlipInd deletePermissionStatsuByDeletePermission(String deletePermission,Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPaySlipInd ppsi  where ppsi.deletePermission =:deletePermission and ppsi.orgLocationId.id =:orgLocationId and ppsi.status = 1 "; 
		List<PayrollPaySlipInd> indPayrollPayList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setString("deletePermission", deletePermission).list();
		PayrollPaySlipInd  payrollPaySlipInd = null;
		if(indPayrollPayList != null && !indPayrollPayList.isEmpty())
		{
			payrollPaySlipInd = (PayrollPaySlipInd) indPayrollPayList.iterator().next();
		}
		return payrollPaySlipInd;
	}

	@Override
	public PayrollPaySlipInd getActivePayslipByOrglocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPaySlipInd ppsi  where ppsi.isActive ='Y' and ppsi.orgLocationId.id =:orgLocationId and ppsi.status = 1 "; 
		List<PayrollPaySlipInd> indPayrollPayList = session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
		PayrollPaySlipInd  payrollPaySlipInd = null;
		if(indPayrollPayList != null && !indPayrollPayList.isEmpty())
		{
			payrollPaySlipInd = (PayrollPaySlipInd) indPayrollPayList.iterator().next();
		}
		return payrollPaySlipInd;
	}
	 
}
