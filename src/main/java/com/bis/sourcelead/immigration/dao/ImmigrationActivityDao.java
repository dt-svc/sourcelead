package com.bis.sourcelead.immigration.dao;

import java.util.List;

import com.bis.sourcelead.entity.ImmigrationActivityHistory;
/**
 * @author Srinivas.Sabbu
 *
 */
public interface ImmigrationActivityDao {
	
	public ImmigrationActivityHistory storeImmigrationActivities(ImmigrationActivityHistory immigrationActivityHistory) throws Exception;

	public List<ImmigrationActivityHistory> getImmigrationActivitiesList(Long immigrationId, Long orgLocationId) throws Exception;


}
