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
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reports.dao.ClientReportDao;
import com.bis.sourcelead.reports.service.ClientReportService;
import com.bis.sourcelead.reportsDTO.ContractReportsDTO;

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
	public Map<String, Integer> getContractsListOFAllClients(Long orgLocationId, User user,String userRoleName,ContractReportsDTO contractReportsDTO)
	{
		Map<String, Integer> conCount = new HashMap<>();
		List<OrgRelations> orgRelationsList = getAllClientsByOrgLocationIdAndUserId(orgLocationId, user.getId());
		List<Contracts> contractList = new ArrayList<>();
		int totalMsaCount = 0;
		int totalSowCount = 0;
		int totalNdaCount = 0;
		int totalNsaCount = 0;
		for(OrgRelations orgRelation : orgRelationsList){
			List<Contracts> conList = clientReportDao.getContractsListOFAllClients(orgLocationId, user,orgRelation.getId(),userRoleName,contractReportsDTO);
//			contractList.add(conList.size());
			if(conList != null && conList.size() > 0){
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocationId.longValue()){
					conCount.put(orgRelation.getResponderLocationId().getOrgLocationName(), conList.size());
				}else{
					conCount.put(orgRelation.getLocationId().getOrgLocationName(), conList.size());
				}
				for(Contracts con : conList){
					if(con.getContractType().contains("MSA")){
						totalMsaCount += 1;	
					}else if(con.getContractType().contains("SOW")){
						totalSowCount += 1;
					}else if(con.getContractType().contains("NDA")){
						totalNdaCount += 1;
					}else if(con.getContractType().contains("NSA")){
						totalNsaCount += 1;
					}
				}
				conCount.put("MsaCount",totalMsaCount);
				conCount.put("SowCount",totalSowCount);
				conCount.put("NdaCount",totalNdaCount);
				conCount.put("NsaCount",totalNsaCount);
			}
		}
		return conCount;
	}

	@Override
	public List<OrgSpoc> getClientSpocListByOrgRelationid(Long orgRelationid, Long locationId, Long userId,Date[] freqDates)
	{
		return clientReportDao.getClientSpocListByOrgRelationid(orgRelationid, locationId, userId,freqDates);
	}

	@Override
	public List<Requirement> getClientJobsByOrgRelationId(Long orgRelationid, Long locationId, Long userId, Date[] freqDates,Long spocId)
	{
		return clientReportDao.getClientJobsByOrgRelationId(orgRelationid,locationId,userId,freqDates,spocId);
	}

	@Override
	public Map<String, Integer> getSubmissionListByRequirementId(Long requirementId, Long orgLocationId, Long userId, Date[] freqDates)
	{
		return clientReportDao.getSubmissionListByRequirementId(requirementId,orgLocationId,userId,freqDates);
	}

}
