package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RegistrationOptionalServiceDao;
import com.bis.sourcelead.entity.RegistrationOptionalService;
import com.bis.sourcelead.service.RegistrationOptionalServicesService;

@Service
public class RegistrationOptionalServicesServiceImpl  implements RegistrationOptionalServicesService{

	@Autowired
	RegistrationOptionalServiceDao registrationOptionalServiceDao;
	

	@Override
	public List<RegistrationOptionalService> getRegistraionOptionalServiceListByRegisterType(String registerType) throws Exception {
		return registrationOptionalServiceDao.getRegistraionOptionalServiceListByRegisterType(registerType);
	}
	


}
