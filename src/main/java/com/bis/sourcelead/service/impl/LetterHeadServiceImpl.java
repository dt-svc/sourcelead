package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LetterHeadDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.LetterHeadDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.LetterHeadService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class LetterHeadServiceImpl implements LetterHeadService{

	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	LetterHeadDao letterHeadDao;
	
	@Override
	public LetterHead storeLetterHeadData(LetterHeadDTO letterHeadDTO,OrgLocations orgLocation) throws Exception {
		
		
		
		LetterHead letterHead = null;
		if(StringUtils.isNotBlank(letterHeadDTO.getLetterHeadUUID())){
			letterHead = letterHeadDao.getLetterheadByUUID(letterHeadDTO.getLetterHeadUUID());
		}else{
			letterHead = new LetterHead();
		}
		
		letterHead.setLetterHeadName(letterHeadDTO.getLetterHeadName());
		letterHead.setLetterHeadDescription(letterHeadDTO.getLetterHeadDescription());
		letterHead.setLetterHeadUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		letterHead.setOrgLocationId(orgLocation);
		letterHead.setTemplateBgColor(letterHeadDTO.getTemplateBackgroundColor());
		letterHead.setTemplateType(letterHeadDTO.getTemplateType());
		letterHead.setPaddingLeft(letterHeadDTO.getPaddingLeft());
		letterHead.setPaddingRight(letterHeadDTO.getPaddingRight());
		letterHead.setPaddingTop(letterHeadDTO.getPaddingTop());
		letterHead.setPaddingBottom(letterHeadDTO.getPaddingBottom());
		
		if(letterHeadDTO.getFileInfo() != null) {
			FileInfo fileInfo = letterHeadDTO.getFileInfo().get(0);
			letterHead.setFileName(fileInfo.getFileName());
			letterHead.setFileType(fileInfo.getContentType());
			letterHead.setFileURL(fileInfo.getFileURL());
        	FileUploadUtil.moveFile(staticPath, fileInfo, "LetterHeadDocs");
        }
		
		letterHead.setAlignLogo(letterHeadDTO.getAlignLogo());
		letterHead.setHeaderBgColor(letterHeadDTO.getHeaderBackgroundColor());
		letterHead.setHeaderText(letterHeadDTO.getHeaderText());
		letterHead.setAlignHeaderText(letterHeadDTO.getHeaderTextAlign());
		letterHead.setFooterText(letterHeadDTO.getFooterText());
		letterHead.setAlignFooterText(letterHeadDTO.getAlignFooter());
		letterHead.setFooterBgColor(letterHeadDTO.getFooterBackgroundColor());
		letterHead.setStatus(Constants.MEMBER_ACTIVE);
		
		return letterHeadDao.storeLetterHeadData(letterHead);
	}

	@Override
	public ResponseList<LetterHeadDTO> getAllLetterHeadList(LetterHeadDTO letterHeadDTO,Long orgLocationId) {
		return letterHeadDao.getAllLetterHeadList(letterHeadDTO,orgLocationId);
	}

	@Override
	public List<LetterHead> getLetterheadBylocationId(Long orgLocationId) {
		return letterHeadDao.getLetterheadBylocationId(orgLocationId);
	}

	@Override
	public LetterHead getLetterheadByUUID(String UUID) {
		return letterHeadDao.getLetterheadByUUID(UUID);
	}

	@Override
	public LetterHead getLetterheadById(Long letterHeadId) {
		return letterHeadDao.getLetterheadById(letterHeadId);
	}
	
	

}
