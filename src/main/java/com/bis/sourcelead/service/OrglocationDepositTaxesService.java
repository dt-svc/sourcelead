package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.FederalAndStateTaxDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationDepositTaxes;
import com.bis.sourcelead.entity.User;

public interface OrglocationDepositTaxesService {

	OrglocationDepositTaxes saveOrglocationDepositTaxes(FederalAndStateTaxDTO federalAndStateTaxDTO,OrgLocations loginOrgLocation, User user) throws Exception;

	OrglocationDepositTaxes getOrglocationDepositTaxesByOrgLocationId(Long locationId);

}
