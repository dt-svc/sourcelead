package com.bis.sourcelead.dao.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
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
	
	@Autowired
	private UserGroupDao userGroupDao;
	
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

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public PaysheetFrequency getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation,
			User loginUser) {
		String[] groupUUID = generateINDPayrollDTO.getGroupId().split(",");
		UserGroup userGroup = userGroupDao.findUserGroupById(groupUUID[0]);
		String dates[] = generateINDPayrollDTO.getPayPeriod().split(",");
		Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[0].replaceAll("/", "-"));
		Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), dates[1].replaceAll("/", "-"));
		java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		Session session = this.sessionFactory.getCurrentSession();
		PaysheetFrequency paysheetFrequency = null;
		List<PaysheetFrequency> list = session.createQuery("from PaysheetFrequency pf where pf.userGroup.id =:groupId and pf.orgLocation.id = :orgLocationId and pf.frequencyStartDate = :startDate and pf.frequencyEndDate = :endDate and pf.type = 'PAYROLL'")
		.setLong("groupId", userGroup.getId())
		.setLong("orgLocationId", orgLocation.getId())
		.setDate("startDate", sqlStartDate)
		.setDate("endDate", sqlEndDate).list();
		if (list != null && list.size() > 0) {
			paysheetFrequency = list.get(0);
		}
		return paysheetFrequency;
	}
	
	
	
}
