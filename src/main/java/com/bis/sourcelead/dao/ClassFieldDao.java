package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.ClassField;

public interface ClassFieldDao {

	ClassField getClassFieldByDelimiterStr(String delimiterStr) throws Exception;
}
