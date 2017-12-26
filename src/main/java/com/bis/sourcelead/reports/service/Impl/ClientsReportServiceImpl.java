package com.bis.sourcelead.reports.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.dao.ClientReportDao;
import com.bis.sourcelead.reports.service.ClientReportService;

import net.didion.jwnl.data.list.PointerTargetTreeNodeList.Operation;

@Service
public class ClientsReportServiceImpl implements ClientReportService{

	@Autowired
	ClientReportDao clientReportDao;
	
	@Override
	public Map<String, Integer> getAllClientsCountByOrgLocationIdAndUserId(Long locationId, Long userId)
	{
		return clientReportDao.getAllClientsCountByOrgLocationIdAndUserId(locationId, userId);
	}

	@Override
	public Map<String, Integer> getAllClientsSpocCountByOrgLocationIdAndUserId(Long locationId, Long userId)
	{
		Map<String, Integer> spocCount = new HashMap<>();
		List<OrgRelations> orgRelationsList = getAllClientsByOrgLocationIdAndUserId(locationId, userId);
		int totalactiveCount = 0;
		int totalInactiveCount = 0;
		if(orgRelationsList != null){
			for(OrgRelations rel : orgRelationsList){
				Map<String, Integer> contactCount = clientReportDao.getAllClientsSpocCountByOrgLocationIdAndUserId(rel.getId(),locationId, userId);
				int spocActiveCount = contactCount.get("spocActiveCount");
				int spocInactiveCount = contactCount.get("spocInactiveCount");
				totalactiveCount += spocActiveCount;
				totalInactiveCount += spocInactiveCount;
			}
		}
		spocCount.put("totalactiveCount", totalactiveCount);
		spocCount.put("totalInactiveCount", totalInactiveCount);
		spocCount.put("totalSpocCount", totalactiveCount+totalInactiveCount);
		return spocCount;
	}

	@Override
	public List<OrgRelations> getAllClientsByOrgLocationIdAndUserId(Long locationId, Long userId)
	{
		return clientReportDao.getAllClientsByOrgLocationIdAndUserId(locationId, userId);
	}

	@Override
	public Map<String, Integer> getContractsListOFAllClients(Long orgLocationId, User user,String userRoleName,Date startDate,Date endDate)
	{
		Map<String, Integer> conCount = new HashMap<>();
		List<OrgRelations> orgRelationsList = getAllClientsByOrgLocationIdAndUserId(orgLocationId, user.getId());
		List<Contracts> contractList = new ArrayList<>();
		for(OrgRelations orgRelation : orgRelationsList){
			List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocationId, user,orgRelation.getId(),userRoleName,startDate,endDate);
//			contractList.add(conList.size());
			if(conList != null && conList.size() > 0){
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocationId.longValue()){
					conCount.put(orgRelation.getResponderLocationId().getOrgLocationName(), conList.size());
				}else{
					conCount.put(orgRelation.getLocationId().getOrgLocationName(), conList.size());
				}
			}
		}
		return conCount;
	}

}
