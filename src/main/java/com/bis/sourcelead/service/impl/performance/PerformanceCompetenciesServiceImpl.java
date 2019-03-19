package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceCompetenciesDao;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceCompetencies;
import com.bis.sourcelead.service.performance.PerformanceCompetenciesService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class PerformanceCompetenciesServiceImpl implements PerformanceCompetenciesService{
	
	@Autowired
	PerformanceCompetenciesDao performanceCompetenciesDao;

	@Override
	public PerformanceCompetencies savePerformanceCompetencies(PerformanceCompetencies performanceCompetencies)
			throws Exception {
		return performanceCompetenciesDao.savePerformanceCompetencies(performanceCompetencies);
	}

	@Override
	public List<PerformanceCompetenciesDTO> getPerformanceCompetenciesListByOrgLocationId(Long orgLocationId)
			throws Exception {
		return performanceCompetenciesDao.getPerformanceCompetenciesListByOrgLocationId(orgLocationId);
	}

	@Override
	public PerformanceCompetencies storePerformanceCompetencies(PerformanceCompetenciesDTO performanceCompetenciesDTO,
			Long orgLocationId, Long userId) throws Exception {
		
		PerformanceCompetencies performanceCompetencies = new PerformanceCompetencies();
		performanceCompetencies.setCompetenciesName(performanceCompetenciesDTO.getName());
		performanceCompetencies.setDescription(performanceCompetenciesDTO.getDescription());
		performanceCompetencies.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		OrgLocations orgLcoations = new OrgLocations();
		orgLcoations.setId(orgLocationId);
		performanceCompetencies.setOrgLocationId(orgLcoations);
		User user = new User();
		user.setId(userId);
		performanceCompetencies.setCreatedBy(user);
		performanceCompetencies.setStatus(Constants.RECORD_ACTIVE);
		performanceCompetencies.setCreatedDate(new Date());
		return performanceCompetenciesDao.savePerformanceCompetencies(performanceCompetencies);
		
	}

}
