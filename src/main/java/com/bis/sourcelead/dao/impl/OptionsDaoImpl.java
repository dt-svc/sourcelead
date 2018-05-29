package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.OptionsDao;
import com.bis.sourcelead.entity.Option;
import com.bis.sourcelead.entity.Org;

@Repository
public class OptionsDaoImpl implements OptionsDao{
	
	private static final Logger logger = LoggerFactory.getLogger(OptionsDaoImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public Option saveLeaveOption(Option options) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			if (options != null) {
				session.saveOrUpdate(options);
			}
		} catch (Exception exception) {
			logger.error(ExceptionUtils.getFullStackTrace(exception));
		}
		return options;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<Option> getOptionsListByLocationId(Long orgLocationId) {
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Option options where options.orgLocations.id = :orgLocationId and options.status = '1'";
		return session.createQuery(query).setLong("orgLocationId", orgLocationId).list();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Option getOptionsById(String optionUUID) throws Exception {
		Option option = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Option option where option.optionUUID= :optionUUID";
		List<Option> optionList = session.createQuery(query).setString("optionUUID", optionUUID).list();
		if (optionList != null && optionList.size() > 0) {
			option = optionList.get(0);
		}
		return option;
	}
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public Option getOptionsByOptionId(String id) throws Exception {
		Option option = null;
		Session session = this.sessionFactory.getCurrentSession();
		String query = "from Option option where option.id= :id";
		List<Option> optionList = session.createQuery(query).setString("id", id).list();
		if (optionList != null && optionList.size() > 0) {
			option = optionList.get(0);
		}
		return option;
	}
	
	
}
