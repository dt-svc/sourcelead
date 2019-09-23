package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.OrglocationDepositTaxes;

public interface OrglocationDepositTaxesDao {

	OrglocationDepositTaxes saveOrglocationDepositTaxes(OrglocationDepositTaxes orglocationDepositTaxes) throws Exception;

	OrglocationDepositTaxes getOrglocationDepositTaxesByOrgLocationId(Long locationId);

}
