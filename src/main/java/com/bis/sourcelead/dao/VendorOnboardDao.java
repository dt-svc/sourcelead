package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.VendorOnboarding;

public interface VendorOnboardDao {

	VendorOnboarding saveVendorOnboarding(VendorOnboarding vendorOnboarding) throws Exception;
	
	VendorOnboarding getVendorOnboardingByOrgRelationId(Long orgRelationId) throws Exception;
	
	VendorOnboarding getVendorOnboardingById(Long id) throws Exception;
	
	VendorOnboarding getVendorOnboardingByUUID(String onboardUUID) throws Exception;
	
	VendorOnboarding getVendorOnboardingByOrgLocationIdAndVendorLocationId(Long orgLocationId,Long vendorLocationId) throws Exception;
	
	String getVendorOnboardingUUIDByOrgRelationId(Long orgRelationId);
}
