package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.HotListEmailCampaignDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmailService {

	public void sendEmail(HotListEmailCampaignDTO hotListEmailCampaignDTO,User user,OrgLocations orgLocation) throws Exception;
}
