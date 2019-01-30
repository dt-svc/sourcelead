package com.bis.sourcelead.couchbase;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserDashboardCountsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	List<Map<Long, UserLocationStatsCountDTO>> userProfileLocationsList;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Map<Long, UserLocationStatsCountDTO>> getUserProfileLocationsList() {
		return userProfileLocationsList;
	}

	public void setUserProfileLocationsList(List<Map<Long, UserLocationStatsCountDTO>> userProfileLocationsList) {
		this.userProfileLocationsList = userProfileLocationsList;
	}

	
}
