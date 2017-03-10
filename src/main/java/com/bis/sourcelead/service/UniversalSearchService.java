package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.User;

public interface UniversalSearchService {

	List<Object> univesalSearch(OrgLocations orgLocation, String searchType, String searchValue, Org org,String userRole, User user) throws Exception;

	List<List<Object>> univesalSearchForAll(OrgLocations orgLocation, String searchType, String searchValue, Org org,String userRole, User user, Collection<Tabs> tabList)throws Exception;

	
}
