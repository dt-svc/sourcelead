package com.bis.sourcelead.reports.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reportsDTO.APAgingDTO;

public interface FinanceService {

	APAgingDTO getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO aPAgingDTO);

	ResponseList<APAgingDTO> getApAgingBillListByLocation(OrgLocations orgLocation, APAgingDTO aPAgingDTO);

}
