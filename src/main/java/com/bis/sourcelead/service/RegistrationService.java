package com.bis.sourcelead.service;

import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.form.Registration;

public interface RegistrationService {
	
	public void addRegistration(Registration registration) throws Exception;

}
