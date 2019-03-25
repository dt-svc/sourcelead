package com.bis.sourcelead.service.impl.performance;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.performance.PerformanceCompetenciesDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceCompetenciesDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.performance.PerformanceBadges;
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
		
		
		OrgLocations orgLcoations = new OrgLocations();
		orgLcoations.setId(orgLocationId);
		
		User user = new User();
		user.setId(userId);
		
		PerformanceCompetencies performanceCompetencies = null;
		if(performanceCompetenciesDTO.getCompetenceId() != null) {
			performanceCompetencies = performanceCompetenciesDao.getPerformanceCompetenciesById(performanceCompetenciesDTO.getCompetenceId());
			performanceCompetencies.setUpdatedBy(user);
			performanceCompetencies.setUpdatedDate(new Date());
		}else {
			performanceCompetencies = new PerformanceCompetencies();
			performanceCompetencies.setCreatedBy(user);
			performanceCompetencies.setCreatedDate(new Date());
			performanceCompetencies.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		}
		performanceCompetencies.setCompetenciesName(performanceCompetenciesDTO.getName());
		performanceCompetencies.setDescription(performanceCompetenciesDTO.getDescription());
		performanceCompetencies.setOrgLocationId(orgLcoations);
		performanceCompetencies.setStatus(Constants.RECORD_ACTIVE);
		return performanceCompetenciesDao.savePerformanceCompetencies(performanceCompetencies);
		
	}

	@Override
	public ResponseList<PerformanceCompetenciesDTO> getPerformanceCompetenciesList(
			PerformanceCompetenciesDTO performanceCompetenciesDTO, String dateFormat, Long orgLocationId)
			throws Exception {
		return performanceCompetenciesDao.getPerformanceCompetenciesList(performanceCompetenciesDTO, dateFormat, orgLocationId);
	}

	@Override
	public PerformanceCompetenciesDTO getPerformanceCompetenciesDTOById(Long id) throws Exception {
		PerformanceCompetencies compentencies =  performanceCompetenciesDao.getPerformanceCompetenciesById(id);
		PerformanceCompetenciesDTO  competenciesDTO = new PerformanceCompetenciesDTO();
		competenciesDTO.setCompetenceId(compentencies.getId());
		competenciesDTO.setName(compentencies.getCompetenciesName());
		competenciesDTO.setDescription(compentencies.getDescription());
		return competenciesDTO;
	}

	@Override
	public PerformanceCompetencies getPerformanceCompetenciesById(Long id) throws Exception {
		PerformanceCompetencies compentencies =  performanceCompetenciesDao.getPerformanceCompetenciesById(id);
		return compentencies;
	}

	@Override
	public boolean isCompetenceNameExisted(Long orgLocationId, String competenceName) throws Exception {
		return performanceCompetenciesDao.isCompetenceNameExisted(orgLocationId, competenceName);
	}

}
