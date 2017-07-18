package com.bis.sourcelead.payroll.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dao.BisUserMappingDao;
import com.bis.sourcelead.payroll.dao.WageTaxSummaryDao;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.Checks;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;


@Repository
public class BisUserMappingDaoImpl implements BisUserMappingDao {
	

	private static final Logger logger = LoggerFactory.getLogger(BisUserMappingDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BisUserMapping saveBisUserMapping(BisUserMapping bisUserMapping) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(bisUserMapping);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return bisUserMapping;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BisUserMapping> getBisUserListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> bisUserList = null;
		String query = "from BisUserMapping bisUser  left join fetch bisUser.orgLocationId where bisUser.orgLocationId.id =:orgLocationId and bisUser.userId.id IS NULL";
		bisUserList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(bisUserList != null && bisUserList.size() >0)
		{
			return bisUserList;
		}
		return bisUserList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public BisUserMapping getBisUserById(Long importedUserId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> users = session.createQuery("from BisUserMapping where id=:id" ).setCacheable(true).setLong( "id", importedUserId ).setCacheable(true).list();
		logger.debug("getUserById end");
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<BisUserMapping> getMappedUsersListByOrgLocationId(Long orgLocationId) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		List<BisUserMapping> bisUserList = null;
		String query = "from BisUserMapping bisUser  left join fetch bisUser.orgLocationId where bisUser.orgLocationId.id =:orgLocationId and bisUser.userId.id <> NULL";
		bisUserList = session.createQuery(query).setLong("orgLocationId", orgLocationId).setCacheable(true).list();
		if(bisUserList != null && bisUserList.size() >0)
		{
			return bisUserList;
		}
		return bisUserList;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Checks saveChecks(Checks checks) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.saveOrUpdate(checks);
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return checks;
	}




	

}
