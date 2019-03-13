package com.bis.sourcelead.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.RegistrationOptionalServiceDao;
import com.bis.sourcelead.entity.RegistrationOptionalService;

@Repository
public class RegistrationOptionalServiceDaoImpl implements RegistrationOptionalServiceDao {

	@Autowired
	 private SessionFactory sessionFactory;
	
	@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<RegistrationOptionalService> getRegistraionOptionalServiceListByRegisterType(String registerType) {
		Session session = this.sessionFactory.getCurrentSession();
		Integer registerStatus = 1;
		String query = "from RegistrationOptionalService registerOptional where registerOptional.registerType = :registerType and registerOptional.registerStatus = :registerStatus";
		List<RegistrationOptionalService> optionalServicsList =  session.createQuery(query.toString()).setString("registerType", registerType).setInteger("registerStatus",registerStatus).list();
		return optionalServicsList;
	}

}
