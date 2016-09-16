package com.bis.sourcelead.dao;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;

public interface TeamDao {

	public List<Team>   getTeamListByOrgLocationID(Long orgLocationId) throws Exception;

}
