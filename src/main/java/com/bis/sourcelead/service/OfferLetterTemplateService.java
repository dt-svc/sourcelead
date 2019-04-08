package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OfferLetterTemplateDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OfferLetterTemplate;

public interface OfferLetterTemplateService {
 
	OfferLetterTemplate saveOfferLetterTemplate(OfferLetterTemplateDTO offerLetterTemplateDTO) throws Exception;
	 	 
	OfferLetterTemplate getOfferLetterTemplateDetailsByTemplateUUID(String templateUUID)throws Exception;

	OfferLetterTemplate getOfferLetterTemplateDetailsById(Long Id)throws Exception;
	
	ResponseList<OfferLetterTemplate> getAllOfferLetterTemplatesByOrgLocationid(OfferLetterTemplateDTO offerLetterTemplateDTO)throws Exception;

	OfferLetterTemplate validateOfferLetterTemplateByTemplateUUIDAndTemplateName(String templateUUID,String templateName)throws Exception;
	
	Boolean validateOfferLetterTemplateByOrgLocationIdAndTemplateName(Long orgLocationId,String templateName) throws Exception;

	List<OfferLetterTemplate> getOfferLetterTemplateListByOrgLocationId(Long orgLocationId)throws Exception;
}
