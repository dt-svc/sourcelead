package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RegistrationOptionalService;

public interface RegistrationOptionalServicesService  {
	public List<RegistrationOptionalService> getRegistraionOptionalServiceListByRegisterType(String registerType) throws Exception;
}
