package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OfferLetterTemplateDao;
import com.bis.sourcelead.dto.OfferLetterTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OfferLetterTemplate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.service.OfferLetterTemplateService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;


@Service
public class OfferLetterTemplateServiceImpl implements OfferLetterTemplateService{

	@Autowired
	private OfferLetterTemplateDao offerLetterTemplateDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	LetterHeadService letterHeadService;

	@Override
	public OfferLetterTemplate saveOfferLetterTemplate(OfferLetterTemplateDTO offerLetterTemplateDTO) throws Exception {
		OfferLetterTemplate offerLetterTemplate = null;
		 if(offerLetterTemplateDTO.getTemplateUUID() != "" && offerLetterTemplateDTO.getTemplateUUID() != null) {
			 offerLetterTemplate = offerLetterTemplateDao.getOfferLetterTemplateDetailsByTemplateUUID(offerLetterTemplateDTO.getTemplateUUID());
			 offerLetterTemplate.setUpdatedBy(offerLetterTemplateDTO.getUserCode());
			 offerLetterTemplate.setUpdatedDate(new Date());
		 }else {
			 offerLetterTemplate = new OfferLetterTemplate();
		 }
		//Template Name
		offerLetterTemplate.setTemplateName(offerLetterTemplateDTO.getTemplateName());
		
		//Letter Head 
		if(offerLetterTemplateDTO.getLetterHead() != null && offerLetterTemplateDTO.getLetterHead() != "") {
			LetterHead letterHead = letterHeadService.getLetterheadByUUID(offerLetterTemplateDTO.getLetterHead());
			offerLetterTemplate.setLetterHeadId(letterHead);
		}else {
			offerLetterTemplate.setLetterHeadId(null);
		}
		
		
		//Description
		offerLetterTemplate.setDescription(offerLetterTemplateDTO.getDescription());
		
		//Template Content
		offerLetterTemplate.setTemplateContent(offerLetterTemplateDTO.getTemplateContent());
		
		//Template UUD
		offerLetterTemplate.setTemplateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		
		//Status
		offerLetterTemplate.setTemplateStatus(Constants.RECORD_ACTIVE);
		
		//Created By
		offerLetterTemplate.setCreatedBy(offerLetterTemplateDTO.getUserCode());
		
		//Created Date
		offerLetterTemplate.setCreatedDate(new Date());
		
		OrgLocations orgLocation = new OrgLocations();
		orgLocation.setId(offerLetterTemplateDTO.getOrgLocationId());
		offerLetterTemplate.setOrgLocationId(orgLocation);
		
		offerLetterTemplateDao.saveOfferLetterTemplate(offerLetterTemplate);
		return offerLetterTemplate;
	}

	@Override
	public ResponseList<OfferLetterTemplate> getAllOfferLetterTemplatesByOrgLocationid(OfferLetterTemplateDTO offerLetterTemplateDTO) throws Exception {
		return offerLetterTemplateDao.getAllOfferLetterTemplatesByOrgLocationid(offerLetterTemplateDTO);
	}

	@Override
	public OfferLetterTemplate getOfferLetterTemplateDetailsByTemplateUUID(String templateUUID) throws Exception {
		
		return offerLetterTemplateDao.getOfferLetterTemplateDetailsByTemplateUUID(templateUUID);
	}

	@Override
	public OfferLetterTemplate validateOfferLetterTemplateByTemplateUUIDAndTemplateName(String templateUUID, String templateName) throws Exception {
		return offerLetterTemplateDao.validateOfferLetterTemplateByTemplateUUIDAndTemplateName(templateUUID, templateName);
	}

	@Override
	public Boolean validateOfferLetterTemplateByOrgLocationIdAndTemplateName(Long orgLocationId, String templateName) throws Exception {
		return offerLetterTemplateDao.validateOfferLetterTemplateByOrgLocationIdAndTemplateName(orgLocationId, templateName);
	}

	@Override
	public List<OfferLetterTemplate> getOfferLetterTemplateListByOrgLocationId(Long orgLocationId) throws Exception {
		return offerLetterTemplateDao.getOfferLetterTemplateListByOrgLocationId(orgLocationId);
	}

	@Override
	public OfferLetterTemplate getOfferLetterTemplateDetailsById(Long Id) throws Exception {
		return offerLetterTemplateDao.getOfferLetterTemplateDetailsById(Id);
	}
	
	
	
}