package com.bis.sourcelead.reports.dao;

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

public interface ClientReportDao {

	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long relationId ,Long locationId, Long userId);

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);

	List<Contracts> getContractsListOFAllClients(OrgLocations orgLocations, User user, Long relationId, String userRoleName,ContractReportsDTO contractReportsDTO) throws Exception;

	List<Invoice> getClientBasedInvoiceLists(Long orgRelationId, Long orgLocId, String invoiceType, Long contactUserId,Date[] freqDates);

	List<OrgSpoc> getClientSpocListByOrgRelationid(Long orgRelationid, Long locationId, Long userId, Date[] freqDates);

	List<Requirement> getClientJobsByOrgRelationId(Long orgRelationid, Long locationId, Long userId, Date[] freqDates, Long spocId);

	Map<String, Integer> getSubmissionListByRequirementId(Long requirementId, Long orgLocationId, Long userId, Date[] freqDates);

}
