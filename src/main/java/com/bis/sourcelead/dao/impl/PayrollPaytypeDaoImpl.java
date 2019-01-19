package com.bis.sourcelead.dao.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
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

import com.bis.sourcelead.dao.PayrollPaytypeDao;
import com.bis.sourcelead.dto.PayrollPaytypeDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollPaytype;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

@Repository
public class PayrollPaytypeDaoImpl implements PayrollPaytypeDao{

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

	private static final Logger logger = LoggerFactory.getLogger(PayrollPaytypeDaoImpl.class);

	@Override
	@Transactional
	public PayrollPaytype savePayrollPaytype(PayrollPaytype payrollPaytype) throws Exception {
		logger.debug("Adding payrollPaytype");
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (payrollPaytype != null) {
				session.saveOrUpdate(payrollPaytype);
			}
			logger.debug("payrollPaytype Added Successfully");
		} catch (ConstraintViolationException exception) {
			StringBuffer message= new StringBuffer(MessageFormat.format(duplicateValue,"PAYROLL_PAYTYPE"));
			throw new SourceLeadConstraintViolationException(message.toString() +exception.getCause().toString());
		}
		return payrollPaytype;
	}
	
	
	@Override
	@Transactional
	public ResponseList<PayrollPaytypeDTO> getPayrollPaytypeList(OrgLocations orgLocations) throws Exception {
		ResponseList<PayrollPaytypeDTO> payrollPaytypeDTOList = new ResponseList<>();
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from PayrollPaytype pt  where (pt.orgLocationId = '1' or pt.orgLocationId =:orgLocationId) and pt.status = '1' ";  
		List<PayrollPaytype> payrollPaytypeList = session.createQuery(query).setLong("orgLocationId", orgLocations.getId()).list();
		
		payrollPaytypeDTOList.setData(setPayrollPaytypeList(payrollPaytypeList,orgLocations));
		return payrollPaytypeDTOList;
	}
	private List<PayrollPaytypeDTO> setPayrollPaytypeList(List<PayrollPaytype> payrollPaytypeList,OrgLocations orgLocations) throws Exception {
		List<PayrollPaytypeDTO> payrollPaytypeDTOList = new ArrayList<>();
		if(payrollPaytypeList != null && !payrollPaytypeList.isEmpty()) {
			for(PayrollPaytype payRollPaytype : payrollPaytypeList) {
				PayrollPaytypeDTO PaytypeDTO = new PayrollPaytypeDTO();
				PaytypeDTO.setId(payRollPaytype.getId());
				PaytypeDTO.setName(payRollPaytype.getName());
				PaytypeDTO.setDescription(payRollPaytype.getDescription());
				payrollPaytypeDTOList.add(PaytypeDTO);
			}
		}
		return payrollPaytypeDTOList;
	}
	
	
	@Override
	@Transactional(rollbackFor = { Exception.class })	
	public Boolean findPayrollPaytypeByNameAndLocationId(PayrollPaytypeDTO payrollPaytypeDTO) throws Exception {
		 Session session = this.sessionFactory.getCurrentSession();
		 boolean flag = false;
		 if(payrollPaytypeDTO.getName() != null && payrollPaytypeDTO.getOrgLocationId() != null){
				String query = " from PayrollPaytype pt  where (pt.orgLocationId = '1' or pt.orgLocationId =:orgLocationId) and lower(pt.name) =:name and pt.status = '1' ";
				List<PayrollPaytype> payrollPaytypeList = session.createQuery(query).setString("name", payrollPaytypeDTO.getName().trim().toLowerCase()).setLong("orgLocationId", Long.parseLong(payrollPaytypeDTO.getOrgLocationId())).list();
				if (payrollPaytypeList != null && payrollPaytypeList.size() > 0) {
					flag = true;
				}
		 }
		return flag;
	}
	
}
