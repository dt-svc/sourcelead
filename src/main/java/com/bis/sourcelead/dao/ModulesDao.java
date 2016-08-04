package com.bis.sourcelead.dao;

import java.util.List;

public interface ModulesDao {

	List<Long> getModulesIdByModuleName(String modules) throws Exception;
}
