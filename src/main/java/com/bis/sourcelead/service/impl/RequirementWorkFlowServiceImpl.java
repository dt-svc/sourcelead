package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementWorkFlowDao;
import com.bis.sourcelead.dto.RequirementWorkFlowDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementWorkFlow;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementWorkFlowService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class RequirementWorkFlowServiceImpl implements RequirementWorkFlowService {
	private static final Logger logger = LoggerFactory.getLogger(RequirementWorkFlowServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	RequirementWorkFlowDao requirementWorkFlowDao;

	@Override
	public RequirementWorkFlow saveRequirementWorkFlowDetails(RequirementWorkFlowDTO requirementWorkFlowDTO, User user,
			OrgLocations orgLocations) throws Exception {
			RequirementWorkFlow requirementWorkFlow = null;
			try {
				if(StringUtils.isNotBlank(requirementWorkFlowDTO.getReqWorkFlowId())) {
					requirementWorkFlow =requirementWorkFlowDao.getRequirementWorkFlowById(Long.parseLong(requirementWorkFlowDTO.getReqWorkFlowId()));
				}else {
					requirementWorkFlow = new RequirementWorkFlow();
				}
				requirementWorkFlow = saveRequirementWorkFlowDetailsInfo(requirementWorkFlowDTO,user,orgLocations);
	
			} catch (Exception e) {
				logger.error("Exception caugth in savePetitionDetails service" + e.getMessage());
				e.printStackTrace();
			}
	
			  return requirementWorkFlow;
		}
	
	
   private RequirementWorkFlow saveRequirementWorkFlowDetailsInfo(RequirementWorkFlowDTO requirementWorkFlowDTO, User user, OrgLocations orgLocations) throws Exception {
		
	   RequirementWorkFlow reqWorkFlow = null;
		Date currentDate = commonUtil.orgLocationCommonDate(new Date(), orgLocations);
		if (requirementWorkFlowDTO.getReqWorkFlowId() != null && StringUtils.isNotEmpty(requirementWorkFlowDTO.getReqWorkFlowId())) {
			reqWorkFlow = requirementWorkFlowDao.getRequirementWorkFlowById(Long.parseLong(requirementWorkFlowDTO.getReqWorkFlowId()));
		} else {
			reqWorkFlow = new RequirementWorkFlow();
			reqWorkFlow.setCreatedBy(user);
			reqWorkFlow.setCreatedDate(currentDate);
		}
		if(orgLocations != null){
			reqWorkFlow.setOrgLocationId(orgLocations);
			}
		reqWorkFlow.setWorkFlowName(requirementWorkFlowDTO.getWorkFlowName());
		reqWorkFlow.setDescription(requirementWorkFlowDTO.getDescription());
		reqWorkFlow.setNoOfStages(Integer.parseInt(requirementWorkFlowDTO.getNoOfStages()));
		reqWorkFlow.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
		if(requirementWorkFlowDTO.getStatus().equalsIgnoreCase("Active")) {
			reqWorkFlow.setStatus(Constants.RECORD_ACTIVE);
		}else {
			reqWorkFlow.setStatus(Constants.RECORD_INACTIVE);
		}
		reqWorkFlow.setUpdatedBy(user);
		reqWorkFlow.setUpdatedDate(currentDate);

		return reqWorkFlow;
		
	}
	
}
















