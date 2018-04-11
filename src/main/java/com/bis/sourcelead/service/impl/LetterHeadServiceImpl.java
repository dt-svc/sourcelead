package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.LetterHeadDao;
import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.util.FileUploadUtil;

@Service
public class LetterHeadServiceImpl implements LetterHeadService{

	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	LetterHeadDao letterHeadDao;
	
	@Override
	public LetterHead storeLetterHeadData(LetterHeadDTO letterHeadDTO) throws Exception {
		
		LetterHead letterHead = new LetterHead();
		letterHead.setLetterHeadName(letterHeadDTO.getLetterHeadName());
		letterHead.setLetterHeadDescription(letterHeadDTO.getLetterHeadDescription());
		letterHead.setTemplateBgColor(letterHeadDTO.getTemplateBackgroundColor());
		letterHead.setTemplateType(letterHeadDTO.getTemplateType());
		letterHead.setPaddingLeft(letterHeadDTO.getPaddingLeft());
		letterHead.setPaddingRight(letterHeadDTO.getPaddingRight());
		letterHead.setPaddingTop(letterHeadDTO.getPaddingTop());
		letterHead.setPaddingBottom(letterHeadDTO.getPaddingBottom());
		
		if(letterHeadDTO.getFileInfo() != null) {
        	FileUploadUtil.moveFile(staticPath, letterHeadDTO.getFileInfo().get(0), "LetterHeadDocs");
        }
		
		letterHead.setAlignLogo(letterHeadDTO.getAlignLogo());
		letterHead.setHeaderBgColor(letterHeadDTO.getHeaderBackgroundColor());
		letterHead.setHeaderText(letterHeadDTO.getHeaderText());
		letterHead.setAlignHeaderText(letterHeadDTO.getHeaderTextAlign());
		letterHead.setFooterText(letterHeadDTO.getFooterText());
		letterHead.setAlignFooterText(letterHeadDTO.getAlignFooter());
		letterHead.setFooterBgColor(letterHeadDTO.getFooterBackgroundColor());
		
		return letterHeadDao.storeLetterHeadData(letterHead);
	}
	
	

}
