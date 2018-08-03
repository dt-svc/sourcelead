package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.AssociateSalaryHistoryDTO;
import com.bis.sourcelead.entity.OrgLocations;

public interface AssociateSalaryHistoryService {

	List<AssociateSalaryHistoryDTO> findAssociateSalHistory(Long associateSalid, OrgLocations orgLocation);
}
