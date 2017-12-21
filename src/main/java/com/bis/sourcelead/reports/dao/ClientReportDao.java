package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;

public interface ClientReportDao {

	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long relationId ,Long locationId, Long userId);

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);

	List<Contracts> getContractsListOFAllClients(Long orgLocationId, User user, Long relationId, String userRoleName, Date startDate, Date endDate);

}
