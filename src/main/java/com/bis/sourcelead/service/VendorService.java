package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.VendorOnboardDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface VendorService {

	public OrgLocations updateVendor(ClientDTO clientDTO,AddUserDTO addUserDTO,CandidateDTO candidateDTO,User user,OrgLocations orgLocations,Long orgRelationId,Org org) throws Exception;
	
	VendorOnboardDTO getVendorOnboardDetails(Long vendorOnboardId,String notificationUUID) throws Exception;
}
