package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.JobListEmailCampaignDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface JobListEmailCampaignService {
	
	public void sendJobListEmailCampaign(JobListEmailCampaignDTO jobListEmailCampaignDTO,User user,OrgLocations orgLocation,MultipartFile multiFile) throws Exception;
	
}
