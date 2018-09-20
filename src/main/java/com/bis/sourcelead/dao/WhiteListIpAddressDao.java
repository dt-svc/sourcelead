package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.WhiteListIPAddress;

public interface WhiteListIpAddressDao {

	WhiteListIPAddress addWhiteListIps(WhiteListIPAddress whiteListIPAddress) throws Exception;

	List<WhiteListIPAddress> getWhiteListIpsByLocationId(Long locationId, Long whiteListIpId);

	WhiteListIPAddress getWhiteListIpsById(Long whiteListIpId);

}