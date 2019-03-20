package com.bis.sourcelead.service.impl.performance;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceTagCompetenciesDao;
import com.bis.sourcelead.dto.performance.PerformanceTagCompetenciesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceTagCompetencies;
import com.bis.sourcelead.service.performance.PerformanceTagCompetenciesService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceTagCompetenciesServiceImpl  implements PerformanceTagCompetenciesService{
	
	@Autowired
	PerformanceTagCompetenciesDao performanceTagCompetenciesDao;

	@Override
	public PerformanceTagCompetencies savePerformanceTagCompetencies(
			PerformanceTagCompetencies performanceTagCompetencies) throws Exception {
		return performanceTagCompetenciesDao.savePerformanceTagCompetencies(performanceTagCompetencies);
	}

	@Override
	public PerformanceTagCompetencies storePerformanceTagCompetencies(
			PerformanceTagCompetenciesDTO performanceTagCompetenciesDTO, Long orgLocationId, Long userId)
			throws Exception {
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(orgLocationId);
		User user = new User();
		user.setId(userId);
		
		PerformanceTagCompetencies tagCometencies = new PerformanceTagCompetencies();
		tagCometencies.setCompetencies(performanceTagCompetenciesDTO.getPerformanceCompetence());
		tagCometencies.setRoles(performanceTagCompetenciesDTO.getCompetenceRole());
		tagCometencies.setCompetenciesTaggingJsonString(performanceTagCompetenciesDTO.getJsonCompetenceString());
		tagCometencies.setCreatedBy(user);
		tagCometencies.setCreatedDate(new Date());
		tagCometencies.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		savePerformanceTagCompetencies(tagCometencies);
		
		return tagCometencies;
	}

}
