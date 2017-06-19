package com.bis.sourcelead.payroll.entity;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * File validator to select file mandatory
 * 
 * @author Rajashekar
 *
 */
@Component
public class PayrollFileValidator implements Validator {
	public boolean supports(Class<?> paramClass) {
		return File.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		File file = (File) obj;
		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "valid.file","Please select a file!");
		}
	}
}
