package com.bis.sourcelead.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.RequirementAssignToService;

@Service
public class RequirementAssignToServiceImpl implements RequirementAssignToService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementAssignToServiceImpl.class);

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
}
