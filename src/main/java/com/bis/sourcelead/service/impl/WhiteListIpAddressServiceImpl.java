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
import com.bis.sourcelead.entity.UserRole;
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
		WhiteListIPAddress ipAdddress;
		if(whiteListIPAddressDTO.getId() != 0){
			ipAdddress = getWhiteListIpsById(whiteListIPAddressDTO.getId());
		}else{
			ipAdddress = new WhiteListIPAddress();
		}
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
	public List<WhiteListIPAddressDTO> getWhiteListIpsByLocationId(Long locationId, Long whiteListIpId)
	{
		List<WhiteListIPAddressDTO> ipDtoList = new ArrayList<>();
		List<WhiteListIPAddress> ipList = whiteListIpAddressDao.getWhiteListIpsByLocationId(locationId,whiteListIpId);
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

	@Override
	public WhiteListIPAddress getWhiteListIpsById(Long whiteListIpId) {
		return whiteListIpAddressDao.getWhiteListIpsById(whiteListIpId);
	}

	@Override
	public boolean validateIPAddress(String ipAddress,UserRole userRole,Long whiteListIpId,String accessingType)
	{
		List<WhiteListIPAddressDTO> ipList = getWhiteListIpsByLocationId(userRole.getOrgLocations().getId(),whiteListIpId);
		String[] currentIp = ipAddress.split("\\.");
		boolean matched = false;
		if("settings".equalsIgnoreCase(accessingType)){
			if(ipList.size() > 0){
				matched = validateEnteredIpAddress(ipList,currentIp);
			}else{
				matched = false;
			}
		}else{
			if(ipList.size() > 0){
				matched = validateEnteredIpAddress(ipList,currentIp);
			}else{
				matched = true;
			}
		}
		
		return matched;
	}
	
	private boolean validateEnteredIpAddress(List<WhiteListIPAddressDTO> ipList, String[] currentIp){
		boolean matched = false;
		for(WhiteListIPAddressDTO wlipDto : ipList){
			String fromip = wlipDto.getFromIpAddress();
			String toip = wlipDto.getToIpAddress();
			
			String[] fromipaddr = fromip.split("\\.");
			String[] toipaddr = toip.split("\\.");
			
			if((Integer.parseInt(currentIp[3]) >= Integer.parseInt(fromipaddr[3])) && (Integer.parseInt(currentIp[3]) <= Integer.parseInt(toipaddr[3]))){
				if(Integer.parseInt(currentIp[2]) >= Integer.parseInt(fromipaddr[2]) && (Integer.parseInt(currentIp[2]) <= Integer.parseInt(toipaddr[2]))){
					if(Integer.parseInt(currentIp[1]) >= Integer.parseInt(fromipaddr[1]) && (Integer.parseInt(currentIp[1]) <= Integer.parseInt(toipaddr[1]))){
						if(Integer.parseInt(currentIp[0]) >= Integer.parseInt(fromipaddr[0]) && (Integer.parseInt(currentIp[0]) <= Integer.parseInt(toipaddr[0]))){
							matched = true;
						}else{
							matched = false;
						}
					}else{
						matched = false;
					}
				}else{
					matched = false;
				}
			}else{
				matched = false;
			}
		}
		return matched;
	}
}
