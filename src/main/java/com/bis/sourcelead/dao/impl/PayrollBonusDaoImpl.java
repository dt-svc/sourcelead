package com.bis.sourcelead.dao.impl;

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

import com.bis.sourcelead.dao.PayrollBonusDao;
import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.entity.PayrollBonus;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class PayrollBonusDaoImpl implements PayrollBonusDao{

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

	private static final Logger logger = LoggerFactory.getLogger(PayrollBonusDaoImpl.class);

	@Override
	@Transactional
	public PayrollBonus savePayrollBonus(PayrollBonus payrollBonus) throws Exception {
		
		logger.debug("Adding payrollBonus");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollBonus != null) {
				session.saveOrUpdate(payrollBonus);
			}
			logger.debug("payrollBonus Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"PAYROLL_BONUS"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollBonus;
	}

	@Override
	@Transactional
	public List<PayrollBonusDTO> getPayrollBonusList(Long orgLocationId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public PayrollBonus getPayrollBonusByUUID(String uuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
