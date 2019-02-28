package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.OnboardTasksDTO;
import com.bis.sourcelead.dto.VendorOnboardDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;

public interface VendorOnboardService {

	VendorOnboarding saveVendorOnboarding(OrgLocations vendorOrgLocations, OrgLocations orgLocations,User user) throws Exception;
	
	VendorOnboarding getVendorOnboardingByOrgRelationId(Long orgRelationId) throws Exception;
	
	VendorOnboarding getVendorOnboardingById(Long id) throws Exception;
	
	void finishVendorOnboarding(OnboardTasksDTO onboardTasksDTO,User user,OrgLocations orgLocations) throws Exception;
	
	VendorOnboarding getVendorOnboardingByUUID(String onboardUUID) throws Exception;
	
	void saveVendorOnboardDetails(VendorOnboardDTO vendorOnboardDTO,Long vendorOnboardId,User user) throws Exception;
	
	void saveVendorOnboardDocuments(VendorOnboardDTO vendorOnboardDTO) throws Exception;
	
	void finishVendorOnboardProcess(Long vendorOnboardId,String notificationUUID,User user,String actionType) throws Exception;
	
	VendorOnboarding getVendorOnboardingByOrgLocationIdAndVendorLocationId(Long orgLocationId,Long vendorLocationId) throws Exception;
	
	void sendMailAndNotificationToVendorContact(VendorOnboarding vendorOnboarding,OrgLocations vendorOrgLocations, OrgLocations orgLocations,User user,User spocUser, OrgSpoc orgSpoc) throws Exception;
	
	void saveVendorOnboardForVendorBulkImports(Long vendorOnboardingId,User user,User spocUser,OrgLocations orgLocations) throws Exception;
	
	String getVendorOnboardingUUIDByOrgRelationId(Long orgRelationId);
	
}
