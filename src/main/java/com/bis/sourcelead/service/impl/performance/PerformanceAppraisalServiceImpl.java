package com.bis.sourcelead.service.impl.performance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.performance.PerformanceAppraisalDao;
import com.bis.sourcelead.dto.PerformanceFeedbackTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalDetailsDTO;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;
import com.bis.sourcelead.service.performance.PerformanceAppraisalService;

@Service
public class PerformanceAppraisalServiceImpl implements PerformanceAppraisalService{

	@Autowired
	PerformanceAppraisalDao performanceAppraisalDao;
	
	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId, Long orgLocationId,String dateFormat)
			throws Exception {
		return performanceAppraisalDao.getAssignedMyAppraisalList(performanceAppraisalTemplateDTO,userId, orgLocationId, dateFormat);
	}

	@Override
	public ResponseList<PerformanceAppraisalTemplateDTO> getAssignedPeersAppraisalList(
			PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO, Long userId, Long orgLocationId,
			String dateFormat) throws Exception {
		return performanceAppraisalDao.getAssignedPeersAppraisalList(performanceAppraisalTemplateDTO, userId, orgLocationId, dateFormat);
	}

	@Override
	public PerformanceAppraisalDetailsDTO getSelfAppraisalTemplateDetails(String assignedUUID, String dateFormat)
			throws Exception {
		return performanceAppraisalDao.getSelfAppraisalTemplateDetails(assignedUUID, dateFormat);
	}

	@Override
	public List<PerformanceFeedbackTemplateDTO> getPerformanceAssignedFeedbackTemplateDTOList(String assignedUUID)
			throws Exception {
		return performanceAppraisalDao.getPerformanceAssignedFeedbackTemplateDTOList(assignedUUID);
	}

	@Override
	public PerformanceFeedbackTemplateDTO getPerformanceFeedbackTemplateDTOByUUID(String uuid) throws Exception {
		return performanceAppraisalDao.getPerformanceFeedbackTemplateDTOByUUID(uuid);
	}

}
