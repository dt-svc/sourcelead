package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.WhiteListIPAddressDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.WhiteListIPAddress;

public interface WhiteListIpAddressService {
	
	public WhiteListIPAddress addWhiteListIps(WhiteListIPAddressDTO whiteListIPAddressDTO, OrgLocations orgLocations, User user) throws Exception;

	public List<WhiteListIPAddressDTO> getWhiteListIpsByLocationId(Long locationId,Long whiteListIpId);

	public WhiteListIPAddress getWhiteListIpsById(Long whiteListIpId);

	public boolean validateIPAddress(String ipAddress, UserRole userRole, Long whiteListId, String accessingType);

}