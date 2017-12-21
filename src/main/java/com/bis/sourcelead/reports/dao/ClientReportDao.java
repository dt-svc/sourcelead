package com.bis.sourcelead.reports.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgRelations;

public interface ClientReportDao {

	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long relationId ,Long locationId, Long userId);

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);

}
