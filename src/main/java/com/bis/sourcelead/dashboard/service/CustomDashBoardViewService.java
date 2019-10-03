package com.bis.sourcelead.dashboard.service;

import java.util.List;

import com.bis.sourcelead.dashboard.dto.ConfigureDashBoardViewDTO;

public interface CustomDashBoardViewService {

	List<ConfigureDashBoardViewDTO> getConfiguredDataByDashBoardId(Long dashBoardId);
}
