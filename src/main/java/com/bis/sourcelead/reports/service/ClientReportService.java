package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;

public interface ClientReportService {

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);
	
	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	List<Contracts> getContractsListOFAllClients(Long orgLocationId, User user, String userRoleName, Date startDate, Date endDate);

}
