package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.ClassField;

/**
 * 
 * @author Prasad salina
 *
 */
public interface ClassFieldService {
	
	ClassField getClassFieldByDelimiterStr(String delimiterStr) throws Exception;

}