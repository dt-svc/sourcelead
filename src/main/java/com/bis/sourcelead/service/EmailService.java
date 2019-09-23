package com.bis.sourcelead.service;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.HotListEmailCampaignDTO;
import com.bis.sourcelead.dto.ReadMailDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface EmailService {

	public void sendEmail(HotListEmailCampaignDTO hotListEmailCampaignDTO,User user,OrgLocations orgLocation,MultipartFile multiFile) throws Exception;

	public Response<Boolean> sendEmailFromMailBox(ReadMailDTO readMailDTO, long userId, long orgId, long orgLocationId, String staticPath);

//	public boolean replyEmailFromMailBox(ReadMailDTO readMailDTO, long id, long orgLocationId, MultipartFile multiFile);
}
