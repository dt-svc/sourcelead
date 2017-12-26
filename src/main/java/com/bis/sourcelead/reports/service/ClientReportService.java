package com.bis.sourcelead.reports.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;
import com.bis.sourcelead.reportsDTO.InvoiceReportDTO;

public interface ClientReportService {

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);
	
	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getContractsListOFAllClients(Long orgLocationId, User user, String userRoleName,ContractReportsDTO contractReportsDTO);

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocId,String invoiceType);

	InvoiceReportDTO getTopFiveReceivsblesReports(OrgLocations orgLocation, User user, String type);
}
