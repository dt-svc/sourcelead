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

import com.bis.sourcelead.payroll.dao.UsMyPayrollFormDao;
import com.bis.sourcelead.payroll.entity.AssociateContribution;
import com.bis.sourcelead.payroll.entity.UsMyPayrollForms;

@Repository
public class UsMyPayrollFormDaoImpl implements UsMyPayrollFormDao{
	
	private static final Logger logger = LoggerFactory.getLogger(UsMyPayrollFormDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public UsMyPayrollForms saveUsMyPayrollForm(UsMyPayrollForms myPayrollForm)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(myPayrollForm);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return myPayrollForm;
	}

	@Override
	@Transactional
	public List<UsMyPayrollForms> getUsMyPayrollFormByLocationIdAndCandidateId(Long locationId, Long candidateId)
	{
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("from UsMyPayrollForms umpf where umpf.candidate.id=:candidateId and umpf.orgLocationId.id= :orgLocationId and umpf.status = 1" ).setLong( "candidateId", candidateId).setLong("orgLocationId", locationId).list();
	}

	@Override
	@Transactional
	public UsMyPayrollForms getUsMyPayrollFormByUUID(String uuid)
	{
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from UsMyPayrollForms forms where forms.uuid =:uuid";
		List<UsMyPayrollForms> list = session.createQuery(query).setString("uuid", uuid).list();
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}else
			return null;
	}


}
