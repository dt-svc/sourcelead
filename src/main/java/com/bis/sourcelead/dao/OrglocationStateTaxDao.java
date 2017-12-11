package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrglocationStateTax;

public interface OrglocationStateTaxDao {

	OrglocationStateTax saveOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception;
	
	List<OrglocationStateTax> getOrglocationStateTaxListByOrgLocationId(Long orgLocationId) throws Exception;
	
	OrglocationStateTax getOrglocationStateTaxByUUID(String UUID) throws Exception;
	
	void deleteOrglocationStateTax(OrglocationStateTax orglocationStateTax) throws Exception;
}
