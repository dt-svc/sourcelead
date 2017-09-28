package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class VendorOnboardServiceImpl implements VendorOnboardService {
	
	@Autowired
	VendorOnboardDao vendorOnboardDao;

	@Override
	public VendorOnboarding saveVendorOnboarding(OrgLocations vendorOrgLocations, OrgLocations orgLocations, User user)
			throws Exception {
		
		VendorOnboarding vendorOnboarding = new VendorOnboarding();
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(vendorOrgLocations.getVendorOrgRelationId());
		vendorOnboarding.setOrgRelationId(orgRelations);
		vendorOnboarding.setVendorLocationId(vendorOrgLocations);
		User spocUser = new User();
		spocUser.setId(vendorOrgLocations.getVendorSpocId());
		vendorOnboarding.setSpocUserId(spocUser);
		vendorOnboarding.setVendorOnboardStatus("Draft");
		vendorOnboarding.setOnboardSubmitted(Constants.NO);
		vendorOnboarding.setOnboardUUID(SourceLeadRandomCodeHelper.generateUUID());
		vendorOnboarding.setOrgLocationsId(orgLocations);
		vendorOnboarding.setStatus(1);
		vendorOnboarding.setCreatedBy(user);
		vendorOnboarding.setUpdatedBy(user);
		vendorOnboarding.setCreatedDate(new Date());
		vendorOnboarding.setUpdatedDate(new Date());
		vendorOnboardDao.saveVendorOnboarding(vendorOnboarding);
		
		return vendorOnboarding;
		
	}

	@Override
	public VendorOnboarding getVendorOnboardingByOrgRelationId(Long orgRelationId) throws Exception {
		return vendorOnboardDao.getVendorOnboardingByOrgRelationId(orgRelationId);
	}

	@Override
	public VendorOnboarding getVendorOnboardingById(Long id) throws Exception {
		return vendorOnboardDao.getVendorOnboardingById(id);
	}

}
