package com.bis.sourcelead.service.performance;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;

public interface PerformanceAppraisalService {

	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
}
