package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.UserDroppedHolidaysDao;
import com.bis.sourcelead.entity.UserDroppedHolidays;
import com.bis.sourcelead.service.UserDroppedHolidaysService;

@Service
public class UserDroppedHolidaysServiceImpl implements UserDroppedHolidaysService {

	
	@Autowired
	private UserDroppedHolidaysDao userDroppedHolidaysDao;
	
	@Value("${static.path}")
    private String staticPath;

	@Override
	public UserDroppedHolidays addUserDroppedHolidays(UserDroppedHolidays userDroppedHolidays) throws Exception {
		return userDroppedHolidaysDao.addUserDroppedHolidays(userDroppedHolidays);
	}

	@Override
	public List<Long> getDroppedHolidayIdsListByUserIdAndLocationId(Long userId, Long locationId) throws Exception {
		return userDroppedHolidaysDao.getDroppedHolidayIdsListByUserIdAndLocationId(userId, locationId);
	}

	@Override
	public UserDroppedHolidays getUserDroppedHolidaysFromUserIdlocationIdAndHolidayId(Long userId, Long locationId, Long holidayId)
			throws Exception {
		return userDroppedHolidaysDao.getUserDroppedHolidaysFromUserIdlocationIdAndHolidayId(userId, locationId, holidayId);
	}
	
	
}

