package com.bis.sourcelead.clockInOutService;

import com.bis.sourcelead.clockInOutDTO.ClockInOutConfigDTO;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;
import com.bis.sourcelead.entity.OrgLocations;

public interface ClockInOutConfigService {
	
	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfigDTO clockInOutConfigDTO, OrgLocations loginOrgLocation, Long userId);

	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId);
}
