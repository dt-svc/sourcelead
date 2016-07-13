package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.CitiesDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Cities;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CitiesService;
import com.bis.sourcelead.service.OrgLocationService;

@Service
public class OrgLocationsServiceImpl implements OrgLocationService {

	@Autowired
	OrgLocationsDao orgLocationsDao;

	@Override
	public OrgLocations addOrgLocations(OrgLocations orgLocations) throws Exception {
		// TODO Auto-generated method stub
		return orgLocationsDao.addOrgLocations(orgLocations);
	}

	@Override
	public OrgLocations updateOrgLocations(OrgLocations orgLocations) throws Exception {
		// TODO Auto-generated method stub
		return orgLocationsDao.updateOrgLocations(orgLocations);
	}

	@Override
	public OrgLocations getOrgLocations(Long id) throws Exception {
		// TODO Auto-generated method stub
		return orgLocationsDao.getOrgLocations(id);
	}

	@Override
	public Boolean deleteOrgLocations(Long id) throws Exception {
		// TODO Auto-generated method stub
		return orgLocationsDao.deleteOrgLocations(id);
		
	}

	@Override
	public List<OrgLocations> getAllOrgLocations() throws Exception {
		// TODO Auto-generated method stub
		return orgLocationsDao.getAllOrgLocations();
	}
	
	
	@Override
	public OrgLocations getOrgLocationsByLocationCode(String  locationCode) throws Exception {
		// TODO Auto-generated method stub
		return orgLocationsDao.getOrgLocationsByLocationCode(locationCode);
	}
	
}
