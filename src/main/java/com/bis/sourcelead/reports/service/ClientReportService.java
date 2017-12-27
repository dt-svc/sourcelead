package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;

public interface ClientReportService {

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);
	
	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getContractsListOFAllClients(Long orgLocationId, User user, String userRoleName,ContractReportsDTO contractReportsDTO);

	List<OrgSpoc> getClientSpocListByOrgRelationid(Long orgRelationid, Long locationId, Long userId);

	List<Requirement> getClientJobsByOrgRelationId(Long orgRelationid, Long locationId, Long userId);

	Map<String, Integer> getSubmissionListByRequirementId(Long requirementId, Long locationId, Long userId);

}
