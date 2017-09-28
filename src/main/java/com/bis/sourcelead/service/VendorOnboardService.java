package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;

public interface VendorOnboardService {

	VendorOnboarding saveVendorOnboarding(OrgLocations vendorOrgLocations, OrgLocations orgLocations,User user) throws Exception;
	
	VendorOnboarding getVendorOnboardingByOrgRelationId(Long orgRelationId) throws Exception;
	
	VendorOnboarding getVendorOnboardingById(Long id) throws Exception;
}
