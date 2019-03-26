package com.bis.sourcelead.clockInOutDao;

import com.bis.sourcelead.clockInOutEntity.ClockInOut;
import com.bis.sourcelead.clockInOutEntity.ClockInOutConfig;

public interface ClockInOutConfigDao {

	public ClockInOutConfig saveClockInOutConfiguration(ClockInOutConfig clockInOut);

	public ClockInOutConfig getClockInOutConfigBasedOnOrgLocationId(Long orgLocationId);

	public ClockInOutConfig getClockInOutConfigBasedOnId(Long clockInOutId);

	public void saveClockInOutTime(ClockInOut clockInOut);

}
