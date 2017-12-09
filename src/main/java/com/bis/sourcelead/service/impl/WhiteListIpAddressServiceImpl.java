package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.IPAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.WhiteListIpAddressDao;
import com.bis.sourcelead.dto.WhiteListIPAddressDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.WhiteListIPAddress;
import com.bis.sourcelead.service.WhiteListIpAddressService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class WhiteListIpAddressServiceImpl implements WhiteListIpAddressService {

	private static final Logger logger = LoggerFactory.getLogger(WhiteListIpAddressServiceImpl.class);

	@Autowired
	WhiteListIpAddressDao whiteListIpAddressDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Override
	public WhiteListIPAddress addWhiteListIps(WhiteListIPAddressDTO whiteListIPAddressDTO, OrgLocations orgLocations,User user) throws Exception
	{
		WhiteListIPAddress ipAdddress = new WhiteListIPAddress();
		ipAdddress.setFromIpAddress(whiteListIPAddressDTO.getFromIpAddress());
		ipAdddress.setToIpAddress(whiteListIPAddressDTO.getToIpAddress());
		ipAdddress.setOrgLocation(orgLocations);
		ipAdddress.setStatus(Constants.ACTIVE);
		ipAdddress.setCreatedBy(user);
		ipAdddress.setCreatedDate(new Date());
		ipAdddress.setUpdatedBy(user);
		ipAdddress.setUpdatedDate(new Date());
		return whiteListIpAddressDao.addWhiteListIps(ipAdddress);
	}

	@Override
	public List<WhiteListIPAddressDTO> getWhiteListIpsByLocationId(Long locationId)
	{
		List<WhiteListIPAddressDTO> ipDtoList = new ArrayList<>();
		List<WhiteListIPAddress> ipList = whiteListIpAddressDao.getWhiteListIpsByLocationId(locationId);
		if(ipList != null){
			for(WhiteListIPAddress ip : ipList){
				WhiteListIPAddressDTO wlIpDto = new WhiteListIPAddressDTO();
				wlIpDto.setId(ip.getId());
				wlIpDto.setFromIpAddress(ip.getFromIpAddress());
				wlIpDto.setToIpAddress(ip.getToIpAddress());
				wlIpDto.setStatus(ip.getStatus());
				StringBuilder sb = new StringBuilder();
				if(StringUtils.isNotBlank(ip.getCreatedBy().getLastName()))
					wlIpDto.setCreatedBy(sb.append(ip.getCreatedBy().getFirstName()).append(" ").append(ip.getCreatedBy().getLastName()).toString());
				else
					wlIpDto.setCreatedBy(sb.append(ip.getCreatedBy().getFirstName()).toString());
				StringBuilder updatedUsersb = new StringBuilder();
				if(StringUtils.isNotBlank(ip.getUpdatedBy().getLastName()))
					wlIpDto.setUpdatedBy(updatedUsersb.append(ip.getUpdatedBy().getFirstName()).append(" ").append(ip.getUpdatedBy().getLastName()).toString());
				else
					wlIpDto.setUpdatedBy(sb.append(ip.getUpdatedBy().getFirstName()).toString());
				wlIpDto.setUpdatedDate(commonUtil.dateStrFromOrgDateFormatter(ip.getOrgLocation().getDateFormat(), ip.getUpdatedDate()));
				wlIpDto.setCreatedDate(commonUtil.dateStrFromOrgDateFormatter(ip.getOrgLocation().getDateFormat(), ip.getCreatedDate()));
				ipDtoList.add(wlIpDto);
			}
		}
		return ipDtoList;
	}
	
}
