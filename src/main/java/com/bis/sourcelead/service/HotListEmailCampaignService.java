package com.bis.sourcelead.service;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface HotListEmailCampaignService {

	public void sendHotListEmailCampaign(JSONObject jsonObj,User user,OrgLocations orgLocation,MultipartFile multiFile) throws Exception;
}
