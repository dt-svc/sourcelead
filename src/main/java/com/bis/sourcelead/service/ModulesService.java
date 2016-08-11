package com.bis.sourcelead.service;

import java.util.List;

public interface ModulesService {

	List<Long> getModulesIdByModuleName(String modules) throws Exception;
}
