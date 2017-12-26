package com.bis.sourcelead.reports.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;

public interface ClientReportDao {

	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long relationId ,Long locationId, Long userId);

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);

	List<Contracts> getContractsListOFAllClients(Long orgLocationId, User user, Long relationId, String userRoleName,ContractReportsDTO contractReportsDTO);

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocId, String invoiceType);

}
