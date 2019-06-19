package com.bis.sourcelead.dao.performance;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.performance.PerformanceAppraisalTemplateDTO;

public interface PerformanceAppraisalDao {

	ResponseList<PerformanceAppraisalTemplateDTO> getAssignedMyAppraisalList(PerformanceAppraisalTemplateDTO performanceAppraisalTemplateDTO,Long userId,Long orgLocationId,String dateFormat) throws Exception;
}
