package com.bis.sourcelead.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;

@Service
public class RequirementApproverHierarchyServiceImpl implements RequirementApproverHierarchyService{
	
	private static final Logger logger = LoggerFactory.getLogger(RequirementApproverHierarchyServiceImpl.class);

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private RequirementApproverHierarchyDao requirementApproverHierarchyDao;
	

	@Override
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyList(Long requirementId,Long approverUserId) throws Exception {
		return requirementApproverHierarchyDao.getRequirementApproverHierarchyList(requirementId, approverUserId);
	}

	@Override
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementId(Long requirementId,Long approverUserId) throws Exception {
		
		return requirementApproverHierarchyDao.getRequirementApproverHierarchyByRequirementId(requirementId,approverUserId);
		
	}

	@Override
	public RequirementApproverHierarchy addRequirementApproverHierarchy(
			RequirementApproverHierarchy requirementApproverHierarchy) throws Exception {
			return requirementApproverHierarchyDao.storeRequirementApproverHierarchy(requirementApproverHierarchy);
	}

	@Override
	public Integer getRequirementApproverHierarachyWithHighestLevel(Long requirementId)
			throws Exception {
		return requirementApproverHierarchyDao.getRequirementApproverHierarachyWithHighestLevel(requirementId);

	}

	@Override
	public List<RequirementApproverHierarchy> getRequirementApproverHierarchyListByRequirementId(Long requirementId)
			throws Exception {
		
		   return requirementApproverHierarchyDao.getRequirementApproverHierarchyListByRequirementId(requirementId);

	}
	
}
