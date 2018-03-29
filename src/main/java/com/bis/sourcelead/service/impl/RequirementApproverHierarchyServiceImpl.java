package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementApproverHierarchyDao;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RequirementApproverHierarchyServiceImpl implements RequirementApproverHierarchyService{
	

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	private RequirementApproverHierarchyDao requirementApproverHierarchyDao;
	
	@Autowired
	 private CommonUtil commonUtil;

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
			requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
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

	@Override
	public Boolean deleteTheRequirementApproverByRequirementIdAndApproverId(Long userId,Long requirementId)
			throws Exception {
		RequirementApproverHierarchy rah = requirementApproverHierarchyDao.getRequirementApproverHierarchyByRequirementId(requirementId, userId);
		return requirementApproverHierarchyDao.deleteTheRequirementApproverByRequirementIdAndApproverId(rah);
	}

	@Override
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(
			Long requirementId) throws Exception {
		
		return requirementApproverHierarchyDao.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
	}

	@Override
	public List<RequirementApproverHierarchy> getRequirementsAsApprover(Long userId, Long orgLocationId)
			throws Exception {
		return requirementApproverHierarchyDao.getRequirementsAsApprover(userId, orgLocationId);
	}

	@Override
	public RequirementApproverHierarchy getApproverHierarchyByRequirementId(Long requirementId) {
		return requirementApproverHierarchyDao.getApproverHierarchyByRequirementId(requirementId);
	}
	
	@Override
	public RequirementApproverHierarchy getRejectedApproverHierarchyByRequirementId(Long requirementId) {
		return requirementApproverHierarchyDao.getRejectedApproverHierarchyByRequirementId(requirementId);
	}

	@Override
	public RequirementApproverHierarchy getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(
			Long requirementApproverHierarchyId, Long requirementId) throws Exception {
		   return requirementApproverHierarchyDao.getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(requirementApproverHierarchyId,requirementId);
	}
	
}
