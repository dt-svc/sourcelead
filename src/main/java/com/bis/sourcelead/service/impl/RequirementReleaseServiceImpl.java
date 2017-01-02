package com.bis.sourcelead.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.RequirementReleaseService;

@Service
public class RequirementReleaseServiceImpl implements RequirementReleaseService{
	

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
}
