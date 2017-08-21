package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dao.InvoiceDataDao;
import com.bis.sourcelead.payroll.entity.InvoiceData;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

@Repository
public class InvoiceDataDaoImpl implements InvoiceDataDao{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceDataDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public InvoiceData saveInvoiceData(InvoiceData invoiceData) {
		
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(invoiceData);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return invoiceData;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<InvoiceData> getInvoiceDetails(Long userId, Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDataList = null;
		String query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId";
		invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if (invoiceDataList != null && invoiceDataList.size() > 0) {
			return invoiceDataList;
		}
		return invoiceDataList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<InvoiceData> getInvoiceDetailsByEmpId(Long empId, OrgLocations orgLocations) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<InvoiceData> invoiceDataList = null;
		String query = "from InvoiceData iData  left join fetch iData.orgLocationId where iData.orgLocationId.id =:orgLocationId and iData.sku=:empId";
		invoiceDataList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).setLong("empId", empId).setCacheable(true).list();
		if (invoiceDataList != null && invoiceDataList.size() > 0) {
			return invoiceDataList;
		}
		return invoiceDataList;
	}

}
