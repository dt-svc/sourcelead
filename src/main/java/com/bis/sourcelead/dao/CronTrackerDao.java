package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.CronTracker;

/**
 * 
 * @author rajat rana
 *
 */

public interface CronTrackerDao {

	CronTracker saveCronTracker(CronTracker cronTracker)throws Exception;
	
}
