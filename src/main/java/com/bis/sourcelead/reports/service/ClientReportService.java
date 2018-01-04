package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ClientReportsDTO;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;
import com.bis.sourcelead.reportsDTO.InvoiceReportDTO;

public interface ClientReportService {

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);
	
	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getContractsListOFAllClients(OrgLocations orgLocation, User user, String userRoleName,ContractReportsDTO contractReportsDTO)  throws Exception;

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocId,String invoiceType, Long contactUserId,Date[] freqDates);

	InvoiceReportDTO getTopFiveReceivsblesReports(OrgLocations orgLocation, User user, String type, String dateFrequency, String fromDate, String toDate)  throws Exception;

	List<OrgSpoc> getClientSpocListByOrgRelationid(Long orgRelationid, Long locationId, Long userId, Date[] freqDates);

	List<Requirement> getClientJobsByOrgRelationId(Long orgRelationid, Long locationId, Long userId, Date[] freqDates, Long spocId);

	Map<String, Integer> getSubmissionListByRequirementId(Long requirementId, Long locationId, Long userId, Date[] freqDates);

	List<Contracts> getClientBasedContractsLists(OrgLocations orgLocation, User user, String userRoleName,Long orgRelationId, ContractReportsDTO contractReportsDTO) throws Exception;

}
