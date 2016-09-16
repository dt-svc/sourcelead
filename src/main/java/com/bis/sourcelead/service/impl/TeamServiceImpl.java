package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TeamDao;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author sarath kumar
 *
*/

@Service
public class TeamServiceImpl implements TeamService {
	
	@Autowired
	TeamDao teamDao;
	
	@Autowired
	CommonUtil commonUtil;

	 /**
	   * @author sarath kumar
	   * @since  sep 14 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting TeamList By OrgLocationID
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	 */
	
	@Override
	public List<Team> getTeamListByOrgLocationID(Long orgLocationId) throws Exception {
	   return teamDao.getTeamListByOrgLocationID(orgLocationId);
	}
	
}
	 
