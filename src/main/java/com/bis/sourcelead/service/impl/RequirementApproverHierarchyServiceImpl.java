package com.bis.sourcelead.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.service.RequirementApproverHierarchyService;

@Service
public class RequirementApproverHierarchyServiceImpl implements RequirementApproverHierarchyService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementApproverHierarchyServiceImpl.class);

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
}
