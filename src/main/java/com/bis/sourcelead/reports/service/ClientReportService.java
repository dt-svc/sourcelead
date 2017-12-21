package com.bis.sourcelead.reports.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.OrgRelations;

public interface ClientReportService {

	List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId);
	
	Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId);

	Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long locationId, Long userId);

}
