package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.PayrollDao;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.util.CommonUtil;

/**
 * 
 * @author srath and ajith
 * @date 16'th dec 2016
 *
 */
@Repository
public class PayrollDaoImpl implements PayrollDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommonUtil commonUtil;
	
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
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Payroll getPayrollById(Long id) {
		 
			Session session = this.sessionFactory.getCurrentSession();
			List<Payroll> payrolls = new ArrayList<Payroll>();
			payrolls = session.createQuery("from Payroll payroll where payroll.id =:id" ).setLong("id", id).list();
			logger.debug("Payroll end");
			if (payrolls.size() > 0) {
				return payrolls.get(0);
			} else {
				return null;
			}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Payroll> getAllPayroll() {
		
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Payroll payroll";
	   
		List<Payroll> payrollList = session.createQuery(query.toString()).list();
	    return payrollList;
	    
	}
	
	
	
}
