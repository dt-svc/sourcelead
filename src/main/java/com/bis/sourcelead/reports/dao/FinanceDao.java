package com.bis.sourcelead.reports.dao;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.reportsDTO.APAgingDTO;

public interface FinanceDao {

	ResponseList<Invoice> getApAgingListByLocation(OrgLocations orgLocation, APAgingDTO apAgingDTO);

}
